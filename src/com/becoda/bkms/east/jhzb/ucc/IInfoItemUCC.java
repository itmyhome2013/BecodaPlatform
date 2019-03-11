package com.becoda.bkms.east.jhzb.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-4
 * Time: 11:51:34
 * To change this template use File | Settings | File Templates.
 */
public interface IInfoItemUCC {

    /**
     * 添加信息项，ID是否存在重复
     *
     * @param infoItem 信息项BO
     */
    public void createInfoItem(InfoItemBO infoItem, String userId) throws BkmsException;

    /**
     * 修改信息项，先检测信息项名称是否存在重复
     *
     * @param infoItem 信息项BO
     */
    public void updateInfoItem(InfoItemBO infoItem) throws BkmsException;

    /**
     * 删除信息项
     * dao.deleteInfoItem
     * @param infoItemID 信息项ID
     */
    // public void deleteInfoItem(String infoItemID);

    /**
     * 删除信息项
     * dao.deleteInfoItems
     *
     * @param itemIds 信息项BO数组
     */
    public void deleteInfoItems(String setId, String[] itemIds, String userId) throws BkmsException;

    /**
     * 查询信息项
     *
     * @param infoItemId 信息项ID
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoItemBO
     */
    public InfoItemBO findInfoItem(String setId, String infoItemId) throws BkmsException;

    /**
     * 根据SetID查询信息项
     * @param setId 信息项BO数组
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoItemBO[]
     */
    //public InfoItemBO[] queryInfoItems(String setId) throws BkmsException;

    /**
     * 修改信息项状态,将其改为启用或者禁用
     *
     * @param itemIds 信息项ID
     * @param status  状态
     */
    public void makeStatus(String setId, String[] itemIds, String status) throws BkmsException;

    /**
     * 发布信息项
     *
     * @param infoItemID 信息项ID
     */
    public void issueInfoItem(String infoItemID);

    /**
     * 根据SetID查询信息项
     *
     * @param setId 信息项BO数组
     * @param user  user对象
     */
//    public List getCellVOList(User user, String setId) throws BkmsException;
    public List queryRightItemlist(User user, String setId) throws BkmsException;

    public String getNewItemId(String setId, String property) throws BkmsException;

    public String checkAllowDelete(String[] itemIds) throws BkmsException;
    public List findSheetInfoByName(String name)  throws BkmsException;
    public void addOrUpdateInfo(String setId,String infoId,String field,String values)  throws BkmsException;
}
