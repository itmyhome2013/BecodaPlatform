package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;

import java.util.Hashtable;
import java.util.List;

/**
 * 角色信息项权限
 * author:lirg
 * 2015-5-24
 */
public interface IRoleDataUCC {

    /**
     * 修改角色权限
     *
     * @param roleDatas 信息项权限列表
     *                  将读权限的项滤掉
     */
    public void updateRoleData(Hashtable roleDatas, String sType, String roleId, String managerFlag, User user) throws RollbackableException;

    /**
     * 查询制定类别范围内的所有信息项权限
     *
     * @param roleID 角色ID
     * @param sType  小类代码
     * @param flag   过滤标记  null||'': 全部  其他值: 启用状态
     * @return Hashtable
     * @roseuid 4479D23000F2
     */
    public Hashtable queryRoleData(String roleID, String sType, String flag) throws RollbackableException;

    /**
     * 得到管理员有权限的指标集
     *
     * @param user
     * @param sType 小类代码
     * @param flag  过滤标记  null||'': 全部  其他值: 启用状态
     */
    public Hashtable querySysOperInfoItem(User user, String sType, String flag) throws RollbackableException;

    /**
     * 得到管理员有权限的指标集
     *
     * @param user
     * @param sType 小类代码
     * @param flag  过滤标记  null||'': 全部  其他值: 启用状态
     * @return list obj = InfoSetPermissionVO
     */
    public List querySysOperInfoSet(User user, String sType, String flag) throws RollbackableException;
}
