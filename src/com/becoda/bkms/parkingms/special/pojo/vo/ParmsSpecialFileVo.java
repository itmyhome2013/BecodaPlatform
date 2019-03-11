package com.becoda.bkms.parkingms.special.pojo.vo;


/**
 * 专项附件表
 * @author hudl
 * 2015-9-8 18:30:45
 */
public class ParmsSpecialFileVo {

	private String fileId;
	private String specialId;//整改id
	private String fileName;//附件名称
	private String filePath;//附件路径
	private String createTime;//上传时间
	private String orderNum;//排序号
	private String filePathUUID;
	private String type;
	
	public String getFilePathUUID() {
		return filePathUUID;
	}
	public void setFilePathUUID(String filePathUUID) {
		this.filePathUUID = filePathUUID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
}
