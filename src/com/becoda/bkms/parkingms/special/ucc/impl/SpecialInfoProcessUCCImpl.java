package com.becoda.bkms.parkingms.special.ucc.impl;

import javax.annotation.Resource;

import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfoProcess;
import com.becoda.bkms.parkingms.special.service.SpecialInfoProcessService;
import com.becoda.bkms.parkingms.special.ucc.ISpecialInfoProcessUCC;

public class SpecialInfoProcessUCCImpl implements ISpecialInfoProcessUCC{
	
	
	private SpecialInfoProcessService  specialInfoProcessService;
	
	public SpecialInfoProcessService getSpecialInfoProcessService() {
		return specialInfoProcessService;
	}  

	public void setSpecialInfoProcessService(
			SpecialInfoProcessService specialInfoProcessService) {
		this.specialInfoProcessService = specialInfoProcessService;
	}

	//保存数据流程记录信息
	public void saveInfo(ParmsSpecialInfoProcess parmsSpecialInfoProcess)
	{
		specialInfoProcessService.saveInfo(parmsSpecialInfoProcess);
	}
}
