package com.becoda.bkms.csu.test.pojo1;

import java.math.BigDecimal;


/**
 * KpVideoRecord entity. @author MyEclipse Persistence Tools
 */

public class KpVideoRecord  implements java.io.Serializable {


    // Fields    

     private String recordId;
     private String foreignId;
     private String userId;
     private String videoId;
     private String createTime;
     private String preBreadPointTime;
     private BigDecimal recordTime;


    // Constructors

    /** default constructor */
    public KpVideoRecord() {
    }

    
    /** full constructor */
    public KpVideoRecord(String foreignId, String userId, String videoId, String createTime, String preBreadPointTime, BigDecimal recordTime) {
        this.foreignId = foreignId;
        this.userId = userId;
        this.videoId = videoId;
        this.createTime = createTime;
        this.preBreadPointTime = preBreadPointTime;
        this.recordTime = recordTime;
    }

   
    // Property accessors

    public String getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return this.videoId;
    }
    
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPreBreadPointTime() {
        return this.preBreadPointTime;
    }
    
    public void setPreBreadPointTime(String preBreadPointTime) {
        this.preBreadPointTime = preBreadPointTime;
    }

    public BigDecimal getRecordTime() {
        return this.recordTime;
    }
    
    public void setRecordTime(BigDecimal recordTime) {
        this.recordTime = recordTime;
    }
   








}