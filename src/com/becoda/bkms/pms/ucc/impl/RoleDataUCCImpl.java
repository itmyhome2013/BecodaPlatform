package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleDataBO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.service.RoleDataService;
import com.becoda.bkms.pms.service.RoleManageService;
import com.becoda.bkms.pms.ucc.IRoleDataUCC;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class RoleDataUCCImpl implements IRoleDataUCC {
    private RoleDataService roleDataService;
    private RoleManageService roleManageService;

    public RoleDataService getRoleDataService() {
        return roleDataService;
    }

    public void setRoleDataService(RoleDataService roleDataService) {
        this.roleDataService = roleDataService;
    }

    public RoleManageService getRoleManageService() {
        return roleManageService;
    }

    public void setRoleManageService(RoleManageService roleManageService) {
        this.roleManageService = roleManageService;
    }

    /**
     * 如果是管理员,需要检测是否减少了权限,因为考虑到权限收回的问题.
     * 处理规则:如果权限增加了,不进行处理,只检测减少减少的部分.
     * 需要收回此管理员下的所有角色,及其此管理员管辖的管理员角色下的角色的操作权限
     * 所以,此处采用反向收回,先检测最后一层管理员的所有角色,然后在收回上一层管理员的所有角色的权限
     * 说明:分级授权是涉及到3级,从效率考虑,此处固化规则,只向下检测2层管理员
     *
     * @param roleDatas
     * @roseuid 447D53090128
     */
    public void updateRoleData(Hashtable roleDatas, String sType, String roleId, String managerFlag, User user) throws RollbackableException {
        //信息项权限改变并且权限变小的信息项列表
        List modified_Read_Data = new ArrayList();
        List modified_Reduced_Data = new ArrayList();
        if (PmsConstants.IS_SYS_MANAGER.equals(managerFlag)) {
            Hashtable oldDatas = roleDataService.queryRoleDataBO(roleId, sType);
            if (oldDatas != null) {
                //先检测从写权限改为读、拒绝的，此时需要通过循环源权限列表，查找所有写权限的对象，来与新权限列表对比
                //如果在新权限列表中能够找到，并且是拒绝的，将此信息记录下来，
                // 如果在新权限列表中没有找到，则代表改为读权限了
                Iterator itr = oldDatas.values().iterator();
                while (itr.hasNext()) {
                    RoleDataBO data = (RoleDataBO) itr.next();
                    if (String.valueOf(PmsConstants.PERMISSION_WRITE).equals(data.getPmsType())) {
                        RoleDataBO newData = (RoleDataBO) roleDatas.get(data.getDataId());
                        if (newData != null) {
                            if (String.valueOf(PmsConstants.PERMISSION_REFUSE).equals(newData.getPmsType())) {
                                modified_Reduced_Data.add(newData);
                            }
                        } else {
                            modified_Read_Data.add(data);
                        }
                    }
                }

                //再检测从读改成拒绝的。此时，因为读权限不进行存储，所以需要从新权限列表中循环，
                // 查找所有拒绝的并且在源权限中是读权限的，也就是在源列表中是空的信息项。
                itr = roleDatas.values().iterator();
                while (itr.hasNext()) {
                    RoleDataBO data = (RoleDataBO) itr.next();
                    if (String.valueOf(PmsConstants.PERMISSION_REFUSE).equals(data.getPmsType())) {
                        RoleDataBO oldData = (RoleDataBO) oldDatas.get(data.getDataId());
                        if (oldData == null) {
                            modified_Reduced_Data.add(data);
                        }
                    }
                }
            }

            //取得
            //先修改原来是写权限，现在改为读的，此时，可将相应的记录删除即可
            String treeId = "";
            RoleInfoBO bo = roleManageService.findRoleInfo(roleId);
            if (bo != null) treeId = bo.getTreeId();
            String sql = "from RoleDataBO r where r.roleId in ";

            sql += "(select r2.roleId from RoleInfoBO r2 where r2.treeId like '" + treeId + "%')";
            String AddCondition = "";
            if (modified_Read_Data.size() > 0) {
                for (int i = 0; i < modified_Read_Data.size(); i++) {
                    if (!"".equals(AddCondition))
                        AddCondition += " or ";
                    AddCondition += "(r.dataId='" + ((RoleDataBO) modified_Read_Data.get(i)).getDataId() + "' and r.pmsType='" + PmsConstants.PERMISSION_WRITE + "')";
                }
            }

            //拒绝权限，先将所有的拒绝list中的信息项删除，然后重新插入拒绝权限
            if (modified_Reduced_Data.size() > 0) {
                for (int i = 0; i < modified_Reduced_Data.size(); i++) {
                    if (!"".equals(AddCondition))
                        AddCondition += " or ";
                    AddCondition += "r.dataId='" + ((RoleDataBO) modified_Reduced_Data.get(i)).getDataId() + "'";
                }
            }
            if (!"".equals(AddCondition)) {
                roleDataService.DeleteDataBySql(sql + " and (" + AddCondition + ")");
            }

            //将拒绝权限插入到下级每个角色的信息项权限中
            if (modified_Reduced_Data.size() > 0) {
                sql = "select r2.roleId from RoleInfoBO r2 where  r2.treeId like '" + treeId + "%'";
                List list = roleDataService.queryRoleListBySql(sql);
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        String tmpRoleId = list.get(i).toString();
                        for (int j = 0; j < modified_Reduced_Data.size(); j++) {
                            RoleDataBO newRD = new RoleDataBO();
                            Tools.copyProperties(newRD, modified_Reduced_Data.get(j));
                            newRD.setRoleId(tmpRoleId);
                            roleDataService.createRoleData(newRD);
                        }
                    }
                }
            }
        }
        roleDataService.updateRoleData(roleDatas, sType, roleId);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改了角色编号为（" + roleId + "）的指标权限。");
    }

    /**
     * @param roleID
     * @param sType
     * @param flag   过滤标记  null||'': 全部  其他值: 启用状态
     * @return Hashtable
     * @roseuid 447D53090148
     */
    public Hashtable queryRoleData(String roleID, String sType, String flag) throws RollbackableException {
        return roleDataService.queryRoleData(roleID, sType, flag);
    }

    /**
     * 得到管理员有权限的指标集
     *
     * @param user
     * @param sType 小类代码
     * @param flag  过滤标记  null||'': 全部  其他值: 启用状态
     * @return list obj = InfoSetPermissionVO
     */
    public Hashtable querySysOperInfoItem(User user, String sType, String flag) throws RollbackableException {
        return roleDataService.querySysOperInfoItem(user, sType, flag);
    }

    /**
     * 得到管理员有权限的指标集
     *
     * @param user
     * @param sType 小类代码
     * @param flag  过滤标记  null||'': 全部  其他值: 启用状态
     * @return list obj = InfoSetPermissionVO
     */
    public List querySysOperInfoSet(User user, String sType, String flag) throws RollbackableException {
        return roleDataService.querySysOperInfoSet(user, sType, flag);
    }
}
