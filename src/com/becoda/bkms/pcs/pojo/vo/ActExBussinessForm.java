package com.becoda.bkms.pcs.pojo.vo;

import java.util.Date;



/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-4-27
 * Time: 10:41:49
 * To change this template use File | Settings | File Templates.
 */
public class ActExBussinessForm  {

	private String bussiness_id;
	private String bussiness_name;
	private String bussiness_reamrk;
	private Date bussiness_date = new Date();
	private Integer state = 0;// 请假单状态 0初始录入,1.社区审批,2街道审批，3区审批
	public String getBussiness_id() {
		return bussiness_id;
	}
	public void setBussiness_id(String bussinessId) {
		bussiness_id = bussinessId;
	}
	public String getBussiness_name() {
		return bussiness_name;
	}
	public void setBussiness_name(String bussinessName) {
		bussiness_name = bussinessName;
	}
	public String getBussiness_reamrk() {
		return bussiness_reamrk;
	}
	public void setBussiness_reamrk(String bussinessReamrk) {
		bussiness_reamrk = bussinessReamrk;
	}
	public Date getBussiness_date() {
		return bussiness_date;
	}
	public void setBussiness_date(Date bussinessDate) {
		bussiness_date = bussinessDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
