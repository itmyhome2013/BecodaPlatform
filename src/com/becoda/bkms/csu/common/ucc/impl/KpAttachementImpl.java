package com.becoda.bkms.csu.common.ucc.impl;

import java.util.Date;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.csu.common.pojo.KpAttachement;
import com.becoda.bkms.csu.common.service.KpAttachementService;
import com.becoda.bkms.csu.common.ucc.IKpAttachementUCC;
import com.becoda.bkms.csu.suggest.pojo.KpOpinionFeedback;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.util.HrmsLog;

public class KpAttachementImpl implements IKpAttachementUCC {
	
	private KpAttachementService kpAttachementService;
	
	public KpAttachementService getKpAttachementService() {
		return kpAttachementService;
	}

	public void setKpAttachementService(KpAttachementService kpAttachementService) {
		this.kpAttachementService = kpAttachementService;
	}

	/**
	 * 保存附件
	 * @param entity
	 * @param user
	 * @throws RollbackableException
	 */
	public void addEntity(KpAttachement entity, User user)
			throws RollbackableException {
		entity.setCreateUserId(user.getUserId());
		entity.setCreateUserName(user.getName());
		kpAttachementService.addEntity(entity);
		HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "添加附件成功！");
	}

	/**
	 * 删除附件
	 * @param id
	 * @param user
	 * @throws RollbackableException
	 */
	public void deleteEntity(KpAttachement entity, User user) throws RollbackableException {
		// TODO Auto-generated method stub
		kpAttachementService.deleteEntity(entity);
		HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "附件删除成功！");
	}

	/**
	 * 批量删除附件
	 * @param ids
	 * @param user
	 * @throws RollbackableException
	 */
	public void deleteEntitys(String[] ids, User user)
			throws RollbackableException {
		kpAttachementService.deleteEntitys(ids);
	}

	/**
	 * 修改附件
	 * @param kpAttachement
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateEntity(KpAttachement entity, User user)
			throws RollbackableException {
		entity.setUpdateUserId(user.getUserId());
		entity.setUpdateUserName(user.getName());
		kpAttachementService.updateEntity(entity);
		HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "意见反馈修改成功！");
	}

	/**
	 * 修改附件 为附件添加关联，即添加关联外键
	 * @param ids 附件id
	 * @param foreignId 关联id
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateEntity(String[] ids, String foreignId, User user)
			throws RollbackableException {
		if(ids!=null&&ids.length>0) {
			for(int i=0; i<=ids.length; i++) {
				KpAttachement temp = kpAttachementService.findEntity(ids[i]);
				temp.setForeignId(foreignId);
				temp.setUpdateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				temp.setUpdateUserId(user.getUserId());
				temp.setUpdateUserName(user.getName());
				kpAttachementService.updateEntity(temp);
			}
		}
	}
	
	/**
	 * 查询附件
	 * @param id
	 * @param user
	 * @return
	 * @throws RollbackableException
	 */
	public KpAttachement findEntity(String id, User user)
			throws RollbackableException {
		KpAttachement temp = kpAttachementService.findEntity(id);
		return temp;
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
	public Map queryList(KpAttachement entity, int page , int rows, User user)
			throws RollbackableException {
		return kpAttachementService.queryList(entity, page, rows);
	}

}
