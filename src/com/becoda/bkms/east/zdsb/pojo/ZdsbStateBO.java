package com.becoda.bkms.east.zdsb.pojo;
/**
 * <p>Description: 设备状态BO </p>
 * @author zhu_lw
 * @date 2018-01-22
 *
 */
public class ZdsbStateBO {
	private String zt_id;// 主键
	private String zdsb_id;// 设备id
	private String zdsb_jxrq;// 检修日期
	private String zdsb_jxry;// 检修人员
	private String zdsb_state; //设备状态
	private String zdsb_ssbm;// 所属部门
	private String zdsb_sbmc;// 设备名称
	private String sisvalue;// sis值
	private String zdsb_qnsc;// 全年时长
	private String zdsb_zsc; //总时长
	
	public String getZt_id() {
		return zt_id;
	}
	public void setZt_id(String zt_id) {
		this.zt_id = zt_id;
	}
	public String getZdsb_id() {
		return zdsb_id;
	}
	public void setZdsb_id(String zdsb_id) {
		this.zdsb_id = zdsb_id;
	}
	public String getZdsb_jxrq() {
		return zdsb_jxrq;
	}
	public void setZdsb_jxrq(String zdsb_jxrq) {
		this.zdsb_jxrq = zdsb_jxrq;
	}
	public String getZdsb_jxry() {
		return zdsb_jxry;
	}
	public void setZdsb_jxry(String zdsb_jxry) {
		this.zdsb_jxry = zdsb_jxry;
	}
	public String getZdsb_state() {
		return zdsb_state;
	}
	public void setZdsb_state(String zdsb_state) {
		this.zdsb_state = zdsb_state;
	}
	public String getZdsb_ssbm() {
		return zdsb_ssbm;
	}
	public void setZdsb_ssbm(String zdsb_ssbm) {
		this.zdsb_ssbm = zdsb_ssbm;
	}
	public String getZdsb_sbmc() {
		return zdsb_sbmc;
	}
	public void setZdsb_sbmc(String zdsb_sbmc) {
		this.zdsb_sbmc = zdsb_sbmc;
	}
	public String getSisvalue() {
		return sisvalue;
	}
	public void setSisvalue(String sisvalue) {
		this.sisvalue = sisvalue;
	}
	public String getZdsb_qnsc() {
		return zdsb_qnsc;
	}
	public void setZdsb_qnsc(String zdsb_qnsc) {
		this.zdsb_qnsc = zdsb_qnsc;
	}
	public String getZdsb_zsc() {
		return zdsb_zsc;
	}
	public void setZdsb_zsc(String zdsb_zsc) {
		this.zdsb_zsc = zdsb_zsc;
	}
	
}
