package com.becoda.bkms.east.jhzb.web;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.common.exception.BkmsException;
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
 * Time: 15:05:22
 * To change this template use File | Settings | File Templates.
 */
public class InfoSetEditAction extends GenericAction {
    private InfoSetVO form1;

    public InfoSetVO getForm1() {
        return form1;
    }

    public void setForm1(InfoSetVO form1) {
        this.form1 = form1;
    }

    public String editSet() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            IInfoSetUCC ucc = (IInfoSetUCC) BkmsContext.getBean("sys_infoSetUCC");
            if(form1==null){
                form1=new InfoSetVO();
                form1.setSetId(request.getParameter("setId"));
                form1.setSet_sType(request.getParameter("set_sType"));
                //form1.setSetName(request.getParameter("setName"));
            }
            String setId = form1.getSetId();
            if (setId != null && !"".equals(setId)) {
                InfoSetBO set = ucc.findInfoSet(setId);
                Tools.copyProperties(form1, set);
            }
            request.setAttribute("setId",setId);
            request.setAttribute("set_sType",request.getParameter("set_sType"));
            request.setAttribute("setName",request.getParameter("setName"));
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "success";
    }

    public String saveSet() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            //编辑保存指标集
            IInfoSetUCC ucc = (IInfoSetUCC) BkmsContext.getBean("sys_infoSetUCC");
            InfoSetBO infoSet = new InfoSetBO();
            Tools.copyProperties(infoSet, form1);
            ucc.updateInfoSet(infoSet);
//            内存cache同步
            List list1 = new ArrayList();
            list1.add(infoSet);
            SysCache.setMap(list1, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_INFOSET);
            String stype = form1.getSet_sType();
            if (stype != null) {
                List list = ucc.queryRightSetlist(user, stype);
                request.setAttribute("list", list);
            }
            request.setAttribute("set_bType",form1.getSet_sType());
            this.showMessage("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "listSet";
    }

    public String addSet() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String set_bType = getBtype(form1.getSet_sType());
            form1.setSet_bType(set_bType);
            IInfoSetUCC ucc = (IInfoSetUCC) BkmsContext.getBean("sys_infoSetUCC");
            String property = form1.getSetProperty();
            if (property == null) {
                property = SysConstants.INFO_SET_PROPERTY_USER;//用户指标集
                form1.setSetProperty(property);
            }

            String setId = ucc.getNewSetId(form1.getSet_bType(), form1.getSetProperty());
            if (setId.equals("")) {
                this.showMessage("已经到达了所允许建立指标集的最大数，详细请与系统管理员联系！");
            } else {
                form1.setSetId(setId);
            }
            form1.setSetFk("ID");
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(form1.getSet_rsType()))
                form1.setSetPk("SUBID");
            else
                form1.setSetPk("ID");
            if (form1.getSetSequence() == null || "".equals(form1.getSetSequence()))
                form1.setSetSequence("99");
            InfoSetBO infoSet = new InfoSetBO();
            Tools.copyProperties(infoSet, form1);
            ucc.createInfoSet(infoSet, user.getUserId());
            //内存cache同步
            List list1 = new ArrayList();
            list1.add(infoSet);
            SysCache.setMap(list1, CacheConstants.OPER_ADD, CacheConstants.OBJ_INFOSET);
            //同步内存权限
            Hashtable hash = user.getPmsInfoSet();
            hash.put(infoSet.getSetId(), "3");

            String stype = form1.getSet_sType();
            if (stype != null) {
                List list = ucc.queryRightSetlist(user, stype);
                request.setAttribute("list", list);
            }
            request.setAttribute("set_sType", stype);
            this.showMessage("新建成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
        }
        return "listSet";
    }

    private String getBtype(String sType) {

        String set_bType = "";
        if (!sType.equals("")) {
            if (sType.startsWith("200101"))
                set_bType = "A";
            else if (sType.startsWith("200102"))
                set_bType = "B";
            else if (sType.startsWith("200103"))
                set_bType = "C";
            else if (sType.startsWith("200104"))
                set_bType = "D";
            else if (sType.startsWith("200105"))
                set_bType = "E";
            else if (sType.startsWith("200106"))
                set_bType = "F";
            else if (sType.startsWith("200107"))
                set_bType = "G";
            else if (sType.startsWith("20014010"))
                set_bType = "J";
            else if (sType.startsWith("20014011"))
                set_bType = "K";
            else if (sType.startsWith("200140"))
                set_bType = "T";
        }
        return set_bType;
    }
}
