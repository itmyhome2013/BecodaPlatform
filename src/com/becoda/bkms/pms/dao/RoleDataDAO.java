
package com.becoda.bkms.pms.dao;


import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleDataBO;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.util.Tools;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


/**
 * 角色信息项权限数据访问
 * author:lirg
 * 2015-5-24
 */
public class RoleDataDAO extends GenericDAO {
    /**
     * 功能描述：删除指定角色、类别范围内的所有信息项权限
     *
     * @param roleID 角色ID
     * @param sType  小类代码
     * @roseuid 4474617B02A2
     */
    public void deleteRoleData(String roleID, String sType) throws RollbackableException {
        try {
            //删除信息集
            StringBuffer sb = new StringBuffer();
            sb.append("select rd from RoleDataBO rd,InfoSetBO infoset where rd.dataId = infoset.setId and rd.roleId='")
                    .append(roleID)
                    .append("' and infoset.set_sType='")
                    .append(sType)
                    .append("' ");
            List list = hibernateTemplate.find(sb.toString());
            super.deleteBo(list);
            //删除信息项
            sb.delete(0, sb.length());
            sb.append("select rd from RoleDataBO rd,InfoItemBO infoitem, InfoSetBO infoset where rd.roleId='")
                    .append(roleID)
                    .append("' and rd.dataId = infoitem.itemId and infoitem.setId = infoset.setId and infoset.set_sType='")
                    .append(sType)
                    .append("' ");
            list = hibernateTemplate.find(sb.toString());
            if (list != null && list.size() > 0)
                super.deleteBo(list);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("在删除信息项权限时出现错误！", e, RoleDataDAO.class);
        }
    }

    /**
     * 按信息项或信息集ID和角色 删除信息项权限
     *
     * @param roleID 角色ID
     * @param dataId 信息项或信息集ID
     */
    public void deleteRoleDataByDataId(String roleID, String dataId) throws RollbackableException {
        try {
            //删除信息集
            StringBuffer sb = new StringBuffer();
            sb.append("select rd from RoleDataBO rd where  rd.roleId='")
                    .append(roleID).append("'");
            if (dataId.length() == PmsConstants.INFOSET_LENGTH)  //如果是指标集，则连同指标项一同删除

                sb.append(" and rd.dataId like '").append(dataId).append("%'");
            else               //如果是指标项，则只删除指标项
                sb.append(" and rd.dataId ='").append(dataId).append("'");

            List list = hibernateTemplate.find(sb.toString());
            if (list != null && list.size() > 0)
                super.deleteBo(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("在删除信息项权限时出现错误！", e, RoleDataDAO.class);
        }

    }

    /**
     * 批插入信息项权限
     *
     * @param rDataBOs
     * @throws RollbackableException
     */
    public void createRoleDatas(Hashtable rDataBOs) throws RollbackableException {
        try {
            //RoleDataBO[] rds = new RoleDataBO[rDataBOs.length];
            //Tools.copyArrayObject(RoleDataBO.class,rDataBOs,rds);
            Iterator it = rDataBOs.values().iterator();
            while (it.hasNext()) {
                Object o = it.next();
                super.createBo(o);
            }
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("数据存储失败", e, RoleDataDAO.class);
        }
    }


    /**
     * 查询制定类别范围内的所有信息项权限
     *
     * @param roleID 角色ID ，如果是sys角色，直接读取信息集和信息项表
     * @param sType  小类代码
     * @param flag   过滤标记  null||'': 全部  其他值: 启用状态
     * @return Hashtable
     * @roseuid 447462990109
     */
    public Hashtable queryRoleData(String roleID, String sType, String flag) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            //指标集
            sb.append("select rd.dataId,rd.pmsType from RoleDataBO rd,InfoSetBO st  where rd.roleId='")
                    .append(roleID)
                    .append("' and rd.dataId =st.setId and st.set_sType='")
                    .append(sType)
                    .append("'");
            if (!"".equals(Tools.filterNull(flag)))
                sb.append(" and st.setStatus ='").append(SysConstants.INFO_STATUS_OPEN).append("'");
            sb.append(" order by rd.dataId");
            Hashtable hash = null;
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty()) {
                int len = list.size();
                hash = new Hashtable();
                for (int i = 0; i < len; i++) {
                    Object[] obj = (Object[]) list.get(i);
                    hash.put(obj[0], obj[1]);
                }
            }
            //指标项
            sb.delete(0, sb.length());
            sb.append("select rd.dataId,rd.pmsType from RoleDataBO rd,InfoItemBO ft,InfoSetBO st  where rd.roleId='")
                    .append(roleID)
                    .append("' and rd.dataId =ft.itemId and  ft.setId = st.setId and st.set_sType='")
                    .append(sType).append("'");
            if (!"".equals(Tools.filterNull(flag)))
                sb.append(" and st.setStatus ='").append(SysConstants.INFO_STATUS_OPEN).append("'")
                        .append(" and ft.itemStatus ='").append(SysConstants.INFO_STATUS_OPEN).append("'");
            sb.append("  order by rd.dataId");
            list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty()) {
                int len = list.size();
                if (hash == null) hash = new Hashtable();
                for (int i = 0; i < len; i++) {
                    Object[] obj = (Object[]) list.get(i);
                    hash.put(obj[0], obj[1]);
                }
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败！", e, RoleDataDAO.class);
        }

    }


    /**
     * 查询制定类别范围内的所有信息项权限
     *
     * @param roleID 角色ID ，如果是sys角色，直接读取信息集和信息项表
     * @param sType  小类代码
     * @return Hashtable
     * @roseuid 447462990109
     */
    public Hashtable queryRoleDataBO(String roleID, String sType) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            //指标集
            sb.append("select rd from RoleDataBO rd,InfoSetBO st  where rd.roleId='")
                    .append(roleID)
                    .append("' and rd.dataId =st.setId and st.set_sType='")
                    .append(sType)
                    .append("' order by rd.dataId");
            Hashtable hash = null;
            List list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty()) {
                int len = list.size();
                hash = new Hashtable();
                for (int i = 0; i < len; i++) {
                    RoleDataBO rd = (RoleDataBO) list.get(i);
                    hash.put(rd.getDataId(), rd);
                }
            }
            //指标项
            sb.delete(0, sb.length());
            sb.append("select rd from RoleDataBO rd,InfoItemBO ft,InfoSetBO st  where rd.roleId='")
                    .append(roleID)
                    .append("' and rd.dataId =ft.itemId and  ft.setId = st.setId and st.set_sType='")
                    .append(sType)
                    .append("'  order by rd.dataId");
            list = hibernateTemplate.find(sb.toString());
            if (list != null && !list.isEmpty()) {
                int len = list.size();
                if (hash == null) hash = new Hashtable();
                for (int i = 0; i < len; i++) {
                    RoleDataBO rd = (RoleDataBO) list.get(i);
                    hash.put(rd.getDataId(), rd);
                }
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据失败！", e, RoleDataDAO.class);
        }

    }

    /**
     * 按照SQL语句删除信息项权限
     * 收回下级用户的权限
     */
    public void DeleteDataBySql(String sql) throws RollbackableException {
        try {
            List list = hibernateTemplate.find(sql);
            super.deleteBo(list);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除下级信息项权限时出错，存储失败！", e, RoleOperateDAO.class);
        }

    }

    /**
     * 根据SQL查询角色列表  obj = RoleId
     *
     * @param sql
     * @return
     * @throws RollbackableException
     */
    public List queryRoleListBySql(String sql) throws RollbackableException {
        try {
            return hibernateTemplate.find(sql);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取角色数据失败！", e, RoleDataDAO.class);
        }
    }
}
