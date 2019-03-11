package com.becoda.bkms.csu.suggest.dao;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.suggest.pojo.KpOpinionFeedback;
import com.becoda.bkms.util.BkmsContext;

/**
 * Created by IntelliJ IDEA.
 * User: yinhui
 * Date: 2016-10-08
 * Time: 17:30:53
 * To save file information
 */
public class KpOpinionFeedbackDAO extends GenericDAO {

	/**
	 * 保存意见反馈信息
	 * @param pojo
	 * @throws RollbackableException
	 */
    public void addKpOpinionFeedback(KpOpinionFeedback pojo) throws RollbackableException {
        this.createBo(pojo);
    }
    
    /**
	 * 修改意见反馈信息
	 * @param pojo
	 * @throws RollbackableException
	 */
    public void updateKpOpinionFeedback(KpOpinionFeedback pojo) throws RollbackableException {
    	this.updateBo(pojo.getOpinionId(), pojo);
	}

    /**
	 * 查询意见反馈
	 * @param po
	 * @param page
	 * @param rows
	 * @return
	 * @throws RollbackableException
	 */
	public Map querySpecailList(KpOpinionFeedback po, int page, int rows) throws RollbackableException {
		try {
            StringBuffer hql = new StringBuffer();
            StringBuffer count = new StringBuffer();
            hql.append("from KpOpinionFeedback t where 1=1 ");
            count.append("select count(t) from KpOpinionFeedback t where 1=1 ");
            if(po.getOpinionId()!=null&&!"".equals(po.getOpinionId())){  //主键
            	hql.append(" and opinionId like '%").append(po.getOpinionId()).append("%'");
            	count.append(" and opinionId like '%").append(po.getOpinionId()).append("%'");
            }
            if(po.getForeignId()!=null&&!"".equals(po.getForeignId())){  //外键
            	hql.append(" and foreignId like '%").append(po.getForeignId()).append("%'");
            	count.append(" and foreignId like '%").append(po.getForeignId()).append("%'");
            }
            if(po.getType()!=null&&!"".equals(po.getType())){  //反馈意见类型
            	hql.append(" and type = '").append(po.getType()).append("'");
            	count.append(" and type = '").append(po.getType()).append("'");
            }
            if(po.getContent()!=null&&!"".equals(po.getContent())){  //反馈意见内容
            	hql.append(" and content like '%").append(po.getContent()).append("%'");
            	count.append(" and content like '%").append(po.getContent()).append("%'");
            }
            String order = " order by t.createTime  ";
            return pageQueryByEsayUI(page,rows, count.toString(), hql.append(order).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, KpOpinionFeedbackDAO.class);
        }
	}

	/**
	 * 根据主键id获取意见实体对象
	 * @param id
	 * @throws RollbackableException
	 */
	public KpOpinionFeedback findKpOpinionFeedback(String id) throws RollbackableException {
		return (KpOpinionFeedback) this.findBo(KpOpinionFeedback.class, id);
	}

	/**
	 * 删除反馈意见
	 * @param ids
	 */
	public int deleteEntitys(String hql,String columnName,String ids[])throws BkmsException{
		for (int i = 0; i < ids.length; i++) {
			if(i==0) {
                hql += " where "+columnName+" = '"+ids[i]+"'";
            } else {
                hql += " or " + columnName+" = '"+ids[i]+"'";
            }
		}
		SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
		Session session = sf.getCurrentSession();
		Query q= session.createQuery(hql);
		int count = q.executeUpdate();
		session.flush();
		session.clear();
        return count;
	}

}