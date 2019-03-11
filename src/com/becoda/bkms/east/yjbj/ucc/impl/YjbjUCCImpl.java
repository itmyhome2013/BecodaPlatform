package com.becoda.bkms.east.yjbj.ucc.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.yjbj.pojo.NxjcYjbjSbBO;
import com.becoda.bkms.east.yjbj.pojo.YcbjBO;
import com.becoda.bkms.east.yjbj.pojo.YjbjBO;
import com.becoda.bkms.east.yjbj.service.YjbjService;
import com.becoda.bkms.east.yjbj.ucc.IYjbjUCC;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description: 预警报警UCC实现类</p>
 * @author zhu_lw
 * @date 2017-11-24
 *
 */
public class YjbjUCCImpl implements IYjbjUCC{

	private YjbjService yjbjService;
	public YjbjService getYjbjService() {
		return yjbjService;
	}
	
	public void setYjbjService(YjbjService yjbjService) {
		this.yjbjService = yjbjService;
	}

	@Override
	public DataQuery queryList(DataQuery query) {
		return yjbjService.queryList(query);
	}

	@Override
	public List<YnsbBO> queryByBO(YnsbBO bo) throws RollbackableException {
		return yjbjService.queryByBO(bo);
	}

	@Override
	public void editYnsb(YnsbBO bo) throws RollbackableException {
		yjbjService.editYnsb(bo);
	}

	@Override
	public void deleteYnsb(YnsbBO bo) throws RollbackableException {
		yjbjService.deleteYnsb(bo);
	}

	
	@Override
	public DataQuery queryYnsbSis(DataQuery query) {
		return yjbjService.queryYnsbSis(query);
	}

	@Override
	public void deleteYnsbSis(YnsbSisBO bo) throws RollbackableException {
		yjbjService.deleteYnsbSis(bo);
	}

	@Override
	public void deleteYnsbSisBySbid(String ynsbid) {
		yjbjService.deleteYnsbSisBySbid(ynsbid);
	}

	@Override
	public void editYjbj(YjbjBO yjbjBO,User user)
			throws BkmsException {
		// TODO Auto-generated method stub
		yjbjService.editYjbj(yjbjBO,user);
	}

	public void addYcbj(YcbjBO bo) throws RollbackableException {
		yjbjService.addYcbj(bo);
	}

	public void saveTimingYcbj() throws ParseException, RollbackableException {
		yjbjService.saveTimingYcbj();
	}

	public DataQuery queryYcbj(DataQuery query, String sj,String lx) {
		return yjbjService.queryYcbj(query, sj,lx);
	}

	public DataQuery queryHistoryYcbj(DataQuery query,String lx) {
		return yjbjService.queryHistoryYcbj(query,lx);
	}

	public List<Map<String, Object>> queryAlarmAlert() {
		return yjbjService.queryAlarmAlert();
	}

	public List<Map<String, Object>> queryYcbj(String starttime,String endtime, String sbbh,String lx) {
		return yjbjService.queryYcbj(starttime, endtime, sbbh,lx);
	}

	public List<Map<String, String>> queryBySj(String sj,String fid) throws BkmsException {
		return yjbjService.queryBySj(sj,fid);
	}

	@Override
	public List<YjbjBO> queryByYjbjBO(YjbjBO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		return yjbjService.queryByYjbjBO(bo);
	}

	@Override
	public List<ZdsbSisBO> queryBySisBO(ZdsbSisBO bo)
			throws RollbackableException {
		// TODO Auto-generated method stub
		return yjbjService.queryBySisBO(bo);
	}

	public DataQuery queryNxjcYcbjSb(DataQuery query) {
		return yjbjService.queryNxjcYcbjSb(query);
	}

	public void editNxjcYjbj(NxjcYjbjSbBO bo,User user) throws BkmsException {
		yjbjService.editNxjcYjbj(bo,user);
	}

	@Override
	public List<YcbjBO> queryYcbjByBO(YcbjBO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		return yjbjService.queryYcbjByBO(bo);
	}

	@Override
	public void editYcbj(YcbjBO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		yjbjService.editYcbj(bo);
	}
	
}
