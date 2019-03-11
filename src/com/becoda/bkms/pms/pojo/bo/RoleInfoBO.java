package com.becoda.bkms.pms.pojo.bo;


/**
 * po_角色<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleInfoBO {
    /**
     * 经理人
     */
    private String deptLeader;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否系统管理员
     */
    private String sysOper;

    /**
     * 创建者角色ID
     */
    private String creator;

    /**
     * 是否领导人员
     */
    private String hrLeader;
    /**
     * 是否是业务用户
     */
    private String businessUser;
    private String createTime;
    private String treeId;

    public String getDeptLeader() {
        return deptLeader;
    }

    public void setDeptLeader(String deptLeader) {
        this.deptLeader = deptLeader;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getHrLeader() {
        return hrLeader;
    }

    public void setHrLeader(String hrLeader) {
        this.hrLeader = hrLeader;
    }
}
