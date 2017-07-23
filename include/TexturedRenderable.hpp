#ifndef TexturedRenderable_hpp
#define TexturedRenderable_hpp

#include "Graphics.hpp"
#include "Rendering.hpp"
#include "EntityAssembler.hpp"

namespace transitoreality {
    class TexturedRenderSystem;

    struct TexturedRenderable : Component {
        friend class TexturedRenderSystem;

        static void assemble(Entity entity, const std::vector<ComponentVariable> &componentVariables);
        static TexturedRenderable createRenderable(const std::vector<float> &vertices, const std::vector<float> &uvs, std::shared_ptr<Texture> texture, GLenum renderMode=GL_TRIANGLES);

        const GLenum renderMode;
        const GLint startIndex;
        const GLsizei length;
        const std::shared_ptr<VAO> vao;
        
        std::shared_ptr<Texture> texture;

    private:
        TexturedRenderable(GLenum renderMode, GLint startIndex, GLsizei length, std::shared_ptr<VAO> vao, std::shared_ptr<Texture> texture);
    };

    class TexturedRenderSystem : public System<TexturedRenderable, TransformComponent> {
        Projection &projection;
        Camera &camera;

        void renderEntity(const glm::mat4 &vp, const Entity &entity);

        virtual void update(const EntityManager& entity, double deltaTime);
    public:
        TexturedRenderSystem(Camera &camera, Projection &projection);
    };
}

#endif /* TexturedRenderable_hpp */
