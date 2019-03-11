package com.becoda.bkms.csu.suggest.service;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.suggest.dao.KpOpinionFeedbackDAO;
import com.becoda.bkms.csu.suggest.pojo.KpOpinionFeedback;

/**
 * Created by IntelliJ IDEA.
 * User: yinhui
 * Date: 2016-9-20
 * Time: 10:30:53
 * To file upload
 */
public class KpOpinionFeedbackService {
	
	private KpOpinionFeedbackDAO kpOpinionFeedbackDAO;

	public KpOpinionFeedbackDAO getKpOpinionFeedbackDAO() {
		return kpOpinionFeedbackDAO;
	}

	public void setKpOpinionFeedbackDAO(KpOpinionFeedbackDAO kpOpinionFeedbackDAO) {
		this.kpOpinionFeedbackDAO = kpOpinionFeedbackDAO;
	}

	/**
	 * 保存意见反馈信息
	 * @param pojo
	 * @throws RollbackableException
	 */
    public void addKpOpinionFeedback(KpOpinionFeedback pojo) throws RollbackableException {
    	kpOpinionFeedbackDAO.addKpOpinionFeedback(pojo);
    }
    
    /**
	 * 修改意见反馈信息
	 * @param pojo
	 * @throws RollbackableException
	 */
    public void updateKpOpinionFeedback(KpOpinionFeedback pojo) throws RollbackableException {
    	kpOpinionFeedbackDAO.updateKpOpinionFeedback(pojo);
	}

    /**
	 * 查询意见反馈
	 * @param po
	 * @param page
	 * @param rows
	 * @return
	 * @throws RollbackableException
	 */
	public Map queryList(KpOpinionFeedback po, int page, int rows) throws RollbackableException {
		return kpOpinionFeedbackDAO.querySpecailList(po, page, rows);
	}

	/**
	 * 根据主键id获取意见实体对象
	 * @param id
	 * @throws RollbackableException
	 */
	public KpOpinionFeedback findKpOpinionFeedback(String id) throws RollbackableException {
		// TODO Auto-generated method stub
		return kpOpinionFeedbackDAO.findKpOpinionFeedback(id);
	}

	/**
	 * 删除反馈意见
	 * @param ids
	 */
	public void deleteEntitys(String[] ids) throws RollbackableException {
		String hql = "delete from KpOpinionFeedback ";
		String columnName = "opinionId";
		
		try {
			kpOpinionFeedbackDAO.deleteByIds(hql, columnName, ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
