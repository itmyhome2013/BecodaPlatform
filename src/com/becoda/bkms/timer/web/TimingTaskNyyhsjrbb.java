package com.becoda.bkms.timer.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.util.Tools;
import com.farm.core.time.TimeTool;
import com.farm.core.util.DBUtil;


/**
 * 定时任务 - 能源用户数据日报表
 * 
 * @author huxuxu
 * 
 */
public class TimingTaskNyyhsjrbb {

	public void autoRealMonitor() throws RollbackableException {
		try {
			
			String lastDate = DateUtil.getLastDay("yyyy-MM-dd"); //昨天
			String lastLastDate = DateUtil.getLastLastDay("yyyy-MM-dd"); //前天

			Statement sta = null;
			Statement pstInsert = null;
			Connection conn = DBUtil.getConnection();
			sta = conn.createStatement();
			pstInsert = conn.createStatement();
			String sql = " select RQ,YNSB_GLBH,to_char(LAST1VALUE,'fm999999999990.000') LAST1VALUE,to_char(LAST2VALUE,'fm999999999990.000') LAST2VALUE,YNSB_NYZL,to_char(LJZ,'fm999999999990.000') LJZ,to_char(SHL,'fm999999999990.000') SHL,to_char(BYJSL,'fm999999999990.000') BYJSL FROM (SELECT cast('"+lastDate+"' AS varchar(10)) AS rq,a.ynsb_glbh,a.ynsb_nyzl,c.sisvalue AS last1value, d.sisvalue AS last2value,c.sisvalue - d.sisvalue AS ljz,(c.sisvalue - d.sisvalue) * 0.05 AS shl,(c.sisvalue - d.sisvalue) * 1.05 AS byjsl  FROM EAST_YNSB a left join EAST_YNSBSIS b on b.ynsb_id = a.ynsb_id left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select max(sistime),sisid FROM zd_test2 WHERE sistime like '"+lastDate+"%' group by sisid)) c  on c.sisid = b.ynsbsis_bs left join (select * FROM zd_test2 WHERE (sistime,sisid) in  (select min(sistime),sisid FROM zd_test2 WHERE sistime like '"+lastDate+"%' group by sisid)) d on d.sisid = b.ynsbsis_bs WHERE b.ynsbsis_islj = '1' and a.ynsb_fid = '3010401101'" +
					")  WHERE 1=1 union all select '"+lastDate+"' as rq ,a.tenantname as YNSB_GLBH,a.totalvalue as LAST1VALUE,b.totalvalue as LAST2VALUE,'电能表' as YNSB_GLBH,to_char((a.totalvalue - b.totalvalue), 'fm999999999990.000')as LJZ,to_char('0', 'fm999999999990.000') as shl,to_char('0', 'fm999999999990.000') AS byjsl from (select t.tenantname,t.meterno,t.totalvalue from zdep_parkmeter t where t.NumericDate='"+lastDate+"')a,(select t.tenantname,t.meterno,t.totalvalue from zdep_parkmeter t where t.NumericDate='"+lastLastDate+"')b where a.meterno = b.meterno";
			
			PreparedStatement pst = conn.prepareStatement("delete from EAST_TJBB_NYYHSJRBB where rq like '"+lastDate+"%'  ");
			pst.execute();
			
			System.out.println(TimeTool.getFormatTimeDate14() + " info : 开始执行" );
			ResultSet rs = sta.executeQuery(sql);
			
			while(rs.next()){
				String rq = Tools.filterNull(rs.getString(1));
				String ynsb_glbh = Tools.filterNull(rs.getString(2));
				String last1value = Tools.filterNull(rs.getString(3));
				String last2value = Tools.filterNull(rs.getString(4));
				String ynsb_nyzl = Tools.filterNull(rs.getString(5));
				String ljz = Tools.filterNull(rs.getString(6));
				String shl = Tools.filterNull(rs.getString(7));
				String byjsl = Tools.filterNull(rs.getString(8));
				
				String insertSQL = "insert into EAST_TJBB_NYYHSJRBB(rq,ynsb_glbh,last1value,last2value,ynsb_nyzl,ljz,shl,byjsl) values ('"+rq+"','"+ynsb_glbh+"','"+last1value+"','"+last2value+"','"+ynsb_nyzl+"','"+ljz+"','"+shl+"','"+byjsl+"')";
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

	public static void main(String[] args) {
		TimingTaskNyyhsjrbb tt = new TimingTaskNyyhsjrbb();
		try {
			tt.autoRealMonitor();
		} catch (RollbackableException e) {
			e.printStackTrace();
		}
	}
}