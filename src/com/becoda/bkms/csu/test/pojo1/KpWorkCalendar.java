package com.becoda.bkms.csu.test.pojo1;



/**
 * KpWorkCalendar entity. @author MyEclipse Persistence Tools
 */

public class KpWorkCalendar  implements java.io.Serializable {


    // Fields    

     private String taskId;
     private String content;
     private String startDate;
     private String endDate;
     private String createUserId;
     private String createUserName;
     private String createTime;


    // Constructors

    /** default constructor */
    public KpWorkCalendar() {
    }

    
    /** full constructor */
    public KpWorkCalendar(String content, String startDate, String endDate, String createUserId, String createUserName, String createTime) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createTime = createTime;
    }

   
    // Property accessors

    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
   








}