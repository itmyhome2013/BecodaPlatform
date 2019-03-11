package com.becoda.bkms.pcs.dao;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;

public class ActExBussinessDAO extends GenericDAO {
	
	public List findByBussinessId(String bussinessId) {
		String strHQL = "from ActExProcessBO db ";
		String[] obj = new String[1];
		obj[0] = bussinessId;
		List list = hibernateTemplate.find(strHQL);
		return list;
	}
}
