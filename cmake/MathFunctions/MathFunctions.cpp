//
// Created by dell on 2021/11/12.
//

#include "MathFunctions.h"


#ifdef USE_MYMATH

#  include "mysqrt.h"

#endif

namespace mathfunctions {
    double sqrt(double x) {
#ifdef USE_MYMATH
        return detail::mysqrt(x);
#else
        return std::sqrt(x);
#endif
    }
}