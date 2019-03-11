package com.becoda.bkms.csu.test.pojo1;



/**
 * KpJoinus entity. @author MyEclipse Persistence Tools
 */

public class KpJoinus  implements java.io.Serializable {


    // Fields    

     private String joinId;
     private String unitName;
     private String unitAddress;
     private String unitPhone;
     private String contactor;
     private String contactorPhone;
     private String createTime;
     private String synopsis;
     private String scholz;
     private String companyProperty;
     private String industry;
     private String cooperationIntention;
     private String status;


    // Constructors

    /** default constructor */
    public KpJoinus() {
    }

    
    /** full constructor */
    public KpJoinus(String unitName, String unitAddress, String unitPhone, String contactor, String contactorPhone, String createTime, String synopsis, String scholz, String companyProperty, String industry, String cooperationIntention, String status) {
        this.unitName = unitName;
        this.unitAddress = unitAddress;
        this.unitPhone = unitPhone;
        this.contactor = contactor;
        this.contactorPhone = contactorPhone;
        this.createTime = createTime;
        this.synopsis = synopsis;
        this.scholz = scholz;
        this.companyProperty = companyProperty;
        this.industry = industry;
        this.cooperationIntention = cooperationIntention;
        this.status = status;
    }

   
    // Property accessors

    public String getJoinId() {
        return this.joinId;
    }
    
    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }

    public String getUnitName() {
        return this.unitName;
    }
    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitAddress() {
        return this.unitAddress;
    }
    
    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getUnitPhone() {
        return this.unitPhone;
    }
    
    public void setUnitPhone(String unitPhone) {
        this.unitPhone = unitPhone;
    }

    public String getContactor() {
        return this.contactor;
    }
    
    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getContactorPhone() {
        return this.contactorPhone;
    }
    
    public void setContactorPhone(String contactorPhone) {
        this.contactorPhone = contactorPhone;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSynopsis() {
        return this.synopsis;
    }
    
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getScholz() {
        return this.scholz;
    }
    
    public void setScholz(String scholz) {
        this.scholz = scholz;
    }

    public String getCompanyProperty() {
        return this.companyProperty;
    }
    
    public void setCompanyProperty(String companyProperty) {
        this.companyProperty = companyProperty;
    }

    public String getIndustry() {
        return this.industry;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCooperationIntention() {
        return this.cooperationIntention;
    }
    
    public void setCooperationIntention(String cooperationIntention) {
        this.cooperationIntention = cooperationIntention;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   








}