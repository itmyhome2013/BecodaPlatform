package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.service.OrgService;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;
import com.becoda.bkms.pms.service.RoleManageService;
import com.becoda.bkms.pms.service.RoleOrgScaleService;
import com.becoda.bkms.pms.ucc.IRoleOrgScaleUCC;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.Tools;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class RoleOrgScaleUCCImpl implements IRoleOrgScaleUCC {
    private RoleOrgScaleService roleOrgScaleService;
    private RoleManageService roleManageService;
    private OrgService orgService;

    public RoleOrgScaleService getRoleOrgScaleService() {
        return roleOrgScaleService;
    }

    public void setRoleOrgScaleService(RoleOrgScaleService roleOrgScaleService) {
        this.roleOrgScaleService = roleOrgScaleService;
    }

    public RoleManageService getRoleManageService() {
        return roleManageService;
    }

    public void setRoleManageService(RoleManageService roleManageService) {
        this.roleManageService = roleManageService;
    }

    public OrgService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }

    /**
     */
    public void createRoleOrgScale(RoleOrgScaleBO roleOrgScale, User user, String managerFlag) throws RollbackableException {
        //在存储维护权限时,需要检测当前管理员是否有排除权限,
        // 如果有,再检测所添加的机构是否包含这个排除机构,
        // 如果包含则须将排除权限插入到角色当中
        if (PmsConstants.SCALE_USE.equals(roleOrgScale.getPmsType())) {  //如果是插入有权机构
            List noOrgList = null;
            if (PmsConstants.QUERY_SCALE.equals(roleOrgScale.getScaleFlag())) {  //如果存储的是查询范围，则取用户的查询排出机构
                noOrgList = user.getHaveNoQueryOrgScale();
            } else {
                noOrgList = user.getHaveNoOperateOrgScale();
            }
            Org newOrg = SysCacheTool.findOrgById(roleOrgScale.getCondId());
            if (newOrg != null && noOrgList != null) {
                for (int i = 0; i < noOrgList.size(); i++) {
                    if (((Org) noOrgList.get(i)).getTreeId().startsWith(newOrg.getTreeId())) {
                        RoleOrgScaleBO orgScaleBO = new RoleOrgScaleBO();
                        Tools.copyProperties(orgScaleBO, roleOrgScale);
                        orgScaleBO.setCondId(((Org) noOrgList.get(i)).getOrgId());
                        orgScaleBO.setPmsType(PmsConstants.SCALE_REFUSE);
                        roleOrgScaleService.createRoleOrgScale(orgScaleBO);
                    }
                }
            }
        } else {
            //如果插入的是排除权限，则需要检测被插入的角色是否是管理员
            //如果是管理员，则需要给他的子用户以及子子用户加入相应的排除权限，
            // 前提是，下级用户的有权机构必须包含此排除机构，
            // 如果排除机构包含了下级用户的有权机构，则需要将下级用户的有权机构，以及所包含的相关排除机构删除
            if (PmsConstants.IS_SYS_MANAGER.equals(managerFlag)) {

                //得到下级用户的机构范围列表和排除列表
                String roleId = roleOrgScale.getRoleId();
                Org newOrg = SysCacheTool.findOrgById(roleOrgScale.getCondId());
                Hashtable haveOrgScaleList = roleOrgScaleService.queryRoleOrgScaleByAdminRoleId(roleId, roleOrgScale.getScaleFlag(), PmsConstants.SCALE_USE);
                Hashtable haveNoOrgScaleList = roleOrgScaleService.queryRoleOrgScaleByAdminRoleId(roleId, roleOrgScale.getScaleFlag(), PmsConstants.SCALE_REFUSE);
                if (haveOrgScaleList != null) {
                    Iterator it = haveOrgScaleList.values().iterator();
                    while (it.hasNext()) {
                        List list = (List) it.next();
                        for (int i = 0; i < list.size(); i++) {
                            RoleOrgScaleBO mOrgScale = (RoleOrgScaleBO) list.get(i);
                            Org mOrg = SysCacheTool.findOrgById(mOrgScale.getCondId());
                            if (newOrg.getTreeId().startsWith(mOrg.getTreeId()))  //如果有权机构包含此排除机构，则给相应的角色添加排除机构
                            {
                                //首先查询排除机构中是否已经包含了此机构，如果没包含则添加
                                boolean flag = false;  //排除机构是否包含标志
                                if (haveNoOrgScaleList != null && haveNoOrgScaleList.size() > 0) {
                                    List noList = (List) haveNoOrgScaleList.get(mOrgScale.getRoleId());
                                    if (noList != null) {
                                        for (int j = 0; j < noList.size(); j++) {
                                            if (((RoleOrgScaleBO) noList.get(j)).getCondId().equals(roleOrgScale.getCondId())) {
                                                flag = true;
                                                continue;
                                            }
                                        }
                                    }
                                }
                                if (!flag) {
                                    RoleOrgScaleBO newOrgScaleBO = new RoleOrgScaleBO();
                                    Tools.copyProperties(newOrgScaleBO, roleOrgScale);
                                    newOrgScaleBO.setRoleId(mOrgScale.getRoleId());
                                    roleOrgScaleService.createRoleOrgScale(newOrgScaleBO);
                                    continue;
                                }
                            } else if (mOrg.getTreeId().startsWith(newOrg.getTreeId())) {
                                //如果排除机构的范围比有权机构范围大，则需要将相应的有权机构和排除机构删除
                                roleOrgScaleService.deleteRoleOrgScale(mOrgScale.getRoleId(), mOrgScale.getScaleFlag(), mOrgScale.getPmsType(), mOrgScale.getCondId());
                                //检测是否包含下级的排除机构，如果有，则删除掉
                                if (haveNoOrgScaleList != null && haveNoOrgScaleList.size() > 0) {
                                    List noList = (List) haveNoOrgScaleList.get(mOrgScale.getRoleId());
                                    if (noList != null) {
                                        for (int j = 0; j < noList.size(); j++) {
                                            RoleOrgScaleBO mNoOrgScale = (RoleOrgScaleBO) noList.get(j);
                                            Org mNoOrg = SysCacheTool.findOrgById(mNoOrgScale.getCondId());
                                            if (mNoOrg.getTreeId().startsWith(mOrg.getTreeId())) {
                                                roleOrgScaleService.deleteRoleOrgScale(mNoOrgScale.getRoleId(), mNoOrgScale.getScaleFlag(), mNoOrgScale.getPmsType(), mNoOrgScale.getCondId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        roleOrgScaleService.createRoleOrgScale(roleOrgScale);
        if (PmsConstants.SCALE_USE.equals(roleOrgScale.getPmsType())) {
            if (PmsConstants.QUERY_SCALE.equals(roleOrgScale.getScaleFlag())) {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleOrgScale.getRoleId() + "）的机构查询范围权限。");
            } else {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleOrgScale.getRoleId() + "）的机构维护范围权限。");
            }
        } else {
            if (PmsConstants.QUERY_SCALE.equals(roleOrgScale.getScaleFlag())) {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleOrgScale.getRoleId() + "）的机构查询排除范围权限。");
            } else {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleOrgScale.getRoleId() + "）的机构维护排除范围权限。");
            }
        }
    }

    /**
     * 根据标示符查询维护范围或者查询范围,按照CodeID排序
     *
     * @param roleID    角色ID
     * @param scaleFlag 范围权限类别 1 维护范围 0 查询范围
     * @param pmsType   1 有权机构  0 无权机构
     * @roseuid 4474713A010E
     */
    public List queryRoleOrgScale(String roleID, String scaleFlag, String pmsType) throws RollbackableException {
        List list = roleOrgScaleService.queryRoleOrgScale(roleID, scaleFlag, pmsType);
        //将机构名称写入
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RoleOrgScaleBO orgScalebo = (RoleOrgScaleBO) list.get(i);
                Org org = SysCacheTool.findOrgById(orgScalebo.getCondId());
                if (org != null) {
                    OrgBO tmpOrg = orgService.findOrgByDept(org.getOrgId());
                    //如果当前机构=返回机构，则代表是外设机构，不等，则代表当前机构是部门
                    if (tmpOrg != null) {
                        if (tmpOrg.getOrgId().equals(org.getOrgId()))
                            orgScalebo.setOrgName(org.getName());
                        else
                            orgScalebo.setOrgName(tmpOrg.getName() + "—" + org.getName());
                    } else {
                        orgScalebo.setOrgName(org.getName());
                    }

                }
            }
        }
        return list;
    }

    /**
     * 删除机构范围权限
     * 权限收回：如果删除有权机构时，考虑下级用户是否包含此机构构或者下级机构，
     * 如果有，需要将其连同相关的排除机构删除
     *
     * @param roleID
     * @param scaleFlag
     * @param pmsType
     * @param orgIds
     * @throws RollbackableException
     */
    public void deleteRoleOrgScale(String roleID, String scaleFlag, String pmsType, String[] orgIds, String managerFlag, User user) throws RollbackableException {
        //删除机构列表 ,用户权限同步,内部存储的是RoleOrgScaleBO
        for (int i = 0; i < orgIds.length; i++) {
            if (PmsConstants.SCALE_USE.equals(pmsType) && PmsConstants.IS_SYS_MANAGER.equals(managerFlag)) {
                Org newOrg = SysCacheTool.findOrgById(orgIds[i]);
                Hashtable haveOrgScale = roleOrgScaleService.queryRoleOrgScaleByAdminRoleId(roleID, scaleFlag, PmsConstants.SCALE_USE);
                Hashtable haveNoOrgScale = roleOrgScaleService.queryRoleOrgScaleByAdminRoleId(roleID, scaleFlag, PmsConstants.SCALE_REFUSE);
                if (haveOrgScale != null) {
                    Iterator it = haveOrgScale.values().iterator();
                    while (it.hasNext()) {
                        List list = (List) it.next();
                        for (int j = 0; j < list.size(); j++) {
                            RoleOrgScaleBO mOrgScale = (RoleOrgScaleBO) list.get(j);
                            Org mOrg = SysCacheTool.findOrgById(mOrgScale.getCondId());
                            if (mOrg.getTreeId().startsWith(newOrg.getTreeId())) {
                                roleOrgScaleService.deleteRoleOrgScale(mOrgScale.getRoleId(), mOrgScale.getScaleFlag(), mOrgScale.getPmsType(), mOrgScale.getCondId());
                                if (haveNoOrgScale != null && haveNoOrgScale.size() > 0) {
                                    List nolist = (List) haveNoOrgScale.get(mOrgScale.getRoleId());
                                    if (nolist != null) {
                                        for (int k = 0; k < nolist.size(); k++) {
                                            RoleOrgScaleBO mNoOrgScale = (RoleOrgScaleBO) nolist.get(k);
                                            Org mNoOrg = SysCacheTool.findOrgById(mNoOrgScale.getCondId());
                                            if (mNoOrg.getTreeId().startsWith(mOrg.getTreeId())) {
                                                roleOrgScaleService.deleteRoleOrgScale(mNoOrgScale.getRoleId(), mNoOrgScale.getScaleFlag(), mNoOrgScale.getPmsType(), mNoOrgScale.getCondId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            roleOrgScaleService.deleteRoleOrgScale(roleID, scaleFlag, pmsType, orgIds[i]);
        }
        if (PmsConstants.SCALE_USE.equals(pmsType)) {
            if (PmsConstants.QUERY_SCALE.equals(scaleFlag)) {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleID + "）的机构查询范围权限。");
            } else {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleID + "）的机构维护范围权限。");
            }
        } else {
            if (PmsConstants.QUERY_SCALE.equals(scaleFlag)) {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleID + "）的机构查询排除范围权限。");
            } else {
                HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleID + "）的机构维护排除范围权限。");
            }
        }
    }

    /**
     * 检测是否某个机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @param pmsType   1有权机构 0 排除机构
     * @return true 已选，false 未选
     */
    public boolean checkOrgSelected(String roleId, String scaleFlag, String pmsType, String orgId) throws RollbackableException {
        return roleOrgScaleService.checkOrgSelected(roleId, scaleFlag, pmsType, orgId);
    }

    /**
     * 检测排除机构是否在有权机构范围
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return true 在，false 不在
     */
    public boolean checkInHaveOrgScale(String roleId, String scaleFlag, String orgId) throws RollbackableException {
        return roleOrgScaleService.checkInHaveOrgScale(roleId, scaleFlag, orgId);
    }

    /**
     * * 查询角色的代码权限
     *
     * @param roleId
     * @param scaleFlag
     * @return
     * @throws RollbackableException
     */
    public Hashtable queryRolePmsCode(String roleId, String scaleFlag, List pmsCode) throws RollbackableException {
        return roleOrgScaleService.queryRolePmsCode(roleId, scaleFlag, pmsCode);
    }

    public void updateRoleCodeScale(String roleId, String scaleFlag, List codeList, String managerFlag, List pmsCode, User user) throws RollbackableException {
        // 权限收回
        // 先获得获得减少的权限
        //将子角色的权限删除
        //List reducedOperateList = new ArrayList();
        String reducedCodeIds = "";
        if (PmsConstants.IS_SYS_MANAGER.equals(managerFlag)) {
            String newCodeIds = "";
            if (codeList != null) {
                for (int i = 0; i < codeList.size(); i++) {
                    RoleOrgScaleBO code = (RoleOrgScaleBO) codeList.get(i);
                    newCodeIds += code.getCondId();
                }
            }
            Hashtable hash = roleOrgScaleService.queryRolePmsCode(roleId, scaleFlag, pmsCode);
            if (hash != null) {
                Iterator it = hash.values().iterator();
                while (it.hasNext()) {
                    List list = (List) it.next();
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            String codeItemId = (String) list.get(i);
                            if (newCodeIds.indexOf(codeItemId) == -1) {
                                if (reducedCodeIds.equals(""))
                                    reducedCodeIds += "'" + codeItemId + "'";
                                else
                                    reducedCodeIds += ",'" + codeItemId + "'";
                            }
                        }
                    }

                }
            }
            //取得
            if (!"".equals(reducedCodeIds)) {
                String treeId = "";
                RoleInfoBO bo = roleManageService.findRoleInfo(roleId);
                if (bo != null) treeId = bo.getTreeId();
                StringBuffer sb = new StringBuffer();
                sb.append("from RoleOrgScaleBO r where r.roleId in(")
                        .append("select r2.roleId from RoleInfoBO r2 where r2.treeId like '")
                        .append(treeId).append("%') and r.codeId <> '" + PmsConstants.CODE_TYPE_ORG + "' and r.condId in (")
                        .append(reducedCodeIds).append(")");
                roleOrgScaleService.deleteDataBySql(sb.toString());
            }
        }
        roleOrgScaleService.updateRoleCodeScale(roleId, scaleFlag, codeList, pmsCode);
        if (PmsConstants.QUERY_SCALE.equals(scaleFlag)) {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleId + "）的特殊代码查询范围权限。");
        } else {
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改角色编号为（" + roleId + "）的特殊代码维护范围权限。");
        }
    }

    /**
     * 检测维护机构是否在查询机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryOrgScale(String roleId, String orgId) throws RollbackableException {
        return roleOrgScaleService.checkInQueryOrgScale(roleId, orgId);
    }

    /**
     * 检测维护机构是否在查询排除机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryNoOrgScale(String roleId, String orgId) throws RollbackableException {
        return roleOrgScaleService.checkInQueryNoOrgScale(roleId, orgId);
    }
}
