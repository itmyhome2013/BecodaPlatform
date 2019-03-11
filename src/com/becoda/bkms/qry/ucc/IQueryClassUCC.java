package com.becoda.bkms.qry.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.qry.pojo.bo.QueryClassBO;

/**
 * 需要注入queryClassUcc
 *
 * @author kang
 */
public interface IQueryClassUCC {

    /**
     * 根据rootId查询只属于自己的查询类别,使用personId作为root的表示私有查询类别的根节点<BR>
     * 使用orgId表示公有查询的根节点.<BR>
     * 本方法一次返回全部下级节点,但是必须根据treeId进行排序.<BR>
     *
     * @param rootId rootId格式为personId + "_" + "A|B|C|D"或orgId + "_" + "A|B|C|D"
     * @return QueryClassBO[]
     */
    public QueryClassBO[] queryClasses(String rootId) throws BkmsException;

    /**
     * 创建一个查询类别
     *
     * @param bo
     * @return String
     */
    public String createQueryClass(QueryClassBO bo) throws BkmsException;

    /**
     * 删除一个查询类别.删除前判断下级节点或挂了查询则不能删除.
     *
     * @param classId
     */
    public void deleteQueryClass(String classId) throws BkmsException;

    public QueryClassBO findQueryClassBO(String classId) throws BkmsException;

    public void updateQueryClassBO(QueryClassBO bo) throws BkmsException;

}