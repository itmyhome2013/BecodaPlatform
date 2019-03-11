package com.becoda.bkms.east.zdsb.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.east.ssjc.SsjcConstants;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.east.ynsb.ucc.IYnsbUCC;
import com.becoda.bkms.east.zdsb.pojo.BjqdBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSbscBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbStateBO;
import com.becoda.bkms.east.zdsb.ucc.IZdsbUCC;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.farm.core.report.ReportException;
import com.farm.core.report.ReportManagerInter;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.util.FarmManager;

/**
 * 
 * <p>
 * Description: 重点设备action
 * </p>
 * 
 * @author zhu_lw
 * @date 2017-12-22
 * 
 */
public class ZdsbAction extends GenericPageAction {

	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private ZdsbBO bo;// 重点设备BO
	private BjqdBO bjBo;// 备件清单BO
	private List<Map<String, Object>> resultList;

	private InputStream inputStream;
	private String filename;

	// 查询重点设备
	public String queryZdsbSon() {
		query = EasyUiUtils.formatGridQuery(request, query);
		String ssbm = Tools.filterNull(request.getParameter("ssbm"));
		String sbmc = Tools.filterNull(request.getParameter("sbmc"));
		try {
			if(!Tools.stringIsNull(ssbm)){
				ssbm = URLDecoder.decode(ssbm, "UTF-8");
				query.addUserWhere(" and ZDSB_SSBM='"+ssbm+"' ");
			}
			if(!Tools.stringIsNull(sbmc)){
				sbmc = URLDecoder.decode(sbmc, "UTF-8");
				query.addUserWhere(" and ZDSB_SBMC like '%"+sbmc+"%' ");
			}
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			DataResult search = ucc.queryList(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
			resultList = search.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 查询重点设备状态
	public String queryZdsbStateSon() {
		query = EasyUiUtils.formatGridQuery(request, query);
		query.addUserWhere(" and zdsb_state is not null ");
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			DataResult search = ucc.queryStateList(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 更新重点设备初始化
	public String editInit() {
		String zdsbid = Tools.filterNull(request.getParameter("zdsbid"));
		if (!zdsbid.equals("")) {
			try {
				IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
				ZdsbBO bo1 = new ZdsbBO();
				bo1.setZdsb_id(zdsbid);
				List<ZdsbBO> queryByBO = ucc.queryByBO(bo1);
				if (queryByBO != null && queryByBO.size() > 0) {
					bo = queryByBO.get(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "editInit";
	}

	// 更新用能设备
	public void zdsbEdit() throws IOException {
		boolean flag = false;
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			ucc.editZdsb(bo,user);
			ZdsbSbscBO sbscBo = new ZdsbSbscBO();
			sbscBo.setZdsb_id(Tools.filterNull(bo.getZdsb_id()));
			ucc.editZdsbSbsc(sbscBo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 删除重点设备
	public void deleteZdsb() throws IOException {
		boolean flag = false;
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			ucc.deleteZdsbSisBySbid(bo.getZdsb_id(),user);// 删除sis标识
			ucc.deleteZdsbSbscBySbid(bo.getZdsb_id(),user);// 删除设备时长
			ucc.deleteZdsb(bo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 更新重点设备状态
	public void editZdsbState() throws IOException {
		String sbzt = Tools.filterNull(request.getParameter("sbzt"));
		boolean flag = false;
		ZdsbStateBO stateBo = new ZdsbStateBO();
		ZdsbBO bo1 = new ZdsbBO();
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			List<ZdsbBO> queryByBO = ucc.queryByBO(bo);
			if (queryByBO != null && queryByBO.size() > 0) {
				bo1 = queryByBO.get(0);
			}
			if (!Tools.stringIsNull(sbzt)) {
				if ("1".equals(sbzt)) {
					stateBo.setZdsb_id(bo.getZdsb_id());
					stateBo.setZdsb_jxry(user.getName());
					stateBo.setZdsb_state(bo.getZdsb_sbzt());
					bo1.setZdsb_sbzt(bo.getZdsb_sbzt());
					ucc.editZdsb(bo1,user);
					ucc.editZdsbState(stateBo,user);
					flag = true;
				} else if ("2".equals(sbzt)) {
					stateBo.setZdsb_id(bo.getZdsb_id());
					stateBo.setZdsb_jxry(user.getName());
					stateBo.setZdsb_state(bo.getZdsb_sbzt());
					bo1.setZdsb_sbzt("");
					ucc.editZdsb(bo1,user);
					ucc.editZdsbState(stateBo,user);
					flag = true;
				}
			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 设置重点设备sis标示初始化
	public String editZdsbSisInit() {
		String zdsbid = Tools.filterNull(request.getParameter("zdsbid"));
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			ZdsbBO bo = new ZdsbBO();
			bo.setZdsb_id(zdsbid);
			List<ZdsbBO> list = ucc.queryByBO(bo);
			if (list != null && list.size() > 0) {
				ZdsbBO boc = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("zdsbid", zdsbid);
		return "editZdsbSisInit";
	}

	// 设备检修
	public String editZdsbJxrq() {
		String zdsbid = Tools.filterNull(request.getParameter("zdsbid"));
		String jxrq = Tools.filterNull(request.getParameter("jxrq"));

		request.setAttribute("zdsbid", zdsbid);
		request.setAttribute("jxrq", jxrq);
		return "editZdsbJxrq";
	}

	// 更新重点设备(保存或更新检修日期)
	public void saveZdsbJxrq() throws IOException {
		boolean flag = false;
		String zdsbid = Tools.filterNull(request.getParameter("zdsbid"));
		String jxrq = Tools.filterNull(request.getParameter("jxrq"));
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			ZdsbBO bo1 = new ZdsbBO();
			bo1.setZdsb_id(zdsbid);
			List<ZdsbBO> queryByBO = ucc.queryByBO(bo1);
			if (queryByBO != null && queryByBO.size() > 0) {
				bo = queryByBO.get(0);
				bo.setZdsb_jxrq(jxrq);
				ucc.editZdsb(bo,user);
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 查询重点设备sis标示
	public String queryZdsbSis() {
		String zdsbid = request.getParameter("zdsbid");
		query = EasyUiUtils.formatGridQuery(request, query);
		query.addUserWhere(" and ZDSB_ID = '" + zdsbid + "'");
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			DataResult search = ucc.queryZdsbSis(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 编辑重点设备标示
	public void editZdsbSis() throws IOException {
		boolean flag = false;
		String jsondata = request.getParameter("jsondata");
		String zdsbid = request.getParameter("zdsbid");
		JSONArray jsonDataArray = JSONArray.fromObject(jsondata);
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			for (int i = 0; i < jsonDataArray.size(); i++) {
				JSONObject obj = jsonDataArray.getJSONObject(i);
				ZdsbSisBO bo = new ZdsbSisBO();
				bo.setZdsb_id(zdsbid);
				bo.setZdsbsis_order(obj.getInt("ZDSBSIS_ORDER"));// 排序
				bo.setZdsbsis_bs(obj.getString("ZDSBSIS_BS"));// 设备标识
				bo.setZdsbsis_mc(obj.getString("ZDSBSIS_MC"));// 标识名称
				bo.setZdsbsis_id(obj.getString("ZDSBSIS_ID"));
				ucc.editZdsbSis(bo,user);
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 删除重点设备标示
	public void deleteZdsbSis() throws IOException {
		boolean flag = false;
		String jsondata = request.getParameter("jsondata");
		JSONArray jsonDataArray = JSONArray.fromObject(jsondata);
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			for (int i = 0; i < jsonDataArray.size(); i++) {
				JSONObject obj = jsonDataArray.getJSONObject(i);
				ZdsbSisBO bo = new ZdsbSisBO();
				bo.setZdsbsis_id(obj.getString("ZDSBSIS_ID"));
				ucc.deleteZdsbSis(bo,user);
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 查询备件清单
	public String queryBjqdSon() {
		query = EasyUiUtils.formatGridQuery(request, query);
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			DataResult search = ucc.queryBjqdList(query).search();
			search.runDictionary(FarmManager.instance().findDicTitleForIndex("COMPANY"), "BJQD_JGMC");
			search.runDictionary(FarmManager.instance().findDicTitleForIndex("BJZL"), "BJQD_BJZL");
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 更新备件清单初始化
	public String editBjqdInit() {
		String bjqdid = Tools.filterNull(request.getParameter("bjqdid"));
		if (!bjqdid.equals("")) {
			try {
				IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
				BjqdBO bo1 = new BjqdBO();
				bo1.setId(bjqdid);
				List<BjqdBO> queryByBO = ucc.queryBjqdByBO(bo1);
				if (queryByBO != null && queryByBO.size() > 0) {
					bjBo = queryByBO.get(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "editBjqdInit";
	}

	// 更新备件清单
	public void bjqdEdit() throws IOException {
		boolean flag = false;
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			ucc.editBjqd(bjBo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 删除备件清单
	public void deleteBjqd() throws IOException {
		boolean flag = false;
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			ucc.deleteBjqd(bjBo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	// 导出重点设备
	public String zdsbExport2() {
		query = EasyUiUtils.formatGridQuery(request, query);
		try {
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			String zdsb_ssbm = request.getParameter("zdsb_ssbm");
			String zdsb_sbmc = request.getParameter("zdsb_sbmc");
			query.addUserWhere(" and ZDSB_SSBM like '%" + zdsb_ssbm + "%'");
			query.addUserWhere(" and ZDSB_SBMC like '%" + zdsb_sbmc + "%'");
			DataResult search = ucc.queryList(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
			resultList = search.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 导出重点设备
	 * 
	 * @return
	 * @throws BkmsException
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 */
	public void zdsbExport() throws BkmsException, SQLException, UnsupportedEncodingException {
			query = EasyUiUtils.formatGridQuery(request, query);
			String ssbm = Tools.filterNull(request.getParameter("ssbm"));
			String sbmc = Tools.filterNull(request.getParameter("sbmc"));
			if(!Tools.stringIsNull(ssbm)){
					ssbm = URLDecoder.decode(ssbm, "UTF-8");
					query.addUserWhere(" and ZDSB_SSBM='"+ssbm+"' ");
				}
			if(!Tools.stringIsNull(sbmc)){
					sbmc = URLDecoder.decode(sbmc, "UTF-8");
					query.addUserWhere(" and ZDSB_SBMC like '%"+sbmc+"%' ");
				}
			query.setPagesize(1000);
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			DataResult search = ucc.queryList(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
			resultList = search.getResultList();
			String tableName = ""+ssbm+"" +  "重点设备表.xls";//excel名
			String titleName = ""+ssbm+"" +  "重点设备表";//title名
			// ------------------------开始导出
			try {		
				OutputStream os = httpResponse.getOutputStream();
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
				sheet.setColumnView(3, 20);
				// 标题格式
				WritableFont BoldFont = new WritableFont(WritableFont.ARIAL,
						10, WritableFont.BOLD);
				WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
				wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
				// 大标题格式
				WritableFont DBoldFont = new WritableFont(WritableFont.ARIAL,
						15, WritableFont.BOLD);
				WritableCellFormat Dwcf_center = new WritableCellFormat(
						DBoldFont);
				Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
				// 居中
				WritableCellFormat zwcf_center = new WritableCellFormat();
				zwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
				sheet.mergeCells(0, 0, 3, 0);
				sheet.addCell(new Label(0, 0, "" + titleName + "",
						Dwcf_center));
				sheet.addCell(new Label(0, 1, "所属部门",wcf_center));
				sheet.addCell(new Label(1, 1, "设备名称",wcf_center));
				sheet.addCell(new Label(2, 1, "状态",wcf_center));
				sheet.addCell(new Label(3, 1, "检修日期",wcf_center));
				if (resultList != null && resultList.size() > 0) {
					for (int i = 0; i < resultList.size(); i++) {
						Map<String, Object> mapzb = resultList.get(i);
						sheet.addCell(new Label(0,i+2,Tools.filterNull(mapzb.get("ZDSB_SSBM"))));//所属部门
						sheet.addCell(new Label(1,i+2,Tools.filterNull(mapzb.get("ZDSB_SBMC"))));//设备名称
						sheet.addCell(new Label(2,i+2,Tools.filterNull(mapzb.get("ZDSB_SBZT"))));//设备状态
						sheet.addCell(new Label(3,i+2,Tools.filterNull(mapzb.get("ZDSB_JXRQ"))));//检修日期					
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
	 * 导出备件清单
	 * 
	 * @return
	 * @throws BkmsException
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 */
	public void bjqdExport() throws BkmsException, SQLException, UnsupportedEncodingException {
		query = EasyUiUtils.formatGridQuery(request, query);
		query.setPagesize(1000);
		IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
		DataResult search = ucc.queryBjqdList(query).search();
		search.runDictionary(FarmManager.instance().findDicTitleForIndex("COMPANY"), "BJQD_JGMC");
		search.runDictionary(FarmManager.instance().findDicTitleForIndex("BJZL"), "BJQD_BJZL");
		jsonResult = EasyUiUtils.formatGridData(search);
		resultList = search.getResultList();
		String bjqd_jgmc_name = Tools.filterNull(request.getParameter("bjqd_jgmc_name"));
		if(!Tools.stringIsNull(bjqd_jgmc_name)){
			bjqd_jgmc_name = URLDecoder.decode(bjqd_jgmc_name, "UTF-8");
		}
		String tableName = ""+bjqd_jgmc_name+"" +  "备件清单表.xls";//excel名
		String titleName = ""+bjqd_jgmc_name+"" +  "备件清单表";//title名
			// ------------------------开始导出
			try {		
				OutputStream os = httpResponse.getOutputStream();
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
				sheet.setColumnView(0, 15);
				sheet.setColumnView(1, 15);
				sheet.setColumnView(2, 15);
				sheet.setColumnView(3, 15);
				sheet.setColumnView(4, 25);
				sheet.setColumnView(5, 15);
				sheet.setColumnView(6, 15);
				sheet.setColumnView(7, 15);
				// 标题格式
				WritableFont BoldFont = new WritableFont(WritableFont.ARIAL,
						10, WritableFont.BOLD);
				WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
				wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
				// 大标题格式
				WritableFont DBoldFont = new WritableFont(WritableFont.ARIAL,
						15, WritableFont.BOLD);
				WritableCellFormat Dwcf_center = new WritableCellFormat(
						DBoldFont);
				Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
				// 居中
				WritableCellFormat zwcf_center = new WritableCellFormat();
				zwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
				sheet.mergeCells(0, 0, 7, 0);
				sheet.addCell(new Label(0, 0, "" + titleName + "",
						Dwcf_center));
				sheet.addCell(new Label(0, 1, "机构名称",wcf_center));
				sheet.addCell(new Label(1, 1, "单元机组",wcf_center));
				sheet.addCell(new Label(2, 1, "备件名称",wcf_center));
				sheet.addCell(new Label(3, 1, "规格图号",wcf_center));
				sheet.addCell(new Label(4, 1, "备件种类（机、电、仪）",wcf_center));
				sheet.addCell(new Label(5, 1, "应储量（套/件）",wcf_center));
				sheet.addCell(new Label(6, 1, "实储量（套/件）",wcf_center));
				sheet.addCell(new Label(7, 1, "备注",wcf_center));
				if (resultList != null && resultList.size() > 0) {
					for (int i = 0; i < resultList.size(); i++) {
						Map<String, Object> mapzb = resultList.get(i);
						sheet.addCell(new Label(0,i+2,Tools.filterNull(mapzb.get("BJQD_JGMC"))));//机构名称
						sheet.addCell(new Label(1,i+2,Tools.filterNull(mapzb.get("BJQD_DYJZ"))));//单元机组
						sheet.addCell(new Label(2,i+2,Tools.filterNull(mapzb.get("BJQD_BJMC"))));//备件名称
						sheet.addCell(new Label(3,i+2,Tools.filterNull(mapzb.get("BJQD_GGTH"))));//规格图号
						sheet.addCell(new Label(4,i+2,Tools.filterNull(mapzb.get("BJQD_BJZL"))));//备件种类（机、电、仪）
						sheet.addCell(new Label(5,i+2,Tools.filterNull(mapzb.get("BJQD_YCL"))));//应储量（套/件）
						sheet.addCell(new Label(6,i+2,Tools.filterNull(mapzb.get("BJQD_SCL"))));//实储量（套/件）
						sheet.addCell(new Label(7,i+2,Tools.filterNull(mapzb.get("REMARK"))));//备注
					}
				}
				workbook.write();
				workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
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

	public ZdsbBO getBo() {
		return bo;
	}

	public void setBo(ZdsbBO bo) {
		this.bo = bo;
	}

	public BjqdBO getBjBo() {
		return bjBo;
	}

	public void setBjBo(BjqdBO bjBo) {
		this.bjBo = bjBo;
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
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
