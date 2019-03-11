package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.vo.UserPmsInfoVO;
import com.becoda.bkms.pms.service.RoleManageService;
import com.becoda.bkms.pms.ucc.IRoleManageUCC;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

import java.util.List;

public class RoleManageUCCImpl implements IRoleManageUCC {
    private RoleManageService roleManageService;

    public RoleManageService getRoleManageService() {
        return roleManageService;
    }

    public void setRoleManageService(RoleManageService roleManageService) {
        this.roleManageService = roleManageService;
    }

    public void updateRole(RoleInfoBO roleInfo, User user) throws RollbackableException {
        roleManageService.updateRole(roleInfo);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改了角色编号为（" + roleInfo.getRoleId() + "）的角色信息。");
    }

    public void deleteRoles(String[] roleIds, User user) throws RollbackableException {
        for (int i = 0; i < roleIds.length; i++) {
            roleManageService.deleteRole(roleIds[i]);
        }
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "删除了（" + roleIds.length + "）个角色。");
    }

    public RoleInfoBO findRoleInfo(String roleId) throws RollbackableException {
        return roleManageService.findRoleInfo(roleId);
    }

    /**
     * 查询指定角色创建的所有角色
     *
     * @param userId
     * @param isTrans 是否翻译
     * @return
     * @throws RollbackableException
     */
    public List queryAllRoleInfos(String userId, boolean isTrans) throws RollbackableException {
        if (isTrans)
            return translateCode(roleManageService.queryRoleCreateBySys(userId));
        else
            return roleManageService.queryRoleCreateBySys(userId);
    }

    /**
     * 查询指定角色创建的所有角色
     *
     * @param userId
     * @param isTrans  是否翻译
     * @param roleName
     * @return
     * @throws RollbackableException
     */
    public List queryAllRoleInfos(PageVO vo, String userId, boolean isTrans, String roleName) throws RollbackableException {
        if (isTrans)
            return translateCode(roleManageService.queryRoleCreateBySys(vo, userId, roleName));
        else
            return roleManageService.queryRoleCreateBySys(vo, userId, roleName);
    }


    public void createRole(RoleInfoBO roleInfo, User user) throws RollbackableException {
        RoleInfoBO r = roleManageService.findRoleInfo(user.getBelongRoleId());
        if (r != null) {
            String treeId = null;
            try {
                treeId = SequenceGenerator.getTreeId("PMS_ROLEINFO", "TREE_ID", r.getTreeId(), PmsConstants.TREE_LENGTH, PmsConstants.TREE_STEP_LENGTH);
            } catch (BkmsException e) {
                throw new RollbackableException("生成角色Tree错误!", e, getClass());
            }
            roleInfo.setTreeId(treeId);
        }
        roleManageService.createRole(roleInfo);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "创建了名称为（" + roleInfo.getRoleName() + "）的角色。");
    }

    public List queryRoleUser(String roleID) throws RollbackableException {
        try {
            List list = roleManageService.queryRoleUser(roleID);
            translateCode2(list);
            return list;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new RollbackableException("读取数据错误!", e, getClass());
        }
    }

    private void translateCode2(List list) throws RollbackableException {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                UserPmsInfoVO user = new UserPmsInfoVO();
                Tools.copyProperties(user, list.get(i));
                user.setSex(SysCacheTool.interpretCode(user.getSex()));
                user.setOrgId(SysCacheTool.interpretOrg(user.getOrgId()));
                user.setDeptId(SysCacheTool.interpretOrg(user.getDeptId()));
                user.setPostId(SysCacheTool.interpretPost(user.getPostId()));
                user.setPersonType(SysCacheTool.interpretCode(user.getPersonType()));
                if (PmsConstants.STATUS_OPEN.equals(user.getStatus()))
                    user.setStatus("启用");
                else
                    user.setStatus("<font color='red'>禁用</font>");
                if (PmsConstants.IS_SYS_MANAGER.equals(user.getSysOper()))
                    user.setSysOper("是");
                else
                    user.setSysOper("否");

                if (PmsConstants.IS_HR_LEADER.equals(user.getHrLeader()))
                    user.setBusinessUser("是");
                else
                    user.setBusinessUser("否");

                if (PmsConstants.IS_BUSINESS_USER.equals(user.getBusinessUser()))
                    user.setBusinessUser("是");
                else
                    user.setBusinessUser("否");
//
//                if (PmsConstants.IS_UNIT_LEADER.equals(user.getUnitLeader()))
//                    user.setUnitLeader("是");
//                else
//                    user.setUnitLeader("否");
//
//                if (PmsConstants.IS_DEPT_LEADER.equals(user.getDeptLeader()))
//                    user.setDeptLeader("是");
//                else
//                    user.setDeptLeader("否");
            }
        }
    }

    private List translateCode(List roles) {
        //翻译代码
        if (roles != null) {
            for (int i = 0; i < roles.size(); i++) {
                RoleInfoBO role = (RoleInfoBO) roles.get(i);
                if (PmsConstants.IS_SYS_MANAGER.equals(role.getSysOper()))
                    role.setSysOper("是");
                else
                    role.setSysOper("否");

                if (PmsConstants.IS_BUSINESS_USER.equals(role.getBusinessUser()))
                    role.setBusinessUser("是");
                else
                    role.setBusinessUser("否");

//                if (PmsConstants.IS_UNIT_LEADER.equals(role.getUnitLeader()))
//                    role.setUnitLeader("是");
//                else
//                    role.setUnitLeader("否");
//
                if (PmsConstants.IS_DEPT_LEADER.equals(role.getDeptLeader()))
                    role.setDeptLeader("是");
                else
                    role.setDeptLeader("否");

                if (PmsConstants.IS_HR_LEADER.equals(role.getHrLeader()))
                    role.setHrLeader("是");
                else
                    role.setHrLeader("否");

            }
        }
        return roles;
    }

    /**
     * 复制角色信息
     *
     * @param newRoleName  新角色名
     * @param sourceRoleId 源角色ID
     * @throws RollbackableException
     */
    public void saveAsRoleInfo(String newRoleName, String sourceRoleId, User user) throws RollbackableException {
        roleManageService.saveAsRoleInfo(newRoleName, sourceRoleId);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "从角色编号为（" + sourceRoleId + "）的角色复制了名称为" + newRoleName + "的新角色。");
    }

    /**
     * 查询指定管理员角色是否存在本级别用户
     *
     * @param sysRoleId 系统管理员角色ID
     * @return RoleInfoBO[]
     */
    public boolean checkCurrentUserBySysRoleId(String sysRoleId) throws RollbackableException {
        return roleManageService.checkCurrentUserBySysRoleId(sysRoleId);
    }

    /**
     * 查询指定管理员角色是否存在下属角色
     *
     * @param sysRoleId 系统管理员角色ID
     * @return
     */
    public boolean checkSubRoleBySysRoleId(String sysRoleId) throws RollbackableException {
        return roleManageService.checkSubRoleBySysRoleId(sysRoleId);
    }

    public void deleteRoleUser(String[] ids, String roleId, User user) throws RollbackableException {
        roleManageService.deleteRoleUser(ids, roleId);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "从角色编号为（" + roleId + "）的角色下移出了（" + ids.length + "）名人员。");
    }

    /**
     * 查询指定用户创建的所有角色
     *
     * @param userId 系统管理员用户ID
     * @return List 角色列表 obj = RoleInfoBO
     */
    public List queryChildRoleIncludeSelf(String userId) throws RollbackableException {
        return roleManageService.queryChildRoleIncludeSelf(userId);
    }
}
