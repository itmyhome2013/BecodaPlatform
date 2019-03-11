package com.becoda.bkms.sys.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.InfoItemVO;
import com.becoda.bkms.sys.ucc.IInfoItemUCC;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.struts2.ServletActionContext;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-6
 * Time: 10:59:50
 * To change this template use File | Settings | File Templates.
 */
public class InfoItemListAction extends GenericAction {
    private InfoItemVO form1;

    public InfoItemVO getForm1() {
        return form1;
    }

    public void setForm1(InfoItemVO form1) {
        this.form1 = form1;
    }

    public String openItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            String[] itemId = request.getParameterValues("chk");
//            InfoItemVO form1 = (InfoItemVO) form;
            String setId =request.getParameter("setId");
            if(form1==null){
                form1=new InfoItemVO();
                form1.setSetId(setId);
            }
            IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
            ucc.makeStatus(form1.getSetId(), itemId, SysConstants.INFO_STATUS_OPEN);
            //同步缓存cache
            List list = new ArrayList();
            for (int i = 0; i < itemId.length; i++) {
                InfoItemBO item = SysCacheTool.findInfoItem(form1.getSetId(), itemId[i]);
                item.setItemStatus(SysConstants.INFO_STATUS_OPEN);
                list.add(item);
            }
            SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_INFOITEM);
            getItemList(ucc, form1, hrequest, user);
        } catch (Exception e) {
            e.printStackTrace();
             this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String banItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            //禁用
            String[] itemId = request.getParameterValues("chk");
//            InfoItemVO form1 = (InfoItemVO) form;
            String setId =request.getParameter("setId");
            if(form1==null){
                form1=new InfoItemVO();
                form1.setSetId(setId);
            }
            IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
            ucc.makeStatus(form1.getSetId(), itemId, SysConstants.INFO_STATUS_BAN);
            //同步缓存cache
            List list = new ArrayList();
            for (int i = 0; i < itemId.length; i++) {
                InfoItemBO item = SysCacheTool.findInfoItem(form1.getSetId(), itemId[i]);
                item.setItemStatus(SysConstants.INFO_STATUS_BAN);
                list.add(item);
            }
            SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_INFOITEM);
            getItemList(ucc, form1, hrequest, user);
        } catch (Exception e) {
            e.printStackTrace();
             this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String delItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            String[] itemId = request.getParameterValues("chk");
            IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
//            InfoItemVO form1 = (InfoItemVO) form;
            String setId =request.getParameter("setId");
            if(form1==null){
                form1=new InfoItemVO();
                form1.setSetId(setId);
            }
            ucc.deleteInfoItems(form1.getSetId(), itemId, user.getUserId());

            //内存同步
//            SysCache.setMap(itemId, CacheConstants.OPER_DELETE, CacheConstants.OBJ_INFOITEM);
            //同步内存权限
            Hashtable hash = user.getPmsInfoSet();
            for (int i = 0; i < itemId.length; i++) {
                hash.remove(itemId[i]);
            }
            getItemList(ucc, form1, hrequest, user);
            this.showMessage("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
             this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String listItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
//            InfoItemVO form1 = (InfoItemVO) form;
            getItemList(ucc, form1, hrequest, user);
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public void getItemList(IInfoItemUCC ucc, InfoItemVO form1, BkmsHttpRequest request, User user) throws BkmsException {
        if(form1==null){
                form1=new InfoItemVO();
                form1.setSetId(request.getParameter("setId"));
            }
        if (form1.getSetId() != null) {
            List list = ucc.queryRightItemlist(user, form1.getSetId());
            request.setAttribute("setId",form1.getSetId());
            request.setAttribute("list", list);
        }
    }
}
