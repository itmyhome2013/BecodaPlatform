package com.becoda.bkms.online.info.service;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.online.info.dao.InfoDAO;
import com.becoda.bkms.online.info.pojo.bo.InfoBo;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;

public class InfoService {

	private InfoDAO infoDAO;

	private GenericDAO genericDAO;

	public InfoDAO getInfoDAO() {
		return infoDAO;
	}

	public void setInfoDAO(InfoDAO infoDAO) {
		this.infoDAO = infoDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	public InfoBo getInfoById(String id) throws RollbackableException {
        try {
            List list;
             list=infoDAO.getHibernateTemplate().find("from InfoBo b where b.id = '" + id + "'");
             if (list==null || list.size() == 0)
                return null;
            else
                return (InfoBo)list.get(0);
        } catch (Exception e) {
            throw new RollbackableException("获得建议征集失败", e, InfoBo.class);
        }
    }
	
	public boolean checkSameName(String name, String id) throws RollbackableException {
        String hq = "";
        if (id == null || "".equals(id)) {
            hq = "from InfoBo b where b.title = '" + name.trim() + "' ";
        } else {
            hq = "from InfoBo b where b.title = '" + name.trim() + "' and b.id <> '" + id + "'";
        }
        List list = infoDAO.getHibernateTemplate().find(hq);
        return !list.isEmpty();
    }
	
	public void deleteInformation(String id) throws RollbackableException {
        try {
            Object o = infoDAO.getHibernateTemplate().get(InfoBo.class, id);
            if (o != null) {
            	infoDAO.getHibernateTemplate().delete(o);
            }
        } catch (Exception e) {
            throw new RollbackableException("删除建议征集失败", e, this.getClass());
        }
    }
	
	public void deleteInformation(String[] ids) throws RollbackableException {
        try {
            if (ids == null || ids.length == 0)
                return;
            for (int i = 0; i < ids.length; i++) {
                Object o = infoDAO.findBo(InfoBo.class, ids[i]);
                if (o != null) {
                	infoDAO.deleteBo(o);
                }
            }

        } catch (RollbackableException e) {
            throw new RollbackableException("删除建议征集失败", e, this.getClass());
        }
    }
	
	public String saveInfomation(InfoBo bo) throws RollbackableException {
        try {
            String id = infoDAO.createBo(bo);
            return id;
        } catch (Exception e) {
            throw new RollbackableException("新增建议征集失败", e, this.getClass());
        }
    }
	
	public void updateInfomation(InfoBo bo) throws RollbackableException {
        try {
        	infoDAO.updateBo(bo.getSubId(), bo);
        } catch (Exception e) {
            throw new RollbackableException("修改建议征集失败", e, this.getClass());
        }
    }
	
	//批量更新信息状态
    public void updateAllStatus(String[] ids, String col ,String value) throws RollbackableException {
        try {
        	infoDAO.updateAllStatus(ids, col,value);
        } catch (Exception e) {
            throw new RollbackableException("批量建议征集状态", e, InformationBO.class);
        }
    }
    
  //根据主键获得信息集合
    public List queryInfo(String[] ids) throws RollbackableException {
        try {
            String strid = "";
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    if (i == 0) {
                        strid += "'" + ids[i] + "'";
                    } else {
                        strid += ",'" + ids[i] + "'";
                    }
                }
            }
            String hql = "from InfoBo b where b.id in(" + strid + ")";
            List list = infoDAO.getHibernateTemplate().find(hql);
            return list;
        } catch (Exception e) {
            throw new RollbackableException("批量建议征集状态", e, InformationBO.class);
        }
    }
}
