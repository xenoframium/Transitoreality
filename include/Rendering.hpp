#ifndef Renderable_hpp
#define Renderable_hpp

#include <cmath>
#include <glm/glm.hpp>
#include <glm/gtx/transform.hpp>
#include <glm/gtx/quaternion.hpp>

#include "Entity.hpp"
#include "Graphics.hpp"
#include "EntityAssembler.hpp"

namespace transitoreality {
    struct Camera {
        static Camera createIsometricCamera(float distance, glm::vec3 target=glm::vec3(0, 0, 0));
        
        glm::mat4 viewMatrix;
        Camera(glm::vec3 pos, glm::vec3 target);
    };
    
    struct Projection {
        const glm::mat4 projectionMatrix;
        Projection(float aspect, float near, float far);
    };
    
    struct TransformComponent : Component {
        friend class TransformSystem;

        static void assemble(Entity entity, const std::vector<ComponentVariable> &componentVariables);
        Entity *parent;
        
        glm::vec3 pos;
        glm::vec3 scale = {1, 1, 1};
        glm::quat rot;
        
        glm::mat4 modelMatrix;
    private:
        unsigned int lastUpdated = -1;
    };
    
    struct TransformSystem : System<TransformComponent> {
        virtual void update(const EntityManager &manager, double deltaTime);
    private:
        unsigned int loopNumber = 0;
        
        glm::mat4 dfs(Entity *entity);
    };
}

#endif /* Renderable_hpp */
