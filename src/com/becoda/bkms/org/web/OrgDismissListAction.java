package com.becoda.bkms.org.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.org.OrgConstants;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.org.util.OrgTool;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.util.BkmsContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class OrgDismissListAction extends GenericPageAction {

    public String query() throws BkmsException {
        IOrgUCC orgUcc= (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        session.removeAttribute(OrgConstants.OBJECT);
        session.removeAttribute("activeSql");
        session.removeAttribute("pageNum");
        session.removeAttribute("rowNum");
        String superId = hrequest.getParameter("superId");
        String orgName = request.getParameter("orgName");
        String orgLevel = request.getParameter("orgLevel");
        String orgType = request.getParameter("orgType");
        String from = request.getParameter("from");
        TableVO table = orgUcc.queryOrgList(user, vo, orgName, superId,orgType, orgLevel, Constants.YES, from);
        request.setAttribute("table", table);
        return "success";
    }

    public String advanceQueryOrg() throws BkmsException {
        try {
            TableVO table = super.advanceQuery(user, vo, QryConstants.PMS_TYPE_ORG);
            request.setAttribute("table", table);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
    }

    //撤销恢复机构
    public String cancelOrgRecover() throws BkmsException {
        IOrgUCC orgUcc= (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        try {
            String[] ids = request.getParameterValues("chk");
            String showName = null;
            List list = new ArrayList();
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    Org bo = SysCacheTool.findOrgById(ids[i]);
                    if (bo == null) {
                        continue;
                    }
                    String orgCode = bo.getOrgCode();
                    if (orgCode != null && !"".equals(orgCode)) {
                        orgCode = OrgTool.getBackOrgCode(orgCode);
                        int count1 = orgUcc.queryOrgCodeCount(orgCode, bo.getOrgId());
                        if (count1 > 0) {
                            Org bo1 = SysCacheTool.findOrgByCode(orgCode);
                            showName = bo1 == null ? bo.getName() + "的机构代码重复！" : bo.getName() + "的机构代码与" + bo1.getName() + "重复，请维护机构代码！";
                        } else {
                            list.add(ids[i]);
                        }
                    } else {
                        list.add(ids[i]);
                    }
                }
            }
            if (showName != null) {
//                ActionError ae = new ActionError("info", showName);
//                this.actionErrors.add(ae);

            }
            if (list.size() > 0) {
                String[] orgids = (String[]) list.toArray(new String[list.size()]);
                orgUcc.updateBackOrg(orgids, user);
                //删除表记录
//            int count = orgids.length;
//            TableVO table = (TableVO) session.getAttribute(OrgConstants.OBJECT);
//            table.setSetPk("ORGUID");
//            Hashtable rs = table.rowArray2Hash();
//            for (int i = 0; i < count; i++) {
//                rs.remove(orgids[i]);
//            }
//            int size2 = rs.size();
//            RecordVO[] row = new RecordVO[size2];
//            Enumeration values = rs.elements();
//            while (values.hasMoreElements()) {
//                row[--size2] = (RecordVO) values.nextElement();
//            }
//            table.setRowData(row);
//            session.setAttribute(OrgConstants.OBJECT, table);
                //更新缓存
                SysCache.setMap(orgids, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_ORG);
                super.showMessage("撤销恢复成功!");
                query();
                request.setAttribute("backRefresh", "1");
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "success";
    }

    //删除机构
    public String delOrg() throws BkmsException {
        IOrgUCC orgUcc= (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        try {
            String[] ids = request.getParameterValues("chk");
            List list = new ArrayList();
            if (ids != null && ids.length > 0) {
                orgUcc.checkOrgSubOrg(ids);
                for (int i = 0; i < ids.length; i++) {
                    list.add(ids[i]);
                }
            }
            if (list.size() > 0) {
                String[] orgids = ((String[]) list.toArray(new String[list.size()]));
//            orgUcc.updateBackOrg(orgids,user);
                orgUcc.deleteOrg(orgids, user);
                //删除表记录

                //更新缓存
                SysCache.setMap(orgids, CacheConstants.OPER_DELETE, CacheConstants.OBJ_ORG);
                super.showMessage("删除成功!");
                query();
                request.setAttribute("backRefresh", "1");
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "success";
    }

    private void pageUqery() throws BkmsException {
        ActivePageService apm = (ActivePageService) BkmsContext.getBean("sys_activePageService");
//        String rowNums = request.getParameter("rowNum");//Constants.PAGE;
//        int pageNum = request.getParameter("pageNum") == null || "".equals(request.getParameter("pageNum")) ? 1 : Integer.parseInt(request.getParameter("pageNum"));

        String sql = (String) session.getAttribute("activeSql");

        TableVO table = (TableVO) session.getAttribute(OrgConstants.OBJECT);
//        int rowNum = Constants.ACTIVE_PAGE_SIZE;
//        if (rowNums != null) {
//            rowNum = Integer.parseInt(rowNums);
//        }

        apm.queryListBySql(sql, table.getHeader(), StringUtils.join(table.getRightItem(), ","), vo, true);
//        session.setAttribute(OrgConstants.OBJECT, table);
//        session.setAttribute("activeSql", sql);
//        session.setAttribute("pageNum", String.valueOf(pageNum));
//        session.setAttribute("rowNum", String.valueOf(pageVO.getPageSize()));

    }

    private void flag1() throws BkmsException {
        IOrgUCC orgUcc= (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        session.removeAttribute(OrgConstants.OBJECT);
        session.removeAttribute("activeSql");
        session.removeAttribute("pageNum");
        session.removeAttribute("rowNum");


        String superId = request.getParameter("superId");
        String orgName = request.getParameter("orgName");
        String orgType = request.getParameter("orgType");
        String from = request.getParameter("from");
//        TableVO table = new TableVO();
//        String rowNums = (String) session.getAttribute("rowNum");
//        int rowNum = Constants.ACTIVE_PAGE_SIZE;
//        if (rowNums != null) {
//            rowNum = Integer.parseInt(rowNums);
//        }
        TableVO table = orgUcc.queryOrgList(user, vo, orgName, superId, orgType, Constants.YES, from);
        request.setAttribute("table", table);
//        session.setAttribute("activeSql", sql);
//        session.setAttribute("pageNum", String.valueOf("1"));
//        session.setAttribute("rowNum", String.valueOf(page.getPageSize()));
//        session.setAttribute(OrgConstants.OBJECT, table);
    }

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
                String sql = om.queryOrgList(table, orgName, superId, orgType, 1, rowNum, Constants.YES, user, "1");
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
                String sql = om.queryOrgList(table, orgName, superId, orgType, 1, rowNum, Constants.YES, user, "2");
                session.setAttribute("activeSql", sql);
                session.setAttribute("pageNum", String.valueOf("1"));
                session.setAttribute("rowNum", String.valueOf(rowNum));
                session.setAttribute(Constants.OBJECT, table);
            } else if ("2".equals(act)) { //撤销机构恢复
                String orgName = request.getParameter("orgName");
                if (orgName != null)
                    orgName = new String(orgName.getBytes("ISO-8859-1"), "GBK");
                String[] ids = request.getParameterValues("chk");
                String showName = "";
                List list = new ArrayList();
                if (ids != null && ids.length > 0) {
                    OrgManager om = new OrgManager();
                    for (int i = 0; i < ids.length; i++) {
                        OrgBO bo = SysCacheTool.findOrgById(ids[i]);
                        if (bo != null) {
                            String orgCode = bo.getOrgCode();
                            if (orgCode != null && !"".equals(orgCode)) {
                                orgCode = OrgTool.getBackOrgCode(orgCode);
                                int count1 = om.queryOrgCodeCount(orgCode, bo.getOrgId());
                                if (count1 > 0) {
                                    OrgBO bo1 = SysCacheTool.findOrgByCode(orgCode);
                                    if (bo1 != null) {
                                        showName = bo.getName() + "的机构代码与" + bo1.getName() + "重复，请维护机构代码！";
                                    } else {
                                        showName = bo.getName() + "的机构代码重复！";
                                    }
                                } else {
                                    list.add(ids[i]);
                                }

                            } else {
                                list.add(ids[i]);
                            }
                        }
                    }
                }
                if (showName != null && !"".equals(showName.trim())) {
                    ActionError ae = new ActionError("info", showName);
                    this.actionErrors.add(ae);
                }
                if (list != null && list.size() > 0) {
                    OrgManager om = new OrgManager();
                    String[] orgids = ((String[]) list.toArray(new String[0]));
                    om.updateBackOrg(orgids);
                    //删除表记录
                    int count = orgids.length;
                    TableVO table = (TableVO) session.getAttribute(Constants.OBJECT);
                    table.setSetPk("ORGUID");
                    Hashtable rs = table.rowArray2Hash();
                    for (int i = 0; i < count; i++) {
                        rs.remove(orgids[i]);
                    }
                    int size2 = rs.size();
                    RecordVO[] row = new RecordVO[size2];
                    Enumeration values = rs.elements();
                    while (values.hasMoreElements()) {
                        row[--size2] = (RecordVO) values.nextElement();
                    }
                    table.setRowData(row);
                    session.setAttribute(Constants.OBJECT, table);
                    //更新缓存
                    SysCache.setMap(orgids, SysCache.OPER_UPDATE, SysCache.OBJ_ORG);
                    request.setAttribute("backRefresh", "1");
                }
            } else if ("3".equals(act)) {  //机构删除
                String orgName = request.getParameter("orgName");
                if (orgName != null)
                    orgName = new String(orgName.getBytes("ISO-8859-1"), "GBK");
                String[] ids = request.getParameterValues("chk");
                String showName = "";
                List list = new ArrayList();
                if (ids != null) {
                    for (int i = 0; i < ids.length; i++) {

                        if (OrgTool.checkWageUnit(ids[i])) {
                            showName += OrgTool.getOrgName(ids[i]) + "是发薪机构，不能删除!\\n";
                        } else {
                            list.add(ids[i]);
                        }
                    }
                }
                if (showName != null && !"".equals(showName.trim())) {
                    ActionError ae = new ActionError("info", showName);
                    this.actionErrors.add(ae);
                }
                if (list != null && list.size() > 0) {
                    OrgManager om = new OrgManager();
                    String[] orgids = ((String[]) list.toArray(new String[0]));

                    om.checkOrgSubOrg(orgids);
                    om.deleteOrg(orgids, user);
                    //删除表记录
                    int count = orgids.length;
                    TableVO table = (TableVO) session.getAttribute(Constants.OBJECT);
                    table.setSetPk("ORGUID");
                    Hashtable rs = table.rowArray2Hash();
                    for (int i = 0; i < count; i++) {
                        rs.remove(orgids[i]);
                    }
                    int size2 = rs.size();
                    RecordVO[] row = new RecordVO[size2];
                    Enumeration values = rs.elements();
                    while (values.hasMoreElements()) {
                        row[--size2] = (RecordVO) values.nextElement();
                    }
                    table.setRowData(row);
                    session.setAttribute(Constants.OBJECT, table);
                    //更新缓存
                    SysCache.setMap(orgids, SysCache.OPER_DELETE, SysCache.OBJ_ORG);
                }

                request.setAttribute("delRefresh", "1");
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
}
