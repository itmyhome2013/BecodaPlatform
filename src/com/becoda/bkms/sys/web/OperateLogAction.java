package com.becoda.bkms.sys.web;


import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.*;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.sys.pojo.vo.OperLogVO;
import com.becoda.bkms.sys.ucc.IOperateLogUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.ServletContext;
import java.util.List;

import org.apache.struts2.ServletActionContext;


public class OperateLogAction extends GenericPageAction {
    String defaultForward = "operManager";
    private IOperateLogUCC operateLogUCC;
    private OperLogVO operationLogFormLog;

    public OperLogVO getOperationLogFormLog() {
        return operationLogFormLog;
    }

    public void setOperationLogFormLog(OperLogVO operationLogFormLog) {
        this.operationLogFormLog = operationLogFormLog;
    }

    public OperateLogAction() throws BkmsException {
        operateLogUCC = (IOperateLogUCC) BkmsContext.getBean("operate_LogUCC");
    }

    public String defaultAct() throws BkmsException {
        return defaultForward;
    }


    public String queryLogs() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        //查处日志文件
        String orgId = request.getParameter("orgId");
        vo.setPageSize(Constants.COMMON_PAGE_SIZE);
        List vector = operateLogUCC.queryOperLog(Tools.filterNull(operationLogFormLog.getStartTime()), Tools.filterNull(operationLogFormLog.getEndTime()), Tools.filterNull(operationLogFormLog.getOperName()), Tools.filterNull(operationLogFormLog.getOperatorName()), Tools.filterNull(operationLogFormLog.getOperType()), Tools.filterNull(orgId), vo);
        request.setAttribute("array", vector);
        return defaultForward;
    }

    public String exportLogs() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        //导出日志
        ServletContext sc= ServletActionContext.getServletContext();
        String rootPath = sc.getRealPath("/");
        String file = request.getParameter("fileType");
        Integer fileType = Integer.valueOf(file);
        String[] pk = request.getParameterValues("chk");
        operateLogUCC.deleteAndExpOperLog(pk, fileType.intValue(), rootPath);
        this.showMessage("操作日志导出成功！");
        return queryLogFiles();
    }

    public String queryLogFiles() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        //在硬盘中查询导出的日志
        ServletContext sc= ServletActionContext.getServletContext();
        String rootPath = sc.getRealPath("/");
        String[] file1 = operateLogUCC.getDiskLogFile(rootPath);
        request.setAttribute("array_string", file1);
        return defaultForward;
    }

    public String delLogFiles() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        //删除硬盘中已经导出的日志文件
        ServletContext sc= ServletActionContext.getServletContext();
        String rootPath = sc.getRealPath("/");
        String[] pk = request.getParameterValues("chk");
        operateLogUCC.delDiskLogFile(pk, rootPath);
        return queryLogFiles();
    }

    public String queryLogsByPerson() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        ///根据被操作人员编号查询日志信息
        String optPersonId = request.getParameter("fk");
        String setId = request.getParameter("setId");
        OperLogBO logLog = new OperLogBO();

        logLog.setOperPersonId(optPersonId);
        logLog.setOperInfosetId(setId);
        OperLogBO[] logLogs = operateLogUCC.query(logLog);
        request.setAttribute("array", logLogs);
        return "operLogInfoByPerId";
    }

    /*
    public ActionForward executeDo(PageVO vo, User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String defaultForward = "operManager";
        String act = request.getParameter("act");
        String orgId = request.getParameter("orgId");
        OperLogVO operationLogForm = (OperLogVO) form;
        String rootPath = this.getServlet().getServletContext().getRealPath("/");
        try {
            SysLogOperManager operLogManager = new SysLogOperManager();
            if ("1".equalsIgnoreCase(act)) {
                //查处日志文件
                vo.setPageSize(Integer.parseInt(Constants.LOG_PAGE_SIZE));
                Vector vector = (Vector) operLogManager.queryOperLog(Tools.filterNull(operationLogForm.getStartTime()), Tools.filterNull(operationLogForm.getEndTime()), Tools.filterNull(operationLogForm.getOperName()), Tools.filterNull(operationLogForm.getOperatorName()), Tools.filterNull(operationLogForm.getOperType()), Tools.filterNull(orgId), vo);
                request.setAttribute("array", vector);
            } else if ("2".equalsIgnoreCase(act)) {
                //导出日志
                String file = request.getParameter("fileType");
                Integer fileType = Integer.valueOf(file);
                String[] pk = request.getParameterValues("chk");
                operLogManager.deleteAndExpOperLog(pk, fileType.intValue(), rootPath);
                this.showMessage("操作日志导出成功！");
            } else if ("3".equalsIgnoreCase(act)) {
                //在硬盘中查询导出的日志
                String[] file1 = (String[]) operLogManager.getDiskLogFile(rootPath);
                request.setAttribute("array_string", file1);

            } else if ("4".equalsIgnoreCase(act)) {
                //删除硬盘中已经导出的日志文件
                String[] pk = request.getParameterValues("chk");
                operLogManager.delDiskLogFile(pk, rootPath);
                this.showMessage("硬盘里的操作日志文件删除成功！");
            } else if ("5".equalsIgnoreCase(act)) {
                //根据被操作人员编号查询日志信息
                String optPersonId = request.getParameter("fk");
                String setId = request.getParameter("setId");
                OperLogBO log = new OperLogBO();
                log.setOperPersonId(optPersonId);
                log.setOperInfosetId(setId);
                OperLogBO[] logs = operLogManager.query(log);
                request.setAttribute("array", logs);
                defaultForward = "operLogInfoByPerId";
            }
        } catch (BkmsException he) {
            ActionError error = new ActionError("info", he.getFlag(), he.getMessage(), he.toString());
            this.actionErrors.add(error);
        } catch (Exception e) {
            ActionError ae = new ActionError("info", "<div style='color:red'>错误</div>", e.getMessage(), e.toString());
            this.actionErrors.add(ae);
        }
        return mapping.findForward(defaultForward);
    }*/
}
