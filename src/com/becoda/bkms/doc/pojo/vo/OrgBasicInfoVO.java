package com.becoda.bkms.doc.pojo.vo;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-4-11
 * Time: 17:00:20
 * To change this template use File | Settings | File Templates.
 */
public class OrgBasicInfoVO {
    //B001 机构基本信息
    private String orgId;     //机构编号
    private String orgCode;  //机构代码  B001010
    private String superId;
    private String name;
    private String orgAllName;
    private String nature;
    private String orgSort; //机构排序  B001715
    private String orgLevel;     //机构层级   B001050
    private String orgHeader;  //机构全称     B001255
    private String orgCancel;  //是否逻辑删除 B001730
    private String orgClass;
    private String phone; //电话 B001208
    private String postCode; //邮政编码   B001240
    private String orgAddress; //机构地址  B001225
    private String stockKind; //股权控股性质 B001080
    private String stockSuper; //股权关系上级 B001085
    private String stockTreeId;//股权关系TreeId B001086
    private String secondTreeId; //内设机构双跨treeId   用于机构图
    private String orgArea;//机构行政区划  B001214
    private String fax;//传真 B001212

    //B705 机构变动子集
    private String changeInfo;//机构变动情况

    private String factPer;//负责人   B001203
    private String office; //B001200 职责


    /**
     * @return Returns the changeInfo.
     */
    public String getChangeInfo() {
        return changeInfo;
    }

    /**
     * @param changeInfo The changeInfo to set.
     */
    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the operId.
     */


    /**
     * @return Returns the orgClass.
     */
    public String getOrgClass() {
        return orgClass;
    }

    /**
     * @param orgClass The orgClass to set.
     */
    public void setOrgClass(String orgClass) {
        this.orgClass = orgClass;
    }

    /**
     * @return Returns the orgCode.
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode The orgCode to set.
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }



    /**
     * @return Returns the phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return Returns the superId.
     */
    public String getSuperId() {
        return superId;
    }

    /**
     * @param superId The superId to set.
     */
    public void setSuperId(String superId) {
        this.superId = superId;
    }

    /**
     * @return Returns the orgId.
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId The orgId to set.
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }


    public String getFactPer() {
        return factPer;
    }

    public void setFactPer(String factPer) {
        this.factPer = factPer;
    }

    


    public String getOffice() {
        return office;
    }
    public void setOffice(String office) {
        this.office = office;
    }


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getOrgAllName() {
        return orgAllName;
    }

    public void setOrgAllName(String orgAllName) {
        this.orgAllName = orgAllName;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getOrgSort() {
        return orgSort;
    }

    public void setOrgSort(String orgSort) {
        this.orgSort = orgSort;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgHeader() {
        return orgHeader;
    }

    public void setOrgHeader(String orgHeader) {
        this.orgHeader = orgHeader;
    }

    public String getOrgCancel() {
        return orgCancel;
    }

    public void setOrgCancel(String orgCancel) {
        this.orgCancel = orgCancel;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getStockKind() {
        return stockKind;
    }

    public void setStockKind(String stockKind) {
        this.stockKind = stockKind;
    }

    public String getStockSuper() {
        return stockSuper;
    }

    public void setStockSuper(String stockSuper) {
        this.stockSuper = stockSuper;
    }

    public String getStockTreeId() {
        return stockTreeId;
    }

    public void setStockTreeId(String stockTreeId) {
        this.stockTreeId = stockTreeId;
    }


    public String getSecondTreeId() {
        return secondTreeId;
    }

    public void setSecondTreeId(String secondTreeId) {
        this.secondTreeId = secondTreeId;
    }

    public String getOrgArea() {
        return orgArea;
    }

    public void setOrgArea(String orgArea) {
        this.orgArea = orgArea;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
