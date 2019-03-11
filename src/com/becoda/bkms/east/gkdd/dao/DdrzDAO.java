package com.becoda.bkms.east.gkdd.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 调度日志DAO</p>
 * @author liu_hq
 * @date 2017-9-14
 *
 */
public class DdrzDAO extends GenericDAO{
	/**
	 * 查询调度日志
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_DDRZ",
						"DDRZ_ID,DDRZ_RQ,DDRZ_SJ,DDRZ_FYRY,DDRZ_WTJY,DDRZ_CLJG,DDRZ_WWGZ,DDRZ_ZBR,DDRZ_FYDW").addUserWhere(" and 1=1 order by DDRZ_RQ desc,DDRZ_SJ desc nulls last");
		return dbQuery;
	}
}
