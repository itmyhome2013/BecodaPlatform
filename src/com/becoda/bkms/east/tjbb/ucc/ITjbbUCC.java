package com.becoda.bkms.east.tjbb.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.east.tjbb.pojo.YddlfpdBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 *统计报表
 */
public interface ITjbbUCC {
	
	/**
	 * 月度动力分配单
	 * @param query
	 * @return
	 */
	public DataQuery yddlfpd(DataQuery query);
	
	public DataQuery yddlfpd1(DataQuery query, String rq);
	public DataQuery yddlfpd2(DataQuery query, String rq);
	public DataQuery yddlfpd3(DataQuery query, String rq);
	public DataQuery yddlfpd4(DataQuery query, String rq);
	public DataQuery yddlfpd5(DataQuery query, String rq);
	/**
	 * 编辑月度动力分配单
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYddlfpd(YddlfpdBO bo) throws RollbackableException;
	
	/**
	 * 能源用户数据日报表
	 * @param query
	 * @return
	 */
	public DataQuery nyyhsjrbb(DataQuery query, String rq);
	
	public DataQuery nyyhsjrbb1(DataQuery query, String rq);
	public DataQuery nyyhsjrbb2(DataQuery query, String rq);
	public DataQuery nyyhsjrbb3(DataQuery query, String rq);
	public DataQuery nyyhsjrbb4(DataQuery query, String rq);
	public DataQuery nyyhsjrbb5(DataQuery query, String rq);
	
	/**
	 * 生产计划
	 * @param query
	 * @return
	 */
	public DataQuery scjj(DataQuery query,String type);
	
	/**
	 * 设备计划
	 * @param query
	 * @return
	 */
	public DataQuery sbjj(DataQuery query,String type);
	
	/**
	 * 指标体系
	 * @param query
	 * @return
	 */
	public DataQuery zbtx(DataQuery query,String type);
	
	public List<CodeItemBO> queryCodeItem() throws RollbackableException;
	
}
