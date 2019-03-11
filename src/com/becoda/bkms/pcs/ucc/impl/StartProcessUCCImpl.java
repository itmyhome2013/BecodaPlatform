package com.becoda.bkms.pcs.ucc.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import com.becoda.bkms.pcs.pojo.bo.ActExProcessBO;
import com.becoda.bkms.pcs.service.StartProcessService;
import com.becoda.bkms.pcs.ucc.ProcessCoreUCC;
import com.becoda.bkms.pcs.ucc.StartProcessUCC;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：StartProcessUCCImpl
 * @描述：TODO(启动流程UCC实现层)
 * @创建人： 张晓亮
 * @创建时间：2016年1月18日 下午1:48:09
 * @修改人：张晓亮
 * @修改时间：2016年1月18日 下午1:48:09
 * @修改备注：
 */
public class StartProcessUCCImpl extends ProcessCoreUCCImpl implements StartProcessUCC{
	private StartProcessService startProcessService;
	
	public List<ProcessDefinition> queryProcDef() {
		    List<ProcessDefinition> list = getRepositoryService()
				                           .createProcessDefinitionQuery()
										   .orderByProcessDefinitionVersion()
										   .latestVersion()
										   .asc().list();
		return list;
	}
	
	public ProcessInstance saveStartProcess(String procdefkey,ActExProcessBO actExProcess,
			String businessKey, Map<String, Object> variables) throws Exception {
		this.startProcessService.saveActExProcess(actExProcess);//保存流程对象
		ProcessInstance processInstance = saveStartProcessInstanceByKey(procdefkey, businessKey, variables);
		return processInstance;
	}
	
	
	
	
	
	//get and set
	public StartProcessService getStartProcessService() {
		return startProcessService;
	}
	public void setStartProcessService(StartProcessService startProcessService) {
		this.startProcessService = startProcessService;
	}
	
	
	
	

}
