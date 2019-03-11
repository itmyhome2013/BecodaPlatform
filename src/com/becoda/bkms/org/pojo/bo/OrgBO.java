package com.becoda.bkms.org.pojo.bo;

import com.becoda.bkms.common.pojo.vo.NodeVO;

/**
 * User: yxm
 * Date: 2015-6-7
 * Time: 14:18:52
 */
public class OrgBO extends NodeVO {
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
    private String chargeLeader;//主管领导
    private String factPer;//机构负责人   B001203
    private String office;//职责     B001200
    private String stockKind; //股权控股性质 B001080
    private String stockSuper; //股权关系上级 B001085
    private String stockTreeId;//股权关系TreeId B001086
    private String secondTreeId; //内设机构双跨treeId   用于机构图
    private String orgArea;//机构行政区划  B001214
    private String fax;//传真 B001212
    private String factPersonNum;
	private String yearsoc;//年扣分（另加属性）
	private String monthsoc;//月扣分
	private String weksoc;//周扣分

    public String getFactPersonNum() {
        return factPersonNum;
    }

    public void setFactPersonNum(String factPersonNum) {
        this.factPersonNum = factPersonNum;
    }

    public String getOrgAllName() {
        return orgAllName;
    }

    public void setOrgAllName(String orgAllName) {
        this.orgAllName = orgAllName;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getOrgArea() {
        return orgArea;
    }

    public void setOrgArea(String orgArea) {
        this.orgArea = orgArea;
    }


    public String getOrgHeader() {
        return orgHeader;
    }

    public void setOrgHeader(String orgHeader) {
        this.orgHeader = orgHeader;
    }


    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
        super.setPkId(orgId);
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgSort() {
        return orgSort;
    }

    public void setOrgSort(String orgSort) {
        this.orgSort = orgSort;
    }


    public String getOrgCancel() {
        return orgCancel;
    }

    public void setOrgCancel(String orgCancel) {
        this.orgCancel = orgCancel;
    }

    public String getOrgClass() {
        return orgClass;
    }

    public void setOrgClass(String orgClass) {
        this.orgClass = orgClass;
    }


    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChargeLeader() {
        return chargeLeader;
    }

    public void setChargeLeader(String chargeLeader) {
        this.chargeLeader = chargeLeader;
    }

    public String getFactPer() {
        return factPer;
    }

    public void setFactPer(String factPer) {
        this.factPer = factPer;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
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

	public String getYearsoc() {
		return yearsoc;
	}

	public void setYearsoc(String yearsoc) {
		this.yearsoc = yearsoc;
	}

	public String getMonthsoc() {
		return monthsoc;
	}

	public void setMonthsoc(String monthsoc) {
		this.monthsoc = monthsoc;
	}

	public String getWeksoc() {
		return weksoc;
	}

	public void setWeksoc(String weksoc) {
		this.weksoc = weksoc;
	}
    
}
