#ifndef Graphics_hpp
#define Graphics_hpp

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <vector>

#include "IOUtils.hpp"

namespace transitoreality {
    bool initGL();
    GLFWwindow *createWindow(int width, int height, const std::string &title);
    bool initGLEW();
    
    struct VBO {
        const GLuint id;
        VBO();
        ~VBO();
        void bind(GLenum target=GL_ARRAY_BUFFER) const;
        
        bool operator ==(const VBO &other) const {
            return id == other.id;
        }
        
        template<class T>
        void bufferData(const std::vector<T> &data, GLenum target=GL_ARRAY_BUFFER, GLenum drawType=GL_STATIC_DRAW) const {
            bind();
            glBufferData(target, data.size() * sizeof(T), &data[0], drawType);
        }
        VBO(const VBO& vbo) = delete;
    };
    
    struct VAO {
        const GLuint id;
        VAO();
        ~VAO();
        void bind() const;
        bool operator ==(const VAO &other) const {
            return id == other.id;
        }
        void addAttribPointer(std::shared_ptr<VBO> vbo, GLuint index, GLint size, GLenum type, GLboolean normalised=GL_FALSE, GLsizei stride=0, const GLvoid *offset=NULL);
        VAO(const VAO& vao) = delete;
    private:
        std::vector<std::shared_ptr<VBO>> vbos;
    };
    
    struct Shader {
        const GLuint id;
        const GLenum shaderType;
        Shader(GLenum shaderType, const std::string &shaderPath);
        ~Shader();
        
        bool operator ==(const Shader &other) const {
            return id == other.id;
        }
        Shader(const Shader& vao) = delete;
    };
    
    struct ShaderProgram {
        const GLuint id;
        ShaderProgram();
        ~ShaderProgram();
        void use() const;
        
        bool operator ==(const ShaderProgram &other) const {
            return id == other.id;
        }
        ShaderProgram(const ShaderProgram& vao) = delete;
    };
    
    struct Uniform {
        const GLint id;
        Uniform(const ShaderProgram &program, const std::string &name);
        
        bool operator ==(const Uniform &other) const {
            return id == other.id;
        }
    };
    
    struct Texture {
        const GLuint id;
        const int width;
        const int height;
        const int comp;
        
        ~Texture();
        void bind() const;
        
        static std::shared_ptr<Texture> create(const std::string &path);
        
        bool operator ==(const ShaderProgram &other) const {
            return id == other.id;
        }
        Texture(const Texture& texture) = delete;
    private:
        Texture(GLuint id, int width, int height, int comp);
    };
}

#endif /* Graphics_hpp */
