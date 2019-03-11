package com.becoda.bkms.east.tjbb.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.east.tjbb.pojo.YddlfpdBO;
import com.becoda.bkms.east.tjbb.service.TjbbService;
import com.becoda.bkms.east.tjbb.ucc.ITjbbUCC;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.farm.core.sql.query.DataQuery;

public class TjbbUCCImpl implements ITjbbUCC {

	private TjbbService tjbbService;

	@Override
	public DataQuery yddlfpd(DataQuery query) {
		// TODO Auto-generated method stub
		return tjbbService.yddlfpd(query);
	}
	
	@Override
	public DataQuery yddlfpd1(DataQuery query, String rq) {
		return tjbbService.yddlfpd1(query, rq);
	}
	@Override
	public DataQuery yddlfpd2(DataQuery query, String rq) {
		return tjbbService.yddlfpd2(query, rq);
	}
	@Override
	public DataQuery yddlfpd3(DataQuery query, String rq) {
		return tjbbService.yddlfpd3(query, rq);
	}
	@Override
	public DataQuery yddlfpd4(DataQuery query, String rq) {
		return tjbbService.yddlfpd4(query, rq);
	}
	@Override
	public DataQuery yddlfpd5(DataQuery query, String rq) {
		return tjbbService.yddlfpd5(query, rq);
	}
	
	@Override
	public DataQuery nyyhsjrbb(DataQuery query, String rq) {
		return tjbbService.nyyhsjrbb(query, rq);
	}
	
	@Override
	public DataQuery nyyhsjrbb1(DataQuery query, String rq) {
		return tjbbService.nyyhsjrbb1(query, rq);
	}
	@Override
	public DataQuery nyyhsjrbb2(DataQuery query, String rq) {
		return tjbbService.nyyhsjrbb2(query, rq);
	}
	@Override
	public DataQuery nyyhsjrbb3(DataQuery query, String rq) {
		return tjbbService.nyyhsjrbb3(query, rq);
	}
	@Override
	public DataQuery nyyhsjrbb4(DataQuery query, String rq) {
		return tjbbService.nyyhsjrbb4(query, rq);
	}
	@Override
	public DataQuery nyyhsjrbb5(DataQuery query, String rq) {
		return tjbbService.nyyhsjrbb5(query, rq);
	}
	
	@Override
	public DataQuery scjj(DataQuery query,String type) {
		// TODO Auto-generated method stub
		return tjbbService.scjj(query,type);
	}
	
	@Override
	public DataQuery sbjj(DataQuery query,String type) {
		// TODO Auto-generated method stub
		return tjbbService.sbjj(query,type);
	}
	
	@Override
	public DataQuery zbtx(DataQuery query,String type) {
		// TODO Auto-generated method stub
		return tjbbService.zbtx(query,type);
	}
	
	public TjbbService getTjbbService() {
		return tjbbService;
	}

	public void setTjbbService(TjbbService tjbbService) {
		this.tjbbService = tjbbService;
	}

	@Override
	public void editYddlfpd(YddlfpdBO bo) throws RollbackableException {
		// TODO Auto-generated method stub
		tjbbService.editYddlfpd(bo);
	}

	@Override
	public List<CodeItemBO> queryCodeItem() throws RollbackableException {
		return tjbbService.queryCodeItem();
	}

}
