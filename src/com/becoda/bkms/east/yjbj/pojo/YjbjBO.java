package com.becoda.bkms.east.yjbj.pojo;
/**
 * <p>Description: 预警报警BO </p>
 * @author zhu_lw
 * @date 2017-11-24
 *
 */
public class YjbjBO {
	private String yjbj_id;// 主键
	private String yjbj_sbid;// 关联设备id
	private String yjbj_sis_bs;// 关联sis标识
	private String yjbj_min;// 最小值
	private String yjbj_max;// 最大值
	public String getYjbj_id() {
		return yjbj_id;
	}
	public void setYjbj_id(String yjbjId) {
		yjbj_id = yjbjId;
	}
	public String getYjbj_sbid() {
		return yjbj_sbid;
	}
	public void setYjbj_sbid(String yjbjSbid) {
		yjbj_sbid = yjbjSbid;
	}
	public String getYjbj_sis_bs() {
		return yjbj_sis_bs;
	}
	public void setYjbj_sis_bs(String yjbjSisBs) {
		yjbj_sis_bs = yjbjSisBs;
	}
	public String getYjbj_min() {
		return yjbj_min;
	}
	public void setYjbj_min(String yjbjMin) {
		yjbj_min = yjbjMin;
	}
	public String getYjbj_max() {
		return yjbj_max;
	}
	public void setYjbj_max(String yjbjMax) {
		yjbj_max = yjbjMax;
	}
	
	

}
