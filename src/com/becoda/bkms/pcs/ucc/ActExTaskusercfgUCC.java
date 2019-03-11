package com.becoda.bkms.pcs.ucc;

import java.util.List;
import java.util.Map;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ActExTaskusercfgUCC
 * @描述：TODO(工作流人员配置UCC)
 * @创建人： 张晓亮
 * @创建时间：2016年1月21日 上午9:56:02
 * @修改人：张晓亮
 * @修改时间：2016年1月21日 上午9:56:02
 * @修改备注：
 */
public interface ActExTaskusercfgUCC {
	
	/**
	 * 
	 * @方法名称: getActExTaskurgBytaskKey
	 * @描述：TODO(查询任务节点下的用户) 
	 * @返回值类型： List<String>  
	 * @param taskKey 任务定义KEY
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryActExTaskurgBytaskKey(String taskKey) throws Exception;
}
