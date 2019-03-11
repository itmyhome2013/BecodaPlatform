package com.becoda.bkms.csu.examinfo.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.csu.examinfo.pojo.KpQuestion;
import com.becoda.bkms.csu.examinfo.service.KpQuestionService;
import com.becoda.bkms.csu.examinfo.ucc.KpQuestionUCC;
import com.becoda.bkms.tls.webmgr.service.InformationService;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 16:44:57
 */
public class KpQuestionUCCImpl implements KpQuestionUCC {
    private KpQuestionService service;


    

    public KpQuestionService getService() {
		return service;
	}




	public void setService(KpQuestionService service) {
		this.service = service;
	}




	public Map queryInfoByType(Integer page,Integer rows,KpQuestion kpQuestion) throws RollbackableException {
        StringBuffer hql = new StringBuffer("from KpQuestion i where 1=1 ");
        if (kpQuestion != null && kpQuestion.getLessonName().trim().length() > 0) {
            hql.append(" and i.lessonName like '%").append(kpQuestion.getLessonName()).append("%'");
        }
        String countHql = "select count(i.questionId) " + hql.toString();
        //hql.append(" order by i.isTop desc,i.infoIsBanner desc ,i.createTime desc");
        return service.getKpQuestionDAO().pageQueryByEsayUI(page,rows, countHql, hql.toString());
    }
    
    
}
