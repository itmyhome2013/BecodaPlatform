package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;

import java.util.Hashtable;
import java.util.List;


/**
 * 角色机构权限
 * author:lirg
 * 2015-5-24
 */
public interface IRoleOrgScaleUCC {

    /**
     * 先删除再添加
     *
     * @roseuid 4479E4A90174
     */
    public void createRoleOrgScale(RoleOrgScaleBO roleOrgScale, User user, String managerFlag) throws RollbackableException;

    /**
     * 根据标示符查询维护范围或者查询范围,按照CodeID排序
     *
     * @roseuid 4479E4B30178
     */
    public List queryRoleOrgScale(String roleID, String scaleFlag, String pmsType) throws RollbackableException;

    public void deleteRoleOrgScale(String roleID, String scaleFlag, String pmsType, String[] orgIds, String managerFlag, User user) throws RollbackableException;

    /**
     * 检测是否某个机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @param pmsType   1有权机构 0 排除机构
     * @return true 已选，false 未选
     */
    public boolean checkOrgSelected(String roleId, String scaleFlag, String pmsType, String orgId) throws RollbackableException;

    /**
     * 检测排除机构是否在有权机构范围
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @return true 在，false 不在
     */
    public boolean checkInHaveOrgScale(String roleId, String scaleFlag, String orgId) throws RollbackableException;

    /**
     * * 查询角色的代码权限
     *
     * @param roleId
     * @param scaleFlag
     * @return
     * @throws RollbackableException
     */
    public Hashtable queryRolePmsCode(String roleId, String scaleFlag, List pmsCode) throws RollbackableException;

//       public List queryAlPmsCodeByUserId(String userId) throws RollbackableException;
    public void updateRoleCodeScale(String roleId, String scaleFlag, List codeList, String manageFlag, List pmsCode, User user) throws RollbackableException;

    /**
     * 检测维护机构是否在查询机构范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryOrgScale(String roleId, String orgId) throws RollbackableException;

    public boolean checkInQueryNoOrgScale(String roleId, String orgId) throws RollbackableException;
}
