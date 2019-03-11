package com.becoda.bkms.csu.test.pojo1;



/**
 * KpClassSpace entity. @author MyEclipse Persistence Tools
 */

public class KpClassSpace  implements java.io.Serializable {


    // Fields    

     private String spaceId;
     private String classId;
     private String title;
     private String pictureName;
     private String pictureUrl;
     private String vedioName;
     private String vedioUrl;
     private String suggest;
     private String status;
     private String handleUser;
     private String createUser;
     private String createTime;
     private String isCurveWrecker;


    // Constructors

    /** default constructor */
    public KpClassSpace() {
    }

    
    /** full constructor */
    public KpClassSpace(String classId, String title, String pictureName, String pictureUrl, String vedioName, String vedioUrl, String suggest, String status, String handleUser, String createUser, String createTime, String isCurveWrecker) {
        this.classId = classId;
        this.title = title;
        this.pictureName = pictureName;
        this.pictureUrl = pictureUrl;
        this.vedioName = vedioName;
        this.vedioUrl = vedioUrl;
        this.suggest = suggest;
        this.status = status;
        this.handleUser = handleUser;
        this.createUser = createUser;
        this.createTime = createTime;
        this.isCurveWrecker = isCurveWrecker;
    }

   
    // Property accessors

    public String getSpaceId() {
        return this.spaceId;
    }
    
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getClassId() {
        return this.classId;
    }
    
    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureName() {
        return this.pictureName;
    }
    
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }
    
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getVedioName() {
        return this.vedioName;
    }
    
    public void setVedioName(String vedioName) {
        this.vedioName = vedioName;
    }

    public String getVedioUrl() {
        return this.vedioUrl;
    }
    
    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public String getSuggest() {
        return this.suggest;
    }
    
    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getHandleUser() {
        return this.handleUser;
    }
    
    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsCurveWrecker() {
        return this.isCurveWrecker;
    }
    
    public void setIsCurveWrecker(String isCurveWrecker) {
        this.isCurveWrecker = isCurveWrecker;
    }
   








}