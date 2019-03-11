package com.becoda.bkms.sys.pojo.vo;


/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-4-19
 * Time: 9:21:51
 * To change this template use File | Settings | File Templates.
 */
public class LoginLogVO {
    /**
     * 用户名
     */
    private String personId;
    /**
     * 开始时间 例如：2015-7-08
     */
    private String startTime;
    /**
     * 人员姓名
     */
    private String personName;
    /**

     */
    private String hostName;
    /**

     */
    private String ip;
    /**
     * 结束时间 例如：2015-7-08
     */
    private String endTime;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}


