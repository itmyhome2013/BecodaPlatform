package com.becoda.bkms.csu.test.pojo1;



/**
 * KpUserMsg entity. @author MyEclipse Persistence Tools
 */

public class KpUserMsg  implements java.io.Serializable {


    // Fields    

     private String umsgId;
     private String senderId;
     private String receiverId;
     private String mailId;
     private String sendStatus;
     private String receiveStatus;
     private String readStatus;


    // Constructors

    /** default constructor */
    public KpUserMsg() {
    }

    
    /** full constructor */
    public KpUserMsg(String senderId, String receiverId, String mailId, String sendStatus, String receiveStatus, String readStatus) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.mailId = mailId;
        this.sendStatus = sendStatus;
        this.receiveStatus = receiveStatus;
        this.readStatus = readStatus;
    }

   
    // Property accessors

    public String getUmsgId() {
        return this.umsgId;
    }
    
    public void setUmsgId(String umsgId) {
        this.umsgId = umsgId;
    }

    public String getSenderId() {
        return this.senderId;
    }
    
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return this.receiverId;
    }
    
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMailId() {
        return this.mailId;
    }
    
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getSendStatus() {
        return this.sendStatus;
    }
    
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getReceiveStatus() {
        return this.receiveStatus;
    }
    
    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getReadStatus() {
        return this.readStatus;
    }
    
    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
   








}