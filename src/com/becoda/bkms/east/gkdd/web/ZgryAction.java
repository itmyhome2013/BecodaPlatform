package com.becoda.bkms.east.gkdd.web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.sf.json.JSONArray;

import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.gkdd.util.DBUtil;
import com.becoda.bkms.east.ssjc.pojo.NxjcBO;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.sql.utils.JsonUtil;

/**
 * 在岗人员action
 * <p>Description: </p>
 * @author liu_hq
 * @date 2017-9-19
 *
 */
public class ZgryAction extends GenericPageAction{
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	/**
	 * 每页显示的记录数
	 */
	private Integer rows;
	/**
	 * 当前页数
	 */
	private Integer page;
	
	// 分页查询
	public String queryList() throws IOException {
//		if(rows == null){
//			rows = 10000; //默认每页10
//		}
//		if(page == null){
//			page = 0;
//		}else{
//			page = page - 1;
//		}
		
		query = EasyUiUtils.formatGridQuery(request, query);
//		DBRule vemp_name = query.getAndRemoveRule("VEMP_NAME");//员工姓名
//		StringBuffer countsql = new StringBuffer("select count(*)");
//		StringBuffer queysql = new StringBuffer("select top ").append(rows)
		StringBuffer queysql = new StringBuffer("select ")
				.append(" users.vEmp_Name as VEMP_NAME,users.vCardNo as VCARDNO,users.vDepart as VDEPART, CONVERT(char(10), rec.dTime,120) as DTIME," +
						"CONVERT(char(5), rec.dTime,108) as DTIMESJ,rec.cDoorId,rec.cReadId,rec.cStatus as CSTATUS,door.vDoorName as VDOORNAME,red.vImpact as VIMPACT");
		queysql.append(" from MJ_MacRecord rec, Employee users,MJ_DoorInfo door,MJ_ReadInfo red where 1=1 ");
//		countsql.append(" from MJ_MacRecord rec, 查询3 users,MJ_DoorInfo door,MJ_ReadInfo red where 1=1");
//		if(vemp_name != null){//员工姓名
//			queysql.append(" and users.vEmp_Name like '%").append(vemp_name.getValue()).append("%'");
//			countsql.append(" and users.vEmp_Name like '%").append(vemp_name.getValue()).append("%'");
//		}
//		DBRule vcardno = query.getAndRemoveRule("VCARDNO");//员工卡号
//		if(vcardno != null){
//			queysql.append(" and users.vCardNo = '").append(vcardno.getValue()).append("'");
//			countsql.append(" and users.vCardNo = '").append(vcardno.getValue()).append("'");
//		}
		DBRule gzsj = query.getAndRemoveRule("GZSJ"); //工作时间
//		String dtime = "2014-11-06";//打卡日期
		String dtime = Tools.getSysDate("yyyy-MM-dd");//打卡日期
		
		if(gzsj!=null){
			if(gzsj.getValue().equals("1")){
				queysql.append(" and rec.dTime >= '").append(dtime+" 07:45").append("'");
				queysql.append(" and rec.dTime <= '").append(dtime+" 15:45").append("'");
//				countsql.append(" and rec.dTime >= '").append(dtime+" 07:45").append("'");
//				countsql.append(" and rec.dTime <= '").append(dtime+" 15:45").append("'");
			}else if(gzsj.getValue().equals("2")){
				queysql.append(" and rec.dTime >= '").append(dtime+" 15:45").append("'");
				queysql.append(" and rec.dTime <= '").append(dtime+" 23:45").append("'");
//				countsql.append(" and rec.dTime >= '").append(dtime+" 15:45").append("'");
//				countsql.append(" and rec.dTime <= '").append(dtime+" 23:45").append("'");
			}else if(gzsj.getValue().equals("3")){
				queysql.append(" and rec.dTime >= '").append(Tools.plusMinusDay(dtime, -1)+" 23:45").append("'");
				queysql.append(" and rec.dTime <= '").append(dtime+" 07:45").append("'");
//				countsql.append(" and rec.dTime >= '").append(Tools.plusMinusDay(dtime, -1)+" 23:45").append("'");
//				countsql.append(" and rec.dTime <= '").append(dtime+" 07:45").append("'");
			}
		}else{ //默认早班
			queysql.append(" and rec.dTime >= '").append(dtime+" 07:45").append("'");
			queysql.append(" and rec.dTime <= '").append(dtime+" 15:45").append("'");
//			countsql.append(" and rec.dTime >= '").append(dtime+" 07:45").append("'");
//			countsql.append(" and rec.dTime <= '").append(dtime+" 15:45").append("'");
		}
		
		
		DBRule vdepart = query.getAndRemoveRule("VDEPART"); //部门
		if(vdepart!=null){
			queysql.append(" and users.VDEPART like '%").append(vdepart.getValue()).append("%'");
//			countsql.append(" and users.VDEPART like '%").append(vdepart.getValue()).append("%'");
		}
		
//		queysql.append(" and CONVERT(char(10), rec.dTime,120) = '").append(dtime).append("'");
//		countsql.append(" and CONVERT(char(10), rec.dTime,120) = '").append(dtime).append("'");
		
//		countsql.append(" and users.vCardNo = rec.cCardNo and rec.cDoorId = door.cDoorId and rec.cDoorId = red.cDoorId and rec.cReadId = red.cReadId");
		
//		int toptotal = page * rows;
//		queysql.append(" and rec.dTime not in (select top ").append(toptotal)
//		.append(" rec.dTime from MJ_MacRecord rec, 查询3 users where users.vCardNo = rec.cCardNo ");
		
//		queysql.append(" and CONVERT(char(10), rec.dTime,120) = '").append(dtime).append("'");
//		if(vemp_name != null){//员工姓名
//			queysql.append(" and users.vEmp_Name like '%").append(vemp_name.getValue()).append("%'");
//		}
//		if(vcardno != null){// 员工卡号
//			queysql.append(" and users.vCardNo = '").append(vcardno.getValue()).append("'");
//		}
//		if(vdepart!=null){
//			queysql.append(" and users.VDEPART like '%").append(vdepart.getValue()).append("%'");
//		}
//		
//		if(gzsj!=null){
//			if(gzsj.getValue().equals("1")){
//				queysql.append(" and rec.dTime >= '").append(dtime+" 07:45").append("'");
//				queysql.append(" and rec.dTime <= '").append(dtime+" 15:45").append("'");
//			}else if(gzsj.getValue().equals("2")){
//				queysql.append(" and rec.dTime >= '").append(dtime+" 15:45").append("'");
//				queysql.append(" and rec.dTime <= '").append(dtime+" 23:45").append("'");
//			}else if(gzsj.getValue().equals("3")){
//				queysql.append(" and rec.dTime >= '").append(Tools.plusMinusDay(dtime, -1)+" 23:45").append("'");
//				queysql.append(" and rec.dTime <= '").append(dtime+" 07:45").append("'");
//			}
//		}else{ //默认早班
//			queysql.append(" and rec.dTime >= '").append(dtime+" 07:45").append("'");
//			queysql.append(" and rec.dTime <= '").append(dtime+" 15:45").append("'");
//		}
		
//		queysql.append(" order by CONVERT(char(19), rec.dTime,120) desc)");
		queysql.append(" and users.vCardNo = rec.cCardNo and rec.cDoorId = door.cDoorId and rec.cDoorId = red.cDoorId and rec.cReadId = red.cReadId");
		queysql.append(" order by CONVERT(char(19), rec.dTime,120) desc");
		Connection conn = DBUtil.getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
//		int size = 0; //总条数
		List list = new ArrayList();
		System.out.println(queysql);
//		System.out.println(countsql);
		try {
			sta = conn.prepareStatement(queysql.toString());
			rs = sta.executeQuery(); //查询
			list = DBUtil.convertList(rs);
//			size = DBUtil.getSize(countsql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, sta, conn);
		}
		PageVO pageVo = new PageVO();
//		pageVo.setPageSize(10000);
//		pageVo.setCurrentPage(page + 1);
//		pageVo.setTotalRecord(size);
		jsonResult = toEasyuiJson(list, pageVo);
		return "success";
	}
	
	// 分页历史查询
	public String queryHistoryList() throws IOException {
//		if(rows == null){
//			rows = 10; //默认每页10
//		}
//		if(page == null){
//			page = 0;
//		}else{
//			page = page - 1;
//		}
		
		query = EasyUiUtils.formatGridQuery(request, query);
//		DBRule vemp_name = query.getAndRemoveRule("VEMP_NAME");//员工姓名
//		StringBuffer countsql = new StringBuffer("select count(*)");
		StringBuffer queysql = new StringBuffer("select ")
				.append(" users.vEmp_Name as VEMP_NAME,users.vCardNo as VCARDNO,users.vDepart as VDEPART, CONVERT(char(10), rec.dTime,120) as DTIME," +
						"CONVERT(char(5), rec.dTime,108) as DTIMESJ,rec.cDoorId,rec.cReadId,rec.cStatus as CSTATUS,door.vDoorName as VDOORNAME,red.vImpact as VIMPACT");
		queysql.append(" from MJ_MacRecord rec, Employee users,MJ_DoorInfo door,MJ_ReadInfo red where 1=1 ");
//		countsql.append(" from MJ_MacRecord rec, 查询3 users,MJ_DoorInfo door,MJ_ReadInfo red where 1=1");
		DBRule vdepart = query.getAndRemoveRule("VDEPART");//部门
		if(vdepart != null){
			queysql.append(" and users.VDEPART like '%").append(vdepart.getValue()).append("%'");
		}
		
		DBRule dtime = query.getAndRemoveRule("DTIME");//日期
		String querytime = Tools.getSysDate("yyyy-MM-dd");
		if(dtime!=null){
			querytime = dtime.getValue();
		}
		
		DBRule gzsj = query.getAndRemoveRule("GZSJ"); //工作时间
		
		if(gzsj!=null){
			if(gzsj.getValue().equals("1")){
				queysql.append(" and rec.dTime >= '").append(querytime+" 07:45").append("'");
				queysql.append(" and rec.dTime <= '").append(querytime+" 15:45").append("'");
			}else if(gzsj.getValue().equals("2")){
				queysql.append(" and rec.dTime >= '").append(querytime+" 15:45").append("'");
				queysql.append(" and rec.dTime <= '").append(querytime+" 23:45").append("'");
			}else if(gzsj.getValue().equals("3")){
				queysql.append(" and rec.dTime >= '").append(Tools.plusMinusDay(querytime, -1)+" 23:45").append("'");
				queysql.append(" and rec.dTime <= '").append(querytime+" 07:45").append("'");
			}
		}else{ //默认早班
			queysql.append(" and rec.dTime >= '").append(querytime+" 07:45").append("'");
			queysql.append(" and rec.dTime <= '").append(querytime+" 15:45").append("'");
		}
		
//		countsql.append(" and users.vCardNo = rec.cCardNo and rec.cDoorId = door.cDoorId and rec.cDoorId = red.cDoorId and rec.cReadId = red.cReadId");
		
//		int toptotal = page * rows;
//		queysql.append(" and rec.dTime not in (select top ").append(toptotal)
//		.append(" dTime from MJ_MacRecord rec, 查询3 users where users.vCardNo = rec.cCardNo ");
//		
//		if(vdepart != null){
//			queysql.append(" and users.VDEPART like '%").append(vdepart.getValue()).append("%'");
//		}
//		if(startdtime != null){//开始时间
//			queysql.append(" and rec.dTime >='").append(startdtime.getValue()).append("'");
//		}
//		if(enddtime != null){//结束时间
//			queysql.append(" and rec.dTime <='").append(enddtime.getValue()).append("'");
//		}
//		queysql.append(" order by CONVERT(char(19), rec.dTime,120) desc)");
		queysql.append(" and users.vCardNo = rec.cCardNo and rec.cDoorId = door.cDoorId and rec.cDoorId = red.cDoorId and rec.cReadId = red.cReadId");
		queysql.append(" order by CONVERT(char(19), rec.dTime,120) desc");
		Connection conn = DBUtil.getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		List list = new ArrayList();
		System.out.println(queysql);
		try {
			sta = conn.prepareStatement(queysql.toString());
			rs = sta.executeQuery(); //查询
			list = DBUtil.convertList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, sta, conn);
		}
		PageVO pageVo = new PageVO();
//		pageVo.setPageSize(rows);
//		pageVo.setCurrentPage(page + 1);
//		pageVo.setTotalRecord(size);
		jsonResult = toEasyuiJson(list, pageVo);
		return "success";
	}
	
	//导出在岗人员
	public void exportZgry() {
		session.setAttribute("exportflag", "true");
		StringBuffer sql = new StringBuffer(
				"select users.vEmp_Name as VEMP_NAME,users.vCardNo as VCARDNO,users.vDepart as VDEPART, CONVERT(char(10)," +
				" rec.dTime,120) as DTIME,CONVERT(char(5), rec.dTime,108) as DTIMESJ,rec.cDoorId,rec.cReadId,rec.cStatus as CSTATUS," +
				"door.vDoorName as VDOORNAME,red.vImpact as VIMPACT from MJ_MacRecord rec, Employee users,MJ_DoorInfo door,MJ_ReadInfo red" +
				" where 1=1   and users.vCardNo = rec.cCardNo and rec.cDoorId = door.cDoorId  and rec.cDoorId = red.cDoorId " +
				" and rec.cReadId = red.cReadId");
		
		
		
		
		String dtime = Tools.filterNull(request.getParameter("dtime"));
		String	vdepart = Tools.filterNull(request.getParameter("vdepart")); //部门
		
		if(vdepart != null){
			sql.append(" and users.VDEPART like '%").append(vdepart).append("%'");
		}
		
		String querytime = Tools.getSysDate("yyyy-MM-dd");
		if(!dtime.equals("")){
			querytime = dtime;
		}
		
		String gzsj = Tools.filterNull(request.getParameter("gzsj")); //工作时间
		
		if(!gzsj.equals("")){
			if(gzsj.equals("1")){
				sql.append(" and rec.dTime >= '").append(querytime+" 07:45").append("'");
				sql.append(" and rec.dTime <= '").append(querytime+" 15:45").append("'");
			}else if(gzsj.equals("2")){
				sql.append(" and rec.dTime >= '").append(querytime+" 15:45").append("'");
				sql.append(" and rec.dTime <= '").append(querytime+" 23:45").append("'");
			}else if(gzsj.equals("3")){
				sql.append(" and rec.dTime >= '").append(Tools.plusMinusDay(querytime, -1)+" 23:45").append("'");
				sql.append(" and rec.dTime <= '").append(querytime+" 07:45").append("'");
			}
		}else{ //默认早班
			sql.append(" and rec.dTime >= '").append(querytime+" 07:45").append("'");
			sql.append(" and rec.dTime <= '").append(querytime+" 15:45").append("'");
		}
		
		
		
		
		sql.append(" order by CONVERT(char(19), rec.dTime,120) desc");
		
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		int size = 0; //总条数
//		List list = new ArrayList();
		System.out.println(sql);
		try {
			sta = conn.prepareStatement(sql.toString());
			rs = sta.executeQuery(); //查询
//			list = DBUtil.convertList(rs);
			OutputStream os = httpResponse.getOutputStream();
			httpResponse.reset();
			httpResponse.setHeader("Content-disposition", "attachment;filename="+ new String("在岗人员.xls".getBytes("GB2312"),"ISO8859-1"));
			//创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);  
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			//标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); //水平居中对齐
			//标题
			sheet.addCell(new Label(0, 0, "员工卡号",wcf_center));
			sheet.addCell(new Label(1, 0, "员工姓名",wcf_center));
			sheet.addCell(new Label(2, 0, "部门",wcf_center));
			sheet.addCell(new Label(3, 0, "打卡日期",wcf_center));
			sheet.addCell(new Label(4, 0, "打卡时间",wcf_center));
			sheet.addCell(new Label(5, 0, "出入状态",wcf_center));
			sheet.addCell(new Label(6, 0, "出入门",wcf_center));
			sheet.addCell(new Label(7, 0, "状态",wcf_center));
			sheet.setColumnView(0, 20);//设置宽度
			sheet.setColumnView(1, 20);//设置宽度
			sheet.setColumnView(2, 20);//设置宽度
			sheet.setColumnView(3, 10);//设置宽度
			
			int i = 0;
			while (rs.next()){
				i++;
				sheet.addCell(new Label(0,i,Tools.filterNull(rs.getString("vcardno"))));//员工卡号
				sheet.addCell(new Label(1,i,Tools.filterNull(rs.getString("vemp_name"))));//员工姓名
				sheet.addCell(new Label(2,i,Tools.filterNull(rs.getString("vdepart"))));//部门
				sheet.addCell(new Label(3,i,Tools.filterNull(rs.getString("dtime"))));//打卡日期
				sheet.addCell(new Label(4,i,Tools.filterNull(rs.getString("dtimesj"))));//打卡时间
				sheet.addCell(new Label(5,i,Tools.filterNull(rs.getString("vimpact"))));//出入状态
				sheet.addCell(new Label(6,i,Tools.filterNull(rs.getString("vdoorname"))));//出入门
				sheet.addCell(new Label(7,i,Tools.filterNull(rs.getString("cstatus"))));//状态
			}
			
			workbook.write(); 
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, sta, conn);
			session.removeAttribute("exportflag");
		}
	}
	//查询部门
	public void queryDepar(){
		String sql = "select vDepart,vUpDepartId from Depart";
		Connection conn = DBUtil.getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		List list = new ArrayList();
		System.out.println(sql);
		try {
			sta = conn.prepareStatement(sql);
			rs = sta.executeQuery(); //查询
			list = DBUtil.convertList(rs);
			String jsonResult = JsonUtil.toJson(list);
			JsonUtil.printEasyuiJson(jsonResult, httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(rs, sta, conn);
		}
	}
	
	public Map<String, Object> toEasyuiJson(List<Map<String, String>> list, PageVO pageVo) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (list == null || list.size() <= 0) {
			result.put("total", 0);
			result.put("rows", "");
		} else {
			result.put("total", pageVo.getTotalRecord());
			result.put("rows", list);
		}
		return result;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
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
	
}
