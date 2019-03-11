package com.becoda.bkms.run.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-4-27
 * Time: 9:39:45
 * To change this template use File | Settings | File Templates.
 */
public class BulletinParamBO {
    private String blltnId;
    private String authorId;
    private String submitDate;
    private String blltnTopic;
    private String startDate;
    private String endDate;
    private String readerType;
    private String createOrgId;

    public String getBlltnId() {
        return blltnId;
    }

    public void setBlltnId(String blltnId) {
        this.blltnId = blltnId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getBlltnTopic() {
        return blltnTopic;
    }

    public void setBlltnTopic(String blltnTopic) {
        this.blltnTopic = blltnTopic;
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

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }
}
