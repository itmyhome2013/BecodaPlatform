package com.becoda.bkms.org.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-4-4
 * Time: 9:41:02
 * To change this template use File | Settings | File Templates.
 */
public class OrgSortAction extends GenericAction {


    public String sort() throws BkmsException {
        IOrgUCC orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
        String[] orgids = request.getParameterValues("showItem");
        String superId = request.getParameter("superId");
        String superSort = "";
        if (!"".equals(Tools.filterNull(superId))) {
            OrgBO superOrg = orgUcc.findOrgBO(superId);
            if (superOrg != null) {
                superSort = superOrg.getOrgSort();
            }
            if (orgids != null && orgids.length > 0) {
                OrgBO[] orgs = new OrgBO[orgids.length];
                for (int i = 0; i < orgs.length; i++) {
                    OrgBO bo = orgUcc.findOrgBO(orgids[i]);
                    if (i < 9) {
                        bo.setOrgSort(superSort + "00" + (i + 1));
                    } else if (i < 99) {
                        bo.setOrgSort(superSort + "0" + (i + 1));
                    } else {
                        bo.setOrgSort(superSort + (i + 1));
                    }
                    orgs[i] = bo;
                }
                //修改排序
                orgUcc.updateOrgSort(orgs, superId, user);
                //更新缓存
                SysCache.setMap(orgids, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_ORG);
                SysCache.updateSubStrMap(CacheConstants.OBJ_ORG, null, superId);
                this.showMessage("机构排序成功!");
                request.setAttribute("sortRefresh", "1");
                request.setAttribute("superId", superId);
            }
//            this.showMessage("机构排序成功!");
        }
        return "success";
    }

    /*
    public ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String act = request.getParameter("act");
        String forward = "success";
        try {
            if ("1".equals(act)) {
                OrgManager om = new OrgManager();
                String[] orgids = request.getParameterValues("showItem");
                String superId = request.getParameter("superId");
                String superSort = "";
                OrgBO superOrg = SysCacheTool.findOrgById(superId);
                if (superOrg != null) {
                    superSort = superOrg.getOrgSort();
                }
                if (orgids != null && orgids.length > 0) {
                    OrgBO[] orgs = new OrgBO[orgids.length];
                    for (int i = 0; i < orgs.length; i++) {
                        OrgBO bo = SysCacheTool.findOrgById(orgids[i]);
                        if (i < 9) {
                            bo.setOrgSort(superSort + "00" + (i + 1));
                        } else if (i < 99) {
                            bo.setOrgSort(superSort + "0" + (i + 1));
                        } else {
                            bo.setOrgSort(superSort + (i + 1));
                        }
                        orgs[i] = bo;
                    }
                    //修改排序
                    om.updateOrgSort(orgs, superId);
                    //更新缓存
                    SysCache.setMap(orgids, SysCache.OPER_UPDATE, SysCache.OBJ_ORG);
                    SysCache.updateSubStrMap(SysCache.OBJ_ORG, null, superId);

                }
                request.setAttribute("sortRefresh", "1");
                this.showMessage("机构排序成功!");
            }

        } catch (BkmsException e) {
            ActionError ae = new ActionError("info", e.getFlag(), e.getMessage(), e.toString());
            this.actionErrors.add(ae);
        } catch (Exception e) {
            ActionError ae = new ActionError("info", "<div style='font:red'>错误</div>", e.getMessage(), e.toString());
            this.actionErrors.add(ae);
        }
        return mapping.findForward(forward);
    }*/
}
