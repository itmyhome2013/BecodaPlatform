package com.becoda.bkms.pms.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;

import java.util.List;

/**
 * 角色操作范围数据访问
 * author:lirg
 * 2015-5-24
 */
public class RoleOperateDAO extends GenericDAO {
    /**
     * 查询操作权限，按照TreeID排序 <br>
     * 说名:如果是sys角色,则直接查询pmsOperate表
     *
     * @param roleID 角色ID <br>
     * @return List obj = operateBO
     * @roseuid 4474645C0003
     */
    public List queryRoleOperate(String roleID) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleOperateBO ro where ro.roleId='")
                    .append(roleID).append("'");

            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            //  e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, RoleOperateDAO.class);
        }
    }

    public void deleteOperatesByRoleId(String roleId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from RoleOperateBO ro where ro.roleId='")
                    .append(roleId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && list.size() > 0)
                super.deleteBo(list);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RollbackableException("修改功能权限时出现错误，存储失败！", e, RoleOperateDAO.class);
        }
    }

    /**
     * 按照SQL语句删除数据
     * 收回下级用户的权限
     */
    public void deleteDataBySql(String sql) throws RollbackableException {
        try {
            List list = hibernateTemplate.find(sql);
            if (list != null && list.size() > 0)
                super.deleteBo(list);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RollbackableException("删除下级用户权限时出错，存储失败！", e, RoleOperateDAO.class);
        }

    }
}
