package com.becoda.bkms.sys.platform.ucc;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 结果集合 代码集转换
 * 
 */
public class DataResult {
	private static final Logger log = Logger.getLogger(DataResult.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 私有构造方法
	 */
	private DataResult() {
	}

	/**
	 *  单条记录转换
	 * @param items 代码项
	 * @param title 待转换值
	 * @return
	 */
	public static String runDictionarySingle(Map<String, String> items, String title) {
		for (Map.Entry<String, String> entry : items.entrySet()) {
			if (title.equals(entry.getKey())) {
				return entry.getValue();
			}
		}
		return "";
	}
	
	/**
	 * List<Map>集合转换
	 * @param resultList 结果集
	 * @param items 代码项
	 * @param title 待转换值
	 */
	public static void runDictionaryList(List<Map<String, String>> resultList, Map<String, String> items, String title) {
		
		for (Map<String, String> node : resultList) {
			String key = String.valueOf(node.get(title));
			String value = items.get(key);
			if (value != null) {
				node.put(title, value);
			}else{
				node.put(title, "");
			}
		}
	}

}
