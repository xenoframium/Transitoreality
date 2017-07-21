#ifndef TexturedRenderable_hpp
#define TexturedRenderable_hpp

#include "Graphics.hpp"
#include "Rendering.hpp"

namespace transitoreality {
    struct TexturedRenderable : Component {
        friend class TexturedRenderSystem;
        
        const GLenum renderMode;
        const GLint startIndex;
        const GLsizei length;
        const std::shared_ptr<VAO> vao;
        
        std::shared_ptr<Texture> texture;
    private:
        TexturedRenderable(GLenum renderMode, GLint startIndex, GLsizei length, std::shared_ptr<VAO> vao, std::shared_ptr<Texture> texture);
    };
    
    class TexturedRenderSystem : public System<TexturedRenderable, TransformComponent> {
        static const std::string VERTEX_SHADER_PATH;
        static const std::string FRAGMENT_SHADER_PATH;
        static const std::string MVP_UNIFORM_NAME;
        
        static bool hasInitialisedShaderProgram;
        
        const Uniform mvpUniform;
        const std::shared_ptr<ShaderProgram> program;
        
        TexturedRenderSystem(std::shared_ptr<ShaderProgram> program, Uniform mvpUniform, Camera &camera, Projection &projection);
        
        void checkVAO(const TexturedRenderable &renderable);
        void renderEntity(const glm::mat4 &vp, const Entity &entity);
        
        Projection &projection;
        Camera &camera;
        
    public:
        static TexturedRenderSystem create(Camera &camera, Projection &projection);
        
        TexturedRenderable createRenderable(const std::vector<float> &vertices, const std::vector<float> &uvs, std::shared_ptr<Texture> texture, GLenum renderMode=GL_TRIANGLES);
        
        virtual void update(const EntityManager& entity, double deltaTime);
    };
}

#endif /* TexturedRenderable_hpp */
