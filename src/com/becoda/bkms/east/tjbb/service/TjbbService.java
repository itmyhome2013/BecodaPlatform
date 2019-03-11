package com.becoda.bkms.east.tjbb.service;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.east.tjbb.dao.TjbbDAO;
import com.becoda.bkms.east.tjbb.pojo.YddlfpdBO;
import com.becoda.bkms.east.tjbb.ucc.ITjbbUCC;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.SequenceGenerator;
import com.farm.core.sql.query.DataQuery;


/**
 * 
 * 统计报表
 */
public class TjbbService {

	private TjbbDAO tjbbDao;

	public DataQuery yddlfpd(DataQuery query){
		return tjbbDao.yddlfpd(query);
	}
	
	public DataQuery yddlfpd1(DataQuery query, String rq){
		return tjbbDao.yddlfpd1(query, rq);
	}
	public DataQuery yddlfpd2(DataQuery query, String rq){
		return tjbbDao.yddlfpd2(query, rq);
	}
	public DataQuery yddlfpd3(DataQuery query, String rq){
		return tjbbDao.yddlfpd3(query, rq);
	}
	public DataQuery yddlfpd4(DataQuery query, String rq){
		return tjbbDao.yddlfpd4(query, rq);
	}
	public DataQuery yddlfpd5(DataQuery query, String rq){
		return tjbbDao.yddlfpd5(query, rq);
	}
	
	public DataQuery nyyhsjrbb(DataQuery query, String rq){
		return tjbbDao.nyyhsjrbb(query, rq);
	}
	
	public DataQuery nyyhsjrbb1(DataQuery query, String rq){
		return tjbbDao.nyyhsjrbb1(query, rq);
	}
	public DataQuery nyyhsjrbb2(DataQuery query, String rq){
		return tjbbDao.nyyhsjrbb2(query, rq);
	}
	public DataQuery nyyhsjrbb3(DataQuery query, String rq){
		return tjbbDao.nyyhsjrbb3(query, rq);
	}
	public DataQuery nyyhsjrbb4(DataQuery query, String rq){
		return tjbbDao.nyyhsjrbb4(query, rq);
	}
	public DataQuery nyyhsjrbb5(DataQuery query, String rq){
		return tjbbDao.nyyhsjrbb5(query, rq);
	}
	
	public DataQuery scjj(DataQuery query,String type){
		return tjbbDao.scjj(query,type);
	}
	
	public DataQuery sbjj(DataQuery query,String type){
		return tjbbDao.sbjj(query,type);
	}
	
	public DataQuery zbtx(DataQuery query,String type){
		return tjbbDao.zbtx(query,type);
	}
	/**
	 * 编辑月度动力分配单
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYddlfpd(YddlfpdBO bo) throws RollbackableException{
		if(bo.getId()!=null&&!"".equals(bo.getId())){
			tjbbDao.updateBo(bo.getId(), bo);
		}else{
			bo.setId(SequenceGenerator.getUUID());
			tjbbDao.createBo(bo);
		}
	}
	
	
	public List<CodeItemBO> queryCodeItem() throws RollbackableException{
		String hql = "from CodeItemBO c where c.setId = '3010' and c.itemId not in ('3010401111','3010401112','3010401113','3010401114')  order by treeId asc";
		return tjbbDao.queryHqlList(hql);
	}
	
	public TjbbDAO getTjbbDao() {
		return tjbbDao;
	}

	public void setTjbbDao(TjbbDAO tjbbDao) {
		this.tjbbDao = tjbbDao;
	}

}