package com.becoda.bkms.pms.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;
import com.becoda.bkms.pms.ucc.IRoleOrgScaleUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.util.Tools;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-2
 * Time: 11:44:17
 * To change this template use File | Settings | File Templates.
 */
public class CodeScaleAction extends GenericAction {
    public String pageInit() throws BkmsException {
        String paramId = Tools.filterNull(request.getParameter("paramId"));
        String selectedQueryNodes = Tools.filterNull(request.getParameter("selectedQueryNodes"));
        String selectedOperateNodes = Tools.filterNull(request.getParameter("selectedOperateNodes"));
        try {
            String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
            String qryFlag = Tools.filterNull(request.getParameter("qryFlag"));
            DrawOperateTree(qryFlag);
            if ("1".equals(pageFlag)) {  //角色授权 得到有权限的权限ID
                selectedQueryNodes = queryRoleCode(PmsConstants.QUERY_SCALE, selectedQueryNodes, "", paramId);
                selectedOperateNodes = queryRoleCode(PmsConstants.OPERATE_SCALE, "", selectedOperateNodes, paramId);

            } else {                     //用户授权 得到有权限的权限ID
                selectedQueryNodes = queryUserCode(PmsConstants.QUERY_SCALE, selectedQueryNodes, "", paramId, qryFlag);
                selectedOperateNodes = queryUserCode(PmsConstants.OPERATE_SCALE, "", selectedOperateNodes, paramId, qryFlag);
            }
            request.setAttribute("selectedQueryNodes", selectedQueryNodes);
            request.setAttribute("selectedOperateNodes", selectedOperateNodes);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "view";
    }

    /**
     * 生成权限特殊代码树数据
     *
     * @param flag    过滤标记 null||'' 查询全部,否则只查询启用的代码集或代码项
     * @throws BkmsException
     */
    private void DrawOperateTree(String flag) throws BkmsException {
        IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
        Hashtable hash = ucc.queryPmsCode(user.getUserId(), PmsConstants.QUERY_SCALE, flag);
        List querylist = new ArrayList();
        if (hash != null) {
            Object obj = hash.get(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID));//员工类别
            if (obj != null) {
                //将代码集添加到list中
                String codeSetId = "";
                boolean disabledFlag = false;
                List list1 = (List) obj;
                for (int i = 0; i < list1.size(); i++) {
                    CodeItemBO cbo = (CodeItemBO) list1.get(i);
                    if (!codeSetId.equals(cbo.getSetId()) && (cbo.getSetId().equals(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID)))) {
                        codeSetId = cbo.getSetId();
                        CodeSetBO set = SysCacheTool.findCodeSet(cbo.getSetId()); //得到代码集
                        if (flag != null && !"".equals(flag)) {
                            if (!SysConstants.INFO_STATUS_OPEN.equals(set.getSetStatus())) {
                                disabledFlag = true;
                                continue;
                            }
                        }
                        //使用CodeItemBO 来表示CodeSetBO
                        CodeItemBO newSet = new CodeItemBO();
                        newSet.setSetId(cbo.getSetId());
                        newSet.setItemName(set.getSetName());
                        newSet.setItemId(cbo.getSetId());
                        newSet.setItemSuper("0000");
                        newSet.setTreeId(cbo.getSetId());
                        querylist.add(newSet);
                    }
                    if (!disabledFlag) {
                        // 更改item的TreeId == setId+treeId;
                        cbo.setTreeId(cbo.getSetId() + cbo.getTreeId());
                        // 如果superId=="-1" 更改 superId=setId
                        if (cbo.getItemSuper().equals("-1")) {
                            cbo.setItemSuper(cbo.getSetId());
                        }
                        querylist.add(list1.get(i));
                    }
                }

            }
            
           

        }
        request.setAttribute("pms_querycode", querylist);

        hash = ucc.queryPmsCode(user.getUserId(), PmsConstants.OPERATE_SCALE, flag);
        List operateList = new ArrayList();
        if (hash != null) {
            Object obj = hash.get(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID));//员工类别
            if (obj != null) {
                //将代码集添加到list中
                String codeSetId = "";
                boolean disabledFlag = false;
                List list1 = (List) obj;
                for (int i = 0; i < list1.size(); i++) {
                    CodeItemBO cbo = (CodeItemBO) list1.get(i);
                    if (!codeSetId.equals(cbo.getSetId()) && (cbo.getSetId().equals(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID)))) {
                        codeSetId = cbo.getSetId();
                        CodeSetBO set = SysCacheTool.findCodeSet(cbo.getSetId()); //得到代码集
                        if (flag != null && !"".equals(flag)) {
                            if (!SysConstants.INFO_STATUS_OPEN.equals(set.getSetStatus())) {
                                disabledFlag = true;
                                continue;
                            }
                        }
                        //使用CodeItemBO 来表示CodeSetBO
                        CodeItemBO newSet = new CodeItemBO();
                        newSet.setSetId(cbo.getSetId());
                        newSet.setItemName(set.getSetName());
                        newSet.setItemId(cbo.getSetId());
                        newSet.setItemSuper("0000");
                        newSet.setTreeId(cbo.getSetId());
                        operateList.add(newSet);
                    }
                    if (!disabledFlag) {
                        // 更改item的TreeId == setId+treeId;
                        cbo.setTreeId(cbo.getSetId() + cbo.getTreeId());
                        // 如果superId=="-1" 更改 superId=setId
                        if (cbo.getItemSuper().equals("-1")) {
                            cbo.setItemSuper(cbo.getSetId());
                        }
                        operateList.add(list1.get(i));
                    }
                }
            }
           
           
        }
        request.setAttribute("pms_operatecode", operateList);
    }

    /**
     * 修改操作权限
     * 说明：先删除、在添加。
     * 考虑：权限收回的实现,可以在删除之前，先比较与原来的权限的减少情况，如果有减少，?
     * 根据此管理员所创建的角色，按递归的方法检测。
     *
     * @return String
     */
    public String updateRoleCode() throws BkmsException {
        String paramId = Tools.filterNull(request.getParameter("paramId"));
//        String pageFlag = Tools.filterNull(request.getParameter("pageFlag"));
        String manageFlag = Tools.filterNull(request.getParameter("manageFlag"));
        String selectedQueryNodes = Tools.filterNull(request.getParameter("selectedQueryNodes"));
        String selectedOperateNodes = Tools.filterNull(request.getParameter("selectedOperateNodes"));
        if ("".equals(paramId)) {
            this.showMessage("参数错误,从重新登录!");
            return "";
        }
        try {
            //存储查询范围代码
            saveRoleCodeScale(selectedQueryNodes, PmsConstants.QUERY_SCALE, paramId, manageFlag, user);

            //存储维护范围代码
            saveRoleCodeScale(selectedOperateNodes, PmsConstants.OPERATE_SCALE, paramId, manageFlag, user);

            this.showMessage("存储成功！");
        }
        catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return pageInit();
    }

    /**
     * 存储代码范围权限
     *
     * @param codes     代码字符串   用逗号分割
     * @param scaleFlag "1" 维护  "0" 查询
     */
    private void saveRoleCodeScale(String codes, String scaleFlag, String paramId, String manageFlag, User user) throws BkmsException {
        List list = new ArrayList();
        if (!codes.equals("")) {
            String[] itemIds = codes.split(",");
            for (int i = 0; i < itemIds.length; i++) {
                String itemId = itemIds[i];
                itemId = itemId.substring(1, itemId.length() - 1);
                if (itemId.length() > 4) {
                    RoleOrgScaleBO roleCodeBO = new RoleOrgScaleBO();
                    roleCodeBO.setCodeId(itemId.substring(0, 4));
                    roleCodeBO.setRoleId(paramId);
                    roleCodeBO.setCondId(itemId);
                    roleCodeBO.setScaleFlag(scaleFlag);
                    roleCodeBO.setPmsType(PmsConstants.SCALE_USE);
                    list.add(roleCodeBO);
                }
            }
        }
        List codeSetIdList = new ArrayList();
        codeSetIdList.add(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID));
        IRoleOrgScaleUCC ucc = (IRoleOrgScaleUCC) getBean("pms_roleOrgScaleUCC");
        ucc.updateRoleCodeScale(paramId, scaleFlag, list, manageFlag, codeSetIdList, user);
    }

    /**
     * 查询操作权限
     */
    public String queryRoleCode(String scaleFlag, String selectedQueryNodes, String selectedOperateNodes, String paramId) throws BkmsException {
        if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
            selectedQueryNodes = "";
        else
            selectedOperateNodes = "";
        if (paramId == null || paramId.equals("")) {
            return "";
        }
        List codeSetIdList = new ArrayList();
        codeSetIdList.add(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID));
        IRoleOrgScaleUCC ucc = (IRoleOrgScaleUCC) getBean("pms_roleOrgScaleUCC");
        Hashtable hash = ucc.queryRolePmsCode(paramId, scaleFlag, codeSetIdList);
        if (hash != null) {
            Object obj = hash.get(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID));//员工类别
            if (obj != null) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
                        selectedQueryNodes += "~" + list.get(i).toString() + "~";
                    else
                        selectedOperateNodes += "~" + list.get(i).toString() + "~";
                }
            }
        }
        if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
            return selectedQueryNodes;
        else
            return selectedOperateNodes;
    }

    private String queryUserCode(String scaleFlag, String selectedQueryNodes, String selectedOperateNodes, String paramId, String flag) throws BkmsException {
        if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
            selectedQueryNodes = "";
        else
            selectedOperateNodes = "";
        if (paramId == null || paramId.equals("")) {
            return "";
        }
        IUserManageUCC ucc = (IUserManageUCC) getBean("pms_userManageUCC");
        Hashtable hash = ucc.queryPmsCode(paramId, scaleFlag, flag);
        if (hash != null) {
            Object obj = hash.get(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID));//员工类别
            if (obj != null) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
                        selectedQueryNodes += "~" + ((CodeItemBO) list.get(i)).getItemId() + "~";
                    else
                        selectedOperateNodes += "~" + ((CodeItemBO) list.get(i)).getItemId() + "~";
                }
            }
        }
        if (PmsConstants.QUERY_SCALE.equals(scaleFlag))
            return selectedQueryNodes;
        else
            return selectedOperateNodes;
    }
}
