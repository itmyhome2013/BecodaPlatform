package com.becoda.bkms.pms.pojo.bo;


/**
 * 角色_操作权限<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleOperateBO {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 功能ID
     */
    private String operateId;

    /**
     * 角色操作权限关系ID
     */
    private String roleOperateId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getRoleOperateId() {
        return roleOperateId;
    }

    public void setRoleOperateId(String roleOperateId) {
        this.roleOperateId = roleOperateId;
    }

}
