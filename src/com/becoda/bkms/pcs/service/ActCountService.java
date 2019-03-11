package com.becoda.bkms.pcs.service;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.dao.ActCountDao;
 

/**
 * 
 * @项目名称：BecodaPlatForm
 * @描述：主要完成统计功能、统计查询、我的案件、经办案件、已归档案件
 * @author kris
 * @Date 2016-1-22
 */
public class ActCountService {
	private ActCountDao actCountDao;
	
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
		return this.actCountDao.queryPageListBySql(vo, countSQL, querySQL);
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
		return this.actCountDao.queryListBySql(querySQL);
	}

	public ActCountDao getActCountDao() {
		return actCountDao;
	}

	public void setActCountDao(ActCountDao actCountDao) {
		this.actCountDao = actCountDao;
	}
	
	
	 
	

}
