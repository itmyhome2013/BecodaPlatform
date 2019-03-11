package com.becoda.bkms.pcs.pojo.bo;

/**
 * ActExLeavetest entity. @author MyEclipse Persistence Tools
 */

public class ActExLeavetestBO implements java.io.Serializable {

	// Fields

	private String leaveid;
	private String processid;
	private String leavename;
	private String starttime;
	private String endtime;

	// Constructors

	/** default constructor */
	public ActExLeavetestBO() {
	}

	/** minimal constructor */
	public ActExLeavetestBO(String leaveid) {
		this.leaveid = leaveid;
	}

	/** full constructor */
	public ActExLeavetestBO(String leaveid, String processid, String leavename,
			String starttime, String endtime) {
		this.leaveid = leaveid;
		this.processid = processid;
		this.leavename = leavename;
		this.starttime = starttime;
		this.endtime = endtime;
	}

	// Property accessors

	public String getLeaveid() {
		return this.leaveid;
	}

	public void setLeaveid(String leaveid) {
		this.leaveid = leaveid;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getLeavename() {
		return this.leavename;
	}

	public void setLeavename(String leavename) {
		this.leavename = leavename;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}