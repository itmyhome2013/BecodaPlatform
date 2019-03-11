package com.becoda.bkms.east.jhzb.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.dao.InfoItemDAO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-4
 * Time: 11:50:07
 * To change this template use File | Settings | File Templates.
 */
public class InfoItemService {
    private InfoItemDAO infoItemDAO;

    public InfoItemDAO getInfoItemDAO() {
        return infoItemDAO;
    }

    public void setInfoItemDAO(InfoItemDAO infoItemDAO) {
        this.infoItemDAO = infoItemDAO;
    }

    /**
     * 添加信息项，ID是否存在重复
     *
     * @param infoItem 信息项BO
     */
    public void createInfoItem(InfoItemBO infoItem) throws RollbackableException {
        infoItemDAO.createInfoItem(infoItem);
    }

    /**
     * 修改信息项，先检测信息项名称是否存在重复
     *
     * @param infoItem 信息项BO
     */
    public void updateInfoItem(InfoItemBO infoItem) throws RollbackableException {
        infoItemDAO.updateInfoItem(infoItem);
    }

    /**
     * 删除指定指标集下的所有指标项，不做删除数据表字段的操作，删除数据表时，自动删除
     * dao.deleteInfoItems
     */
    public void deleteInfoItems(String setId) throws RollbackableException {
        infoItemDAO.deleteInfoItems(setId);
    }

    /**
     * 删除信息项
     * dao.deleteInfoItems
     *
     * @param infoItemIds 信息项BO数组
     */
    public void deleteInfoItems(String setId, String[] infoItemIds) throws RollbackableException {
        infoItemDAO.deleteInfoItems(setId, infoItemIds);
    }


    /**
     * 根据SetID查询信息项
     *
     * @param setId 信息项BO数组
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoItemBO[]
     */
    public InfoItemBO[] queryInfoItems(String setId) throws RollbackableException {
        return infoItemDAO.queryInfoItems(setId, "", "", "", false);
    }

    public InfoItemBO findInfoItem(String setId, String itemId) throws RollbackableException {
        return infoItemDAO.findInfoItem(setId, itemId);
    }

    /**
     * 修改信息项状态,将其改为启用或者禁用
     *
     * @param setId  信息集ID
     * @param itemId 信息项ID
     * @param status 状态
     */
    public void makeStatus(String setId, String itemId, String status) throws RollbackableException {
        infoItemDAO.setStatus(setId, itemId, status);
    }

    /**
     * 得到一个新的指标项名称
     *
     * @param setId 大类
     * @return 指标集名称
     */
    public String getNewItemId(String setId, String property) throws RollbackableException {
        return infoItemDAO.getNewItemId(setId, property);
    }
}
