package com.becoda.bkms.pms.dao;

//import com.becoda.bkms.ccp.pojo.bo.PartyBO;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RolePartyScaleBO;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/**
 * 角色党务范围数据访问
 *
 * @author:lirg
 * @2015-5-24
 */
public class RolePartyScaleDAO extends GenericDAO {
    /**
     * 删除操作范围或者维护范围权限
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @roseuid 447484FC0236
     */
    public void deleteRolePartyScale(String roleID, String scaleFlag, String partyId) throws RollbackableException {
        try {
            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("from RolePartyScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.partyId='")
                    .append(partyId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty())
                super.deleteBo(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除数据失败!", e, RolePartyScaleDAO.class);
        }
    }

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return RolePartyScale[]
     * @roseuid 44748530028B
     */
    public List queryRolePartyScale(String roleID, String scaleFlag) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RolePartyScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag).append("'");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RolePartyScaleDAO.class);
        }
    }

    /**
     * 查询权限范围的党组织ID
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return ret 逗号分隔的字符串
     * @throws RollbackableException
     */
    public String checkPartySelected(String roleId, String scaleFlag) throws RollbackableException {
        try {
            String ret = "";
            String sql = new StringBuffer()
                    .append("from RolePartyScaleBO r where   r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("'").toString();
            List list = hibernateTemplate.find(sql);
            if (list == null || list.isEmpty()) return ret;

            for (int i = 0; i < list.size(); i++) {
                RolePartyScaleBO bo = (RolePartyScaleBO) list.get(i);
                ret += bo.getPartyId() + ",";
            }
            return ret;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 检测维护党组织是否在查询党组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryPartyScale(String roleId, String pId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.partyId from RolePartyScaleBO r where r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='" + PmsConstants.QUERY_SCALE + "'").toString();
            List list = hibernateTemplate.find(sql);
//            PartyBO party = (PartyBO) super.findBo(PartyBO.class, pId);
//            if (list != null && list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    String partyId = list.get(i).toString();
//                    PartyBO nParty = (PartyBO) super.findBo(PartyBO.class, partyId);
//                    if (party.getTreeId().startsWith(nParty.getTreeId())) {
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
     * @throws RollbackableException
     */
    public Hashtable queryRolePartyScaleByAdminRoleId(String treeId, String scaleFlag) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RolePartyScaleBO r where r.roleId in(")
                    .append("select r2.roleId from RoleInfoBO r2 where  r2.treeId like '")
                    .append(treeId).append("%' and r2.treeId <> '").append(treeId).append("') and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' order by r.roleId");
            List list = hibernateTemplate.find(sb.toString());
            List rtlist = null;
            Hashtable hash = new Hashtable();
            for (int i = 0; i < list.size(); i++) {
                RolePartyScaleBO partyScale = (RolePartyScaleBO) list.get(i);
                if (!hash.containsKey(partyScale.getRoleId())) {
                    rtlist = new ArrayList();
                    hash.put(partyScale.getRoleId(), rtlist);
                }
                RolePartyScaleBO mPartyScale = new RolePartyScaleBO();
                Tools.copyProperties(mPartyScale, partyScale);
                rtlist.add(mPartyScale);
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RolePartyScaleDAO.class);
        }
    }

}
