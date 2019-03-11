package com.becoda.bkms.emp.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
import com.becoda.bkms.emp.ucc.IEmpInfoManageUCC;
import com.becoda.bkms.emp.ucc.IPersonUCC;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.pms.PmsConstants;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-16
 * Time: 11:10:47
 * To change this template use File | Settings | File Templates.
 */
public class EmpInfoManageAction extends GenericPageAction {
    public String findRecord() throws BkmsException {
        try {
            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
            String setId = request.getParameter("setId");
            String pk = request.getParameter("pk");
            TableVO table = ucc.findEmpInfoSetRecord(user, setId, pk);
            request.setAttribute("tableVO", table);
        } catch (Exception e) {
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "editPage";
    }

    public String listRecord() throws BkmsException {
        try {
            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
            String setId = request.getParameter("setId");
            String fk = request.getParameter("fk");
            TableVO table = ucc.queryEmpInfoSetRecordList(user, setId, fk);
            request.setAttribute("setId", setId);
            request.setAttribute("tableVO", table);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "listPage";
    }
    public String listEvaRecord() throws BkmsException {
        try {
            String setId = request.getParameter("setId");
            String fk = request.getParameter("fk");
            TableVO table = new TableVO();
            InfoSetBO set=SysCacheTool.findInfoSet(setId);
            InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(setId).toArray(new InfoItemBO[0]);
            ActivePageService activePageService=(ActivePageService)this.getBean("sys_activePageService");
            String rows[][] = activePageService.queryRecord(set, header, fk);
            table.setInfoSet(set);
            table.setHeader(header);
            table.setRows(rows);
            table.setTableRight(PmsConstants.PERMISSION_READ);
            request.setAttribute("tableVO", table);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "listPage";
    }

    public String createRecord() throws BkmsException {
        try {
            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
            String setId = request.getParameter("setId");
            String fk = request.getParameter("fk");
            TableVO table = ucc.createBlankEmpInfoSetRecord(user, setId, fk);
            request.setAttribute("tableVO", table);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "editPage";
    }

    public String saveRecord() throws BkmsException {
        String forward = "listPage";
        try {
            //编辑保存指标集
            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
            byte[] photo = (byte[]) session.getAttribute("images");
            String setId = request.getParameter("setId");
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            String pk = request.getParameter(set.getSetPk());
            Map map = hrequest.getParameterMap();
            if (pk != null && pk.length() > 0) {
                if ("A001".equals(setId)) {   //add 校验 start
                    IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
                    String personCode = (String) map.get("A001735");
                    String n_name = (String)map.get("A001001");
                    String idNum = (String) map.get("A001077");
                    PersonBO bo = personUCC.findPerson(pk);
                    PersonVO vo = new PersonVO();
                    if (!personCode.equals(bo.getPersonCode())) {
                        vo.setPersonCode(personCode);
                    } else if (!idNum.equals(bo.getIdCard())) {
                        vo.setIdNum(idNum);
                    }
                    String check = personUCC.checkNewPerson(vo);
                    if (check != null) {
                        this.showMessageDetail(check);
                        request.setAttribute("tableVO", ucc.findEmpInfoSetRecord(user, setId, pk));
                        return "editPage";
                    }
                    ucc.updatePersonBO(n_name, pk);
                }    
                //add 校验 end
                //ucc.updateEmpInfoSetRecord(user, setId, map, pk);
              
            } else {
                ucc.addEmpInfoSetRecord(user, setId, hrequest.getParameterMap());
            }
            TableVO table = null;
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType())) {
                String fk = request.getParameter(set.getSetFk());
                table = ucc.queryEmpInfoSetRecordList(user, setId, fk);
            } else {
                table = ucc.findEmpInfoSetRecord(user, setId, pk);
                forward = "editPage";
            }
            request.setAttribute("tableVO", table);
            super.showMessage("保存成功");
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return forward;
    }

    public String delRecord() throws BkmsException {
        try {
            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
            String setId = request.getParameter("setId");
            String fk = request.getParameter("fk");
            String[] pk = request.getParameterValues("chk");
            if (pk != null && pk.length > 0) {
                ucc.deleteEmpInfoSetRecord(user, setId, pk, fk);
                super.showMessage("删除成功");
            }
            TableVO table = ucc.queryEmpInfoSetRecordList(user, setId, fk);
            request.setAttribute("tableVO", table);

        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "listPage";
    }
    public String forword() {
        return "success";
    }

}
