package com.becoda.bkms.pms.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-1
 * Time: 9:57:15
 * To change this template use File | Settings | File Templates.
 */
public class RoleUnionScaleBO {
    private String roleId;
    private String unionId;

    /**
     * 工会组织权限类型
     * 1 有权限工会组织
     * 0 无权限工会组织
     * 目前无权工会组织不做，可以以后扩展使用，所以当前取1
     */
    private String pmsType = "1";

    /**
     * 范围标志
     * 1 维护范围
     * 0 查询范围
     */
    private String scaleFlag;

    private String unionScaleId;

    private String unionName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getPmsType() {
        return pmsType;
    }

    public void setPmsType(String pmsType) {
        this.pmsType = pmsType;
    }

    public String getScaleFlag() {
        return scaleFlag;
    }

    public void setScaleFlag(String scaleFlag) {
        this.scaleFlag = scaleFlag;
    }

    public String getUnionScaleId() {
        return unionScaleId;
    }

    public void setUnionScaleId(String unionScaleId) {
        this.unionScaleId = unionScaleId;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }
}
