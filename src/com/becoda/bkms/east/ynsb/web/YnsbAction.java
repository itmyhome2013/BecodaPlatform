package com.becoda.bkms.east.ynsb.web;

import java.io.IOException;
import java.io.OutputStream;
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
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.east.ssjc.SsjcConstants;
import com.becoda.bkms.east.ssjc.ucc.IZbjcUCC;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.east.ynsb.ucc.IYnsbUCC;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.util.FarmManager;

/**
 * 
 * <p>Description: 用能设备action</p>
 * @author liu_hq
 * @date 2017-9-27
 *
 */
public class YnsbAction extends GenericPageAction{

	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private YnsbBO bo;//用能设备BO
	private String treeId;
	
	public String queryYnsbIndex(){
		List list = SysCacheTool.queryCodeItemBySetIdAndSuperId(SsjcConstants.YNSB_SUPERID,"-1");
		request.setAttribute("list", list);
		return "queryYnsbIndex";
	}
	public String queryYnsbIndexSM(){
		List list = SysCacheTool.queryCodeItemBySetIdAndSuperId(SsjcConstants.YNSB_SUPERID,"-1");
		request.setAttribute("list", list);
		return "queryYnsbIndexSM";
	}
	//查询用能设备初始化
	public String queryYnsbSonInit() throws BkmsException{
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		String itemId = SsjcConstants.ITEMID_ZDYN; //默认正东集团用能单位计量
		String itemName=null;
		//根据用能单位id查询name
		List<CodeItemBO> list = ucc.queryNameById(SsjcConstants.YNSB_SUPERID, itemId);
		if(list!=null && list.size()>0){
			CodeItemBO codeItem=list.get(0);
			itemName=codeItem.getItemName();
		}
		String val = request.getParameter("itemId");
		String types = Tools.filterNull(request.getParameter("types"));
		
		if(val!=null&&!val.equals("")){
			itemId = val;
		}
		String title = request.getParameter("title");
		if (title != null && !title.equals("")) {
			itemName = title;
		}
		
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		
		request.setAttribute("itemId", itemId);
		request.setAttribute("itemName", itemName);
		if(types.equals("hbjc")){
			return "queryYnsbSonHbjc";
		}
		
		return "queryYnsbSon";
	}
	public String queryYnsbSonInitSM() throws BkmsException{
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		String itemId = SsjcConstants.ITEMID_ZDYN; //默认正东集团用能单位计量
		String itemName=null;
		//根据用能单位id查询name
		List<CodeItemBO> list = ucc.queryNameById(SsjcConstants.YNSB_SUPERID, itemId);
		if(list!=null && list.size()>0){
			CodeItemBO codeItem=list.get(0);
			itemName=codeItem.getItemName();
		}
		String val = request.getParameter("itemId");
		String types = Tools.filterNull(request.getParameter("types"));
		
		if(val!=null&&!val.equals("")){
			itemId = val;
		}
		String title = request.getParameter("title");
		if (title != null && !title.equals("")) {
			itemName = title;
		}
		
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		
		request.setAttribute("itemId", itemId);
		request.setAttribute("itemName", itemName);
		if(types.equals("hbjc")){
			return "queryYnsbSonHbjcSM";
		}
		
		return "queryYnsbSonSM";
	}
	
	//查询用能设备
	public String queryYnsbSon(){
		query = EasyUiUtils.formatGridQuery(request, query);
		String itemid = request.getParameter("itemid");
		if(itemid!=null&&!itemid.equals("")){
			query.addUserWhere(" and YNSB_FID = '"+itemid+"'");
		}
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			DataResult search = ucc.queryList(query).search();
			search.runDictionary(FarmManager.instance().findDicTitleForIndex("NYZL"), "YNSB_NYZL");
			jsonResult = EasyUiUtils.formatGridData(search);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String zdyqglListInit() throws BkmsException{
		String itemid = Tools.filterNull(request.getParameter("itemid"));
		String itemName="";
		String nyzl = "11";
		if(itemid.equals("3010401110")){
			itemName = "园区水表";
		}else if(itemid.equals("3010401118")){
			itemName = "园区供热";
		}else if(itemid.equals("3010401120")){
			itemName = "园区电表";
		}
		request.setAttribute("itemId", itemid);
		request.setAttribute("itemName", itemName);
		request.setAttribute("nyzl", nyzl);
		
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		
		return "zdyqglListInit";
	}
	
	public String zdyqglListInitSM() throws BkmsException{
		String itemid = Tools.filterNull(request.getParameter("itemid"));
		String itemName="";
		String nyzl = "11";
		if(itemid.equals("3010401110")){
			itemName = "园区水表";
		}else if(itemid.equals("3010401118")){
			itemName = "园区供热";
		}else if(itemid.equals("3010401120")){
			itemName = "园区电表";
		}
		request.setAttribute("itemId", itemid);
		request.setAttribute("itemName", itemName);
		request.setAttribute("nyzl", nyzl);
		
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		
		return "zdyqglListInitSM";
	}
	
	// 查询用能设备(正东园区管理)
	public String queryYnsbZdyq() {
		query = EasyUiUtils.formatGridQuery(request, query);
		String itemid = request.getParameter("itemid");
		if (itemid != null && !itemid.equals("")) {
			query.addUserWhere(" and YNSB_FID = '" + itemid + "'");
//			if(itemid.equals("3010401110")){
//				query.addUserWhere(" and YNSB_NYZL = '4'");
//			}
		}
		try {
			IYnsbUCC ucc = (IYnsbUCC) BkmsContext.getBean("ynsbUCC");
			DataResult search = ucc.queryList(query).search();
			search.runDictionary(FarmManager.instance().findDicTitleForIndex("NYZL"),"YNSB_NYZL");
			//search.runDictionary("1:水,2:电,3:热", "YNSB_NYZL");
			jsonResult = EasyUiUtils.formatGridData(search);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	
	//导出用能设备
	public void exportYnsb(){
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			String itemName = Tools.filterNull(request.getParameter("itemName"));
			List<YnsbBO> list = ucc.queryByBO(bo);
			String tableName = ""+itemName+"" +  "计量器具表.xls";//excel名
			String titleName = ""+itemName+"" +  "计量器具表";//title名
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
			sheet.mergeCells(0, 0, 5, 0);
			sheet.addCell(new Label(0, 0, "" + titleName + "",
					Dwcf_center));
			sheet.addCell(new Label(0, 1, "用能单位管理编号",wcf_center));
			sheet.addCell(new Label(1, 1, "安装使用地点",wcf_center));
			sheet.addCell(new Label(2, 1, "能源种类",wcf_center));
			sheet.addCell(new Label(3, 1, "校准时间",wcf_center));
			sheet.addCell(new Label(4, 1, "倍率",wcf_center));
			sheet.addCell(new Label(5, 1, "状态",wcf_center));
			sheet.setColumnView(0, 15);//设置宽度
			sheet.setColumnView(1, 15);//设置宽度
			sheet.setColumnView(2, 15);//设置宽度
			sheet.setColumnView(3, 15);//设置宽度
			
			
			if(list!=null&&list.size()>0){
				Map<String, String> map = FarmManager.instance().findDicTitleForIndex("NYZL");
				for(int i = 0;i<list.size();i++){
					YnsbBO ynsbBO = list.get(i);
					sheet.addCell(new Label(0,i+2,Tools.filterNull(ynsbBO.getYnsb_glbh())));//设备编号
					String nyzl = "";
					if(ynsbBO.getYnsb_nyzl()!=null&&!ynsbBO.getYnsb_nyzl().equals("")){
							String key = ynsbBO.getYnsb_nyzl();
							String value = map.get(key);
							if (value != null) {
								if(!"''".equals(value)){
									nyzl = value;	
								}
								
							}
					}
					sheet.addCell(new Label(1,i+2,Tools.filterNull(ynsbBO.getYnsb_azdd())));//安装使用地点
					sheet.addCell(new Label(2,i+2,nyzl));//能源种类
					sheet.addCell(new Label(3,i+2,Tools.filterNull(ynsbBO.getYnsb_jdrq())));//校准时间
					sheet.addCell(new Label(4,i+2,Tools.filterNull(ynsbBO.getYnsb_rate())));//倍率
					String zt = "";
					if(ynsbBO.getYnsb_zt()!=null){
						if(ynsbBO.getYnsb_zt().equals("1")){
							zt = "启用";
						}else if(ynsbBO.getYnsb_zt().equals("0")){
							zt = "禁用";
						}
					}
					sheet.addCell(new Label(5,i+2,zt));//状态
				}
			}
			workbook.write();  
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//更新用能设备初始化
	public String editInit(){
		String itemId = Tools.filterNull(request.getParameter("itemid"));
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid")); 
		String isyq = Tools.filterNull(request.getParameter("isyq"));//是否为园区管理
		String updatetype = Tools.filterNull(request.getParameter("updatetype")); //环保
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
		request.setAttribute("isyq", isyq);
		request.setAttribute("updatetype", updatetype);
		return "editInit";
	}
	//更新用能设备
	public void ynsbEdit() throws IOException{
		boolean flag = false;
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			ucc.editYnsb(bo,user);
			ucc.editSisBySbid(bo.getYnsb_id(), bo.getYnsb_nyzl(),user); //修改sis标示
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	
	//更新用能设备(保存或更新设备校对日期)
	public void saveYnsbJdrq() throws IOException{
		boolean flag = false;
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid")); 
		String jdrq = Tools.filterNull(request.getParameter("jdrq")); 
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			YnsbBO bo1 = new YnsbBO();
			bo1.setYnsb_id(ynsbid);
			List<YnsbBO> queryByBO = ucc.queryByBO(bo1);
			if(queryByBO!=null&&queryByBO.size()>0){
				bo = queryByBO.get(0);
				bo.setYnsb_jdrq(jdrq);
				ucc.editYnsb(bo,user);
				flag = true;
			}	
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	
	//更新用能设备(保存或更新设备倍率)
	public void saveYnsbRate() throws IOException{
		boolean flag = false;
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid")); 
		String rate = Tools.filterNull(request.getParameter("rate")); 
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			YnsbBO bo1 = new YnsbBO();
			bo1.setYnsb_id(ynsbid);
			List<YnsbBO> queryByBO = ucc.queryByBO(bo1);
			if(queryByBO!=null&&queryByBO.size()>0){
				bo = queryByBO.get(0);
				bo.setYnsb_rate(rate);
				ucc.editYnsb(bo,user);
				flag = true;
			}	
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	
	//删除用能设备
	public void deleteYnsb() throws IOException{
		boolean flag = false;
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			ucc.deleteYnsbSisBySbid(bo.getYnsb_id(),user);
			ucc.deleteYnsb(bo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	//设置用能设备sis标示初始化
	public String editYnsbSisInit(){
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid"));
		String nyzl = "";
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			YnsbBO bo = new YnsbBO();
			bo.setYnsb_id(ynsbid);
			List<YnsbBO> list = ucc.queryByBO(bo);
			if(list!=null&&list.size()>0){
				YnsbBO boc = list.get(0);
				nyzl = boc.getYnsb_nyzl();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("ynsbid", ynsbid);
		request.setAttribute("nyzl", nyzl);
		return "editYnsbSisInit";
	}
	//设置用能设备校对日期
	public String editYnsbJdrq(){
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid"));
		String jdrq = Tools.filterNull(request.getParameter("jdrq"));
		request.setAttribute("ynsbid", ynsbid);
		request.setAttribute("jdrq", jdrq);
		return "editYnsbJdrq";
	}
	
	/**
	 * 设置倍率
	 * @return
	 */
	public String editYnsbRate(){
		String ynsbid = Tools.filterNull(request.getParameter("ynsbid"));
		String rate = Tools.filterNull(request.getParameter("rate"));
		request.setAttribute("ynsbid", ynsbid);
		request.setAttribute("rate", rate);
		return "editYnsbRate";
	}
	
	//查询用能设备sis标示
	public String queryYnsbSis(){
		String ynsbid = request.getParameter("ynsbid");
		query = EasyUiUtils.formatGridQuery(request, query);
		query.addUserWhere(" and YNSB_ID = '"+ynsbid+"'");
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			DataResult search = ucc.queryYnsbSis(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//编辑用能设备标示
	public void editYnsbSis() throws IOException{
		boolean flag = false;
		String jsondata = request.getParameter("jsondata");
		String ynsbid = request.getParameter("ynsbid");
		String nyzl = request.getParameter("nyzl");
		JSONArray jsonDataArray = JSONArray.fromObject(jsondata);
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			for(int i = 0; i<jsonDataArray.size();i++){
				JSONObject obj = jsonDataArray.getJSONObject(i);
				YnsbSisBO bo = new YnsbSisBO();
				bo.setYnsb_id(ynsbid);
				bo.setYnsbsis_nyzl(nyzl);
				bo.setYnsbsis_order(obj.getInt("YNSBSIS_ORDER"));
				bo.setYnsbsis_bs(obj.getString("YNSBSIS_BS"));
				bo.setYnsbsis_mc(obj.getString("YNSBSIS_MC"));
				bo.setYnsbsis_id(obj.getString("YNSBSIS_ID"));
				bo.setYnsbsis_islj(obj.getString("YNSBSIS_ISLJ"));
				bo.setYnsbsis_sj(Tools.getSysDate("yyyy-MM-dd HH:mm"));
				ucc.editYnsbSis(bo,user);
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	//删除用能设备标示
	public void deleteYnsbSis() throws IOException{
		boolean flag = false;
		String jsondata = request.getParameter("jsondata");
		JSONArray jsonDataArray = JSONArray.fromObject(jsondata);
		try {
			IYnsbUCC ucc = (IYnsbUCC)BkmsContext.getBean("ynsbUCC");
			for(int i = 0; i<jsonDataArray.size();i++){
				JSONObject obj = jsonDataArray.getJSONObject(i);
				YnsbSisBO bo = new YnsbSisBO();
				bo.setYnsbsis_id(obj.getString("YNSBSIS_ID"));
				ucc.deleteYnsbSis(bo,user);
			}
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
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	
}
