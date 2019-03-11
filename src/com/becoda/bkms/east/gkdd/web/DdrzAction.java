package com.becoda.bkms.east.gkdd.web;

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

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.east.gkdd.pojo.DdrzBO;
import com.becoda.bkms.east.gkdd.ucc.IDdrzUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
/**
 * 
 * <p>Description:调度日志action </p>
 * @author liu_hq
 * @date 2017-9-14
 *
 */
public class DdrzAction extends GenericAction{
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private DdrzBO ddrzbo; //调度日志实体类
	
	//分页查询调度日志
	public String queryList(){
		try {
			IDdrzUCC ucc = (IDdrzUCC)BkmsContext.getBean("ddrzUCC");
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addUserWhere(" and DDRZ_RQ = '"+Tools.getSysDate("yyyy-MM-dd")+"'");
			DataResult result = ucc.queryList(query).search();
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//分页查询历史调度日志
	public String queryHistoryList(){
		try {
			IDdrzUCC ucc = (IDdrzUCC)BkmsContext.getBean("ddrzUCC");
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = ucc.queryList(query).search();
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//录入调度日志初始化
	public String ddrzAddInit(){
		request.setAttribute("username", user.getName());
		return "ddrzAddInit";
	}
	//编辑调度日志
	public void ddrzEdit() throws IOException{
		boolean flag = false;
		try {
			IDdrzUCC ucc = (IDdrzUCC)BkmsContext.getBean("ddrzUCC");
			ucc.editDdrz(ddrzbo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	//查看调度日志详情
	public String ddrzView(){
		String ddrzid = request.getParameter("ddrzid");
		DdrzBO bo = new DdrzBO();
		bo.setDdrz_id(ddrzid);
		try {
			IDdrzUCC ucc = (IDdrzUCC)BkmsContext.getBean("ddrzUCC");
			List<DdrzBO> list = ucc.queryDdrzList(bo);
			if(list!=null&&list.size()>0){
				bo = list.get(0);
			}
			request.setAttribute("ddrz", bo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ddrzView";
	}

	//删除调度日志
	public void deleteDdrz() throws IOException{
		String ddrz_id = Tools.filterNull(request.getParameter("ddrz_id"));
		DdrzBO bo = new DdrzBO();
		bo.setDdrz_id(ddrz_id);
		boolean flag = false;
		try {
			IDdrzUCC ucc = (IDdrzUCC)BkmsContext.getBean("ddrzUCC");
			ucc.deleteDdrz(bo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}
	//修改调度日志初始化数据
	public String ddrzEditQuery(){
		String ddrzid = request.getParameter("ddrzid");
		DdrzBO bo = new DdrzBO();
		bo.setDdrz_id(ddrzid);
		try {
			IDdrzUCC ucc = (IDdrzUCC)BkmsContext.getBean("ddrzUCC");
			List<DdrzBO> list = ucc.queryDdrzList(bo);
			if(list!=null&&list.size()>0){
				bo = list.get(0);
			}
			request.setAttribute("ddrz", bo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ddrzEditQuery";
	}
	//导出调度日志
	public void exportDdrz(){
		try {
			IDdrzUCC ucc = (IDdrzUCC)BkmsContext.getBean("ddrzUCC");
			List<DdrzBO> list = ucc.queryDdrzList(ddrzbo);
			String dateName=null;
			if(ddrzbo!=null){
				if(ddrzbo.getDdrz_rq()!=null && !"".equals(ddrzbo.getDdrz_rq())){
					dateName="" + ddrzbo.getDdrz_rq() + "";
				}else{
					dateName="";
				}
			}
			String tableName = "" + Tools.filterNull(dateName) + ""+  "调度日志表.xls";//excel名
			String titleName = "" + Tools.filterNull(dateName) + ""+  "调度日志表";//title名
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
			sheet.mergeCells(0, 0, 6, 0);
			sheet.addCell(new Label(0, 0, "" + titleName + "",
					Dwcf_center));
			sheet.addCell(new Label(0, 1, "日期",wcf_center));
			sheet.addCell(new Label(1, 1, "时间",wcf_center));
			sheet.addCell(new Label(2, 1, "反映单位",wcf_center));
			sheet.addCell(new Label(3, 1, "问题纪要",wcf_center));
			sheet.addCell(new Label(4, 1, "处理经过及结果",wcf_center));
			sheet.addCell(new Label(5, 1, "未完工作",wcf_center));
			sheet.addCell(new Label(6, 1, "值班人",wcf_center));
			sheet.setColumnView(0, 15);//设置宽度
			sheet.setColumnView(2, 30);//设置宽度
			sheet.setColumnView(3, 40);//设置宽度
			sheet.setColumnView(4, 40);//设置宽度
			sheet.setColumnView(5, 40);//设置宽度
			if(list!=null&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					DdrzBO bo = list.get(i);
					sheet.addCell(new Label(0,i+2,Tools.filterNull(bo.getDdrz_rq())));//日期
					sheet.addCell(new Label(1,i+2,Tools.filterNull(bo.getDdrz_sj())));//时间
					sheet.addCell(new Label(2,i+2,Tools.filterNull(bo.getDdrz_fydw())));//反应单位
					sheet.addCell(new Label(3,i+2,Tools.filterNull(bo.getDdrz_wtjy())));//问题纪要
					sheet.addCell(new Label(4,i+2,Tools.filterNull(bo.getDdrz_cljg())));//处理经过及结果
					sheet.addCell(new Label(5,i+2,Tools.filterNull(bo.getDdrz_wwgz())));//未完工作
					sheet.addCell(new Label(6,i+2,Tools.filterNull(bo.getDdrz_zbr())));//值班人
					
				}
			}
			workbook.write();  
			workbook.close();
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
	public DdrzBO getDdrzbo() {
		return ddrzbo;
	}
	public void setDdrzbo(DdrzBO ddrzbo) {
		this.ddrzbo = ddrzbo;
	}
}
