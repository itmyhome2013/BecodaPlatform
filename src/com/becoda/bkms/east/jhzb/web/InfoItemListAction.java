package com.becoda.bkms.east.jhzb.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.jhzb.pojo.InfoTtemQueryVO;
import com.becoda.bkms.east.tjbb.ucc.ITjbbUCC;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.InfoItemVO;
import com.becoda.bkms.sys.ucc.IInfoItemUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.farm.core.report.ReportException;
import com.farm.core.report.ReportManagerInter;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.utils.JsonUtil;
import com.farm.core.util.FarmManager;

/**
 * Created by IntelliJ IDEA. User: kangdw Date: 2015-3-6 Time: 10:59:50 To
 * change this template use File | Settings | File Templates.
 */
public class InfoItemListAction extends GenericAction {
	private InfoItemVO form1;
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private File file; // 文件
	private String fileFileName; // 文件名
	private String filePath; // 文件路径
	private String rows;// 每页显示的记录数
	private String page;// 当前页数
	private InputStream inputStream;
	private String filename;
	private ReportManagerInter reportUCC;
	public InfoItemListAction(){
		try {
			reportUCC = (ReportManagerInter) BkmsContext.getBean("excelReportId");
		} catch (BkmsException e) {
			e.printStackTrace();
		}
	}
	public InfoItemVO getForm1() {
		return form1;
	}

	public void setForm1(InfoItemVO form1) {
		this.form1 = form1;
	}

	public String openItem() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
		try {
			String[] itemId = request.getParameterValues("chk");
			// InfoItemVO form1 = (InfoItemVO) form;
			String setId = request.getParameter("setId");
			if (form1 == null) {
				form1 = new InfoItemVO();
				form1.setSetId(setId);
			}
			IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
					.getBean("sys_infoItemUCC");
			ucc.makeStatus(form1.getSetId(), itemId,
					SysConstants.INFO_STATUS_OPEN);
			// 同步缓存cache
			List list = new ArrayList();
			for (int i = 0; i < itemId.length; i++) {
				InfoItemBO item = SysCacheTool.findInfoItem(form1.getSetId(),
						itemId[i]);
				item.setItemStatus(SysConstants.INFO_STATUS_OPEN);
				list.add(item);
			}
			SysCache.setMap(list, CacheConstants.OPER_UPDATE,
					CacheConstants.OBJ_INFOITEM);
			getItemList(ucc, form1, hrequest, user);
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
		return "success";
	}

	public String banItem() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
		try {
			// 禁用
			String[] itemId = request.getParameterValues("chk");
			// InfoItemVO form1 = (InfoItemVO) form;
			String setId = request.getParameter("setId");
			if (form1 == null) {
				form1 = new InfoItemVO();
				form1.setSetId(setId);
			}
			IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
					.getBean("sys_infoItemUCC");
			ucc.makeStatus(form1.getSetId(), itemId,
					SysConstants.INFO_STATUS_BAN);
			// 同步缓存cache
			List list = new ArrayList();
			for (int i = 0; i < itemId.length; i++) {
				InfoItemBO item = SysCacheTool.findInfoItem(form1.getSetId(),
						itemId[i]);
				item.setItemStatus(SysConstants.INFO_STATUS_BAN);
				list.add(item);
			}
			SysCache.setMap(list, CacheConstants.OPER_UPDATE,
					CacheConstants.OBJ_INFOITEM);
			getItemList(ucc, form1, hrequest, user);
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
		return "success";
	}

	public String delItem() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
		try {
			String[] itemId = request.getParameterValues("chk");
			IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
					.getBean("sys_infoItemUCC");
			// InfoItemVO form1 = (InfoItemVO) form;
			String setId = request.getParameter("setId");
			if (form1 == null) {
				form1 = new InfoItemVO();
				form1.setSetId(setId);
			}
			ucc.deleteInfoItems(form1.getSetId(), itemId, user.getUserId());

			// 内存同步
			// SysCache.setMap(itemId, CacheConstants.OPER_DELETE,
			// CacheConstants.OBJ_INFOITEM);
			// 同步内存权限
			Hashtable hash = user.getPmsInfoSet();
			for (int i = 0; i < itemId.length; i++) {
				hash.remove(itemId[i]);
			}
			getItemList(ucc, form1, hrequest, user);
			this.showMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
		return "success";
	}

	public String listItem() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
		try {
			IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
					.getBean("sys_infoItemUCC");
			// InfoItemVO form1 = (InfoItemVO) form;
			getItemList(ucc, form1, hrequest, user);
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
		return "success";
	}

	public void getItemList(IInfoItemUCC ucc, InfoItemVO form1,
			BkmsHttpRequest request, User user) throws BkmsException {
		if (form1 == null) {
			form1 = new InfoItemVO();
			form1.setSetId(request.getParameter("setId"));
		}
		if (form1.getSetId() != null) {
			List list = ucc.queryRightItemlist(user, form1.getSetId());
			request.setAttribute("setId", form1.getSetId());
			request.setAttribute("list", list);
		}
	}

	public void addOneNullMsg() throws IOException {		
		Map<String, String> fields = new HashMap<String, String>();
		PageVO pageVo = new PageVO();
		String setId = request.getParameter("setId");// 表名
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		try {
			List list = SysCacheTool.queryInfoItemBySuperId(setId);
			if (list != null && list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					InfoItemBO item = (InfoItemBO) list.get(i);
					String id = item.getItemId();
					fields.put(id, "");
				}
			}
			list2.add(fields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonUtil.printEasyuiJson(JsonUtil.toEasyuiJson(list2, pageVo), httpResponse);
		
	}

	/**
	 * 跳转到详情页面
	 * 
	 * @return
	 * @throws BkmsException
	 */
	public String listPlanItem() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String setId=Tools.filterNull(request.getParameter("setId"));	
			String sheetType=Tools.filterNull(request.getParameter("sheetType"));
			String desc=Tools.filterNull(request.getParameter("desc"));//年度/月份
			String tableName=null;
			if(Tools.stringIsNull(setId)){
				List list = SysCacheTool.queryCodeItemBySetIdAndSuperId(Constants.INFO_SET_TYPE_CODEID, "-1");
				  if (list != null && list.size() != 0) {
				 	for(int i=0;i<list.size();i++){
				 		CodeItemBO item = (CodeItemBO) list.get(i);
				 		String itemAbbr=item.getItemAbbr();
				 		if(itemAbbr.equals(sheetType)){
				 		String name=item.getItemName();
				 		String itemId=item.getItemId();
				 		String val=itemId+"&setName="+name;
		                 List list2 = SysCacheTool.queryInfoSetBySuperId(itemId);
		                 if(list2 != null && list2.size() != 0){
		                	    InfoSetBO item2 = (InfoSetBO) list2.get(0);
		                	    setId=item2.getSetId();	
		                	    desc=item2.getSetDesc();
		                	    tableName=item2.getSetName();
		                 }
		                 }
				 		}
		                 }
			}
			request.setAttribute("setId",setId);
			request.setAttribute("desc",desc);
			request.setAttribute("itemAbbr", request.getParameter("itemAbbr"));
			request.setAttribute("tableName", tableName);
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
		return "success";
	}

	/**
	 * 根据表名查询指标表信息
	 * 
	 * @throws BkmsException
	 */
	public void queryItemAll() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
				.getBean("sys_infoItemUCC");
		try {
			String setId = request.getParameter("setId");// 表名
			String desc = request.getParameter("desc");// 指标下达类型（1：年度2：月度）
			String type = request.getParameter("type");
			if (setId != null && !"null".equals(setId) && !"".equals(setId)) {
				Map<String, String> fields = new HashMap<String, String>();
				PageVO pageVo = new PageVO();
				// if (!Tools.stringIsNull(rows)) {
				// pageVo.setCurrentPage(Integer.parseInt(page));
				// pageVo.setPageSize(Integer.parseInt(rows));
				// } else {
				// pageVo.setPageSize(10);
				// pageVo.setCurrentPage(1);
				// }
				String indexDate = null;
				if ("2".equals(desc)) {
					indexDate = Tools.getSysDate("yyyy-MM");
				} else {
					indexDate = Tools.getSysYear();
				}
				InfoTtemQueryVO queryVO = new InfoTtemQueryVO();
				queryVO.setSetId(setId);
				queryVO.setReportDate(indexDate);
				List resultList = ucc.findSheetInfoByNameAndYear(pageVo,
						queryVO);
				JsonUtil.printEasyuiJson(
						JsonUtil.toEasyuiJson(resultList, pageVo), httpResponse);

			}
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
	}

	/**
	 * 根据表名、日期、项目、单位查询指标表信息
	 * 
	 * @throws BkmsException
	 */
	public void queryItemMsgAll() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
		try {
			String setId = Tools.filterNull(request.getParameter("setId"));// 表名
			String desc = Tools.filterNull(request.getParameter("desc"));// 计划类别（1:年度/2:月度）
			String reportDate = Tools.filterNull(request.getParameter("reportDate"));// 日期
			String search_project = Tools.filterNull(request.getParameter("search_project"));// 项目
			search_project = URLDecoder.decode(search_project, "UTF-8");
			String search_unit = Tools.filterNull(request.getParameter("search_unit"));// 单位
			search_unit = URLDecoder.decode(search_unit, "UTF-8");
			String msgDesc="xm";
			if (Tools.stringIsNull(reportDate)) {
				if ("2".equals(desc)) {
					reportDate = Tools.getSysDate("yyyy-MM");
				} else if ("1".equals(desc)) {
					reportDate = Tools.getSysYear();
				}
			}
			if (setId != null && !"null".equals(setId) && !"".equals(setId)) {
				Map<String, String> fields = new HashMap<String, String>();

				PageVO pageVo = new PageVO();
				// if (!Tools.stringIsNull(rows)) {
				// pageVo.setCurrentPage(Integer.parseInt(page));
				// pageVo.setPageSize(Integer.parseInt(rows));
				// } else {
				// pageVo.setPageSize(10);
				// pageVo.setCurrentPage(1);
				// }
				InfoTtemQueryVO queryVO = new InfoTtemQueryVO();
				queryVO.setSetId(setId);
				queryVO.setReportDate(reportDate);
				queryVO.setSearch_project(search_project);
				//查询项目中文名称（树结构包含父节点）
				/*if(!Tools.stringIsNull(search_project)){
					List<Map<String, Object>> listForSjzd = FarmManager.instance().findRuleItemOption(search_project,msgDesc);
					ArrayList<String> nameList = new ArrayList<String>();
					if(listForSjzd != null && listForSjzd.size() > 0){	
						for (int i = 0; i < listForSjzd.size(); i++) {
							Map<String, Object> type = (Map<String, Object>) listForSjzd.get(i);
							nameList.add((String) type.get("NAME"));
					    }																		
					  }
					if(nameList != null && nameList.size() > 0){
					String[] array = new String[nameList.size()];
				    // List转换成数组
				    for (int j = 0; j < nameList.size(); j++) {
				        array[j] = nameList.get(j);
				    }
				    queryVO.setProjectArray(array);
				    }
				}	*/			
				queryVO.setSearch_unit(search_unit);
				List resultList = ucc.findSheetInfoByNameAndYear(pageVo,queryVO);
				JsonUtil.printEasyuiJson(JsonUtil.toEasyuiJson(resultList, pageVo), httpResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
	}

	/**
	 * 根据表名、日期、项目、单位导出指标表信息
	 * 
	 * @throws BkmsException
	 */
	public void exportItemMsg() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
				.getBean("sys_infoItemUCC");
		try {
			String setId = Tools.filterNull(request.getParameter("setId"));// 表名
			String desc = Tools.filterNull(request.getParameter("desc"));// 计划类别（1:年度/2:月度）
			String tableItemName = Tools.filterNull(request
					.getParameter("tableName"));// 表中文名
			String reportDate = Tools.filterNull(request
					.getParameter("reportDate"));// 日期
			String search_project = Tools.filterNull(request
					.getParameter("search_project"));// 项目
			search_project = URLDecoder.decode(search_project, "UTF-8");
			String search_unit = Tools.filterNull(request
					.getParameter("search_unit"));// 单位
			search_unit = URLDecoder.decode(search_unit, "UTF-8");
			String search_unit_text = Tools.filterNull(request
					.getParameter("search_unit_text"));// 机构名称中文名
			search_unit = URLDecoder.decode(search_unit, "UTF-8");
			String xmDesc="xm";
			if (setId != null && !"null".equals(setId) && !"".equals(setId)) {
				Map<String, String> fields = new HashMap<String, String>();

				PageVO pageVo = new PageVO();
				// if (!Tools.stringIsNull(rows)) {
				// pageVo.setCurrentPage(Integer.parseInt(page));
				// pageVo.setPageSize(Integer.parseInt(rows));
				// } else {
				// pageVo.setPageSize(10);
				// pageVo.setCurrentPage(1);
				// }
				InfoTtemQueryVO queryVO = new InfoTtemQueryVO();
				queryVO.setSetId(setId);
				if(Tools.stringIsNull(reportDate)){
					if ("2".equals(desc)) {
						reportDate = Tools.getSysDate("yyyy-MM");
					} else if ("1".equals(desc)) {
						reportDate = Tools.getSysYear();
					}
				}
				queryVO.setReportDate(reportDate);
//				queryVO.setSearch_project(search_project);
				//查询项目中文名称（树结构包含父节点）
				if(!Tools.stringIsNull(search_project)){
					List<Map<String, Object>> listForSjzd = FarmManager.instance().findRuleItemOption(search_project,xmDesc);
					ArrayList<String> nameList = new ArrayList<String>();
					if(listForSjzd != null && listForSjzd.size() > 0){	
						for (int i = 0; i < listForSjzd.size(); i++) {
							Map<String, Object> type = (Map<String, Object>) listForSjzd.get(i);
							nameList.add((String) type.get("NAME"));
					    }																		
					  }
					if(nameList != null && nameList.size() > 0){
					String[] array = new String[nameList.size()];
				    // List转换成数组
				    for (int j = 0; j < nameList.size(); j++) {
				        array[j] = nameList.get(j);
				    }
				    queryVO.setProjectArray(array);
				    }
				}
				queryVO.setSearch_unit(search_unit);

				String tableName = "" + reportDate + ""+ ""+search_unit_text+"" + tableItemName + "表.xls";//excel名
				String titleName = "" + reportDate + ""+ ""+search_unit_text+"" + tableItemName + "表";//excel Title名
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
				if (setId != null) {
					List listForSheetName = SysCacheTool
							.queryInfoItemBySuperId(setId);// 字段名
					List listForItemMsg = ucc.findSheetInfoByNameAndYear(
							pageVo, queryVO);// 数据
					sheet.mergeCells(0, 0, listForSheetName.size() - 4, 0);
					sheet.addCell(new Label(0, 0, "" + titleName + "",
							Dwcf_center));
					
					CellView cellView = new CellView();  
					cellView.setSize(4000);
				       
				       
					if (listForItemMsg != null && listForItemMsg.size() != 0) {
						for (int i = 0; i < listForItemMsg.size(); i++) {
							Map<String, String> itemMsgMap = (Map<String, String>) listForItemMsg
									.get(i);
							if (listForSheetName != null
									&& listForSheetName.size() != 0) {
								for (int j = 0; j < listForSheetName.size(); j++) {
									InfoItemBO item = (InfoItemBO) listForSheetName
											.get(j);
									String id = item.getItemId();
									String sheetItemName = item.getItemName();
									String msgDesc=item.getItemDesc();
									
									if("工作令号/费用（万元）".equals(sheetItemName)){
										sheetItemName = "工作令号";
									}
									
									System.out.println("sheetItemName: " + sheetItemName + ", msgDesc: " + msgDesc);
									
									if (!"SUBID".equals(id) && !"ID".equals(id)
											&& sheetItemName != null
											&& !"null".equals(sheetItemName)
											) {
										
										//
										 sheet.setColumnView(j, cellView);//根据内容自动设置列宽  
										//
										
										sheet.addCell(new Label(j - 2, 1, ""
												+ sheetItemName + "",
												wcf_center));
										String sheetMsg = Tools
												.filterNull(itemMsgMap.get(id));
										if(!Tools.stringIsNull(sheetMsg)  && !Tools.stringIsNull(msgDesc) ){
											if(!"undefined".equals(sheetMsg) && ("jgmc".equals(msgDesc) || "jldw".equals(msgDesc))){
												List<Map<String, Object>> listForSjzd = FarmManager.instance().findRuleItemOption(sheetMsg,msgDesc);
												if(listForSjzd != null && listForSjzd.size() == 1){										  
															Map<String, Object> type = (Map<String, Object>) listForSjzd.get(0);
															sheetMsg=(String) type.get("NAME");											
												  }
											}else if("undefined".equals(sheetMsg)){
												sheetMsg="";
											}		
										}
										sheet.addCell(new Label(j - 2, 2 + i,
												"" + sheetMsg + "", wcf_center));
									}
								}
							}
						}
					} else {
						if (listForSheetName != null
								&& listForSheetName.size() != 0) {
							for (int j = 0; j < listForSheetName.size(); j++) {
								InfoItemBO item = (InfoItemBO) listForSheetName
										.get(j);
								String id = item.getItemId();
								String sheetItemName = item.getItemName();
								if (!"SUBID".equals(id) && !"ID".equals(id)
										&& sheetItemName != null
										&& !"null".equals(sheetItemName)) {
									sheet.addCell(new Label(j - 2, 1, ""
											+ sheetItemName + "", wcf_center));
								}
							}
						}
					}
				}
				workbook.write();
				workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.showMessage("错误:" + e.getMessage() + e.toString());
		}
	}

	/**
	 * 更新指标下达状态为已下达
	 */
	public void updateIndexStatus() throws BkmsException, IOException {
		IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
				.getBean("sys_infoItemUCC");
		boolean flag = false;
		String setId = request.getParameter("setId");// 表名
		String stateIds = request.getParameter("stateIds");// 关联状态表id
		String state = request.getParameter("state");// 指标日期
		String ids = request.getParameter("ids");// 指标id
		try {
			ucc.updateIndexStatus(ids, stateIds, state, setId);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonUtil.printEasyuiJson(JsonUtil.toJson(flag), httpResponse);
	}

	/**
	 * 保存或更新指标表数据
	 * 
	 * @throws BkmsException
	 * @throws IOException
	 */
	public void saveInfo() throws BkmsException, IOException {
		IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
				.getBean("sys_infoItemUCC");
		boolean flag = false;
		String setId = request.getParameter("setId");// 表名
		String fieldName = setId + "201";// 日期字段名
		String projectFieldName = setId + "202";// 项目字段名
		String companyFieldName = setId + "203";// 机构名称字段名
		String dateValue = request.getParameter("dateValue");// 指标日期
		String projectValue = request.getParameter("projectValue");// 项目
		String companyValue = request.getParameter("companyValue");// 机构名称
		String jldwValue = request.getParameter("jldwValue");// 计量单位
		String[] dateValues = dateValue.split(",");
		String[] projectValues=projectValue.split(",");
		String[] companyValues=companyValue.split(",");
		String[] jldwValues=jldwValue.split(",");
		String json = request.getParameter("jsondata");
		if (setId != null && !"null".equals(setId)) {
			try {
				List list = SysCacheTool.queryInfoItemBySuperId(setId);
				JSONArray jsonDataArray = JSONArray.fromObject(json);
				if (list != null && list.size() != 0
						&& jsonDataArray.size() > 0) {
					for (int j = 0; j < jsonDataArray.size(); j++) {
						StringBuffer field = new StringBuffer();
						StringBuffer values = new StringBuffer();
						StringBuffer sql = new StringBuffer();
						String infoId = null;
						JSONObject job = jsonDataArray.getJSONObject(j);		
						for (int i = 0; i < list.size(); i++) {
							InfoItemBO item = (InfoItemBO) list.get(i);
							String id = item.getItemId();
							String desc =item.getItemDesc();
							if(desc!=null && desc.equals("ndyf") && dateValues!=null && dateValues.length != 0 ){
								if(j < dateValues.length){
								job.remove(id);
								job.put(id, Tools.filterNull(dateValues[j]));
								}
							}else if(desc!=null && desc.equals("xm") && projectValues!=null && projectValues.length != 0 ){
								if(j < projectValues.length){
									job.remove(id);
									job.put(id, Tools.filterNull(projectValues[j]));
								}							
							}else if(desc!=null && desc.equals("jgmc") && companyValues!=null && companyValues.length != 0){
								if(j < companyValues.length){
								job.remove(id);
								job.put(id, Tools.filterNull(companyValues[j]));
								}
							}else if(desc!=null && desc.equals("jldw") && jldwValues!=null && jldwValues.length != 0){
								if(j < jldwValues.length){
								job.remove(id);
								job.put(id, Tools.filterNull(jldwValues[j]));
								}
							}
							if (i == 0) {
								if (job.get(id) != null
										&& !"".equals(job.get(id))) {
									infoId = (String) job.get(id);
								}
							}
							// 判断是执行更新操作还是插入操作
							if (infoId != null && !"".equals(infoId)) {
								if (i == list.size() - 1) {
									sql.append(id + "='"
											+ Tools.filterNull(job.get(id))
											+ "'");
									ucc.addOrUpdateInfo(dateValues[j], setId,
											infoId, field.toString(),
											values.toString(), sql.toString(),user);
									flag = true;
								} else if (i > 0) {
									sql.append(id + "='"
											+ Tools.filterNull(job.get(id))
											+ "',");
								}
							} else {
								if (i == list.size() - 1) {
									field.append(id);
									if (job.get(id) != null
											&& !"".equals(job.get(id))) {
										values.append("'" + job.get(id) + "'");
									} else {
										values.append(null + "");
									}
									ucc.addOrUpdateInfo(dateValues[j], setId,
											infoId, field.toString(),
											values.toString(), sql.toString(),user);
									flag = true;
								} else {
									if (i == 0) {
										field.append(id + ",");
										values.append("'"
												+ SequenceGenerator.getUUID()
												+ "'" + ",");
									} else {
										field.append(id + ",");
										if (job.get(id) != null
												&& !"".equals(job.get(id))) {
											values.append("'" + job.get(id)
													+ "',");
										} else {
											values.append(null + ",");
										}

									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JsonUtil.printEasyuiJson(JsonUtil.toJson(flag), httpResponse);
	}

	/**
	 * 根据表名和数据id删除指标数据
	 * 
	 * @throws BkmsException
	 * @throws IOException
	 */
	public void deleteInfo() throws BkmsException, IOException {
		IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
				.getBean("sys_infoItemUCC");
		boolean flag = false;
		// 表名
		String name = request.getParameter("setId");
		String ids = request.getParameter("ids");
		try {
			ucc.deleteInfoByNameAndId(name, ids,user);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.printEasyuiJson(JsonUtil.toJson(flag), httpResponse);
	}

	/**
	 * excel导入
	 * 
	 * @return
	 * @throws BkmsException
	 * @throws IOException
	 */
	public String saveUpload() throws BkmsException, IOException {
		Boolean flag = false;
		IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext
				.getBean("sys_infoItemUCC");
		String setId = Tools.filterNull(request.getParameter("setId"));
		try {
			if (this.file != null) {
				File file = this.getFile();
				String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
				String name = fileName
						+ fileFileName.substring(fileFileName.lastIndexOf(".")); // 保存在硬盘中的文件名

				String suffix = fileFileName.substring(fileFileName
						.lastIndexOf(".") + 1);
				if (!"xls".equals(suffix) && !"XLS".equals(suffix)) {
					flag = false;
				} else {
					List list = SysCacheTool.queryInfoItemBySetID(setId);
					if (list != null && list.size() != 0) {
						StringBuffer errorInfo = readExcelFile(file, setId, 0,
								list, ucc, 2, list.size() - 3,user);
						flag = true;
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 
	 * @param file
	 *            excel文件
	 * @param keyRow
	 * @param lpList
	 * @param startRow
	 *            开始读取的行数
	 * @param needCols
	 *            导入excel的列数
	 * @return
	 * @throws BkmsException
	 */
	public static StringBuffer readExcelFile(File file, String sheetName,
			int keyRow, List list, IInfoItemUCC ucc, int startRow, int needCols,User user)
			throws BkmsException {
		StringBuffer errorInfo = new StringBuffer("");
		try {
			Workbook wbook = null;
			wbook = Workbook.getWorkbook(file);
			// 获取第一张Sheet表
			Sheet sheet = wbook.getSheet(0);
			int rows = sheet.getRows(); // sheet 行数
			if (rows == 0 || rows < keyRow + 1) {
				errorInfo.append("导入的文件内容为空" + "<br/>");
				return errorInfo;
			}
			int columns = sheet.getColumns(); // sheet 列数
			if (columns != needCols && (columns - 1) != needCols) {
				errorInfo.append("导入的excel文件的列数不等于规定的导入列数,必须为" + needCols
						+ "列数据" + "<br/>");
				return errorInfo;
			}
			// 正式开始
			for (int i = startRow; i < rows; i++) {// 循环行
				StringBuffer field = new StringBuffer();
				StringBuffer values = new StringBuffer();
				StringBuffer sql = new StringBuffer();
				String infoId = null;
				for (int m = 0; m < list.size(); m++) {// 循环列
					InfoItemBO item = (InfoItemBO) list.get(m);
					String id = item.getItemId();
					if (m == list.size() - 1) {
						field.append(id);
					} else {
						field.append(id + ",");
					}
				}
				for (int j = 0; j < list.size(); j++) {// 循环列
					String value = null;
					if (j > 2) {
						value = Tools.filterNull(sheet.getCell(j - 3, i)
								.getContents().trim());
						if(!Tools.stringIsNull(value)){
							if("正东".equals(value)){
								value="1";
							}else if("恒东热电".equals(value)){
								value="2";
							}else if("动力南厂".equals(value)){
								value="3";
							}else if("正东园区".equals(value)){
								value="4";
							}
						}
					} else {
						value = null;
					}
					if (j == list.size() - 1) {
						if (value != null && !"".equals(value)) {
							values.append("'" + value + "'");
						} else {
							values.append(null + "");
						}
					} else {
						if (j == 0) {
							values.append("'" + SequenceGenerator.getUUID()
									+ "'" + ",");
						} else {
							if (value != null && !"".equals(value)) {
								values.append("'" + value + "',");
							} else {
								values.append(null + ",");
							}

						}
					}

				}
				ucc.addOrUpdateInfo(null, sheetName, infoId, field.toString(),
						values.toString(), sql.toString(),user);
			}
		} catch (Exception e) {
			throw new BkmsException("读取Excel文件错误");
		}
		return errorInfo;
	}	
public void queryCompanyList() throws BkmsException, IOException{
	    String id = request.getParameter("id");//字段值
		String ruleItem=null;
		String  desc= request.getParameter("desc");//字段名称
		StringBuffer items = new StringBuffer();	
		List<Map<String, Object>> list = FarmManager.instance().findRuleItemOption(id,desc);
		  if(list != null && list.size() == 1){
			  for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map<String, String> type = (Map<String, String>) iterator.next();
					items.append(type.get("NAME"));	
				}
		  }else{
			  items.append("");	
		  }
				
		ruleItem = items.toString();
		JsonUtil.printEasyuiJson(JsonUtil.toJson(ruleItem), httpResponse);
}
public void queryCompanyListForEdit() throws BkmsException, IOException{
	String id = null;//字段值
	String ruleItem=null;
	StringBuffer items = new StringBuffer();	
	String  desc= request.getParameter("desc");//字段名称
	items.append("<option value='undefined'>请选择</option>");
	List<Map<String, Object>> list = FarmManager.instance().findRuleItemOption(id,desc);
	id = request.getParameter("id");//字段值
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> type = (Map<String, String>) iterator.next();
				if(!Tools.stringIsNull(id) && id.equals(type.get("MARK"))){
					items.append("<option value='" + type.get("MARK") + "' selected='selected'>" + type.get("NAME")+"</option>");
				}else{
					items.append("<option value='" + type.get("MARK") + "'>" + type.get("NAME")+"</option>");
				}	
			}
	ruleItem = items.toString();
	JsonUtil.printEasyuiJson(JsonUtil.toJson(ruleItem), httpResponse);
}
	/**
	 * 跳转至上传页面
	 */
	public String view() {
		return "success";
	}
	// 下载设备计划模板
		public String downloadJhzbTemplate() {
			try {
				//------------------------开始导出
				Map<String, Object> para = new HashMap<String, Object>();
				filename = "设备计划模板.xls";
				reportUCC.generate(filename, para);
				inputStream = new FileInputStream(new File(reportUCC.getReportPath(filename)));		
			} catch (ReportException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}			
			return "success";
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		try {
			filename =  new String(filename.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	

}
