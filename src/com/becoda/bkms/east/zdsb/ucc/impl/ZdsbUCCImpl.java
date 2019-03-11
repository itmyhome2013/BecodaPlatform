package com.becoda.bkms.east.zdsb.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
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
 * <p>Description: 重点设备UCC实现类</p>
 * @author zhu_lw
 * @date 2017-12-22
 *
 */
public class ZdsbUCCImpl implements IZdsbUCC{

	private ZdsbService zdsbService;
	public ZdsbService getZdsbService() {
		return zdsbService;
	}

	public void setZdsbService(ZdsbService zdsbService) {
		this.zdsbService = zdsbService;
	}

	@Override
	public DataQuery queryList(DataQuery query) {
		// TODO Auto-generated method stub
		return zdsbService.queryList(query);
	}

	@Override
	public void editZdsb(ZdsbBO bo,User user) throws BkmsException {
		// TODO Auto-generated method stub
		zdsbService.editZdsb(bo,user);
	}

	@Override
	public List<ZdsbBO> queryByBO(ZdsbBO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		return zdsbService.queryByBO(bo);
	}

	@Override
	public void deleteZdsb(ZdsbBO bo,User user) throws BkmsException {
		// TODO Auto-generated method stub
		zdsbService.deleteZdsb(bo,user);
	}

	@Override
	public void deleteZdsbSisBySbid(String zdsbid,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.deleteZdsbSisBySbid(zdsbid,user);
	}

	@Override
	public DataQuery queryZdsbSis(DataQuery query) {
		// TODO Auto-generated method stub
		return zdsbService.queryZdsbSis(query);
	}

	@Override
	public void editZdsbSis(ZdsbSisBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.editZdsbSis(bo,user);
	}

	@Override
	public void deleteZdsbSis(ZdsbSisBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.deleteZdsbSis(bo,user);
	}
	public List<Map<String, Object>> queryZdsbByParams(Map<String,String> params) {
		return zdsbService.queryZdsbByParams(params);
	}

	@Override
	public void editZdsbState(ZdsbStateBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.editZdsbState(bo,user);
	}

	@Override
	public List<Map<String, Object>> queryZdsbSbjcByParams(String sj) {
		// TODO Auto-generated method stub
		return zdsbService.queryZdsbSbjcByParams(sj);
	}

	@Override
	public void editZdsbSbsc(ZdsbSbscBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.editZdsbSbsc(bo,user);
	}

	@Override
	public List<Map<String, Object>> queryZdsbSbsc() {
		// TODO Auto-generated method stub
		return zdsbService.queryZdsbSbsc();
	}

	@Override
	public void deleteZdsbSbscBySbid(String zdsbid,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.deleteZdsbSbscBySbid(zdsbid,user);
	}

	@Override
	public DataQuery queryBjqdList(DataQuery query) {
		// TODO Auto-generated method stub
		return zdsbService.queryBjqdList(query);
	}

	@Override
	public List<BjqdBO> queryBjqdByBO(BjqdBO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		return zdsbService.queryBjqdByBO(bo);
	}

	@Override
	public void editBjqd(BjqdBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.editBjqd(bo,user);
	}

	@Override
	public void deleteBjqd(BjqdBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		zdsbService.deleteBjqd(bo,user);
	}

	@Override
	public DataQuery queryStateList(DataQuery query) {
		// TODO Auto-generated method stub
		return zdsbService.queryStateList(query);
	}
}
