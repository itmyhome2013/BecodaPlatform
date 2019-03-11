package com.becoda.bkms.qry.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.pojo.bo.QueryStaticBO;
import com.becoda.bkms.util.Tools;

import java.util.List;

public class QueryStaticDAO extends GenericDAO {

    /**
     * 批量删除static
     *
     * @param qryId
     * @throws BkmsException
     * @roseuid 446EB54B00FB
     */
    public void deleteStatic(String qryId) throws BkmsException {
        if (qryId == null)
            return;
        try {
            List list = hibernateTemplate.find("from QueryStaticBO s where s.qryId = ?", qryId);
            if (list.isEmpty())
                return;
            hibernateTemplate.deleteAll(list);
        } catch (Exception e) {
            throw new RollbackableException("无法删除统计项", e, this.getClass());
        }
    }

    /**
     * 根据qryId查询所有的static对象.
     *
     * @param qryId
     * @return com.becoda.bkms.qry.pojo.bo.QueryStaticBO[]@throws BkmsException
     * @roseuid 446EB5590336
     */
    public QueryStaticBO[] queryStatic(String qryId) throws BkmsException {
        if (qryId == null)
            return null;
        try {
            List list = hibernateTemplate.find("from QueryStaticBO s where s.qryId = ? order by s.staticId asc", qryId);
            if (list.isEmpty())
                return null;
            QueryStaticBO[] bos = new QueryStaticBO[list.size()];
            for (int i = 0; i < bos.length; i++) {
                QueryStaticBO bo = new QueryStaticBO();
                Tools.copyProperties(bo, list.get(i));
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("无法查询统计项", e, this.getClass());
        }
    }
}
