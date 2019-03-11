package com.becoda.bkms.east.jhzb.pojo;
/**
 * <p>Description: 计划指标完成状态BO </p>
 * @author zhu_lw
 * @date 2017-11-9
 *
 */
public class JhzbIndexStateBO {
	private String jhzb_id;// 主键
	private String jhzb_index_date;// 指标日期
	private String jhzb_sheet_name;// 表名
	private String jhzb_index_state;// 指标完成状态（1:已完成;2:未完成）
	private String jhzb_index_id;//关联指标id
	public String getJhzb_id() {
		return jhzb_id;
	}
	public void setJhzb_id(String jhzbId) {
		jhzb_id = jhzbId;
	}
	public String getJhzb_index_date() {
		return jhzb_index_date;
	}
	public void setJhzb_index_date(String jhzbIndexDate) {
		jhzb_index_date = jhzbIndexDate;
	}
	public String getJhzb_sheet_name() {
		return jhzb_sheet_name;
	}
	public void setJhzb_sheet_name(String jhzbSheetName) {
		jhzb_sheet_name = jhzbSheetName;
	}
	public String getJhzb_index_state() {
		return jhzb_index_state;
	}
	public void setJhzb_index_state(String jhzbIndexState) {
		jhzb_index_state = jhzbIndexState;
	}
	public String getJhzb_index_id() {
		return jhzb_index_id;
	}
	public void setJhzb_index_id(String jhzbIndexId) {
		jhzb_index_id = jhzbIndexId;
	}
	
	
}
