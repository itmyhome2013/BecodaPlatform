package com.becoda.bkms.csu.test.pojo1;



/**
 * KpOpinionFeedback entity. @author MyEclipse Persistence Tools
 */

public class KpOpinionFeedback  implements java.io.Serializable {


    // Fields    

     private String opinionId;
     private String foreignId;
     private String type;
     private String content;
     private String createUserId;
     private String createUserName;
     private String createTime;
     private String updateUserId;
     private String updateUserName;
     private String updateTime;
     private String attr1;
     private String attr2;
     private String attr3;
     private String isdel;


    // Constructors

    /** default constructor */
    public KpOpinionFeedback() {
    }

    
    /** full constructor */
    public KpOpinionFeedback(String foreignId, String type, String content, String createUserId, String createUserName, String createTime, String updateUserId, String updateUserName, String updateTime, String attr1, String attr2, String attr3, String isdel) {
        this.foreignId = foreignId;
        this.type = type;
        this.content = content;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createTime = createTime;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.isdel = isdel;
    }

   
    // Property accessors

    public String getOpinionId() {
        return this.opinionId;
    }
    
    public void setOpinionId(String opinionId) {
        this.opinionId = opinionId;
    }

    public String getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
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