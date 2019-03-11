package com.becoda.bkms.east.yjbj.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.XxglConstants;
import com.becoda.bkms.east.yjbj.dao.YjbjDAO;
import com.becoda.bkms.east.yjbj.pojo.NxjcYjbjSbBO;
import com.becoda.bkms.east.yjbj.pojo.YcbjBO;
import com.becoda.bkms.east.yjbj.pojo.YjbjBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 预警报警service</p>
 * @author zhu lw
 * @date 2017-11-24
 *
 */
public class YjbjService {

	private YjbjDAO yjbjDao;

	/**
	 * 查询用能设备
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		return yjbjDao.queryList(query);
	}
	/**
	 * 条件查询用能设备
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryByBO(YnsbBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from YnsbBO where 1=1");
		//id
		if(bo.getYnsb_id()!=null&&!bo.getYnsb_id().equals("")){
			hql.append(" and ynsb_id = '").append(bo.getYnsb_id()).append("'");
		}
		//父id
		if(bo.getYnsb_fid()!=null&&!bo.getYnsb_fid().equals("")){
			hql.append(" and ynsb_fid = '").append(bo.getYnsb_fid()).append("'");
		}
		return yjbjDao.queryHqlList(hql.toString());
	}
	/**
	 * 条件查询预警报警
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YjbjBO> queryByYjbjBO(YjbjBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from YjbjBO where 1=1");
		//id
		if(bo.getYjbj_sbid()!=null&&!bo.getYjbj_sbid().equals("")){
			hql.append(" and yjbj_sbid = '").append(bo.getYjbj_sbid()).append("'");
		}
		return yjbjDao.queryHqlList(hql.toString());
	}
	/**
	 * 条件查询sisbo
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZdsbSisBO> queryBySisBO(ZdsbSisBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from ZdsbSisBO where 1=1");
		//id
		if(bo.getZdsb_id()!=null&&!bo.getZdsb_id().equals("")){
			hql.append(" and zdsb_id = '").append(bo.getZdsb_id()).append("'");
		}
		return yjbjDao.queryHqlList(hql.toString());
	}
	/**
	 * 编辑用能设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYnsb(YnsbBO bo) throws RollbackableException{
		if(bo.getYnsb_id()!=null&&!bo.getYnsb_id().equals("")){ //修改
			yjbjDao.updateBo(bo.getYnsb_id(), bo);
		}else{ //新增
			bo.setYnsb_id(SequenceGenerator.getUUID());
			yjbjDao.createBo(bo);
		}
	}
	/**
	 * 删除用能设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteYnsb(YnsbBO bo) throws RollbackableException{
		yjbjDao.deleteBo(bo);
	}
	/**
	 * 编辑预警报警指标
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editYjbj(YjbjBO yjbjBO,User user) throws BkmsException{
		if(yjbjBO.getYjbj_id()!=null&&!"".equals(yjbjBO.getYjbj_id())){
			yjbjDao.updateBo(yjbjBO.getYjbj_id(), yjbjBO);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_YJBJ);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YJBJ);
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(yjbjBO.getYjbj_id());
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条预警报警信息。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{
			yjbjBO.setYjbj_id(SequenceGenerator.getUUID());
			yjbjDao.createBo(yjbjBO);
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(XxglConstants.TABLE_ID_YJBJ);
			opebo.setOperInfosetName(XxglConstants.TABLE_NAME_YJBJ);
			opebo.setModuleName(XxglConstants.MODULE_NAME);		
			opebo.setOperRecordId(yjbjBO.getYjbj_id());
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条预警报警信息。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}
	/**
	 * 查询用能设备sis标示
	 * @param query
	 * @return
	 */
	public DataQuery queryYnsbSis(DataQuery query){
		return yjbjDao.queryYnsbSis(query);
	}
	/**
	 * 删除用能设备sis标示
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteYnsbSis(YnsbSisBO bo) throws RollbackableException{
		yjbjDao.deleteBo(bo);
	}
	/**
	 * 根据用能设备id删除sis标示
	 * @param ynsbid
	 */
	public void deleteYnsbSisBySbid(String ynsbid){
		yjbjDao.deleteYnsbSisBySbid(ynsbid);
	}
	/**
	 * 新增异常报警
	 * @param bo
	 * @throws RollbackableException
	 */
	public void addYcbj(YcbjBO bo) throws RollbackableException{
		yjbjDao.createBo(bo);
	}
	/**
	 * 查询异常报警
	 * @param query
	 * @param sj
	 * @return
	 */
	public DataQuery queryYcbj(DataQuery query,String sj,String lx){
		return yjbjDao.queryYcbj(query, sj,lx);
	}
	/**
	 * 根据时间查询异常报警
	 * @param sj
	 * @return
	 * @throws BkmsException 
	 */
	public List<Map<String, String>> queryBySj(String sj,String fid) throws BkmsException{
		StringBuffer sql = new StringBuffer();
		if(fid!=null&&fid.equals("1")){
			sql.append(" select sb.nxjcsb_mc as YNSB_GLBH,sb.nxjcsb_lx as YNSBSIS_MC,bj.ycbj_sj,bj.ycbj_val,bj.ycbj_bjlx " +
					"from east_ycbj bj left join east_nxjcsb sb on sb.nxjcsb_id = bj.ynsb_id " +
					" where bj.ycbj_nxjclx is not null");
			if(sj!=null&&!sj.equals("")){
				sql.append(" and bj.ycbj_sj like '").append(sj).append("%'");
			}
		}else{
			sql.append(
					"select sb.YNSB_ID,sb.YNSB_GLBH,sis.YNSBSIS_MC,yc.YCBJ_VAL,yc.YCBJ_BJLX,yc.YCBJ_SJ"
					+ " from east_ycbj yc,east_ynsbsis sis,east_ynsb sb where yc.ynsb_sis=sis.ynsbsis_bs and sis.ynsb_id = sb.ynsb_id");
			if(sj!=null&&!sj.equals("")){
				sql.append(" and yc.ycbj_sj like '").append(sj).append("%'");
			}if(fid!=null&&!fid.equals("")){
				sql.append(" and sb.YNSB_FID = '").append(fid).append("'");
			}
		}
		return yjbjDao.queryListBySql(sql.toString());
	}
	/**
	 * 查询历史异常报警
	 * @param query
	 * @return
	 */
	public DataQuery queryHistoryYcbj(DataQuery query,String lx){
		return yjbjDao.queryHistoryYcbj(query,lx);
	}
	/**
	 * 条件查询异常报警
	 * @param starttime
	 * @param endtime
	 * @param sbbh
	 * @return
	 */
	public List<Map<String, Object>> queryYcbj(String starttime,String endtime, String sbbh,String lx) {
		StringBuffer sql = new StringBuffer();
		if(lx.equals("1")){ //能效检测预警报警
			sql.append(" select sb.nxjcsb_mc as YNSB_GLBH,sb.nxjcsb_lx as YNSBSIS_MC,bj.ycbj_sj,bj.ycbj_val,bj.ycbj_bjlx " +
					"from east_ycbj bj left join east_nxjcsb sb on sb.nxjcsb_id = bj.ynsb_id " +
					" where bj.ycbj_nxjclx is not null");
			if(!starttime.equals("")){
				sql.append(" and bj.YCBJ_SJ >= '").append(starttime).append("'");
			}
			if(!endtime.equals("")){
				sql.append(" and bj.YCBJ_SJ <= '").append(endtime).append("'");
			}
			if(!sbbh.equals("")){
				sql.append(" and bj.ycbj_nxjclx = '").append(sbbh).append("'");
			}
		}else{
			sql.append(
					"select sb.YNSB_ID,sb.YNSB_GLBH,sis.YNSBSIS_MC,yc.YCBJ_VAL,yc.YCBJ_BJLX,yc.YCBJ_SJ"
					+ " from east_ycbj yc,east_ynsbsis sis,east_ynsb sb where yc.ynsb_sis=sis.ynsbsis_bs and sis.ynsb_id = sb.ynsb_id");
			if(!starttime.equals("")){
				sql.append(" and yc.YCBJ_SJ >= '").append(starttime).append("'");
			}
			if(!endtime.equals("")){
				sql.append(" and yc.YCBJ_SJ <= '").append(endtime).append("'");
			}
			if(!sbbh.equals("")){
				sql.append(" and sb.YNSB_GLBH = '").append(sbbh).append("'");
			}
		}
		return yjbjDao.getJdbcTemplate().queryForList(sql.toString());
	}
	/**
	 * 查询能效检测预警报警设备
	 * @param query
	 * @return
	 */
	public DataQuery queryNxjcYcbjSb(DataQuery query){
		return yjbjDao.queryNxjcYcbjSb(query);
	}
	/**
	 * 修改能效检测预警报警设备
	 * @param bo
	 * @throws BkmsException 
	 */
	public void editNxjcYjbj(NxjcYjbjSbBO bo,User user) throws BkmsException{
		yjbjDao.updateBo(bo.getNxjcsb_id(), bo);
		//操作日志
		OperLogBO opebo = new OperLogBO();
		opebo.setOperatorId(user.getUserId());
		opebo.setOperatorName(user.getName());
		opebo.setOperatorOrg(user.getDeptId());
		opebo.setOperInfosetId(XxglConstants.TABLE_ID_NXJCYJBJSB);
		opebo.setOperInfosetName(XxglConstants.TABLE_NAME_NXJCYJBJSB);
		opebo.setModuleName(XxglConstants.MODULE_NAME);			
		opebo.setOperRecordId(bo.getNxjcsb_id());
		opebo.setOperType("modify");
		opebo.setOperDesc(user.getName() + "修改了一条能效检测预警报警设备信息。");
        HrmsLog.addOperLog(this.getClass(), opebo);
	}
	/**
	 * 定时处理异常报警
	 * @throws ParseException
	 * @throws RollbackableException 
	 */
	public void saveTimingYcbj(){
		try {
			String datestr = Tools.minusMinute(Tools.getSysDate("yyyy-MM-dd HH:mm")+":00", -1); //查询上一分钟数据，错开数据同步时间
			yjbjDao.updateNxjc(datestr);
			//处理已恢复正常的设备
			String updateYcsql = "update east_ycbj ycbj set ycbj.ycbj_state = '1' where ycbj.ycbj_state='0' and exists (" +
					"select sis.sisid from (select sisval.sisid from zd_test2 sisval,east_yjbj yjbj where " +
					"sisval.sistime = '"+datestr+"' and exists (select t1.ynsb_sis from (select t.ynsb_sis" +
					" from east_ycbj t where t.ycbj_state = '0'  group by t.ynsb_sis) t1" +
					" where sisval.sisid = t1.ynsb_sis) and yjbj.yjbj_sis_bs = sisval.sisid and to_number(sisval.sisvalue)< to_number(nvl(yjbj.yjbj_max,0))" +
					" and to_number(sisval.sisvalue)> to_number(nvl(yjbj.yjbj_min,0)))sis where sis.sisid = ycbj.ynsb_sis )";
			
			yjbjDao.getJdbcTemplate().update(updateYcsql);
			
			
			//设备异常报警
//		String datestr = Tools.minusMinute("2017-11-28 15:45:00", -1); //查询上一分钟数据，错开数据同步时间
				
			String sql = "select jc.sisid,jc.sisvalue,bj.yjbj_sbid,bj.yjbj_sis_bs,bj.yjbj_min,bj.yjbj_max,'低于最小值：'|| bj.yjbj_min as yclx "
					+ " from zd_test2 jc,east_yjbj bj where jc.sistime='"
					+ datestr
					+ "' and jc.sisid = bj.yjbj_sis_bs "
					+ " and to_number(jc.sisvalue) < to_number(bj.yjbj_min) union all "
					+ " select jc.sisid,jc.sisvalue,bj.yjbj_sbid,bj.yjbj_sis_bs,bj.yjbj_min,bj.yjbj_max,'高于最大值：'|| bj.yjbj_max as yclx"
					+ " from zd_test2 jc,east_yjbj bj where jc.sistime='"
					+ datestr
					+ "' and jc.sisid = bj.yjbj_sis_bs "
					+ " and to_number(jc.sisvalue) > to_number(bj.yjbj_max)";
			List<Map<String, Object>> list = yjbjDao.getJdbcTemplate().queryForList(sql);
			for (Map<String, Object> map : list) {
				YcbjBO bo = new YcbjBO();
				bo.setYcbj_id(SequenceGenerator.getUUID());
				bo.setYcbj_bjlx(map.get("yclx").toString());
				bo.setYcbj_sj(datestr);
				bo.setYcbj_val(map.get("sisvalue").toString());
				bo.setYnsb_id(map.get("yjbj_sbid").toString());
				bo.setYnsb_sis(map.get("yjbj_sis_bs").toString());
				bo.setYcbj_state("0");
				yjbjDao.createBo(bo);
			}
			//能效检测报警预警
			String datestrNxjc = Tools.minusMinute(datestr,-1);//两分钟前数据
//		String datestrNxjc = "2018-03-04 10:58:00";//两分钟前数据
			String sqlNxjc = "select jc.nxjc_id,sb.nxjcsb_id,jc.nxjc_sb,jc.nxjc_val,jc.nxjc_lx,sb.nxjcsb_yjmin,sb.nxjcsb_yjmax" +
					",'低于最小值：'|| sb.nxjcsb_yjmin as yclx from east_nxjc jc left join east_nxjcsb sb on sb.nxjcsb_mc = jc.nxjc_sb" +
					" where jc.nxjc_sj='"+datestrNxjc+"' and to_number(jc.nxjc_val) < to_number(sb.nxjcsb_yjmin)  union all" +
					" select jc.nxjc_id,sb.nxjcsb_id,jc.nxjc_sb,jc.nxjc_val,jc.nxjc_lx,sb.nxjcsb_yjmin,sb.nxjcsb_yjmax" +
					" ,'高于最大值：'|| sb.nxjcsb_yjmax as yclx from east_nxjc jc left join east_nxjcsb sb on sb.nxjcsb_mc = jc.nxjc_sb" +
					" where jc.nxjc_sj='"+datestrNxjc+"' and to_number(jc.nxjc_val) > to_number(sb.nxjcsb_yjmax)";
			
			List<Map<String, Object>> listnxjc = yjbjDao.getJdbcTemplate().queryForList(sqlNxjc);
			for (Map<String, Object> map : listnxjc) {
				YcbjBO bo = new YcbjBO();
				bo.setYcbj_id(SequenceGenerator.getUUID());
				bo.setYcbj_bjlx(map.get("yclx").toString());
				bo.setYcbj_sj(datestrNxjc);
				bo.setYcbj_val(map.get("nxjc_val").toString());
				bo.setYnsb_id(map.get("nxjcsb_id").toString());
				bo.setYcbj_nxjclx(map.get("nxjc_lx").toString());
				bo.setYcbj_state("0");
				yjbjDao.createBo(bo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 报警提醒查询
	 * @return
	 */
	public List<Map<String, Object>> queryAlarmAlert(){
		return yjbjDao.queryAlarmAlert();
	}
	/**
	 * 查询异常报警
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YcbjBO> queryYcbjByBO(YcbjBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from YcbjBO where 1=1");
		//id
		if(bo.getYcbj_id()!=null && !bo.getYcbj_id().equals("")){
			hql.append(" and ycbj_id = '").append(bo.getYcbj_id()).append("'");
		}
		return yjbjDao.queryHqlList(hql.toString());
	}
	/**
	 * 修改异常报警
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYcbj(YcbjBO bo) throws RollbackableException{
		yjbjDao.updateBo(bo.getYcbj_id(), bo);
	}
	public YjbjDAO getYjbjDao() {
		return yjbjDao;
	}
	public void setYjbjDao(YjbjDAO yjbjDao) {
		this.yjbjDao = yjbjDao;
	}
	
	
}
