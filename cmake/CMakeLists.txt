cmake_minimum_required(VERSION 3.20)
project(Tutorial VERSION 1.2.3)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED True)

set(CMAKE_INSTALL_PREFIX "E:/tmp/cmake")

# control where the static and shared libraries are built so that on windows
# we don't need to tinker with the path to run the executable
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY "${PROJECT_BINARY_DIR}")
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "${PROJECT_BINARY_DIR}")
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${PROJECT_BINARY_DIR}")

option(BUILD_SHARED_LIBS "Build using shared libraries" ON)

configure_file(TutorialConfig2.h.in TutorialConfig.h)

add_subdirectory(MathFunctions)
add_executable(app2 main2.cpp)

target_link_libraries(app2 PUBLIC MathFunctions)

target_include_directories(app2 PUBLIC
        "${PROJECT_BINARY_DIR}"
        )
