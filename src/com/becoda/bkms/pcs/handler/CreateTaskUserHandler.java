package com.becoda.bkms.pcs.handler;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.pcs.ucc.ActExTaskusercfgUCC;
import com.becoda.bkms.util.BkmsContext;

/**
 * 
*    
* 项目名称：pcsnew   
* 类名称：ManagerTaskHandler   
* 类描述：   根据节点动态分配节点人员
* 创建人：张晓亮   
* 创建时间：2015年7月2日 上午10:36:11   
* 修改人：张晓亮   
* 修改时间：2015年7月2日 上午10:36:11   
* 修改备注：   
* @version    
*
 */
@SuppressWarnings("serial")
public class CreateTaskUserHandler implements TaskListener {
	
	public void notify(DelegateTask delegateTask) {
		try {
		    String taskKey=delegateTask.getTaskDefinitionKey();
		    ActExTaskusercfgUCC actExTaskusercfgUCC = (ActExTaskusercfgUCC) BkmsContext.getBean("pcs_actExTaskusercfgUCC");
		    List<Map<String, String>> userList = actExTaskusercfgUCC.queryActExTaskurgBytaskKey(taskKey);
		    for(Map<String, String> user:userList){
		    	 delegateTask.addCandidateUser(user.get("USERID"));
		    }
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
