package com.becoda.bkms.pms.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.dao.RoleInfoDAO;
import com.becoda.bkms.pms.dao.UserRoleDAO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;

import java.util.List;

/**
 * 角色管理数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleManageService {
    private RoleInfoDAO roleInfoDAO;
    private UserRoleDAO userRoleDAO;

    public RoleInfoDAO getRoleInfoDAO() {
        return roleInfoDAO;
    }

    public void setRoleInfoDAO(RoleInfoDAO roleInfoDAO) {
        this.roleInfoDAO = roleInfoDAO;
    }

    public UserRoleDAO getUserRoleDAO() {
        return userRoleDAO;
    }

    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    /**
     * 修改角色信息
     *
     * @param roleInfo 角色BO
     */
    public void updateRole(RoleInfoBO roleInfo) throws RollbackableException {
        roleInfoDAO.updateBo(roleInfo.getRoleId(), roleInfo);
    }

    /**
     * 删除角色信息
     *
     * @param roleID 角色ID
     */
    public void deleteRole(String roleID) throws RollbackableException {
        RoleInfoBO bo = (RoleInfoBO) roleInfoDAO.findBo(RoleInfoBO.class, roleID);
        if (bo != null)
            roleInfoDAO.deleteBo(bo);
    }

    /**
     * 查询角色
     *
     * @param roleID 角色ID
     * @return com.becoda.bkms.pms.pojo.bo.RoleInfoBO
     */
    public RoleInfoBO findRoleInfo(String roleID) throws RollbackableException {
        return (RoleInfoBO) roleInfoDAO.findBo(RoleInfoBO.class, roleID);
    }

    /**
     * 查询指定角色下是否存在本级用户
     *
     * @param sysRoleId 系统管理员角色ID
     * @return RoleInfoBO[]
     */
    public boolean checkCurrentUserBySysRoleId(String sysRoleId) throws RollbackableException {
        return roleInfoDAO.checkCurrentUserBySysRoleId(sysRoleId);
    }

    /**
     * 查询指定管理员角色是否存在下属角色
     *
     * @param sysRoleId 系统管理员角色ID
     */
    public boolean checkSubRoleBySysRoleId(String sysRoleId) throws RollbackableException {
        return roleInfoDAO.checkSubRoleBySysRoleId(sysRoleId);
    }


    /**
     * 查询指定用户创建的所有角色
     *
     * @param userId 系统管理员用户ID
     * @return List 角色列表 obj = RoleInfoBO
     */
    public List queryRoleCreateBySys(String userId) throws RollbackableException {
        List list = userRoleDAO.queryUserRole(userId);
        if (list != null && list.size() > 0)    //如果是系统管理员，则只有一个角色，所以取第一个RoleId
        {
            RoleInfoBO role = (RoleInfoBO) list.get(0);
            return roleInfoDAO.queryAllRoleInfos(role.getRoleId());
        }
        return null;
    }

    /**
     * 查询指定用户创建的所有角色
     *
     * @param userId 系统管理员用户ID
     * @return List 角色列表 obj = RoleInfoBO
     */
    public List queryRoleCreateBySys(PageVO vo, String userId, String roleName) throws RollbackableException {
        List list = userRoleDAO.queryUserRole(userId);
        if (list != null && list.size() > 0)    //如果是系统管理员，则只有一个角色，所以取第一个RoleId
        {
            RoleInfoBO role = (RoleInfoBO) list.get(0);
            return roleInfoDAO.queryAllRoleInfos(vo, role.getRoleId(), roleName);
        }
        return null;
    }

    /**
     * 查询指定用户创建的所有角色
     *
     * @param userId 系统管理员用户ID
     * @return List 角色列表 obj = RoleInfoBO
     */
    public List queryChildRoleIncludeSelf(String userId) throws RollbackableException {
        List list = userRoleDAO.queryUserRole(userId);
        if (list != null && list.size() > 0)    //如果是系统管理员，则只有一个角色，所以取第一个RoleId
        {
            RoleInfoBO role = (RoleInfoBO) list.get(0);
            return roleInfoDAO.queryChildRoleIncludeSelf(role.getRoleId());
        }
        return null;
    }

    /**
     * 创建角色，调用dao 基类的方法
     *
     * @param roleInfo 角色BO
     */
    public void createRole(RoleInfoBO roleInfo) throws RollbackableException {
        roleInfoDAO.createBo(roleInfo);
    }

    /**
     * 查询指定角色所拥有的用户列表
     *
     * @param roleID
     * @return
     * @throws RollbackableException
     */
    public List queryRoleUser(String roleID) throws RollbackableException {
        return userRoleDAO.queryRoleUser(roleID);
    }

    /**
     * 复制角色信息
     *
     * @param newRoleName  新角色名
     * @param sourceRoleId 源角色ID
     * @throws RollbackableException
     */
    public void saveAsRoleInfo(String newRoleName, String sourceRoleId) throws RollbackableException {
        roleInfoDAO.saveAsRoleInfo(newRoleName, sourceRoleId);
    }

    public void deleteRoleUser(String[] ids, String roleId) throws RollbackableException {
        userRoleDAO.deleteRoleUser(ids, roleId);
    }

}