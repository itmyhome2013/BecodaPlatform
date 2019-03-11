package com.becoda.bkms.csu.test.pojo1;



/**
 * KpClassChooseCourse entity. @author MyEclipse Persistence Tools
 */

public class KpClassChooseCourse  implements java.io.Serializable {


    // Fields    

     private String id;
     private String classId;
     private String lessonId;
     private String time;
     private String address;
     private String linkName;
     private String linkPhone;


    // Constructors

    /** default constructor */
    public KpClassChooseCourse() {
    }

    
    /** full constructor */
    public KpClassChooseCourse(String classId, String lessonId, String time, String address, String linkName, String linkPhone) {
        this.classId = classId;
        this.lessonId = lessonId;
        this.time = time;
        this.address = address;
        this.linkName = linkName;
        this.linkPhone = linkPhone;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return this.classId;
    }
    
    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getLessonId() {
        return this.lessonId;
    }
    
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getTime() {
        return this.time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkName() {
        return this.linkName;
    }
    
    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkPhone() {
        return this.linkPhone;
    }
    
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }
   








}