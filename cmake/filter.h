//
// Created by dell on 2021/12/28.
//

#ifndef CMAKE_TEST_APP_FILTER_H
#define CMAKE_TEST_APP_FILTER_H
#include "Queue.h"

//fdatool coff


//const int NL = 7;
//const double NUM[7] = {
//  8.647182682702e-05,                 0,-0.0002594154804811,                 0,
//  0.0002594154804811,                 0,-8.647182682702e-05
//};
//const int DL = 7;
//const double DEN[7] = {
//                   1,   -5.816988323661,    14.10212014572,    -18.2377974322,
//      13.27055943226,   -5.151265517789,   0.8333716956989
//};
//const int NL = 7;
//const double NUM[7] = {
//    0.1067522002352,                 0,  -0.3202566007055,                 0,
//        0.3202566007055,                 0,  -0.1067522002352
//};
//const int DL = 7;
//const double DEN[7] = {
//    1,   -3.513875456283,    5.250962848301,   -4.849806049927,
//         3.240523073604,   -1.364958159455,   0.2371805207409
//};

//5.17改为475采样率的0.5-30hz的IIR 切比雪夫一型 6阶  IIR滤波器
const int NL = 7;
const double NUM[7] = {
        0.003039735391596,                 0,-0.009119206174788,                 0,
        0.009119206174788,                 0,-0.003039735391596
};
const int DL = 7;
const double DEN[7] = {
        1,   -5.506206913152,     12.7320178769,   -15.83915712888,
        11.18786801032,   -4.255299318627,   0.6807774884832
};


template<typename T>
class Filter_IIR
{
public:
    Filter_IIR();
    ~Filter_IIR();
    char filterData(T data,T* res);
private:
    unsigned int orders;
    Queue<T>* num;
    Queue<T>* den;

};

template<typename T>
Filter_IIR<T>::Filter_IIR(){
    orders = DL-1;  // 6阶！！！！！！
    num = new Queue<T>(DL);
    den = new Queue<T>(DL);
    for(int i = 0;i< orders; i ++){
        num->push(0);
        den->push(0);
    }

}

template<typename T>
Filter_IIR<T>::~Filter_IIR(){
    delete num;
    delete den;
}

template<typename T>
char Filter_IIR<T>::filterData(T data,T *res){
    unsigned int n = orders;
    T temp,temp_res;
    temp_res = data*NUM[0];
    while(n--){         //n-1 ~0
        num->at(n,temp);   // 新 -> 老
        temp_res += temp*NUM[DL-1-n];  // 此处已经减过1
        den->at(n,temp);
        temp_res -= temp*DEN[DL-1-n];
    }
    num->pop(temp);  //必须先pop
    den->pop(temp);
    num->push(data);
    den->push(temp_res);

    *res = temp_res;
    return 1;
}


#endif //CMAKE_TEST_APP_FILTER_H
