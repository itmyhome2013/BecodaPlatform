package com.becoda.bkms.pcs.service;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.dao.DoTaskDao;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：DoTaskService
 * @描述：TODO(代办理任务Service)
 * @创建人： 张晓亮
 * @创建时间：2016年1月18日 下午3:24:10
 * @修改人：张晓亮
 * @修改时间：2016年1月18日 下午3:24:10
 * @修改备注：
 */
public class DoTaskService {
	private DoTaskDao doTaskDao;
	
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
		return this.doTaskDao.queryPageListBySql(vo, countSQL, querySQL);
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
		return this.doTaskDao.queryListBySql(querySQL);
	}
	
	
	
	//get and set
	public DoTaskDao getDoTaskDao() {
		return doTaskDao;
	}

	public void setDoTaskDao(DoTaskDao doTaskDao) {
		this.doTaskDao = doTaskDao;
	}
	

}
