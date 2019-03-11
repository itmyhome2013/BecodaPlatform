package com.becoda.bkms.csu.test.pojo1;



/**
 * KpClickRecordDetail entity. @author MyEclipse Persistence Tools
 */

public class KpClickRecordDetail  implements java.io.Serializable {


    // Fields    

     private String id;
     private String recordId;
     private String foreignId;
     private String clickerId;
     private String createTime;
     private String type;


    // Constructors

    /** default constructor */
    public KpClickRecordDetail() {
    }

    
    /** full constructor */
    public KpClickRecordDetail(String recordId, String foreignId, String clickerId, String createTime, String type) {
        this.recordId = recordId;
        this.foreignId = foreignId;
        this.clickerId = clickerId;
        this.createTime = createTime;
        this.type = type;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getClickerId() {
        return this.clickerId;
    }
    
    public void setClickerId(String clickerId) {
        this.clickerId = clickerId;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
   








}