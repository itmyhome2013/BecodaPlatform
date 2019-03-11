package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.pojo.bo.OperateBO;
import com.becoda.bkms.pms.pojo.bo.RoleOperateBO;
import com.becoda.bkms.pms.ucc.IRoleOperateUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import java.util.Hashtable;
import java.util.List;

public class OperateAction extends GenericAction {
    public String pInit() throws BkmsException {
        String pageFlag = Tools.filterNull(hrequest.getParameter("pageFlag"));  // "1" 角色页面编辑  "2" 用户页面编辑
        try {
            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
            List list = ucc.queryUserOperate(user.getUserId());
            request.setAttribute("pms_operate", list);
            String selectedNodes = "";
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                selectedNodes = queryRoleOperate();
            } else {                     //用户授权 得到有权限的权限ID
                selectedNodes = queryUserOperate();
            }
            request.setAttribute("selectedNodes", selectedNodes);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    /**
     * 修改操作权限
     * 说明：先删除、在添加。
     * 考虑：权限收回的实现,可以在删除之前，先比较与原来的权限的减少情况，如果有减少，?
     * 根据此管理员所创建的角色，按递归的方法检测。
     *
     * @return String
     * @roseuid 447AE933032A
     */
    public String updateRoleOperate() throws BkmsException {
        String paramId = Tools.filterNull(hrequest.getParameter("paramId"));
        String manageFlag = Tools.filterNull(hrequest.getParameter("manageFlag"));
        String selectedNodes = Tools.filterNull(hrequest.getParameter("selectedNodes"));
        if (paramId == null || "".equals(paramId)) {
            this.showMessage("参数错误,从重新登录!");
            return "";
        }
        try {
            selectedNodes = Tools.filterNull(selectedNodes);
            Hashtable hash = new Hashtable();
            if (!"".equals(selectedNodes)) {
                String[] operIds = selectedNodes.split(",");
                for (int i = 0; i < operIds.length; i++) {
                    RoleOperateBO roleOper = new RoleOperateBO();
                    String operId = operIds[i];
                    operId = operId.substring(1, operId.length() - 1);
                    roleOper.setOperateId(operId);
                    roleOper.setRoleId(paramId);
                    hash.put(operId, roleOper);
                }
            }
            IRoleOperateUCC ucc = (IRoleOperateUCC) getBean("pms_roleOperateUCC");
            ucc.updateRoleOperate(paramId, hash, manageFlag, user);
            request.setAttribute("selectedNodes", selectedNodes);
            this.showMessage("存储成功！");
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return pInit();
    }

    /**
     * 查询菜单权限
     */
    private String queryRoleOperate() throws BkmsException {
        String paramId = Tools.filterNull(hrequest.getParameter("paramId"));
        String selectedNodes = "";
        if (paramId == null || paramId.equals("")) {
            return selectedNodes;
        }
        IRoleOperateUCC ucc = (IRoleOperateUCC) getBean("pms_roleOperateUCC");
        List list = ucc.queryRoleOperate(paramId);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                selectedNodes += "~" + ((RoleOperateBO) list.get(i)).getOperateId() + "~";
            }
        }
        return selectedNodes;
    }

    private String queryUserOperate() throws BkmsException {
        String paramId = Tools.filterNull(hrequest.getParameter("paramId"));
        String selectedNodes = "";
        if (paramId == null || paramId.equals("")) {
            return selectedNodes;
        }
        IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
        List list = ucc.queryUserOperate(paramId);
        selectedNodes = "";
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                selectedNodes += "~" + ((OperateBO) list.get(i)).getOperateId() + "~";
            }
        }
        return selectedNodes;
    }
}
