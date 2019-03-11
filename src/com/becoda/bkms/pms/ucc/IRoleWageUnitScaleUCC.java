package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;

import java.util.List;

/**
 * 角色薪酬机构权限
 * author:lirg
 * 2015-5-24
 */
public interface IRoleWageUnitScaleUCC {
    public void createRoleWageUnitScale(RoleOrgScaleBO roleOrgScale, User user, String managerFlag) throws RollbackableException;

    public List queryRoleWageUnitScale(String roleID, String scaleFlag, String pmsType) throws RollbackableException;

    public void deleteRoleWageUnitScale(String roleID, String scaleFlag, String pmsType, String[] orgIds, String managerFlag, User user) throws RollbackableException;

    public boolean checkWageUnitSelected(String roleId, String scaleFlag, String pmsType, String orgId) throws RollbackableException;

    public boolean checkInHaveWageUnitScale(String roleId, String scaleFlag, String orgId) throws RollbackableException;

    public boolean checkInQueryWageUnitScale(String roleId, String orgId) throws RollbackableException;

}
