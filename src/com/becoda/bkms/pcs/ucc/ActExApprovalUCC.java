package com.becoda.bkms.pcs.ucc;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.pojo.bo.ActExApprovalBO;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ActExApprovalUCC
 * @描述：TODO(审批信息UCC)
 * @创建人： 张晓亮
 * @创建时间：2016年1月21日 下午2:18:44
 * @修改人：张晓亮
 * @修改时间：2016年1月21日 下午2:18:44
 * @修改备注：
 */
public interface ActExApprovalUCC extends ProcessCoreUCC{
	
	/**
	 * 
	 * @方法名称: queryApprovalPageList
	 * @描述：TODO(审批列表)
	 * @返回值类型： List<Map<String,String>>
	 * @param vo
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryApprovalPageList(PageVO vo,String processId)throws Exception;
	
	
   
	/**
	 * 
	 * @方法名称: saveCommitProcess
	 * @描述：TODO(保存事件审批信息并提交任务) 
	 * @返回值类型： void  
	 * @param actExApproval
	 * @throws Exception
	 */
	public void saveCommitProcess(ActExApprovalBO actExApproval)throws Exception;
}
