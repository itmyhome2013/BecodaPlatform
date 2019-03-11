package com.becoda.bkms.pcs.ucc;

import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import com.becoda.bkms.pcs.pojo.bo.ActExProcessBO;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：StartProcessUCC
 * @描述：TODO(启动流程UCC)
 * @创建人： 张晓亮
 * @创建时间：2016年1月18日 下午1:35:11
 * @修改人：张晓亮
 * @修改时间：2016年1月18日 下午1:35:11
 * @修改备注：
 */
public interface StartProcessUCC extends ProcessCoreUCC{
	
	/**
	 * 
	 * @方法名称: queryProcDef
	 * @描述：TODO(流程定义列表-多个版本时只显示最后一个版本) 
	 * @返回值类型： List<ProcessDefinition>  
	 * @return 流程定义列表
	 */
	public List<ProcessDefinition> queryProcDef() throws Exception;
	
	/**
	 * 
	 * @方法名称: saveStartProcess
	 * @描述：TODO(保存启动流程)
	 * @返回值类型： ProcessInstance
	 * @param actExProcess
	 *            (ACT_EX_PROCESS业务流程表对象(
	 * @param businessKey
	 *            (业务ID)
	 * @param variables
	 *            (流程变量)
	 * @param procdefkey
	 *            (流程定义KEY)
	 * @return ProcessInstance 流程实例对象
	 */
	public ProcessInstance saveStartProcess(String procdefkey,ActExProcessBO actExProcess,String businessKey,Map<String,Object> variables) throws Exception;
	
	
	

}
