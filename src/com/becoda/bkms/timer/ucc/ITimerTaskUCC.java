package com.becoda.bkms.timer.ucc;

import com.becoda.bkms.common.exception.RollbackableException;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-5-19
 * Time: 14:15:27
 * To change this template use File | Settings | File Templates.
 */
public interface ITimerTaskUCC {
//    public void createWageInfo() throws RollbackableException;

    public void autoAgeCompute() throws RollbackableException;

    public void autoYearsCompute() throws RollbackableException;
    
    /**
     * 月度动力分配单
     * @throws RollbackableException
     */
    public void auto_yddlfpd_EXT() throws RollbackableException;
    
    /**
     * 能源用户数据日报表
     * @throws RollbackableException
     */
    public void auto_nyyhsjrbb_EXT() throws RollbackableException;
    
    /**
     * 设备监测
     * @throws RollbackableException
     */
    public void auto_sbjc_EXT() throws RollbackableException;
    
}
