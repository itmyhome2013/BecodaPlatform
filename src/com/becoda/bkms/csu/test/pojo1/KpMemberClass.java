package com.becoda.bkms.csu.test.pojo1;



/**
 * KpMemberClass entity. @author MyEclipse Persistence Tools
 */

public class KpMemberClass  implements java.io.Serializable {


    // Fields    

     private String id;
     private String memId;
     private String classId;


    // Constructors

    /** default constructor */
    public KpMemberClass() {
    }

    
    /** full constructor */
    public KpMemberClass(String memId, String classId) {
        this.memId = memId;
        this.classId = classId;
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

    public String getClassId() {
        return this.classId;
    }
    
    public void setClassId(String classId) {
        this.classId = classId;
    }
   








}