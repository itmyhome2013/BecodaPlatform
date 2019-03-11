package com.becoda.bkms.csu.test.pojo1;



/**
 * KpMemberLesson entity. @author MyEclipse Persistence Tools
 */

public class KpMemberLesson  implements java.io.Serializable {


    // Fields    

     private String id;
     private String lessonId;
     private String memId;


    // Constructors

    /** default constructor */
    public KpMemberLesson() {
    }

    
    /** full constructor */
    public KpMemberLesson(String lessonId, String memId) {
        this.lessonId = lessonId;
        this.memId = memId;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getLessonId() {
        return this.lessonId;
    }
    
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getMemId() {
        return this.memId;
    }
    
    public void setMemId(String memId) {
        this.memId = memId;
    }
   








}