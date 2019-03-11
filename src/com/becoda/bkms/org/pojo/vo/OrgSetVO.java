package com.becoda.bkms.org.pojo.vo;



/**
 * 机构设置情况
 * User: yxm
 * Date: 2015-6-19
 * Time: 11:07:47
 */
public class OrgSetVO  {
    private String orgId;
    private String setupTime;//设立时间
    private String setupType;//设立类别
    private String setupNo;//设立文号
    private String setupUnit;//批准设立单位
    private String cancelTime;//撤销时间
    private String cancelType;//撤销类别
    private String cancelNo;//撤销文号
    private String cancelUnit;//批准撤销单位
    private String memo;//备注


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public String getSetupType() {
        return setupType;
    }

    public void setSetupType(String setupType) {
        this.setupType = setupType;
    }

    public String getSetupNo() {
        return setupNo;
    }

    public void setSetupNo(String setupNo) {
        this.setupNo = setupNo;
    }

    public String getSetupUnit() {
        return setupUnit;
    }

    public void setSetupUnit(String setupUnit) {
        this.setupUnit = setupUnit;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getCancelNo() {
        return cancelNo;
    }

    public void setCancelNo(String cancelNo) {
        this.cancelNo = cancelNo;
    }

    public String getCancelUnit() {
        return cancelUnit;
    }

    public void setCancelUnit(String cancelUnit) {
        this.cancelUnit = cancelUnit;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
