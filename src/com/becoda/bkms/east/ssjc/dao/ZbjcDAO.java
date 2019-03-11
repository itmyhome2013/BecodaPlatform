package com.becoda.bkms.east.ssjc.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.ssjc.pojo.NxjcBO;
import com.becoda.bkms.east.ssjc.pojo.ZbjcBO;
import com.becoda.bkms.east.ssjc.pojo.ZbjcQueryCondition;
import com.becoda.bkms.east.ssjc.pojo.ZdTest2BO;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>
 * Description:用能设备指标检测数据
 * </p>
 * 
 * @author liu_hq
 * @date 2017-11-1
 * 
 */
public class ZbjcDAO extends GenericDAO {

	/**
	 * 指标检测使用查询机组
	 * 
	 * @param setId
	 * @param superId
	 * @param itemSpell
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeJzZbjc(String setId, String superId)
			throws RollbackableException {
		String hql = "from CodeItemBO c where c.setId = '" + setId + "' "
				+ "and c.itemSuper = '" + superId + "' and c.itemStatus='1' "
				+ " and itemAbbr='3' order by c.treeId";
		return queryHqlList(hql);
	}

	public List<CodeItemBO> queryCode(String setId, String superId,
			String itemSpell) throws RollbackableException {
		String hql = "from CodeItemBO c where c.setId = '" + setId + "' "
				+ "and c.itemSuper = '" + superId + "' and c.itemStatus='1' "
				+ "and itemSpell = '" + itemSpell
				+ "' and itemAbbr='3' order by c.treeId";
		return queryHqlList(hql);
	}

	/**
	 * 查询重点机组
	 * 
	 * @param setId
	 * @param superId
	 * @param itemSpell
	 * @param itemAbbr
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeIn(String setId, String superId,
			String itemSpell, String itemAbbr) throws RollbackableException {
		String wherehql = "";
		if (itemAbbr != "") {
			wherehql = " and c.itemAbbr <> '" + itemAbbr + "' ";
		}
		String hql = "from CodeItemBO c where c.setId = '" + setId + "' "
				+ wherehql + "" + "and c.itemSuper = '" + superId
				+ "'  and c.itemStatus='1' " + "  order by c.treeId";
		return queryHqlList(hql);
	}

	/**
	 * 查询预警报警重点机组
	 * 
	 * @param setId
	 * @param superId
	 * @param itemSpell
	 * @param itemAbbr
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryYjbjCodeIn(String setId, String superId,
			String itemSpell, String itemAbbr) throws RollbackableException {
		String wherehql = "";
		if (itemAbbr != "") {
			wherehql = " and c.itemAbbr <> '" + itemAbbr + "' ";
		}
		String hql = "from CodeItemBO c where c.setId = '" + setId + "' "
				+ wherehql + "" + "and c.itemSuper = '" + superId
				+ "' and c.itemStatus='1' " + "  order by c.treeId";
		return queryHqlList(hql);
	}

	/**
	 * 查询重点机组
	 * 
	 * @param setId
	 * @param itemId
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryNameById(String setId, String itemId)
			throws RollbackableException {
		String wherehql = "";
		if (itemId != "") {
			wherehql = " and c.itemId = '" + itemId + "' ";
		}
		String hql = "from CodeItemBO c where c.setId = '" + setId + "' "
				+ wherehql + ""
				+ " and c.itemStatus='1' and c.itemId <> '3010401116' "
				+ "  order by c.treeId";
		return queryHqlList(hql);
	}

	/**
	 * 查询手工补录重点机组
	 * 
	 * @param setId
	 * @param superId
	 * @param itemSpell
	 * @param itemAbbr
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeInSgbl(String setId, String superId,
			String qxDesc, String itemAbbr, String description)
			throws RollbackableException {
		StringBuffer wherehql = new StringBuffer(" and 1=1 ");

		if (itemAbbr != "") {
			wherehql.append(" and c.itemAbbr <> '" + itemAbbr + "' ");
		}
		// 动力南厂核算员
		description = Tools.filterNull(description);
		if (description.equals("12")) {
			wherehql.append(" and c.itemSpell = '" + 3 + "'");
		} else if (description.equals("15")) {// 恒东热电核算员
			wherehql.append(" and c.itemSpell = '" + 2 + "'");
		} else if (description.equals("7")) {// 经营管理处核算员
			wherehql.append(" and c.itemId in ('3010401101','3010401102','3010401106','3010401120')");
		}

		if (qxDesc != null && ("12".equals(qxDesc) || "15".equals(qxDesc))) {
			if ("12".equals(qxDesc)) {
				wherehql.append(" and itemId in ('3010401107','3010401108','3010401109') ");
			} else if ("15".equals(qxDesc)) {
				wherehql.append(" and itemId in ('3010401104','3010401105') ");
			}

		} else {
			wherehql.append(" and itemId not in ('3010401111','3010401112','3010401113') ");
		}
		String hql = "from CodeItemBO c where c.setId = '" + setId + "' "
				+ wherehql.toString() + "" + "and c.itemSuper = '" + superId
				+ "' and c.itemStatus='1' " + "  order by c.treeId";
		return queryHqlList(hql);
	}

	/**
	 * 用能设备计量编号
	 * 
	 * @param itemid
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryYnsbGlbh(String itemid)
			throws RollbackableException {
		String wherehql = "";
		if (itemid != "") {
			wherehql = " and c.ynsb_fid ='" + itemid + "' ";
		}
		String hql = "from YnsbBO c where 1=1 " + wherehql + "";
		return queryHqlList(hql);
	}

	/**
	 * 用能设备计量编号
	 * 
	 * @param itemid
	 * @param nyzl
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryGlbhByNyzl(String itemid, String nyzl)
			throws RollbackableException {
		StringBuffer wherehql = new StringBuffer("");
		if (itemid != "") {
			wherehql.append(" and c.ynsb_fid ='" + itemid + "' ");
		}
		if (nyzl != "") {
			wherehql.append(" and c.ynsb_nyzl ='" + nyzl + "' ");
		}
		String hql = "from YnsbBO c where 1=1 " + wherehql.toString() + "";
		return queryHqlList(hql);
	}

	/**
	 * 能源监测使用
	 * 
	 * @param setId
	 * @param superId
	 * @return
	 * @throws RollbackableException
	 */
	public List<CodeItemBO> queryCodeNyjc(String setId, String superId)
			throws RollbackableException {
		String hql = "from CodeItemBO c where c.setId = '"
				+ setId
				+ "' "
				+ "and c.itemSuper = '"
				+ superId
				+ "' and c.itemStatus='1' "
				+ " and itemId<>'3010401110' and c.itemSpell<> '4' and c.itemSpell<> '5' and c.itemSpell<> '6' order by c.treeId";
		return queryHqlList(hql);
	}

	/**
	 * 查询能源种类
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryType(String nyzl, String nyjc,
			String fid) {
		String sql = "SELECT YNSB_NYZL FROM EAST_YNSB E WHERE E.YNSB_FID = '"
				+ fid + "'";
		if(nyzl!=null&&!nyzl.equals("")){
			sql += " and YNSB_NYZL not in "+nyzl+"";
		}
		sql += " GROUP BY YNSB_NYZL ORDER BY CAST(YNSB_NYZL AS int)";
		List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
		return map;
	}

	/**
	 * 查询用能设备
	 * 
	 * @param fid
	 * @return
	 * @throws RollbackableException
	 */
	public List<Map<String, Object>> queryYnsb(String fid)
			throws RollbackableException {
		StringBuffer hql = new StringBuffer("select * from EAST_YNSB where 1=1");
		if (fid != null && !fid.equals("")) {
			hql.append(" and YNSB_FID = '").append(fid).append("'");
		}
		return jdbcTemplate.queryForList(hql.toString());
	}

	/**
	 * 查询指标检测值
	 * 
	 * @param sj
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZbjcBO> queryZbjc(String sj) throws RollbackableException {
		StringBuffer hql = new StringBuffer("from ZbjcBO where 1=1");
		if (sj != null && !sj.equals("")) {
			hql.append(" and zbjc_sj = '").append(sj).append("'");
		}
		return queryHqlList(hql.toString());
	}

	/**
	 * 根据机组id查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySis(String fid) {
		String sql = "select t.ynsb_nyzl,s.ynsbsis_mc,s.ynsbsis_order from east_ynsb t,"
				+ " east_ynsbsis s where t.YNSB_FID = '"
				+ fid
				+ "' and t.ynsb_id=s.ynsb_id"
				+ " group by t.ynsb_nyzl,s.ynsbsis_mc,s.ynsbsis_order  order by s.ynsbsis_order";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 根据机组id和能源种类查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySisByNyzl(String fid, String nyzl) {
		String sql = "select s.ynsbsis_mc from east_ynsb t,"
				+ " east_ynsbsis s where t.YNSB_FID = '"
				+ fid
				+ "' and t.ynsb_id=s.ynsb_id and t.ynsb_nyzl='"
				+ nyzl
				+ "'"
				+ " group by t.ynsb_nyzl,s.ynsbsis_mc,s.ynsbsis_order  order by s.ynsbsis_order";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 根据设备id查询sis
	 * 
	 * @param sbid
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbSisBO> querySisBySb(String sbid)
			throws RollbackableException {
		String hql = "from YnsbSisBO where ynsb_id = '" + sbid
				+ "' and ynsbsis_bs is not null order by ynsbsis_order";
		return queryHqlList(hql);
	}

	/**
	 * 根据机组id查询设备
	 * 
	 * @param fid
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> querySbByfid(String fid) throws RollbackableException {
		String hql = "from YnsbBO where ynsb_fid = '" + fid + "' order by ltrim(ynsb_glbh) ";
		return queryHqlList(hql);
	}

	/**
	 * 根据条件查询sis
	 * 
	 * @param fid
	 * @return
	 */
	public List<Map<String, Object>> querySisByParams(Map<String, String> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.ynsbsis_mc                ")
				.append(" from east_ynsb t                      ")
				.append(" inner join east_ynsbsis s             ")
				.append(" on t.ynsb_id = s.ynsb_id              ")
				.append(" where 1=1                             ");
		if (StringUtils.isNotBlank(params.get("fid"))) {
			sql.append(" and t.YNSB_FID = '" + params.get("fid") + "' ");
		}
		sql.append(" group by s.ynsbsis_mc,s.ynsbsis_order ").append(
				" order by s.ynsbsis_order              ");
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 根据机组id和时间查询指标值
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryZbz(String fid, String sj) {
		String sql = "select ynsb_id,ynsb_glbh,ynsb_nyzl,LISTAGG(zbjc_sz, ',') WITHIN GROUP(ORDER BY ynsbsis_order) zbjc_sz"
				+ " from(select sb.ynsb_id,jc.SISTIME as zbjc_sj,sb.ynsb_glbh,SIS.ynsbsis_bs,NVL(jc.SISVALUE,' ') as zbjc_sz,sis.ynsbsis_order ,sb.ynsb_nyzl"
				+ " from EAST_YNSB sb LEFT JOIN EAST_YNSBSIS sis on sis.ynsb_id=sb.ynsb_id"
				+ " LEFT JOIN ZD_TEST2 jc on jc.SISID= sis. ynsbsis_bs  and jc.SISTIME='"
				+ sj
				+ "' where sb.ynsb_fid='"
				+ fid
				+ "'"
				+ " ORDER BY sis.ynsbsis_order) group by ynsb_id,ynsb_nyzl,ynsb_glbh ORDER by cast(YNSB_NYZL AS int) asc,REGEXP_REPLACE(YNSB_GLBH,'[0-9]+#',''),cast(REGEXP_SUBSTR(YNSB_GLBH, '[0-9]+') as int)";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 查询指标检测累计值
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryLjz(String fid, String sj) {
		String sql = "select sb.ynsb_id,lj.zbjclj_blj,lj.zbjclj_rlj,lj.zbjclj_ylj,lj.zbjclj_nlj"
				+ " from east_ynsb sb left join east_zbjcljz lj on sb.ynsb_id=lj.ynsb_id and lj.ZBJCLJ_SJ = '"
				+ sj + "'" + " where sb.ynsb_fid='" + fid + "'";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 分页查询历史指标检测值
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistoryZbz(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		StringBuffer querySql = new StringBuffer(
				"SELECT ynsb_id,ynsb_glbh,zbjc_sj,ynsb_nyzl,LISTAGG (zbjc_sz, ',') WITHIN GROUP (ORDER BY ynsbsis_order) zbjc_sz");
		querySql.append(" FROM(SELECT sb.ynsb_id,jc.SISTIME AS zbjc_sj,sb.ynsb_glbh,SIS.ynsbsis_bs,jc.SISVALUE as zbjc_sz,"
				+ "sis.ynsbsis_order,sb.ynsb_nyzl FROM EAST_YNSB sb LEFT JOIN EAST_YNSBSIS sis ON sis.ynsb_id = sb.ynsb_id"
				+ " LEFT JOIN ZD_TEST2 jc ON jc.SISID = sis.ynsbsis_bs");
		StringBuffer countSql = new StringBuffer(
				"SELECT count(*) FROM (SELECT ynsb_id FROM(SELECT sb.ynsb_id,jc.SISTIME AS zbjc_sj,"
						+ " sb.ynsb_glbh,sb.ynsb_nyzl FROM EAST_YNSB sb LEFT JOIN EAST_YNSBSIS sis ON sis.ynsb_id = sb.ynsb_id"
						+ " LEFT JOIN ZD_TEST2 jc ON jc.SISID = sis.ynsbsis_bs ");
		// 开始时间
		if (querybo.getStarttime() != null
				&& !querybo.getStarttime().equals("")) {
			querySql.append(" and jc.SISTIME >='")
					.append(querybo.getStarttime()).append("'");
			countSql.append(" and jc.SISTIME >='")
					.append(querybo.getStarttime()).append("'");
		}
		// 结束时间
		if (querybo.getEndtime() != null && !querybo.getEndtime().equals("")) {
			querySql.append(" and jc.SISTIME<='").append(querybo.getEndtime())
					.append("'");
			countSql.append(" and jc.SISTIME<='").append(querybo.getEndtime())
					.append("'");
		}
		// 设备id
		querySql.append(" where sb.ynsb_id = '").append(querybo.getSbid())
				.append("'");
		countSql.append(" where sb.ynsb_id = '").append(querybo.getSbid())
				.append("'");
		
		// 能源种类
		if(querybo.getNyzl()!=null && !"".equals(querybo.getNyzl())){
			querySql.append(" and sb.ynsb_nyzl = '").append(querybo.getNyzl())
					.append("'");
			countSql.append(" and sb.ynsb_nyzl = '").append(querybo.getNyzl())
					.append("'");	
		}

		querySql.append(" AND sis.YNSBSIS_BS IS NOT null ");
		countSql.append(" AND sis.YNSBSIS_BS IS NOT null ");

		// 用能设备编号
		if (querybo.getYnsbbh() != null && !querybo.getYnsbbh().equals("")) {
			querySql.append(" and sb.YNSB_GLBH = '")
					.append(querybo.getYnsbbh()).append("'");
			countSql.append(" and sb.YNSB_GLBH = '")
					.append(querybo.getYnsbbh()).append("'");
		}
		querySql.append(" ) GROUP BY ynsb_id,zbjc_sj,ynsb_nyzl,ynsb_glbh order by zbjc_sj desc");
		countSql.append(" ) GROUP BY ynsb_id,zbjc_sj,ynsb_nyzl,ynsb_glbh)");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (vo != null) {
			list = queryPageListBySql(vo, countSql.toString(),
					querySql.toString());
		} else {
			list = queryListBySql(querySql.toString());
		}
		return list;
	}
	
	//查询班累计
	public String queryBlj(String startTime, String endTime, ZbjcQueryCondition querybo) throws BkmsException{
		
		StringBuffer querySql = new StringBuffer(
				"select zbjc_sz from ( SELECT ynsb_id,ynsb_glbh,zbjc_sj,ynsb_nyzl,LISTAGG (zbjc_sz, ',') WITHIN GROUP (ORDER BY ynsbsis_order) zbjc_sz");
		querySql.append(" FROM(SELECT sb.ynsb_id,jc.SISTIME AS zbjc_sj,sb.ynsb_glbh,SIS.ynsbsis_bs,jc.SISVALUE as zbjc_sz,"
				+ "sis.ynsbsis_order,sb.ynsb_nyzl FROM EAST_YNSB sb LEFT JOIN EAST_YNSBSIS sis ON sis.ynsb_id = sb.ynsb_id"
				+ " LEFT JOIN ZD_TEST2 jc ON jc.SISID = sis.ynsbsis_bs");

		// 设备id
		querySql.append(" where sb.ynsb_id = '").append(querybo.getSbid())
				.append("'");
		
		querySql.append(" AND sis.YNSBSIS_BS IS NOT null ");
		
		// 用能设备编号
		if (querybo.getYnsbbh() != null && !querybo.getYnsbbh().equals("")) {
			querySql.append(" and sb.YNSB_GLBH = '")
					.append(querybo.getYnsbbh()).append("'");
		
		}
		querySql.append(" ) GROUP BY ynsb_id,zbjc_sj,ynsb_nyzl,ynsb_glbh order by zbjc_sj desc  ) where zbjc_sj = '"+startTime+"'");
		List<Map<String, String>> list1 = new ArrayList<Map<String, String>>();
		list1 = queryListBySql(querySql.toString());

		////////////////
		
		StringBuffer querySql2 = new StringBuffer(
				"select zbjc_sz from ( SELECT ynsb_id,ynsb_glbh,zbjc_sj,ynsb_nyzl,LISTAGG (zbjc_sz, ',') WITHIN GROUP (ORDER BY ynsbsis_order) zbjc_sz");
		querySql2.append(" FROM(SELECT sb.ynsb_id,jc.SISTIME AS zbjc_sj,sb.ynsb_glbh,SIS.ynsbsis_bs,jc.SISVALUE as zbjc_sz,"
				+ "sis.ynsbsis_order,sb.ynsb_nyzl FROM EAST_YNSB sb LEFT JOIN EAST_YNSBSIS sis ON sis.ynsb_id = sb.ynsb_id"
				+ " LEFT JOIN ZD_TEST2 jc ON jc.SISID = sis.ynsbsis_bs");
		
		// 设备id
		querySql2.append(" where sb.ynsb_id = '").append(querybo.getSbid())
				.append("'");
		
		querySql2.append(" AND sis.YNSBSIS_BS IS NOT null ");
		
		// 用能设备编号
		if (querybo.getYnsbbh() != null && !querybo.getYnsbbh().equals("")) {
			querySql2.append(" and sb.YNSB_GLBH = '")
					.append(querybo.getYnsbbh()).append("'");
		
		}
		querySql2.append(" ) GROUP BY ynsb_id,zbjc_sj,ynsb_nyzl,ynsb_glbh order by zbjc_sj desc  ) where zbjc_sj = '"+endTime+"'");
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2 = queryListBySql(querySql2.toString());
		
		if(list2 != null && list1 != null){
			Map<String, String> m2 = list2.get(0);
			String s2_[] = m2.get("ZBJC_SZ").split(",");
			String s2 = s2_[s2_.length-1];
			
			Map<String, String> m1 = list1.get(0);
			String s1_[] = m1.get("ZBJC_SZ").split(",");
			String s1 = s1_[s1_.length-1];
			
			float blj = Float.parseFloat(s2) - Float.parseFloat(s1);
			DecimalFormat df = new DecimalFormat("0.00");
			return String.valueOf(df.format(blj));
			//return String.valueOf(blj);
		}else {
			return "";
		}
		
		
		
		
	}

	/**
	 * 分页查询设备监测历史数据
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistorySbjc(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		StringBuffer querySql = new StringBuffer("");
		querySql.append(" select s.zdsb_id zdsb_id,s.zdsb_ssbm zdsb_ssbm,s.zdsb_sbmc zdsb_sbmc,case when s.sisvalue >0 then sisvalue  else '0'  end sisvalue,s.zdsb_jxrq sistime,s.zdsb_state zdsb_state,round(to_number(TO_DATE(s.zdsb_qnsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE((SELECT To_char(trunc(sysdate,'yyyy'), 'yyyy-mm-dd hh24:mi:ss') FROM dual),'yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_qnsc,round(to_number(TO_DATE(s.zdsb_zsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE('2018-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_zsc  from east_zdsb_state s ");
		StringBuffer countSql = new StringBuffer("");
		countSql.append(" select count(*)  from east_zdsb_state s ");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		// 历史查询
		if (querybo.getStarttime() != null
				&& !querybo.getStarttime().equals("")
				&& querybo.getEndtime() != null
				&& !querybo.getEndtime().equals("")) {
			whereSql.append(" and s.zdsb_jxrq >='")
					.append(querybo.getStarttime()).append("'");
			whereSql.append(" and s.zdsb_jxrq <='")
					.append(querybo.getEndtime()).append("'");
		} else if (querybo.getStarttime() != null
				&& !querybo.getStarttime().equals("")) {
			// 日报表
			whereSql.append(" and s.zdsb_jxrq like '")
					.append(querybo.getStarttime()).append("%'");
		}
		// 用能设备编号
		if (querybo.getYnsbbh() != null && !querybo.getYnsbbh().equals("")) {
			whereSql.append(" and s.zdsb_sbmc = '").append(querybo.getYnsbbh())
					.append("'");
		}
		whereSql.append(" order by s.zdsb_ssbm,s.zdsb_sbmc");
		querySql.append(whereSql.toString());
		countSql.append(whereSql.toString());
		return queryPageListBySql(vo, countSql.toString(), querySql.toString());
	}

	/**
	 * 查询设备监测历史数据
	 * 
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> querySbjcData(ZbjcQueryCondition querybo)
			throws BkmsException {
		StringBuffer querySql = new StringBuffer("");
		querySql.append(" select s.zdsb_id zdsb_id,s.zdsb_ssbm zdsb_ssbm,s.zdsb_sbmc zdsb_sbmc,case when s.sisvalue >0 then sisvalue  else '0'  end sisvalue,s.zdsb_jxrq sistime,s.zdsb_state zdsb_state,round(to_number(TO_DATE(s.zdsb_qnsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE((SELECT To_char(trunc(sysdate,'yyyy'), 'yyyy-mm-dd hh24:mi:ss') FROM dual),'yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_qnsc,round(to_number(TO_DATE(s.zdsb_zsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE('2018-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_zsc  from east_zdsb_state s ");
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		// 历史查询
		if (querybo.getStarttime() != null
				&& !querybo.getStarttime().equals("")
				&& querybo.getEndtime() != null
				&& !querybo.getEndtime().equals("")) {
			whereSql.append(" and s.zdsb_jxrq >='")
					.append(querybo.getStarttime()).append("'");
			whereSql.append(" and s.zdsb_jxrq <='")
					.append(querybo.getEndtime()).append("'");
		} else if (querybo.getStarttime() != null
				&& !querybo.getStarttime().equals("")) {
			// 日报表
			whereSql.append(" and s.zdsb_jxrq like'")
					.append(querybo.getStarttime()).append("%'");
		}
		// 用能设备编号
		if (querybo.getYnsbbh() != null && !querybo.getYnsbbh().equals("")) {
			whereSql.append(" and s.zdsb_sbmc = '").append(querybo.getYnsbbh())
					.append("'");
		}
		whereSql.append(" order by s.zdsb_ssbm,s.zdsb_sbmc");
		querySql.append(whereSql.toString());
		return queryListBySql(querySql.toString());
	}

	/**
	 * 分页查询历史指标值
	 * 
	 * @param vo
	 * @param querybo
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryHistoryZbzInfo(PageVO vo,
			ZbjcQueryCondition querybo) throws BkmsException {
		StringBuffer querySql = new StringBuffer(
				"SELECT ynsb_id,ynsb_glbh,zbjc_sj,ynsb_nyzl,LISTAGG (zbjc_sz, ',') WITHIN GROUP (ORDER BY ynsbsis_order) zbjc_sz");
		querySql.append(" FROM(SELECT sb.ynsb_id,jc.SISTIME AS zbjc_sj,sb.ynsb_glbh,SIS.ynsbsis_bs,jc.SISVALUE as zbjc_sz,"
				+ "sis.ynsbsis_order,sb.ynsb_nyzl FROM EAST_YNSB sb LEFT JOIN EAST_YNSBSIS sis ON sis.ynsb_id = sb.ynsb_id"
				+ " LEFT JOIN ZD_TEST2 jc ON jc.SISID = sis.ynsbsis_bs");
		StringBuffer countSql = new StringBuffer(
				"SELECT count(*) FROM (SELECT ynsb_id FROM(SELECT sb.ynsb_id,jc.SISTIME AS zbjc_sj,"
						+ " sb.ynsb_glbh,sb.ynsb_nyzl FROM EAST_YNSB sb LEFT JOIN EAST_YNSBSIS sis ON sis.ynsb_id = sb.ynsb_id"
						+ " LEFT JOIN ZD_TEST2 jc ON jc.SISID = sis.ynsbsis_bs ");
		// 开始时间
		if (StringUtils.isNotBlank(querybo.getStarttime())) {
			querySql.append(" and jc.SISTIME >='")
					.append(querybo.getStarttime()).append("'");
			countSql.append(" and jc.SISTIME >='")
					.append(querybo.getStarttime()).append("'");
		}
		// 结束时间
		if (StringUtils.isNotBlank(querybo.getEndtime())) {
			querySql.append(" and jc.SISTIME<='").append(querybo.getEndtime())
					.append("'");
			countSql.append(" and jc.SISTIME<='").append(querybo.getEndtime())
					.append("'");
		}
		// 设备id
		querySql.append(" where sb.ynsb_id = '").append(querybo.getSbid())
				.append("'");
		countSql.append(" where sb.ynsb_id = '").append(querybo.getSbid())
				.append("'");

		querySql.append(" AND sis.YNSBSIS_BS IS NOT null ");
		countSql.append(" AND sis.YNSBSIS_BS IS NOT null ");

		// 能源种类
		if (StringUtils.isNotBlank(querybo.getNyzl())) {
			querySql.append(" and sb.YNSB_NYZL = '").append(querybo.getNyzl())
					.append("'");
			countSql.append(" and sb.YNSB_NYZL = '").append(querybo.getNyzl())
					.append("'");
		}
		// 用能设备编号
		if (StringUtils.isNotBlank(querybo.getYnsbbh())) {
			querySql.append(" and sb.YNSB_GLBH = '")
					.append(querybo.getYnsbbh()).append("'");
			countSql.append(" and sb.YNSB_GLBH = '")
					.append(querybo.getYnsbbh()).append("'");
		}
		querySql.append(" ) GROUP BY ynsb_id,zbjc_sj,ynsb_nyzl,ynsb_glbh order by zbjc_sj desc");
		countSql.append(" ) GROUP BY ynsb_id,zbjc_sj,ynsb_nyzl,ynsb_glbh)");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (vo != null) {
			list = queryPageListBySql(vo, countSql.toString(),
					querySql.toString());
		} else {
			list = queryListBySql(querySql.toString());
		}
		return list;
	}

	/**
	 * 平衡图数据手工补录
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> querySglrByMonthMax(String fid, String sj)
			throws BkmsException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'6') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_TOTAL)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='6' and  substr(t.east_sjbl_date,'0','7') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='6'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'1') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_LJZ)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='1' and  substr(t.east_sjbl_date,'0','7') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='1'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'4') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_LJZ)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='4' and  substr(t.east_sjbl_date,'0','7') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='4'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'7') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_TOTAL)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='7' and  substr(t.east_sjbl_date,'0','7') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='7'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");

		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'3') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_LJZ)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='3' and  substr(t.east_sjbl_date,'0','7') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='3'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'9') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_LJZ)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='9' and  substr(t.east_sjbl_date,'0','7') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='9'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'5') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_LJZ)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='5' and  substr(t.east_sjbl_date,'0','7') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='5'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 正东平衡图自动
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdByMonthMax(String fid, String sj)
			throws BkmsException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'7' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,7)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='7' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'3' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,7)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='3' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'9' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,7)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='9' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'5' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,7)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='5' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'6' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,7)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='6' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'1' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,7)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='1' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'4' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,7)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='4' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 平衡图数据（手工补录年份）
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> querySglrByYearMax(String fid, String sj)
			throws BkmsException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'6') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_TOTAL)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='6' and  substr(t.east_sjbl_date,'0','4') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='6'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'1') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_LJZ)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='1' and  substr(t.east_sjbl_date,'0','4') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='1'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		sql.append(" union all ");
		sql.append(
				"select nvl(max(val.east_sjbl_zdjz),'"
						+ fid
						+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'4') as east_sjbl_nyzl,"
						+ "nvl(max(sum(val.EAST_SJBL_LJZ)),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
						+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
						+ " where t.EAST_SJBL_ZDJZ='")
				.append(fid)
				.append("'")
				.append(""
						+ "  and t.east_sjbl_nyzl='4' and  substr(t.east_sjbl_date,'0','4') = '")
				.append(sj)
				.append(""
						+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='4'"
						+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 正东平衡图（年份自动）
	 * 
	 * @param fid
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdByYearMax(String fid, String sj)
			throws BkmsException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'6' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,4)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='6' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'1' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,4)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='1' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		sql.append(" union all ");
		sql.append(
				"select nvl(sum(jc.sisvalue),0) as total,'4' as nyzl from zd_test2 jc,"
						+ " (select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,4)='"
						+ sj
						+ "' )sj"
						+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
				.append(fid)
				.append("'")
				.append(" and sb.ynsb_nyzl='4' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
						+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = sj.sj");
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 实时监测动态数据手工补录数据
	 * 
	 * @param fid
	 *            机组id
	 * @param sj
	 *            时间
	 * @param nyzl
	 *            能源种类
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> querySglrSumNy(String fid, String sj,
			String[] nyzl) throws BkmsException {
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < nyzl.length; i++) {
			if (i > 0) {
				sql.append(" union all ");
			}
			String sumfield = "val.EAST_SJBL_LJZ";
			// 电进电出
			if (nyzl[i].equals("7") || nyzl[i].equals("6") || nyzl[i].equals("10")) {
				sumfield = "val.EAST_SJBL_TOTAL";
			}
			// 热水出
			String sqlwhere = "";
			if (nyzl[i].equals("9")) {
				sqlwhere = " and val.EAST_SJBL_SBID  <>'402880356097e29a016097f0ab490000' ";
			}
			
			///////
			int sjlength = sj.length(); // 时间长度
			
			if (nyzl[i].equals("6")) {
				sqlwhere = " and val.east_sjbl_sbbh like '%受电正孙%' ";
			}
			if (nyzl[i].equals("10")) {
				int nyzl_ = 6;
				sqlwhere = " and val.east_sjbl_sbbh like '%B%' ";
				sql.append(
						"select nvl(max(val.east_sjbl_zdjz),'"
								+ fid
								+ "') as east_sjbl_zdjz,'10' as east_sjbl_nyzl,"
								+ "nvl(max(sum("
								+ sumfield
								+ ")),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
								+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
								+ " where t.EAST_SJBL_ZDJZ='")
						.append(fid)
						.append("'")
						.append("" + "  and t.east_sjbl_nyzl='" + nyzl_
								+ "' and  substr(t.east_sjbl_date,'0','" + sjlength
								+ "') = '")
						.append(sj)
						.append(""
								+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='"
								+ nyzl_
								+ "' "
								+ sqlwhere
								+ ""
								+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
			}else {
				sql.append(
						"select nvl(max(val.east_sjbl_zdjz),'"
								+ fid
								+ "') as east_sjbl_zdjz,nvl(max(val.east_sjbl_nyzl),'"
								+ nyzl[i]
								+ "') as east_sjbl_nyzl,"
								+ "nvl(max(sum("
								+ sumfield
								+ ")),'0') as total from EAST_SJBL_MANUAL_MAKEUP val,"
								+ " (select max(t.east_sjbl_date) as sj from EAST_SJBL_MANUAL_MAKEUP t"
								+ " where t.EAST_SJBL_ZDJZ='")
						.append(fid)
						.append("'")
						.append("" + "  and t.east_sjbl_nyzl='" + nyzl[i]
								+ "' and  substr(t.east_sjbl_date,'0','" + sjlength
								+ "') = '")
						.append(sj)
						.append(""
								+ "' )maxsj where val.east_sjbl_date = maxsj.sj and val.east_sjbl_nyzl='"
								+ nyzl[i]
								+ "' "
								+ sqlwhere
								+ ""
								+ " group by val.east_sjbl_zdjz,val.east_sjbl_nyzl ");
			}
			
			
			
		}
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 实时监测动态数据自动提取数据
	 * 
	 * @param fid
	 *            机组id
	 * @param sj
	 *            时间
	 * @param nyzl
	 *            能源种类
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdSumNy(String fid, String sj,
			String[] nyzl) throws BkmsException {
		StringBuffer sql = new StringBuffer();
		// int sjlength = sj.length(); //时间长度
		// String sqlsj =
		// "select max(t.sistime) as sj from zd_test2 t where substr(t.sistime,0,"+sjlength+")='"+sj+"'";
		// //查询最大时间sql
		// List<Map<String, Object>> sjlist = jdbcTemplate.queryForList(sqlsj);
		// String maxsj = Tools.getSysDate("yyyy-MM");
		// if(sjlist!=null&&sjlist.size()>0){
		// maxsj = Tools.filterNull(sjlist.get(0).get("sj"));
		// }
		for (int i = 0; i < nyzl.length; i++) {
			if (i > 0) {
				sql.append(" union all ");
			}
			sql.append(
					"select nvl(sum(jc.sisvalue),0) as total,'"
							+ nyzl[i]
							+ "' as nyzl from zd_test2 jc"
							+ " where exists(select sb.ynsb_id,sis.ynsbsis_bs,sb.ynsb_nyzl from east_ynsb sb,east_ynsbsis sis where sb.ynsb_fid='")
					.append(fid)
					.append("'")
					.append(" and sb.ynsb_nyzl='"
							+ nyzl[i]
							+ "' and sis.ynsb_id=sb.ynsb_id and sis.ynsbsis_islj='1'"
							+ " and jc.sisid = sis.ynsbsis_bs ) and jc.sistime = '"
							+ sj + "'");
		}
		return jdbcTemplate.queryForList(sql.toString());
	}

	/**
	 * 正东平衡图查询面积
	 * 
	 * @param sj
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, Object>> queryZdMj(String sj) throws BkmsException {
		String sql = "select sum(t.EAST_SJBL_LJZ) as EAST_SJBL_LJZ from EAST_SJBL_MANUAL_MAKEUP t"
				+ " where t.EAST_SJBL_SBID='402880356097e29a016097f0ab490000'"
				+ " AND SUBSTR (t.east_sjbl_date, '0', '7') = '" + sj + "' ";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 查询能效检测
	 * 
	 * @return
	 */
	public DataQuery queryNcjcList(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "EAST_NXJC",
				"NXJC_ID,NXJC_SJ as NXJCSJ,NXJC_VAL,NXJC_DW,NXJC_SB,NXJC_LX");
		return dbQuery;
	}

	/**
	 * 条件查询能效检测
	 * 
	 * @param starttime
	 * @param endtime
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<NxjcBO> queryListNxjcBy(String starttime, String endtime,
			NxjcBO bo) throws RollbackableException {
		StringBuffer hql = new StringBuffer("from NxjcBO where 1=1");
		if (!starttime.equals("")) {
			hql.append(" and nxjc_sj>= '").append(starttime).append("'");
		}
		if (!endtime.equals("")) {
			hql.append(" and nxjc_sj<= '").append(endtime).append("'");
		}
		if (bo.getNxjc_lx() != null && !bo.getNxjc_lx().equals("")) {
			hql.append(" and nxjc_lx = '").append(bo.getNxjc_lx()).append("'");
		}
		hql.append(" order by nxjc_sj desc");
		return queryHqlList(hql.toString());
	}

	/**
	 * 园区电表数据查询
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryParkMeter(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(query,
						"ZDEP_PARKMETER",
						"TENANTNAME,METERNO,NUMERICDATE,PEAKVALUE,VALLEYVALUE,FAIRVALUE,POINTVALUE,TOTALVALUE,RATE");
		return dbQuery;
	}

	/**
	 * 查询园区电表
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery queryParkMeterWatch1(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(query,
						"(select TENANTNAME,METERNO from ZDEP_PARKMETER group by TENANTNAME,METERNO)",
						"TENANTNAME,METERNO");
		return dbQuery;
	}
	
	/**
	 * 查询园区电表
	 * @param query
	 * @return
	 */
	public DataQuery queryParkMeterWatch(DataQuery query){
		DataQuery dbQuery = DataQuery
				.init(query,
						"EAST_YQDB",
						"YQDB_ID,YQDB_ZH,YQDB_BH,YQDB_JDSJ,YQDB_BL");
		return dbQuery;
	}
	
	

	/**
	 * 实时数据zd_test2
	 * 
	 * @param id
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZdTest2BO> queryZdTest2BoById(String id)
			throws RollbackableException {
		String wherehql = "";
		if (id != "") {
			wherehql = " and c.id ='" + id + "' ";
		}
		String hql = "from ZdTest2BO c where 1=1 " + wherehql + "";
		return queryHqlList(hql);
	}
	
	public ZdTest2BO queryZdTest2BoBySisidAndSistime(String sisid, String sistime) throws RollbackableException{
		String wherehql = " and c.sisid = '"+sisid+"' and c.sistime = '"+sistime+"'";
	
		String hql = "from ZdTest2BO c where 1=1 " + wherehql + "";
		List list  = hibernateTemplate.find(hql);
		if(list.size() > 0){
			ZdTest2BO bo = (ZdTest2BO)list.get(0);
			return bo;
		}
		
		return null;
	}
}