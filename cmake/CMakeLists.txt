cmake_minimum_required(VERSION 3.20)
project(Tutorial VERSION 1.2.3)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED True)
SET(CMAKE_INSTALL_PREFIX "E:/tmp/cmake")

option(USE_MYMATH "Use tutorial provided math implementation" ON)

if (USE_MYMATH)
    message(WARNING "use my math lib")
    add_subdirectory(MathFunctions)
    list(APPEND EXTRA_LIBS MathFunctions)
else ()
    message(WARNING "use env lib")
endif ()

add_executable(app2 main2.cpp)

target_link_libraries(app2 PUBLIC ${EXTRA_LIBS})

configure_file(TutorialConfig2.h.in TutorialConfig.h)

target_include_directories(app2 PUBLIC
        "${PROJECT_BINARY_DIR}"
        )

install(TARGETS app2 DESTINATION bin)
install(FILES "${PROJECT_BINARY_DIR}/TutorialConfig.h"
        DESTINATION include
        )


enable_testing()
add_test(NAME Runs COMMAND app2 25)
add_test(NAME Usage COMMAND app2)
set_tests_properties(Usage
        PROPERTIES PASS_REGULAR_EXPRESSION "Usage:.*number"
        )
# define a function to simplify adding tests
function(do_test target arg result)
    add_test(NAME Comp${arg} COMMAND ${target} ${arg})
    set_tests_properties(Comp${arg}
            PROPERTIES PASS_REGULAR_EXPRESSION ${result}
            )
endfunction()

# do a bunch of result based tests
do_test(app2 4 "4 is 2")
do_test(app2 9 "9 is 3")
do_test(app2 5 "5 is 2.236")
do_test(app2 7 "7 is 2.645")
do_test(app2 25 "25 is 5")
do_test(app2 -25 "-25 is (-nan|nan|0)")
do_test(app2 0.0001 "0.0001 is 0.01")

