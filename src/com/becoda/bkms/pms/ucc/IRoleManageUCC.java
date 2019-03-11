package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;

import java.util.List;


/**
 * 角色管理
 * author:lirg
 * 2015-5-24
 */
public interface IRoleManageUCC {
    /**
     * 修改角色信息
     *
     * @param roleInfo 角色BO
     */
    public void updateRole(RoleInfoBO roleInfo, User user) throws RollbackableException;

    /**
     * 删除角色信息
     *
     * @param roleIds 角色ID
     */
    public void deleteRoles(String[] roleIds, User user) throws RollbackableException;

    /**
     * 查询角色
     *
     * @param roleId 角色ID
     */
    public RoleInfoBO findRoleInfo(String roleId) throws RollbackableException;

    /**
     * 查询指定角色创建的所有角色
     *
     * @param userId:系统管理员角色ID
     */
    public List queryAllRoleInfos(String userId, boolean isTrans) throws RollbackableException;

    /**
     * 查询指定角色创建的所有角色
     *
     * @param userId:系统管理员角色ID
     */
    public List queryAllRoleInfos(PageVO vo, String userId, boolean isTrans, String roleName) throws RollbackableException;

    /**
     * 创建角色，调用dao 基类的方法
     *
     * @param roleInfo 角色BO
     */
    public void createRole(RoleInfoBO roleInfo, User user) throws RollbackableException;

    /**
     * 查询角色的用户
     *
     * @param roleID
     * @return
     * @throws RollbackableException
     */
    public List queryRoleUser(String roleID) throws RollbackableException;

    public void saveAsRoleInfo(String newRoleName, String sourceRoleId, User user) throws RollbackableException;

    /**
     * 查询指定管理员角色是否存在本级别用户
     *
     * @param sysRoleId 系统管理员角色ID
     * @return RoleInfoBO[]
     */
    public boolean checkCurrentUserBySysRoleId(String sysRoleId) throws RollbackableException;

    /**
     * 查询指定管理员角色是否存在下属角色
     *
     * @param sysRoleId 系统管理员角色ID
     */
    public boolean checkSubRoleBySysRoleId(String sysRoleId) throws RollbackableException;

    public void deleteRoleUser(String[] ids, String roleId, User user) throws RollbackableException;

    /**
     * 查询指定用户创建的所有角色
     *
     * @param userId 系统管理员用户ID
     * @return List 角色列表 obj = RoleInfoBO
     */
    public List queryChildRoleIncludeSelf(String userId) throws RollbackableException;

}
