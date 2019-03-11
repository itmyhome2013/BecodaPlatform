package com.becoda.bkms.east.ynsb.pojo;
/**
 * 
 * <p>Description:用能设备sis标示表 </p>
 * @author liu_hq
 * @date 2017-11-22
 *
 */
public class YnsbSisBO {

	private String ynsbsis_id; //主键
	private String ynsbsis_mc; //sis名称
	private String ynsbsis_bs; //sis标示
	private String ynsb_id; //用能设备id
	private Integer ynsbsis_order; //排序字段
	private String ynsbsis_sj; //创建时间
	private String ynsbsis_nyzl; //能源种类
	private String ynsbsis_islj; //是否累计值
	
	public String getYnsbsis_id() {
		return ynsbsis_id;
	}
	public void setYnsbsis_id(String ynsbsis_id) {
		this.ynsbsis_id = ynsbsis_id;
	}
	public String getYnsbsis_mc() {
		return ynsbsis_mc;
	}
	public void setYnsbsis_mc(String ynsbsis_mc) {
		this.ynsbsis_mc = ynsbsis_mc;
	}
	public String getYnsbsis_bs() {
		return ynsbsis_bs;
	}
	public void setYnsbsis_bs(String ynsbsis_bs) {
		this.ynsbsis_bs = ynsbsis_bs;
	}
	public String getYnsb_id() {
		return ynsb_id;
	}
	public void setYnsb_id(String ynsb_id) {
		this.ynsb_id = ynsb_id;
	}
	public Integer getYnsbsis_order() {
		return ynsbsis_order;
	}
	public void setYnsbsis_order(Integer ynsbsis_order) {
		this.ynsbsis_order = ynsbsis_order;
	}
	public String getYnsbsis_sj() {
		return ynsbsis_sj;
	}
	public void setYnsbsis_sj(String ynsbsis_sj) {
		this.ynsbsis_sj = ynsbsis_sj;
	}
	public String getYnsbsis_nyzl() {
		return ynsbsis_nyzl;
	}
	public void setYnsbsis_nyzl(String ynsbsis_nyzl) {
		this.ynsbsis_nyzl = ynsbsis_nyzl;
	}
	public String getYnsbsis_islj() {
		return ynsbsis_islj;
	}
	public void setYnsbsis_islj(String ynsbsis_islj) {
		this.ynsbsis_islj = ynsbsis_islj;
	}
}
