package com.becoda.bkms.csu.test.pojo1;



/**
 * KpWorkflow entity. @author MyEclipse Persistence Tools
 */

public class KpWorkflow  implements java.io.Serializable {


    // Fields    

     private String flowId;
     private String foreognId;
     private String flowType;
     private String sendUserId;
     private String sendUserName;
     private String sendUnitId;
     private String sendUnitName;
     private String receiveUserId;
     private String receiveUserName;
     private String receiveUnitId;
     private String receiveUnitName;
     private String flowContent;
     private String flowNode;
     private String createTime;


    // Constructors

    /** default constructor */
    public KpWorkflow() {
    }

    
    /** full constructor */
    public KpWorkflow(String foreognId, String flowType, String sendUserId, String sendUserName, String sendUnitId, String sendUnitName, String receiveUserId, String receiveUserName, String receiveUnitId, String receiveUnitName, String flowContent, String flowNode, String createTime) {
        this.foreognId = foreognId;
        this.flowType = flowType;
        this.sendUserId = sendUserId;
        this.sendUserName = sendUserName;
        this.sendUnitId = sendUnitId;
        this.sendUnitName = sendUnitName;
        this.receiveUserId = receiveUserId;
        this.receiveUserName = receiveUserName;
        this.receiveUnitId = receiveUnitId;
        this.receiveUnitName = receiveUnitName;
        this.flowContent = flowContent;
        this.flowNode = flowNode;
        this.createTime = createTime;
    }

   
    // Property accessors

    public String getFlowId() {
        return this.flowId;
    }
    
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getForeognId() {
        return this.foreognId;
    }
    
    public void setForeognId(String foreognId) {
        this.foreognId = foreognId;
    }

    public String getFlowType() {
        return this.flowType;
    }
    
    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getSendUserId() {
        return this.sendUserId;
    }
    
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return this.sendUserName;
    }
    
    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendUnitId() {
        return this.sendUnitId;
    }
    
    public void setSendUnitId(String sendUnitId) {
        this.sendUnitId = sendUnitId;
    }

    public String getSendUnitName() {
        return this.sendUnitName;
    }
    
    public void setSendUnitName(String sendUnitName) {
        this.sendUnitName = sendUnitName;
    }

    public String getReceiveUserId() {
        return this.receiveUserId;
    }
    
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserName() {
        return this.receiveUserName;
    }
    
    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveUnitId() {
        return this.receiveUnitId;
    }
    
    public void setReceiveUnitId(String receiveUnitId) {
        this.receiveUnitId = receiveUnitId;
    }

    public String getReceiveUnitName() {
        return this.receiveUnitName;
    }
    
    public void setReceiveUnitName(String receiveUnitName) {
        this.receiveUnitName = receiveUnitName;
    }

    public String getFlowContent() {
        return this.flowContent;
    }
    
    public void setFlowContent(String flowContent) {
        this.flowContent = flowContent;
    }

    public String getFlowNode() {
        return this.flowNode;
    }
    
    public void setFlowNode(String flowNode) {
        this.flowNode = flowNode;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
   








}