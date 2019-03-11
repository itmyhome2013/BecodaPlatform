package com.becoda.bkms.east.gkdd.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.gkdd.pojo.DdrzBO;
import com.becoda.bkms.east.gkdd.service.DdrzService;
import com.becoda.bkms.east.gkdd.ucc.IDdrzUCC;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 调度日志UCC实现类</p>
 * @author liu_hq
 * @date 2017-9-14
 *
 */
public class DdrzUCCImpl implements IDdrzUCC{

	private DdrzService ddrzService;
	
	public DdrzService getDdrzService() {
		return ddrzService;
	}

	public void setDdrzService(DdrzService ddrzService) {
		this.ddrzService = ddrzService;
	}
	
	//编辑调度日志
	@Override
	public void editDdrz(DdrzBO bo,User user) throws BkmsException {
		ddrzService.editDdrz(bo,user);
	}
	//查询调度日志
	@Override
	public DataQuery queryList(DataQuery query) {
		return ddrzService.queryList(query);
	}

	@Override
	public List<DdrzBO> queryDdrzList(DdrzBO bo) throws RollbackableException {
		return ddrzService.queryDdrzList(bo);
	}

	@Override
	public void deleteDdrz(DdrzBO bo,User user) throws BkmsException{
		ddrzService.deleteDdrz(bo,user);
	}

}
