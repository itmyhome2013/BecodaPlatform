package com.becoda.bkms.east.ssjc.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.east.ssjc.SsjcConstants;
import com.becoda.bkms.east.ssjc.pojo.NxjcBO;
import com.becoda.bkms.east.ssjc.pojo.ZbjcQueryCondition;
import com.becoda.bkms.east.ssjc.pojo.ZdTest2BO;
import com.becoda.bkms.east.ssjc.service.ZbjcService;
import com.becoda.bkms.east.ssjc.ucc.IZbjcUCC;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.east.ynsb.pojo.YqdbBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSbscBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbStateBO;
import com.becoda.bkms.east.zdsb.ucc.IZdsbUCC;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.ucc.ICodeUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.sql.utils.JsonUtil;
import com.farm.core.time.TimeTool;
import com.farm.core.util.FarmManager;

/**
 * 
 * <p>
 * Description:指标检测action
 * </p>
 * 
 * @author liu_hq
 * @date 2017-11-1
 * 
 */
public class ZbjcAction extends GenericPageAction {

	private ZbjcQueryCondition zbjcquerybo; // 指标检测查询条件
	private ZbjcService zbjcService;
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private YqdbBO yqdbbo; // 园区电表BO

	// 指标检测初始
	public String zbjcIndexInit() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			List<CodeItemBO> list = ucc.queryCodeJzZbjc(
					SsjcConstants.YNSB_SUPERID, "-1");
			List<CodeItemBO> listtype = new ArrayList<CodeItemBO>();
			CodeItemBO hdbo = new CodeItemBO();
			hdbo.setSetId("0");
			hdbo.setItemSpell("2");
			hdbo.setItemName("恒东热电");
			listtype.add(hdbo);
			CodeItemBO dnbo = new CodeItemBO();
			dnbo.setItemId("0");
			dnbo.setItemSpell("3");
			dnbo.setItemName("动力南厂");
			listtype.add(dnbo);
			request.setAttribute("list", list);
			request.setAttribute("listtype", listtype);
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		return "zbjcIndexInit";
	}

	// 恒东热电指标检测初始
	public String hdIndex() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			List<CodeItemBO> list = ucc.queryCode(SsjcConstants.YNSB_SUPERID,
					"-1", "2");
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hdIndex";
	}

	// 指标检测数据
	public String queryHd() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String fid = Tools.filterNull(request.getParameter("fid"));
			if (fid.equals("")) {
				fid = SsjcConstants.YNSB_HDRQZQGL;
			}
			List<Map<String, Object>> list = ucc.queryType("('6','11')", "", fid); // 能源种类
			String sj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			// String sj = "2017-11-28 15:45:00";
			Map<String, String> map = FarmManager.instance().findDicTitleForIndex("NYZL");
			if (list != null) {
				for (Map<String, Object> maptype : list) {
					if (maptype.get("YNSB_NYZL") != null) {
						String key = maptype.get("YNSB_NYZL").toString();
						Object value = map.get(key);
						if (value != null) {
							if ("''".equals(value)) {
								maptype.put("YNSB_NYZLNAME", "");
							} else {
								maptype.put("YNSB_NYZLNAME", value);
							}
						}
					}
				}
			}
			sj = Tools.minusMinute(sj, -2);// 时间减两分钟，查询上两分钟的，因需错开实时数据同步和计算累计时间
			String ljsj = sj; // 累计时间
			// 查询前一天23:45的记录
			if (Tools.compareTo(sj.substring(11, 16), "07:45", "HH:mm") == -1) {
				ljsj = Tools.modifiedHour(sj.substring(0, 10) + " 07:45", -8);
			} else if (Tools.compareTo(sj.substring(11, 16), "15:45", "HH:mm") == -1) {
				ljsj = sj.substring(0, 10) + " 07:45";
			} else if (Tools.compareTo(sj.substring(11, 16), "23:45", "HH:mm") == -1) {
				ljsj = sj.substring(0, 10) + " 15:45";
			}
			List<Map<String, Object>> sislist = ucc.querySis(fid); // sis
			List<Map<String, Object>> zbzlist = ucc.queryZbz(fid, sj);// 指标检测值
			List<Map<String, Object>> ljzlist = ucc.queryLjz(fid, ljsj); // 累计值
			request.setAttribute("ljzlist", ljzlist);
			
			//如果数值大于设定的值，重新赋值为0
			for(Map<String, Object> m : zbzlist){
				String nyzl = (String)m.get("YNSB_NYZL");
				if("7".equals(nyzl)){ //电（出）
					String sz = (String)m.get("ZBJC_SZ");
					String szs[] = sz.split(",");
					float f5 = Float.parseFloat(szs[5]); //数组第6个值是总值
					if(f5 > Float.parseFloat("999999999")){
						DecimalFormat df = new DecimalFormat("#.00");
						szs[5] = String.valueOf(df.format(f5 - Float.parseFloat("999999999")));
					}
					String val = "";
					for(int i=0;i<szs.length;i++){
						val += szs[i] + ",";
					}
					m.put("ZBJC_SZ", val);
				}
				
				//公式：瞬时热量 = 4.187 *（出口温度-入口温度）* 瞬时流量 /1000
				if("9".equals(nyzl)){ //热水（出）
					String sz = (String)m.get("ZBJC_SZ");
					String szs[] = sz.split(",");
					float f0 = Float.parseFloat(Tools.filterNullToZero(szs[0])); //入口温度
					float f1 = Float.parseFloat(Tools.filterNullToZero(szs[1])); //出口温度
					float f3 = Float.parseFloat(Tools.filterNullToZero(szs[3])); //瞬时流量
					float f4 = Float.parseFloat(Tools.filterNullToZero(String.valueOf(4.187 * (f1 - f0) * f3 / 1000))); //瞬时热量
					szs[4] = String.valueOf(Tools.filterNullToZero(f4));
					
					String val = "";
					for(int i=0;i<szs.length;i++){
						val += szs[i] + ",";
					}
					m.put("ZBJC_SZ", val);
				}
			}
			//
			
			request.setAttribute("zbzlist", zbzlist);
			request.setAttribute("sislist", sislist);
			request.setAttribute("typelist", list);
			request.setAttribute("sj", sj);
			request.setAttribute("fid", fid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryHd";
	}

	// 动力南厂指标检测初始
	public String dlIndex() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			List list = ucc.queryCode(SsjcConstants.YNSB_SUPERID, "-1", "3");
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dlIndex";
	}

	// 历史查询数据监测
	public String histSjIndex() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			List<CodeItemBO> list = ucc.queryCodeNyjc(
					SsjcConstants.YNSB_SUPERID, "-1");
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "histSjIndex";
	}

	// 历史指标初始
	public String queryHistoryZbjcInit() throws ParseException {
		if (zbjcquerybo == null) {
			zbjcquerybo = new ZbjcQueryCondition();
		}
		String currenttime = Tools.getSysDate("yyyy-MM-dd HH:mm:") + "00";
		// 开始时间
		if (zbjcquerybo.getStarttime() == null) {
			zbjcquerybo.setStarttime(Tools.modifiedHour(currenttime, -24)
					+ ":00");
		}
		// 结束时间
		if (zbjcquerybo.getEndtime() == null) {
			zbjcquerybo.setEndtime(currenttime);
		}
		// 机组id
		if (zbjcquerybo.getFid() == null) {
			zbjcquerybo.setFid(SsjcConstants.ITEMID_ZDYN);
		}
		return "queryHistoryZbjcInit";
	}

	// 根据fid查询设备生成下拉框
	public void querySbByfid() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String fid = request.getParameter("fid");
			List listsb = ucc.querySbByfid(fid);
			String jsonResult = JsonUtil.toJson(listsb);
			JsonUtil.printEasyuiJson(jsonResult, httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 历史指标检测
	public String queryHistoryZbjc() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			// 机组id
			if (zbjcquerybo.getFid() == null) {
				zbjcquerybo.setFid(SsjcConstants.ITEMID_ZDYN);
			}
			vo.setPageSize(20);
			List sislist = ucc.querySisBySb(zbjcquerybo.getSbid()); // sis

			//String startTime = zbjcquerybo.getStarttime();
			//String endTime = zbjcquerybo.getEndtime();
			List<String> days = TimeTool.getDays(zbjcquerybo.getStarttime().substring(0,10), zbjcquerybo.getEndtime().substring(0,10));
			String b1, b2, b3, b4, b5, b6, b7, b8;
			List<Map<String,String>> bljs = new ArrayList<Map<String,String>>();
			for (int i = 0; i < days.size(); i++) {
				
				// 夜班
				b2 = days.get(i) + " 07:45:00";
				b1 = Tools.modifiedHour(b2, -8) + ":00";

				// 白班
				b4 = days.get(i) + " 15:45:00";
				b3 = Tools.modifiedHour(b4, -8) + ":00";

				// 中班
				b6 = days.get(i) + " 23:45:00";
				b5 = Tools.modifiedHour(b6, -8) + ":00";
				
				//b7 = days.get(i) + " 23:45:00";
				//b8 = Tools.modifiedHour(b7, +8) + ":00";
				
				Map<String,String> map1 = new HashMap<String,String>();
				map1.put("startTime", b1);
				map1.put("endTime", b2);
				map1.put("blj", ucc.queryBlj(b1, b2, zbjcquerybo));
				
				Map<String,String> map2 = new HashMap<String,String>();
				map2.put("startTime", b3);
				map2.put("endTime", b4);
				map2.put("blj", ucc.queryBlj(b3, b4, zbjcquerybo));
				
				Map<String,String> map3 = new HashMap<String,String>();
				map3.put("startTime", b5);
				map3.put("endTime", b6);
				map3.put("blj", ucc.queryBlj(b5, b6, zbjcquerybo));
				
				/*Map<String,String> map4 = new HashMap<String,String>();
				map4.put("startTime", b7);
				map4.put("endTime", b8);
				map4.put("blj", ucc.queryBlj(b7, b8, zbjcquerybo));*/
				
				bljs.add(map1);
				bljs.add(map2);
				bljs.add(map3);
				//bljs.add(map4);

			}
			

			List<Map<String, String>> zblist = ucc.queryHistoryZbz(vo,zbjcquerybo);
			// 翻译能源种类
			Map<String, String> map = FarmManager.instance().findDicTitleForIndex("NYZL");
			if (zblist != null) {
				for (Map<String, String> maptype : zblist) {
					String key = maptype.get("YNSB_NYZL");
					String value = map.get(key);
					
					String zbjc_sj = maptype.get("ZBJC_SJ");
					
					//20181019修改  当前日期显示的是上一班的班累计
					for(int i=0;i<bljs.size();i++){
						Map<String,String> b = bljs.get(i);
						String startTime = b.get("startTime");
						String endTime = b.get("endTime");

						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if ((dateFormat.parse(startTime).getTime() < dateFormat.parse(zbjc_sj).getTime())
								&& (dateFormat.parse(zbjc_sj).getTime() < dateFormat.parse(endTime).getTime())) {
							maptype.put("BLJ", bljs.get(i-1).get("blj"));
						} else {
							//System.out.println("2");
						}
					}
					
					/*for(Map<String,String> b : bljs){
						String startTime = b.get("startTime");
						String endTime = b.get("endTime");

						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if ((dateFormat.parse(startTime).getTime() < dateFormat.parse(zbjc_sj).getTime())
								&& (dateFormat.parse(zbjc_sj).getTime() < dateFormat.parse(endTime).getTime())) {
							maptype.put("BLJ", b.get("blj"));
						} else {
							//System.out.println("2");
						}
					}*/
					
					if (value != null) {
						if ("''".equals(value)) {
							maptype.put("YNSB_NYZLNAME", "");
						} else {
							maptype.put("YNSB_NYZLNAME", value);
						}

					}
				}
			}
			request.setAttribute("zbzlist", zblist);
			request.setAttribute("sislist", sislist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryHistoryZbjc";
	}

	// 导出历史指标检测
	public void exportZbjc() {
		try {
			session.setAttribute("exportflag", "true");
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			// 机组id
			if (zbjcquerybo.getFid() == null) {
				zbjcquerybo.setFid(SsjcConstants.ITEMID_ZDYN);
			}
			List sislist = ucc.querySisBySb(zbjcquerybo.getSbid()); // sis
			List<Map<String, String>> zblist = ucc.queryHistoryZbz(null,
					zbjcquerybo);
			// 翻译能源种类
			Map<String, String> map = FarmManager.instance()
					.findDicTitleForIndex("NYZL");
			if (zblist != null) {
				for (Map<String, String> maptype : zblist) {
					String key = maptype.get("YNSB_NYZL");
					String value = map.get(key);
					if (value != null) {
						if ("''".equals(value)) {
							maptype.put("YNSB_NYZLNAME", "");
						} else {
							maptype.put("YNSB_NYZLNAME", value);
						}

					}
				}
			}

			OutputStream os = httpResponse.getOutputStream();
			httpResponse.reset();
			httpResponse.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String("能源监测.xls".getBytes("GB2312"),
									"ISO8859-1"));
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 标题
			sheet.addCell(new Label(0, 0, "设备管理编号", wcf_center));
			sheet.addCell(new Label(1, 0, "时间", wcf_center));
			sheet.addCell(new Label(2, 0, "能源种类", wcf_center));
			if (sislist != null && sislist.size() > 0) {
				for (int i = 0; i < sislist.size(); i++) {
					YnsbSisBO sisbo = (YnsbSisBO) sislist.get(i);
					sheet.addCell(new Label(i + 3, 0, sisbo.getYnsbsis_mc(),
							wcf_center));
					sheet.setColumnView(i + 3, 20);// 设置宽度
				}
			}
			sheet.setColumnView(0, 20);// 设置宽度
			sheet.setColumnView(1, 20);// 设置宽度
			sheet.setColumnView(2, 20);// 设置宽度

			if (zblist != null && zblist.size() > 0) {
				for (int i = 0; i < zblist.size(); i++) {
					Map<String, String> mapzb = zblist.get(i);
					sheet.addCell(new Label(0, i + 1, Tools.filterNull(mapzb
							.get("YNSB_GLBH"))));// 设备
					sheet.addCell(new Label(1, i + 1, Tools.filterNull(mapzb
							.get("ZBJC_SJ"))));// 时间
					sheet.addCell(new Label(2, i + 1, Tools.filterNull(mapzb
							.get("YNSB_NYZLNAME"))));// 时间
					String zbjc_sz = Tools.filterNull(mapzb.get("ZBJC_SZ"));
					String[] zbsz = zbjc_sz.split(",");
					for (int j = 0; j < zbsz.length; j++) {
						sheet.addCell(new Label(j + 3, i + 1, Tools
								.filterNull(zbsz[j])));// 时间
					}
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.removeAttribute("exportflag");
		}
	}

	// 设备监测历史数据查询
	public String queryHistorySbjc() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String title = null;
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			if (!Tools.stringIsNull(zbjcquerybo.getYnsbbh())) {
				title = zbjcquerybo.getYnsbbh();
				Map<String, String> map = FarmManager.instance()
						.findDicTitleForIndex("SBMC");
				if (map != null && map.size() != 0) {
					String value = map.get(title);
					if (value != null) {
						if ("''".equals(value)) {
							zbjcquerybo.setYnsbbh("");
						} else {
							zbjcquerybo.setYnsbbh(value.toString());
						}

					}
				}
			}
			vo.setPageSize(10);
			List<Map<String, String>> zblist = ucc.queryHistorySbjc(vo,
					zbjcquerybo);
			request.setAttribute("zbzlist", zblist);
			request.setAttribute("sbbh", title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryHistorySbjc";
	}

	// 设备监测报表历史数据查询
	public String queryHistoryBBSbjc() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String queryDate = null;
			String startTime = null;
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			if (!Tools.stringIsNull(zbjcquerybo.getStarttime())) {
				startTime = zbjcquerybo.getStarttime();
				queryDate = startTime;
				startTime = startTime + " 07:00";
				zbjcquerybo.setStarttime(startTime);
			} else {
				queryDate = Tools.getSysDate("yyyy-MM-dd");
				startTime = queryDate + " 07:00";
				zbjcquerybo.setStarttime(startTime);
			}
			vo.setPageSize(10);
			List<Map<String, String>> zblist = ucc.queryHistorySbjc(vo,
					zbjcquerybo);
			request.setAttribute("zbzlist", zblist);
			request.setAttribute("queryDate", queryDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryHistoryBBSbjc";
	}

	// 正东集团平衡图初始化
	public String zdphtInit() {
		// request.setAttribute("months", Tools.getSysDate("MM"));
		request.setAttribute("months", Tools.getSysDate("yyyy-MM"));
		return "zdphtInit";
	}

	// 正东集团平衡图
	public void zdphtQuery() {
		String sj = Tools.getSysDate("yyyy-MM");
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String[] nyzlmonth = { "6", "1", "4", "7", "3", "9", "5" };
			List<Map<String, Object>> endlist = ucc.querySglrSumNy(SsjcConstants.ITEMID_ZDYN, sj, nyzlmonth);// 手工补录数据月
			List<Map<String, Object>> startlist = new ArrayList<Map<String, Object>>();
			Map<String, String> phtvalmonth = new HashMap<String, String>();
			String strartsj = Tools.minusMonth(sj, 1);
			// 查询上一个月数据（手工补录数据）
			startlist = ucc.querySglrSumNy(SsjcConstants.ITEMID_ZDYN, strartsj,nyzlmonth);
			// 手工补录数据月
			for (Map<String, Object> endmap : endlist) {
				for (Map<String, Object> startmap : startlist) {
					if (endmap.get("EAST_SJBL_NYZL").equals(startmap.get("EAST_SJBL_NYZL"))) {
						Double dou = Double.parseDouble(endmap.get("TOTAL").toString())- Double.parseDouble(startmap.get("TOTAL").toString());
						phtvalmonth.put(endmap.get("EAST_SJBL_NYZL").toString() + "v", String.valueOf(dou));
						break;
					}
				}
			}

			String zdsj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			zdsj = Tools.minusMinute(zdsj, -2);
			String zdstrartsj = Tools.getUpMonthEnd() + " 23:59:00";
			// 自动提取数据月
			List<Map<String, Object>> zdendlist = ucc.queryZdSumNy(
					SsjcConstants.ITEMID_ZDYN, zdsj, nyzlmonth);
			List<Map<String, Object>> zdstartlist = ucc.queryZdSumNy(
					SsjcConstants.ITEMID_ZDYN, zdstrartsj, nyzlmonth);// 上个月
			for (Map<String, Object> zdendmap : zdendlist) {
				for (Map<String, Object> zdstartmap : zdstartlist) {
					if (zdendmap.get("NYZL").equals(zdstartmap.get("NYZL"))) {
						Double dou = Double.parseDouble(zdendmap.get("TOTAL").toString()) - Double.parseDouble(zdstartmap.get("TOTAL").toString());
						String totaly = String.valueOf(dou + Double.parseDouble(phtvalmonth.get(zdendmap.get("NYZL") + "v")));
						phtvalmonth.put(zdendmap.get("NYZL").toString() + "v",totaly);
						break;
					}
				}
			}

			// 查询面积
			List<Map<String, Object>> mjlist = ucc.queryZdMj(sj);
			Map<String, String> ymj = new HashMap<String, String>();
			if (mjlist.get(0).get("EAST_SJBL_LJZ") != null) {
				ymj.put("ymjval", mjlist.get(0).get("EAST_SJBL_LJZ").toString());
			} else {
				ymj.put("ymjval", "0.0");
			}
			Map returnmap = new HashMap<String, Object>();
			returnmap.put("phtvalmonth", phtvalmonth);
			returnmap.put("mjval", ymj);
			JSONArray returnjson = JSONArray.fromObject(returnmap);
			httpResponse.getWriter().print(returnjson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 恒东次级流程图初始化
	public String hdzjLctInit() {
		// request.setAttribute("years",Tools.getSysDate("yyyy"));
		request.setAttribute("months", Tools.getSysDate("yyyy-MM"));
		return "hdzjLctInit";
	}

	// 恒东次级流程图
	public void hdzjLct() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String[] nyzl = { "4", "6", "1", "8", "2", "9", "3" };// 需要查询的能源种类
			String yearMonthend = Tools.getSysDate("yyyy-MM");// 年月
			List<Map<String, Object>> endlistMonth = ucc.querySglrSumNy(
					SsjcConstants.YNSB_HDRDCJ, yearMonthend, nyzl);// 手工录入数据
			String yearMonthStart = Tools.minusMonth(yearMonthend, 1);
			List<Map<String, Object>> startlistMonth = ucc.querySglrSumNy(
					SsjcConstants.YNSB_HDRDCJ, yearMonthStart, nyzl);// 上个月手工录入数据
			Map<String, String> lctMonthVal = new HashMap<String, String>();
			for (Map<String, Object> endmap : endlistMonth) {
				for (Map<String, Object> startmap : startlistMonth) {
					if (endmap.get("EAST_SJBL_NYZL").equals(
							startmap.get("EAST_SJBL_NYZL"))) {
						Double dou = Double.parseDouble(endmap.get("TOTAL")
								.toString())
								- Double.parseDouble(startmap.get("TOTAL")
										.toString());
						lctMonthVal.put(endmap.get("EAST_SJBL_NYZL").toString()
								+ "v", String.valueOf(dou));
						break;
					}
				}
			}
			// 自动提取月数据
			String zdsj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			zdsj = Tools.minusMinute(zdsj, -2);
			String zdstrartsj = Tools.getUpMonthEnd() + " 23:59:00";
			List<Map<String, Object>> endZdListMonth = ucc.queryZdSumNy(
					SsjcConstants.YNSB_HDRDCJ, zdsj, nyzl);
			List<Map<String, Object>> startZdListMonth = ucc.queryZdSumNy(
					SsjcConstants.YNSB_HDRDCJ, zdstrartsj, nyzl);// 上个月数据
			for (Map<String, Object> zdendmap : endZdListMonth) {
				for (Map<String, Object> zdstartmap : startZdListMonth) {
					if (zdendmap.get("NYZL").equals(zdstartmap.get("NYZL"))) {
						Double dou = Double.parseDouble(zdendmap.get("TOTAL")
								.toString())
								- Double.parseDouble(zdstartmap.get("TOTAL")
										.toString());
						String totaly = String.valueOf(dou
								+ Double.parseDouble(lctMonthVal.get(zdendmap
										.get("NYZL") + "v")));
						lctMonthVal.put(zdendmap.get("NYZL").toString() + "v",
								totaly);
						break;
					}
				}
			}
			Map returnmap = new HashMap<String, Object>();
			returnmap.put("lctMonthVal", lctMonthVal);
			JSONArray returnjson = JSONArray.fromObject(returnmap);
			httpResponse.getWriter().print(returnjson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 动力南厂流程图初始化
	public String dlncLctInit() {
		request.setAttribute("months", Tools.getSysDate("yyyy-MM"));
		return "dlncLctInit";
	}

	// 动力南厂数据
	public void dlncLctQuery() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String[] nyzl = { "6", "1", "4", "8", "2", "3", "7", "9", "10" };// 需要查询的能源种类
			String yearMonthend = Tools.getSysDate("yyyy-MM");// 年月
			String yearMonthStart = Tools.minusMonth(yearMonthend, 1);

			// 手工录入数据
			List<Map<String, Object>> listsdMonthEnd = ucc.querySglrSumNy(
					SsjcConstants.YNSB_DLCJYN, yearMonthend, nyzl);// 当前月
			List<Map<String, Object>> listsdMonthStart = ucc.querySglrSumNy(
					SsjcConstants.YNSB_DLCJYN, yearMonthStart, nyzl);// 上个月数据
			Map<String, String> lctMonthVal = new HashMap<String, String>();
			for (Map<String, Object> endmap : listsdMonthEnd) {
				for (Map<String, Object> startmap : listsdMonthStart) {
					if (endmap.get("EAST_SJBL_NYZL").equals(
							startmap.get("EAST_SJBL_NYZL"))) {
						Double dou = Double.parseDouble(endmap.get("TOTAL")
								.toString())
								- Double.parseDouble(startmap.get("TOTAL")
										.toString());
						lctMonthVal.put(endmap.get("EAST_SJBL_NYZL").toString()
								+ "v", String.valueOf(dou));
						break;
					}
				}
			}

			// 自动数据
			//lctMonthVal.remove("");
			String zdsj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			zdsj = Tools.minusMinute(zdsj, -2);
			String zdstrartsj = Tools.getUpMonthEnd() + " 23:59:00";
			List<Map<String, Object>> listMonthEnd = ucc.queryZdSumNy(SsjcConstants.YNSB_DLCJYN, zdsj, nyzl);// 查询当前月
			List<Map<String, Object>> listMonthStart = ucc.queryZdSumNy(SsjcConstants.YNSB_DLCJYN, zdstrartsj, nyzl);// 上个月数据
			for (Map<String, Object> zdendmap : listMonthEnd) {
				for (Map<String, Object> zdstartmap : listMonthStart) {
					if (zdendmap.get("NYZL").equals(zdstartmap.get("NYZL"))) {
						Double dou = Double.parseDouble(zdendmap.get("TOTAL")
								.toString())
								- Double.parseDouble(zdstartmap.get("TOTAL")
										.toString());
						String totaly = String.valueOf(dou
								+ Double.parseDouble(lctMonthVal.get(zdendmap
										.get("NYZL") + "v")));
						lctMonthVal.put(zdendmap.get("NYZL").toString() + "v",
								totaly);
						break;
					}
				}
			}

			Map returnmap = new HashMap<String, Object>();
			returnmap.put("lctMonthVal", lctMonthVal);
			JSONArray returnjson = JSONArray.fromObject(returnmap);
			httpResponse.getWriter().print(returnjson);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设备监测初始
	 * 
	 * @return
	 */
	public String sbjcIndex() {
		/*
		 * try { ICodeUCC ucc = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
		 * Map<String,String> params = new HashMap<String, String>(); String
		 * itemSpell = request.getParameter("itemSpell");
		 * if(StringUtils.isNotBlank(itemSpell)){ params.put("itemSpell",
		 * itemSpell); }else{ throw new IllegalArgumentException(); }
		 * params.put("setId", SsjcConstants.YNSB_SUPERID);
		 * params.put("itemSuper", "-1"); List<CodeItemBO> list =
		 * ucc.queryCodeByParams(params); request.setAttribute("list", list);
		 * request.setAttribute("itemSpell", itemSpell); } catch (BkmsException
		 * e) { e.printStackTrace(); }
		 */
		return "sbjcIndex";
	}

	/**
	 * 设备监测查询
	 * 
	 * @return
	 */
	public String sbjcList() {
		try {
			
			IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
			/*
			 * String fid = request.getParameter("fid"); String itemSpell =
			 * request.getParameter("itemSpell"); if(StringUtils.isBlank(fid)){
			 * if(StringUtils.isNotBlank(itemSpell)){
			 * if(StringUtils.equals(SsjcConstants.HBJC_ITEM_SPELL,
			 * itemSpell)){//环保监测 fid = SsjcConstants.YNSB_HBJC; }else
			 * if(StringUtils.equals(SsjcConstants.ZLJC_ITEM_SPELL,
			 * itemSpell)){//质量监测 fid = SsjcConstants.YNSB_ZLJC; } }else{ throw
			 * new IllegalArgumentException(); } }
			 */
			Map<String, String> params = new HashMap<String, String>();
			List<Map<String, Object>> list = ucc.queryZdsbByParams(params);
			// String sj = "2017-11-28 15:45:00";
			String sj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			Map<String, String> map = FarmManager.instance()
					.findDicTitleForIndex("NYZL");
			/*
			 * if(list != null){ for(Map<String, Object> maptype : list){ String
			 * key = maptype.get("YNSB_NYZL").toString(); Object value =
			 * map.get(key); if (value != null) { if("''".equals(value)){
			 * maptype.put("YNSB_NYZLNAME", ""); }else{
			 * maptype.put("YNSB_NYZLNAME", value); } } } }
			 */
			sj = Tools.minusMinute(sj, -2);// 时间减两分钟，查询上两分钟的，因需错开实时数据同步和计算累计时间
			String ljsj = sj; // 累计时间
			// 查询前一天23:45的记录
			if (Tools.compareTo(sj.substring(11, 16), "07:45", "HH:mm") == -1) {
				ljsj = Tools.modifiedHour(sj.substring(0, 10) + " 07:45", -8);
			} else if (Tools.compareTo(sj.substring(11, 16), "15:45", "HH:mm") == -1) {
				ljsj = sj.substring(0, 10) + " 07:45";
			} else if (Tools.compareTo(sj.substring(11, 16), "23:45", "HH:mm") == -1) {
				ljsj = sj.substring(0, 10) + " 15:45";
			}
			List<Map<String, Object>> ztlist = ucc.queryZdsbSbjcByParams(sj);// 设备状态
			// List<Map<String, Object>> sislist = ucc.querySis(fid);//sis
			// List<Map<String, Object>> zbzlist = ucc.queryZbz(fid, sj);//指标检测值
			// List<Map<String, Object>> ljzlist = ucc.queryLjz(fid, ljsj);
			// //累计值
			// request.setAttribute("sislist", sislist);
			// request.setAttribute("zbzlist", zbzlist);
			// request.setAttribute("ljzlist", ljzlist);
			request.setAttribute("ztlist", ztlist);
			request.setAttribute("list", list);
			request.setAttribute("sj", sj);
			request.setAttribute("SBJC_VALUE", Float.parseFloat(StaticVariable.get(PmsConstants.SBJC_VALUE))); //设备监测值
			// request.setAttribute("fid", fid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sbjcList";
	}

	/**
	 * 环保监测,质量监测初始 /hbjcIndex.action?itemSpell=4
	 * 
	 * @return
	 */
	public String hbjcIndex() {
		try {
			ICodeUCC ucc = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
			Map<String, String> params = new HashMap<String, String>();
			String itemSpell = request.getParameter("itemSpell");
			if (StringUtils.isNotBlank(itemSpell)) {
				params.put("itemSpell", itemSpell);
				if("5".equals(itemSpell)){
					params.put("itemName", "质量监测");
				}
			} else {
				throw new IllegalArgumentException();
			}
			params.put("setId", SsjcConstants.YNSB_SUPERID);
			params.put("itemSuper", "-1");			
			List<CodeItemBO> list = ucc.queryCodeByParams(params);
			request.setAttribute("list", list);
			request.setAttribute("itemSpell", itemSpell);
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		return "hbjcIndex";
	}

	/**
	 * 环保监测,质量监测查询
	 * 
	 * @return
	 */
	public String hbjcList() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String fid = request.getParameter("fid");
			String itemSpell = request.getParameter("itemSpell");
			if (StringUtils.isBlank(fid)) {
				if (StringUtils.isNotBlank(itemSpell)) {
					if (StringUtils.equals(SsjcConstants.HBJC_ITEM_SPELL,
							itemSpell)) {// 环保监测
						fid = SsjcConstants.YNSB_HBJC;
					} else if (StringUtils.equals(
							SsjcConstants.ZLJC_ITEM_SPELL, itemSpell)) {// 质量监测
						fid = SsjcConstants.YNSB_ZLJC;
					}
				} else {
					throw new IllegalArgumentException();
				}
			}
			List<Map<String, Object>> list = ucc.queryType("", "", fid); // 能源种类
			// String sj = "2017-11-28 15:45:00";
			String sj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			Map<String, String> map = FarmManager.instance()
					.findDicTitleForIndex("NYZL");
			if (list != null) {
				for (Map<String, Object> maptype : list) {
					String key = maptype.get("YNSB_NYZL").toString();
					Object value = map.get(key);
					if (value != null) {
						if ("''".equals(value)) {
							maptype.put("YNSB_NYZLNAME", "");
						} else {
							maptype.put("YNSB_NYZLNAME", value);
						}
					}
				}
			}
			sj = Tools.minusMinute(sj, -2);// 时间减两分钟，查询上两分钟的，因需错开实时数据同步和计算累计时间
			String ljsj = sj; // 累计时间
			// 查询前一天23:45的记录
			if (Tools.compareTo(sj.substring(11, 16), "07:45", "HH:mm") == -1) {
				ljsj = Tools.modifiedHour(sj.substring(0, 10) + " 07:45", -8);
			} else if (Tools.compareTo(sj.substring(11, 16), "15:45", "HH:mm") == -1) {
				ljsj = sj.substring(0, 10) + " 07:45";
			} else if (Tools.compareTo(sj.substring(11, 16), "23:45", "HH:mm") == -1) {
				ljsj = sj.substring(0, 10) + " 15:45";
			}
			List<Map<String, Object>> sislist = ucc.querySis(fid);// sis
			List<Map<String, Object>> zbzlist = ucc.queryZbz(fid, sj);// 指标检测值
			// List<Map<String, Object>> ljzlist = ucc.queryLjz(fid, ljsj);
			// //累计值
			request.setAttribute("sislist", sislist);
			request.setAttribute("zbzlist", zbzlist);
			// request.setAttribute("ljzlist", ljzlist);
			request.setAttribute("typelist", list);
			request.setAttribute("sj", sj);
			request.setAttribute("fid", fid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hbjcList";
	}

	/**
	 * 设备监测历史初始
	 * 
	 * @return
	 */
	public String sbjcHistoryIndex() {
		/*
		 * try { ICodeUCC ucc = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
		 * Map<String,String> params = new HashMap<String, String>(); String
		 * itemSpell = request.getParameter("itemSpell");
		 * if(StringUtils.isNotBlank(itemSpell)){ params.put("itemSpell",
		 * itemSpell); }else{ throw new IllegalArgumentException(); }
		 * params.put("setId", SsjcConstants.YNSB_SUPERID);
		 * params.put("itemSuper", "-1"); List<CodeItemBO> list =
		 * ucc.queryCodeByParams(params); request.setAttribute("list", list);
		 * request.setAttribute("itemSpell", itemSpell); } catch (BkmsException
		 * e) { e.printStackTrace(); }
		 */
		return "sbjcHistoryIndex";
	}

	/**
	 * 设备监测历史查询
	 * 
	 * @return
	 */
	public String sbjcHistoryList() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			/*
			 * String itemSpell = request.getParameter("itemSpell");
			 * if(StringUtils.isBlank(zbjcquerybo.getFid())){
			 * if(StringUtils.isNotBlank(itemSpell)){
			 * if(StringUtils.equals(SsjcConstants.HBJC_ITEM_SPELL,
			 * itemSpell)){//环保监测 zbjcquerybo.setFid(SsjcConstants.YNSB_HBJC);
			 * }else if(StringUtils.equals(SsjcConstants.ZLJC_ITEM_SPELL,
			 * itemSpell)){//质量监测 zbjcquerybo.setFid(SsjcConstants.YNSB_ZLJC); }
			 * }else{ throw new IllegalArgumentException(); } }
			 */
			vo.setPageSize(20);
			Map<String, String> params = new HashMap<String, String>();
			params.put("fid", zbjcquerybo.getFid());
			List<Map<String, Object>> sislist = ucc.querySisByParams(params); // sis
			// List<Map<String, String>> zblist = ucc.queryHistoryZbzInfo(vo,
			// zbjcquerybo);
			// 翻译能源种类
			/*
			 * Map<String, String> map =
			 * FarmManager.instance().findDicTitleForIndex("NYZL");
			 * if(zblist!=null){ for(Map<String, String> maptype:zblist){ String
			 * key = maptype.get("YNSB_NYZL"); String value = map.get(key); if
			 * (value != null) { if("''".equals(value)){
			 * maptype.put("YNSB_NYZLNAME", ""); }else{
			 * maptype.put("YNSB_NYZLNAME", value); } } } }
			 */
			request.setAttribute("sislist", sislist);
			// request.setAttribute("zbzlist", zblist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sbjcHistoryList";
	}

	/**
	 * 环保监测,质量监测历史初始
	 * 
	 * @return
	 */
	public String hbjcHistoryIndex() {
		try {
			ICodeUCC ucc = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
			Map<String, String> params = new HashMap<String, String>();
			String itemSpell = request.getParameter("itemSpell");
			if (StringUtils.isNotBlank(itemSpell)) {
				params.put("itemSpell", itemSpell);
				if("5".equals(itemSpell)){
					params.put("itemName", "质量监测");
				}
			} else {
				throw new IllegalArgumentException();
			}
			params.put("setId", SsjcConstants.YNSB_SUPERID);
			params.put("itemSuper", "-1");
			List<CodeItemBO> list = ucc.queryCodeByParams(params);
			request.setAttribute("list", list);
			request.setAttribute("itemSpell", itemSpell);
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		return "hbjcHistoryIndex";
	}

	public String hbjcHistoryListInit() throws ParseException {
		String itemSpell = request.getParameter("itemSpell");
		if (zbjcquerybo == null) {
			zbjcquerybo = new ZbjcQueryCondition();
		}
		String currenttime = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
		// 开始时间
		if (zbjcquerybo.getStarttime() == null) {
			zbjcquerybo.setStarttime(Tools.modifiedHour(currenttime, -24)
					+ ":00");
		}
		// 结束时间
		if (zbjcquerybo.getEndtime() == null) {
			zbjcquerybo.setEndtime(currenttime);
		}
		if (StringUtils.isBlank(zbjcquerybo.getFid())) {
			if (StringUtils.isNotBlank(itemSpell)) {
				if (StringUtils
						.equals(SsjcConstants.HBJC_ITEM_SPELL, itemSpell)) {// 环保监测
					zbjcquerybo.setFid(SsjcConstants.YNSB_HBJC);
				} else if (StringUtils.equals(SsjcConstants.ZLJC_ITEM_SPELL,
						itemSpell)) {// 质量监测
					zbjcquerybo.setFid(SsjcConstants.YNSB_ZLJC);
				}
			} else {
				throw new IllegalArgumentException();
			}
		}
		request.setAttribute("itemSpell", itemSpell);
		return "hbjcHistoryListInit";
	}

	/**
	 * 环保监测,质量监测历史查询
	 * 
	 * @return
	 */
	public String hbjcHistoryList() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			String itemSpell = request.getParameter("itemSpell");
			if (StringUtils.isBlank(zbjcquerybo.getFid())) {
				if (StringUtils.isNotBlank(itemSpell)) {
					if (StringUtils.equals(SsjcConstants.HBJC_ITEM_SPELL,
							itemSpell)) {// 环保监测
						zbjcquerybo.setFid(SsjcConstants.YNSB_HBJC);
					} else if (StringUtils.equals(
							SsjcConstants.ZLJC_ITEM_SPELL, itemSpell)) {// 质量监测
						zbjcquerybo.setFid(SsjcConstants.YNSB_ZLJC);
					}
				} else {
					throw new IllegalArgumentException();
				}
			}
			vo.setPageSize(20);
			Map<String, String> params = new HashMap<String, String>();
			params.put("fid", zbjcquerybo.getFid());
			List sislist = ucc.querySisBySb(zbjcquerybo.getSbid()); // sis
			List<Map<String, String>> zblist = ucc.queryHistoryZbzInfo(vo,
					zbjcquerybo);
			// 翻译能源种类
			/*
			 * Map<String, String> map =
			 * FarmManager.instance().findDicTitleForIndex("NYZL");
			 * if(zblist!=null){ for(Map<String, String> maptype:zblist){ String
			 * key = maptype.get("YNSB_NYZL"); String value = map.get(key); if
			 * (value != null) { if("''".equals(value)){
			 * maptype.put("YNSB_NYZLNAME", ""); }else{
			 * maptype.put("YNSB_NYZLNAME", value); } } } }
			 */
			request.setAttribute("zbzlist", zblist);
			request.setAttribute("sislist", sislist);
			request.setAttribute("itemSpell", itemSpell);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hbjcHistoryList";
	}

	public void exportHbjc() {
		try {
			session.setAttribute("exportflag", "true");
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			String itemSpell = request.getParameter("itemSpell");
			String excelname = "";
			if (StringUtils.equals(SsjcConstants.HBJC_ITEM_SPELL, itemSpell)) {// 环保监测
				zbjcquerybo.setFid(SsjcConstants.YNSB_HBJC);
				excelname = "环保检测.xls";
			} else if (StringUtils.equals(SsjcConstants.ZLJC_ITEM_SPELL,
					itemSpell)) {// 质量监测
				zbjcquerybo.setFid(SsjcConstants.YNSB_ZLJC);
				excelname = "质量检测.xls";
			}

			List sislist = ucc.querySisBySb(zbjcquerybo.getSbid()); // sis
			List<Map<String, String>> zblist = ucc.queryHistoryZbzInfo(null,
					zbjcquerybo);

			OutputStream os = httpResponse.getOutputStream();
			httpResponse.reset();
			httpResponse.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String(excelname.getBytes("GB2312"),
									"ISO8859-1"));
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 标题
			sheet.addCell(new Label(0, 0, "设备管理编号", wcf_center));
			sheet.addCell(new Label(1, 0, "时间", wcf_center));
			sheet.setColumnView(0, 25);// 设置宽度
			sheet.setColumnView(1, 20);// 设置宽度

			if (sislist != null && sislist.size() > 0) {
				for (int i = 0; i < sislist.size(); i++) {
					YnsbSisBO sisbo = (YnsbSisBO) sislist.get(i);
					sheet.addCell(new Label(i + 2, 0, sisbo.getYnsbsis_mc(),
							wcf_center));
					sheet.setColumnView(i + 2, 20);// 设置宽度
				}
			}

			if (zblist != null && zblist.size() > 0) {
				for (int i = 0; i < zblist.size(); i++) {
					Map<String, String> mapzb = zblist.get(i);
					sheet.addCell(new Label(0, i + 1, Tools.filterNull(mapzb
							.get("YNSB_GLBH"))));// 设备
					sheet.addCell(new Label(1, i + 1, Tools.filterNull(mapzb
							.get("ZBJC_SJ"))));// 时间
					String zbjc_sz = Tools.filterNull(mapzb.get("ZBJC_SZ"));
					String[] zbsz = zbjc_sz.split(",");
					for (int j = 0; j < zbsz.length; j++) {
						sheet.addCell(new Label(j + 2, i + 1, Tools
								.filterNull(zbsz[j])));// 时间
					}
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.removeAttribute("exportflag");
		}
	}

	public void addSbscNextMin() throws BkmsException, ParseException {
		IZdsbUCC ucc = (IZdsbUCC) BkmsContext.getBean("zdsbUCC");
		List<Map<String, Object>> sbscList = ucc.queryZdsbSbsc();
		String sj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
		sj = Tools.minusMinute(sj, -2);// 时间减两分钟，查询上两分钟的，因需错开实时数据同步和计算累计时间
		List<Map<String, Object>> ztlist = ucc.queryZdsbSbjcByParams(sj);// 设备状态
		if (sbscList != null && sbscList.size() != 0) {
			for (int i = 0; i < sbscList.size(); i++) {
				Map<String, Object> map = sbscList.get(i);
				ZdsbSbscBO bo = new ZdsbSbscBO();
				ZdsbStateBO ztBo = new ZdsbStateBO();
				String sbIdSb = Tools.filterNull(map.get("ZDSB_ID"));
				String qnsc = Tools.filterNull(map.get("ZDSB_QNSC"));
				String zsc = Tools.filterNull(map.get("ZDSB_ZSC"));
				if (ztlist != null && ztlist.size() != 0) {
					for (int j = 0; j < ztlist.size(); j++) {
						Map<String, Object> ztMap = ztlist.get(j);
						String val = Tools.filterNull(ztMap.get("SISVALUE"));
						String sbIdZt = Tools.filterNull(ztMap.get("ZDSB_ID"));
						if (sbIdZt.equals(sbIdSb)) {
							if (!Tools.stringIsNull(val)
									&& Float.parseFloat(val) > 1) {
								if (!Tools.stringIsNull(qnsc)) {
									if (qnsc.substring(0, 4).equals(
											Tools.getSysYear())) {
										qnsc = Tools.minusMinute(qnsc, 1);
										bo.setZdsb_qnsc(qnsc);
										ztBo.setZdsb_qnsc(qnsc);
									} else {
										SimpleDateFormat dateFormat = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										// 设置指定日期
										Calendar cal = Calendar.getInstance();
										cal.set(Calendar.YEAR, Integer
												.parseInt(Tools.getSysYear()));
										cal.set(Calendar.MONTH, 0);
										cal.set(Calendar.DAY_OF_MONTH, 1);
										cal.set(Calendar.HOUR_OF_DAY, 0);
										cal.set(Calendar.MINUTE, 0);
										cal.set(Calendar.SECOND, 0);
										cal.set(Calendar.MILLISECOND, 0);
										Date date = new Date(
												cal.getTimeInMillis());
										// 格式化
										String dateStr = dateFormat
												.format(date);
										bo.setZdsb_qnsc(dateStr);
										ztBo.setZdsb_qnsc(dateStr);
									}
									zsc = Tools.minusMinute(zsc, 1);
									bo.setZdsb_zsc(zsc);
									ztBo.setZdsb_zsc(zsc);
								}
							} else {
								bo.setZdsb_qnsc(qnsc);
								bo.setZdsb_zsc(zsc);
								ztBo.setZdsb_qnsc(qnsc);
								ztBo.setZdsb_zsc(zsc);
							}
							bo.setId(Tools.filterNull(map.get("ID")));
							bo.setZdsb_id(sbIdSb);
							ucc.editZdsbSbsc(bo,user);

							ztBo.setZdsb_id(sbIdSb);// 设备id
							ztBo.setSisvalue(val);// sis值
							ztBo.setZdsb_state(Tools.filterNull(ztMap
									.get("ZDSB_STATE")));// 设备状态
							ztBo.setZdsb_ssbm(Tools.filterNull(ztMap
									.get("ZDSB_SSBM")));// 所属部门
							ztBo.setZdsb_sbmc(Tools.filterNull(ztMap
									.get("ZDSB_SBMC")));// 设备名称
							ztBo.setZdsb_jxrq(sj);
							ucc.editZdsbState(ztBo,user);
						}
					}
				}
			}

		}
	}

	public String nxjcList() {
		String lx = "1"; // 默认查询汽耗比
		String lxname = "气耗比";
		if (request.getParameter("lx") != null) {
			lx = request.getParameter("lx");
			if (lx.equals("2")) {
				lxname = "用水指标";
			} else if (lx.equals("3")) {
				lxname = "厂综合用电率";
			} else if (lx.equals("4")) {
				lxname = "单位面积耗热量";
			} else if (lx.equals("5")) {
				lxname = "单位面积耗电量";
			} else if (lx.equals("6")) {
				lxname = "单位面积耗水量";
			}
		}
		request.setAttribute("lx", lx);
		request.setAttribute("lxname", lxname);
		return "nxjcList";
	}

	public String queryNxjcList() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(20);
			String lx = request.getParameter("lx");
			String newtime = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			String querytime = Tools.minusMinute(newtime, -2);
			query.addUserWhere("and NXJC_LX = '" + lx + "' and NXJC_SJ = '"
					+ querytime + "' ");
			DataResult search = ucc.queryNcjcList(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String nxjcListHistory() {
		String lx = "1"; // 默认查询汽耗比
		String lxname = "气耗比";
		if (request.getParameter("lx") != null) {
			lx = request.getParameter("lx");
			if (lx.equals("2")) {
				lxname = "用水指标";
			} else if (lx.equals("3")) {
				lxname = "厂综合用电率";
			} else if (lx.equals("4")) {
				lxname = "单位面积耗热量";
			} else if (lx.equals("5")) {
				lxname = "单位面积耗电量";
			} else if (lx.equals("6")) {
				lxname = "单位面积耗水量";
			}
		}
		request.setAttribute("lx", lx);
		request.setAttribute("lxname", lxname);
		return "nxjcListHistory";
	}

	public String queryNxjcHistoryList() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			query = EasyUiUtils.formatGridQuery(request, query);
			DBRule starttimer = query.getAndRemoveRule("STARTTIME");// 开始时间
			if (starttimer != null) {
				query.addUserWhere("and NXJC_SJ >= '" + starttimer.getValue()
						+ "'");
			}
			DBRule endtimer = query.getAndRemoveRule("ENDTIME"); // 结束时间
			if (endtimer != null) {
				query.addUserWhere("and NXJC_SJ <= '" + endtimer.getValue()
						+ "'");
			}
			String lx = request.getParameter("lx");
			query.addUserWhere("and NXJC_LX = '" + lx + "'");
			DBSort dbsort = new DBSort("NXJCSJ", "desc");
			query.addDefaultSort(dbsort);
			DataResult search = ucc.queryNcjcList(query).search();
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 导出设备监测信息
	 * 
	 * @throws BkmsException
	 */
	public void exportSbjcMsg() throws BkmsException {
		HttpServletRequest request = ServletActionContext.getRequest();
		IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
		String titleName = Tools.filterNull(request.getParameter("tableName"));// 表中文名
		String tableName = "" + titleName + "" + ".xls";// excel名
		String starttime = Tools.filterNull(request.getParameter("starttime"));// 开始时间
		String endtime = Tools.filterNull(request.getParameter("endtime"));// 结束时间
		String sbbh = Tools.filterNull(request.getParameter("sbbh"));// 设备编号

		try {
			String title = null;
			if (zbjcquerybo == null) {
				zbjcquerybo = new ZbjcQueryCondition();
			}
			zbjcquerybo.setStarttime(Tools.filterNull(starttime));
			zbjcquerybo.setEndtime(Tools.filterNull(endtime));
			zbjcquerybo.setYnsbbh(Tools.filterNull(sbbh));
			if (!Tools.stringIsNull(zbjcquerybo.getYnsbbh())) {
				title = zbjcquerybo.getYnsbbh();
				Map<String, String> map = FarmManager.instance().findDicTitleForIndex("SBMC");
				if (map != null && map.size() != 0) {
					String value = map.get(title);
					if (value != null) {
						if ("''".equals(value)) {
							zbjcquerybo.setYnsbbh("");
						} else {
							zbjcquerybo.setYnsbbh(value.toString());
						}
					}
				}
			}
			List<Map<String, String>> zblist = ucc.querySbjcData(zbjcquerybo);

			OutputStream os = httpResponse.getOutputStream();
			httpResponse.reset();
			httpResponse.setHeader("Content-disposition", "attachment;filename=" + new String(tableName.getBytes("GB2312"),"ISO8859-1"));
			httpResponse.setContentType("application/msexcel");
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			sheet.setColumnView(0, 20);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(9, 20);
			sheet.setColumnView(10, 20);
			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 大标题格式
			WritableFont DBoldFont = new WritableFont(WritableFont.ARIAL, 15,WritableFont.BOLD);
			WritableCellFormat Dwcf_center = new WritableCellFormat(DBoldFont);
			Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 居中
			WritableCellFormat zwcf_center = new WritableCellFormat();
			zwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			sheet.mergeCells(0, 0, 10, 0);
			sheet.mergeCells(0, 1, 0, 2);
			sheet.mergeCells(1, 1, 1, 2);
			sheet.mergeCells(2, 1, 2, 2);
			sheet.mergeCells(3, 1, 3, 2);
			sheet.mergeCells(4, 1, 4, 2);
			sheet.mergeCells(5, 1, 8, 1);
			sheet.mergeCells(9, 1, 9, 2);
			sheet.mergeCells(10, 1, 10, 2);
			sheet.addCell(new Label(0, 0, "" + titleName + "", Dwcf_center));
			sheet.addCell(new Label(0, 1, "所属部门", wcf_center));
			sheet.addCell(new Label(1, 1, "设备编号", wcf_center));
			sheet.addCell(new Label(2, 1, "时间", wcf_center));
			sheet.addCell(new Label(3, 1, "瞬时值", wcf_center));
			sheet.addCell(new Label(4, 1, "单位", wcf_center));
			sheet.addCell(new Label(5, 1, "运行状态", wcf_center));
			sheet.addCell(new Label(9, 1, "本年运行时长", wcf_center));
			sheet.addCell(new Label(10, 1, "运行总时长", wcf_center));
			sheet.addCell(new Label(5, 2, "运行", wcf_center));
			sheet.addCell(new Label(6, 2, "检修", wcf_center));
			sheet.addCell(new Label(7, 2, "故障", wcf_center));
			sheet.addCell(new Label(8, 2, "备用", wcf_center));
			if (zblist != null && zblist.size() > 0) {
				for (int i = 0; i < zblist.size(); i++) {
					Map<String, String> map = (Map<String, String>) zblist.get(i);
					sheet.addCell(new Label(0, 3 + i, Tools.filterNull(map.get("ZDSB_SSBM")), zwcf_center));
					sheet.addCell(new Label(1, 3 + i, Tools.filterNull(map.get("ZDSB_SBMC")), zwcf_center));
					sheet.addCell(new Label(2, 3 + i, Tools.filterNull(map.get("SISTIME")), zwcf_center));
					sheet.addCell(new Label(3, 3 + i, Tools.filterNull(map.get("SISVALUE")), zwcf_center));
					
					/// 设置单位
					String sbmc = map.get("ZDSB_SBMC");
					if(sbmc.indexOf("变")!=-1){
						sheet.addCell(new Label(4, 3 + i, "A", zwcf_center));
					}else if(sbmc.indexOf("GE")!=-1 || sbmc.indexOf("发电机")!=-1){
						sheet.addCell(new Label(4, 3 + i, "MW", zwcf_center));
					}else if(sbmc.indexOf("热水锅炉")!=-1){
						sheet.addCell(new Label(4, 3 + i, "GJ/h", zwcf_center));
					}else{
						sheet.addCell(new Label(4, 3 + i, "t/h", zwcf_center));
					}
					///
					
					String state = Tools.filterNull(map.get("ZDSB_STATE"));
					String sisvalue = Tools.filterNull(map.get("SISVALUE"));
					if (sisvalue != null && !"".equals(sisvalue) && Double.parseDouble(sisvalue) > 1) {
						sheet.addCell(new Label(5, 3 + i, "√", zwcf_center));
					} else if ("检修".equals(state)) {
						sheet.addCell(new Label(6, 3 + i, "√", zwcf_center));
					} else if ("故障".equals(state)) {
						sheet.addCell(new Label(7, 3 + i, "√", zwcf_center));
					} else if ("备用".equals(state)) {
						sheet.addCell(new Label(8, 3 + i, "√", zwcf_center));
					}
					Object obQnsc = map.get("ZDSB_QNSC");
					if (obQnsc != null) {
						sheet.addCell(new Label(9, 3 + i, obQnsc.toString(),zwcf_center));
					}
					Object obZsc = map.get("ZDSB_ZSC");
					if (obZsc != null) {
						sheet.addCell(new Label(10, 3 + i, obZsc.toString(),zwcf_center));
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

	// 导出能效检测
	public String exportNxjc() {
		try {
			session.setAttribute("exportflag", "true");
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String lx = request.getParameter("lx");
			String lxname = "";
			if (lx.equals("1")) {
				lxname = "气耗比";
			} else if (lx.equals("2")) {
				lxname = "用水指标";
			} else if (lx.equals("3")) {
				lxname = "厂综合用电率";
			} else if (lx.equals("4")) {
				lxname = "单位面积耗热量";
			} else if (lx.equals("5")) {
				lxname = "单位面积耗电量";
			} else if (lx.equals("6")) {
				lxname = "单位面积耗水量";
			}
			NxjcBO bo = new NxjcBO();
			bo.setNxjc_lx(lx);
			List<NxjcBO> list = ucc.queryListNxjcBy(starttime, endtime, bo);
			OutputStream os = httpResponse.getOutputStream();
			httpResponse.reset();
			httpResponse.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String("能效检测.xls".getBytes("GB2312"),
									"ISO8859-1"));
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 标题
			sheet.addCell(new Label(0, 0, "设备", wcf_center));
			sheet.addCell(new Label(1, 0, "时间", wcf_center));
			sheet.addCell(new Label(2, 0, lxname, wcf_center));
			sheet.addCell(new Label(3, 0, "单位", wcf_center));
			sheet.setColumnView(0, 20);// 设置宽度
			sheet.setColumnView(1, 20);// 设置宽度
			sheet.setColumnView(2, 20);// 设置宽度
			sheet.setColumnView(3, 10);// 设置宽度
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					NxjcBO boval = list.get(i);
					sheet.addCell(new Label(0, i + 1, Tools.filterNull(boval
							.getNxjc_sb())));// 设备
					sheet.addCell(new Label(1, i + 1, Tools.filterNull(boval
							.getNxjc_sj())));// 时间
					sheet.addCell(new Label(2, i + 1, Tools.filterNull(boval
							.getNxjc_val())));
					sheet.addCell(new Label(3, i + 1, Tools.filterNull(boval
							.getNxjc_dw())));// 单位
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.removeAttribute("exportflag");
		}
		return null;
	}

	public void isExport() throws IOException {
		Object exportedFlag = session.getAttribute("exportflag");
		if (exportedFlag == null) {
			httpResponse.getWriter().print(true);
		} else {
			httpResponse.getWriter().print(false);
		}
	}

	// 查询园区电表数据
	public String queryParkMeter() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addUserWhere(" and NUMERICDATE = '"
					+ DateUtil.getLastDay("yyyy-MM-dd") + "' ");
			DataResult result = ucc.queryParkMeter(query).search();
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 查询历史园区电表数据
	public String queryHistoryParkMeter() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			query = EasyUiUtils.formatGridQuery(request, query);
			DBSort dbsort = new DBSort("NUMERICDATE", "desc");
			query.addSort(dbsort);
			DataResult result = ucc.queryParkMeter(query).search();
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 查询园区电表
	public String queryParkMeterWatch() {
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = ucc.queryParkMeterWatch(query).search();
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	//修改园区电表初始化
	public String editParkMeterWatchInit(){
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String id = Tools.filterNull(request.getParameter("id"));
			if(!id.equals("")){
				if(yqdbbo==null){
					yqdbbo = new YqdbBO();
				}
				yqdbbo.setYqdb_id(id);
				List<YqdbBO> list = ucc.queryYqdb(yqdbbo);
				if(list!=null&&list.size()>0){
					yqdbbo = list.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editParkMeterWatchInit";
	}
	//删除园区电表
	public void deleteYqdb(){
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String id = Tools.filterNull(request.getParameter("id"));
			YqdbBO bo = new YqdbBO();
			bo.setYqdb_id(id);
			ucc.deleteYqdb(bo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//更新园区电表
	public void editParkMeterWatch() throws IOException{
		boolean flag = false;
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			ucc.editYqdb(yqdbbo);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	

	public void exportParkMeter() {
		session.setAttribute("exportflag", "true");
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			String rq = Tools.filterNull(request.getParameter("rq")); // 日期
			String zh = Tools.filterNull(request.getParameter("zh")); // 租户

			List<Map<String, String>> list = ucc.queryParkMeter(rq, zh);

			String tableName = rq + "园区电表数据.xls";// excel名
			String titleName = rq + "园区电表数据";// title名
			OutputStream os = httpResponse.getOutputStream();
			httpResponse.reset();
			httpResponse.setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String(tableName.getBytes("GB2312"),
									"ISO8859-1"));
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			// 标题格式
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			WritableCellFormat Dwcf_center = new WritableCellFormat(BoldFont);
			Dwcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setAlignment(Alignment.CENTRE); // 水平居中对齐
			// 标题
			sheet.mergeCells(0, 0, 8, 0);
			sheet.addCell(new Label(0, 0, "" + titleName + "", Dwcf_center));
			sheet.addCell(new Label(0, 1, "租户", wcf_center));
			sheet.addCell(new Label(1, 1, "电表编号", wcf_center));
			sheet.addCell(new Label(2, 1, "日期", wcf_center));
			sheet.addCell(new Label(3, 1, "峰值", wcf_center));
			sheet.addCell(new Label(4, 1, "谷值", wcf_center));
			sheet.addCell(new Label(5, 1, "平值", wcf_center));
			sheet.addCell(new Label(6, 1, "尖值", wcf_center));
			sheet.addCell(new Label(7, 1, "总值", wcf_center));
			sheet.addCell(new Label(8, 1, "倍率值", wcf_center));
			sheet.setColumnView(0, 20);// 设置宽度
			sheet.setColumnView(1, 15);// 设置宽度
			sheet.setColumnView(2, 15);// 设置宽度
			sheet.setColumnView(3, 15);// 设置宽度
			sheet.setColumnView(4, 15);// 设置宽度
			sheet.setColumnView(5, 15);// 设置宽度
			sheet.setColumnView(6, 15);// 设置宽度
			sheet.setColumnView(7, 15);// 设置宽度
			sheet.setColumnView(8, 15);// 设置宽度
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> map = list.get(i);
					sheet.addCell(new Label(0, i + 2, Tools.filterNull(map
							.get("TENANTNAME"))));// 租户
					sheet.addCell(new Label(1, i + 2, Tools.filterNull(map
							.get("METERNO"))));// 电表编号
					sheet.addCell(new Label(2, i + 2, Tools.filterNull(map
							.get("NUMERICDATE"))));// 日期
					sheet.addCell(new Label(3, i + 2, Tools.filterNull(map
							.get("PEAKVALUE"))));// 峰值
					sheet.addCell(new Label(4, i + 2, Tools.filterNull(map
							.get("VALLEYVALUE"))));// 谷值
					sheet.addCell(new Label(5, i + 2, Tools.filterNull(map
							.get("FAIRVALUE"))));// 平值
					sheet.addCell(new Label(6, i + 2, Tools.filterNull(map
							.get("POINTVALUE"))));// 尖值
					sheet.addCell(new Label(7, i + 2, Tools.filterNull(map
							.get("TOTALVALUE"))));// 总值
					sheet.addCell(new Label(8, i + 2, Tools.filterNull(map
							.get("RATE"))));// 倍率值
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.removeAttribute("exportflag");
		}
	}

	// 查询单元机组信息
	public String queryDyjzSon() {
		query = EasyUiUtils.formatGridQuery(request, query);
		String ynsb_glbh = request.getParameter("ynsb_glbh");// 计量编号
		String dyjz_date = request.getParameter("dyjz_date");// 日期
		query.addUserWhere(" and SB.YNSB_ID= '" + ynsb_glbh + "'");
		query.addUserWhere(" and ZD.SISTIME like '" + dyjz_date + "%'");
		query.addUserWhere(" order by zd.sistime desc ");
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			query.setPagesize(10000);
			DataResult search = ucc.queryDyjzList(query).search();
			List<Map<String, Object>> list = search.getResultList();
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				int i=list.size();
				list2.add(list.get(i-1));
				list2.add(list.get(0));
				search.setResultList(list2);
			}
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 编辑单元机组累积值
	public void editDyjzValue() throws IOException {
		boolean flag = false;
		String dyjzId = request.getParameter("dyjzId");
		String dyjzValue = request.getParameter("dyjzValue");
		try {
			IZbjcUCC ucc = (IZbjcUCC) BkmsContext.getBean("zbjcUCC");
			List<ZdTest2BO> list = ucc.queryZdTest2BoById(dyjzId);
			if (list != null && list.size() > 0) {
				ZdTest2BO bo = list.get(0);
				
				/*ZdTest2BO bo2 = ucc.queryZdTest2BoBySisidAndSistime(bo.getSisid(), "2018-08-09 23:59:00");
				long newValue = Long.parseLong(dyjzValue);
				long oldValue = Long.parseLong(bo.getSisvalue());
				long v = Long.parseLong(bo2.getSisvalue());
				String s = String.valueOf(v + (newValue - oldValue));
				bo2.setSisvalue_new(s);*/
				
				bo.setSisvalue(dyjzValue);
				ucc.editZdTest2(bo);
				//ucc.editZdTest2(bo2);
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	
	
	public YqdbBO getYqdbbo() {
		return yqdbbo;
	}

	public void setYqdbbo(YqdbBO yqdbbo) {
		this.yqdbbo = yqdbbo;
	}

	public ZbjcQueryCondition getZbjcquerybo() {
		return zbjcquerybo;
	}

	public void setZbjcquerybo(ZbjcQueryCondition zbjcquerybo) {
		this.zbjcquerybo = zbjcquerybo;
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