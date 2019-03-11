package com.becoda.bkms.timer.ucc.impl;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.emp.EmpConstants;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
//import com.becoda.bkms.lead.LeadConstants;
//import com.becoda.bkms.lead.ucc.ILeadHisItemUCC;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.sys.api.SysAPI;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.timer.ucc.ITimerTaskUCC;
import com.becoda.bkms.timer.web.TimingTaskNyyhsjrbb;
import com.becoda.bkms.timer.web.TimingTaskSbjc;
import com.becoda.bkms.timer.web.TimingTaskYddlfpd;
import com.becoda.bkms.tls.TlsConstants;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.cache.SysCacheTool;
//import com.becoda.bkms.kq.util.KqTools;
//import com.becoda.bkms.kq.KqConstants;
//import com.becoda.bkms.kq.dao.KqVacationRuleDAO;
//import com.becoda.bkms.kq.pojo.bo.KqVacationRule;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-5-19
 * Time: 14:26:48
 * To change this template use File | Settings | File Templates.
 */
public class TimerTaskUCCImpl implements ITimerTaskUCC {
    private ActivePageService activePageService;
    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

    
    /**
     * 月度动力分配单
     */
    @Override
	public void auto_yddlfpd_EXT() throws RollbackableException {
		// TODO Auto-generated method stub
    	TimingTaskYddlfpd tt = new TimingTaskYddlfpd();
    	tt.autoRealMonitor();
		System.out.println("********** SUCCESS! ************");
	}
    
    /**
     * 能源用户数据日报表
     */
    @Override
    public void auto_nyyhsjrbb_EXT() throws RollbackableException {
    	TimingTaskNyyhsjrbb tt = new TimingTaskNyyhsjrbb();
    	tt.autoRealMonitor();
		System.out.println("********** SUCCESS! ************");
    }
    
    
    /**
     * 设备监测 
     */
    @Override
	public void auto_sbjc_EXT() throws RollbackableException {
		// TODO Auto-generated method stub
    	TimingTaskSbjc tt = new TimingTaskSbjc();
    	tt.addSbscNextMin();
		System.out.println("********** SUCCESS! ************");
	}

    //计算年龄
    public void autoAgeCompute() throws RollbackableException {
        try {
                //计算年龄
                String sql = "update A001 set A001205 = to_char(floor((sysdate - to_date(A001011,'yyyy-MM-dd')) / 365.25))";
                activePageService.executeSql(sql);
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new RollbackableException("计算年龄", e, getClass());
        }
    }

    //计算工龄、本系统工作年限、金融从业年限
    public void autoYearsCompute() throws RollbackableException {
        try {
            //计算工龄
            String sql="update A001 set A001710=to_char(floor((sysdate - to_date(A001041,'yyyy-MM-dd')) / 365.25)) where A001730='00900'";
            activePageService.executeSql(sql);
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new RollbackableException("计算年龄", e, getClass());
        }
    }

    

    
}
