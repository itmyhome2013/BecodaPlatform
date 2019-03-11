package com.becoda.bkms.pms.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.dao.OperateDAO;
import com.becoda.bkms.pms.dao.RoleInfoDAO;
import com.becoda.bkms.pms.dao.RoleOperateDAO;
import com.becoda.bkms.util.Tools;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * 角色功能权限数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleOperateService {
    private RoleOperateDAO roleOperateDAO;
    private RoleInfoDAO roleInfoDAO;
    private OperateDAO operateDAO;

    public RoleOperateDAO getRoleOperateDAO() {
        return roleOperateDAO;
    }

    public void setRoleOperateDAO(RoleOperateDAO roleOperateDAO) {
        this.roleOperateDAO = roleOperateDAO;
    }

    public RoleInfoDAO getRoleInfoDAO() {
        return roleInfoDAO;
    }

    public void setRoleInfoDAO(RoleInfoDAO roleInfoDAO) {
        this.roleInfoDAO = roleInfoDAO;
    }

    public OperateDAO getOperateDAO() {
        return operateDAO;
    }

    public void setOperateDAO(OperateDAO operateDAO) {
        this.operateDAO = operateDAO;
    }

    /**
     * 修改操作权限
     * 说明：先删除、在添加。
     * 考虑：权限收回的实现,可以在删除之前，先比较与原来的权限的减少情况，如果有减少，?
     * 根据此管理员所创建的角色，按递归的方法检测。
     *
     * @param roleOpers 角色操作权限数组
     */
    public void updateRoleOperate(String roleId, Hashtable roleOpers) throws RollbackableException {
        roleOperateDAO.deleteOperatesByRoleId(roleId);
        if (roleOpers != null) {
            Iterator it = roleOpers.values().iterator();
            while (it.hasNext()) {
                Object o = it.next();
                roleOperateDAO.createBo(o);
            }
        }
    }

    /**
     * 查询操作权限 <br>
     * 说名:如果是sys角色，则直接查询pmsOperate表。
     *
     * @param roleId 角色ID <br>
     * @return list obj = OperateBO
     * @roseuid 4479D4210006
     */
    public List queryRoleOperate(String roleId) throws RollbackableException {
        roleId = Tools.filterNull(roleId);
        if ("".equals(roleId)) return null;
        return roleOperateDAO.queryRoleOperate(roleId);
    }

    public void deleteDataBySql(String sql) throws RollbackableException {
        roleOperateDAO.deleteDataBySql(sql);
    }
}
