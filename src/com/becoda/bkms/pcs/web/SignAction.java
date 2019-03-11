package com.becoda.bkms.pcs.web;

import java.util.List;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pcs.pojo.vo.ActExBussinessForm;
import com.becoda.bkms.pcs.ucc.IActExBussinessUCC;
import com.becoda.bkms.run.pojo.bo.BulletinParamBO;
import com.becoda.bkms.run.pojo.vo.BulletinForm;
import com.becoda.bkms.run.ucc.IBulletionUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

public class SignAction extends GenericPageAction{
	 private ActExBussinessForm bussinessForm ;
	 //查询
    public String list() throws BkmsException {
    	
        IActExBussinessUCC ucc = (IActExBussinessUCC) BkmsContext.getBean("bussinessUCC");
        List bos = null;
        String bussinessId = Tools.filterNull(request.getParameter("businessId"));
        bos = ucc.findByBussinessId("");
        request.setAttribute("bos", bos);
        return "list";
    }
    
    //新增时初始化页面
    public String find() throws BkmsException {
    	IActExBussinessUCC ucc = (IActExBussinessUCC) BkmsContext.getBean("bussinessUCC");
        String id = Tools.filterNull(request.getParameter("id"));
        if(bussinessForm==null){
        	bussinessForm=new ActExBussinessForm();
        	bussinessForm.setBussiness_id(id);
        }
        return "find";
    }
    
  //保存
    public String save() throws BkmsException {
        String id = Tools.filterNull(bussinessForm.getBussiness_id());
        IActExBussinessUCC ucc = (IActExBussinessUCC) BkmsContext.getBean("bussinessUCC");
        if ("".equals(id)) { //添加
            Person person = SysCacheTool.findPersonById(user.getUserId());
            ucc.createBussiness(bussinessForm, user.getUserId(), person.getOrgId(), user);
        } else {   //修改
            
        }
        super.showMessage("发布成功!");
        return this.list();
    }
    
    public String handle() throws BkmsException{
    	String optType= Tools.filterNull(request.getParameter("optType"));
    	String taskId = Tools.filterNull(request.getParameter("taskId"));
    	String processId = Tools.filterNull(request.getParameter("processId"));
    	String processName = Tools.filterNull(request.getParameter("processName"));
    	String taskName = Tools.filterNull(request.getParameter("taskName"));
    	String taskDefKey = Tools.filterNull(request.getParameter("taskDefKey"));
    	
    	request.setAttribute("optType", optType);
    	request.setAttribute("taskId", taskId);
    	request.setAttribute("processId", processId);
    	request.setAttribute("processName", processName);
    	request.setAttribute("taskName", taskName);
    	request.setAttribute("taskDefKey", taskDefKey);
    	return "handle";
    }

	public ActExBussinessForm getBussinessForm() {
		return bussinessForm;
	}

	public void setBussinessForm(ActExBussinessForm bussinessForm) {
		this.bussinessForm = bussinessForm;
	}
    
}
