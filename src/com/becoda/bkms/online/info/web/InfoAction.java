package com.becoda.bkms.online.info.web;

import java.util.List;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.online.info.InfoConstants;
import com.becoda.bkms.online.info.pojo.bo.InfoBo;
import com.becoda.bkms.online.info.pojo.vo.InfoVo;
import com.becoda.bkms.online.info.ucc.IInfoUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;


public class InfoAction extends GenericPageAction {
	
    private  InfoVo info;

	public InfoVo getInfo() {
		return info;
	}

	public void setInfo(InfoVo info) {
		this.info = info;
	}

	//查询信息
    public String listInformation() throws BkmsException {
    	String category = request.getParameter("category");
    	if(info==null){
    		info = new InfoVo();
    		info.setCategory(category);
    	}
    	IInfoUCC ucc = (IInfoUCC) BkmsContext.getBean("onlineInfoUCC");
        List ls = ucc.queryInfoByType(vo,info);
        List codeList = SysCacheTool.queryCodeItemBySetIdAndSuperId(info.getCategory(), "-1");
		request.setAttribute("codeList", codeList);
        request.setAttribute("infoList", ls);
        request.setAttribute("category", category);
        return "list";
    }
   
	//保存或更新信息
    public String saveInformation() throws BkmsException {
    	IInfoUCC ucc = (IInfoUCC) BkmsContext.getBean("onlineInfoUCC");
        String subId=info.getSubId();
        InfoBo bo = ucc.findInfoById(subId); 
        String isPublish = request.getParameter("isPublish");
        
        if (bo == null) { //新增
            bo = new InfoBo();
            Tools.copyProperties(bo, info);
            bo.setCreateUserId(user.getUserId());
            bo.setCreateTime(Tools.getSysDate(null));
            bo.setUpdateTime(Tools.getSysDate(null));
            bo.setStatus(InfoConstants.INFO_STATUS_NO);
            bo.setTop(InfoConstants.IS_NOT_TOP);
            bo.setReCreateTime(Tools.getSysDate(null));
        	bo.setReCreateUserid(user.getUserId());
            this.publishInfo(bo, isPublish);
            ucc.saveInfomation(bo);
        } else {       //保存
        	String reado = request.getParameter("reado");
        	if(Tools.stringIsNull(reado)){
        		bo.setTitle(info.getTitle());
                bo.setContent(info.getContent());
                bo.setPhone(info.getPhone());
                bo.setCreateUserName(info.getCreateUserName());
                bo.setIsPublic(info.getIsPublic());
                bo.setType(info.getType());
                bo.setCreateUserId(user.getUserId());
        	}
        	bo.setReContent(info.getReContent());
        	bo.setReCreateTime(Tools.getSysDate(null));
        	bo.setReCreateUserid(user.getUserId());
            bo.setStatus(InfoConstants.INFO_STATUS_NO);
            bo.setUpdateTime(Tools.getSysDate(null));
            this.publishInfo(bo, isPublish);
            ucc.updateInfomation(bo);
        }
        showMessage("保存成功！");
        info.setType(null);
        info.setIsPublic(null);
        return listInformation();
    }
    
    private InfoBo publishInfo(InfoBo bo, String isPublish) throws RollbackableException {
        //是否发布
        if (isPublish != null && "isPublish".equals(isPublish)) {
            bo.setStatus(InfoConstants.INFO_STATUS_YES);
        }else{
            bo.setTop(InfoConstants.IS_NOT_TOP);
        }
        return bo;
    }
    
    //获得某一信息进行编辑
    public String findInfo() throws BkmsException {
    	IInfoUCC ucc = (IInfoUCC) BkmsContext.getBean("onlineInfoUCC");
        String id = request.getParameter("id");
        if (id != null && !"".equals(id)) {
        	InfoBo bo = ucc.findInfoById(id);
        	List codeList = SysCacheTool.queryCodeItemBySetIdAndSuperId(bo.getCategory(), "-1");
    		request.setAttribute("codeList", codeList);
    		if(!bo.getCreateUserId().equals(user.getUserId())){
    			request.setAttribute("reado", "reado");
    		}
        	info=new InfoVo();
            Tools.copyProperties(info, bo);
        }
        
        return "edit";
    }

    //跳转至编辑页面
    public String gotoEdit() throws BkmsException {
    	if(info!=null){
    		List codeList = SysCacheTool.queryCodeItemBySetIdAndSuperId(info.getCategory(), "-1");
    		request.setAttribute("codeList", codeList);
    		info.setIsPublic(InfoConstants.IS_PUBLIC);
    	}
    	return "edit";
    }

    //删除信息
    public String deleteInformation() throws BkmsException {
        String id = request.getParameter("id");
        IInfoUCC ucc = (IInfoUCC) BkmsContext.getBean("onlineInfoUCC");
        InfoBo bo = ucc.findInfoById(id);
        ucc.deleteInfomation(id);
       
        super.showMessage("删除成功！");
        return listInformation();
    }

    //批量修改信息状态
    public String updateInfoStatus() throws BkmsException {
        String[] ids = request.getParameterValues("chk");
        String status = request.getParameter("status");
        IInfoUCC ucc = (IInfoUCC) BkmsContext.getBean("onlineInfoUCC");
        if (InfoConstants.INFO_STATUS_YES.equals(status)) {
            super.showMessage("发布成功！");
            List list = ucc.queryInfo(ids);
            //模板名称：information.templete
        } else {
            ucc.updateAllStatus(ids, "status", status);
            super.showMessage("取消发布成功！");
        }
        return listInformation();
    }

    //批量修改信息是否置顶
    public String updateInfoIsTop() throws BkmsException {
        String[] ids = request.getParameterValues("chk");
        String status = Tools.filterNull(request.getParameter("status"));
        IInfoUCC ucc = (IInfoUCC) BkmsContext.getBean("onlineInfoUCC");
        ucc.updateAllStatus(ids, "TOP", status);
        if (InfoConstants.IS_TOP.equals(status)) {
            super.showMessage("置顶成功！");
        } else {
            super.showMessage("取消成功！");
        }
        return listInformation();
    }
    
    public static void main(String[] args) {
    	
		String aa1= "insert into ONLINE_INFO (SUBID, TITLE, CONTENT, PHONE, CREATETIME, CREATEUSERID, CREATEUSERNAME, STATUS, TOP, UPDATETIME, RECONTENT, RECREATEUSERID, RECREATETIME, CATEGORY, TYPE, ISPUBLIC) values ('";
		String aa2 = "', '1111', '1111', null, '2015-11-08 14:48:46', '11112024', null, '1', '0', '2015-11-08 14:48:46', '11111', '11112024', '2015-11-08 14:48:46', '3005', '3005401028', '00901' )"; 
		for(int i=3;i<100;i++){
			String aa3 = "";
			aa3 = aa1+i+aa2+";";
			System.out.println(aa3);
		}
    }
}
