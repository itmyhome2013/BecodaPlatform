package com.becoda.bkms.east.sjbl.service;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.XxglConstants;
import com.becoda.bkms.east.sjbl.dao.SjblDAO;
import com.becoda.bkms.east.sjbl.pojo.SjblBO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 手工补录service</p>
 * @author zhu lw
 * @date 2017-11-27
 *
 */
public class SjblService {

	private SjblDAO sjblDao;

	/**
	 * 手工补录信息
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		return sjblDao.queryList(query);
	}
	
	/**
	 * 查询手工补录用于编辑
	 * @param zdjz
	 * @param nyzl
	 * @param tbrq
	 * @return
	 * @throws BkmsException
	 */
	public List querySjblMsgForEdit(String zdjz,String nyzl,String tbrq,String sjbl_id) throws BkmsException{
		StringBuffer sql =new StringBuffer();
		sql.append(" select A.YNSB_ID AS YNSB_ID ,A.YNSB_GLBH AS YNSB_GLBH,A.YNSB_NYZL AS YNSB_NYZL," +
				"A.YNSB_FID AS YNSB_FID,B.EAST_SJBL_ID AS EAST_SJBL_ID,B.EAST_SJBL_LJZ AS EAST_SJBL_LJZ," +
				"B.EAST_SJBL_FENG AS EAST_SJBL_FENG,B.EAST_SJBL_GU AS EAST_SJBL_GU,B.EAST_SJBL_PING AS EAST_SJBL_PING," +
				"B.EAST_SJBL_JIAN AS EAST_SJBL_JIAN,B.EAST_SJBL_TOTAL AS EAST_SJBL_TOTAL,B.EAST_SJBL_DATE AS EAST_SJBL_DATE,B.East_Sjbl_Lookdate as EAST_SJBL_LOOKDATE FROM EAST_YNSB a " +
				"left join EAST_SJBL_MANUAL_MAKEUP b on b.east_sjbl_sbid=a.ynsb_id and b.east_sjbl_date= '").append(tbrq).append("'");
		sql.append(" where  a.ynsb_fid = '").append(zdjz).append("'");
		sql.append(" and a.ynsb_nyzl = '").append(nyzl).append("'");
		
		if(sjbl_id!=null && !"".equals(sjbl_id)){
			sql.append(" and b.east_sjbl_id = '").append(sjbl_id).append("'");
		}
		
		sql.append(" ORDER BY ynsb_glbh ");
		return sjblDao.queryListBySql(sql.toString());
	}
	/**
	 * 新增或修改手工补录信息
	 * @param bo
	 * @throws BkmsException 
	 */
	public void addOrUpdate(SjblBO bo,User user) throws BkmsException{
		if(bo.getEast_sjbl_id()==null || "".equals(bo.getEast_sjbl_id())){
			bo.setEast_sjbl_id(SequenceGenerator.getUUID());
			sjblDao.createBo(bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_SJBL);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SJBL);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getEast_sjbl_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条数据补录信息。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{
			sjblDao.updateBo(bo.getEast_sjbl_id(), bo);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_SJBL);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SJBL);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(bo.getEast_sjbl_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条数据补录信息。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}	
	}
	public SjblDAO getSjblDao() {
		return sjblDao;
	}

	public void setSjblDao(SjblDAO sjblDao) {
		this.sjblDao = sjblDao;
	}
	
	
}
