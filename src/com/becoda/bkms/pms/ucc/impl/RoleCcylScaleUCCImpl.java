package com.becoda.bkms.pms.ucc.impl;

//import com.becoda.bkms.ccyl.pojo.bo.CcylBO;
//import com.becoda.bkms.ccyl.service.CcylService;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleCcylScaleBO;
import com.becoda.bkms.pms.service.RoleCcylScaleService;
import com.becoda.bkms.pms.ucc.IRoleCcylScaleUCC;
import com.becoda.bkms.util.HrmsLog;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class RoleCcylScaleUCCImpl implements IRoleCcylScaleUCC {
    private RoleCcylScaleService roleCcylScaleService;
//    private CcylService ccylService;

    public RoleCcylScaleService getRoleCcylScaleService() {
        return roleCcylScaleService;
    }

    public void setRoleCcylScaleService(RoleCcylScaleService roleCcylScaleService) {
        this.roleCcylScaleService = roleCcylScaleService;
    }

//    public CcylService getCcylService() {
//        return ccylService;
//    }
//
//    public void setCcylService(CcylService ccylService) {
//        this.ccylService = ccylService;
//    }

    /**
     * @roseuid 447D530902BF
     */
    public void createRoleCcylScale(RoleCcylScaleBO roleCcylScale, User user) throws RollbackableException {
        roleCcylScaleService.createRoleCcylScale(roleCcylScale);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "为角色编号为（" + roleCcylScale.getRoleId() + "）的角色增加了团组织权限。");
    }

    /**
     * @roseuid 447D530902CE
     */
    public List queryRoleCcylScale(String roleID, String scaleFlag) throws RollbackableException {
        List list = roleCcylScaleService.queryRoleCcylScale(roleID, scaleFlag);
        //将机构名称写入
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RoleCcylScaleBO ccylScalebo = (RoleCcylScaleBO) list.get(i);
//                CcylBO ccyl = ccylService.findGroupById(ccylScalebo.getCcylId());
//                if (ccyl != null)
//                    ccylScalebo.setCcylName(ccyl.getName());
            }
        }
        return list;
    }

    public void deleteRoleCcylScale(String roleID, String scaleFlag, String[] ccylIds, String manageFlag, User user) throws RollbackableException {
        for (int i = 0; i < ccylIds.length; i++) {
            //权限收回
            if (PmsConstants.IS_SYS_MANAGER.equals(manageFlag)) {
//                CcylBO newCcyl = ccylService.findGroupById(ccylIds[i]);
                Hashtable haveCcylScale = roleCcylScaleService.queryRoleCcylScaleByAdminRoleId(roleID, scaleFlag);
                if (haveCcylScale != null) {
                    Iterator it = haveCcylScale.values().iterator();
                    while (it.hasNext()) {
                        List list = (List) it.next();
                        for (int j = 0; j < list.size(); j++) {
                            RoleCcylScaleBO mCcylScale = (RoleCcylScaleBO) list.get(j);
//                            CcylBO mccyl = ccylService.findGroupById(mCcylScale.getCcylId());
//                            if (mccyl.getTreeId().startsWith(newCcyl.getTreeId())) {
//                                roleCcylScaleService.deleteRoleCcylScale(mCcylScale.getRoleId(), mCcylScale.getScaleFlag(), mCcylScale.getCcylId());
//                            }
                        }
                    }
                }
            }
            roleCcylScaleService.deleteRoleCcylScale(roleID, scaleFlag, ccylIds[i]);
        }
        if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "删除了角色编号为（" + roleID + "）的角色的团组织查询权限。");
        else
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "删除了角色编号为（" + roleID + "）的角色的团组织维护权限。");
    }

    /**
     * 检测是否某个团组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkCcylSelected(String roleId, String scaleFlag, String ccylId) throws RollbackableException {
//        return roleCcylScaleService.checkCcylSelected(roleId, scaleFlag, ccylId);
//    }

    /**
     * 检测维护团组织是否在查询团组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryCcylScale(String roleId, String ccylId) throws RollbackableException {
        return roleCcylScaleService.checkInQueryCcylScale(roleId, ccylId);
    }
}

