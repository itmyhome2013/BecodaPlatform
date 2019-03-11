package com.becoda.bkms.pms.dao;

//import com.becoda.bkms.ccyl.pojo.bo.CcylBO;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleCcylScaleBO;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色团组织范围数据访问
 *
 * @author:lirg
 * @2015-5-24
 */
public class RoleCcylScaleDAO extends GenericDAO {
    /**
     * 删除操作范围或者维护范围权限
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @roseuid 447484FC0236
     */
    public void deleteRoleCcylScale(String roleID, String scaleFlag, String ccylId) throws RollbackableException {
        try {
            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("from RoleCcylScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.ccylId='")
                    .append(ccylId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty())
                super.deleteBo(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除数据失败!", e, RoleCcylScaleDAO.class);
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
    public List queryRoleCcylScale(String roleID, String scaleFlag) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleCcylScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag).append("'");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleCcylScaleDAO.class);
        }
    }

    /**
     * 查询权限范围的团组织ID
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return ret 逗号分隔的字符串
     * @throws RollbackableException
     */
    public String checkCcylSelected(String roleId, String scaleFlag) throws RollbackableException {
        try {
            String ret = "";
            String sql = new StringBuffer()
                    .append("from RoleCcylScaleBO r where   r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("'").toString();
            List list = hibernateTemplate.find(sql);
            if (list == null || list.isEmpty()) return ret;

            for (int i = 0; i < list.size(); i++) {
                RoleCcylScaleBO bo = (RoleCcylScaleBO) list.get(i);
                ret += bo.getCcylId() + ",";
            }
            return ret;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 检测维护团组织是否在查询团组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryCcylScale(String roleId, String pId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.ccylId from RoleCcylScaleBO r where r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='" + PmsConstants.QUERY_SCALE + "'").toString();
            List list = hibernateTemplate.find(sql);
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }


    /**
     * 根据管理员角色ID查询其下属的用户的团组织范围权限
     *
     * @param treeId
     * @param scaleFlag
     * @return key=roleId,object = list
     *
     */
    public Hashtable queryRoleCcylScaleByAdminRoleId(String treeId, String scaleFlag) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleCcylScaleBO r where r.roleId in(")
                    .append("select r2.roleId from RoleInfoBO r2 where r2.treeId like '")
                    .append(treeId).append("%' and r2.treeId <> '").append(treeId).append("')  and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' order by r.roleId");
            List list = hibernateTemplate.find(sb.toString());
            List rtlist = null;
            Hashtable hash = new Hashtable();
            for (int i = 0; i < list.size(); i++) {
                RoleCcylScaleBO ccylScale = (RoleCcylScaleBO) list.get(i);
                if (!hash.containsKey(ccylScale.getRoleId())) {
                    rtlist = new ArrayList();
                    hash.put(ccylScale.getRoleId(), rtlist);
                }
                RoleCcylScaleBO mCcylScale = new RoleCcylScaleBO();
                Tools.copyProperties(mCcylScale, ccylScale);
                rtlist.add(mCcylScale);
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleCcylScaleDAO.class);
        }
    }
}
