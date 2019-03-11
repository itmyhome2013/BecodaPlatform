package com.becoda.bkms.pcs.ucc.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.becoda.bkms.pcs.ucc.ProcessCoreUCC;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ProcessCoreUCCImpl
 * @描述：TODO(程操作核心类，此核心类主要处理：流程通过、驳回、转办、中止、挂起等核心操作 )
 * @创建人： 张晓亮
 * @创建时间：2016年1月19日 下午1:45:40
 * @修改人：张晓亮
 * @修改时间：2016年1月19日 下午1:45:40
 * @修改备注：
 */
public class ProcessCoreUCCImpl extends ServiceImpl implements ProcessCoreUCC   {
	
/************************************************************************************************************
 ******************************************以下为流程管理******************************************************
 ************************************************************************************************************/
	
	/**
	 * 部署流程定义
	 * 
	 * @param deployProcessZip
	 *        部署流程文件(Zip格式)
	 * @param Processname
	 *        部署流程名称
	 * @throws Exception 
	 */
	public void saveDeployProcess(File deployProcessZip, String Processname) throws Exception {
		//将File类型的文件转化成ZipInputStream流
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(deployProcessZip));
		//部署流程
		this.repositoryService.createDeployment()
				              .addZipInputStream(zipInputStream)
				              .name(Processname)
				              .deploy();
	}
	
	/**
	 * 查询部署对象信息
	 * 
	 * @return List<Deployment>
	 *         部署对象信息集合
	 * @throws Exception
	 */
	public List<Deployment> findDeploymentList() throws Exception{
		List<Deployment> list = this.repositoryService.createDeploymentQuery()
				                                      .orderByDeploymenTime()
				                                      .asc()
				                                      .list();
		return list;
	}
	
	/**
	 * 查询流程定义的信息
	 * 
	 * @return List<ProcessDefinition>
	 *         流程定义的信息集合
	 * @throws Exception
	 */
	public List<ProcessDefinition> findProcessDefinitionList() throws Exception{
		List<ProcessDefinition> list = this.repositoryService.createProcessDefinitionQuery()
				                                             .orderByProcessDefinitionVersion()
				                                             .asc()
				                                             .list();
		return list;
	}

	/**
	 * 级联删除流程定义
	 * 
	 * @param deploymentIds
	 *            部署对象ID
	 * @throws Exception
	 */
	public void deleteDeploymentByIds(String[] deploymentIds)
			throws Exception {
		if(deploymentIds==null || deploymentIds.length==0){
			throw new Exception("部署ID数组为空！");
		}else{
			for(String deploymentId : deploymentIds){
				this.repositoryService.deleteDeployment(deploymentId, true);
			}
		}
		
	}

	
	/**
	 * 激活流程实例
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @param activateProcessInstances
	 *            是否激活流程实例
	 * @param activationDate
	 *            激活日期
	 * @throws Exception
	 */
	public void saveActivateProcessDefinitionById(String processDefinitionId,
			boolean activateProcessInstances, Date activationDate)
			throws Exception {
		this.repositoryService.activateProcessDefinitionById(
				processDefinitionId, activateProcessInstances, activationDate);
	}
	
	/**
	 * 激活流程实例
	 * @param processDefinitionId
	 *        流程定义ID
	 * @throws Exception
	 */
	public void saveActivateProcessDefinitionById(String processDefinitionId)
			throws Exception {
		this.repositoryService.activateProcessDefinitionById(processDefinitionId);
	}
	
	
	/**
	 * 激活流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义Key
	 * @param activateProcessInstances
	 *            是否激活流程实例
	 * @param activationDate
	 *            激活日期
	 * @throws Exception
	 */
	public void saveActivateProcessDefinitionByKey(String processDefinitionKey,
			boolean activateProcessInstances, Date activationDate)
			throws Exception {
		this.repositoryService.activateProcessDefinitionByKey(
				processDefinitionKey, activateProcessInstances, activationDate);

	}
	
	/**
	 * 激活流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义Key
	 * @throws Exception
	 */
	public void saveActivateProcessDefinitionByKey(String processDefinitionKey)
			throws Exception {
		this.repositoryService.activateProcessDefinitionByKey(processDefinitionKey);
	}
	
	/**
	 * 挂起流程实例
	 * 
	 * @param processDefinitionId
	 *            流程定义Id
	 * @param suspendProcessInstances
	 *            是否挂起流程实例
	 * @param suspensionDate
	 *            挂起日期
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionById(String processDefinitionId,
			boolean suspendProcessInstances, Date suspensionDate)
			throws Exception {
		this.repositoryService.suspendProcessDefinitionById(
				processDefinitionId, suspendProcessInstances, suspensionDate);
	}
	
	/**
	 * 挂起流程实例
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionById(String processDefinitionId)
			throws Exception {
		this.repositoryService.suspendProcessDefinitionById(processDefinitionId);
	}
	
	/**
	 * 挂起流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义Key
	 * @param suspendProcessInstances
	 *            是否挂起流程实例
	 * @param suspensionDate
	 *            挂起日期
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionByKey(String processDefinitionKey,
			boolean suspendProcessInstances, Date suspensionDate)
			throws Exception {
		this.repositoryService.suspendProcessDefinitionByKey(
				processDefinitionKey, suspendProcessInstances, suspensionDate);

	}
	
	/**
	 * 挂起流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义Key
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionByKey(String processDefinitionKey)
			throws Exception {
		this.repositoryService
				.suspendProcessDefinitionByKey(processDefinitionKey);

	}
	
	
/************************************************************************************************************
 ******************************************以上为流程管理******************************************************
 ************************************************************************************************************/

	
/************************************************************************************************************
 ******************************************以下为工作流执行操作**************************************************
 ************************************************************************************************************/	
	
	/**
	 * 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
	 * 
	 * @param processDefinitionKey
	 *            流程定义的key
	 * @param businessKey
	 * @param variables
	 * @return ProcessInstance
	 */
	public ProcessInstance saveStartProcessInstanceByKey (
			String processDefinitionKey, String businessKey,
			Map<String, Object> variables) throws Exception {
		    if(variables==null){
		    	variables = new HashMap<String, Object>();
		    }
		return  runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}
	
	/**
	 * 使用流程定义的ID，启动流程实例，同时设置表单变量
	 * 
	 * @param processDefinitionId
	 *         流程定义ID
	 * @param businessKey
	 *        业务ID
	 * @param variables
	 *        表单变量
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance saveStartFormDataInstanceByKey(
			String processDefinitionId,String businessKey, Map<String, String> variables) throws Exception{
		if(variables==null){
	    	variables = new HashMap<String, String>();
	    }
		if(StringUtils.isEmpty(businessKey)){
			return  formService.submitStartFormData(processDefinitionId, variables);	
		}
		return  formService.submitStartFormData(processDefinitionId,businessKey, variables);
	}
	
	
	/**
	 * 审批通过
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程存储参数
	 * @throws Exception
	 */
	public void savePassProcess(String taskId, Map<String, Object> variables)
			throws Exception {
		  if(variables==null){
			variables = new HashMap<String, Object>();
		  }
		saveCommitProcess(taskId, variables, null);
	}
	
	/**
	 * 驳回流程
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            驳回节点ID
	 * @param variables
	 *            流程存储参数
	 * @throws Exception
	 */
	public void saveBackProcess(String taskId, String activityId,
			Map<String, Object> variables) throws Exception {
		if (activityId==null || "".equals(activityId.trim())) {  
            throw new Exception("驳回目标节点ID为空！");  
        }
		if(variables==null){
			variables = new HashMap<String, Object>();
		}
		this.saveCommitProcess(taskId, variables, activityId);

	}


	
	/**
	 * 通用完成任务
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程变量
	 * @param activityId
	 *            流程转向执行任务节点ID<br>
	 *            此参数为空，默认为提交操作
	 * @throws Exception
	 */
	public void saveCommitProcess(String taskId, Map<String, Object> variables,
			String activityId) throws Exception {
		//如果流程变量为空时
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 跳转节点为空，默认提交操作
		if (activityId==null || "".equals(activityId.trim())) {
			taskService.complete(taskId, variables);
		} else {
			// 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}
	
	/**
	 * 取回流程
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            取回节点ID
	 * @throws Exception
	 */
	public void saveCallBackProcess(String taskId, String activityId)
			throws Exception {
		if (activityId==null || "".equals(activityId.trim())) {  
            throw new Exception("驳回目标节点ID为空！");  
        }
		// 查找所有并行任务节点，同时取回  
		List<Task> taskList = findTaskListByKey(
				findProcessInstanceByTaskId(taskId).getId(),
				findTaskById(taskId).getTaskDefinitionKey()); 
		for (Task task : taskList) {  
            saveCommitProcess(task.getId(), null, activityId);  
        }
	}
	
	/**
	 * 中止流程(特权人直接审批通过等)
	 * 
	 * @param taskId
	 *            任务ID
	 */
	public void saveEndProcess(String taskId) throws Exception {
		ActivityImpl endActivity = findActivitiImpl(taskId, "end");
		this.saveCommitProcess(taskId, null, endActivity.getId());

	}
	/** 
     * 转办流程 
     *  
     * @param taskId 
     *            当前任务节点ID 
     * @param userCode 
     *            被转办人Code 
     */  
    public void saveTransferAssignee(String taskId, String userCode)throws Exception {  
        taskService.setAssignee(taskId, userCode);  
    }

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 *            当前任务节点ID
	 * @param userCode
	 *            签收人Code
	 *            当userCode为空时表未反签收（还原到未签收状态）
	 */
	public void saveTaskclaim(String taskId, String userCode) {
		this.taskService.claim(taskId, userCode);
	}
	/**
	 * 委托流程
	 * 
	 * @param taskId
	 *            当前任务节点ID
	 * @param userCode
	 *            被委托人Code
	 */
    public void saveDelegateTask(String taskId, String userCode) {
    	this.taskService.delegateTask(taskId, userCode);
    	
    }

    
/************************************************************************************************************
 ******************************************以上为工作流执行操作**************************************************
 ************************************************************************************************************/
    
	
	
/************************************************************************************************************
 ****************************************** 以下为工作流变量操作**************************************************
 ************************************************************************************************************/	
	
	/**
	 * 表示使用执行对象ID，和流程变量的名称
	 * 设置流程变量的值（一次只能设置一个值）
	 * @param executionId
	 *        执行对象ID
	 * @param variableName
	 *        流程变量的名称
	 * @param value
	 *        变量的值
	 * @throws Exception
	 */
	public void saveVariableByExecutionId(String executionId, String variableName, Object value) throws Exception{
		this.runtimeService.setVariable(executionId, variableName, value);
	}
    
	/**
	 * 表示使用执行对象ID，和Map集合设置流程变量
	 * map集合的key就是流程变量的名称
	 * map集合的value就是流程变量的值（一次设置多个值）
	 * @param executionId
	 *        执行对象ID
	 * @param Map<String,Object> variables
	 *        流程变量集合
	 * @throws Exception
	 */
	public void saveVariablesByExecutionId(String executionId, Map<String,Object> variables) throws Exception{
		this.runtimeService.setVariables(executionId, variables);
	}

	/**
	 * 表示使用任务ID，和流程变量的名称
	 * 设置流程变量的值（一次只能设置一个值）
	 * @param taskId
	 *        任务ID
	 * @param variableName
	 *        流程变量的名称
	 * @param value
	 *        变量的值
	 * @throws Exception
	 */
	public void saveVariableByTaskId(String taskId,String  variableName, Object value) throws Exception{
		 this.taskService.setVariable(taskId, variableName, value);
	}
	
	/**
	 * 表示使用任务ID，和Map集合设置流程变量
	 * map集合的key就是流程变量的名称
	 * map集合的value就是流程变量的值（一次设置多个值）
	 * @param taskId
	 *        任务ID
	 * @param Map<String,Object> variables
	 *        流程变量集合
	 * @throws Exception
	 */
	public void saveVariablesByTaskId(String taskId, Map<String,Object> variables) throws Exception{
		this.taskService.setVariables(taskId, variables);
	}
	
	
	/**
	 * 使用执行对象ID和流程变量的名称，获取流程变量的值
	 * @param executionId
	 *        执行ID
	 * @param variableName
	 *        变量名称
	 * @return Object
	 *         
	 * @throws Exception
	 */
	public Object findVariablesByExecutionIdAndVariableName(String executionId, String variableName) throws Exception{
		return this.runtimeService.getVariable(executionId, variableName);
	}
	
	/**
	 * 使用执行对象ID，获取所有的流程变量，将流程变量放置到Map集合中
	 * map集合的key就是流程变量的名称，map集合的value就是流程变量的值
	 * @param executionId
	 *        执行ID
	 * @return Map<String,Object>
	 *         变量集合
	 * @throws Exception
	 */
	public Map<String,Object> findVariablesByExecutionId(String executionId) throws Exception{
		return this.runtimeService.getVariables(executionId);
	}
	
	/**
	 * 使用执行对象ID，获取流程变量的值，通过设置流程变量的名称存放到集合中
	 * 获取指定流程变量名称的流程变量的值，值存放到Map集合中
	 * @param executionId
	 *        执行ID
	 * @param List<String> variableNames
	 *        变量名称集合
	 * @return Map<String,Object>
	 *         变量集合
	 * @throws Exception
	 */
	public Map<String,Object> findVariablesByExecutionIdAndVariableNames(String executionId, List<String> variableNames) throws Exception{
		return this.runtimeService.getVariables(executionId, variableNames);
	}
	
	/**
	 * 使用任务ID和流程变量的名称，获取流程变量的值
	 * @param executionId
	 *        任务ID
	 * @param variableName
	 *        变量名称
	 * @return Object
	 *         
	 * @throws Exception
	 */
	public Object findVariableBytaskIdAndVariableName(String taskId, String variableName) throws Exception{
		return this.taskService.getVariable(taskId, variableName);
	}
	
	/**
	 * 使用任务ID，获取所有的流程变量，将流程变量放置到Map集合中，
	 * map集合的key就是流程变量的名称，map集合的value就是流程变量的值
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findVariablesByTaskId(String TaskId) throws Exception {
		return this.taskService.getVariables(TaskId);
	}
	
	/**
	 * 使用任务ID，获取流程变量的值，通过设置流程变量的名称存放到集合中
	 * 获取指定流程变量名称的流程变量的值，值存放到Map集合中
	 * @param taskId
	 *        任务ID
	 * @param List<String> variableNames
	 *        变量名称集合
	 * @return Map<String,Object>
	 *         变量集合
	 * @throws Exception
	 */
	public Map<String,Object> findVariablesByTaskIdAndVariableNames(String taskId, List<String> variableNames) throws Exception{
		return this.taskService.getVariables(taskId, variableNames);
	}
	
	
	
/************************************************************************************************************
 ****************************************** 以上为工作流变量操作**************************************************
 ************************************************************************************************************/	
	
	
	
	
	
	
	
/************************************************************************************************************
 ******************************************以下为工作流查询操作**************************************************
 ************************************************************************************************************/
    
	
	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public TaskEntity findTaskById(String taskId) throws Exception {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery()
				                                  .taskId(taskId)
				                                  .singleResult();
		if (task == null) {
			throw new Exception("任务实例未找到!");
		}
		return task;
	}
	
    /**
	 * 根据当前任务ID，查询可以驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findBackAvtivity(String taskId) throws Exception {
		List<ActivityImpl> rtnList = null;
		//if (processOtherService.isJointTask(taskId)) {// 会签任务节点，不允许驳回
			rtnList = new ArrayList<ActivityImpl>();
		//} else {
			rtnList = iteratorBackActivity(taskId,
					findActivitiImpl(taskId, null),
					new ArrayList<ActivityImpl>(),
					new ArrayList<ActivityImpl>());
	//	}
		return reverList(rtnList);
	}
	
	/**
	 * 查询（用户）的任务列表，获取当前任务的集合
	 * 
	 * @param userCode
	 *            办理人Code
	 * @return List<Task> 
	 *            任务集合
	 * @throws Exception
	 */
	public List<Task> findTaskListByAssignee(String userCode) {
		List<Task> list = this.taskService.createTaskQuery()
				                          .taskAssignee(userCode)
				                          .orderByTaskCreateTime()
				                          .asc()
				                          .list();
		return list;
	}
	
	/**
	 * 查询（用户组）任务列表，获取当前任务的集合
	 * 
	 * @param userCode
	 *           办理人Code
	 * @return List<Task> 
	 *            任务集合
	 * @throws Exception
	 */
	public List<Task> findTaskListByCandidateUser(String userCode)
			throws Exception {
		List<Task> list = this.taskService.createTaskQuery()
				                          .taskCandidateUser(userCode)
				                          .orderByTaskCreateTime()
				                          .asc()
				                          .list();
		return list;
	}
	
	/**
	 * 查询（角色组）任务列表，获取当前任务的集合
	 * 必须为activiti自带的角色表
	 * @param userCode
	 *            办理人Code
	 * @return List<Task> 
	 *         任务集合
	 * @throws Exception
	 */
	public List<Task> findTaskListByCandidateGroup(String userCode)
			throws Exception {
		List<Task> list = this.taskService.createTaskQuery()
				                          .taskCandidateGroup(userCode)
				                          .orderByTaskCreateTime()
				                          .asc()
				                          .list();
		return list;
	}
	
	
	/**
	 * 查询所有的任务列表 Assignee(指定人)+CandidateUser(用户组)+CandidateGroup(角色组)
	 * 
	 * @param userCode
	 *            办理人Code
	 * @return List<Task> 
	 *            任务集合
	 * @throws Exception
	 */
	public List<Task> findTaskListAll(String userCode) throws Exception {
		List<Task> taskAll = new ArrayList<Task>();
		List<Task> assignee = this.findTaskListByAssignee(userCode);
		List<Task> candidateUser=this.findTaskListByCandidateUser(userCode);
		List<Task> candidateGroup=this.findTaskListByCandidateGroup(userCode);
		taskAll.addAll(assignee);
		taskAll.addAll(candidateUser);
		taskAll.addAll(candidateGroup);
		return taskAll;
	}
	
	
	/**
	 * 使用任务ID，查找业务ID
	 * 
	 * @param taskId
	 *            任务ID
	 * @return businessKey 
	 *         业务ID
	 * @throws Exception
	 */
	public String findBusinessIdByTaskId(String taskId) throws Exception {
		String  businessKey=this.findProcessInstanceByTaskId(taskId).getBusinessKey();
		return businessKey;
	}
	
	/**
	 * 根据流程实例ID获取对应的流程实例
	 * 可用来判断流程是否结束
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance findProcessInstanceByProcessInstanceId(
			String processInstanceId) throws Exception {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				                           .processInstanceId(processInstanceId)//使用流程实例ID查询
				                           .singleResult();
		return pi;
	}
	
	/**
	 * 根据任务ID和activityId查询下一步节点信息
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            节点Id
	 * @return
	 * @throws Exception
	 */
	public List<PvmActivity> getOutPvmActivity(String taskId,String activityId) throws Exception{
		List<PvmActivity> list = new ArrayList<PvmActivity>();
		 ActivityImpl activityImpl=this.findActivitiImpl(taskId, activityId);
		 List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
		 for(PvmTransition p : outgoingTransitions){
			 list.add(p.getDestination());
		 }
		 return list;
	}
	
	
	
	
	
/************************************************************************************************************
 ******************************************以上为工作流查询操作**************************************************
 ************************************************************************************************************/    
    
	
/************************************************************************************************************
 ***************************************************以下为流程图片处理******************************************
 ************************************************************************************************************/	
	
	/**
	 * 使用部署对象ID和资源图片名称，获取图片的输入流
	 * 
	 * @param deploymentId
	 *            部署ID
	 * @param imageName
	 *            图片名称
	 * @return InputStream
	 *         输入流
	 * @throws Exception
	 */
	public InputStream findImageInputStream(String deploymentId,
			String imageName) throws Exception {
		InputStream inputStream = this.repositoryService.getResourceAsStream(deploymentId, imageName);
		return inputStream;
	}

	/**
	 * 查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>
	 * 中并把Map放入到List集合中
	 * 
	 * @param taskId
	 *            任务ID
	 * @return List<Map<String, Object>> 
	 *           当期活动对应的坐标x,y,width,height集合
	 * @throws Exception
	 */
	public List<Map<String, Object>> findCoordingByTask(String taskId)
			throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		//根据任务ID获取流程实例ID
	    String processInstanceId = this.findTaskById(taskId).getProcessInstanceId();
	    //根据任务实例查询正在执行的任务节点
		List<Execution> executionList=this.runtimeService.createExecutionQuery()
				                                         .processInstanceId(processInstanceId)
				                                         .list();
	    for(Execution execution:executionList){
	    	ActivityImpl activityImpl = this.findActivitiImpl(taskId, execution.getActivityId());
	    	if("userTask".equals(activityImpl.getProperty("type"))){
	    		map.put("x", activityImpl.getX());
				map.put("y", activityImpl.getY());
				map.put("width", activityImpl.getWidth());
				map.put("height", activityImpl.getHeight());
				list.add(map);
	    	}
	    }
		return list;
	}
	
	/**
	 * 根据任务ID查询当前流程图
	 * @param taskId
	 *        任务ID
	 * @return InputStream
	 *         图片输入流
	 * @throws Exception
	 */
	public InputStream findviewCurrentImage(String taskId) throws Exception{
		TaskEntity taskEntity= this.findTaskById(taskId);
		BpmnModel pm=this.repositoryService.getBpmnModel(taskEntity.getProcessDefinitionId());
		List<Execution> elist = this.findExecutionTaskByProcessInstanceId(taskEntity.getProcessInstanceId());
		List<String> list = new ArrayList<String>();
		 for(Execution execution:elist){
		    	ActivityImpl activityImpl = this.findActivitiImpl(taskId, execution.getActivityId());
		    	if("userTask".equals(activityImpl.getProperty("type"))){
		    		list.add(activityImpl.getId());
		    	}
		    }
		 ProcessEngineConfigurationImpl processEngineConfigurationImpl = ((ProcessEngineImpl) this.processEngine)
				    .getProcessEngineConfiguration();
		 Context.setProcessEngineConfiguration(processEngineConfigurationImpl);
		 InputStream in=ProcessDiagramGenerator.generateDiagram(pm,"png",list);
		 return in;
	}
	
/************************************************************************************************************
 ***************************************************以上为流程图片处理******************************************
 ************************************************************************************************************/		
	
	
/************************************************************************************************************
 ***************************************************以下为Model（模型）处理******************************************
 ************************************************************************************************************/	
	
	/**
	 * 初始化Model对象
	 * @return Model
	 *         返回new Model对象
	 * @throws Exception
	 */
	public Model findNewModel() throws Exception {
		return this.repositoryService.newModel();
	}
	/**
	 * 得到Model集合
	 * @return List<Model>
	 *         返回model集合
	 * @throws Exception
	 */
	public List<Model> findProcessModelList() throws Exception{
		List<Model> mdlist = this.repositoryService.createModelQuery()
				                                   .orderByCreateTime().asc()
				                                   .orderByModelVersion().asc()
				                                   .list();
		return mdlist;
	}
	
	
	/**
	 * 把流程定义转换为模型
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @throws Exception
	 */
	public void saveConvertToModel(String processDefinitionId) throws Exception{
		//查询流程定义
		ProcessDefinition processDefinition = this.repositoryService.getProcessDefinition(processDefinitionId);	
		//读取流程定义的XMLl输入流
		InputStream bpmnStream = this.repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(),
				processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream,"UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel =  new BpmnXMLConverter().convertToBpmnModel(xtr);
		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modeNode = converter.convertToJson(bpmnModel);
		Model modelData = this.repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getResourceName());
		modelData.setCategory(processDefinition.getDeploymentId());
		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION,1);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());
		//保存模型并设置模型属性
		this.repositoryService.saveModel(modelData);
		this.repositoryService.addModelEditorSource(modelData.getId(), modeNode.toString().getBytes("UTF-8"));
	}
	
	
	/**
	 * 创建新的Model模型
	 * 
	 * @param model
	 *            Model对象
	 * @return Model 
	 *           返回保存后的Model对象
	 * @throws Exception
	 */
	public Model saveCreateNewModel(Model model) throws Exception {
		//主要用到model中的name,metaInfo,key
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, model.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		
		String description = StringUtils.defaultString(model.getMetaInfo());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,description);
		
		model.setMetaInfo(modelObjectNode.toString());
		model.setName(model.getName());
		model.setKey(StringUtils.defaultString(model.getKey()));
       
		this.repositoryService.saveModel(model);
	    this.repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
	    return model;
	}
	
	/**
	 * 根据Model部署流程
	 * 
	 * @param modelId
	 *            模型ID
	 * @return Deployment 
	 *            部署对象
	 * @throws Exception
	 */
	public Deployment saveDeployProcess(String modelId) throws Exception {
		//根据modelId得到Model模型
		Model model = repositoryService.getModel(modelId);
		ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
		byte[] bpmnBytes = null;
		BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
		String processName = model.getName() + ".bpmn20.xml";
		InputStream is = new ByteArrayInputStream(bpmnBytes);
		Deployment deployment = repositoryService.createDeployment()
				                                 .addInputStream(processName, is)
				                                 .name(model.getName())
				                                 .deploy();
		return deployment;
	}

	/**
	 * 删除Model模型
	 * 
	 * @param modelId
	 *            模型ID
	 * @throws Exception
	 */
	public void deleteModel(String modelId) throws Exception {
		this.repositoryService.deleteModel(modelId);
	}

	/**
	 * 得到BpmnModel模型的输入流
	 * 
	 * @param modelId
	 *            模型ID
	 * @return ByteArrayInputStream 
	 *            输入流
	 * @throws Exception
	 */
	public ByteArrayInputStream findBpmnModelByteArrayInputStream(String modelId)
			throws Exception {
		Model model = repositoryService.getModel(modelId);
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		JsonNode editorNode = new ObjectMapper().readTree(repositoryService
				.getModelEditorSource(model.getId()));
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
		ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
		return in;
	}

	/**
	 * 得到model的当前流程图
	 * 
	 * @param taskId
	 *            任务ID
	 * @return InputStream 
	 *            输入流
	 * @throws Exception
	 */
	public InputStream findviewCurrentImageBymodel(String taskId)
			throws Exception {
		  List<String> list = new ArrayList<String>();
		  Task task=this.findTaskById(taskId);
		  String deploymentId = task.getProcessDefinitionId();
		  BpmnModel bpmnModel=this.repositoryService.getBpmnModel(deploymentId);
		  List<Execution> executionList=this.processEngine.getRuntimeService()
				                                  .createExecutionQuery()
				                                  .processInstanceId(task.getProcessInstanceId())
				                                  .list();
		
		for (Execution execution : executionList) {
			ActivityImpl activityImpl = this.findActivitiImpl(taskId,execution.getActivityId());
			if ("userTask".equals(activityImpl.getProperty("type"))) {
				list.add(activityImpl.getId());
			}
		}
		ProcessEngineConfigurationImpl processEngineConfigurationImpl = ((ProcessEngineImpl) this.processEngine)
				.getProcessEngineConfiguration();
		Context.setProcessEngineConfiguration(processEngineConfigurationImpl);
		InputStream in = ProcessDiagramGenerator.generateDiagram(bpmnModel,"png", list);
		return in;
	}
/************************************************************************************************************
 ***************************************************以上为Model处理******************************************
 ************************************************************************************************************/
	
/************************************************************************************************************
 ***************************************************以下为为from(表单)处理***************************************
 ************************************************************************************************************/	
	
	/**
	 * 根据任务Id获取TaskFormData对象
	 * 
	 * @param taskId
	 *            任务ID
	 * @return TaskFormData
	 */
	public TaskFormData getTaskFormDataByTaskId(String taskId) throws Exception{
		return this.getFormService().getTaskFormData(taskId);
	}
	

	/**
	 * 根据任务Id获取renderedTaskForm对象(读取上传的.form文件)
	 * 
	 * @param TaskId
	 *            任务ID
	 * @return Object
	 * 
	 * @throws Exception
	 */
	public Object getRenderedTaskFormByTaskId(String TaskId) throws Exception {
	    return this.formService.getRenderedTaskForm(TaskId);
	}
	
	/**
	 * 根据流程定义ID,流程名称获取开始节点的renderedTaskForm对象(读取上传的.form文件)
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @param formEngineName 
	 *            FormEngine名称（FormEngine formEngine = new JuelFormEngine();
		                         formEngine.getName();）
	 * @return
	 * @throws Exception
	 */
	public Object getRenderedStartFormByproDefId(String processDefinitionId,
			String formEngineName) throws Exception {
		if(StringUtils.isEmpty(formEngineName)){
			return this.formService.getRenderedStartForm(processDefinitionId);
		}
		return this.formService.getRenderedStartForm(processDefinitionId, formEngineName);
	}
	
	/**
	 * 保存任务表单数据
	 * 
	 * @param taskId
	 *            任务ID
	 * @param variables
	 *            Map<String,Object> key:属性名称 value:属性值
	 * @throws Exception
	 */
	public void SaveTaskFormData(String taskId, Map<String, String> variables)
			throws Exception {
		this.formService.submitTaskFormData(taskId, variables);
	}
	
/************************************************************************************************************
 ***************************************************以上为为from(表单)处理***************************************
 ************************************************************************************************************/	
	
/************************************************************************************************************
 ***************************************************以下为为comment(批注)处理***************************************
 ************************************************************************************************************/		
	
	/**
	 * 保存批注信息（activiti自带 act_hi_comment）
	 * 
	 * @param taskId
	 *            任务Id
	 * @param processInstance
	 *            流程实例ID
	 * @param message
	 *            批注信息
	 * @throws Exception
	 */
	public void SaveComment(String taskId, String processInstance,
			String message) throws Exception {
	      this.taskService.addComment(taskId, processInstance, message);
	}
	
	/**
	 * 根据流程实例Id查询出实例所有批注集合
	 * 
	 * @param ProcessInstanceId
	 *            流程实例Id
	 * @return List<Comment>
	 * @throws Exception
	 */
	public List<Comment> findProcessInstanceCommentsByProInsId(
			String ProcessInstanceId) throws Exception {
		return this.taskService.getProcessInstanceComments(ProcessInstanceId);
	}
	
	/**
	 * 根据任务Id查询出任务的批注集合
	 * 
	 * @param taskId
	 *            任务Id
	 * @return List<Comment>
	 * @throws Exception
	 */
	public List<Comment> findTaskCommentsByTaskId(String taskId)
			throws Exception {
		return this.taskService.getTaskComments(taskId);
	}

/************************************************************************************************************
 ***************************************************以上为为comment(批注)处理***************************************
 ************************************************************************************************************/	
	
	
	
/************************************************************************************************************
 ******************************************以下为工作流操作核心逻**************************************************
 ************************************************************************************************************/     
		
	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId)
			throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (activityId==null || "".equals( activityId.trim()) ) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
				.findActivity(activityId);

		return activityImpl;
	}
	
	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
			String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(findTaskById(taskId)
				.getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}
	


	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 *        流程实例ID
	 * @param key
	 *       任务key值
	 * @return
	 */
	private List<Task> findTaskListByKey(String processInstanceId, String key) {
		return taskService.createTaskQuery()
				          .processInstanceId(processInstanceId)
				          .taskDefinitionKey(key)
				          .list();
	}
	
    /**
     * 根据流程实例ID查找正在执行的任务列表
     * @param processInstanceId
     *        流程实例ID
     * @return List<Execution>
     *         正在执行的任务列表
     */
	private List<Execution> findExecutionTaskByProcessInstanceId(String processInstanceId){
		return this.runtimeService.createExecutionQuery()
                                  .processInstanceId(processInstanceId)
                                  .list();
	}
	
	
/************************************************************************************************************
 ******************************************以上为工作流操作核心逻**************************************************
 ************************************************************************************************************/ 

/************************************************************************************************************
 ******************************************以下为流程转向操作核心逻辑**********************************************
 ************************************************************************************************************/	
	
	/**
	 * 根据任务ID获取对应的流程实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessInstance findProcessInstanceByTaskId(String taskId)
			throws Exception {
		// 找到流程实例
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(findTaskById(taskId).getProcessInstanceId())
				.singleResult();
		if (processInstance == null) {
			throw new Exception("流程实例未找到!");
		}
		return processInstance;
	}
	
	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId,
			Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}
	
	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	} 
	
	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	private void restoreTransition(ActivityImpl activityImpl,
			List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
	
/************************************************************************************************************
 ******************************************以上为流程转向操作核心逻辑**********************************************
 ************************************************************************************************************/		
	
	
/************************************************************************************************************
 ******************************************以下为查询流程驳回节点核心逻辑******************************************
 ************************************************************************************************************/
	
	/**
	 * 迭代循环流程树结构，查询当前节点可驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储回退节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 回退节点集合
	 */
	private List<ActivityImpl> iteratorBackActivity(String taskId,
			ActivityImpl currActivity, List<ActivityImpl> rtnList,
			List<ActivityImpl> tempList) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);

		// 当前节点的流入来源
		List<PvmTransition> incomingTransitions = currActivity
				.getIncomingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			ActivityImpl activityImpl = transitionImpl.getSource();
			String type = (String) activityImpl.getProperty("type");
			/**
			 * 并行节点配置要求：<br>
			 * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
			 */
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId
						.lastIndexOf("_") + 1);
				if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
					return rtnList;
				} else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
					parallelGateways.add(activityImpl);
				}
			} else if ("startEvent".equals(type)) {// 开始节点，停止递归
				return rtnList;
			} else if ("userTask".equals(type)) {// 用户任务
				tempList.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
				currActivity = transitionImpl.getSource();
				exclusiveGateways.add(currActivity);
			}
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : parallelGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 根据同级userTask集合，过滤最近发生的节点
		 */
		currActivity = filterNewestActivity(processInstance, tempList);
		if (currActivity != null) {
			// 查询当前节点的流向是否为并行终点，并获取并行起点ID
			String id = findParallelGatewayId(currActivity);
			if (id==null || "".equals(id.trim())) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
				rtnList.add(currActivity);
			} else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
				currActivity = findActivitiImpl(taskId, id);
			}

			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorBackActivity(taskId, currActivity, rtnList, tempList);
		}
		return rtnList;
	}

	/**
	 * 反向排序list集合，便于驳回节点按顺序显示
	 * 
	 * @param list
	 * @return
	 */
	private List<ActivityImpl> reverList(List<ActivityImpl> list) {
		List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();
		// 由于迭代出现重复数据，排除重复
		for (int i = list.size(); i > 0; i--) {
			if (!rtnList.contains(list.get(i - 1)))
				rtnList.add(list.get(i - 1));
		}
		return rtnList;
	}

	/**
	 * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
	 * 
	 * @param activityImpl
	 *            当前节点
	 * @return
	 */
	private String findParallelGatewayId(ActivityImpl activityImpl) {
		List<PvmTransition> incomingTransitions = activityImpl
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId
						.lastIndexOf("_") + 1);
				if ("END".equals(gatewayType.toUpperCase())) {
					return gatewayId.substring(0, gatewayId.lastIndexOf("_"))
							+ "_start";
				}
			}
		}
		return null;
	}

	/**
	 * 根据流入任务集合，查询最近一次的流入任务节点
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param tempList
	 *            流入任务集合
	 * @return
	 */
	private ActivityImpl filterNewestActivity(ProcessInstance processInstance,
			List<ActivityImpl> tempList) {
		while (tempList.size() > 0) {
			ActivityImpl activity_1 = tempList.get(0);
			HistoricActivityInstance activityInstance_1 = findHistoricUserTask(
					processInstance, activity_1.getId());
			if (activityInstance_1 == null) {
				tempList.remove(activity_1);
				continue;
			}

			if (tempList.size() > 1) {
				ActivityImpl activity_2 = tempList.get(1);
				HistoricActivityInstance activityInstance_2 = findHistoricUserTask(
						processInstance, activity_2.getId());
				if (activityInstance_2 == null) {
					tempList.remove(activity_2);
					continue;
				}

				if (activityInstance_1.getEndTime().before(
						activityInstance_2.getEndTime())) {
					tempList.remove(activity_1);
				} else {
					tempList.remove(activity_2);
				}
			} else {
				break;
			}
		}
		if (tempList.size() > 0) {
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 查询指定任务节点的最新记录
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param activityId
	 * @return
	 */
	private HistoricActivityInstance findHistoricUserTask(
			ProcessInstance processInstance, String activityId) {
		HistoricActivityInstance rtnVal = null;
		// 查询当前流程实例审批结束的历史节点
		List<HistoricActivityInstance> historicActivityInstances = historyService
				.createHistoricActivityInstanceQuery().activityType("userTask")
				.processInstanceId(processInstance.getId())
				.activityId(activityId).finished()
				.orderByHistoricActivityInstanceEndTime().desc().list();
		if (historicActivityInstances.size() > 0) {
			rtnVal = historicActivityInstances.get(0);
		}

		return rtnVal;
	}
	
/************************************************************************************************************
 ******************************************以上为查询流程驳回节点核心逻辑******************************************
 ************************************************************************************************************/

	
	
/************************************************************************************************************
 ******************************************以下Spring 注入Activiti Service*************************************
 ************************************************************************************************************/	
	
	/**
	 * 流程引擎的抽象，通过它我们可以获得我们需要的一切服务。
	 */
	private ProcessEngine processEngine;
	/**
	 * Activiti中每一个不同版本的业务流程的定义都需要使用一些定义文件，部署文件和支持数据(例如BPMN2.0XML文件，
	 * 表单定义文件，流程定义图像文件等) 这些文件都存储在Activiti内建的Repository中。RepositoryService提供了对
	 * repository的存取服务。
	 */
	private RepositoryService repositoryService;
	/**
	 * 在Activiti中，每当一个流程定义被启动一次之后，都会生成一个相应的流程对象实例。RuntimeService提供了启动流程、查询流程实例、
	 * 设置获取流程实例变量等功能。此外它还提供了对流程部署，流程定义和流程实例的存取服务。
	 */
	private RuntimeService runtimeService;
	/**
	 * 在Activiti中业务流程定义中的每一个执行节点被称为一个Task，对流程中的数据存取，状态变更等操作均需要在Task中完成。
	 * TaskService提供了对用户Task 和Form相关的操作。它提供了运行时任务查询、领取、完成、删除以及变量设置等功能。
	 */
	private TaskService taskService;
	/**
	 * HistoryService用于获取正在运行或已经完成的流程实例的信息，与RuntimeService中获取的流程信息不同，
	 * 历史信息包含已经持久化存储的永久信息，并已经被针对查询优化。
	 */
	private HistoryService historyService;
	/**
	 * ManagementService提供了对Activiti流程引擎的管理和维护功能，这些功能不在工作流驱动的应用程序中使用，
	 * 主要用于Activiti系统的日常维护。
	 */
	private ManagementService managementService;
	/**
	 * Activiti中内置了用户以及组管理的功能，必须使用这些用户和组的信息才能获取到相应的Task。
	 * IdentityService提供了对Activiti 系统中的用户和组的管理功能。
	 */
	private IdentityService identityService;
	/**
	 * FormService: Activiti 中的流程和状态 Task 均可以关联业务相关的数据。通过使用 Form Service
	 * 可以存取启动和完成任务所需的表单数据并且根据需要来渲染表单。
	 */
	private FormService formService;

/************************************************************************************************************
 ******************************************以上Spring 注入Activiti Service*************************************
 ************************************************************************************************************/	
	
	
/************************************************************************************************************
 ******************************************以下 GET Set*******************************************************
 ************************************************************************************************************/
	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public RuntimeService getRuntimeService() {
		return runtimeService;
	}
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public HistoryService getHistoryService() {
		return historyService;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	public ManagementService getManagementService() {
		return managementService;
	}
	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}
	public IdentityService getIdentityService() {
		return identityService;
	}
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	public FormService getFormService() {
		return formService;
	}
	public void setFormService(FormService formService) {
		this.formService = formService;
	}

/************************************************************************************************************
 ******************************************以上 GET Set*******************************************************
 ************************************************************************************************************/	
}
