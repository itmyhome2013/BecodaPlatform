package com.becoda.bkms.east.zdsb.pojo;
/**
 * <p>Description: 重点设备设备时长BO </p>
 * @author zhu_lw
 * @date 2018-01-24
 *
 */
public class ZdsbSbscBO {
	private String id;
	private String zdsb_id;// 设备id
	private String zdsb_qnsc;//全年时长
	private String zdsb_zsc;//总时长
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZdsb_id() {
		return zdsb_id;
	}
	public void setZdsb_id(String zdsb_id) {
		this.zdsb_id = zdsb_id;
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
