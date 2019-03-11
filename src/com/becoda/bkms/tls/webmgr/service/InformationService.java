package com.becoda.bkms.tls.webmgr.service;

import com.becoda.bkms.tls.webmgr.pojo.vo.InformationVO;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;
import com.becoda.bkms.tls.webmgr.dao.InformationDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.dao.HrmsJdbcTemplate;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 16:49:28
 * To change this template use File | Settings | File Templates.
 */
public class InformationService {
    private InformationDAO informationDAO;
   
    public InformationDAO getInformationDAO() {
        return informationDAO;
    }

    public void setInformationDAO(InformationDAO informationDAO) {
        this.informationDAO = informationDAO;
    }

    public String saveInfomation(InformationBO bo) throws RollbackableException {
        try {
            String id = informationDAO.createBo(bo);
            return id;
        } catch (Exception e) {
            throw new RollbackableException("新增培训信息失败", e, this.getClass());
        }
    }

    public void updateInfomation(InformationBO bo) throws RollbackableException {
        try {
            informationDAO.updateBo(bo.getId(), bo);
        } catch (Exception e) {
            throw new RollbackableException("修改培训信息失败", e, this.getClass());
        }
    }

    public void deleteInformation(String id) throws RollbackableException {
        try {
            Object o = getInformationDAO().getHibernateTemplate().get(InformationBO.class, id);
            if (o != null) {
                getInformationDAO().getHibernateTemplate().delete(o);
            }
        } catch (Exception e) {
            throw new RollbackableException("删除培训信息失败", e, this.getClass());
        }
    }

    /**
     * 删除信息
     *
     * @param ids
     * @throws RollbackableException
     */
    public void deleteInformation(String[] ids) throws RollbackableException {
        try {
            if (ids == null || ids.length == 0)
                return;
            for (int i = 0; i < ids.length; i++) {
                Object o = getInformationDAO().findBo(InformationBO.class, ids[i]);
                if (o != null) {
                    getInformationDAO().deleteBo(o);
                }
            }

        } catch (RollbackableException e) {
            throw new RollbackableException("删除公告失败", e, this.getClass());
        }
    }

    public InformationBO getInfoById(String id) throws RollbackableException {
        try {
            List list;
             list=informationDAO.getHibernateTemplate().find("from InformationBO b where b.id = '" + id + "'");
             if (list==null || list.size() == 0)
                return null;
            else
                return (InformationBO)list.get(0);
//            return (InformationBO) getInformationDAO().getHibernateTemplate().get(InformationBO.class, id);
        } catch (Exception e) {
            throw new RollbackableException("获得培训信息失败", e, InformationBO.class);
        }
    }

    public List queryInfoByType(String type) throws RollbackableException {
        try {
            return informationDAO.queryInfoByType(type);
        } catch (Exception e) {
            throw new RollbackableException("根据信息类型查询培训信息失败", e, InformationBO.class);
        }
    }

    public List queryInfo() throws RollbackableException {
        try {
            return informationDAO.queryInfo();
        } catch (Exception e) {
            throw new RollbackableException("查询培训信息失败", e, InformationBO.class);
        }
    }

    //根据机构查询
    public List queryInfoByOrg(PageVO page, String orgId, String type) throws RollbackableException {
        try {
            return informationDAO.queryInfoByOrg(page, orgId, type);
        } catch (Exception e) {
            throw new RollbackableException("根据机构查询培训信息失败", e, InformationBO.class);
        }
    }

    /**
     * 判断同一类型的公告是否存在重名
     *
     * @param name
     * @param type
     * @return
     * @throws RollbackableException
     */
    public boolean checkSameName(String name, String type, String id) throws RollbackableException {
        String hq = "";
        if (id == null || "".equals(id)) {
            hq = "from InformationBO b where b.title = '" + name.trim() + "' and b.type='" + type + "'";
        } else {
            hq = "from InformationBO b where b.title = '" + name.trim() + "' and b.type='" + type + "' and b.id <> '" + id + "'";
        }
        List list = informationDAO.getHibernateTemplate().find(hq);
        return !list.isEmpty();
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
            String hql = "from InformationBO b where b.id in(" + strid + ")";
            List list = informationDAO.getHibernateTemplate().find(hql);
            return list;
        } catch (Exception e) {
            throw new RollbackableException("批量更新信息状态", e, InformationBO.class);
        }
    }

    //批量更新信息状态
    public void updateAllStatus(String[] ids, String col ,String value) throws RollbackableException {
        try {
            informationDAO.updateAllStatus(ids, col,value);
        } catch (Exception e) {
            throw new RollbackableException("批量更新信息状态", e, InformationBO.class);
        }
    }
}
