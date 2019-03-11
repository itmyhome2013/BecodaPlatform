package com.becoda.bkms.sys.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.pojo.bo.LoginLogBO;
import org.jdom.JDOMException;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-20
 * Time: 13:53:47
 */
public interface ILoginLogUCC {

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
     * @pdOid b1913d60-074d-4a5a-8c5a-91616bac5027
     */
    public void createLog(String sessionId, String personId, String personName, String userName, String hostName, String ip, Timestamp loginTime) throws BkmsException;

    /**
     * 记录注销日志
     *
     * @param logoutTime
     * @param sessionId
     * @pdOid bb19c748-f7ef-42a2-81c8-fb13eaf9b9e2
     */
    public void removeLog(String sessionId, Timestamp logoutTime) throws BkmsException;

    /**
     * 查询登录日志
     *
     * @param startDate  开始时间 timestamp in format yyyy-mm-dd hh:mm:ss
     * @param endDate    开始时间  timestamp in format yyyy-mm-dd hh:mm:ss
     * @param userName
     * @param personName
     * @param hostName
     * @param ip
     * @pdOid dd34fe82-309a-455b-9e33-e01b40bf3dfc
     */
    public List queryLoginLog(String startDate, String endDate, String userName, String personName, String hostName, String ip, PageVO vo) throws BkmsException;

    /**
     * 删除系统日志记录
     *
     * @param sessionId
     * @throws BkmsException
     */
    public void removeLoginLog(LoginLogBO[] sessionId) throws BkmsException;

    /**
     * 导出登录日志到excel文件 ,删除系统记录
     *
     * @param sessionId 日志id
     * @param fileType  文件类型 1 xls,2 mdb,3 xml
     * @pdOid 8d9d1202-7e5a-413c-9c4b-d5f46ff8a811
     */
    public void deleteAndExportLoginLog(String[] sessionId, int fileType, String rpath) throws BkmsException;

    /**
     * 导出登录日志到excel文件 ,删除系统记录
     *
     * @param log
     * @param fileType 文件类型 1 xls,2 mdb,3 xml
     * @pdOid 8d9d1202-7e5a-413c-9c4b-d5f46ff8a811
     */
    public void expLoginLog(LoginLogBO[] log, int fileType, String rootPath) throws BkmsException;

    /**
     * 导出登录日志文件。查询出所有日志，生成日志文件，并删除所有日志记录
     *
     * @pdOid e189f766-867e-46a9-a8ec-e6d8fbf35010
     */
    public void expLogFile2Excel(LoginLogBO[] log) throws BkmsException;


    public void expLogFile2Xml(LoginLogBO[] log) throws BkmsException;

    /**
     * 删除磁盘日志文件
     *
     * @param fileName
     * @pdOid c2fda429-56db-40f0-a592-31eeb9fa6d1a
     */
    public void delDiskLogFile(String[] fileName, String rootPath);

    /**
     * 得到磁盘的日志文件列表
     *
     * @return
     */

    public String[] getDiskLogFile(String rootPath);

    /**
     * 导出xml的方法
     *
     * @param log
     * @param f
     * @throws java.io.IOException
     * @throws org.jdom.JDOMException
     */

    public void BuildXMLDoc(LoginLogBO[] log, File f) throws IOException, JDOMException;

    public LoginLogBO[] queryAllLoginLog() throws BkmsException;
}
