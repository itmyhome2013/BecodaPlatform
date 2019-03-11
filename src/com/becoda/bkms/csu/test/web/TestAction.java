package com.becoda.bkms.csu.test.web;

import java.io.IOException;
import java.util.Map;

import net.sf.json.JSONObject;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.csu.common.FormToVo;
import com.becoda.bkms.csu.test.pojo1.demoPo;
import com.becoda.bkms.csu.test.ucc.ITestUCC;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.util.BkmsContext;


public class TestAction extends GenericAction {
   
	
	public String toList()throws BkmsException{
		return "toList";
	}
	
	
	public String queryProblem() throws BkmsException{
		
		ITestUCC  domeUcc = (ITestUCC) BkmsContext.getBean("demoUCC");
		
		String code = request.getParameter("code");
		ParmsSpecialInfo info =  new ParmsSpecialInfo();
		info.setCode(code);
		
		Map map = domeUcc.queryList(page,rows,info);
		
		//JSONArray jsonArray = JSONArray.fromObject(isProblemList);
		
		JSONObject jo = JSONObject.fromObject(map);
		
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
	
	
	public String getByCode() throws BkmsException{
		
		String code = request.getParameter("code");
		demoPo info =  new demoPo();
		//info.setna(code);
		info.setId("机构");
		info.setCode("2");
		info.setPar1("1,3,4");
		info.setPar2("31");
		info.setPar3("fsdfsdfasdfasdfasfasdfasdf111111哈哈啊哈哈");
		//JSONArray jsonArray = JSONArray.fromObject(isProblemList);
		
		JSONObject jo = JSONObject.fromObject(info);
		System.out.println(jo.toString());
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
	
	
	public String saveDemo() throws BkmsException{
		
		ITestUCC  domeUcc = (ITestUCC) BkmsContext.getBean("demoUCC");
		String [] ids  = {"402894b84fda938a014fda9469640001","40289e924fda9318014fdaa7eb370002"};
		domeUcc.delete(ids);
		
		try {
			demoPo info = (demoPo)FormToVo.formToVo(request, demoPo.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	
}
