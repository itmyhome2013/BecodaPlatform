package com.becoda.bkms.east.gkdd.service;


import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.XxglConstants;
import com.becoda.bkms.east.gkdd.dao.DdrzDAO;
import com.becoda.bkms.east.gkdd.pojo.DdrzBO;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 调度日志service</p>
 * @author liu_hq
 * @date 2017-9-14
 *
 */
public class DdrzService {
	private DdrzDAO ddrzDao;

	/**
	 * 编辑调度日志
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editDdrz(DdrzBO bo,User user) throws BkmsException{
		if(bo.getDdrz_id()!=null&&!bo.getDdrz_id().equals("")){//修改
			ddrzDao.updateBo(bo.getDdrz_id(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_DDRZ);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_DDRZ);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getDdrz_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条调度日志。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{//新增
			bo.setDdrz_id(SequenceGenerator.getUUID());
			ddrzDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_DDRZ);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_DDRZ);
			opebo.setModuleName(XxglConstants.MODULE_NAME);		
			opebo.setOperRecordId(bo.getDdrz_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条调度日志。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	/**
	 * 查询调度日志
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		return ddrzDao.queryList(query);
	}
	/**
	 * 条件查询调度日志
	 * @param bo
	 * @return List<DdrzBO>
	 * @throws RollbackableException
	 */
	public List<DdrzBO> queryDdrzList(DdrzBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from DdrzBO where 1=1");
		if(bo.getDdrz_id()!=null&&!bo.getDdrz_id().equals("")){
			hql.append(" and ddrz_id = '").append(bo.getDdrz_id()).append("'");
		}
		if(bo.getDdrz_rq()!=null&&!bo.getDdrz_rq().equals("")){
			hql.append(" and ddrz_rq = '").append(bo.getDdrz_rq()).append("'");
		}
		if(bo.getDdrz_zbr()!=null&&!bo.getDdrz_zbr().equals("")){
			hql.append(" and ddrz_zbr like '%").append(bo.getDdrz_zbr()).append("%'");
		}
		hql.append(" order by ddrz_rq desc ");
		return ddrzDao.queryHqlList(hql.toString());
	}
	/**
	 * 删除
	 * @param bo
	 * @throws BkmsException 
	 */
	public void deleteDdrz(DdrzBO bo,User user) throws BkmsException{
		String id=bo.getDdrz_id();
		ddrzDao.deleteBo(bo);
		//操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_DDRZ);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_DDRZ);
		opebo.setModuleName(XxglConstants.MODULE_NAME);			
		opebo.setOperRecordId(id);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条调度日志。");
        HrmsLog.addOperLog(this.getClass(), opebo);	
	}
	public DdrzDAO getDdrzDao() {
		return ddrzDao;
	}
	public void setDdrzDao(DdrzDAO ddrzDao) {
		this.ddrzDao = ddrzDao;
	}
	
}
