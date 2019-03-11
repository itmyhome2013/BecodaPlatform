package com.becoda.bkms.doc.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.doc.pojo.vo.OrgBasicInfoVO;
import com.becoda.bkms.doc.ucc.IDocBrowseUCC;
import com.becoda.bkms.doc.util.DocExcelExport;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-4-11
 * Time: 16:52:02
 * To change this template use File | Settings | File Templates.
 */
public class OrgBasicInfoAction extends GenericAction {

    public String query() throws BkmsException {
        IDocBrowseUCC docBrowseUcc = (IDocBrowseUCC) BkmsContext.getBean("doc_docBrowseUCC");
        String forward = "success";
        String id = hrequest.getParameter("orgId");
        String act = hrequest.getParameter("act");
        String orgId = "";
        try {
            OrgBasicInfoVO orgVO = new OrgBasicInfoVO();
//          DocBrowseManager manager=new DocBrowseManager();
            if (id != null && !"".equalsIgnoreCase(id.trim())) {
                orgId = id.trim();
//            orgVO=manager.queryOrgInfo(orgId);
                orgVO = docBrowseUcc.queryOrgInfo(orgId);
            } else {
                orgVO.setOrgAddress("");
//            orgVO.setArea("");
//            orgVO.setBuzAddress("");
//            orgVO.setBuzRange("");
            }
            request.setAttribute("orgVO", orgVO);

        } catch (Exception e) {
            this.showMessage("错误！");
        }
        return forward;
    }

    public String export() throws BkmsException {
        IDocBrowseUCC docBrowseUcc = (IDocBrowseUCC) BkmsContext.getBean("doc_docBrowseUCC");
        String forward = "success";
        String id = request.getParameter("orgId");
        String orgId = "";
        try {
            OrgBasicInfoVO orgVO = new OrgBasicInfoVO();
            if (id != null && !"".equalsIgnoreCase(id.trim())) {
                orgId = id.trim();
                orgVO = docBrowseUcc.queryOrgInfo(orgId);
            } else {
                orgVO.setOrgAddress("");
            }

            if (orgVO == null) {
                this.showMessage("机构基本信息为空，无法导出！");
            }
            Hashtable b = DocExcelExport.orgVoToHashtable(orgVO);
//            String path = this.getServlet().getServletContext().getRealPath("/");     此处待修改
            String path = "";
            byte[] pt = new byte[]{};
            String file = DocExcelExport.orgExport(Tools.getSysDate("yyMMddHHmmss")+"_orgBaseInfo.xls", b, "orgTemplate.xls", path, pt);
            request.setAttribute("FileURL", file);

        } catch (Exception e) {
            this.showMessage("导出文件错误！");
        }
        return "export";
    }
}
