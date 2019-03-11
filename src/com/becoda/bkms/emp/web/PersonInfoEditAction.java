package com.becoda.bkms.emp.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.ucc.IEmpInfoManageUCC;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;


import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-22
 * Time: 11:16:21
 * To change this template use File | Settings | File Templates.
 */
public class PersonInfoEditAction extends GenericPageAction {
    public String show() throws BkmsException {
        try {
            IEmpInfoManageUCC ucc = (IEmpInfoManageUCC) getBean("emp_empInfoManageUCC");
            String setId = request.getParameter("setId");
            String pk = request.getParameter("pk");
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType())) {
                TableVO table = ucc.queryEmpInfoSetRecordList(user, setId, pk);
                request.setAttribute("tableVO", table);
                return "many";
            } else {
                TableVO table = ucc.findEmpInfoSetRecord(user, setId, pk);
                request.setAttribute("tableVO", table);
                return "one";
            }
        } catch (Exception e) {
           BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return "";
        }
    }
}
