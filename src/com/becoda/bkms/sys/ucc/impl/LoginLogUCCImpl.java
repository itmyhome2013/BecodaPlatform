package com.becoda.bkms.sys.ucc.impl;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.pojo.bo.LoginLogBO;
import com.becoda.bkms.sys.service.LoginLogService;
import com.becoda.bkms.sys.ucc.ILoginLogUCC;
import org.jdom.JDOMException;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-20
 * Time: 13:54:12
 */
public class LoginLogUCCImpl implements ILoginLogUCC {
    private LoginLogService loginLogService;

    public LoginLogService getLoginLogService() {
        return loginLogService;
    }

    public void setLoginLogService(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }


    public void createLog(String sessionId, String personId, String personName, String userName, String hostName, String ip, Timestamp loginTime) throws RollbackableException {
        loginLogService.login(sessionId, personId, personName, userName, hostName, ip, loginTime);
    }


    public void removeLog(String sessionId, Timestamp logoutTime) throws RollbackableException {
        loginLogService.logout(sessionId, logoutTime);
    }

    public List queryLoginLog(String startDate, String endDate, String userName, String personName, String hostName, String ip, PageVO vo) throws RollbackableException {
        return loginLogService.queryLoginLog(startDate, endDate, userName, personName, hostName, ip, vo);
    }

    public void removeLoginLog(LoginLogBO[] logs) throws RollbackableException {
        loginLogService.removeLoginLog(logs);

    }

    public void deleteAndExportLoginLog(String[] sessionId, int fileType, String rpath) throws RollbackableException {
        loginLogService.expLoginLog(sessionId, fileType, rpath);
    }

    public void expLoginLog(LoginLogBO[] log, int fileType, String rootPath) throws RollbackableException {
        loginLogService.expLoginLog(log, fileType, rootPath);
    }

    public void expLogFile2Excel(LoginLogBO[] log) throws RollbackableException {
        loginLogService.expLogFile2Excel(log);
    }

    public void expLogFile2Xml(LoginLogBO[] log) throws RollbackableException {
        loginLogService.expLogFile2Xml(log);
    }

    public void delDiskLogFile(String[] fileName, String rootPath) {
        loginLogService.delDiskLogFile(fileName, rootPath);
    }

    public String[] getDiskLogFile(String rootPath) {
        return loginLogService.getDiskLogFile(rootPath);
    }

    public void BuildXMLDoc(LoginLogBO[] log, File f) throws IOException, JDOMException {
        loginLogService.BuildXMLDoc(log, f);
    }

    public LoginLogBO[] queryAllLoginLog() throws RollbackableException {
        return loginLogService.queryAllLoginLog();
    }
}

