package com.becoda.bkms.qry.web;

import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.qry.util.QryTools;
import com.becoda.bkms.sys.pojo.vo.TableVO;

import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: ye
 * Date: 2015-4-9
 * Time: 16:00:13
 * To change this template use File | Settings | File Templates.
 */
public class QueryPageAction extends GenericPageAction {
    public String query() throws BkmsException {
        IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
        String sql = (String) request.getAttribute(QryConstants.QRYSQL_hash);
        if (sql == null) {
            sql = request.getParameter(QryConstants.QRYSQL_hash);
            request.setAttribute(QryConstants.QRYSQL_hash, sql);
        }
        vo.setHash(sql);
        Hashtable hsSql = QryTools.deSer(sql);
        String setType=request.getParameter("setType");
        TableVO tvo = qm.executeQuery(user,hsSql, vo,setType);
        request.setAttribute(QryConstants.OBJECT, tvo);
        return null;
    }

    public String export() throws BkmsException {
        IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
        String sql = (String) request.getAttribute(QryConstants.QRYSQL_hash);
        if (sql == null) {
            sql = request.getParameter(QryConstants.QRYSQL_hash);
            request.setAttribute(QryConstants.QRYSQL_hash, sql);
        }
        vo.setHash(sql);
        Hashtable hsSql = QryTools.deSer(sql);
        String setType=request.getParameter("setType");
        TableVO tvo = qm.executeQuery(user,hsSql, null,setType);
        request.setAttribute(QryConstants.OBJECT, tvo);
        //request.setAttribute("PageVO",vo);
        return "export";
    }

}
