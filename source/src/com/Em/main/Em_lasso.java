package com.Em.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Jama.Matrix;
import java.util.HashMap;
import java.util.Random;
import zb.archer.Demo.R.CrossImp;
import zb.archer.Demo.R.Diag;
import zb.archer.Demo.R.RmathImp;
import zb.archer.Demo.domain.Bayesian;
import zb.archer.Demo.domain.Em_BlassoEigen;
import zb.archer.Demo.operate.Compare;

public class Em_lasso {

    private static CrossImp ci = new CrossImp();
    private static RmathImp ri = new RmathImp();

    public static Map<String, Object> Lasso_EM(double[][] x, byte[][] z, double[] y) {

        Map<String, Object> map = new TreeMap<String, Object>();
//		n<-nrow(z);k<-ncol(z)
        int n = z.length;
        int k = z[0].length;
//		b<-solve(crossprod(x,x))%*%(crossprod(x,y))
        double[] b = ci.ncrossprod((new Matrix(ci.crossprod(x, x)).inverse().getArray()), ci.crossprod(x, y));
//		temp = (y-x%*%b)
        double[] temp = ri.minus(y, ci.ncrossprod(x, b));
//		v0<-as.numeric(crossprod((y-x%*%b),(y-x%*%b))/n)
        double v0 = ci.crossprod(temp, temp) / n;
//		u<-matrix(rep(0,k),k,1)	// k长度0矩阵
        double[] u = new double[k];
//		v<-matrix(rep(0,k),k,1)
        double[] v = new double[k];
//		s<-matrix(rep(0,k),k,1)
        double[] s = new double[k];
        byte[] zz = new byte[z.length];
        /*for(i in 1:k){
	      zz<-z[,i]
	      s[i]<-((crossprod(zz,zz)+1e-100)^(-1))*v0
	      u[i]<-s[i]*crossprod(zz,(y-x%*%b))/v0
	      v[i]<-u[i]^2+s[i]
	    }*/
        for (int i = 0; i < k; i++) {
            zz = ri.getArrayByCol(z, i);
            s[i] = Math.pow(ci.crossprod(zz, zz) + (1e-100), (-1)) * v0;
            u[i] = s[i] * ci.crossprod(zz, temp) / v0;
            v[i] = Math.pow(u[i], 2) + s[i];
        }
//		vv<-matrix(rep(0,n*n),n,n);
        double[][] vv = new double[n][n];
        /*for(i in 1:k){
	      zz<-z[,i]
	      vv=vv+tcrossprod(zz,zz)*v[i]
	    }*/
        for (int i = 0; i < k; i++) {
            zz = ri.getArrayByCol(z, i);
            vv = ri.plus(vv, ri.times(ci.tcrossprod(zz, zz), v[i]));
        }
//		vv<-vv+diag(n)*v0
        vv = ri.plus(vv, Diag.diag(n, v0));

        int iter = 0;
        double err = 1000;
        double iter_max = 5000;
        double err_max = 1e-10;
        double tau = 0;
        double omega = 0;
        double[] beta = null;
        double sigma2 = 0;

        while ((iter < iter_max) && (err > err_max)) {
            iter++;
            double v01 = v0;

            double[] v1 = new double[v.length];
            for (int i = 0; i < v.length; i++) {
                v1[i] = v[i];
            }
            double[] b1 = new double[b.length];
            for (int i = 0; i < b.length; i++) {
                b1[i] = b[i];
            }
            double[][] vi = new Matrix(vv).inverse().getArray();
            double[][] xtv = ci.crossprod(x, vi);
//	    	if(x[0].length==1)		      {
//	    		double[] tttt = ci.ncrossprod(xtv, y);
//	    		((xtv%*%x)^(-1))
//	    		double[] temp1 = ri.pow(ci.ncrossprod(xtv, x),-1);
//		        bline = ri.times(temp1, ci.ncrossprod(xtv, y));
//		    }else{
            b = ci.ncrossprod(new Matrix(ci.ncrossprod(xtv, x)).inverse().getArray(), ci.ncrossprod(xtv, y));
//	    	}

            double[] r = ri.minus(y, ci.ncrossprod(x, b));
            double[] ss = new double[n];
//	    	for(i in 1:k)
//	        {
//	          zz<-z[,i]
//	          zztvi<-crossprod(zz,vi)
//	          u[i]<-v[i]*zztvi%*%r
//	          s[i]<-v[i]*(1-zztvi%*%zz*v[i])
//	          v[i]<-(u[i]^2+s[i]+omega)/(tau+3)
//	          ss<-ss+zz*u[i]
//	        }
            for (int i = 0; i < k; i++) {
                zz = ri.getArrayByCol(z, i);
                double[] zztvi = ci.crossprod(zz, vi);
                u[i] = v[i] * ci.crossprod(zztvi, r);
                s[i] = v[i] * (1 - ci.ncrossprod(zztvi, zz) * v[i]);
                v[i] = (Math.pow(u[i], 2) + s[i] + omega) / (tau + 3);
                ss = ri.plus(ss, ri.times(zz, u[i]));
            }
            v0 = ci.crossprod(r, ri.minus(r, ss)) / n;
            vv = new double[n][n];

            for (int i = 0; i < k; i++) {
                zz = ri.getArrayByCol(z, i);
                vv = ri.plus(vv, ri.times(ci.tcrossprod(zz, zz), v[i]));
            }

            vv = ri.plus(vv, ri.times(Diag.diag(n), v0));
            err = (ci.crossprod(ri.minus(b1, b), ri.minus(b1, b)) + Math.pow((v01 - v0), 2) + ci.crossprod(ri.minus(v1, v), ri.minus(v1, v))) / (2 + k); // return 中无err
//	        beta<-t(b)
            beta = b.clone();
            sigma2 = v0;
        }
        map.put("u", u);
        map.put("sigma2", sigma2);
        map.put("beta", beta);
        return map;
    }

    public static double[] multivanormal(double[] y, double[] mean, double sigma) {

        double[] temp = ri.minus(y, mean);
        double[] pdf_value = ri.times(arrayExp(ri.times(ri.times(ri.times(temp, temp), -1), 1 / (2 * sigma))), (1 / Math.sqrt(2 * 3.14159265358979323846 * sigma)));
        return pdf_value;

//		pdf_value = (1/sqrt(2*3.14159265358979323846*sigma))*exp(-(y-mean)*(y-mean)/(2*sigma));
//		return (pdf_value)
    }

    /*
	  multivanormal<-function(y,mean,sigma)
	  {
	    pdf_value<-(1/sqrt(2*3.14159265358979323846*sigma))*exp(-(y-mean)*(y-mean)/(2*sigma));
	    return (pdf_value)
	  }
     */
    public static double[] likelihood(double[][] xxn, int[][] xxx, double[] yn, double[] bbo) {

        int nq = xxx[0].length;
        int ns = yn.length;
        int at1 = 0;
        int[] ww1 = AbsGreaterThen(bbo, 1e-5);
        at1 = ww1.length;
        double[] lod = new double[nq];
        double[][] ad = null;
        if (at1 > 0.5) {
            ad = new double[xxn.length][xxn[0].length + ww1.length];
            for (int i = 0; i < xxn.length; i++) {
                System.arraycopy(xxn[i], 0, ad[i], 0, xxn[i].length);
                double[] arrayBy = getArrayByIndexToDou(xxx, ww1)[i];
                System.arraycopy(arrayBy, 0, ad[i], xxn[i].length, arrayBy.length);
            }
        } else {
            ad = xxn;
        }
        double[] bb = null;
        if (AbsBaderThen(Em_BlassoEigen.operateD(new Matrix(ci.crossprod(ad, ad)).eig().getD().getArray()), 1e-6).length > 0) {
            bb = ci.ncrossprod(new Matrix(ci.crossprod(ad, ad)).plus(new Matrix(Diag.diag(ad[0].length)).times(0.01)).inverse().getArray(), ci.crossprod(ad, yn));
        } else {
            double[][] temp = ci.crossprod(ad, ad);
            bb = ci.ncrossprod(new Matrix(temp).inverse().getArray(), ci.crossprod(ad, yn));
        }
        double vv1 = ci.crossprod(ri.minus(yn, ci.ncrossprod(ad, bb)), ri.minus(yn, ci.ncrossprod(ad, bb))) / ns;
        double ll1 = ri.sum(ri.log(ri.abs(multivanormal(yn, ci.ncrossprod(ad, bb), vv1))));
        int[] sub = new int[ad[0].length];
        for (int i = 0; i < sub.length; i++) {
            sub[i] = i;
        }
        if (at1 > 0.5) {
            for (int i = 0; i < at1; i++) {
                int[] ij = new int[sub.length - 1];
                int count = 0;
                if (i + xxn[0].length < sub.length) {
                    for (int count1 : sub) {
                        if (count1 != i + xxn[0].length) {
                            ij[count++] = count1;
                        }
                    }
                }
                double[][] ad1 = Em_lasso.getArrayByIndex(ad, ij);
                double[] bb1;
                if (AbsBaderThen(Em_BlassoEigen.operateD(new Matrix(ci.crossprod(ad1, ad1)).eig().getD().getArray()), 1e-6).length > 0) {
                    bb1 = ci.ncrossprod(new Matrix(ci.crossprod(ad1, ad1)).plus(new Matrix(Diag.diag(ad1[0].length)).times(0.01)).inverse().getArray(), ci.crossprod(ad1, yn));
                } else {
                    bb1 = ci.ncrossprod(new Matrix(ci.crossprod(ad1, ad1)).inverse().getArray(), ci.crossprod(ad1, yn));
                }
                double vv0 = ci.crossprod(ri.minus(yn, ci.ncrossprod(ad1, bb1)), ri.minus(yn, ci.ncrossprod(ad1, bb1))) / ns;
                double ll0 = ri.sum(ri.log(ri.abs(multivanormal(yn, ci.ncrossprod(ad1, bb1), vv0))));
                lod[ww1[i]] = -2.0 * (ll0 - ll1) / (2.0 * Math.log(10));
            }
        }

        return lod;
    }

    public static Map<String, Object> En_lasso(double[][] CV, byte[][] GD, double[] y, int[][] GM, String[] GMName) {

        double[][] z = null;
        int[] sig1 = null;
        double[] bbo = null;
        double[] her = null;

        if (CV != null) {
            z = new double[GD.length][CV[0].length + 1];
            for (int i = 0; i < GD.length; i++) {
                z[i][0] = 1;
                System.arraycopy(CV[i], 0, z[i], 1, CV[0].length);
            }
        } else {
            z = new double[GD.length][1];
            for (int i = 0; i < GD.length; i++) {
                z[i][0] = 1;
            }
        }
        Map<String, Object> u1 = Lasso_EM(z, GD, y);
        double[] result1 = (double[]) u1.get("u");
        //result1 后面未再使用， Res1 作 result1 指针, 引用 result1 地址
        double[] Res1 = result1;
        double[] lod = null;
//		  int le2 = countAbs(Res1, 1e-5);
        int le2 = AbsGreaterThen(Res1, 1e-5).length;

        if (le2 == 0) {
//            System.out.println("There is no QTN significant");
//            return null;
        } else {
            sig1 = AbsGreaterThen(Res1, 1e-5);
            bbo = Em_lasso.getArrayByIndex(Res1, sig1);
            her = new double[le2];
            int AA = max(GD);
            for (int i = 0; i < le2; i++) {
                double p1 = countP(GD, sig1[i], AA);
                double p2 = 1 - p1;
                her[i] = (((p1 + p2) - Math.pow((p1 - p2), 2)) * Math.pow(Res1[sig1[i]], 2)) / var(y) * 100;
            }
            int[][] xxxx = Em_lasso.getArrayByIndex(GD, sig1);
            double[] yn = y;
            double[][] xxn = z;
            lod = likelihood(xxn, xxxx, yn, bbo);

        }
        double[] beta = (double[]) u1.get("beta");

        int[][] GMsig1 ;
        String[] GMName1 ;
        double[] Res1sig1 ;
        if ( sig1 != null ) {
            GMsig1 = getArrayRoWByIndex(GM, sig1);
            GMName1 = getArrayByIndex(GMName, sig1);
            Res1sig1 = getArrayByIndex(Res1, sig1);
        } else {
            int[] index = {new Random().nextInt(GM.length)};
            GMsig1 = getArrayRoWByIndex(GM, index);
            GMName1 = getArrayByIndex(GMName, index);
            Res1sig1 = getArrayByIndex(Res1, index);
        }
        
        String[][] GWAS = new String[GMsig1.length][GM[0].length + 4];

        for (int j = 0; j < GMsig1.length; j++) {

            GWAS[j][0] = GMName1[j];
            for (int i = 0; i < GMsig1[0].length; i++) {
                GWAS[j][i + 1] = String.valueOf(GMsig1[j][i]);
            }
            GWAS[j][GMsig1[0].length + 1] = String.valueOf(Res1sig1[j]);
            GWAS[j][GMsig1[0].length + 2] = String.valueOf(lod[j]);
            GWAS[j][GMsig1[0].length + 3] = String.valueOf(her[j]);

        }
        Map<String, Object> GWASlist = new TreeMap<String, Object>();

        GWASlist.put("GWAS", GWAS);
        GWASlist.put("beta", beta);

        return GWASlist;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static double[] getArrayByIndex(double[] num, int[] index) {

        double[] douArr = new double[index.length];
        for (int i = 0; i < index.length; i++) {
            douArr[i] = num[index[i]];
        }

        return douArr;
    }
    
    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static int[][] getArrayByIndex(byte[][] num, int[] index) {

        int[][] douArr = new int[num.length][index.length];
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < index.length; j++) {
                douArr[i][j] = num[i][index[j]];
            }
        }
        return douArr;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static int[][] getArrayRoWByIndex(int[][] num, int[] index) {

        int[][] douArr = new int[index.length][num[0].length];
        for (int i = 0; i < index.length; i++) {
            System.arraycopy(num[index[i]], 0, douArr[i], 0, num[0].length);
        };

        return douArr;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static double[][] getArrayRoWByIndex(double[][] num, int[] index) {

        double[][] douArr = new double[index.length][num[0].length];
        for (int i = 0; i < index.length; i++) {
            System.arraycopy(num[index[i]], 0, douArr[i], 0, num[0].length);
        }

        return douArr;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static byte[][] getArrayRowByIndex(byte[][] num, int[] index) {

        byte[][] douArr = new byte[index.length][num[0].length];
        for (int i = 0; i < index.length; i++) {
            System.arraycopy(num[index[i]], 0, douArr[i], 0, num[0].length);
        }

        return douArr;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static String[] getArrayByIndex(String[] num, int[] index) {

        String[] douArr = new String[index.length];
        for (int i = 0; i < index.length; i++) {
            douArr[i] = num[index[i]];
        }

        return douArr;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static double[][] getArrayByIndexToDou(int[][] num, int[] index) {

        double[][] douArr = new double[num.length][index.length];
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < index.length; j++) {
                douArr[i][j] = num[i][index[j]];
            }
        }

        return douArr;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static double[][] getArrayByIndex(double[][] num, int[] index) {

        double[][] douArr = new double[num.length][index.length];
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < index.length; j++) {
                douArr[i][j] = num[i][index[j]];
            }
        }

        return douArr;
    }

    /**
     * 获取Res1[]中大于num1的数的序数
     *
     * @param Res1
     * @param num1
     * @return
     */
    private static int[] AbsGreaterThen(double[] Res1, double num1) {

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < Res1.length; i++) {
            if (Math.abs(Res1[i]) > num1) {
                list.add(i);
            }
        }
        int count = 0;
        int[] intArray = new int[list.size()];
        for (int temp1 : list) {
            intArray[count++] = temp1;
        }

        return intArray;
    }

    /**
     * 获取Res1[]中小于num1的数的序数
     *
     * @param Res1
     * @param num1
     * @return
     */
    private static int[] AbsBaderThen(double[] Res1, double num1) {

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < Res1.length; i++) {
            if (Res1[i] < num1) {
                list.add(i);
            }
        }
        int count = 0;
        int[] intArray = new int[list.size()];
        for (int temp1 : list) {
            intArray[count++] = temp1;
        }

        return intArray;
    }

    /**
     * 统计Res1[]中大于num1的个数
     *
     * @param Res1
     * @param num1
     * @return
     */
    private static int countAbs(double[] Res1, double num1) {
        int le2 = 0;
        double[] temp = ri.abs(Res1);
        for (double temp1 : temp) {
            if (temp1 > num1) {
                le2++;
            }
        }
        return le2;
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

    /**
     * @param GD
     * @return
     */
    private static int max(byte[][] GD) {
        int max = -1;
        for (int i = 0; i < GD.length; i++) {
            max = GD[i][0] > GD[i][1] ? GD[i][0] : GD[i][1];
            max = max > GD[i][2] ? max : GD[i][2];
            if (max == 2) {
                break;
            }
        }

        return max;
    }

    /**
     * @param GD
     * @param index
     * @param AA
     * @return
     */
    private static double countP(byte[][] GD, int index, int AA) {
        double count = 0;
        for (int j = 0; j < GD.length; j++) {
            if (GD[j][index] == AA) {
                count++;
            }
        }
        count = count / GD.length;

        return count;
    }

    /**
     * 方差
     *
     * @param array
     * @return
     */
    public static double var(double[] array) {

        double ave = 0;

        for (int i = 0; i < array.length; i++) {
            ave += array[i];
        }
        ave /= array.length;

        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += (array[i] - ave) * (array[i] - ave);
        }
        sum /= array.length;

        return sum;
    }

    private static double[] arrayExp(double[] array) {

        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Math.exp(array[i]);
        }

        return result;

    }

    /**
     *
     * @param num
     * @param index
     * @return
     */
    private static byte[][] getArrayByRowIndex(byte[][] num, int[] index) {

        byte[][] byteArr = new byte[index.length][num[0].length];
        
        for ( int i = 0 ; i < index.length ; i++) {
            System.arraycopy(num[index[i]], 0, byteArr[i], 0, num[0].length);
        }
//        for (int i = 0; i < num.length; i++) {
//            for (int j = 0; j < index.length; j++) {
//                douArr[i][j] = num[i][index[j]];
//            }
//        }
        return byteArr;
    }

    private static double[] getArrayByIndex(double[][] num, int index) {

        double[] douArr = new double[num.length];
        for (int i = 0; i < num.length; i++) {
            douArr[i] = num[i][index];
        }

        return douArr;
    }

    /**
     *
     * @param num
     * @param index
     * @return
     */
    public static byte[][] getArrayByIndexToByte(byte[][] num, int index[]) {

        byte[][] bytArr = new byte[num.length][index.length];
        for (int j = 0; j < index.length; j++) {
            for (int i = 0; i < num.length; i++) {
                bytArr[i][j] = num[i][index[j]-1];
            }
        }
        return bytArr;
    }

    /**
     *  按名比较
     * @param GAWS
     * @param GM
     * @return
     */
    public static int[] match(String[][] GAWS, String[] GM) {
        List<Integer> index1 = new ArrayList<Integer>();
        for (int i = 0; i < GAWS.length; i++) {
            for (int j = 0; j < GM.length; j++) {
                if (GAWS[i][0].equals(GM[j])) {
                    //+1 强行补误差， 原因不明
                    index1.add(j + 1);
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
     * subDoubleArray
     *
     * @param beta
     * @param start
     * @param end
     * @return
     */
    public static double[] subDoubleArray(double[] beta, int start, int end) {

        double[] arraycopy = new double[end - start + 1];
        System.arraycopy(beta, start, arraycopy, 0, end - start + 1);
        return arraycopy;
    }

    private static int[] whichIsNa(boolean[][] phe3, int index) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < phe3.length; i++) {
            if (!phe3[i][index]) {
                list.add(i);
            }
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private static boolean[][] getNa(Bayesian bayes, String[] GDName, boolean[][] phe3) {
        boolean[][] result = new boolean[bayes.name.length][bayes.num[0].length];
        for (int i = 0; i < bayes.getName().length; i++) {
            for (int j = 0; j < GDName.length; j++) {
                if (bayes.getName()[i].equals(GDName[j])) {
                    result[i] = phe3[j];
                    continue;
                }
            }
        }
        return result;
    }

    /**
     * runEn_lasso方法
     *
     * @param CV
     * @param GD
     * @param y
     * @param GM
     * @param GMName
     * @param index
     * @param YName
     * @param YColName
     * @return
     */
    public static Map<String, Object> runEn_lasso(double[][] CV, byte[][] GD, double[][] y, int[][] GM, String[] GMName, int[] index, String[] YName, String[] YColName, boolean[][] phe3, String[] GDName) {

        String[][] result = null;//new String[YName.length + 1][index.length + 1];
        double[][] forecas = null;//new double[YName.length][index.length];
        String[] phename = null;

        for (int i = 0; i < index.length; i++) {
            Bayesian bayes = Compare.Compare(GDName, GD, YName, getArrayByIndex(y, index[i]));
            phename = bayes.getName();
            if (CV == null) {
                Random r1 = new Random();
                CV = new double[bayes.getNum().length][6];
                for (int k = 0; k < CV.length; k++) {
                    for (int l = 0; l < CV[0].length; l++) {
                        CV[k][l] = r1.nextDouble() * 2 - 1;
                    }
                }
            }

            if (result == null) {
                result = new String[bayes.getName().length + 1][index.length + 1];
                forecas = new double[bayes.getName().length][index.length];
            }
            boolean[][] temp = getNa(bayes, GDName, phe3);
            int[] index_y = whichIsNa(temp, index[i]);
            Map<String, Object> GWAS;
            if (CV == null) {
//                GWAS = Em_lasso.En_lasso(CV, getArrayRowByIndex(GD, index_y), getArrayByIndex(Em_lasso.getArrayByIndex(y, index[i]), index_y), GM, GMName);
                GWAS = Em_lasso.En_lasso(CV, getArrayRowByIndex(bayes.getNum(), index_y), getArrayByIndex(bayes.getTraits(), index_y), GM, GMName);
            } else {
                GWAS = Em_lasso.En_lasso(getArrayRoWByIndex(CV, index_y), getArrayRowByIndex(bayes.getNum(), index_y), getArrayByIndex(bayes.getTraits(), index_y), GM, GMName);

            }

            String[][] GWAS1 = (String[][]) GWAS.get("GWAS");
            double[] beta = (double[]) GWAS.get("beta");

            double[] subDoubleArray = subDoubleArray(beta, 1, beta.length - 1);
            double[] fixed;
            if (CV == null) {
                CV = new double[bayes.getNum().length][1];
                for (int k = 0; k < bayes.getNum().length; k++) {
                    CV[k][0] = 1;
                }
                fixed = new double[CV.length]; //new CrossImp().ncrossprod(CV, subDoubleArray);
                for (int m = 0; m < CV.length; m++) {
                    fixed[m] = CV[m][0] * subDoubleArray[m];
                }
            } else {
                fixed = new CrossImp().ncrossprod(getArrayRoWByIndex(CV, index_y), subDoubleArray);
            }

            int[] indexr = match(GWAS1, GMName );

            double[] u = new double[GWAS1.length];
            for (int j = 0; j < GWAS1.length; j++) {
                u[j] = Double.valueOf(GWAS1[j][3]);
            }

            byte[][] temppp = getArrayByIndexToByte(bayes.getNum(), indexr);
            double[] blup = new CrossImp().ncrossprod(temppp, u);

            double[] phe = new double[fixed.length];
            for (int j = 0; j < fixed.length; j++) {
                phe[j] = fixed[j] + blup[j];
            }

            for (int j = 0; j < blup.length; j++) {
                result[j + 1][i + 1] = "" + blup[j];
            }
            for (int j = 0; j < phe.length; j++) {
                forecas[j][i] = phe[j];
            }
        }

        result[0][0] = "name";
        for (int i = 0; i < index.length; i++) {
            result[0][i+1] = YColName[index[i] + 1];
        }
        for (int j = 0; j < phename.length; j++) {
            result[j + 1][0] = phename[j];
        }

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("result", result);
        temp.put("forecas", forecas);

        return temp;
    }

}
