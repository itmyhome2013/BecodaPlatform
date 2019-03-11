package com.becoda.bkms.pms.pojo.bo;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-6-27
 * Time: 21:24:55
 * To change this template use File | Settings | File Templates.
 */
public class LoginLogBO {
    /**
     * @pdOid 1580cac1-82fd-4eb1-abdb-0a66b254eef2
     */
    private String sessionId;
    /**
     * @pdOid efdf4a19-1d95-4d88-adc3-b019075e1c9b
     */
    private String personId;
    /**
     * 时间精确到时分秒 例如：2015-7-08 15:05:56
     *
     * @pdOid 755af442-3fba-4ea0-81c8-dc7f437cbbca
     */
    private Timestamp loginTime;
    /**
     * @pdOid a1a121da-556a-47ce-8596-89a210e35787
     */
    private String userName;
    /**
     * @pdOid dda425a9-dc75-45ec-ba25-b7ccf0bd8a1b
     */
    private String personName;
    /**
     * @pdOid 28cce2af-fc5b-4465-93c6-b9b9e27b829d
     */
    private String hostName;
    /**
     * @pdOid dcf016c0-4b9f-419f-b489-c94aa30a0278
     */
    private String ip;
    /**
     * 时间精确到时分秒 例如：2015-7-08 15:05:56
     *
     * @pdOid f1572b5e-42b0-4954-b0fc-bc209a7ba875
     */
    private Timestamp logoutTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }
}
