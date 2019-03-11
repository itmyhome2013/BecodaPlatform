package com.becoda.bkms.parkingms.special.pojo.bo;

import java.io.Serializable;


/**
 * 分值记录表
 * 
 * @author lhq
 * 
 */
public class ParmsSpecialSocreRec implements Serializable {
	private String recSocreId;
	private String specialId;// 整改id
	private String recId;// 单位id
	private String recName;// 单位名称
	private String checkOrgId;// 检查单位id
	private String checkOrgName;// 检查单位名称
	private String isProblemId;// 存在问题id
	private String isProblem;// 存在问题名称
	private String checkdate;// 检查时间
	private String checkUserId;// 检查人id
	private String checkUserName;// 检查人
	private String socreTime;// 分值日期
	private String socreId;// 分值id
	private String ctcid;// 区交委id


	public String getRecSocreId() {
		return recSocreId;
	}

	public void setRecSocreId(String recSocreId) {
		this.recSocreId = recSocreId;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getCheckOrgId() {
		return checkOrgId;
	}

	public void setCheckOrgId(String checkOrgId) {
		this.checkOrgId = checkOrgId;
	}

	public String getCheckOrgName() {
		return checkOrgName;
	}

	public void setCheckOrgName(String checkOrgName) {
		this.checkOrgName = checkOrgName;
	}

	public String getIsProblemId() {
		return isProblemId;
	}

	public void setIsProblemId(String isProblemId) {
		this.isProblemId = isProblemId;
	}

	public String getIsProblem() {
		return isProblem;
	}

	public void setIsProblem(String isProblem) {
		this.isProblem = isProblem;
	}

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getSocreTime() {
		return socreTime;
	}

	public void setSocreTime(String socreTime) {
		this.socreTime = socreTime;
	}

	public String getSocreId() {
		return socreId;
	}

	public void setSocreId(String socreId) {
		this.socreId = socreId;
	}

	public String getCtcid() {
		return ctcid;
	}

	public void setCtcid(String ctcid) {
		this.ctcid = ctcid;
	}


}
