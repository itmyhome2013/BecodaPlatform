package com.becoda.bkms.csu.test.pojo1;



/**
 * KpSurveyOptions entity. @author MyEclipse Persistence Tools
 */

public class KpSurveyOptions  implements java.io.Serializable {


    // Fields    

     private String id;
     private String suveryId;
     private String salisa;
     private String soption;


    // Constructors

    /** default constructor */
    public KpSurveyOptions() {
    }

    
    /** full constructor */
    public KpSurveyOptions(String suveryId, String salisa, String soption) {
        this.suveryId = suveryId;
        this.salisa = salisa;
        this.soption = soption;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getSuveryId() {
        return this.suveryId;
    }
    
    public void setSuveryId(String suveryId) {
        this.suveryId = suveryId;
    }

    public String getSalisa() {
        return this.salisa;
    }
    
    public void setSalisa(String salisa) {
        this.salisa = salisa;
    }

    public String getSoption() {
        return this.soption;
    }
    
    public void setSoption(String soption) {
        this.soption = soption;
    }
   








}