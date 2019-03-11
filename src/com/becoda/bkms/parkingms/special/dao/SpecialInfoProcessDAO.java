package com.becoda.bkms.parkingms.special.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfoProcess;

public class SpecialInfoProcessDAO extends  GenericDAO{
	//保存数据流程记录信息
	public void saveInfo(ParmsSpecialInfoProcess parmsSpecialInfoProcess)
	{
		hibernateTemplate.save(parmsSpecialInfoProcess);
	}

}
