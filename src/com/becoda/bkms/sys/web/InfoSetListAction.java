package com.becoda.bkms.sys.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.InfoSetVO;
import com.becoda.bkms.sys.ucc.IInfoSetUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-5
 * Time: 10:45:11
 * To change this template use File | Settings | File Templates.
 */
public class InfoSetListAction extends GenericAction {
    private InfoSetVO form1;

    public InfoSetVO getForm1() {
        return form1;
    }

    public void setForm1(InfoSetVO form1) {
        this.form1 = form1;
    }

    public String openSet() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            String[] setId = request.getParameterValues("chk");
            IInfoSetUCC ucc = (IInfoSetUCC) BkmsContext.getBean("sys_infoSetUCC");
            ucc.makeStatus(setId, SysConstants.INFO_STATUS_OPEN);

            //同步缓存cache
            List list = new ArrayList();
            for (int i = 0; i < setId.length; i++) {
                InfoSetBO set = SysCacheTool.findInfoSet(setId[i]);
                set.setSetStatus(SysConstants.INFO_STATUS_OPEN);
                list.add(set);
            }
            SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_INFOSET);
            getSetList(ucc, hrequest, user);
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String banSet() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            //禁用
            String[] setId = request.getParameterValues("chk");
            IInfoSetUCC ucc = (IInfoSetUCC) BkmsContext.getBean("sys_infoSetUCC");
            ucc.makeStatus(setId, SysConstants.INFO_STATUS_BAN);
            //同步缓存cache
            List list = new ArrayList();
            for (int i = 0; i < setId.length; i++) {
                InfoSetBO set = SysCacheTool.findInfoSet(setId[i]);
                set.setSetStatus(SysConstants.INFO_STATUS_BAN);
                list.add(set);
            }
            SysCache.setMap(list, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_INFOSET);
            getSetList(ucc, hrequest, user);
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String delSet() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            String[] setId = request.getParameterValues("chk");
            IInfoSetUCC ucc = (IInfoSetUCC) BkmsContext.getBean("sys_infoSetUCC");
            if (ucc.checkAllowDelete(setId).equals("")) {
                ucc.deleteInfoSets(setId, user.getUserId());
            } else {
                throw new BkmsException("此表为系统表或者国标表，不能删除！", this.getClass());
            }
            //内存同步  
            SysCache.setMap(setId, CacheConstants.OPER_DELETE, CacheConstants.OBJ_INFOSET);
            //同步内存权限
            Hashtable hash = user.getPmsInfoSet();
            for (int i = 0; i < setId.length; i++) {
                hash.remove(setId[i]);
            }
            getSetList(ucc, hrequest, user);
            this.showMessage("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String listSet() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        BkmsHttpRequest hrequest = new BkmsHttpRequest(request);
        try {
            IInfoSetUCC ucc = (IInfoSetUCC) BkmsContext.getBean("sys_infoSetUCC");
            getSetList(ucc,  hrequest, user);
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public void getSetList(IInfoSetUCC ucc, BkmsHttpRequest request, User user) throws BkmsException {
        String stype = request.getParameter("set_sType");
        if(Tools.filterNull(stype).equals("")&&form1!=null){
            stype=form1.getSet_sType();
        }
        if (stype != null) {
            List list = ucc.queryRightSetlist(user,stype);
            request.setAttribute("list", list);
            request.setAttribute("set_sType", stype);
        }
    }
}
