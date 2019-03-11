package com.becoda.bkms.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
	
public class LocalUtils {
	
	/**
	 * 如果存在代理的时候也可以获取客户端的真实ip地址
	 * @return
	 */
	public static String getLocalIP() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
//	public static void main(String[] args) {
//		System.out.println("------ip--------"+getLocalIP());
//	}
}