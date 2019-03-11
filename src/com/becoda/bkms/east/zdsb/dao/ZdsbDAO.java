package com.becoda.bkms.east.zdsb.dao;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description:重点设备dao </p>
 * @author zhu_lw
 * @date 2017-12-22
 *
 */
public class ZdsbDAO extends GenericDAO{
	/**
	 * 查询重点设备
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_ZDSB",
						"ZDSB_ID,ZDSB_SSBM,ZDSB_SBMC,ZDSB_YNZL,ZDSB_LRRQ,ZDSB_JXRQ,ZDSB_SBZT").addUserWhere(" order by ZDSB_LRRQ,ZDSB_SBMC");
		return dbQuery;
	}
	/**
	 * 查询重点设备状态
	 * @param query
	 * @return
	 */
	public DataQuery queryStateList(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						" east_zdsb_state s left join east_zdsb z on s.zdsb_id=z.zdsb_id ",
						"s.zdsb_id as zdsb_id,s.zdsb_jxrq as zdsb_jxrq,s.zdsb_jxry as zdsb_jxry,s.zdsb_state as zdsb_state,z.zdsb_sbmc as zdsb_sbmc,z.zdsb_ssbm as zdsb_ssbm ");
		return dbQuery;
	}
	/**
	 * 根据重点设备id删除sis标示
	 * @param zdsbid
	 */
	public void deleteZdsbSisBySbid(String zdsbid){
		String sql = "delete from EAST_ZDSBSIS where ZDSB_ID='"+zdsbid+"'";
		getJdbcTemplate().execute(sql);
	}
	/**
	 * 根据重点设备id删除设备时长
	 * @param zdsbid
	 */
	public void deleteZdsbSbscBySbid(String zdsbid){
		String sql = "delete from ZDSB_SBSC where ZDSB_ID='"+zdsbid+"'";
		getJdbcTemplate().execute(sql);
	}
	/**
	 * 查询重点设备sis标示
	 * 
	 * @param query
	 * @param zdsbid
	 * @return
	 */
	public DataQuery queryZdsbSis(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "EAST_ZDSBSIS",
				"ZDSBSIS_ID,ZDSBSIS_MC,ZDSBSIS_BS,ZDSBSIS_ORDER,ZDSB_ID,ZDSBSIS_DATE")
				.addUserWhere("and 1=1 order by ZDSBSIS_ORDER");
		return dbQuery;
	}
	/**
	 * 通过条件查询重点设备
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbByParams(Map<String,String> params) {
		String sql = "select zdsb_id,zdsb_ssbm,zdsb_sbmc from east_zdsb order by zdsb_ssbm desc,zdsb_sbmc";
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 查询重点设备设备时长
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbSbsc() {
		String sql = "select sc.id,sc.zdsb_id,sc.zdsb_qnsc,sc.zdsb_zsc from zdsb_sbsc sc  ";
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 通过条件查询设备检测信息
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbSbjcByParams(String sj) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select sis.zdsb_id,sis.zdsb_ssbm,sis.zdsb_sbmc,sis.zdsbsis_bs,zd.sisvalue,sis.zdsb_state,sbsc.zdsb_qnsc,sbsc.zdsb_zsc from" +
				" (select z.zdsb_id zdsb_id,z.zdsb_ssbm zdsb_ssbm,z.zdsb_sbmc zdsb_sbmc,s.zdsbsis_bs zdsbsis_bs,z.zdsb_sbzt zdsb_state  from east_zdsb z" +
				" left join east_zdsbsis s on z.zdsb_id=s.zdsb_id order by zdsb_ssbm desc,zdsb_sbmc)  sis  left join  zd_test2 zd  on sis.zdsbsis_bs=zd.sisid  and zd.sistime='").append(sj).append("'")
				.append("  left join  (select sc.zdsb_id as zdsb_id,round(to_number(TO_DATE(sc.zdsb_qnsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE((SELECT To_char(trunc(sysdate,'yyyy'), 'yyyy-mm-dd hh24:mi:ss') FROM dual),'yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_qnsc," +
						"round(to_number(TO_DATE(sc.zdsb_zsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE('2018-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_zsc from zdsb_sbsc sc) sbsc on sis.zdsb_id=sbsc.zdsb_id order by sis.zdsb_ssbm, sis.zdsb_sbmc");
		return jdbcTemplate.queryForList(sql.toString());
	}
	/**
	 * 查询备件清单
	 * @param query
	 * @return
	 */
	public DataQuery queryBjqdList(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_BJQD",
						"ID,BJQD_JGMC,BJQD_DYJZ,BJQD_BJMC,BJQD_GGTH,BJQD_BJZL,BJQD_YCL,BJQD_SCL,REMARK,BJQD_CJSJ").addUserWhere(" order by BJQD_JGMC,BJQD_DYJZ,BJQD_BJZL ");
		return dbQuery;
	}
}