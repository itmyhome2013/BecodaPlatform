package com.becoda.bkms.east.ynsb.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.east.ynsb.service.YnsbService;
import com.becoda.bkms.east.ynsb.ucc.IYnsbUCC;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description: 用能设备UCC实现类</p>
 * @author liu_hq
 * @date 2017-9-27
 *
 */
public class YnsbUCCImpl implements IYnsbUCC{

	private YnsbService ynsbService;

	public YnsbService getYnsbService() {
		return ynsbService;
	}

	public void setYnsbService(YnsbService ynsbService) {
		this.ynsbService = ynsbService;
	}

	public DataQuery queryList(DataQuery query) {
		return ynsbService.queryList(query);
	}

	public List<YnsbBO> queryByBO(YnsbBO bo) throws RollbackableException {
		return ynsbService.queryByBO(bo);
	}

	public void editYnsb(YnsbBO bo,User user) throws BkmsException {
		ynsbService.editYnsb(bo,user);
	}

	public void deleteYnsb(YnsbBO bo,User user) throws BkmsException {
		ynsbService.deleteYnsb(bo,user);
	}

	public void editYnsbSis(YnsbSisBO bo,User user) throws BkmsException {
		ynsbService.editYnsbSis(bo,user);
	}

	public DataQuery queryYnsbSis(DataQuery query) {
		return ynsbService.queryYnsbSis(query);
	}

	public void deleteYnsbSis(YnsbSisBO bo,User user) throws BkmsException {
		ynsbService.deleteYnsbSis(bo,user);
	}

	public void deleteYnsbSisBySbid(String ynsbid,User user) throws BkmsException {
		ynsbService.deleteYnsbSisBySbid(ynsbid,user);
	}

	public void editSisBySbid(String ynsbid, String nyzl,User user) throws BkmsException {
		ynsbService.editSisBySbid(ynsbid, nyzl,user);
	}

	public List<CodeItemBO> queryCodeItem(String itemids, String itemSpell)throws RollbackableException {
		return ynsbService.queryCodeItem(itemids, itemSpell);
	}

	public DataQuery queryYqdb(DataQuery query) {
		return ynsbService.queryYqdb(query);
	}
	
}
