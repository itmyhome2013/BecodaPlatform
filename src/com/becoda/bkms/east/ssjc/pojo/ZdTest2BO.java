package com.becoda.bkms.east.ssjc.pojo;
/**
 * 
 * <p>Description:实时数据表zd_test2 </p>
 * @author zhu_lw
 * @date 2018-05-07
 *
 */
public class ZdTest2BO  {
	private String id; //主键
	private String sisid;//sis标识
	private String sisvalue; //累积值
	private String sisvalue_new; //累积新值，用于数据更正
	private String sistime; //时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSisid() {
		return sisid;
	}
	public void setSisid(String sisid) {
		this.sisid = sisid;
	}
	public String getSisvalue() {
		return sisvalue;
	}
	public void setSisvalue(String sisvalue) {
		this.sisvalue = sisvalue;
	}
	public String getSistime() {
		return sistime;
	}
	public void setSistime(String sistime) {
		this.sistime = sistime;
	}
	public String getSisvalue_new() {
		return sisvalue_new;
	}
	public void setSisvalue_new(String sisvalueNew) {
		sisvalue_new = sisvalueNew;
	}
	
	
}
