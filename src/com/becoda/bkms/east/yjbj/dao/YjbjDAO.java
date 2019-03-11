package com.becoda.bkms.east.yjbj.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;
/**
 * 
 * <p>Description:预警报警dao </p>
 * @author zhu_lw
 * @date 2017-11-24
 *
 */
public class YjbjDAO extends GenericDAO{
	/**
	 * 查询用能设备
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_YNSB",
						"YNSB_ID,YNSB_GLBH,YNSB_NYZL,YNSB_FID,YNSB_ZT");
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
		DataQuery dbQuery = DataQuery.init(query, "EAST_YNSBSIS a left join EAST_YJBJ b on  a.YNSB_ID=b.YJBJ_SBID and a.ynsbsis_bs=b.yjbj_sis_bs",
				"a.YNSBSIS_ID as YNSBSIS_ID,a.YNSBSIS_MC as YNSBSIS_MC,a.YNSBSIS_BS as YNSBSIS_BS,a.YNSB_ID as YNSB_ID,a.YNSBSIS_ORDER as YNSBSIS_ORDER,b.YJBJ_MIN as YJBJ_MIN,b.YJBJ_Max as YJBJ_Max,b.YJBJ_ID as YJBJ_ID")
				.addUserWhere("and 1=1 order by a.YNSBSIS_ORDER");
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
	 * 查询异常报警
	 * @param query
	 * @param sj
	 * @return
	 */
	public DataQuery queryYcbj(DataQuery query,String sj,String lx){
		String tables = "east_ycbj yc,east_ynsbsis sis,east_ynsb sb";
		String titles = "sb.YNSB_ID,sb.YNSB_GLBH,sis.YNSBSIS_MC,yc.YCBJ_VAL,yc.YCBJ_BJLX,yc.YCBJ_SJ,yc.YCBJ_CZR,yc.YCBJ_CZSJ,yc.YCBJ_STATE";
		String wheresql = " and yc.ycbj_sj like '"
				+ sj
				+ "%' and yc.ynsb_sis=sis.ynsbsis_bs and sis.ynsb_id = sb.ynsb_id ORDER BY yc.YCBJ_SJ DESC";
		if(lx!=null&&lx.equals("1")){
			tables = "east_ycbj bj left join east_nxjcsb sb on sb.nxjcsb_id = bj.ynsb_id ";
			titles = " sb.nxjcsb_mc as YNSB_GLBH,sb.nxjcsb_lx as YNSBSIS_MC,bj.ycbj_sj as YCBJ_SJ,bj.ycbj_val,bj.ycbj_bjlx,bj.YCBJ_CZR,bj.YCBJ_CZSJ,bj.YCBJ_STATE";
			wheresql = " and bj.ycbj_sj like '"+sj+"%' and bj.ycbj_nxjclx is not null";
		}
		DataQuery dbQuery = DataQuery
				.init(query, tables, titles)
				.addUserWhere(wheresql);
		return dbQuery;
	}

	/**
	 * 查询历史异常报警
	 * 
	 * @param query
	 * @param sj
	 * @return
	 */
	public DataQuery queryHistoryYcbj(DataQuery query,String lx) {
		String tables = "east_ycbj yc,east_ynsbsis sis,east_ynsb sb";
		String titles = "sb.YNSB_ID,sb.YNSB_GLBH,sis.YNSBSIS_MC,yc.YCBJ_VAL,yc.YCBJ_BJLX,yc.YCBJ_SJ,yc.YCBJ_CZR,yc.YCBJ_CZSJ,yc.YCBJ_STATE";
		String whereSql = " and yc.ynsb_sis=sis.ynsbsis_bs and sis.ynsb_id = sb.ynsb_id ORDER BY yc.YCBJ_SJ DESC";
		if(lx!=null&&lx.equals("1")){ //能效检测异常报警
			tables = "EAST_YCBJ yc,east_nxjcsb sb";
			titles = " sb.nxjcsb_mc as YNSB_GLBH,sb.nxjcsb_lx as YNSBSIS_MC,yc.ycbj_sj,yc.ycbj_val,yc.ycbj_bjlx,yc.YCBJ_CZR,yc.YCBJ_CZSJ,yc.YCBJ_STATE ";
			whereSql = " and yc.ycbj_nxjclx is not null and yc.ynsb_id=sb.nxjcsb_id ORDER BY yc.YCBJ_SJ DESC";
		}
		DataQuery dbQuery = DataQuery.init(query, tables, titles).addUserWhere(whereSql);
		return dbQuery;
	}
	/**
	 * 报警提醒查询
	 * @return
	 */
	public List<Map<String, Object>> queryAlarmAlert(){
//		String sql = "SELECT * FROM(SELECT SB.YNSB_FID,SB.YNSB_GLBH,SIS.YNSBSIS_MC,"
//				+ "YC.YCBJ_VAL,YC.YCBJ_BJLX,YC.YCBJ_SJ FROM east_ycbj yc,east_ynsbsis sis,east_ynsb sb"
//				+ " WHERE yc.ynsb_sis = sis.ynsbsis_bs AND sis.ynsb_id = sb.ynsb_id ORDER BY YC.YCBJ_SJ DESC) WHERE ROWNUM<6";
		
		String currTime = Tools.getSysDate("yyyy-MM-dd"); // 当前日期
		
		String sql = "select * from (select * from ( SELECT SB.YNSB_FID,SB.YNSB_GLBH,SIS.YNSBSIS_MC," +
				"YC.YCBJ_VAL,YC.YCBJ_BJLX,YC.YCBJ_SJ,yc.YCBJ_NXJCLX,YC.YCBJ_CZR,YC.YCBJ_CZSJ,YC.YCBJ_STATE,YC.YCBJ_ID FROM east_ycbj yc,east_ynsbsis sis,east_ynsb sb" +
				" WHERE yc.ynsb_sis = sis.ynsbsis_bs AND sis.ynsb_id = sb.ynsb_id and YC.YCBJ_STATE !=1 union all " +
				" select nsb.nxjcsb_lx as YNSB_FID,nsb.nxjcsb_mc as YNSB_GLBH,nsb.nxjcsb_lx as YNSBSIS_MC," +
				" bj.YCBJ_VAL,bj.YCBJ_BJLX,bj.YCBJ_SJ,bj.YCBJ_NXJCLX,BJ.YCBJ_CZR,BJ.YCBJ_CZSJ,BJ.YCBJ_STATE,BJ.YCBJ_ID  from east_ycbj bj" +
				" ,east_nxjcsb nsb where bj.ynsb_id = nsb.nxjcsb_id and BJ.YCBJ_STATE !=1) ycbj where 1 = 1 and ycbj_sj like '"+currTime+"%' ORDER BY ycbj.YCBJ_SJ DESC) where rownum<6";
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 查询能效检测预警报警设备
	 * @param query
	 * @return
	 */
	public DataQuery queryNxjcYcbjSb(DataQuery query){
		DataQuery dbQuery = DataQuery.init(query, "EAST_NXJCSB", "NXJCSB_ID,NXJCSB_LX,NXJCSB_MC,NXJCSB_YJMAX,NXJCSB_YJMIN");
		return dbQuery;
	}
	//处理能效检测已恢复正常设备报警
	public void updateNxjc(String datestr) throws ParseException{
		datestr = Tools.minusMinute(datestr, -1);
		String nxjcUpdateYcsql = "update east_ycbj ycbj set ycbj.ycbj_state = '1' where ycbj.ycbj_state = '0'  " +
		 		"and ycbj.ycbj_nxjclx is not null and exists ( select sis.nxjcsb_id  from (select sb.nxjcsb_id " +
		 		" from east_nxjc nxjc, east_nxjcsb sb  where nxjc.nxjc_sj = '"+datestr+"' and exists(" +
		 		" select t1.ynsb_id  from (select t.ynsb_id from east_ycbj t where t.ycbj_state = '0'" +
		 		" and t.ycbj_nxjclx is not null group by t.ynsb_id) t1 where t1.ynsb_id = nxjc.nxjc_sbid)" +
		 		" and sb.nxjcsb_id = nxjc.nxjc_sbid and nxjc.nxjc_val < nvl(sb.nxjcsb_yjmax, 0)" +
		 		" and nxjc.nxjc_val > nvl(sb.nxjcsb_yjmin, 0)) sis where ycbj.ynsb_id = sis.nxjcsb_id)"; //能效检测
		 
		 getJdbcTemplate().update(nxjcUpdateYcsql);
	}
}
