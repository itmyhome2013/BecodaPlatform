package com.becoda.bkms.csu.classInfo.pojo;

import java.util.Date;

/**
 * KpClass entity. @author MyEclipse Persistence Tools
 */

public class KpClass implements java.io.Serializable {

	// Fields

	private String classId; //主键ID
	private String className;//班级名称
	private String classNum;//班级编码
	private String classResume;//班级简介
	private Date classStarttime;//开班时间
	private Date classEndtime;//结业时间
	private String headmasterName;//班主任名称
	private String headmasterResume;//班主任简介
	private String classType;//班级类别（特色）
	private String classNotice;//班级公告
	private String faceName;//封面
	private String faceUrl;//封面URL
	private String isBanner;//是否设置BANNER图
	private String createUserId;//创建人ID
	private String createUserName;//创建人NAME
	private Date createTime;//创建时间
	private String updateUserId;//修改人ID
	private String updateUserName;//修改人NAME
	private Date updateTime;//修改时间
	private String status;//流程状态
	private String attr1;//解散标识
	private String attr2;
	private String attr3;
	private String isdel;//删除状态

	// Constructors

	/** default constructor */
	public KpClass() {
	}

	/** full constructor */
	public KpClass(String className, String classNum, String classResume,
			Date classStarttime, Date classEndtime, String headmasterName,
			String headmasterResume, String classType, String classNotice,
			String faceName, String faceUrl, String isBanner,
			String createUserId, String createUserName, Date createTime,
			String updateUserId, String updateUserName, Date updateTime,
			String status, String attr1, String attr2, String attr3,
			String isdel) {
		this.className = className;
		this.classNum = classNum;
		this.classResume = classResume;
		this.classStarttime = classStarttime;
		this.classEndtime = classEndtime;
		this.headmasterName = headmasterName;
		this.headmasterResume = headmasterResume;
		this.classType = classType;
		this.classNotice = classNotice;
		this.faceName = faceName;
		this.faceUrl = faceUrl;
		this.isBanner = isBanner;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.status = status;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.isdel = isdel;
	}

	// Property accessors

	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassNum() {
		return this.classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getClassResume() {
		return this.classResume;
	}

	public void setClassResume(String classResume) {
		this.classResume = classResume;
	}

	public Date getClassStarttime() {
		return this.classStarttime;
	}

	public void setClassStarttime(Date classStarttime) {
		this.classStarttime = classStarttime;
	}

	public Date getClassEndtime() {
		return this.classEndtime;
	}

	public void setClassEndtime(Date classEndtime) {
		this.classEndtime = classEndtime;
	}

	public String getHeadmasterName() {
		return this.headmasterName;
	}

	public void setHeadmasterName(String headmasterName) {
		this.headmasterName = headmasterName;
	}

	public String getHeadmasterResume() {
		return this.headmasterResume;
	}

	public void setHeadmasterResume(String headmasterResume) {
		this.headmasterResume = headmasterResume;
	}

	public String getClassType() {
		return this.classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getClassNotice() {
		return this.classNotice;
	}

	public void setClassNotice(String classNotice) {
		this.classNotice = classNotice;
	}

	public String getFaceName() {
		return this.faceName;
	}

	public void setFaceName(String faceName) {
		this.faceName = faceName;
	}

	public String getFaceUrl() {
		return this.faceUrl;
	}

	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}

	public String getIsBanner() {
		return this.isBanner;
	}

	public void setIsBanner(String isBanner) {
		this.isBanner = isBanner;
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

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
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

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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