package com.becoda.bkms.pcs.pojo.bo;

/**
 * ActExGroupuser entity. @author MyEclipse Persistence Tools
 */

public class ActExGroupuserBO implements java.io.Serializable {

	// Fields

	private String groupuserid;
	private String groupid;
	private String userid;

	// Constructors

	/** default constructor */
	public ActExGroupuserBO() {
	}

	/** minimal constructor */
	public ActExGroupuserBO(String groupuserid, String groupid) {
		this.groupuserid = groupuserid;
		this.groupid = groupid;
	}

	/** full constructor */
	public ActExGroupuserBO(String groupuserid, String groupid, String userid) {
		this.groupuserid = groupuserid;
		this.groupid = groupid;
		this.userid = userid;
	}

	// Property accessors

	public String getGroupuserid() {
		return this.groupuserid;
	}

	public void setGroupuserid(String groupuserid) {
		this.groupuserid = groupuserid;
	}

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}