package com.becoda.bkms.emp.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.pojo.vo.RecoverVO;
import com.becoda.bkms.emp.ucc.IPersonUCC;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Endecode;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.pms.api.PmsAPI;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-28
 * Time: 16:43:43
 * To change this template use File | Settings | File Templates.
 */

public class PersonDismissListAction extends GenericPageAction {

    private  RecoverVO rvo;

    public RecoverVO getRvo() {
        return rvo;
    }

    public void setRvo(RecoverVO rvo) {
        this.rvo = rvo;
    }

    public String query() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        String superId =request.getParameter("superId");

        String name = request.getParameter("name");
        String dismissType = request.getParameter("dismissType");
        String disstartDate = request.getParameter("disstartDate");
        String disendDate = request.getParameter("disendDate");
        TableVO table = personUCC.queryDismissPer(user, name, dismissType, disstartDate, disendDate, superId, vo);
//                  String sql = pm.queryPersonList(table, name, personType, superId, 1, rowNum, Constants.YES, user, Constants.DEFAULT_QUERY_PERSON);
//        TableVO table = personUCC.queryPersonList(user, name, dismissType, superId, page, Constants.YES);
        request.setAttribute("table", table);
        return "personList";
    }

    public String listPersonBySuper() throws BkmsException {
        return query();
    }

    public String gotoRecover() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        try {
            String perIds = request.getParameter("perIds");
            PersonBO bo = personUCC.findPerson(perIds);
            request.setAttribute("PersonBO", bo);
            return "recover";
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return "recover";
        }
    }
    //减员恢复
    public String recoverForCzb() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        try {
//             rvo =new RecoverVO();
            String perId = rvo.getPerId();
            if (perId != null) {
                personUCC.updateBackPersonForCzb(user, rvo);
                request.setAttribute("message","该人员已重新入职，请及时维护人员的基本信息、工作经历、职务等各项信息!");
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "close";
    }

    public String recover() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        try {
            String[] ids = request.getParameterValues("chk");
            List list = new ArrayList();
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    PersonBO bo = personUCC.findPerson(ids[i]);
                    if (!(("013523".equals(bo.getPersonType()) || "013522".equals(bo.getPersonType())) && Constants.YES.equals(bo.getRetireCancel()) && Constants.NO.equals(bo.getPersonCancel())))
                    { //不是逻辑减员的离退休人员不能恢复
                        list.add(ids[i]);
                    }
                }
                String[] ids2 = (String[]) list.toArray(new String[list.size()]);
                personUCC.updateBackPerson(user, ids2);
                this.showMessage("减员恢复成功!");
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return listPersonBySuper();
    }

    public String advanceQuery() throws BkmsException {

        TableVO table = advanceQuery(user, vo, QryConstants.PMS_TYPE_PERSON);

        PmsAPI pms = new PmsAPI();
        pms.checkPersonRecord(user, table);

        request.setAttribute("table", table);
        return "personList";
    }


    public String delete() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        String[] ids = request.getParameterValues("chk");
        if (ids != null) {
            personUCC.deletePerson(ids, user);
            this.showMessage("删除成功!");
        }
        return listPersonBySuper();
    }

}
