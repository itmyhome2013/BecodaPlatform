package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.pojo.bo.RolePartyScaleBO;

import java.util.List;


/**
 * 角色党务权限
 * author:lirg
 * 2015-5-24
 */
public interface IRolePartyScaleUCC {

    /**
     * 修改党务范围权限
     *
     * @param rolePartyScale 角色党务权限数组
     * @roseuid 4479E4C20206
     */
    public void createRolepartyScale(RolePartyScaleBO rolePartyScale, User user) throws RollbackableException;

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @roseuid 4479E4CC0250
     */
    public List queryRolePartyScale(String roleID, String scaleFlag) throws RollbackableException;

    public void deleteRolePartyScale(String roleID, String scaleFlag, String[] partyIds, String manageFlag, User user) throws RollbackableException;

    /**
     * 检测是否某个党组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkPartySelected(String roleId, String scaleFlag, String partyId) throws RollbackableException;

    /**
     * 检测维护党组织是否在查询党组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryPartyScale(String roleId, String partyId) throws RollbackableException;

}
