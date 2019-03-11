package com.becoda.bkms.pms.web;

//import com.becoda.bkms.ccyl.pojo.bo.CcylBO;
//import com.becoda.bkms.ccyl.ucc.ICcylUCC;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleCcylScaleBO;
import com.becoda.bkms.pms.ucc.IRoleCcylScaleUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-1
 * Time: 13:41:58
 * To change this template use File | Settings | File Templates.
 */
public class CcylScaleAction extends GenericAction {
    public String pageInit() throws BkmsException {
        try {
            String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
            String paramId = Tools.filterNull(request.getParameter("paramId"));
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleCcylScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserCcylScale(paramId);
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
    public void queryRoleCcylScale(String paramId) throws BkmsException {
        if (paramId != null && !paramId.equals("")) {
            IRoleCcylScaleUCC ucc = (IRoleCcylScaleUCC) getBean("pms_roleCcylScaleUCC");
            List queryCcylScale = ucc.queryRoleCcylScale(paramId, PmsConstants.QUERY_SCALE);
            List operateCcylScale = ucc.queryRoleCcylScale(paramId, PmsConstants.OPERATE_SCALE);
            request.setAttribute("queryCcylScale", queryCcylScale);
            request.setAttribute("operateCcylScale", operateCcylScale);
        }
    }

    /**
     * 查询用户机构范围
     */
    public void queryUserCcylScale(String paramId) throws BkmsException {
        if (paramId != null && !paramId.equals("")) {
            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
            List queryCcylScale = ucc.queryUserCcylScale(paramId, PmsConstants.QUERY_SCALE, PmsConstants.SCALE_USE);
            List operateCcylScale = ucc.queryUserCcylScale(paramId, PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_USE);
            request.setAttribute("queryCcylScale", queryCcylScale);
            request.setAttribute("operateCcylScale", operateCcylScale);
        }
    }

    public String deleteQueryCcylScale() throws BkmsException {
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String[] ids = request.getParameterValues("selectItem1");
        try {
            if (ids == null) return "";
            //如果删除查询范围，则检测维护范围中是否包含删除的机构或者字节点
            IRoleCcylScaleUCC ucc = (IRoleCcylScaleUCC) getBean("pms_roleCcylScaleUCC");
//            List list = ucc.queryRoleCcylScale(paramId, PmsConstants.OPERATE_SCALE);
//            if (list != null) {
//                ICcylUCC ccylucc = (ICcylUCC) getBean("ccyl_ccylUCC");
//                for (int i = 0; i < ids.length; i++) {
//                    CcylBO delCcyl = ccylucc.findGroupById(ids[i]);
//                    for (int j = 0; j < list.size(); j++) {
//                        RoleCcylScaleBO ccylScale = (RoleCcylScaleBO) list.get(j);
//                        CcylBO operCcyl = ccylucc.findGroupById(ccylScale.getCcylId());
//                        if (operCcyl.getTreeId().startsWith(delCcyl.getTreeId())) {
//                            this.showMessage("维护范围权限中已经包含了此查询范围内的团组织机构,请先删除维护范围中的团组织机构!");
//                            queryRoleCcylScale(paramId);
//                            return "";
//                        }
//                    }
//                }
//            }
            ucc.deleteRoleCcylScale(paramId, PmsConstants.QUERY_SCALE, ids, manageFlag, user);
            this.showMessage("删除成功！");
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleCcylScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserCcylScale(paramId);
            }
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    public String deleteOperateCcylScale() throws BkmsException {
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        try {
            String[] ids = request.getParameterValues("selectItem2");
            if (ids == null) return "";
            IRoleCcylScaleUCC ucc = (IRoleCcylScaleUCC) getBean("pms_roleCcylScaleUCC");
            ucc.deleteRoleCcylScale(paramId, PmsConstants.OPERATE_SCALE, ids, manageFlag, user);
            this.showMessage("删除成功！");
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleCcylScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserCcylScale(paramId);
            }
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }


    /**
     * 修改角色党务范围
     *
     * @return String
     * @roseuid 447AE8D20367
     */
    public String saveQueryCcylScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String selCcylId = Tools.filterNull(request.getParameter("selCcylId"));
        try {
            IRoleCcylScaleUCC ucc = (IRoleCcylScaleUCC) getBean("pms_roleCcylScaleUCC");
//            if (ucc.checkCcylSelected(paramId, scaleFlag, selCcylId)) {
//                this.showMessage("此团组织已添加过或者添加的团组织已经在范围之内，请添加其他团组织。");
//                queryRoleCcylScale(paramId);
//                return "";
//            }

            RoleCcylScaleBO ccylScale = new RoleCcylScaleBO();
            ccylScale.setCcylId(selCcylId);
            ccylScale.setScaleFlag(PmsConstants.QUERY_SCALE);
            ccylScale.setRoleId(paramId);
            //ccylScale.setPmsType();
            ucc.createRoleCcylScale(ccylScale, user);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleCcylScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserCcylScale(paramId);
            }
            this.showMessage("添加成功!");
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    public String saveOperateCcylScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String selCcylId = Tools.filterNull(request.getParameter("selCcylId"));
        try {
            IRoleCcylScaleUCC ucc = (IRoleCcylScaleUCC) getBean("pms_roleCcylScaleUCC");
//            if (ucc.checkCcylSelected(paramId, scaleFlag, selCcylId)) {
//                this.showMessage("此团组织已添加过或者添加的团组织已经在范围之内，请添加其他团组织。");
//                queryRoleCcylScale(paramId);
//                return "";
//            }

            //判断有权维护团组织是否在有权查询范围内
            if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
                if (!ucc.checkInQueryCcylScale(paramId, selCcylId)) {
                    this.showMessage("设置的维护团组织范围必须在查询团组织范围内，请重新选择！");
                    queryRoleCcylScale(paramId);
                    return "";
                }
            }
            RoleCcylScaleBO ccylScale = new RoleCcylScaleBO();
            ccylScale.setCcylId(selCcylId);
            ccylScale.setScaleFlag(PmsConstants.OPERATE_SCALE);
            ccylScale.setRoleId(paramId);
            //ccylScale.setPmsType();
            ucc.createRoleCcylScale(ccylScale, user);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleCcylScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserCcylScale(paramId);
            }
            this.showMessage("添加成功!");
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }
}

