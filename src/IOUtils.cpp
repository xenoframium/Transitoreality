#include <fstream>

#include "IOUtils.hpp"

using namespace transitoreality;

std::string transitoreality::readFileToString(const std::string &path) {
    std::string output;
    std::string line;
    std::ifstream stream(path);
    if (stream.fail()) {
        throw IOFailure("Failed to open file.");
    }

    while (std::getline(stream, line)) {
        output += line;
        output += '\n';
    }
    
    return output;
}

IOFailure::IOFailure(const std::string &text) : std::runtime_error(text) {}