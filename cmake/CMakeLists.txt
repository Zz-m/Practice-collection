cmake_minimum_required(VERSION 3.20)
project(cmake_test_app VERSION 1.2.3)

set(CMAKE_CXX_STANDARD 14)

add_executable(cmake_testApp main.cpp)
add_executable(cmake_testApp_calc main.cpp calc.cpp)

add_library(test_library STATIC calc.cpp)

include_directories(includes/general)
include_directories(includes/math)

#find_library(TEST_LIBRARY test_library lib)
find_library(DHX_LIBRARY dhx_lib lib)

target_link_libraries(cmake_testApp LINK_PUBLIC ${DHX_LIBRARY})
#target_include_directories()