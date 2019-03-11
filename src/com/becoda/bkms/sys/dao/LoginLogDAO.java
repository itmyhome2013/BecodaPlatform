package com.becoda.bkms.sys.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.pojo.bo.LoginLogBO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-20
 * Time: 13:57:16
 */
public class LoginLogDAO extends GenericDAO {
    /**
     * 新增登录日志
     *
     * @param loginLog log
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void addLoginLog(LoginLogBO loginLog) throws RollbackableException {
        if (null != loginLog.getSessionId()) {
            this.createBo(loginLog);
        }

    }


    /**
     * 查询登录日志
     *
     * @param startdate  e
     * @param enddate    e
     * @param username   e
     * @param personName e
     * @param hostName   e
     * @param ip         e
     * @param vo         e
     * @return e
     * @throws RollbackableException e
     */
    public List queryLoginLog(Timestamp startdate, Timestamp enddate, String username, String personName, String hostName, String ip, PageVO vo) throws RollbackableException {
        try {
            String strHql = "from LoginLogBO l where 1=1 ";
            String strCountHql = "Select count(l) from LoginLogBO l where 1=1 ";
            List list;
            List reList = new ArrayList();
            StringBuffer sql = new StringBuffer();
            StringBuffer where = new StringBuffer();
            where.append(startdate == null ? "" : " and to_char(l.loginTime,'yyyy-MM-dd HH:mm:ss.q')>'" + startdate + "'");
            where.append(enddate == null ? "" : " and to_char(l.loginTime,'yyyy-MM-dd HH:mm:ss.q')<'" + enddate + "'");
            where.append(username == null || "".equals(username) ? "" : " and l.userName like'" + username + "%'");
            where.append(personName == null || "".equals(personName) ? "" : " and l.personName like'" + personName + "%'");
            where.append(hostName == null || "".equals(hostName) ? "" : " and l.hostName='" + hostName + "'");
            where.append(ip == null || "".equals(ip) ? "" : " and l.ip='" + ip + "'");
            if (where.length() > 0) {
                sql.append(where);
            }
            sql.append(" order by l.loginTime desc");
            if (sql.toString() != null && !sql.toString().equals("")) {
                strHql = strHql + " " + sql.toString();
                strCountHql = strCountHql + " " + sql.toString();
            }
            list = pageQuery(vo, strCountHql, strHql);
            if (null == list || list.size() == 0)
                return null;
            else {
                int length = list.size();
                for (int i = 0; i < length; i++) {
                    reList.add(list.get(i));
                }
                return reList;
            }
        } catch (RollbackableException e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }


    public LoginLogBO[] queryLoginLog(String[] sessionId) throws RollbackableException {
        try {
            if (sessionId == null || sessionId.length == 0) {
                List list;
                String sql = "from LoginLogBO l ";
                list = hibernateTemplate.find(sql);
                if (null == list || list.size() == 0) {
                    return null;
                } else {
                    int length = list.size();
                    LoginLogBO[] log = new LoginLogBO[length];
                    for (int i = 0; i < length; i++) {
                        log[i] = (LoginLogBO) list.get(i);
                    }
                    return log;
                }
            } else {
                int count = sessionId.length;
                LoginLogBO[] log = new LoginLogBO[count];
                for (int i = 0; i < count; i++) {
                    log[i] = (LoginLogBO) this.findBoById(LoginLogBO.class, sessionId[i]);
                }
                return log;
            }
        } catch (RollbackableException e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }

    /**
     * 删除数据库登录日志
     *
     * @param sessionId s
     * @throws RollbackableException e
     */
    public void removeLogingLog(String sessionId) throws RollbackableException {
        try {
//            LoginLogBO login = new LoginLogBO();
//            login.setSessionId(sessionId);
            //deleteBo(login);
            deleteBo(findBo(LoginLogBO.class, sessionId));
//            this.deletePo(login);
        } catch (Exception e) {
            throw new RollbackableException("删除失败.", e, this.getClass());
        }
    }

    public void removeLogingLog(LoginLogBO[] log) throws RollbackableException {
        try {
            int count = log.length;
            for (int i = 0; i < count; i++) {
//                deleteBo(log[i]);
                deleteBo(findBo(LoginLogBO.class, log[i].getSessionId()));
            }
        } catch (Exception e) {
            throw new RollbackableException("删除失败.", e, this.getClass());
        }
    }

    /**
     * 根据退出时间为空查询当前用户
     * add by zenghongxia 2015-06-27
     *
     * @return 登录日志数组
     * @throws RollbackableException e
     */
    public LoginLogBO[] queryUserOnline() throws RollbackableException {
        LoginLogBO[] logs = null;
        try {
            List list = hibernateTemplate.find("from LoginLogBO l where l.logoutTime is null and to_char(login_datetime,'yyyy/mm/dd') = to_char(sysdate,'yyyy/mm/dd')");
            if (list != null && list.size() > 0) {
                logs = new LoginLogBO[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    logs[i] = (LoginLogBO) list.get(i);
                }
            }
        } catch (Exception he) {
            throw new RollbackableException("读取数据错误", he, this.getClass());
        }
        return logs;
    }
}
