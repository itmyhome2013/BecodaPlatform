package com.becoda.bkms.run.pojo.vo;



/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-5-9
 * Time: 13:42:25
 * To change this template use File | Settings | File Templates.
 */
public class RemForm  {
    private String remId;
    private String remName;
    private String remCondDesc;
    private String remCond;
    private String remMsg;
    private String remField;
    private String remFieldDataType;
    private String validFlag;
    private String creator;
    private String createDate;
    private String createOrg;
    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getCreateOrg() {
        return createOrg;
    }

    public void setCreateOrg(String createOrg) {
        this.createOrg = createOrg;
    }

    public String getRemId() {
        return remId;
    }

    public void setRemId(String remId) {
        this.remId = remId;
    }

    public String getRemName() {
        return remName;
    }

    public void setRemName(String remName) {
        this.remName = remName;
    }

    public String getRemCondDesc() {
        return remCondDesc;
    }

    public void setRemCondDesc(String remCondDesc) {
        this.remCondDesc = remCondDesc;
    }

    public String getRemCond() {
        return remCond;
    }

    public void setRemCond(String remCond) {
        this.remCond = remCond;
    }

    public String getRemMsg() {
        return remMsg;
    }

    public void setRemMsg(String remMsg) {
        this.remMsg = remMsg;
    }

    public String getRemField() {
        return remField;
    }

    public void setRemField(String remField) {
        this.remField = remField;
    }

    public String getRemFieldDataType() {
        return remFieldDataType;
    }

    public void setRemFieldDataType(String remFieldDataType) {
        this.remFieldDataType = remFieldDataType;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
