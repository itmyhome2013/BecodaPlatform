package com.becoda.bkms.sys.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.sys.pojo.vo.CodeItemVO;
import com.becoda.bkms.sys.ucc.ICodeUCC;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-9
 * Time: 17:01:14
 */
public class CodeItemAction extends GenericAction {
    private CodeItemVO codeitemform;

    public CodeItemVO getCodeitemform() {
        return codeitemform;
    }

    public void setCodeitemform(CodeItemVO codeitemform) {
        this.codeitemform = codeitemform;
    }

    public String list() throws BkmsException {
        return list("list");
    }

    private String list(String forward) throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        if ("list".equals(forward)) {
            ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
            String setId = request.getParameter("setId");
            if(Tools.filterNull(setId).equals("")){
                setId=codeitemform.getSetId();
            }
            List list1 = codeUCC.queryCodeItems(setId, "");
            request.setAttribute("setId", setId);
            request.setAttribute("list", list1);
        }
        return forward;
    }


    public String toItemEdit() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
//        CodeItemVO codeitemform = (CodeItemVO) form;
        //显示编辑列表信息
        String itemId = codeitemform.getItemId();
        itemId = itemId == null ? "" : itemId;
        CodeItemBO ci = (CodeItemBO) codeUCC.findBo(CodeItemBO.class, itemId);
        Tools.copyProperties(codeitemform, ci);
        if (ci != null) {
            String s = "";
            if (!"-1".equals(codeitemform.getItemSuper())) {
                s = codeitemform.getTreeId().substring(codeitemform.getTreeId().length() - 4);
                codeitemform.setSeqId(s);
            } else {
                s = codeitemform.getTreeId();
                codeitemform.setSeqId(s);
                //itemSuper 如果是-1 则不显示
                codeitemform.setItemSuper("");
            }
        }
        return list("edit");
    }
    public String itemEdit() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
//        CodeItemVO codeitemform = (CodeItemVO) form;
        //显示编辑列表信息
        if(codeitemform==null){
            codeitemform=new CodeItemVO();
            codeitemform.setItemId(request.getParameter("itemId"));
        }
        String itemId = codeitemform.getItemId();
        itemId = itemId == null ? "" : itemId;
        CodeItemBO ci = (CodeItemBO) codeUCC.findBo(CodeItemBO.class, itemId);
        Tools.copyProperties(codeitemform, ci);
        if (ci != null) {
            String s = "";
            if (!"-1".equals(codeitemform.getItemSuper())) {
                s = codeitemform.getTreeId().substring(codeitemform.getTreeId().length() - 4);
                codeitemform.setSeqId(s);
            } else {
                s = codeitemform.getTreeId();
                codeitemform.setSeqId(s);
                //itemSuper 如果是-1 则不显示
                codeitemform.setItemSuper("");
            }
        }

    String setId = request.getParameter("setId");
    CodeSetBO csb = SysCacheTool.findCodeSet(setId);
//        request.setAttribute("setId",setId);
//        request.setAttribute("setName",csb.getSetName());
        codeitemform.setSetId(setId);
        codeitemform.setSetName(csb.getSetName());
        return list("edit");
    }

    public String saveItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
//        CodeItemVO codeitemform = (CodeItemVO) form;
        String setId = codeitemform.getSetId();
        //编辑保存代码项
        //if (!mgr.checkCodeItemName(setId, codeitemform.getItemName())) {
        String itemId = codeitemform.getItemId();
        CodeItemBO ci = new CodeItemBO();
        Tools.copyProperties(ci, codeitemform);
        String itemSuper = Tools.filterNull(codeitemform.getItemSuper());
        String spId = itemSuper;
        //如果没上级节点，则赋-1
        if (spId.equals("")) {
            spId = "-1";
            ci.setItemSuper(spId);
        }
        /*************得到新的treeId*************************/
        String newTreeId = null;
        String pTreeId = "";
        //如果层次码不为空,则判断层次码的长度
        String seqId = Tools.filterNull(codeitemform.getSeqId());
        if (!"-1".equals(spId))
            pTreeId = codeUCC.getSuperTreeId(spId);

        if (!seqId.equals("")) {
            if (seqId.length() != 4) {
                throw new BkmsException("请输入4位层次码！", this.getClass());
            }
            newTreeId = pTreeId + seqId;

        } else {
            String newSeqId = codeUCC.getNewTreeId(codeitemform.getSetId(), spId);
            newTreeId = pTreeId + newSeqId;
        }
        ci.setTreeId(newTreeId);
        //判断组成的TreeId是否重复
        if (codeUCC.isRepeatedCodeTreeID(newTreeId, codeitemform.getSetId(), itemId)) {
            throw new BkmsException("层次码重复,请重新输入！", this.getClass());
        }
//        codeUCC.updateBo(ci, ci.getItemId());
        codeUCC.updateCodeItem(ci);
        //内存同步
        List list = new ArrayList();
        list.add(ci);
        //有可能更改层级关系,需先更新更新修改前的代码项的层级关系
        CodeItemBO oldItem = SysCacheTool.findCodeItem(itemId);
        if (!oldItem.getItemSuper().equals(itemSuper)) {
            SysCache.updateSubStrMap(CacheConstants.OBJ_CODEITEM, setId, oldItem.getItemSuper());
        }
        SysCache.setMap(list, CacheConstants.OPER_ADD, CacheConstants.OBJ_CODEITEM);
        SysCache.updateSubStrMap(CacheConstants.OBJ_CODEITEM, setId, spId);
        //转向列表页面
        this.showMessage("保存成功！");
        return list();
    }

    public String addItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
//        CodeItemVO codeitemform = (CodeItemVO) form;
        String setId = codeitemform.getSetId();
        //编辑保存代码项
        //新建代码项
        if (!codeUCC.isExistCodeItemName(setId, codeitemform.getItemName())) {
            CodeItemBO ci = new CodeItemBO();
            Tools.copyProperties(ci, codeitemform);
            String spId = Tools.filterNull(codeitemform.getItemSuper());
            //如果没上级节点，则付-1
            if (spId.equals("")) {
                spId = "-1";
                ci.setItemSuper(spId);
            }
            /*************得到新的treeId*************************/
            String newTreeId = null;
            String pTreeId = "";
            //如果层次码不为空,则判断层次码的长度
            String seqId = Tools.filterNull(codeitemform.getSeqId());
            if (!seqId.equals("")) {
                if (seqId.length() != 4) {
                    throw new BkmsException("请输入4位层次码！", this.getClass());
                }
                newTreeId = pTreeId + seqId;
            } else {
                if (!"-1".equals(spId)) {
                    pTreeId = codeUCC.getSuperTreeId(spId);
                    newTreeId = codeUCC.getNewTreeId(codeitemform.getSetId(), pTreeId);
                } else {
                    newTreeId = codeUCC.getNewTreeId(codeitemform.getSetId(), "-1");
                }
            }
            ci.setTreeId(newTreeId);
            //判断组成的TreeId是否重复
            if (codeUCC.isRepeatedCodeTreeID(newTreeId, setId, "")) {
                throw new BkmsException("层次码重复,请重新输入！", this.getClass());
            }

            //代码项ID
//            String itemId = setId.concat(codeUCC.getNewTreeId(setId, ""));
            String itemId = setId.concat(codeUCC.getNewItemId());
            //判断ItemId是否重复
            if (codeUCC.isRepeatedCodeItemID(itemId)) {
                throw new BkmsException("代码值重复,请重新输入！", this.getClass());
            }
            ci.setItemId(itemId);
            codeUCC.createBo(ci);
            //内存同步
            List list = new ArrayList();
            list.add(ci);
            SysCache.setMap(list, CacheConstants.OPER_ADD, CacheConstants.OBJ_CODEITEM);
            SysCache.updateSubStrMap(CacheConstants.OBJ_CODEITEM, setId, spId);
            //转向列表页面
            this.showMessage("新建成功！");
        } else {
            throw new BkmsException("同一代码集下的代码项名称重复，请重新输入！", this.getClass());
        }
        return list();
    }


    public String openItem() throws BkmsException {
        return changeItemStatus(SysConstants.INFO_STATUS_OPEN);
    }

    public String banItem() throws BkmsException {
        return changeItemStatus(SysConstants.INFO_STATUS_BAN);
    }

    private String changeItemStatus(String status) throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
//        String setId = request.getParameter("setId");
        //启用指标项
        String[] itemids = request.getParameterValues("chk");
        codeUCC.makeItemStatus(SysConstants.INFO_STATUS_OPEN.equals(status), itemids);
        //内存同步
        List list = new ArrayList();
        for (int i = 0; i < itemids.length; i++) {
            CodeItemBO item = SysCacheTool.findCodeItem(itemids[i]);
            item.setItemStatus(status);
            list.add(item);
        }
        SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_CODEITEM);
        return list();
    }

    public String delItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
        //删除代码项
        String[] itemids = request.getParameterValues("chk");
        codeUCC.deleteCodeItems(itemids);
        //同步内存
//        SysCache.setMap(itemids, CacheConstants.OPER_DELETE, CacheConstants.OBJ_CODEITEM);
        this.showMessage("删除成功！");
        return list();
    }

}
