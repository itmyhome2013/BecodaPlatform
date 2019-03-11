package com.becoda.bkms.emp.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
import com.becoda.bkms.emp.ucc.IPersonCodeUCC;
import com.becoda.bkms.emp.ucc.IPersonUCC;
import com.becoda.bkms.emp.util.IDCard18;
import com.becoda.bkms.emp.util.PersonTool;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.ucc.IOrgUCC;
//import com.becoda.bkms.sfjk.ucc.impl.SfjkMailTypeUCCImpl;
//import com.becoda.bkms.sfjk.ucc.ISfjkMailTypeUCC;
//import com.becoda.bkms.sfjk.SfjkConstants;




import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;


public class PersonAddAction extends GenericAction {
    private  PersonVO person;

    public PersonVO getPerson() {
        return person;
    }

    public void setPerson(PersonVO person) {
        this.person = person;
    }

    public String save() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
//        person =new PersonVO();
        String orgId=request.getParameter("orgIdt");
        String deptId=request.getParameter("deptIdt");
        if(!Tools.filterNull("").equals(orgId)){
        	person.setOrgId(orgId);
        }if(!Tools.filterNull("").equals(deptId)){
        	person.setDeptId(deptId);
        }
        byte[] photo = (byte[]) session.getAttribute("images");
        String check = personUCC.checkNewPerson(person);
        if (check != null) {
            this.showMessageDetail(check);
            return "addPage";
        }
        String idNum = person.getIdNum();
        //身份证15位变为18位
        if (idNum != null && !idNum.equals("")) {
            IDCard18 id = new IDCard18();
            idNum = id.Get18(idNum);
            person.setIdNum(idNum);
        }
        person.setAge(PersonTool.year(person.getBirth(), Tools.getSysDate("yyyy-MM-dd")));       //处理年龄
        String perId = personUCC.createPerson(person, user, photo);
//        request.setAttribute("P_PERSONID", perId);
//        request.setAttribute("perIds", perId);
        if (perId != null) {
            super.showMessage("人员添加成功");
            //发送邮件和短信
//            try {
//                ISfjkMailTypeUCC sfjkMailTypeUCC = (ISfjkMailTypeUCC) getBean("sfjk_mailTypeUCC");
//                sfjkMailTypeUCC.sendMail(perId, SfjkConstants.SFJK_MAIL_TYPE_CONDITION_ADD_PERSON);
//            } catch (Exception e) {
//                super.showMessage("发送邮件或短信失败");
//            }
//            return cancel();
        } else {
              super.showMessage("人员添加失败");
        }
        return "addPage";
    }

    public String init() throws BkmsException {
        String deptId = request.getParameter("deptId");
        IOrgUCC orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        if (!"".equals(deptId) && deptId != null) {
            OrgBO bo = orgUcc.findOrgByDept(deptId);
            person =new PersonVO();
            person.setOrgId(bo.getOrgId());
            person.setDeptId(deptId);
            //插入默认值
            person.setStatus("70021001");
            person.setFolk("011501");
            person.setPersonType("70011001");
        }
        return "addPage";
    }

    public String cancel() throws BkmsException {
        return "addPage";
    }
    //查看工号
    public String queryPersonCode() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        String personCode = request.getParameter("personCode");
        List list = personUCC.queryPersonCodeByCode(personCode);
        request.setAttribute("list", list);
        return "viewCode";
    }
    
    public void checkPersonCode() throws BkmsException, IOException{
    	 boolean flag = false;
    	 IPersonCodeUCC personUCC = (IPersonCodeUCC) BkmsContext.getBean("emp_personCodeUCC");
         String personCode = request.getParameter("personCode");
         List list = personUCC.checkPerCodeUsed(personCode);
         if(list!=null&&list.size()>0){
        	 flag = true;
         }
         httpResponse.getWriter().print(flag);
    }

}
