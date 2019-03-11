package com.becoda.bkms.qry.web;

import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.qry.util.QryTools;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.StringUtils;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;

public class QuerySettingAction extends GenericAction {
    /**
     * 本方法对应查询定义的查询按钮<BR>
     * 应该先调用updateQueryVO方法<BR>
     * 本方法调用ucc的传queryVo查询方法,查询结果序列化存放到hidden中
     *
     * @return 返回值为autoClose的页面.
     */
    public String forQuery() throws BkmsException {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            String flag = request.getParameter("flag");
            Hashtable hsSql = null;
            QueryVO qryVO = null;
            if ("querylist".equals(flag)) {
                //从查询列表中直接查询需要根据qryId读取queryvo
                String qryId = request.getParameter("qryId");
                hsSql = qm.findSQL(qryId);
                qryVO = qm.findQueryVO(qryId);

                String addedCondition = request.getParameter("addedCondition");
                if (Tools.filterNull(addedCondition).length() > 0) {
                    qryVO.setAddedCondition(addedCondition);
                    Hashtable tm = qm.buildQuerySqlHash(null, qryVO);
                    String select = (String) tm.get(QryConstants.SQL_SELECT_PART);
                    String from = (String) tm.get(QryConstants.SQL_FROM_PART);
                    String where = (String) tm.get(QryConstants.SQL_WHERE_PART);

                    String order = (String) tm.get(QryConstants.SQL_ORDER_PART);
                    String sql = "select " + select + " from " + from
                            + ("".equals(where) ? "" : " where " + where)
                            + ("".equals(order) ? "" : " order by " + order);
                    hsSql.put(QryConstants.QRYSQL_qrySql, sql);
                    hsSql.put(QryConstants.QRYSQL_showField, select);
                    hsSql.put(QryConstants.QRYSQL_from, from);
                    hsSql.put(QryConstants.QRYSQL_where, StringUtils.trimToEmpty(where));
                    hsSql.put(QryConstants.QRYSQL_order, StringUtils.trimToEmpty(order));
                }
            }
            if (hsSql == null) {
//                BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
//            this.addActionError(he.getFlag()+he.getCause().getMessage());
                return "";
            }

            request.setAttribute(QryConstants.QRYSQL_hash, QryTools.ser(hsSql));
            request.setAttribute(QryConstants.QRYSQL_qryVO, QryTools.ser(qryVO));
            if ("Y".equals(request.getParameter("pop")))
                return "close";
            else
                return "queryresult";
        } catch (Exception e) {
            this.showMessageDetail(e.getMessage());
            return "";
        }
    }

    public String forDel() throws BkmsException {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            String[] qryIds = request.getParameterValues("ids");
            qm.deleteQuery(qryIds);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return list();
    }

    public String toStaticResult() throws BkmsException {
        return "staticresult";
    }

    public String list() throws BkmsException {
        try {
            //查询列表
            String classId = request.getParameter("classId");
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            QueryBO[] QueryList = qm.queryQuery(classId);
            request.setAttribute("QueryList", QueryList);

        } catch (Exception e) {
            if (session != null) session.removeAttribute("QUERY_VO");
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "listTwo";
    }
}

