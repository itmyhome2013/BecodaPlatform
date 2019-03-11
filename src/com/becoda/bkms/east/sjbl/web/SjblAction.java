package com.becoda.bkms.east.sjbl.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.sjbl.pojo.SjblBO;
import com.becoda.bkms.east.sjbl.ucc.ISjblUCC;
import com.becoda.bkms.east.ssjc.SsjcConstants;
import com.becoda.bkms.east.ssjc.ucc.IZbjcUCC;
import com.becoda.bkms.east.yjbj.pojo.YjbjBO;
import com.becoda.bkms.east.yjbj.ucc.IYjbjUCC;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.farm.core.report.ReportException;
import com.farm.core.report.ReportManagerInter;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.sql.utils.JsonUtil;

/**
 * 
 * <p>Description: 手工补录action</p>
 * @author zhu lw
 * @date 2017-11-27
 *
 */
public class SjblAction extends GenericPageAction{

	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private YnsbBO bo;//用能设备BO
	private List<Map<String, Object>> resultList;
	private InputStream inputStream;
	private String filename;
	private String zdjz_name; //重点机组
	private String nyzl_name; //能源种类(名字)
	private String sjbl_nyzl; //能源种类
	private String sjbl_date; //录入日期
	private String treeId;
	
	public String forSend(){
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		return "success";
	}

	public String queryYnsbIndex() throws IOException, BkmsException{
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		List qxList=user.getUserRoleList();
		RoleInfoBO role=(RoleInfoBO) qxList.get(0);
		String qxDesc=role.getDescription();
		List<CodeItemBO> list = ucc.queryCodeInSgbl(SsjcConstants.YNSB_SUPERID, "-1", qxDesc,"5",qxDesc);
		 String jsonResult = JsonUtil.toJson(list);
		 JsonUtil.printEasyuiJson(jsonResult, httpResponse);
		return null;
	}
	//查询计量编号
	public String queryYnsbGlbh() throws IOException, BkmsException{
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		String itemid = request.getParameter("itemid");//重点机组
		List<YnsbBO> list = ucc.queryYnsbGlbh(itemid);
		 String jsonResult = JsonUtil.toJson(list);
		 JsonUtil.printEasyuiJson(jsonResult, httpResponse);
		return null;
	}
	//查询计量编号
	public String queryGlbhByNyzl() throws IOException, BkmsException{
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		String itemid = request.getParameter("itemid");//重点机组
		String nyzl = request.getParameter("nyzl");//能源种类
		List<YnsbBO> list = ucc.queryGlbhByNyzl(itemid,nyzl);
		 String jsonResult = JsonUtil.toJson(list);
		 JsonUtil.printEasyuiJson(jsonResult, httpResponse);
		return null;
	}
		
	/**
	 * 手工补录导出	
	 * @return
	 */
	public String sgblExport() {
		try {
			//++++++++
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(1000);
			query.getAndRemoveRule("EAST_SJBL_DATE");
			String q_date_start = request.getParameter("q_date_start"); //查询开始日期
			String q_date_end = request.getParameter("q_date_end"); //查询结束日期
			query.addUserWhere(" and EAST_SJBL_DATE >= '"+q_date_start+"' and EAST_SJBL_DATE <= '"+q_date_end+"'");	
			query.addUserWhere(" order by EAST_SJBL_DATE desc ,EAST_SJBL_SBBH ");
			try {
				ISjblUCC ucc = (ISjblUCC)BkmsContext.getBean("sjblUCC");
				DataResult search = ucc.queryList(query).search();
				resultList = search.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//++++++++
			ReportManagerInter reportUCC = (ReportManagerInter) BkmsContext.getBean("excelReportId");
			
			// ------------------------开始导出
			Map<String, Object> para = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (resultList != null && resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> map = resultList.get(i);
					String nyzl = (String) map.get("YNSB_NYZL");
					list.add(map);
				}
			}

			para.put("result", list);
			para.put("zdjz_name", zdjz_name);
			para.put("nyzl_name", nyzl_name);
			para.put("sjbl_date", sjbl_date);
			
			if("6".equals(sjbl_nyzl) || "7".equals(sjbl_nyzl)){
				filename = "手工补录 - 电.xls";
			}else{
				filename = "手工补录.xls";
			}
			
			reportUCC.generate(filename, para);
			inputStream = new FileInputStream(new File(reportUCC.getReportPath(filename)));
		} catch (ReportException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BkmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";
	}
		
	//查询手动补录数据
	public String querySjblSon(){
		query = EasyUiUtils.formatGridQuery(request, query);
		String zdjz = request.getParameter("zdjz");//重点机组
		String nyzl = request.getParameter("nyzl");//能源种类
		String lrrq = request.getParameter("lrrq");//录入日期
		
		String q_date_start = request.getParameter("q_date_start"); //查询开始日期
		String q_date_end = request.getParameter("q_date_end"); //查询结束日期
		
		query.addUserWhere(" and EAST_SJBL_ZDJZ= '"+zdjz+"'");
		query.addUserWhere(" and EAST_SJBL_NYZL= '"+nyzl+"'");
		if(Tools.isNull(lrrq)){
			lrrq= Tools.getSysDate("yyyy-MM");
			query.addUserWhere(" and EAST_SJBL_DATE like '"+lrrq+"%'");
		}else{
			//query.addUserWhere(" and EAST_SJBL_DATE= '"+lrrq+"'");
			query.addUserWhere(" and EAST_SJBL_DATE >= '"+q_date_start+"' and EAST_SJBL_DATE <= '"+q_date_end+"'");
		}	
		query.addUserWhere(" order by EAST_SJBL_DATE desc ,EAST_SJBL_SBBH ");
		try {
			ISjblUCC ucc = (ISjblUCC)BkmsContext.getBean("sjblUCC");
			DataResult search = ucc.queryList(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//设置信息补录页面初始化
	public String editYjbjSisInit(){
		String zdjz = Tools.filterNull(request.getParameter("zdjz"));
		String nyzl = Tools.filterNull(request.getParameter("nyzl"));
		String lrrq = Tools.filterNull(request.getParameter("lrrq"));
		String sjbl_id = Tools.filterNull(request.getParameter("sjbl_id"));
		request.setAttribute("zdjz", zdjz);
		request.setAttribute("nyzl", nyzl);
		request.setAttribute("lrrq", lrrq);
		request.setAttribute("sjbl_id", sjbl_id);
		return "editInit";
	}
	//查询手动补录信息进行编辑
	public void querySjblMsgForEdit(){
		String zdjz = request.getParameter("zdjz");//重点机组
		String nyzl = request.getParameter("nyzl");//能源种类
		String lrrq = request.getParameter("lrrq");//录入日期
		String sjbl_id = request.getParameter("sjbl_id"); //补录信息ID
		try {
			ISjblUCC ucc = (ISjblUCC)BkmsContext.getBean("sjblUCC");
			PageVO pageVo = new PageVO();
			List list = ucc.querySjblMsgForEdit(zdjz,nyzl,lrrq,sjbl_id);
			JsonUtil.printEasyuiJson(JsonUtil.toEasyuiJson(list, pageVo), httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//编辑手工补录
	public void editSjblMsg() throws IOException{
		boolean flag = false;
		String jsondata = request.getParameter("jsondata");
		String lrrq = request.getParameter("lrrq");//录入日期
		String zdjz = request.getParameter("zdjz");//重点机组
		try {
		JSONArray jsonDataArray = JSONArray.fromObject(jsondata);
		ISjblUCC ucc = (ISjblUCC)BkmsContext.getBean("sjblUCC");
			for(int i = 0; i<jsonDataArray.size();i++){
				JSONObject obj = jsonDataArray.getJSONObject(i);
				SjblBO bo=new SjblBO();
				bo.setEast_sjbl_date(lrrq);
				bo.setEast_sjbl_zdjz(zdjz);
				String nyzl=Tools.filterNull(obj.getString("YNSB_NYZL"));
				bo.setEast_sjbl_nyzl(nyzl);//能源种类
				bo.setEast_sjbl_sbid(Tools.filterNull(obj.getString("YNSB_ID")));//设备ID
				bo.setEast_sjbl_sbbh(Tools.filterNull(obj.getString("YNSB_GLBH")));//设备编号
				if("1".equals(nyzl) || "2".equals(nyzl) || "3".equals(nyzl) || "4".equals(nyzl) || "5".equals(nyzl) || "8".equals(nyzl) || "9".equals(nyzl) || "10".equals(nyzl)){
					if(obj.has("EAST_SJBL_LJZ") && !"".equals(obj.getString("EAST_SJBL_LJZ"))){
						bo.setEast_sjbl_ljz(Tools.filterNull(obj.getString("EAST_SJBL_LJZ")));//累积值
						
					}if(obj.has("EAST_SJBL_LOOKDATE") && !"".equals(obj.getString("EAST_SJBL_LOOKDATE"))){
						bo.setEast_sjbl_lookdate(Tools.filterNull(obj.getString("EAST_SJBL_LOOKDATE"))); //查表时间
						
					}else{
						continue;
					}
				}else if("6".equals(nyzl) || "7".equals(nyzl)){
					int m=0;
					if(obj.has("EAST_SJBL_FENG") && !"".equals(obj.getString("EAST_SJBL_FENG"))){
						bo.setEast_sjbl_feng(Tools.filterNull(obj.getString("EAST_SJBL_FENG")));// 峰（电累积值）
						m++;
					}
					if(obj.has("EAST_SJBL_GU") && !"".equals(obj.getString("EAST_SJBL_GU"))){
						bo.setEast_sjbl_gu(Tools.filterNull(obj.getString("EAST_SJBL_GU")));// 谷（电累积值）
						m++;
					}
					if(obj.has("EAST_SJBL_PING") && !"".equals(obj.getString("EAST_SJBL_PING"))){
						bo.setEast_sjbl_ping(Tools.filterNull(obj.getString("EAST_SJBL_PING")));// 平（电累积值）
						m++;
					}
					if(obj.has("EAST_SJBL_JIAN") && !"".equals(obj.getString("EAST_SJBL_JIAN"))){
						bo.setEast_sjbl_jian(Tools.filterNull(obj.getString("EAST_SJBL_JIAN")));// 尖（电累积值）
						m++;
					}
					if(obj.has("EAST_SJBL_TOTAL") && !"".equals(obj.getString("EAST_SJBL_TOTAL"))){
						bo.setEast_sjbl_total(Tools.filterNull(obj.getString("EAST_SJBL_TOTAL")));// 总值
						m++;
					}
					if(obj.has("EAST_SJBL_LOOKDATE") && !"".equals(obj.getString("EAST_SJBL_LOOKDATE"))){
						bo.setEast_sjbl_lookdate(Tools.filterNull(obj.getString("EAST_SJBL_LOOKDATE")));// 查表时间
						m++;
					}
					if(m==0){
						continue;
					}	
				}
				if (!obj.has("EAST_SJBL_ID")){
					obj.put("EAST_SJBL_ID", "");
			    }
				bo.setEast_sjbl_id(Tools.filterNull(obj.getString("EAST_SJBL_ID")));
				ucc.addOrUpdate(bo,user);
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
	public List<Map<String, Object>> getResultList() {
		return resultList;
	}
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
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
	public String getZdjz_name() {
		return zdjz_name;
	}
	public void setZdjz_name(String zdjzName) {
		zdjz_name = zdjzName;
	}
	public String getNyzl_name() {
		return nyzl_name;
	}
	public void setNyzl_name(String nyzlName) {
		nyzl_name = nyzlName;
	}
	public String getSjbl_date() {
		return sjbl_date;
	}
	public void setSjbl_date(String sjblDate) {
		sjbl_date = sjblDate;
	}
	public String getSjbl_nyzl() {
		return sjbl_nyzl;
	}
	public void setSjbl_nyzl(String sjblNyzl) {
		sjbl_nyzl = sjblNyzl;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

}
