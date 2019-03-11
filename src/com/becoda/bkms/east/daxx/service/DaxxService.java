package com.becoda.bkms.east.daxx.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.XxglConstants;
import com.becoda.bkms.east.daxx.dao.DaxxDAO;
import com.becoda.bkms.east.daxx.pojo.DaxxBO;
import com.becoda.bkms.east.zdsb.dao.ZdsbDAO;
import com.becoda.bkms.east.zdsb.pojo.BjqdBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSbscBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbStateBO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 档案信息service</p>
 * @author zhu_lw
 * @date 2018-04-10
 *
 */
public class DaxxService {

	private DaxxDAO daxxDao;

	/**
	 * 查询档案信息
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		return daxxDao.queryList(query);
	}
	/**
	 * 获取部门
	 * @return
	 */
	public List findSsbm(){
		return daxxDao.findSsbm();
	}
	/**
	 * 获取所有机组
	 * @return
	 */
	public List findTotalJz(){
		return daxxDao.findTotalJz();
	}
	/**
	 * 获取部门下的机组
	 * @return
	 * @throws IOException
	 * @throws BkmsException
	 */
	public List findSsjz(String bmid){
		return daxxDao.findSsjz(bmid);
	}
	/**
	 * 条件查询档案信息
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<DaxxBO> queryByBO(DaxxBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from DaxxBO where 1=1");
		//id
		if(bo.getDaxx_id()!=null&&!bo.getDaxx_id().equals("")){
			hql.append(" and daxx_id = '").append(bo.getDaxx_id()).append("'");
		}
		return daxxDao.queryHqlList(hql.toString());
	}
	/**
	 * 编辑档案信息
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editDaxx(DaxxBO bo,User user) throws BkmsException{
		if(bo.getDaxx_id()!=null&&!bo.getDaxx_id().equals("")){ //修改
			daxxDao.updateBo(bo.getDaxx_id(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_DAXX);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_DAXX);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getDaxx_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条档案信息。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{ //新增
			bo.setDaxx_id(SequenceGenerator.getUUID());
			daxxDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_DAXX);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_DAXX);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getDaxx_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条档案信息。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	/**
	 * 删除档案信息
	 * @param bo
	 * @throws BkmsException 
	 */
	public void deleteDaxx(DaxxBO bo,User user) throws BkmsException{
		String daxxId=bo.getDaxx_id();
		daxxDao.deleteBo(bo);
		//操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_DAXX);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_DAXX);
		opebo.setModuleName(XxglConstants.MODULE_NAME);			
		opebo.setOperRecordId(daxxId);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条档案信息。");
        HrmsLog.addOperLog(this.getClass(), opebo);
	}
	public DaxxDAO getDaxxDao() {
		return daxxDao;
	}

	public void setDaxxDao(DaxxDAO daxxDao) {
		this.daxxDao = daxxDao;
	}
	
	
}