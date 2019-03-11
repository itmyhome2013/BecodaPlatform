package com.becoda.bkms.pms.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.PmsConstants;

import java.util.Hashtable;
import java.util.List;


/**
 * 用户角色关系数据访问
 *
 * @author:lirg
 * @2015-5-24
 */
public class UserRoleDAO extends GenericDAO {
    /**
     * 查询指定角色所拥有的用户列表
     *
     * @param roleId 角色ID
     * @return List
     */
    public List queryRoleUser(String roleId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select uv from UserPmsInfoVO uv,UserRoleBO ur where uv.personId=ur.personId and ur.roleId='")
                    .append(roleId).append("'");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, UserRoleDAO.class);
        }
    }

    /**
     * 查询指定用户所属的角色列表
     *
     * @param userId 用户ID
     * @return list obj = RoleInfoBO
     * @roseuid 447485F30295
     */
    public List queryUserRole(String userId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select r from RoleInfoBO r,UserRoleBO u where r.roleId=u.roleId and u.personId='")
                    .append(userId).append("'");
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            // e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, UserRoleDAO.class);
        }

    }

    /**
     * 将用户的角色移出
     *
     * @param userId 用户ID
     * @roseuid 4474877F0050
     */
    public void clearRole(String userId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select ur from UserRoleBO ur where ur.personId='")
                    .append(userId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && list.size() > 0)
                super.deleteBo(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("数据删除失败!", e, UserRoleDAO.class);
        }
    }

    /**
     * 得到所有的系统管理员信息
     *
     * @return key = "roleId" obj = "roleName"
     */
    public Hashtable queryAllSysOperRoleName() throws RollbackableException {
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("select u.roleId,u.roleName from RoleInfoBO u where u.sysOper='" + PmsConstants.IS_SYS_MANAGER + "'");
            List list = hibernateTemplate.find(sf.toString());
            if (list == null || list.isEmpty()) return null;
            Hashtable hash = new Hashtable();
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                hash.put(obj[0], obj[1]);
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取用户信息错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 移出角色的用户
     *
     * @param ids    用户ID数组
     * @param roleId 角色ID
     * @throws RollbackableException
     */

    public void deleteRoleUser(String[] ids, String roleId) throws RollbackableException {
        try {
            String InStr = "";
            for (int i = 0; i < ids.length; i++) {
                if (i == 0)
                    InStr += "'" + ids[i] + "'";
                else
                    InStr += ",'" + ids[i] + "'";
            }
            String sql = "from UserRoleBO u where u.personId in (" + InStr + ") and u.roleId='" + roleId + "'";
            List list = hibernateTemplate.find(sql);
            if (list != null && list.size() > 0)
                super.deleteBo(list);

        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("移出用户失败!", e, UserInfoDAO.class);

        }

    }

}
