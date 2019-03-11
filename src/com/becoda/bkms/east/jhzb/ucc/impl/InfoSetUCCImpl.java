package com.becoda.bkms.east.jhzb.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.pms.pojo.bo.RoleDataBO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.service.RoleDataService;
import com.becoda.bkms.pms.service.UserManageService;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.service.InfoSetService;
import com.becoda.bkms.sys.ucc.IInfoSetUCC;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-4
 * Time: 11:52:02
 * To change this template use File | Settings | File Templates.
 */
public class InfoSetUCCImpl implements IInfoSetUCC {
    private InfoSetService infoSetService;

    public InfoSetService getInfoSetService() {
        return infoSetService;
    }

    public void setInfoSetService(InfoSetService infoSetService) {
        this.infoSetService = infoSetService;
    }

    private UserManageService userManageService;
    private RoleDataService roleDataService;
//    private WageAPI wageAPI;

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    public RoleDataService getRoleDataService() {
        return roleDataService;
    }

    public void setRoleDataService(RoleDataService roleDataService) {
        this.roleDataService = roleDataService;
    }

//    public WageAPI getWageAPI() {
//        return wageAPI;
//    }
//
//    public void setWageAPI(WageAPI wageAPI) {
//        this.wageAPI = wageAPI;
//    }

    /**
     * @param infoSet
     */
    public void createInfoSet(InfoSetBO infoSet, String userId) throws BkmsException {
        infoSetService.createInfoSet(infoSet);
        //同步权限
        String isManage = userManageService.isSysManager(userId);
        if ("1".equals(isManage)) //如果是系统管理员,则返回
            return;

        String roleId;
        List lst = userManageService.queryUserRole(userId);
        if (lst != null && lst.size() > 0) {
            RoleInfoBO rbo = (RoleInfoBO) lst.get(0);
            roleId = rbo.getRoleId();  //管理员,都是一个角色
            RoleDataBO rdbo = new RoleDataBO();
            rdbo.setDataId(infoSet.getSetId());
            rdbo.setDataType(PmsConstants.INFO_SET_TYPE);
            rdbo.setPmsType(String.valueOf(PmsConstants.PERMISSION_WRITE));
            rdbo.setRoleId(roleId);
            roleDataService.createRoleData(rdbo);
        }
    }

    /**
     * @param infoSet
     */
    public void updateInfoSet(InfoSetBO infoSet) throws BkmsException {
        infoSetService.updateInfoSet(infoSet);
    }

    /**
     * @param setIds
     * @param status
     */
    public void makeStatus(String[] setIds, String status) throws BkmsException {
        try {
            for (int i = 0; i < setIds.length; i++) {
                infoSetService.makeStatus(setIds[i], status);
            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException("", e, this.getClass());
        }
    }

    /**
     * @param setId
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoSetBO
     */
    public InfoSetBO findInfoSet(String setId) throws BkmsException {
        return infoSetService.findInfoSet(setId);
    }

    /**
     * 得到所有有权限的TableVO
     *
     * @param sType 小类 如果为空，则查询所有
     * @return List object="TableVO"
     */
    public List queryRightSetlist(User user, String sType) throws BkmsException {
        try {
            InfoSetBO[] infosets = infoSetService.queryInfoSets(sType);
            if (infosets == null) return null;
            ArrayList list = new ArrayList();
            ArrayList right = new ArrayList();
            int len = infosets.length;
            PmsAPI pmsapi = new PmsAPI();
            for (int i = 0; i < len; i++) {
                String checkReadOnly = String.valueOf(pmsapi.checkInfoSet(user, infosets[i].getSetId()));
                list.add(infosets[i]);
                right.add(checkReadOnly);
            }
            list.add(0, right);
            return list;
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException("", e, this.getClass());
        }
    }

    /**
     * @param setIds
     */
    public void deleteInfoSets(String[] setIds, String userId) throws BkmsException {
        try {
            if (setIds != null && setIds.length != 0) {
                for (int i = 0; i < setIds.length; i++) {
                    infoSetService.deleteInfoSet(setIds[i]);
                }
            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
        //同步权限
        String isManage = userManageService.isSysManager(userId);
        if ("1".equals(isManage)) //如果是系统管理员,则返回
            return;

        String roleId;
        List lst = userManageService.queryUserRole(userId);
        if (lst != null && lst.size() > 0) {
            RoleInfoBO rbo = (RoleInfoBO) lst.get(0);
            roleId = rbo.getRoleId();  //管理员,都是一个角色
            roleDataService.deleteRoleDataByDataId(roleId, setIds);
        }
    }

    /**
     * 得到一个新的指标集名称
     *
     * @param bType 大类  A B C D
     * @return 指标集名称
     */
    public String getNewSetId(String bType, String setProperty) throws BkmsException {
        return infoSetService.getNewSetId(bType, setProperty);
    }

    /**
     * 检测指标集是否被其他模块使用
     *
     * @param setIds 指标集数组
     * @return "" 表示没有,!="" 表示被占用,返回值返回被占用的模块
     */
    public String checkAllowDelete(String[] setIds) throws BkmsException {
        String rtnValue = "";
        //检测新姿模块
//        for (int i = 0; i < setIds.length; i++) {
//            if (wageAPI.checkUsingWageInfoSet(setIds[i])) {
//                InfoSetBO set = SysCacheTool.findInfoSet(setIds[i]);
//                if ("".equals(rtnValue))
//                    rtnValue += "{" + set.getSetName() + "}";
//                else
//                    rtnValue += ",{" + set.getSetName() + "}";
//            }
//        }
//        if (!"".equals(rtnValue)) {
//            rtnValue = "指标集" + rtnValue + "已经被薪资模块使用.";
//        }

        //检测国标范围
        for (int i = 0; i < setIds.length; i++) {
            String id = setIds[i].substring(1, 4);
            if (Tools.checkInGBScale(Integer.parseInt(id))) {
                InfoSetBO set = SysCacheTool.findInfoSet(setIds[i]);
                if ("".equals(rtnValue))
                    rtnValue += "{" + set.getSetName() + "}";
                else
                    rtnValue += ",{" + set.getSetName() + "}";
            }
        }
        if (!"".equals(rtnValue)) {
            rtnValue = "指标集" + rtnValue + "属于国标范围.";
        }

        //检测系统范围
        for (int i = 0; i < setIds.length; i++) {
            String id = setIds[i].substring(1, 4);
            if (Tools.checkInProgramScale(Integer.parseInt(id))) {
                InfoSetBO set = SysCacheTool.findInfoSet(setIds[i]);
                if ("".equals(rtnValue))
                    rtnValue += "{" + set.getSetName() + "}";
                else
                    rtnValue += ",{" + set.getSetName() + "}";
            }
        }
        if (!"".equals(rtnValue)) {
            rtnValue = "指标集" + rtnValue + "属于系统使用指标集.";
        }


        return rtnValue;
    }

}
