package com.becoda.bkms.common.queue;

import com.becoda.bkms.common.exception.BkmsException;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-27
 * Time: 11:13:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class QueueTask implements java.io.Serializable {


    /**
     * 队列任务编号，由hibernate自动生成
     * 32位uuid，队列任务表主键
     */
    private String queueId;
    /**
     * jmsId 任务加入jms队列中自动生成
     * 当jms使用spring包装的模板时 jmsid = null
     */
    private String jmsId;
    /**
     * 增加任务的用户personid
     */
    private String personId;
    /**
     * 姓名中文显示用
     */
    private String personName;
    /**
     * 部门编号
     */
    private String deptId;
    /**
     * 部门的treeid 用来查询本单位队伍任务
     */
    private String deptTreeId;
    /**
     * 部门名词中文 显示用
     */
    private String deptName;
    /**
     * 任务名称 显示用
     */
    private String taskName;
    /**
     * 队列任务执行结果.长度不要超过500个英文字符或250个中文字符
     * 结果可以包含url,比如数据导出的任务队列.
     * result可以为
     * xx中级职称人员导出成功[url='/file/exp/xxxx.csv']中级职称.csv[/url]
     */
    private String result;
    private Timestamp inQueueTime;
    private Timestamp executedTime;
    private String moduleName;

    /**
     * null = STATUS_WATTING
     */
    private String status;
    public static final String STATUS_WAITTING = "WATTING";
    public static final String STATUS_FAIL = "FAIL";
    public static final String STATUS_COMPLETE = "COMPLETE";

    public abstract void doTask() throws Exception;

    public abstract void onError(Exception e);

    public void execute() {
        try {
            doTask();
        } catch (Exception e) {
            new BkmsException(e, QueueTask.class);
            onError(e);
        }
    }


    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptTreeId() {
        return deptTreeId;
    }

    public void setDeptTreeId(String deptTreeId) {
        this.deptTreeId = deptTreeId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getJmsId() {
        return jmsId;
    }

    public void setJmsId(String jmsId) {
        this.jmsId = jmsId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getInQueueTime() {
        return inQueueTime;
    }

    public void setInQueueTime(Timestamp inQueueTime) {
        this.inQueueTime = inQueueTime;
    }

    public Timestamp getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(Timestamp executedTime) {
        this.executedTime = executedTime;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
