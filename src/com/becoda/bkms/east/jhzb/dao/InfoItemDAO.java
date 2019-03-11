package com.becoda.bkms.east.jhzb.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.util.Tools;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-4
 * Time: 11:22:55
 * To change this template use File | Settings | File Templates.
 */
public class InfoItemDAO extends GenericDAO {
    /**
     * 检测ItemID是否重复
     *
     * @param setId  信息集ID
     * @param itemId 代码项ID
     * @return boolean
     */
    public boolean checkInfoItemID(String setId, String itemId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from InfoItemBO iItem where iItem.setId='");
            sb.append(setId);
            sb.append("' and iItem.itemId = '");
            sb.append(itemId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            return list.size() > 0;
        } catch (Exception e) {
            throw new RollbackableException("读取数据错误！", e, InfoItemDAO.class);
        }
    }

    /**
     * 添加信息项
     * 县添加信息项信息
     * 然后再将字段添加到相应数据表中
     *
     * @param infoItem 信息项BO
     */
    public void createInfoItem(InfoItemBO infoItem) throws RollbackableException {
        try {
            if ("".equals(infoItem.getItemDataLength()) || infoItem.getItemDataLength() == null)
                infoItem.setItemDataLength("100");
            hibernateTemplate.save(infoItem);

            StringBuffer sb = new StringBuffer();
            sb.append("alter table ").append(infoItem.getSetId()).append(" add ( ");
            String field = infoItem.getItemId();
            int length;
            if (SysConstants.INFO_ITEM_DATA_TYPE_REMARK.equals(infoItem.getItemDataType())) //如果是备注型，则长度存储4000
                length = 4000;
            else {  ////如果长度小于100,则数据库存200,否则按2倍长度存储
                if (Integer.parseInt(infoItem.getItemDataLength()) < 100)
                    length = 200;
                else
                    length = Integer.parseInt(infoItem.getItemDataLength()) * 2;
            }
            sb.append(field).append(" VARCHAR2(").append(length).append(") )");
            jdbcTemplate.execute(sb.toString());
        } catch (Exception e) {
            throw new RollbackableException("创建数据表字段失败！", e, InfoItemDAO.class);
        }
    }

    /**
     * 修改信息项
     * 只修改信息项信息，不修改数据表信息
     *
     * @param infoItem
     */
    public void updateInfoItem(InfoItemBO infoItem) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            if ("".equals(infoItem.getItemDataLength()) || infoItem.getItemDataLength() == null)
                infoItem.setItemDataLength("100");
            sb.append("from InfoItemBO iItem where iItem.setId='");
            sb.append(infoItem.getSetId());
            sb.append("' and iItem.itemId = '");
            sb.append(infoItem.getItemId()).append("'");
            List list = hibernateTemplate.find(sb.toString());
            InfoItemBO sitem = (InfoItemBO) list.get(0);
            //源数据长度
            String oldLength = sitem.getItemDataLength();
            if (oldLength == null || "".equals(oldLength)) oldLength = "100";
            String newLength = infoItem.getItemDataLength();
            if (newLength == null || "".equals(newLength)) newLength = "100";

            int length = 100;
            boolean modifyFlag = false;// 修改数据库标志
            if (sitem.getItemDataType().equals(infoItem.getItemDataType()) &&
                    !SysConstants.INFO_ITEM_DATA_TYPE_REMARK.equals(infoItem.getItemDataType())) {
                if (!newLength.equals(oldLength)) {
                    modifyFlag = true;
                    if (Integer.parseInt(newLength) < 100) {
                        length = 200;
                    } else {
                        length = Integer.parseInt(newLength) * 2;
                    }
                }
            } else {
                if (SysConstants.INFO_ITEM_DATA_TYPE_REMARK.equals(infoItem.getItemDataType())) {
                    modifyFlag = true;
                    length = 4000;
                } else {
                    if (!newLength.equals(oldLength)) {
                        modifyFlag = true;
                        if (Integer.parseInt(newLength) < 100) {
                            length = 200;
                        } else {
                            length = Integer.parseInt(newLength) * 2;
                        }
                    }
                }
            }
            Tools.copyProperties(sitem, infoItem);
//            hibernateTemplate.update(infoItem);
            if (modifyFlag) {
                sb.delete(0, sb.length());
                sb.append("alter table ").append(infoItem.getSetId()).append(" MODIFY ( ");
                String field = infoItem.getItemId();
                sb.append(field).append(" VARCHAR2(").append(length).append(") )");
                jdbcTemplate.execute(sb.toString());
            }
        } catch (Exception e) {
            throw new RollbackableException("修改指标项失败.", e, this.getClass());
        }
    }

    /**
     * 删除信息项
     * 删除数据表的相应字段
     *
     * @param setId  信息集ID
     * @param itemId 信息项ID
     */
    private void deleteInfoItem(String setId, String itemId) throws RollbackableException {
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("from InfoItemBO iItem where iItem.setId='");
            sf.append(setId);
            sf.append("' and iItem.itemId = '");
            sf.append(itemId).append("'");
            InfoItemBO p = (InfoItemBO) hibernateTemplate.find(sf.toString()).get(0);
            hibernateTemplate.delete(p);
            //删除数据表字段
            sf = new StringBuffer();
            sf.append("alter table ").append(setId).append(" drop ( ")
                    .append(itemId).append(")");
            jdbcTemplate.execute(sf.toString());
        } catch (Exception e) {
            throw new RollbackableException("删除信息项失败！", e, InfoItemBO.class);
        }
    }

    /**
     * 批删除信息项
     * 批删除数据表的相应字段
     *
     * @param infoItemIds 信息项BO数组
     */
    public void deleteInfoItems(String setId, String[] infoItemIds) throws RollbackableException {
        try {
            for (int i = 0; i < infoItemIds.length; i++) {
                deleteInfoItem(setId, infoItemIds[i]);
            }
        } catch (Exception e) {
            throw new RollbackableException("删除信息项失败！", e, InfoItemBO.class);
        }
    }

    /**
     * 批删除信息项
     * 批删除数据表的相应字段
     *
     * @param setId 信息项BO数组
     */
    public void deleteInfoItems(String setId) throws RollbackableException {
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("from InfoItemBO iItem where iItem.setId='");
            sf.append(setId).append("'");
            List list = hibernateTemplate.find(sf.toString());
            hibernateTemplate.deleteAll(list);
        } catch (Exception e) {
            throw new RollbackableException("删除信息项失败！", e, InfoItemBO.class);
        }
    }

    /**
     * 修改信息项状态,将其改为启用或者禁用
     *
     * @param setId  信息集ID
     * @param itemId 信息项ID
     * @param status 状态  1 启用 0 禁用
     */
    public void setStatus(String setId, String itemId, String status) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from InfoItemBO iItem where iItem.setId='");
            sb.append(setId);
            sb.append("' and iItem.itemId = '");
            sb.append(itemId).append("'");
            InfoItemBO iItem = (InfoItemBO) hibernateTemplate.find(sb.toString()).get(0);
            iItem.setItemStatus(status);
            hibernateTemplate.update(iItem);
        } catch (Exception e) {
            throw new RollbackableException("状态修改失败！", e, InfoItemDAO.class);
        }
    }

    /**
     * 根据信息项查询信息项
     *
     * @param setId  信息集ID
     * @param itemId 信息项ID
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoItemBO
     */
    public InfoItemBO findInfoItem(String setId, String itemId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from InfoItemBO b where b.setId='");
            sb.append(setId);
            sb.append("' and b.itemId = '");
            sb.append(itemId).append("'");
            List list = hibernateTemplate.find(sb.toString());
            if (list == null || list.size() == 0) return null;
            else {
                InfoItemBO p = (InfoItemBO) list.get(0);
                return p;
            }
        } catch (Exception e) {
            throw new RollbackableException("查询失败！", e, InfoItemDAO.class);
        }
    }

    /**
     * 根据小类查询指标项
     *
     * @param sType
     * @param status 过滤标记      status =null or "" 则查询所有,否则查启用的指标集和项
     * @return
     * @throws RollbackableException
     */
    public InfoItemBO[] queryInfoItems(String sType, String status) throws RollbackableException {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select p from InfoSetBO s,InfoItemBO p where s.setId=p.setId and p.editProp<>'").append(SysConstants.INFO_ITEM_EDIT_PROP_HIDE).append("'");
            if (sType != null && !"".equals(sType))
                sql.append(" and s.set_sType='").append(sType).append("'");
            if (status != null && !"".equals(status))
                sql.append(" and s.setStatus ='").append(SysConstants.INFO_STATUS_OPEN).append("'")
                        .append(" and p.itemStatus='").append(SysConstants.INFO_STATUS_OPEN).append("'");

            sql.append(" order by p.setId,p.itemSequence+0,p.itemId");
            List list = hibernateTemplate.find(sql.toString());
            if (list == null || list.size() == 0) return null;
            else return (InfoItemBO[]) list.toArray(new InfoItemBO[0]);
        } catch (Exception e) {
            throw new RollbackableException("查询指标项失败！", e, InfoItemDAO.class);
        }
    }

    /**
     * 根据信息集查询信息项
     *
     * @param getCtl 是否查询控制字段
     * @param setId  信息集ID
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoItemBO[]
     */
    public InfoItemBO[] queryInfoItems(String setId, String status, String dataType, String property, boolean getCtl) throws RollbackableException {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("from InfoItemBO p");
            StringBuffer tmp = new StringBuffer();
            if (!getCtl)
                tmp.append(" and p.editProp<>'").append(SysConstants.INFO_ITEM_EDIT_PROP_HIDE).append("'");

            if (setId != null && !"".equals(setId.trim())) {
                tmp.append(" and p.setId='").append(setId).append("' ");
            }
            //添加数据类型条件
            if (dataType != null && !"".equals(dataType.trim())) {
                tmp.append(" and p.itemDataType='").append(dataType).append("' ");
            }
            //添加属性条件
            if (property != null && !"".equals(property.trim())) {
                tmp.append(" and p.itemProperty='").append(property).append("' ");
            }
            //添加状态条件
            if (status != null && !"".equals(status.trim())) {
                tmp.append(" and p.itemStatus='").append(status).append("' ");
            }
            if (tmp.length() > 0) {
                tmp.delete(0, 4);
                tmp.insert(0, " where ");
            }
            sql.append(tmp.toString());
            sql.append(" order by p.setId,p.itemSequence+0,p.itemId");
            List list = hibernateTemplate.find(sql.toString());
            if (list == null || list.size() == 0) return null;
            else return (InfoItemBO[]) list.toArray(new InfoItemBO[0]);

        } catch (Exception e) {
            throw new RollbackableException("查询失败！", e, InfoItemDAO.class);
        }
    }

    /**
     * 发布信息项
     *
     * @param itemId 信息项ID
     */
    public void issueInfoItem(String setId, String itemId) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from InfoItemBO iItem where iItem.setId='");
            sb.append(setId);
            sb.append("' and iItem.itemId = '");
            sb.append(itemId).append("'");
            InfoItemBO p = (InfoItemBO) hibernateTemplate.find(sb.toString()).get(0);
            p.setItemIssue("1");
        } catch (Exception e) {
            throw new RollbackableException("信息项发布失败！", e, InfoItemDAO.class);
        }
    }

    /**
     * 得到一个新的指标项名称
     *
     * @param setId
     * @return 指标集名称
     */
    public String getNewItemId(String setId, String property) throws RollbackableException {
        try {
            String newItemId = "";
            int ini = 200;
            int end = 699;
            if (SysConstants.INFO_ITEM_PROPERTY_SYSTEM.equals(property)) {
                ini = 700;
                end = 999;
            }
            if (SysConstants.INFO_ITEM_PROPERTY_GB.equals(property)) {
                ini = 0;
                end = 199;
            }
            List list = hibernateTemplate.find("select b.itemId from InfoItemBO b where b.setId ='" + setId + "'");
            if (list != null && list.size() > 0) {
                for (int i = ini; i <= end; i++) {
                    newItemId = "00000" + Integer.toString(i);
                    newItemId = setId + newItemId.substring(newItemId.length() - 3);
                    if (list.indexOf(newItemId) == -1) {
                        return newItemId;
                    }
                }
                //达到最大数
                return "";
            } else {
                newItemId = "00000" + Integer.toString(ini);
                newItemId = setId + newItemId.substring(newItemId.length() - 3);
                return newItemId;
            }
        } catch (Exception e) {
            throw new RollbackableException("读取指标集数据时出错！", e, getClass());
        }
    }
}
