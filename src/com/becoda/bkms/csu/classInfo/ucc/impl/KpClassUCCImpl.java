package com.becoda.bkms.csu.classInfo.ucc.impl;


import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.classInfo.pojo.KpClass;
import com.becoda.bkms.csu.classInfo.service.KpClassService;
import com.becoda.bkms.csu.classInfo.ucc.KpClassUCC;

public class KpClassUCCImpl implements KpClassUCC {
	private KpClassService kpClassService;
	
	public KpClassService getKpClassService() {
		return kpClassService;
	}
	public void setKpClassService(KpClassService kpClassService) {
		this.kpClassService = kpClassService;
	}
	public void saveKpClass(KpClass kpClass) throws RollbackableException {
		this.kpClassService.saveKpClass(kpClass);
	}
	public String deleteKpClass(KpClass kpClass) throws RollbackableException {
		return this.kpClassService.deletekpClass(kpClass);
	}
	public KpClass updateKpClass(KpClass kpClass,String id) throws RollbackableException {
		KpClass kpClasstemp = this.kpClassService.getKpClass(id);
		if(kpClasstemp!=null){
			kpClasstemp.setClassName(kpClass.getClassName());
			kpClasstemp.setClassNum(kpClass.getClassNum());
		}
		this.kpClassService.updateKpClass(kpClasstemp);
		return kpClasstemp;
	}
	public List<KpClass> queryKpClass(String providerId, String projectId,String packageId) throws RollbackableException {
		return this.kpClassService.queryKpClass(providerId, projectId, packageId);
	}
	public KpClass getKpClass(String id) {
		return this.kpClassService.getKpClass(id);
	}
	public Map queryClassInfoByType(Integer page, Integer rows,KpClass kpClass) throws RollbackableException {
		return this.kpClassService.queryKpClassInfoByType(page, rows, kpClass);
	}
	public int deletKpClassByIds( String[] ids) throws RollbackableException {
		String hql = " delete from KpClass ";
		String columnName = "classId";
		int flag = this.kpClassService.deletKpClassByIds(hql, columnName, ids);
		return flag;
	}
	public int disbandKpClassByIds(String[] ids) throws RollbackableException {
		int flag = this.kpClassService.disbandKpClassByIds(ids);
		return flag;
	}
	
	
}
