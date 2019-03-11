package com.becoda.bkms.emp.web.personcode;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.emp.pojo.bo.PersonCodeBO;
import com.becoda.bkms.emp.pojo.vo.PersonCodeVO;
import com.becoda.bkms.emp.ucc.IPersonCodeUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;




import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-16
 * Time: 16:52:28
 * To change this template use File | Settings | File Templates.
 */
public class PersonCodeEditAction extends GenericAction {

   private PersonCodeVO pform;

    public PersonCodeVO getPform() {
        return pform;
    }

    public void setPform(PersonCodeVO pform) {
        this.pform = pform;
    }

    public String save() throws BkmsException {
         IPersonCodeUCC personCodeUCC = (IPersonCodeUCC) BkmsContext.getBean("emp_personCodeUCC");
//        增加、修改一个区段
        pform =new PersonCodeVO();
        PersonCodeBO po = new PersonCodeBO();
        String max = pform.getMax();
        Tools.copyProperties(po, pform);
        po.setMax(max);
        if (null != po.getId() && !"".equals(po.getId())) {
            personCodeUCC.modifyPersonCode(user, po);
//            this.showMessage("修改成功！");
            request.setAttribute("message", "修改成功！");
        } else {
            po.setMax("0");
            personCodeUCC.addPersonCode(user, po);
//            this.showMessage("增加成功！");
            request.setAttribute("message", "增加成功！");
        }
        return "success";
    }

    public String toUpdate() throws BkmsException {
        IPersonCodeUCC personCodeUCC = (IPersonCodeUCC) BkmsContext.getBean("emp_personCodeUCC");
        String id = request.getParameter("id");
        PersonCodeBO po = personCodeUCC.findPersonCode(id);
//        Tools.copyProperties(form, po);
        Tools.copyProperties(pform, po);
        return "success";
    }

//    public ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//        String act = request.getParameter("act");
//        String id = request.getParameter("id");
//        String forward = "success";
//        PersonCodeVO pform =(PersonCodeVO)form;
//        try {
//            if ("save".equals(act)) {
//                //增加、修改一个区段
//                PersonCodeBO po = new PersonCodeBO();
//                PersonCodeManager mgManager = new PersonCodeManager();
//                String max = pform.getMax();
//                Tools.copyProperties(po, pform);
//                po.setMax(max);
//                if (null != po.getId() && !"".equals(po.getId())) {
//                    mgManager.modifyPersonCode(po);
//                    this.showMessage("修改成功！");
//
//
//                } else {
//                    po.setMax("0");
//                    mgManager.addPersonCode(po);
//                    this.showMessage("增加成功！");
//
//                }
//                request.setAttribute("info", "<script>window.opener.location.href=window.opener.location.href</script>");
//            }
//            if ("update".equals(act)) {
//                PersonCodeManager mgManager = new PersonCodeManager();
//                PersonCodeBO po = mgManager.findPersonCode(id);
//                Tools.copyProperties(form, po);
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
