package com.becoda.bkms.qry.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticResultVO;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.util.Tools;

import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: lium
 * Date: 2015-4-3
 * Time: 14:17:40
 * To change this template use File | Settings | File Templates.
 */
public class StaticSettingAction extends GenericAction {
    public String list() throws BkmsException {
        try {
            //统计列表
            String classId = request.getParameter("classId");
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            QueryBO[] StaticList = qm.queryQuery(classId);
            request.setAttribute("StaticList", StaticList);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "";
    }

    public String forQuery() throws BkmsException {
        String forward = doQuery(session, request);
        return forward;
    }

    public String forDel() throws BkmsException {
        deleteQuery(session, request);
        //add by kangdw
        list();
        //end 
        return "";
    }

    private void updateStaticItem(HttpSession session, HttpServletRequest request) throws BkmsException {
        String staticItemId = request.getParameter("staticItemId");
        String[] methods = request.getParameterValues("staticMethod");
        QueryVO vo = (QueryVO) request.getAttribute("QUERY_VO");
        if (vo == null)
            throw new BkmsException("queryvo为空,无法继续", null, getClass());
        vo.setStaticItemId(staticItemId);
        if (methods == null || methods.length <= 0)
            throw new BkmsException("没有选择统计方法,无法继续", null, getClass());
        String method = "";
        for (int i = 0; i < methods.length; i++)
            method += methods[i] + ",";
        //
        vo.setCount("");
        vo.setMax("");
        vo.setMin("");
        vo.setAvg("");
        //
        if (method.indexOf("count") != -1)
            vo.setCount("checked");
        if (method.indexOf("max") != -1)
            vo.setMax("checked");
        if (method.indexOf("min") != -1)
            vo.setMin("checked");
        if (method.indexOf("avg") != -1)
            vo.setAvg("checked");

        request.setAttribute("QUERY_VO", vo);
    }


    public String doQuery(HttpSession session, HttpServletRequest request) {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            if ("org".equalsIgnoreCase(request.getParameter("flag"))) {
                StaticOrgSettingAction sosAction = new StaticOrgSettingAction();
                sosAction.updateQueryVO(session, request);
            }

            if ("staticlist".equals(request.getParameter("flag"))) {
                String qryId = request.getParameter("qryId");
                QueryVO vo = qm.findQueryVO(qryId);
                request.setAttribute("QUERY_VO", vo);
            }

            if ("staticitem".equals(request.getParameter("flag")))
                updateStaticItem(session, request);
            QueryVO vo = (QueryVO) request.getAttribute("QUERY_VO");

            if (vo == null)
                throw new BkmsException("统计定义为空，无法保存", null, this.getClass());
            StaticResultVO[] srvos = qm.executeStatic(getUserInfo(session), vo);
            session.setAttribute("STATIC_RESULT", srvos);

        } catch (Exception e) {
            /***************** add by wanglijun 2015-7-26 ****************/
            session.removeAttribute("STATIC_RESULT");
            /****************** end add ***********************/
//            ActionMessage error = new ActionMessage("msg", e.getMessage(), e.toString());
//            this.actionErrors.add(error);
            return null;
        }
        //
        if ("Y".equals(request.getParameter("pop")))
            return "close";
        else
            return "staticresult";
    }


    public String toStaticResult(HttpSession session, HttpServletRequest request) {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            QueryVO vo = (QueryVO) session.getAttribute("QUERY_VO");
            vo.setQsType("S");
            vo.setQryId(null);
            vo.setClassId(Tools.filterNull(vo.getClassId()).replaceAll("Q", "S"));
            StaticResultVO[] srvos = qm.executeStatic(getUserInfo(session), vo);
            request.setAttribute("STATIC_RESULT", srvos);
        } catch (Exception e) {
            /***************** add by wanglijun 2015-7-26 ****************/
            session.removeAttribute("STATIC_RESULT");
            /****************** end add ***********************/
//            ActionMessage error = new ActionMessage("info", e.getMessage(), e.toString());
//            this.actionErrors.add(error);
        }
        return "staticresult";
    }


    public String deleteQuery(HttpSession session, HttpServletRequest request) {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            String[] qryIds = request.getParameterValues("ids");
            qm.deleteQuery(qryIds);
        } catch (Exception e) {
//            ActionMessage error = new ActionMessage("msg", e.getMessage(), e.toString());
//            this.actionErrors.add(error);
        }
        return null;
    }

    private User getUserInfo(HttpSession session) {
        return (User) session.getAttribute(Constants.USER_INFO);
    }

}