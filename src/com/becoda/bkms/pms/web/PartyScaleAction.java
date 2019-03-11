package com.becoda.bkms.pms.web;

//import com.becoda.bkms.ccp.pojo.bo.PartyBO;
//import com.becoda.bkms.ccp.ucc.ICcpUCC;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RolePartyScaleBO;
import com.becoda.bkms.pms.ucc.IRolePartyScaleUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-25
 * Time: 9:26:08
 * To change this template use File | Settings | File Templates.
 */
public class PartyScaleAction extends GenericAction {
    public String pageInit() throws BkmsException {
        try {
            String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
            String paramId = Tools.filterNull(request.getParameter("paramId"));
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRolePartyScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserPartyScale(paramId);
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
    public void queryRolePartyScale(String paramId) throws BkmsException {
        if (paramId != null && !paramId.equals("")) {
            IRolePartyScaleUCC ucc = (IRolePartyScaleUCC) getBean("pms_rolePartyScaleUCC");
            List queryPartyScale = ucc.queryRolePartyScale(paramId, PmsConstants.QUERY_SCALE);
            List operatePartyScale = ucc.queryRolePartyScale(paramId, PmsConstants.OPERATE_SCALE);
            request.setAttribute("queryPartyScale", queryPartyScale);
            request.setAttribute("operatePartyScale", operatePartyScale);
        }
    }

    /**
     * 查询用户机构范围
     */
    public void queryUserPartyScale(String paramId) throws BkmsException {
        if (paramId != null && !paramId.equals("")) {
            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
            List queryPartyScale = ucc.queryUserPartyScale(paramId, PmsConstants.QUERY_SCALE, PmsConstants.SCALE_USE);
            List operatePartyScale = ucc.queryUserPartyScale(paramId, PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_USE);
            request.setAttribute("queryPartyScale", queryPartyScale);
            request.setAttribute("operatePartyScale", operatePartyScale);
        }
    }

    public String deleteQueryPartyScale() throws BkmsException {
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String[] ids = request.getParameterValues("selectItem1");
        try {
            if (ids == null) return "";
            //如果删除查询范围，则检测维护范围中是否包含删除的机构或者字节点
            IRolePartyScaleUCC ucc = (IRolePartyScaleUCC) getBean("pms_rolePartyScaleUCC");
//            List list = ucc.queryRolePartyScale(paramId, PmsConstants.OPERATE_SCALE);
//            if (list != null) {
//                ICcpUCC partyucc = (ICcpUCC) getBean("ccp_ccpUCC");
//                for (int i = 0; i < ids.length; i++) {
//                    PartyBO delParty = partyucc.findPartyById(ids[i]);
//                    for (int j = 0; j < list.size(); j++) {
//                        RolePartyScaleBO partyScale = (RolePartyScaleBO) list.get(j);
//                        PartyBO operParty = partyucc.findPartyById(partyScale.getPartyId());
//                        if (operParty.getTreeId().startsWith(delParty.getTreeId())) {
//                            this.showMessage("维护范围权限中已经包含了此查询范围内的党组织机构,请先删除维护范围中的党组织机构!");
//                            queryRolePartyScale(paramId);
//                            return "";
//                        }
//                    }
//                }
//            }
            ucc.deleteRolePartyScale(paramId, PmsConstants.QUERY_SCALE, ids, manageFlag, user);
            this.showMessage("删除成功！");
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRolePartyScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserPartyScale(paramId);
            }
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    public String deleteOperatePartyScale() throws BkmsException {
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        try {
            String[] ids = request.getParameterValues("selectItem2");
            if (ids == null) return "";
            IRolePartyScaleUCC ucc = (IRolePartyScaleUCC) getBean("pms_rolePartyScaleUCC");
            ucc.deleteRolePartyScale(paramId, PmsConstants.OPERATE_SCALE, ids, manageFlag, user);
            this.showMessage("删除成功！");
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRolePartyScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserPartyScale(paramId);
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
    public String saveQueryPartyScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String selPartyId = Tools.filterNull(request.getParameter("selPartyId"));
        try {
            IRolePartyScaleUCC ucc = (IRolePartyScaleUCC) getBean("pms_rolePartyScaleUCC");
//            if (ucc.checkPartySelected(paramId, scaleFlag, selPartyId)) {
//                this.showMessage("此党组织已添加过或者添加的党组织已经在范围之内，请添加其他党组织。");
//                queryRolePartyScale(paramId);
//                return "";
//            }

            RolePartyScaleBO partyScale = new RolePartyScaleBO();
            partyScale.setPartyId(selPartyId);
            partyScale.setScaleFlag(PmsConstants.QUERY_SCALE);
            partyScale.setRoleId(paramId);
            //partyScale.setPmsType();
            ucc.createRolepartyScale(partyScale, user);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRolePartyScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserPartyScale(paramId);
            }
            this.showMessage("添加成功!");
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    public String saveOperatePartyScale() throws BkmsException {
        String scaleFlag = Tools.filterNull(request.getParameter("scaleFlag"));
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String selPartyId = Tools.filterNull(request.getParameter("selPartyId"));
        try {
            IRolePartyScaleUCC ucc = (IRolePartyScaleUCC) getBean("pms_rolePartyScaleUCC");
//            if (ucc.checkPartySelected(paramId, scaleFlag, selPartyId)) {
//                this.showMessage("此党组织已添加过或者添加的党组织已经在范围之内，请添加其他党组织。");
//                queryRolePartyScale(paramId);
//                return "";
//            }

            //判断有权维护党组织是否在有权查询范围内
            if ("1".equals(scaleFlag)) {
                if (!ucc.checkInQueryPartyScale(paramId, selPartyId)) {
                    this.showMessage("设置的维护党组织范围必须在查询党组织范围内，请重新选择！");
                    queryRolePartyScale(paramId);
                    return "";
                }
            }
            RolePartyScaleBO partyScale = new RolePartyScaleBO();
            partyScale.setPartyId(selPartyId);
            partyScale.setScaleFlag(PmsConstants.OPERATE_SCALE);
            partyScale.setRoleId(paramId);
            //partyScale.setPmsType();
            ucc.createRolepartyScale(partyScale, user);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                queryRolePartyScale(paramId);
            } else {                     //用户授权 得到有权限的权限ID
                queryUserPartyScale(paramId);
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
