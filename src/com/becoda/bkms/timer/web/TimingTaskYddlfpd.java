package com.becoda.bkms.timer.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.farm.core.time.TimeTool;
import com.farm.core.util.DBUtil;


/**
 * 定时任务 - 月度动力分配单
 * 
 * @author huxuxu
 * 
 */
public class TimingTaskYddlfpd {
	
	private String date; // 要格式化的原日期
	private String formatDate; //返回格式化后的日期
	private String formatLastDate; //返回格式化后的上(月或日)日期

	public void autoRealMonitor() throws RollbackableException {
		try {
			
			String lastDate = DateUtil.getLastYearMonth("yyyy-MM"); //上月
			String lastLastDate = DateUtil.getLastLastYearMonth("yyyy-MM"); //上上月

			Statement sta = null;
			Statement pstInsert = null;
			Connection conn = DBUtil.getConnection();
			sta = conn.createStatement();
			pstInsert = conn.createStatement();
			String sql = "select a.ynsb_fid,a.rq,a.ynsb_glbh,sum(a.LAST1VALUE) as LAST1VALUE,sum(a.LAST2VALUE) as LAST2VALUE,a.YNSB_NYZL,sum(a.LJZ) as ljz,sum(a.SHL) as SHL,sum(a.byjsl) as byjsl from(" +
					" select ynsb_fid,RQ,YNSB_GLBH,to_char(LAST1VALUE,'fm999999999990.000') LAST1VALUE,to_char(LAST2VALUE,'fm999999999990.000') LAST2VALUE,YNSB_NYZL,to_char(LJZ,'fm999999999990.000') LJZ,to_char(SHL,'fm999999999990.000') SHL,to_char(BYJSL,'fm999999999990.000') BYJSL FROM (SELECT a.ynsb_fid, cast('"+lastDate+"' AS varchar(10)) AS rq,a.ynsb_glbh,a.ynsb_nyzl,c.sisvalue AS last1value, d.sisvalue AS last2value,c.sisvalue - d.sisvalue AS ljz,(c.sisvalue - d.sisvalue) * 0.05 AS shl,(c.sisvalue - d.sisvalue) * 1.05 AS byjsl  FROM EAST_YNSB a left join EAST_YNSBSIS b on b.ynsb_id = a.ynsb_id left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select max(sistime),sisid FROM zd_test2 WHERE sistime like '"+lastDate+"%' group by sisid)) c  on c.sisid = b.ynsbsis_bs left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select max(sistime),sisid FROM zd_test2 WHERE sistime like '"+lastLastDate+"%' group by sisid)) d on d.sisid = b.ynsbsis_bs WHERE b.ynsbsis_islj = '1' and a.ynsb_fid ='3010401101'" +
					" union " +
					" SELECT a.EAST_SJBL_ZDJZ as ybsb_fid, cast('"+lastDate+"' AS varchar(10)) AS rq ,a.east_sjbl_sbbh as ynsb_glbh,a.east_sjbl_nyzl as ynsb_nyzl,a.east_sjbl_ljz as last1value,b.east_sjbl_ljz as last2value, a.east_sjbl_ljz - b.east_sjbl_ljz as ljz,(a.east_sjbl_ljz - b.east_sjbl_ljz) * 0.05 as shl,(a.east_sjbl_ljz - b.east_sjbl_ljz) * 1.05 as byjsl from (select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"+lastDate+"%' and east_sjbl_nyzl not in ('6', '7') group by east_sjbl_sbid)) a left join ( select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"+lastLastDate+"%' and east_sjbl_nyzl not in ('6', '7') group by east_sjbl_sbid)) b on b.east_sjbl_sbid = a.east_sjbl_sbid " +
					" union " +
					" SELECT a.EAST_SJBL_ZDJZ as ybsb_fid, cast('"+lastDate+"' AS varchar(10)) AS rq ,a.east_sjbl_sbbh as ynsb_glbh,a.east_sjbl_nyzl as ynsb_nyzl,a.east_sjbl_total as last1value,b.east_sjbl_total as last2value, a.east_sjbl_total - b.east_sjbl_total as ljz,(a.east_sjbl_total - b.east_sjbl_total) * 0.05 as shl,(a.east_sjbl_total - b.east_sjbl_total) * 1.05 as byjsl from (select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"+lastDate+"%' and east_sjbl_nyzl in('6','7') group by east_sjbl_sbid) ) a left join ( select * from EAST_SJBL_MANUAL_MAKEUP where (east_sjbl_date,east_sjbl_sbid) in (select max(east_sjbl_date),east_sjbl_sbid from EAST_SJBL_MANUAL_MAKEUP where east_sjbl_date like '"+lastLastDate+"%' and east_sjbl_nyzl in('6','7') group by east_sjbl_sbid)) b on b.east_sjbl_sbid = a.east_sjbl_sbid " + 
					")  WHERE 1=1) a group by a.ynsb_glbh,a.ynsb_nyzl,a.rq,a.ynsb_fid";
			
			PreparedStatement pst = conn.prepareStatement("delete from EAST_TJBB_YDDLFPD where rq like '"+lastDate+"%' ");
			pst.execute();
			
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 开始执行" );
			ResultSet rs = sta.executeQuery(sql);
			
			while(rs.next()){
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
				
				String insertSQL = "insert into EAST_TJBB_YDDLFPD(id,ynsb_fid,rq,ynsb_glbh,last1value,last2value,ynsb_nyzl,ljz,shl,byjsl) values ('"+id+"','"+ynsbfid+"','"+rq+"','"+ynsb_glbh+"','"+last1value+"','"+last2value+"','"+ynsb_nyzl+"','"+ljz+"','"+shl+"','"+byjsl+"')";
				pstInsert.addBatch(insertSQL);
			}
			
			pstInsert.executeBatch();
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 执行结束" );
			DBUtil.closeAll(rs, sta, conn); // 关闭链接
			DBUtil.closeStatement(pst);
			

		} catch (Exception e) {
			throw new RollbackableException("同步数据失败", e, getClass());
		}
	}

	/**
	 * 格式化年月
	 * @return
	 */
	public String formartDateMonth(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
		try {
			if(date.equals("")){ //日期为空 显示昨天日期
				date = DateUtil.getLastYearMonth("yyyy-MM");
			}
			Date d = sdf.parse(date) ;
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.MONTH, 0);
			
			Calendar cal_last = Calendar.getInstance();
			cal_last.setTime(d);
			cal_last.add(Calendar.MONTH, -1);
			
			formatDate = new SimpleDateFormat("yyyy年MM月").format(cal.getTime());
			formatLastDate = new SimpleDateFormat("yyyy年MM月").format(cal_last.getTime()); 
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return "success";
	}
	
	/**
	 * 格式化年月日
	 * @return
	 */
	public String formartDateDay(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		try {
			if(date.equals("")){ //日期为空 显示昨天日期
				date = DateUtil.getLastDay("yyyy-MM-dd");
			}
			Date d = sdf.parse(date) ;
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DAY_OF_YEAR, 0);
			
			Calendar cal_last = Calendar.getInstance();
			cal_last.setTime(d);
			cal_last.add(Calendar.DAY_OF_YEAR, -1);
			
			formatDate = new SimpleDateFormat("yyyy年MM月dd日").format(cal.getTime());
			formatLastDate = new SimpleDateFormat("yyyy年MM月dd日").format(cal_last.getTime()); 
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return "success";
	}

	public static void main(String[] args) {
		TimingTaskYddlfpd tt = new TimingTaskYddlfpd();
		try {
			tt.autoRealMonitor();
		} catch (RollbackableException e) {
			e.printStackTrace();
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public String getFormatLastDate() {
		return formatLastDate;
	}

	public void setFormatLastDate(String formatLastDate) {
		this.formatLastDate = formatLastDate;
	}
	
}