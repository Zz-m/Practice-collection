//
// Created by dell on 2021/11/10.
//
#include <cmath>
#include <iostream>
#include <string>
#include "TutorialConfig.h"

#ifdef USE_MYMATH
#include "MathFunctions.h"
#endif

int main(int argc, char *argv[]) {
    if (argc < 2) {
        std::cout << "begin" << std::endl;
        std::cout << argv[0] << " Version " << Tutorial_VERSION_MAJOR << "."
                  << Tutorial_VERSION_MINOR << std::endl;
        std::cout << "Usage: " << argv[0] << " number" << std::endl;
        std::cout << "end" << std::endl;
        std::cout << "patch : " << Tutorial_VERSION_PATCH << std::endl;
        return 1;
    }

    // convert input to double
//    const double inputValue = atof(argv[1]);
    const double inputValue = std::stod(argv[1]);

    // calculate square root
#ifdef USE_MYMATH
    const double outputValue = mysqrt(inputValue);
    std::cout << "use my lib." << std::endl;
#else
    const double outputValue = sqrt(inputValue);
    std::cout << "use env lib." << std::endl;
#endif

    std::cout << "The square root of " << inputValue << " is " << outputValue
              << std::endl;
    return 0;
}