package com.becoda.bkms.east.jhzb.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.InfoItemVO;
import com.becoda.bkms.sys.ucc.IInfoItemUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.struts2.ServletActionContext;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-6
 * Time: 11:00:09
 * To change this template use File | Settings | File Templates.
 */
public class InfoItemEditAction extends GenericAction {
    private InfoItemVO form1;

    public InfoItemVO getForm1() {
        return form1;
    }

    public void setForm1(InfoItemVO form1) {
        this.form1 = form1;
    }

    public String editItem() throws BkmsException {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
            String itemId =request.getParameter("itemId");
            String setId =request.getParameter("setId");
            if(form1==null){
                form1=new InfoItemVO();
                form1.setItemId(itemId);
                form1.setSetId(setId);
            }
            if (itemId != null && !"".equals(itemId)) {
                InfoItemBO set = ucc.findInfoItem(form1.getSetId(), form1.getItemId());
                Tools.copyProperties(form1, set);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String saveItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            //编辑保存指标集
            IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
//            InfoItemVO form1 = (InfoItemVO) form;
            InfoItemBO item = new InfoItemBO();
            Tools.copyProperties(item, form1);
            ucc.updateInfoItem(item);
//            内存cache同步
            List list1 = new ArrayList();
            list1.add(item);
            SysCache.setMap(list1, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_INFOITEM);
            SysCache.updateInfoItemBySetId(item.getSetId());

            List list = ucc.queryRightItemlist(user, form1.getSetId());
            request.setAttribute("list", list);
            request.setAttribute("setId", form1.getSetId());
            this.showMessage("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "listItem";
    }

    public String addItem() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
//            InfoItemVO form1 = (InfoItemVO) form;
            IInfoItemUCC ucc = (IInfoItemUCC) BkmsContext.getBean("sys_infoItemUCC");
            String itemId = ucc.getNewItemId(form1.getSetId(), form1.getItemProperty());
            if (itemId.equals("")) {
                this.showMessage("已经到达了所允许建立指标集的最大数，详细请与系统管理员联系！");
            }
            form1.setItemId(itemId);
            InfoItemBO item = new InfoItemBO();
            Tools.copyProperties(item, form1);
            ucc.createInfoItem(item, user.getUserId());

            //内存cache同步
            List list1 = new ArrayList();
            list1.add(item);
            SysCache.setMap(list1, CacheConstants.OPER_ADD, CacheConstants.OBJ_INFOITEM);
            SysCache.updateInfoItemBySetId(form1.getSetId());
            //同步内存权限
            Hashtable hash = user.getPmsInfoItem();
            hash.put(form1.getItemId(), "3");

            List list = ucc.queryRightItemlist(user, form1.getSetId());
            request.setAttribute("list", list);
            request.setAttribute("setId", form1.getSetId());

            this.showMessage("新建成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "listItem";
    }
}
