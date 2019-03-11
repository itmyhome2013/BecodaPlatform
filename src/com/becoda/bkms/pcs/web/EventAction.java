package com.becoda.bkms.pcs.web;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.pcs.pojo.bo.ActExProcessBO;
import com.becoda.bkms.pcs.pojo.vo.ActExBussinessForm;
import com.becoda.bkms.pcs.ucc.IActExBussinessUCC;
import com.becoda.bkms.sys.platform.ucc.DataResult;
import com.becoda.bkms.sys.platform.ucc.IPlatFormUCC;
import com.becoda.bkms.sys.platform.ucc.PlatFormManager;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

public class EventAction extends GenericPageAction{
	 private ActExBussinessForm bussinessForm ;
	 //查询
    public String list() throws BkmsException {
    	
        IActExBussinessUCC ucc = (IActExBussinessUCC) BkmsContext.getBean("bussinessUCC");
        List bos = null;
        String bussinessId = Tools.filterNull(request.getParameter("businessId"));
        bos = ucc.findByBussinessId("");
        
        Map<String, String> itemsMap = PlatFormManager.getPlatFormBean().findCodeItemForIndex("urgency"); //查询代码集
        
        for(int i=0;i<bos.size();i++){
        	ActExProcessBO bo = (ActExProcessBO) bos.get(i);
        	bo.setUrgency(DataResult.runDictionarySingle(itemsMap, bo.getUrgency()));
        }
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
  //删除
    public String del() throws BkmsException {
    	IActExBussinessUCC ucc = (IActExBussinessUCC) BkmsContext.getBean("bussinessUCC");
        String ids[] = request.getParameterValues("processId");
        ucc.deleteBussinessByBussinessIdArray(ids, user);
        super.showMessage("删除成功!");
        this.list();
        return "list";
    }
	public ActExBussinessForm getBussinessForm() {
		return bussinessForm;
	}

	public void setBussinessForm(ActExBussinessForm bussinessForm) {
		this.bussinessForm = bussinessForm;
	}
    
}
