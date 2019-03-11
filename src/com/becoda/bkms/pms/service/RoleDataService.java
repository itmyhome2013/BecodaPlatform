package com.becoda.bkms.pms.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.dao.RoleDataDAO;
import com.becoda.bkms.pms.pojo.bo.RoleDataBO;
import com.becoda.bkms.pms.pojo.vo.InfoItemPermissionVO;
import com.becoda.bkms.pms.pojo.vo.InfoSetPermissionVO;
import com.becoda.bkms.sys.dao.InfoItemDAO;
import com.becoda.bkms.sys.dao.InfoSetDAO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 角色信息项权限数据服务<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class RoleDataService {
    private RoleDataDAO roleDataDAO;
    private InfoSetDAO infoSetDAO;
    private InfoItemDAO infoItemDAO;

    public RoleDataDAO getRoleDataDAO() {
        return roleDataDAO;
    }

    public void setRoleDataDAO(RoleDataDAO roleDataDAO) {
        this.roleDataDAO = roleDataDAO;
    }

    public InfoSetDAO getInfoSetDAO() {
        return infoSetDAO;
    }

    public void setInfoSetDAO(InfoSetDAO infoSetDAO) {
        this.infoSetDAO = infoSetDAO;
    }

    public InfoItemDAO getInfoItemDAO() {
        return infoItemDAO;
    }

    public void setInfoItemDAO(InfoItemDAO infoItemDAO) {
        this.infoItemDAO = infoItemDAO;
    }

    /**
     * 修改角色权限
     * 说明：先删除、在添加。
     * 注：只读权限不进行存储，只存储拒绝和维护权限两类
     * 考虑：权限收回的实现,可以在删除之前，先比较与原来的权限的减少情况，如果有减少，则
     * 用递归方法，将其下级的管理员和用户的权限删除。
     * 只考虑权限收回，不考虑权限收回的撤销操作。
     * 根据此管理员所创建的角色，按递归的方法检测。
     *
     * @param roleDatas 角色信息项权限数组，滤掉只读权限的信息，只读信息项不进行存储
     */
    public void updateRoleData(Hashtable roleDatas, String sType, String roleId) throws RollbackableException {
        //roleDatas不为null
        roleDataDAO.deleteRoleData(roleId, sType);
        roleDataDAO.createRoleDatas(roleDatas);
    }

    public void createRoleData(RoleDataBO rdata) throws RollbackableException {
        roleDataDAO.createBo(rdata);
    }

    /**
     * 查询制定类别范围内的所有信息项权限
     *
     * @param roleID 角色ID
     * @param sType  小类代码
     * @param flag   过滤标记  null||'': 全部  其他值: 启用状态
     * @return Hashtable
     * @roseuid 447489C402EA
     */
    public Hashtable queryRoleData(String roleID, String sType, String flag) throws RollbackableException {
        return roleDataDAO.queryRoleData(roleID, sType, flag);
    }

    public Hashtable queryRoleDataBO(String roleID, String sType) throws RollbackableException {
        return roleDataDAO.queryRoleDataBO(roleID, sType);
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
        InfoItemBO[] items = infoItemDAO.queryInfoItems(sType, flag);
        Hashtable hash = user.getPmsInfoItem();
        String setId = "";
        List list = null;
        if (items == null) return hash;
        for (int i = 0; i < items.length; i++) {
            InfoItemPermissionVO infoitem = new InfoItemPermissionVO();
            infoitem.setItemId(items[i].getItemId());
            infoitem.setItemName(items[i].getItemName());
            Object obj = hash.get(items[i].getItemId());
            if (obj != null) {
                String pms = obj.toString();
                infoitem.setPermission(pms);
            } else
                try {
                    infoitem.setPermission(StaticVariable.get(PmsConstants.FIELDDEFAULT_RULE));
                } catch (BkmsException e) {
                    throw new RollbackableException("获取变量错误", getClass());
                }
            if (!setId.equals(items[i].getSetId())) {
                list = new ArrayList();
                setId = items[i].getSetId();
                hash.put(setId, list);
            }
            list.add(infoitem);
        }
        return hash;

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
        //得到有权限的指标集
        Hashtable hash = user.getPmsInfoSet();
        //得到所有的指标集
        InfoSetBO[] infosets = infoSetDAO.queryInfoSets(sType, flag);
        List list = new ArrayList();
        for (int i = 0; i < infosets.length; i++) {
            Object obj = hash.get(infosets[i].getSetId());
            if (obj != null) {
                String pms = obj.toString();
                InfoSetPermissionVO infoset = new InfoSetPermissionVO();
                infoset.setSetId(infosets[i].getSetId());
                infoset.setSetName(infosets[i].getSetName());
                infoset.setPermission(pms);
                list.add(infoset);
            } else {
                InfoSetPermissionVO infoset = new InfoSetPermissionVO();
                infoset.setSetId(infosets[i].getSetId());
                infoset.setSetName(infosets[i].getSetName());
                try {
                    infoset.setPermission(StaticVariable.get(PmsConstants.TABLEDEFAULT_RULE));
                } catch (BkmsException e) {
                    throw new RollbackableException("获取变量错误", getClass());
                }
                list.add(infoset);
            }
        }
        return list;
    }

    /**
     * 按信息项或信息集ID和角色 删除信息项权限
     *
     * @param roleId  角色ID
     * @param dataIds 信息项或信息集ID
     */
    public void deleteRoleDataByDataId(String roleId, String[] dataIds) throws RollbackableException {
        for (int i = 0; i < dataIds.length; i++) {
            roleDataDAO.deleteRoleDataByDataId(roleId, dataIds[i]);
        }
    }

    public void DeleteDataBySql(String sql) throws RollbackableException {
        roleDataDAO.DeleteDataBySql(sql);
    }

    public List queryRoleListBySql(String sql) throws RollbackableException {
        return roleDataDAO.queryRoleListBySql(sql);
    }
}
