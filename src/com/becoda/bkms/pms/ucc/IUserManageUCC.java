package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
import com.becoda.bkms.pms.pojo.vo.UserInfoVO;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;

/**
 * 用户管理
 * author:lirg
 * 2015-5-24
 */
public interface IUserManageUCC {

    /**
     * 修改密码
     * @param username 用户ID
     * @param password  密码
     */
    // public void updatePassword(String username, String password) throws RollbackableException;
    // public String getPassword(String userName) throws RollbackableException;

    /**
     * 读取用户信息
     *
     * @param userName
     * @return
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public User findUserByUserName(String userName) throws RollbackableException;

    /**
     * 修改登录名
     *
     * @param userID   用户ID
     * @param userName 用户名
     * @roseuid 4479E4E200C1
     */
//    public void updateUserInfo(String userID, String userName, String password) throws RollbackableException;

//    public void updateUserInfo(String userID, String userName) throws RollbackableException;

    /**
     * 创建用户，此专供接口人员模块使用
     *
     * @param user 用户信息BO
     * @param user
     * @roseuid 4479E4EA0326
     */
    public void updateUserInfo(User user, User operator) throws RollbackableException;

    //   public RoleDataBO[] queryUserData(String userID, String sType);

    /**
     * 检查操作权限
     * 方法：通过SQL语句查询出当前用户下所有角色的操作权限，使用distinct方法,按照CodeID
     * 排序
     *
     * @param userID 用户ID
     * @return com.becoda.bkms.pms.pojo.bo.OperateBO[]
     * @roseuid 4479E4FB0398
     */
    public List queryUserOperate(String userID) throws RollbackableException;


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
     * @roseuid 447ADC4E0193
     */
    public List queryCurrentLevelUser(String roleId, PageVO vo) throws RollbackableException;

    /**
     * 设置用户的启用禁用状态
     *
     * @param status  启用，禁用
     * @param userIds
     * @roseuid 447AE78F01F0
     */
    public void makeStatus(String[] userIds, boolean status, User user) throws RollbackableException;

    /**
     * 登录验证
     *
     * @param userName
     * @param password
     */
    public User makeVerifyLogon(String userName, String password, HttpSession session) throws RollbackableException;

    /**
     * 同一认证登录
     *
     * @param personCode
     * @param session
     * @return
     * @throws RollbackableException
     */
    public User makeUnionformVerifyLogon(String personCode,String password, HttpSession session) throws RollbackableException;


    /**
     * 报表登陆
     * add by yejb 061223
     *
     * @param userName 用户名
     * @param password 密码
     * @param session
     * @return user 对象
     * @throws Exception
     */
    public User makeVerifyLogonRep(String userName, String password, HttpSession session) throws Exception;
    /**
     * 根据TreeId查询用户信息
     * @param treeId
     * @param vo
     * @return
     * @throws RollbackableException
     */
    // public List queryUserPmsInfoVOByOrg(String treeId, PageVO vo,String belongRoleId,User user,String personType) throws RollbackableException;

    /**
     * 根据姓名查询用户信息
     *
     * @param personName
     * @param vo
     * @return
     * @throws RollbackableException
     */
    public List queryUserPmsInfo(String personName, String postType, String treeId, PageVO vo, String personType, String belongRoleId, User user) throws RollbackableException;

    /**
     * 查询用户所属角色
     *
     * @param userId
     * @return
     * @throws RollbackableException
     */
    public List queryUserRoleInfo(String userId) throws RollbackableException;

    /**
     * 将指定用户设为本级用户
     */
    public void makeCurrentLevelUser(String[] userIds, String roleId, User user) throws RollbackableException;

    /**
     * 取消本级用户
     */
    public void cancelCurrentLevelUser(String[] userIds, String roleId, User user) throws RollbackableException;

    /**
     * 给用户分配角色
     */
    public void makeAssignRole(String[] userIds, String[] roleIds, User user) throws RollbackableException;

    /**
     * 清除用户角色
     */
    public void makeClearRole(String[] userIds, User user) throws RollbackableException;

    /**
     * 检查信息集和信项权限
     * 方法：通过SQL语句查询出当前用户下所有角色的信息项和信息集的最大权限
     *
     * @param userID 用户ID
     * @param sType  0 指标集  1：指标项
     * @return RoleDataBO[]
     * @roseuid 4479E4F4037A
     */
    public Hashtable queryUserDataByStype(String userID, String sType) throws RollbackableException;

    /**
     * 检测机构范围权限
     *
     * @param userID 用户ID
     * @param flag   1维护 0 查询
     * @param type   1有权机构 0 无权机构
     */
    public List queryUserOrgScale(String userID, String flag, String type) throws RollbackableException;

    /**
     * 检测薪酬机构范围权限
     *
     * @param userID 用户ID
     * @param flag   1维护 0 查询
     * @param type   1有权机构 0 无权机构
     */
    public List queryUserWageUnitScale(String userID, String flag, String type) throws RollbackableException;

    /**
     * 检测党组织范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1有权机构 0 无权机构
     */
    public List queryUserPartyScale(String userId, String flag, String type) throws RollbackableException;

    /**
     * 检测团组织范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1有权机构 0 无权机构
     */
    public List queryUserCcylScale(String userId, String flag, String type) throws RollbackableException;

    /**
     * 检测工会组织范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1有权机构 0 无权机构
     */
    public List queryUserUnionScale(String userId, String flag, String type) throws RollbackableException;

    /**
     * 查询指定用户
     * @param userId
     * @return
     * @throws RollbackableException
     */
    ;

    public User findUserById(String userId) throws RollbackableException;

    /**
     * 检测用户名是否重复
     *
     * @param userId
     * @param loginName
     * @return
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public boolean checkRepLoginName(String userId, String loginName) throws RollbackableException;

    public Hashtable queryPmsCode(String userId, String flag, String status) throws RollbackableException;

    public void addUserInfo(PersonVO person, User user) throws RollbackableException;

    public void batchBuildUserInfo(User user) throws RollbackableException;

    /**
     * 检测是否是系统管理员
     *
     * @param userId 用户ID
     * @return 1 超级管理员  2 系统管理员 3 其他用户
     */
    public String isSysManager(String userId) throws RollbackableException;

    public List queryUserInfo(PageVO vo, String name, String treeId, String personType, User user) throws RollbackableException;

    public UserInfoVO[] queryUserInfo(String name, String treeId, String personType, User user) throws RollbackableException;
}
