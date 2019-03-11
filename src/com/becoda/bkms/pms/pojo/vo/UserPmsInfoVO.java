package com.becoda.bkms.pms.pojo.vo;

//import com.becoda.bkms.emp.pojo.bo.PersonBO;

/**
 * Created by IntelliJ IDEA.
 * 用户状态VO
 * User: lirg
 * Date: 2015-7-15
 * Time: 21:10:22
 * To change this template use File | Settings | File Templates.
 */
public class UserPmsInfoVO {
    private String personId;
    private String name;//姓名
    private String loginName;//登录名
    private String sex;//性别
    private String birth;//出生日期
    private String orgId;//所属机构
    private String deptId;//所属部门
    private String postId;//所属岗位
    private String deptTreeId;//所属部门treeId
    private String belongRoleId;//所属角色id
    private String sysCredit; //干部管理标识
    private String deptOrder;//所在部门排序号
    private String personOrder;//人员排序号
    private String personType; //用工类别
    private String postType;  //岗位类别
    private String saveAbility;  //后备干部

    private String status;//用户是否启用
    private String sysOper;//是否系统管理员
    private String businessUser;//是否是业务用户
    private String hrLeader;//是否是领导人员
    private String deptLeader;//是否是经理人


    public String getDeptLeader() {
        return deptLeader;
    }

    public void setDeptLeader(String deptLeader) {
        this.deptLeader = deptLeader;
    }

    public String getSaveAbility() {
        return saveAbility;
    }

    public void setSaveAbility(String saveAbility) {
        this.saveAbility = saveAbility;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getDeptOrder() {
        return deptOrder;
    }

    public void setDeptOrder(String deptOrder) {
        this.deptOrder = deptOrder;
    }

    public String getPersonOrder() {
        return personOrder;
    }

    public void setPersonOrder(String personOrder) {
        this.personOrder = personOrder;
    }

    public String getSysCredit() {
        return sysCredit;
    }

    public void setSysCredit(String sysCredit) {
        this.sysCredit = sysCredit;
    }

    public String getBelongRoleId() {
        return belongRoleId;
    }

    public void setBelongRoleId(String belongRoleId) {
        this.belongRoleId = belongRoleId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDeptTreeId() {
        return deptTreeId;
    }

    public void setDeptTreeId(String deptTreeId) {
        this.deptTreeId = deptTreeId;
    }

    public String getSysOper() {
        return sysOper;
    }

    public void setSysOper(String sysOper) {
        this.sysOper = sysOper;
    }


    public String getBusinessUser() {
        return businessUser;
    }

    public void setBusinessUser(String businessUser) {
        this.businessUser = businessUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //add by yxm on 2015-1-30
    public String getHrLeader() {
        return hrLeader;
    }

    public void setHrLeader(String hrLeader) {
        this.hrLeader = hrLeader;
    }
    //add by yxm on 2015-1-30
}
