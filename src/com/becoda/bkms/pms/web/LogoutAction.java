package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.ucc.ILoginLogUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.common.web.GenericAction;
import com.opensymphony.xwork2.ActionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

import org.apache.struts2.ServletActionContext;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-5-16
 * Time: 17:43:20
 * To change this template use File | Settings | File Templates.
 */
public class LogoutAction extends GenericAction {
    public String execute()
            throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        ActionContext ctx=ActionContext.getContext();
        ServletContext application = session.getServletContext();
        try {
            ILoginLogUCC log = (ILoginLogUCC) BkmsContext.getBean("login_LogUCC");
            String sessionId = session.getId();
            Timestamp stmp = new Timestamp(session.getLastAccessedTime());
            log.removeLog(sessionId, stmp); //记录退出日志
            session.removeAttribute(Constants.USER_INFO);
            application.removeAttribute(user.getName());//系统退出，移除该用户的sessionId
            session.invalidate(); //销毁这个session对象，也就是当前session,并删除该对象所包含的数据及会话对象本身。
            
        } catch (BkmsException he) {
            he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), he, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        } catch (Exception e) {
            //将action中抛出的所有非BkmsException异常包装成一个BkmsException异常
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("出现错误").toString(), e, this.getClass());
            ctx.put("errorsMsg", " <script> alert('"+he.getFlag()+he.getCause().getMessage()+"') </script>");
        }
        return "logout";
    }
    
    
    public String exitAndRefresh() throws Exception{
    	HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        ActionContext ctx=ActionContext.getContext();
        ServletContext application = session.getServletContext();
        try {
            ILoginLogUCC log = (ILoginLogUCC) BkmsContext.getBean("login_LogUCC");
            String sessionId = session.getId();
            Timestamp stmp = new Timestamp(session.getLastAccessedTime());
            log.removeLog(sessionId, stmp); //记录退出日志
            //session.removeAttribute(Constants.USER_INFO);
            session.removeAttribute(user.getName() + ":count");
            application.removeAttribute(user.getName());//系统退出，移除该用户的sessionId
            
        } catch (BkmsException he) {
            he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), he, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        } catch (Exception e) {
            //将action中抛出的所有非BkmsException异常包装成一个BkmsException异常
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("出现错误").toString(), e, this.getClass());
            ctx.put("errorsMsg", " <script> alert('"+he.getFlag()+he.getCause().getMessage()+"') </script>");
        }
        return "logout";
    }
}
