package com.becoda.bkms.org.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.org.OrgConstants;
import com.becoda.bkms.org.pojo.vo.OrgVO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

import javax.servlet.http.HttpSession;
import java.util.List;

public class OrgAddAction extends GenericAction {
    private OrgVO org ;

    public OrgVO getOrg() {
        return org;
    }
    public void setOrg(OrgVO org) {
        this.org = org;
    }

    public String changePage() throws BkmsException {
        org = new OrgVO();
        String nature = org.getNature();
        if (OrgConstants.NATURE_FH_1.equals(nature) || OrgConstants.NATURE_FH_2.equals(nature) || OrgConstants.NATURE_FH_3.equals(nature)
                || OrgConstants.NATURE_FH_4.equals(nature)) {
            nature = OrgConstants.NATURE_FH;
        }
        request.setAttribute("nature", nature);
        return "success";
    }



    public String cancel() throws BkmsException {

        return "cancel";
    }

    /**
     * 机构新增—初始化页面
     * fxj
     * @return
     * @throws BkmsException
     */
    public String init() throws BkmsException {
        org = new OrgVO();
        String superId=hrequest.getParameter("superId");
        Org orgbo= SysCacheTool.findOrgById(superId);
//        Tools.copyProperties(org, orgbo);
        if(orgbo!=null){
           org.setSuperId(orgbo.getOrgId());
        }
        return "cancel";
    }
     /**
     * 机构新增
     * fxj
     * @return
     * @throws BkmsException
     */
    public String add() throws BkmsException {
        try {
             IOrgUCC orgUcc=(IOrgUCC) BkmsContext.getBean("org_orgUCC");
            List list = orgUcc.queryOrgName(org.getSuperId(), org.getOrgName());
            if (list != null && list.size() > 0) {
                super.showMessage("当前主管机构下已有该机构名称!");
                return changePage();
            }
            String orgId = orgUcc.createOrg(user, "B001", org);
            //更新缓存
            SysCache.setMap(new String[]{orgId}, CacheConstants.OPER_ADD, CacheConstants.OBJ_ORG);
            request.setAttribute("P_ORGID", org.getSuperId());
            super.showMessage("保存成功");
            request.setAttribute("reflash", "reflash");
            return cancel();
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "success";
    }

    private String list(User user, String s) {
        return s;
    }

    /*
    public ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String act = request.getParameter("act");
        String forward = "cancel";
        try {
            if ("1".equals(act)) {
                 OrgManager pm = new OrgManager();
                 OrgVO org = (OrgVO) form;
                List list = pm.queryOrgName(org.getSuperId() , org.getOrgName());
                if(list!=null && list.size()>0){
                    super.showMessageDetail("当前主管机构下已有该机构名称!");
                    return mapping.findForward(forward);
                }

                byte[] photo = (byte[]) session.getAttribute("images");
                String orgId = pm.createOrg(org, user, photo);
                //更新缓存
                SysCache.setMap(new String[]{orgId}, SysCache.OPER_ADD, SysCache.OBJ_ORG);
                SysCache.updateSubStrMap(SysCache.OBJ_ORG, null, org.getSuperId());
                request.setAttribute("P_ORGID",orgId);
                forward = "success";
            } else if ("2".equals(act)) {
                form = new OrgVO();
                forward = "cancel";
            }
        } catch (HrmsException e) {
            ActionError ae = new ActionError("info", e.getFlag(), e.getMessage(), e.toString());
            this.actionErrors.add(ae);
        } catch (Exception e) {
            ActionError ae = new ActionError("info", "<div style='font:red'>错误</div>", e.getMessage(), e.toString());
            this.actionErrors.add(ae);
        }
        return mapping.findForward(forward);
    }*/
}
