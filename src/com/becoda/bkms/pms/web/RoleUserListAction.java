package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.ucc.IRoleManageUCC;
import com.becoda.bkms.util.Tools;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-19
 * Time: 10:23:46
 * To change this template use File | Settings | File Templates.
 */
public class RoleUserListAction extends GenericAction {

    public String queryRoleList() throws BkmsException {
        try {
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            List roleinfos = ucc.queryAllRoleInfos(user.getUserId(), true);
            request.setAttribute("roleinfos", roleinfos);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    public String deleteRoleUser() throws BkmsException {
        String roleId = Tools.filterNull(request.getParameter("roleId"));
        String[] ids = request.getParameterValues("selectItem");
        if (ids != null && ids.length > 0) {
            try {
                IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
                ucc.deleteRoleUser(ids, roleId, user);
                this.showMessage("删除成功！");
                List userList = ucc.queryRoleUser(roleId);
                request.setAttribute("users", userList);
                request.setAttribute("roleName", ucc.findRoleInfo(roleId).getRoleName());
            } catch (Exception e) {
                BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
                this.addActionError(he.getFlag()+he.getCause().getMessage());
            }
        }
        return "viewUser";
    }

    public String queryRoleUserList() throws BkmsException {
        try {
            String roleId = Tools.filterNull(request.getParameter("roleId"));
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            List userList = ucc.queryRoleUser(roleId);
            request.setAttribute("users", userList);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "";
    }
}
