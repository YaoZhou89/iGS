package com.Polygenic.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Jama.Matrix;
import zb.archer.Demo.R.CrossImp;
import zb.archer.Demo.R.Diag;
import zb.archer.Demo.R.RmathImp;

public class CalcAIvar {

	private static RmathImp ri = new RmathImp();
	private static CrossImp ci = new CrossImp();
	Map<String, Object> result = new HashMap<String, Object>();

	public static Map<String, Object> calcAIvar(double[] y, List<double[][]> B, double[][] X, double[] Var,
			String alg) {

		// input:
		double[][] Z = null;
		double[][] Ze = null;
		boolean reps = false;
		// String alg = "ai" ;
		// double[][] Var = null;
		double conv_val = 1e-4;

		// output:
		Map<String, Object> result = new HashMap<String, Object>();
		double[][] invI = null;
		double[][] Vinv = null;
		double[] Bhat = null;
		double LL = 0 ;
		int n = y.length;
		// Ind = Matrix(diag(n), sparse=T) 稀疏矩阵
		double[][] Ind = Diag.diag(y.length);
		if (B.isEmpty() || B == null) {
			B = new ArrayList<double[][]>();
			// 注意：获取Ind的引用 非值
			B.add(Ind);
		}

		if (Z == null) {
			Z = Ind;
		}
		// 虚拟变量/哑变量
		// **** if( X == null ) { X = model.matrix(y~1);}
                if ( X == null ){
                    X = model_matrix(y.length);
                }
                
		if (Ze == null) {
			Ze = Ind;
		}

		// 列名还是还是什么不参与计算1
		// VC.names=paste('sigma', c(names(B), 'E'), sep='')

		int N_s_c = B.size();
		int Vcmp_cnt = N_s_c + 1;
		if (Var == null) {
			Var = rep((double)1/Vcmp_cnt, Vcmp_cnt);
		}

		double[][] I = new double[Vcmp_cnt][Vcmp_cnt];
		double[][] s = new double[Vcmp_cnt][1];

		double[] diffs = rep(10, Vcmp_cnt);

		ArrayList<double[][]> VV = new ArrayList<double[][]>();
		for ( int i = 0; i < N_s_c; i++ ) {
			// VV[[i]]=Z %*% tcrossprod(B[[i]],Z)
			VV.add(ci.ncrossprod(Z, ci.tcrossprod(B.get(i), Z)));
		}
		VV.add(Ze);

		int i = 0;
		int theLoop = 100;
		// var.old 就感觉没啥卵用，
		// int var_old = 0;
		while (sumifelse(diffs, conv_val) < Vcmp_cnt && i < theLoop) {

			i = i + 1;
			double[][] V = new double[n][n];
			for (int vcs = 0; vcs < VV.size(); vcs++) {
				V = ri.plus(V, new Matrix((double[][]) VV.get(vcs)).times(Var[vcs]).getArray());
			}
			// print('Inverting V')
			Vinv = new Matrix(V).inverse().getArray();
			// print('Done inverting V')
			double[][] tXVinvX = ci.ncrossprod(ci.ncrossprod(new Matrix(X).transpose().getArray(), Vinv), X);
			double[][] inv_tXVinvX = new Matrix(tXVinvX).inverse().getArray();

			double[][] VinvX = ci.ncrossprod(Vinv, X);
			double[][] itv = ci.ncrossprod(ci.ncrossprod(inv_tXVinvX, new Matrix(X).transpose().getArray()), Vinv);
			double[][] P = ri.minus(Vinv, ci.ncrossprod(ci.ncrossprod(Vinv, X), itv));
			// # P = Vinv - (VinvX %*% (ginvXVX %*% t(VinvX)))
			if (alg == "fs") {
//				System.out.println("Fisher scoring algorithm: calculating expected VC Hessian");
			}
			if (alg == "nr") {
//				System.out.println("Netwon rhapson algorithm: calculating observed VC Hessian");
			}
			if (alg == "ai") {
//				System.out.println("Average information algorithm: calculating avg of expected and observed VC Hessians");
			}
			double[] Py = ci.ncrossprod(P, y);
			for (int ii = 0; ii < Vcmp_cnt; ii++) {
				for (int jj = ii; jj < Vcmp_cnt; jj++) {
					if (alg == "fs") {
						// I[ii][jj]= 0.5 * sum( diag((( P%*%VV[[ii]] ) %*%P )
						// %*% VV[[jj]] ) )
						I[ii][jj] = 0.5 * sumdiag(ci.ncrossprod(ci.ncrossprod(ci.ncrossprod(P, (double[][]) VV.get(ii)), P),
								(double[][]) VV.get(jj)));
					}
					if (alg == "nr") {
						// *****I[ii][jj]=-0.5*sum(diag(P%*%VV[[ii]]%*%P%*%VV[[jj]]))
						// + (t(y)%*%P%*%VV[[ii]]%*%P%*%VV[[jj]]%*%P%*%y)[1,1]
						I[ii][jj] = -0.5 * sumdiag(ci.ncrossprod(ci.ncrossprod(ci.ncrossprod(P, (double[][]) VV.get(ii)), P), (double[][]) VV.get(ii)) ) 
								+ ci.ncrossprod(ci.ncrossprod(ci.ncrossprod(ci.ncrossprod(ci.ncrossprod(
								y, P), (double[][]) VV.get(ii)), P),(double[][]) VV.get(jj)), Py);
					}
					if (alg == "ai") {
						// t(Py)%*%VV[[ii]]%*%P%*%VV[[jj]]%*%Py)[1,1]
						/*
						I[ii][jj] = 0.5 * (ci.ncrossprod(ci.ncrossprod(ci.ncrossprod(
								ci.ncrossprod(new Matrix(Py).transpose().getArray(), (double[][]) VV.get(ii)), P),
								(double[][]) VV.get(jj)), Py)[0][0]);
						*/
						I[ii][jj] = 0.5 * (ci.ncrossprod(ci.ncrossprod(ci.ncrossprod(
								ci.ncrossprod(Py, (double[][]) VV.get(ii)), P),
								(double[][]) VV.get(jj)), Py));
					}
					// print(paste(ii, jj))
					I[jj][ii] = I[ii][jj];
				}
			}
			for (int ii = 0; ii < Vcmp_cnt; ii++) {
				// s[ii,1]= -0.5*sum(diag(P%*%VV[[ii]])) +
				// .5*(t(Py)%*%VV[[ii]]%*%Py )[1,1]
				/*
				s[ii][0] = -0.5 * sumdiag(ci.ncrossprod(P, (double[][]) VV.get(ii))) + 0.5
						* (ci.ncrossprod(ci.ncrossprod(new Matrix(Py).transpose().getArray(), (double[][]) VV.get(ii)),
								Py)[0][0]);
				 */
				s[ii][0] = -0.5 * sumdiag(ci.ncrossprod(P, (double[][]) VV.get(ii))) + 0.5
						* (ci.ncrossprod(ci.ncrossprod(Py, (double[][]) VV.get(ii)),
								Py));
			}

			 invI = new Matrix(I).inverse().getArray();
			// s[n][0] 只一列
			double[] newVar = ri.plus(Var, ri.getArrayByCol(ci.ncrossprod(invI, s), 0));

			for (int j = 0; j < newVar.length; j++) {
				if (newVar[j] < 0)
					newVar[j] = 2.6e-9;
			}
			for (int d = 0; d < diffs.length; d++) {
				diffs[d] = Math.abs(Var[d] - newVar[d]);
			}
			// 注意 引用地址！！！非传值******************************
			Var = newVar;

			// cat('\n')
			// cat("iteration ", i, '\n')
			// cat(VC.names, '\n')
			// cat(Var, '\n')

			Bhat = ci.ncrossprod(itv, y);
			// cat("Fixed Effects, Bhat = ", as.matrix(Bhat), '\n')
			// det_tXVinvX=determinant(tXVinvX, logarithm=TRUE) 行列式--modulus系数/模
			// ？反正不是行列式值，sign正负标识
			// det() 求行列式的值
			double tXVinvX_modulus = Math.log(new Matrix(tXVinvX).det());
			// double tXVinvX_sign = Math.copySign(1.0, tXVinvX_modulus);
			double det_tXVinvX = tXVinvX_modulus;

			// ***** det_tXVinvX = det_tXVinvX$modulus * tXVinvX_sign ;
			double det_V_modulus = Math.log(new Matrix(V).det());
			// double det_V_sign = Math.copySign(1.0, det_V_modulus);
			double det_V = det_V_modulus;

			LL = (Math.log(ci.ncrossprod(ci.ncrossprod(y, P), y)) +	(det_tXVinvX + det_V))* -0.5;
		
			// cat("Log Likelihood = " , as.matrix(LL), '\n')
			// cat("VC convergence vals", '\n')
			// cat(diffs, '\n')

			

		}
		result.put("Var", Var);
		result.put("invI", invI);
		result.put("W", Vinv);
		result.put("Bhat", Bhat);
		result.put("llik", LL);
		return result;
	}

	private static double sumdiag(double[][] var) {
		double result = 0;
		for (int i = 0; i < var.length; i++) {
			result += var[i][i];
		}
		return result;
	}

	private static int sumifelse(double[] diffs, double conv_val) {
		int sum = 0;
		for (int i = 0; i < diffs.length; i++) {
			if (diffs[i] < conv_val)
				sum++;
		}
		return sum;
	}

	private static double[] rep(double num, int length) {
		double[] result = new double[length];
		for (int i = 0; i < length; i++) {
			result[i] = num;
		}
		return result;
	}
        
        private static double[][] model_matrix(int length) {
        double[][] result = new double[length][1];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = 1;
        }
        return result;
    }
}
