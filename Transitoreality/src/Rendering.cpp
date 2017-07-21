#include "Rendering.hpp"

using namespace transitoreality;

Camera Camera::createIsometricCamera(float distance, glm::vec3 target) {
    float offset = sqrtf(distance / 3.0f);
    glm::vec3 cameraLocation(offset, offset, -offset);
    return Camera(cameraLocation + target, target);
}

Camera::Camera(glm::vec3 pos, glm::vec3 target) {
    viewMatrix = glm::lookAt(pos, target, glm::vec3(0, 1, 0));
}

Projection::Projection(float aspect, float near, float far) : projectionMatrix(glm::ortho(-1.f, 1.f, -aspect, aspect, near, far)) {}

glm::mat4 TransformSystem::dfs(Entity *entity) {
    TransformComponent* comp = entity->getComponent<TransformComponent>();
    if (comp->lastUpdated == loopNumber) {
        return comp->modelMatrix;
    }
    comp->modelMatrix = glm::translate(comp->pos) * glm::toMat4(comp->rot) * glm::scale(comp->scale);
    
    if (comp->parent != nullptr) {
        comp->modelMatrix = dfs(comp->parent) * comp->modelMatrix;
    }
    
    return comp->modelMatrix;
}

void TransformSystem::update(const EntityManager &manager, double deltaTime) {
    for (auto entity : entities) {
        dfs(&entity);
    }
}
