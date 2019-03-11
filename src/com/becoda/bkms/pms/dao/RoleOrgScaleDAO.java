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
public class RoleOrgScaleDAO extends GenericDAO {
    /**
     * 删除操作范围或者维护范围权限
     *
     * @param scaleFlag 范围权限类别 1 维护范围 0 查询范围
     * @param roleID    * @param pmsType  1 有权机构  0 无权机构
     * @roseuid 4474707D01C0
     */
    public void deleteRoleOrgScale(String roleID, String scaleFlag, String pmsType, String orgId) throws RollbackableException {
        try {
            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("from RoleOrgScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.codeId = '" + PmsConstants.CODE_TYPE_ORG + "'  and r.scaleFlag='")
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
            //e.printStackTrace();
            throw new RollbackableException("删除数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 根据代码集IDList,得到In条件的代码集ID
     *
     * @param pmsCodeSet
     */
    private String getSqlCodeInCondition(List pmsCodeSet) {
        if (pmsCodeSet == null || pmsCodeSet.size() == 0) {
            return "''";
        }
        String inStr = "";
        for (int i = 0; i < pmsCodeSet.size(); i++) {
            if (i == 0) {
                inStr = "'" + pmsCodeSet.get(i).toString() + "'";
            } else {
                inStr += ",'" + pmsCodeSet.get(i).toString() + "'";
            }
        }
        return inStr;
    }

    /**
     * 删除代码权限
     *
     * @param roleId
     * @param scaleFlag 范围权限类别 1 维护范围 0 查询范围
     */
    public void deleteCodeScaleByRoleId(String roleId, String scaleFlag, List pmsCodeSet) throws RollbackableException {
        try {
            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("from RoleOrgScaleBO r where r.roleId='")
                    .append(roleId)
                    .append("' and r.codeId not in ('" + PmsConstants.CODE_TYPE_ORG + "','" + PmsConstants.CODE_TYPE_WAGE + "')  and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.codeId in (").append(getSqlCodeInCondition(pmsCodeSet)).append(")");
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty())
                super.deleteBo(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除代码权限失败!", e, RoleOrgScaleDAO.class);
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
    public String checkOrgSelected(String roleId, String scaleFlag, String pmsType) throws RollbackableException {
        try {
            String ret = "";
            String sql = new StringBuffer()
                    .append("from RoleOrgScaleBO r where r.codeId='" + PmsConstants.CODE_TYPE_ORG + "' and r.roleId='")
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
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 检测排除机构是否在有权机构范围
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return true 在，false 不在
     */
    public boolean checkInHaveOrgScale(String roleId, String scaleFlag, String orgId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.condId from RoleOrgScaleBO r where r.codeId='" + PmsConstants.CODE_TYPE_ORG + "' and r.roleId='")
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
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 检测维护机构是否在查询机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryOrgScale(String roleId, String orgId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.condId from RoleOrgScaleBO r where r.codeId='" + PmsConstants.CODE_TYPE_ORG + "' and r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='" + PmsConstants.QUERY_SCALE + "' and r.pmsType='" + PmsConstants.SCALE_USE + "'").toString();
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
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     * 检测维护机构是否在查询排除机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryNoOrgScale(String roleId, String orgId) throws RollbackableException {
        try {
            //RoleOrgScaleBO bo;
            String sql = new StringBuffer()
                    .append("select r.condId from RoleOrgScaleBO r where r.codeId='" + PmsConstants.CODE_TYPE_ORG + "' and r.roleId='")
                    .append(roleId)
                    .append("' and r.scaleFlag='" + PmsConstants.QUERY_SCALE + "' and r.pmsType='" + PmsConstants.SCALE_REFUSE + "'").toString();
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
            throw new RollbackableException("查询数据失败!", e, RoleOrgScaleDAO.class);
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
        try {
            StringBuffer sb = new StringBuffer();    
            sb.append("from RoleOrgScaleBO r where r.roleId='")
                    .append(roleID)
                    .append("' and r.codeId = '" + PmsConstants.CODE_TYPE_ORG + "' and r.scaleFlag='")
                    .append(scaleFlag)
                    .append("' and r.pmsType='")
                    .append(pmsType).append("' order by substr(r.orgScaleId,20,28)");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

//    /**
//     * 根据管理员角色ID查询其下属的用户的机构范围权限
//     *
//     * @param roleId
//     * @param scaleFlag
//     * @param pmsType
//     * @return key=roleId,object = list
//     * @throws RollbackableException
//     */
//    public Hashtable queryRoleOrgScaleByAdminRoleId(String roleId, String scaleFlag, String pmsType) throws RollbackableException {
//        try {
//            StringBuffer sb = new StringBuffer();
//            sb.append("from RoleOrgScaleBO r where r.roleId in(")
//                    .append("select r2.roleId from RoleInfoBO r2 where  r2.creator in (select r1.roleId from RoleInfoBO r1 where r1.creator='")
//                    .append(roleId).append("' and r1.sysOper='").append(PmsConstants.IS_SYS_MANAGER).append("') or r2.creator='").append(roleId).append("'")
//                    .append(") and r.codeId = '" + PmsConstants.CODE_TYPE_ORG + "' and r.scaleFlag='")
//                    .append(scaleFlag)
//                    .append("' and r.pmsType='")
//                    .append(pmsType).append("' order by r.roleId");
//            List list = hibernateTemplate.find(sb.toString());
//            if (list == null || list.isEmpty()) return null;
//            List rtlist = null;
//            Hashtable hash = new Hashtable();
//            for (int i = 0; i < list.size(); i++) {
//                RoleOrgScaleBO orgScale = (RoleOrgScaleBO) list.get(i);
//                if (!hash.containsKey(orgScale.getRoleId())) {
//                    rtlist = new ArrayList();
//                    hash.put(orgScale.getRoleId(), rtlist);
//                }
//                RoleOrgScaleBO mOrgScale = new RoleOrgScaleBO();
//                Tools.copyProperties(mOrgScale, orgScale);
//                rtlist.add(mOrgScale);
//            }
//            return hash;
//        }
//        catch (Exception e) {
//            //e.printStackTrace();
//            throw new RollbackableException("读取数据失败!", e, RoleOrgScaleDAO.class);
//        }
//    }

    /**
     * 根据管理员角色TREEID查询其下属的用户的机构范围权限
     *
     * @param treeId
     * @param scaleFlag
     * @param pmsType
     * @return key=roleId,object = list
     * @throws RollbackableException
     */
    public Hashtable queryRoleOrgScaleByAdminRoleId(String treeId, String scaleFlag, String pmsType) throws RollbackableException {
        if (treeId == null || "".equals(treeId)) return null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleOrgScaleBO r where r.roleId in(")
                    .append("select r2.roleId from RoleInfoBO r2 where r2.treeId like '").append(treeId)
                    .append("%' and r2.treeId <> '").append(treeId)
                    .append("') and r.codeId = '" + PmsConstants.CODE_TYPE_ORG + "' and r.scaleFlag='")
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
            throw new RollbackableException("读取数据失败!", e, RoleOrgScaleDAO.class);
        }
    }


    /**
     * 查询角色的代码权限
     *
     * @param roleId
     * @param scaleFlag
     * @return
     * @throws RollbackableException
     */
    public Hashtable queryRolePmsCode(String roleId, String scaleFlag, List pmsCodeSet) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleOrgScaleBO r where r.roleId='")
                    .append(roleId)
                    .append("' and r.codeId not in ('" + PmsConstants.CODE_TYPE_ORG + "','" + PmsConstants.CODE_TYPE_WAGE + "') and r.scaleFlag='")
                    .append(scaleFlag).append("' and r.codeId in (").append(getSqlCodeInCondition(pmsCodeSet))
                    .append(") order by r.codeId");
            List list = hibernateTemplate.find(sb.toString());
            if (list == null || list.size() == 0) return null;

            Hashtable hash = new Hashtable();
            List codeList = null;
            for (int i = 0; i < list.size(); i++) {
                RoleOrgScaleBO rbo = (RoleOrgScaleBO) list.get(i);
                String codeid = rbo.getCodeId();
                codeid = Tools.filterNull(codeid);
                if (!"".equals(codeid)) {
                    if (!hash.containsKey(codeid)) {
                        codeList = new ArrayList();
                        hash.put(codeid, codeList);
                    }
                    String codeitemid = rbo.getCondId();
                    codeList.add(codeitemid);
                }
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败!", e, RoleOrgScaleDAO.class);
        }
    }

    /**
     //     * 查询所有系统代码
     //
     //     */
//    public List queryAlPmsCodeByUserId(String userId) throws RollbackableException {
//        try {
//            StringBuffer sb = new StringBuffer();
//            sb.append("from CodeItemBO c  where c.setId in ('")
//                    .append(Constants.SYS_CADRE_CODEID)
//                    .append("','").append(Constants.SYS_SAVE_ABILITY_PERSON).append("') order by c.setId");
//            List list = hibernateTemplate.find(sb.toString());
//            List rtnList = new ArrayList();
//            String codeSetId="";
//            if (list != null) {
//                for (int i = 0; i < list.size(); i++) {
//                    CodeItemBO cbo = new CodeItemBO();
//                    Tools.copyProperties(cbo, list.get(i));
//                    //将代码集添加到list中
//                    if (!codeSetId.equals(cbo.getSetId()) && (cbo.getSetId().equals(Constants.SYS_CADRE_CODEID) || cbo.getSetId().equals(Constants.SYS_SAVE_ABILITY_PERSON)))
//                    {
//                        codeSetId = cbo.getSetId();
//                        CodeSetBO set = SysCacheTool.findCodeSet(cbo.getSetId()); //得到代码集
//                        //使用CodeItemBO 来表示CodeSetBO
//                        CodeItemBO newSet = new CodeItemBO();
//                        newSet.setSetId(cbo.getSetId());
//                        newSet.setItemName(set.getSetName());
//                        newSet.setItemId(cbo.getSetId());
//                        newSet.setItemSuper("0000");
//                        newSet.setTreeId(cbo.getSetId());
//                        rtnList.add(newSet);
//                    }
//                    //更改item的TreeId == setId+treeId;
//                    cbo.setTreeId(cbo.getSetId() + cbo.getTreeId());
//                    // 如果superId=="-1" 更改 superId=setId
//                    if (cbo.getItemSuper().equals("-1")) {
//                        cbo.setItemSuper(cbo.getSetId());
//                    }
//                    rtnList.add(cbo);
//                }
//            }
//            return rtnList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RollbackableException( "读取数据失败!", e, RoleOrgScaleDAO.class);
//        }

    //    }

    /**
     * 按照SQL语句删除数据
     * 收回下级用户的权限
     */
    public void deleteDataBySql(String sql) throws RollbackableException {
        try {
            List list = hibernateTemplate.find(sql);
            if (list != null && !list.isEmpty())
                super.deleteBo(list);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除下级用户代码权限时出错，存储失败！", e, RoleOperateDAO.class);
        }

    }
}
