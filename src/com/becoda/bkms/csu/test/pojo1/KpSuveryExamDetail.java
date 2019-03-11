package com.becoda.bkms.csu.test.pojo1;



/**
 * KpSuveryExamDetail entity. @author MyEclipse Persistence Tools
 */

public class KpSuveryExamDetail  implements java.io.Serializable {


    // Fields    

     private String examDetailId;
     private String userId;
     private String paperId;
     private String questionId;
     private String userAnswer;


    // Constructors

    /** default constructor */
    public KpSuveryExamDetail() {
    }

    
    /** full constructor */
    public KpSuveryExamDetail(String userId, String paperId, String questionId, String userAnswer) {
        this.userId = userId;
        this.paperId = paperId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
    }

   
    // Property accessors

    public String getExamDetailId() {
        return this.examDetailId;
    }
    
    public void setExamDetailId(String examDetailId) {
        this.examDetailId = examDetailId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaperId() {
        return this.paperId;
    }
    
    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getQuestionId() {
        return this.questionId;
    }
    
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return this.userAnswer;
    }
    
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
   








}