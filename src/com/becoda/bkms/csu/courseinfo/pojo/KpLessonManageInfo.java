package com.becoda.bkms.csu.courseinfo.pojo;

/**
 * 课程对象
 */
public class KpLessonManageInfo  implements java.io.Serializable {


    // Fields    

     private String lessonId;//课程ID
     private String lessonName;//课程名称
     private String lessonNum;//课程编号
     private String lessonClassIficationmethod;//课程分类方式 1.学科专业；2.科普类型；3.适合人群
     private String lessonShape;//课程形式
     private String lessonDescription;//课程简介
     private String lessonFace;//课程封面
     private String lessonFaceUrl;//课程封面路径
     private String isOpen;//是否公开
     private String isBanner;//是否设置BANNER图
     private Long lessonDuration;//课程时长
     private Byte lessonHours;//课程学时
     private String lessonSource;//课件来源
     private String lessonFormSource;//转载来源
     private String lectureSpecialist;//讲座专家
     private String lessonType;//课程类型
     private String uploadTime;//上传时间
     private String status;//流程状态
     private String coursewareDevelopTime;//课件开发时间
     private String createUserId;//创建人ID
     private String createUserName;//创建人NAME
     private String createTime;//创建时间
     private String updateUserId;//修改人ID
     private String updateUserName;//修改人NAME
     private String updateTime;//修改时间
     private String status2;//流程状态2
     private String attr1;//预留字段1
     private String attr2;//预留字段2
     private String attr3;//预留字段3
     private String isdel;//删除状态:0未删除,1已删除

    // Constructors

    public KpLessonManageInfo() {
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