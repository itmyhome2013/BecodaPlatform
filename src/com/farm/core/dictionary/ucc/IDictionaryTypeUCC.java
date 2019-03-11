package com.farm.core.dictionary.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.farm.core.dictionary.bo.DictionaryTypeBO;

public interface IDictionaryTypeUCC {
	
	/**
	 * 保存
	 * @param dictionaryTypeBO
	 * @throws RollbackableException
	 */
	public void saveDictionaryType(DictionaryTypeBO dictionaryTypeBO) throws RollbackableException;
	
	/**
	 * 修改
	 * @param dictionaryTypeBO
	 * @throws RollbackableException  
	 */
	public void updateDictionaryType(DictionaryTypeBO dictionaryTypeBO) throws RollbackableException;
	
	/**
	 * 删除
	 * @param ids
	 * @throws RollbackableException
	 */
	public void deleteDictionaryType(String ids) throws RollbackableException;
	
	/**
	 * 根据ID获得实体
	 * @param ids
	 */
	public DictionaryTypeBO getDictionaryType(String ids) throws RollbackableException ;
	
	/**
	 * 检查是否已存在默认选项
	 * @param id
	 * @return
	 */
	public boolean checkIsHasDefault(String id);
}
