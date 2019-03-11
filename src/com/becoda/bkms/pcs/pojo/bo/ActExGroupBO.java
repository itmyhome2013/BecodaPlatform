package com.becoda.bkms.pcs.pojo.bo;

/**
 * ActExGroup entity. @author MyEclipse Persistence Tools
 */

public class ActExGroupBO implements java.io.Serializable {

	// Fields

	private String groupid;
	private String groupname;
	private String createuserid;
	private String createdate;
	private String describe;

	// Constructors

	/** default constructor */
	public ActExGroupBO() {
	}

	/** minimal constructor */
	public ActExGroupBO(String groupid, String groupname) {
		this.groupid = groupid;
		this.groupname = groupname;
	}

	/** full constructor */
	public ActExGroupBO(String groupid, String groupname, String createuserid,
			String createdate, String describe) {
		this.groupid = groupid;
		this.groupname = groupname;
		this.createuserid = createuserid;
		this.createdate = createdate;
		this.describe = describe;
	}

	// Property accessors

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
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

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}