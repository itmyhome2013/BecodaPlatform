package com.becoda.bkms.pms.ucc;


import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;

import java.util.Hashtable;
import java.util.List;

/**
 * 角色功能权限
 * author:lirg
 * 2015-5-24
 */
public interface IRoleOperateUCC {

    /**
     * 修改操作权限
     * 说明：先删除、在添加。
     * 考虑：权限收回的实现,可以在删除之前，先比较与原来的权限的减少情况，如果有减少，?
     * 根据此管理员所创建的角色，按递归的方法检测。
     *
     * @param roleOpers
     * @roseuid 4479D51700E6
     */
    public void updateRoleOperate(String roleId, Hashtable roleOpers, String managerFlag, User user) throws RollbackableException;

    /**
     * 查询操作权限 <br>
     *
     * @param roleID:角色ID <br>
     *                    说名:如果是sys角色，则直接查询pmsOperate表
     * @roseuid 4479D52C00A0
     */
    public List queryRoleOperate(String roleID) throws RollbackableException;
}
