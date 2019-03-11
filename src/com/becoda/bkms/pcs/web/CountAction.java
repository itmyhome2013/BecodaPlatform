package com.becoda.bkms.pcs.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
 
import com.becoda.bkms.pcs.ucc.IActCountUCC;
 
/**
 
 * @描述：TODO(主要完成统计功能、统计查询、我的案件、经办案件、已归档案件)
 * @author kris
 * @date 2016-1-22
 
 */
public class CountAction extends GenericPageAction{
	
	
	
    /**
     * 我的任务列表
     * @return
     */
	public String findMyPcsList(){
	   try {
			IActCountUCC actCountUCC = (IActCountUCC) getBean("pcs_actCountUCC");
			List<Map<String, String>> countMyPageList = actCountUCC.queryMyPcsPageList(vo, user);
			request.setAttribute("countMyPageList", countMyPageList);
		} catch (Exception e) {
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	        this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return SUCCESS;
	}
	 
	 /**
     * 查询已归档的流程列表
     * @return
     */
	public String findEndPcsList(){
	   try {
			IActCountUCC actCountUCC = (IActCountUCC) getBean("pcs_actCountUCC");
			List<Map<String, String>> countMyPageList = actCountUCC.queryEndPcsPageList(vo, user);
			request.setAttribute("countEndPageList", countMyPageList);
		} catch (Exception e) {
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	        this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return "list";// SUCCESS;
	}
	
	 /**
     * 查询全部的流程列表
     * @return
     */
	public String findAllPcsList(){
	   try {
			IActCountUCC actCountUCC = (IActCountUCC) getBean("pcs_actCountUCC");
			List<Map<String, String>> countMyPageList = actCountUCC.queryAllPcsPageList(vo, user);
			request.setAttribute("countAllPageList", countMyPageList);
		} catch (Exception e) {
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	        this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return SUCCESS;
	}
	
	
}
