package com.becoda.bkms.run.pojo.vo;



/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-4-27
 * Time: 10:41:49
 * To change this template use File | Settings | File Templates.
 */
public class BulletinForm  {

    private String topic;
    private String content;
    private String submitDate;
    private String startDate;
    private String endDate;
    private String readerType;
    private String scopeOrgNames;
    private String scopeOrgIds;

    private String bulletinId;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public String getScopeOrgNames() {
        return scopeOrgNames;
    }

    public void setScopeOrgNames(String scopeOrgNames) {
        this.scopeOrgNames = scopeOrgNames;
    }

    public String getScopeOrgIds() {
        return scopeOrgIds;
    }

    public void setScopeOrgIds(String scopeOrgIds) {
        this.scopeOrgIds = scopeOrgIds;
    }

    public String getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }


}
