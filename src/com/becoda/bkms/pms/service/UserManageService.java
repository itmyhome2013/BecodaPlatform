package com.becoda.bkms.pms.service;

import com.becoda.bkms.cache.SysCacheTool;
//import com.becoda.bkms.ccp.pojo.bo.PartyBO;
//import com.becoda.bkms.ccyl.pojo.bo.CcylBO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.dao.UserInfoDAO;
import com.becoda.bkms.pms.dao.UserRoleDAO;
import com.becoda.bkms.pms.pojo.bo.UserRoleBO;
import com.becoda.bkms.pms.pojo.vo.UserInfoVO;
import com.becoda.bkms.pms.pojo.vo.UserPmsInfoVO;
//import com.becoda.bkms.union.pojo.bo.UnionBO;
import com.becoda.bkms.util.Tools;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 用户管理数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class UserManageService {
    private UserInfoDAO userInfoDAO;
    private UserRoleDAO userRoleDAO;
//    private DataLockAPI datalockapi;

//    public DataLockAPI getDatalockapi() {
//        return datalockapi;
//    }
//
//    public void setDatalockapi(DataLockAPI datalockapi) {
//        this.datalockapi = datalockapi;
//    }

    public UserInfoDAO getUserInfoDAO() {
        return userInfoDAO;
    }

    public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
        this.userInfoDAO = userInfoDAO;
    }

    public UserRoleDAO getUserRoleDAO() {
        return userRoleDAO;
    }

    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    /**
     * 创建用户，此专供接口人员模块使用
     *
     * @param userInfo 用户信息BO
     */
    public void updateUserInfo(User userInfo) throws RollbackableException {
        userInfoDAO.updateBo(userInfo.getUserId(), userInfo);
    }

    /**
     * 检查信息集和信项权限
     * 方法：通过SQL语句查询出当前用户下所有角色的信息项和信息集的最大权限
     *
     * @param userID 用户ID
     * @param sType  指标集小类别
     * @return Hashtable   key 指标集 或者指标项     object 1, 2,3
     */
    public Hashtable queryUserDataByStype(String userID, String sType) throws RollbackableException {
        return userInfoDAO.queryUserDataByStype(userID, sType);
    }

    /**
     * 检查操作权限
     * 方法：通过SQL语句查询出当前用户下所有角色的操作权限，使用distinct方法,按照CodeID
     * 排序
     *
     * @param userID 用户ID
     * @return com.becoda.bkms.pms.pojo.bo.OperateBO[]
     */
    public List queryUserOperate(String userID) throws RollbackableException {
        return userInfoDAO.queryUserOperate(userID);
    }

    /**
     * 检测操作权限
     *
     * @param userID 用户ID
     * @param flag   1维护 0 查询
     * @return com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO[]
     */
    public List queryUserOrgScale(String userID, String flag, String type) throws RollbackableException {
        return userInfoDAO.queryUserOrgScale(userID, flag, type);
    }

    public List queryUserWageUnitScale(String userID, String flag, String type) throws RollbackableException {
        return userInfoDAO.queryUserWageUnitScale(userID, flag, type);
    }

    public List queryUserPartyScale(String userId, String flag, String type) throws RollbackableException {
        return userInfoDAO.queryUserPartyScale(userId, flag, type);
    }

    public List queryUserCcylScale(String userId, String flag, String type) throws RollbackableException {
        return userInfoDAO.queryUserCcylScale(userId, flag, type);
    }

    public List queryUserUnionScale(String userId, String flag, String type) throws RollbackableException {
        return userInfoDAO.queryUserUnionScale(userId, flag, type);
    }


    /**
     * 查询管理员管辖的所有本级用户
     * 查询方法：
     * 先查出当前用户所出的角色ID
     * 然后通过角色表、角色用户关系表、用户表建立关联，查询处当前管理员管辖的所有的用户
     * 信息，按personID排序，将personID相同的用户合并为：是否业务用户、是否机构管理员、
     * 是否部门管理员，取权限最大值
     *
     * @param roleId
     * @return com.becoda.bkms.pms.pojo.bo.UserInfoBO[]
     */
    public List queryCurrentLevelUser(String roleId, PageVO vo) throws RollbackableException {
        return userInfoDAO.queryCurrentLevelUser(roleId, vo);
    }

    /**
     * 读取用户信息
     *
     * @param userName
     * @return
     * @throws RollbackableException
     */
    public User findUserByUserName(String userName) throws RollbackableException {
        return userInfoDAO.findUserByUserName(userName);
    }

    /**
     * 设置用户的启用禁用状态
     *
     * @param status 启用，禁用
     * @param userID
     */
    public void makeStatus(String userID, boolean status, String superManageId) throws RollbackableException {
        userInfoDAO.makeStatus(userID, status, superManageId);
    }

    /**
     * 登录验证
     *
     * @param userName
     * @param password
     */
    public User verifyLogon(String userName, String password, HttpSession session) throws RollbackableException {
        try {
            String pwd = Tools.md5(password);//加密
            User user = userInfoDAO.verifyLogon(userName, pwd);
            if (user == null) {
                throw new RollbackableException("用户名或密码错误", this.getClass());
            }
            if (!PmsConstants.STATUS_OPEN.equals(user.getStatus())) {
                throw new RollbackableException("登录失败,此用户已禁用", this.getClass());
            }
            loadUserInfo(user, session);
            return user;
        } catch (RollbackableException de) {
            //将DAO中抛出的DAOException包装成一个BkmsException异常抛给action
            throw new RollbackableException(de, this.getClass());
        } catch (Exception e) {
            throw new RollbackableException("登录验证失败", e, this.getClass());
        }
    }

    public void loadUserInfo(User user, HttpSession session) throws RollbackableException {
        try {
            //为user对象set人员的一些基本信息
            Person person = SysCacheTool.findPersonById(user.getUserId());
            if (person != null) {
                user.setName(person.getName());         //人员姓名
                user.setOrgId(person.getOrgId());       //所属机构
                user.setDeptId(person.getDeptId());     //所属部门
//                user.setSex(person.getSex());           //性别

                Org org = SysCacheTool.findOrgById(user.getOrgId());
                if (org != null) {
                    user.setOrgName(org.getName());     //所属机构名称
                } else {
                    throw new RollbackableException("找不到登录用户所对应的机构信息，登录失败", this.getClass());
                }
            } else {
                throw new RollbackableException("找不到登录用户所对应的人员信息，登录失败", this.getClass());
            }

            //为user对象set角色身份
            UserPmsInfoVO uservo = userInfoDAO.queryUserPmsInfoVOByUserId(user.getUserId());
            if (uservo == null) {
                throw new RollbackableException("查询用户信息出错，登录失败", this.getClass());
            }
            if (PmsConstants.IS_BUSINESS_USER.equals(uservo.getBusinessUser())) { //是否是业务人员
                user.setBusinessUser(true);
            } else
                user.setBusinessUser(false);
            if (PmsConstants.IS_HR_LEADER.equals(uservo.getHrLeader())) { //是否是领导人员
                user.setHrLeader(true);
            } else
                user.setHrLeader(false);
            if (PmsConstants.IS_DEPT_LEADER.equals(uservo.getDeptLeader())) { //是否是经理人
                user.setDeptLeader(true);
            } else
                user.setDeptLeader(false);
            if (PmsConstants.IS_SYS_MANAGER.equals(uservo.getSysOper())) {  //是否是管理员
                user.setSysOper(true);
            } else
                user.setSysOper(false);

            user.setUserRoleList(userInfoDAO.queryUserRoleByLoginCall(user.getUserId()));
            //为user对象set权限的信息
            boolean isCheckPms = false;//是否检测其他权限，默认否
            //如果具有菜单权限（即非自助用户），就需要检测其他权限，如：机构查询范围、机构操作范围.....
            user.setPmsMenus(userInfoDAO.queryUserMenu(user.getUserId()));
            if (user.getPmsMenus() != null && user.getPmsMenus().values().size() > 0) {
                isCheckPms = true;
            }
            //modify kangdw 2015-3-16 for 浙商行项目
            if(PmsConstants.IS_DEPT_LEADER.equals(uservo.getDeptLeader())||PmsConstants.IS_HR_LEADER.equals(uservo.getHrLeader())) isCheckPms = true;
            //end
            
            if (isCheckPms) {//如果是非自助用户,则检测权限
                //可维护机构列表
                user.setHaveOperateOrgScale(userInfoDAO.queryUserOrgScaleByLoginCall(user.getUserId(), PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_USE));
                //不可维护机构列表
                user.setHaveNoOperateOrgScale(userInfoDAO.queryUserOrgScaleByLoginCall(user.getUserId(), PmsConstants.OPERATE_SCALE, PmsConstants.SCALE_REFUSE));
                //可查询机构列表
                user.setHaveQueryOrgScale(userInfoDAO.queryUserOrgScaleByLoginCall(user.getUserId(), PmsConstants.QUERY_SCALE, PmsConstants.SCALE_USE));
                //不可查询机构列表
                user.setHaveNoQueryOrgScale(userInfoDAO.queryUserOrgScaleByLoginCall(user.getUserId(), PmsConstants.QUERY_SCALE, PmsConstants.SCALE_REFUSE));
                
                user.setPmsOperateCode(userInfoDAO.queryPmsCode(user.getUserId(), PmsConstants.OPERATE_SCALE, "1")); //代码 ：操作
                user.setPmsQueryCode(userInfoDAO.queryPmsCode(user.getUserId(), PmsConstants.QUERY_SCALE, "1"));   //代码：查询
                user.setPmsOperateList(userInfoDAO.queryUserOperate(user.getUserId()));//检查功能权限,包括菜单和按钮
                user.setPmsInfoSet(userInfoDAO.queryUserDataByDataType(user.getUserId(), PmsConstants.INFO_SET_TYPE));//指标集
                user.setPmsInfoItem(userInfoDAO.queryUserDataByDataType(user.getUserId(), PmsConstants.INFO_ITEM_TYPE)); //指标项
            }

            //如果是部门领导或者机构领导 并且查寻范围为空，则将查询范围设置为所在机构
            if (user.isHrLeader() || user.isDeptLeader()) {
                List list = user.getHaveQueryOrgScale();
                if (list == null || list.size() == 0) {
                    Org org = SysCacheTool.findOrgById(user.getOrgId());
                    list = new ArrayList();
                    list.add(org);
                    user.setHaveQueryOrgScale(list);
                    user.setPmsQueryCode(userInfoDAO.queryAllPmsCode());//添加代码权限
                }
            }

            //关于登录的一些信息...
            //更新登录者最后一次登录系统的时间 和 登录次数
            String LoginTime = Tools.getSysDate(null);
            userInfoDAO.updateUserLoginInfo(LoginTime, user.getUserId());
            //记录访问量的功能废弃
//            //系统累计总访问量和今天的总访问量  start
//            String path = session.getServletContext().getRealPath("/");
//            File file = new File(path + File.separator + "file" + File.separator + "traffic.txt");
//            String totalCount = "1";
//            String todayCount = "1";
//            if (!file.exists()) {
//                file.createNewFile();
//            } else {
//                BufferedReader br = new BufferedReader(new FileReader(file));
//                totalCount = br.readLine();
//                String tmp = br.readLine();
//                String date = tmp.split("=")[0];
//                String count = tmp.split("=")[1];
//                if (Tools.getSysDate("yyyy-MM-dd").equals(date))
//                    todayCount = Arith.add("1", count);
//                totalCount = Arith.add("1", totalCount);
//                br.close();
//            }
//            PrintWriter pw = new PrintWriter(new FileWriter(file));
//            pw.println(totalCount);
//            pw.println(Tools.getSysDate("yyyy-MM-dd") + "=" + todayCount);
//            pw.flush();
//            pw.close();
//            session.setAttribute("TOTAL_COUNT", totalCount);// 系统累计总访问量放入session
//            session.setAttribute("TODAY_COUNT", todayCount);  // 今天的总访问量放入session
            //系统累计总访问量和今天的总访问量  end
        } catch (RollbackableException de) {
            throw de;
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
     * @return
     * @throws Exception
     */
    public User verifyLogonRep(String userName, String password) throws Exception {
        try {
            String pwd = Tools.md5(password);//加密
            User user = userInfoDAO.verifyLogon(userName, pwd);
            if (user == null) {
                throw new RollbackableException("用户名或密码错误", this.getClass());
            }
            if (!PmsConstants.STATUS_OPEN.equals(user.getStatus())) {
                throw new RollbackableException("登录失败,此用户已禁用", this.getClass());
            }
            loadUserInfo(user, null);
            return user;
        } catch (RollbackableException de) {
            throw new RollbackableException(de, this.getClass());
        } catch (Exception e) {
            throw new RollbackableException("登录验证失败", e, this.getClass());
        }
    }

    public List queryUserPmsInfo(String personName, String postType, String treeId, PageVO vo, String personType, String belongRoleId, User user) throws RollbackableException {
        return userInfoDAO.queryUserPmsInfo(personName, postType, treeId, vo, personType, belongRoleId, user);
    }

    /**
     * 查询指定用户所属的角色列表
     *
     * @param userID 用户ID
     * @return list object = RoleInfoBO
     */
    public List queryUserRole(String userID) throws RollbackableException {
        return userRoleDAO.queryUserRole(userID);
    }

    /**
     * 将指定用户设为本级用户
     */
    public void makeCurrentLevelUser(String userID, String roleId, String superManageId) throws RollbackableException {
        userInfoDAO.makeCurrentLevelUser(userID, roleId, superManageId);
    }

    /**
     * 取消本级用户
     */
    public void cancelCurrentLevelUser(String userID, String roleId) throws RollbackableException {
        userInfoDAO.cancelCurrentLevelUser(userID, roleId);
    }

    /**
     * 得到所有的系统管理员信息
     *
     * @return key = "roleId" obj = "roleName"
     */
    public Hashtable queryAllSysOperRoleName() throws RollbackableException {
        return userRoleDAO.queryAllSysOperRoleName();
    }

    public void assignRole(String[] userIds, String[] roleIds) throws RollbackableException {
        //先清楚用户角色
        for (int j = 0; j < userIds.length; j++) {
            userRoleDAO.clearRole(userIds[j]);
            for (int i = 0; i < roleIds.length; i++) {
                UserRoleBO ur = new UserRoleBO();
                ur.setPersonId(userIds[j]);
                ur.setRoleId(roleIds[i]);
                userRoleDAO.createBo(ur);
            }
        }
    }

    /**
     * 清除用户角色
     */
    public void clearRole(String[] userIds) throws RollbackableException {
        for (int j = 0; j < userIds.length; j++) {
            userRoleDAO.clearRole(userIds[j]);
            userInfoDAO.cancelCurrentLevelUser(userIds[j]);  //add by yejb 20070125 清除本级用户
        }
    }

    /**
     * 根据用户ID读取用户信息
     *
     * @param userId
     * @return
     * @throws RollbackableException
     */

    public User findUserById(String userId) throws RollbackableException {
        return (User) userInfoDAO.findBo(User.class, userId);
    }

    /**
     * 检验用户名是否重复
     */
    public boolean checkRepLoginName(String userId, String loginName) throws RollbackableException {
        return userInfoDAO.checkRepLoginName(userId, loginName);
    }

    /**
     * 检测是否是系统管理员
     *
     * @param userId 用户ID
     * @return 1 超级管理员  2 系统管理员 3 其他用户
     */
    public String isSysManager(String userId) throws RollbackableException {
        return userInfoDAO.isSysManager(userId);
    }

    public Hashtable queryPmsCode(String userId, String flag, String status) throws RollbackableException {
        return userInfoDAO.queryPmsCode(userId, flag, status);
    }

    //得到系统超级管理员
    public String getSuperManagerId() throws RollbackableException {
        return userInfoDAO.getSuperManagerId();
    }

    public void addUserInfo(PersonVO person) throws RollbackableException {
        userInfoDAO.addUserInfo(person);
    }

    public void batchBuildUserInfo() throws RollbackableException {
        userInfoDAO.batchBuildUserInfo();
    }

    public List queryUserInfo(PageVO vo, String name, String treeId, String personType, User user) throws RollbackableException {
        return userInfoDAO.queryUserInfo(vo, name, treeId, personType, user);
    }

    public UserInfoVO[] queryUserInfo(String name, String treeId, String personType, User user) throws RollbackableException {
        return userInfoDAO.queryUserInfo(name, treeId, personType, user);
    }
}
