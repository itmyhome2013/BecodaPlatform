package com.becoda.bkms.csu.test.pojo1;



/**
 * KpOptionisReturn entity. @author MyEclipse Persistence Tools
 */

public class KpOptionisReturn  implements java.io.Serializable {


    // Fields    

     private String optionReturnId;
     private String optionId;
     private String replyUserId;
     private String replyUserName;
     private String replyContent;
     private String replytime;


    // Constructors

    /** default constructor */
    public KpOptionisReturn() {
    }

    
    /** full constructor */
    public KpOptionisReturn(String optionId, String replyUserId, String replyUserName, String replyContent, String replytime) {
        this.optionId = optionId;
        this.replyUserId = replyUserId;
        this.replyUserName = replyUserName;
        this.replyContent = replyContent;
        this.replytime = replytime;
    }

   
    // Property accessors

    public String getOptionReturnId() {
        return this.optionReturnId;
    }
    
    public void setOptionReturnId(String optionReturnId) {
        this.optionReturnId = optionReturnId;
    }

    public String getOptionId() {
        return this.optionId;
    }
    
    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getReplyUserId() {
        return this.replyUserId;
    }
    
    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return this.replyUserName;
    }
    
    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getReplyContent() {
        return this.replyContent;
    }
    
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplytime() {
        return this.replytime;
    }
    
    public void setReplytime(String replytime) {
        this.replytime = replytime;
    }
   








}