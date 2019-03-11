package com.becoda.bkms.pcs.pojo.bo;

/**
 * ActExApproval entity. @author MyEclipse Persistence Tools
 */

public class ActExApprovalBO implements java.io.Serializable {

	// Fields

	private String approvalid;
	private String processid;
	private String approvaldate;
	private String approvalmessage;
	private String approvaluserid;
	private String approvalstate;
	private String approvaltaskkey;
	private String approvaltaskname;
	private String submitnodekey;
	private String submitnodekname;
	private String taskid;
	private String approvalusername;

	// Constructors

	/** default constructor */
	public ActExApprovalBO() {
	}

	/** minimal constructor */
	public ActExApprovalBO(String approvalid) {
		this.approvalid = approvalid;
	}

	/** full constructor */
	public ActExApprovalBO(String approvalid, String processid,
			String approvaldate, String approvalmessage, String approvaluserid,
			String approvalstate, String approvaltaskkey,
			String approvaltaskname, String submitnodekey,
			String submitnodekname,String taskid) {
		this.approvalid = approvalid;
		this.processid = processid;
		this.approvaldate = approvaldate;
		this.approvalmessage = approvalmessage;
		this.approvaluserid = approvaluserid;
		this.approvalstate = approvalstate;
		this.approvaltaskkey = approvaltaskkey;
		this.approvaltaskname = approvaltaskname;
		this.submitnodekey = submitnodekey;
		this.submitnodekname = submitnodekname;
		this.taskid=taskid;
	}

	// Property accessors

	public String getApprovalid() {
		return this.approvalid;
	}

	public void setApprovalid(String approvalid) {
		this.approvalid = approvalid;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getApprovaldate() {
		return this.approvaldate;
	}

	public void setApprovaldate(String approvaldate) {
		this.approvaldate = approvaldate;
	}

	public String getApprovalmessage() {
		return this.approvalmessage;
	}

	public void setApprovalmessage(String approvalmessage) {
		this.approvalmessage = approvalmessage;
	}

	

	public String getApprovaluserid() {
		return approvaluserid;
	}

	public void setApprovaluserid(String approvaluserid) {
		this.approvaluserid = approvaluserid;
	}

	public String getApprovalusername() {
		return approvalusername;
	}

	public void setApprovalusername(String approvalusername) {
		this.approvalusername = approvalusername;
	}

	public String getApprovalstate() {
		return this.approvalstate;
	}

	public void setApprovalstate(String approvalstate) {
		this.approvalstate = approvalstate;
	}

	public String getApprovaltaskkey() {
		return this.approvaltaskkey;
	}

	public void setApprovaltaskkey(String approvaltaskkey) {
		this.approvaltaskkey = approvaltaskkey;
	}

	public String getApprovaltaskname() {
		return this.approvaltaskname;
	}

	public void setApprovaltaskname(String approvaltaskname) {
		this.approvaltaskname = approvaltaskname;
	}

	public String getSubmitnodekey() {
		return this.submitnodekey;
	}

	public void setSubmitnodekey(String submitnodekey) {
		this.submitnodekey = submitnodekey;
	}

	public String getSubmitnodekname() {
		return this.submitnodekname;
	}

	public void setSubmitnodekname(String submitnodekname) {
		this.submitnodekname = submitnodekname;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

}