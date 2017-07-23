#ifndef IOUtils_hpp
#define IOUtils_hpp

#include <string>

namespace transitoreality {
    std::string readFileToString(const std::string &path);

    struct IOFailure : std::runtime_error {
        IOFailure(const std::string &text);
    };
}

#endif /* IOUtils_hpp */
