package com.becoda.bkms.org.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.org.pojo.vo.OrgSetVO;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;


public class OrgDismissAction extends GenericPageAction {
    private  OrgSetVO orgsetvo;

    public OrgSetVO getOrgsetvo() {
        return orgsetvo;
    }

    public void setOrgsetvo(OrgSetVO orgsetvo) {
        this.orgsetvo = orgsetvo;
    }
    public String list() throws BkmsException {
         IOrgUCC  orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        String superId = request.getParameter("superId");
        String orgName = request.getParameter("orgName");
        String orgType = request.getParameter("orgType");
        String orgLevel = request.getParameter("orgLevel");
        String from = request.getParameter("from");
        TableVO table = orgUcc.queryOrgList(user, vo, orgName, superId, orgType, orgLevel, Constants.NO, from);
        request.setAttribute("table", table);
        return "list";
    }

    public String query() throws BkmsException {
         IOrgUCC  orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        String superId = request.getParameter("superId");
        String orgName = request.getParameter("orgName");
        String orgType = request.getParameter("orgType");
        TableVO table = orgUcc.queryOrgList(user, vo, orgName, superId, orgType, Constants.NO, "");
        request.setAttribute("table", table);
        return "list";
    }

    public String toDismiss() throws BkmsException {
        return "dismiss";
    }

    public String dismissOrg() throws BkmsException {
         IOrgUCC  orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        try {
            String ids = request.getParameter("ids");
            orgsetvo =new OrgSetVO();
            if (ids != null && !"".equals(ids)) {
                orgUcc.updateDismissOrg(orgsetvo, ids.split(","), user);
                SysCache.setMap(ids.split(","), CacheConstants.OPER_UPDATE, CacheConstants.OBJ_ORG);
                request.setAttribute("message", "撤销成功!");
            }
            return "close";
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return "";
        }
    }

    public String  advanceQueryOrg() throws BkmsException {
        try {
            TableVO table = super.advanceQuery(user, vo, QryConstants.PMS_TYPE_ORG);
            request.setAttribute("table", table);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
//            return mapping.getInputForward();
        }
         return "list";
    }
    
}
