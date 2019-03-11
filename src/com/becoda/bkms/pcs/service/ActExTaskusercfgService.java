package com.becoda.bkms.pcs.service;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.dao.ActExTaskusercfgDao;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ActExTaskusercfgService
 * @描述：TODO(工作流人员配置Service)
 * @创建人： 张晓亮
 * @创建时间：2016年1月21日 上午9:55:06
 * @修改人：张晓亮
 * @修改时间：2016年1月21日 上午9:55:06
 * @修改备注：
 */
public class ActExTaskusercfgService {
	private ActExTaskusercfgDao actExTaskusercfgDao;

	/**
	 * 
	 * @方法名称: queryPageListBySql
	 * @描述：TODO(sql分页查询)
	 * @返回值类型： List<Map<String,String>>
	 * @param vo
	 *            分页对象
	 * @param countSQL
	 *            记录数据SQL
	 * @param querySQL
	 *            查询SQL
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String,String>> queryPageListBySql(PageVO vo, String countSQL, String querySQL) throws BkmsException{
		return this.actExTaskusercfgDao.queryPageListBySql(vo, countSQL, querySQL);
	}
	
	/**
	 * 
	 * @方法名称: queryListBySql
	 * @描述：TODO(sql查询)
	 * @返回值类型： List<Map<String,String>>
	 * @param querySQL
	 *            查询SQL
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String,String>> queryListBySql(String querySQL) throws BkmsException{
		return this.actExTaskusercfgDao.queryListBySql(querySQL);
	}
	
	//get and set
	public ActExTaskusercfgDao getActExTaskusercfgDao() {
		return actExTaskusercfgDao;
	}

	public void setActExTaskusercfgDao(ActExTaskusercfgDao actExTaskusercfgDao) {
		this.actExTaskusercfgDao = actExTaskusercfgDao;
	}
	
	
	

}
