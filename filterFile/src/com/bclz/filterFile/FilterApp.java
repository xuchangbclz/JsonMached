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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
		try {
			
			String pathAndNum="";
			String wayAndData="";
			System.out.println("Please import target path and threadNum by & (example:C://Users//xuchang//Desktop//123&3):");
			Scanner sc=new Scanner(System.in);
			if(sc.hasNext()) {
				pathAndNum=sc.nextLine();
			}
			
			System.out.println("Please import filter way and data by > and & ; (example:!=>2&3&2;=>2&3&2&1):");
			if(sc.hasNext()) {
				Map<String, List<String>> con=new HashMap<>();
				wayAndData=sc.nextLine();
				String[] conList=wayAndData.split(";");
				for(String conA :conList) {
					List<String> conValue=new ArrayList<>();
					String[] wayValue=conA.split(">");
					for(String cc:wayValue[1].split("&")) {
						conValue.add(cc);
					}
					con.put(wayValue[0], conValue);
				}
				String[] path=pathAndNum.split("&");
				System.out.println(path[0]+"   "+path[1]);
				System.out.println(con.toString());
				(new FilterFiles()).FilterFileByDir(path[0], con,Integer.parseInt(path[1]));
			}
			
		}catch(Exception e){
			System.out.println("输入异常:"+e);
		}
		
	}

}
