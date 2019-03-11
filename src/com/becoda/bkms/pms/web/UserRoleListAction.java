package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-29
 * Time: 15:51:58
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleListAction extends GenericPageAction {
    public String pageInit() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String userId = Tools.filterNull(request.getParameter("userId"));
        try {
            //获得大类和小类
            List roleList = findUserRoleList(userId);
            request.setAttribute("roleList", roleList);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    private List findUserRoleList(String userId) throws BkmsException {
        List roleList = null;
        if (userId != null && !userId.equals("")) {
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            roleList = ucc.queryUserRoleInfo(userId);
        }
        return roleList;
    }
}
