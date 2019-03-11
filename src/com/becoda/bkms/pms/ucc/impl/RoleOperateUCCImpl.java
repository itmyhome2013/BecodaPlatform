package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.pojo.bo.RoleOperateBO;
import com.becoda.bkms.pms.service.RoleManageService;
import com.becoda.bkms.pms.service.RoleOperateService;
import com.becoda.bkms.pms.ucc.IRoleOperateUCC;
import com.becoda.bkms.util.HrmsLog;

import java.util.Hashtable;
import java.util.List;

public class RoleOperateUCCImpl implements IRoleOperateUCC {
    private RoleOperateService roleOperateService;
    private RoleManageService roleManageService;

    public RoleOperateService getRoleOperateService() {
        return roleOperateService;
    }

    public void setRoleOperateService(RoleOperateService roleOperateService) {
        this.roleOperateService = roleOperateService;
    }

    public RoleManageService getRoleManageService() {
        return roleManageService;
    }

    public void setRoleManageService(RoleManageService roleManageService) {
        this.roleManageService = roleManageService;
    }

    /**
     * 存储操作权限
     * 如果是管理员,需要检测是否减少了权限,因为考虑到权限收回的问题.
     * 处理规则:如果权限增加了,不进行处理,只检测减少减少的部分.
     * 需要收回此管理员下的所有角色,及其此管理员管辖的管理员角色下的角色的操作权限
     * 所以,此处采用反向收回,先检测最后一层管理员的所有角色,然后在收回上一层管理员的所有角色的权限
     * 说明:分级授权是涉及到3级,从效率考虑,此处固化规则,只向下检测2层管理员
     *
     * @param roleOpers
     * @roseuid 447D53090203
     */
    public void updateRoleOperate(String roleId, Hashtable roleOpers, String managerFlag, User user) throws RollbackableException {
        //获得减少的权限
        //List reducedOperateList = new ArrayList();
        String reducedOpers = "";
        if (PmsConstants.IS_SYS_MANAGER.equals(managerFlag)) {
            List list = roleOperateService.queryRoleOperate(roleId);
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    String operId = ((RoleOperateBO) list.get(i)).getOperateId();
                    if (!roleOpers.containsKey(operId)) {
                        if (reducedOpers.equals(""))
                            reducedOpers = "'" + operId + "'";
                        else
                            reducedOpers += ",'" + operId + "'";
                    }
                }
            //取得
            if (!"".equals(reducedOpers)) {
                String treeId = "";
                RoleInfoBO bo = roleManageService.findRoleInfo(roleId);
                if (bo != null) treeId = bo.getTreeId();
                String sql = "from RoleOperateBO r where r.roleId in ";
                sql += "(select r2.roleId from RoleInfoBO r2 where r2.treeId like '" + treeId + "%')";

                sql += " and r.operateId in (" + reducedOpers + ")";
                roleOperateService.deleteDataBySql(sql);
            }
        }
        roleOperateService.updateRoleOperate(roleId, roleOpers);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改了角色编号为（" + roleId + "）的菜单权限信息。");
    }

    /**
     * @param roleID
     * @return com.becoda.bkms.pms.pojo.bo.OperateBO[]
     * @roseuid 447D53090222
     */
    public List queryRoleOperate(String roleID) throws RollbackableException {
        return roleOperateService.queryRoleOperate(roleID);
    }
}
