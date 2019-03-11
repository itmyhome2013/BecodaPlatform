package com.becoda.bkms.pcs.pojo.bo;

/**
 * ActExApprovaltest entity. @author MyEclipse Persistence Tools
 */

public class ActExApprovaltestBO implements java.io.Serializable {

	// Fields

	private String approvaltestid;
	private String processid;
	private String approvalmessage;

	// Constructors

	/** default constructor */
	public ActExApprovaltestBO() {
	}

	/** minimal constructor */
	public ActExApprovaltestBO(String approvaltestid) {
		this.approvaltestid = approvaltestid;
	}

	/** full constructor */
	public ActExApprovaltestBO(String approvaltestid, String processid,
			String approvalmessage) {
		this.approvaltestid = approvaltestid;
		this.processid = processid;
		this.approvalmessage = approvalmessage;
	}

	// Property accessors

	public String getApprovaltestid() {
		return this.approvaltestid;
	}

	public void setApprovaltestid(String approvaltestid) {
		this.approvaltestid = approvaltestid;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getApprovalmessage() {
		return this.approvalmessage;
	}

	public void setApprovalmessage(String approvalmessage) {
		this.approvalmessage = approvalmessage;
	}

}