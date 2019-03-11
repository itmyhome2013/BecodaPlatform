package com.becoda.bkms.csu.test.pojo1;



/**
 * KpLessonManageInfo entity. @author MyEclipse Persistence Tools
 */

public class KpLessonManageInfo  implements java.io.Serializable {


    // Fields    

     private String lessonId;
     private String lessonName;
     private String lessonNum;
     private String lessonClassIficationmethod;
     private String lessonShape;
     private String lessonDescription;
     private String lessonFace;
     private String lessonFaceUrl;
     private String isOpen;
     private String isBanner;
     private Long lessonDuration;
     private Byte lessonHours;
     private String lessonSource;
     private String lessonFormSource;
     private String lectureSpecialist;
     private String lessonType;
     private String uploadTime;
     private String status;
     private String coursewareDevelopTime;
     private String createUserId;
     private String createUserName;
     private String createTime;
     private String updateUserId;
     private String updateUserName;
     private String updateTime;
     private String status2;
     private String attr1;
     private String attr2;
     private String attr3;
     private String isdel;


    // Constructors

    /** default constructor */
    public KpLessonManageInfo() {
    }

    
    /** full constructor */
    public KpLessonManageInfo(String lessonName, String lessonNum, String lessonClassIficationmethod, String lessonShape, String lessonDescription, String lessonFace, String lessonFaceUrl, String isOpen, String isBanner, Long lessonDuration, Byte lessonHours, String lessonSource, String lessonFormSource, String lectureSpecialist, String lessonType, String uploadTime, String status, String coursewareDevelopTime, String createUserId, String createUserName, String createTime, String updateUserId, String updateUserName, String updateTime, String status2, String attr1, String attr2, String attr3, String isdel) {
        this.lessonName = lessonName;
        this.lessonNum = lessonNum;
        this.lessonClassIficationmethod = lessonClassIficationmethod;
        this.lessonShape = lessonShape;
        this.lessonDescription = lessonDescription;
        this.lessonFace = lessonFace;
        this.lessonFaceUrl = lessonFaceUrl;
        this.isOpen = isOpen;
        this.isBanner = isBanner;
        this.lessonDuration = lessonDuration;
        this.lessonHours = lessonHours;
        this.lessonSource = lessonSource;
        this.lessonFormSource = lessonFormSource;
        this.lectureSpecialist = lectureSpecialist;
        this.lessonType = lessonType;
        this.uploadTime = uploadTime;
        this.status = status;
        this.coursewareDevelopTime = coursewareDevelopTime;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createTime = createTime;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.status2 = status2;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.isdel = isdel;
    }

   
    // Property accessors

    public String getLessonId() {
        return this.lessonId;
    }
    
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return this.lessonName;
    }
    
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonNum() {
        return this.lessonNum;
    }
    
    public void setLessonNum(String lessonNum) {
        this.lessonNum = lessonNum;
    }

    public String getLessonClassIficationmethod() {
        return this.lessonClassIficationmethod;
    }
    
    public void setLessonClassIficationmethod(String lessonClassIficationmethod) {
        this.lessonClassIficationmethod = lessonClassIficationmethod;
    }

    public String getLessonShape() {
        return this.lessonShape;
    }
    
    public void setLessonShape(String lessonShape) {
        this.lessonShape = lessonShape;
    }

    public String getLessonDescription() {
        return this.lessonDescription;
    }
    
    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public String getLessonFace() {
        return this.lessonFace;
    }
    
    public void setLessonFace(String lessonFace) {
        this.lessonFace = lessonFace;
    }

    public String getLessonFaceUrl() {
        return this.lessonFaceUrl;
    }
    
    public void setLessonFaceUrl(String lessonFaceUrl) {
        this.lessonFaceUrl = lessonFaceUrl;
    }

    public String getIsOpen() {
        return this.isOpen;
    }
    
    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getIsBanner() {
        return this.isBanner;
    }
    
    public void setIsBanner(String isBanner) {
        this.isBanner = isBanner;
    }

    public Long getLessonDuration() {
        return this.lessonDuration;
    }
    
    public void setLessonDuration(Long lessonDuration) {
        this.lessonDuration = lessonDuration;
    }

    public Byte getLessonHours() {
        return this.lessonHours;
    }
    
    public void setLessonHours(Byte lessonHours) {
        this.lessonHours = lessonHours;
    }

    public String getLessonSource() {
        return this.lessonSource;
    }
    
    public void setLessonSource(String lessonSource) {
        this.lessonSource = lessonSource;
    }

    public String getLessonFormSource() {
        return this.lessonFormSource;
    }
    
    public void setLessonFormSource(String lessonFormSource) {
        this.lessonFormSource = lessonFormSource;
    }

    public String getLectureSpecialist() {
        return this.lectureSpecialist;
    }
    
    public void setLectureSpecialist(String lectureSpecialist) {
        this.lectureSpecialist = lectureSpecialist;
    }

    public String getLessonType() {
        return this.lessonType;
    }
    
    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getUploadTime() {
        return this.uploadTime;
    }
    
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoursewareDevelopTime() {
        return this.coursewareDevelopTime;
    }
    
    public void setCoursewareDevelopTime(String coursewareDevelopTime) {
        this.coursewareDevelopTime = coursewareDevelopTime;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }
    
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }
    
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return this.updateUserId;
    }
    
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return this.updateUserName;
    }
    
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus2() {
        return this.status2;
    }
    
    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getAttr1() {
        return this.attr1;
    }
    
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return this.attr2;
    }
    
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return this.attr3;
    }
    
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getIsdel() {
        return this.isdel;
    }
    
    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }
   








}