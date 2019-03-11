package com.becoda.bkms.pcs.pojo.bo;

/**
 * ActExCompletedform entity. @author MyEclipse Persistence Tools
 */

public class ActExCompletedformBO implements java.io.Serializable {

	// Fields

	private String completedformid;
	private String processid;
	private String pcsfromcfgid;
	private String informant;
	private String infordate;
	private String dataid;
	private String fromtablename;
    private String lastupdatedate;
	// Constructors

	/** default constructor */
	public ActExCompletedformBO() {
	}

	/** minimal constructor */
	public ActExCompletedformBO(String completedformid) {
		this.completedformid = completedformid;
	}

	/** full constructor */
	public ActExCompletedformBO(String completedformid, String processid,
			String pcsfromcfgid,String informant, String infordate, String dataid,String fromtablename) {
		this.completedformid = completedformid;
		this.processid = processid;
		this.pcsfromcfgid = pcsfromcfgid;
		this.informant = informant;
		this.infordate = infordate;
		this.dataid = dataid;
		this.fromtablename=fromtablename;
	}

	// Property accessors

	public String getCompletedformid() {
		return this.completedformid;
	}

	public void setCompletedformid(String completedformid) {
		this.completedformid = completedformid;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getPcsfromcfgid() {
		return this.pcsfromcfgid;
	}

	public void setPcsfromcfgid(String pcsfromcfgid) {
		this.pcsfromcfgid = pcsfromcfgid;
	}

	public String getInformant() {
		return this.informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getInfordate() {
		return this.infordate;
	}

	public void setInfordate(String infordate) {
		this.infordate = infordate;
	}

	public String getDataid() {
		return this.dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getFromtablename() {
		return fromtablename;
	}

	public void setFromtablename(String fromtablename) {
		this.fromtablename = fromtablename;
	}

	public String getLastupdatedate() {
		return lastupdatedate;
	}

	public void setLastupdatedate(String lastupdatedate) {
		this.lastupdatedate = lastupdatedate;
	}

}