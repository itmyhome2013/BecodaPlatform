package com.becoda.bkms.csu.test.pojo1;



/**
 * KpAttachement entity. @author MyEclipse Persistence Tools
 */

public class KpAttachement  implements java.io.Serializable {


    // Fields    

     private String fileId;
     private String foreignId;
     private String createTime;
     private String createUserId;
     private String createUserName;
     private String fileName;
     private String serialName;
     private String fileUrl;
     private String fileHdUrl;
     private String fileSdUrl;
     private String fileImgUrl;
     private String fileCode;
     private String fileType;


    // Constructors

    /** default constructor */
    public KpAttachement() {
    }

    
    /** full constructor */
    public KpAttachement(String foreignId, String createTime, String createUserId, String createUserName, String fileName, String serialName, String fileUrl, String fileHdUrl, String fileSdUrl, String fileImgUrl, String fileCode, String fileType) {
        this.foreignId = foreignId;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.fileName = fileName;
        this.serialName = serialName;
        this.fileUrl = fileUrl;
        this.fileHdUrl = fileHdUrl;
        this.fileSdUrl = fileSdUrl;
        this.fileImgUrl = fileImgUrl;
        this.fileCode = fileCode;
        this.fileType = fileType;
    }

   
    // Property accessors

    public String getFileId() {
        return this.fileId;
    }
    
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSerialName() {
        return this.serialName;
    }
    
    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileHdUrl() {
        return this.fileHdUrl;
    }
    
    public void setFileHdUrl(String fileHdUrl) {
        this.fileHdUrl = fileHdUrl;
    }

    public String getFileSdUrl() {
        return this.fileSdUrl;
    }
    
    public void setFileSdUrl(String fileSdUrl) {
        this.fileSdUrl = fileSdUrl;
    }

    public String getFileImgUrl() {
        return this.fileImgUrl;
    }
    
    public void setFileImgUrl(String fileImgUrl) {
        this.fileImgUrl = fileImgUrl;
    }

    public String getFileCode() {
        return this.fileCode;
    }
    
    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
   








}