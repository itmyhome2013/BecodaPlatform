package com.becoda.bkms.csu.test.pojo1;



/**
 * KpOptions entity. @author MyEclipse Persistence Tools
 */

public class KpOptions  implements java.io.Serializable {


    // Fields    

     private String optionId;
     private String createUserId;
     private String createUserName;
     private String content;
     private String createTime;


    // Constructors

    /** default constructor */
    public KpOptions() {
    }

    
    /** full constructor */
    public KpOptions(String createUserId, String createUserName, String content, String createTime) {
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.content = content;
        this.createTime = createTime;
    }

   
    // Property accessors

    public String getOptionId() {
        return this.optionId;
    }
    
    public void setOptionId(String optionId) {
        this.optionId = optionId;
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

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
   








}