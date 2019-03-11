package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;
import com.becoda.bkms.pms.pojo.vo.UserInfoVO;
import com.becoda.bkms.pms.pojo.vo.UserPmsInfoVO;
import com.becoda.bkms.pms.service.UserManageService;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
//import com.becoda.bkms.util.Arith;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.Tools;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class UserManageUCCImpl implements IUserManageUCC {
    private UserManageService userManageService;

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    public User findUserByUserName(String userName) throws RollbackableException {
        return userManageService.findUserByUserName(userName);
    }

    /**
     * @param userID
     * @param userName
     *  447D5309032C
     */
//    public void updateUserInfo(String userID, String userName, String password) throws RollbackableException {
//        try {
//            password = Tools.md5(password);
//        } catch (Exception e) {
//            throw new RollbackableException("密码转换错误!修改失败!");
//        }
//        userManageService.updateUserInfo(userID, userName, password);
//    }

//    public void updateUserInfo(String userID, String userName) throws RollbackableException {
//        userManageService.updateUserInfo(userID, userName);
//    }

    /**
     * @param user 447D5309037A
     */
    public void updateUserInfo(User user, User operator) throws RollbackableException {
        userManageService.updateUserInfo(user);
        if (user.getUserId().equals(operator.getUserId()))
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, operator.getName() + "修改了自己的密码。");
        else
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, operator.getName() + "修改了用户编号为（" + user.getUserId() + "）的信息。");
    }

    /**
     * 检查信息集和信项权限
     * 方法：通过SQL语句查询出当前用户下所有角色的信息项和信息集的最大权限
     *
     * @param userID 用户ID
     * @param sType  指标集小类别
     * @return Hashtable   key 指标集 或者指标项     object 1, 2,3
     *         4479E2A80343
     */
    public Hashtable queryUserDataByStype(String userID, String sType) throws RollbackableException {
        return userManageService.queryUserDataByStype(userID, sType);
    }

    /**
     * @param userID
     * @return com.becoda.bkms.pms.pojo.bo.OperateBO[]
     *         447D530A000F
     */
    public List queryUserOperate(String userID) throws RollbackableException {
        return userManageService.queryUserOperate(userID);
    }

    /**
     * @param userID
     * @param flag
     * @return com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO[]
     *         447D530A003E
     */
    public List queryUserOrgScale(String userID, String flag, String type) throws RollbackableException {
        List list = userManageService.queryUserOrgScale(userID, flag, type);
        // 转换成RoleOrgScaleBO roleId="";
        List list2 = new ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RoleOrgScaleBO rorgBO = new RoleOrgScaleBO();
                //rorgBO.setCodeId("DEPT");
                Org org = (Org) list.get(i);
                rorgBO.setCondId(org.getOrgId());
                rorgBO.setOrgName(org.getName());
                list2.add(rorgBO);
            }
        }
        return list2;
    }

    /**
     * @param userID
     * @param flag
     * @return com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO[]
     *         447D530A003E
     */
    public List queryUserWageUnitScale(String userID, String flag, String type) throws RollbackableException {
//        List list = userManageService.queryUserWageUnitScale(userID, flag, type);
//        // 转换成RoleOrgScaleBO roleId="";
//        List list2 = new ArrayList();
//        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                RoleOrgScaleBO rorgBO = new RoleOrgScaleBO();
//                WageUnitBO org = (WageUnitBO) list.get(i);
//                rorgBO.setCondId(org.getUnitId());
//                rorgBO.setOrgName(org.getName());
//                list2.add(rorgBO);
//            }
//        }
//        return list2;
        return null;
    }

    public List queryUserPartyScale(String userId, String flag, String type) throws RollbackableException {
        return userManageService.queryUserPartyScale(userId, flag, type);
    }

    public List queryUserCcylScale(String userId, String flag, String type) throws RollbackableException {
        return userManageService.queryUserCcylScale(userId, flag, type);
    }

    public List queryUserUnionScale(String userId, String flag, String type) throws RollbackableException {
        return userManageService.queryUserUnionScale(userId, flag, type);
    }

    /**
     * @param roleId
     * @return com.becoda.bkms.pms.pojo.bo.UserInfoBO[]
     *         447D530A008C
     */
    public List queryCurrentLevelUser(String roleId, PageVO vo) throws RollbackableException {
        List list = userManageService.queryCurrentLevelUser(roleId, vo);
        translateCode(list);
        return list;
    }

    /**
     * @param userIds
     * @param status  447D530A00BB
     */
    public void makeStatus(String[] userIds, boolean status, User user) throws RollbackableException {
        String superManageId = userManageService.getSuperManagerId();
        for (int i = 0; i < userIds.length; i++) {
            userManageService.makeStatus(userIds[i], status, superManageId);
        }
        if (status)
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "启用了（" + userIds.length + "）名用户。");
        else
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "禁用了（" + userIds.length + "）名用户。");
    }

    /**
     * 登录验证
     *
     * @param userName
     * @param password
     * @return
     * @throws RollbackableException
     */
    public User makeVerifyLogon(String userName, String password, HttpSession session) throws RollbackableException {
        return userManageService.verifyLogon(userName, password, session);
    }

    public User makeUnionformVerifyLogon(String personCode,String password, HttpSession session) throws RollbackableException {
        try {
            makeVerifyLogon(personCode,password, session);
            Person p = SysCacheTool.findPersonByCode(personCode);
            if (p == null) {
                throw new RollbackableException("用户不是管理系统用户，不能登录管理系统", this.getClass());
            }
            User user = userManageService.findUserById(p.getPersonId());
            if (!PmsConstants.STATUS_OPEN.equals(user.getStatus())) {
                throw new RollbackableException("用户尚未开通管理系统的权限，不能登录管理系统", this.getClass());
            }
            userManageService.loadUserInfo(user, session);
            return user;
        } catch (RollbackableException de) {
            //将DAO中抛出的DAOException包装成一个BkmsException异常抛给action
            throw new RollbackableException(de, this.getClass());
        } catch (Exception e) {
            throw new RollbackableException("登录验证失败", e, this.getClass());
        }
    }

    /**
     * 报表登陆
     * add by yejb 061223
     *
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws Exception
     */
    public User makeVerifyLogonRep(String userName, String password, HttpSession session) throws Exception {
        User user = userManageService.verifyLogonRep(userName, password);
//        //计数
//        String path = session.getServletContext().getRealPath("/");
//        File file = new File(path + File.separator + "file" + File.separator + "traffic.txt");
//        String totalCount = "1";
//        String todayCount = "1";
//        if (!file.exists()) {
//            file.createNewFile();
//        } else {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            totalCount = br.readLine();
//            String tmp = br.readLine();
//            String date = tmp.split("=")[0];
//            String count = tmp.split("=")[1];
//            if (Tools.getSysDate("yyyy-MM-dd").equals(date))
//                todayCount = Arith.add("1", count);
//            totalCount = Arith.add("1", totalCount);
//            br.close();
//        }
//
//        PrintWriter pw = new PrintWriter(new FileWriter(file));
//        pw.println(totalCount);
//        pw.println(Tools.getSysDate("yyyy-MM-dd") + "=" + todayCount);
//        pw.flush();
//        pw.close();
//
//        session.setAttribute("TOTAL_COUNT", totalCount);
//        session.setAttribute("TODAY_COUNT", todayCount);
        return user;
    }

    public List queryUserPmsInfo(String personName, String postType, String treeId, PageVO vo, String personType, String belongRoleId, User user) throws RollbackableException {
        List list = userManageService.queryUserPmsInfo(personName, postType, treeId, vo, personType, belongRoleId, user);
        translateCode(list);
        return list;
    }

//    public List queryUserPmsInfoVOByName(String name, PageVO vo, String belongRoleId, User user) throws RollbackableException {
//        List list = userManageService.queryUserPmsInfoVOByName(name, vo, belongRoleId, user);
//        translateCode(list);
//        return list;
//    }

    private void translateCode(List list) throws RollbackableException {
        if (list != null && list.size() > 0) {
            //翻译代码
            String superManageId = userManageService.getSuperManagerId();
            Hashtable sysOperRoleInfo = userManageService.queryAllSysOperRoleName();

            for (int i = 0; i < list.size(); i++) {
                UserPmsInfoVO user = (UserPmsInfoVO) list.get(i);
                user.setSex(SysCacheTool.interpretCode(user.getSex()));
                user.setOrgId(SysCacheTool.interpretOrg(user.getOrgId()));
                user.setDeptId(SysCacheTool.interpretOrg(user.getDeptId()));
                user.setPostId(SysCacheTool.interpretPost(user.getPostId()));
                user.setPersonType(SysCacheTool.interpretCode(user.getPersonType()));
                if (PmsConstants.STATUS_OPEN.equals(user.getStatus()))
                    user.setStatus("启用");
                else
                    user.setStatus("<font color='red'>禁用</font>");
                if (superManageId.equals(user.getPersonId()))  //超级管理员
                    user.setSysOper("<font color='red'>总管理员</font>");
                else if (PmsConstants.IS_SYS_MANAGER.equals(user.getSysOper()))  //一般管理员
                    user.setSysOper("是");
                else                                        //普通用户
                    user.setSysOper("否");

                if (PmsConstants.IS_BUSINESS_USER.equals(user.getBusinessUser()))
                    user.setBusinessUser("是");
                else
                    user.setBusinessUser("否");

//                if (RoleInfoBO.IS_UNIT_LEADER.equals(user.getUnitLeader()))
//                    user.setUnitLeader("是");
//                else
//                    user.setUnitLeader("否");
//
                if (PmsConstants.IS_DEPT_LEADER.equals(user.getDeptLeader()))
                    user.setDeptLeader("是");
                else
                    user.setDeptLeader("否");
                if (PmsConstants.IS_HR_LEADER.equals(user.getHrLeader()))
                    user.setHrLeader("是");
                else
                    user.setHrLeader("否");
                //得到所属管理员角色名称
                String roleId = user.getBelongRoleId();
                roleId = Tools.filterNull(roleId);
                if (!roleId.equals("")) {
                    user.setBelongRoleId(sysOperRoleInfo.get(user.getBelongRoleId()).toString());
                }
            }
        }
    }

    /**
     * @param list
     * @throws RollbackableException
     * @deprecated
     */
    private void translateCode2(List list) throws RollbackableException {
        if (list != null && list.size() > 0) {
            //翻译代码
            String superManageId = userManageService.getSuperManagerId();
            Hashtable sysOperRoleInfo = userManageService.queryAllSysOperRoleName();

            for (int i = 0; i < list.size(); i++) {
                UserInfoVO user = (UserInfoVO) list.get(i);
                user.setSex(SysCacheTool.interpretCode(user.getSex()));
                user.setOrgId(SysCacheTool.interpretOrg(user.getOrgId()));
                user.setDeptId(SysCacheTool.interpretOrg(user.getDeptId()));

                user.setPostId(SysCacheTool.interpretPost(user.getPostId()));
                user.setPersonType(SysCacheTool.interpretCode(user.getPersonType()));
                user.setPostType(SysCacheTool.interpretCode(user.getPostType()));
                user.setPostLevel(SysCacheTool.interpretCode(user.getPostLevel()));
                if (PmsConstants.STATUS_OPEN.equals(user.getStatus()))
                    user.setStatus("启用");
                else
                    user.setStatus("<font color='red'>禁用</font>");

                //得到所属管理员角色名称
                String roleId = user.getBelongRoleId();
                roleId = Tools.filterNull(roleId);
                if (!"".equals(roleId)) {
                    user.setBelongRoleId(Tools.filterNull((String) sysOperRoleInfo.get(user.getBelongRoleId())));
                }
            }
        }
    }

    public List queryUserRoleInfo(String userId) throws RollbackableException {
        return userManageService.queryUserRole(userId);
    }

    /**
     * 将指定用户设为本级用户
     */
    public void makeCurrentLevelUser(String[] userIds, String roleId, User user) throws RollbackableException {
        String superManageId = userManageService.getSuperManagerId();
        for (int i = 0; i < userIds.length; i++) {
            userManageService.makeCurrentLevelUser(userIds[i], roleId, superManageId);
        }
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "将（" + userIds.length + "）名用户设置为本级用户。");
    }

    /**
     * 取消本级用户
     */
    public void cancelCurrentLevelUser(String[] userIds, String roleId, User user) throws RollbackableException {
        for (int i = 0; i < userIds.length; i++) {
            userManageService.cancelCurrentLevelUser(userIds[i], roleId);
        }
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "将（" + userIds.length + "）名用户取消本级用户。");
    }

    /**
     * 给用户分配角色
     */
    public void makeAssignRole(String[] userIds, String[] roleIds, User user) throws RollbackableException {
        userManageService.assignRole(userIds, roleIds);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "给（" + userIds.length + "）名用户分配了角色。");
    }

    /**
     * 清除用户角色
     */
    public void makeClearRole(String[] userIds, User user) throws RollbackableException {
        userManageService.clearRole(userIds);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "清除了（" + userIds.length + "）名用户的角色。");
    }

    public User findUserById(String userId) throws RollbackableException {
        return userManageService.findUserById(userId);
    }

    public boolean checkRepLoginName(String userId, String loginName) throws RollbackableException {
        return userManageService.checkRepLoginName(userId, loginName);
    }

    public Hashtable queryPmsCode(String userId, String flag, String status) throws RollbackableException {
        return userManageService.queryPmsCode(userId, flag, status);
    }

    public void addUserInfo(PersonVO person, User user) throws RollbackableException {
        userManageService.addUserInfo(person);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "为用户编号为（" + person.getPersonId() + "）的员工生成登录帐户密码。");
    }

    public void batchBuildUserInfo(User user) throws RollbackableException {
        userManageService.batchBuildUserInfo();
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "批量生成全部帐户密码。");
    }

    /**
     * 检测是否是系统管理员
     *
     * @param userId 用户ID
     * @return 1 超级管理员  2 系统管理员 3 其他用户
     */
    public String isSysManager(String userId) throws RollbackableException {
        return userManageService.isSysManager(userId);
    }

    public List queryUserInfo(PageVO vo, String name, String treeId, String personType, User user) throws RollbackableException {
        List list = userManageService.queryUserInfo(vo, name, treeId, personType, user);
        translateCode2(list);
        return list;
    }

    public UserInfoVO[] queryUserInfo(String name, String treeId, String personType, User user) throws RollbackableException {
        UserInfoVO[] users = userManageService.queryUserInfo(name, treeId, personType, user);
        try {
            if (users != null && users.length > 0) {
                Hashtable sysOperRoleInfo = userManageService.queryAllSysOperRoleName();
                for (int i = 0; i < users.length; i++) {
                    UserInfoVO vo = users[i];
                    if (vo != null) {
                        if (PmsConstants.STATUS_OPEN.equals(vo.getStatus()))
                            vo.setStatus("启用");
                        else
                            vo.setStatus("禁用");

                        //得到所属管理员角色名称
                        String roleId = user.getBelongRoleId();
                        roleId = Tools.filterNull(roleId);
                        if (!roleId.equals("")) {
                            //yejb add 070405
                            if (vo.getBelongRoleId() != null)
                                vo.setBelongRoleId(sysOperRoleInfo.get(vo.getBelongRoleId()).toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RollbackableException("用户导出异常", e, getClass());
        }
        return users;
    }
}
