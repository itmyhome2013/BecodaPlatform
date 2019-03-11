package com.becoda.bkms.csu.test.pojo1;



/**
 * KpActivityParticipant entity. @author MyEclipse Persistence Tools
 */

public class KpActivityParticipant  implements java.io.Serializable {


    // Fields    

     private String id;
     private String activityId;
     private String memId;
     private String isSuccess;
     private String registTime;
     private String registWay;


    // Constructors

    /** default constructor */
    public KpActivityParticipant() {
    }

    
    /** full constructor */
    public KpActivityParticipant(String activityId, String memId, String isSuccess, String registTime, String registWay) {
        this.activityId = activityId;
        this.memId = memId;
        this.isSuccess = isSuccess;
        this.registTime = registTime;
        this.registWay = registWay;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return this.activityId;
    }
    
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getMemId() {
        return this.memId;
    }
    
    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getIsSuccess() {
        return this.isSuccess;
    }
    
    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getRegistTime() {
        return this.registTime;
    }
    
    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public String getRegistWay() {
        return this.registWay;
    }
    
    public void setRegistWay(String registWay) {
        this.registWay = registWay;
    }
   








}