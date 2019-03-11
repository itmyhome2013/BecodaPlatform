package com.becoda.bkms.pms.dao;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色机构范围数据访问
 * author:lirg
 * 2015-5-24
 */
public class RoleWageUnitScaleDAO extends GenericDAO {
    /**
     * 删除操作范围或者维护范围权限
     *
     * @param scaleFlag 范围权限类别 1 维护范围 0 查询范围
     * @param roleID    * @param pmsType  1 有权机构  0 无权机构
     * @roseuid 4474707D01C0
     */
    public void deleteRoleWageUnitScale(String roleID, String scaleFlag, String pmsType, String orgId) throws RollbackableException {
        try {
            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("from RoleOrgScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.codeId = '" + PmsConstants.CODE_TYPE_WAGE + "'  and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.pmsType='")
                    .append(pmsType)
                    .append("' and r.condId = '")
                    .append(orgId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty())
                super.deleteBo(list);
        }
        catch (Exception e) {
            throw new RollbackableException("删除数据失败!", e, RoleWageUnitScaleDAO.class);
        }
    }

    /**
     * 检测是否某个机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @param pmsType   1有权机构 0 排除机构
     * @return true 已选，false 未选
     * @throws RollbackableException
     */
    public String checkWageUnitSelected(String roleId, String scaleFlag, String pmsType) throws RollbackableException {
        try {
            String ret = "";
            String sql = new StringBuffer()
                    .append("from RoleOrgScaleBO r where r.codeId='" + PmsConstants.CODE_TYPE_WAGE + "' and r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.pmsType='")
                    .append(pmsType)
                    .append("'").toString();
            List list = hibernateTemplate.find(sql);
            if (list == null || list.isEmpty()) return ret;

            for (int i = 0; i < list.size(); i++) {
                RoleOrgScaleBO bo = (RoleOrgScaleBO) list.get(i);
                ret += bo.getCondId() + ",";
            }
            return ret;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleWageUnitScaleDAO.class);
        }
    }

    /**
     * 检测排除机构是否在有权机构范围
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return true 在，false 不在
     */
    public boolean checkInHaveWageUnitScale(String roleId, String scaleFlag, String orgId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.condId from RoleOrgScaleBO r where r.codeId='" + PmsConstants.CODE_TYPE_WAGE + "' and r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.pmsType='" + PmsConstants.SCALE_USE + "'").toString();
            List list = hibernateTemplate.find(sql);
            Org org = SysCacheTool.findOrgById(orgId);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String oId = list.get(i).toString();
                    Org nOrg = SysCacheTool.findOrgById(oId);
                    if (org.getTreeId().startsWith(nOrg.getTreeId())) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleWageUnitScaleDAO.class);
        }
    }

    /**
     * 检测维护机构是否在查询机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryWageUnitScale(String roleId, String orgId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.condId from RoleOrgScaleBO r where r.codeId='" + PmsConstants.CODE_TYPE_WAGE + "' and r.roleId='")
                    .append(roleId).append("' and r.scaleFlag='").append(PmsConstants.QUERY_SCALE)
                    .append("' and r.pmsType='").append(PmsConstants.SCALE_USE).append("'").toString();
            List list = hibernateTemplate.find(sql);
            Org org = SysCacheTool.findOrgById(orgId);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String oId = list.get(i).toString();
                    Org nOrg = SysCacheTool.findOrgById(oId);
                    if (org.getTreeId().startsWith(nOrg.getTreeId())) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleWageUnitScaleDAO.class);
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
    public List queryRoleWageUnitScale(String roleID, String scaleFlag, String pmsType) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleOrgScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.codeId = '" + PmsConstants.CODE_TYPE_WAGE + "' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.pmsType='")
                    .append(pmsType).append("'");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleWageUnitScaleDAO.class);
        }
    }

    /**
     * 根据管理员角色ID查询其下属的用户的机构范围权限
     *
     * @param treeId
     * @param scaleFlag
     * @param pmsType
     * @return key=roleId,object = list
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public Hashtable queryRoleWageUnitScaleByAdminRoleId(String treeId, String scaleFlag, String pmsType) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleOrgScaleBO r where r.roleId in(")
                    .append("select r2.roleId from RoleInfoBO r2 where r2.treeId like '")
                    .append(treeId).append("%' and r2.treeId <> '").append(treeId)
                    .append("') and r.codeId = '" + PmsConstants.CODE_TYPE_WAGE + "' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.pmsType='")
                    .append(pmsType).append("' order by r.roleId");
            List list = hibernateTemplate.find(sb.toString());
            if (list == null || list.isEmpty()) return null;
            List rtlist = null;
            Hashtable hash = new Hashtable();
            for (int i = 0; i < list.size(); i++) {
                RoleOrgScaleBO orgScale = (RoleOrgScaleBO) list.get(i);
                if (!hash.containsKey(orgScale.getRoleId())) {
                    rtlist = new ArrayList();
                    hash.put(orgScale.getRoleId(), rtlist);
                }
                RoleOrgScaleBO mOrgScale = new RoleOrgScaleBO();
                Tools.copyProperties(mOrgScale, orgScale);
                rtlist.add(mOrgScale);
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleWageUnitScaleDAO.class);
        }
    }
}
