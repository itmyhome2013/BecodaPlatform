package com.becoda.bkms.csu.test.pojo1;



/**
 * KpBannerPictures entity. @author MyEclipse Persistence Tools
 */

public class KpBannerPictures  implements java.io.Serializable {


    // Fields    

     private String bannerId;
     private String foreignId;
     private String tableName;
     private String title;
     private String faceName;
     private String faceUrl;
     private String setTime;


    // Constructors

    /** default constructor */
    public KpBannerPictures() {
    }

    
    /** full constructor */
    public KpBannerPictures(String foreignId, String tableName, String title, String faceName, String faceUrl, String setTime) {
        this.foreignId = foreignId;
        this.tableName = tableName;
        this.title = title;
        this.faceName = faceName;
        this.faceUrl = faceUrl;
        this.setTime = setTime;
    }

   
    // Property accessors

    public String getBannerId() {
        return this.bannerId;
    }
    
    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getFaceName() {
        return this.faceName;
    }
    
    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }

    public String getFaceUrl() {
        return this.faceUrl;
    }
    
    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getSetTime() {
        return this.setTime;
    }
    
    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }
   








}