package com.becoda.bkms.pms.pojo.bo;


/**
 * 角色_党务范围权限<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RolePartyScaleBO {
    private String roleId;
    private String partyId;

    /**
     * 党务权限类型
     * 1 有权限党务
     * 0 无权限党务
     * 目前无权党务机构不做，可以以后扩展使用，所以当前取1
     */
    private String pmsType = "1";

    /**
     * 范围标志
     * 1 维护范围
     * 0 查询范围
     */
    private String scaleFlag;

    /**
     * 党务权限ID
     */
    private String partyScaleId;

    private String partyName;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyScaleId() {
        return partyScaleId;
    }

    public void setPartyScaleId(String partyScaleId) {
        this.partyScaleId = partyScaleId;
    }
}
