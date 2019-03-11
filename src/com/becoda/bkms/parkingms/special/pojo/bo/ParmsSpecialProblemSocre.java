package com.becoda.bkms.parkingms.special.pojo.bo;

import java.io.Serializable;

/**
 * 区交委检查问题分值记录表
 * 
 * @author lhq
 * 
 */
public class ParmsSpecialProblemSocre implements Serializable {
	private String problemsocid;
	private String ctcid;// 区交委id
	private String ctcname;// 区交委名称
	private String problemid;// 问题id
	private String problemname;// 问题名称
	private String socretime;// 日期
	private String socre;// 分值

	public String getProblemsocid() {
		return problemsocid;
	}

	public void setProblemsocid(String problemsocid) {
		this.problemsocid = problemsocid;
	}

	public String getCtcid() {
		return ctcid;
	}

	public void setCtcid(String ctcid) {
		this.ctcid = ctcid;
	}

	public String getCtcname() {
		return ctcname;
	}

	public void setCtcname(String ctcname) {
		this.ctcname = ctcname;
	}

	public String getProblemid() {
		return problemid;
	}

	public void setProblemid(String problemid) {
		this.problemid = problemid;
	}

	public String getProblemname() {
		return problemname;
	}

	public void setProblemname(String problemname) {
		this.problemname = problemname;
	}

	public String getSocretime() {
		return socretime;
	}

	public void setSocretime(String socretime) {
		this.socretime = socretime;
	}

	public String getSocre() {
		return socre;
	}

	public void setSocre(String socre) {
		this.socre = socre;
	}

}
