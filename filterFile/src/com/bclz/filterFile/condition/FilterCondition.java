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
import java.util.regex.Pattern;

/**  
* Title: FilterCondition
* Description: 
* @author xuchang 
* @date 2018年6月14日  
*/
public class FilterCondition implements FileFilter {
	
	private String regex;
	
	public FilterCondition(String regex){
		
		this.regex=regex;
	}
	
	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		String fileName = file.getName();
		if(Pattern.matches(regex, fileName) )
		{
			return true;
		}
		return false;
	}
	
}
