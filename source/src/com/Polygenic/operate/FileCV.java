package com.Polygenic.operate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Em_lasso ---- Traits表型对象
 *
 * phe1 表型矩阵String
 *
 * @author Belong.
 *
 */
public class FileCV {

    private String[][] phe1;

    public double[][] getDoubleArray() {
        if (phe1 == null) {
            return null;
        }

        double[][] temp = new double[phe1.length][phe1[0].length];
        for (int i = 0; i < phe1.length; i++) {
            for (int j = 0; j < phe1[0].length; j++) {
                temp[i][j] = Double.valueOf(phe1[i][j]);
            }
        }

        return temp;
    }

    public String[][] getPhe1() {
        return phe1;
    }

    public double[][] getDoublePhe1(String type) {
        if (phe1 == null) {
            return null;
        }

        double[][] result = new double[phe1.length][phe1[0].length];
        for (int i = 0; i < phe1.length; i++) {
            if (type.equals("K")) {
                for (int j = 0; j <= i; j++) {
                    result[i][j] = Double.valueOf(phe1[i][j]);
                    result[j][i] = Double.valueOf(phe1[i][j]);
                }
            } else if (type.equals("CV")) {
                for (int j = 0; j < phe1[0].length; j++) {
                    result[i][j] = Double.valueOf(phe1[i][j]);
                }
            }
        }
        return result;
    }

    /**
     * 取第index列表型数据, NA或 NaN跳过不取 。
     *
     * @param index 获取第index列， index ∈( 1：n )
     * @return data2 表行中指定列表型数据
     */
    public double[] getIndexData(int index) {

        ArrayList<Double> data1 = new ArrayList<Double>();
        // 取表型判断是否 NA或 NaN
        for (String[] phe : this.phe1) {
            if (!phe[index - 1].equals("0.0")) {
                data1.add(Double.valueOf(phe[index - 1]));
            }
        }
        Double[] data = (Double[]) data1.toArray(new Double[data1.size()]);
        double[] data2 = new double[data.length];
        for (int i = 0; i < data2.length; i++) {
            data2[i] = data[i].doubleValue();
        }

        return data2;
    }

    public FileCV() {
        phe1 = null;
    }

    /**
     * 读表型值文件
     *
     * @param file 文件地址
     * @param tip 是否有文件列标题
     */
    public FileCV(String file, boolean tip) {

        ArrayList<String[]> liststr = new ArrayList<String[]>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            // 列名
            String line;
            if (tip) {
                line = br.readLine();
            }
            while ((line = br.readLine()) != null) {
                // 隔符在这里改！！！！
                String[] str = line.split("\t");
                liststr.add(str);
            }
            phe1 = new String[liststr.size()][liststr.get(liststr.size() - 1).length];

            for (int i = 0; i < liststr.size(); i++) {
                for (int j = 0; j < liststr.get(i).length; j++) {
                    phe1[i][j] = liststr.get(i)[j];
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
	 * public static void main(String[] args) throws
	 * UnsupportedEncodingException{ String file =
	 * "d:/2015student/javalassoA/file/mdp_traits.txt";
	 * 
	 * FileTraits fb = new FileTraits(file); String[] colName = fb.getColName();
	 * String[] pheName = fb.getPheName(); String[][] phe = fb.getPhe1();
	 * 
	 * 
	 * for(String str:colName) System.out.print(str+"\t"); System.out.println();
	 * 
	 * for(String str:pheName) System.out.print(str+"\t"); System.out.println();
	 * 
	 * 
	 * 
	 * for(String[] phe1:phe){ for(String str:phe1) System.out.print(str+"\t");
	 * System.out.println(); } }
     */
}
