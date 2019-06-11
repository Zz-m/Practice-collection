#include "dbg.h"
#include <stdlib.h>
#include <stdio.h>

void test_debug() {
    debug("I have brown whatever.");

    debug("I am %d years old.", 32);
}

int test_check_mem() {
    char *test = NULL;
    check_mem(test);

    free(test);
    return 1;

error:
    return -1;
}

int main(int argc, char *argv[]){
    check(argc == 2, "Need an argument.");

    test_debug();
    check(test_check_mem() == -1, "test_check_mem failed.");

    return 0;

error:
    return 1;
}
