package com.becoda.bkms.csu.test.pojo1;



/**
 * KpTemplate entity. @author MyEclipse Persistence Tools
 */

public class KpTemplate  implements java.io.Serializable {


    // Fields    

     private String templateId;
     private String templateName;
     private String templateUrl;
     private String status;


    // Constructors

    /** default constructor */
    public KpTemplate() {
    }

    
    /** full constructor */
    public KpTemplate(String templateName, String templateUrl, String status) {
        this.templateName = templateName;
        this.templateUrl = templateUrl;
        this.status = status;
    }

   
    // Property accessors

    public String getTemplateId() {
        return this.templateId;
    }
    
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return this.templateName;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateUrl() {
        return this.templateUrl;
    }
    
    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   








}