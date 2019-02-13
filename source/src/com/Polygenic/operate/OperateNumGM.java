package com.Polygenic.operate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**		Em_lasso 方法处理文件类
 * 
 * @author Belong.
 *
 */
public class OperateNumGM {
	
	public static String[] name;
	public static int[][] num;
	
	public OperateNumGM() {}
	public OperateNumGM(String[] name2, int[][] num2) {
		this.name = name2 ;
		this.num = num2 ;
	}
	public String[] getName(){
		return name;
	}
	public int[][] getNum(){
		return num;
	}
	
	/**	处理文件类
	 * @param file1
	 * @return
	 * @throws IOException
	 */
	public OperateNumGM(String file1, boolean tip) {

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
			
			this.name = new String[liststr.size()];
			this.num = new int[liststr.size()][liststr.get(0).length-1];
			for (int i = 0; i < liststr.size(); i++) {
				this.name[i] = liststr.get(i)[0];
				for (int j = 1; j < liststr.get(0).length; j++) {
					this.num[i][j - 1] = Integer.valueOf(liststr.get(i)[j]);
				}
			}
			liststr.clear();
//			System.out.println("OperateNum   ok");
			br.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		//return new OperateNum(name, num);
	}
	
}
