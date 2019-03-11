package com.becoda.bkms.pms.pojo.bo;


/**
 * 角色_信息项权限<br>
 * 包括信息集和信息项权限<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleDataBO {
    private String roleDataId;
    private String roleId;

    /**
     * 存储信息集和信息项的ID
     */
    private String dataId;

    /**
     * 权限类型
     * 1：拒绝
     * 2：可读
     * 3：可写
     */
    private String pmsType;

    /**
     * 信息项类型
     * 0：指标集
     * 1：指标项
     */
    private String dataType;

    public String getRoleDataId() {
        return roleDataId;
    }

    public void setRoleDataId(String roleDataId) {
        this.roleDataId = roleDataId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getPmsType() {
        return pmsType;
    }

    public void setPmsType(String pmsType) {
        this.pmsType = pmsType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
