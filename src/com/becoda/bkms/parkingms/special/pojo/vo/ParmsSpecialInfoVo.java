package com.becoda.bkms.parkingms.special.pojo.vo;

import java.util.List;

/**
 * 专项信息表
 * @author hudl
 * 2015-9-8 18:30:45
 */
public class ParmsSpecialInfoVo {

	private String specialId;          //specialid
	private String code;               //工单编号
	private String recId;              //整改单位id
	private String recName;            //整改单位名称
	private String checkOrgId;         //检查单位id
	private String checkOrgName;       //检查单位名称
	private String isProblemId;        //存在问题id
	private String isProblem;          //存在问题
	private String checkdate;          //检查时间
	private String checkcontent;       //检查情况
	private String checkUserId;        //检查人
	private String checkUserName;      //检查人名称
	private String auditOpinion;       //审批意见
	private String auditUserId;        //审批人
	private String auditUserName;      //审批人名称
	private String auditTime;          //审批时间
	private String guidance;           //指导意见
	private String guideUserId;        //指导人
	private String guideUserName;      //指导人名称
	private String guideTime;          //指导时间
	private String receiveOrgId;       //指派单位id
	private String receiveOrgName;     //指派单位名称
	private String receiveTime;        //指派时间
	private String recTification;      //整改情况
	private String recUserId;          //整改人
	private String recuserName;        //整改人名称
	private String recTime;            //整改时间
	private String againCheckContent;  //再次检查情况
	private String againTime;          //再次检查时间
	private String createTime;         //创建时间
	private String state;              //状态
	private String lastStateTime;      //最后修改状态时间
    
    private String filePathUUID;
    
    private String trialContent;//初审意见
	private String trialUserId;//初审人
	private String trialUserName;//初审人姓名
	private String trialTime;//初审时间
	private String confirmContent;//确认意见	
	private String confirmUserId;//确认人
	private String confirmUserName;//确认人姓名
	private String confirmTime;//确认时间

    
    
    private List recList;
    private List isProblemList;
    
    private String beginDate;
    private String endDate;
    private String filePath;
    private String fileName;
    private List<String> filePars;
    
    
    
	public String getTrialContent() {
		return trialContent;
	}
	public void setTrialContent(String trialContent) {
		this.trialContent = trialContent;
	}
	public String getTrialUserId() {
		return trialUserId;
	}
	public void setTrialUserId(String trialUserId) {
		this.trialUserId = trialUserId;
	}
	public String getTrialUserName() {
		return trialUserName;
	}
	public void setTrialUserName(String trialUserName) {
		this.trialUserName = trialUserName;
	}
	public String getTrialTime() {
		return trialTime;
	}
	public void setTrialTime(String trialTime) {
		this.trialTime = trialTime;
	}
	public String getConfirmContent() {
		return confirmContent;
	}
	public void setConfirmContent(String confirmContent) {
		this.confirmContent = confirmContent;
	}
	public String getConfirmUserId() {
		return confirmUserId;
	}
	public void setConfirmUserId(String confirmUserId) {
		this.confirmUserId = confirmUserId;
	}
	public String getConfirmUserName() {
		return confirmUserName;
	}
	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public List<String> getFilePars() {
		return filePars;
	}
	public void setFilePars(List<String> filePars) {
		this.filePars = filePars;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePathUUID() {
		return filePathUUID;
	}
	public void setFilePathUUID(String filePathUUID) {
		this.filePathUUID = filePathUUID;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getCheckcontent() {
		return checkcontent;
	}
	public void setCheckcontent(String checkcontent) {
		this.checkcontent = checkcontent;
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
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getGuidance() {
		return guidance;
	}
	public void setGuidance(String guidance) {
		this.guidance = guidance;
	}
	public String getGuideUserId() {
		return guideUserId;
	}
	public void setGuideUserId(String guideUserId) {
		this.guideUserId = guideUserId;
	}
	public String getGuideUserName() {
		return guideUserName;
	}
	public void setGuideUserName(String guideUserName) {
		this.guideUserName = guideUserName;
	}
	public String getGuideTime() {
		return guideTime;
	}
	public void setGuideTime(String guideTime) {
		this.guideTime = guideTime;
	}
	public String getReceiveOrgId() {
		return receiveOrgId;
	}
	public void setReceiveOrgId(String receiveOrgId) {
		this.receiveOrgId = receiveOrgId;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getRecTification() {
		return recTification;
	}
	public void setRecTification(String recTification) {
		this.recTification = recTification;
	}
	public String getRecUserId() {
		return recUserId;
	}
	public void setRecUserId(String recUserId) {
		this.recUserId = recUserId;
	}
	public String getRecuserName() {
		return recuserName;
	}
	public void setRecuserName(String recuserName) {
		this.recuserName = recuserName;
	}
	public String getRecTime() {
		return recTime;
	}
	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}
	public String getAgainCheckContent() {
		return againCheckContent;
	}
	public void setAgainCheckContent(String againCheckContent) {
		this.againCheckContent = againCheckContent;
	}
	public String getAgainTime() {
		return againTime;
	}
	public void setAgainTime(String againTime) {
		this.againTime = againTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLastStateTime() {
		return lastStateTime;
	}
	public void setLastStateTime(String lastStateTime) {
		this.lastStateTime = lastStateTime;
	}
	public List getRecList() {
		return recList;
	}
	public void setRecList(List recList) {
		this.recList = recList;
	}
	public List getIsProblemList() {
		return isProblemList;
	}
	public void setIsProblemList(List isProblemList) {
		this.isProblemList = isProblemList;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
    
}
