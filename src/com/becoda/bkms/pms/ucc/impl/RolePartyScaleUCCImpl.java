package com.becoda.bkms.pms.ucc.impl;

//import com.becoda.bkms.ccp.pojo.bo.PartyBO;
//import com.becoda.bkms.ccp.service.CcpService;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RolePartyScaleBO;
import com.becoda.bkms.pms.service.RolePartyScaleService;
import com.becoda.bkms.pms.ucc.IRolePartyScaleUCC;
import com.becoda.bkms.util.HrmsLog;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class RolePartyScaleUCCImpl implements IRolePartyScaleUCC {
    private RolePartyScaleService rolePartyScaleService;
//    private CcpService ccpService;

    public RolePartyScaleService getRolePartyScaleService() {
        return rolePartyScaleService;
    }

    public void setRolePartyScaleService(RolePartyScaleService rolePartyScaleService) {
        this.rolePartyScaleService = rolePartyScaleService;
    }

//    public CcpService getCcpService() {
//        return ccpService;
//    }
//
//    public void setCcpService(CcpService ccpService) {
//        this.ccpService = ccpService;
//    }

    /**
     * @roseuid 447D530902BF
     */
    public void createRolepartyScale(RolePartyScaleBO rolePartyScale, User user) throws RollbackableException {
        rolePartyScaleService.createRolePartyScale(rolePartyScale);
        if (PmsConstants.OPERATE_SCALE.equals(rolePartyScale.getScaleFlag())) {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + rolePartyScale.getRoleId() + "）增加党组织维护范围权限。");
        } else {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + rolePartyScale.getRoleId() + "）增加党组织查询范围权限。");
        }
    }

    /**
     * @roseuid 447D530902CE
     */
    public List queryRolePartyScale(String roleID, String scaleFlag) throws RollbackableException {
        List list = rolePartyScaleService.queryRolePartyScale(roleID, scaleFlag);
        //将机构名称写入
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RolePartyScaleBO partyScalebo = (RolePartyScaleBO) list.get(i);
//                PartyBO party = ccpService.findPartyById(partyScalebo.getPartyId());
//                if (party != null)
//                    partyScalebo.setPartyName(party.getName());
            }
        }
        return list;
    }

    public void deleteRolePartyScale(String roleID, String scaleFlag, String[] partyIds, String manageFlag, User user) throws RollbackableException {
        for (int i = 0; i < partyIds.length; i++) {
            //权限收回
            if (PmsConstants.IS_SYS_MANAGER.equals(manageFlag)) {
//                PartyBO newParty = ccpService.findPartyById(partyIds[i]);
                Hashtable havePartyScale = rolePartyScaleService.queryRolePartyScaleByAdminRoleId(roleID, scaleFlag);
                if (havePartyScale != null) {
                    Iterator it = havePartyScale.values().iterator();
                    while (it.hasNext()) {
                        List list = (List) it.next();
                        for (int j = 0; j < list.size(); j++) {
                            RolePartyScaleBO mPartyScale = (RolePartyScaleBO) list.get(j);
//                            PartyBO mparty = ccpService.findPartyById(mPartyScale.getPartyId());
//                            if (mparty.getTreeId().startsWith(newParty.getTreeId())) {
//                                rolePartyScaleService.deleteRolePartyScale(mPartyScale.getRoleId(), mPartyScale.getScaleFlag(), mPartyScale.getPartyId());
//                            }
                        }
                    }
                }
            }
            rolePartyScaleService.deleteRolePartyScale(roleID, scaleFlag, partyIds[i]);
        }
        if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + roleID + "）增加党组织维护范围权限。");
        } else {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给角色编号为（" + roleID + "）增加党组织查询范围权限。");
        }
    }

    /**
     * 检测是否某个党组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkPartySelected(String roleId, String scaleFlag, String partyId) throws RollbackableException {
//        return rolePartyScaleService.checkPartySelected(roleId, scaleFlag, partyId);
//    }

    /**
     * 检测维护党组织是否在查询党组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryPartyScale(String roleId, String partyId) throws RollbackableException {
        return rolePartyScaleService.checkInQueryPartyScale(roleId, partyId);
    }
}