package com.becoda.bkms.doc.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.ucc.IPersonUCC;
//import com.becoda.bkms.emp.web.jobRotation.JRHelper;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.util.Tools;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-24
 * Time: 11:29:42
 */
public class QueryPersonAction extends GenericAction {
     

    public String query() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) getBean("emp_personUCC");
        String personName = Tools.filterNull(request.getParameter("personName"));
        String orgId = Tools.filterNull(request.getParameter("orgId"));
        String sql = Tools.filterNull(request.getParameter("filterSQL"));
        if (sql != null) {
            sql = sql.replaceAll("＇", "'");
        }
        String orgTree = "";
        if (orgId != null && !"".equals(orgId)) {
            Org bo = SysCacheTool.findOrgById(orgId);
            if (bo != null) {
                orgTree = bo.getTreeId();
            }
        }
        List resultList;
        //只拥有查询权限
//        String scale = JRHelper.getAddSql(user,"p",false);
//        String tSql = " and p.name like '%" + personName + "%'" + " and " + scale;
        String tSql = " and p.name like '%" + personName + "%'" ;
        if (!"".equalsIgnoreCase(sql)) {
            tSql += " and " + sql;
        }
        if (!"".equals(orgTree)) {
            tSql += " and  p.deptTreeId like '" + orgTree + "%'";
        }
        resultList = new ArrayList();
        PersonBO[] personArray = personUCC.queryAllPerson(tSql);
        if (personArray != null && personArray.length > 0) {
            for (int i = 0; i < personArray.length; i++) {
                personArray[i].setOrgId(SysCacheTool.interpretOrg(personArray[i].getOrgId()));
                personArray[i].setDeptId(SysCacheTool.interpretOrg(personArray[i].getDeptId()));
                personArray[i].setSex(SysCacheTool.interpretCode(personArray[i].getSex()));
                resultList.add(personArray[i]);
            }
        }
        request.setAttribute("resultList", resultList);
        return "success";
    }

//    public ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//        String forward = "success";
//        String personName = Tools.filterNull(request.getParameter("personName"));
//        String orgId = Tools.filterNull(request.getParameter("orgId"));
//        String orgTree = "";
//        if (orgId != null && !"".equals(orgId)) {
//            Org bo = SysCacheTool.findOrgById(orgId);
//            if (bo != null) {
//                orgTree = bo.getTreeId();
//            }
//        }
//        String sql = Tools.filterNull(request.getParameter("filterSQL"));
//
//        List resultList = null;
//        try {
////            PersonManager mgr = new PersonManager();
//
//
//        } catch (Exception e) {
//
//        }
//        return mapping.findForward(forward);
//    }
}
