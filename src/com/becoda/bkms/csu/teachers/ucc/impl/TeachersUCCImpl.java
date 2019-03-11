package com.becoda.bkms.csu.teachers.ucc.impl;

import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.teachers.pojo.KpPersonLibrary;
import com.becoda.bkms.csu.teachers.service.TeachersService;
import com.becoda.bkms.csu.teachers.ucc.ITeachersUCC;

public class TeachersUCCImpl implements ITeachersUCC{

	private TeachersService teachersSer;
	
	public TeachersService getTeachersSer() {
		return teachersSer;
	}

	public void setTeachersSer(TeachersService teachersSer) {
		this.teachersSer = teachersSer;
	}

	public Map queryList(int page, int rows, Map parameter)
			throws RollbackableException {
		// TODO Auto-generated method stub
		return teachersSer.queryList(page,rows,parameter);
	}

	public KpPersonLibrary getTeachersId(String id) throws BkmsException {
		// TODO Auto-generated method stub
		return teachersSer.getTeachersId(id);
	}

	public boolean saveTeachers(KpPersonLibrary kpPersonLibrary) {
		// TODO Auto-generated method stub
		return teachersSer.saveTeachers(kpPersonLibrary);
	}

	public void updateTeachers(KpPersonLibrary kpPersonLibrary) {
		// TODO Auto-generated method stub
		this.teachersSer.updateTeachers(kpPersonLibrary);
	}

	public void deleteTeachersIds(String ids) {
		// TODO Auto-generated method stub
		this.teachersSer.deleteTeachersIds(ids);
	}

}
