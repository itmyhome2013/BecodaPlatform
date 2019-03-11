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
public class GenericPageAction extends ActionSupport {

//    protected User user = (User) session.getAttribute(Constants.USER_INFO);
    protected User user;
    protected PageVO vo;
    protected HttpSession session;
    protected HttpServletRequest request;
    protected BkmsHttpRequest hrequest;
    protected HttpServletResponse httpResponse;
    public void validate() {
        request = ServletActionContext.getRequest();
        hrequest = new BkmsHttpRequest(request);
        httpResponse = ServletActionContext.getResponse();
        session = request.getSession();
        user = (User) session.getAttribute(Constants.USER_INFO);
        //判断是否登录
        if (null == user) {
            try {
                httpResponse.sendRedirect("/jsp/overtime.jsp");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        try {
            this.getPageVo();
        } catch (BkmsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
    public void getPageVo() throws BkmsException {
        HttpServletRequest  request = ServletActionContext.getRequest();
        vo = new PageVO();
        String cPage = request.getParameter("PAGE_TAG_currentPage");
        String cPageSize = request.getParameter("PAGE_TAG_pageSize");
        String orderField = request.getParameter("PAGE_TAG_orderField");
        String isASC = request.getParameter("PAGE_TAG_isASC");
        String qrySql = request.getParameter("QRYSQL_qrySql");
        String showField = request.getParameter("QRYSQL_showField");
        String hash = request.getParameter("QRYSQL_hash");
        vo.setHash(hash);
        vo.setQrySql(qrySql);
        vo.setShowField(showField);

        if (cPage == null || "".equals(cPage))
            cPage = "1";
        if (cPageSize == null || "".equals(cPageSize))
            cPageSize = String.valueOf(vo.getPageSize());
        vo.setCurrentPage(Integer.parseInt(cPage));
        vo.setPageSize(Integer.parseInt(cPageSize));
        if (vo.getCurrentPage() < 1)
            vo.setCurrentPage(1);
        if (!"".equals(Tools.filterNull(orderField))) {
            if ("".equals(Tools.filterNull(isASC))) isASC = "ASC";
            vo.setOrderPart(orderField + " " + isASC);
            vo.setASC(isASC);
            vo.setOrderField(orderField);
        }
        request.setAttribute("pageVO", vo);
    }
    /**
     * 默认的高级查询方法，返回查询结果 TableVO
     * 返回的TableVO已经经过权限处理
     * 参数错误或查询错误将返回 NULL
     *
     * @param user    user
     * @param vo      PageVO
     * @param pmsType pmsType 的可选值有，传入空值将不检查权限
     *                QryConstants.PMS_TYPE_PERSON   //人员
     *                QryConstants.PMS_TYPE_ORG      //机构
     *                QryConstants.PMS_TYPE_POST     //岗位
 
     * @return TableVO对象，查询错误将返回NULL
     */
    protected TableVO advanceQuery(User user, PageVO vo, String pmsType) throws BkmsException {
        IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
        Hashtable hsSql = vo.getHashSQL();
        if (hsSql != null) {
            return qm.executeQuery(user, hsSql, vo, pmsType);
        } else {
            return null;
        }
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

    public PageVO getVo() {
        return vo;
    }

    public void setVo(PageVO vo) {
        this.vo = vo;
    }
}
