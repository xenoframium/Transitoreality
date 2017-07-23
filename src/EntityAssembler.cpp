//
// Created by Chris Jung on 23/07/17.
//

#include <sstream>

#include "EntityAssembler.hpp"
#include "IOUtils.hpp"

using namespace transitoreality;

typedef void (*Assembler)(Entity, const std::vector<ComponentVariable> &);

ComponentVariable::ComponentVariable(const std::string &name, const std::string &data) : name(name), data(data) {}

void advanceWhitespace(std::stringstream &contentStream) {
    while (isspace(contentStream.peek())) {
        contentStream.ignore();
    }
}

bool transitoreality::doVariablesMatch(const std::vector<ComponentVariable> &variables, const std::vector<std::string> &required) {
    if (required.size() != variables.size()) {
        return false;
    }
    std::unordered_set<std::string> variableNames(variables.size() * 2);
    for (auto i : variables) {
        variableNames.insert(i.name);
    }

    variableNames.insert(required.begin(), required.end());
    return variableNames.size() == variables.size();
}

EntityAssembler::EntityAssembler(EntityManager &manager) : manager(manager) {}

void EntityAssembler::registerComponentAssembler(const std::string &identifier, Assembler assembler){
    componentAssemblers[identifier] = assembler;
}

Entity EntityAssembler::getEntity(const std::string &name) {
    auto it = entitiesByName.find(name);
    if (it == entitiesByName.end()) {
        throw(std::string("Entity: " + name + " not loaded."));
    }
    return it->second;
}

struct MissingAssembler : std::runtime_error {
    MissingAssembler(const std::string &assemblerName) : std::runtime_error("Missing assembler of name: " + assemblerName) {}
};

bool doesStartWithName(std::stringstream &stream) {
    std::string temp;
    std::getline(stream, temp, ':');
    return temp == "Name";
}

std::string getName(std::stringstream &stream) {
    if (!doesStartWithName(stream)) {
        throw InvalidEntityFileSyntax("Entity file missing name.");
    }

    std::string name;
    if (!std::getline(stream, name, ';')) {
        throw InvalidEntityFileSyntax("Entity file missing name.");
    }

    return name;
}

void EntityAssembler::assembleComponent(Entity entity, const std::string &componentContent, Assembler assembler) {
    std::stringstream contentStream(componentContent);
    std::string variableName, value, temp;
    std::vector<ComponentVariable> componentVariables;

    while (isspace(contentStream.peek())) {
        contentStream.ignore();
    }

    advanceWhitespace(contentStream);
    while (std::getline(contentStream, variableName, ':')) {
        advanceWhitespace(contentStream);
        if (!std::getline(contentStream, value, ';')) {
            throw InvalidEntityFileSyntax("Variable missing value.");
        }

        componentVariables.push_back(ComponentVariable(variableName, value));
        std::getline(contentStream,temp);
        advanceWhitespace(contentStream);
    }

    assembler(entity, componentVariables);
}

Assembler EntityAssembler::getComponentAssembler(const std::string &name) {
    auto it = componentAssemblers.find(name);
    if (it == componentAssemblers.end()) {
        throw MissingAssembler(name);
    }
    return it->second;
}

void EntityAssembler::assembleComponents(Entity entity, std::stringstream &stream) {
    std::string componentName;
    while (stream >> componentName) {
        Assembler assembler = getComponentAssembler(componentName);
        std::string temp;
        std::string contents;

        std::getline(stream, temp, '{');
        std::getline(stream, contents, '}');
        assembleComponent(entity, contents, assembler);
    }
}

Entity EntityAssembler::assembleEntity(const std::string &path) {
    std::string contents = readFileToString(path);
    std::stringstream stream(contents);
    if (stream.fail()) {
        throw IOFailure("Failed to open file at: " + path);
    }

    std::string name = getName(stream);

    Entity entity = manager.createEntity();
    assembleComponents(entity, stream);

    entitiesByName.insert({name, entity});
    entitiesByPath.insert({path, entity});

    return manager.createEntity();
}