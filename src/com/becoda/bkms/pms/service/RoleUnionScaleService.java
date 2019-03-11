package com.becoda.bkms.pms.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.dao.RoleUnionScaleDAO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.bo.RoleUnionScaleBO;
//import com.becoda.bkms.union.pojo.bo.UnionBO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色党务权限数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleUnionScaleService {
    private RoleUnionScaleDAO roleUnionScaleDAO;

    public RoleUnionScaleDAO getRoleUnionScaleDAO() {
        return roleUnionScaleDAO;
    }

    public void setRoleUnionScaleDAO(RoleUnionScaleDAO roleUnionScaleDAO) {
        this.roleUnionScaleDAO = roleUnionScaleDAO;
    }

    /**
     * 修改党务范围权限
     *
     * @param roleUnionScale 角色党务权限数组
     * @roseuid 4479E24E0307
     */
    public void createRoleUnionScale(RoleUnionScaleBO roleUnionScale) throws RollbackableException {
        roleUnionScaleDAO.createBo(roleUnionScale);
    }

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return com.becoda.bkms.pms.pojo.bo.RoleUnionScaleBO[]
     * @roseuid 4479E25E03AA
     */
    public List queryRoleUnionScale(String roleID, String scaleFlag) throws RollbackableException {
        return roleUnionScaleDAO.queryRoleUnionScale(roleID, scaleFlag);
    }

    public void deleteRoleUnionScale(String roleID, String scaleFlag, String unionId) throws RollbackableException {
        roleUnionScaleDAO.deleteRoleUnionScale(roleID, scaleFlag, unionId);
    }

    /**
     * 检测是否某个党组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkUnionSelected(String roleId, String scaleFlag, String unionId) throws RollbackableException {
//        String ret = roleUnionScaleDAO.checkUnionSelected(roleId, scaleFlag);
//        if (unionId == null || "".equals(unionId)) return true;
//        List list = new ArrayList();
//        try {
//            this.querySuperUnionId(list, unionId);
//            if (!list.isEmpty()) {
//                for (int i = 0; i < list.size(); i++) {
//                    if (ret.indexOf(list.get(i) + ",") != -1)
//                        return true;
//                }
//            }
//            return false;
//        } catch (RollbackableException re) {
//            throw re;
//        } catch (Exception re) {
//            throw new RollbackableException("读取数据失败!", re, RoleUnionScaleService.class);
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
//    private List querySuperUnionId(List list, String orgId) throws RollbackableException {
//        if (orgId == null || "".equals(orgId)) return list;
//        try {
//            UnionBO org = (UnionBO) roleUnionScaleDAO.findBo(UnionBO.class, orgId);
//            if (org != null) {
//                String superId = org.getSuperId();
//                if ("-1".equals(superId)) {
//                    list.add(org.getLabourId());
//                    return list;
//                } else {
//                    list.add(org.getLabourId());
//                    return this.querySuperUnionId(list, org.getSuperId());
//                }
//            }
//            return list;
//        } catch (RollbackableException re) {
//            throw re;
//        } catch (Exception e) {
//            throw new RollbackableException("读取数据失败!", e, RoleUnionScaleService.class);
//        }
//    }

    /**
     * 检测维护党组织是否在查询党组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryUnionScale(String roleId, String unionId) throws RollbackableException {
        return roleUnionScaleDAO.checkInQueryUnionScale(roleId, unionId);
    }

    /**
     * 根据管理员角色ID查询其下属的用户的党组织范围权限
     *
     * @param roleId
     * @param scaleFlag
     * @return key=roleId,object = list
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public Hashtable queryRoleUnionScaleByAdminRoleId(String roleId, String scaleFlag) throws RollbackableException {
        if (roleId == null || "".equals(roleId)) return null;
        String treeId = "";
        try {
            RoleInfoBO bo = (RoleInfoBO) roleUnionScaleDAO.findBo(RoleInfoBO.class, roleId);
            if (bo != null) treeId = bo.getTreeId();
        } catch (RollbackableException re) {
            throw new RollbackableException("读取数据失败!", re, RoleOrgScaleService.class);
        }
        return roleUnionScaleDAO.queryRoleUnionScaleByAdminRoleId(treeId, scaleFlag);
    }


}
