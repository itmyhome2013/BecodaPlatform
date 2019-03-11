package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleUnionScaleBO;
import com.becoda.bkms.pms.ucc.IRoleUnionScaleUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
//import com.becoda.bkms.union.pojo.bo.UnionBO;
//import com.becoda.bkms.union.ucc.IUnionUCC;
import com.becoda.bkms.util.Tools;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-1
 * Time: 13:50:52
 * To change this template use File | Settings | File Templates.
 */
public class UnionScaleAction extends GenericAction {
    public String pageInit() throws BkmsException {
        try {
            String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
            String paramId = Tools.filterNull(request.getParameter("paramId"));
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleUnionScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserUnionScale(paramId);
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
    public void queryRoleUnionScale(String paramId) throws BkmsException {
        if (paramId != null && !paramId.equals("")) {
            IRoleUnionScaleUCC ucc = (IRoleUnionScaleUCC) getBean("pms_roleUnionScaleUCC");
            List queryUnionScale = ucc.queryRoleUnionScale(paramId, PmsConstants.QUERY_SCALE);
            List operateUnionScale = ucc.queryRoleUnionScale(paramId, PmsConstants.OPERATE_SCALE);
            request.setAttribute("queryUnionScale", queryUnionScale);
            request.setAttribute("operateUnionScale", operateUnionScale);
        }
    }

    /**
     * 查询用户机构范围
     */
    public void queryUserUnionScale(String paramId) throws BkmsException {
        if (paramId != null && !paramId.equals("")) {
            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
            List queryUnionScale = ucc.queryUserUnionScale(paramId, PmsConstants.QUERY_SCALE, PmsConstants.SCALE_USE);
            List operateUnionScale = ucc.queryUserUnionScale(paramId, PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_REFUSE);
            request.setAttribute("queryUnionScale", queryUnionScale);
            request.setAttribute("operateUnionScale", operateUnionScale);
        }
    }

    public String deleteQueryUnionScale() throws BkmsException {
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String[] ids = request.getParameterValues("selectItem1");
        try {
            if (ids == null) return "";
            //如果删除查询范围，则检测维护范围中是否包含删除的机构或者字节点
            IRoleUnionScaleUCC ucc = (IRoleUnionScaleUCC) getBean("pms_roleUnionScaleUCC");
//            List list = ucc.queryRoleUnionScale(paramId, PmsConstants.OPERATE_SCALE);
//            if (list != null) {
//                IUnionUCC unionucc = (IUnionUCC) getBean("union_unionUCC");
//                for (int i = 0; i < ids.length; i++) {
//                    UnionBO delUnion = unionucc.findUnionById(ids[i]);
//                    for (int j = 0; j < list.size(); j++) {
//                        RoleUnionScaleBO unionScale = (RoleUnionScaleBO) list.get(j);
//                        UnionBO operUnion = unionucc.findUnionById(unionScale.getUnionId());
//                        if (operUnion.getTreeId().startsWith(delUnion.getTreeId())) {
//                            this.showMessage("维护范围权限中已经包含了此查询范围内的工会组织机构,请先删除维护范围中的工会组织机构!");
//                            queryRoleUnionScale(paramId);
//                            return "";
//                        }
//                    }
//                }
//            }
            ucc.deleteRoleUnionScale(paramId, PmsConstants.QUERY_SCALE, ids, manageFlag);
            this.showMessage("删除成功！");
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleUnionScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserUnionScale(paramId);
            }
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    public String deleteOperateUnionScale() throws BkmsException {
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        try {
            String[] ids = request.getParameterValues("selectItem2");
            if (ids == null) return "";
            IRoleUnionScaleUCC ucc = (IRoleUnionScaleUCC) getBean("pms_roleUnionScaleUCC");
            ucc.deleteRoleUnionScale(paramId, PmsConstants.OPERATE_SCALE, ids, manageFlag);
            this.showMessage("删除成功！");
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleUnionScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserUnionScale(paramId);
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
    public String saveQueryUnionScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String selUnionId = Tools.filterNull(request.getParameter("selUnionId"));
        try {
            IRoleUnionScaleUCC ucc = (IRoleUnionScaleUCC) getBean("pms_roleUnionScaleUCC");
//            if (ucc.checkUnionSelected(paramId, scaleFlag, selUnionId)) {
//                this.showMessage("此工会组织已添加过或者添加的工会组织已经在范围之内，请添加其他工会组织。");
//                queryRoleUnionScale(paramId);
//                return "";
//            }

            RoleUnionScaleBO unionScale = new RoleUnionScaleBO();
            unionScale.setUnionId(selUnionId);
            unionScale.setScaleFlag(PmsConstants.QUERY_SCALE);
            unionScale.setRoleId(paramId);
            //unionScale.setPmsType();
            ucc.createRoleUnionScale(unionScale);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleUnionScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserUnionScale(paramId);
            }
            this.showMessage("添加成功!");
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    public String saveOperateUnionScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String selUnionId = Tools.filterNull(request.getParameter("selUnionId"));
        try {
            IRoleUnionScaleUCC ucc = (IRoleUnionScaleUCC) getBean("pms_roleUnionScaleUCC");
//            if (ucc.checkUnionSelected(paramId, scaleFlag, selUnionId)) {
//                this.showMessage("此工会组织已添加过或者添加的工会组织已经在范围之内，请添加其他工会组织。");
//                queryRoleUnionScale(paramId);
//                return "";
//            }

            //判断有权维护工会组织是否在有权查询范围内
            if ("1".equals(scaleFlag)) {
                if (!ucc.checkInQueryUnionScale(paramId, selUnionId)) {
                    this.showMessage("设置的维护工会组织范围必须在查询工会组织范围内，请重新选择！");
                    queryRoleUnionScale(paramId);
                    return "";
                }
            }
            RoleUnionScaleBO unionScale = new RoleUnionScaleBO();
            unionScale.setUnionId(selUnionId);
            unionScale.setScaleFlag(PmsConstants.OPERATE_SCALE);
            unionScale.setRoleId(paramId);
            //unionScale.setPmsType();
            ucc.createRoleUnionScale(unionScale);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRoleUnionScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserUnionScale(paramId);
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

