package com.becoda.bkms.org.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.emp.ucc.IEmpInfoManageUCC;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.util.BkmsContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


public class OrgInfoEditAction extends GenericAction {
//    IOrgUCC orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");

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

    //
    public String find() throws BkmsException {
        String flag = request.getParameter("flag");
        String forward = "one";
        String setId = hrequest.getParameter("setId");
        String fk = hrequest.getParameter("fk");
        String pk = hrequest.getParameter("pk");
        TableVO table = new TableVO();
        IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
        if ((fk == null || "".equals(fk))) {
            if (!"B001".equals(setId)) {

            } else {
                table = ucc.findEmpInfoSetRecord(user, setId, pk);
            }
        } else {
            InfoSetBO infoSet = SysCacheTool.findInfoSet(setId);
            if (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(infoSet.getSet_rsType())) {
//                table = activePageService.queryPageInfo(setId, fk, "", user);
                table = ucc.findEmpInfoSetRecord(user, setId, fk);
                forward = "one";
            } else {
//                table = activePageService.queryPageInfo(setId, null, fk, user);
                table = ucc.queryEmpInfoSetRecordList(user, setId, fk);
                forward = "many";
            }
        }
        request.setAttribute("tableVO", table);
        return forward;
    }
}
