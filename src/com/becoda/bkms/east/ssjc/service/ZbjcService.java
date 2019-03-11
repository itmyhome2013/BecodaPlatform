package com.becoda.bkms.east.ssjc.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.BkmsJdbcTemplate;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.ssjc.SsjcConstants;
import com.becoda.bkms.east.ssjc.dao.ZbjcDAO;
import com.becoda.bkms.east.ssjc.pojo.NxjcBO;
import com.becoda.bkms.east.ssjc.pojo.ZbjcBO;
import com.becoda.bkms.east.ssjc.pojo.ZbjcQueryCondition;
import com.becoda.bkms.east.ssjc.pojo.ZbjcljzBO;
import com.becoda.bkms.east.ssjc.pojo.ZdTest2BO;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YqdbBO;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>
 * Description:指标检测service
 * </p>
 * 
 * @author liu_hq
 * @date 2017-11-1
 * 
 */
public class ZbjcService {
	private ZbjcDAO zbjcDao;

	public ZbjcDAO getZbjcDao() {
		return zbjcDao;
	}

	public void setZbjcDao(ZbjcDAO zbjcDao) {
		this.zbjcDao = zbjcDao;
	}

	public List<CodeItemBO> queryCode(String setId, String superId,
			String itemSpell) throws RollbackableException {
		return zbjcDao.queryCode(setId, superId, itemSpell);
	}

	/**
	 * 查询能源类型
	 * 
	 * @param nyzl
	 * @param nyjc
	 * @return
	 */
	public List<Map<String, Object>> queryType(String nyzl, String nyjc,
			String fid) {
		return zbjcDao.queryType(nyzl, nyjc, fid);
	}

	/**
	 * 查询用能设备
	 * 
	 * @param fid
	 * @return
	 * @throws RollbackableException
	 */
	public List<Map<String, Object>> queryYnsb(String fid)
			throws RollbackableException {
		return zbjcDao.queryYnsb(fid);
	}

	/**
	 * 查询指标检测值
	 * 
	 * @param sj
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZbjcBO> queryZbjc(String sj) throws RollbackableException {
		return zbjcDao.queryZbjc(sj);
	}

	/**
	 * 查询重点机组
	 * 
	 * @param setId
	 * @param superId
	 * @param itemSpell
	 * @param itemAbbr
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeIn(String setId, String superId,
			String itemSpell, String itemAbbr) throws RollbackableException {
		return zbjcDao.queryCodeIn(setId, superId, itemSpell, itemAbbr);
	}

	/**
	 * 查询预警报警重点机组
	 * 
	 * @param setId
	 * @param superId
	 * @param itemSpell
	 * @param itemAbbr
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryYjbjCodeIn(String setId, String superId,
			String itemSpell, String itemAbbr) throws RollbackableException {
		return zbjcDao.queryYjbjCodeIn(setId, superId, itemSpell, itemAbbr);
	}

	/**
	 * 查询重点机组
	 * 
	 * @param setId
	 * @param itemId
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryNameById(String setId, String itemId)
			throws RollbackableException {
		return zbjcDao.queryNameById(setId, itemId);
	}

	/**
	 * 查询手工补录重点机组
	 * 
	 * @param setId
	 * @param superId
	 * @param qxDesc
	 * @param itemAbbr
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeInSgbl(String setId, String superId,
			String qxDesc, String itemAbbr, String description)
			throws RollbackableException {
		return zbjcDao.queryCodeInSgbl(setId, superId, qxDesc, itemAbbr,
				description);
	}

	/**
	 * 用能设备计量编号
	 * 
	 * @param itemid
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryYnsbGlbh(String itemid)
			throws RollbackableException {
		return zbjcDao.queryYnsbGlbh(itemid);
	}

	/**
	 * 用能设备计量编号
	 * 
	 * @param itemid
	 * @param nyzl
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryGlbhByNyzl(String itemid, String nyzl)
			throws RollbackableException {
		return zbjcDao.queryGlbhByNyzl(itemid, nyzl);
	}

	/**
	 * 根据机组id查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySis(String fid) {
		return zbjcDao.querySis(fid);
	}

	/**
	 * 根据机组id和时间查询指标值
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryZbz(String fid, String sj) {
		return zbjcDao.queryZbz(fid, sj);
	}

	/**
	 * 查询指标检测累计值
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryLjz(String fid, String sj) {
		return zbjcDao.queryLjz(fid, sj);
	}

	/**
	 * 分页查询指标检测历史数据
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistoryZbz(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		return zbjcDao.queryHistoryZbz(vo, querybo);
	}
	
	public String queryBlj(String startTime, String endTime,ZbjcQueryCondition querybo) throws BkmsException{
		return zbjcDao.queryBlj(startTime, endTime,querybo);
	}

	/**
	 * 分页查询设备检测历史数据
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistorySbjc(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		return zbjcDao.queryHistorySbjc(vo, querybo);
	}

	/**
	 * 查询设备检测历史数据
	 * 
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> querySbjcData(ZbjcQueryCondition querybo)
			throws BkmsException {
		return zbjcDao.querySbjcData(querybo);
	}

	/**
	 * 分页查询历史指标值
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistoryZbzInfo(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		return zbjcDao.queryHistoryZbzInfo(vo, querybo);
	}

	/**
	 * 根据机组id和能源种类查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySisByNyzl(String fid, String nyzl) {
		return zbjcDao.querySisByNyzl(fid, nyzl);
	}

	/**
	 * 根据条件查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySisByParams(Map<String, String> params) {
		return zbjcDao.querySisByParams(params);
	}

	/**
	 * 平衡图数据手工录入
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> querySglrByMonthMax(String fid, String sj)
			throws BkmsException {
		return zbjcDao.querySglrByMonthMax(fid, sj);
	}

	/**
	 * 平衡图数据自动
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdByMonthMax(String fid, String sj)
			throws BkmsException {
		return zbjcDao.queryZdByMonthMax(fid, sj);
	}

	/**
	 * 平衡图年数据 手工补录
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> querySglrByYearMax(String fid, String sj)
			throws BkmsException {
		return zbjcDao.querySglrByYearMax(fid, sj);
	}

	/**
	 * 平衡图年数据 自动
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdByYearMax(String fid, String sj)
			throws BkmsException {
		return zbjcDao.queryZdByYearMax(fid, sj);
	}

	/**
	 * 实时监测动态数据手工补录数据
	 * 
	 * @param fid
	 *            机组id
	 * @param sj
	 *            时间
	 * @param nyzl
	 *            能源种类
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> querySglrSumNy(String fid, String sj,
			String[] nyzl) throws BkmsException {
		return zbjcDao.querySglrSumNy(fid, sj, nyzl);
	}

	/**
	 * 实时监测动态数据自动提取数据
	 * 
	 * @param fid
	 *            机组id
	 * @param sj
	 *            时间
	 * @param nyzl
	 *            能源种类
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdSumNy(String fid, String sj,
			String[] nyzl) throws BkmsException {
		return zbjcDao.queryZdSumNy(fid, sj, nyzl);
	}

	/**
	 * 指标检测使用查询机组
	 * 
	 * @param setId
	 * @param superId
	 * @param itemSpell
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeJzZbjc(String setId, String superId)
			throws RollbackableException {
		return zbjcDao.queryCodeJzZbjc(setId, superId);
	}

	/**
	 * 正东平衡图查询面积
	 * 
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdMj(String sj) throws BkmsException {
		return zbjcDao.queryZdMj(sj);
	}

	/**
	 * 能源监测使用
	 * 
	 * @param setId
	 * @param superId
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeNyjc(String setId, String superId)
			throws RollbackableException {
		return zbjcDao.queryCodeNyjc(setId, superId);
	}

	/**
	 * 根据设备id查询sis
	 * 
	 * @param sbid
	 * @return
	 * @throws RollbackableException
	 */
	public List querySisBySb(String sbid) throws RollbackableException {
		return zbjcDao.querySisBySb(sbid);
	}

	/**
	 * 根据机组id查询设备
	 * 
	 * @param fid
	 * @return
	 * @throws RollbackableException
	 */
	public List querySbByfid(String fid) throws RollbackableException {
		return zbjcDao.querySbByfid(fid);
	}

	/**
	 * 查询能效检测
	 * 
	 * @return
	 */
	public DataQuery queryNcjcList(DataQuery query) {
		return zbjcDao.queryNcjcList(query);
	}

	/**
	 * 条件查询能效检测
	 * 
	 * @param starttime
	 * @param endtime
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<NxjcBO> queryListNxjcBy(String starttime, String endtime,
			NxjcBO bo) throws RollbackableException {
		return zbjcDao.queryListNxjcBy(starttime, endtime, bo);
	}

	/**
	 * 园区电表数据查询
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryParkMeter(DataQuery query) {
		return zbjcDao.queryParkMeter(query);
	}

	/**
	 * 查询园区电表数据
	 * 
	 * @param times
	 * @param tenant
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryParkMeter(String times, String tenant)
			throws BkmsException {
		StringBuffer sql = new StringBuffer(
				"select TENANTNAME,METERNO,NUMERICDATE,PEAKVALUE,VALLEYVALUE,FAIRVALUE,POINTVALUE,TOTALVALUE,RATE from ZDEP_PARKMETER where 1=1");
		if (!times.equals("")) {
			sql.append(" and NUMERICDATE = '" + times + "' ");
		}
		if (!tenant.equals("")) {
			sql.append(" and TENANTNAME like '%" + tenant + "%'");
		}
		sql.append(" order by NUMERICDATE desc");
		return zbjcDao.queryListBySql(sql.toString());
	}

	/**
	 * 查询园区电表
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryParkMeterWatch(DataQuery query) {
		return zbjcDao.queryParkMeterWatch(query);
	}
	/**
	 * 更新园区电表信息
	 * @param bo
	 * @throws RollbackableException 
	 */
	public void editYqdb(YqdbBO bo) throws RollbackableException{
		if(bo.getYqdb_id()!=null&&!bo.getYqdb_id().equals("")){ //修改
			bo.setYqdb_czsj(Tools.getSysDate("yyyy-MM-dd HH:mm"));
			zbjcDao.updateBo(bo.getYqdb_id(), bo);
		}else {
			bo.setYqdb_id(SequenceGenerator.getUUID());
			bo.setYqdb_czsj(Tools.getSysDate("yyyy-MM-dd HH:mm"));
			zbjcDao.createBo(bo);
		}
	}
	/**
	 * 条件查询园区电表
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YqdbBO> queryYqdb(YqdbBO bo) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from YqdbBO where 1=1");
		if(bo.getYqdb_id()!=null&&!bo.getYqdb_id().equals("")){
			hql.append(" and yqdb_id = '"+bo.getYqdb_id()+"' ");
		}
		return zbjcDao.queryHqlList(hql.toString()); 
	}
	/**
	 * 删除园区电表
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteYqdb(YqdbBO bo) throws RollbackableException{
		zbjcDao.deleteBo(bo);
	}
	
	public void nxjc_switch() throws NumberFormatException, RollbackableException, BkmsException, ParseException{
		if("true".equals(Float.parseFloat(StaticVariable.get(PmsConstants.SBJC_VALUE)))){
			saveTimingCountlj();
		}
	}

	/**
	 * 定时计算累计
	 * 
	 * @throws ParseException
	 * @throws RollbackableException
	 */
	public void saveTimingCountlj() throws ParseException,
			RollbackableException {
		String endtime = Tools.getSysDate("yyyy-MM-dd HH:mm"); // 当前班结束时间
		// if((endtime.substring(11, 16).equals("07:46")||endtime.substring(11,
		// 16).equals("15:46"))||endtime.substring(11, 16).equals("23:46")){
		endtime = Tools.minusMinute(endtime + ":00", -1).substring(0, 16);// 定时任务为46分执行，减一分钟
		// String endtime = "2017-12-31 23:45"; //当前班结束时间
		// String oldtime = Tools.modifiedHour(endtime, -16)+":00"; //上个班结束时间
		String starttime = Tools.modifiedHour(endtime, -8) + ":00"; // 当前班开始时间
		// 查询上个班累计值sql
		String queryoldljSql = "select * from EAST_ZBJCLJZ where ZBJCLJ_SJ = '"
				+ starttime.substring(0, 16) + "'";
		// 上个班累计值
		List<Map<String, Object>> oldzbzlist = zbjcDao.getJdbcTemplate()
				.queryForList(queryoldljSql);
		// 统计班累计sql
		String queryCountLjSql = "select sis.ynsb_id ,a.sisvalue-b.sisvalue as sumval from"
				+ "  east_ynsbsis sis,"
				+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
				+ endtime
				+ ":00' )a,"
				+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
				+ starttime
				+ "')b"
				+ " where  sis.ynsbsis_islj='1'and sis.ynsbsis_bs = a.sisid and sis.ynsbsis_bs=b.sisid";
		// 统计班累计
		List<Map<String, Object>> bljlist = zbjcDao.getJdbcTemplate()
				.queryForList(queryCountLjSql);

		// 每日最后一天
		if (endtime.substring(11, 16).equals("23:45")) {
			String startrtime = Tools.modifiedHour(endtime, -24) + ":00"; // 日累计开始时间
			// 统计日累计sql
			String queryCountRLjSql = "select sis.ynsb_id ,a.sisvalue-b.sisvalue as sumval from"
					+ "  east_ynsbsis sis,"
					+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
					+ endtime
					+ ":00' )a,"
					+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
					+ startrtime
					+ "')b"
					+ " where  sis.ynsbsis_islj='1'and sis.ynsbsis_bs = a.sisid and sis.ynsbsis_bs=b.sisid";
			// 日累计
			List<Map<String, Object>> rljlist = zbjcDao.getJdbcTemplate()
					.queryForList(queryCountRLjSql);

			// 是否为本月最后一天
			if (Tools.judgeMonthEnd(endtime)) {
				String startYtime = Tools.getUpMonthEnd() + " 23:45:00";// 前月开始统计时间
				// 统计月累计sql
				String queryCountYLjSql = "select sis.ynsb_id ,a.sisvalue-b.sisvalue as sumval from"
						+ "  east_ynsbsis sis,"
						+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
						+ endtime
						+ ":00' )a,"
						+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
						+ startYtime
						+ "')b"
						+ " where  sis.ynsbsis_islj='1'and sis.ynsbsis_bs = a.sisid and sis.ynsbsis_bs=b.sisid";
				// 月累计
				List<Map<String, Object>> yljlist = zbjcDao.getJdbcTemplate()
						.queryForList(queryCountYLjSql);

				if (endtime.substring(5, 10).equals("12-31")) {// 是否年的最后一天
					String startntime = (Integer.parseInt(endtime.substring(0,
							4)) - 1) + "-12-31 23:45:00";
					// 统计年累计sql
					String queryCountNLjSql = "select sis.ynsb_id ,a.sisvalue-b.sisvalue as sumval from"
							+ "  east_ynsbsis sis,"
							+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
							+ endtime
							+ ":00' )a,"
							+ " (select zb.sisid,zb.sisvalue from zd_test2 zb where zb.sistime='"
							+ startntime
							+ "')b"
							+ " where  sis.ynsbsis_islj='1'and sis.ynsbsis_bs = a.sisid and sis.ynsbsis_bs=b.sisid";
					// 年累计
					List<Map<String, Object>> nljlist = zbjcDao
							.getJdbcTemplate().queryForList(queryCountNLjSql);
					// 新增班累计
					for (Map<String, Object> bljmap : bljlist) {
						ZbjcljzBO bo = new ZbjcljzBO();
						bo.setYnsb_id(bljmap.get("ynsb_id").toString());
						bo.setZbjclj_id(SequenceGenerator.getUUID());
						bo.setZbjclj_blj(Tools.filterNull(bljmap.get("sumval")));
						bo.setZbjclj_sj(endtime);
						// 获取年累计
						for (Map<String, Object> nljmap : nljlist) {
							if (nljmap.get("ynsb_id").equals(
									bljmap.get("ynsb_id"))) {
								bo.setZbjclj_nlj(Tools.filterNull(nljmap
										.get("sumval")));
								break;
							}
						}
						// 获取日累计
						for (Map<String, Object> rljmap : rljlist) {
							if (rljmap.get("ynsb_id").equals(
									bljmap.get("ynsb_id"))) {
								bo.setZbjclj_rlj(Tools.filterNull(rljmap
										.get("sumval")));
								break;
							}
						}
						// 获取月累计
						for (Map<String, Object> yljmap : yljlist) {
							if (yljmap.get("ynsb_id").equals(
									bljmap.get("ynsb_id"))) {
								bo.setZbjclj_ylj(Tools.filterNull(yljmap
										.get("sumval")));
								break;
							}
						}
						zbjcDao.createBo(bo);
					}
				} else {
					// 新增班累计
					for (Map<String, Object> bljmap : bljlist) {
						ZbjcljzBO bo = new ZbjcljzBO();
						bo.setYnsb_id(bljmap.get("ynsb_id").toString());
						bo.setZbjclj_id(SequenceGenerator.getUUID());
						bo.setZbjclj_blj(Tools.filterNull(bljmap.get("sumval")));
						bo.setZbjclj_sj(endtime);
						// 循环获取上个班的年累计
						for (Map<String, Object> oldmap : oldzbzlist) {
							if (oldmap.get("ynsb_id").equals(
									bljmap.get("ynsb_id"))) {
								bo.setZbjclj_nlj(Tools.filterNull(oldmap
										.get("zbjclj_nlj")));
								break;
							}
						}
						// 获取日累计
						for (Map<String, Object> rljmap : rljlist) {
							if (rljmap.get("ynsb_id").equals(
									bljmap.get("ynsb_id"))) {
								bo.setZbjclj_rlj(Tools.filterNull(rljmap
										.get("sumval")));
								break;
							}
						}
						// 获取月累计
						for (Map<String, Object> yljmap : yljlist) {
							if (yljmap.get("ynsb_id").equals(
									bljmap.get("ynsb_id"))) {
								bo.setZbjclj_ylj(Tools.filterNull(yljmap
										.get("sumval")));
								break;
							}
						}
						zbjcDao.createBo(bo);
					}
				}

			} else {
				// 新增班累计
				for (Map<String, Object> bljmap : bljlist) {
					ZbjcljzBO bo = new ZbjcljzBO();
					bo.setYnsb_id(bljmap.get("ynsb_id").toString());
					bo.setZbjclj_id(SequenceGenerator.getUUID());
					bo.setZbjclj_blj(Tools.filterNull(bljmap.get("sumval")));
					bo.setZbjclj_sj(endtime);
					// 循环获取上个班的月累计年累计
					for (Map<String, Object> oldmap : oldzbzlist) {
						if (oldmap.get("ynsb_id").equals(bljmap.get("ynsb_id"))) {
							bo.setZbjclj_ylj(Tools.filterNull(oldmap
									.get("zbjclj_ylj")));
							bo.setZbjclj_nlj(Tools.filterNull(oldmap
									.get("zbjclj_nlj")));
							break;
						}
					}
					// 获取日累计
					for (Map<String, Object> rljmap : rljlist) {
						if (rljmap.get("ynsb_id").equals(bljmap.get("ynsb_id"))) {
							bo.setZbjclj_rlj(Tools.filterNull(rljmap
									.get("sumval")));
							break;
						}
					}
					zbjcDao.createBo(bo);
				}
			}
		} else {
			// 新增班累计
			for (Map<String, Object> bljmap : bljlist) {
				ZbjcljzBO bo = new ZbjcljzBO();
				bo.setYnsb_id(bljmap.get("ynsb_id").toString());
				bo.setZbjclj_id(SequenceGenerator.getUUID());
				bo.setZbjclj_blj(bljmap.get("sumval").toString());
				bo.setZbjclj_sj(endtime);
				// 获取日累计月累计年累计
				for (Map<String, Object> oldmap : oldzbzlist) {
					if (oldmap.get("ynsb_id").equals(bljmap.get("ynsb_id"))) {
						bo.setZbjclj_rlj(Tools.filterNull(oldmap
								.get("zbjclj_rlj")));
						bo.setZbjclj_ylj(Tools.filterNull(oldmap
								.get("zbjclj_ylj")));
						bo.setZbjclj_nlj(Tools.filterNull(oldmap
								.get("zbjclj_nlj")));
						break;
					}
				}
				zbjcDao.createBo(bo);
			}
		}
		// }else{
		// String querysj = "";
		// if(Tools.compareTo(endtime.substring(11, 16), "07:46", "HH:mm")==-1){
		// querysj = Tools.modifiedHour(endtime.substring(0, 10)+" 07:45",-8);
		// }else if(Tools.compareTo(endtime.substring(11, 16), "15:45",
		// "HH:mm")==-1){
		// querysj = endtime.substring(0, 10)+" 07:45";
		// }else if(Tools.compareTo(endtime.substring(11, 16), "23:45",
		// "HH:mm")==-1){
		// querysj = endtime.substring(0, 10)+" 15:45";
		// }
		//
		// String hql = "from ZbjcljzBO where zbjclj_sj = '"+querysj+"'";
		// List ljlist = zbjcDao.queryHqlList(hql);
		// if(ljlist.size()>0){
		// for(int i = 0;i<ljlist.size();i++){
		// ZbjcljzBO bo = (ZbjcljzBO)ljlist.get(i);
		// String sql =
		// "insert into EAST_ZBJCLJZ(ZBJCLJ_ID,ZBJCLJ_BLJ,ZBJCLJ_RLJ,ZBJCLJ_YLJ,ZBJCLJ_NLJ,ZBJCLJ_SJ,YNSB_ID)"
		// +
		// " VALUES('"+SequenceGenerator.getUUID()+"','"+bo.getZbjclj_blj()+"',"
		// +
		// "'"+bo.getZbjclj_rlj()+"','"+bo.getZbjclj_ylj()+"','"+bo.getZbjclj_nlj()+"'"
		// +
		// ",'"+endtime+"','"+bo.getYnsb_id()+"')";
		// zbjcDao.getJdbcTemplate().execute(sql);
		// }
		// }
		// }

	}

	/**
	 * 实时更新能效检测（一分钟）
	 * 
	 * @throws ParseException
	 * @throws RollbackableException
	 */
	public void saveTimingNx() throws ParseException, RollbackableException {
		String newtime = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
		String counttime = Tools.minusMinute(newtime, -1); // 减一分钟，查询上一分钟数据，避开sis系统推送数据时间差
		// 恒东热电1#蒸汽锅炉(汽耗比)
		String hdrdqhbsql1 = "select a.sisvalue as avalue,b.sisvalue as bvalue from (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ONEGLRQB
				+ "' and sis.ynsbsis_id='402880355ffc4cab015ffc72af040002' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ONEGLZQLL
				+ "' and sis.ynsbsis_id='402880355ffc4cab015ffc773729000a' and jc.sistime='"
				+ counttime + "'" + " and jc.sisid=sis.ynsbsis_bs)b";
		// 恒东热电2#蒸汽锅炉（汽耗比）
		String hdrdqhbsql2 = "select a.sisvalue as avalue,b.sisvalue as bvalue from (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.TWOGLRQB
				+ "' and sis.ynsbsis_id='402880355ffc4cab015ffc73a4930006' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.TWOGLZQLL
				+ "' and sis.ynsbsis_id='402880355ffc4cab015ffc781568000e' and jc.sistime='"
				+ counttime + "'" + " and jc.sisid=sis.ynsbsis_bs)b";
		// 动南1#热水炉燃气表(汽耗比)
		String dnqhbsql1 = "select a.sisvalue as avalue,b.sisvalue as bvalue from (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ONERSLRQ
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e941a2a0076' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ONERSLRS
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e9ad4f30087' and jc.sistime='"
				+ counttime + "'" + " and jc.sisid=sis.ynsbsis_bs)b";
		// 动南2#热水炉燃气表(汽耗比)
		String dnqhbsql2 = "select a.sisvalue as avalue,b.sisvalue as bvalue from (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.TWORSLRQ
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e94e12c007a' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.TWORSLRS
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e9c8d1d008d' and jc.sistime='"
				+ counttime + "'" + " and jc.sisid=sis.ynsbsis_bs)b";
		// 动南3#热水炉燃气表（汽耗比）
		String dnqhbsql3 = "select a.sisvalue as avalue,b.sisvalue as bvalue from (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.THREERSLRQ
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e958c8a007e' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.THREERSLRS
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e9e06620093' and jc.sistime='"
				+ counttime + "'" + " and jc.sisid=sis.ynsbsis_bs)b";
		// 动南4#热水炉燃气表（汽耗比）
		String dnqhbsql4 = "select a.sisvalue as avalue,b.sisvalue as bvalue from (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.FOURRSLRQ
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e963a5c0082' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.FOURRSLRS
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e9f261e0099' and jc.sistime='"
				+ counttime + "'" + " and jc.sisid=sis.ynsbsis_bs)b";
		// 动南1#、2#蒸汽炉燃气(汽耗比)
		String dnqhbsql5 = " select  nvl(a.sisvalue,0)+nvl(b.sisvalue,0) as avalue,c.sisvalue as bvalue from"
				+ " (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ONEZQLRQ
				+ "' and sis.ynsbsis_id='4028803560699814016069a5dcdf0002' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.TWOZQLRQ
				+ "' and sis.ynsbsis_id='4028803560699814016069a7cbcb0006'  and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)b,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ZQLZQB
				+ "' and sis.ynsbsis_id='4028803560699814016069b4195d0020' and jc.sistime='"
				+ counttime + "' and jc.sisid=sis.ynsbsis_bs)c";
		// 动南1#、2#燃机
		String dnqhbsql6 = " select  nvl(a.sisvalue,0)+nvl(b.sisvalue,0) as avalue,"
				+ "nvl(c.sisvalue,0)+nvl(d.sisvalue,0) as bvalue from"
				+ " (select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ONEDLNCRJ
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e1e6b680002' and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)a,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.TWODLNCRJ
				+ "' and sis.ynsbsis_id='402894b8606dfb6a01606e1f61ef0006'  and jc.sistime='"
				+ counttime
				+ "'"
				+ " and jc.sisid=sis.ynsbsis_bs)b,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.ONEJFD
				+ "' and sis.ynsbsis_id='2c90948460a103970160bb0f7504044a' and jc.sistime='"
				+ counttime
				+ "' and jc.sisid=sis.ynsbsis_bs)c,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.TWOJFD
				+ "' and sis.ynsbsis_id='2c90948460a103970160bb111c8c044b' and jc.sistime='"
				+ counttime
				+ "' and jc.sisid=sis.ynsbsis_bs)d,(select sis.ynsb_id,sis.ynsbsis_bs,jc.sisvalue from east_ynsbsis sis,zd_test2 jc where"
				+ " sis.ynsb_id='"
				+ SsjcConstants.THREEJFD
				+ "' and sis.ynsbsis_id='2c90948460a103970160bb11ca12044c' and jc.sistime='"
				+ counttime + "' and jc.sisid=sis.ynsbsis_bs)e";
		// 恒东热电二级水表(用水指标)
		String hdrdryszbsql = "select nvl(sum(jc.sisvalue),'0.00') as sumval from east_ynsb sb,east_ynsbsis sis,zd_test2 jc "
				+ "  where sb.ynsb_fid='"
				+ SsjcConstants.YNSB_HDRDCJ
				+ "' and sb.ynsb_nyzl='4' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_mc='瞬时流量'"
				+ " and jc.sistime='"
				+ counttime
				+ "' and jc.sisid=sis.ynsbsis_bs";
		// 动力南厂二级水表(用水指标)
		String dnryszbsql = "select nvl(sum(jc.sisvalue),'0.00') as sumval from east_ynsb sb,east_ynsbsis sis,zd_test2 jc "
				+ "  where sb.ynsb_fid='"
				+ SsjcConstants.YNSB_DLCJYN
				+ "' and sb.ynsb_nyzl='4' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_mc='瞬时流量'"
				+ " and jc.sistime='"
				+ counttime
				+ "' and jc.sisid=sis.ynsbsis_bs";

		BkmsJdbcTemplate jdbcTemplate = zbjcDao.getJdbcTemplate();
		// 恒东热电1#蒸汽锅炉(汽耗比)
		List<Map<String, Object>> hdrdqhblist1 = jdbcTemplate
				.queryForList(hdrdqhbsql1);
		// 恒东热电2#蒸汽锅炉（汽耗比）
		List<Map<String, Object>> hdrdqhblist2 = jdbcTemplate
				.queryForList(hdrdqhbsql2);
		// 动南1#热水炉燃气表(汽耗比)
		List<Map<String, Object>> dnqhblist1 = jdbcTemplate
				.queryForList(dnqhbsql1);
		// 动南2#热水炉燃气表(汽耗比)
		List<Map<String, Object>> dnqhblist2 = jdbcTemplate
				.queryForList(dnqhbsql2);
		// 动南3#热水炉燃气表(汽耗比)
		List<Map<String, Object>> dnqhblist3 = jdbcTemplate
				.queryForList(dnqhbsql3);
		// 动南4#热水炉燃气表(汽耗比)
		List<Map<String, Object>> dnqhblist4 = jdbcTemplate
				.queryForList(dnqhbsql4);
		// 动南1#、2#蒸汽炉燃气(汽耗比)
		List<Map<String, Object>> dnqhblist5 = jdbcTemplate
				.queryForList(dnqhbsql5);
		// 动南1#、2#燃机
		List<Map<String, Object>> dnqhblist6 = jdbcTemplate
				.queryForList(dnqhbsql6);
		// 恒东热电二级水表(用水指标)
		List<Map<String, Object>> hdrdryszblist = jdbcTemplate
				.queryForList(hdrdryszbsql);
		// 动力南厂二级水表(用水指标)
		List<Map<String, Object>> dnryszblist = jdbcTemplate
				.queryForList(dnryszbsql);

		// 恒东热电1#蒸汽锅炉(汽耗比)
		if (hdrdqhblist1.size() > 0) {
			Map<String, Object> map = hdrdqhblist1.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/t");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("恒东热电1#蒸汽锅炉");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f0aa0162a2f0aa610000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					//BigDecimal b = bvalue.divide(new BigDecimal(3.6) , 3 ,BigDecimal.ROUND_HALF_UP);
					BigDecimal divide = avalue.divide(bvalue, 3 ,BigDecimal.ROUND_HALF_UP);
					//BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(3.6), 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 恒东热电2#蒸汽锅炉（汽耗比）
		if (hdrdqhblist2.size() > 0) {
			Map<String, Object> map = hdrdqhblist2.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/t");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("恒东热电2#蒸汽锅炉");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f26c0162a2f26cc50000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					//BigDecimal b = bvalue.divide(new BigDecimal(3.6) , 3 ,BigDecimal.ROUND_HALF_UP);
					BigDecimal divide = avalue.divide(bvalue, 3 ,BigDecimal.ROUND_HALF_UP);
					//BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(3.6), 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 动南1#热水炉燃气表(汽耗比)
		if (dnqhblist1.size() > 0) {
			Map<String, Object> map = dnqhblist1.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/MW");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("动南1#热水炉燃气表");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f2f10162a2f2f1c40000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					BigDecimal b = bvalue.divide(new BigDecimal(3.6) , 3 ,BigDecimal.ROUND_HALF_UP);
					BigDecimal divide = avalue.divide(b, 3 ,BigDecimal.ROUND_HALF_UP);
					//BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(3.6), 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 动南2#热水炉燃气表(汽耗比)
		if (dnqhblist2.size() > 0) {
			Map<String, Object> map = dnqhblist2.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/MW");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("动南2#热水炉燃气表");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f3f70162a2f3f7d30000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					BigDecimal b = bvalue.divide(new BigDecimal(3.6) , 3 ,BigDecimal.ROUND_HALF_UP);
					BigDecimal divide = avalue.divide(b, 3 ,BigDecimal.ROUND_HALF_UP);
					//BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(3.6), 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 动南3#热水炉燃气表(汽耗比)
		if (dnqhblist3.size() > 0) {
			Map<String, Object> map = dnqhblist3.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/MW");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("动南3#热水炉燃气表");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f48d0162a2f48e060000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					BigDecimal b = bvalue.divide(new BigDecimal(3.6) , 3 ,BigDecimal.ROUND_HALF_UP);
					BigDecimal divide = avalue.divide(b, 3 ,BigDecimal.ROUND_HALF_UP);
					//BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(3.6), 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 动南4#热水炉燃气表(汽耗比)
		if (dnqhblist4.size() > 0) {
			Map<String, Object> map = dnqhblist4.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/MW");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("动南4#热水炉燃气表");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f4e30162a2f4e3f30000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					BigDecimal b = bvalue.divide(new BigDecimal(3.6) , 3 ,BigDecimal.ROUND_HALF_UP);
					BigDecimal divide = avalue.divide(b, 3 ,BigDecimal.ROUND_HALF_UP);
					//BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(3.6), 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 动南1#、2#蒸汽炉燃气(汽耗比)
		if (dnqhblist5.size() > 0) {
			Map<String, Object> map = dnqhblist5.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/t");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("动南燃气蒸汽炉");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f55b0162a2f55c0a0000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 动南1#、2#燃机（汽耗比）
		if (dnqhblist6.size() > 0) {
			Map<String, Object> map = dnqhblist6.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("Nm3/kWh");
			bo.setNxjc_lx("1"); // 类型
			bo.setNxjc_sb("动南1#、2#燃机");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2f5b50162a2f5b6130000");
			if (map.get("bvalue") != null&& Double.valueOf(map.get("bvalue").toString()) != 0.0) {
				BigDecimal bvalue = new BigDecimal(map.get("bvalue").toString());
				bvalue = bvalue.multiply(new BigDecimal(1000));
				if (map.get("avalue") != null) {
					BigDecimal avalue = new BigDecimal(Double.parseDouble(map.get("avalue").toString().trim()));
					BigDecimal divide = avalue.divide(bvalue, 3,BigDecimal.ROUND_HALF_UP);
					if(divide.compareTo(BigDecimal.ZERO)==1){ //大于0
						bo.setNxjc_val(divide.toString());
					}else{
						bo.setNxjc_val("0.000");
					}
				} else {
					bo.setNxjc_val("0.000");
				}
			} else {
				bo.setNxjc_val("0.000");
			}
			zbjcDao.createBo(bo);
		}
		// 恒东热电二级水表(用水指标)
		if (hdrdryszblist.size() > 0) {
			Map<String, Object> map = hdrdryszblist.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("km3");
			bo.setNxjc_lx("2"); // 类型
			bo.setNxjc_sb("恒东热电二级水表");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2fc890162a2fc89a40000");
			bo.setNxjc_val(map.get("sumval").toString());
			zbjcDao.createBo(bo);
		}
		// 动力南厂二级水表(用水指标)
		if (dnryszblist.size() > 0) {
			Map<String, Object> map = dnryszblist.get(0);
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_id(SequenceGenerator.getUUID());
			bo.setNxjc_dw("km3");
			bo.setNxjc_lx("2"); // 类型
			bo.setNxjc_sb("动力南厂二级水表");
			bo.setNxjc_sj(counttime);
			bo.setNxjc_sbid("402894b862a2fcf90162a2fcf9e70000");
			bo.setNxjc_val(map.get("sumval").toString());
			zbjcDao.createBo(bo);
		}

	}

	/**
	 * 查询单元机组信息
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryDyjzList(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(query,
						" EAST_YNSB SB LEFT JOIN EAST_YNSBSIS SIS ON SB.YNSB_ID=SIS.YNSB_ID AND SIS.YNSBSIS_ISLJ='1' LEFT JOIN ZD_TEST2 ZD ON SIS.YNSBSIS_BS=ZD.SISID ",
						"ZD.ID AS ID,ZD.SISVALUE AS SISVALUE,ZD.SISTIME AS SISTIME,SB.YNSB_ID AS YNSB_ID,SB.YNSB_GLBH AS YNSB_GLBH ");
		return dbQuery;
	}

	/**
	 * 实时数据zd_test2
	 * 
	 * @param id
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZdTest2BO> queryZdTest2BoById(String id)
			throws RollbackableException {
		return zbjcDao.queryZdTest2BoById(id);
	}
	
	public ZdTest2BO queryZdTest2BoBySisidAndSistime(String sisid, String sistime) throws RollbackableException{
		return zbjcDao.queryZdTest2BoBySisidAndSistime(sisid, sistime);
	}

	/**
	 * 编辑实时数据zd_test2
	 * 
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editZdTest2(ZdTest2BO bo) throws RollbackableException {
		if (bo.getId() != null && !"".equals(bo.getId())) {
			zbjcDao.updateBo(bo.getId(), bo);
		}
	}
}
