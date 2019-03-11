package com.becoda.bkms.pcs.ucc.impl;

import java.io.Serializable;
import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pcs.pojo.vo.ActExBussinessForm;
import com.becoda.bkms.pcs.service.ActExBussinessService;
import com.becoda.bkms.pcs.ucc.IActExBussinessUCC;
import com.becoda.bkms.pms.service.OperateService;
import com.becoda.bkms.run.RunConstants;
import com.becoda.bkms.run.pojo.vo.BulletinForm;
import com.becoda.bkms.util.HrmsLog;

public class ActExBussinessUCCImpl implements IActExBussinessUCC, Serializable {

	private ActExBussinessService bussinessService;

	public List findByBussinessId(String bussinessId) throws RollbackableException {
		return bussinessService.findByBussinessId(bussinessId);
	}
	
	public void createBussiness(ActExBussinessForm vo, String userId, String orgId, User user) throws RollbackableException {
		bussinessService.createBussiness(vo, userId, orgId);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "新建Bussiness");
    }

	public void deleteBussinessByBussinessIdArray(String bussinessIdArray[], User user) throws RollbackableException {
		bussinessService.deleteBussinessByBussinessIdArray(bussinessIdArray);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "删除事件");
    }
	
	public ActExBussinessService getBussinessService() {
		return bussinessService;
	}

	public void setBussinessService(ActExBussinessService bussinessService) {
		this.bussinessService = bussinessService;
	}

	

}
