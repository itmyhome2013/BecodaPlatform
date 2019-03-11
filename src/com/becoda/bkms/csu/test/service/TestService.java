package com.becoda.bkms.csu.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.csu.test.dao.TestDAO;
import com.becoda.bkms.csu.test.pojo1.KpLessonManageInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialFile;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialInfoVo;
import com.becoda.bkms.util.SequenceGenerator;

public class TestService {

	private TestDAO demoDao;
	private GenericDAO genericDAO;

	public TestDAO getDemoDao() {
		return demoDao;
	}

	public void setDemoDao(TestDAO demoDao) {
		this.demoDao = demoDao;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	public Map queryDemo(int page , int rows ,ParmsSpecialInfo po) throws RollbackableException{
		
		return demoDao.querySpecailList(page, rows ,po);
	}
	
	
	public void deleteinfo(String ids[]){
		String hql = "delete from ParmsSpecialInfo ";
		String columnName = "specialid";
		
		try {
			genericDAO.deleteByIds(hql, columnName, ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
