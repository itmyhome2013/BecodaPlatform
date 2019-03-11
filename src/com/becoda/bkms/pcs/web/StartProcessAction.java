package com.becoda.bkms.pcs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pcs.PcsConstants;
import com.becoda.bkms.pcs.pojo.bo.ActExProcessBO;
import com.becoda.bkms.pcs.ucc.IActExBussinessUCC;
import com.becoda.bkms.pcs.ucc.StartProcessUCC;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.pcs.util.UUIDGenerator;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：StartProcessAction
 * @描述：TODO(流程管理-启动流程ACTION)
 * @创建人： 张晓亮
 * @创建时间：2016年1月18日 下午2:16:08
 * @修改人：张晓亮
 * @修改时间：2016年1月18日 下午2:16:08
 * @修改备注：
 */
public class StartProcessAction extends GenericAction{

	private ActExProcessBO actExProcess;
	/**
	 * 
	 * @方法名称: findProcDefTree
	 * @描述：TODO(流程定义树型查询) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findProcDefTree(){
		try {
		StartProcessUCC startProcessUCC = (StartProcessUCC) getBean("pcs_actExProcessUCC");
		List<ProcessDefinition> procDefList = startProcessUCC.queryProcDef();
		request.setAttribute("procDefList", procDefList);
		} catch (Exception e) {
			 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	         this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @方法名称: forSendStartIframe
	 * @描述：TODO(树型加载页面 ) 
	 * @返回值类型： String  
	 * @return
	 */
	public String forSendStartIframe(){
		try {
			String procDefId = Tools.filterNull(request.getParameter("procDefId"));
			StartProcessUCC startProcessUCC = (StartProcessUCC) getBean("pcs_actExProcessUCC");
			ProcessDefinition procDefEntity=startProcessUCC.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
			Deployment deploymentEntity= startProcessUCC.getRepositoryService().createDeploymentQuery().deploymentId(procDefEntity.getDeploymentId()).singleResult();
			request.setAttribute("procDefEntity", procDefEntity);
			request.setAttribute("deploymentEntity", deploymentEntity);
			} catch (Exception e) {
				 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
		         this.addActionError(he.getFlag()+he.getCause().getMessage());
			}
	     return SUCCESS;
	}
	
	
	
	/**
	 * 
	 * @方法名称: startProcess
	 * @描述：TODO(正式启动流程) 
	 * @返回值类型： String  
	 * @return
	 */
	public String startProcess(){
		try {
			String procdefkey=PcsConstants.procdefkey;
			String processId = UUIDGenerator.getUUID();
			actExProcess.setProcessid(processId);
			actExProcess.setIsdeleted("0");
			actExProcess.setCreateuserid(this.user.getUserId());
			actExProcess.setCreateusername(this.user.getName());
			actExProcess.setCreatedate(DateUtil.date2Str(DateUtil.datetimeFormat));
			StartProcessUCC startProcessUCC = (StartProcessUCC) getBean("pcs_actExProcessUCC");
			Map<String,Object> variables = new HashMap<String, Object>();
			variables.put("inputUser", user.getUserId());
			actExProcess.setProcdefkey("LeaveProcess");
			actExProcess.setProcdefname("请假流程");
			startProcessUCC.saveStartProcess(procdefkey, actExProcess,processId, variables);
			} catch (Exception e) {
				 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
		         this.addActionError(he.getFlag()+he.getCause().getMessage());
			}
			
			//返回事件填报列表 
			try {
				IActExBussinessUCC ucc = (IActExBussinessUCC) BkmsContext.getBean("bussinessUCC");
				List bos = null;
		        String bussinessId = Tools.filterNull(request.getParameter("businessId"));
		        bos = ucc.findByBussinessId("");
		        request.setAttribute("bos", bos);
			} catch (BkmsException e) {
				e.printStackTrace();
			}
			
			return SUCCESS;
	}

	
	//get and set
	public ActExProcessBO getActExProcess() {
		return actExProcess;
	}

	public void setActExProcess(ActExProcessBO actExProcess) {
		this.actExProcess = actExProcess;
	}
	
	
	
}
