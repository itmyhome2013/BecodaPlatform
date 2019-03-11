package com.becoda.bkms.pms.pojo.bo;


/**
 * 角色_机构范围权限<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleOrgScaleBO {

    /**
     * 机构权限ID
     */
    private String orgScaleId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 用于存储机构ID或者代码ID
     */
    private String condId;

    /**
     * 1 有权限机构
     * 0 无权限机构
     */
    private String pmsType;

    /**
     * 1 维护范围
     * 0 查询范围
     */
    private String scaleFlag;

    private String orgName;

    /**
     * 当代码集ID为DEPT时 代表当前Cond_ID代表当前为机构编码
     * 当代码集为SetID时，Cond_ID代表次代码集下的某个SetItemID值，然后通过与范围代码关?1?7
     * <p/>
     * 表，拼出条件语句。注，目前此处代码集只支持人员相关代码集。
     */
    private String codeId;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPmsType() {
        return pmsType;
    }

    public void setPmsType(String pmsType) {
        this.pmsType = pmsType;
    }


    public String getOrgScaleId() {
        return orgScaleId;
    }

    public void setOrgScaleId(String orgScaleId) {
        this.orgScaleId = orgScaleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCondId() {
        return condId;
    }

    public void setCondId(String condId) {
        this.condId = condId;
    }

    public String getScaleFlag() {
        return scaleFlag;
    }

    public void setScaleFlag(String scaleFlag) {
        this.scaleFlag = scaleFlag;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
