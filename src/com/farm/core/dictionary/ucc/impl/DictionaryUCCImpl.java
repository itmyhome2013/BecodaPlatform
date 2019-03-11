package com.farm.core.dictionary.ucc.impl;

import java.io.Serializable;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.util.Tools;
import com.farm.core.dictionary.bo.DictionaryBO;
import com.farm.core.dictionary.service.DictionaryService;
import com.farm.core.dictionary.ucc.IDictionaryUCC;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.util.GenerateRandom;

public class DictionaryUCCImpl implements IDictionaryUCC, Serializable {
	private DictionaryService dictionaryService;

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
//	@Override
	public DataQuery queryAll(DataQuery query) {
		// TODO Auto-generated method stub
		return dictionaryService.queryAll(query);
	}

	public void saveDictionary(DictionaryBO dictionaryBO) throws RollbackableException {
		dictionaryBO.setId(GenerateRandom.generateRandomArray(6));
		dictionaryBO.setUtime(Tools.getSysDate("yyyy-MM-dd HH:mm:ss")); //修改时间
		dictionaryService.saveDictionary(dictionaryBO);
	}
	
	public void updateDictionary(DictionaryBO dictionaryBO) throws RollbackableException {
		dictionaryBO.setUtime(Tools.getSysDate("yyyy-MM-dd HH:mm:ss")); //修改时间
		dictionaryService.updateDictionary(dictionaryBO);
	}

	public DictionaryBO getDictionary(String ids) throws RollbackableException {
		return dictionaryService.getDictionary(ids);
	}

	public void deleteDictionary(String ids) throws RollbackableException {
		dictionaryService.deleteDictionary(ids);
	}
}
