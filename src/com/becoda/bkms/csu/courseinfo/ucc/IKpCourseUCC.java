package com.becoda.bkms.csu.courseinfo.ucc;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.courseinfo.pojo.KpLessonManageInfo;

public interface IKpCourseUCC {
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @param po
	 * @return
	 * @throws RollbackableException
	 */
	public Map queryCourseInfoList(int page, int rows, KpLessonManageInfo po) throws RollbackableException;
	/**
	 * 保存
	 * @param po
	 * @throws RollbackableException
	 */
	public void saveCourseInfo(KpLessonManageInfo po) throws RollbackableException;
	/**
	 * 删除
	 * @param po
	 * @return
	 * @throws RollbackableException
	 */
	public String deleteCourseInfo(KpLessonManageInfo po) throws RollbackableException;
    /**
     * 更新
     * @param po
     * @throws RollbackableException
     */
    public void updateCourseInfo(KpLessonManageInfo po) throws RollbackableException;
    /**
     * 通过主键获得对象
     * @param id
     * @return
     */
    public KpLessonManageInfo getCourseInfoById(String id);
}
