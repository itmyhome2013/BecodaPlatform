package com.farm.core.dictionary.ucc.impl;

import java.io.Serializable;

import com.becoda.bkms.common.exception.RollbackableException;
import com.farm.core.dictionary.bo.DictionaryTypeBO;
import com.farm.core.dictionary.service.DictionaryTypeService;
import com.farm.core.dictionary.ucc.IDictionaryTypeUCC;
import com.farm.core.util.GenerateRandom;

public class DictionaryTypeUCCImpl implements IDictionaryTypeUCC, Serializable  {
	private DictionaryTypeService dictionaryTypeService;

	public DictionaryTypeService getDictionaryTypeService() {
		return dictionaryTypeService;
	}

	public void setDictionaryTypeService(DictionaryTypeService dictionaryTypeService) {
		this.dictionaryTypeService = dictionaryTypeService;
	}

	public void saveDictionaryType(DictionaryTypeBO dictionaryTypeBO)
			throws RollbackableException {
		
		//插入字典类型实体
		if (dictionaryTypeBO.getParentid() == null
				|| dictionaryTypeBO.getParentid().trim().length() <= 0) {
			dictionaryTypeBO.setParentid("NONE");
		}
		
		dictionaryTypeBO.setId(GenerateRandom.generateRandomArray(6));
		dictionaryTypeService.saveDictionaryType(dictionaryTypeBO);
		
		
		// 获取新增后的实体ID，并修改树索引码
		DictionaryTypeBO fatherEntity = getDictionaryType(dictionaryTypeBO.getParentid());
		if (fatherEntity == null) {
			dictionaryTypeBO.setTreecode(dictionaryTypeBO.getId());
		} else {
			dictionaryTypeBO.setTreecode(fatherEntity.getTreecode() + dictionaryTypeBO.getId());
		}
		dictionaryTypeService.updateDictionaryType(dictionaryTypeBO);
		
	}

	public void updateDictionaryType(DictionaryTypeBO dictionaryTypeBO)
			throws RollbackableException {  
		dictionaryTypeService.updateDictionaryType(dictionaryTypeBO);
	}

	public DictionaryTypeBO getDictionaryType(String ids) throws RollbackableException {
		return dictionaryTypeService.getDictionaryType(ids);
	}
	
	public void deleteDictionaryType(String ids) throws RollbackableException {
		dictionaryTypeService.deleteDictionaryType(ids);
	}

//	@Override
	public boolean checkIsHasDefault(String id) {
		return dictionaryTypeService.checkIsHasDefault(id);
	}
}
