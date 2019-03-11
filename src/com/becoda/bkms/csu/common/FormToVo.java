package com.becoda.bkms.csu.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

public class FormToVo {

	/**
	 * 反射封装页面 form信息到vo  使用时 需要 页面 form 中name与 vo 属性一直 并且vo中有相应的 set方法
	 * @param request
	 * @param clas
	 * @return
	 * @throws Exception
	 */
	public static Object formToVo(HttpServletRequest request,Class clas)throws Exception{
		//获取vo全部属性
 		Field[] fs = clas.getDeclaredFields();
 		//反射生成对象
 		Object obj = clas.newInstance();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			//属性名称  用于获取页面 form 信息
			String name = f.getName();
			//属性类型
			String typ = f.getType().toString();
			typ = typ.substring(typ.lastIndexOf(".")+1,typ.length());
			//拼装方法名称
			String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length());
			//获取方法
			Method method = clas.getDeclaredMethod(methodName,new Class[] { f.getType() });
			//根据属性名称获取页面信息class java.lang.Integer
			Object o = null;
			String par = request.getParameter(name);
			if(par!=null&&!"".equals(par)){
				if(typ.equals("Integer")){
					o = Integer.parseInt(par);
				}else if(typ.equals("String")){
					o = par;
				}else if(typ.equals("Double")){
					o = Double.parseDouble(par);
				}else if(typ.equals("Float")){
					o = Float.parseFloat(par);
				}else if(typ.equals("Long")){
					o = Long.parseLong(par);
				}else if(typ.equals("Date")){
					SimpleDateFormat sfmt = new SimpleDateFormat("yyyy-MM-dd"); 
					o = sfmt.parse(par);
				}
			}
			
			//如果页面信息不为空 调用set方法
			if(o!=null){
				method.invoke(obj, o);
			}
		}
		return obj;
	}
	
	
}
