package cn.denghanxi.xgboost;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

/**
 * Created by dhx on 2021/6/24.
 */
public class Main {
    public static void main(String[] args) throws XGBoostError {
//        System.out.println("start");
//        URL url = Main.class.getClassLoader().getResource("train.svm.txt");
//        System.out.println("path:" + url.getPath());
//        DMatrix trainMat = new DMatrix(DATA, 3, 4);
//        DMatrix validMat = new DMatrix(DATA, 3, 4);
//        DMatrix testMat = new DMatrix(DATA, 3, 4);

        long[] rowHeaders = new long[] {0,2,4,7};
        float[] data = new float[] {1f,2f,4f,3f,3f,1f,2f};
        int[] colIndex = new int[] {0,2,0,3,0,1,2};
        int numColumn = 4;
        DMatrix trainMat = new DMatrix(rowHeaders, colIndex, data, DMatrix.SparseType.CSR, numColumn);
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
                put("test", trainMat);
            }
        };
        int nround = 2;
        Booster booster = XGBoost.train(trainMat, params, nround, watches, null, null);
        booster.saveModel("target/model.bin");
    }

    private static float[] DATA = {1, 0, 2, 0, 4, 0, 0, 3, 3, 1, 2, 0};
}
