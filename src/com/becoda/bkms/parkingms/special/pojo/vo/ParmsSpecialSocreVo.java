package com.becoda.bkms.parkingms.special.pojo.vo;

/**
 *停车单位分值表
 * 
 * @author lhq
 * 
 */
public class ParmsSpecialSocreVo {
	private String socreId;
	private String recId;// 单位id
	private String recName;// 单位名称
	private String socreTime;// 日期
	private String socre;// 分值
	private String ctcid;// 区交委id
	private String yearsoc;//年扣分（另加属性）
	private String monthsoc;//月扣分
	private String weksoc;//周扣分

	public String getSocreId() {
		return socreId;
	}

	public void setSocreId(String socreId) {
		this.socreId = socreId;
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

	public String getSocreTime() {
		return socreTime;
	}

	public void setSocreTime(String socreTime) {
		this.socreTime = socreTime;
	}

	public String getSocre() {
		return socre;
	}

	public void setSocre(String socre) {
		this.socre = socre;
	}

	public String getYearsoc() {
		return yearsoc;
	}

	public void setYearsoc(String yearsoc) {
		this.yearsoc = yearsoc;
	}

	public String getMonthsoc() {
		return monthsoc;
	}

	public void setMonthsoc(String monthsoc) {
		this.monthsoc = monthsoc;
	}

	public String getWeksoc() {
		return weksoc;
	}

	public void setWeksoc(String weksoc) {
		this.weksoc = weksoc;
	}

	public String getCtcid() {
		return ctcid;
	}

	public void setCtcid(String ctcid) {
		this.ctcid = ctcid;
	}

}
