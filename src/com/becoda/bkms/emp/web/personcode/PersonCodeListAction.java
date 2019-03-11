package com.becoda.bkms.emp.web.personcode;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.emp.pojo.bo.PersonCodeBO;
import com.becoda.bkms.emp.ucc.IPersonCodeUCC;
import com.becoda.bkms.util.BkmsContext;




import javax.servlet.http.HttpSession;

public class PersonCodeListAction extends GenericAction {


    public String list() throws BkmsException {
        IPersonCodeUCC personCodeUCC = (IPersonCodeUCC) BkmsContext.getBean("emp_personCodeUCC");
        PersonCodeBO[] mgList = personCodeUCC.queryPersonCode();
        request.setAttribute("list", mgList);
        return "success";
    }

    public String query() throws BkmsException {
        IPersonCodeUCC personCodeUCC = (IPersonCodeUCC) BkmsContext.getBean("emp_personCodeUCC");
        String org = request.getParameter("org");
        PersonCodeBO[] mgList = null;
        if (org != null && !"".equals(org)) {
            mgList = personCodeUCC.queryPersonCode(org);
        } else {
            mgList = personCodeUCC.queryPersonCode();
        }
        request.setAttribute("list", mgList);
        return "codeList";
    }

    public String del() throws BkmsException {
        IPersonCodeUCC personCodeUCC = (IPersonCodeUCC) BkmsContext.getBean("emp_personCodeUCC");
        String[] pk = request.getParameterValues("chk");
        if (pk != null) {
            personCodeUCC.deletePersonCode(user, pk);
        }
        showMessageDetail("删除工号设置成功");
        return list();
    }

//    public ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//        String act = request.getParameter("act");
//        String org = request.getParameter("org");
//        String forward = "success";
//        try {
//            if (act == null) {
//                PersonCodeManager mgManager = new PersonCodeManager();
//                PersonCodeBO [] mgList = mgManager.queryPersonCode();
//                request.setAttribute("list", mgList);
//            }
//            if ("del".equals(act)) {
//                PersonCodeManager mgManager = new PersonCodeManager();
//                String[] pk = request.getParameterValues("chk");
//                if (pk != null) {
//                    for (int i = 0; i < pk.length; i++) {
//                        mgManager.deletePersonCode(pk[i]);
//                    }
//                }
//                PersonCodeBO [] mgList = mgManager.queryPersonCode();
//                request.setAttribute("list", mgList);
//            }
//            if ("query".equals(act)) {
//                PersonCodeManager mgManager = new PersonCodeManager();
//                PersonCodeBO [] mgList = null;
//                if (org != null && !"".equals(org)) {
//                    mgList = mgManager.queryPersonCode(org);
//                } else {
//                    mgList = mgManager.queryPersonCode();
//                }
//                request.setAttribute("list", mgList);
//            }
//        } catch (BkmsException he) {
//            ActionError error = new ActionError("info", he.getFlag(), he.getMessage(), he.toString());
//            this.actionErrors.add(error);
//        } catch (Exception e) {
//            ActionError ae = new ActionError("info", "<div style='color:red'>错误</div>", e.getMessage(), e.toString());
//            this.actionErrors.add(ae);
//        }
//        return mapping.findForward(forward);
//    }


}


