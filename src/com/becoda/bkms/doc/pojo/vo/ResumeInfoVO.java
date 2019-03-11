package com.becoda.bkms.doc.pojo.vo;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-13
 * Time: 14:24:39
 */
public class ResumeInfoVO {
    //	A019 简历
    private String resumeBeginDate;//简历开始时间 A019005
    private String resumeEndDate;//简历结束时间 A019010
    private String resumeUnit;//工作单位 A019015
    private String resumeDept;//A019020  部门
    private String resumeDuty;//职务名称 A019205

    public String getResumeBeginDate() {
        return resumeBeginDate;
    }

    public void setResumeBeginDate(String resumeBeginDate) {
        this.resumeBeginDate = resumeBeginDate;
    }

    public String getResumeEndDate() {
        return resumeEndDate;
    }

    public void setResumeEndDate(String resumeEndDate) {
        this.resumeEndDate = resumeEndDate;
    }

    public String getResumeUnit() {
        return resumeUnit;
    }

    public void setResumeUnit(String resumeUnit) {
        this.resumeUnit = resumeUnit;
    }

    public String getResumeDept() {
        return resumeDept;
    }

    public void setResumeDept(String resumeDept) {
        this.resumeDept = resumeDept;
    }

    public String getResumeDuty() {
        return resumeDuty;
    }

    public void setResumeDuty(String resumeDuty) {
        this.resumeDuty = resumeDuty;
    }
}
