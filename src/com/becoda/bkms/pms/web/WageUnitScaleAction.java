package com.becoda.bkms.pms.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;
import com.becoda.bkms.pms.ucc.IRoleWageUnitScaleUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-25
 * Time: 9:25:53
 * To change this template use File | Settings | File Templates.
 */
public class WageUnitScaleAction extends GenericAction {
    public String pageInit() throws BkmsException {
        try {
            String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
            String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
            String paramId = Tools.filterNull(request.getParameter("paramId"));
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleWageUnitScale(paramId, scaleFlag);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserWageUnitScale(paramId, scaleFlag);
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    /**
     * 查询角色机构范围
     */
    private void queryRoleWageUnitScale(String paramId, String scaleFlag) throws BkmsException {
        if (paramId != null && !paramId.equals("")) {
            IRoleWageUnitScaleUCC ucc = (IRoleWageUnitScaleUCC) getBean("pms_roleWageUnitScaleUCC");
            List queryWageUnitScale = ucc.queryRoleWageUnitScale(paramId, PmsConstants.QUERY_SCALE, PmsConstants.SCALE_USE);
            List operateWageUnitScale = ucc.queryRoleWageUnitScale(paramId, PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_USE);
            request.setAttribute("queryWageUnitScale", queryWageUnitScale);
            request.setAttribute("operateWageUnitScale", operateWageUnitScale);
        }
    }

    /**
     * 查询用户机构范围
     */
    private void queryUserWageUnitScale(String paramId, String scaleFlag) throws BkmsException {
        if (paramId != null && !paramId.equals("") && scaleFlag != null && !scaleFlag.equals("")) {
            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
            List queryWageUnitScale = ucc.queryUserWageUnitScale(paramId, PmsConstants.QUERY_SCALE, PmsConstants.SCALE_USE);
            List operateWageUnitScale = ucc.queryUserWageUnitScale(paramId, PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_USE);
            request.setAttribute("queryWageUnitScale", queryWageUnitScale);
            request.setAttribute("operateWageUnitScale", operateWageUnitScale);
        }
    }

    /**
     * 删除有权机构权限
     *
     * @throws BkmsException
     */
    public String deleteHaveWageUnitScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String[] ids = null;
        if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
            ids = request.getParameterValues("selectItem1");
        else
            ids = request.getParameterValues("selectItem2");
        try {
            if (ids == null) return "";
            IRoleWageUnitScaleUCC ucc = (IRoleWageUnitScaleUCC) getBean("pms_roleWageUnitScaleUCC");
            if (PmsConstants.QUERY_SCALE.equals(scaleFlag)) {  //如果删除查询范围，则检测维护范围中是否包含删除的机构或者子节点
                List list = ucc.queryRoleWageUnitScale(paramId, PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_USE);
                if (list != null) {
                    for (int i = 0; i < ids.length; i++) {
                        Org delOrg = SysCacheTool.findOrgById(ids[i]);
                        for (int j = 0; j < list.size(); j++) {
                            RoleOrgScaleBO orgScale = (RoleOrgScaleBO) list.get(j);
                            Org operOrg = SysCacheTool.findOrgById(orgScale.getCondId());
                            if (operOrg.getTreeId().startsWith(delOrg.getTreeId())) {
                                this.showMessage("维护范围权限中已经包含了此查询范围内的机构,请先删除维护范围中的机构!");
                                queryRoleWageUnitScale(paramId, scaleFlag);
                                return "";
                            }
                        }
                    }
                }
            }
            //检测排除范围中是否包含了删除机构或者字节点
            List list = ucc.queryRoleWageUnitScale(paramId, scaleFlag, PmsConstants.SCALE_REFUSE);
            if (list != null) {
                for (int i = 0; i < ids.length; i++) {
                    Org delOrg = SysCacheTool.findOrgById(ids[i]);
                    for (int j = 0; j < list.size(); j++) {
                        RoleOrgScaleBO orgScale = (RoleOrgScaleBO) list.get(j);
                        Org operOrg = SysCacheTool.findOrgById(orgScale.getCondId());
                        if (operOrg.getTreeId().startsWith(delOrg.getTreeId())) {
                            this.showMessage("请先删除相关的排除权限!");
                            queryRoleWageUnitScale(paramId, scaleFlag);
                            return "";
                        }
                    }
                }
            }
            ucc.deleteRoleWageUnitScale(paramId, scaleFlag, PmsConstants.SCALE_USE, ids, manageFlag, user);
            this.showMessage("删除成功！");
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleWageUnitScale(paramId, scaleFlag);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserWageUnitScale(paramId, scaleFlag);
            }
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    /**
     * 修改角色有权机构范围
     *
     * @return String
     */
    public String saveWageUnitScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String selOrgId = Tools.filterNull(request.getParameter("selOrgId"));
        try {
            IRoleWageUnitScaleUCC ucc = (IRoleWageUnitScaleUCC) getBean("pms_roleWageUnitScaleUCC");
            if (ucc.checkWageUnitSelected(paramId, scaleFlag, PmsConstants.SCALE_USE, selOrgId)) {
                this.showMessage("此机构已添加过或者添加的机构已经在范围之内，请添加其他机构!");
                queryRoleWageUnitScale(paramId, scaleFlag);
                return "";
            }
            //判断有权维护机构是否在有权查询范围内 ,排除维护权限和排除查询范围不进行检测
            if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
                if (!ucc.checkInQueryWageUnitScale(paramId, selOrgId)) {
                    this.showMessage("设置的维护机构范围必须在查询机构范围内，请重新选择！");
                    queryRoleWageUnitScale(paramId, scaleFlag);
                    return "";
                }
            }
            RoleOrgScaleBO orgScale = new RoleOrgScaleBO();
            orgScale.setCodeId(PmsConstants.CODE_TYPE_WAGE);
            orgScale.setCondId(selOrgId);
            orgScale.setPmsType(PmsConstants.SCALE_USE);
            orgScale.setScaleFlag(scaleFlag);
            orgScale.setRoleId(paramId);
            ucc.createRoleWageUnitScale(orgScale, user, manageFlag);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleWageUnitScale(paramId, scaleFlag);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserWageUnitScale(paramId, scaleFlag);
            }
            this.showMessage("添加成功！");
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }
}
