package com.becoda.bkms.east.ynsb.dao;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description:用能设备dao </p>
 * @author liu_hq
 * @date 2017-9-28
 *
 */
public class YnsbDAO extends GenericDAO{
	/**
	 * 查询用能设备
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_YNSB",
						"YNSB_ID,YNSB_GLBH,YNSB_NYZL,YNSB_FID,YNSB_JDRQ,YNSB_RATE,YNSB_ZT,YNSB_AZDD");
		return dbQuery;
	}

	/**
	 * 查询用能设备sis标示
	 * 
	 * @param query
	 * @param ynsbid
	 * @return
	 */
	public DataQuery queryYnsbSis(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "EAST_YNSBSIS",
				"YNSBSIS_ID,YNSBSIS_MC,YNSBSIS_BS,YNSB_ID,YNSBSIS_ORDER,YNSBSIS_ISLJ")
				.addUserWhere("and 1=1 order by YNSBSIS_ORDER");
		return dbQuery;
	}
	/**
	 * 根据用能设备id删除sis标示
	 * @param ynsbid
	 */
	public void deleteYnsbSisBySbid(String ynsbid){
		String sql = "delete from EAST_YNSBSIS where YNSB_ID='"+ynsbid+"'";
		getJdbcTemplate().execute(sql);
	}
	/**
	 * 根据设备id修改sis标示能源种类
	 * @param ynsbid
	 * @param nyzl
	 */
	public void editSisBySbid(String ynsbid, String nyzl) {
		String sql = "update EAST_YNSBSIS set YNSBSIS_NYZL = '" + nyzl
				+ "' where YNSB_ID = '" + ynsbid + "'";
		getJdbcTemplate().execute(sql);
	}
	/**
	 * 查询用能设备
	 * @param itemids
	 * @param itemSpell
	 * @return
	 * @throws RollbackableException 
	 */
	public List<CodeItemBO> queryCodeItem(String itemids,String itemSpell) throws RollbackableException{
		StringBuffer hql = new StringBuffer("from CodeItemBO c where c.setId = '3010' and c.itemSuper = '-1' and c.itemStatus='1'");
		if(itemSpell!=null&&!itemSpell.equals("")){
			hql.append(" and c.itemSpell<> '").append(itemSpell).append("'");
		}
		hql.append(" order by c.treeId ");
		return queryHqlList(hql.toString());
	}
	/**
	 * 查询园区电表
	 * @param query
	 * @return
	 */
	public DataQuery queryYqdb(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_YQDB",
						"YQDB_ID,YQDB_ZH,YQDB_BH,YQDB_JDSJ,YQDB_BL");
		return dbQuery;
	}
}
