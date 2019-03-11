package com.becoda.bkms.pms.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.*;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

import java.util.List;

/**
 * 角色信息数据访问
 * author:lirg
 * 2015-5-24
 */
public class RoleInfoDAO extends GenericDAO {
    /**
     * 查询指定角色创建的所有角色
     *
     * @param sysRoleId 系统管理员角色ID
     * @return RoleInfoBO[]
     */
    public List queryAllRoleInfos(String sysRoleId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleInfoBO rd where rd.creator='")
                    .append(sysRoleId).append("' order by rd.sysOper||rd.hrLeader||rd.businessUser desc,rd.roleName");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, RoleInfoDAO.class);
        }
    }

    /**
     * 查询指定角色创建的所有角色
     *
     * @param sysRoleId 系统管理员角色ID
     * @param roleName
     * @return RoleInfoBO[]
     */
    public List queryAllRoleInfos(PageVO vo, String sysRoleId, String roleName) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            StringBuffer countsb = new StringBuffer();
            if (roleName == null || "".equals(roleName)) {
                sb.append("from RoleInfoBO rd where rd.creator='")
                        .append(sysRoleId).append("' order by rd.sysOper||rd.hrLeader||rd.businessUser desc,rd.roleName");
                countsb.append("select count(rd) from RoleInfoBO rd where rd.creator='")
                        .append(sysRoleId).append("' order by rd.sysOper||rd.hrLeader||rd.businessUser desc,rd.roleName");
            } else {
                sb.append("from RoleInfoBO rd where rd.creator='")
                        .append(sysRoleId).append("' and rd.roleName like '%")
                        .append(roleName).append("%' order by rd.sysOper||rd.hrLeader||rd.businessUser desc,rd.roleName");
                countsb.append("select count(rd) from RoleInfoBO rd where rd.creator='")
                        .append(sysRoleId).append("' and rd.roleName like '%")
                        .append(roleName).append("%' order by rd.sysOper||rd.hrLeader||rd.businessUser desc,rd.roleName");
            }

            return pageQuery(vo, countsb.toString(), sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, RoleInfoDAO.class);
        }
    }

    /**
     * 查询指定角色创建的所有角色,包含当前用户所属角色
     *
     * @param sysRoleId 系统管理员角色ID
     * @return RoleInfoBO[]
     */
    public List queryChildRoleIncludeSelf(String sysRoleId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleInfoBO rd where rd.creator='")
                    .append(sysRoleId).append("' or rd.roleId='").append(sysRoleId)
                    .append("' order by rd.sysOper||rd.hrLeader||rd.businessUser desc,rd.roleName");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, RoleInfoDAO.class);
        }
    }

    /**
     * 查询指定管理员角色是否存在本级别用户
     *
     * @param sysRoleId 系统管理员角色ID
     * @return
     */
    public boolean checkCurrentUserBySysRoleId(String sysRoleId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select count(*) from User u where u.belongRoleId='")
                    .append(sysRoleId).append("'");

            List list = hibernateTemplate.find(sb.toString());
            String s = list.get(0).toString();
            int count = Integer.parseInt(s);
            return count > 0;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, RoleInfoDAO.class);
        }
    }

    /**
     * 查询指定管理员角色是否存在下属角色
     *
     * @param sysRoleId 系统管理员角色ID
     * @return
     */
    public boolean checkSubRoleBySysRoleId(String sysRoleId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select count(*) from RoleInfoBO rd where rd.creator='")
                    .append(sysRoleId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            String s = list.get(0).toString();
            int count = Integer.parseInt(s);
            return count > 0;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, RoleInfoDAO.class);
        }
    }


    public void deleteRoleInfo(String roleId) throws RollbackableException {
        try {
            RoleInfoBO bo = (RoleInfoBO) super.findBo(RoleInfoBO.class, roleId);
            super.deleteBo(bo);
            List list = hibernateTemplate.find("from RoleOperateBO r where r.roleId='" + roleId + "'");
            if (list != null && list.size() > 0)
                super.deleteBo(list);
            list = hibernateTemplate.find("from RoleDataBO r where r.roleId='" + roleId + "'");
            if (list != null && list.size() > 0)
                super.deleteBo(list);
            list = hibernateTemplate.find("from RolePartyScaleBO r where r.roleId='" + roleId + "'");
            if (list != null && list.size() > 0)
                super.deleteBo(list);
            list = hibernateTemplate.find("from RoleOrgScaleBO r where r.roleId='" + roleId + "'");
            if (list != null && list.size() > 0)
                super.deleteBo(list);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除权限信息失败!", e, RoleInfoDAO.class);

        }

    }

    /**
     * 复制角色信息
     *
     * @param newRoleName  新角色名
     * @param sourceRoleId 源角色ID
     * @throws RollbackableException
     */
    public void saveAsRoleInfo(String newRoleName, String sourceRoleId) throws RollbackableException {
        try {
            //取得源角色信息
            RoleInfoBO sRole = (RoleInfoBO) super.findBo(RoleInfoBO.class, sourceRoleId);
            RoleInfoBO dRole = new RoleInfoBO();
            Tools.copyProperties(dRole, sRole);
            dRole.setRoleId("");
            dRole.setRoleName(newRoleName);

            String sourceTreeId = sRole.getTreeId();
            String superTree = sourceTreeId.substring(0, sourceTreeId.length() - PmsConstants.TREE_LENGTH);
            String treeId = SequenceGenerator.getTreeId("PMS_ROLEINFO", "TREE_ID", superTree, PmsConstants.TREE_LENGTH, PmsConstants.TREE_STEP_LENGTH);
            dRole.setTreeId(treeId);

            String newRoleId = super.createBo(dRole);

            //取得源角色功能权限
            List list = hibernateTemplate.find("from RoleOperateBO r where r.roleId = '" + sourceRoleId + "'");
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    RoleOperateBO oper = new RoleOperateBO();
                    Tools.copyProperties(oper, list.get(i));
                    oper.setRoleId(newRoleId);
                    oper.setRoleOperateId("");
                    super.createBo(oper);
                }
            }
            //信息项权限
            list = hibernateTemplate.find("from RoleDataBO r where r.roleId = '" + sourceRoleId + "'");
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    RoleDataBO data = new RoleDataBO();
                    Tools.copyProperties(data, list.get(i));
                    data.setRoleId(newRoleId);
                    data.setRoleDataId("");
                    super.createBo(data);
                }
            }
            //取得源角色机构权限
            list = hibernateTemplate.find("from RoleOrgScaleBO r where r.roleId = '" + sourceRoleId + "'");
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    RoleOrgScaleBO orgscale = new RoleOrgScaleBO();
                    Tools.copyProperties(orgscale, list.get(i));
                    orgscale.setRoleId(newRoleId);
                    orgscale.setOrgScaleId("");
                    super.createBo(orgscale);
                }
            }

            //取得源角色党组织权限
            list = hibernateTemplate.find("from RolePartyScaleBO r where r.roleId = '" + sourceRoleId + "'");
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    RolePartyScaleBO partyscale = new RolePartyScaleBO();
                    Tools.copyProperties(partyscale, list.get(i));
                    partyscale.setRoleId(newRoleId);
                    partyscale.setPartyScaleId("");
                    super.createBo(partyscale);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("角色复制失败!", e, RoleInfoDAO.class);
        }
    }
}
