package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.pms.ucc.IRoleManageUCC;
import java.util.List;

/**
 * defs:
 * User: yxm
 * Date: 2015-4-8
 * Time: 15:35:17
 */
public class SelRoleAction extends GenericPageAction {
    public String qryRole() throws BkmsException {
        try {
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            //获得大类和小类
            List roleList = ucc.queryChildRoleIncludeSelf(user.getUserId());
            request.setAttribute("roleList", roleList);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }
}
