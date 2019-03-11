package com.becoda.bkms.pms.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.ucc.IRoleManageUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-28
 * Time: 9:51:04
 * To change this template use File | Settings | File Templates.
 */
public class CreditManageAction extends GenericPageAction {
    public String goBack() throws BkmsException {
        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        if ("".equals(pageFlag)) pageFlag = "1";  // "1" 角色页面编辑  "2" 用户页面编辑
        if ("1".equals(pageFlag)) {
            try {
                IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
                String roleName = Tools.filterNull(request.getParameter("roleNameQry"));
                List roleinfos = ucc.queryAllRoleInfos(vo, user.getUserId(), true, roleName);
                request.setAttribute("roleinfos", roleinfos);
            } catch (Exception e) {
                BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
              this.addActionError(he.getFlag()+he.getCause().getMessage());
            }
            return "rolelist";
        } else {
            String condiValue = Tools.filterNull(request.getParameter("condiValue"));
            String condiType = Tools.filterNull(request.getParameter("condiType"));
            String personType = Tools.filterNull(request.getParameter("personType"));
            String orgId = Tools.filterNull(request.getParameter("orgId"));
            String queryType = Tools.filterNull(request.getParameter("queryType"));
            String personName = Tools.filterNull(request.getParameter("personName"));
//            String queryType = Tools.filterNull((String) session.getAttribute("pms_querytype2"));
            try {
//                if (!"".equals(orgId)) {
//                    session.setAttribute("pms_querytype2", "sysorg");
//                }
                List personList = null;
                if ("sysorg".equals(queryType)) {
                    personList = querySysOperUserListByOrgId(orgId, personType, personName);
                } else if ("sysper".equals(queryType)) {
                    personList = queryUserListByMulti(condiValue, condiType, orgId, personType);
                } else if ("sysself".equals(queryType)) {
                    personList = queryCurrentLevelUser();
                }
                request.setAttribute("personList", personList);
//                session.setAttribute("pms_querytype2", queryType);
            } catch (Exception e) {
                //e.printStackTrace();
                BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
                this.addActionError(he.getFlag()+he.getCause().getMessage());
            }
            return "userlist";
        }
    }

    /**
     * 查询所有本级用户
     */
    private List queryCurrentLevelUser() throws BkmsException {
        List list = null;
        try {
//            vo.setPageSize(100);
//            session.setAttribute("pms_querytype2", "sysself");
            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
            list = ucc.queryCurrentLevelUser(querySysRoleId(user.getUserId()), vo);
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
            if (orgId == null || "".equals(orgId)) {
                super.showMessageDetail("请先选择相关机构！");
                return list;
            }
            Org org = SysCacheTool.findOrgById(orgId);
            if (org.getSuperId().equals("-1")) {
                queryCurrentLevelUser(); //如果是根接点，则查询所有本级用户，这样速度更快些
            } else {
                String treeId = org.getTreeId();
                if (treeId != null && !"".equals(treeId)) {
//                    vo.setPageSize(100);
                    IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
                    list = ucc.queryUserPmsInfo(personName, "", treeId, vo, personType, querySysRoleId(user.getUserId()), user);
                }
            }
//            session.setAttribute("pms_querytype2", "sysorg");
        }
        catch (Exception e) {
           BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
                this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return list;
    }

    /**
     * 按姓名或岗位类别查询当前管理员角色的用户
     */
    private List queryUserListByMulti(String condiValue, String condiType, String orgId, String personType) throws BkmsException {
        List list = null;
        try {
            condiValue = Tools.filterNull(condiValue);
            if (orgId == null || "".equals(orgId)) {
                super.showMessageDetail("请先选择相关机构！");
                return list;
            }
            if (condiValue != null && !"".equals(condiValue)) {
                Org org = SysCacheTool.findOrgById(orgId);
                String treeId = org.getTreeId();
//                vo.setPageSize(100);
                IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
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
     * 得到本级用户的角色ID
     */
    private String querySysRoleId(String userId) throws BkmsException {
        IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
        List list = ucc.queryUserRoleInfo(userId);
        RoleInfoBO role = (RoleInfoBO) list.get(0);
        return role.getRoleId();
    }
}
