package com.becoda.bkms.emp.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.emp.pojo.vo.PersonChangeVO;
import com.becoda.bkms.emp.ucc.IPersonUCC;
//import com.becoda.bkms.sfjk.SfjkConstants;
//import com.becoda.bkms.sfjk.ucc.ISfjkMailTypeUCC;
import com.becoda.bkms.util.BkmsContext;




import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-27
 * Time: 14:26:49
 * To change this template use File | Settings | File Templates.
 */
public class PersonDismissAction extends GenericAction {
     private  PersonChangeVO personchangevo;
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public PersonChangeVO getPersonchangevo() {
        return personchangevo;
    }

    public void setPersonchangevo(PersonChangeVO personchangevo) {
        this.personchangevo = personchangevo;
    }

    public String init() throws BkmsException {
//        System.out.println(user);
        return "dismissPage";
    }

    //逻辑减员
    public String cancel() throws BkmsException {
        IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
          HttpServletRequest request= ServletActionContext.getRequest();
          HttpSession session=request.getSession();
          User  user = (User) session.getAttribute(Constants.USER_INFO);
//          String ids=str;
//        personchangevo =new PersonChangeVO();
        String ids =  request.getParameter("pids");

       
        if (ids != null && !"".equals(ids)) {
            personUCC.updateDismissPerson(user, personchangevo, ids.split(","));
            SysCache.setMap(ids.split(","), CacheConstants.OPER_UPDATE, CacheConstants.OBJ_PERSON);
            super.showMessage("人员减员成功");  
            request.setAttribute("message", "人员减员成功");

        }
        return "dismissClosePage";
    }
}
