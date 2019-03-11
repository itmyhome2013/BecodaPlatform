package com.becoda.bkms.csu.test.pojo1;



/**
 * KpQuestion entity. @author MyEclipse Persistence Tools
 */

public class KpQuestion  implements java.io.Serializable {


    // Fields    

     private String questionId;
     private String questionNum;
     private String questionType;
     private String questionClassify;
     private String questionContent;
     private String answer;
     private String hardLevel;
     private String lessonNum;
     private String lessonName;
     private String createUserId;
     private String createUserName;
     private String createTime;
     private String updateUserId;
     private String updateUserName;
     private String updateTime;
     private String status2;
     private String attr1;
     private String attr2;
     private String attr3;
     private String isdel;


    // Constructors

    /** default constructor */
    public KpQuestion() {
    }

    
    /** full constructor */
    public KpQuestion(String questionNum, String questionType, String questionClassify, String questionContent, String answer, String hardLevel, String lessonNum, String lessonName, String createUserId, String createUserName, String createTime, String updateUserId, String updateUserName, String updateTime, String status2, String attr1, String attr2, String attr3, String isdel) {
        this.questionNum = questionNum;
        this.questionType = questionType;
        this.questionClassify = questionClassify;
        this.questionContent = questionContent;
        this.answer = answer;
        this.hardLevel = hardLevel;
        this.lessonNum = lessonNum;
        this.lessonName = lessonName;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createTime = createTime;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.status2 = status2;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.isdel = isdel;
    }

   
    // Property accessors

    public String getQuestionId() {
        return this.questionId;
    }
    
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionNum() {
        return this.questionNum;
    }
    
    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestionType() {
        return this.questionType;
    }
    
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionClassify() {
        return this.questionClassify;
    }
    
    public void setQuestionClassify(String questionClassify) {
        this.questionClassify = questionClassify;
    }

    public String getQuestionContent() {
        return this.questionContent;
    }
    
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return this.answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHardLevel() {
        return this.hardLevel;
    }
    
    public void setHardLevel(String hardLevel) {
        this.hardLevel = hardLevel;
    }

    public String getLessonNum() {
        return this.lessonNum;
    }
    
    public void setLessonNum(String lessonNum) {
        this.lessonNum = lessonNum;
    }

    public String getLessonName() {
        return this.lessonName;
    }
    
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }
    
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }
    
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return this.updateUserId;
    }
    
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return this.updateUserName;
    }
    
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus2() {
        return this.status2;
    }
    
    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getAttr1() {
        return this.attr1;
    }
    
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return this.attr2;
    }
    
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return this.attr3;
    }
    
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getIsdel() {
        return this.isdel;
    }
    
    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }
   








}