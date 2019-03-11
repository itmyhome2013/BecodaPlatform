package com.becoda.bkms.csu.common.ucc;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.csu.common.pojo.KpAttachement;

/**
 * 附件上传
 * User: yinhui
 * Date: 2016-9-20
 * Time: 15:44:28
 */
public interface IKpAttachementUCC {

	/**
	 * 保存附件
	 * @param entity
	 * @param user
	 * @throws RollbackableException
	 */
	public void addEntity(KpAttachement entity, User user) throws RollbackableException;
	
	/**
	 * 删除附件
	 * @param id
	 * @param user
	 * @throws RollbackableException
	 */
	public void deleteEntity(KpAttachement entity, User user) throws RollbackableException;
	
	/**
	 * 批量删除附件
	 * @param ids
	 * @param user
	 * @throws RollbackableException
	 */
	public void deleteEntitys(String[] ids, User user) throws RollbackableException;
	
	/**
	 * 修改附件
	 * @param kpAttachement
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateEntity(KpAttachement entity, User user) throws RollbackableException;
	
	/**
	 * 修改附件 为附件添加关联，即添加关联外键
	 * @param ids 附件id
	 * @param foreignId 关联id
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateEntity(String[] ids, String foreignId, User user) throws RollbackableException;
	
	/**
	 * 查询附件
	 * @param id
	 * @param user
	 * @return
	 * @throws RollbackableException
	 */
	public KpAttachement findEntity(String id, User user) throws RollbackableException;
	
	/**
	 * 查询附件
	 * @param entity
	 * @param page
	 * @param rows
	 * @param user
	 * @return
	 * @throws RollbackableException
	 */
	@SuppressWarnings("rawtypes")
	public Map queryList(KpAttachement entity, int page , int rows, User user) throws RollbackableException;
}
