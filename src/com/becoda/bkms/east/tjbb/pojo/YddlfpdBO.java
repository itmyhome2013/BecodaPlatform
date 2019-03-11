package com.becoda.bkms.east.tjbb.pojo;
/**
 * 
 * <p>Description: 月度动力分配单</p>
 * @author zhu_lw
 * @date 2018-03-09
 *
 */
public class YddlfpdBO {
	private String id; //主键
	private String rq; //日期
	private String ynsb_glbh; //计量编号
	private String last1value; //表底值
	private String last2value; //本月用量
	private String ynsb_nyzl; //能源种类
	private String ljz; //累积值
	private String shl; //损耗量
	private String byjsl; //本月结算量
	private String sjjsl; //实际结算量
	private String jfzt; //缴费状态
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public String getYnsb_glbh() {
		return ynsb_glbh;
	}
	public void setYnsb_glbh(String ynsb_glbh) {
		this.ynsb_glbh = ynsb_glbh;
	}
	public String getLast1value() {
		return last1value;
	}
	public void setLast1value(String last1value) {
		this.last1value = last1value;
	}
	public String getLast2value() {
		return last2value;
	}
	public void setLast2value(String last2value) {
		this.last2value = last2value;
	}
	public String getYnsb_nyzl() {
		return ynsb_nyzl;
	}
	public void setYnsb_nyzl(String ynsb_nyzl) {
		this.ynsb_nyzl = ynsb_nyzl;
	}
	public String getLjz() {
		return ljz;
	}
	public void setLjz(String ljz) {
		this.ljz = ljz;
	}
	public String getShl() {
		return shl;
	}
	public void setShl(String shl) {
		this.shl = shl;
	}
	public String getByjsl() {
		return byjsl;
	}
	public void setByjsl(String byjsl) {
		this.byjsl = byjsl;
	}
	public String getSjjsl() {
		return sjjsl;
	}
	public void setSjjsl(String sjjsl) {
		this.sjjsl = sjjsl;
	}
	public String getJfzt() {
		return jfzt;
	}
	public void setJfzt(String jfzt) {
		this.jfzt = jfzt;
	}
	
	
}
