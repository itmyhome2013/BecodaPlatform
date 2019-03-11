package com.becoda.bkms.common.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.util.Endecode;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.ActionMessage;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-26
 * Time: 14:00:42
 * To change this template use File | Settings | File Templates.
 */
public class GenericAction extends ActionSupport {

//    protected User user = (User) session.getAttribute(Constants.USER_INFO);
    protected User user;
    protected HttpSession session;
    protected BkmsHttpRequest hrequest;
    protected HttpServletRequest request;
    protected HttpServletResponse httpResponse;
    protected int page;
    protected int rows;
    protected String genericIds;
    protected String [] ids;
    public void validate() {
        request = ServletActionContext.getRequest();
        hrequest = new BkmsHttpRequest(request);
        httpResponse = ServletActionContext.getResponse();
        session = request.getSession();
        user = (User) session.getAttribute(Constants.USER_INFO);
        String pa = request.getParameter("page");
        String ro = request.getParameter("rows");
        page = Integer.parseInt(pa!=null?pa:"1");
        rows = Integer.parseInt(ro!=null?ro:"10");
        String generic = request.getParameter("genericIds");
        if(generic!=null){
        	ids = generic.split(",");
        }
        //判断是否登录
        if (null == user) {
            try {
                httpResponse.sendRedirect("/jsp/overtime.jsp");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

        public void showMessageDetail(String message) {
        if (message != null && message.trim().length() > 0) {
            this.addActionError("信息:"+message);
            }
        }
    public void showMessage(String message) {
        ActionContext ctx=ActionContext.getContext();
        if (message != null && message.trim().length() > 0) {
                ctx.put("messageContext", " <script> alert('"+message+"') </script>");
            }
        }
    public BkmsHttpRequest getBrequest(){
        HttpServletRequest  request = ServletActionContext.getRequest();
        BkmsHttpRequest brequest = new BkmsHttpRequest(request);
        return brequest;
    }
    public Object getBean(String beanId) throws BkmsException {
        return BkmsContext.getBean(beanId);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
