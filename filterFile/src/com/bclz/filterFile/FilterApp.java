/**  
* Title: FilterApp.java 
* Description:  
* Copyright: Copyright (c) 2017  
* Company: www.baidudu.com 
* @author xuchang  
* @date 2018年6月14日  
* @version 1.0  
*/  
package com.bclz.filterFile;


import com.bclz.filterFile.core.FilterFiles;

/**  
* Title: FilterApp
* Description: 
* @author xuchang 
* @date 2018年6月14日  
*/
public class FilterApp {
	
	
	public static void main(String[] args) {
//		args=new String[] {"C:\\Users\\xuchang\\Desktop\\123","02013311"};
		if(args!=null&&args.length>=2) {
			long a=System.currentTimeMillis();
			(new FilterFiles()).FilterFileByDir(args[0], args[1],3);
			long b=System.currentTimeMillis();
			System.out.println("Total Time:"+(b-a));
		}else {
			System.out.println("请输入路径或字段");
		}
		
	}

}
