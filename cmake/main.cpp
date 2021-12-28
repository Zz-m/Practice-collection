#include <iostream>

#include "filter.h"

int a = 123;

int main() {

    double data[] = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};

    Filter_IIR<double> filterIir = Filter_IIR<double>();

    double tmp = 123.3;

    for (double i: data) {
        filterIir.filterData(i, &tmp);
        printf("%f\n", tmp);
    }


    return 0;
}
