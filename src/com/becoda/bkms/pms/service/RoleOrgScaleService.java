package com.becoda.bkms.pms.service;

import com.becoda.bkms.common.exception.RollbackableException;
//import com.becoda.bkms.org.util.OrgTool;
import com.becoda.bkms.pms.dao.RoleOrgScaleDAO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色机构权限数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleOrgScaleService {
    private RoleOrgScaleDAO roleOrgScaleDAO;

    public RoleOrgScaleDAO getRoleOrgScaleDAO() {
        return roleOrgScaleDAO;
    }

    public void setRoleOrgScaleDAO(RoleOrgScaleDAO roleOrgScaleDAO) {
        this.roleOrgScaleDAO = roleOrgScaleDAO;
    }

    /**
     * 添加
     *
     * @param roleOrgScale 机构范围权限数组
     */
    public void createRoleOrgScale(RoleOrgScaleBO roleOrgScale) throws RollbackableException {
        roleOrgScaleDAO.createBo(roleOrgScale);
    }

    /**
     * 删除
     */
    public void deleteRoleOrgScale(String roleID, String scaleFlag, String pmsType, String orgId) throws RollbackableException {
        roleOrgScaleDAO.deleteRoleOrgScale(roleID, scaleFlag, pmsType, orgId);
    }

    /**
     * 查询操作权限，按照TreeID排序 <br>
     * 说名:如果是sys角色，则直接查询pmsOperate表
     *
     * @param roleID    角色ID <br>
     * @param scaleFlag 1 菜单权限,2 按钮权限
     */
    public List queryRoleOrgScale(String roleID, String scaleFlag, String pmsType) throws RollbackableException {
        return roleOrgScaleDAO.queryRoleOrgScale(roleID, scaleFlag, pmsType);
    }

    /**
     * 检测是否某个机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @param pmsType   1有权机构 0 排除机构
     * @return true 已选，false 未选
     */
    public boolean checkOrgSelected(String roleId, String scaleFlag, String pmsType, String orgId) throws RollbackableException {
        try {
            String ret = roleOrgScaleDAO.checkOrgSelected(roleId, scaleFlag, pmsType);
            if (orgId == null || "".equals(orgId)) return true;
            List list = new ArrayList();
//            OrgTool.querySuperOrgId(list, orgId);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    if (ret.indexOf(list.get(i) + ",") != -1)
                        return true;
                }
            }
            return false;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new RollbackableException("读取数据失败!", e, RoleOrgScaleService.class);
        }
    }

    /**
     * 检测排除机构是否在有权机构范围
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return true 在，false 不在
     */
    public boolean checkInHaveOrgScale(String roleId, String scaleFlag, String orgId) throws RollbackableException {
        return roleOrgScaleDAO.checkInHaveOrgScale(roleId, scaleFlag, orgId);
    }

    /**
     * * 查询角色的代码权限
     *
     * @param roleId
     * @param scaleFlag
     * @return
     * @throws RollbackableException
     */
    public Hashtable queryRolePmsCode(String roleId, String scaleFlag, List pmsCodeSet) throws RollbackableException {
        return roleOrgScaleDAO.queryRolePmsCode(roleId, scaleFlag, pmsCodeSet);
    }

    /**
     * 检测维护机构是否在查询机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryOrgScale(String roleId, String orgId) throws RollbackableException {
        return roleOrgScaleDAO.checkInQueryOrgScale(roleId, orgId);
    }

    /**
     * 检测维护机构是否在查询排除机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryNoOrgScale(String roleId, String orgId) throws RollbackableException {
        return roleOrgScaleDAO.checkInQueryNoOrgScale(roleId, orgId);
    }

    /**
     * 修改操作权限
     * 说明：先删除、在添加。
     */
    public void updateRoleCodeScale(String roleId, String scaleFlag, List codeList, List pmsCodeSet) throws RollbackableException {
        roleOrgScaleDAO.deleteCodeScaleByRoleId(roleId, scaleFlag, pmsCodeSet);
        if (codeList != null) {
            for (int i = 0; i < codeList.size(); i++) {
                RoleOrgScaleBO oper = (RoleOrgScaleBO) codeList.get(i);
                roleOrgScaleDAO.createBo(oper);
            }
        }
    }

    public Hashtable queryRoleOrgScaleByAdminRoleId(String roleId, String scaleFlag, String pmsType) throws RollbackableException {
        if (roleId == null || "".equals(roleId)) return null;
        String treeId = "";
        try {
            RoleInfoBO bo = (RoleInfoBO) roleOrgScaleDAO.findBo(RoleInfoBO.class, roleId);
            if (bo != null) treeId = bo.getTreeId();
        } catch (RollbackableException re) {
            throw new RollbackableException("读取数据失败!", re, RoleOrgScaleService.class);
        }
        return roleOrgScaleDAO.queryRoleOrgScaleByAdminRoleId(treeId, scaleFlag, pmsType);
    }

    public void deleteDataBySql(String sql) throws RollbackableException {
        roleOrgScaleDAO.deleteDataBySql(sql);
    }

}
