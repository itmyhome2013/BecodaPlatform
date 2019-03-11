package com.farm.core.dictionary.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.farm.core.dictionary.bo.DictionaryBO;
import com.farm.core.sql.query.DataQuery;

public interface IDictionaryUCC {
	
	
	/**
	 * 创建查询
	 * @param query
	 * @return
	 */
	public DataQuery queryAll(DataQuery query);
	
	/**
	 * 保存
	 * @param dictionaryBO
	 * @throws RollbackableException
	 */
	public void saveDictionary(DictionaryBO dictionaryBO) throws RollbackableException;
	
	/**
	 * 修改
	 * @param dictionaryBO
	 * @throws RollbackableException
	 */
	public void updateDictionary(DictionaryBO dictionaryBO) throws RollbackableException;
	
	/**
	 * 删除
	 * @param ids
	 * @throws RollbackableException
	 */
	public void deleteDictionary(String ids) throws RollbackableException;
	
	/**
	 * 根据ID获得实体
	 * @param ids
	 */
	public DictionaryBO getDictionary(String ids) throws RollbackableException ;
	
}
