package com.becoda.bkms.csu.courseinfo.ucc.impl;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.courseinfo.pojo.KpLessonManageInfo;
import com.becoda.bkms.csu.courseinfo.service.KpCourseService;
import com.becoda.bkms.csu.courseinfo.ucc.IKpCourseUCC;

public class KpCourseUCCImpl implements IKpCourseUCC {
    private KpCourseService kpCourseService;
	public void setKpCourseService(KpCourseService kpCourseService) {
		this.kpCourseService = kpCourseService;
	}
	
	public Map queryCourseInfoList(int page, int rows, KpLessonManageInfo po) throws RollbackableException {
        return this.kpCourseService.queryCourseInfoList(page, rows, po);
    }
	public void saveCourseInfo(KpLessonManageInfo po) throws RollbackableException{
		this.kpCourseService.saveCourseInfo(po);
	}
	public String deleteCourseInfo(KpLessonManageInfo po) throws RollbackableException{
		return this.kpCourseService.deleteCourseInfo(po);
	}
    public void updateCourseInfo(KpLessonManageInfo po) throws RollbackableException{
    	this.kpCourseService.updateCourseInfo(po);
    }
    public KpLessonManageInfo getCourseInfoById(String id){
    	return this.kpCourseService.getCourseInfoById(id);
    }
}
