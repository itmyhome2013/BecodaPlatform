package com.becoda.bkms.csu.test.pojo1;



/**
 * KpUserCertificate entity. @author MyEclipse Persistence Tools
 */

public class KpUserCertificate  implements java.io.Serializable {


    // Fields    

     private String certificateId;
     private String userId;
     private String filenAme;
     private String fileuRl;
     private String createTime;
     private String graduationTime;
     private String graduationContent;


    // Constructors

    /** default constructor */
    public KpUserCertificate() {
    }

    
    /** full constructor */
    public KpUserCertificate(String userId, String filenAme, String fileuRl, String createTime, String graduationTime, String graduationContent) {
        this.userId = userId;
        this.filenAme = filenAme;
        this.fileuRl = fileuRl;
        this.createTime = createTime;
        this.graduationTime = graduationTime;
        this.graduationContent = graduationContent;
    }

   
    // Property accessors

    public String getCertificateId() {
        return this.certificateId;
    }
    
    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFilenAme() {
        return this.filenAme;
    }
    
    public void setFilenAme(String filenAme) {
        this.filenAme = filenAme;
    }

    public String getFileuRl() {
        return this.fileuRl;
    }
    
    public void setFileuRl(String fileuRl) {
        this.fileuRl = fileuRl;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGraduationTime() {
        return this.graduationTime;
    }
    
    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getGraduationContent() {
        return this.graduationContent;
    }
    
    public void setGraduationContent(String graduationContent) {
        this.graduationContent = graduationContent;
    }
   








}