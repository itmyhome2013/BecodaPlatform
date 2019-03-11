package com.becoda.bkms.org.web;

import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-30
 * Time: 10:49:39
 * To change this template use File | Settings | File Templates.
 */
public class OrgInfoQueryAction extends GenericPageAction {

    /**
     * 机构查询
     * fxj
     * @return
     * @throws BkmsException
     */
    public String query() throws BkmsException {
        IOrgUCC orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
//        String pageSupport = request.getParameter("pageSupport");
        String export = request.getParameter("flag");
        if ("export".equals(export)) {
            vo.setPageSize(20000);
            vo.setCurrentPage(1);
        }
        String from = request.getParameter("from");
        String superId = request.getParameter("superId");
        String orgName = request.getParameter("orgName");
        String orgType = request.getParameter("orgType");
        String orgLevel = request.getParameter("orgLevel");
        TableVO table = orgUcc.queryOrgList(user, vo, orgName, superId, orgType, orgLevel, Constants.NO, from);
        if ("export".equals(export)) {
            request.setAttribute("table", table);
            request.setAttribute("sessionKey", "table");
            return "export";
        } else {
            request.setAttribute("tableVO", table);
            return "success";
        }
    }

    public String list() throws BkmsException {
        return "success";
    }

    public String advanceQueryOrg() throws BkmsException {
        try {
            TableVO table = super.advanceQuery(user, vo, QryConstants.PMS_TYPE_ORG);
            request.setAttribute("tableVO", table);
            return "success";
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
         return "success";
    }
//    public ActionForward forTrunPage(PageVO pageVO, User user, ActionMapping mapping, ActionForm form, HrmsHttpRequest request, HrmsHttpResponse response, HttpSession session) throws BkmsException {
//        try {
////            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
//            TableVO table = orgUcc.queryOrgList(user, pageVO.getQrySql(), pageVO.getShowField(), pageVO);
////                                                (user,pageVO,orgName, superId, orgType,"1", from);
//            request.setAttribute("QRYSQL_qrySql", pageVO.getQrySql());
//            request.setAttribute("QRYSQL_showField", pageVO.getShowField());
//            request.setAttribute("tableVO", table);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ActionMessage error = new ActionMessage("info", "错误:", e.getMessage(), e.toString());
//            this.actionErrors.add(error);
//            return mapping.getInputForward();
//        }
//        return mapping.findForward("listEmp");
//    }
    /*
    public ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String act = request.getParameter("act");
        try {

            String pageFlag = request.getParameter("pageFlag");
            String flag = request.getParameter("flag");
            String sessionFlag = request.getParameter("sessionFlag");
            if (sessionFlag == null) {
                session.removeAttribute(Constants.OBJECT);
            }

            //add by sunmh 2015-09-06 ,可以传入 查询ID，查询不同结果
            String queryId = request.getParameter("queryId");
            if (queryId == null || "".equals(queryId)) {
                queryId = Constants.DEFAULT_QUERY_ORG;
            }
            //end sunmh

            if ("1".equals(pageFlag)) {  //分页

                ActivePageManager apm = new ActivePageManager();
                String rowNums = request.getParameter("rowNum");//Constants.PAGE;
                int pageNum = request.getParameter("pageNum") == null || "".equals(request.getParameter("pageNum")) ? 1 : Integer.parseInt(request.getParameter("pageNum"));

                String sql = (String) session.getAttribute("activeSql");

                TableVO table = (TableVO) session.getAttribute(Constants.OBJECT);
                int rowNum = Constants.ACTIVE_PAGE_SIZE;
                if (rowNums != null) {
                    rowNum = Integer.parseInt(rowNums);
                }

                apm.querySql(table, sql, user, pageNum, rowNum, true);
                session.setAttribute(Constants.OBJECT, table);
                session.setAttribute("activeSql", sql);
                session.setAttribute("pageNum", String.valueOf(pageNum));
                session.setAttribute("rowNum", String.valueOf(rowNum));


            }
            if ("1".equals(flag)) {
                session.removeAttribute(Constants.OBJECT);
                session.removeAttribute("activeSql");
                session.removeAttribute("pageNum");
                session.removeAttribute("rowNum");

                OrgManager om = new OrgManager();
                String superId = request.getParameter("superId");
                String orgName = request.getParameter("orgName");
                String orgType = request.getParameter("orgType");
                TableVO table = new TableVO();
                String rowNums = (String) session.getAttribute("rowNum");
                int rowNum = Constants.ACTIVE_PAGE_SIZE;
                if (rowNums != null) {
                    rowNum = Integer.parseInt(rowNums);
                }
                String sql = om.queryOrgList(table, orgName, superId, orgType, 1, rowNum, Constants.NO, user, "1", queryId);

                //add by sunmh 2015-09-06,子类实现 specialTreatment，实现条件查询
                String st_sql = specialTreatSql(sql, user, session, mapping, form, request, response);
                if (st_sql != null) {
                    sql = st_sql;
                    ActivePageManager apm = new ActivePageManager();
                    apm.querySql(table, sql, user, 1, rowNum, true);
                }

                session.setAttribute("activeSql", sql);
                session.setAttribute("pageNum", String.valueOf("1"));
                session.setAttribute("rowNum", String.valueOf(rowNum));
                session.setAttribute(Constants.OBJECT, table);

            }
            if ("1".equals(act)) {
                session.removeAttribute(Constants.OBJECT);
                session.removeAttribute("activeSql");
                session.removeAttribute("pageNum");
                session.removeAttribute("rowNum");
                String superId = request.getParameter("superId");
                String orgName = request.getParameter("orgName");
                String orgType = request.getParameter("orgType");

                TableVO table = new TableVO();
                String rowNums = (String) session.getAttribute("rowNum");
                int rowNum = Constants.ACTIVE_PAGE_SIZE;
                if (rowNums != null) {
                    rowNum = Integer.parseInt(rowNums);
                }
                OrgManager om = new OrgManager();
                String sql = om.queryOrgList(table, orgName, superId, orgType, 1, rowNum, Constants.NO, user, "2", queryId);

                //add by sunmh 2015-09-06,子类实现 specialTreatment，实现条件查询
                String st_sql = specialTreatSql(sql, user, session, mapping, form, request, response);
                if (st_sql != null) {
                    sql = st_sql;
                    ActivePageManager apm = new ActivePageManager();
                    apm.querySql(table, sql, user, 1, rowNum, true);
                }

                session.setAttribute("activeSql", sql);
                session.setAttribute("pageNum", String.valueOf("1"));
                session.setAttribute("rowNum", String.valueOf(rowNum));
                session.setAttribute(Constants.OBJECT, table);
            }
        } catch (BkmsException e) {
            ActionError ae = new ActionError("info", e.getFlag(), e.getMessage(), e.toString());
            this.actionErrors.add(ae);
            session.removeAttribute(Constants.OBJECT);
            session.removeAttribute("activeSql");
            session.removeAttribute("pageNum");
            session.removeAttribute("rowNum");
        } catch (Exception e) {
            ActionError ae = new ActionError("info", "<div style='font:red'>错误</div>", e.getMessage(), e.toString());
            this.actionErrors.add(ae);
            session.removeAttribute(Constants.OBJECT);
            session.removeAttribute("activeSql");
            session.removeAttribute("pageNum");
            session.removeAttribute("rowNum");
        }
        return mapping.findForward("success");
    }*/

//    /**
//     * 对sql进行特殊处理
//     * 可以实现条件查询
//     * 在高级查询定义  条件1={值1}，用 specialTreatSql方法将{值1}替换成实际值
//     *
//     * @param oSql
//     * @return sql  ==null:不处理。 ！=nul：处理后sql
//     */
//
//    public String specialTreatSql(String oSql, User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//        return null;
//    }
}
