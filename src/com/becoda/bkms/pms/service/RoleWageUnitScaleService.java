package com.becoda.bkms.pms.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.dao.RoleWageUnitScaleDAO;
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
public class RoleWageUnitScaleService {
    private RoleWageUnitScaleDAO roleWageUnitScaleDAO;

    public RoleWageUnitScaleDAO getRoleWageUnitScaleDAO() {
        return roleWageUnitScaleDAO;
    }

    public void setRoleWageUnitScaleDAO(RoleWageUnitScaleDAO roleWageUnitScaleDAO) {
        this.roleWageUnitScaleDAO = roleWageUnitScaleDAO;
    }

    /**
     * 添加
     *
     * @param roleOrgScale 机构范围权限数组
     */
    public void createRoleWageUnitScale(RoleOrgScaleBO roleOrgScale) throws RollbackableException {
        roleWageUnitScaleDAO.createBo(roleOrgScale);
    }

    /**
     * 删除
     */
    public void deleteRoleWageUnitScale(String roleID, String scaleFlag, String pmsType, String orgId) throws RollbackableException {
        roleWageUnitScaleDAO.deleteRoleWageUnitScale(roleID, scaleFlag, pmsType, orgId);
    }

    /**
     * 查询范围权限 <br>
     *
     * @param roleID    角色ID <br>
     * @param scaleFlag 1 维护范围 0 查询范围
     * @param pmsType   1有权机构 0 排除机构
     */
    public List queryRoleWageUnitScale(String roleID, String scaleFlag, String pmsType) throws RollbackableException {
        return roleWageUnitScaleDAO.queryRoleWageUnitScale(roleID, scaleFlag, pmsType);
    }

    /**
     * 检测是否某个机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @param pmsType   1有权机构 0 排除机构
     * @return true 已选，false 未选
     */
    public boolean checkWageUnitSelected(String roleId, String scaleFlag, String pmsType, String orgId) throws RollbackableException {
        try {
            String ret = roleWageUnitScaleDAO.checkWageUnitSelected(roleId, scaleFlag, pmsType);
            if (orgId == null || "".equals(orgId)) return true;
            List list = new ArrayList();
            this.querySuperWageUnitId(list, orgId);
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
            throw new RollbackableException("读取数据失败!", e, RoleWageUnitScaleService.class);
        }
    }

    /**
     * 得到上级机构ID
     *
     * @param list  机构ID列表
     * @param orgId 查询机构ID
     * @return List
     * @throws RollbackableException
     */
    private List querySuperWageUnitId(List list, String orgId) throws RollbackableException {
//        if (orgId == null || "".equals(orgId)) return list;
//        try {
//            WageUnitBO org = (WageUnitBO) roleWageUnitScaleDAO.findBo(WageUnitBO.class, orgId);
//            if (org != null) {
//                String superId = org.getSuperId();
//                if ("-1".equals(superId)) {
//                    list.add(org.getUnitId());
//                    return list;
//                } else {
//                    list.add(org.getUnitId());
//                    return this.querySuperWageUnitId(list, org.getSuperId());
//                }
//            }
//            return list;
//        } catch (RollbackableException re) {
//            throw re;
//        } catch (Exception e) {
//            throw new RollbackableException("读取数据失败!", e, RoleWageUnitScaleService.class);
//        }
        return null;
    }

    /**
     * 检测排除机构是否在有权机构范围
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return true 在，false 不在
     */
    public boolean checkInHaveWageUnitScale(String roleId, String scaleFlag, String orgId) throws RollbackableException {
        return roleWageUnitScaleDAO.checkInHaveWageUnitScale(roleId, scaleFlag, orgId);
    }

    /**
     * 检测维护机构是否在查询机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryWageUnitScale(String roleId, String orgId) throws RollbackableException {
        return roleWageUnitScaleDAO.checkInQueryWageUnitScale(roleId, orgId);
    }

    public Hashtable queryRoleWageUnitScaleByAdminRoleId(String roleId, String scaleFlag, String pmsType) throws RollbackableException {
        if (roleId == null || "".equals(roleId)) return null;
        String treeId = "";
        try {
            RoleInfoBO bo = (RoleInfoBO) roleWageUnitScaleDAO.findBo(RoleInfoBO.class, roleId);
            if (bo != null) treeId = bo.getTreeId();
        } catch (RollbackableException re) {
            throw new RollbackableException("读取数据失败!", re, RoleOrgScaleService.class);
        }
        return roleWageUnitScaleDAO.queryRoleWageUnitScaleByAdminRoleId(treeId, scaleFlag, pmsType);
    }
}
