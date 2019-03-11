package com.becoda.bkms.csu.test.pojo1;



/**
 * KpSurveyPaper entity. @author MyEclipse Persistence Tools
 */

public class KpSurveyPaper  implements java.io.Serializable {


    // Fields    

     private String suveryId;
     private String title;
     private String content;
     private String inquirer;
     private String byinquirer;
     private String create;
     private String satisfaction;
     private String opinion;


    // Constructors

    /** default constructor */
    public KpSurveyPaper() {
    }

    
    /** full constructor */
    public KpSurveyPaper(String title, String content, String inquirer, String byinquirer, String create, String satisfaction, String opinion) {
        this.title = title;
        this.content = content;
        this.inquirer = inquirer;
        this.byinquirer = byinquirer;
        this.create = create;
        this.satisfaction = satisfaction;
        this.opinion = opinion;
    }

   
    // Property accessors

    public String getSuveryId() {
        return this.suveryId;
    }
    
    public void setSuveryId(String suveryId) {
        this.suveryId = suveryId;
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

    public String getInquirer() {
        return this.inquirer;
    }
    
    public void setInquirer(String inquirer) {
        this.inquirer = inquirer;
    }

    public String getByinquirer() {
        return this.byinquirer;
    }
    
    public void setByinquirer(String byinquirer) {
        this.byinquirer = byinquirer;
    }

    public String getCreate() {
        return this.create;
    }
    
    public void setCreate(String create) {
        this.create = create;
    }

    public String getSatisfaction() {
        return this.satisfaction;
    }
    
    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getOpinion() {
        return this.opinion;
    }
    
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
   








}