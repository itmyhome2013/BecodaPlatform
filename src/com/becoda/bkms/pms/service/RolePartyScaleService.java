package com.becoda.bkms.pms.service;

//import com.becoda.bkms.ccp.pojo.bo.PartyBO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.dao.RolePartyScaleDAO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.bo.RolePartyScaleBO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色党务权限数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RolePartyScaleService {
    private RolePartyScaleDAO rolePartyScaleDAO;

    public RolePartyScaleDAO getRolePartyScaleDAO() {
        return rolePartyScaleDAO;
    }

    public void setRolePartyScaleDAO(RolePartyScaleDAO rolePartyScaleDAO) {
        this.rolePartyScaleDAO = rolePartyScaleDAO;
    }

    /**
     * 修改党务范围权限
     *
     * @param rolePartyScale 角色党务权限数组
     * @roseuid 4479E24E0307
     */
    public void createRolePartyScale(RolePartyScaleBO rolePartyScale) throws RollbackableException {
        rolePartyScaleDAO.createBo(rolePartyScale);
    }

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return com.becoda.bkms.pms.pojo.bo.RolePartyScaleBO[]
     * @roseuid 4479E25E03AA
     */
    public List queryRolePartyScale(String roleID, String scaleFlag) throws RollbackableException {
        return rolePartyScaleDAO.queryRolePartyScale(roleID, scaleFlag);
    }

    public void deleteRolePartyScale(String roleID, String scaleFlag, String partyId) throws RollbackableException {
        rolePartyScaleDAO.deleteRolePartyScale(roleID, scaleFlag, partyId);
    }

    /**
     * 检测是否某个党组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkPartySelected(String roleId, String scaleFlag, String partyId) throws RollbackableException {
//        String ret = rolePartyScaleDAO.checkPartySelected(roleId, scaleFlag);
//        if (partyId == null || "".equals(partyId)) return true;
//        List list = new ArrayList();
//        try {
//            this.querySuperPartyId(list, partyId);
//            if (!list.isEmpty()) {
//                for (int i = 0; i < list.size(); i++) {
//                    if (ret.indexOf(list.get(i) + ",") != -1)
//                        return true;
//                }
//            }
//            return false;
//        } catch (RollbackableException re) {
//            throw re;
//        } catch (Exception e) {
//            throw new RollbackableException("读取数据失败!", e, RolePartyScaleService.class);
//        }
//    }

    /**
     * 得到上级机构ID
     *
     * @param list  机构ID列表
     * @param orgId 查询机构ID
     * @return List
     * @throws RollbackableException
     */
//    private List querySuperPartyId(List list, String orgId) throws RollbackableException {
//        if (orgId == null || "".equals(orgId)) return list;
//        try {
//            PartyBO org = (PartyBO) rolePartyScaleDAO.findBo(PartyBO.class, orgId);
//            if (org != null) {
//                String superId = org.getSuperId();
//                if ("-1".equals(superId)) {
//                    list.add(org.getPartyId());
//                    return list;
//                } else {
//                    list.add(org.getPartyId());
//                    return this.querySuperPartyId(list, org.getSuperId());
//                }
//            }
//            return list;
//        } catch (RollbackableException re) {
//            throw re;
//        } catch (Exception e) {
//            throw new RollbackableException("读取数据失败!", e, RolePartyScaleService.class);
//        }
//    }

    /**
     * 检测维护党组织是否在查询党组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryPartyScale(String roleId, String partyId) throws RollbackableException {
        return rolePartyScaleDAO.checkInQueryPartyScale(roleId, partyId);
    }

    /**
     * 根据管理员角色ID查询其下属的用户的党组织范围权限
     *
     * @param roleId
     * @param scaleFlag
     * @return key=roleId,object = list
     * @throws RollbackableException
     */
    public Hashtable queryRolePartyScaleByAdminRoleId(String roleId, String scaleFlag) throws RollbackableException {
        if (roleId == null || "".equals(roleId)) return null;
        String treeId = "";
        try {
            RoleInfoBO bo = (RoleInfoBO) rolePartyScaleDAO.findBo(RoleInfoBO.class, roleId);
            if (bo != null) treeId = bo.getTreeId();
        } catch (RollbackableException re) {
            throw new RollbackableException("读取数据失败!", re, RoleOrgScaleService.class);
        }
        return rolePartyScaleDAO.queryRolePartyScaleByAdminRoleId(treeId, scaleFlag);
    }


}
