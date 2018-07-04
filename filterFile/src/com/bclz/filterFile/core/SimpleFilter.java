/**  
* Title: SimpleFilter.java 
* Description:  
* Copyright: Copyright (c) 2017  
* Company: www.baidudu.com 
* @author xuchang  
* @date 2018年6月22日  
* @version 1.0  
*/  
package com.bclz.filterFile.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bclz.filterFile.condition.FindCondition;

/**  
* Title: SimpleFilter
* Description: 
* @author xuchang 
* @date 2018年6月22日  
*/
public class SimpleFilter implements FindCondition {


	@Override
	public boolean processCondition(Map<String, List<String>> con,String target) {
		// TODO Auto-generated method stub
		for(Entry<String, List<String>> cond:con.entrySet()) {
			
			if("=".equals(cond.getKey())) {
				for(String value:cond.getValue()) {
					if(target.indexOf(value)<0) {
						return false;
					}
				}
				
			}
			if("!=".equals(cond.getKey())) {
				for(String value:cond.getValue()) {
					if(target.indexOf(value)>0) {
						return false;
					}
				}
			}
			
			
		}
		
		return true;
	}

}
