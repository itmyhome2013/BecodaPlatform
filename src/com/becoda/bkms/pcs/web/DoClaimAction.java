package com.becoda.bkms.pcs.web;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.pcs.ucc.DoClaimUCC;
import com.becoda.bkms.util.Tools;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：DoClaimAction
 * @描述：TODO(任务管理-待签收任务ACTION)
 * @创建人： 张晓亮
 * @创建时间：2016年1月19日 上午11:10:36
 * @修改人：张晓亮
 * @修改时间：2016年1月19日 上午11:10:36
 * @修改备注：
 */
public class DoClaimAction extends GenericPageAction{
	private String flag;
	private String message;
	/**
	 * 
	 * @方法名称: findDoClaimList
	 * @描述：TODO(待签收列表) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findDoClaimList(){
		try {
			String processName = Tools.filterNull(request.getParameter("processName"));
			DoClaimUCC doClaimUCC = (DoClaimUCC) getBean("pcs_doClaimUCC");
			List<Map<String, String>> doClaimPageList = doClaimUCC.queryDoClaimPageList(vo, user,processName);
			request.setAttribute("doClaimPageList", doClaimPageList);
		} catch (Exception e) {
			 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	         this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return "doClaimList";
	}
	
	/**
	 * 
	 * @方法名称: taskClaim
	 * @描述：TODO(签收任务) 
	 * @返回值类型： String  
	 * @return
	 */
	public String taskClaim(){
		try {
			String taskId = Tools.filterNull(request.getParameter("taskId"));
			DoClaimUCC doClaimUCC = (DoClaimUCC) getBean("pcs_doClaimUCC");
			TaskEntity taskEntity = doClaimUCC.findTaskById(taskId);
			flag="true";
			if(null==taskEntity){
				message="该任务已不存在!";
				flag="false";
			}
			if(!Tools.isNull(taskEntity.getAssignee())){
				message="该任务已被签收!";
				flag="false";
			}
			if(flag.equals("true")){
				doClaimUCC.SaveTaskClaim(taskId, user);
				message="签收成功!";
				flag="true";
			}
			
		} catch (Exception e) {
			 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	         this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return SUCCESS;
	}

	//get and set
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
