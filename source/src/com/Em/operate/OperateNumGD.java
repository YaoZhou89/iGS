package com.Em.operate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**	Em_lasso 方法处理文件类
 *      byte[][] num;
 * @author Belong.
 *
 */
public class OperateNumGD {
	
	public static byte[][] num;
	
	public OperateNumGD() {}
	public OperateNumGD( byte[][] num2) {
		this.num = num2 ;
	}
	
	public byte[][] getNum(){
		return num;
	}
	
	/** 处理num表 去掉全0全2行列 方法、
	 * @param file1
	 * @return
	 * @throws IOException
	 */
	public OperateNumGD(String file1, boolean tip) {

		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
			String line;
			// 如果大表有标题
			if (tip)
				line = br.readLine();
			ArrayList<String[]> liststr = new ArrayList<String[]>();
			while ((line = br.readLine()) != null) {
				//间隔符在这里改！！！！
				String[] str = line.split("\t");
				liststr.add(str);
				
			}
			
			this.num = new byte[liststr.size()][liststr.get(0).length];
			for (int i = 0; i < liststr.size(); i++) {
				for (int j = 0; j < liststr.get(0).length; j++) {
					this.num[i][j] = Byte.valueOf(liststr.get(i)[j]);
				}
			}
			liststr.clear();
			br.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		//return new OperateNum(name, num);
	}
	
}
