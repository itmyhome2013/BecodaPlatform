package com.becoda.bkms.qry.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.dao.QueryClassDAO;
import com.becoda.bkms.qry.pojo.bo.QueryClassBO;
import com.becoda.bkms.util.SequenceGenerator;

public class QueryClassService {
    private QueryClassDAO classdao;

    /**
     * 创建一个QueryClass,bo数据有backingbean构造好
     *
     * @param bo
     * @return String
     * @roseuid 446940C700A7
     */
    public String createQueryClass(QueryClassBO bo) throws BkmsException {
        try {
            if (bo == null)
                return null;
            String key = SequenceGenerator.getKeyId("qry_query_class");
            QueryClassBO po = (QueryClassBO) classdao.findBoById(QueryClassBO.class, bo.getSuperId());
            String treeId = "-1";
            if (po != null)
                treeId = po.getTreeId();
            treeId = SequenceGenerator.getTreeId("qry_query_class", "tree_id", treeId, 4, 1, "root_id = '" + bo.getRootId() + "'");
            bo.setTreeId(treeId);
            key = bo.getRootId().substring(0, 2) + key;
            bo.setClassId(key);
            return classdao.createBo(bo);
        } catch (BkmsException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException("插入错误", this.getClass());
        }
    }

    /**
     * 根据classId删除queryClass 删除前需要判断下面有否挂子节点.
     *
     * @param classId
     * @roseuid 446940D90369
     */
    public void deleteQueryClass(String classId) throws BkmsException {
        QueryClassBO po = (QueryClassBO) classdao.findBo(QueryClassBO.class, classId);
        classdao.deleteBo(po);
    }

    /**
     * 根据rootId查询所有queryclassbo 必须按照treeid进行排序
     *
     * @param rootId
     * @return QueryClassBO
     */
    public QueryClassBO[] queryClasses(String rootId) throws BkmsException {
        return classdao.queryQueryClass(rootId);
    }

    public QueryClassBO[] queryClassBySuperId(String superId) throws BkmsException {
        return classdao.querySubQueryClass(superId);
    }

    /**
     * @param classId
     * @return com.becoda.bkms.qry.pojo.bo.QueryClassBO
     * @roseuid 446945650345
     */
    public QueryClassBO findQueryClassById(String classId) throws BkmsException {
        return (QueryClassBO) classdao.findBoById(QueryClassBO.class, classId);
    }

    public void updateQueryClassBO(QueryClassBO bo) throws BkmsException {
        if (bo == null || bo.getClassId() == null)
            return;
        classdao.updateBo(bo.getClassId(), bo);
    }

    public QueryClassDAO getClassdao() {
        return classdao;
    }

    public void setClassdao(QueryClassDAO classdao) {
        this.classdao = classdao;
    }


}
