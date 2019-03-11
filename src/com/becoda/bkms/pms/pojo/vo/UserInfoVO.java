package com.becoda.bkms.pms.pojo.vo;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-31
 * Time: 15:05:45
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoVO {
    /**
     * 人员编号
     */
    private String personId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生日期
     */
    private String birth;
    /**
     * 所属机构
     */
    private String orgId;
    /**
     * 所属部门
     */
    private String deptId;
    /**
     * 所属岗位
     */
    private String postId;
    /**
     * 所属部门treeId
     */
    private String deptTreeId;
    /**
     * 状态
     */
    private String status;

    private String belongRoleId;
    private String sysCredit;

    private String deptOrder;
    private String personOrder;

    private String personType;//工号

    private String postType;

    private String saveAbility;

    private String postLevel;//职等

    public String getSaveAbility() {
        return saveAbility;
    }

    public void setSaveAbility(String saveAbility) {
        this.saveAbility = saveAbility;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
