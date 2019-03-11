package com.becoda.bkms.qry.dao;


import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.util.Tools;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import java.util.List;

public class QueryDAO extends GenericDAO {

    /**
     * 根据classid查询类别id查询,返回该类别下挂的查询定义
     *
     * @param classId
     * @return com.becoda.bkms.qry.pojo.bo.QueryBO[]@throws BkmsException
     * @roseuid 446932B30086
     */
    public QueryBO[] queryQueryByClassId(String classId) throws BkmsException {
        if (classId == null)
            return null;
        try {
            List list = hibernateTemplate.find("from QueryBO q where q.classId = ? order by q.qryId", classId);
            if (list.isEmpty())
                return null;
            QueryBO[] bos = new QueryBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                QueryBO po = (QueryBO) list.get(i);
                QueryBO bo = new QueryBO();
                Tools.copyProperties(bo, po);
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("无法按类别进行查询", e, this.getClass());
        }
    }

    public QueryBO[] queryQueryByClassId(String classId, PageVO vo) throws BkmsException {
        if (classId == null)
            return null;
        try {
            String hq = "from QueryBO b where b.classId = ? order by q.qryId";
            String countHq = "select count(b) from QueryBO b where b.classId = ?";
            List list = pageQuery(vo, countHq, hq, new Object[]{classId}, new Type[]{Hibernate.STRING});

            if (list.isEmpty())
                return null;
            QueryBO[] bos = new QueryBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                QueryBO po = (QueryBO) list.get(i);
                QueryBO bo = new QueryBO();
                Tools.copyProperties(bo, po);
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("无法按类别进行查询", e, this.getClass());
        }
    }

    /**
     * 根据系统标志查询
     *
     * @param sysFlag
     * @return
     * @throws BkmsException
     */
    public List queryQueryBySysFlag(String sysFlag) throws BkmsException {
        try {
            return hibernateTemplate.find("from QueryBO q where q.sysFlag = ? order by q.qryId", sysFlag);
        } catch (Exception e) {
            throw new RollbackableException("查询错误", e, this.getClass());
        }
    }

}
