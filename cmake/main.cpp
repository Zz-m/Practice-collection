#include <iostream>
#include "content/text-provider.h"
#include "content/speaker.h"

int main() {
    std::cout << getValue() << std::endl;

    Speaker speaker2{};

    speaker2.speak();
    speaker2.sit();
    return 0;
}
