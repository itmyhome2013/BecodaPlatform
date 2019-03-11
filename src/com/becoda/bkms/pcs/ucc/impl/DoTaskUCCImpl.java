package com.becoda.bkms.pcs.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.service.DoTaskService;
import com.becoda.bkms.pcs.ucc.DoTaskUCC;
import com.becoda.bkms.pcs.ucc.ProcessCoreUCC;
import com.becoda.bkms.util.Tools;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：DoTaskUCCImpl
 * @描述：TODO(待办理UCC实现层)
 * @创建人： 张晓亮
 * @创建时间：2016年1月19日 上午10:48:20
 * @修改人：张晓亮
 * @修改时间：2016年1月19日 上午10:48:20
 * @修改备注：
 */
public class DoTaskUCCImpl extends ProcessCoreUCCImpl implements DoTaskUCC{
	private DoTaskService doTaskService;
    
	
	public List<Map<String, String>> queryDoTaskPageList(PageVO vo,User user,String processName) throws BkmsException {
		StringBuffer whereSQL= new StringBuffer(" where 1=1 ");
		StringBuffer querySQL= new StringBuffer(" select ");
					 querySQL.append(" B.ID_ as TASKID,TASK_DEF_KEY_ AS TASKDEFKEY, NAME_ as TASKNAME,PROCESSID ,name_ as CURRENTTASKNAME,PROCESSNAME,CREATEUSERNAME,CREATEDATE,PROCDEFNAME,URGENCY,COMMENTS ");
					 querySQL.append(" from act_ru_execution a left join act_ru_task b on a.id_ = b.execution_id_ left join act_ex_process c on a.business_key_ = c.processid ");
		StringBuffer countSQL= new StringBuffer(" select count(a.id_)  from act_ru_execution a left join act_ru_task b on a.id_ = b.execution_id_ left join act_ex_process c on a.business_key_ = c.processid  ");
		if(!Tools.stringIsNull(Tools.filterNull(processName))){
	    	//条件
			whereSQL.append(" and C.PROCESSNAME like '%"+processName+"%' ");
	     }
		if(!Tools.stringIsNull(Tools.filterNull(user.getUserId()))){
	    	//条件
			whereSQL.append(" and B.ASSIGNEE_ = '"+user.getUserId()+"' ");
	     }else{
	    	 throw new RuntimeException("用户ID为空");
	     }
		   querySQL.append(whereSQL);
		   countSQL.append(whereSQL);
	        //排序
    	    querySQL.append("order by C.URGENCY desc");
		 return this.doTaskService.queryPageListBySql(vo, countSQL.toString(), querySQL.toString());
	}

	
	
	public List<Map<String,String>> queryDoTaskfillFormList(String taskKey,String processId) throws Exception{
		StringBuffer querySQL= new StringBuffer(" select ");
					 querySQL.append(" A.PCSFROMCFGID as PCSFROMCFGID, A.TASKKEY as TASKKEY, A.TASKNAME as TASKNAME,A.FORMNAME as FORMNAME, A.FROMTYPE as FROMTYPE,A.ISREQUIRED as ISREQUIRED,A.FROMTABLENAME as FROMTABLENAME,A.FORMURL as FORMURL,A.ISDISABLE as ISDISABLE,B.DATAID as DATAID,B.COMPLETEDFORMID as COMPLETEDFORMID ");
					 querySQL.append(" from act_ex_pcsfromcfg a left join act_ex_completedform b on a.pcsfromcfgid = b.pcsfromcfgid and b.processid = '"+processId+"' ");
					 querySQL.append(" where  A.ISDISABLE='0' ");
		 //条件
		 if(!Tools.stringIsNull(Tools.filterNull(taskKey))){querySQL.append(" and A.TASKKEY = '"+taskKey+"' ");}else{throw new RuntimeException("taskKey为空");}
		  return this.doTaskService.queryListBySql(querySQL.toString());
	}
	
	
	public List<Map<String,String>> queryCompletedFormList(String processId) throws Exception{
		StringBuffer querySQL= new StringBuffer(" select ");
					 querySQL.append(" A.PCSFROMCFGID as PCSFROMCFGID, A.TASKKEY as TASKKEY, A.TASKNAME as TASKNAME,A.FORMNAME as FORMNAME, A.FROMTYPE as FROMTYPE,A.ISREQUIRED as ISREQUIRED,A.FROMTABLENAME as FROMTABLENAME,A.FORMURL as FORMURL,A.ISDISABLE as ISDISABLE,B.DATAID as DATAID ");
					 querySQL.append(" from act_ex_pcsfromcfg a right join act_ex_completedform b on a.pcsfromcfgid = b.pcsfromcfgid ");
					 querySQL.append(" where  1=1 ");
		//条件
		if(!Tools.stringIsNull(Tools.filterNull(processId))){querySQL.append(" and B.PROCESSID = '"+processId+"' ");}else{throw new RuntimeException("processId为空");}
		
		//排序
	    querySQL.append("order by B.INFORDATE desc");
		return this.doTaskService.queryListBySql(querySQL.toString());
	}
	
	
    //get and set
	public DoTaskService getDoTaskService() {
		return doTaskService;
	}


	public void setDoTaskService(DoTaskService doTaskService) {
		this.doTaskService = doTaskService;
	}

	
	
}
