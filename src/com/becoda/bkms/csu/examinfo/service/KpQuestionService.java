package com.becoda.bkms.csu.examinfo.service;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.examinfo.dao.KpQuestionDAO;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 16:49:28
 * To change this template use File | Settings | File Templates.
 */
public class KpQuestionService {
    private KpQuestionDAO kpQuestionDAO;
   
    

    public KpQuestionDAO getKpQuestionDAO() {
		return kpQuestionDAO;
	}



	public void setKpQuestionDAO(KpQuestionDAO kpQuestionDAO) {
		this.kpQuestionDAO = kpQuestionDAO;
	}



	public List queryInfoByType(String type) throws RollbackableException {
        try {
            return kpQuestionDAO.queryInfoByType(type);
        } catch (Exception e) {
            throw new RollbackableException("根据信息类型查询培训信息失败", e, InformationBO.class);
        }
    }

  
}
