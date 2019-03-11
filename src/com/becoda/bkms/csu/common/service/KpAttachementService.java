package com.becoda.bkms.csu.common.service;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.common.dao.KpAttachementDAO;
import com.becoda.bkms.csu.common.pojo.KpAttachement;

/**
 * Created by IntelliJ IDEA.
 * User: yinhui
 * Date: 2016-9-20
 * Time: 10:30:53
 * To file upload
 */
public class KpAttachementService {
	
	private KpAttachementDAO kpAttachementDAO;

	public KpAttachementDAO getKpAttachementDAO() {
		return kpAttachementDAO;
	}

	public void setKpAttachementDAO(KpAttachementDAO kpAttachementDAO) {
		this.kpAttachementDAO = kpAttachementDAO;
	}
	
	/**
	 * 保存附件
	 * @param entity
	 * @param user
	 * @throws RollbackableException
	 */
    public void addEntity(KpAttachement pojo) throws RollbackableException {
    	kpAttachementDAO.addEntity(pojo);
    }

    /**
	 * 删除附件
	 * @param id
	 * @param user
	 * @throws RollbackableException
	 */
	public void deleteEntity(KpAttachement entity) throws RollbackableException {
		kpAttachementDAO.deleteEntity(entity);
	}

	/**
	 * 批量删除附件
	 * @param ids
	 * @param user
	 * @throws RollbackableException
	 */
	public void deleteEntitys(String[] ids) throws RollbackableException {
		String hql = "delete from KpAttachement ";
		String columnName = "fileId";
		
		try {
			kpAttachementDAO.deleteByIds(hql, columnName, ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改附件
	 * @param kpAttachement
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateEntity(KpAttachement entity) throws RollbackableException {
		kpAttachementDAO.updateEntity(entity);
	}

	/**
	 * 查询附件
	 * @param id
	 * @param user
	 * @return
	 * @throws RollbackableException
	 */
	public KpAttachement findEntity(String id) throws RollbackableException {
		return kpAttachementDAO.findEntity(id);
	}

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
	public Map queryList(KpAttachement entity, int page , int rows) throws RollbackableException {
		return kpAttachementDAO.queryList(entity, page, rows);
	}
}
