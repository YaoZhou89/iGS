package com.Polygenic.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Jama.Matrix;
import com.Polygenic.operate.FileTraits;
import zb.archer.Demo.R.CrossImp;
import zb.archer.Demo.R.Diag;
import zb.archer.Demo.R.RmathImp;

public class GBLUP_eps {

	private static RmathImp ri = new RmathImp();
	private static CrossImp ci = new CrossImp();

	public static Map<String, Object> gBLUP_eps(double[][] X, List<?> K, Map<String, Object> mm, double[] y) {

		// index_inf= which(is.na(y))
		// index.ref = which(!is.na(y))
//		int[] index_ref = FileTraits.getIndexArrayWithoutZero(y);
		int[] index_ref = FileTraits.getIndexArrayWithoutNA(y);
		// n.inf = length(index.inf)
		int n_inf = y.length - index_ref.length;
		// n.ref = sum(!is.na(y))
		int n_ref = index_ref.length;
		int n = y.length;
		// cat("The individual number of Inference population
		// is:",length(index.inf),sep=" ")
		int n_random = K.size(); // # Kinship number
		if (n_random == 0)
//			System.err.println("This no random effect! Kinship Matrix is needed.");
                    System.out.println( X == null );
		if (X == null) {
			X = model_matrix(y.length);
		}
		double[][] Ind = Diag.diag(n_ref, 1);
		double[][] BLUP_left = new double[n][n_ref];
		double[] Var = (double[]) mm.get("Var");
		for (int i = 0; i < n_random; i++) {

			double[][] temp2 = ri.times(ri.getArrayByCol((double[][]) K.get(i), index_ref), Var[i]);
			// Var[i]* K[[i]][,index.ref]
			double[][] temp3 = new Matrix(
					ri.plus(ri.times((ri.getArrayByRowandCol(((double[][]) K.get(i)), index_ref, index_ref)), Var[0]),
							ri.times(Ind, Var[Var.length - 1]))).inverse().getArray();
			BLUP_left = ri.plus(BLUP_left , ci.ncrossprod(temp2 , temp3));
			// BLUP.left = BLUP.left + Var[i]* K[[i]][,index.ref]
			// 	%*%solve(K[[i]][index.ref,index.ref]*Var[1]+Ind*Var[length(Var)])
			// 
		}
		double[] BLUP_right = ri.minus(ri.getArrayByIndex(y, index_ref),
				ci.ncrossprod(ri.getArrayByRow(X, index_ref), (double[]) mm.get("Bhat")));
		// BLUP.right =as.matrix(y[index.ref]) - X[index.ref,]%*%mm$Bhat
		// BLUP = BLUP.left %*% BLUP.right
		double[] BLUP = ci.ncrossprod(BLUP_left, BLUP_right);
		// phe = X %*% mm$Bhat + BLUP
		double[] phe = ri.plus(ci.ncrossprod(X, (double[]) mm.get("Bhat")), BLUP);
		// LL = mm$llik
		double LL = (double) mm.get("llik");

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("BLUP", BLUP);
		result.put("phe", phe);
		result.put("Var", Var);
		result.put("LL", LL);
		
		return result;
		// return(list(BLUP = BLUP, phe = phe, Var = Var, LL = LL))
	}
        
        private static double[][] model_matrix(int length) {
        double[][] result = new double[length][1];
        for (int i = 0; i < result.length; i++) {
            result[i][0] = 1;
        }
        return result;
    }
}
