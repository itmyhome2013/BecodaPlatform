package com.becoda.bkms.csu.test.pojo1;



/**
 * KpQuestionOptions entity. @author MyEclipse Persistence Tools
 */

public class KpQuestionOptions  implements java.io.Serializable {


    // Fields    

     private String id;
     private String questionId;
     private String salisa;
     private String soption;


    // Constructors

    /** default constructor */
    public KpQuestionOptions() {
    }

    
    /** full constructor */
    public KpQuestionOptions(String questionId, String salisa, String soption) {
        this.questionId = questionId;
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

    public String getQuestionId() {
        return this.questionId;
    }
    
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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