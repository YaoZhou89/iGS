package com.Em.operate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**	Bayesian 方法处理文件类————处理通用文件类
 * 
 * @author Belong.
 *
 */
public class OperateNum {
	
	public static String[] name;
	public static byte[][] num;
	
	public OperateNum() {}
	public OperateNum(String[] name2, byte[][] num2) {
		this.name = name2 ;
		this.num = num2 ;
	}
	public String[] getName(){
		return name;
	}
	public byte[][] getNum(){
		return num;
	}
	
	/** 处理num表 去掉全0全2行列 方法、
	 * @param file1
	 * @return
	 * @throws IOException
	 */
	public OperateNum(String file1, boolean tip) {

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
				StringBuffer sbs = new StringBuffer();
				for (int i = 1; i < str.length; i++) {
					sbs.append(str[i]);
				}
				String newstr = sbs.toString();
				sbs.delete(0, sbs.length() - 1); // 释放buffer
				if (!newstr.matches("[0,2]+")) {
					liststr.add(str);
				}
			}
			String[][] Array1 = new String[liststr.size()][liststr.get(0).length];
			boolean[] bool = new boolean[liststr.get(0).length];
			bool[0] = true;
			for (int i = 0; i < liststr.size(); i++) {
				for (int j = 0; j < liststr.get(i).length; j++) {
					Array1[i][j] = liststr.get(i)[j];
				}
			}
			for (int j = 1; j < liststr.get(0).length; j++) {
				for (int i = 0; i < liststr.size(); i++) {
					//
					if (!Array1[i][j].equals("0") && !Array1[i][j].equals("2")) {
						bool[j] = true;
						break;
					}
				}
			}
			int realcol = 0;
			for (int i = 1; i < bool.length; i++) {
				if (bool[i])
					realcol++;
			}
			this.name = new String[liststr.size()];
			this.num = new byte[liststr.size()][realcol];
			for (int i = 0; i < liststr.size(); i++) {
				int k = 0;
				this.name[i] = Array1[i][0];
				for (int j = 1; j < liststr.get(0).length; j++) {
					if (bool[j]) { // bool 列标识数
						this.num[i][k] = Byte.valueOf(Array1[i][j]);
						k++;
					}
				}
			}
			liststr.clear();
			br.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
}
