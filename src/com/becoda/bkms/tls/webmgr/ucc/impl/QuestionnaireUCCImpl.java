package com.becoda.bkms.tls.webmgr.ucc.impl;

import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.tls.webmgr.ucc.IQuestionnaireUCC;
import com.becoda.bkms.tls.webmgr.pojo.bo.QuestionnaireBO;
import com.becoda.bkms.tls.webmgr.service.QuestionnaireService;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;

import java.util.List;
import java.util.Map;


public class QuestionnaireUCCImpl implements IQuestionnaireUCC {
	private QuestionnaireService questionnaireservice;

	private ActivePageService activePageService;

	public ActivePageService getActivePageService() {
		return activePageService;
	}

	public void setActivePageService(ActivePageService activePageService) {
		this.activePageService = activePageService;
	}

	public QuestionnaireService getQuestionnaireService() {
		return questionnaireservice;
	}

	public void setQuestionnaireservice(QuestionnaireService service) {
		this.questionnaireservice = service;
	}

	public String saveQuestionnaire(QuestionnaireBO bo)
			throws RollbackableException {
		return questionnaireservice.saveQuestionnaire(bo);
	}

	public void updateQuestionnaire(QuestionnaireBO bo)
			throws RollbackableException {
		questionnaireservice.updateQuestionnaire(bo);
	}

	public void deleteQuestionnaire(String id) throws RollbackableException {
		questionnaireservice.deleteQuestionnaire(id);
	}

	public void deleteQuestionnaires(String[] id) throws RollbackableException {
		try{
		questionnaireservice.deleteQuestionnaires(id);
		questionnaireservice.deleteOptions(id);
		questionnaireservice.deleteRight(id, null);
		questionnaireservice.deleteResult(id);
		
		} catch (Exception e) {
			throw new RollbackableException("删除问卷调查失败", e,
					this.getClass());
		}
	}

	//
	// public InformationBO findInfoById(String id) throws RollbackableException
	// {
	// return service.getInfoById(id);
	// }
	////根据年度查询调查问卷信息
	public List queryQuesByTime(String sur_start_date)
			throws RollbackableException {
		return questionnaireservice.queryQuesByTime(sur_start_date);
	}

	public List queryQues() throws RollbackableException {
		return questionnaireservice.queryQues();
	}

	public List queryQues(PageVO vo, String sur_start_date) throws RollbackableException {
		return questionnaireservice.queryQues(vo, sur_start_date);
	}
	
	// 查询问卷表的选项
	public Map queryOption(String sur_start_date) throws RollbackableException {
		return questionnaireservice.queryOption(sur_start_date);
	}
	public QuestionnaireBO queryQuesBySurID(String surid)
			throws RollbackableException {
			return questionnaireservice.queryQuesBySurID(surid);
		
	}

	public boolean checkSameName(String name) throws RollbackableException {
		return questionnaireservice.checkSameName(name);
	}

	public void updateAllStatus(String[] ids, String status)
			throws RollbackableException {
		questionnaireservice.updateAllStatus(ids, status);
	}
	
	// 发布前查询问卷选项是否为空
	public boolean checkIsNullOption(String[] surid)
			throws RollbackableException {
		return questionnaireservice.checkIsNullOption(surid);
		
	}

	public void saveOptions(String surid, String[] contents, String special)
			throws RollbackableException {
		String[] sql = null;
		String opt_id = null;
		if (special != null) {
			sql = new String[contents.length + 1];
			opt_id = SequenceGenerator.getUUID();
			sql[contents.length] = "INSERT INTO TLS_SURVEY_OPTION(OPT_ID,SUR_ID, OPT_NO, OPT_NAME, OPT_TYPE)"
					+ "values('"
					+ opt_id
					+ "','"
					+ surid
					+ "','"
					+ (contents.length+1)
					+ "','"
					+ special + "','" + 2 + "')";
		} else
			sql = new String[contents.length];

		for (int i = 0; i < contents.length; i++) {
			opt_id = SequenceGenerator.getUUID();
			int opt_no = i + 1;
			sql[i] = "INSERT INTO TLS_SURVEY_OPTION(OPT_ID,SUR_ID, OPT_NO, OPT_NAME, OPT_TYPE)"
					+ "values('"
					+ opt_id
					+ "','"
					+ surid
					+ "','"
					+ opt_no
					+ "','" + contents[i] + "','" + 1 + "')";

		}

		activePageService.batchExecuteSql(sql);

	}

	// 删除已有问卷的选项内容
	public void deleteOptions(String[] surid) throws RollbackableException {
		try {
			questionnaireservice.deleteOptions(surid);

		} catch (Exception e) {
			throw new RollbackableException("删除已有问卷的选项内容失败", e,
					this.getClass());
		}
	}
//	 删除已有问卷的调查结果
	public void deleteResult(String[] surid) throws RollbackableException {
		try {
			questionnaireservice.deleteResult(surid);

		} catch (Exception e) {
			throw new RollbackableException("删除已有问卷的结果内容失败", e,
					this.getClass());
		}
	}

	// 根据surid查询是否已经存在选项内容
	public int queryOptionsBySurID(String surid) throws RollbackableException {
			return questionnaireservice.queryOptionsBySurID(surid);
	
	}

	// 根据surid查询是否已经存在选项内容
	public List queryOptionsBySur(String surid) throws RollbackableException {
			return questionnaireservice.queryOptionsBySur(surid);
		
	}
	//增加人员权限
	public void saveQuesRight(String[] surid, String[] per_org_id, String type)
			throws RollbackableException {
		
		try{
		questionnaireservice.deleteRight(surid, per_org_id);
		String[] sql = new String[per_org_id.length];
		String r_id = null;
			for (int i = 0; i < per_org_id.length; i++) {
				r_id = SequenceGenerator.getUUID();
				sql[i] = "INSERT INTO TLS_VOTE_RIGHT(R_ID,R_TYPE, SUR_ID, PER_ORG_ID)"
						+ "values('"
						+ r_id
						+ "','"
						+ type
						+ "','"
						+ surid[0]
						+ "','" + per_org_id[i] + "')";

			}

		activePageService.batchExecuteSql(sql);
		} catch (Exception e) {
			throw new RollbackableException("增加人员权限信息失败", e,
					this.getClass());
		}

	}

	public List queryQuesRightByName(PageVO vo,String surid,String name,String type) throws RollbackableException {
		return questionnaireservice.queryQuesRightByName(vo,surid,name,type);
	}
	
	// 删除已有问卷的选项内容
//	public void deleteQuesRight(String id) throws RollbackableException {
//		try {
//			questionnaireservice.deleteQuesRight(id);
//
//		} catch (Exception e) {
//			throw new RollbackableException("删除问卷调查权限信息失败", e,
//					QuestionnaireBO.class);
//		}
//	}
	
	// 删除已有问卷的选项内容
//	public void deleteQuesRights(String[] ids) throws RollbackableException {
//		try {
//			questionnaireservice.deleteQuesRights(ids);
//
//		} catch (Exception e) {
//			throw new RollbackableException("删除问卷调查权限信息失败", e,
//					QuestionnaireBO.class);
//		}
//	}
	
	
//  删除人员权限
	public void deleteRight(String[] surid,String[] perid) throws RollbackableException {
		try {
			questionnaireservice.deleteRight(surid, perid);
			
		} catch (Exception e) {
			throw new RollbackableException("删除人员权限失败", e,
					this.getClass());
		}
	}
	
//  根据surid和人员ID查询是否已经存在相应的
    public int queryQuesRightBySurID_PerID(String surid,String perid) 
			throws RollbackableException {
			return questionnaireservice.queryQuesRightBySurID_PerID(surid, perid);
		
	}
//  根据surid查询调查问卷结果表结果
	public List queryResult(String surid) throws RollbackableException {
		return questionnaireservice.queryResult(surid);
	}
	
	// 根据surid查询调查问卷结果表其他选项结果
	public List queryResultOther(String surid) throws RollbackableException {
		return questionnaireservice.queryResultOther(surid);
		
	}
}