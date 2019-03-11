package com.becoda.bkms.csu.common.dao;

import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.common.pojo.KpAttachement;

/**
 * Created by IntelliJ IDEA.
 * User: yinhui
 * Date: 2016-9-20
 * Time: 10:30:53
 * To save file information
 */
public class KpAttachementDAO extends GenericDAO {

	/**
	 * 保存附件
	 * @param entity
	 * @param user
	 * @throws RollbackableException
	 */
    public void addEntity(KpAttachement pojo) throws RollbackableException {
        this.createBo(pojo);
    }
    
    /**
	 * 删除附件
	 * @param id
	 * @param user
	 * @throws RollbackableException
	 */
	public void deleteEntity(KpAttachement entity) throws RollbackableException {
		this.deleteBo(entity);
	}

	/**
	 * 修改附件
	 * @param kpAttachement
	 * @param user
	 * @throws RollbackableException
	 */
	public void updateEntity(KpAttachement entity) throws RollbackableException {
		this.updateBo(entity.getFileId(), entity);
	}

	/**
	 * 查询附件
	 * @param id
	 * @param user
	 * @return
	 * @throws RollbackableException
	 */
	public KpAttachement findEntity(String id) throws RollbackableException{
		return (KpAttachement) this.findBo(KpAttachement.class, id);
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
	public Map queryList(KpAttachement entity, int page , int rows) throws RollbackableException {
		try {
            StringBuffer hql = new StringBuffer();
            StringBuffer count = new StringBuffer();
            hql.append("from KpAttachement t where 1=1 ");
            count.append("select count(t) from KpAttachement t where 1=1 ");
            if(entity.getFileId()!=null&&!"".equals(entity.getFileId())){  //主键
            	hql.append(" and fileId like '%").append(entity.getFileId()).append("%'");
            	count.append(" and fileId like '%").append(entity.getFileId()).append("%'");
            }
            if(entity.getFileName()!=null&&!"".equals(entity.getFileName())){  //主键
            	hql.append(" and fileName like '%").append(entity.getFileName()).append("%'");
            	count.append(" and fileName like '%").append(entity.getFileName()).append("%'");
            }
            if(entity.getFileType()!=null&&!"".equals(entity.getFileType())){  //主键
            	hql.append(" and fileType like '%").append(entity.getFileType()).append("%'");
            	count.append(" and fileType like '%").append(entity.getFileType()).append("%'");
            }
            String order = " order by t.createTime desc ";
            return pageQueryByEsayUI(page,rows, count.toString(), hql.append(order).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, KpAttachementDAO.class);
        }
	}

}