package com.becoda.bkms.csu.common.pojo;

import java.util.Date;

/**
 * KpAttachement entity. @author yinhui
 */

public class KpAttachement implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -4300062246790203308L;
	
	private String fileId;         //文件主键
	private String foreignId;      //关联外键
	private String createTime;       //创建时间
	private String createUserId;   //创建人ID
	private String createUserName; //创建人姓名
	private String updateTime;       //修改时间
	private String updateUserId;   //修改人ID
	private String updateUserName; //修改人姓名
	private String fileName;    //文件名称
	private String serialName;  //编号形式的文件名称
	private String fileUrl;     //文件的原路径  如：视频的原路径，图片的原路径，文件的原路径
	private String fileHdUrl;   //视频文件的高清路径   
	private String fileSdUrl;   //视频文件 的普清路径
	private String fileImgUrl;  //视频文件的截图文件路径
	private String fileCode;    //文件编码
	private String fileType;    //文件类型 1：视频   2：图片 3：文件 4：其他

	// Constructors

	/** default constructor */
	public KpAttachement() {
	}

	/** full constructor */
	public KpAttachement(String foreignId, String createTime,
			String createUserId, String createUserName, String updateTime,
			String updateUserId, String updateUserName,String fileName,String serialName,
			String fileUrl, String fileHdUrl, String fileSdUrl, String fileImgUrl, 
			String fileCode, String fileType) {
		this.foreignId = foreignId;
		this.createTime = createTime;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.updateTime = updateTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.fileName = fileName;
		this.serialName = serialName;
		this.fileUrl = fileUrl;
		this.fileHdUrl = fileHdUrl;
		this.fileSdUrl = fileSdUrl;
		this.fileImgUrl = fileImgUrl;
		this.fileCode = fileCode;
		this.fileType = fileType;
	}

	// Property accessors

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getForeignId() {
		return this.foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getFileHdUrl() {
		return fileHdUrl;
	}

	public void setFileHdUrl(String fileHdUrl) {
		this.fileHdUrl = fileHdUrl;
	}

	public String getFileSdUrl() {
		return fileSdUrl;
	}

	public void setFileSdUrl(String fileSdUrl) {
		this.fileSdUrl = fileSdUrl;
	}

	public String getFileImgUrl() {
		return fileImgUrl;
	}

	public void setFileImgUrl(String fileImgUrl) {
		this.fileImgUrl = fileImgUrl;
	}

	public String getFileCode() {
		return this.fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}