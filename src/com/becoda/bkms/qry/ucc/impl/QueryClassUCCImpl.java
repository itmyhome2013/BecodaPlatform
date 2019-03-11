package com.becoda.bkms.qry.ucc.impl;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.bo.QueryClassBO;
import com.becoda.bkms.qry.service.QueryClassService;
import com.becoda.bkms.qry.service.QueryService;
import com.becoda.bkms.qry.ucc.IQueryClassUCC;

public class QueryClassUCCImpl implements IQueryClassUCC {
    /**
     * 注入QueryClassService
     */
    private QueryClassService classservice;
    private QueryService qservice;

    /**
     * 根据rootId查询只属于自己的查询类别,使用personId作为root的表示私有查询类别的根节点<BR>
     * 使用orgId表示公有查询的根节点.<BR>
     * 本方法一次返回全部下级节点,但是必须根据treeId进行排序.<BR>
     *
     * @param rootId rootId格式为personId + "_" + "A|B|C|D"或orgId + "_" + "A|B|C|D"
     * @return QueryClassBO[]
     */
    public QueryClassBO[] queryClasses(String rootId) throws BkmsException {
        return classservice.queryClasses(rootId);
    }

    /**
     * 创建一个查询类别
     *
     * @param bo
     * @return String
     */
    public String createQueryClass(QueryClassBO bo) throws BkmsException {
        return classservice.createQueryClass(bo);
    }

    /**
     * 删除一个查询类别.删除前判断下级节点或挂了查询则不能删除.
     *
     * @param classId
     */
    public void deleteQueryClass(String classId) throws BkmsException {
        if (classId == null)
            return;
        QueryClassBO[] subclass = classservice.queryClassBySuperId(classId);
        if (subclass != null)
            throw new RollbackableException("包含下级查询类别不能删除", this.getClass());
        QueryBO[] querys = qservice.queryQuery(classId);
        if (querys != null)
            throw new RollbackableException("本类别下包含查询，类别不能删除", this.getClass());

        classservice.deleteQueryClass(classId);
    }

    public QueryClassBO findQueryClassBO(String classId) throws BkmsException {
        return classservice.findQueryClassById(classId);
    }

    public void updateQueryClassBO(QueryClassBO bo) throws BkmsException {
        classservice.updateQueryClassBO(bo);
    }

    //getter setter
    public QueryClassService getClassservice() {
        return classservice;
    }

    public void setClassservice(QueryClassService classservice) {
        this.classservice = classservice;
    }

    public QueryService getQservice() {
        return qservice;
    }

    public void setQservice(QueryService qservice) {
        this.qservice = qservice;
    }
}
