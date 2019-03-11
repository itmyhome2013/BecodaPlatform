package com.becoda.bkms.east.gkdd.web;

import com.becoda.bkms.common.web.GenericAction;

/**
 * 
 * <p>
 * Description:大华视频监控
 * </p>
 * 
 * @author liu_hq
 * @date 2017-12-20
 * 
 */
public class DhVideoAction extends GenericAction{
	private String ip;
	private String username;
	private String pwd;
	private String portnum;
	
	//172.16.20.63(恒东锅炉房一)
	public String videoInit17216163(){
		ip = "60.194.185.19";
		username = "ems";
		pwd = "751751";
		portnum="37780";
		return "videoInit";
	}
	//172.16.20.90（恒东锅炉房二）
	public String videoInit17216190(){
		ip = "60.194.185.19";
		username = "ems";
		pwd = "751751";
		portnum="37790";
		return "videoInit";
	}
	
	//172.16.20.70（恒东变电站一）
	public String videoInit17216170(){
		ip = "60.194.185.19";
		username = "ems";
		pwd = "751751";
		portnum="37785";
		return "videoInit";
	}
	//172.16.20.71（恒东变电站二）
	public String videoInit17216171(){
		ip = "60.194.185.19";
		username = "ems";
		pwd = "751751";
		portnum="37786";
		return "videoInit";
	}
	
	//动南杭州大华151
	public String dlnc151(){
		ip = "111.198.58.89";
		username = "admin";
		pwd = "aa751751";
		portnum="8000";
		return "ncvideoInit";
	}
	//动南杭州大华152
	public String dlnc152(){
		ip = "111.198.58.89";
		username = "admin";
		pwd = "aa751751";
		portnum="8001";
		return "ncvideoInit";
	}
	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPortnum() {
		return portnum;
	}

	public void setPortnum(String portnum) {
		this.portnum = portnum;
	}
	
}
