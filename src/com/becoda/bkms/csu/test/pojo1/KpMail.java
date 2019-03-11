package com.becoda.bkms.csu.test.pojo1;



/**
 * KpMail entity. @author MyEclipse Persistence Tools
 */

public class KpMail  implements java.io.Serializable {


    // Fields    

     private String mailId;
     private String title;
     private String content;
     private String sendTime;
     private String isSend;


    // Constructors

    /** default constructor */
    public KpMail() {
    }

    
    /** full constructor */
    public KpMail(String title, String content, String sendTime, String isSend) {
        this.title = title;
        this.content = content;
        this.sendTime = sendTime;
        this.isSend = isSend;
    }

   
    // Property accessors

    public String getMailId() {
        return this.mailId;
    }
    
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return this.sendTime;
    }
    
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getIsSend() {
        return this.isSend;
    }
    
    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }
   








}