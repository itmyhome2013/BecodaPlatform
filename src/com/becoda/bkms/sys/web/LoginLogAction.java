package com.becoda.bkms.sys.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.sys.pojo.vo.LoginLogVO;
import com.becoda.bkms.sys.ucc.ILoginLogUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import java.util.List;

import org.apache.struts2.ServletActionContext;


public class LoginLogAction extends GenericPageAction {
    private String defaultForward = "success";
    private ILoginLogUCC loginLogUCC;
    private LoginLogVO loginLogForm;

    public LoginLogVO getLoginLogForm() {
        return loginLogForm;
    }

    public void setLoginLogForm(LoginLogVO loginLogForm) {
        this.loginLogForm = loginLogForm;
    }

    public LoginLogAction() throws BkmsException {
        loginLogUCC = (ILoginLogUCC) BkmsContext.getBean("login_LogUCC");
    }

    public String defaultAct() throws BkmsException {
        return defaultForward;
    }

    public String queryLogs() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        //查询日志
        vo.setPageSize(Constants.COMMON_PAGE_SIZE);
        List vector = loginLogUCC.queryLoginLog(Tools.filterNull(loginLogForm.getStartTime()), Tools.filterNull(loginLogForm.getEndTime()), Tools.filterNull(loginLogForm.getPersonName()), loginLogForm.getPersonId(), Tools.filterNull(loginLogForm.getHostName()), Tools.filterNull(loginLogForm.getIp()), vo);
        request.setAttribute("array", vector);
        return defaultForward;
    }

    //导出日志
    public String exportLogs() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        String file = request.getParameter("fileType");
        Integer fileType = Integer.valueOf(file);
        String[] pk = request.getParameterValues("chk");
//        String rootPath = this.getServlet().getServletContext().getRealPath("/");
        ServletContext sc= ServletActionContext.getServletContext();
        String rootPath = sc.getRealPath("/");

        loginLogUCC.deleteAndExportLoginLog(pk, fileType.intValue(), rootPath);
        this.showMessage("登录日志文件导出成功！");
        return queryLogFiles();
    }

    public String queryLogFiles() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        ServletContext sc= ServletActionContext.getServletContext();
        String rootPath = sc.getRealPath("/");
        String[] file1 = loginLogUCC.getDiskLogFile(rootPath);
        request.setAttribute("string_array", file1);
        return defaultForward;
    }

    public String delLogFiles() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        String[] pk = request.getParameterValues("chk");
        ServletContext sc= ServletActionContext.getServletContext();
        String rootPath = sc.getRealPath("/");
        loginLogUCC.delDiskLogFile(pk, rootPath);
        this.showMessage("硬盘里的操作日志文件删除成功！");
        return queryLogFiles();
    }

//    public ActionForward executeDo(PageVO vo,User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//        String forward = "success";
//        String act = request.getParameter("act");
//        LoginLogVO loginLogForm = (LoginLogVO) form;
//        String rootPath = this.getServlet().getServletContext().getRealPath("/");
//        try {
//            LoginLogManager loginLogManager = new LoginLogManager();
//            if ("1".equalsIgnoreCase(act)) {
//                //查询日志
//                vo.setPageSize(Integer.parseInt(Constants.LOG_PAGE_SIZE));
//                Vector vector = (Vector) loginLogManager.queryLoginLog(Tools.filterNull(loginLogForm.getStartTime()), Tools.filterNull(loginLogForm.getEndTime()), Tools.filterNull(loginLogForm.getPersonName()), loginLogForm.getPersonId(), Tools.filterNull(loginLogForm.getHostName()), Tools.filterNull(loginLogForm.getIp()),vo);
//                request.setAttribute("array", vector);
//            } else if ("2".equalsIgnoreCase(act)) {
//                //导出日志
//                String file = request.getParameter("fileType");
//                Integer fileType = Integer.valueOf(file);
//                String[] pk = request.getParameterValues("chk");
//                loginLogManager.deleteAndExportLoginLog(pk, fileType.intValue(), rootPath);
//                this.showMessage("登录日志文件导出成功！");
//
//            } else if ("3".equalsIgnoreCase(act)) {
//                //在硬盘中查询导出的日志
//                String[] file1 = (String[]) loginLogManager.getDiskLogFile(rootPath);
//                request.setAttribute("string_array", file1);
//            } else if ("4".equalsIgnoreCase(act)) {
//                //删除硬盘中的日志文件
//                String[] pk = request.getParameterValues("chk");
//                loginLogManager.delDiskLogFile(pk, rootPath);
//                this.showMessage("硬盘里的操作日志文件删除成功！");
//
//            }
//        } catch (BkmsException he) {
//            ActionError error = new ActionError("info", he.getFlag(), he.getMessage(), he.toString());
//            this.actionErrors.add(error);
//        } catch (Exception e) {
//            ActionError ae = new ActionError("info", "<div style='color:red'>错误</div>", e.getMessage(), e.toString());
//            this.actionErrors.add(ae);
//        }
//        return mapping.findForward(forward);
//    }
}

