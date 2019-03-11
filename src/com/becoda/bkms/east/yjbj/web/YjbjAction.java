package com.becoda.bkms.east.yjbj.web;

import java.io.IOException;
import java.io.OutputStream;
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
import net.sf.json.JSONObject;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.ssjc.SsjcConstants;
import com.becoda.bkms.east.ssjc.ucc.IZbjcUCC;
import com.becoda.bkms.east.yjbj.pojo.NxjcYjbjSbBO;
import com.becoda.bkms.east.yjbj.pojo.YcbjBO;
import com.becoda.bkms.east.yjbj.pojo.YjbjBO;
import com.becoda.bkms.east.yjbj.ucc.IYjbjUCC;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.ucc.IYnsbUCC;
import com.becoda.bkms.east.zdsb.pojo.ZdsbBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.becoda.bkms.east.zdsb.ucc.IZdsbUCC;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.util.FarmManager;

/**
 * 
 * <p>Description: 预警报警action</p>
 * @author zhu lw
 * @date 2017-11-24
 *
 */
public class YjbjAction extends GenericPageAction{

	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private YnsbBO bo;//用能设备BO
	private YjbjBO yjbjBo;//预警报警BO\
	private NxjcYjbjSbBO nxjcbo; //能效检测预警报警设备BO
	private YcbjBO ycbjBo;//异常报警BO
	private String treeId;
	
	
	public String queryYjbjIndex(){
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			List list = ucc.queryCodeItem("", "6");
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryYjbjIndex";
	}
	//查询预警报警初始化
	public String queryYjbjSonInit(){
		String itemId = SsjcConstants.ITEMID_ZDYN; //默认正东集团用能单位计量
		String val = request.getParameter("itemId");
		if(val!=null&&!val.equals("")){
			itemId = val;
		}
		request.setAttribute("itemId", itemId);
		
		//
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		//
		
		return "queryYjbjSon";
	}
	//查询预警报警
	public String queryYjbjSon(){
		query = EasyUiUtils.formatGridQuery(request, query);
		String itemid = request.getParameter("itemid");
		if(itemid!=null&&!itemid.equals("")){
			query.addUserWhere(" and YNSB_FID = '"+itemid+"'");
		}
		try {
			IYjbjUCC ucc = (IYjbjUCC)BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryList(query).search();
			search.runDictionary(FarmManager.instance().findDicTitleForIndex("NYZL"), "YNSB_NYZL");
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//更新用能设备初始化
	public String editInit(){
		String itemId = Tools.filterNull(request.getParameter("itemid"));
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid")); 
		if(!ynsbid.equals("")){
			try {
				IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
				YnsbBO bo1 = new YnsbBO();
				bo1.setYnsb_id(ynsbid);
				List<YnsbBO> queryByBO = ucc.queryByBO(bo1);
				if(queryByBO!=null&&queryByBO.size()>0){
					bo = queryByBO.get(0);
					itemId = bo.getYnsb_fid();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("itemId", itemId);
		return "editInit";
	}
	//更新用能设备
	public void ynsbEdit() throws IOException{
		boolean flag = false;
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			ucc.editYnsb(bo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	// 更新重点设备初始化
	public String editYjbjZdsbInit() {
		String zdsbid = Tools.filterNull(request.getParameter("zdsbid"));
		String sisBs=null;
		if (!zdsbid.equals("")) {
			try {
				IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
				YjbjBO bo1 = new YjbjBO();
				bo1.setYjbj_sbid(zdsbid);
				List<YjbjBO> queryByBO = ucc.queryByYjbjBO(bo1);
				if (queryByBO != null && queryByBO.size() > 0) {
					yjbjBo = queryByBO.get(0);
				}
				ZdsbSisBO sisbo=new ZdsbSisBO();
				sisbo.setZdsb_id(zdsbid);
				List<ZdsbSisBO> queryBySisBO = ucc.queryBySisBO(sisbo);
				if (queryBySisBO != null && queryBySisBO.size() > 0) {
					ZdsbSisBO querySisBo = queryBySisBO.get(0);
					sisBs=Tools.filterNull(querySisBo.getZdsbsis_bs());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("sisBs", sisBs);
			request.setAttribute("zdsbid", zdsbid);
		}
		
		//
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		//
		
		return "editYjbjZdsbInit";
	}
	//更新用能设备
	public void yjbjDataEdit() throws IOException{
		boolean flag = false;
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			ucc.editYjbj(yjbjBo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	//设置预警报警sis标示初始化
	public String editYjbjSisInit(){
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid"));
		String nyzl = Tools.filterNull(request.getParameter("nyzl"));
		String nyjc = Tools.filterNull(request.getParameter("nyjc"));
		request.setAttribute("ynsbid", ynsbid);
		request.setAttribute("nyzl", nyzl);
		request.setAttribute("nyjc", nyjc);
		
		//
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		//
		
		return "editYjbjSisInit";
	}
	//编辑异常报警初始化
		public String editYcbjInit(){
			String ycbjId = Tools.filterNull(request.getParameter("ycbjId"));
			request.setAttribute("ycbjId", ycbjId);
			return "editYcbjInit";
		}
		//更新异常报警状态
		public void editYcbjState() throws IOException{
			boolean flag = false;
			try {
				IYjbjUCC ucc = (IYjbjUCC)BkmsContext.getBean("yjbjUCC");
				if(ycbjBo!=null && ycbjBo.getYcbj_id()!=null){
					String[] oids = ycbjBo.getYcbj_id().split(",");
					for (int i = 0; i < oids.length; i++) {
						YcbjBO bjbo=new YcbjBO();
						bjbo.setYcbj_id(oids[i]);
						List<YcbjBO> list=ucc.queryYcbjByBO(bjbo);
						YcbjBO queryBjbo=list.get(0);
						queryBjbo.setYcbj_czr(Tools.filterNull(ycbjBo.getYcbj_czr()));
						queryBjbo.setYcbj_czsj(Tools.filterNull(ycbjBo.getYcbj_czsj()));
						queryBjbo.setYcbj_state(Tools.filterNull(ycbjBo.getYcbj_state()));
						ucc.editYcbj(queryBjbo);
					}
					flag = true;
				}else{
					flag=false;
				}							
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
			httpResponse.getWriter().print(flag);
		}
	//查询用能设备sis标示
	public String queryYjbjSis(){
		String ynsbid = request.getParameter("ynsbid");
		query = EasyUiUtils.formatGridQuery(request, query);
		query.addUserWhere(" and YNSB_ID = '"+ynsbid+"'");
		try {
			IYjbjUCC ucc = (IYjbjUCC)BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryYnsbSis(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//编辑用能设备标示
	public void editYjbjSis() throws IOException{
		boolean flag = false;
		String jsondata = request.getParameter("jsondata");
		try {
		JSONArray jsonDataArray = JSONArray.fromObject(jsondata);
			IYjbjUCC ucc = (IYjbjUCC)BkmsContext.getBean("yjbjUCC");
			for(int i = 0; i<jsonDataArray.size();i++){
				JSONObject obj = jsonDataArray.getJSONObject(i);
				if(!Tools.filterNull(obj.getString("YNSBSIS_BS")).equals("")){
					YjbjBO yjbjBO=new YjbjBO();
					//预警报警指标
					yjbjBO.setYjbj_id(Tools.filterNull(obj.getString("YJBJ_ID")));
					yjbjBO.setYjbj_sbid(Tools.filterNull(obj.getString("YNSB_ID")));
					yjbjBO.setYjbj_sis_bs(Tools.filterNull(obj.getString("YNSBSIS_BS")));
					yjbjBO.setYjbj_min(Tools.filterNull(obj.getString("YJBJ_MIN")));
					yjbjBO.setYjbj_max(Tools.filterNull(obj.getString("YJBJ_MAX")));
					ucc.editYjbj(yjbjBO,user);
				}
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	
	public String queryYcbjIndex(){
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			List list = ucc.queryCodeItem("", "6");
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryYcbjIndex";
	}
	public String queryYcbjHistoryIndex(){
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			List list = ucc.queryCodeItem("", "6");
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryYcbjHistoryIndex";
	}
	//查询异常报警初始化
	public String queryYcbjInit() throws BkmsException {
		String lx = Tools.filterNull(request.getParameter("itemId"));
		if(lx.equals("nxjc")){
			request.setAttribute("itemId", "1");
			request.setAttribute("itemName", "能效检测");
			return "queryNxjcYcbjInit";
		}
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		String itemId = SsjcConstants.ITEMID_ZDYN; // 默认正东集团用能单位计量
		String itemName=null;
		//根据用能单位id查询name
		List<CodeItemBO> list = ucc.queryNameById(SsjcConstants.YNSB_SUPERID, itemId);
		if(list!=null && list.size()>0){
			CodeItemBO codeItem=list.get(0);
			itemName=codeItem.getItemName();
		}
		String val = request.getParameter("itemId");
		if (val != null && !val.equals("")) {
			itemId = val;
		}
		String title = request.getParameter("title");
		if (title != null && !title.equals("")) {
			itemName = title;
		}
		request.setAttribute("itemId", itemId);
		request.setAttribute("itemName", itemName);
		return "queryYcbjInit";
	}
	//查询历史异常报警初始化
	public String queryHistoryYcbjInit() throws BkmsException {
		String lx = Tools.filterNull(request.getParameter("itemId"));
		if(lx.equals("nxjc")){
			request.setAttribute("itemId", "1");
			request.setAttribute("itemName", "能效检测");
			return "queryHistoryNxjcYcbjInit";
		}
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		String itemId = SsjcConstants.ITEMID_ZDYN; // 默认正东集团用能单位计量
		String itemName=null;
		//根据用能单位id查询name
		List<CodeItemBO> list = ucc.queryNameById(SsjcConstants.YNSB_SUPERID, itemId);
		if(list!=null && list.size()>0){
			CodeItemBO codeItem=list.get(0);
			itemName=codeItem.getItemName();
		}
		String val = request.getParameter("itemId");
		if (val != null && !val.equals("")) {
			itemId = val;
		}
		String title = request.getParameter("title");
		if (title != null && !title.equals("")) {
			itemName = title;
		}
		request.setAttribute("itemId", itemId);
		request.setAttribute("itemName", itemName);
		return "queryHistoryYcbjInit";
	}
	
	//查询异常报警
	public String queryYcbj() {
		query = EasyUiUtils.formatGridQuery(request, query);
		String itemid = request.getParameter("itemid");
		if (itemid != null && !itemid.equals("")) {
			query.addUserWhere(" and sb.YNSB_FID = '" + itemid + "'");
		}
		try {
			String sj = Tools.getSysDate("yyyy-MM-dd");
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryYcbj(query,sj,itemid).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	//查询异常报警
	public String queryNxjcYcbj() {
		query = EasyUiUtils.formatGridQuery(request, query);
		String itemid = request.getParameter("itemid");
		try {
			String sj = Tools.getSysDate("yyyy-MM-dd");
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryYcbj(query,sj,itemid).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
		//导出异常报警
		public void exportYcbjSS(){
			try {
				IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
				String itemid = Tools.filterNull(request.getParameter("itemid"));
				String itemName = Tools.filterNull(request.getParameter("itemName"));
				String sj = Tools.getSysDate("yyyy-MM-dd");
				String tableName = "" + sj + ""+ ""+itemName+"" +  "异常报警表.xls";//excel名
				String titleName = "" + sj + ""+ ""+itemName+"" +  "异常报警表";//title名
				List<Map<String, String>> list = ucc.queryBySj(sj, itemid);
				OutputStream os = httpResponse.getOutputStream();
				httpResponse.reset();
				httpResponse.setHeader("Content-disposition", "attachment;filename="+ new String(tableName.getBytes("GB2312"),"ISO8859-1"));
				//创建工作薄
				WritableWorkbook workbook = Workbook.createWorkbook(os);  
				WritableSheet sheet = workbook.createSheet("Sheet1", 0);
				//标题格式
				WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
				WritableCellFormat Dwcf_center = new WritableCellFormat(
						BoldFont);
				Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
				WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
				wcf_center.setAlignment(Alignment.CENTRE); //水平居中对齐
				//标题
				sheet.mergeCells(0, 0, 4, 0);
				sheet.addCell(new Label(0, 0, "" + titleName + "",
						Dwcf_center));
				sheet.addCell(new Label(0, 1, "设备编号",wcf_center));
				sheet.addCell(new Label(1, 1, "报警项",wcf_center));
				sheet.addCell(new Label(2, 1, "报警时间",wcf_center));
				sheet.addCell(new Label(3, 1, "监测值",wcf_center));
				sheet.addCell(new Label(4, 1, "报警描述",wcf_center));
				sheet.setColumnView(0, 15);//设置宽度
				sheet.setColumnView(1, 15);//设置宽度
				sheet.setColumnView(2, 15);//设置宽度
				sheet.setColumnView(3, 15);//设置宽度
				sheet.setColumnView(4, 15);//设置宽度
				if(list!=null&&list.size()>0){
					for(int i = 0;i<list.size();i++){
						Map<String, String> map = list.get(i);
						sheet.addCell(new Label(0,i+2,Tools.filterNull(map.get("YNSB_GLBH"))));//设备编号
						
						if(itemid.equals("1")){ //能效检测
							String bjx = "";
							String bjxs = Tools.filterNull(map.get("YNSBSIS_MC"));
							if(bjxs.equals("1")){
								bjx = "气耗比";
							}else if(bjxs.equals("2")){
								bjx = "用水指标";
							}else if(bjxs.equals("3")){
								bjx = "厂综合用电率";
							}else if(bjxs.equals("4")){
								bjx = "单位面积耗热量";
							}else if(bjxs.equals("5")){
								bjx = "单位面积耗电量";
							}else if(bjxs.equals("6")){
								bjx = "单位面积耗水量";
							}
							sheet.addCell(new Label(1,i+2,bjx));//报警项
						}else{
							sheet.addCell(new Label(1,i+2,Tools.filterNull(map.get("YNSBSIS_MC"))));//报警项
						}
						sheet.addCell(new Label(2,i+2,Tools.filterNull(map.get("YCBJ_SJ"))));//报警时间
						sheet.addCell(new Label(3,i+2,Tools.filterNull(map.get("YCBJ_VAL"))));//检测值
						sheet.addCell(new Label(4,i+2,Tools.filterNull(map.get("YCBJ_BJLX"))));//报警描述
					}
				}
				workbook.write();  
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	//查询历史异常报警
	public String queryHistoryYcbj() {
		query = EasyUiUtils.formatGridQuery(request, query);
		String itemid = request.getParameter("itemid");
		if (itemid == null && itemid.equals("")) {
			itemid = SsjcConstants.ITEMID_ZDYN;
		}
		query.addUserWhere(" and sb.YNSB_FID = '" + itemid + "'");
		DBRule startTimeobj = query.getAndRemoveRule("STARTDTIME");
		DBRule endTimeobj = query.getAndRemoveRule("ENDDTIME");
		if(startTimeobj!=null){
			query.addUserWhere(" and yc.YCBJ_SJ >= '" + startTimeobj.getValue() + "'");
		}
		if(endTimeobj!=null){
			query.addUserWhere(" and yc.YCBJ_SJ <= '" + endTimeobj.getValue() + "'");
		}
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryHistoryYcbj(query,itemid).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	//查询历史异常报警
	public String queryNxjcHistoryYcbj() {
		query = EasyUiUtils.formatGridQuery(request, query);
		String itemid = request.getParameter("itemid");
		DBRule startTimeobj = query.getAndRemoveRule("STARTDTIME");
		DBRule endTimeobj = query.getAndRemoveRule("ENDDTIME");
		if(startTimeobj!=null){
			query.addUserWhere(" and yc.YCBJ_SJ >= '" + startTimeobj.getValue() + "'");
		}
		if(endTimeobj!=null){
			query.addUserWhere(" and yc.YCBJ_SJ <= '" + endTimeobj.getValue() + "'");
		}
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryHistoryYcbj(query,itemid).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	//报警提醒查询
	public void queryAlarmAlert(){
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			List<Map<String, Object>> list = ucc.queryAlarmAlert();
			if(list.size()>0){
				for(Map<String, Object> map:list){
					map.put("YNSB_FIDNAME", SysCacheTool.interpretCode(map.get("YNSB_FID").toString()));
				}
			}
			JSONArray json = JSONArray.fromObject(list);
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//报警提醒查询
	public String ycbjMarqueeDetail(){
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			List<Map<String, Object>> list = ucc.queryAlarmAlert();
			if(list.size()>0){
				for(Map<String, Object> map:list){
					map.put("YNSB_FIDNAME", SysCacheTool.interpretCode(map.get("YNSB_FID").toString()));
				}
			}
			jsonResult = new HashMap<String, Object>();
			if (list == null || list.size() <= 0) {
				jsonResult.put("total", 0);
				jsonResult.put("rows", "");
			} else {
				jsonResult.put("total", 5);
				jsonResult.put("rows", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//导出异常报警
	public void exportYcbj(){
		session.setAttribute("exportflag", "true");
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			String starttime = Tools.filterNull(request.getParameter("starttime"));
			String endtime = Tools.filterNull(request.getParameter("endtime"));
			String sbbh = Tools.filterNull(request.getParameter("sbbh"));
			String itemName = Tools.filterNull(request.getParameter("itemName"));
			String lx = Tools.filterNull(request.getParameter("lx"));
			List<Map<String, Object>> list = ucc.queryYcbj(starttime, endtime, sbbh,lx);
			String dateName=null;
			if(starttime!=null && !"".equals(starttime) && endtime!=null && !"".equals(endtime)){
				dateName="" + starttime + ""+"至"+""+endtime+"";
			}else if(starttime!=null && !"".equals(starttime)){
				dateName="" + starttime + "";
			}else if(endtime!=null && !"".equals(endtime)){
				dateName="" + endtime + "";
			}else{
				dateName="";
			}
			String tableName = "" + dateName + ""+ ""+itemName+"" +  "异常报警表.xls";//excel名
			String titleName = "" + dateName + ""+ ""+itemName+"" +  "异常报警表";//title名
			OutputStream os = httpResponse.getOutputStream();
			httpResponse.reset();
			httpResponse.setHeader("Content-disposition", "attachment;filename="+ new String(tableName.getBytes("GB2312"),"ISO8859-1"));
			//创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);  
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			//标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
			WritableCellFormat Dwcf_center = new WritableCellFormat(
					BoldFont);
			Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); //水平居中对齐
			//标题
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(new Label(0, 0, "" + titleName + "",
					Dwcf_center));
			sheet.addCell(new Label(0, 1, "设备编号",wcf_center));
			sheet.addCell(new Label(1, 1, "报警项",wcf_center));
			sheet.addCell(new Label(2, 1, "报警时间",wcf_center));
			sheet.addCell(new Label(3, 1, "检测值",wcf_center));
			sheet.addCell(new Label(4, 1, "报警描述",wcf_center));
			sheet.setColumnView(0, 15);//设置宽度
			sheet.setColumnView(1, 15);//设置宽度
			sheet.setColumnView(2, 15);//设置宽度
			sheet.setColumnView(3, 15);//设置宽度
			sheet.setColumnView(4, 15);//设置宽度
			if(list!=null&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					Map<String, Object> map = list.get(i);
					sheet.addCell(new Label(0,i+2,Tools.filterNull(map.get("ynsb_glbh"))));//设备编号
					if(lx.equals("1")){ //能效检测
						String bjx = "";
						String bjxs = Tools.filterNull(map.get("YNSBSIS_MC"));
						if(bjxs.equals("1")){
							bjx = "气耗比";
						}else if(bjxs.equals("2")){
							bjx = "用水指标";
						}else if(bjxs.equals("3")){
							bjx = "厂综合用电率";
						}else if(bjxs.equals("4")){
							bjx = "单位面积耗热量";
						}else if(bjxs.equals("5")){
							bjx = "单位面积耗电量";
						}else if(bjxs.equals("6")){
							bjx = "单位面积耗水量";
						}
						sheet.addCell(new Label(1,i+2,bjx));//报警项
					}else{
						sheet.addCell(new Label(1,i+2,Tools.filterNull(map.get("YNSBSIS_MC"))));//报警项
					}
					sheet.addCell(new Label(2,i+2,Tools.filterNull(map.get("ycbj_sj"))));//报警时间
					sheet.addCell(new Label(3,i+2,Tools.filterNull(map.get("ycbj_val"))));//检测值
					sheet.addCell(new Label(4,i+2,Tools.filterNull(map.get("ycbj_bjlx"))));//报警描述
				}
			}
			workbook.write();  
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.removeAttribute("exportflag");
		}
	}
	//能效检测预警报警管理
	public String nxjcYjbj(){
		//
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		return "nxjcYjbj";
	}
	public String nxjcYjbjSb(){
		query = EasyUiUtils.formatGridQuery(request, query);
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryNxjcYcbjSb(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//查询能效检测预警报警设备
	public String queryNxjcYjbjSb(){
		query = EasyUiUtils.formatGridQuery(request, query);
		String id = request.getParameter("id");
		query.addUserWhere(" and NXJCSB_ID = '"+id+"' ");
		NxjcYjbjSbBO bo = new NxjcYjbjSbBO();
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			DataResult search = ucc.queryNxjcYcbjSb(query).search();
			List<Object> list = search.getObjectList(NxjcYjbjSbBO.class);
			if(list!=null&&list.size()>0){
				bo = (NxjcYjbjSbBO)list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("nxjcbo", bo);
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		return "queryNxjcYjbjSb";
	}
	//编辑能效检测设备
	public void editNxjcSb() throws IOException{
		boolean flag = false;
		try {
			IYjbjUCC ucc = (IYjbjUCC) BkmsContext.getBean("yjbjUCC");
			ucc.editNxjcYjbj(nxjcbo,user);
			flag = true;
		} catch (Exception e) {
			 flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
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
	public YnsbBO getBo() {
		return bo;
	}
	public void setBo(YnsbBO bo) {
		this.bo = bo;
	}
	public YjbjBO getYjbjBo() {
		return yjbjBo;
	}
	public void setYjbjBo(YjbjBO yjbjBo) {
		this.yjbjBo = yjbjBo;
	}
	public NxjcYjbjSbBO getNxjcbo() {
		return nxjcbo;
	}
	public void setNxjcbo(NxjcYjbjSbBO nxjcbo) {
		this.nxjcbo = nxjcbo;
	}
	public YcbjBO getYcbjBo() {
		return ycbjBo;
	}
	public void setYcbjBo(YcbjBO ycbjBo) {
		this.ycbjBo = ycbjBo;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	
}
