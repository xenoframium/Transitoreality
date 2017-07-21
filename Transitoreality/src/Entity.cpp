#include <cassert>

#include "Entity.hpp"

using namespace transitoreality;

int Entity::counter = 0;

bool Entity::operator ==(const Entity &other) const {
    return this->id == other.id;
}

void EntityManager::assertIfEntityDestroyed(Entity entity) const {
    assert("Attempted to access a destroyed entity." && !(entityToComponents.find(entity) == entityToComponents.end()));
}

Entity EntityManager::createEntity() {
    Entity entity(*this);
    entityToComponents[entity] = std::make_unique<std::unordered_map<std::type_index, std::unique_ptr<Component>>>();
    return entity;
}

void EntityManager::destroyEntity(Entity entity) {
    entityToComponents.erase(entity);
}

void EntityManager::subscribeSystem(BaseSystem *system, SystemPriority priority) {
    systems[priority].insert(system);
    for (auto &entity : entityToComponents) {
        system->inspectEntity(entity.first);
    }
}

void EntityManager::notifySystems(Entity entity) const {
    for (auto priority : systems) {
        for (auto system : priority.second) {
            system->inspectEntity(entity);
        }
    }
}

void EntityManager::unsubscribeSystem(BaseSystem *system) {
    systems[systemsToPriorities[system]].erase(system);
}

void EntityManager::updateSystems() {
    double currentTime = glfwGetTime();
    double deltaTime = currentTime - lastTime;
    lastTime = currentTime;
    
    for (auto system : systems[SYSTEM_PRIORITY_FIRST]) {
        system->update(*this, deltaTime);
    }
    
    for (auto system : systems[SYSTEM_PRIORITY_HIGH]) {
        system->update(*this, deltaTime);
    }
    
    for (auto system : systems[SYSTEM_PRIORITY_NORMAL]) {
        system->update(*this, deltaTime);
    }
    
    for (auto system : systems[SYSTEM_PRIORITY_LOW]) {
        system->update(*this, deltaTime);
    }
    
    for (auto system : systems[SYSTEM_PRIORITY_LAST]) {
        system->update(*this, deltaTime);
    }
}
