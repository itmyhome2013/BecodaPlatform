package com.becoda.bkms.csu.test.pojo1;



/**
 * KpSuverypaperDetail entity. @author MyEclipse Persistence Tools
 */

public class KpSuverypaperDetail  implements java.io.Serializable {


    // Fields    

     private String id;
     private String suveryPaperId;
     private String suveryQuestionId;
     private String orderId;


    // Constructors

    /** default constructor */
    public KpSuverypaperDetail() {
    }

    
    /** full constructor */
    public KpSuverypaperDetail(String suveryPaperId, String suveryQuestionId, String orderId) {
        this.suveryPaperId = suveryPaperId;
        this.suveryQuestionId = suveryQuestionId;
        this.orderId = orderId;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getSuveryPaperId() {
        return this.suveryPaperId;
    }
    
    public void setSuveryPaperId(String suveryPaperId) {
        this.suveryPaperId = suveryPaperId;
    }

    public String getSuveryQuestionId() {
        return this.suveryQuestionId;
    }
    
    public void setSuveryQuestionId(String suveryQuestionId) {
        this.suveryQuestionId = suveryQuestionId;
    }

    public String getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
   








}