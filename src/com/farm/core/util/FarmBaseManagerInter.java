package com.farm.core.util;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;


/**
 * platForm门面类
 * 
 * @author huxuxu
 * 
 */
public interface FarmBaseManagerInter {
	

	/** 通过字段名获得数据字典的键值对儿
	 * @param index 字段
	 * @return map<类型,类型title>
	 */
	public Map<String, String> findDicTitleForIndex(String index) throws BkmsException;
	
	/**
	 * 根据parentid获取类别下的所有事项
	 * @param parentid
	 * @return
	 */
	public List<Map<String, Object>> findRuleItemOption(String parentid,String fieldName) throws BkmsException;
	
}
