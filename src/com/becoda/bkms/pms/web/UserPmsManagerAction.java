package com.becoda.bkms.pms.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.vo.UserPmsInfoVO;
import com.becoda.bkms.pms.ucc.IRoleManageUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.apache.struts2.ServletActionContext;

public class UserPmsManagerAction extends GenericPageAction {
    public String pageInit() throws BkmsException {
        String condiValue = Tools.filterNull(hrequest.getParameter("condiValue"));
        String condiType = Tools.filterNull(hrequest.getParameter("condiType"));
        String personType = Tools.filterNull(hrequest.getParameter("personType"));
        String orgId = Tools.filterNull(hrequest.getParameter("orgId"));
        String queryType = Tools.filterNull(hrequest.getParameter("queryType"));
//        String queryType = Tools.filterNull((String) session.getAttribute("pms_querytype2"));
        String personName = Tools.filterNull(hrequest.getParameter("personName"));
        if ((!"".equals(personName) || !"".equals(personType)) && "".equals(orgId)) {
            super.showMessageDetail("请先选择相关机构！");
            return "";
        }
        try {
            if (!"".equals(orgId)) {
//                session.setAttribute("pms_querytype2", "sysorg");
            } else {
                orgId = "-1";
            }

            List personList = null;
            if ("sysorg".equals(queryType)) {
                personList = querySysOperUserListByOrgId(orgId, personType, personName);
            } else if ("sysper".equals(queryType)) {
                personList = queryUserListByMulti(vo, user, session, condiValue, condiType, orgId, personType);
            } else if ("sysself".equals(queryType)) {
                personList = queryCurrentLevelUser(vo, user, session);
            }
            request.setAttribute("personList", personList);
//            session.setAttribute("pms_querytype2", queryType);
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    /**
     * 查询所有本级用户
     */
    private List queryCurrentLevelUser(PageVO vo, User user, HttpSession session) throws BkmsException {
        List list = null;
        try {
//            vo.setPageSize(100);
//            session.setAttribute("pms_querytype2", "sysself");
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            list = ucc.queryCurrentLevelUser(querySysRoleId(user.getUserId()), vo);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return list;
    }

    /**
     * 查询所有本级用户
     */
    public String queryCurrentLevelUsers() throws BkmsException {
        try {
//            vo.setPageSize(100);
//            session.setAttribute("pms_querytype2", "sysself");
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            List list = ucc.queryCurrentLevelUser(querySysRoleId(user.getUserId()), vo);
            request.setAttribute("personList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    /**
     * 查询所有本级角色
     */
    public String queryAllRoleInfos() throws BkmsException {
        try {
            vo.setPageSize(100);
            IRoleManageUCC ucc = (IRoleManageUCC) BkmsContext.getBean("pms_roleManageUCC");
            List list = ucc.queryAllRoleInfos(user.getUserId(), false);
            request.setAttribute("roleinfos", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "roleList";
    }

    /**
     * 按姓名或岗位类别查询当前管理员角色的用户
     */
    private List queryUserListByMulti(PageVO vo, User user, HttpSession session, String condiValue, String condiType, String orgId, String personType) throws BkmsException {
        List list = null;
        try {
            condiValue = Tools.filterNull(condiValue);
            if (condiValue != null && !"".equals(condiValue)) {
                Org org = SysCacheTool.findOrgById(orgId);
                String treeId = org.getTreeId();
//                vo.setPageSize(100);
                IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
                if ("personName".equals(condiType)) {
                    list = ucc.queryUserPmsInfo(condiValue, "", treeId, vo, personType, querySysRoleId(user.getUserId()), user);
                } else {
                    list = ucc.queryUserPmsInfo("", condiValue, treeId, vo, personType, querySysRoleId(user.getUserId()), user);
                }
                //  condiValue = "";
//                session.setAttribute("pms_querytype2", "sysper");
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return list;
    }

    /**
     * 按机构查询当前管理员角色的用户
     */
    private List querySysOperUserListByOrgId(String orgId, String personType, String personName) throws BkmsException {
        List list = null;
        try {
            Org org = SysCacheTool.findOrgById(orgId);
            if (org != null) {
                if (org.getSuperId().equals("-1")) {
                    list = queryCurrentLevelUser(vo, user, session); //如果是根接点，则查询所有本级用户，这样速度更快些
                } else {
                    String treeId = org.getTreeId();
                    if (treeId != null && !"".equals(treeId)) {
//                        vo.setPageSize(100);
                        IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
                        //update by hudl 修复授权时无法查询其他用户问题
                        list = ucc.queryUserPmsInfo(personName, "", treeId, vo, personType, null, user);
                    }
                }
//                session.setAttribute("pms_querytype2", "sysorg");
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return list;
    }

    /**
     * 得到本级用户的角色ID
     */
    private String querySysRoleId(String userId) throws BkmsException {
        IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
        List list = ucc.queryUserRoleInfo(userId);
        RoleInfoBO role = (RoleInfoBO) list.get(0);
        return role.getRoleId();
    }

    /**
     * 分配角色
     */
    public String assignRole() throws BkmsException {
        String selRoleId = Tools.filterNull(hrequest.getParameter("selRoleId"));
        try {
            String ids[] = selRoleId.split(",");
            String[] userids = hrequest.getParameterValues("selectItem");
            if (ids != null && ids.length > 0 && userids != null && userids.length > 0) {
                //判断管理员角色是否已经被其他用户占用
                //页面控制了只允许选择一个管理员,所以此处只需判断一个角色即可
                String items[] = ids[0].split(":");
                String sysOperFlag = items[1];
                if (PmsConstants.IS_SYS_MANAGER.equals(sysOperFlag)) {
                    //如果是管理员，则只允许选择一个用户，所以此处也选择一个用户即可
                    IRoleManageUCC roleucc = (IRoleManageUCC) BkmsContext.getBean("pms_roleManageUCC");
                    List list = roleucc.queryRoleUser(items[0]);
                    if (list != null && list.size() > 0) {
                        UserPmsInfoVO uservo = (UserPmsInfoVO) list.get(0);
                        if (!uservo.getPersonId().equals(userids[0])) {
                            this.showMessage("一个管理员角色只允许分配给一个用户,此管理员角色\\n已经分配给{" + uservo.getName() + "}使用,当前操作失败!");
                            pageInit();
                            return "list";
                        }
                    }
                }

                //去掉:号
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = ids[i].split(":")[0];
                }
                IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
                ucc.makeAssignRole(userids, ids, user);
            }
        } catch (BkmsException e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return pageInit();
    }

    /**
     * 清除用户角色
     */
    public String clearRole() throws BkmsException {
        try {
            String[] userids = hrequest.getParameterValues("selectItem");
            if (userids == null) return "";
            IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
            ucc.makeClearRole(userids, user);
        } catch (BkmsException e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return pageInit();
    }

    /**
     *
     */
    public String findUserRoleList() throws BkmsException {
        String personId = Tools.filterNull(hrequest.getParameter("personId"));
        try {
            List personList = null;
            if (!"".equals(personId)) {
                IUserManageUCC ucc = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
                personList = ucc.queryUserRoleInfo(personId);
            }
            request.setAttribute("roleList", personList);
        } catch (BkmsException e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "userRoleList";
    }

    public String viewUserPms() throws BkmsException {
        String personId = Tools.filterNull(hrequest.getParameter("roleId"));
        try {
            request.setAttribute("title", "用户:" + SysCacheTool.interpretPerson(personId) + "—权限浏览");
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "viewSet";
    }
}
