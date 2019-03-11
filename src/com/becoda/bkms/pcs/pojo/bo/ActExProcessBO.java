package com.becoda.bkms.pcs.pojo.bo;

/**
 * ActExProcess entity. @author MyEclipse Persistence Tools
 */

public class ActExProcessBO implements java.io.Serializable {

	// Fields

	private String processid;
	private String processname;
	private String createusername;
	private String createuserid;
	private String createdate;
	private String isdeleted;
	private String procdefkey;
	private String procdefname;
	private String comments;
	private String urgency;

	// Constructors

	/** default constructor */
	public ActExProcessBO() {
	}

	/** minimal constructor */
	public ActExProcessBO(String processid, String processname,
			String procdefkey, String procdefname, String urgency) {
		this.processid = processid;
		this.processname = processname;
		this.procdefkey = procdefkey;
		this.procdefname = procdefname;
		this.urgency = urgency;
	}

	/** full constructor */
	public ActExProcessBO(String processid, String processname,
			String createusername, String createuserid, String createdate,
			String isdeleted, String procdefkey, String procdefname,
			String comments, String urgency) {
		this.processid = processid;
		this.processname = processname;
		this.createusername = createusername;
		this.createuserid = createuserid;
		this.createdate = createdate;
		this.isdeleted = isdeleted;
		this.procdefkey = procdefkey;
		this.procdefname = procdefname;
		this.comments = comments;
		this.urgency = urgency;
	}

	// Property accessors

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProcessname() {
		return this.processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getCreateusername() {
		return this.createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	public String getCreateuserid() {
		return this.createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getProcdefkey() {
		return this.procdefkey;
	}

	public void setProcdefkey(String procdefkey) {
		this.procdefkey = procdefkey;
	}

	public String getProcdefname() {
		return this.procdefname;
	}

	public void setProcdefname(String procdefname) {
		this.procdefname = procdefname;
	}

	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUrgency() {
		return this.urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

}