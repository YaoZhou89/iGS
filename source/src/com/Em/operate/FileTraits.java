package com.Em.operate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**	Traits表型对象————处理通用文件类
 * 
 * 	colName		表型名
 *	pheName		个体名
 *	phe1		表型矩阵String
 *	phe2		表型数组double
 * 
 * @author Belong.
 *
 */
public class FileTraits {
	
	private String[] colName;
	private String[] pheName;
	private String[][] phe1;
	private double[][] phe2;

	public double[][] toDoubleArray(){
		double[][] temp = new double[phe1.length][phe1[0].length];
		for( int i = 0 ; i < phe1.length ; i++ )
			for( int j = 0 ; j < phe1[0].length ; j++ )
				temp[i][j] = Double.valueOf(phe1[i][j]);
		
		phe2 = temp;
		return phe2;
	}
	public String[] getColName() {
		return colName;
	}
	
	/**	获取index集合 对应表型名
	 * @param index			待求表型列数集合
	 * @return colname		表型名
	 */
	public String[] getColName(int[] index) {
		String[] colname = new String[index.length];
		for(int i=0; i<index.length; i++)
			colname[i]=this.colName[index[i]];
		
		return colname;
	}

	public String[] getPheName() {
		return pheName;
	}

	public String[][] getPhe1() {
		return phe1;
	}

	/**	从phe[][] 获取第index列数据 ,将已有phe表名与pedigree表名比对，
	 *	phe中不存在的  在return的矩阵中添零补齐、phe中为NA的改成0.0
	 *	index范围1;n
	 *
	 * @param pedegreeName 
	 * @param phe			phedata
	 * @param index			1:n
	 * @return
	 */
	public double[] getRealData(String[] pedigreeName, int index) {
		double[] doublearray = new double[pedigreeName.length];
		//行数标识符
		int count=0;
		try{
		for(int i=0; i<pedigreeName.length; i++){
			//匹配成功标识符   匹配成功时true  失败时false， 表型值 
			boolean ide = false ;
			for(int j=0; j<phe1.length; j++){
				if(pedigreeName[i].equals(pheName[j])){
					ide = true;
					doublearray[count] = Double.valueOf( phe1[j][index-1]);
				}
			}
			//匹配失败时 赋0.0
			if(!ide)	
				doublearray[count] = 0.0;
			count++;			
		}
		}catch(Exception e){
//			System.out.println("输入列异常！");
			System.exit(1);
		}
		return doublearray;
	}

	/**		取第index列表型数据, NA或 NaN跳过不取 。
	 * 
	 * @param index		获取第index列，  index ∈( 1：n )
	 * @return data2		表行中指定列表型数据
	 */
	public double[] getIndexData(int index){
		
		ArrayList<Double> data1 = new ArrayList<Double>();
		//取表型判断是否 NA或 NaN
		for(String[] phe:this.phe1)
			if(!phe[index-1].equals("0.0")){
				data1.add( Double.valueOf(phe[index-1]) );
				}
		Double[] data = (Double[])data1.toArray(new Double[data1.size()]);
		double[] data2 = new double[data.length];
		for(int i=0; i<data2.length; i++)
			data2[i] = data[i].doubleValue();
			
		return data2;
	}
	
	/** 第index列表型个体名，表型为NA或 NaN跳过不取 。
	 * 
	 * @param index		获取第index列，  index ∈( 1：n )
	 * @return data		表行中指定列表型数据
	 */
	public String[] getIndexName(int index){
		
		ArrayList<String> name = new ArrayList<String>();
		
		//填入表型非 Na或 NaN个体名
		for(int i=0; i<pheName.length; i++)
			if(!this.phe1[i][index-1].equals("0.0"))
				name.add( pheName[i] );
		String[] name1 = (String[])name.toArray(new String[name.size()]);
		
		return name1;
	}


	/**	读表型值文件  
	 * 
	 * @param file		文件地址
	 */
	public FileTraits(String file) {
		
		ArrayList<String> liststr = new ArrayList<String>();
		BufferedReader br = null;

		try{
		br = new BufferedReader(new InputStreamReader(new FileInputStream( file ),"UTF-8"));
		//列名
		String line = (String)br.readLine();
		//第一行列标题	间隔符在这里改！！！！
		colName = line.split("\t");
		
		while ((line = br.readLine()) != null) {
			//		隔符在这里改！！！！
			String[] str = line.split("\t");
			//add第一列 个体名
			//add表型值对应列      从0列开始 （逻辑物理技术转换）
			for(int i=0; i<str.length; i++)
				liststr.add(str[i]);
		}
		
		phe1 = new String [liststr.size()/colName.length][colName.length-1];
		//个体名
		pheName = new String[liststr.size()/colName.length]; 
		
		for(int i=0; i<liststr.size()/colName.length; i++){
			pheName[i] = liststr.get(i*colName.length);
			for(int j=1; j<colName.length; j++){
				//原表型值为NA时  运算前改成0.0  此处改、
				if((liststr.get(i*colName.length+j)).equals("NaN") || (liststr.get(i*colName.length+j)).equals("NA"))
					phe1[i][j-1] = "0.0" ;
				else
					phe1[i][j-1] = liststr.get(i*colName.length+j);
			}
		}
		
		br.close();
		}catch (IOException e){
//			e.printStackTrace();
		}
	}

	/*
	public static void main(String[] args) throws UnsupportedEncodingException{
		String file = "d:/2015student/javalassoA/file/mdp_traits.txt";
		
		FileTraits fb = new FileTraits(file);
		String[] colName = fb.getColName();
		String[] pheName = fb.getPheName();
		String[][] phe = fb.getPhe1();
		
		
		for(String str:colName)
			System.out.print(str+"\t");
		System.out.println();
		
		for(String str:pheName)
			System.out.print(str+"\t");
		System.out.println();
		
		
		
		for(String[] phe1:phe){
			for(String str:phe1)
				System.out.print(str+"\t");
			System.out.println();
		}
	}
	*/
	

}
