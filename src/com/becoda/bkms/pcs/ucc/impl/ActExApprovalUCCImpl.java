package com.becoda.bkms.pcs.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.pojo.bo.ActExApprovalBO;
import com.becoda.bkms.pcs.service.ActExApprovalService;
import com.becoda.bkms.pcs.ucc.ActExApprovalUCC;
import com.becoda.bkms.util.Tools;

public class ActExApprovalUCCImpl extends ProcessCoreUCCImpl implements ActExApprovalUCC{
    private ActExApprovalService actExApprovalService;
    
    
    public List<Map<String,String>> queryApprovalPageList(PageVO vo,String processId)throws Exception{
    	StringBuffer whereSQL= new StringBuffer(" where 1=1 ");
    	StringBuffer querySQL= new StringBuffer(" select ");
					 querySQL.append(" APPROVALID,PROCESSID,APPROVALUSERNAME,APPROVALDATE,APPROVALMESSAGE,APPROVALSTATE,SUBMITNODEKNAME,APPROVALTASKNAME ");
					 querySQL.append(" from ACT_EX_APPROVAL ");
		StringBuffer countSQL= new StringBuffer(" select count(APPROVALID)  from ACT_EX_APPROVAL ");			 
		 if(!Tools.stringIsNull(Tools.filterNull(processId))){
		    	//条件
			 whereSQL.append(" and PROCESSID = '"+processId+"' ");
		     }else{
		    	 throw new RuntimeException("processId为空");
		     }
		    querySQL.append(whereSQL);
		    countSQL.append(whereSQL);
		    //排序
 	        querySQL.append("order by APPROVALDATE desc");
    	return this.actExApprovalService.queryPageListBySql(vo, countSQL.toString(), querySQL.toString());
    }
    
    public void saveCommitProcess(ActExApprovalBO actExApproval)throws Exception{
    	//同意
		 if(actExApproval.getApprovalstate().equals("0")){
			 actExApproval.setSubmitnodekey(null);
			 actExApproval.setSubmitnodekname(null);
			 this.actExApprovalService.saveActExApproval(actExApproval);
			 saveCommitProcess(actExApproval.getTaskid(), null, null);
		 }else{
			 this.actExApprovalService.saveActExApproval(actExApproval);
			 saveCommitProcess(actExApproval.getTaskid(), null, actExApproval.getSubmitnodekey());
		 }
    }

    //get and set
	public ActExApprovalService getActExApprovalService() {
		return actExApprovalService;
	}

	public void setActExApprovalService(ActExApprovalService actExApprovalService) {
		this.actExApprovalService = actExApprovalService;
	}
    
    
    
    
}
