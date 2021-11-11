cmake_minimum_required(VERSION 3.20)
project(Tutorial VERSION 1.2.3)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED True)

option(USE_MYMATH "Use tutorial provided math implementation" ON)

if (USE_MYMATH)
    message(WARNING "use my math lib")
    add_subdirectory(MathFunctions)
    list(APPEND EXTRA_LIBS MathFunctions)
    list(APPEND EXTRA_INCLUDES "${PROJECT_SOURCE_DIR}/MathFunctions")
else ()
    message(WARNING "use env lib")
endif ()

add_executable(app2 main2.cpp)

target_link_libraries(app2 PUBLIC ${EXTRA_LIBS})

configure_file(TutorialConfig2.h.in TutorialConfig.h)

target_include_directories(app2 PUBLIC
        "${PROJECT_BINARY_DIR}"
        ${EXTRA_INCLUDES}
        )




