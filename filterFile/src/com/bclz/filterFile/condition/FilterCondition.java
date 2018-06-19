/**  
* Title: FilterCondition.java 
* Description:  
* Copyright: Copyright (c) 2017  
* Company: www.baidudu.com 
* @author xuchang  
* @date 2018年6月14日  
* @version 1.0  
*/  
package com.bclz.filterFile.condition;

import java.io.File;
import java.io.FileFilter;

/**  
* Title: FilterCondition
* Description: 
* @author xuchang 
* @date 2018年6月14日  
*/
public class FilterCondition implements FileFilter {
	
	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		String fileName = file.getName();
		if(fileName.indexOf(".json")>0 && fileName.indexOf("___")>0 )
		{
			return true;
		}
		return false;
	}
	
}
