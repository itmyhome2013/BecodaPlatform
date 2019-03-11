package com.becoda.bkms.east.ssjc.ucc;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.ssjc.pojo.NxjcBO;
import com.becoda.bkms.east.ssjc.pojo.ZbjcBO;
import com.becoda.bkms.east.ssjc.pojo.ZbjcQueryCondition;
import com.becoda.bkms.east.ssjc.pojo.ZdTest2BO;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YqdbBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>
 * Description: 指标检测UCC接口
 * </p>
 * 
 * @author liu_hq
 * @date 2017-11-1
 * 
 */
public interface IZbjcUCC {

	public List<CodeItemBO> queryCode(String setId, String superId,
			String itemSpell) throws RollbackableException;

	/**
	 * 查询能源种类
	 * 
	 * @param nyzl
	 * @param nyjc
	 * @return
	 */
	public List<Map<String, Object>> queryType(String nyzl, String nyjc,
			String fid);

	/**
	 * 查询用能设备
	 * 
	 * @param fid
	 * @return
	 * @throws RollbackableException
	 */
	public List<Map<String, Object>> queryYnsb(String fid)
			throws RollbackableException;

	/**
	 * 查询指标检测值
	 * 
	 * @param sj
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZbjcBO> queryZbjc(String sj) throws RollbackableException;

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
			String itemSpell, String itemAbbr) throws RollbackableException;

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
			String itemSpell, String itemAbbr) throws RollbackableException;

	/**
	 * 查询重点机组
	 * 
	 * @param setId
	 * @param itemId
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryNameById(String setId, String itemId)
			throws RollbackableException;

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
			throws RollbackableException;

	/**
	 * 用能设备计量编号
	 * 
	 * @param itemid
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryYnsbGlbh(String itemid)
			throws RollbackableException;

	/**
	 * 用能设备计量编号
	 * 
	 * @param itemid
	 * @param nyzl
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryGlbhByNyzl(String itemid, String nyzl)
			throws RollbackableException;

	/**
	 * 根据机组id查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySis(String fid);

	/**
	 * 根据机组id和时间查询指标值
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryZbz(String fid, String sj);

	/**
	 * 查询指标检测累计值
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryLjz(String fid, String sj);

	/**
	 * 定时计算班累计
	 * 
	 * @throws ParseException
	 * @throws RollbackableException
	 */
	public void saveTimingCountlj() throws ParseException,
			RollbackableException;

	/**
	 * 分页查询历史指标检测数据
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistoryZbz(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException;
	
	/**
	 * 查询能源监测班累计
	 * @param startTime 开始时间 
	 * @param endTime 结束时间
	 * @return
	 * @throws BkmsException
	 */
	public String queryBlj(String startTime, String endTime, ZbjcQueryCondition querybo) throws BkmsException;

	/**
	 * 分页查询设备监测历史数据
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistorySbjc(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException;

	/**
	 * 查询设备监测历史数据
	 * 
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> querySbjcData(ZbjcQueryCondition querybo)
			throws BkmsException;

	/**
	 * 分页查询历史指标值
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistoryZbzInfo(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException;

	/**
	 * 根据机组id和能源种类查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySisByNyzl(String fid, String nyzl);

	/**
	 * 根据条件查询sis
	 * 
	 * @return
	 */
	public List<Map<String, Object>> querySisByParams(Map<String, String> params);

	/**
	 * 平衡图数据手工补录
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> querySglrByMonthMax(String fid, String sj)
			throws BkmsException;

	/**
	 * 平衡图数据自动
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdByMonthMax(String fid, String sj)
			throws BkmsException;

	/**
	 * 平衡图年数据 手工补录
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> querySglrByYearMax(String fid, String sj)
			throws BkmsException;

	/**
	 * 平衡图年数据 自动
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdByYearMax(String fid, String sj)
			throws BkmsException;

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
			String[] nyzl) throws BkmsException;

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
			String[] nyzl) throws BkmsException;

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
			throws RollbackableException;

	/**
	 * 正东平衡图查询面积
	 * 
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdMj(String sj) throws BkmsException;

	/**
	 * 能源监测使用
	 * 
	 * @param setId
	 * @param superId
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeNyjc(String setId, String superId)
			throws RollbackableException;

	/**
	 * 根据机组id查询设备
	 * 
	 * @param fid
	 * @return
	 * @throws RollbackableException
	 */
	public List querySbByfid(String fid) throws RollbackableException;

	/**
	 * 根据设备id查询sis
	 * 
	 * @param sbid
	 * @return
	 * @throws RollbackableException
	 */
	public List querySisBySb(String sbid) throws RollbackableException;

	/**
	 * 实时更新能效检测（一分钟）
	 * 
	 * @throws ParseException
	 * @throws RollbackableException
	 */
	public void saveTimingNx() throws ParseException, BkmsException;
	
	/**
	 * 实时更新能效检测（开关）
	 * 
	 * @throws ParseException
	 * @throws RollbackableException
	 */
	public void nxjc_switch()   throws ParseException, BkmsException;

	/**
	 * 查询能效检测
	 * 
	 * @return
	 */
	public DataQuery queryNcjcList(DataQuery query);

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
			NxjcBO bo) throws RollbackableException;

	/**
	 * 园区电表数据查询
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryParkMeter(DataQuery query);

	/**
	 * 查询园区电表数据
	 * 
	 * @param times
	 * @param tenant
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryParkMeter(String times, String tenant)
			throws BkmsException;

	/**
	 * 查询园区电表
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryParkMeterWatch(DataQuery query);

	/**
	 * 查询单元机组信息
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryDyjzList(DataQuery query);

	/**
	 * 实时数据zd_test2
	 * 
	 * @param id
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZdTest2BO> queryZdTest2BoById(String id)
			throws RollbackableException;
	
	
	public ZdTest2BO queryZdTest2BoBySisidAndSistime(String sisid, String sistime) throws RollbackableException;

	/**
	 * 编辑实时数据zd_test2
	 * 
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editZdTest2(ZdTest2BO bo) throws RollbackableException;
	/**
	 * 更新园区电表信息
	 * @param bo
	 * @throws RollbackableException 
	 */
	public void editYqdb(YqdbBO bo) throws RollbackableException;
	/**
	 * 条件查询园区电表
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YqdbBO> queryYqdb(YqdbBO bo) throws RollbackableException;
	/**
	 * 删除园区电表
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteYqdb(YqdbBO bo) throws RollbackableException;
}
