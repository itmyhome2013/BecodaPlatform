package com.becoda.bkms.pms.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.*;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.pojo.vo.UserForm;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.List;

import org.apache.struts2.ServletActionContext;


/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-26
 * Time: 11:22:10
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoAction extends GenericPageAction {
    private  UserForm form;

    public UserForm getForm() {
        return form;
    }

    public void setForm(UserForm form) {
        this.form = form;
    }

    public String updateUserInfo() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String orgId = Tools.filterNull(request.getParameter("orgId"));
        String personType = Tools.filterNull(request.getParameter("personType"));
        String personName = Tools.filterNull(request.getParameter("personName"));
        try {
            //检测用户名是否重复
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            if (ucc.checkRepLoginName(form.getUserId(), form.getLoginName())) {
                this.showMessage("此用户名已经有人使用,修改失败!");
                return "";
            }
            User u = new User();
            Tools.copyProperties(u, form);
            String userName = Tools.filterNull(u.getLoginName());
            if ("".equals(userName)) {
                this.showMessage("用户名不能为空,修改失败!");
                return "";
            }
            String pwd = Tools.filterNull(u.getPassword());
            if ("".equals(pwd)) {
                this.showMessage("密码不能为空,修改失败!");
                return "";
            }
            u.setPassword(Tools.md5(pwd));
            ucc.updateUserInfo(u, user);
            this.showMessage("修改成功！");
            if (!"".equals(orgId)) {
                Org org = SysCacheTool.findOrgById(orgId);
                String treeId = org.getTreeId();
                if (treeId != null && !"".equals(treeId)) {
                    List personList = ucc.queryUserInfo(vo, personName, treeId, personType, user);
                    request.setAttribute("personList", personList);
                }
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    public String queryUserList() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String orgId = Tools.filterNull(request.getParameter("orgId"));
        String personType = Tools.filterNull(request.getParameter("personType"));
        String personName = Tools.filterNull(request.getParameter("personName"));
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
    public void hdrdsis()throws ParseException {
    	try  
        {  
            ProcessBuilder proc = new ProcessBuilder("D:\\Chrome_Portable_Xp580\\chrome.exe", "http://60.194.185.72:10005");  
            proc.start();  
        }  
        catch (Exception e)  
        {  
            System.out.println("Error executing progarm.");  
        }  
    }
}
