package com.Em.operate;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Write {
	
	/**	运算单列BLUP值添加至BLUP矩阵
	 *		
	 * @param result	目标矩阵
	 * @param result1	单列BLUP值
	 * @param realPhe	判断表型是否存在
	 * @param index		添加至第index列（0~n）
	 * @return
	 */
	public static String[][] add(String[][] result, double[] result1,double[] realPhe, int index){
		
		for(int i=0; i<result.length; i++){
			//表型为NA 或没有对应个体表型值时 realPhe[i]=0.0 
			//if判断如果没有表型值  可选择输出NA 或 模拟BLUP（未确定是否为预测BLUP值）
	/*		 if(!(realPhe[i]!=0.0))
				 result[i][index] = "NA";
			 else	*/
				 result[i][index] = String.valueOf( result1[i]);
		}
		return result;
	}
	
	

	/**	BLUP结果输出txt文件
	 * @param rolname		表型名
	 * @param pedigreename	个体名
	 * @param resultstr		系谱法结果
	 * @param path			输出文件地址
	 */
	public static void writeFile( String[][] resultstr, String path) {
		
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			
			//写数据
			for(int i=0; i<resultstr.length; i++){

				for(String data:resultstr[i])
					writer.write(data+"\t");
				writer.write("\r\n");
			}
			
			writer.close();
			//System.out.println("写文件完成");
		} catch (Exception e) {
			//System.out.println("写文件内容操作出错");
			e.printStackTrace();
		}
	}
	
	public static void writeK(double[][] pedigree, String path){
		
		
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			//写数据
			for(int i=0; i<pedigree.length; i++){
				
				for(double data:pedigree[i])
					writer.write(data+", ");
				writer.write("\r\n");
			}
			writer.close();
			//System.out.println("写文件完成");
		} catch (Exception e) {
			//System.out.println("写文件内容操作出错");
			e.printStackTrace();
		}
	}
	
	public static void writeFile(ArrayList pedigree, String path){
		
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			String[] name = {"G0(个体)","G1(父)","G11(父父)","G12(父母)","G111(父父父)","G112()父父母","G121(父母父)","G122(父母母)","G2(母)","G21(母父)","G22(母母)","G211(母父父)","G212(母父母)","G221(母母父)","G222(母母母)"};
			
			//写标题
			for(String name1:name)
				writer.write(name1+"\t");
			writer.write("\r\n");
			
			//写数据			
			for(int i=0; i<pedigree.size()/15; i++){
				for(int j=0; j<15; j++)
					writer.write(pedigree.get(i*15+j)+"\t");
				writer.write("\r\n");
			}
			writer.close();
			//System.out.println("写文件完成");
		} catch (Exception e) {
			//System.out.println("写文件内容操作出错");
			e.printStackTrace();
		}
	}
}
