package com.becoda.bkms.org.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.ucc.IEmpInfoManageUCC;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Endecode;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-16
 * Time: 11:10:47
 * To change this template use File | Settings | File Templates.
 */
public class OrgInfoManageAction extends GenericPageAction {


    public String init() throws BkmsException {
        ActivePageService activePageService = (ActivePageService) BkmsContext.getBean("sys_activePageService");
        String flag = request.getParameter("flag");
        String forward = "";
        String setId = request.getParameter("setId");
        String fk = request.getParameter("fk");
        String pk = request.getParameter("pk");
        TableVO table = null;
        if ((fk == null || "".equals(fk))) {
            if ("B001".equals(setId)) {
                table = activePageService.getBlankInfoSetRecord(setId, pk, fk);
            }
        } else {
            InfoSetBO infoSet = SysCacheTool.findInfoSet(setId);

            if (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(infoSet.getSet_rsType())) {
//                table = activePageService.queryPageInfo(setId, fk, "", user);
                ArrayList list = SysCacheTool.queryInfoItemBySetId(setId);
                InfoItemBO[] ibs = (InfoItemBO[]) list.toArray(new InfoItemBO[list.size()]);
                String[] ss = activePageService.findRecord(infoSet, ibs, fk);
                forward = "one";
            } else {
                table = new TableVO();
                InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(infoSet.getSetId()).toArray(new InfoItemBO[SysCacheTool.queryInfoItemBySetId(infoSet.getSetId()).size()]);
                String[][] rows = null;
                if (pk == null) {
                    rows = new String[1][header.length];
                    String[] blank = new String[header.length];
                    for (int i = 0; i < header.length; i++) {
                        blank[i] = header[i].getItemDefaultValue();
                    }
                    rows[0] = blank;
                } else {
                    String row[] = activePageService.findRecord(infoSet, header, pk);
                    rows = new String[1][header.length];
                    rows[0] = row;
                }
                table.setInfoSet(infoSet);
                table.setHeader(header);
                table.setRows(rows);

                PmsAPI pms = new PmsAPI();
                pms.checkPersonRecord(user, table);
                table.setTableRight(pms.checkTable(user, setId));
                forward = "many";
            }
        }
        request.setAttribute("table", table);
        request.setAttribute("tableId", "table");
        return forward;
    }

    public String findRecord() throws BkmsException {
        IOrgUCC orgUCC = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        try {

            String setId = Endecode.base64Decode(request.getParameter("setId"));
            String pk =  hrequest.getParameter("pk");
            TableVO table = orgUCC.findOrgInfoSetRecord(user, setId, pk);
            request.setAttribute("tableVO", table);
//            request.setAttribute("tableId","tableVO");
        } catch (Exception e) {
           BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
           this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "one";
    }

    public String listRecord() throws BkmsException {
        IOrgUCC orgUCC = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        try {
            String setId = Endecode.base64Decode(request.getParameter("setId"));
            String fk = request.getParameter("fk");
            TableVO table = orgUCC.queryOrgInfoSetRecordList(user, setId, fk);
            request.setAttribute("tableVO", table);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
           this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "many";
    }

    public String addRecord() throws BkmsException {
        IOrgUCC orgUCC = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        try {
            
            String setId =Endecode.base64Decode(request.getParameter("setId"));
            String fk = Endecode.base64Decode(request.getParameter("fk"));
            TableVO table = orgUCC.getBlankOrgInfoSetRecord(user, setId, fk);
            request.setAttribute("tableVO", table);
        } catch (Exception e) {
           BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
           this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "one";
    }

    public String saveRecord() throws BkmsException {
        IOrgUCC orgUCC = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        String forward = "many";
        try {
            //编辑保存指标集
            String setId = request.getParameter("setId");
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            String pk = request.getParameter(set.getSetPk());
            Map map = hrequest.getParameterMap();
            if (pk != null && pk.length() > 0) {
                orgUCC.updateOrgInfoSetRecord(user, setId, map, pk);
            } else {
                orgUCC.addOrgInfoSetRecord(user, setId, hrequest.getParameterMap());
            }
            TableVO table = null;
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType())) {
                String fk = request.getParameter(set.getSetFk());
                table = orgUCC.queryOrgInfoSetRecordList(user, setId, fk);
            } else {
                table = orgUCC.findOrgInfoSetRecord(user, setId, pk);
                forward = "one";
            }
            request.setAttribute("tableVO", table);
            request.setAttribute("orgRefresh", "orgRefresh");
            super.showMessageDetail("保存成功");
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
           this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return forward;
    }

    public String delRecord() throws BkmsException {
        IOrgUCC orgUCC = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        try {
            String setId = Endecode.base64Decode(request.getParameter("setId"));
            String fk = Endecode.base64Decode(request.getParameter("fk"));
            String[] pk = request.getParameterValues("chk");
            orgUCC.deleteOrgInfoSetRecord(user, setId, pk, fk);
            super.showMessage("删除成功");
            TableVO table = orgUCC.queryOrgInfoSetRecordList(user, setId, fk);
            request.setAttribute("tableVO", table);
            request.setAttribute("orgRefresh", "orgRefresh");
        } catch (Exception e) {
           BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
           this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "many";
    }
//
//    public ActionForward queryEmp(PageVO pageVO, User user, ActionMapping mapping, ActionForm form, HrmsHttpRequest request, HrmsHttpResponse response, HttpSession session) throws BkmsException {
//        try {
//            PersonQueryVO form1 = (PersonQueryVO) form;
//            String name = Tools.filterNull(form1.getPersonName());
//            String empType = Tools.filterNull(form1.getPersonType());
//            String orgId = Tools.filterNull(form1.getOrgId());
//            if (name.length() > 0 || empType.length() > 0 || orgId.length() > 0) {
//                Org org = SysCacheTool.findOrgById(form1.getOrgId());
//                String orgTreeId = Tools.filterNull(org.getTreeId());
//                TableVO table = orgUCC.queryEmpListByCond(user, name, empType, orgTreeId, pageVO);
//                if (table != null) {
//                    request.setAttribute("tableVO", table);
//                    request.setAttribute("QRYSQL_qrySql", pageVO.getQrySql());
//                    request.setAttribute("QRYSQL_showField", pageVO.getShowField());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            ActionMessage error = new ActionMessage("info", "错误:", e.getMessage(), e.toString());
//            this.actionErrors.add(error);
//            return mapping.getInputForward();
//        }
//        return mapping.findForward("listEmp");
//    }

    public String advenceQueryEmp() throws BkmsException {
        try {
            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
            TableVO table = ucc.queryEmpList(user, vo.getQrySql(), vo.getShowField(), vo);
            request.setAttribute("QRYSQL_qrySql", vo.getQrySql());
            request.setAttribute("QRYSQL_showField", vo.getShowField());
            request.setAttribute("tableVO", table);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
           this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "listEmp";
    }

}
