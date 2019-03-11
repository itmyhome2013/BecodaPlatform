package com.becoda.bkms.east.daxx.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.daxx.pojo.DaxxBO;
import com.becoda.bkms.east.daxx.service.DaxxService;
import com.becoda.bkms.east.daxx.ucc.IDaxxUCC;
import com.becoda.bkms.east.zdsb.pojo.BjqdBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSbscBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbStateBO;
import com.becoda.bkms.east.zdsb.service.ZdsbService;
import com.becoda.bkms.east.zdsb.ucc.IZdsbUCC;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description: 档案信息UCC实现类</p>
 * @author zhu_lw
 * @date 2018-04-10
 *
 */
public class DaxxUCCImpl implements IDaxxUCC{

	private DaxxService daxxService;
	public DaxxService getDaxxService() {
		return daxxService;
	}
	public void setDaxxService(DaxxService daxxService) {
		this.daxxService = daxxService;
	}
	@Override
	public DataQuery queryList(DataQuery query) {
		// TODO Auto-generated method stub
		return daxxService.queryList(query);
	}
	@Override
	public List findSsbm() {
		// TODO Auto-generated method stub
		return daxxService.findSsbm();
	}
	@Override
	public List findSsjz(String bmid) {
		// TODO Auto-generated method stub
		return daxxService.findSsjz(bmid);
	}
	@Override
	public List<DaxxBO> queryByBO(DaxxBO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		return daxxService.queryByBO(bo);
	}
	@Override
	public void editDaxx(DaxxBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		daxxService.editDaxx(bo,user);
	}
	@Override
	public List findTotalJz() {
		// TODO Auto-generated method stub
		return daxxService.findTotalJz();
	}
	@Override
	public void deleteDaxx(DaxxBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		daxxService.deleteDaxx(bo,user);
	}
	
}
