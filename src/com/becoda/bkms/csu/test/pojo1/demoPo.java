package com.becoda.bkms.csu.test.pojo1;

import java.util.Date;


public class demoPo implements java.io.Serializable {

	private String id;
	private Date name;
	private String code;
	private String par1;
	private String par2;
	private String par3;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getName() {
		return name;
	}
	public void setName(Date name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPar1() {
		return par1;
	}
	public void setPar1(String par1) {
		this.par1 = par1;
	}
	public String getPar2() {
		return par2;
	}
	public void setPar2(String par2) {
		this.par2 = par2;
	}
	public String getPar3() {
		return par3;
	}
	public void setPar3(String par3) {
		this.par3 = par3;
	}

}