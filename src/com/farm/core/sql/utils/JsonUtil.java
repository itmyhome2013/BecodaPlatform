package com.farm.core.sql.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import se.akerfeldt.com.google.gson.Gson;
import se.akerfeldt.com.google.gson.GsonBuilder;
import com.becoda.bkms.common.web.PageVO;

public class JsonUtil {
	
	static DecimalFormat format = new DecimalFormat("#.000000");

	//返回easyui 分页json
	public static String toEasyuiJson(List<Map<String,String>> list,PageVO pageVo){
		Gson gson = new Gson();
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(list == null ||list.size() <= 0){
			result.put("total",0);
			result.put("rows", "");
		}else{
			result.put("total", pageVo.getTotalRecord());
			result.put("rows", list);
		}
		
		String jsonResult = gson.toJson(result);
		gson = null;
		result = null;
		return jsonResult;
	}
	
	//返回普通json
	public static String toJson(Object obj){
		Gson gson = new Gson();
		String jsonResult = gson.toJson(obj);
		gson = null;
		return jsonResult;
	}
	
	//将json转化为普通对象
	public static Object[] parseJson(String jsonString, Class clazz) {  
	    JSONArray array = JSONArray.fromObject(jsonString);  
	    Object[] obj = new Object[array.size()];  
	    for (int i = 0; i < array.size(); i++) {  
	        JSONObject jsonObject = array.getJSONObject(i);  
	        obj[i] =  JSONObject.toBean(jsonObject, clazz);  
	    }  
	    return obj;  
	}  
	//将easyui json 输出到客户端
	public static void printEasyuiJson(String jsonResult,
			HttpServletResponse httpResponse) throws IOException {
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		PrintWriter writer = httpResponse.getWriter();
		writer.print(jsonResult);
		
	}
	
	public static void printEasyuiJson(List<Map<String,String>> list,PageVO pageVo,
			HttpServletResponse httpResponse) throws IOException {
		String jsonResult = toEasyuiJson(list,pageVo);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		PrintWriter writer = httpResponse.getWriter();
		writer.print(jsonResult);
	}
	//返回easyui 分页json
		public static String toEasyuiJsonPro(Object object,PageVO pageVo){
			Gson gson =new GsonBuilder().serializeNulls().create();
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("total", pageVo.getTotalRecord());
			result.put("rows", object);
			String jsonResult = gson.toJson(result);
			gson = null;
			result = null;
			return jsonResult;
		}
}
