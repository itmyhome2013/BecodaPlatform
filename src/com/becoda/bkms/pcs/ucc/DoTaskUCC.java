package com.becoda.bkms.pcs.ucc;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：DoTaskUCC
 * @描述：TODO(待办理任务UCC)
 * @创建人： 张晓亮
 * @创建时间：2016年1月18日 下午3:29:57
 * @修改人：张晓亮
 * @修改时间：2016年1月18日 下午3:29:57
 * @修改备注：
 */
public interface DoTaskUCC extends ProcessCoreUCC{
	
	/**
	 * 
	 * @方法名称: queryDoTaskPageList
	 * @描述：TODO(查询当前用户代办任务)
	 * @返回值类型： List<Map<String,String>>
	 * @param vo
	 *            分页对象
	 * @param user
	 *            登录对象
	 * @param processName
	 *            流程名称
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryDoTaskPageList(PageVO vo,User user,String processName) throws Exception;
	
	/**
	 * 
	 * @方法名称: queryDoTaskfillFormList
	 * @描述：TODO(当前任务可填表单)
	 * @返回值类型： List<Map<String,String>>
	 * @param taskKey
	 *            任务节点KEY
	 * @param processId
	 *            流程ID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryDoTaskfillFormList(String taskKey,String processId) throws Exception;
	
	/**
	 * 
	 * @方法名称: queryDoTaskfillFormList
	 * @描述：TODO(当前任务可填表单)
	 * @返回值类型： List<Map<String,String>>
	 * @param processId
	 *            流程ID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryCompletedFormList(String processId) throws Exception;
	
	
	
}
