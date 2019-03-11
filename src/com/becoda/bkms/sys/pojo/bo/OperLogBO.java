/**
 *
 */
package com.becoda.bkms.sys.pojo.bo;

import java.sql.Timestamp;


public class OperLogBO {
    private String operId;
    /**
     * @pdOid 6ba0736c-beff-4a13-b557-b39401ad66ae
     */
    private Timestamp operDatetime;
    /**
     * @pdOid 6d13decd-a9d6-4e57-b877-73e9e826c3c4
     */
    private String operatorId;
    /**
     * @pdOid d95a77cb-29aa-4221-820b-90cc00b30702
     */
    private String operatorName;
    /**
     * @pdOid 49583f08-185e-431a-8e7b-318b83322c16
     */
    private String operType;

    public String getOperatorOrg() {
        return operatorOrg;
    }

    public void setOperatorOrg(String operatorOrg) {
        this.operatorOrg = operatorOrg;
    }

    private String operatorOrg;
    /**
     * @pdOid 9a82d1cf-372b-4e4f-973e-67ddc9cd4dae
     */
    private String operPersonId;
    /**
     * @pdOid c542f568-3767-4443-85af-761d86096950
     */
    private String operUsername;
    /**
     * @pdOid 91d40f94-28ae-471e-b4d8-573af839c357
     */
    private String operUserDept;
    /**
     * @pdOid 701301ea-db29-439f-8cd6-314671e7749a
     */
    private String operRecordId;
    /**
     * @pdOid 06ae6de5-da2e-40c5-9f4c-d9cc51d5c902
     */
    private String operInfosetId;
    /**
     * @pdOid e73ccb72-cd7d-4a44-9f23-f1f7ad4f1043
     */
    private String operInfosetName;
    /**
     * @pdOid 37426bb6-3dcd-4e7e-ba92-a9d6e1fb9365
     */
    private String operInfoitemId;
    /**
     * @pdOid 71c93d3b-8639-41a0-8ea9-7680204f0fde
     */
    private String operInfoitemName;
    /**
     * @pdOid 77bda11c-a263-46cc-add4-2ca60a37fab6
     */
    private String operValuePre;
    /**
     * @pdOid ce112088-6bb4-4c45-9baf-1f9ff39481ec
     */
    private String operDescPre;
    /**
     * @pdOid 8aec300e-97c2-40b9-bcd6-b4ba4f1371f4
     */
    private String operValueSuf;
    /**
     * @pdOid ebccb2c2-dcac-4be7-89a4-2b7d34c0b04c
     */
    private String operDescSuf;

    private String operOrgTreeId;//被操作人的 机构treeId

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public Timestamp getOperDatetime() {
        return operDatetime;
    }

    public void setOperDatetime(Timestamp operDatetime) {
        this.operDatetime = operDatetime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperPersonId() {
        return operPersonId;
    }

    public void setOperPersonId(String operPersonId) {
        this.operPersonId = operPersonId;
    }

    public String getOperUsername() {
        return operUsername;
    }

    public void setOperUsername(String operUsername) {
        this.operUsername = operUsername;
    }

    public String getOperUserDept() {
        return operUserDept;
    }

    public void setOperUserDept(String operUserDept) {
        this.operUserDept = operUserDept;
    }

    public String getOperRecordId() {
        return operRecordId;
    }

    public void setOperRecordId(String operRecordId) {
        this.operRecordId = operRecordId;
    }

    public String getOperInfosetId() {
        return operInfosetId;
    }

    public void setOperInfosetId(String operInfosetId) {
        this.operInfosetId = operInfosetId;
    }

    public String getOperInfosetName() {
        return operInfosetName;
    }

    public void setOperInfosetName(String operInfosetName) {
        this.operInfosetName = operInfosetName;
    }

    public String getOperInfoitemId() {
        return operInfoitemId;
    }

    public void setOperInfoitemId(String operInfoitemId) {
        this.operInfoitemId = operInfoitemId;
    }

    public String getOperInfoitemName() {
        return operInfoitemName;
    }

    public void setOperInfoitemName(String operInfoitemName) {
        this.operInfoitemName = operInfoitemName;
    }

    public String getOperValuePre() {
        return operValuePre;
    }

    public void setOperValuePre(String operValuePre) {
        this.operValuePre = operValuePre;
    }

    public String getOperDescPre() {
        return operDescPre;
    }

    public void setOperDescPre(String operDescPre) {
        this.operDescPre = operDescPre;
    }

    public String getOperValueSuf() {
        return operValueSuf;
    }

    public void setOperValueSuf(String operValueSuf) {
        this.operValueSuf = operValueSuf;
    }

    public String getOperDescSuf() {
        return operDescSuf;
    }

    public void setOperDescSuf(String operDescSuf) {
        this.operDescSuf = operDescSuf;
    }

    public String getOperOrgTreeId() {
        return operOrgTreeId;
    }

    public void setOperOrgTreeId(String operOrgTreeId) {
        this.operOrgTreeId = operOrgTreeId;
    }

    private String operDesc;
    private String moduleName;
    private String operatorDept;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc;
    }

    public String getOperatorDept() {
        return operatorDept;
    }

    public void setOperatorDept(String operatorDept) {
        this.operatorDept = operatorDept;
    }
}
