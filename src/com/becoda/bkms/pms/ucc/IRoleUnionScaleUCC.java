package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.pojo.bo.RoleUnionScaleBO;

import java.util.List;

/**
 * 角色工会权限
 * author:lirg
 * 2015-5-24
 */
public interface IRoleUnionScaleUCC {

    /**
     * 修改工会范围权限
     *
     * @param rolePartyScale 角色工会权限数组
     * @roseuid 4479E4C20206
     */
    public void createRoleUnionScale(RoleUnionScaleBO rolePartyScale) throws RollbackableException;

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @roseuid 4479E4CC0250
     */
    public List queryRoleUnionScale(String roleID, String scaleFlag) throws RollbackableException;

    public void deleteRoleUnionScale(String roleID, String scaleFlag, String[] unionIds, String manageFlag) throws RollbackableException;

    /**
     * 检测是否某个工会组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkUnionSelected(String roleId, String scaleFlag, String unionId) throws RollbackableException;

    /**
     * 检测维护工会组织是否在查询工会组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryUnionScale(String roleId, String unionId) throws RollbackableException;

}
