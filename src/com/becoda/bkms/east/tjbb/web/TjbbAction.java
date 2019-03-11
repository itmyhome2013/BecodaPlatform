package com.becoda.bkms.east.tjbb.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.east.ssjc.SsjcConstants;
import com.becoda.bkms.east.ssjc.ucc.IZbjcUCC;
import com.becoda.bkms.east.tjbb.TjbbConstants;
import com.becoda.bkms.east.tjbb.pojo.YddlfpdBO;
import com.becoda.bkms.east.tjbb.ucc.ITjbbUCC;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.farm.core.report.ReportException;
import com.farm.core.report.ReportManagerInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.sql.utils.JsonUtil;
import com.farm.core.time.TimeTool;
import com.farm.core.util.DBUtil;
import com.farm.core.util.FarmManager;

/**
 * 
 * 统计报表
 */
public class TjbbAction extends GenericPageAction {

	private DataResult result;// 结果集合
	private Map<String, Object> jsonResult;// 结果集合
	private List<Map<String, Object>> resultList;
	private DataQuery query;// 条件查询

	private String lastDate; // 上个月的年月
	private String lastLastDate; // 上上个月的年月

	private String lastDay; // 昨天
	private String lastLastDay; // 前天

	private String rq; // 日期
	private String type; // year:年度 ，month：月度
	private String title; // 标题

	private InputStream inputStream;
	private String filename;
	private String treeId;

	private ReportManagerInter reportUCC;
	private ITjbbUCC ucc;

	public TjbbAction() {
		try {
			ucc = (ITjbbUCC) BkmsContext.getBean("tjbbUCC");
			reportUCC = (ReportManagerInter) BkmsContext
					.getBean("excelReportId");
		} catch (BkmsException e) {
			e.printStackTrace();
		}
	}

	public String forSend() {
		lastDate = DateUtil.getLastYearMonth("yyyy年MM月");
		lastLastDate = DateUtil.getLastLastYearMonth("yyyy年MM月");

		lastDay = DateUtil.getLastDay("yyyy年MM月dd日");
		lastLastDay = DateUtil.getLastLastDay("yyyy年MM月dd日");
		
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}

		return "success";
	}

	/**
	 * 月度动力分配单
	 * 
	 * @return
	 */
	public String yddlfpd() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String sjbl_date = request.getParameter("sjbl_date");
			query = EasyUiUtils.formatGridQuery(request, query);
			DBRule rq = query.getAndRemoveRule("RQ");
			String rq_ = "";
			StringBuffer whereSQL = new StringBuffer(" and 1=1");
			if (rq != null) {
				whereSQL.append(" and RQ= '").append(rq.getValue()).append("'");
				rq_ = rq.getValue();
			} else {
				if(!Tools.stringIsNull(sjbl_date)){
					whereSQL.append(" and RQ= '").append(sjbl_date).append("'");
				}else{
					String dldDate = DateUtil.getLastYearMonth("yyyy-MM");
					whereSQL.append(" and RQ= '").append(dldDate).append("'");
				}
				rq_ = DateUtil.getLastYearMonth("yyyy-MM");
			}
			query.addUserWhere(whereSQL.toString());
			query.setPagesize(1000);
			query.addUserWhere(" ORDER by cast(YNSB_NYZL AS int) asc,  REGEXP_SUBSTR(YNSB_GLBH, '[[:alpha:]]+'),cast(REGEXP_SUBSTR(YNSB_GLBH, '[0-9]+') as int) ");
			DataResult result;
			
			///
			DBRule fid = query.getAndRemoveRule("YNSB_FID");
			String ynsb_fid = "";
			if(fid != null){
				ynsb_fid = fid.getValue();
			}else{
				ynsb_fid = "3010401101"; //默认正东集团用能单位计量
			}
			
			if("3010401104".equals(ynsb_fid)){ //1、恒东热电燃气蒸汽锅炉
				result = ucc.yddlfpd1(query,rq_).search();
			}else if("3010401105".equals(ynsb_fid)){ //2、恒东热电蒸汽+热水换热机组
				result = ucc.yddlfpd2(query,rq_).search();
			}else if("3010401107".equals(ynsb_fid)){ //3、动南燃气联合循环发电机组
				result = ucc.yddlfpd3(query,rq_).search();
			}else if("3010401108".equals(ynsb_fid)){ //4、动南燃气热水锅炉+换热机组 
				result = ucc.yddlfpd4(query,rq_).search();
			}else if("3010401109".equals(ynsb_fid)){ //5、动力南厂燃气蒸汽锅炉
				result = ucc.yddlfpd5(query,rq_).search();
			}else if("3010401101".equals(ynsb_fid)){
				result = ucc.yddlfpd(query).search();
			}else{
				result = ucc.yddlfpd(query).search(); //如果其他选项则不查询结果
			}	
			
			///
			
			
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("NYZL"),"YNSB_NYZL");

			jsonResult = EasyUiUtils.formatGridData(result);
			resultList = result.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
		}
		return "success";
	}

	/**
	 * 月度动力分配单导出
	 * 
	 * @return
	 */
	public String yddlfpdExport() {
		try {
			yddlfpd();
			if (rq == null || "".equals(rq)) {
				rq = DateUtil.getLastYearMonth("yyyy-MM");
			}
			// ------------------------开始导出
			Map<String, Object> para = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (resultList != null && resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> map = resultList.get(i);
					String nyzl = (String) map.get("YNSB_NYZL");
					if (!Tools.stringIsNull(nyzl)) {
						if (TjbbConstants.TRQJ.equals(nyzl)) {
							map.put("DW", "Nm3");
						}else if (TjbbConstants.RSJ.equals(nyzl) || TjbbConstants.RSC.equals(nyzl)) {
							map.put("DW", "GJ");
						} else if (TjbbConstants.ZQJ.equals(nyzl) 
								|| TjbbConstants.ZQC.equals(nyzl)
								|| TjbbConstants.SYSJ.equals(nyzl)
								|| TjbbConstants.SYSC.equals(nyzl)								
								|| TjbbConstants.RHSJ.equals(nyzl)) {
							map.put("DW", "t");
						} else if (TjbbConstants.DJ.equals(nyzl) || TjbbConstants.DC.equals(nyzl)) {
							map.put("DW", "kWh");
						}
					}
					list.add(map);
				}
			}

			para.put("result", list);
			para.put("lastDate", DateUtil.formartDate(rq, 0));
			para.put("lastLastDate", DateUtil.formartDate(rq, 1));
			filename = "月度动力分配单.xls";
			reportUCC.generate(filename, para);
			inputStream = new FileInputStream(new File(
					reportUCC.getReportPath(filename)));
			filename = DateUtil.formartDate(rq, 0) + "度动力分配单.xls";
		} catch (ReportException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "success";
	}

	private void ArrayList() {
		// TODO Auto-generated method stub

	}

	// 编辑月度动力分配单
	public void editYddlfpd() throws IOException {
		boolean flag = false;
		String jsondata = request.getParameter("jsondata");
		try {
			JSONObject obj = JSONObject.fromObject(jsondata);
			ITjbbUCC ucc = (ITjbbUCC) BkmsContext.getBean("tjbbUCC");
			if (obj != null) {
				YddlfpdBO bo = new YddlfpdBO();
				bo.setId(Tools.filterNull(obj.getString("ID")));
				bo.setRq(Tools.filterNull(obj.getString("RQ")));
				bo.setYnsb_glbh(Tools.filterNull(obj.getString("YNSB_GLBH")));
				bo.setLast1value(Tools.filterNull(obj.getString("LAST1VALUE")));
				bo.setLast2value(Tools.filterNull(obj.getString("LAST2VALUE")));
				bo.setYnsb_nyzl(Tools.filterNull(obj.getString("YNSB_NYZL")));
				bo.setLjz(Tools.filterNull(obj.getString("LJZ")));
				bo.setShl(Tools.filterNull(obj.getString("SHL")));
				bo.setByjsl(Tools.filterNull(obj.getString("BYJSL")));
				bo.setSjjsl(Tools.filterNull(obj.getString("SJJSL")));
				bo.setJfzt(Tools.filterNull(obj.getString("JFZT")));
				ucc.editYddlfpd(bo);
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	/**
	 * 重新计算月度动力分配单
	 * 
	 * @throws IOException
	 */
	public void recountYddlfpd() throws IOException {
		boolean flag = false;
		String lastDate = request.getParameter("sjbl_date"); // 选中日期月
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance();
			Date date=sdf.parse(lastDate);
			cal.setTime(date);
			cal.add(Calendar.MONTH, -1);
			String lastLastDate = sdf.format(cal.getTime());// //选中日期上月

			Statement sta = null;
			Statement pstInsert = null;
			Connection conn = DBUtil.getConnection();
			sta = conn.createStatement();
			pstInsert = conn.createStatement();
			String sql = "select a.ynsb_fid,a.rq,a.ynsb_glbh,sum(a.LAST1VALUE) as LAST1VALUE,sum(a.LAST2VALUE) as LAST2VALUE,a.YNSB_NYZL,sum(a.LJZ) as ljz,sum(a.SHL) as SHL,sum(a.byjsl) as byjsl from(" +
					" select ynsb_fid,RQ,YNSB_GLBH,to_char(LAST1VALUE,'fm999999999990.000') LAST1VALUE,to_char(LAST2VALUE,'fm999999999990.000') LAST2VALUE,YNSB_NYZL,to_char(LJZ,'fm999999999990.000') LJZ,to_char(SHL,'fm999999999990.000') SHL,to_char(BYJSL,'fm999999999990.000') BYJSL FROM (SELECT a.ynsb_fid, cast('"
					+ lastDate
					+ "' AS varchar(10)) AS rq,a.ynsb_glbh,a.ynsb_nyzl,c.sisvalue AS last1value, d.sisvalue AS last2value,c.sisvalue - d.sisvalue AS ljz,(c.sisvalue - d.sisvalue) * 0.05 AS shl,(c.sisvalue - d.sisvalue) * 1.05 AS byjsl  FROM EAST_YNSB a left join EAST_YNSBSIS b on b.ynsb_id = a.ynsb_id left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select max(sistime),sisid FROM zd_test2 WHERE sistime like '"
					+ lastDate
					+ "%' group by sisid)) c  on c.sisid = b.ynsbsis_bs left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select max(sistime),sisid FROM zd_test2 WHERE sistime like '"
					+ lastLastDate
					+ "%' group by sisid)) d on d.sisid = b.ynsbsis_bs WHERE b.ynsbsis_islj = '1' and a.ynsb_fid ='3010401101'"
					+ " union "
					+ " SELECT a.EAST_SJBL_ZDJZ as ybsb_fid, cast('"
					+ lastDate
					+ "' AS varchar(10)) AS rq ,a.east_sjbl_sbbh as ynsb_glbh,a.east_sjbl_nyzl as ynsb_nyzl,a.east_sjbl_ljz as last1value,b.east_sjbl_ljz as last2value, a.east_sjbl_ljz - b.east_sjbl_ljz as ljz,(a.east_sjbl_ljz - b.east_sjbl_ljz) * 0.05 as shl,(a.east_sjbl_ljz - b.east_sjbl_ljz) * 1.05 as byjsl from (select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"
					+ lastDate
					+ "%' and east_sjbl_nyzl not in ('6', '7') group by east_sjbl_sbid)) a left join ( select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"
					+ lastLastDate
					+ "%' and east_sjbl_nyzl not in ('6', '7') group by east_sjbl_sbid)) b on b.east_sjbl_sbid = a.east_sjbl_sbid "
					+ " union "
					+ " SELECT a.EAST_SJBL_ZDJZ as ybsb_fid, cast('"
					+ lastDate
					+ "' AS varchar(10)) AS rq ,a.east_sjbl_sbbh as ynsb_glbh,a.east_sjbl_nyzl as ynsb_nyzl,a.east_sjbl_total as last1value,b.east_sjbl_total as last2value, a.east_sjbl_total - b.east_sjbl_total as ljz,(a.east_sjbl_total - b.east_sjbl_total) * 0.05 as shl,(a.east_sjbl_total - b.east_sjbl_total) * 1.05 as byjsl from (select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"
					+ lastDate
					+ "%' and east_sjbl_nyzl in('6','7') group by east_sjbl_sbid) ) a left join ( select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"
					+ lastLastDate
					+ "%' and east_sjbl_nyzl in('6','7') group by east_sjbl_sbid)) b on b.east_sjbl_sbid = a.east_sjbl_sbid "
					+ ")) a group by a.ynsb_glbh,a.ynsb_nyzl,a.rq,a.ynsb_fid ";

			PreparedStatement pst = conn
					.prepareStatement("delete from EAST_TJBB_YDDLFPD where rq like '"
							+ lastDate + "%' ");
			pst.execute();

			System.out.println(TimeTool.getFormatTimeDate14() + " info : 开始执行");
			ResultSet rs = sta.executeQuery(sql);

			while (rs.next()) {
				String id = Tools.filterNull(SequenceGenerator.getUUID());
				String ynsbfid = Tools.filterNull(rs.getString(1));
				String rq = Tools.filterNull(rs.getString(2));
				String ynsb_glbh = Tools.filterNull(rs.getString(3));
				String last1value = Tools.filterNull(rs.getString(4));
				String last2value = Tools.filterNull(rs.getString(5));
				String ynsb_nyzl = Tools.filterNull(rs.getString(6));
				String ljz = Tools.filterNull(rs.getString(7));
				String shl = Tools.filterNull(rs.getString(8));
				String byjsl = Tools.filterNull(rs.getString(9));

				String insertSQL = "insert into EAST_TJBB_YDDLFPD(id,ynsb_fid,rq,ynsb_glbh,last1value,last2value,ynsb_nyzl,ljz,shl,byjsl) values ('"
						+ id
						+ "','"
						+ynsbfid
						+ "','"
						+ rq
						+ "','"
						+ ynsb_glbh
						+ "','"
						+ last1value
						+ "','"
						+ last2value
						+ "','"
						+ ynsb_nyzl
						+ "','" + ljz + "','" + shl + "','" + byjsl + "')";
				pstInsert.addBatch(insertSQL);
			}

			pstInsert.executeBatch();
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 执行结束");
			DBUtil.closeAll(rs, sta, conn); // 关闭链接
			DBUtil.closeStatement(pst);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	/**
	 * 重新计算能源用户数据日报表
	 * 
	 * @throws IOException
	 */
	public void recountNyyhsjrbb() throws IOException {
		boolean flag = false;
		String lastDate = request.getParameter("sjbl_date"); // 选中日期
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date date=sdf.parse(lastDate);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, -1);
			String lastLastDate = sdf.format(cal.getTime());// //选中日期上一天

			Statement sta = null;
			Statement pstInsert = null;
			Connection conn = DBUtil.getConnection();
			sta = conn.createStatement();
			pstInsert = conn.createStatement();
			String sql = " select RQ,YNSB_GLBH,to_char(LAST1VALUE,'fm999999999990.000') LAST1VALUE,to_char(LAST2VALUE,'fm999999999990.000') LAST2VALUE,YNSB_NYZL,to_char(LJZ,'fm999999999990.000') LJZ,to_char(SHL,'fm999999999990.000') SHL,to_char(BYJSL,'fm999999999990.000') BYJSL,ynsb_fid FROM (SELECT cast('"+lastDate+"' AS varchar(10)) AS rq,a.ynsb_glbh,a.ynsb_nyzl,c.sisvalue AS last1value, d.sisvalue AS last2value,c.sisvalue - d.sisvalue AS ljz,(c.sisvalue - d.sisvalue) * 0.05 AS shl,(c.sisvalue - d.sisvalue) * 1.05 AS byjsl ,a.ynsb_fid FROM EAST_YNSB a left join EAST_YNSBSIS b on b.ynsb_id = a.ynsb_id left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select max(sistime),sisid FROM zd_test2 WHERE sistime like '"+lastDate+"%' group by sisid)) c  on c.sisid = b.ynsbsis_bs left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select min(sistime),sisid FROM zd_test2 WHERE sistime like '"+lastDate+"%' group by sisid)) d on d.sisid = b.ynsbsis_bs WHERE b.ynsbsis_islj = '1' and a.ynsb_fid = '3010401101'" +
					")  WHERE 1=1 union all select '"+lastDate+"' as rq ,a.tenantname as YNSB_GLBH,a.totalvalue as LAST1VALUE,b.totalvalue as LAST2VALUE,'电能表' as YNSB_GLBH,to_char((a.totalvalue - b.totalvalue), 'fm999999999990.000')as LJZ,to_char('0', 'fm999999999990.000') as shl,to_char('0', 'fm999999999990.000') AS byjsl,'' as ynsb_fid from (select t.tenantname,t.meterno,t.totalvalue from zdep_parkmeter t where t.NumericDate='"+lastDate+"')a,(select t.tenantname,t.meterno,t.totalvalue from zdep_parkmeter t where t.NumericDate='"+lastLastDate+"')b where a.meterno = b.meterno";
			
			PreparedStatement pst = conn.prepareStatement("delete from EAST_TJBB_NYYHSJRBB where rq like '"+lastDate+"%'  ");
			pst.execute();
			
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 开始执行" );
			ResultSet rs = sta.executeQuery(sql);
			
			while(rs.next()){
				String rq = Tools.filterNull(rs.getString(1));
				String ynsb_glbh = Tools.filterNull(rs.getString(2));
				String last1value = Tools.filterNull(rs.getString(3));
				String last2value = Tools.filterNull(rs.getString(4));
				String ynsb_nyzl = Tools.filterNull(rs.getString(5));
				String ljz = Tools.filterNull(rs.getString(6));
				String shl = Tools.filterNull(rs.getString(7));
				String byjsl = Tools.filterNull(rs.getString(8));
				String ynsb_fid = Tools.filterNull(rs.getString(9));
				
				String insertSQL = "insert into EAST_TJBB_NYYHSJRBB(rq,ynsb_glbh,last1value,last2value,ynsb_nyzl,ljz,shl,byjsl,ynsb_fid) values ('"+rq+"','"+ynsb_glbh+"','"+last1value+"','"+last2value+"','"+ynsb_nyzl+"','"+ljz+"','"+shl+"','"+byjsl+"','"+ynsb_fid+"')";
				pstInsert.addBatch(insertSQL);
			}
			
			pstInsert.executeBatch();
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 执行结束" );
			DBUtil.closeAll(rs, sta, conn); // 关闭链接
			DBUtil.closeStatement(pst);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	/**
	 * 能源用户数据日报表
	 * 
	 * @return
	 */
	public String nyyhsjrbb() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(1000);

			String sjbl_date = request.getParameter("sjbl_date");
			if (query.getQueryRule().size() == 0) {
				if(!Tools.stringIsNull(sjbl_date)){
					query.addRule(new DBRule("RQ", sjbl_date, "="));
				}else{
					query.addRule(new DBRule("RQ", DateUtil.getLastDay("yyyy-MM-dd"), "="));
					sjbl_date = DateUtil.getLastDay("yyyy-MM-dd");
				}				
			}else{
				DBRule rq = query.getAndRemoveRule("RQ");//
				sjbl_date = rq.getValue();
			}
			query.setUserWhere(" ORDER by cast(YNSB_NYZL AS int) asc,  REGEXP_SUBSTR(YNSB_GLBH, '[[:alpha:]]+'),cast(REGEXP_SUBSTR(YNSB_GLBH, '[0-9]+') as int) ");
			DataResult result;
			
			DBRule fid = query.getAndRemoveRule("YNSB_FID");
			String ynsb_fid = "";
			if(fid != null){
				ynsb_fid = fid.getValue();
			}else{
				ynsb_fid = "3010401101"; //默认正东集团用能单位计量
			}
			if("3010401104".equals(ynsb_fid)){ //1、恒东热电燃气蒸汽锅炉
				result = ucc.nyyhsjrbb1(query,sjbl_date).search(); 
			}else if("3010401105".equals(ynsb_fid)){ //2、恒东热电蒸汽+热水换热机组
				result = ucc.nyyhsjrbb2(query,sjbl_date).search();
			}else if("3010401107".equals(ynsb_fid)){ //3、动南燃气联合循环发电机组
				result = ucc.nyyhsjrbb3(query,sjbl_date).search();
			}else if("3010401108".equals(ynsb_fid)){ //4、动南燃气热水锅炉+换热机组 
				result = ucc.nyyhsjrbb4(query,sjbl_date).search();
			}else if("3010401109".equals(ynsb_fid)){ //5、动力南厂燃气蒸汽锅炉
				result = ucc.nyyhsjrbb5(query,sjbl_date).search();
			}else if("3010401101".equals(ynsb_fid)){
				result = ucc.nyyhsjrbb(query,sjbl_date).search();
			}else{
				result = ucc.nyyhsjrbb(query,"x").search(); //如果其他选项则不查询结果
			}		
			///
			
			result.runDictionary(FarmManager.instance().findDicTitleForIndex("NYZL"),"YNSB_NYZL");

			jsonResult = EasyUiUtils.formatGridData(result);
			resultList = result.getResultList();

			//能源种类为“热水（进）”或“热水（进）” 累计值乘以3.6
			for(Map<String, Object> map : resultList){
				String nyzl = (String) map.get("YNSB_NYZL");
				Float ljz = new Float((String)map.get("LJZ"));
				if (TjbbConstants.RSJ.equals(nyzl) || TjbbConstants.RSC.equals(nyzl)){
					map.put("LJZ", new java.text.DecimalFormat("0.000").format(ljz * 3.6) );
				}
				if(ljz < 0){ //负数设为0
					map.put("LJZ", "0.000");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
		}
		return "success";
	}

	/**
	 * 能源用户数据日报表导出
	 * 
	 * @return
	 */
	public String nyyhsjrbbExport() {
		try {
			nyyhsjrbb();
			if (rq == null || "".equals(rq)) {
				rq = DateUtil.getLastDay("yyyy-MM-dd");
			}
			// ------------------------开始导出
			Map<String, Object> para = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (resultList != null && resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> map = resultList.get(i);
					String nyzl = (String) map.get("YNSB_NYZL");
					String glbh = (String) map.get("YNSB_GLBH");
					if (!Tools.stringIsNull(nyzl)) {
						if (TjbbConstants.TRQJ.equals(nyzl)) {
							map.put("DW", "Nm3");
						}else if (TjbbConstants.RSJ.equals(nyzl) || TjbbConstants.RSC.equals(nyzl)) {
							map.put("DW", "GJ");
						} else if (TjbbConstants.ZQJ.equals(nyzl) || TjbbConstants.ZQC.equals(nyzl) || TjbbConstants.SYSJ.equals(nyzl) || TjbbConstants.SYSC.equals(nyzl) || TjbbConstants.RHSJ.equals(nyzl)) {
							if(glbh.contains("热水炉热水表")){
								map.put("DW", "GJ");
							}else{
								map.put("DW", "t");
							}
						} else if (TjbbConstants.DJ.equals(nyzl) || TjbbConstants.DC.equals(nyzl)) {
							map.put("DW", "kWh");
						}
					}
					list.add(map);
				}
			}

			para.put("result", list);
			para.put("lastDay", DateUtil.formartDayDate(rq, 0));
			para.put("lastLastDay", DateUtil.formartDayDate(rq, 1));
			filename = "能源用户数据日报表.xls";
			reportUCC.generate(filename, para);
			inputStream = new FileInputStream(new File(
					reportUCC.getReportPath(filename)));
			filename = rq + "能源用户数据日报表.xls";
		} catch (ReportException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * 统计报表 生产计划
	 * 
	 * @return
	 */
	public String scjj() {

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(1000);
			DBRule J200202 = query.getAndRemoveRule("J200202");
			StringBuffer whereSQL = new StringBuffer(" and 1=1");
			DBRule J200201 = query.getAndRemoveRule("J200201");
			String dtime = Tools.filterNull(request.getParameter("dtime"));
			if (J200201 != null) {
				whereSQL.append(" and J200201= '").append(J200201.getValue())
						.append("' ");
			} else if (dtime != null) {
				whereSQL.append(" and J200201= '").append(dtime).append("' ");
			}
			if (J200202 != null) {
				List<Map<String, Object>> listForSjzd = FarmManager.instance()
						.findRuleItemOption(J200202.getValue(), "xm");
				ArrayList<String> nameList = new ArrayList<String>();
				if (listForSjzd != null && listForSjzd.size() > 0) {
					for (int i = 0; i < listForSjzd.size(); i++) {
						Map<String, Object> type = (Map<String, Object>) listForSjzd
								.get(i);
						nameList.add((String) type.get("NAME"));
					}
				}
				if (nameList != null && nameList.size() > 0) {
					String[] array = new String[nameList.size()];
					// List转换成数组
					for (int j = 0; j < nameList.size(); j++) {
						array[j] = nameList.get(j);
					}

					for (int i = 0; i < array.length; i++) {
						if (array[i] != null && !"".equals(array[i])) {
							if (array.length == 1) {
								whereSQL.append(" and  ").append("J200202")
										.append(" = '").append(array[i])
										.append("'");
							} else {
								if (i == 0) {
									whereSQL.append(" and  (")
											.append("J200202").append(" = '")
											.append(array[i]).append("'");
								} else if (i == array.length - 1) {
									whereSQL.append(" or  ").append("J200202")
											.append(" = '").append(array[i])
											.append("')");
								} else {
									whereSQL.append(" or  ").append("J200202")
											.append(" = '").append(array[i])
											.append("'");
								}
							}

						}
					}
				}

			}
			query.addUserWhere(whereSQL.toString());
			DBRule J200203 = query.getAndRemoveRule("J200203");
			String jg = "1"; // 默认机构为正东
			if (J200203 != null) {
				jg = J200203.getValue();
			}
			query.addUserWhere(" and J200203 = '" + jg + "' ");
			DataResult result = ucc.scjj(query, type).search();
			result.runDictionary(
					FarmManager.instance().findDicTitleForIndex("COMPANY"),
					"J200203");
			result.runDictionary(
					FarmManager.instance().findDicTitleForIndex("UNIT"),
					"J200209");
			jsonResult = EasyUiUtils.formatGridData(result);
			resultList = result.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
		}
		return "success";
	}

	// 生产计划实际值
	public void scjjSj() {
		String jg = request.getParameter("jg");
		String sj = request.getParameter("sj");
		String sgsj = sj;
		String type = request.getParameter("type");
		String nyzl = Tools.filterNull(request.getParameter("nyzl"));
		Map<String, String> phtval = new HashMap<String, String>();
		String jz = "";
		String[] zdnyzl = { "1", "2", "3", "8", "9", "4", "5", "6", "7" };
		// if(nyzl.equals("575502")){
		// zdnyzl = new String[]{"1"};
		// }
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			// 正东
			if (jg.equals("1")) {
				jz = SsjcConstants.ITEMID_ZDYN;
			} else if (jg.equals("2")) {
				jz = SsjcConstants.YNSB_HDRDCJ;
			} else if (jg.equals("3")) {
				jz = SsjcConstants.YNSB_DLCJYN;
			}

			if (!jz.equals("")) {
				// 自动提取
				String strartsj = "";
				if (type.equals("month")) {
					int compareTo = Tools.compareTo(sj,
							Tools.getSysDate("yyyy-MM"), "yyyy-MM");
					if (compareTo >= 0) {
						sj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
						sj = Tools.minusMinute(sj, -2);
						strartsj = Tools.getUpMonthEnd() + " 23:59:00";
					} else {
						strartsj = Tools.minusMonth(sj, 1);
						strartsj = Tools.getLastDayOfMonth(
								Integer.parseInt(strartsj.substring(0, 4)),
								Integer.parseInt(strartsj.substring(5, 7)))
								+ "23:59:00";
						sj = Tools.getLastDayOfMonth(
								Integer.parseInt(sj.substring(0, 4)),
								Integer.parseInt(sj.substring(5, 7)))
								+ "23:59:00";

					}
				} else if (type.equals("year")) {
					if (Integer.parseInt(sj) < Integer.parseInt(Tools
							.getSysDate("yyyy"))) {
						strartsj = (Integer.parseInt(sj) - 1)
								+ "-12-31 23:59:00";
						sj = sj + "-12-31 23:59:00";
					} else if (Integer.parseInt(sj) >= Integer.parseInt(Tools
							.getSysDate("yyyy"))) {
						strartsj = (Integer.parseInt(sj) - 1)
								+ "-12-31 23:59:00";
						sj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
						sj = Tools.minusMinute(sj, -2);
					}

				}

				// 手工补录
				String sgstrartsj = "";
				if (type.equals("month")) {
					sgstrartsj = Tools.minusMonth(sgsj, 1);
				} else if (type.equals("year")) {
					sgstrartsj = (Integer.parseInt(sgsj) - 1) + "";
				}

				List<Map<String, Object>> endlist = ucc.querySglrSumNy(jz,
						sgsj, zdnyzl);
				List<Map<String, Object>> startlist = ucc.querySglrSumNy(jz,
						sgstrartsj, zdnyzl);
				// 手工补录数据月
				for (Map<String, Object> endmap : endlist) {
					for (Map<String, Object> startmap : startlist) {
						if (endmap.get("EAST_SJBL_NYZL").equals(
								startmap.get("EAST_SJBL_NYZL"))) {
							Double dou = Double.parseDouble(endmap.get("TOTAL")
									.toString())
									- Double.parseDouble(startmap.get("TOTAL")
											.toString());
							phtval.put(endmap.get("EAST_SJBL_NYZL").toString()
									+ "v", String.valueOf(dou));
							break;
						}
					}
				}

				List<Map<String, Object>> zdendlist = ucc.queryZdSumNy(jz, sj,
						zdnyzl);
				List<Map<String, Object>> zdstartlist = ucc.queryZdSumNy(jz,
						strartsj, zdnyzl);// 上个月
				for (Map<String, Object> zdendmap : zdendlist) {
					for (Map<String, Object> zdstartmap : zdstartlist) {
						if (zdendmap.get("NYZL").equals(zdstartmap.get("NYZL"))) {
							Double dou = Double.parseDouble(zdendmap.get(
									"TOTAL").toString())
									- Double.parseDouble(zdstartmap
											.get("TOTAL").toString());
							String totaly = String.valueOf(dou
									+ Double.parseDouble(phtval.get(zdendmap
											.get("NYZL") + "v")));
							phtval.put(zdendmap.get("NYZL").toString() + "v",
									totaly);
							break;
						}
					}
				}
			}
			httpResponse.getWriter().print(JSONArray.fromObject(phtval));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生产计划报表导出
	 * 
	 * @return
	 * @throws BkmsException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void scjjExport() throws SQLException, BkmsException, ParseException {
		session.setAttribute("exportflag", "true");
		HttpServletRequest request = ServletActionContext.getRequest();
		String tableItemName = Tools.filterNull(request
				.getParameter("tableName"));// 表中文名
		String date = Tools.filterNull(request.getParameter("date"));// 日期
		query = EasyUiUtils.formatGridQuery(request, query);
		query.setPagesize(1000);

		String J200202 = Tools.filterNull(request.getParameter("J200202"));
		StringBuffer whereSQL = new StringBuffer(" and 1=1");
		if (!J200202.equals("")) {
			List<Map<String, Object>> listForSjzd = FarmManager.instance()
					.findRuleItemOption(J200202, "xm");
			ArrayList<String> nameList = new ArrayList<String>();
			if (listForSjzd != null && listForSjzd.size() > 0) {
				for (int i = 0; i < listForSjzd.size(); i++) {
					Map<String, Object> type = (Map<String, Object>) listForSjzd
							.get(i);
					nameList.add((String) type.get("NAME"));
				}
			}
			if (nameList != null && nameList.size() > 0) {
				String[] array = new String[nameList.size()];
				// List转换成数组
				for (int j = 0; j < nameList.size(); j++) {
					array[j] = nameList.get(j);
				}

				for (int i = 0; i < array.length; i++) {
					if (array[i] != null && !"".equals(array[i])) {
						if (array.length == 1) {
							whereSQL.append(" and  ").append("J200202")
									.append(" = '").append(array[i])
									.append("'");
						} else {
							if (i == 0) {
								whereSQL.append(" and  (").append("J200202")
										.append(" = '").append(array[i])
										.append("'");
							} else if (i == array.length - 1) {
								whereSQL.append(" or  ").append("J200202")
										.append(" = '").append(array[i])
										.append("')");
							} else {
								whereSQL.append(" or  ").append("J200202")
										.append(" = '").append(array[i])
										.append("'");
							}
						}

					}
				}
			}

		}
		query.setUserWhere(" AND  J200201='" + date + "'");
		query.addUserWhere(whereSQL.toString());
		String jgmc = Tools.filterNull(request.getParameter("jgmc"));
		if (!jgmc.equals("")) {
			query.addUserWhere(" and J200203 = '" + jgmc + "' ");
		}
		DataResult result = ucc.scjj(query, type).search();
		result.runDictionary(
				FarmManager.instance().findDicTitleForIndex("COMPANY"),
				"J200203");
		jsonResult = EasyUiUtils.formatGridData(result);
		resultList = result.getResultList();

		Map<String, String> phtval = new HashMap<String, String>();
		String jz = "";
		String[] zdnyzl = { "1", "2", "3", "8", "9", "4", "5", "6", "7" };
		// if(nyzl.equals("575502")){
		// zdnyzl = new String[]{"1"};
		// }
		if (jgmc.equals("1")) {
			jz = SsjcConstants.ITEMID_ZDYN;
		} else if (jgmc.equals("2")) {
			jz = SsjcConstants.YNSB_HDRDCJ;
		} else if (jgmc.equals("3")) {
			jz = SsjcConstants.YNSB_DLCJYN;
		}
		if (!jz.equals("")) {
			// 自动提取
			String strartsj = "";
			String sgdate = date;
			// 自动提取
			if (type.equals("month")) {
				int compareTo = Tools.compareTo(date,
						Tools.getSysDate("yyyy-MM"), "yyyy-MM");
				if (compareTo >= 0) {
					date = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
					date = Tools.minusMinute(date, -2);
					strartsj = Tools.getUpMonthEnd() + " 23:59:00";
				} else {
					strartsj = Tools.minusMonth(date, 1);
					strartsj = Tools.getLastDayOfMonth(
							Integer.parseInt(strartsj.substring(0, 4)),
							Integer.parseInt(strartsj.substring(5, 7)))
							+ "23:59:00";
					date = Tools.getLastDayOfMonth(
							Integer.parseInt(date.substring(0, 4)),
							Integer.parseInt(date.substring(5, 7)))
							+ "23:59:00";

				}
			} else if (type.equals("year")) {
				if (Integer.parseInt(date) < Integer.parseInt(Tools
						.getSysDate("yyyy"))) {
					strartsj = (Integer.parseInt(date) - 1) + "-12-31 23:59:00";
					date = date + "-12-31 23:59:00";
				} else if (Integer.parseInt(date) >= Integer.parseInt(Tools
						.getSysDate("yyyy"))) {
					strartsj = (Integer.parseInt(date) - 1) + "-12-31 23:59:00";
					date = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
					date = Tools.minusMinute(date, -2);
				}

			}

			// 手工补录
			String sgstrartsj = "";
			if (type.equals("month")) {
				sgstrartsj = Tools.minusMonth(sgdate, 1);
			} else if (type.equals("year")) {
				sgstrartsj = (Integer.parseInt(sgdate) - 1) + "";
			}

			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			List<Map<String, Object>> endlist = ucc.querySglrSumNy(jz, sgdate,
					zdnyzl);
			List<Map<String, Object>> startlist = ucc.querySglrSumNy(jz,
					sgstrartsj, zdnyzl);
			// 手工补录数据月
			for (Map<String, Object> endmap : endlist) {
				for (Map<String, Object> startmap : startlist) {
					if (endmap.get("EAST_SJBL_NYZL").equals(
							startmap.get("EAST_SJBL_NYZL"))) {
						Double dou = Double.parseDouble(endmap.get("TOTAL")
								.toString())
								- Double.parseDouble(startmap.get("TOTAL")
										.toString());
						phtval.put(endmap.get("EAST_SJBL_NYZL").toString()
								+ "v", String.valueOf(dou));
						break;
					}
				}
			}

			List<Map<String, Object>> zdendlist = ucc.queryZdSumNy(jz, date,
					zdnyzl);
			List<Map<String, Object>> zdstartlist = ucc.queryZdSumNy(jz,
					strartsj, zdnyzl);// 上个月
			for (Map<String, Object> zdendmap : zdendlist) {
				for (Map<String, Object> zdstartmap : zdstartlist) {
					if (zdendmap.get("NYZL").equals(zdstartmap.get("NYZL"))) {
						Double dou = Double.parseDouble(zdendmap.get("TOTAL")
								.toString())
								- Double.parseDouble(zdstartmap.get("TOTAL")
										.toString());
						String totaly = String.valueOf(dou
								+ Double.parseDouble(phtval.get(zdendmap
										.get("NYZL") + "v")));
						phtval.put(zdendmap.get("NYZL").toString() + "v",
								totaly);
						break;
					}
				}
			}
		}

		try {
			OutputStream os = httpResponse.getOutputStream();
			String tableName = tableItemName + ".xls";
			httpResponse.reset();
			httpResponse.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String(tableName.getBytes("GB2312"),
									"ISO8859-1"));
			httpResponse.setContentType("application/msexcel");
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			sheet.setColumnView(0, 20);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 25);

			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 15);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);

			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 大标题格式
			WritableFont DBoldFont = new WritableFont(WritableFont.ARIAL, 15,
					WritableFont.BOLD);
			WritableCellFormat Dwcf_center = new WritableCellFormat(DBoldFont);
			Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 居中
			WritableCellFormat zwcf_center = new WritableCellFormat();
			zwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			sheet.mergeCells(0, 0, 13, 0);
			// sheet.mergeCells(0, 1, 0, 2);
			// sheet.mergeCells(1, 1, 1, 2);
			// sheet.mergeCells(2, 1, 2, 2);
			// sheet.mergeCells(3, 1, 7, 1);
			// sheet.mergeCells(8, 1, 8, 2);
			sheet.mergeCells(0, 1, 0, 2);
			sheet.mergeCells(1, 1, 1, 2);
			sheet.mergeCells(2, 1, 2, 2);

			sheet.mergeCells(3, 1, 4, 1);
			sheet.mergeCells(5, 1, 6, 1);
			sheet.mergeCells(7, 1, 8, 1);
			sheet.mergeCells(9, 1, 10, 1);
			sheet.mergeCells(11, 1, 12, 1);
			sheet.mergeCells(13, 1, 13, 2);

			sheet.addCell(new Label(0, 0, "" + tableItemName + "", Dwcf_center));
			sheet.addCell(new Label(0, 1, "年度/月份", wcf_center));
			sheet.addCell(new Label(1, 1, "项目", wcf_center));
			sheet.addCell(new Label(2, 1, "机构名称", wcf_center));

			sheet.addCell(new Label(3, 1, "外购", wcf_center));
			sheet.addCell(new Label(5, 1, "生产", wcf_center));
			sheet.addCell(new Label(7, 1, "自用", wcf_center));
			sheet.addCell(new Label(9, 1, "外供", wcf_center));
			sheet.addCell(new Label(11, 1, "结转", wcf_center));
			sheet.addCell(new Label(13, 1, "备注", wcf_center));

			sheet.addCell(new Label(3, 2, "计划", wcf_center));
			sheet.addCell(new Label(4, 2, "实际", wcf_center));
			sheet.addCell(new Label(5, 2, "计划", wcf_center));
			sheet.addCell(new Label(6, 2, "实际", wcf_center));
			sheet.addCell(new Label(7, 2, "计划", wcf_center));
			sheet.addCell(new Label(8, 2, "实际", wcf_center));
			sheet.addCell(new Label(9, 2, "计划", wcf_center));
			sheet.addCell(new Label(10, 2, "实际", wcf_center));
			sheet.addCell(new Label(11, 2, "计划", wcf_center));
			sheet.addCell(new Label(12, 2, "实际", wcf_center));

			DecimalFormat df = new DecimalFormat("######0.00");

			if (resultList != null && resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> mapzb = resultList.get(i);
					sheet.addCell(new Label(0, i + 3, Tools.filterNull(mapzb
							.get("J200201"))));// 年度/月份
					sheet.addCell(new Label(1, i + 3, Tools.filterNull(mapzb
							.get("J200202"))));// 项目
					sheet.addCell(new Label(2, i + 3, Tools.filterNull(mapzb
							.get("J200203"))));// 机构名称

					sheet.addCell(new Label(3, i + 3, Tools.filterNull(mapzb
							.get("J200204"))));// 外购计划
					if (Tools.filterNull(mapzb.get("J200202")).equals("天然气")) {
						sheet.addCell(new Label(4, i + 3, df.format(Double
								.parseDouble(phtval.get("1v")))));// 外购实际天然气
					} else if (Tools.filterNull(mapzb.get("J200202")).equals(
							"蒸汽")) {
						sheet.addCell(new Label(10, i + 3, df.format(Double
								.parseDouble(phtval.get("3v")))));// 外供实际蒸汽
						if (jgmc.equals("2") || jgmc.equals("3")) {
							sheet.addCell(new Label(12, i + 3, df.format(Double
									.parseDouble(phtval.get("2v")))));// 结转实际蒸汽
						} else {
							sheet.addCell(new Label(4, i + 3, df.format(Double
									.parseDouble(phtval.get("2v")))));// 外购实际蒸汽
						}
					} else if (Tools.filterNull(mapzb.get("J200202")).equals(
							"热水")) {
						sheet.addCell(new Label(10, i + 3, df.format(Double
								.parseDouble(phtval.get("9v")))));// 外供实际热水
						if (jgmc.equals("2")) {
							sheet.addCell(new Label(12, i + 3, df.format(Double
									.parseDouble(phtval.get("8v")))));// 外供实际热水
						} else {
							sheet.addCell(new Label(4, i + 3, df.format(Double
									.parseDouble(phtval.get("8v")))));// 外购实际热水
						}
					} else if (Tools.filterNull(mapzb.get("J200202")).equals(
							"水")) {
						sheet.addCell(new Label(4, i + 3, df.format(Double
								.parseDouble(phtval.get("4v")))));// 外购实际蒸汽
						sheet.addCell(new Label(10, i + 3, df.format(Double
								.parseDouble(phtval.get("5v")))));// 外供实际蒸汽
					} else if (Tools.filterNull(mapzb.get("J200202")).equals(
							"电")) {
						sheet.addCell(new Label(4, i + 3, df.format(Double
								.parseDouble(phtval.get("6v")))));// 外购实际蒸汽
						sheet.addCell(new Label(10, i + 3, df.format(Double
								.parseDouble(phtval.get("7v")))));// 外供实际蒸汽
					}

					sheet.addCell(new Label(5, i + 3, Tools.filterNull(mapzb
							.get("J200205"))));// 生产
					sheet.addCell(new Label(7, i + 3, Tools.filterNull(mapzb
							.get("J200206"))));// 自用
					sheet.addCell(new Label(9, i + 3, Tools.filterNull(mapzb
							.get("J200207"))));// 外供
					sheet.addCell(new Label(11, i + 3, Tools.filterNull(mapzb
							.get("J200208"))));// 结转

				}
			}

			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		} finally {
			session.removeAttribute("exportflag");
		}
	}

	/**
	 * 统计报表 设备计划
	 * 
	 * @return
	 */
	public String sbjj() {

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(1000);
			DBRule J200202 = query.getAndRemoveRule("J200202");
			StringBuffer whereSQL = new StringBuffer(" and 1=1");
			DBRule J202201 = query.getAndRemoveRule("J202201");
			String dtime = Tools.filterNull(request.getParameter("dtime"));
			if (J202201 != null) {
				whereSQL.append(" and J202201= '").append(J202201.getValue())
						.append("' ");
			} else if (dtime != null) {
				whereSQL.append(" and J202201= '").append(dtime).append("' ");
			}
			if (J200202 != null) {
				List<Map<String, Object>> listForSjzd = FarmManager.instance()
						.findRuleItemOption(J200202.getValue(), "xm");
				ArrayList<String> nameList = new ArrayList<String>();
				if (listForSjzd != null && listForSjzd.size() > 0) {
					for (int i = 0; i < listForSjzd.size(); i++) {
						Map<String, Object> type = (Map<String, Object>) listForSjzd
								.get(i);
						nameList.add((String) type.get("NAME"));
					}
				}
				if (nameList != null && nameList.size() > 0) {
					String[] array = new String[nameList.size()];
					// List转换成数组
					for (int j = 0; j < nameList.size(); j++) {
						array[j] = nameList.get(j);
					}

					for (int i = 0; i < array.length; i++) {
						if (array[i] != null && !"".equals(array[i])) {
							if (array.length == 1) {
								whereSQL.append(" and  ").append("J202202")
										.append(" = '").append(array[i])
										.append("'");
							} else {
								if (i == 0) {
									whereSQL.append(" and  (")
											.append("J202202").append(" = '")
											.append(array[i]).append("'");
								} else if (i == array.length - 1) {
									whereSQL.append(" or  ").append("J202202")
											.append(" = '").append(array[i])
											.append("')");
								} else {
									whereSQL.append(" or  ").append("J202202")
											.append(" = '").append(array[i])
											.append("'");
								}
							}

						}
					}
				}

			}
			query.addUserWhere(whereSQL.toString());
			DataResult result = ucc.sbjj(query, type).search();
			result.runDictionary(
					FarmManager.instance().findDicTitleForIndex("COMPANY"),
					"J202203");
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
		}
		return "success";
	}

	/**
	 * 设备计划报表导出
	 * 
	 * @return
	 * @throws BkmsException
	 * @throws SQLException
	 */
	public void sbjjExport() throws SQLException, BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tableItemName = Tools.filterNull(request
				.getParameter("tableName"));// 表中文名
		String date = Tools.filterNull(request.getParameter("date"));// 日期
		query = EasyUiUtils.formatGridQuery(request, query);
		query.setPagesize(1000);
		query.setUserWhere(" AND  J202201='" + date + "'");

		String J200202 = Tools.filterNull(request.getParameter("J200202"));
		StringBuffer whereSQL = new StringBuffer(" and 1=1");
		if (J200202 != null) {
			List<Map<String, Object>> listForSjzd = FarmManager.instance()
					.findRuleItemOption(J200202, "xm");
			ArrayList<String> nameList = new ArrayList<String>();
			if (listForSjzd != null && listForSjzd.size() > 0) {
				for (int i = 0; i < listForSjzd.size(); i++) {
					Map<String, Object> type = (Map<String, Object>) listForSjzd
							.get(i);
					nameList.add((String) type.get("NAME"));
				}
			}
			if (nameList != null && nameList.size() > 0) {
				String[] array = new String[nameList.size()];
				// List转换成数组
				for (int j = 0; j < nameList.size(); j++) {
					array[j] = nameList.get(j);
				}

				for (int i = 0; i < array.length; i++) {
					if (array[i] != null && !"".equals(array[i])) {
						if (array.length == 1) {
							whereSQL.append(" and  ").append("J202202")
									.append(" = '").append(array[i])
									.append("'");
						} else {
							if (i == 0) {
								whereSQL.append(" and  (").append("J202202")
										.append(" = '").append(array[i])
										.append("'");
							} else if (i == array.length - 1) {
								whereSQL.append(" or  ").append("J202202")
										.append(" = '").append(array[i])
										.append("')");
							} else {
								whereSQL.append(" or  ").append("J202202")
										.append(" = '").append(array[i])
										.append("'");
							}
						}

					}
				}
			}

		}
		query.addUserWhere(whereSQL.toString());
		String jgmc = Tools.filterNull(request.getParameter("jgmc"));
		if (!jgmc.equals("")) {
			query.addUserWhere(" and J202203 = '" + jgmc + "' ");
		}
		DataResult result = ucc.sbjj(query, type).search();
		result.runDictionary(
				FarmManager.instance().findDicTitleForIndex("COMPANY"),
				"J202203");
		jsonResult = EasyUiUtils.formatGridData(result);
		resultList = result.getResultList();
		try {
			OutputStream os = httpResponse.getOutputStream();
			String tableName = tableItemName + ".xls";
			httpResponse.reset();
			httpResponse.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String(tableName.getBytes("GB2312"),
									"ISO8859-1"));
			httpResponse.setContentType("application/msexcel");
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			sheet.setColumnView(0, 20);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 30);
			sheet.setColumnView(5, 20);
			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 大标题格式
			WritableFont DBoldFont = new WritableFont(WritableFont.ARIAL, 15,
					WritableFont.BOLD);
			WritableCellFormat Dwcf_center = new WritableCellFormat(DBoldFont);
			Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 居中
			WritableCellFormat zwcf_center = new WritableCellFormat();
			zwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			sheet.mergeCells(0, 0, 5, 0);
			sheet.mergeCells(0, 1, 0, 2);
			sheet.mergeCells(1, 1, 1, 2);
			sheet.mergeCells(2, 1, 2, 2);
			sheet.mergeCells(3, 1, 4, 1);
			sheet.mergeCells(5, 1, 5, 2);
			sheet.addCell(new Label(0, 0, "" + tableItemName + "", Dwcf_center));
			sheet.addCell(new Label(0, 1, "年度/月份", wcf_center));
			sheet.addCell(new Label(1, 1, "项目名称", wcf_center));
			sheet.addCell(new Label(2, 1, "机构名称", wcf_center));
			sheet.addCell(new Label(3, 1, "计划", wcf_center));
			sheet.addCell(new Label(5, 1, "实际", wcf_center));
			sheet.addCell(new Label(3, 2, "设备名称", wcf_center));
			sheet.addCell(new Label(4, 2, "设备内容", wcf_center));
			if (resultList != null && resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> mapzb = resultList.get(i);
					sheet.addCell(new Label(0, i + 3, Tools.filterNull(mapzb
							.get("J202201"))));// 年度/月份
					sheet.addCell(new Label(1, i + 3, Tools.filterNull(mapzb
							.get("J202202"))));// 项目
					sheet.addCell(new Label(2, i + 3, Tools.filterNull(mapzb
							.get("J202203"))));// 机构名称
					sheet.addCell(new Label(3, i + 3, Tools.filterNull(mapzb
							.get("J202204"))));// 设备名称
					sheet.addCell(new Label(4, i + 3, Tools.filterNull(mapzb
							.get("J202205"))));// 设备内容
					if (mapzb.get("STATE") != null) {
						if ("1".equals(Tools.filterNull(mapzb.get("STATE")))) {
							sheet.addCell(new Label(5, i + 3, "√"));// 实际
						}
					}

				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
	}

	/**
	 * 统计报表 指标体系
	 * 
	 * @return
	 */
	public String zbtx() {

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(1000);
			DBRule J200202 = query.getAndRemoveRule("J200202");
			StringBuffer whereSQL = new StringBuffer(" and 1=1");
			DBRule RQ = query.getAndRemoveRule("RQ");
			String dtime = Tools.filterNull(request.getParameter("dtime"));
			if (RQ != null) {
				whereSQL.append(" and RQ= '").append(RQ.getValue())
						.append("' ");
			} else if (dtime != null) {
				whereSQL.append(" and RQ= '").append(dtime).append("' ");
			}
			if (J200202 != null) {
				List<Map<String, Object>> listForSjzd = FarmManager.instance()
						.findRuleItemOption(J200202.getValue(), "xm");
				ArrayList<String> nameList = new ArrayList<String>();
				if (listForSjzd != null && listForSjzd.size() > 0) {
					for (int i = 0; i < listForSjzd.size(); i++) {
						Map<String, Object> type = (Map<String, Object>) listForSjzd
								.get(i);
						nameList.add((String) type.get("NAME"));
					}
				}
				if (nameList != null && nameList.size() > 0) {
					String[] array = new String[nameList.size()];
					// List转换成数组
					for (int j = 0; j < nameList.size(); j++) {
						array[j] = nameList.get(j);
					}

					for (int i = 0; i < array.length; i++) {
						if (array[i] != null && !"".equals(array[i])) {
							if (array.length == 1) {
								whereSQL.append(" and  ").append("XMMC")
										.append(" = '").append(array[i])
										.append("'");
							} else {
								if (i == 0) {
									whereSQL.append(" and  (").append("XMMC")
											.append(" = '").append(array[i])
											.append("'");
								} else if (i == array.length - 1) {
									whereSQL.append(" or  ").append("XMMC")
											.append(" = '").append(array[i])
											.append("')");
								} else {
									whereSQL.append(" or  ").append("XMMC")
											.append(" = '").append(array[i])
											.append("'");
								}
							}

						}
					}
				}

			}
			query.addUserWhere(whereSQL.toString());
			DataResult result = ucc.zbtx(query, type).search();
			result.runDictionary(
					FarmManager.instance().findDicTitleForIndex("COMPANY"),
					"JGMC");
			result.runDictionary(
					FarmManager.instance().findDicTitleForIndex("UNIT"), "JLDW");

			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
		}
		return "success";
	}

	/**
	 * 指标体系报表导出
	 * 
	 * @return
	 * @throws BkmsException
	 * @throws SQLException
	 */
	public void zbtxExport() throws SQLException, BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tableItemName = Tools.filterNull(request
				.getParameter("tableName"));// 表中文名
		String date = Tools.filterNull(request.getParameter("date"));// 日期
		query = EasyUiUtils.formatGridQuery(request, query);
		query.setPagesize(1000);
		query.setUserWhere(" AND  RQ='" + date + "'");

		String J200202 = Tools.filterNull(request.getParameter("J200202"));
		StringBuffer whereSQL = new StringBuffer(" and 1=1");
		if (J200202 != null) {
			List<Map<String, Object>> listForSjzd = FarmManager.instance()
					.findRuleItemOption(J200202, "xm");
			ArrayList<String> nameList = new ArrayList<String>();
			if (listForSjzd != null && listForSjzd.size() > 0) {
				for (int i = 0; i < listForSjzd.size(); i++) {
					Map<String, Object> type = (Map<String, Object>) listForSjzd
							.get(i);
					nameList.add((String) type.get("NAME"));
				}
			}
			if (nameList != null && nameList.size() > 0) {
				String[] array = new String[nameList.size()];
				// List转换成数组
				for (int j = 0; j < nameList.size(); j++) {
					array[j] = nameList.get(j);
				}

				for (int i = 0; i < array.length; i++) {
					if (array[i] != null && !"".equals(array[i])) {
						if (array.length == 1) {
							whereSQL.append(" and  ").append("XMMC")
									.append(" = '").append(array[i])
									.append("'");
						} else {
							if (i == 0) {
								whereSQL.append(" and  (").append("XMMC")
										.append(" = '").append(array[i])
										.append("'");
							} else if (i == array.length - 1) {
								whereSQL.append(" or  ").append("XMMC")
										.append(" = '").append(array[i])
										.append("')");
							} else {
								whereSQL.append(" or  ").append("XMMC")
										.append(" = '").append(array[i])
										.append("'");
							}
						}

					}
				}
			}

		}
		query.addUserWhere(whereSQL.toString());

		String jgmc = Tools.filterNull(request.getParameter("jgmc"));
		if (!jgmc.equals("")) {
			query.addUserWhere(" and JGMC = '" + jgmc + "' ");
		}

		DataResult result = ucc.zbtx(query, type).search();
		result.runDictionary(
				FarmManager.instance().findDicTitleForIndex("COMPANY"), "JGMC");
		result.runDictionary(FarmManager.instance()
				.findDicTitleForIndex("UNIT"), "JLDW");
		jsonResult = EasyUiUtils.formatGridData(result);
		resultList = result.getResultList();
		try {
			OutputStream os = httpResponse.getOutputStream();
			String tableName = tableItemName + ".xls";
			httpResponse.reset();
			httpResponse.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String(tableName.getBytes("GB2312"),
									"ISO8859-1"));
			httpResponse.setContentType("application/msexcel");
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			sheet.setColumnView(0, 20);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(5, 20);
			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 大标题格式
			WritableFont DBoldFont = new WritableFont(WritableFont.ARIAL, 15,
					WritableFont.BOLD);
			WritableCellFormat Dwcf_center = new WritableCellFormat(DBoldFont);
			Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 居中
			WritableCellFormat zwcf_center = new WritableCellFormat();
			zwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			sheet.mergeCells(0, 0, 6, 0);
			sheet.mergeCells(0, 1, 0, 2);
			sheet.mergeCells(1, 1, 1, 2);
			sheet.mergeCells(2, 1, 2, 2);
			sheet.mergeCells(3, 1, 5, 1);
			sheet.mergeCells(6, 1, 6, 2);
			sheet.addCell(new Label(0, 0, "" + tableItemName + "", Dwcf_center));
			sheet.addCell(new Label(0, 1, "年度/月份", wcf_center));
			sheet.addCell(new Label(1, 1, "项目名称", wcf_center));
			sheet.addCell(new Label(2, 1, "机构名称", wcf_center));
			sheet.addCell(new Label(3, 1, "计划", wcf_center));
			sheet.addCell(new Label(6, 1, "实际", wcf_center));
			sheet.addCell(new Label(3, 2, "设备名称", wcf_center));
			sheet.addCell(new Label(4, 2, "计量单位", wcf_center));
			sheet.addCell(new Label(5, 2, "计划指标", wcf_center));
			if (resultList != null && resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> mapzb = resultList.get(i);
					sheet.addCell(new Label(0, i + 3, Tools.filterNull(mapzb
							.get("RQ"))));// 年度/月份
					sheet.addCell(new Label(1, i + 3, Tools.filterNull(mapzb
							.get("XMMC"))));// 项目
					sheet.addCell(new Label(2, i + 3, Tools.filterNull(mapzb
							.get("JGMC"))));// 机构名称
					sheet.addCell(new Label(3, i + 3, Tools.filterNull(mapzb
							.get("SBMC"))));// 设备名称
					sheet.addCell(new Label(4, i + 3, Tools.filterNull(mapzb
							.get("JLDW"))));// 计量单位
					sheet.addCell(new Label(5, i + 3, Tools.filterNull(mapzb
							.get("JHZB"))));// 计划指标
					if (mapzb.get("STATE") != null) {
						if ("1".equals(Tools.filterNull(mapzb.get("STATE")))) {
							sheet.addCell(new Label(6, i + 3, "√"));// 实际
						}
					}

				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
	}
	
	public void queryCodeItems(){
		try {
			List<CodeItemBO> list = ucc.queryCodeItem();
			String jsonResult = JsonUtil.toJson(list);
			JsonUtil.printEasyuiJson(jsonResult, httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}

	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getLastLastDate() {
		return lastLastDate;
	}

	public void setLastLastDate(String lastLastDate) {
		this.lastLastDate = lastLastDate;
	}

	public String getLastDay() {
		return lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}

	public String getLastLastDay() {
		return lastLastDay;
	}

	public void setLastLastDay(String lastLastDay) {
		this.lastLastDay = lastLastDay;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		try {
			filename = new String(filename.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

}
