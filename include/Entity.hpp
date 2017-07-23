#ifndef Entity_hpp
#define Entity_hpp

#include <typeindex>
#include <unordered_map>
#include <unordered_set>
#include <functional>
#include <vector>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
namespace transitoreality {
    class EntityManager;
    
    class Entity {
        friend class EntityManager;
        friend struct std::hash<Entity>;
        
        static int counter;
        
        const int id = counter++;

        Entity(EntityManager& manager) : entityManager(manager) {}
    public:
        EntityManager& entityManager;
        bool operator ==(const Entity& other) const;
        
        template<class T, class... Args>
        void addComponent(Args... args) const;
        
        template<class T>
        bool hasComponent() const;
        
        template<class T>
        T* getComponent() const;
        
        template<class T>
        void removeComponent() const;
    };
}

namespace std {
    template<>
    struct hash<transitoreality::Entity> {
        size_t operator()(const transitoreality::Entity& entity) const {
            return std::hash<int>()(entity.id);
        }
    };
}

namespace transitoreality {
    struct Component {};
    
    namespace details {
        template<class... Ts>
        struct TypeChecker {
            static bool check(Entity entity) {
                return true;
            }
        };
        
        template<class T, class... Ts>
        struct TypeChecker<T, Ts...> {
            static bool check(Entity entity) {
                if (!entity.hasComponent<T>()) {
                    return false;
                } else {
                    return TypeChecker<Ts...>::check(entity);
                }
            }
        };
    }
    
    enum SystemPriority {
        SYSTEM_PRIORITY_FIRST,
        SYSTEM_PRIORITY_HIGH,
        SYSTEM_PRIORITY_NORMAL,
        SYSTEM_PRIORITY_LOW,
        SYSTEM_PRIORITY_LAST
    };
    
    class BaseSystem {
        friend class EntityManager;
        
        virtual void inspectEntity(Entity) = 0;
        virtual void removeEntity(Entity) = 0;
        virtual void update(const EntityManager&, double) = 0;
    };
    
    template<class... RequiredComponents>
    class System : public BaseSystem {
        virtual void inspectEntity(Entity entity) {
            if (details::TypeChecker<RequiredComponents...>::check(entity)) {
                entities.insert(entity);
            } else {
                entities.erase(entity);
            }
        }
        
        virtual void removeEntity(Entity entity) {
            entities.erase(entity);
        }
        
    protected:
        std::unordered_set<Entity> entities;
    };
    
    class EntityManager {
        std::unordered_map<Entity, std::unique_ptr<std::unordered_map<std::type_index, std::unique_ptr<Component>>>> entityToComponents;
        std::unordered_map<SystemPriority, std::unordered_set<BaseSystem*>> systems;
        std::unordered_map<BaseSystem*, SystemPriority> systemsToPriorities;
        
        double lastTime = 0;
        
        void assertIfEntityDestroyed(Entity entity) const;
        void notifySystems(Entity entity) const;
        
    public:
        Entity createEntity();
        
        void destroyEntity(Entity entity);
        
        template<class T, class... Args>
        void addComponent(Entity entity, Args... args) {
            assertIfEntityDestroyed(entity);
            static_assert(std::is_base_of<Component, T>(), "Item added must be a Component.");
            
            std::type_index type(typeid(T));
            (*entityToComponents[entity])[type] = std::make_unique<T>(args...);
            
            notifySystems(entity);
        }
        
        template<class T>
        void removeComponent(Entity entity) {
            assertIfEntityDestroyed(entity);
            static_assert(std::is_base_of<Component, T>(), "Item removed must be a Component.");
            
            std::type_index type(typeid(T));
            entityToComponents[entity]->erase(type);
            
            notifySystems(entity);
        }
        
        template<class T>
        bool hasComponent(Entity entity) const {
            assertIfEntityDestroyed(entity);
            
            static_assert(std::is_base_of<Component, T>(), "Item queried must be a Component.");
            std::type_index type(typeid(T));
            auto& entityComponents = entityToComponents.find(entity)->second;
            return entityComponents->find(type) != entityComponents->end();
        }
        
        template<class T>
        T* getComponent(Entity entity) const {
            assertIfEntityDestroyed(entity);
            static_assert(std::is_base_of<Component, T>(), "Item requested must be a Component.");
            
            std::type_index type(typeid(T));
            auto& entityComponents = entityToComponents.find(entity)->second;
            auto it = entityComponents->find(type);
            if (it == entityComponents->end()) {
                return nullptr;
            } else {
                return static_cast<T*>(it->second.get());
            }
        }
        
        void subscribeSystem(BaseSystem *system, SystemPriority priority);
        void unsubscribeSystem(BaseSystem *system);
        void updateSystems();
    };
    
    template<class T, class... Args>
    void Entity::addComponent(Args... args) const {
        entityManager.addComponent<T>(*this, args...);
    }
    
    template<class T>
    bool Entity::hasComponent() const {
        return entityManager.hasComponent<T>(*this);
    }
    
    template<class T>
    T* Entity::getComponent() const {
        return entityManager.getComponent<T>(*this);
    }
    
    template<class T>
    void Entity::removeComponent() const {
        entityManager.removeComponent<T>(*this);
    }
}

#endif /* Entity_hpp */
