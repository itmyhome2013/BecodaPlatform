package com.farm.core.util;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;

public class FarmManager implements FarmBaseManagerInter {
	private static final FarmManager alonemanager = new FarmManager();

	public static FarmManager instance() {
		return alonemanager;
	}

	public Map<String, String> findDicTitleForIndex(String index) throws BkmsException {
		return getDictionaryFace().findTitleForIndex(index);
	}
	
	public Map<String, String> getMapKey(List<Map<String, String>> list) throws BkmsException {
		return getDictionaryFace().getMapKey(list);
	}
	
	private DictionaryFaceInter getDictionaryFace() throws BkmsException {
		return (DictionaryFaceInter) BkmsContext.getBean("dictionaryManager");
	}
	
	@Override
	public List<Map<String, Object>> findRuleItemOption(String parentid,String fieldName) throws BkmsException {
		return getDictionaryFace().findRuleItemOption(parentid,fieldName);
	}
}
