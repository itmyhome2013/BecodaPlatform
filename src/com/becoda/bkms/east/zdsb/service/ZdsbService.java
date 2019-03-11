package com.becoda.bkms.east.zdsb.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.XxglConstants;
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
 * <p>Description: 重点设备service</p>
 * @author zhu_lw
 * @date 2017-12-22
 *
 */
public class ZdsbService {

	private ZdsbDAO zdsbDao;

	/**
	 * 查询重点设备
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		return zdsbDao.queryList(query);
	}
	/**
	 * 查询重点设备状态
	 * @param query
	 * @return
	 */
	public DataQuery queryStateList(DataQuery query){
		return zdsbDao.queryStateList(query);
	}
	/**
	 * 条件查询重点设备
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZdsbBO> queryByBO(ZdsbBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from ZdsbBO where 1=1");
		//id
		if(bo.getZdsb_id()!=null&&!bo.getZdsb_id().equals("")){
			hql.append(" and zdsb_id = '").append(bo.getZdsb_id()).append("'");
		}
		return zdsbDao.queryHqlList(hql.toString());
	}
	
	/**
	 * 编辑重点设备
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editZdsb(ZdsbBO bo,User user) throws BkmsException{
		if(bo.getZdsb_id()!=null&&!bo.getZdsb_id().equals("")){ //修改
			zdsbDao.updateBo(bo.getZdsb_id(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSB);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSB);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getZdsb_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条重点设备。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{ //新增
			bo.setZdsb_id(SequenceGenerator.getUUID());
			bo.setZdsb_lrrq(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
			zdsbDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSB);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSB);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getZdsb_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条重点设备。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	
	/**
	 * 编辑重点设备状态
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editZdsbState(ZdsbStateBO bo,User user) throws BkmsException{
		if(bo.getZt_id()!=null&&!bo.getZt_id().equals("")){ //修改
			zdsbDao.updateBo(bo.getZt_id(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			//opebo.setOperatorId(user.getUserId());
			//opebo.setOperatorName(user.getName());
			//opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSTATE);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSTATE);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getZt_id());
			opebo.setOperType("modify");
			//opebo.setOperDesc(user.getName() + "修改了一条重点设备状态。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{ //新增
			bo.setZt_id(SequenceGenerator.getUUID());
			bo.setZdsb_jxrq(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
			zdsbDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			//opebo.setOperatorId(user.getUserId());
			//opebo.setOperatorName(user.getName());
			//opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSTATE);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSTATE);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getZt_id());
			opebo.setOperType("add");
			//opebo.setOperDesc(user.getName() + "新增了一条重点设备状态。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	/**
	 * 编辑重点设备设备时长
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editZdsbSbsc(ZdsbSbscBO bo,User user) throws BkmsException{
		if(bo.getId()!=null&&!bo.getId().equals("")){ //修改
			zdsbDao.updateBo(bo.getId(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			//opebo.setOperatorId(user.getUserId());
			//opebo.setOperatorName(user.getName());
			//opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSBSC);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSBSC);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getId());
			opebo.setOperType("modify");
			//opebo.setOperDesc(user.getName() + "修改了一条重点设备设备时长。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{ //新增
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 设置指定日期
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(Tools.getSysYear()));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date date = new Date(cal.getTimeInMillis());
			// 格式化
			String dateStr = dateFormat.format(date);
			bo.setId(SequenceGenerator.getUUID());
			bo.setZdsb_qnsc(dateStr);
			bo.setZdsb_zsc(dateStr);
			zdsbDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSBSC);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSBSC);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getId());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条重点设备设备时长。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	/**
	 * 删除重点设备
	 * @param bo
	 * @throws BkmsException 
	 */
	public void deleteZdsb(ZdsbBO bo,User user) throws BkmsException{
		String zdsbId=bo.getZdsb_id();
		zdsbDao.deleteBo(bo);
		//操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSB);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSB);
		opebo.setModuleName(XxglConstants.MODULE_NAME);			
		opebo.setOperRecordId(zdsbId);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条重点设备。");
        HrmsLog.addOperLog(this.getClass(), opebo);
	}
	
	/**
	 * 根据重点设备id删除sis标示
	 * @param zdsbid
	 * @throws BkmsException 
	 */
	public void deleteZdsbSisBySbid(String zdsbid,User user) throws BkmsException{
		zdsbDao.deleteZdsbSisBySbid(zdsbid);
		//操作日志
				OperLogBO opebo = new OperLogBO();
				opebo.setOperatorId(user.getUserId());
				opebo.setOperatorName(user.getName());
				opebo.setOperatorOrg(user.getDeptId());
				opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSIS);
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSIS);
				opebo.setModuleName(XxglConstants.MODULE_NAME);			
				opebo.setOperType("delete");
				opebo.setOperDesc(user.getName() + "删除了一条设备ID为"+zdsbid+"的重点设备SIS标识。");
		        HrmsLog.addOperLog(this.getClass(), opebo);
	}
	/**
	 * 根据重点设备id删除设备时长
	 * @param zdsbid
	 * @throws BkmsException 
	 */
	public void deleteZdsbSbscBySbid(String zdsbid,User user) throws BkmsException{
		zdsbDao.deleteZdsbSbscBySbid(zdsbid);
		//操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSBSC);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSBSC);
		opebo.setModuleName(XxglConstants.MODULE_NAME);			
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条设备ID为"+zdsbid+"的重点设备设备时长。");
        HrmsLog.addOperLog(this.getClass(), opebo);
	}
	/**
	 * 查询重点设备sis标示
	 * @param query
	 * @return
	 */
	public DataQuery queryZdsbSis(DataQuery query){
		return zdsbDao.queryZdsbSis(query);
	}
	/**
	 * 编辑重点设备sis标示
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editZdsbSis(ZdsbSisBO bo,User user) throws BkmsException{
		if(bo.getZdsbsis_id()!=null&&!"".equals(bo.getZdsbsis_id())){
			zdsbDao.updateBo(bo.getZdsbsis_id(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSIS);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSIS);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getZdsbsis_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条重点设备SIS标识。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{
			bo.setZdsbsis_id(SequenceGenerator.getUUID());
			bo.setZdsbsis_date(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
			zdsbDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSIS);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSIS);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getZdsbsis_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条重点设备SIS标识。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	/**
	 * 删除重点设备sis标示
	 * @param bo
	 * @throws BkmsException 
	 */
	public void deleteZdsbSis(ZdsbSisBO bo,User user) throws BkmsException{
		String sisId=bo.getZdsbsis_id();
		zdsbDao.deleteBo(bo);
		//操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_ZDSBSIS);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZDSBSIS);
		opebo.setModuleName(XxglConstants.MODULE_NAME);			
		opebo.setOperRecordId(sisId);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条重点设备SIS标识。");
        HrmsLog.addOperLog(this.getClass(), opebo);
	}
	/**
	 * 通过条件查询重点设备
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbByParams(Map<String,String> params) {
		return zdsbDao.queryZdsbByParams(params);
	}
	/**
	 * 查询重点设备设备时长
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbSbsc() {
		return zdsbDao.queryZdsbSbsc();
	}
	/**
	 * 通过条件查询设备检测信息
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbSbjcByParams(String sj) {
		return zdsbDao.queryZdsbSbjcByParams(sj);
	}
	/**
	 * 查询备件清单
	 * @param query
	 * @return
	 */
	public DataQuery queryBjqdList(DataQuery query){
		return zdsbDao.queryBjqdList(query);
	}
	/**
	 * 条件查询备件清单
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<BjqdBO> queryBjqdByBO(BjqdBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from BjqdBO where 1=1");
		//id
		if(bo.getId()!=null&&!bo.getId().equals("")){
			hql.append(" and id = '").append(bo.getId()).append("'");
		}
		return zdsbDao.queryHqlList(hql.toString());
	}
	/**
	 * 编辑备件清单
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editBjqd(BjqdBO bo,User user) throws BkmsException{
		if(bo.getId()!=null&&!bo.getId().equals("")){ //修改
			zdsbDao.updateBo(bo.getId(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_BJQD);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_BJQD);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getId());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条备件清单。");
	        HrmsLog.addOperLog(this.getClass(), opebo);
		}else{ //新增
			bo.setId(SequenceGenerator.getUUID());
			bo.setBjqd_cjsj(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
			zdsbDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_BJQD);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_BJQD);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getId());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条备件清单。");
	        HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	/**
	 * 删除备件清单
	 * @param bo
	 * @throws BkmsException 
	 */
	public void deleteBjqd(BjqdBO bo,User user) throws BkmsException{
		String bjqdId=bo.getId();
		zdsbDao.deleteBo(bo);
		//操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_BJQD);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_BJQD);
		opebo.setModuleName(XxglConstants.MODULE_NAME);			
		opebo.setOperRecordId(bjqdId);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条备件清单。");
        HrmsLog.addOperLog(this.getClass(), opebo);
	}
	public ZdsbDAO getZdsbDao() {
		return zdsbDao;
	}
	public void setZdsbDao(ZdsbDAO zdsbDao) {
		this.zdsbDao = zdsbDao;
	}
}