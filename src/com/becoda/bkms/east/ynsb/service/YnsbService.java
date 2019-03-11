package com.becoda.bkms.east.ynsb.service;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.XxglConstants;
import com.becoda.bkms.east.ynsb.dao.YnsbDAO;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>
 * Description: 用能设备service
 * </p>
 * 
 * @author liu_hq
 * @date 2017-9-27
 * 
 */
public class YnsbService {

	private YnsbDAO ynsbDao;

	/**
	 * 查询用能设备
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query) {
		return ynsbDao.queryList(query);
	}

	/**
	 * 条件查询用能设备
	 * 
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryByBO(YnsbBO bo) throws RollbackableException {
		StringBuffer hql = new StringBuffer("from YnsbBO where 1=1");
		// id
		if (bo.getYnsb_id() != null && !bo.getYnsb_id().equals("")) {
			hql.append(" and ynsb_id = '").append(bo.getYnsb_id()).append("'");
		}
		// 父id
		if (bo.getYnsb_fid() != null && !bo.getYnsb_fid().equals("")) {
			hql.append(" and ynsb_fid = '").append(bo.getYnsb_fid())
					.append("'");
		}
		// 能源种类
		if (bo.getYnsb_nyzl() != null && !bo.getYnsb_nyzl().equals("")) {
			hql.append("and ynsb_nyzl = '").append(bo.getYnsb_nyzl())
					.append("'");
		}
		// 设备编号
		if (bo.getYnsb_glbh() != null && !bo.getYnsb_glbh().equals("")) {
			hql.append(" and ynsb_glbh like '%").append(bo.getYnsb_glbh())
					.append("%'");
		}
		return ynsbDao.queryHqlList(hql.toString());
	}

	/**
	 * 编辑用能设备
	 * 
	 * @param bo
	 * @throws BkmsException
	 */
	public void editYnsb(YnsbBO bo, User user) throws BkmsException {
		if (bo.getYnsb_id() != null && !bo.getYnsb_id().equals("")) { // 修改
			ynsbDao.updateBo(bo.getYnsb_id(), bo);
			// 操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSB);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSB);
			opebo.setModuleName(XxglConstants.MODULE_NAME);
			opebo.setOperRecordId(bo.getYnsb_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条用能设备。");
			HrmsLog.addOperLog(this.getClass(), opebo);
		} else { // 新增
			bo.setYnsb_id(SequenceGenerator.getUUID());
			ynsbDao.createBo(bo);
			// 操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSB);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSB);
			opebo.setModuleName(XxglConstants.MODULE_NAME);
			opebo.setOperRecordId(bo.getYnsb_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条用能设备。");
			HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}

	/**
	 * 删除用能设备
	 * 
	 * @param bo
	 * @throws BkmsException
	 */
	public void deleteYnsb(YnsbBO bo, User user) throws BkmsException {
		String ynsbId = bo.getYnsb_id();
		ynsbDao.deleteBo(bo);
		// 操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSB);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSB);
		opebo.setModuleName(XxglConstants.MODULE_NAME);
		opebo.setOperRecordId(ynsbId);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条用能设备。");
		HrmsLog.addOperLog(this.getClass(), opebo);
	}

	/**
	 * 编辑用能设备sis标示
	 * 
	 * @param bo
	 * @throws BkmsException
	 */
	public void editYnsbSis(YnsbSisBO bo, User user) throws BkmsException {
		if (bo.getYnsbsis_id() != null && !"".equals(bo.getYnsbsis_id())) {
			ynsbDao.updateBo(bo.getYnsbsis_id(), bo);
			// 操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSBSIS);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSBSIS);
			opebo.setModuleName(XxglConstants.MODULE_NAME);
			opebo.setOperRecordId(bo.getYnsbsis_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条用能设备SIS标识。");
			HrmsLog.addOperLog(this.getClass(), opebo);
		} else {
			bo.setYnsbsis_id(SequenceGenerator.getUUID());
			ynsbDao.createBo(bo);
			// 操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSBSIS);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSBSIS);
			opebo.setModuleName(XxglConstants.MODULE_NAME);
			opebo.setOperRecordId(bo.getYnsbsis_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条用能设备SIS标识。");
			HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}

	/**
	 * 查询用能设备sis标示
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryYnsbSis(DataQuery query) {
		return ynsbDao.queryYnsbSis(query);
	}

	/**
	 * 删除用能设备sis标示
	 * 
	 * @param bo
	 * @throws BkmsException
	 */
	public void deleteYnsbSis(YnsbSisBO bo, User user) throws BkmsException {
		String ynsbSisId = bo.getYnsbsis_id();
		ynsbDao.deleteBo(bo);
		// 操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSBSIS);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSBSIS);
		opebo.setModuleName(XxglConstants.MODULE_NAME);
		opebo.setOperRecordId(ynsbSisId);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条用能设备SIS标识。");
		HrmsLog.addOperLog(this.getClass(), opebo);
	}

	/**
	 * 根据用能设备id删除sis标示
	 * 
	 * @param ynsbid
	 * @throws BkmsException
	 */
	public void deleteYnsbSisBySbid(String ynsbid, User user)
			throws BkmsException {
		ynsbDao.deleteYnsbSisBySbid(ynsbid);
		// 操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSBSIS);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSBSIS);
		opebo.setModuleName(XxglConstants.MODULE_NAME);
		opebo.setOperType("delete");
		opebo.setOperDesc(user.getName() + "删除了一条用能设备ID为" + ynsbid + "的SIS标识。");
		HrmsLog.addOperLog(this.getClass(), opebo);
	}

	/**
	 * 根据用能设备id修改sis标示能源种类
	 * 
	 * @param ynsbid
	 * @param nyzl
	 * @throws BkmsException
	 */
	public void editSisBySbid(String ynsbid, String nyzl, User user)
			throws BkmsException {
		ynsbDao.editSisBySbid(ynsbid, nyzl);
		// 操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_YNSBSIS);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YNSBSIS);
		opebo.setModuleName(XxglConstants.MODULE_NAME);
		opebo.setOperType("modify");
		opebo.setOperDesc(user.getName() + "修改了用能设备ID为" + ynsbid
				+ "的SIS标识的能源种类。");
		HrmsLog.addOperLog(this.getClass(), opebo);
	}

	/**
	 * 查询用能设备
	 * 
	 * @param itemids
	 * @param itemSpell
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeItem(String itemids, String itemSpell)
			throws RollbackableException {
		return ynsbDao.queryCodeItem(itemids, itemSpell);
	}
	/**
	 * 查询园区电表
	 * @param query
	 * @return
	 */
	public DataQuery queryYqdb(DataQuery query){
		return ynsbDao.queryYqdb(query);
	}

	public YnsbDAO getYnsbDao() {
		return ynsbDao;
	}

	public void setYnsbDao(YnsbDAO ynsbDao) {
		this.ynsbDao = ynsbDao;
	}

}
