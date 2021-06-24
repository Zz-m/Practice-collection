package cn.denghanxi.xgboost;

import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoostError;

/**
 * Created by dhx on 2021/6/24.
 */
public class Main {
    public static void main(String[] args) throws XGBoostError {
        System.out.println("start");
        DMatrix dmat = new DMatrix("train.svm.txt");
    }
}
