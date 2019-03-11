package com.becoda.bkms.csu.test.pojo1;



/**
 * KpRegistration entity. @author MyEclipse Persistence Tools
 */

public class KpRegistration  implements java.io.Serializable {


    // Fields    

     private String regId;
     private String userId;
     private String registrationTime;
     private String createTime;
     private String state;


    // Constructors

    /** default constructor */
    public KpRegistration() {
    }

    
    /** full constructor */
    public KpRegistration(String userId, String registrationTime, String createTime, String state) {
        this.userId = userId;
        this.registrationTime = registrationTime;
        this.createTime = createTime;
        this.state = state;
    }

   
    // Property accessors

    public String getRegId() {
        return this.regId;
    }
    
    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegistrationTime() {
        return this.registrationTime;
    }
    
    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
   








}