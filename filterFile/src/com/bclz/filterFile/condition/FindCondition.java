/**  
* Title: FindCondition.java 
* Description:  
* Copyright: Copyright (c) 2017  
* Company: www.baidudu.com 
* @author xuchang  
* @date 2018年6月22日  
* @version 1.0  
*/  
package com.bclz.filterFile.condition;

import java.util.List;
import java.util.Map;


/**  
* Title: FindCondition
* Description: 
* @author xuchang 
* @date 2018年6月22日  
*/
public interface FindCondition {
	
	public boolean processCondition(Map<String, List<String>> con,String target);
	
}
