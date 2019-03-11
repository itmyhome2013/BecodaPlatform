package com.becoda.bkms.csu.test.pojo1;



/**
 * KpPersonPaper entity. @author MyEclipse Persistence Tools
 */

public class KpPersonPaper  implements java.io.Serializable {


    // Fields    

     private String id;
     private String memId;
     private String paperId;


    // Constructors

    /** default constructor */
    public KpPersonPaper() {
    }

    
    /** full constructor */
    public KpPersonPaper(String memId, String paperId) {
        this.memId = memId;
        this.paperId = paperId;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getMemId() {
        return this.memId;
    }
    
    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getPaperId() {
        return this.paperId;
    }
    
    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }
   








}