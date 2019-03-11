package com.becoda.bkms.csu.classInfo.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.csu.classInfo.pojo.KpClass;
import com.becoda.bkms.csu.classInfo.ucc.KpClassUCC;
import com.becoda.bkms.util.BkmsContext;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;


public class KpClassInfoAction extends GenericAction {
	
	private KpClass kpClass = new KpClass();
	private String id ;
	private String editType;
	private JSONObject resutObj;  
	private Map<String, Object> json= new  HashMap<String, Object>();
	
	
	public String deleteEBidProduct() throws BkmsException {
        try {
//        	String ids=request.getParameter("ids");
//        	String userId=user.getUserId();
//        	KpClassUCC kpClassUCC = (KpClassUCC) BkmsContext.getBean("kp_classInfoUCC");
//        	//删除
//        	String str=kpClassUCC.deleteKpClass(ids);
//        	this.showMessage(str);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "queryEBidProduct";
    }
    
	public String toClassManagerPage(){
		return "input";
	}
	/**
	 * 添加 or 修改 班级信息记录
	 * @return
	 */
	public String kpClassSave(){
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		KpClassUCC kpClassUCC = null;
		System.out.println(kpClass.getClassName());
		try {
			kpClassUCC = (KpClassUCC) BkmsContext.getBean("kp_classInfoUCC");
			if(id!=null && !"".equals(id)){ //id 不为null 则为修改
				KpClass updateKpClass = kpClassUCC.updateKpClass(kpClass,id);
				System.out.println(updateKpClass.getClassName());
			}else {
				kpClassUCC.saveKpClass(kpClass);
			}
			httpResponse.getWriter().print("success");
		} catch (BkmsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除 班级信息记录
	 * 
	 * @return
	 */
	public String kpClassDelete(){
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		KpClassUCC kpClassUCC = null;
		int flag = 0;
		try {
			kpClassUCC = (KpClassUCC) BkmsContext.getBean("kp_classInfoUCC");
			flag = kpClassUCC.deletKpClassByIds(ids);
			if(flag == 0){
				httpResponse.getWriter().print("fail");
			}else {
				httpResponse.getWriter().print("success");
			}
		} catch (BkmsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * kpClass info view edit
	 * @return
	 */
	public String kpClassDetail(){
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		KpClassUCC kpClassUCC = null;
		try {
			kpClassUCC = (KpClassUCC) BkmsContext.getBean("kp_classInfoUCC");
			if(id!=null && !"".equals(id)){
				kpClass = kpClassUCC.getKpClass(id);
			}
			JSONObject jo = JSONObject.fromObject(kpClass);
			httpResponse.getWriter().print(jo);
		} catch (BkmsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 *  查询 班级列表信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String kpClassList(){
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		KpClassUCC kpClassUCC = null;
		
		try {
			kpClassUCC = (KpClassUCC) BkmsContext.getBean("kp_classInfoUCC");
			json = kpClassUCC.queryClassInfoByType(page, rows, kpClass);
			JSONObject jo = JSONObject.fromObject(json);
			httpResponse.getWriter().print(jo);
			
			
		} catch (BkmsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  班级解散
	 * @return
	 */
	public String disbandKpClass(){
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		KpClassUCC kpClassUCC = null;
		
		try {
			kpClassUCC = (KpClassUCC) BkmsContext.getBean("kp_classInfoUCC");
			int flag = kpClassUCC.disbandKpClassByIds(ids);
			if(flag == 1){
				httpResponse.getWriter().print("success");
			}else {
				httpResponse.getWriter().print("fail");
			}
		} catch (BkmsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public KpClass getKpClass() {
		return kpClass;
	}

	public void setKpClass(KpClass kpClass) {
		this.kpClass = kpClass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public JSONObject getResutObj() {
		return resutObj;
	}

	public void setResutObj(JSONObject resutObj) {
		this.resutObj = resutObj;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}
   
}
