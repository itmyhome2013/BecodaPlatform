package com.becoda.bkms.east.ssjc.ucc.impl;

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
import com.becoda.bkms.east.ssjc.service.ZbjcService;
import com.becoda.bkms.east.ssjc.ucc.IZbjcUCC;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YqdbBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>
 * Description:指标检测UCC接口
 * </p>
 * 
 * @author liu_hq
 * @date 2017-11-1
 * 
 */
public class ZbjcUCCImpl implements IZbjcUCC {
	private ZbjcService zbjcService;

	public ZbjcService getZbjcService() {
		return zbjcService;
	}

	public void setZbjcService(ZbjcService zbjcService) {
		this.zbjcService = zbjcService;
	}

	public List<CodeItemBO> queryCode(String setId, String superId,
			String itemSpell) throws RollbackableException {
		return zbjcService.queryCode(setId, superId, itemSpell);
	}

	public List<Map<String, Object>> queryType(String nyzl, String nyjc,
			String fid) {
		return zbjcService.queryType(nyzl, nyjc, fid);
	}

	public List<Map<String, Object>> queryYnsb(String fid)
			throws RollbackableException {
		return zbjcService.queryYnsb(fid);
	}

	public List<ZbjcBO> queryZbjc(String sj) throws RollbackableException {
		return zbjcService.queryZbjc(sj);
	}

	public List<CodeItemBO> queryCodeIn(String setId, String superId,
			String itemSpell, String itemAbbr) throws RollbackableException {
		return zbjcService.queryCodeIn(setId, superId, itemSpell, itemAbbr);
	}

	public List<Map<String, Object>> querySis(String fid) {
		return zbjcService.querySis(fid);
	}

	public List<Map<String, Object>> queryZbz(String fid, String sj) {
		return zbjcService.queryZbz(fid, sj);
	}

	public List<Map<String, Object>> queryLjz(String fid, String sj) {
		return zbjcService.queryLjz(fid, sj);
	}

	public void saveTimingCountlj() throws ParseException,
			RollbackableException {
		zbjcService.saveTimingCountlj();
	}

	public List<Map<String, String>> queryHistoryZbz(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		return zbjcService.queryHistoryZbz(vo, querybo);
	}
	
	public String queryBlj(String startTime, String endTime,ZbjcQueryCondition querybo) throws BkmsException{
		return zbjcService.queryBlj(startTime, endTime,querybo);
	}

	public List<Map<String, String>> queryHistoryZbzInfo(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		return zbjcService.queryHistoryZbzInfo(vo, querybo);
	}

	public List<Map<String, Object>> querySisByNyzl(String fid, String nyzl) {
		return zbjcService.querySisByNyzl(fid, nyzl);
	}

	public List<Map<String, Object>> querySisByParams(Map<String, String> params) {
		return zbjcService.querySisByParams(params);
	}

	public List<Map<String, Object>> querySglrByMonthMax(String fid, String sj)
			throws BkmsException {
		return zbjcService.querySglrByMonthMax(fid, sj);
	}

	public List<Map<String, Object>> queryZdByMonthMax(String fid, String sj)
			throws BkmsException {
		return zbjcService.queryZdByMonthMax(fid, sj);
	}

	public List<Map<String, Object>> querySglrByYearMax(String fid, String sj)
			throws BkmsException {
		return zbjcService.querySglrByYearMax(fid, sj);
	}

	public List<Map<String, Object>> queryZdByYearMax(String fid, String sj)
			throws BkmsException {
		return zbjcService.queryZdByYearMax(fid, sj);
	}

	public List<Map<String, Object>> querySglrSumNy(String fid, String sj,
			String[] nyzl) throws BkmsException {
		return zbjcService.querySglrSumNy(fid, sj, nyzl);
	}

	public List<Map<String, Object>> queryZdSumNy(String fid, String sj,
			String[] nyzl) throws BkmsException {
		return zbjcService.queryZdSumNy(fid, sj, nyzl);
	}

	public List<CodeItemBO> queryCodeJzZbjc(String setId, String superId)
			throws RollbackableException {
		return zbjcService.queryCodeJzZbjc(setId, superId);
	}

	public List<Map<String, Object>> queryZdMj(String sj) throws BkmsException {
		return zbjcService.queryZdMj(sj);
	}

	@Override
	public List<Map<String, String>> queryHistorySbjc(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		// TODO Auto-generated method stub
		return zbjcService.queryHistorySbjc(vo, querybo);
	}

	public List<CodeItemBO> queryCodeNyjc(String setId, String superId)
			throws RollbackableException {
		return zbjcService.queryCodeNyjc(setId, superId);
	}

	public List querySbByfid(String fid) throws RollbackableException {
		return zbjcService.querySbByfid(fid);
	}

	public List querySisBySb(String sbid) throws RollbackableException {
		return zbjcService.querySisBySb(sbid);
	}

	public void saveTimingNx() throws ParseException, RollbackableException {
		zbjcService.saveTimingNx();
	}
	
	public void nxjc_switch()   throws ParseException, BkmsException{
		zbjcService.nxjc_switch();
	}

	public DataQuery queryNcjcList(DataQuery query) {
		return zbjcService.queryNcjcList(query);
	}

	@Override
	public List<Map<String, String>> querySbjcData(ZbjcQueryCondition querybo)
			throws BkmsException {
		// TODO Auto-generated method stub
		return zbjcService.querySbjcData(querybo);
	}

	public List<NxjcBO> queryListNxjcBy(String starttime, String endtime,
			NxjcBO bo) throws RollbackableException {
		return zbjcService.queryListNxjcBy(starttime, endtime, bo);
	}

	@Override
	public List<YnsbBO> queryYnsbGlbh(String itemid)
			throws RollbackableException {
		// TODO Auto-generated method stub
		return zbjcService.queryYnsbGlbh(itemid);
	}

	@Override
	public List<CodeItemBO> queryCodeInSgbl(String setId, String superId,
			String qxDesc, String itemAbbr, String description)
			throws RollbackableException {
		// TODO Auto-generated method stub
		return zbjcService.queryCodeInSgbl(setId, superId, qxDesc, itemAbbr,
				description);
	}

	@Override
	public List<CodeItemBO> queryNameById(String setId, String itemId)
			throws RollbackableException {
		// TODO Auto-generated method stub
		return zbjcService.queryNameById(setId, itemId);
	}

	@Override
	public List<CodeItemBO> queryYjbjCodeIn(String setId, String superId,
			String itemSpell, String itemAbbr) throws RollbackableException {
		// TODO Auto-generated method stub
		return zbjcService.queryYjbjCodeIn(setId, superId, itemSpell, itemAbbr);
	}

	public DataQuery queryParkMeter(DataQuery query) {
		return zbjcService.queryParkMeter(query);
	}

	public List<Map<String, String>> queryParkMeter(String times, String tenant)
			throws BkmsException {
		return zbjcService.queryParkMeter(times, tenant);
	}

	public DataQuery queryParkMeterWatch(DataQuery query) {
		return zbjcService.queryParkMeterWatch(query);
	}

	@Override
	public List<YnsbBO> queryGlbhByNyzl(String itemid, String nyzl)
			throws RollbackableException {
		// TODO Auto-generated method stub
		return zbjcService.queryGlbhByNyzl(itemid, nyzl);
	}

	@Override
	public DataQuery queryDyjzList(DataQuery query) {
		// TODO Auto-generated method stub
		return zbjcService.queryDyjzList(query);
	}

	@Override
	public List<ZdTest2BO> queryZdTest2BoById(String id)
			throws RollbackableException {
		// TODO Auto-generated method stub
		return zbjcService.queryZdTest2BoById(id);
	}
	
	@Override
	public ZdTest2BO queryZdTest2BoBySisidAndSistime(String sisid, String sistime) throws RollbackableException{
		return zbjcService.queryZdTest2BoBySisidAndSistime(sisid, sistime);
	}

	@Override
	public void editZdTest2(ZdTest2BO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		zbjcService.editZdTest2(bo);
	}

	public void editYqdb(YqdbBO bo) throws RollbackableException {
		zbjcService.editYqdb(bo);
	}

	public List<YqdbBO> queryYqdb(YqdbBO bo) throws RollbackableException {
		return zbjcService.queryYqdb(bo);
	}

	public void deleteYqdb(YqdbBO bo) throws RollbackableException {
		zbjcService.deleteYqdb(bo);
	}

}
