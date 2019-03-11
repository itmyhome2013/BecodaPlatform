package com.becoda.bkms.east.jhzb.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.dao.InfoSetDAO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-4
 * Time: 11:50:30
 * To change this template use File | Settings | File Templates.
 */
public class InfoSetService {
    private InfoSetDAO infoSetDAO;

    public InfoSetDAO getInfoSetDAO() {
        return infoSetDAO;
    }

    public void setInfoSetDAO(InfoSetDAO infoSetDAO) {
        this.infoSetDAO = infoSetDAO;
    }

    /**
     * 添加信息集
     *
     * @param infoSet 信息及BO
     */
    public void createInfoSet(InfoSetBO infoSet) throws RollbackableException {
        infoSetDAO.createInfoSet(infoSet);
    }

    /**
     * 修改信息集
     *
     * @param infoSet 信息及BO
     */
    public void updateInfoSet(InfoSetBO infoSet) throws RollbackableException {
        infoSetDAO.getHibernateTemplate().saveOrUpdate(infoSet.getSetId(), infoSet);
    }

    /**
     * 得到一个新的指标集名称
     *
     * @param bType 大类  A B C D
     * @return 指标集名称
     */
    public String getNewSetId(String bType, String setProperty) throws RollbackableException {
        return infoSetDAO.getNewSetId(bType, setProperty);
    }

    /**
     * 删除信息集
     * dao.deleteInfoItem
     *
     * @param infoSetID 信息集ID
     */
    public void deleteInfoSet(String infoSetID) throws RollbackableException {
        infoSetDAO.deleteInfoSet(infoSetID);
    }


    /**
     * 设置信息集状态
     *
     * @param setID
     * @param status
     */
    public void makeStatus(String setID, String status) throws RollbackableException {
        try {
            InfoSetBO infoset = (InfoSetBO) infoSetDAO.findBoById(InfoSetBO.class, setID);
            infoset.setSetStatus(status);
            infoSetDAO.updateBo(infoset.getSetId(), infoset);
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 根据信息集ID查询信息集
     *
     * @param infoSetID 信息及ID
     * @return com.becoda.bkms.sys.pojo.bo.InfoSetBO
     */
    public InfoSetBO findInfoSet(String infoSetID) throws RollbackableException {
        return (InfoSetBO) infoSetDAO.findBoById(InfoSetBO.class, infoSetID);
    }

    /**
     * 得到制定小类的指标集列表
     *
     * @param sType 信息集小类
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoSetBO[]
     */
    public InfoSetBO[] queryInfoSets(String sType) throws RollbackableException {
        return infoSetDAO.queryInfoSets(sType, null);
    }
}
