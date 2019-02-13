package zb.archer.Demo.domain;

/**
 * em-lasso
 * @author Asus
 *
 */
public class Em_BlassoEigen {

	double[] eigD;
	double[][] eigV;
	
	public double[] getEigD() {
		return eigD;
	}
	public void setEigD(double[] eigD) {
		this.eigD = eigD;
	}
	public double[][] getEigV() {
		return eigV;
	}
	public void setEigV(double[][] eigV) {
		this.eigV = eigV;
	}
	
	/**	去掉特征值最后一个数	并前后倒置
	 * @param eigD
	 * @return
	 */
	public static double[] operateD(double[][] eigD){		
		double[] eigLine = new double[eigD.length];
		for(int i=0; i<eigD.length; i++){
			eigLine[eigD.length-i-1] = eigD[i][i];
		}
		return eigLine;
	}
	
	/**	去掉特征向量最后 一列	并前后列倒置
	 * @param eigV
	 * @return
	 */
	public static double[][] operateV(double[][] eigV){		
		double[][] eig = new double[eigV.length][eigV[0].length-  1  ];
		for(int i=0; i<eigV.length; i++){
			for(int j = 1   ; j<eigV[0].length; j++){
				eig[i][eigV[0].length-j- 1] = eigV[i][j];
			}
		}
		return eig;
	}
	
}
