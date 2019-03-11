package com.becoda.bkms.timer.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSbscBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbStateBO;
import com.becoda.bkms.east.zdsb.ucc.IZdsbUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.farm.core.time.TimeTool;
import com.farm.core.util.DBUtil;


/**
 * 定时任务 - 设备监测
 * 
 * @author zlw
 * 
 */
public class TimingTaskSbjc {
	/**
	 * @throws RollbackableException
	 */
	public void addSbscNextMin() throws RollbackableException {
		try {
			Statement sta = null;
			Statement pstInsert = null;
			Connection conn = DBUtil.getConnection();
			sta = conn.createStatement();
			pstInsert = conn.createStatement();			
			String sql = " select sc.id,sc.zdsb_id,sc.zdsb_qnsc,sc.zdsb_zsc from zdsb_sbsc sc  ";			
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 开始执行" );
			ResultSet rs = sta.executeQuery(sql);
			List<Map<String, Object>> sbscList = new ArrayList<Map<String,Object>>();// 设备时长
			List<Map<String, Object>> ztlist = new ArrayList<Map<String,Object>>();// 设备状态		
			ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据  
	        int columnCount = md.getColumnCount();   //获得列数   
	        while (rs.next()) {  
	            Map<String,Object> rowData = new HashMap<String,Object>();  
	            for (int i = 1; i <= columnCount; i++) {  
	                rowData.put(md.getColumnName(i), rs.getObject(i));  
	            }  
	            sbscList.add(rowData);  	  
	        }  
			String sj = Tools.getSysDate("yyyy-MM-dd HH:mm") + ":00";
			sj = Tools.minusMinute(sj, -2);// 时间减两分钟，查询上两分钟的，因需错开实时数据同步和计算累计时间			
			StringBuffer ztSql = new StringBuffer();
			ztSql.append("  select sis.zdsb_id,sis.zdsb_ssbm,sis.zdsb_sbmc,sis.zdsbsis_bs,zd.sisvalue,sis.zdsb_state,sbsc.zdsb_qnsc,sbsc.zdsb_zsc from" +
					" (select z.zdsb_id zdsb_id,z.zdsb_ssbm zdsb_ssbm,z.zdsb_sbmc zdsb_sbmc,s.zdsbsis_bs zdsbsis_bs,z.zdsb_sbzt zdsb_state  from east_zdsb z" +
					" left join east_zdsbsis s on z.zdsb_id=s.zdsb_id order by zdsb_ssbm desc,zdsb_sbmc)  sis  left join  zd_test2 zd  on sis.zdsbsis_bs=zd.sisid  and zd.sistime='").append(sj).append("'")
					.append("  left join  (select sc.zdsb_id as zdsb_id,round(to_number(TO_DATE(sc.zdsb_qnsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE((SELECT To_char(trunc(sysdate,'yyyy'), 'yyyy-mm-dd hh24:mi:ss') FROM dual),'yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_qnsc," +
							"round(to_number(TO_DATE(sc.zdsb_zsc,'yyyy-mm-dd hh24:mi:ss')-TO_DATE('2018-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'))*24) as zdsb_zsc from zdsb_sbsc sc) sbsc on sis.zdsb_id=sbsc.zdsb_id");
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 开始执行" );
			ResultSet ztRs = sta.executeQuery(ztSql.toString());
			ResultSetMetaData ztMd = ztRs.getMetaData(); //获得结果集结构信息,元数据  
	        int ztColumnCount = md.getColumnCount();   //获得列数   
	        while (ztRs.next()) {  
	            Map<String,Object> ztRowData = new HashMap<String,Object>();  
	            for (int i = 1; i <= ztColumnCount; i++) {  
	            	ztRowData.put(ztMd.getColumnName(i), ztRs.getObject(i));  
	            }  
	            ztlist.add(ztRowData);  	  
	        }			
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
								if (!Tools.stringIsNull(val) && Float.parseFloat(val) > 1) {
									if (!Tools.stringIsNull(qnsc)) {
										if (qnsc.substring(0, 4).equals(Tools.getSysYear())) {
											qnsc = Tools.minusMinute(qnsc, 1);
											bo.setZdsb_qnsc(qnsc);
											ztBo.setZdsb_qnsc(qnsc);
										} else {
											SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											// 设置指定日期
											Calendar cal = Calendar.getInstance();
											cal.set(Calendar.YEAR, Integer.parseInt(Tools.getSysYear()));
											cal.set(Calendar.MONTH, 0);
											cal.set(Calendar.DAY_OF_MONTH, 1);
											cal.set(Calendar.HOUR_OF_DAY, 0);
											cal.set(Calendar.MINUTE, 0);
											cal.set(Calendar.SECOND, 0);
											cal.set(Calendar.MILLISECOND, 0);
											Date date = new Date(cal.getTimeInMillis());
											// 格式化
											String dateStr = dateFormat.format(date);
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
								editZdsbSbsc(bo);//保存重点设备设备时长
								
								ztBo.setZdsb_id(sbIdSb);// 设备id
								ztBo.setSisvalue(val);// sis值
								ztBo.setZdsb_state(Tools.filterNull(ztMap.get("ZDSB_STATE")));// 设备状态
								ztBo.setZdsb_ssbm(Tools.filterNull(ztMap.get("ZDSB_SSBM")));// 所属部门
								ztBo.setZdsb_sbmc(Tools.filterNull(ztMap.get("ZDSB_SBMC")));// 设备名称
								ztBo.setZdsb_jxrq(sj);
								editZdsbState(ztBo);//保存重点设备设备状态
								
							}
						}
					}
				}

			}
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 执行结束" );
			DBUtil.closeAll(rs, sta, conn); // 关闭链接
		} catch (Exception e) {
			throw new RollbackableException("同步数据失败", e, getClass());
		}
	}
	/**
	 * @param bo
	 */
	public void editZdsbSbsc(ZdsbSbscBO bo){
		if(bo.getId()!=null && !bo.getId().equals("")){ 
			//更新
			
		}else{ 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 设置指定日期
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(Tools.getSysYear()));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date date = new Date(cal.getTimeInMillis());
			// 格式化
			String dateStr = dateFormat.format(date);
			bo.setId(SequenceGenerator.getUUID());
			bo.setZdsb_qnsc(dateStr);
			bo.setZdsb_zsc(dateStr);
			//新增
			
		}
	}
	/**
	 * @param bo
	 */
	public void editZdsbState(ZdsbStateBO bo){
		//System.out.println("#########: " + bo.getSisvalue());
		if(bo.getZt_id()!=null&&!bo.getZt_id().equals("")){ 
			//更新
			
		}else{ 
			bo.setZt_id(SequenceGenerator.getUUID());
			bo.setZdsb_jxrq(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
			//新增
			try {
			Statement sta = null;
			Connection conn = DBUtil.getConnection();
			sta = conn.createStatement();
			String sql = "insert into east_zdsb_state(zt_id,zdsb_id,zdsb_jxrq,zdsb_jxry,zdsb_state,zdsb_ssbm,zdsb_sbmc,sisvalue,zdsb_qnsc,zdsb_zsc) " +
					" values('"+SequenceGenerator.getUUID()+"','"+bo.getZdsb_id()+"','"+bo.getZdsb_jxrq()+"','"+Tools.filterNull(bo.getZdsb_jxry())+"','"+bo.getZdsb_state()+"'" +
							",'"+bo.getZdsb_ssbm()+"','"+bo.getZdsb_sbmc()+"','"+bo.getSisvalue()+"','"+bo.getZdsb_qnsc()+"','"+bo.getZdsb_zsc()+"')";
			System.out.println(sql);
			sta.addBatch(sql);
			sta.executeBatch();
			DBUtil.closeAll(null, sta, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		TimingTaskSbjc tt = new TimingTaskSbjc();
		try {
			tt.addSbscNextMin();
		} catch (RollbackableException e) {
			e.printStackTrace();
		}
	}	
}