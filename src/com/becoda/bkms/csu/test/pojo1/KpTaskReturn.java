package com.becoda.bkms.csu.test.pojo1;



/**
 * KpTaskReturn entity. @author MyEclipse Persistence Tools
 */

public class KpTaskReturn  implements java.io.Serializable {


    // Fields    

     private String taskReturnId;
     private String foreignId;
     private String parentId;
     private String parentName;
     private String returnUserId;
     private String returnUserName;
     private String returnUnitId;
     private String returnUnitName;
     private String content;
     private String createTime;
     private String orderId;


    // Constructors

    /** default constructor */
    public KpTaskReturn() {
    }

    
    /** full constructor */
    public KpTaskReturn(String foreignId, String parentId, String parentName, String returnUserId, String returnUserName, String returnUnitId, String returnUnitName, String content, String createTime, String orderId) {
        this.foreignId = foreignId;
        this.parentId = parentId;
        this.parentName = parentName;
        this.returnUserId = returnUserId;
        this.returnUserName = returnUserName;
        this.returnUnitId = returnUnitId;
        this.returnUnitName = returnUnitName;
        this.content = content;
        this.createTime = createTime;
        this.orderId = orderId;
    }

   
    // Property accessors

    public String getTaskReturnId() {
        return this.taskReturnId;
    }
    
    public void setTaskReturnId(String taskReturnId) {
        this.taskReturnId = taskReturnId;
    }

    public String getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return this.parentName;
    }
    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getReturnUserId() {
        return this.returnUserId;
    }
    
    public void setReturnUserId(String returnUserId) {
        this.returnUserId = returnUserId;
    }

    public String getReturnUserName() {
        return this.returnUserName;
    }
    
    public void setReturnUserName(String returnUserName) {
        this.returnUserName = returnUserName;
    }

    public String getReturnUnitId() {
        return this.returnUnitId;
    }
    
    public void setReturnUnitId(String returnUnitId) {
        this.returnUnitId = returnUnitId;
    }

    public String getReturnUnitName() {
        return this.returnUnitName;
    }
    
    public void setReturnUnitName(String returnUnitName) {
        this.returnUnitName = returnUnitName;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
   








}