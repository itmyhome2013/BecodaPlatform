package com.becoda.bkms.csu.teachers.service;

import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.teachers.dao.TeachersDAO;
import com.becoda.bkms.csu.teachers.pojo.KpPersonLibrary;

public class TeachersService {
	private GenericDAO genericDAO;
	private TeachersDAO teachersDao;
	
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}


	public TeachersDAO getTeachersDao() {
		return teachersDao;
	}

	public void setTeachersDao(TeachersDAO teachersDao) {
		this.teachersDao = teachersDao;
	}

	public Map queryList(int page, int rows, Map parameter) throws RollbackableException{
		// TODO Auto-generated method stub
		return teachersDao.queryList(page, rows,parameter);
	}

	public KpPersonLibrary getTeachersId(String id) throws BkmsException {
		// TODO Auto-generated method stub
		return teachersDao.getTeachersId(id);
	}

	public boolean saveTeachers(KpPersonLibrary kpPersonLibrary) {
		// TODO Auto-generated method stub
		return teachersDao.saveTeachers(kpPersonLibrary);
	}

	public void updateTeachers(KpPersonLibrary kpPersonLibrary) {
		// TODO Auto-generated method stub
		teachersDao.updateTeachers(kpPersonLibrary);
	}

	public void deleteTeachersIds(String ids) {
		// TODO Auto-generated method stub
		teachersDao.deleteTeachersIds(ids);
	}

}
