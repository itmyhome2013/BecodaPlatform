package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-31
 * Time: 14:03:14
 * To change this template use File | Settings | File Templates.
 */
public class PasswordModifyAction extends GenericAction {
    public String updatePassword() throws BkmsException {
        String userName = Tools.filterNull(request.getParameter("userName"));
        String oldPassword = Tools.filterNull(request.getParameter("oldPassword"));
        String newPassword = Tools.filterNull(request.getParameter("newPassword"));
        String confirmPassword = Tools.filterNull(request.getParameter("confirmPassword"));
        if (!newPassword.equals(confirmPassword)) {
            this.showMessage("新密码和确认密码不同！请重新输入！");
            return "edit";
        }
        try {
            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
            user = ucc.findUserByUserName(userName);
            if (user == null) {
                this.showMessage("输入用户名无效！请重新输入！");
                return "edit";
            }

            if (!user.getPassword().equals(Tools.md5(oldPassword))) {
                this.showMessage("输入原密码无效！请重新输入！");
                return "edit";
            }
            user.setPassword(Tools.md5(newPassword));
            ucc.updateUserInfo(user, user);
            this.showMessage("修改成功！");
            return "edit";
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return "edit";
        }
    }
}
