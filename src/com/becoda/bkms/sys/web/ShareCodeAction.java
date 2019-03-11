package com.becoda.bkms.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.sys.pojo.vo.CodeItemVO;
import com.becoda.bkms.sys.pojo.vo.CodeSetVO;
import com.becoda.bkms.sys.ucc.ICodeUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Endecode;
import com.becoda.bkms.util.Tools;
import com.sun.corba.se.spi.activation.Server;
import javax.xml.ws.Response;
import javax.servlet.http.HttpServletResponse;
import com.becoda.bkms.util.SequenceGenerator;

public class ShareCodeAction extends GenericAction{
	 private CodeSetVO codeSetForm;
	 private CodeItemVO codeitemform;

	
		public CodeItemVO getCodeitemform() {
	        return codeitemform;
	    }

	    public void setCodeitemform(CodeItemVO codeitemform) {
	        this.codeitemform = codeitemform;
	    }
	    public CodeSetVO getCodeSetForm() {
	        return codeSetForm;
	    }

	    public void setCodeSetForm(CodeSetVO codeSetForm) {
	        this.codeSetForm = codeSetForm;
	    }
	    
	    /**
	     * 查询所有代码集(国税大彩)
	     * fxj
	     * @return String
	     * @throws BkmsException
	     */
	    public String list() throws BkmsException {
	    	//String codeSetID=Endecode.base64Decode(request.getParameter("setId"));
	    	//String codeSetID="3001";
	        return list(user, "list");
	    }

	    private String list(User user, String forward) throws BkmsException {
	        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
	        if ("list".equals(forward)) {
	        	List list=iCodeUCC.queryCodeSetsByState();
	        	convertCodeToDescChars(list);
	            request.setAttribute("list", list);
	        }
	        return forward;
	    }
	    /**
	     * 设置列表后续显示项
	     * fxj
	     * @return 
	     * @throws 
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
	    /**
	     * 加载代码集维护页面
	     * fxj
	     * @return 
	     * @throws 
	     */
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
	    /**
	     * 执行修改代码集操作
	     * fxj
	     * @return 
	     * @throws 
	     */
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
	    /**
	     * 执行添加代码集操作
	     * fxj
	     * @return 
	     * @throws 
	     */
	    public String addCodeSet() throws BkmsException {
	        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
	        //增加代码集
//	        if (iCodeUCC.isExistCodeSetName(codeSetForm.getSetName())) {
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
//	        } else {
//	            throw new BkmsException("代码集名称重复，请重新输入代码集名称！", this.getClass());
//	        }

	    }
	    /**
	     * 删除代码集操作
	     * fxj
	     * @return 
	     * @throws 
	     */
	    public String setDel() throws BkmsException {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        ICodeUCC iCodeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
	        String[] ids = request.getParameterValues("chk");
	        boolean bl = iCodeUCC.isAllowDelete(ids);
	        if (bl) {
	            throw new BkmsException("国标码和系统码不允许删除！", this.getClass());
	        }
	        String str = iCodeUCC.checkCodeSetUsing(ids);
	        str = Tools.filterNull(str);
	        if (!"".equals(str)) {
	            throw new BkmsException("正在使用，无法删除！", this.getClass());
	        }
	        iCodeUCC.deleteCodeSets(ids);
	        //同步内存
//	        SysCache.setMap(ids, CacheConstants.OPER_DELETE, CacheConstants.OBJ_CODESET);
	        this.showMessage("删除成功！");
	        return list();
	    }
	    /**
	     * 启用
	     * fxj
	     * @return 
	     * @throws 
	     */
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
	    /**
	     * 禁用
	     * fxj
	     * @return 
	     * @throws 
	     */
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
	    /**
	     * 查询所有代码项根据代码集ID
	     * @return
	     * @throws BkmsException
	     */
	    public String itemList() throws BkmsException {
	        return itemList("itemlist");
	    }

	    private String itemList(String forward) throws BkmsException {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        if ("itemlist".equals(forward)) {
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
	    
	    /**
	     * 加载代码项维护页面
	     * fxj
	     * @return
	     * @throws BkmsException
	     */
	    public String itemEdit() throws BkmsException {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
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
	        codeitemform.setSetId(setId);
	        codeitemform.setSetName(csb.getSetName());
	        return itemList("itemedit");
	    }
	    /**
	     * 执行修改代码项操作
	     * fxj
	     * @return
	     * @throws BkmsException
	     */
	    public String saveItem() throws BkmsException {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
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
	        return itemList();
	    }
	    /**
	     * 执行添加代码项操作
	     * fxj
	     * @return
	     * @throws BkmsException
	     */
	    public String addItem() throws BkmsException {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
	        String setId = codeitemform.getSetId();
	        //编辑保存代码项
	        //新建代码项
//	        if (!codeUCC.isExistCodeItemName(setId, codeitemform.getItemName())) {
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
//	        } else {
//	            throw new BkmsException("同一代码集下的代码项名称重复，请重新输入！", this.getClass());
//	        }
	        return itemList();
	    }
	    /**
	     * 删除代码项
	     * fxj
	     * @return
	     * @throws BkmsException
	     */
	    public String delItem() throws BkmsException {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
	        //删除代码项
	        String[] itemids = request.getParameterValues("chk");
	        codeUCC.deleteCodeItems(itemids);
	        //同步内存
//	        SysCache.setMap(itemids, CacheConstants.OPER_DELETE, CacheConstants.OBJ_CODEITEM);
	        this.showMessage("删除成功！");
	        return itemList();
	    }
	    /**
	     * 启用
	     * @return
	     * @throws BkmsException
	     */
	    public String openItem() throws BkmsException {
	        return changeItemStatus(SysConstants.INFO_STATUS_OPEN);
	    }
	    /**
	     * 禁用
	     * @return
	     * @throws BkmsException
	     */
	    public String banItem() throws BkmsException {
	        return changeItemStatus(SysConstants.INFO_STATUS_BAN);
	    }

	    private String changeItemStatus(String status) throws BkmsException {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        ICodeUCC codeUCC = (ICodeUCC) BkmsContext.getBean("sys_codeUCC");
//	        String setId = request.getParameter("setId");
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
	        return itemList();
	    }
	   
}