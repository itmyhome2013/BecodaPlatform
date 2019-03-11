package com.becoda.bkms.pms.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.pojo.bo.OperateBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-5
 * Time: 13:37:10
 * To change this template use File | Settings | File Templates.
 */
public class OperateDAO extends GenericDAO {
    /**
     * 得到系统所有菜单
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryAllOperates() throws RollbackableException {
        try {
            return hibernateTemplate.find("from OperateBO op order by op.treeId");
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 得到系统所有模块
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryAllModule() throws RollbackableException {
        try {
            return hibernateTemplate.find("from OperateBO op where op.superId='-1' order by op.moduleNum");
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 通过operateId获得模块
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public OperateBO getOperateByName(String name) throws RollbackableException {
        try {
            OperateBO bo = null;
            List list = hibernateTemplate.find("from OperateBO op where op.operateName='" + name + "'");
            if (list.size() > 0) {
                bo = (OperateBO) list.get(0);
            }
            return bo;
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 通过treeId获得模块下的所有菜单
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryMenusByTreeId(String treeId) throws RollbackableException {
        try {
            return hibernateTemplate.find("from OperateBO o where o.treeId like '" + treeId + "%'" +
                    " order by o.treeId ASC");
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }
}
