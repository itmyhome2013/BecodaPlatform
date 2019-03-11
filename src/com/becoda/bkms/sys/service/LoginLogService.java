package com.becoda.bkms.sys.service;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.pojo.bo.LoginLogBO;
import com.becoda.bkms.sys.dao.LoginLogDAO;
import com.becoda.bkms.util.Tools;
import jxl.Workbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-20
 * Time: 14:23:03
 */
public class LoginLogService {

    /**
     * @pdOid e2425923-0f62-4809-838e-4989fa25242b
     */
    private LoginLogDAO loginLogDAO;

    public LoginLogDAO getLoginLogDAO() {
        return loginLogDAO;
    }

    public void setLoginLogDAO(LoginLogDAO loginLogDAO) {
        this.loginLogDAO = loginLogDAO;
    }

    private String path;

    private String setLogPath(String rootPath) {
        Constants.PATH_FILE_LOG_LOGIN = rootPath;
        path = rootPath + Constants.URL_FILE_LOG_LOGIN;
        return path;
    }


    /**
     * 记录登录日志
     *
     * @param sessionId  sessionId
     * @param personId   人员id
     * @param personName 人员姓名
     * @param userName   用户名
     * @param hostName   机器名
     * @param ip         ip地址
     * @param loginTime  登录时间
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void login(String sessionId, String personId, String personName, String userName, String hostName, String ip, Timestamp loginTime) throws RollbackableException {
        LoginLogBO login = new LoginLogBO();
        login.setSessionId(sessionId);
        login.setPersonId(personId);
        login.setPersonName(personName);
        login.setUserName(userName);
        login.setLoginTime(loginTime);
        login.setHostName(hostName);
        login.setIp(ip);
        LoginLogBO log = (LoginLogBO) loginLogDAO.findBoById(LoginLogBO.class, sessionId);
        if (log != null)
            loginLogDAO.deleteBo(log);
        loginLogDAO.addLoginLog(login);
    }

    /**
     * 记录注销日志
     *
     * @param logoutTime logoutTime
     * @param sessionId  sessionId
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void logout(String sessionId, Timestamp logoutTime) throws RollbackableException {
        LoginLogBO bo = (LoginLogBO) loginLogDAO.findBoById(LoginLogBO.class, sessionId);
        if (bo != null) {
            bo.setLogoutTime(logoutTime);
            loginLogDAO.updateBo(bo.getSessionId(), bo);
        }
    }

    /**
     * 查询登录日志
     *
     * @param startDate  开始时间 timestamp in format yyyy-mm-dd hh:mm:ss
     * @param endDate    开始时间  timestamp in format yyyy-mm-dd hh:mm:ss
     * @param userName   user
     * @param personName per name
     * @param hostName   hostname
     * @param ip         ip
     * @param vo         e
     * @return List
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public List queryLoginLog(String startDate, String endDate, String userName, String personName, String hostName, String ip, PageVO vo) throws RollbackableException {
        Timestamp st = null;
        Timestamp et = null;
        if (startDate != null && !"".equals(startDate)) {
            st = Timestamp.valueOf(Tools.getDateByFormat(startDate, "yyyy-MM-dd HH:mm:ss"));
        }
        if (endDate != null && !"".equals(endDate)) {
            et = Timestamp.valueOf(Tools.getDateByFormat(endDate, "yyyy-MM-dd HH:mm:ss"));
        }
        return loginLogDAO.queryLoginLog(st, et, userName, personName, hostName, ip, vo);
    }

    /**
     * 删除系统日志记录
     *
     * @param logBo bo
     * @throws RollbackableException e
     */
    public void removeLoginLog(LoginLogBO[] logBo) throws RollbackableException {
        if (logBo == null) return;
        loginLogDAO.removeLogingLog(logBo);
    }

    /**
     * 导出登录日志到excel文件 ,删除系统记录
     *
     * @param sessionId 日志id
     * @param fileType  文件类型 1 xls,2 mdb,3 xml
     * @param rpath     path
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void expLoginLog(String[] sessionId, int fileType, String rpath) throws RollbackableException {
        LoginLogBO[] log = loginLogDAO.queryLoginLog(sessionId);
        if (log != null) {
            this.expLoginLog(log, fileType, rpath);
        }
    }

    /**
     * 导出登录日志到excel文件 ,删除系统记录
     *
     * @param log      log bo
     * @param fileType 文件类型 1 xls,2 mdb,3 xml
     * @param rootPath e
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void expLoginLog(LoginLogBO[] log, int fileType, String rootPath) throws RollbackableException {
        if (log == null) {
            return;
        }
        this.setLogPath(rootPath);
        if (fileType == 1) {
            expLogFile2Excel(log);
        } else if (fileType == 3) {
            expLogFile2Xml(log);
        }
        loginLogDAO.removeLogingLog(log);
    }


    //导出登录日志文件。查询出所有日志，生成日志文件，并删除所有日志记录
    public void expLogFile2Excel(LoginLogBO[] log) throws RollbackableException {
        try {
            String dt = Tools.getSysDate("yyyyMMddHHmmss");
            File fDir = new File(path);
            if (fDir.exists() && fDir.isDirectory()) {
            } else {
                fDir.mkdirs();
            }

            File f = new File(path + System.getProperty("file.separator") + dt + ".xls");
            if (log != null) {
                LoginLogBO tmp = new LoginLogBO();
                int count = log.length;
                jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(f);
                jxl.write.WritableSheet ws = wwb.createSheet("Sheet 1", 0);
                jxl.write.Label labelC;
                for (int i = 0; i < count + 1; i++) {
                    if (i != 0) {
                        tmp = log[i - 1];
                    }
                    labelC = new jxl.write.Label(0, i, i == 0 ? "用户登陆sessionId" : Tools.filterNull(tmp.getSessionId()));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(1, i, i == 0 ? "用户id" : Tools.filterNull(tmp.getPersonId()));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(2, i, i == 0 ? "用户名" : Tools.filterNull(tmp.getUserName()));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(3, i, i == 0 ? "姓名" : Tools.filterNull(tmp.getPersonName()));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(4, i, i == 0 ? "主机名" : Tools.filterNull(tmp.getHostName()));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(5, i, i == 0 ? "主机ip地址" : Tools.filterNull(tmp.getIp()));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(6, i, i == 0 ? "登陆时间" : Tools.filterNull(tmp.getLoginTime().toString()));
                    ws.addCell(labelC);
                    String logouttime = "";
                    if (tmp.getLogoutTime() != null) {
                        logouttime = tmp.getLogoutTime().toString();
                    }
                    labelC = new jxl.write.Label(7, i, i == 0 ? "退出时间" : Tools.filterNull(logouttime));
                    ws.addCell(labelC);
                }
                wwb.write();
                wwb.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException("导出日志失败", e, this.getClass());
        }

    }


    public void expLogFile2Xml(LoginLogBO[] log) throws RollbackableException {
        try {

            File fDir = new File(path);
            if (fDir.exists() && fDir.isDirectory()) {
            } else {
                fDir.mkdirs();
            }
            String dt = Tools.getSysDate("yyyyMMddHHmmss");
            File file = new File(path + System.getProperty("file.separator") + dt + ".xml");
            if (log != null) {
                this.BuildXMLDoc(log, file);
            }
        } catch (Exception e) {
            throw new RollbackableException("导出日志失败", e, this.getClass());
        }

    }

    //  删除磁盘日志文件
    public void delDiskLogFile(String[] fileName, String rootPath) {
        this.setLogPath(rootPath);
        int count = fileName.length;
        for (int i = 0; i < count; i++) {
            File f = new File(path + System.getProperty("file.separator") + fileName[i]);
            f.delete();
        }
    }

    //     * 得到磁盘的日志文件列表
    public String[] getDiskLogFile(String rootPath) {
        this.setLogPath(rootPath);
        File f = new File(path);
        return f.list();
    }

    //导出xml的方法
    public void BuildXMLDoc(LoginLogBO[] log, File f) throws IOException, JDOMException {
        try {
            if (log != null) {
                int count = log.length;
                Element root, id;
                Document Doc;
                root = new Element("Login_information");
                Doc = new Document(root);
                root = Doc.getRootElement();
                List login;
                login = root.getChildren();
                for (int i = 0; i < count; i++) {
                    id = new Element("login_log");
                    id.addContent(new Element("sessionId").setText(log[i].getSessionId()));
                    id.addContent(new Element("personId").setText(log[i].getPersonId()));
                    String logon = "";
                    if (log[i].getLoginTime() != null) {
                        logon = log[i].getLoginTime().toString();
                    }
                    id.addContent(new Element("loginTime").setText(logon));
                    String logout = "";
                    if (log[i].getLogoutTime() != null) {
                        logout = log[i].getLogoutTime().toString();
                    }
                    id.addContent(new Element("logoutTime").setText(logout));
                    id.addContent(new Element("userName").setText(log[i].getUserName()));
                    id.addContent(new Element("personName").setText(log[i].getPersonName()));
                    id.addContent(new Element("hostName").setText(log[i].getHostName()));
                    id.addContent(new Element("ip").setText(log[i].getIp()));
                    login.add(id);
                }
                XMLOutputter XMLOut = new XMLOutputter();
                XMLOut.output(Doc, new FileOutputStream(f));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoginLogBO[] queryAllLoginLog() throws RollbackableException {
        return loginLogDAO.queryLoginLog(null);
    }

}
