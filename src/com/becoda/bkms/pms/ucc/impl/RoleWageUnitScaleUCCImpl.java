package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.service.OrgService;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;
import com.becoda.bkms.pms.service.RoleWageUnitScaleService;
import com.becoda.bkms.pms.ucc.IRoleWageUnitScaleUCC;
import com.becoda.bkms.util.HrmsLog;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class RoleWageUnitScaleUCCImpl implements IRoleWageUnitScaleUCC {
    private RoleWageUnitScaleService roleWageUnitScaleService;
    private OrgService orgService;

    public RoleWageUnitScaleService getRoleWageUnitScaleService() {
        return roleWageUnitScaleService;
    }

    public void setRoleWageUnitScaleService(RoleWageUnitScaleService roleWageUnitScaleService) {
        this.roleWageUnitScaleService = roleWageUnitScaleService;
    }

    public OrgService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }

    /**
     */
    public void createRoleWageUnitScale(RoleOrgScaleBO roleOrgScale, User user, String managerFlag) throws RollbackableException {
//        //在存储维护权限时,需要检测当前管理员是否有排除权限,
//        // 如果有,再检测所添加的机构是否包含这个排除机构,
//        // 如果包含则须将排除权限插入到角色当中
//        if (PmsConstants.SCALE_USE.equals(roleOrgScale.getPmsType())) {  //如果是插入有权机构
//            List noWageUnitList = null;
//            if (PmsConstants.QUERY_SCALE.equals(roleOrgScale.getScaleFlag())) {  //如果存储的是查询范围，则取用户的查询排出机构
//                noWageUnitList = user.getHaveNoQueryWageUnitScale();
//            } else {
//                noWageUnitList = user.getHaveNoOperateWageUnitScale();
//            }
//            Org newOrg = SysCacheTool.findOrgById(roleOrgScale.getCondId());
//            if (newOrg != null && noWageUnitList != null) {
//                for (int i = 0; i < noWageUnitList.size(); i++) {
//                    if (((WageUnitBO) noWageUnitList.get(i)).getTreeId().startsWith(newOrg.getTreeId())) {
//                        RoleOrgScaleBO orgScaleBO = new RoleOrgScaleBO();
//                        Tools.copyProperties(orgScaleBO, roleOrgScale);
//                        orgScaleBO.setCondId(((WageUnitBO) noWageUnitList.get(i)).getUnitId());
//                        orgScaleBO.setPmsType(PmsConstants.SCALE_REFUSE);
//                        roleWageUnitScaleService.createRoleWageUnitScale(orgScaleBO);
//                    }
//                }
//            }
//        }
//        //如果插入的是排除权限，则需要检测被插入的角色是否是管理员
//        //如果是管理员，则需要给他的子用户以及子子用户加入相应的排除权限，
//        // 前提是，下级用户的有权机构必须包含子排除机构，
//        // 如果排除机构包含了下级用户的有权机构，则需要将下级用户的有权机构，以及所包含的相关排除机构删除
//        else {
//            if (PmsConstants.IS_SYS_MANAGER.equals(managerFlag)) {
//
//                //得到下级用户以及下下级用户的机构范围列表和排除列表
//                String roleId = roleOrgScale.getRoleId();
//                Org newOrg = SysCacheTool.findOrgById(roleOrgScale.getCondId());
//                Hashtable haveWageUnitScaleList = roleWageUnitScaleService.queryRoleWageUnitScaleByAdminRoleId(roleId, roleOrgScale.getScaleFlag(), PmsConstants.SCALE_USE);
//                Hashtable haveNoWageUnitScaleList = roleWageUnitScaleService.queryRoleWageUnitScaleByAdminRoleId(roleId, roleOrgScale.getScaleFlag(), PmsConstants.SCALE_REFUSE);
//                if (haveWageUnitScaleList != null) {
//                    Iterator it = haveWageUnitScaleList.values().iterator();
//                    while (it.hasNext()) {
//                        List list = (List) it.next();
//                        for (int i = 0; i < list.size(); i++) {
//                            RoleOrgScaleBO mWageUnitScale = (RoleOrgScaleBO) list.get(i);
//                            Org mOrg = SysCacheTool.findOrgById(mWageUnitScale.getCondId());
//                            if (newOrg.getTreeId().startsWith(mOrg.getTreeId()))  //如果有权机构包含此排除机构，则给相应的角色添加排除机构
//                            {
//                                //首先查询排除机构中是否已经包含了此机构，如果没包含则添加
//                                boolean flag = false;  //排除机构是否包含标志
//                                List noList = (List) haveNoWageUnitScaleList.get(mWageUnitScale.getRoleId());
//                                if (noList != null) {
//                                    for (int j = 0; j < noList.size(); j++) {
//                                        if (((RoleOrgScaleBO) noList.get(j)).getCondId().equals(roleOrgScale.getCondId())) {
//                                            flag = true;
//                                            continue;
//                                        }
//                                    }
//                                }
//                                if (!flag) {
//                                    RoleOrgScaleBO newOrgScaleBO = new RoleOrgScaleBO();
//                                    Tools.copyProperties(newOrgScaleBO, roleOrgScale);
//                                    newOrgScaleBO.setRoleId(mWageUnitScale.getRoleId());
//                                    roleWageUnitScaleService.createRoleWageUnitScale(newOrgScaleBO);
//                                    continue;
//                                }
//                            } else
//                            if (mOrg.getTreeId().startsWith(newOrg.getTreeId())) {  //如果排除机构的范围比有权机构范围大，则需要将相应的有权机构和排除机构删除
//                                roleWageUnitScaleService.deleteRoleWageUnitScale(mWageUnitScale.getRoleId(), mWageUnitScale.getScaleFlag(), mWageUnitScale.getPmsType(), mWageUnitScale.getCondId());
//                                //检测是否保函下级的排除机构，如果有，则删除掉
//                                List noList = (List) haveNoWageUnitScaleList.get(mWageUnitScale.getRoleId());
//                                if (noList != null) {
//                                    for (int j = 0; j < noList.size(); j++) {
//                                        RoleOrgScaleBO mNoWageUnitScale = (RoleOrgScaleBO) noList.get(j);
//                                        Org mNoOrg = SysCacheTool.findOrgById(mNoWageUnitScale.getCondId());
//                                        if (mNoOrg.getTreeId().startsWith(mOrg.getTreeId())) {
//                                            roleWageUnitScaleService.deleteRoleWageUnitScale(mNoWageUnitScale.getRoleId(), mNoWageUnitScale.getScaleFlag(), mNoWageUnitScale.getPmsType(), mNoWageUnitScale.getCondId());
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        roleWageUnitScaleService.createRoleWageUnitScale(roleOrgScale);
//        if (PmsConstants.OPERATE_SCALE.equals(roleOrgScale.getScaleFlag())) {
//            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + roleOrgScale.getRoleId() + "）增加薪酬机构维护范围权限。");
//        } else {
//            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + roleOrgScale.getRoleId() + "）增加薪酬机构查询范围权限。");
//        }

    }

    /**
     */
    public List queryRoleWageUnitScale(String roleID, String scaleFlag, String pmsType) throws RollbackableException {
        List list = roleWageUnitScaleService.queryRoleWageUnitScale(roleID, scaleFlag, pmsType);
        //将机构名称写入
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RoleOrgScaleBO orgScalebo = (RoleOrgScaleBO) list.get(i);
                Org org = SysCacheTool.findOrgById(orgScalebo.getCondId());
                if (org != null) {
                    OrgBO tmpOrg = orgService.findOrgByDept(org.getOrgId());
                    //如果当前机构=返回机构，则代表是外设机构，不等，则代表当前机构是部门
                    if (tmpOrg != null) {
                        if (tmpOrg.getOrgId().equals(org.getOrgId()))
                            orgScalebo.setOrgName(org.getName());
                        else
                            orgScalebo.setOrgName(tmpOrg.getName() + "—" + org.getName());
                    } else {
                        orgScalebo.setOrgName(org.getName());
                    }

                }
            }
        }
        return list;
    }

    /**
     * 删除机构范围权限
     * 权限收回：如果删除有权机构时，考虑下级用户是否包含此机构构或者下级机构，
     * 如果有，需要将其连同相关的排除机构删除
     *
     * @param roleID
     * @param scaleFlag
     * @param pmsType
     * @param orgIds
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public void deleteRoleWageUnitScale(String roleID, String scaleFlag, String pmsType, String[] orgIds, String managerFlag, User user) throws RollbackableException {
        //删除机构列表 ,用户权限同步,内部存储的是RoleOrgScaleBO
        for (int i = 0; i < orgIds.length; i++) {
            if (PmsConstants.SCALE_USE.equals(pmsType) && PmsConstants.IS_SYS_MANAGER.equals(managerFlag)) {
                Org newOrg = SysCacheTool.findOrgById(orgIds[i]);
                Hashtable haveWageUnitScale = roleWageUnitScaleService.queryRoleWageUnitScaleByAdminRoleId(roleID, scaleFlag, PmsConstants.SCALE_USE);
                Hashtable haveNoWageUnitScale = roleWageUnitScaleService.queryRoleWageUnitScaleByAdminRoleId(roleID, scaleFlag, PmsConstants.SCALE_REFUSE);
                if (haveWageUnitScale != null) {
                    Iterator it = haveWageUnitScale.values().iterator();
                    while (it.hasNext()) {
                        List list = (List) it.next();
                        for (int j = 0; j < list.size(); j++) {
                            RoleOrgScaleBO mWageUnitScale = (RoleOrgScaleBO) list.get(j);
                            Org mOrg = SysCacheTool.findOrgById(mWageUnitScale.getCondId());
                            if (mOrg.getTreeId().startsWith(newOrg.getTreeId())) {
                                roleWageUnitScaleService.deleteRoleWageUnitScale(mWageUnitScale.getRoleId(), mWageUnitScale.getScaleFlag(), mWageUnitScale.getPmsType(), mWageUnitScale.getCondId());
                                if (haveNoWageUnitScale != null) {
                                    List nolist = (List) haveNoWageUnitScale.get(mWageUnitScale.getRoleId());
                                    if (nolist != null) {
                                        for (int k = 0; k < nolist.size(); k++) {
                                            RoleOrgScaleBO mNoWageUnitScale = (RoleOrgScaleBO) nolist.get(k);
                                            Org mNoOrg = SysCacheTool.findOrgById(mNoWageUnitScale.getCondId());
                                            if (mNoOrg.getTreeId().startsWith(mOrg.getTreeId())) {
                                                roleWageUnitScaleService.deleteRoleWageUnitScale(mNoWageUnitScale.getRoleId(), mNoWageUnitScale.getScaleFlag(), mNoWageUnitScale.getPmsType(), mNoWageUnitScale.getCondId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            roleWageUnitScaleService.deleteRoleWageUnitScale(roleID, scaleFlag, pmsType, orgIds[i]);
        }
        if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + roleID + "）增加薪酬机构维护范围权限。");
        } else {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + roleID + "）增加薪酬机构查询范围权限。");
        }
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
        return roleWageUnitScaleService.checkWageUnitSelected(roleId, scaleFlag, pmsType, orgId);
    }

    /**
     * 检测排除机构是否在有权机构范围
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return true 在，false 不在
     */
    public boolean checkInHaveWageUnitScale(String roleId, String scaleFlag, String orgId) throws RollbackableException {
        return roleWageUnitScaleService.checkInHaveWageUnitScale(roleId, scaleFlag, orgId);
    }

    /**
     * 检测维护机构是否在查询机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryWageUnitScale(String roleId, String orgId) throws RollbackableException {
        return roleWageUnitScaleService.checkInQueryWageUnitScale(roleId, orgId);
    }
}
