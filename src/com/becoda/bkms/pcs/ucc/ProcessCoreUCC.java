package com.becoda.bkms.pcs.ucc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.FormEngine;
import org.activiti.engine.impl.form.JuelFormEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ProcessCoreUCC
 * @描述：TODO(流程操作核心类接口 )
 * @创建人： 张晓亮
 * @创建时间：2016年1月19日 下午1:43:52
 * @修改人：张晓亮
 * @修改时间：2016年1月19日 下午1:43:52
 * @修改备注：
 */
public interface ProcessCoreUCC  {
	/**
	 * 部署流程定义
	 * 
	 * @param deployProcessZip
	 *        部署流程文件(Zip格式)
	 * @param Processname
	 *        部署流程名称
	 * @throws Exception
	 */
	void saveDeployProcess(File deployProcessZip, String Processname) throws Exception;

	/**
	 * 查询部署对象信息
	 * 
	 * @return List<Deployment>
	 *         部署对象信息集合
	 * @throws Exception
	 */
	List<Deployment> findDeploymentList() throws Exception;
	
    /**
     * 激活流程实例(定时任务)
     * 
     * @param  processDefinitionId
     *        流程定义ID
     * @param activateProcessInstances
     *        是否激活流程实例
     * @param activationDate
     *        激活日期(时间到自动激活)
     * @throws Exception
     */
	public void saveActivateProcessDefinitionById(String processDefinitionId,
			boolean activateProcessInstances, Date activationDate)throws Exception;
	
	/**
	 * 激活流程实例
	 * @param processDefinitionId
	 *        流程定义ID
	 * @throws Exception
	 */
	public void saveActivateProcessDefinitionById(String processDefinitionId)throws Exception;
	
	/**
     * 激活流程实例(定时任务)
     * 
     * @param  processDefinitionKey
     *        流程定义Key
     * @param activateProcessInstances
     *        是否激活流程实例
     * @param activationDate
     *        激活日期(时间到自动激活)
     * @throws Exception
     */
	public void saveActivateProcessDefinitionByKey(String processDefinitionKey,
			boolean activateProcessInstances, Date activationDate)throws Exception;
	
	/**
	 * 激活流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义Key
	 * @throws Exception
	 */
	public void saveActivateProcessDefinitionByKey(String processDefinitionKey)throws Exception;
	
	/**
	 * 挂起流程实例(定时任务)
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @param suspendProcessInstances
	 *            是否挂起流程实例
	 * @param suspensionDate
	 *            挂起日期(时间到自动挂起)
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionById(String processDefinitionId,
			boolean suspendProcessInstances, Date suspensionDate)
			throws Exception;
	
	/**
	 * 挂起流程实例
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionById(String processDefinitionId) throws Exception;
	
	
	/**
	 * 挂起流程实例(定时任务)
	 * 
	 * @param processDefinitionKey
	 *            流程定义Key
	 * @param suspendProcessInstances
	 *            是否挂起流程实例
	 * @param suspensionDate
	 *            挂起日期(时间到自动挂起)
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionByKey(String processDefinitionKey,
			boolean suspendProcessInstances, Date suspensionDate)
			throws Exception;
	
	/**
	 * 挂起流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义Key
	 * @throws Exception
	 */
	public void saveSuspendProcessDefinitionByKey(String processDefinitionKey)	throws Exception;
	

	/**
	 * 查询流程定义的信息
	 * 
	 * @return List<ProcessDefinition>
	 *         流程定义的信息集合
	 * @throws Exception
	 */
	List<ProcessDefinition> findProcessDefinitionList() throws Exception;

	/**
	 * 级联删除流程定义
	 * 
	 * @param deploymentIds
	 *            部署对象ID
	 * @throws Exception
	 */
	void deleteDeploymentByIds(String[] deploymentIds) throws Exception;

	/**
	 * 审批通过
	 * 
	 * @param taskId
	 *            任务ID
	 * @param variables
	 *            流程存储参数
	 * @throws Exception
	 * 
	 */
	 void savePassProcess(String taskId, Map<String, Object> variables) throws Exception;

	/**
	 * 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
	 * 
	 * @param processDefinitionKey
	 *            流程定义的key
	 * @param businessKey
	 * @param variables
	 * @return ProcessInstance
	 */
	ProcessInstance saveStartProcessInstanceByKey(String processDefinitionKey,
			String businessKey, Map<String, Object> variables)throws Exception;
	
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
			String processDefinitionId,String businessKey, Map<String, String> variables) throws Exception;
			
			
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
    void saveBackProcess(String taskId, String activityId,  Map<String, Object> variables) throws Exception;

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
	void saveCommitProcess(String taskId, Map<String, Object> variables,String activityId) throws Exception;

	/**
	 * 取回流程
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            取回节点ID
	 * @throws Exception
	 */
	void saveCallBackProcess(String taskId, String activityId)  throws Exception;

	/**
	 * 中止流程(特权人直接审批通过等)
	 * 
	 * @param taskId
	 *            任务ID
	 * @throws Exception
	 */
	void saveEndProcess(String taskId) throws Exception;
	/** 
     * 转办流程 
     *  
     * @param taskId 
     *            当前任务节点ID 
     * @param userCode 
     *            被转办人Code 
     * @throws Exception
     */  
	void saveTransferAssignee(String taskId, String userCode)throws Exception;

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 *        当前任务节点ID
	 * @param userCode
	 *        签收人Code
	 *        当userCode为空时表未反签收（还原到未签收状态）
	 * @throws Exception
	 */
	void saveTaskclaim(String taskId,String userCode) throws Exception;

	/**
	 * 委托流程
	 * 
	 * @param taskId
	 *            当前任务节点ID
	 * @param userCode
	 *            被委托人Code
	 * @throws Exception
	 */
	void saveDelegateTask(String taskId, String userCode) throws Exception;
	
	

	/**
	 * 根据当前任务ID，查询可以驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @throws Exception
	 */
	List<ActivityImpl> findBackAvtivity(String taskId) throws Exception;
	
	
	/**
	 * 查询（用户）的任务列表，获取当前任务的集合
	 * 
	 * @param userCode
	 *            办理人Code
	 * @return List<Task> 
	 *            任务集合
	 * @throws Exception
	 */
	List<Task> findTaskListByAssignee(String userCode) throws Exception;

	/**
	 * 查询（用户组）任务列表，获取当前任务的集合
	 * 
	 * @param userCode
	 *        办理人Code
	 * @return List<Task> 
	 *         任务集合
	 * @throws Exception
	 */
	List<Task> findTaskListByCandidateUser(String userCode) throws Exception;
	
	/**
	 * 查询（角色组）任务列表，获取当前任务的集合
	 * 必须为activiti自带的角色表
	 * @param userCode
	 *        办理人Code
	 * @return List<Task> 
	 *         任务集合
	 * @throws Exception
	 */
	List<Task> findTaskListByCandidateGroup(String userCode) throws Exception;
	
	/**
	 * 查询所有的任务列表
	 * Assignee(指定人)+CandidateUser(用户组)+CandidateGroup(角色组)
	 * 
	 * @param userCode
	 *        办理人Code
	 * @return List<Task> 
	 *         任务集合
	 * @throws Exception
	 */
	List<Task> findTaskListAll(String userCode) throws Exception;
	
	/**
	 * 使用任务ID，查找业务ID
	 * 
	 * @param taskId
	 *            任务ID
	 * @return businessKey 
	 *         业务ID
	 * @throws Exception
	 */
	String findBusinessIdByTaskId(String taskId) throws Exception;

	/**
	 * 使用部署对象ID和资源图片名称，获取图片的输入流
	 * 
	 * @param deploymentId
	 *            部署ID
	 * @param imageName
	 *            图片名称
	 * @return InputStream
	 *         流
	 * @throws Exception
	 */
	InputStream findImageInputStream(String deploymentId,String imageName)throws Exception;

	/**
	 * 查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>
	 * 中并把Map放入到List集合中
	 * 
	 * @param taskId
	 *            任务ID
	 * @return List<Map<String, Object>> 
	 *            当期活动对应的坐标x,y,width,height集合
	 * @throws Exception
	 */
	 List<Map<String, Object>> findCoordingByTask(String taskId) throws Exception;

	 
	/**
	 * 把流程定义转换为模型
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @throws Exception
	 */
	void saveConvertToModel(String processDefinitionId) throws Exception;
	 
	 
	/**
	 * 创建新的Model模型
	 * 
	 * @param model
	 *            Model对象
	 * @return Model 
	 *           返回保存后的Model对象
	 * @throws Exception
	 */
	 Model saveCreateNewModel(Model model) throws Exception;

	/**
	 * 根据Model部署流程
	 * 
	 * @param modelId
	 *            模型ID
	 * @return Deployment 
	 *            部署对象
	 * @throws Exception
	 */
	 Deployment saveDeployProcess(String modelId) throws Exception;

	/**
	 * 删除Model模型
	 * 
	 * @param modelId
	 *            模型ID
	 * @throws Exception
	 */
	 void deleteModel(String modelId) throws Exception;

	/**
	 * 得到BpmnModel模型的输入流
	 * 
	 * @param modelId
	 *            模型ID
	 * @return ByteArrayInputStream 
	 *            输入流
	 * @throws Exception
	 */
	 ByteArrayInputStream findBpmnModelByteArrayInputStream(String modelId) throws Exception;

	/**
	 * 得到model的当前流程图
	 * 
	 * @param taskId
	 *         任务ID
	 * @return InputStream
	 *          输入流
	 * @throws Exception
	 */
	 InputStream findviewCurrentImageBymodel(String taskId) throws Exception;

	/**
	 * 得到Model集合
	 * @return List<Model>
	 *         返回model集合
	 * @throws Exception
	 */
	List<Model> findProcessModelList() throws Exception;
	/**
	 * 初始化Model对象
	 * @return Model
	 *         返回new Model对象
	 * @throws Exception
	 */
	Model findNewModel() throws Exception;
	
	
	
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
	void saveVariableByExecutionId(String executionId, String variableName, Object value) throws Exception;
	
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
	void saveVariablesByExecutionId(String executionId, Map<String,Object> variables) throws Exception;
	
	
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
	void saveVariableByTaskId(String taskId,String  variableName, Object value) throws Exception;
	
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
	void saveVariablesByTaskId(String taskId, Map<String,Object> variables) throws Exception;
	
	/**
	 * 使用任务ID，获取所有的流程变量，将流程变量放置到Map集合中，
	 * map集合的key就是流程变量的名称，map集合的value就是流程变量的值
	 * @param TaskId
	 *        任务ID
	 * @return Map<String,Object>
	 *         变量集合
	 * @throws Exception
	 */
	Map<String,Object> findVariablesByTaskId(String TaskId) throws Exception;
	
	
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
	Object findVariablesByExecutionIdAndVariableName(String executionId, String variableName) throws Exception;
	

	/**
	 * 使用执行对象ID，获取所有的流程变量，将流程变量放置到Map集合中
	 * map集合的key就是流程变量的名称，map集合的value就是流程变量的值
	 * @param executionId
	 *        执行ID
	 * @return Map<String,Object>
	 *         变量集合
	 * @throws Exception
	 */
	Map<String,Object> findVariablesByExecutionId(String executionId) throws Exception;
	
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
	Map<String,Object> findVariablesByExecutionIdAndVariableNames(String executionId, List<String> variableNames) throws Exception;
	
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
	Object findVariableBytaskIdAndVariableName(String taskId, String variableName) throws Exception;
	
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
	Map<String,Object> findVariablesByTaskIdAndVariableNames(String taskId, List<String> variableNames) throws Exception;
	
	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	TaskEntity findTaskById(String taskId) throws Exception;
	

	
	/**
	 * 根据流程实例ID获取对应的流程实例
	 * 可用来判断流程是否结束
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return
	 * @throws Exception
	 */
	ProcessInstance findProcessInstanceByProcessInstanceId(String processInstanceId) throws Exception;
	
	/**
	 * 根据任务ID查询当前流程图
	 * @param taskId
	 *        任务ID
	 * @return InputStream
	 *         图片输入流
	 * @throws Exception
	 */
	InputStream findviewCurrentImage(String taskId) throws Exception;
	
	/**
	 * 根据任务Id获取TaskFormData对象(Form Properties)
	 * 
	 * @param taskId
	 *            任务ID
	 * @return TaskFormData
	 */
	public TaskFormData getTaskFormDataByTaskId(String taskId) throws Exception;
	
	/**
	 * 根据任务Id获取renderedTaskForm对象(读取上传的.form文件)
	 * 
	 * @param TaskId
	 *            任务ID
	 * @return Object
	 * 
	 * @throws Exception
	 */
	public Object getRenderedTaskFormByTaskId(String TaskId) throws Exception;
	
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
	public Object getRenderedStartFormByproDefId(String processDefinitionId, String formEngineName) throws Exception;
	
	
	/**
	 * 保存任务表单数据
	 * 
	 * @param taskId
	 *            任务ID
	 * @param variables
	 *            Map<String,Object> key:属性名称 value:属性值
	 * @throws Exception
	 */
	public void SaveTaskFormData(String taskId, Map<String,String> variables) throws Exception;
	
	/**
	 * 保存批注信息（activiti自带 act_hi_comment）
	 * @param taskId
	 *        任务Id
	 * @param processInstance
	 *        流程实例ID
	 * @param message
	 *        批注信息
	 * @throws Exception
	 */
	public void SaveComment(String taskId, String processInstance, String message) throws Exception;
	
	/**
	 * 根据流程实例Id查询出实例所有批注集合
	 * 
	 * @param ProcessInstanceId
	 *            流程实例Id
	 * @return List<Comment>
	 * @throws Exception
	 */
	public List<Comment> findProcessInstanceCommentsByProInsId(String ProcessInstanceId) throws Exception;
	
	/**
	 * 根据任务Id查询出任务的批注集合
	 * 
	 * @param taskId
	 *            任务Id
	 * @return List<Comment>
	 * @throws Exception
	 */
	public List<Comment> findTaskCommentsByTaskId(String taskId) throws Exception;
	
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
	public List<PvmActivity> getOutPvmActivity(String taskId,String activityId) throws Exception;
	
	
	
	
	public ProcessEngine getProcessEngine();
	public RepositoryService getRepositoryService();
	public RuntimeService getRuntimeService();
	public TaskService getTaskService();
	public HistoryService getHistoryService();
	public ManagementService getManagementService();
	public IdentityService getIdentityService();
	public FormService getFormService();
	
}
