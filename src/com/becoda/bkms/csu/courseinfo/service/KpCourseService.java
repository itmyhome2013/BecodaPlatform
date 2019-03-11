package com.becoda.bkms.csu.courseinfo.service;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.courseinfo.dao.KpCourseDAO;
import com.becoda.bkms.csu.courseinfo.pojo.KpLessonManageInfo;

public class KpCourseService {
    private KpCourseDAO kpCourseDAO;
	public void setKpCourseDAO(KpCourseDAO kpCourseDAO) {
		this.kpCourseDAO = kpCourseDAO;
	}

	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @param po
	 * @return
	 * @throws RollbackableException
	 */
	public Map queryCourseInfoList(int page, int rows, KpLessonManageInfo po) throws RollbackableException {
        return this.kpCourseDAO.queryCourseInfoList(page, rows, po);
    }
	/**
	 * 保存
	 * @param po
	 * @throws RollbackableException
	 */
	public void saveCourseInfo(KpLessonManageInfo po) throws RollbackableException{
		this.kpCourseDAO.saveCourseInfo(po);
	}
	/**
	 * 删除
	 * @param po
	 * @return
	 * @throws RollbackableException
	 */
	public String deleteCourseInfo(KpLessonManageInfo po) throws RollbackableException{
		return this.kpCourseDAO.deleteCourseInfo(po);
	}
    /**
     * 更新
     * @param po
     * @throws RollbackableException
     */
    public void updateCourseInfo(KpLessonManageInfo po) throws RollbackableException{
    	this.kpCourseDAO.updateCourseInfo(po);
    }
    /**
     * 通过主键获得对象
     * @param id
     * @return
     */
    public KpLessonManageInfo getCourseInfoById(String id){
    	return this.kpCourseDAO.getCourseInfoById(id);
    }
}
