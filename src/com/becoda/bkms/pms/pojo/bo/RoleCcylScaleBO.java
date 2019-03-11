package com.becoda.bkms.pms.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-1
 * Time: 9:57:05
 * To change this template use File | Settings | File Templates.
 */
public class RoleCcylScaleBO {
    private String roleId;
    private String ccylId;

    /**
     * 团组织权限类型
     * 1 有权限团组织
     * 0 无权限团组织
     * 目前无权团组织不做，可以以后扩展使用，所以当前取1
     */
    private String pmsType = "1";

    /**
     * 范围标志
     * 1 维护范围
     * 0 查询范围
     */
    private String scaleFlag;

    private String ccylScaleId;

    private String ccylName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCcylId() {
        return ccylId;
    }

    public void setCcylId(String ccylId) {
        this.ccylId = ccylId;
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

    public String getCcylScaleId() {
        return ccylScaleId;
    }

    public void setCcylScaleId(String ccylScaleId) {
        this.ccylScaleId = ccylScaleId;
    }

    public String getCcylName() {
        return ccylName;
    }

    public void setCcylName(String ccylName) {
        this.ccylName = ccylName;
    }
}
