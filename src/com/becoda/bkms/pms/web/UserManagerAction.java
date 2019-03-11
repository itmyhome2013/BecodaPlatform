package com.becoda.bkms.pms.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.vo.UserForm;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-27
 * Time: 9:10:25
 * To change this template use File | Settings | File Templates.
 */
public class UserManagerAction extends GenericPageAction {
    private UserForm form;

    public UserForm getForm() {
        return form;
    }

    public void setForm(UserForm form) {
        this.form = form;
    }

    /**
     * 查询用户
     *
     * @return
     */
    public String queryUserList() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String orgId = Tools.filterNull(request.getParameter("orgId"));
        String personType = Tools.filterNull(request.getParameter("personType"));
        String personName = Tools.filterNull(request.getParameter("personName"));
        if ((!"".equals(personName) || !"".equals(personType)) && "".equals(orgId)) {
            super.showMessage("请先选择相关机构！");
            return "list";
        }
        try {
            List list = doQuery(vo, user, orgId, personType, personName);
            request.setAttribute("personList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    private List doQuery(PageVO vo, User user, String orgId, String personType, String personName) {
        List personList = null;
        try {
            if (!"".equals(orgId)) {
                Org org = SysCacheTool.findOrgById(orgId);
                if (org != null) {
                    String treeId = org.getTreeId();
                    if (treeId != null && !"".equals(treeId)) {
//                        vo.setPageSize(100);
                        IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
                        personList = ucc.queryUserInfo(vo, personName, treeId, personType, user);
                    }
                }
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return personList;
    }

    /**
     * 修改用户状态为启用
     */
    public String makeStatusOpen() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String[] ids = request.getParameterValues("selectItem");
            String orgId = Tools.filterNull(request.getParameter("orgId"));
            String personType = Tools.filterNull(request.getParameter("personType"));
            String personName = Tools.filterNull(request.getParameter("personName"));
            if (ids == null) return "";
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            ucc.makeStatus(ids, true, user);
            List list = doQuery(vo, user, orgId, personType, personName);
            request.setAttribute("personList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    /**
     * * 修改用户状态为禁用
     */
    public String makeStatusBan() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String[] ids = request.getParameterValues("selectItem");
            String orgId = Tools.filterNull(request.getParameter("orgId"));
            String personType = Tools.filterNull(request.getParameter("personType"));
            String personName = Tools.filterNull(request.getParameter("personName"));
            if (ids == null) return "";
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            ucc.makeStatus(ids, false, user);
            List list = doQuery(vo, user, orgId, personType, personName);
            request.setAttribute("personList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    /**
     * 设置为本级用户
     */
    public String makeCurrentLevelUser() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String[] ids = request.getParameterValues("selectItem");
            String orgId = Tools.filterNull(request.getParameter("orgId"));
            String personType = Tools.filterNull(request.getParameter("personType"));
            String personName = Tools.filterNull(request.getParameter("personName"));
            if (ids == null) return "";
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            ucc.makeCurrentLevelUser(ids, querySysRoleId(), user);
            List list = doQuery(vo, user, orgId, personType, personName);
            request.setAttribute("personList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    /**
     * 设置为本级用户
     */
    public String cancelCurrentLevelUser() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String[] ids = request.getParameterValues("selectItem");
            String orgId = Tools.filterNull(request.getParameter("orgId"));
            String personType = Tools.filterNull(request.getParameter("personType"));
            String personName = Tools.filterNull(request.getParameter("personName"));
            if (ids == null) return "";
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            ucc.cancelCurrentLevelUser(ids, querySysRoleId(), user);
            List list = doQuery(vo, user, orgId, personType, personName);
            request.setAttribute("personList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    /**
     * 得到本级用户的角色ID
     */
    private String querySysRoleId() throws BkmsException {
        IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
        List list = ucc.queryUserRoleInfo(user.getUserId());
        RoleInfoBO role = (RoleInfoBO) list.get(0);
        return role.getRoleId();
    }

    /**
     * 查找用户信息
     *
     * @return
     * @throws BkmsException
     */
    public String findUserInfo() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String userId = Tools.filterNull(request.getParameter("userId"));
        try {
            if (userId != null && !"".equals(userId)) {
                IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
                User userInfo = ucc.findUserById(userId);
                form=new UserForm();
                Tools.copyProperties(form, userInfo);
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "find";
    }
    /**
     * 修改密码弹出框
     *
     * @return
     * @throws BkmsException
     */
    public String findUserInfoPwd() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String userId = Tools.filterNull(request.getParameter("userId"));
        try {
            if (userId != null && !"".equals(userId)) {
                IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
                User userInfo = ucc.findUserById(userId);
                form=new UserForm();
                Tools.copyProperties(form, userInfo);
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "upPwd";
    }
}
