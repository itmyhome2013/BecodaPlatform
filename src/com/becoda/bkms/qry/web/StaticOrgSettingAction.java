package com.becoda.bkms.qry.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticResultVO;
import com.becoda.bkms.qry.pojo.vo.StaticVO;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.qry.util.QryTools;
import com.becoda.bkms.util.Tools;

import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.components.ActionMessage;


public class StaticOrgSettingAction extends GenericAction {
    public String forSave() throws BkmsException {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            this.updateQueryVO(session, request);
            QueryVO vo = (QueryVO) request.getAttribute("QUERY_VO");
            if (vo == null)
                throw new BkmsException("统计定义为空，无法保存", null, this.getClass());
            vo.setQsType("S");
            vo.setName(request.getParameter("qryName"));
            QueryVO newVO = qm.createStatic(vo);
            request.setAttribute("QUERY_VO", newVO);
        } catch (Exception e) {
           BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return null;
        }
//        ActionMessage error = new ActionMessage("msg", "保存成功", "");
//        this.actionErrors.add(error);
        return "";
    }

    public String forQuery() throws BkmsException {
        String forward = "";
        try {
            updateQueryVO(session, request);
            QueryVO vo = (QueryVO) request.getAttribute("QUERY_VO");
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            StaticResultVO[] srvos = qm.executeStatic(getUserInfo(session), vo);
            session.setAttribute("STATIC_RESULT", srvos);
            if ("Y".equals(request.getParameter("pop")))
                forward = "close";
            else
                forward = "staticresult";
        } catch (BkmsException e) {
            session.removeAttribute("STATIC_RESULT");
        }
        return forward;
    }

    private User getUserInfo(HttpSession session) {
        return (User) session.getAttribute(Constants.USER_INFO);
    }

    public String list() throws BkmsException {
        String qryId = request.getParameter("qryId");
        if (qryId == null || "".equals(qryId)) {
            initQueryVO(session, request);
        } else {
            //如果是修改则查询vo
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            QueryVO newVO = qm.findQueryVO(qryId);
            request.setAttribute("QUERY_VO", newVO);
        }
        //返回范围选择页面
        return "";
    }

    //以下由StaticSettingAction移至此
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

    /**
     * 本方法判断session中有否queryVO 若有则清除该vo后重新建立新的queryVO,<BR>
     * 建立新的queryvo需要从数据库中读取默认显示项的定义,默认显示类别从settingBb的属性里里取.<BR>
     * 初始化static等.
     */
    public QueryVO initQueryVO(HttpSession session, HttpServletRequest request) {
        String qryId = Tools.filterNull(request.getParameter("qryId"));
        String flag = request.getParameter("flag");
        String resetFlag = request.getParameter("resetFlag");
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            if (!"".equals(qryId)) {
                //load queryvo
                QueryVO vo = qm.findQueryVO(qryId);
                request.setAttribute("QUERY_VO", vo);

            } else
            if ("Y".equalsIgnoreCase(resetFlag) || session.getAttribute("QUERY_VO") == null || "".equals(qryId)) {
                String qsType = request.getParameter("qsType");
                String setType = request.getParameter("setType");
                String classId = request.getParameter("classId");

                QueryVO defaultVO = qm.findDefaultQueryVO(setType, qsType, classId);
                //新建vo时写入创建人id
                User user = (User) session.getAttribute(Constants.USER_INFO);
                if (user != null)
                    defaultVO.setCreateUser(user.getUserId());
                request.setAttribute("QUERY_VO", defaultVO);
                return defaultVO;

            }
        } catch (Exception e) {
            /***************** add by wanglijun 2015-7-26 ****************/
            session.removeAttribute("QUERY_VO");
            /****************** end add ***********************/
//            ActionError error = new ActionError("msg", e.getMessage(), e.toString());
//            this.actionErrors.add(error);
        }
        return null;
    }

    /**
     * 这个隐藏了两种逻辑，一个是数据库中有queryvo数据的，一个是临时生成在数据库中没有数据的。
     * 应该写一个builder模式来实例化queryvo这个实例，现在没有时间写，先这样。
     *
     * @param session
     * @param request
     * @throws BkmsException
     */
    public void updateQueryVO(HttpSession session, HttpServletRequest request) throws BkmsException {
        initQueryVO(session, request);
        QueryVO vo = (QueryVO) request.getAttribute("QUERY_VO");
        String classId = request.getParameter("classId");
        String qsType = request.getParameter("qsType");
        String setType = request.getParameter("setType");
        String qryName = request.getParameter("qryName");
        String sysFlag = request.getParameter("sysFlag");
        String qryId = request.getParameter("qryId");
        String unitType = request.getParameter("unitType");
        String addedCondition = request.getParameter("addedCondition");

        vo.setCreateDate(Tools.getSysDate("yyyy-MM-dd"));
        vo.setQryId(qryId);
        if (sysFlag != null)
            vo.setSysFlag(sysFlag);
        if (qryName != null)
            vo.setName(qryName);
        if (classId != null)
            vo.setClassId(classId);
        if (addedCondition != null)
            vo.setAddedCondition(addedCondition);
        if (qsType != null)
            vo.setQsType(qsType);
        if (setType != null)
            vo.setSetType(setType);
        if (unitType != null)
            vo.setUnitType(unitType);
        String svos = request.getParameter("svos");
        StaticVO[] svo = (StaticVO[]) QryTools.deSer1(svos);
        vo.setStatics(svo);
        //机构范围设置页面
        String orgIds = request.getParameter("orgIds");
        String orgNames = request.getParameter("orgNames");
        vo.setOrgIds(orgIds);
        vo.setOrgNames(orgNames);
        updateStaticItem(session, request);
    }
}
