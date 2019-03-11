package com.becoda.bkms.doc.pojo.vo;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-13
 * Time: 14:35:38
 */
public class StudyVO {
    private String studyBeginDate;//起始时间  A246200
    private String studyEndDate;//终止时间  A246201
    private String school;//学校名称  A246202
    private String academy;//院系名称  A246203
    private String subject;//专业  A246204
    private String studyRecord;//  学历  A246205
    private String studyDegree;  // 学位  A246206
    private String property;//教育类型  A246219
    private String studyMethod;//学习方式 A246214
    private String graduate;//毕（结、肄）业 A246215

    public String getStudyMethod() {
        return studyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        this.studyMethod = studyMethod;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public String getStudyBeginDate() {
        return studyBeginDate;
    }

    public void setStudyBeginDate(String studyBeginDate) {
        this.studyBeginDate = studyBeginDate;
    }

    public String getStudyEndDate() {
        return studyEndDate;
    }

    public void setStudyEndDate(String studyEndDate) {
        this.studyEndDate = studyEndDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudyRecord() {
        return studyRecord;
    }

    public void setStudyRecord(String studyRecord) {
        this.studyRecord = studyRecord;
    }

    public String getStudyDegree() {
        return studyDegree;
    }

    public void setStudyDegree(String studyDegree) {
        this.studyDegree = studyDegree;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
