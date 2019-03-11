package com.becoda.bkms.east.yjbj.pojo;
/**
 * <p>Description: 能效检测预警报警设备 </p>
 * @author liu_hq
 * @date 2018-4-4
 *
 */
public class NxjcYjbjSbBO {

	private String nxjcsb_id; //主键
	private String nxjcsb_lx; //类型 1：汽耗比 2：用水指标 3：厂综合用电率 4：单位面积耗热量 5：单位面积耗电量 6：单位面积耗水量
	private String nxjcsb_mc; //名称
	private String nxjcsb_yjmax; //预警最大值
	private String nxjcsb_yjmin; //预警最小值
	
	public String getNxjcsb_id() {
		return nxjcsb_id;
	}
	public void setNxjcsb_id(String nxjcsb_id) {
		this.nxjcsb_id = nxjcsb_id;
	}
	public String getNxjcsb_lx() {
		return nxjcsb_lx;
	}
	public void setNxjcsb_lx(String nxjcsb_lx) {
		this.nxjcsb_lx = nxjcsb_lx;
	}
	public String getNxjcsb_mc() {
		return nxjcsb_mc;
	}
	public void setNxjcsb_mc(String nxjcsb_mc) {
		this.nxjcsb_mc = nxjcsb_mc;
	}
	public String getNxjcsb_yjmax() {
		return nxjcsb_yjmax;
	}
	public void setNxjcsb_yjmax(String nxjcsb_yjmax) {
		this.nxjcsb_yjmax = nxjcsb_yjmax;
	}
	public String getNxjcsb_yjmin() {
		return nxjcsb_yjmin;
	}
	public void setNxjcsb_yjmin(String nxjcsb_yjmin) {
		this.nxjcsb_yjmin = nxjcsb_yjmin;
	}
	
}
