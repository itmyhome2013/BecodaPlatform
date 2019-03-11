package com.becoda.bkms.qry.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.util.Tools;

import java.util.List;

public class QueryConditionDAO extends GenericDAO {

    /**
     * 根据staticId删除一个查询的所有Condition
     *
     * @param staticId
     * @throws BkmsException
     * @roseuid 446EB5FA020B
     */
    public void deleteCondition(String staticId) throws BkmsException {
        if (staticId == null)
            return;
        try {
            List list = hibernateTemplate.find("from QueryConditionBO c where c.staticId = ?", staticId);
            if (list.isEmpty())
                return;
            hibernateTemplate.deleteAll(list);
        } catch (Exception e) {
            throw new RollbackableException("无法删除查询条件", e, this.getClass());
        }
    }

    /**
     * 根据staticId查询所有的Condition
     *
     * @param staticId
     * @return com.becoda.bkms.qry.pojo.bo.QueryConditionBO[]@throws BkmsException
     * @roseuid 446EB60601AF
     */
    public QueryConditionBO[] queryCondition(String staticId) throws BkmsException {
        if (staticId == null)
            return null;
        try {
            List list = hibernateTemplate.find("from QueryConditionBO c where c.staticId = ?", staticId);
            if (list.isEmpty())
                return null;
            QueryConditionBO[] bos = new QueryConditionBO[list.size()];
            for (int i = 0; i < bos.length; i++) {
                QueryConditionBO bo = new QueryConditionBO();
                Tools.copyProperties(bo, list.get(i));
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("无法查询定义的条件", e, this.getClass());
        }
    }
}
