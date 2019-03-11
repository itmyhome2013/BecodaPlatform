package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleUnionScaleBO;
import com.becoda.bkms.pms.service.RoleUnionScaleService;
import com.becoda.bkms.pms.ucc.IRoleUnionScaleUCC;
//import com.becoda.bkms.union.pojo.bo.UnionBO;
//import com.becoda.bkms.union.service.UnionService;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class RoleUnionScaleUCCImpl implements IRoleUnionScaleUCC {
    private RoleUnionScaleService roleUnionScaleService;
//    private UnionService unionService;

    public RoleUnionScaleService getRoleUnionScaleService() {
        return roleUnionScaleService;
    }

    public void setRoleUnionScaleService(RoleUnionScaleService roleUnionScaleService) {
        this.roleUnionScaleService = roleUnionScaleService;
    }

//    public UnionService getUnionService() {
//        return unionService;
//    }
//
//    public void setUnionService(UnionService unionService) {
//        this.unionService = unionService;
//    }

    /**
     * @roseuid 447D530902BF
     */
    public void createRoleUnionScale(RoleUnionScaleBO roleUnionScale) throws RollbackableException {
        roleUnionScaleService.createRoleUnionScale(roleUnionScale);
    }

    /**
     * @roseuid 447D530902CE
     */
    public List queryRoleUnionScale(String roleID, String scaleFlag) throws RollbackableException {
        List list = roleUnionScaleService.queryRoleUnionScale(roleID, scaleFlag);
        //将机构名称写入
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RoleUnionScaleBO unionScalebo = (RoleUnionScaleBO) list.get(i);
//                UnionBO union = unionService.findUnionById(unionScalebo.getUnionId());
//                if (union != null)
//                    unionScalebo.setUnionName(union.getName());
            }
        }
        return list;
    }

    public void deleteRoleUnionScale(String roleID, String scaleFlag, String[] unionIds, String manageFlag) throws RollbackableException {
        for (int i = 0; i < unionIds.length; i++) {
            //权限收回
            if (PmsConstants.IS_SYS_MANAGER.equals(manageFlag)) {
//                UnionBO newUnion = unionService.findUnionById(unionIds[i]);
                Hashtable haveUnionScale = roleUnionScaleService.queryRoleUnionScaleByAdminRoleId(roleID, scaleFlag);
                if (haveUnionScale != null) {
                    Iterator it = haveUnionScale.values().iterator();
                    while (it.hasNext()) {
                        List list = (List) it.next();
                        for (int j = 0; j < list.size(); j++) {
                            RoleUnionScaleBO mUnionScale = (RoleUnionScaleBO) list.get(j);
//                            UnionBO munion = unionService.findUnionById(mUnionScale.getUnionId());
//                            if (munion.getTreeId().startsWith(newUnion.getTreeId())) {
//                                roleUnionScaleService.deleteRoleUnionScale(mUnionScale.getRoleId(), mUnionScale.getScaleFlag(), mUnionScale.getUnionId());
//                            }
                        }
                    }
                }
            }
            roleUnionScaleService.deleteRoleUnionScale(roleID, scaleFlag, unionIds[i]);
        }
    }

    /**
     * 检测是否某个工会组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkUnionSelected(String roleId, String scaleFlag, String unionId) throws RollbackableException {
//        return roleUnionScaleService.checkUnionSelected(roleId, scaleFlag, unionId);
//    }

    /**
     * 检测维护工会组织是否在查询工会组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryUnionScale(String roleId, String unionId) throws RollbackableException {
        return roleUnionScaleService.checkInQueryUnionScale(roleId, unionId);
    }
}
