package com.becoda.bkms.east.sjbl.dao;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description:手工补录dao </p>
 * @author zhu_lw
 * @date 2017-11-27
 *
 */
public class SjblDAO extends GenericDAO{
	/**
	 * 查询手工补录信息
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_SJBL_MANUAL_MAKEUP",
						"EAST_SJBL_ID,EAST_SJBL_SBID,EAST_SJBL_LJZ,EAST_SJBL_FENG,EAST_SJBL_GU,EAST_SJBL_PING,EAST_SJBL_JIAN,EAST_SJBL_TOTAL,EAST_SJBL_DATE,EAST_SJBL_SBBH,EAST_SJBL_ZDJZ,EAST_SJBL_NYZL,EAST_SJBL_LOOKDATE");
		return dbQuery;
	}
}
