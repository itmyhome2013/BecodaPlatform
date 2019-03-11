package com.becoda.bkms.pcs.ucc.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.service.DoClaimService;
import com.becoda.bkms.pcs.ucc.DoClaimUCC;
import com.becoda.bkms.pcs.ucc.ProcessCoreUCC;
import com.becoda.bkms.util.Tools;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：DoClaimUCCImpl
 * @描述：TODO(待签收UCC实现层)
 * @创建人： 张晓亮
 * @创建时间：2016年1月19日 上午10:48:47
 * @修改人：张晓亮
 * @修改时间：2016年1月19日 上午10:48:47
 * @修改备注：
 */
public class DoClaimUCCImpl extends ProcessCoreUCCImpl implements DoClaimUCC{
	private DoClaimService doClaimService;
	public List<Map<String, String>> queryDoClaimPageList(PageVO vo,User user,String processName) throws BkmsException{
		StringBuffer querySQL= new StringBuffer(" select ");
		             querySQL.append(" B.ID_ as TASKID,TASK_DEF_KEY_ AS TASKDEFKEY,PROCESSID ,name_ as CURRENTTASKNAME,PROCESSNAME,CREATEUSERNAME,CREATEDATE,PROCDEFNAME,URGENCY,COMMENTS ");
		             querySQL.append(" from act_ru_execution a left join act_ru_task b on a.id_ = b.execution_id_ left join act_ex_process c  on a.business_key_ = c.processid left join ACT_RU_IDENTITYLINK d on d.task_id_ = b.id_  ");
		             querySQL.append(" where 1=1 ");
        StringBuffer countSQL= new StringBuffer(" select count(b.id_) from act_ru_execution a left join act_ru_task b on a.id_ = b.execution_id_ left join act_ex_process c  on a.business_key_ = c.processid left join ACT_RU_IDENTITYLINK d on d.task_id_ = b.id_ where 1=1");
		//条件
        querySQL.append(" and B.ASSIGNEE_ is null");
        countSQL.append(" and B.ASSIGNEE_ is null");
        if(!Tools.stringIsNull(Tools.filterNull(processName))){
	    	//条件
	    	querySQL.append(" and C.PROCESSNAME like '%"+processName+"%' ");
	    	countSQL.append(" and C.PROCESSNAME like '%"+processName+"%' ");
	     }
		if (!Tools.stringIsNull(Tools.filterNull(user.getUserId()))) {
			querySQL.append(" and D.USER_ID_ = '" + user.getUserId() + "' ");
			countSQL.append(" and D.USER_ID_ = '" + user.getUserId() + "' ");
		} else {
			throw new RuntimeException("user为空");
		}
        
        return this.doClaimService.queryPageListBySql(vo, countSQL.toString(), querySQL.toString());
	}
	
	public void SaveTaskClaim(String taskId,User user)throws BkmsException{
		if (Tools.stringIsNull(Tools.filterNull(user.getUserId()))) {
			throw new RuntimeException("user为空");
		}
		if (Tools.stringIsNull(Tools.filterNull(taskId))) {
			throw new RuntimeException("taskId为空");
		}
		saveTaskclaim(taskId, user.getUserId());
	}
	
	
	
	
    //get and set

	public DoClaimService getDoClaimService() {
		return doClaimService;
	}

	public void setDoClaimService(DoClaimService doClaimService) {
		this.doClaimService = doClaimService;
	}

	
	
}
