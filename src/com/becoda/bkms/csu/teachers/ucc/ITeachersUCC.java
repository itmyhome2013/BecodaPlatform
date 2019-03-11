package com.becoda.bkms.csu.teachers.ucc;

import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.teachers.pojo.KpPersonLibrary;

public interface ITeachersUCC {

	public Map queryList(int page, int rows, Map parameter)throws RollbackableException;

	public KpPersonLibrary getTeachersId(String id)throws BkmsException;

	public boolean saveTeachers(KpPersonLibrary kpPersonLibrary);

	public void updateTeachers(KpPersonLibrary kpPersonLibrary);

	public void deleteTeachersIds(String ids);

}
