package com.becoda.bkms.tls.webmgr.pojo.vo;


public class QuestionnaireVO {
	private String sur_id;
	private String sur_name;//问卷名称
	private String sur_start_date;
	private String sur_end_date;
	private String sur_type;//问卷类别 单选：1，多选：2
	private String sur_select_num;//选项个数
	private String sur_canselect_num;//限选个数
	private String sur_view_other;//显示其他项 代码： 是否
	private String sur_jointype;//参与调查人员 所有人员：1，指定人员：2
	private String creator;
	private String create_orgid;
	private String create_date;
	private String sur_stats;//状态 未发布：0，已发布：1，取消发布：2，调查完毕：3
	
	
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_orgid() {
		return create_orgid;
	}
	public void setCreate_orgid(String create_orgid) {
		this.create_orgid = create_orgid;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getSur_jointype() {
		return sur_jointype;
	}
	public void setSur_jointype(String sub_jointype) {
		this.sur_jointype = sub_jointype;
	}
	public String getSur_canselect_num() {
		return sur_canselect_num;
	}
	public void setSur_canselect_num(String sur_canselect_num) {
		this.sur_canselect_num = sur_canselect_num;
	}
	public String getSur_end_date() {
		return sur_end_date;
	}
	public void setSur_end_date(String sur_end_date) {
		this.sur_end_date = sur_end_date;
	}
	public String getSur_id() {
		return sur_id;
	}
	public void setSur_id(String sur_id) {
		this.sur_id = sur_id;
	}
	public String getSur_name() {
		return sur_name;
	}
	public void setSur_name(String sur_name) {
		this.sur_name = sur_name;
	}
	public String getSur_select_num() {
		return sur_select_num;
	}
	public void setSur_select_num(String sur_select_num) {
		this.sur_select_num = sur_select_num;
	}
	public String getSur_start_date() {
		return sur_start_date;
	}
	public void setSur_start_date(String sur_start_date) {
		this.sur_start_date = sur_start_date;
	}
	public String getSur_stats() {
		return sur_stats;
	}
	public void setSur_stats(String sur_stats) {
		this.sur_stats = sur_stats;
	}
	public String getSur_type() {
		return sur_type;
	}
	public void setSur_type(String sur_type) {
		this.sur_type = sur_type;
	}
	public String getSur_view_other() {
		return sur_view_other;
	}
	public void setSur_view_other(String sur_view_other) {
		this.sur_view_other = sur_view_other;
	}
	


}

