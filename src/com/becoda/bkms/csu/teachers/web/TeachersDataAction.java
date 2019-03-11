package com.becoda.bkms.csu.teachers.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.csu.teachers.pojo.KpPersonLibrary;
import com.becoda.bkms.csu.teachers.ucc.ITeachersUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;


public class TeachersDataAction extends GenericAction{
	private KpPersonLibrary kpPersonLibrary;
	
	public KpPersonLibrary getKpPersonLibrary() {
		return kpPersonLibrary;
	}
	public void setKpPersonLibrary(KpPersonLibrary kpPersonLibrary) {
		this.kpPersonLibrary = kpPersonLibrary;
	}
	private  List sexList;
	
    public List getSexList() {
		return sexList;
	}
	public void setSexList(List sexList) {
		this.sexList = sexList;
	}
	public String queryDate(){
    	return "teachers";
    }
	/**
	 * 查询方法
	 * @return
	 * @throws BkmsException
	 */
	public String queryList() throws BkmsException {  
    	ITeachersUCC  teachersUCC = (ITeachersUCC) BkmsContext.getBean("teachersUCC");
    	Map parameter=new HashMap();
    	String name = request.getParameter("name");
    	String idcard = request.getParameter("idcard");
    	String phone =request.getParameter("phone");
    	parameter.put("name", name);
    	parameter.put("idcard", idcard);
    	parameter.put("phone", phone);
    	Map jsonMap = teachersUCC.queryList(page,rows,parameter);
        JSONObject jo = JSONObject.fromObject(jsonMap);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(jo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }  
	/**
	 * 根据id查询单条记录
	 * @return
	 * @throws BkmsException
	 */
    public String getTeachersId()throws BkmsException{
    	ITeachersUCC  teachersUCC = (ITeachersUCC) BkmsContext.getBean("teachersUCC");
    	String id =request.getParameter("id");
    	String type =request.getParameter("type");
    	if(type.equals("view") || type.equals("edit")){
    		kpPersonLibrary= teachersUCC.getTeachersId(id);
    	}
    	sexList=sexData();
    	request.setAttribute("type", type);
    	request.setAttribute("sexList", sexList);
		return  "showTeachers";
    }
    /**
     * 保存记录
     * @return
     * @throws BkmsException
     */
    public String saveTeachers()throws BkmsException{
    	Calendar calendar  =   new  GregorianCalendar();
		calendar.set( Calendar.DATE,1);
    	ITeachersUCC  teachersUCC = (ITeachersUCC) BkmsContext.getBean("teachersUCC");
    	String type =request.getParameter("type");
    	session = request.getSession();
        user = (User) session.getAttribute(Constants.USER_INFO);
    	if(type.equals("add")){
    	 	kpPersonLibrary.setMemId(SequenceGenerator.getUUID());
    	 	kpPersonLibrary.setCreateTime(calendar.getTime());
        	kpPersonLibrary.setCreateUserId(user.getUserId());
        	kpPersonLibrary.setCreateUserName(user.getName());
        	teachersUCC.saveTeachers(kpPersonLibrary);	
    	}else if(type.equals("edit")){
    		kpPersonLibrary.setUpdateTime(calendar.getTime());
    		kpPersonLibrary.setUpdateUserId(user.getUserId());
    		kpPersonLibrary.setUpdateUserName(user.getName());
    		teachersUCC.updateTeachers(kpPersonLibrary);	
    	}
    	sexList=sexData();
    	request.setAttribute("type", type);
    	request.setAttribute("sexList", sexList);
    	httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    /**
     * 删除记录
     * @return
     * @throws BkmsException
     */
    public String deleteTeachersIds()throws BkmsException{
    	ITeachersUCC  teachersUCC = (ITeachersUCC) BkmsContext.getBean("teachersUCC");
    	String ids  = request.getParameter("genericIds");
    	teachersUCC.deleteTeachersIds(ids);	
    	httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null ;
    }
    public List sexData(){
    	List list =new ArrayList();
    	Map map =new HashMap();
    	map.put("types","男");
    	map.put("values","男");
    	list.add(map);
    	Map map1 =new HashMap();
    	map1.put("types","女");
    	map1.put("values","女");
    	list.add(map1);
    	return list;
    }
}
