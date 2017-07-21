#include <fstream>

#include "IOUtils.hpp"
#include <vector>
#include <glob.h>
#include <iostream>
#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>

std::string transitoreality::readFileToString(const std::string &path) {
    std::string output;
    std::ifstream stream(path);
    
    if (stream.fail()) {
        throw(std::string("Failed to open file."));
    }
    
    std::string line;
    
    while (std::getline(stream, line)) {
        output += '\n';
        output += line;
    }
    
    return output;
}
