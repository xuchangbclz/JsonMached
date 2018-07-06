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
		try {
			
			String pathAndNum="";
			String wayAndData="";
			String regex="";
			int isContent=1;
			System.out.println("Please import matches File regex and isContent by 1|0 (example:((.*)_(.*)\\.json)&0)");
			Scanner sc=new Scanner(System.in);
			if(true) {
				String fileParams=sc.nextLine();
				//				String fileParams="((.*)_(.*))\\.json&0";
				regex=fileParams.split("&")[0];
				isContent=Integer.parseInt(fileParams.split("&")[1]);
			}
			System.out.println("Please import target path and threadNum by & (example:C://Users//xuchang//Desktop//123&3):");
			if(true) {
				//				pathAndNum="C://Users//xuchang//Desktop//123&3";
				pathAndNum=sc.nextLine();
			}
			
			System.out.println("Please import filter way and data by > and & ; (example:!=>2&3&2;=>2&3&2&1):");
			if(true) {
				Map<String, List<String>> con=new HashMap<>();
				wayAndData=sc.nextLine();
				//				wayAndData="=>05";
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
				(new FilterFiles(con,regex,isContent)).FilterFileByDir(path[0], Integer.parseInt(path[1]));
			}
			
		}catch(Exception e){
			System.out.println("输入异常:"+e);
		}
		
	}

}
