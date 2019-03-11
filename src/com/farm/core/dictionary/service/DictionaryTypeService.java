package com.farm.core.dictionary.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.farm.core.dictionary.bo.DictionaryTypeBO;
import com.farm.core.dictionary.dao.DictionaryTypeDAO;

public class DictionaryTypeService {
	private DictionaryTypeDAO dictionaryTypeDao;

	public DictionaryTypeDAO getDictionaryTypeDao() {
		return dictionaryTypeDao;
	}

	public void setDictionaryTypeDao(DictionaryTypeDAO dictionaryTypeDao) {
		this.dictionaryTypeDao = dictionaryTypeDao;
	}
	public void saveDictionaryType(DictionaryTypeBO dictionary) throws RollbackableException {
        try {
        	dictionaryTypeDao.getHibernateTemplate().save(dictionary);
        } catch (Exception e) {
            throw new RollbackableException(e, DictionaryTypeBO.class);
        }
    }
	
	public void updateDictionaryType(DictionaryTypeBO dictionary) throws RollbackableException {
        try {
        	dictionaryTypeDao.getHibernateTemplate().update(dictionary);
        } catch (Exception e) {
            throw new RollbackableException(e, DictionaryTypeBO.class);
        }
    }
	
	public void deleteDictionaryType(String ids) throws RollbackableException {
        try {
        	DictionaryTypeBO bo = (DictionaryTypeBO)dictionaryTypeDao.findBo(DictionaryTypeBO.class, ids);
        	dictionaryTypeDao.deleteBo(bo);
        } catch (Exception e) {
            throw new RollbackableException(e, DictionaryTypeBO.class);
        }
    }
	
	public DictionaryTypeBO getDictionaryType(String ids) throws RollbackableException {
		return (DictionaryTypeBO)dictionaryTypeDao.findBo(DictionaryTypeBO.class, ids);
	}
	
	public boolean checkIsHasDefault(String id){
		return dictionaryTypeDao.checkIsHasDefault(id);
	}
}
