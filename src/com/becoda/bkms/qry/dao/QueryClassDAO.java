package com.becoda.bkms.qry.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.pojo.bo.QueryClassBO;
import com.becoda.bkms.util.Tools;

import java.util.List;

public class QueryClassDAO extends GenericDAO {

    /**
     * 根据spuerId查询下级节点
     *
     * @param superClassId
     * @return
     * @throws BkmsException
     */
    public QueryClassBO[] querySubQueryClass(String superClassId) throws BkmsException {

        if (superClassId == null)
            return null;
        try {
            List list = hibernateTemplate.find("from QueryClassBO qc where qc.superId = ? order by qc.treeId", superClassId);
            if (list.isEmpty())
                return null;
            QueryClassBO[] bos = new QueryClassBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                QueryClassBO bo = new QueryClassBO();
                Tools.copyProperties(bo, list.get(i));
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("无法按上级id进行查询", e, this.getClass());
        }
    }

    /**
     * 根据rootId查询全部节点.需要按照treeId进行排序.
     *
     * @param rootId
     * @return
     * @throws BkmsException
     */
    public QueryClassBO[] queryQueryClass(String rootId) throws BkmsException {
        if (rootId == null)
            return null;
        try {
            List list = hibernateTemplate.find("from QueryClassBO qc where qc.rootId = ? order by qc.treeId", rootId);
            if (list.isEmpty())
                return null;
            QueryClassBO[] bos = new QueryClassBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                QueryClassBO bo = new QueryClassBO();
                Tools.copyProperties(bo, list.get(i));
                bos[i] = bo;
            }
            return bos;
        } catch (Exception e) {
            throw new RollbackableException("按根节点查询失败", e, this.getClass());
        }
    }

}
