package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleDataBO;
import com.becoda.bkms.pms.ucc.IRoleDataUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.util.Tools;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class DataAction extends GenericAction {
    public String pInit() throws BkmsException {
        String sType = Tools.filterNull(hrequest.getParameter("stype"));
        String paramId = Tools.filterNull(hrequest.getParameter("paramId"));
        String pageFlag = Tools.filterNull(hrequest.getParameter("pageFlag"));
        String qryFlag = Tools.filterNull(hrequest.getParameter("qryFlag"));
        try {
            if (!"".equals(sType) && !"".equals(paramId) && !"".equals(pageFlag)) {
                querySysOperInfo(sType, qryFlag);
                Hashtable datalist = null;
                if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                    datalist = queryRoleData(paramId, sType, qryFlag);
                } else {                     //用户授权 得到有权限的权限ID
                    datalist = queryUserData(paramId, sType);
                }
                request.setAttribute("datalist_name", datalist);
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    /**
     * 修改角色信息项权限
     *
     * @return String
     * @roseuid 447AE96900E3
     */
    public String updateRoleData() throws BkmsException {
        String paramId = Tools.filterNull(hrequest.getParameter("paramId"));
        String sType = Tools.filterNull(hrequest.getParameter("stype"));
        String pageFlag = Tools.filterNull(hrequest.getParameter("stype"));
        if ("".equals(pageFlag)) pageFlag = "1";
        String manageFlag = Tools.filterNull(hrequest.getParameter("manageFlag"));
        try {
            Enumeration enum1 = hrequest.getParameterNames();
            Hashtable dataHash = new Hashtable();
            while (enum1.hasMoreElements()) {
                String name = (String) enum1.nextElement();
                boolean tabflag = name.startsWith("tab");
                boolean fldflag = name.startsWith("fld");
                String pmsType = hrequest.getParameter(name);
//                if (!"2".equals(pmsType))  //如果不是只读权限,则存储,否则则不存
                if (tabflag || fldflag) {
                    RoleDataBO data = new RoleDataBO();
                    data.setDataId(name.substring(3));
                    data.setPmsType(pmsType);
                    data.setRoleId(paramId);
                    if (tabflag)
                        data.setDataType(PmsConstants.INFO_SET_TYPE);
                    else
                        data.setDataType(PmsConstants.INFO_ITEM_TYPE);
                    dataHash.put(data.getDataId(), data);
                }
            }

            IRoleDataUCC ucc = (IRoleDataUCC) getBean("pms_roleDataUCC");
            ucc.updateRoleData(dataHash, sType, paramId, manageFlag, user);
            this.showMessage("存储成功！");
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return pInit();
    }

    /**
     * 查询角色信息项权限
     */
    private Hashtable queryRoleData(String paramId, String sType, String flag) throws BkmsException {
        if ("".equals(sType) || "".equals(paramId)) {
            return null;
        }
        IRoleDataUCC ucc = (IRoleDataUCC) getBean("pms_roleDataUCC");
        return ucc.queryRoleData(paramId, sType, flag);
    }

    /**
     * 查询用户信息项权限
     */
    private Hashtable queryUserData(String paramId, String sType) throws BkmsException {
        if ("".equals(sType) || "".equals(paramId)) {
            return null;
        }
        IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
        return ucc.queryUserDataByStype(paramId, sType);

    }

    /**
     * 得到管理员的信息项
     */
    private void querySysOperInfo(String sType, String flag) throws BkmsException {
        if ("".equals(sType)) {
            return;
        }
        IRoleDataUCC ucc = (IRoleDataUCC) getBean("pms_roleDataUCC");
        Hashtable itemhash = ucc.querySysOperInfoItem(user, sType, flag);
        List setList = ucc.querySysOperInfoSet(user, sType, flag);
        request.setAttribute("infoitem_name", itemhash);
        request.setAttribute("infoset_name", setList);
    }
}
