package com.becoda.bkms.pms.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleUnionScaleBO;
//import com.becoda.bkms.union.pojo.bo.UnionBO;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色工会组织范围数据访问
 *
 * @author:lirg
 * @2015-5-24
 */
public class RoleUnionScaleDAO extends GenericDAO {
    /**
     * 删除操作范围或者维护范围权限
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @roseuid 447484FC0236
     */
    public void deleteRoleUnionScale(String roleID, String scaleFlag, String unionId) throws RollbackableException {
        try {
            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("from RoleUnionScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.unionId='")
                    .append(unionId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty())
                super.deleteBo(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除数据失败!", e, RoleUnionScaleDAO.class);
        }
    }

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return RoleCcylScale[]
     * @roseuid 44748530028B
     */
    public List queryRoleUnionScale(String roleID, String scaleFlag) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleUnionScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag).append("'");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleUnionScaleDAO.class);
        }
    }

    /**
     * 检测是否某个工会组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
    public String checkUnionSelected(String roleId, String scaleFlag) throws RollbackableException {
        try {
            String ret = "";
            String sql = new StringBuffer()
                    .append("from RoleUnionScaleBO r where   r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("'").toString();
            List list = hibernateTemplate.find(sql);
            if (list == null || list.isEmpty()) return ret;

            for (int i = 0; i < list.size(); i++) {
                RoleUnionScaleBO bo = (RoleUnionScaleBO) list.get(i);
                ret += bo.getUnionId() + ",";
            }
            return ret;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 检测维护工会组织是否在查询工会组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryUnionScale(String roleId, String pId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.unionId from RoleUnionScaleBO r where r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='" + PmsConstants.QUERY_SCALE + "'").toString();
            List list = hibernateTemplate.find(sql);
//            UnionBO uo = (UnionBO) super.findBo(UnionBO.class, pId);
//            if (list != null && list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    String unionId = list.get(i).toString();
//                    UnionBO nCcyl = (UnionBO) super.findBo(UnionBO.class, unionId);
//                    if (uo.getTreeId().startsWith(nCcyl.getTreeId())) {
//                        return true;
//                    }
//                }
//                return false;
//            }
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 根据管理员角色ID查询其下属的用户的党组织范围权限
     *
     * @param treeId
     * @param scaleFlag
     * @return key=roleId,object = list
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public Hashtable queryRoleUnionScaleByAdminRoleId(String treeId, String scaleFlag) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleUnionScaleBO r where r.roleId in(")
                    .append("select r2.roleId from RoleInfoBO r2 where r2.treeId like '")
                    .append(treeId).append("%' and r2.treeId <> '").append(treeId).append("')  and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' order by r.roleId");
            List list = hibernateTemplate.find(sb.toString());
            List rtlist = null;
            Hashtable hash = new Hashtable();
            for (int i = 0; i < list.size(); i++) {
                RoleUnionScaleBO unionScale = (RoleUnionScaleBO) list.get(i);
                if (!hash.containsKey(unionScale.getRoleId())) {
                    rtlist = new ArrayList();
                    hash.put(unionScale.getRoleId(), rtlist);
                }
                RoleUnionScaleBO mCcylScale = new RoleUnionScaleBO();
                Tools.copyProperties(mCcylScale, unionScale);
                rtlist.add(mCcylScale);
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleUnionScaleDAO.class);
        }
    }

}
