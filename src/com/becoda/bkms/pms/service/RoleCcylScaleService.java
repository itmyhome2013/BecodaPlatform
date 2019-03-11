package com.becoda.bkms.pms.service;

//import com.becoda.bkms.ccyl.pojo.bo.CcylBO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.dao.RoleCcylScaleDAO;
import com.becoda.bkms.pms.pojo.bo.RoleCcylScaleBO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色党务权限数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleCcylScaleService {
    private RoleCcylScaleDAO roleCcylScaleDAO;

    public RoleCcylScaleDAO getRoleCcylScaleDAO() {
        return roleCcylScaleDAO;
    }

    public void setRoleCcylScaleDAO(RoleCcylScaleDAO roleCcylScaleDAO) {
        this.roleCcylScaleDAO = roleCcylScaleDAO;
    }

    /**
     * 修改党务范围权限
     *
     * @param roleCcylScale 角色党务权限数组
     * @roseuid 4479E24E0307
     */
    public void createRoleCcylScale(RoleCcylScaleBO roleCcylScale) throws RollbackableException {
        roleCcylScaleDAO.createBo(roleCcylScale);
    }

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return com.icitic.hrms.pms.pojo.bo.RoleCcylScaleBO[]
     * @roseuid 4479E25E03AA
     */
    public List queryRoleCcylScale(String roleID, String scaleFlag) throws RollbackableException {
        return roleCcylScaleDAO.queryRoleCcylScale(roleID, scaleFlag);
    }

    public void deleteRoleCcylScale(String roleID, String scaleFlag, String ccylId) throws RollbackableException {
        roleCcylScaleDAO.deleteRoleCcylScale(roleID, scaleFlag, ccylId);
    }

    /**
     * 检测是否某个党组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkCcylSelected(String roleId, String scaleFlag, String ccylId) throws RollbackableException {
//        String ret = roleCcylScaleDAO.checkCcylSelected(roleId, scaleFlag);
//        if (ccylId == null || "".equals(ccylId)) return true;
//        List list = new ArrayList();
//        try {
//            this.querySuperCcylId(list, ccylId);
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
//            throw new RollbackableException("读取数据失败!", e, RoleCcylScaleService.class);
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
//    private List querySuperCcylId(List list, String orgId) throws RollbackableException {
//        if (orgId == null || "".equals(orgId)) return list;
//        try {
//            CcylBO org = (CcylBO) roleCcylScaleDAO.findBo(CcylBO.class, orgId);
//            if (org != null) {
//                String superId = org.getSuperId();
//                if ("-1".equals(superId)) {
//                    list.add(org.getGroupId());
//                    return list;
//                } else {
//                    list.add(org.getGroupId());
//                    return this.querySuperCcylId(list, org.getSuperId());
//                }
//            }
//            return list;
//        } catch (RollbackableException re) {
//            throw re;
//        } catch (Exception e) {
//            throw new RollbackableException("读取数据失败!", e, RoleCcylScaleService.class);
//        }
//    }

    /**
     * 检测维护党组织是否在查询党组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryCcylScale(String roleId, String ccylId) throws RollbackableException {
        return roleCcylScaleDAO.checkInQueryCcylScale(roleId, ccylId);
    }

    /**
     * 根据管理员角色ID查询其下属的用户的党组织范围权限
     *
     * @param roleId
     * @param scaleFlag
     * @return key=roleId,object = list
     *
     */
    public Hashtable queryRoleCcylScaleByAdminRoleId(String roleId, String scaleFlag) throws RollbackableException {
        if (roleId == null || "".equals(roleId)) return null;
        String treeId = "";
        try {
            RoleInfoBO bo = (RoleInfoBO) roleCcylScaleDAO.findBo(RoleInfoBO.class, roleId);
            if (bo != null) treeId = bo.getTreeId();
        } catch (RollbackableException re) {
            throw new RollbackableException("读取数据失败!", re, RoleOrgScaleService.class);
        }
        return roleCcylScaleDAO.queryRoleCcylScaleByAdminRoleId(treeId, scaleFlag);
    }


}
