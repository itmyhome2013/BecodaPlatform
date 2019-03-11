package com.becoda.bkms.east.sjbl.pojo;
/**
 * <p>Description: 手工补录数据BO </p>
 * @author zhu_lw
 * @date 2017-11-27
 *
 */
public class SjblBO {
	private String east_sjbl_id;// 主键
	private String east_sjbl_sbid;// 关联设备id
	private String east_sjbl_ljz;// 水（累计值）
	private String east_sjbl_feng;// 峰（电累计值）
	private String east_sjbl_gu;// 谷（电累计值）
	private String east_sjbl_ping;// 平（电累计值）
	private String east_sjbl_jian;// 尖（电累计值）
	private String east_sjbl_total;//总值（电）
	private String east_sjbl_date;// 录入日期
	private String east_sjbl_sbbh;// 关联设备编号
	private String east_sjbl_zdjz;//重点机组标识
	private String east_sjbl_nyzl;//燃料种类
	private String east_sjbl_lookdate;  //查表时间
	public String getEast_sjbl_id() {
		return east_sjbl_id;
	}
	public void setEast_sjbl_id(String eastSjblId) {
		east_sjbl_id = eastSjblId;
	}
	public String getEast_sjbl_sbid() {
		return east_sjbl_sbid;
	}
	public void setEast_sjbl_sbid(String eastSjblSbid) {
		east_sjbl_sbid = eastSjblSbid;
	}
	public String getEast_sjbl_ljz() {
		return east_sjbl_ljz;
	}
	public void setEast_sjbl_ljz(String eastSjblLjz) {
		east_sjbl_ljz = eastSjblLjz;
	}
	public String getEast_sjbl_feng() {
		return east_sjbl_feng;
	}
	public void setEast_sjbl_feng(String eastSjblFeng) {
		east_sjbl_feng = eastSjblFeng;
	}
	public String getEast_sjbl_gu() {
		return east_sjbl_gu;
	}
	public void setEast_sjbl_gu(String eastSjblGu) {
		east_sjbl_gu = eastSjblGu;
	}
	public String getEast_sjbl_ping() {
		return east_sjbl_ping;
	}
	public void setEast_sjbl_ping(String eastSjblPing) {
		east_sjbl_ping = eastSjblPing;
	}
	public String getEast_sjbl_jian() {
		return east_sjbl_jian;
	}
	public void setEast_sjbl_jian(String eastSjblJian) {
		east_sjbl_jian = eastSjblJian;
	}
	public String getEast_sjbl_date() {
		return east_sjbl_date;
	}
	public void setEast_sjbl_date(String eastSjblDate) {
		east_sjbl_date = eastSjblDate;
	}
	public String getEast_sjbl_sbbh() {
		return east_sjbl_sbbh;
	}
	public void setEast_sjbl_sbbh(String eastSjblSbbh) {
		east_sjbl_sbbh = eastSjblSbbh;
	}
	public String getEast_sjbl_zdjz() {
		return east_sjbl_zdjz;
	}
	public void setEast_sjbl_zdjz(String eastSjblZdjz) {
		east_sjbl_zdjz = eastSjblZdjz;
	}
	public String getEast_sjbl_nyzl() {
		return east_sjbl_nyzl;
	}
	public void setEast_sjbl_nyzl(String eastSjblNyzl) {
		east_sjbl_nyzl = eastSjblNyzl;
	}
	public String getEast_sjbl_total() {
		return east_sjbl_total;
	}
	public void setEast_sjbl_total(String east_sjbl_total) {
		this.east_sjbl_total = east_sjbl_total;
	}
	public String getEast_sjbl_lookdate() {
		return east_sjbl_lookdate;
	}
	public void setEast_sjbl_lookdate(String eastSjblLookdate) {
		east_sjbl_lookdate = eastSjblLookdate;
	}
	
}
