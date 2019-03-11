package com.becoda.bkms.pcs.ucc;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
 

/** @项目名称：BecodaPlatForm
   * @名称：IActCountUCC
   * @描述：主要完成统计功能、统计查询、我的案件、经办案件、已归档案件
   * @author kris
   * @Date 2016-1-22
   */

public interface IActCountUCC {

	/**
	 * 
	 * @方法名称: queryMyPcsPageList
	 * @描述 :查询我的上报案件
	 * @返回值类型： List<Map<String,String>>
	 * @param user
	 * @return
	 */
	public List<Map<String,String>> queryMyPcsPageList(PageVO vo,User user) throws Exception;
	
	/**
	 * 
	 * @方法名称: queryEndPcsPageList
	 * @描述 :查询我已归档的流程案件
	 * @返回值类型： List<Map<String,String>>
	 * @param user
	 * @return
	 */
	public List<Map<String,String>> queryEndPcsPageList(PageVO vo,User user) throws Exception;
	
 
	/**
	 * 
	 * @方法名称: queryAllPcsPageList
	 * @描述 :查询所有的上报案件
	 * @返回值类型： List<Map<String,String>>
	 * @param user
	 * @return
	 */
	public List<Map<String,String>> queryAllPcsPageList(PageVO vo,User user) throws Exception;
	
 
 
 
}
