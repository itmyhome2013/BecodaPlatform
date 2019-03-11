package com.becoda.bkms.east.ynsb.pojo;
/**
 * <p>Description: 用能设备能源计量器具BO </p>
 * @author liu_hq
 * @date 2017-9-27
 *
 */
public class YnsbBO {
	private String ynsb_id;// 主键
	private String ynsb_glbh;// 用能单位管理编号
	private String ynsb_nyzl;// 能源种类
	private String ynsb_fid;// 父id
	private String ynsb_zt; //状态 0：禁用  1：启用
	private String ynsb_jdrq;//校对日期
	private String ynsb_rate; //倍率
	private String ynsb_azdd; //安装使用地点
	public String getYnsb_id() {
		return ynsb_id;
	}
	public void setYnsb_id(String ynsb_id) {
		this.ynsb_id = ynsb_id;
	}
	public String getYnsb_glbh() {
		return ynsb_glbh;
	}
	public void setYnsb_glbh(String ynsb_glbh) {
		this.ynsb_glbh = ynsb_glbh;
	}
	public String getYnsb_nyzl() {
		return ynsb_nyzl;
	}
	public void setYnsb_nyzl(String ynsb_nyzl) {
		this.ynsb_nyzl = ynsb_nyzl;
	}
	public String getYnsb_fid() {
		return ynsb_fid;
	}
	public void setYnsb_fid(String ynsb_fid) {
		this.ynsb_fid = ynsb_fid;
	}
	public String getYnsb_zt() {
		return ynsb_zt;
	}
	public void setYnsb_zt(String ynsb_zt) {
		this.ynsb_zt = ynsb_zt;
	}
	public String getYnsb_jdrq() {
		return ynsb_jdrq;
	}
	public void setYnsb_jdrq(String ynsb_jdrq) {
		this.ynsb_jdrq = ynsb_jdrq;
	}
	public String getYnsb_rate() {
		return ynsb_rate;
	}
	public void setYnsb_rate(String ynsbRate) {
		ynsb_rate = ynsbRate;
	}
	public String getYnsb_azdd() {
		return ynsb_azdd;
	}
	public void setYnsb_azdd(String ynsb_azdd) {
		this.ynsb_azdd = ynsb_azdd;
	}
}
