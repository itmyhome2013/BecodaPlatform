package com.becoda.bkms.east.zdsb.pojo;
/**
 * <p>Description: 备件清单BO </p>
 * @author zhu_lw
 * @date 2018-02-05
 *
 */
public class BjqdBO {
	private String id;// 主键
	private String bjqd_jgmc;// 机构名称
	private String bjqd_dyjz;// 单元机组
	private String bjqd_bjmc;// 备件名称
	private String bjqd_ggth; //规格图号
	private String bjqd_bjzl; //备件种类（机、电、仪）
	private String bjqd_ycl; //应储量（套/件）
	private String bjqd_scl; //实储量（套/件）
	private String remark; //备注
	private String bjqd_cjsj; //创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBjqd_jgmc() {
		return bjqd_jgmc;
	}
	public void setBjqd_jgmc(String bjqd_jgmc) {
		this.bjqd_jgmc = bjqd_jgmc;
	}
	public String getBjqd_dyjz() {
		return bjqd_dyjz;
	}
	public void setBjqd_dyjz(String bjqd_dyjz) {
		this.bjqd_dyjz = bjqd_dyjz;
	}
	public String getBjqd_bjmc() {
		return bjqd_bjmc;
	}
	public void setBjqd_bjmc(String bjqd_bjmc) {
		this.bjqd_bjmc = bjqd_bjmc;
	}
	public String getBjqd_ggth() {
		return bjqd_ggth;
	}
	public void setBjqd_ggth(String bjqd_ggth) {
		this.bjqd_ggth = bjqd_ggth;
	}
	public String getBjqd_bjzl() {
		return bjqd_bjzl;
	}
	public void setBjqd_bjzl(String bjqd_bjzl) {
		this.bjqd_bjzl = bjqd_bjzl;
	}
	public String getBjqd_ycl() {
		return bjqd_ycl;
	}
	public void setBjqd_ycl(String bjqd_ycl) {
		this.bjqd_ycl = bjqd_ycl;
	}
	public String getBjqd_scl() {
		return bjqd_scl;
	}
	public void setBjqd_scl(String bjqd_scl) {
		this.bjqd_scl = bjqd_scl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBjqd_cjsj() {
		return bjqd_cjsj;
	}
	public void setBjqd_cjsj(String bjqd_cjsj) {
		this.bjqd_cjsj = bjqd_cjsj;
	}
	
}
