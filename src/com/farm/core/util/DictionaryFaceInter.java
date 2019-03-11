package com.farm.core.util;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;

/**
 * 数据字典的门面类
 * 
 * 增加获得树的类型对象由类型ID
 * 
 */
interface DictionaryFaceInter {
	/**
	 * 通过字段名获得数据字典的键值对儿
	 * 
	 * @param index
	 *            字段
	 * @return map<类型,类型title>
	 */
	public Map<String, String> findTitleForIndex(String index) throws BkmsException;
	
	public Map<String, String> getMapKey(List<Map<String, String>> list);

	
	/**
	 * 根据parentid获取类别下的所有事项
	 * @param parentid
	 * @return
	 */
	public List<Map<String, Object>> findRuleItemOption(String parentid,String fieldName) throws BkmsException;
	
	/**
	 * 通过字段名获得数据字典的键值对儿 有序
	 * 
	 * @param index
	 *            字段
	 * @return String[类型,类型title,sort]
	 */
	public List<Map<String, String>> findTitleForIndeHasSort(String index) throws BkmsException;
}
