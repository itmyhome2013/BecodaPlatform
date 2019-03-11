package com.becoda.bkms.east.ssjc.pojo;
/**
 * 
 * <p>Description: 指标检测查询条件</p>
 * @author liu_hq
 * @date 2017-11-28
 *
 */
public class ZbjcQueryCondition {
	private String starttime; //开始时间
	private String endtime; //结束时间
	private String ynsbbh; //设备编号
	private String fid; //父id  机组id
	private String nyzl; //能源种类
	private String sbid; //设备id
	
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getYnsbbh() {
		return ynsbbh;
	}
	public void setYnsbbh(String ynsbbh) {
		this.ynsbbh = ynsbbh;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getNyzl() {
		return nyzl;
	}
	public void setNyzl(String nyzl) {
		this.nyzl = nyzl;
	}
	public String getSbid() {
		return sbid;
	}
	public void setSbid(String sbid) {
		this.sbid = sbid;
	}
}
