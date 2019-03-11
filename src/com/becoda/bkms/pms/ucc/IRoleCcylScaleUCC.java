package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.pojo.bo.RoleCcylScaleBO;

import java.util.List;

/**
 * 角色团组织权限
 * author:lirg
 * 2015-5-24
 */
public interface IRoleCcylScaleUCC {

    /**
     * 修改团组织范围权限
     *
     * @param roleCcylScale 角色团组织权限数组
     * @roseuid 4479E4C20206
     */
    public void createRoleCcylScale(RoleCcylScaleBO roleCcylScale, User user) throws RollbackableException;

    /**
     * 根据标示符查询维护范围或者查询范围
     *
     * @param roleID    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     * @roseuid 4479E4CC0250
     */
    public List queryRoleCcylScale(String roleID, String scaleFlag) throws RollbackableException;

    public void deleteRoleCcylScale(String roleID, String scaleFlag, String[] ccylIds, String manageFlag, User user) throws RollbackableException;

    /**
     * 检测是否某个团组织机构是否在已选
     *
     * @param roleId    角色ID
     * @param scaleFlag 1 维护范围 0 查询范围
     */
//    public boolean checkCcylSelected(String roleId, String scaleFlag, String ccylId) throws RollbackableException;

    /**
     * 检测维护团组织是否在查询团组织范围内
     *
     * @param roleId 角色ID
     * @return true 在，false 不在
     */
    public boolean checkInQueryCcylScale(String roleId, String ccylId) throws RollbackableException;

}
