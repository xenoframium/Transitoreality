//
// Created by Chris Jung on 23/07/17.
//

#ifndef TRANSITOREALITY_ENTITYASSEMBLER_HPP
#define TRANSITOREALITY_ENTITYASSEMBLER_HPP

#include <vector>
#include <unordered_map>

#include "Entity.hpp"

namespace transitoreality {
    struct ComponentVariable;

    struct ComponentVariable {
        friend class EntityAssembler;

        const std::string name;
        const std::string data;
    private:
        ComponentVariable(const std::string &name, const std::string &data);
    };

    bool doVariablesMatch(const std::vector<ComponentVariable> &variables, const std::vector<std::string> &required);

    struct InvalidEntityFileSyntax : std::runtime_error {
        InvalidEntityFileSyntax(const std::string &text) : std::runtime_error(text) {}
    };

    class EntityAssembler {
        typedef void (*Assembler)(Entity, const std::vector<ComponentVariable> &);
        std::unordered_map<std::string, Assembler> componentAssemblers;
        std::unordered_map<std::string, Entity> entitiesByName;
        std::unordered_map<std::string, Entity> entitiesByPath;

        EntityManager &manager;

        void assembleComponent(Entity entity, const std::string &componentContent, Assembler assembler);
        Assembler getComponentAssembler(const std::string &name);
        void assembleComponents(Entity entity, std::stringstream &stream);
    public:
        EntityAssembler(EntityManager &manager);

        void registerComponentAssembler(const std::string &identifier, Assembler assembler);
        Entity getEntity(const std::string &name);
        Entity assembleEntity(const std::string &path);
    };
}

#endif //TRANSITOREALITY_ENTITYASSEMBLER_HPP
