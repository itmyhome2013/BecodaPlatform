package com.becoda.bkms.qry.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.pojo.bo.QueryItemBO;
import com.becoda.bkms.util.Tools;

import java.util.List;

public class QueryItemDAO extends GenericDAO {

    /**
     * 批量删除queryItem
     *
     * @param qryId
     * @throws BkmsException
     * @roseuid 446D7732036A
     */
    public void deleteQueryItem(String qryId) throws BkmsException {
        if (qryId == null)
            return;
        try {
            List list = hibernateTemplate.find("from QueryItemBO i where i.qryId = ?", qryId);
            if (list.isEmpty())
                return;
            hibernateTemplate.deleteAll(list);
        } catch (Exception e) {
            throw new RollbackableException("无法删除显示项目", e, this.getClass());
        }

    }


    public void deleteQryWageItem(String qryId) throws BkmsException {
        if (qryId == null)
            return;
        try {
            List list = hibernateTemplate.find("from QryWageItemBO i where i.qryId = ?", qryId);
            if (list.isEmpty())
                return;
            hibernateTemplate.deleteAll(list);
        } catch (Exception e) {
            throw new RollbackableException("无法删除显示项目", e, this.getClass());
        }

    }

    /**
     * 根据qryId查询所有的显示项目.
     *
     * @param qryId 查询定义的Id
     * @return com.becoda.bkms.qry.pojo.bo.QueryItemBO[]@throws BkmsException
     * @roseuid 446D77450104
     */
    public QueryItemBO[] queryQueryItem(String qryId) throws BkmsException {
        if (qryId == null)
            return null;
        try {
            List list = hibernateTemplate.find("from QueryItemBO i where i.qryId = ? order by i.qryId + 0", qryId);
            if (list.isEmpty())
                return null;
            QueryItemBO[] bos = new QueryItemBO[list.size()];
            for (int i = 0; i < bos.length; i++) {
                QueryItemBO bo = new QueryItemBO();
                Tools.copyProperties(bo, list.get(i));
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("无法查询显示项", e, this.getClass());
        }
    }

    /**
     * 根据typeFlag(abcd)查询默认显示项目
     *
     * @param typeFlag
     * @throws BkmsException
     * @roseuid 446D77540066
     */
    public QueryItemBO[] queryDefaultItem(String typeFlag) throws BkmsException {
        if (typeFlag == null)
            return null;

        try {
            typeFlag = typeFlag.toUpperCase();
            List list = hibernateTemplate.find("from QueryItemBO i where i.defaultType = ? and i.defaultFlag = 1 order by i.qryItemId + 0", typeFlag);
            if (list.isEmpty())
                return null;
            QueryItemBO[] bos = new QueryItemBO[list.size()];
            for (int i = 0; i < bos.length; i++) {
                QueryItemBO bo = new QueryItemBO();
                Tools.copyProperties(bo, list.get(i));
                bo.setDefaultFlag(0);
                bo.setDefaultType(null);
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("无法查询默认显示项", e, this.getClass());
        }
    }
}
