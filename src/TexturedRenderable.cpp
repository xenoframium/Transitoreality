#include "TexturedRenderable.hpp"
#include <iostream>
#include <glm/ext.hpp>

using namespace transitoreality;

const std::string VERTEX_SHADER_PATH = "shaders/TexturedVertexShader.vert";
const std::string FRAGMENT_SHADER_PATH = "shaders/TexturedFragmentShader.frag";
const std::string MVP_UNIFORM_NAME = "mvp";

std::unique_ptr<Uniform> mvpUniform;
std::unique_ptr<ShaderProgram> program;

bool hasInitialisedShaderProgram = false;

void checkAndInitialiseShaderProgram() {
    if (!hasInitialisedShaderProgram) {
        program = std::make_unique<ShaderProgram>();

        Shader vs(GL_VERTEX_SHADER, VERTEX_SHADER_PATH);
        Shader fs(GL_FRAGMENT_SHADER, FRAGMENT_SHADER_PATH);

        glAttachShader(program->id, fs.id);
        glAttachShader(program->id, vs.id);
        glLinkProgram(program->id);
        glDetachShader(program->id, fs.id);
        glDetachShader(program->id, vs.id);

        mvpUniform = std::make_unique<Uniform>(*program.get(), MVP_UNIFORM_NAME);

        hasInitialisedShaderProgram = true;
    }
}

TexturedRenderable::TexturedRenderable(GLenum renderMode, GLint startIndex, GLsizei length, std::shared_ptr<VAO> vao, std::shared_ptr<Texture> texture) : renderMode(renderMode), startIndex(startIndex), length(length), vao(vao), texture(texture) {};

struct UnsupportedDrawMode : std::runtime_error {
    UnsupportedDrawMode(const std::string &mode) : std::runtime_error("Unsupported draw mode: " + mode) {}
};

GLenum getRenderMode(const std::string &renderMode) {
    if (renderMode == "GL_TRIANGLES") {
        return GL_TRIANGLES;
    } else if (renderMode == "GL_TRIANGLE_STRIP") {
        return GL_TRIANGLE_STRIP;
    } else {
        throw UnsupportedDrawMode(renderMode);
    }
}

std::vector<float> readFloats(const std::string &data) {
    std::stringstream stream(data);
    float currentFloat;
    std::vector<float> result;
    while (stream >> currentFloat) {
        result.push_back(currentFloat);
        if (stream.peek() == ',') {
            stream.ignore();
        }
    }
    return result;
}

std::vector<std::string> required = {"Texture", "RenderMode", "Vertices", "UVs"};

void TexturedRenderable::assemble(Entity entity, const std::vector<ComponentVariable> &componentVariables) {
    if (!doVariablesMatch(componentVariables, required)) {
        throw std::runtime_error("Fields in TexturedRenderable component does not match requirements.");
    }

    std::shared_ptr<Texture> texture;
    GLenum renderMode;
    std::vector<float> vertices;
    std::vector<float> uvs;

    for (auto& componentVariable : componentVariables) {
        const std::string &name = componentVariable.name;
        const std::string &data = componentVariable.data;
        std::stringstream stream(data);
        if (name == "Texture") {
            texture = Texture::create(data);
        } else if (name == "RenderMode") {
            renderMode = getRenderMode(data);
        } else if (name == "Vertices") {
            vertices = readFloats(data);
        } else if (name == "UVs") {
            uvs = readFloats(data);
        }
    }

    entity.addComponent<TexturedRenderable>(TexturedRenderable::createRenderable(vertices, uvs, texture, renderMode));
}

TexturedRenderable TexturedRenderable::createRenderable(const std::vector<float> &vertices, const std::vector<float> &uvs, std::shared_ptr<Texture> texture, GLenum renderMode) {
    std::shared_ptr<VAO> vao = std::make_shared<VAO>();
    std::shared_ptr<VBO> posVBO = std::make_shared<VBO>();
    std::shared_ptr<VBO> uvVBO = std::make_shared<VBO>();

    posVBO->bufferData(vertices);
    uvVBO->bufferData(uvs);
    vao->addAttribPointer(posVBO, 0, 3, GL_FLOAT);
    vao->addAttribPointer(uvVBO, 1, 2, GL_FLOAT);

    return TexturedRenderable(renderMode, 0, (GLsizei) vertices.size(), vao, texture);
}

TexturedRenderSystem::TexturedRenderSystem(Camera &camera, Projection &projection) : camera(camera), projection(projection){
    checkAndInitialiseShaderProgram();
}

void TexturedRenderSystem::renderEntity(const glm::mat4 &vp, const Entity &entity) {
    TexturedRenderable *renderable = entity.getComponent<TexturedRenderable>();
    TransformComponent *transform = entity.getComponent<TransformComponent>();
    
    renderable->vao->bind();
    renderable->texture->bind();
    
    glm::mat4 mvp = vp * transform->modelMatrix;
    glUniformMatrix4fv(mvpUniform->id, 1, GL_FALSE, &mvp[0][0]);
    
    glDrawArrays(renderable->renderMode, renderable->startIndex, renderable->length);
}

void TexturedRenderSystem::update(const EntityManager& entityManager, double deltaTime) {
    glEnable(GL_DEPTH_TEST);
    glm::mat4 vp = projection.projectionMatrix * camera.viewMatrix;
    
    program->use();
    
    for (Entity entity : entities) {
        renderEntity(vp, entity);
    }
}
