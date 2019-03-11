package com.becoda.bkms.tls.webmgr.service;

//import com.becoda.bkms.tls.webmgr.pojo.bo.QuesRightBO;
import com.becoda.bkms.tls.webmgr.pojo.bo.QuestionnaireBO;
import com.becoda.bkms.tls.webmgr.dao.QuestionnaireDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;


import java.util.List;
import java.util.Map;


public class QuestionnaireService {
    private QuestionnaireDAO questionnaireDAO;



    public QuestionnaireDAO getQuestionnaireDAO() {
        return questionnaireDAO;
    }

    public void setQuestionnaireDAO(QuestionnaireDAO QuestionnaireDAO) {
        this.questionnaireDAO = QuestionnaireDAO;
    }

    public String saveQuestionnaire(QuestionnaireBO bo) throws RollbackableException {
        try {
            String id = questionnaireDAO.createBo(bo);
            return id;
        } catch (Exception e) {
            throw new RollbackableException("新增问卷调查信息失败", e, this.getClass());
        }
    }

    public void updateQuestionnaire(QuestionnaireBO bo) throws RollbackableException {
        try {
        	questionnaireDAO.updateBo(bo.getSur_id(), bo);
        } catch (Exception e) {
            throw new RollbackableException("修改问卷调查信息失败", e, this.getClass());
        }
    }

    public void deleteQuestionnaire(String id) throws RollbackableException {
        try {
            Object o = getQuestionnaireDAO().getHibernateTemplate().get(QuestionnaireBO.class, id);
            if (o != null) {
                getQuestionnaireDAO().getHibernateTemplate().delete(o);
            }
        } catch (Exception e) {
            throw new RollbackableException("删除问卷调查信息失败", e, this.getClass());
        }
    }

    /**
     * 删除信息
     *
     * @param ids
     * @throws RollbackableException
     */
    public void deleteQuestionnaires(String[] ids) throws RollbackableException {
        try {
            if (ids == null || ids.length == 0)
                return;
            for (int i = 0; i < ids.length; i++) {
                Object o = getQuestionnaireDAO().findBo(QuestionnaireBO.class, ids[i]);
                if (o != null) {
                    getQuestionnaireDAO().deleteBo(o);
                }
            }

        } catch (RollbackableException e) {
            throw new RollbackableException("删除问卷调查信息失败", e, this.getClass());
        }
    }
    
//  根据年度查询调查问卷信息
    public List queryQuesByTime(String sur_start_date) throws RollbackableException {
            return questionnaireDAO.queryQuesByTime(sur_start_date);
    }
	// 查询问卷表的选项
	public Map queryOption(String sur_start_date) throws RollbackableException {
		return questionnaireDAO.queryOption(sur_start_date);
	}

    public List queryQues() throws RollbackableException {
            return questionnaireDAO.queryQues();
       
    }
    
    public List queryQues(PageVO vo, String sur_start_date) throws RollbackableException {
            return questionnaireDAO.queryQues(vo, sur_start_date);
       
    }
    
    
	// 根据surid查询
	public QuestionnaireBO queryQuesBySurID(String surid) throws RollbackableException {
			
			return questionnaireDAO.queryQuesBySurID(surid);
		
	}
    public boolean checkSameName(String name) throws RollbackableException {
        
        String hq = "from QuestionnaireBO b where b.sur_name = '" + name.trim() + "'";
        
        List list = questionnaireDAO.getHibernateTemplate().find(hq);
        return !list.isEmpty();
    }

    //批量更新信息状态
    public void updateAllStatus(String[] ids, String status) throws RollbackableException {
            questionnaireDAO.updateAllStatus(ids, status);
        
    }
	// 发布前查询问卷选项是否为空
	public boolean checkIsNullOption(String[] surid)
			throws RollbackableException {
		return questionnaireDAO.checkIsNullOption(surid);
		
	}
    
	// 删除已有问卷的选项内容
	public void deleteOptions(String[] surid)
			throws RollbackableException {
			questionnaireDAO.deleteOptions(surid);
		
	}
	// 删除已有问卷的调查结果
	public void deleteResult(String[] surid) throws RollbackableException {
		questionnaireDAO.deleteResult(surid);
	}
	
	
	// 根据surid查询是否已经存在选项内容
	public int queryOptionsBySurID(String surid)
			throws RollbackableException {
			return questionnaireDAO.queryOptionsBySurID(surid);
		
	}
	
	// 根据surid查询是否已经存在选项内容
	public List queryOptionsBySur(String surid)
			throws RollbackableException {
			return questionnaireDAO.queryOptionsBySur(surid);
		
	}
	
    public List queryQuesRightByName(PageVO vo,String surid,String name,String type) throws RollbackableException {
            return questionnaireDAO.queryQuesRightByName(vo,surid,name,type);
       
    }
    
    
//    public void deleteQuesRight(String id) throws RollbackableException {
//        try {
//            Object o = getQuestionnaireDAO().getHibernateTemplate().get(QuesRightBO.class, id);
//            if (o != null) {
//                getQuestionnaireDAO().getHibernateTemplate().delete(o);
//            }
//        } catch (Exception e) {
//            throw new RollbackableException("删除问卷调查权限信息失败", e, this.getClass());
//        }
//    }

    /**
     * 删除信息
     *
     * @param ids
     * @throws RollbackableException
     */
//    public void deleteQuesRights(String[] ids) throws RollbackableException {
//        try {
//            if (ids == null || ids.length == 0)
//                return;
//            for (int i = 0; i < ids.length; i++) {
//                Object o = getQuestionnaireDAO().findBo(QuesRightBO.class, ids[i]);
//                if (o != null) {
//                    getQuestionnaireDAO().deleteBo(o);
//                }
//            }
//
//        } catch (RollbackableException e) {
//            throw new RollbackableException("删除问卷调查权限信息失败", e, this.getClass());
//        }
//    }
    
    
//  删除人员权限
	public void deleteRight(String[] surid,String[] perid) throws RollbackableException {
			questionnaireDAO.deleteRight(surid, perid);
	}
    
//  根据surid和人员ID查询是否已经存在相应的
    public int queryQuesRightBySurID_PerID(String surid,String perid) 
			throws RollbackableException {
			return questionnaireDAO.queryQuesRightBySurID_PerID(surid, perid);
		
	}
    
//  根据surid查询调查问卷结果表结果
	public List queryResult(String surid) throws RollbackableException {
		return questionnaireDAO.queryResult(surid);
	}
	// 根据surid查询调查问卷结果表其他选项结果
	public List queryResultOther(String surid) throws RollbackableException {
		return questionnaireDAO.queryResultOther(surid);
		
	}

}
