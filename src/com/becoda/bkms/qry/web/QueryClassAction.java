package com.becoda.bkms.qry.web;

import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.qry.pojo.bo.QueryClassBO;
import com.becoda.bkms.qry.ucc.IQueryClassUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: ye
 * Date: 2015-4-2
 * Time: 10:30:20
 * To change this template use File | Settings | File Templates.
 */
public class QueryClassAction extends GenericAction {
    public String createQueryClass() throws BkmsException {
        try {
            IQueryClassUCC qm = (IQueryClassUCC) BkmsContext.getBean("qry_queryClassUCC");
            String rootId = request.getParameter("rootId");
            String className = request.getParameter("className");
            String superId = request.getParameter("classId");
            QueryClassBO bo = new QueryClassBO();
            if (user != null)
                bo.setCreateUser(user.getUserId());
            bo.setName(className);
            bo.setRootId(rootId);
            bo.setSuperId(superId);
            bo.setTypeFlag(rootId.substring(1, 2));
            qm.createQueryClass(bo);
        } catch (Exception e) {
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return this.list();
    }

    public String deleteQueryClass() throws BkmsException {
        IQueryClassUCC qm = (IQueryClassUCC) BkmsContext.getBean("qry_queryClassUCC");
        try {
            String classId = request.getParameter("classId");
            if (classId != null)
                qm.deleteQueryClass(classId);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return this.list();
    }

    public String updateQueryClass() throws BkmsException {
        IQueryClassUCC qm = (IQueryClassUCC) BkmsContext.getBean("qry_queryClassUCC");
        try {
            String name = request.getParameter("className");
            String classId = request.getParameter("classId");
            QueryClassBO bo = qm.findQueryClassBO(classId);
            bo.setName(name);
            qm.updateQueryClassBO(bo);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return this.list();
    }

    public String list() throws BkmsException {
        IQueryClassUCC qm = (IQueryClassUCC) BkmsContext.getBean("qry_queryClassUCC");
        try {
            String qsType = hrequest.getParameter("qsType");
            String setType = hrequest.getParameter("setType");
            if ("".equals(Tools.filterNull(qsType))) qsType = "Q";
            if ("".equals(Tools.filterNull(setType))) setType = "A";
            String publicRoot = qsType + setType + "1039628";  //改为将公共查询设置为全行共享
            String privateRoot = qsType + setType + Tools.filterNull(user.getUserId());
            QueryClassBO[] bosPublic = qm.queryClasses(publicRoot);
            QueryClassBO[] bosPrivate = qm.queryClasses(privateRoot);
            request.setAttribute("classPublic", bosPublic);
            request.setAttribute("classPrivate", bosPrivate);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "listOne";

    }
}
