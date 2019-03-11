package com.becoda.bkms.east.sjbl.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.sjbl.pojo.SjblBO;
import com.becoda.bkms.east.sjbl.service.SjblService;
import com.becoda.bkms.east.sjbl.ucc.ISjblUCC;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description: 手工补录UCC实现类</p>
 * @author zhu_lw
 * @date 2017-11-27
 *
 */
public class SjblUCCImpl implements ISjblUCC{

	private SjblService sjblService;
	
	public SjblService getSjblService() {
		return sjblService;
	}

	public void setSjblService(SjblService sjblService) {
		this.sjblService = sjblService;
	}

	@Override
	public DataQuery queryList(DataQuery query) {
		return sjblService.queryList(query);
	}

	@Override
	public List querySjblMsgForEdit(String zdjz, String nyzl,String tbrq,String sjbl_id)
			throws BkmsException {
		// TODO Auto-generated method stub
		return sjblService.querySjblMsgForEdit(zdjz, nyzl, tbrq,sjbl_id);
	}

	@Override
	public void addOrUpdate(SjblBO bo,User user) throws BkmsException{
		// TODO Auto-generated method stub
		sjblService.addOrUpdate(bo,user);
	}

	
}
