#include "TexturedRenderable.hpp"
#include <iostream>
#include <glm/ext.hpp>

using namespace transitoreality;

TexturedRenderable::TexturedRenderable(GLenum renderMode, GLint startIndex, GLsizei length, std::shared_ptr<VAO> vao, std::shared_ptr<Texture> texture) : renderMode(renderMode), startIndex(startIndex), length(length), vao(vao), texture(texture) {};

const std::string TexturedRenderSystem::VERTEX_SHADER_PATH = "shaders/TexturedVertexShader.vert";
const std::string TexturedRenderSystem::FRAGMENT_SHADER_PATH = "shaders/TexturedFragmentShader.frag";
const std::string TexturedRenderSystem::MVP_UNIFORM_NAME = "mvp";

bool TexturedRenderSystem::hasInitialisedShaderProgram = false;

TexturedRenderSystem TexturedRenderSystem::create(Camera &camera, Projection &projection) {
    static std::shared_ptr<ShaderProgram> staticShaderProgram = std::make_shared<ShaderProgram>();
    
    if (!hasInitialisedShaderProgram) {
        Shader vs(GL_VERTEX_SHADER, VERTEX_SHADER_PATH);
        Shader fs(GL_FRAGMENT_SHADER, FRAGMENT_SHADER_PATH);
        
        glAttachShader(staticShaderProgram->id, fs.id);
        glAttachShader(staticShaderProgram->id, vs.id);
        glLinkProgram(staticShaderProgram->id);
        glDetachShader(staticShaderProgram->id, fs.id);
        glDetachShader(staticShaderProgram->id, vs.id);
        
        hasInitialisedShaderProgram = true;
    }
    
    static Uniform staticMvpUniform(*staticShaderProgram.get(), MVP_UNIFORM_NAME);
    
    return TexturedRenderSystem(staticShaderProgram, staticMvpUniform, camera, projection);
}

TexturedRenderSystem::TexturedRenderSystem(std::shared_ptr<ShaderProgram> program, Uniform mvpUniform, Camera &camera, Projection &projection) : program(program), mvpUniform(mvpUniform), projection(projection), camera(camera) {};

TexturedRenderable TexturedRenderSystem::createRenderable(const std::vector<float> &vertices, const std::vector<float> &uvs, std::shared_ptr<Texture> texture, GLenum renderMode) {
    
    std::shared_ptr<VAO> vao = std::make_shared<VAO>();
    std::shared_ptr<VBO> posVBO = std::make_shared<VBO>();
    std::shared_ptr<VBO> uvVBO = std::make_shared<VBO>();
    
    posVBO->bufferData(vertices);
    uvVBO->bufferData(uvs);
    vao->addAttribPointer(posVBO, 0, 3, GL_FLOAT);
    vao->addAttribPointer(uvVBO, 1, 2, GL_FLOAT);
    
    return TexturedRenderable(renderMode, 0, (GLsizei) vertices.size(), vao, texture);
}

void TexturedRenderSystem::renderEntity(const glm::mat4 &vp, const Entity &entity) {
    TexturedRenderable *renderable = entity.getComponent<TexturedRenderable>();
    TransformComponent *transform = entity.getComponent<TransformComponent>();
    
    renderable->vao->bind();
    renderable->texture->bind();
    
    glm::mat4 mvp = vp * transform->modelMatrix;
    glUniformMatrix4fv(mvpUniform.id, 1, GL_FALSE, &mvp[0][0]);
    
    glDrawArrays(renderable->renderMode, renderable->startIndex, renderable->length);
}

void TexturedRenderSystem::update(const EntityManager& entity, double deltaTime) {
    glEnable(GL_DEPTH_TEST);
    glm::mat4 vp = projection.projectionMatrix * camera.viewMatrix;
    
    program->use();
    
    for (Entity entity : entities) {
        renderEntity(vp, entity);
    }
}
