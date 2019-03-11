package com.becoda.bkms.emp.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.ucc.IPersonUCC;
import com.becoda.bkms.util.BkmsContext;




import javax.servlet.http.HttpSession;


public class PersonSortAction extends GenericAction {

    public String list() throws BkmsException {
         IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        String superId = request.getParameter("superId");
        PersonBO[] personlist = personUCC.queryPersonBySuper(superId, Constants.NO);
        request.setAttribute("personList", personlist);
        request.setAttribute("superId", superId);
        return "success";
    }


    public String sort() throws BkmsException {
         IPersonUCC personUCC = (IPersonUCC) BkmsContext.getBean("emp_personUCC");
        String superId = request.getParameter("superId");
        String[] orgids = request.getParameterValues("showItem");
        if (orgids != null && orgids.length > 0) {
            PersonBO[] orgs = new PersonBO[orgids.length];
            for (int i = 0; i < orgids.length; i++) {
//                PersonBO bo = SysCacheTool.findPersonById(orgids[i]);
                PersonBO bo = personUCC.findPerson(orgids[i]);
                String sort = "000" + String.valueOf(i + 1);
                sort = sort.substring(sort.length() - 4);
                bo.setSort(sort);
                orgs[i] = bo;
            }
            //修改排序
            personUCC.updatePersonSort(user, orgs);
            //更新缓存
            SysCache.setMap(orgids, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_PERSON);
            PersonBO[] personlist = personUCC.queryPersonBySuper(superId, Constants.NO);
            request.setAttribute("personList", personlist);
        }
        request.setAttribute("superId", superId);
        request.setAttribute("sortRefresh", "1");
        super.showMessage("人员排序成功");
        return "success";
    }

}
