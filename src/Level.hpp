#ifndef TRANSITOREALITY_LEVEL_HPP
#define TRANSITOREALITY_LEVEL_HPP

#include <vector>

#include "IOUtils.hpp"
#include "Entity.hpp"

namespace transitoreality {
    class Level : public Entity {

    public:
        Level(EntityManager &manager, std::string path) : Entity(manager.createEntity()) {

        }

    };
}

#endif //TRANSITOREALITY_LEVEL_HPP
