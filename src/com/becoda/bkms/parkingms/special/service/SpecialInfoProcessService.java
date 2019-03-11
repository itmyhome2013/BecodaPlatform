package com.becoda.bkms.parkingms.special.service;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.parkingms.special.dao.SpecialInfoProcessDAO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfoProcess;

public class SpecialInfoProcessService {
	private SpecialInfoProcessDAO specialInfoProcessDAO;
	private GenericDAO genericDAO;

	public SpecialInfoProcessDAO getSpecialInfoProcessDAO() {
		return specialInfoProcessDAO;
	}

	public void setSpecialInfoProcessDAO(SpecialInfoProcessDAO specialInfoProcessDAO) {
		this.specialInfoProcessDAO = specialInfoProcessDAO;
	}	
	
	
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	//保存数据流程记录信息
	public void saveInfo(ParmsSpecialInfoProcess parmsSpecialInfoProcess)
	{
		specialInfoProcessDAO.saveInfo(parmsSpecialInfoProcess);	
	}	
}








	
	
