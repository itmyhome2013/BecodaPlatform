package com.becoda.bkms.csu.suggest.pojo;

import java.util.Date;

/**
 * KpOpinionFeedback entity. @author MyEclipse Persistence Tools
 */

public class KpOpinionFeedback implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 2054042778037066663L;
	private String opinionId;     //意见反馈ID
	private String foreignId;     //外键ID
	private String type;          //类型 (针对课程，班级，教师)
	private String content;       //意见内容
	private String createUserId;  //创建人ID
	private String createUserName;//创建人NAME
	private String createTime;      //创建时间
	private String updateUserId;  //修改人ID
	private String updateUserName;//修改人NAME
	private String updateTime;      //修改时间
	private String attr1;         //预留字段1
	private String attr2;         //预留字段2
	private String attr3;         //预留字段3
	private String isdel;         //删除状态

	// Constructors

	/** default constructor */
	public KpOpinionFeedback() {
	}

	/** full constructor */
	public KpOpinionFeedback(String foreignId, String type, String content,
			String createUserId, String createUserName, String createTime,
			String updateUserId, String updateUserName, String updateTime,
			String attr1, String attr2, String attr3,
			String isdel) {
		this.foreignId = foreignId;
		this.type = type;
		this.content = content;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		this.isdel = isdel;
	}

	// Property accessors

	public String getOpinionId() {
		return this.opinionId;
	}

	public void setOpinionId(String opinionId) {
		this.opinionId = opinionId;
	}

	public String getForeignId() {
		return this.foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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