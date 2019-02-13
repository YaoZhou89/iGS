package com.Polygenic.main;

import com.Polygenic.operate.FileCV;
import com.Polygenic.operate.FileTraits;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Polygenic_model {

    public static Map<String, Object> runiGS(FileCV myCV, FileTraits myY, double[] Var, String alg, int[] index, ArrayList<double[][]> K) {

        String[][] result = new String[myY.getPhe1().length + 1][index.length + 1];
        double[][] forecas = new double[myY.getPhe1().length ][index.length ];
        result[0][0] = "Taxa";
        for (int i = 0; i < index.length; i++) {
            result[0][i + 1] = myY.getColName()[index[i]+1];
        }
        for (int j = 0; j < myY.getPheName().length; j++) {
            result[j + 1][0] = myY.getPheName()[j];
        }


        for (int i = 0; i < index.length; i++) {
	
            Map<String, Object> iGS_eps = IGS_eps.iGS_eps(myCV.getDoublePhe1("CV"), K, myY.getFullIndexData(index[i]), Var, "fs");
            double[] BLUP = (double[]) iGS_eps.get("BLUP");
            double[] phe = (double[]) iGS_eps.get("phe");

            for (int j = 0; j < BLUP.length; j++) {
                result[j + 1][i + 1] = "" + BLUP[j];
            }
            for (int j = 0; j < phe.length; j++) {
                forecas[j][i] = phe[j];
            }
        }
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("BLUP", result);
        res.put("phe", forecas);
        return res;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static double[] getArrayByIndex(double[][] num, int index) {

        double[] douArr = new double[num.length];
        for (int i = 0; i < num.length; i++) {
            douArr[i] = num[i][index];
        }

        return douArr;
    }

    private static byte[][] getArrayByIndex(byte[][] num, int index[]) {

        byte[][] bytArr = new byte[num.length][index.length];
        for (int j = 0; j < index.length; j++) {
            for (int i = 0; i < num.length; i++) {
                bytArr[i][j] = Byte.valueOf(num[i][index[j]]);
            }
        }
        return bytArr;
    }

    /**
     * toDouble
     *
     * @param CV
     * @return
     */
    private static double[][] toDouble(String[][] CV) {
        if (CV == null) {
            return null;
        }

        double[][] doubleCV = new double[CV.length][CV[0].length];
        for (int i = 0; i < CV.length; i++) {
            for (int j = 0; j < CV[0].length; j++) {
                doubleCV[i][j] = Double.valueOf(CV[i][j]);
            }
        }
        return doubleCV;
    }

    /**
     * subDoubleArray
     *
     * @param beta
     * @param start
     * @param end
     * @return
     */
    private static double[] subDoubleArray(double[] beta, int start, int end) {

        double[] arraycopy = new double[end - start + 1];
        System.arraycopy(beta, start, arraycopy, 0, end - start + 1);
        return arraycopy;
    }

    /**
     * @param GAWS
     * @param GM
     * @return
     */
    private static int[] match(String[][] GAWS, String[] GM) {

        List<Integer> index1 = new ArrayList<Integer>();
        for (int i = 0; i < GAWS.length; i++) {
            for (int j = 0; j < GM.length; j++) {
                if (GAWS[i][0].equals(GM[j])) {
                    index1.add(j);
                    continue;
                }
            }
        }

        int[] index = new int[index1.size()];
        for (int i = 0; i < index1.size(); i++) {
            index[i] = index1.get(i);
        }
        return index;
    }

    /**
     * @param GD
     * @return
     */
    private static double min(double[][] GD) {
        double max = -1;
        for (int i = 0; i < GD.length; i++) {
            max = GD[i][0] < GD[i][1] ? GD[i][0] : GD[i][1];
            max = max < GD[i][2] ? max : GD[i][2];
            if (max == 0) {
                break;
            }
        }

        return max;
    }

//    private void ttt(FileCV aa, int a, FileCV... args) {
//
//    }
//
//    private void rrr() {
//        ttt(new FileCV(), 32, new FileCV(), new FileCV(), new FileCV(), new FileCV());
//        String[][] aaaa = Polygenic_model.runiGS(new FileCV("", false), new FileTraits(""), new double[10], "fa", new int[10], new FileCV("", false), new FileCV("", false));
//    }
}
