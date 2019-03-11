package com.becoda.bkms.csu.suggest.ucc.impl;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.csu.suggest.pojo.KpOpinionFeedback;
import com.becoda.bkms.csu.suggest.service.KpOpinionFeedbackService;
import com.becoda.bkms.csu.suggest.ucc.IKpOpinionFeedbackUCC;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.util.HrmsLog;

/**
 * 
 * @author yinhui
 *
 */
public class KpOpinionFeedbackImpl implements IKpOpinionFeedbackUCC {
	
	private KpOpinionFeedbackService kpOpinionFeedbackService;
	
	public KpOpinionFeedbackService getKpOpinionFeedbackService() {
		return kpOpinionFeedbackService;
	}

	public void setKpOpinionFeedbackService(
			KpOpinionFeedbackService kpOpinionFeedbackService) {
		this.kpOpinionFeedbackService = kpOpinionFeedbackService;
	}

	/**
	 * 添加意见反馈
	 * @param pojo
	 * @param user
	 * @throws RollbackableException
	 */
	public void addKpOpinionFeedback(KpOpinionFeedback pojo, User user)
			throws RollbackableException {
		pojo.setCreateUserId(user.getUserId());
		pojo.setCreateUserName(user.getName());
		kpOpinionFeedbackService.addKpOpinionFeedback(pojo);
		HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "意见反馈保存成功！");
	}
	
	/**
	 * 修改意见反馈
	 * @param pojo
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateKpOpinionFeedback(KpOpinionFeedback pojo, User user)
			throws RollbackableException {
		pojo.setUpdateUserId(user.getUserId());
		pojo.setUpdateUserName(user.getName());
		kpOpinionFeedbackService.updateKpOpinionFeedback(pojo);
		HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "意见反馈修改成功！");
	}

	/**
	 * 根据主键id获取意见实体对象
	 * @param id
	 * @throws RollbackableException
	 */
	public KpOpinionFeedback findKpOpinionFeedback(String id, User user) throws RollbackableException {
		KpOpinionFeedback temp = kpOpinionFeedbackService.findKpOpinionFeedback(id);
		return temp;
	}
	
	/**
	 * 查询意见反馈
	 * @param po
	 * @param page
	 * @param rows
	 * @return
	 * @throws RollbackableException
	 */
	public Map queryList(KpOpinionFeedback po, int page, int rows, User user)
			throws RollbackableException {
		Map map = kpOpinionFeedbackService.queryList(po,page,rows);
		return map;
	}

	/**
	 * 删除反馈意见
	 * @param ids
	 * @throws RollbackableException 
	 */
	public void deleteEntitys(String[] ids) throws RollbackableException {
		kpOpinionFeedbackService.deleteEntitys(ids);
	}

}