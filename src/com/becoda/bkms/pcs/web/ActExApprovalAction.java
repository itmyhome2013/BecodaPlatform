package com.becoda.bkms.pcs.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.pcs.pojo.bo.ActExApprovalBO;
import com.becoda.bkms.pcs.ucc.ActExApprovalUCC;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.pcs.util.UUIDGenerator;
import com.becoda.bkms.util.Endecode;
import com.becoda.bkms.util.Tools;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ActExApprovalAction
 * @描述：TODO(审批信息Action)
 * @创建人： 张晓亮
 * @创建时间：2016年1月21日 下午2:21:29
 * @修改人：张晓亮
 * @修改时间：2016年1月21日 下午2:21:29
 * @修改备注：
 */
public class ActExApprovalAction extends GenericPageAction{
	private ActExApprovalBO actExApproval;
	private String flag;
	private String message;
	
	/**
	 * 
	 * @方法名称: findApprovalList
	 * @描述：TODO(审批列表) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findApprovalList(){
		try {
		    String processId =Tools.filterNull(request.getParameter("processId"));
		    processId = Endecode.base64Decode(processId);
		    ActExApprovalUCC actExApprovalUCC = (ActExApprovalUCC) getBean("pcs_actExApprovalUCC");
		    vo.setPageSize(5);
		    List<Map<String, String>> approvalList = actExApprovalUCC.queryApprovalPageList(vo,processId);
		    request.setAttribute("approvalList", approvalList);
			} catch (Exception e) {
				 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
		         this.addActionError(he.getFlag()+he.getCause().getMessage());
			}
		return "approvalList";
	}
	
	
	/**
	 * 
	 * @方法名称: forSendApprovalInfoAdd
	 * @描述：TODO(返回审批页面) 
	 * @返回值类型： String  
	 * @return
	 */
	public String forSendApprovalInfoAdd(){
		try {
	    String taskId =Tools.filterNull(Endecode.base64Decode(actExApproval.getTaskid()));
		ActExApprovalUCC actExApprovalUCC = (ActExApprovalUCC) getBean("pcs_actExApprovalUCC");
		List<ActivityImpl> activityList = new ArrayList<ActivityImpl>();
	    activityList=actExApprovalUCC.findBackAvtivity(taskId);
	    Collections.reverse(activityList);
        request.setAttribute("activityList", activityList);
        request.setAttribute("actExApproval", actExApproval);
		} catch (Exception e) {
			 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	         this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return "approvalInfoAdd";
	}
	
	
	/**
	 * 
	 * @方法名称: saveApprovalandTaskcomplete
	 * @描述：TODO(保存事件审批信息并提交任务) 
	 * @返回值类型： String  
	 * @return
	 */
	public String saveApprovalandTaskcomplete(){
		try {
			actExApproval.setApprovalid(UUIDGenerator.getUUID());
			actExApproval.setApprovaldate(DateUtil.date2Str(DateUtil.datetimeFormat));
		    actExApproval.setApprovaluserid(user.getUserId());
		    actExApproval.setApprovalusername(user.getName());
		    ActExApprovalUCC actExApprovalUCC = (ActExApprovalUCC) getBean("pcs_actExApprovalUCC");
		    actExApprovalUCC.saveCommitProcess(actExApproval);
		    flag="true";
		    message="审批成功";
		} catch (Exception e) {
		   flag="false";
		   message=e.toString();
		}
		return this.SUCCESS;
	}

	//get and set
	public ActExApprovalBO getActExApproval() {
		return actExApproval;
	}

	public void setActExApproval(ActExApprovalBO actExApproval) {
		this.actExApproval = actExApproval;
	}


	

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
