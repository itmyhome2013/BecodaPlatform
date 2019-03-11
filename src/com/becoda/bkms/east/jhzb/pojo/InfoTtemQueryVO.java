package com.becoda.bkms.east.jhzb.pojo;

public class InfoTtemQueryVO {
     private String setId;//表名
     private String reportDate;//日期
     private String desc;// 计划类别（1:年度/2:月度）
     private String search_project;//项目
     private String search_unit;//单位
     private String[] projectArray;
	public String getSetId(){
		return setId;
	}
	public void setSetId(String setId) {
		this.setId = setId;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSearch_project() {
		return search_project;
	}
	public void setSearch_project(String searchProject) {
		search_project = searchProject;
	}
	public String getSearch_unit() {
		return search_unit;
	}
	public void setSearch_unit(String searchUnit) {
		search_unit = searchUnit;
	}
	public String[] getProjectArray() {
		return projectArray;
	}
	public void setProjectArray(String[] projectArray) {
		this.projectArray = projectArray;
	}
     
}
