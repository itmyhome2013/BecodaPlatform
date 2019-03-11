package com.becoda.bkms.csu.courseinfo.dao;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.courseinfo.pojo.KpLessonManageInfo;

public class KpCourseDAO extends GenericDAO {
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @param po
	 * @return
	 * @throws RollbackableException
	 */
	public Map queryCourseInfoList(int page, int rows, KpLessonManageInfo po)throws RollbackableException {
		try {
			StringBuffer countSql = new StringBuffer();
			StringBuffer querySql = new StringBuffer();
			countSql.append(" select count(t) from KpLessonManageInfo t where 1=1 ");
			querySql.append(" from KpLessonManageInfo t where 1=1 ");
			if(StringUtils.isNotBlank(po.getLessonId())){
				querySql.append(" and lessonId = '").append(po.getLessonId()).append("' ");
				countSql.append(" and lessonId = '").append(po.getLessonId()).append("' ");
			}
			querySql.append(" and isdel ='0' ");
			countSql.append(" and isdel ='0' ");
			String orderSql = " order by t.createTime ";
			return pageQueryByEsayUI(page,rows, countSql.toString(), querySql.append(orderSql).toString());
		}
		catch (Exception e) {
			throw new RollbackableException("读取数据错误!", e, this.getClass());
		}
	}
	/**
	 * 保存
	 * @param po
	 * @throws RollbackableException
	 */
	public void saveCourseInfo(KpLessonManageInfo po) throws RollbackableException{
		this.createBo(po);
	}
	/**
	 * 删除
	 * @param po
	 * @return
	 * @throws RollbackableException
	 */
	public String deleteCourseInfo(KpLessonManageInfo po) throws RollbackableException{
		this.deleteBo(po);
		return "删除成功！";
	}
    /**
     * 更新
     * @param po
     * @throws RollbackableException
     */
    public void updateCourseInfo(KpLessonManageInfo po) throws RollbackableException{
    	this.updateBo(po.getLessonId(), po);
    }
    /**
     * 通过主键获得对象
     * @param id
     * @return
     */
    public KpLessonManageInfo getCourseInfoById(String id){
    	KpLessonManageInfo po = null;
		try {
			po = (KpLessonManageInfo) this.findBo(KpLessonManageInfo.class, id);
		} catch (RollbackableException e) {
			e.printStackTrace();
		}
		return po;
    }
}
