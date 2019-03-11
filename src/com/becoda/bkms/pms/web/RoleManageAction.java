package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.vo.RoleInfoForm;
import com.becoda.bkms.pms.pojo.vo.UserPmsInfoVO;
import com.becoda.bkms.pms.ucc.IRoleManageUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import java.util.List;

public class RoleManageAction extends GenericPageAction {
    private RoleInfoForm form;
    /**
     * 查询角色列表
     *
     * @return
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     */
    public String queryRoleInfoList() throws BkmsException {
        try {
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            String roleName = Tools.filterNull(request.getParameter("roleNameQry"));
            List roleinfos = ucc.queryAllRoleInfos(vo, user.getUserId(), true, roleName);
            request.setAttribute("roleinfos", roleinfos);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    public String addRole() throws BkmsException {
        try {
            //如果是最底层管理员，禁止建系统管理员角色
//            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
//            List list = ucc.queryUserRoleInfo(user.getUserId());
//            if (list != null && list.size() > 0) {
//                //因为进入此模块的都是是系统管理员，则取第一个角色
//                RoleInfoBO sysRole = (RoleInfoBO) list.get(0);
//                //获得最上层管理员,
//                IRoleManageUCC roleucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
//                RoleInfoBO role1 = roleucc.findRoleInfo(sysRole.getCreator());
//                if (role1 != null) {
//                    role1 = roleucc.findRoleInfo(role1.getCreator());
//                    if (role1 != null) {
//                        request.setAttribute("lastLevelManageFlag", "1");
//                    }
//                }
//            }
            RoleInfoForm rf = new RoleInfoForm();
            Tools.copyProperties(form, rf);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "find";
    }

    public String editRole() throws BkmsException {
        String roleId = Tools.filterNull(request.getParameter("roleId"));
        try {
            //如果是最底层管理员，禁止建系统管理员角色
//            IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
//            List list = ucc.queryUserRoleInfo(user.getUserId());
            IRoleManageUCC roleucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
//            if (list != null && list.size() > 0) {
//                //因为进入此模块的都是是系统管理员，则取第一个角色
//                RoleInfoBO sysRole = (RoleInfoBO) list.get(0);
//                //获得最上层管理员,
//                RoleInfoBO role1 = roleucc.findRoleInfo(sysRole.getCreator());
//                if (role1 != null) {
//                    role1 = roleucc.findRoleInfo(role1.getCreator());
//                    if (role1 != null) {
//                        request.setAttribute("lastLevelManageFlag", "1");
//                    }
//                }
//            }
            RoleInfoBO rbo = roleucc.findRoleInfo(roleId);
            form=new RoleInfoForm();
            Tools.copyProperties(form, rbo);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "find";
    }

    public String saveRoleInfo() throws BkmsException {
        RoleInfoBO role = new RoleInfoBO();
        Tools.copyProperties(role, form);
        RoleInfoForm rif = (RoleInfoForm) form;
        String flag = Tools.filterNull(rif.getRoleId());
        try {
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            if ("".equals(flag)) { //添加
                IUserManageUCC userucc = (IUserManageUCC) getBean("pms_userManageUCC");
                List list = userucc.queryUserRoleInfo(user.getUserId());
                String sysRoleId;
                //因为进入此模块的都是是系统管理员，则取第一个角色
                if (list != null && list.size() > 0) {
                    sysRoleId = ((RoleInfoBO) list.get(0)).getRoleId();
                    role.setCreator(sysRoleId);
                }
                role.setCreateTime(Tools.getSysDate("yyyy-MM-dd"));
                ucc.createRole(role, user);
            } else {//修改
                // 如果是系统管理员,则判断是否被2个以上用户占用,如果占用,则不允许修改
                if (PmsConstants.IS_SYS_MANAGER.equals(role.getSysOper())) {
                    List list = ucc.queryRoleUser(flag);
                    if (list != null && list.size() > 1) {
                        String userNames = "";
                        for (int i = 0; i < list.size(); i++)
                            userNames += "{" + ((UserPmsInfoVO) list.get(i)).getName() + "},";
                        userNames = userNames.substring(0, userNames.length() - 1);
                        this.showMessage("管理员角色只允许分配给一个用户,此角色已经分配给了\\n" + userNames + list.size() + "用户,\\n不允许修改成管理员角色,修改失败!");
                        return "find";
                    }
                }
                ucc.updateRole(role, user);
            }
            this.showMessage("保存成功！");
            List roleinfos = ucc.queryAllRoleInfos(user.getUserId(), true);
            request.setAttribute("roleinfos", roleinfos);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";

    }

    public String deleteRoles() throws BkmsException {
        try {
            String[] ids = request.getParameterValues("selectItem");
            if (ids != null) {
                IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
                for (int i = 0; i < ids.length; i++) {
                    RoleInfoBO role = ucc.findRoleInfo(ids[i]);
                    if (PmsConstants.IS_SYS_MANAGER.equals(role.getSysOper())) {
                        //如果是管理员角色,还需检测此管理员级别的用户是否存在
                        if (ucc.checkCurrentUserBySysRoleId(ids[i])) {
                            this.showMessage("角色" + role.getRoleName() + "是管理员角色，其下已经设定了本级用户，请先通知相关管理员，\\n将本级用户撤销,然后再将相关管理员的角色清除，才可以删除本角色。");
                            List roleinfos = ucc.queryAllRoleInfos(user.getUserId(), true);
                            request.setAttribute("roleinfos", roleinfos);
                            return "list";
                        }
                        if (ucc.checkSubRoleBySysRoleId(ids[i])) {
                            this.showMessage("角色" + role.getRoleName() + "是管理员角色,其下已经设定了下属角色，请先将下属角色删除！");
                            List roleinfos = ucc.queryAllRoleInfos(user.getUserId(), true);
                            request.setAttribute("roleinfos", roleinfos);
                            return "list";
                        }
                    }

                    //检测角色是否被使用
                    List list = ucc.queryRoleUser(ids[i]);
                    if (list != null && list.size() > 0) {
                        this.showMessage("角色" + role.getRoleName() + "下已经分配了用户，不能删除！");
                        List roleinfos = ucc.queryAllRoleInfos(user.getUserId(), true);
                        request.setAttribute("roleinfos", roleinfos);
                        return "list";
                    }
                }
                ucc.deleteRoles(ids, user);
                List roleinfos = ucc.queryAllRoleInfos(user.getUserId(), true);
                request.setAttribute("roleinfos", roleinfos);
                this.showMessage("删除成功！");
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    public String saveAsRoleInfo() throws BkmsException {
        String newRoleName = Tools.filterNull(request.getParameter("newRoleName"));
        String sourceRoleId = Tools.filterNull(request.getParameter("roleId"));
        try {
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            ucc.saveAsRoleInfo(newRoleName, sourceRoleId, user);
            this.showMessage("复制成功,新角色名是" + newRoleName + ".");
            List roleinfos = ucc.queryAllRoleInfos(user.getUserId(), true);
            request.setAttribute("roleinfos", roleinfos);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    public String viewUser() throws BkmsException {
        String roleId = Tools.filterNull(request.getParameter("roleId"));
        try {
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            request.setAttribute("roleName", ucc.findRoleInfo(roleId).getRoleName());
            List userList = ucc.queryRoleUser(roleId);
            request.setAttribute("users", userList);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return "";
        }
        return "viewUser";
    }

    public String setRole() throws BkmsException {
        String roleId = Tools.filterNull(request.getParameter("roleId"));
        try {
            IRoleManageUCC ucc = (IRoleManageUCC) getBean("pms_roleManageUCC");
            request.setAttribute("title", "角色:" + ucc.findRoleInfo(roleId).getRoleName() + "—权限设置");
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "setRole";
    }

    public RoleInfoForm getForm() {
        return form;
    }

    public void setForm(RoleInfoForm form) {
        this.form = form;
    }
}
