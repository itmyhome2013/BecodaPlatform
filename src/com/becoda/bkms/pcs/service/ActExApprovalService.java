package com.becoda.bkms.pcs.service;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.dao.ActExApprovalDao;
import com.becoda.bkms.pcs.pojo.bo.ActExApprovalBO;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ActExApprovalService
 * @描述：TODO(审批信息Service)
 * @创建人： 张晓亮
 * @创建时间：2016年1月21日 下午2:17:07
 * @修改人：张晓亮
 * @修改时间：2016年1月21日 下午2:17:07
 * @修改备注：
 */
public class ActExApprovalService {
	private ActExApprovalDao actExApprovalDao;
	
	/**
	 * 
	 * @方法名称: saveActExApproval
	 * @描述：TODO(保存审批信息) 
	 * @返回值类型： void  
	 * @param actExApproval
	 * @throws RollbackableException
	 */
	public void saveActExApproval(ActExApprovalBO actExApproval) throws RollbackableException{
		this.actExApprovalDao.createBo(actExApproval);
	}

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
		return this.actExApprovalDao.queryPageListBySql(vo, countSQL, querySQL);
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
		return this.actExApprovalDao.queryListBySql(querySQL);
	}
	
	//get and set
	public ActExApprovalDao getActExApprovalDao() {
		return actExApprovalDao;
	}

	public void setActExApprovalDao(ActExApprovalDao actExApprovalDao) {
		this.actExApprovalDao = actExApprovalDao;
	}
	

}
