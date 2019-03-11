package com.becoda.bkms.sys.pojo.vo;


/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-4-17
 * Time: 17:04:32
 * To change this template use File | Settings | File Templates.
 */
public class OperLogVO {
    /**
     * 被操作人
     */
    private String operatorName;
    /**
     * 操作类型
     */
    private String operType;

    /**
     * 操作人
     */
    private String operName;

    /**
     * 开始时间 例如：2015-7-08
     */
    private String startTime;
    /**
     * 结束时间 例如：2015-7-08
     */
    private String endTime;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }


    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}


