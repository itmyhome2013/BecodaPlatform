package com.becoda.bkms.pcs.ucc;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：DoClaimUCC
 * @描述：TODO(待签收UCC)
 * @创建人： 张晓亮
 * @创建时间：2016年1月19日 上午10:47:20
 * @修改人：张晓亮
 * @修改时间：2016年1月19日 上午10:47:20
 * @修改备注：
 */
public interface DoClaimUCC extends ProcessCoreUCC{
	/**
	 * 
	 * @方法名称: queryDoClaimPageList
	 * @描述：TODO(查询当前用户待签收任务) 
	 * @返回值类型： List<Map<String,String>>  
	 * @param vo
	 * @param user
	 * @param processName 流程名称
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryDoClaimPageList(PageVO vo,User user,String processName) throws BkmsException;
	
	/**
	 * 
	 * @方法名称: SaveTaskClaim
	 * @描述：TODO(签收任务) 
	 * @返回值类型： void  
	 * @param taskId
	 * @param user
	 * @throws BkmsException
	 */
	public void SaveTaskClaim(String taskId,User user)throws BkmsException;

}
