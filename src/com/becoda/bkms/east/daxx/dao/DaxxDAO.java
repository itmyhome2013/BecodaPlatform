package com.becoda.bkms.east.daxx.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description:档案信息dao </p>
 * @author zhu_lw
 * @date 2018-04-10
 *
 */
public class DaxxDAO extends GenericDAO{
	/**
	 * 查询档案信息
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_DAXX",
						"DAXX_ID,DAXX_SSBM,DAXX_SSJZ,DAXX_DALX,DAXX_DAMC,DAXX_FILENAME,DAXX_FILEPATH,DAXX_DAMS,DAXX_PERSON,DAXX_DATE,DAXX_CJDW").addUserWhere(" order by DAXX_DATE ");
		return dbQuery;
	}
	/**
	 * 获取部门
	 * @return
	 */
	public List findSsbm(){
		String sql = " select * from dictionary_type where ENTITY = '480778' and treecode is not null order by sort ";
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 获取所有机组
	 * @return
	 */
	public List findTotalJz(){
		String sql = " select * from dictionary_type where ENTITY = '480778' and treecode is  null order by sort ";
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 获取部门下的机组
	 * @return
	 * @throws IOException
	 * @throws BkmsException
	 */
	public List findSsjz(String bmid){
		StringBuffer querySql=new StringBuffer();
		StringBuffer whereSql=new StringBuffer(" where ENTITY = '480778' ");
		querySql.append(" select * from dictionary_type ");
		if(!Tools.stringIsNull(bmid)){
			whereSql.append(" and parentid = '").append(bmid).append("'");
		}
		whereSql.append(" order by sort ");
		querySql.append(whereSql.toString());
		return jdbcTemplate.queryForList(querySql.toString());
	}
}