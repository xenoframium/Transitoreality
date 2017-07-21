#define STB_IMAGE_IMPLEMENTATION

#include <stb/stb_image.h>

#include "Graphics.hpp"

using namespace transitoreality;
namespace trlty = transitoreality;

bool trlty::initGL() {
    return glfwInit();
}

GLFWwindow* trlty::createWindow(int width, int height, const std::string &title) {
#ifdef __APPLE__
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
#endif
    
    GLFWwindow* window = glfwCreateWindow(width, height, title.c_str(), NULL, NULL);
    if (!window) {
        glfwTerminate();
        return nullptr;
    }
    glfwMakeContextCurrent(window);
    return window;
}

bool trlty::initGLEW() {
    glewExperimental = GL_TRUE;
    if (glewInit() != GLEW_OK) {
        return false;
    }
    return true;
}

GLuint trlty::details::genBuffer() {
    GLuint tempID;
    glGenBuffers(1, &tempID);
    return tempID;
}

GLuint trlty::details::genArray() {
    GLuint tempID;
    glGenVertexArrays(1, &tempID);
    return tempID;
}

VBO::~VBO() {
    glDeleteBuffers(1, &id);
}

void VBO::bind(GLenum target) const {
    glBindBuffer(target, id);
}

void VAO::addAttribPointer(std::shared_ptr<VBO> vbo, GLuint index, GLint size, GLenum type, GLboolean normalised, GLsizei stride, const GLvoid *offset) {
    vbos[index] = vbo;
    bind();
    vbo->bind();
    glEnableVertexAttribArray(index);
    glVertexAttribPointer(index, size, type, normalised, stride, offset);
}

VAO::~VAO() {
    glDeleteVertexArrays(1, &id);
}

void VAO::bind() const {
    glBindVertexArray(id);
}

Shader::Shader(GLenum shaderType, const std::string &shaderPath) : id(glCreateShader(shaderType)), shaderType(shaderType) {
    std::string source = readFileToString(std::move(shaderPath));
    const char* shaderSource = source.c_str();
    glShaderSource(id, 1, &shaderSource, NULL);
    glCompileShader(id);
}

Shader::~Shader() {
    glDeleteShader(id);
}

ShaderProgram::ShaderProgram() : id(glCreateProgram()) {}

ShaderProgram::~ShaderProgram() {
    glDeleteProgram(id);
}

void ShaderProgram::use() const {
    glUseProgram(id);
}

Uniform::Uniform(const ShaderProgram &program, const std::string &name) : id(glGetUniformLocation(program.id, name.c_str())) {}

Texture::Texture(GLuint id, int width, int height, int comp) : id(id), width(width), height(height), comp(comp) {}

Texture::~Texture() {
    glDeleteTextures(1, &id);
}

void Texture::bind() const {
    glBindTexture(GL_TEXTURE_2D, id);
}

std::shared_ptr<Texture> Texture::create(const std::string &path) {
    stbi_set_flip_vertically_on_load(true);
    
    int width, height, comp;
    float* image = stbi_loadf(path.c_str(), &width, &height, &comp, STBI_default);
    
    if(!image) {
        throw(std::string("Failed to load texture from " + path + "."));
    }
    
    GLuint id;
    glGenTextures(1, &id);
    
    glBindTexture(GL_TEXTURE_2D, id);
    
    if(comp == STBI_rgb) {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_FLOAT, image);
    } else if(comp == STBI_rgb_alpha) {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_FLOAT, image);
    }
    
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    
    stbi_image_free(image);
    
    return std::shared_ptr<Texture>(new Texture(id, width, height, comp));
}
