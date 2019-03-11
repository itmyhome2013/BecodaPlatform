package com.becoda.bkms.common.pojo.vo;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-2-25
 * Time: 14:15:20
 * To change this template use File | Settings | File Templates.
 */
public class User {

    /**
     * 是否系统管理员
     */
    private boolean isSysOper;
    /**
     * 是否业务用户
     */
    private boolean isBusinessUser;
    /**
     * 是否领导人员
     */
    private boolean isHrLeader;
    /**
     * 是否经理人
     */
    private boolean isDeptLeader;

    //角色身份信息 end

    //人员登录信息 start

    /**
     * 用户ID,和员工ID相同
     */
    private String userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户状态  1： 启用，0 ：禁用
     */
    private String status;
    /**
     * 上次登陆时间
     */
    private String lastLoginTime;
    /**
     * 登录次数
     */
    private String loginTimes;
    /**
     * 所属角色id
     */
    private String belongRoleId;

    //人员登录信息 end

    //人员基本信息 start
    /**
     * 用户姓名
     */
    private String Name;
    /**
     * 所属机构ID
     */
    private String orgId;
    /**
     * 所属部门ID
     */
    private String deptId;
    /**
     * 性别
     */
    private String sex;
    /**
     * 所属机构名称
     */
    private String orgName;
    /**
     * 所属党务机构ID
     */
    private String partyOrgId;
    /**
     * 所属党务机构名称
     */
    private String partyOrgName;
    /**
     * 所属团ID
     */
    private String ccylId;
    /**
     * 所属团名称
     */
    private String ccylName;
    /**
     * 所属工会ID
     */
    private String labourId;
    /**
     * 所属工会名称
     */
    private String labourName;

    /**
     * 发薪机构名称
     */
    private String salaryOrgName;

    //人员基本信息 end

    //登录者权限信息 start

    /**
     * 用户角色列表
     */
    private List userRoleList;

    /**
     * 有权机构操作范围，使用List存储Node结构
     */
    private List haveOperateOrgScale;

    /**
     * 无权机构操作范围，使用List存储Node结构
     */
    private List haveNoOperateOrgScale;
    /**
     * 有权机构查询范围，使用List存储Node结构
     */
    private List haveQueryOrgScale;
    /**
     * 无权机构查询范围，使用List存储Node结构
     */
    private List haveNoQueryOrgScale;
    /**
     * 有权党务操作范围，使用List存储Node结构
     */
    private List haveOperatePartyScale;
    /**
     * 无权党务操作范围，使用List存储Node结构
     */
    private List haveNoOperatePartyScale;
    /**
     * 有权党务查询范围，使用List存储Node结构
     */
    private List haveQueryPartyScale;
    /**
     * 无权党务查询范围，使用List存储Node结构
     */
    private List haveNoQueryPartyScale;
    /**
     * 有权薪酬操作范围，使用List存储Node结构
     */
    private List haveOperateWageUnitScale;
    /**
     * 有权薪酬查询范围，使用List存储Node结构
     */
    private List haveQueryWageUnitScale;
    /**
     * 无权薪酬操作范围，使用List存储Node结构
     */
    private List haveNoOperateWageUnitScale;
    /**
     * 无权薪酬查询范围，使用List存储Node结构
     */
    private List haveNoQueryWageUnitScale;


    /**
     * 代码：操作范围
     * hash结构: key = SetId,object = List(object=CodeItemBO)
     */
    private Hashtable pmsOperateCode;

    /**
     * 代码：查询范围
     * hash结构: key = SetId,object = List(object=CodeItemBO)
     */
    private Hashtable pmsQueryCode;
    /**
     * 有权限得信息集。
     */
    private Hashtable pmsInfoSet;
    /**
     * 有权限的信息项
     */
    private Hashtable pmsInfoItem;
    /**
     * 菜单
     * 数据结构 key = "operateId"  object = menuObj;
     * hash中存储一级菜单,所有的二级菜单以及子菜单都通过ChildMenuObj结构实现,存放到一级菜单机构中.
     */
    private Hashtable pmsMenus;

    /**
     * 功能权限列表：菜单+按钮，List中存储opreateBO
     */
    private List pmsOperateList;

    /**
     * 有权团务操作范围，使用List存储Node结构
     */
    private List haveOperateCcylScale;
    /**
     * 无权团务操作范围，使用List存储Node结构
     */
    private List haveNoOperateCcylScale;
    /**
     * 有权团务查询范围，使用List存储Node结构
     */
    private List haveQueryCcylScale;
    /**
     * 无权团务查询范围，使用List存储Node结构
     */
    private List haveNoQueryCcylScale;
    /**
     * 有权工会操作范围，使用List存储Node结构
     */
    private List haveOperateLabourScale;
    /**
     * 无权工会操作范围，使用List存储Node结构
     */
    private List haveNoOperateLabourScale;
    /**
     * 有权工会查询范围，使用List存储Node结构
     */
    private List haveQueryLabourScale;
    /**
     * 无权工会查询范围，使用List存储Node结构
     */
    private List haveNoQueryLabourScale;

    /**
     * **************end  added*****************
     */

    //登录者权限信息
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getBelongRoleId() {
        return belongRoleId;
    }

    public void setBelongRoleId(String belongRoleId) {
        this.belongRoleId = belongRoleId;
    }

    public boolean isSysOper() {
        return isSysOper;
    }

    public void setSysOper(boolean sysOper) {
        isSysOper = sysOper;
    }

    public String getSalaryOrgName() {
        return salaryOrgName;
    }

    public void setSalaryOrgName(String salaryOrgName) {
        this.salaryOrgName = salaryOrgName;
    }

    public Hashtable getPmsOperateCode() {
        return pmsOperateCode;
    }

    public void setPmsOperateCode(Hashtable pmsOperateCode) {
        this.pmsOperateCode = pmsOperateCode;
    }

    public Hashtable getPmsQueryCode() {
        return pmsQueryCode;
    }

    public void setPmsQueryCode(Hashtable pmsQueryCode) {
        this.pmsQueryCode = pmsQueryCode;
    }

    public Hashtable getPmsMenus() {
        return pmsMenus;
    }

    public void setPmsMenus(Hashtable pmsMenus) {
        this.pmsMenus = pmsMenus;
    }

    public String getName() {
        return Name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Hashtable getPmsInfoSet() {
        return pmsInfoSet;
    }

    public void setPmsInfoSet(Hashtable pmsInfoSet) {
        this.pmsInfoSet = pmsInfoSet;
    }

    public Hashtable getPmsInfoItem() {
        return pmsInfoItem;
    }

    public void setPmsInfoItem(Hashtable pmsInfoItem) {
        this.pmsInfoItem = pmsInfoItem;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLoginTimes() {
        if (loginTimes != null && !loginTimes.equals("")) {
            return loginTimes;
        }
        loginTimes = "0";
        return loginTimes;
    }

    public void setLoginTimes(String loginTimes) {
        this.loginTimes = loginTimes;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List getHaveOperateOrgScale() {
        return haveOperateOrgScale;
    }

    public void setHaveOperateOrgScale(List haveOperateOrgScale) {
        this.haveOperateOrgScale = haveOperateOrgScale;
    }

    public List getHaveNoOperateOrgScale() {
        return haveNoOperateOrgScale;
    }

    public void setHaveNoOperateOrgScale(List haveNoOperateOrgScale) {
        this.haveNoOperateOrgScale = haveNoOperateOrgScale;
    }

    public List getHaveQueryOrgScale() {
        return haveQueryOrgScale;
    }

    public void setHaveQueryOrgScale(List haveQueryOrgScale) {
        this.haveQueryOrgScale = haveQueryOrgScale;
    }

    public List getHaveNoQueryOrgScale() {
        return haveNoQueryOrgScale;
    }

    public void setHaveNoQueryOrgScale(List haveNoQueryOrgScale) {
        this.haveNoQueryOrgScale = haveNoQueryOrgScale;
    }

    public List getHaveOperatePartyScale() {
        return haveOperatePartyScale;
    }

    public void setHaveOperatePartyScale(List haveOperatePartyScale) {
        this.haveOperatePartyScale = haveOperatePartyScale;
    }

    public List getHaveNoOperatePartyScale() {
        return haveNoOperatePartyScale;
    }

    public void setHaveNoOperatePartyScale(List haveNoOperatePartyScale) {
        this.haveNoOperatePartyScale = haveNoOperatePartyScale;
    }

    public List getHaveQueryPartyScale() {
        return haveQueryPartyScale;
    }

    public void setHaveQueryPartyScale(List haveQueryPartyScale) {
        this.haveQueryPartyScale = haveQueryPartyScale;
    }

    public List getHaveNoQueryPartyScale() {
        return haveNoQueryPartyScale;
    }

    public void setHaveNoQueryPartyScale(List haveNoQueryPartyScale) {
        this.haveNoQueryPartyScale = haveNoQueryPartyScale;
    }

    public boolean isBusinessUser() {
        return isBusinessUser;
    }

    public void setBusinessUser(boolean businessUser) {
        isBusinessUser = businessUser;
    }

    public List getPmsOperateList() {
        return pmsOperateList;
    }

    public void setPmsOperateList(List pmsOperateList) {
        this.pmsOperateList = pmsOperateList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartyOrgId() {
        return partyOrgId;
    }

    public void setPartyOrgId(String partyOrgId) {
        this.partyOrgId = partyOrgId;
    }

    public String getPartyOrgName() {
        return partyOrgName;
    }

    public void setPartyOrgName(String partyOrgName) {
        this.partyOrgName = partyOrgName;
    }

    //add by yxm on 2015-1-30
    public boolean isHrLeader() {
        return isHrLeader;
    }

    public void setHrLeader(boolean hrLeader) {
        isHrLeader = hrLeader;
    }

    public List getHaveNoQueryLabourScale() {
        return haveNoQueryLabourScale;
    }

    public void setHaveNoQueryLabourScale(List haveNoQueryLabourScale) {
        this.haveNoQueryLabourScale = haveNoQueryLabourScale;
    }

    public List getHaveOperateCcylScale() {
        return haveOperateCcylScale;
    }

    public void setHaveOperateCcylScale(List haveOperateCcylScale) {
        this.haveOperateCcylScale = haveOperateCcylScale;
    }

    public List getHaveNoOperateCcylScale() {
        return haveNoOperateCcylScale;
    }

    public void setHaveNoOperateCcylScale(List haveNoOperateCcylScale) {
        this.haveNoOperateCcylScale = haveNoOperateCcylScale;
    }

    public List getHaveQueryCcylScale() {
        return haveQueryCcylScale;
    }

    public void setHaveQueryCcylScale(List haveQueryCcylScale) {
        this.haveQueryCcylScale = haveQueryCcylScale;
    }

    public List getHaveNoQueryCcylScale() {
        return haveNoQueryCcylScale;
    }

    public void setHaveNoQueryCcylScale(List haveNoQueryCcylScale) {
        this.haveNoQueryCcylScale = haveNoQueryCcylScale;
    }

    public List getHaveOperateLabourScale() {
        return haveOperateLabourScale;
    }

    public void setHaveOperateLabourScale(List haveOperateLabourScale) {
        this.haveOperateLabourScale = haveOperateLabourScale;
    }

    public List getHaveNoOperateLabourScale() {
        return haveNoOperateLabourScale;
    }

    public void setHaveNoOperateLabourScale(List haveNoOperateLabourpScale) {
        this.haveNoOperateLabourScale = haveNoOperateLabourpScale;
    }

    public List getHaveQueryLabourScale() {
        return haveQueryLabourScale;
    }

    public void setHaveQueryLabourScale(List haveQueryLabourScale) {
        this.haveQueryLabourScale = haveQueryLabourScale;
    }

    public List getHaveOperateWageUnitScale() {
        return haveOperateWageUnitScale;
    }

    public void setHaveOperateWageUnitScale(List haveOperateWageUnitScale) {
        this.haveOperateWageUnitScale = haveOperateWageUnitScale;
    }

    public List getHaveQueryWageUnitScale() {
        return haveQueryWageUnitScale;
    }

    public void setHaveQueryWageUnitScale(List haveQueryWageUnitScale) {
        this.haveQueryWageUnitScale = haveQueryWageUnitScale;
    }

    public List getHaveNoOperateWageUnitScale() {
        return haveNoOperateWageUnitScale;
    }

    public void setHaveNoOperateWageUnitScale(List haveNoOperateWageUnitScale) {
        this.haveNoOperateWageUnitScale = haveNoOperateWageUnitScale;
    }

    public List getHaveNoQueryWageUnitScale() {
        return haveNoQueryWageUnitScale;
    }

    public void setHaveNoQueryWageUnitScale(List haveNoQueryWageUnitScale) {
        this.haveNoQueryWageUnitScale = haveNoQueryWageUnitScale;
    }

    public String getCcylId() {
        return ccylId;
    }

    public void setCcylId(String ccylId) {
        this.ccylId = ccylId;
    }

    public String getCcylName() {
        return ccylName;
    }

    public void setCcylName(String ccylName) {
        this.ccylName = ccylName;
    }

    public String getLabourId() {
        return labourId;
    }

    public void setLabourId(String labourId) {
        this.labourId = labourId;
    }

    public String getLabourName() {
        return labourName;
    }

    public void setLabourName(String labourName) {
        this.labourName = labourName;
    }
    //add by yxm on 2015-1-30

    public List getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List userRoleList) {
        this.userRoleList = userRoleList;
    }

    public boolean isDeptLeader() {
        return isDeptLeader;
    }

    public void setDeptLeader(boolean deptLeader) {
        isDeptLeader = deptLeader;
    }
}