package com.Polygenic.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.Polygenic.operate.FileTraits;
import zb.archer.Demo.R.RmathImp;

public class IGS_eps {

    private static RmathImp ri = new RmathImp();

    public static Map<String, Object> iGS_eps(double[][] X, List<double[][]> K, double[] Y, double[] Var, String alg) {
        //
        if (alg.isEmpty()) {
            alg = "ai";
        }
        double[] y = Y;
        //int[] index_y = FileTraits.getIndexArrayWithoutZero(Y);
        int[] index_y = FileTraits.getIndexArrayWithoutNA(Y);
        int n_random = K.size();
        List<double[][]> Knew = new LinkedList<double[][]>();

        if (n_random == 0) {
//            System.err.println("This no random effect! Kinship Matrix is needed.");
        }
        double[][] A = new double[index_y.length][index_y.length];
        // ��������0.0����NA ��������������
        for (int i = 0; i < n_random; i++) {
            double[][] temp = K.get(i);
            Knew.add(temp);
        }
        double[][] Xnew;
        if (X != null) {
            Xnew = new double[X.length][X.length];
            for (int i = 0; i < X.length; i++) {
                Xnew[i] = X[i];
            }
        } else {
            Xnew = model_matrix(y.length);
        }

        double[] ynew = ri.getArrayByIndex(y, index_y);
        Map<String, Object> mm = CalcAIvar.calcAIvar(ynew, Knew, Xnew, Var, alg);
        Map<String, Object> gblup = GBLUP_eps.gBLUP_eps(X, K, mm, y);
        double[] BLUP = (double[]) gblup.get("BLUP");
        double[] phe = (double[]) gblup.get("phe");

        Var = (double[]) mm.get("Var");
        double LL = (double) mm.get("llik");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("BLUP", BLUP);
        result.put("phe", phe);
        result.put("Var", Var);
        result.put("LL", LL);

        return result;
//		  return(list(BLUP = BLUP, phe = phe, Var = Var, LL = LL))		
    }

    private static double[][] model_matrix(int length) {
        double[][] result = new double[length][1];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = 1;
        }
        return result;
    }
}
