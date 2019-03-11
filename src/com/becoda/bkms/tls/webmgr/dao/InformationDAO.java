package com.becoda.bkms.tls.webmgr.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 17:03:11
 * To change this template use File | Settings | File Templates.
 */
public class InformationDAO extends GenericDAO {
    public List queryInfoByType(String type) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from InformationBO i where i.type = '" + type + "' order by i.isTop desc,i.createTime");
            if (null == list || list.size() == 0)
                return null;
            else
                return list;
        } catch (Exception e) {
            throw new RollbackableException("根据信息类型查询培训信息失败", e, InformationBO.class);
        }
    }

    public List queryInfo() throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from InformationBO");
            if (null == list || list.size() == 0)
                return null;
            else
                return list;
        } catch (Exception e) {
            throw new RollbackableException("查询培训信息失败", e, InformationBO.class);
        }
    }

    //根据机构查询
    public List queryInfoByOrg(PageVO page, String orgId, String type) throws RollbackableException {
        try {
//            String sql = "";
            String countHql = "Select count(b) from InformationBO b where 1=1 ";
            String queryHql = "from InformationBO b where 1=1 ";
            StringBuffer where = new StringBuffer();
            if (type != null && !"".equals(type)) {
                where.append("and b.type='").append(type).append("'");
            }
            if (orgId != null && !"".equals(orgId)) {
                where.append("and b.createOrgId='").append(orgId).append("'");
            }
            countHql = countHql.concat(where.toString());
            queryHql = queryHql.concat(where.toString());
            return this.pageQuery(page, countHql, queryHql);
        } catch (Exception e) {
            throw new RollbackableException("根据机构查询培训信息失败", e, InformationBO.class);
        }
    }

    //批量更新信息状态
    public void updateAllStatus(String[] ids, String col, String value) throws RollbackableException {
        try {
            String idstr = "";
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    if (i == 0) {
                        idstr = "'" + ids[0] + "'";
                    } else {
                        idstr = idstr + ",'" + ids[i] + "'";
                    }
                }
            }
            String sql = "UPDATE tls_information  SET " + col + "='" + value + "' where INFO_ID in(" + idstr + ")";
            getJdbcTemplate().execute(sql);
        } catch (Exception e) {
            throw new RollbackableException("批量更新信息状态", e, InformationBO.class);
        }
    }
}
