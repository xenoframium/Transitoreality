//
//  2DTexturedRenderable.hpp
//  ECS
//
//  Created by Chris Jung on 18/07/17.
//  Copyright © 2017 Chris Jung. All rights reserved.
//

#ifndef _TexturedRenderable2D_hpp
#define _TexturedRenderable2D_hpp

#include "Graphics.hpp"
#include "TexturedRenderable.hpp"
/*
namespace gfx {
    struct TexturedRenderable2D : ecs::Component {
        friend class TexturedRenderSystem2D;
        
        const GLenum renderMode;
        const GLint startIndex;
        const GLsizei length;
        const std::shared_ptr<gl::VAO> vao;
        
        std::shared_ptr<gl::Texture> texture;
    private:
        TexturedRenderable2D(GLenum renderMode, GLint startIndex, GLsizei length, std::shared_ptr<gl::VAO> vao, std::shared_ptr<gl::Texture> texture);
    };
    
    struct TexturedRenderSystem2D : public ecs::System<TexturedRenderable, TransformComponent> {
        std::shared_ptr<Projection> projection;
        
        std::shared_ptr<gl::VAO> currentVAO;
        std::shared_ptr<gl::VBO> currentVertexVBO;
        std::shared_ptr<gl::VBO> currentUVVBO;
        std::vector<float> unsentVertices;
        std::vector<float> unsentUVs;
        
        static TexturedRenderSystem create(Projection &projection);
        
        std::shared_ptr<TexturedRenderable2D> createRenderable(const std::vector<float> &vertices, const std::vector<float> &uvs, std::shared_ptr<gl::Texture> texture);
        
        virtual void update(const ecs::EntityManager& entity, double deltaTime);
        
    private:
        static const std::string VERTEX_SHADER_PATH;
        static const std::string FRAGMENT_SHADER_PATH;
        static const std::string MVP_UNIFORM_NAME;
        
        static bool hasInitialisedShaderProgram;
                  
        const gl::Uniform mvpUniform;
        const std::shared_ptr<gl::ShaderProgram> program;
        
        TexturedRenderSystem2D(std::shared_ptr<gl::ShaderProgram> program, gl::Uniform mvpUniform, Projection &projection);
        
        void checkVAO(const TexturedRenderable2D &renderable);
    };
}
*/
#endif /* _DTexturedRenderable_hpp */
