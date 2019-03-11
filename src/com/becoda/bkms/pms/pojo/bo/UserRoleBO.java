package com.becoda.bkms.pms.pojo.bo;


/**
 * 用户角色关系<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class UserRoleBO {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 用户ID
     */
    private String personId;

    /**
     * 主键
     */
    private String rolePersonId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getRolePersonId() {
        return rolePersonId;
    }

    public void setRolePersonId(String rolePersonId) {
        this.rolePersonId = rolePersonId;
    }
}
