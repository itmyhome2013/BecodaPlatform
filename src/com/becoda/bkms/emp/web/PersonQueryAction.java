package com.becoda.bkms.emp.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.ucc.IPersonUCC;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.BkmsContext;




import javax.servlet.http.HttpSession;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-9
 * Time: 11:08:19
 */
public class PersonQueryAction extends GenericPageAction {

    public String listPersonBySuper() throws BkmsException {
         IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        String superId = request.getParameter("superId");
        String qryId = request.getParameter("qryId");
        String right = request.getParameter("rightFlag");
        TableVO table;
        if ("0".equals(right)) {
            table = personUCC.queryPersonBySuperRight(user, superId, vo, Constants.NO, false);
        } else {
            table = personUCC.queryPersonBySuperRight(user, superId, vo, Constants.NO, true);
        }
        request.setAttribute("table", table);
        return "personList";
    }

    public String query() throws BkmsException {
         IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        String superId = request.getParameter("superId");
        String qryId = request.getParameter("qryId");
        String name = request.getParameter("name");
        String personCode = request.getParameter("personCode");
        if (name != null) {
            name = name.trim();
        }
        if (personCode != null) {
            personCode = personCode.trim();
        }
//        TableVO table = personUCC.queryPersonList(user, name, personType, superId, page, Constants.NO,qryId);
        TableVO table = personUCC.queryPersonList(user, name, superId, vo, Constants.NO, qryId, personCode);
        request.setAttribute("table", table);
        return "personList";
    }

    public String advanceQuery() throws BkmsException {
        TableVO table = advanceQuery(user, vo, QryConstants.PMS_TYPE_PERSON);
//        PmsAPI pms = new PmsAPI();
//        pms.checkPersonRecord(user, table);
        request.setAttribute("table", table);
        return "personList";
    }

    public String trunPage() throws BkmsException {
         IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        TableVO table = personUCC.queryPersonList(user, vo);
        request.setAttribute("table", table);
        return "personList";
    }

    public String export() throws BkmsException {
         IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        vo.setPageSize(20000);
        vo.setCurrentPage(1);
        String superId = request.getParameter("superId");
        String name = request.getParameter("name");
//        String personType = request.getParameter("personType");
        String personCode = request.getParameter("personCode");
        String qryId = request.getParameter("qryId");
        String adQry = request.getParameter("QRYSQL_hash");
        TableVO table;
        if (adQry == null || "".equals(adQry)) {
            table = personUCC.queryPersonList(user, name, superId, vo, Constants.NO, qryId, personCode);
        } else {
            table = advanceQuery(user, vo, QryConstants.PMS_TYPE_PERSON);
        }
        request.setAttribute("table", table);
        request.setAttribute("sessionKey", "table");
//        session.setAttribute("sessionKey", "table");
//        session.setAttribute("table", table);
        return "export";
    }
}
