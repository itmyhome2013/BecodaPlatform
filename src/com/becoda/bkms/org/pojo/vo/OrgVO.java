package com.becoda.bkms.org.pojo.vo;



/**
 * 机构基本信息VO
 * User: yxm
 * Date: 2015-6-19
 * Time: 9:39:48
 */
public class OrgVO  {
    private String orgId;     //机构编号
    private String orgCode;  //机构代码  B001010
    private String superId;
    private String orgName;
    private String orgAllName;
    private String nature;
    private String orgSort; //机构排序  B001715
    private String orgLevel;     //机构层级   B001050
    private String orgHeader;
    private String orgCancel;  //是否逻辑删除 B001730
    private String orgClass;
    private String phone; //电话 B001208
    private String postCode; //邮政编码   B001240
    private String orgAddress; //机构地址  B001225
    private String factPer;//机构负责人   B001203
    private String office;//职责     B001200
    private String stockKind; //股权控股性质 B001080
    private String stockSuper; //股权关系上级 B001085
    private String stockTreeId;//股权关系TreeId B001086
    private String secondTreeId; //内设机构双跨treeId   用于机构图
    private String orgArea;//机构行政区划  B001214
    private String fax;//传真 B001212
	private String yearsoc;//年扣分（另加属性）
	private String monthsoc;//月扣分
	private String weksoc;//周扣分

    //机构设立信息集
    private String deptSetupTime;
    private String deptSetupNo;
    private String orgTempNo;
    private String orgTempTime;
    private String agreeNo;
    private String agreeDate;
    private String workingTime;
    private String licenceTime;
    private String startTime;
    private String scmemo;

    public String getDeptSetupTime() {
        return deptSetupTime;
    }

    public void setDeptSetupTime(String deptSetupTime) {
        this.deptSetupTime = deptSetupTime;
    }

    public String getDeptSetupNo() {
        return deptSetupNo;
    }

    public void setDeptSetupNo(String deptSetupNo) {
        this.deptSetupNo = deptSetupNo;
    }

    public String getOrgTempNo() {
        return orgTempNo;
    }

    public void setOrgTempNo(String orgTempNo) {
        this.orgTempNo = orgTempNo;
    }

    public String getOrgTempTime() {
        return orgTempTime;
    }

    public void setOrgTempTime(String orgTempTime) {
        this.orgTempTime = orgTempTime;
    }

    public String getAgreeNo() {
        return agreeNo;
    }

    public void setAgreeNo(String agreeNo) {
        this.agreeNo = agreeNo;
    }

    public String getAgreeDate() {
        return agreeDate;
    }

    public void setAgreeDate(String agreeDate) {
        this.agreeDate = agreeDate;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getLicenceTime() {
        return licenceTime;
    }

    public void setLicenceTime(String licenceTime) {
        this.licenceTime = licenceTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getScmemo() {
        return scmemo;
    }

    public void setScmemo(String scmemo) {
        this.scmemo = scmemo;
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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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