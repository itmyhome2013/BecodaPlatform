package com.becoda.bkms.csu.test.pojo1;



/**
 * KpActivityCollect entity. @author MyEclipse Persistence Tools
 */

public class KpActivityCollect  implements java.io.Serializable {


    // Fields    

     private String favoriteId;
     private String activityId;
     private String userId;
     private String createTime;


    // Constructors

    /** default constructor */
    public KpActivityCollect() {
    }

    
    /** full constructor */
    public KpActivityCollect(String activityId, String userId, String createTime) {
        this.activityId = activityId;
        this.userId = userId;
        this.createTime = createTime;
    }

   
    // Property accessors

    public String getFavoriteId() {
        return this.favoriteId;
    }
    
    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getActivityId() {
        return this.activityId;
    }
    
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
   








}