package com.becoda.bkms.common.exception;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-14
 * Time: 16:35:20
 * To change this template use File | Settings | File Templates.
 */
public class BkmsException extends Exception {
    /**
     * 人为创建一个BkmsException异常
     * 使用info级
     */
    public BkmsException(String info, Class clazz) {
        if (null != clazz) {
            if (null == info) {
                info = "消息内容为空";
            }
            this.message = info;
            this.isErrorMessage = false;
            Logger log = Logger.getLogger(clazz);
            log.info(info);
        }
    }

    public BkmsException(String info) {
        this.message = info;
        this.isErrorMessage = false;
    }

    /**
     * 人为创建一个BkmsException异常
     * 使用error级,向日志中记录消息同时记录堆栈信息
     */
    public BkmsException(String error, Exception e, Class clazz) {
        super(e);
        message = error;
        this.isErrorMessage = true;
        Logger log = Logger.getLogger(clazz);
        log.error(message);
        if (null != e) {
            log.error(e.toString());
            StackTraceElement[] trace = this.getCause().getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                log.error("\tat " + trace[i]);
            }
            //当日志中记录的错误信息不足以分析出错误原因.需要在这里追加引起exception"causedException"的错误堆栈输出.
        }

        //writeErrorLog(message, e, log);
    }

    public BkmsException(Throwable e, Class clazz) {
        super(e);
        this.isErrorMessage = true;
        Logger log = Logger.getLogger(clazz);
        log.error(message);
        if (null != e) {
            message = e.getMessage();
            log.error(e.toString());
            StackTraceElement[] trace = this.getCause().getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                log.error("\tat " + trace[i]);
            }
            //当日志中记录的错误信息不足以分析出错误原因.需要在这里追加引起exception"causedException"的错误堆栈输出.
        }
        //writeErrorLog(cause.getMessage(), cause, log);
    }

    private String message;
    private boolean isErrorMessage = true;

    public String getFlag() {
        if (isErrorMessage)
            return "错误：";
        else
            return "信息：";
    }

    public String getMessage() { //重构
        if (isErrorMessage)
            return message;
        else
            return message;
    }

    public String toString() { //重构
        if (null == super.getMessage())
            return "";
        else
            return "<div>" + super.getMessage() + "</div>";
    }

    protected void writeErrorLog(String error, Throwable e, Logger log) {
        /*if (e instanceof BkmsException) {
           return; //堆栈异常已经记录,不再重复记
        }*/
        StringBuffer sb = new StringBuffer(error + " Cause At ");
        if (e.getStackTrace() != null) {
            int n = e.getStackTrace().length;
            for (int i = 0; i < n; i++) {
                String tmp = e.getStackTrace()[i].toString();
                if (tmp.toLowerCase().indexOf("weblogic") != -1) {
                    break;
                }
                sb.append("\r\n\t").append(tmp);
            }
        }
        log.error(sb.append("\r\n").toString());
    }

    public String toCauseString() {
        Throwable e = this;
        StringBuffer sb = new StringBuffer(this.getMessage());
        while (e != null) {
            sb.append(" cause by ").append(e.getMessage());
            e = e.getCause();
        }
        return sb.toString();
    }

}
