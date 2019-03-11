package com.farm.core.dictionary.service;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.farm.core.dictionary.bo.DictionaryBO;
import com.farm.core.dictionary.bo.DictionaryTypeBO;
import com.farm.core.dictionary.dao.DictionaryDAO;
import com.farm.core.dictionary.dao.DictionaryTypeDAO;
import com.farm.core.sql.query.DataQuery;

public class DictionaryService {
	private DictionaryDAO dictionaryDao;
	private DictionaryTypeDAO dictionaryTypeDao;

	public DictionaryDAO getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDAO dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	
	public DataQuery queryAll(DataQuery query) {
		return dictionaryDao.queryAll(query);
	}
	public void saveDictionary(DictionaryBO dictionary) throws RollbackableException {
        try {
        	dictionaryDao.getHibernateTemplate().save(dictionary);
        } catch (Exception e) {
            throw new RollbackableException(e, DictionaryBO.class);
        }
    }
	
	public void updateDictionary(DictionaryBO dictionary) throws RollbackableException {
        try {
        	dictionaryDao.getHibernateTemplate().update(dictionary);
        } catch (Exception e) {
            throw new RollbackableException(e, DictionaryBO.class);
        }
    }
	
	public void deleteDictionary(String ids) throws RollbackableException {
        try {
        	DictionaryBO bo = (DictionaryBO)dictionaryDao.findBo(DictionaryBO.class, ids);
        	
        	List<DictionaryTypeBO> types = dictionaryTypeDao.findBoByEntity(ids);
        	//级联删除字典项
        	for(DictionaryTypeBO typebo : types){
        		dictionaryTypeDao.deleteBo(typebo);
        	}
        	
        	dictionaryDao.deleteBo(bo);
        } catch (Exception e) {
            throw new RollbackableException(e, DictionaryBO.class);
        }
    }
	
	public DictionaryBO getDictionary(String ids) throws RollbackableException {
		return (DictionaryBO)dictionaryDao.findBo(DictionaryBO.class, ids);
	}

	public DictionaryTypeDAO getDictionaryTypeDao() {
		return dictionaryTypeDao;
	}

	public void setDictionaryTypeDao(DictionaryTypeDAO dictionaryTypeDao) {
		this.dictionaryTypeDao = dictionaryTypeDao;
	}
	
}
