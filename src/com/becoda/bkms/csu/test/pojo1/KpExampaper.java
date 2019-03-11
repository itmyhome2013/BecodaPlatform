package com.becoda.bkms.csu.test.pojo1;

import java.math.BigDecimal;


/**
 * KpExampaper entity. @author MyEclipse Persistence Tools
 */

public class KpExampaper  implements java.io.Serializable {


    // Fields    

     private String paperId;
     private String lessonOptioin;
     private String examTimelong;
     private BigDecimal score;
     private BigDecimal judgeNum;
     private BigDecimal optionNum;
     private BigDecimal optionsNum;
     private BigDecimal total;
     private String questionBuildType;
     private String questionMatchType;
     private String lessonTypePercent;
     private String status;
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
    public KpExampaper() {
    }

    
    /** full constructor */
    public KpExampaper(String lessonOptioin, String examTimelong, BigDecimal score, BigDecimal judgeNum, BigDecimal optionNum, BigDecimal optionsNum, BigDecimal total, String questionBuildType, String questionMatchType, String lessonTypePercent, String status, String createUserId, String createUserName, String createTime, String updateUserId, String updateUserName, String updateTime, String status2, String attr1, String attr2, String attr3, String isdel) {
        this.lessonOptioin = lessonOptioin;
        this.examTimelong = examTimelong;
        this.score = score;
        this.judgeNum = judgeNum;
        this.optionNum = optionNum;
        this.optionsNum = optionsNum;
        this.total = total;
        this.questionBuildType = questionBuildType;
        this.questionMatchType = questionMatchType;
        this.lessonTypePercent = lessonTypePercent;
        this.status = status;
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

    public String getPaperId() {
        return this.paperId;
    }
    
    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getLessonOptioin() {
        return this.lessonOptioin;
    }
    
    public void setLessonOptioin(String lessonOptioin) {
        this.lessonOptioin = lessonOptioin;
    }

    public String getExamTimelong() {
        return this.examTimelong;
    }
    
    public void setExamTimelong(String examTimelong) {
        this.examTimelong = examTimelong;
    }

    public BigDecimal getScore() {
        return this.score;
    }
    
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getJudgeNum() {
        return this.judgeNum;
    }
    
    public void setJudgeNum(BigDecimal judgeNum) {
        this.judgeNum = judgeNum;
    }

    public BigDecimal getOptionNum() {
        return this.optionNum;
    }
    
    public void setOptionNum(BigDecimal optionNum) {
        this.optionNum = optionNum;
    }

    public BigDecimal getOptionsNum() {
        return this.optionsNum;
    }
    
    public void setOptionsNum(BigDecimal optionsNum) {
        this.optionsNum = optionsNum;
    }

    public BigDecimal getTotal() {
        return this.total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getQuestionBuildType() {
        return this.questionBuildType;
    }
    
    public void setQuestionBuildType(String questionBuildType) {
        this.questionBuildType = questionBuildType;
    }

    public String getQuestionMatchType() {
        return this.questionMatchType;
    }
    
    public void setQuestionMatchType(String questionMatchType) {
        this.questionMatchType = questionMatchType;
    }

    public String getLessonTypePercent() {
        return this.lessonTypePercent;
    }
    
    public void setLessonTypePercent(String lessonTypePercent) {
        this.lessonTypePercent = lessonTypePercent;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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