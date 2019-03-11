package com.becoda.bkms.sys.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.sys.pojo.vo.CodeSetVO;
import com.becoda.bkms.sys.ucc.ICodeUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-9
 * Time: 16:35:20
 */
public class CodeAction extends GenericAction {
    private CodeSetVO codeSetForm;

    public CodeSetVO getCodeSetForm() {
        return codeSetForm;
    }

    public void setCodeSetForm(CodeSetVO codeSetForm) {
        this.codeSetForm = codeSetForm;
    }

    public String list() throws BkmsException {
        return list(user, "list");
    }

    private String list(User user, String forward) throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        if ("list".equals(forward)) {
            List list = iCodeUCC.queryCodeSets();
            convertCodeToDescChars(list);
            request.setAttribute("list", list);
        }
        return forward;
    }

    //编辑代码项
    public String makeCodeSetEdit(User user) throws BkmsException {
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        //编辑显示代码集
        CodeSetBO codeSet = iCodeUCC.queryCodeSet(codeSetForm.getSetId());
        Tools.copyProperties(codeSetForm, codeSet);
        return list(user,"edit");
    }

    public String codeSetEdit() throws BkmsException {
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        HttpServletRequest request = ServletActionContext.getRequest();
        //编辑显示代码集
        if(codeSetForm==null){
            codeSetForm=new CodeSetVO();
            codeSetForm.setSetId(request.getParameter("setId"));
        }
        CodeSetBO codeSet = iCodeUCC.queryCodeSet(codeSetForm.getSetId());
        Tools.copyProperties(codeSetForm, codeSet);
        return list(user,"edit");
    }

    public String addCodeSet() throws BkmsException {
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        //增加代码集
        if (iCodeUCC.isExistCodeSetName(codeSetForm.getSetName())) {
            CodeSetBO codeSet = new CodeSetBO();
            Tools.copyProperties(codeSet, codeSetForm);
            String sId = iCodeUCC.getNewSetId();
            codeSet.setSetId(sId);
            iCodeUCC.createBo(codeSet);
            //同步缓存
            List list = new ArrayList();
            list.add(codeSet);
            SysCache.setMap(list, CacheConstants.OPER_ADD, CacheConstants.OBJ_CODESET);
            this.showMessage("新建成功！");
            return list();
        } else {
            throw new BkmsException("代码集名称重复，请重新输入代码集名称！", this.getClass());
        }

    }

    public String saveCodeSet() throws BkmsException {
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        //保存代码集
        CodeSetBO codeSet = new CodeSetBO();
        Tools.copyProperties(codeSet, codeSetForm);
        iCodeUCC.updateCodeSet(codeSet);
        //同步缓存
        List list = new ArrayList();
        list.add(codeSet);
        SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_CODESET);
        this.showMessage("保存成功！");
        return list();
    }

    public String setList() throws BkmsException {
        return list();
    }

    //启用
    public String setOpen() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        //启用
        String[] ids = request.getParameterValues("chk");
        iCodeUCC.makeStatus(true, ids);
        //同步内存
        List list = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            CodeSetBO cSet = SysCacheTool.findCodeSet(ids[i]);
            cSet.setSetStatus(SysConstants.INFO_STATUS_OPEN);
            list.add(cSet);
        }
        SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_CODESET);
        return list();
    }

    //禁用
    public String setBan() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        String[] ids = request.getParameterValues("chk");
        iCodeUCC.makeStatus(false, ids);
        //同步内存
        List list = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            CodeSetBO cSet = SysCacheTool.findCodeSet(ids[i]);
            cSet.setSetStatus(SysConstants.INFO_STATUS_BAN);
            list.add(cSet);
        }
        SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_CODESET);
        return list();
    }

    //删除
    public String setDel() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        String[] ids = request.getParameterValues("chk");
        boolean bl = iCodeUCC.isAllowDelete(ids);
        if (bl) {
            this.showMessage("国标码和系统码不允许删除！");
            return list();
        }
        String str = iCodeUCC.checkCodeSetUsing(ids);
        str = Tools.filterNull(str);
        if (!"".equals(str)) {
            this.showMessage("正在使用，无法删除！");
            return list();
        }
        iCodeUCC.deleteCodeSets(ids);
        //同步内存
//        SysCache.setMap(ids, CacheConstants.OPER_DELETE, CacheConstants.OBJ_CODESET);
        this.showMessage("删除成功！");
        return list();
    }


    public String search() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        String name = request.getParameter("searchName");
        List list = iCodeUCC.queryCodeSetByName(name);
        convertCodeToDescChars(list);
        request.setAttribute("list", list);
        return "list";
    }


    /**
     * @param cItems items
     */
    private void convertCodeToDescChars(List cItems) {
        for (int i = 0; i < cItems.size(); i++) {
            CodeSetBO b1 = (CodeSetBO) cItems.get(i);
            CodeSetBO b = new CodeSetBO();
            Tools.copyProperties(b, b1);

            if (!SysConstants.INFO_STATUS_OPEN.equals(b.getSetStatus())) {
                b.setSetStatus("<font color=red>禁用</font>");
            } else {
                b.setSetStatus("启用");
            }
            if (SysConstants.CODE_LAYER_LEAF.equals(b.getSetLayer())) {
                b.setSetLayer("录到最低层");
            } else {
                b.setSetLayer("录到任意层");
            }
            int st = Integer.parseInt(b.getSetId());
            if (st < 1000) {
                b.setSetScaleName("<font color='green'>国标</font>");
            } else if (Integer.parseInt(b.getSetId()) >= 1000 && st < 2000) {
                b.setSetScaleName("用户");
            } else if (Integer.parseInt(b.getSetId()) >= 2000 && st < 3000) {
                b.setSetScaleName("<font color='red'>系统</font>");
            } else {
                b.setSetScaleName("用户");
            }
            cItems.set(i, b);
        }
    }
}
