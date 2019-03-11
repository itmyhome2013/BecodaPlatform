package com.becoda.bkms.east.zdsb.pojo;
/**
 * 
 * <p>Description:重点设备sis标示表 </p>
 * @author zhu_lw
 * @date 2017-12-22
 *
 */
public class ZdsbSisBO {
	private String zdsbsis_id; //主键
	private String zdsbsis_mc; //sis名称
	private String zdsbsis_bs; //sis标示
	private Integer zdsbsis_order; //排序字段
	private String zdsb_id; //用能设备id
	private String zdsbsis_date; //创建时间
	public String getZdsbsis_id() {
		return zdsbsis_id;
	}
	public void setZdsbsis_id(String zdsbsis_id) {
		this.zdsbsis_id = zdsbsis_id;
	}
	public String getZdsbsis_mc() {
		return zdsbsis_mc;
	}
	public void setZdsbsis_mc(String zdsbsis_mc) {
		this.zdsbsis_mc = zdsbsis_mc;
	}
	public String getZdsbsis_bs() {
		return zdsbsis_bs;
	}
	public void setZdsbsis_bs(String zdsbsis_bs) {
		this.zdsbsis_bs = zdsbsis_bs;
	}
	
	public Integer getZdsbsis_order() {
		return zdsbsis_order;
	}
	public void setZdsbsis_order(Integer zdsbsis_order) {
		this.zdsbsis_order = zdsbsis_order;
	}
	public String getZdsb_id() {
		return zdsb_id;
	}
	public void setZdsb_id(String zdsb_id) {
		this.zdsb_id = zdsb_id;
	}
	public String getZdsbsis_date() {
		return zdsbsis_date;
	}
	public void setZdsbsis_date(String zdsbsis_date) {
		this.zdsbsis_date = zdsbsis_date;
	}
	
}
