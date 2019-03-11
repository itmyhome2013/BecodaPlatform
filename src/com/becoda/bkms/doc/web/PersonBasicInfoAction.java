package com.becoda.bkms.doc.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.doc.pojo.vo.PersonBasicInfoVO;
import com.becoda.bkms.doc.ucc.IDocBrowseUCC;
import com.becoda.bkms.doc.util.DocExcelExport;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-13
 * Time: 11:09:39
 */
public class PersonBasicInfoAction extends GenericAction {



    public String show() throws BkmsException {
         IDocBrowseUCC browseUCC = (IDocBrowseUCC) BkmsContext.getBean("doc_docBrowseUCC");
        String personId = request.getParameter("personId");
        PersonBasicInfoVO infovo;
        if (personId != null && !personId.equals("")) {
            infovo = browseUCC.queryPersonBasicInfoSet(personId);
        } else {
            infovo = new PersonBasicInfoVO();
        }
        request.setAttribute("personinfovo", infovo);
        return "success";
    }

    public String export() throws BkmsException {
         IDocBrowseUCC browseUCC = (IDocBrowseUCC) BkmsContext.getBean("doc_docBrowseUCC");
        String personId = request.getParameter("personId");
        PersonBasicInfoVO infovo;
        if (personId != null && !personId.equals(""))
            infovo = browseUCC.queryPersonBasicInfoSet(personId);
        else
            infovo = new PersonBasicInfoVO();
            Hashtable b = DocExcelExport.voToHashtable(infovo);
//            String path = this.getServlet().getServletContext().getRealPath("/");
            String path = "";
//            byte[] pt = browseUCC.findPhoto(infovo.getPhoto());
//            String file = DocExcelExport.filefillCard(Tools.getSysDate("yyMMddHHmmss")+"_personBaseInfo.xls", b, "personTemplate.xls", path, pt);
//            request.setAttribute("FileURL", file);
        return "export";
    }
}
