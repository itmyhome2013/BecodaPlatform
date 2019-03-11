package com.becoda.bkms.east.jhzb.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-4
 * Time: 11:51:00
 * To change this template use File | Settings | File Templates.
 */
public interface IInfoSetUCC {
    /**
     * 添加信息集
     *
     * @param infoSet 信息及BO
     */
    public void createInfoSet(InfoSetBO infoSet, String userId) throws BkmsException;

    /**
     * 修改信息集
     *
     * @param infoSet 信息及BO
     */
    public void updateInfoSet(InfoSetBO infoSet) throws BkmsException;

    /**
     * 设置信息集状态
     *
     * @param setIds
     * @param status
     */
    public void makeStatus(String[] setIds, String status) throws BkmsException;

    /**
     * 根据信息集ID查询信息集
     *
     * @param setID 信息及ID
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoSetBO
     */
    public InfoSetBO findInfoSet(String setID) throws BkmsException;

    /**
     * 得到制定小类的指标集列表
     *
     * @param sType 信息集小类
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoSetBO[]
     */
    public List queryRightSetlist(User user, String sType) throws BkmsException;

    /**
     * 批删除信息集
     *
     * @param setIds 信息及ID数组
     */
    public void deleteInfoSets(String[] setIds, String userID) throws BkmsException;

    /**
     * 得到一个新的指标集名称
     *
     * @param bType 大类  A B C D
     * @return 指标集名称
     */
    public String getNewSetId(String bType, String setProperty) throws BkmsException;

    public String checkAllowDelete(String[] setIds) throws BkmsException;
}
