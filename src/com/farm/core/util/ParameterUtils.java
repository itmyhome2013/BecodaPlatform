package com.farm.core.util;

import java.util.ArrayList;
import java.util.List;


public class ParameterUtils {
/**
 * 将逗号分隔字符串转换为字符串数组
 * @param 02:position,01:position,02:role,01:role,1:project
 * RETURN :list<02:position>
 * */
	public static List<String> expandDomainPara(String para){
		//String mark="02:position,01:position,02:role,01:role,1:project";
		if(para==null)return new ArrayList<String>();
		para=para.replace("，",",");
		String[] markdot= para.split(",");
		List<String> list_=new ArrayList<String>();
		for (int i = 0; i < markdot.length; i++) {
			String temp=markdot[i];
			if(temp!=null&&!temp.equals("")&&!temp.equals(" "))
			list_.add(temp);
		}
		return list_;
	}
	public static String removeNull(String value){
		if(value==null||value.equals("null")){
			return "";
		}
		return value;
	}
}
