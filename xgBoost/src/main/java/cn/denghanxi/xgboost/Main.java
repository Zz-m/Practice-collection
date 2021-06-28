package cn.denghanxi.xgboost;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhx on 2021/6/24.
 */
public class Main {
    public static void main(String[] args) throws XGBoostError {

        DMatrix trainMat = new DMatrix("e:\\tmp\\agaricus.txt.train");
        DMatrix testMat = new DMatrix("e:\\tmp\\agaricus.txt.test");
        Map<String, Object> params = new HashMap<>() {
            {
                put("eta", 1.0);
                put("max_depth", 2);
                put("objective", "binary:logistic");
                put("eval_metric", "logloss");
            }
        };

        Map<String, DMatrix> watches = new HashMap<>() {
            {
                put("train", trainMat);
                put("test", testMat);
            }
        };
        int nround = 2;
        Booster booster = XGBoost.train(trainMat, params, nround, watches, null, null);
        booster.saveModel("e:\\tmp\\model.bin");
    }

    private static float[] DATA = {1, 0, 2, 0, 4, 0, 0, 3, 3, 1, 2, 0};
}
