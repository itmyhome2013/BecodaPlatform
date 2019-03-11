package com.becoda.bkms.csu.suggest.ucc;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.csu.suggest.pojo.KpOpinionFeedback;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;

/**
 * 意见反馈
 * User: yinhui
 * Date: 2016-9-20
 * Time: 15:44:28
 */
public interface IKpOpinionFeedbackUCC {

	/**
	 * 添加意见反馈
	 * @param pojo
	 * @param user
	 * @throws RollbackableException
	 */
	public void addKpOpinionFeedback(KpOpinionFeedback pojo, User user) throws RollbackableException;
	
	/**
	 * 修改意见反馈
	 * @param pojo
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateKpOpinionFeedback(KpOpinionFeedback pojo, User user) throws RollbackableException;
	
	/**
	 * 根据主键id获取意见实体对象
	 * @param id
	 * @throws RollbackableException
	 */
	public KpOpinionFeedback findKpOpinionFeedback(String id, User user) throws RollbackableException;
	
	/**
	 * 查询意见反馈
	 * @param po
	 * @param page
	 * @param rows
	 * @return
	 * @throws RollbackableException
	 */
	@SuppressWarnings("rawtypes")
	public Map queryList(KpOpinionFeedback po, int page , int rows, User user)throws RollbackableException;

	/**
	 * 删除反馈意见
	 * @param ids
	 * @throws RollbackableException 
	 */
	public void deleteEntitys(String[] ids) throws RollbackableException; 
	
}
