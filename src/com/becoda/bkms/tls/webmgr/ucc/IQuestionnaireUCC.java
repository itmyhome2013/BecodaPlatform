package com.becoda.bkms.tls.webmgr.ucc;

import com.becoda.bkms.tls.webmgr.pojo.bo.QuestionnaireBO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;

import java.util.List;
import java.util.Map;


public interface IQuestionnaireUCC {
	/**
	 * 保存信息
	 * 
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public String saveQuestionnaire(QuestionnaireBO bo)
			throws RollbackableException;

	/**
	 * 保存信息
	 * 
	 * @param bo
	 * @throws RollbackableException
	 */
	public void updateQuestionnaire(QuestionnaireBO bo)
			throws RollbackableException;

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @throws RollbackableException
	 */
	public void deleteQuestionnaire(String id) throws RollbackableException;

	/**
	 * 删除多条信息
	 * 
	 * @param id
	 * @throws RollbackableException
	 */
	public void deleteQuestionnaires(String[] id) throws RollbackableException;


	public List queryQuesByTime(String sur_start_date)
			throws RollbackableException;

	/**
	 * 查询所有信息
	 * 
	 * @return
	 * @throws RollbackableException
	 */
	public List queryQues() throws RollbackableException;

	public List queryQues(PageVO vo, String sur_start_date) throws RollbackableException;
	public Map queryOption(String sur_start_date) throws RollbackableException;
	public boolean checkSameName(String name) throws RollbackableException;

	/**
	 * 批量修改信息状态
	 * 
	 * @param ids
	 * @param status
	 * @throws RollbackableException
	 */
	public void updateAllStatus(String[] ids, String status)
			throws RollbackableException;

	public QuestionnaireBO queryQuesBySurID(String surid)
			throws RollbackableException;

	public void saveOptions(String surid, String[] contents, String special)
			throws RollbackableException;

	public void deleteOptions(String[] surid) throws RollbackableException;

	// 根据surid查询是否已经存在选项内容
	public int queryOptionsBySurID(String surid) throws RollbackableException;

	public List queryOptionsBySur(String surid) throws RollbackableException;

	public void saveQuesRight(String[] surid, String[] per_org_id, String type)
			throws RollbackableException;

	public List queryQuesRightByName(PageVO vo,String surid,String name,String type) throws RollbackableException;

//	public void deleteQuesRights(String[] ids) throws RollbackableException;

	public int queryQuesRightBySurID_PerID(String surid, String perid)
			throws RollbackableException;
	
//	public void deleteQuesRight(String id) throws RollbackableException;
	
	public void deleteRight(String[] surid,String[] perid) throws RollbackableException;
//  根据surid查询调查问卷结果表结果
	public List queryResult(String surid) throws RollbackableException;
//	 删除已有问卷的调查结果
	public void deleteResult(String[] surid) throws RollbackableException;
	// 根据surid查询调查问卷结果表其他选项结果
	public List queryResultOther(String surid) throws RollbackableException;
	// 发布前查询问卷选项是否为空
	public boolean checkIsNullOption(String[] surid)
			throws RollbackableException;
}

