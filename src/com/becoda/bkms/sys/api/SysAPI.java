package com.becoda.bkms.sys.api;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.dao.CodeDAO;
import com.becoda.bkms.sys.dao.InfoItemDAO;
import com.becoda.bkms.sys.dao.InfoSetDAO;
import com.becoda.bkms.sys.pojo.bo.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-8
 * Time: 9:44:08
 * To change this template use File | Settings | File Templates.
 */
public class SysAPI {
    private InfoSetDAO infoSetDAO;
    private InfoItemDAO infoItemDAO;
    private CodeDAO codeDAO;

    public CodeDAO getCodeDAO() {
        return codeDAO;
    }

    public void setCodeDAO(CodeDAO codeDAO) {
        this.codeDAO = codeDAO;
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

    public InfoSetBO findInfoSet(String infoSetId) throws BkmsException {
        return (InfoSetBO) infoSetDAO.getHibernateTemplate().load(InfoSetBO.class, infoSetId);

    }

    public CodeSetBO findCodeSet(String codeSetID) throws BkmsException {
        return (CodeSetBO) codeDAO.findBo(CodeSetBO.class, codeSetID);
    }

    public CodeItemBO findCodeItem(String codeItemID) throws BkmsException {
        return (CodeItemBO) codeDAO.findBo(CodeItemBO.class, codeItemID);
    }

    public List queryCodeItemsBySuperID(String codeSetID, String superID) throws BkmsException {
        return codeDAO.queryCodeItems(codeSetID, superID);
    }

    public InfoSetBO[] queryCascadeInfoSet(String setId) throws BkmsException {
        return infoSetDAO.queryCascadeInfoSet(setId);
    }

    public InfoItemBO findInfoItem(String setId, String infoItemId) throws BkmsException {

        return infoItemDAO.findInfoItem(setId, infoItemId);

    }

    /**
     * 查询指标集下代码项(不含系统控制指标)
     *
     * @param infoSetId
     * @param status
     * @return
     * @throws BkmsException
     */
    public InfoItemBO[] queryInfoItem(String infoSetId, String status) throws BkmsException {
        return infoItemDAO.queryInfoItems(infoSetId, status, null, null, false);
    }

    /**
     * 查询指标集下代码项 (含系统控制指标)
     *
     * @param infoSetId
     * @param status
     */
    public InfoItemBO[] queryAllInfoItem(String infoSetId, String status) throws BkmsException {
        try {
            return infoItemDAO.queryInfoItems(infoSetId, status, null, null, true);
        } catch (Exception e) {
            throw new BkmsException("查询失败", e, this.getClass());
        }
    }

    //*********************************以下Cache使用**************************
    public InfoItemBO[] queryAllInfoItem() throws BkmsException {
        try {
            return infoItemDAO.queryInfoItems(null, null, null, null, true);
        } catch (Exception e) {
            throw new BkmsException("查询失败", e, this.getClass());
        }
    }

    public InfoSetBO[] queryAllInfoSet() throws BkmsException {
        try {
            return infoSetDAO.queryInfoSets(null, null);
        } catch (Exception e) {
            throw new BkmsException("查询失败", e, this.getClass());
        }
    }

    public CodeItemBO[] queryAllCodeItem() throws BkmsException {
        try {
            List list = codeDAO.queryCodeItems("", "");
            CodeItemBO[] citems = null;
            if (list != null) {
                citems = new CodeItemBO[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    citems[i] = (CodeItemBO) list.get(i);
                }
            }
            return citems;
        } catch (Exception e) {
            throw new BkmsException("查询失败", e, this.getClass());
        }
    }

    public CodeSetBO[] queryAllCodeSet() throws BkmsException {
        try {
            List list = codeDAO.queryCodeSets();
            CodeSetBO[] s = new CodeSetBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                s[i] = (CodeSetBO) list.get(i);
            }
            return s;
        } catch (Exception e) {
            throw new BkmsException("查询失败", e, this.getClass());
        }

    }

    //public

    public InfoSetBO[] queryInfoSetByBigType(String type) throws RollbackableException {
        return infoSetDAO.queryInfoSetByBigType(type);
    }

    public InfoSetBO[] queryInfoSetByBigType(String type, String rsType) throws RollbackableException {
        return infoSetDAO.queryInfoSetByBigType(type, rsType);
    }

    public InfoSetBO[] queryInfoSetBySmallType(String stype) throws RollbackableException {
        return infoSetDAO.queryInfoSets(stype, null);
    }

    public void addOperLog(OperLogBO logLog) throws RollbackableException {
        try {
            codeDAO.createBo(logLog);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    public Object findObject(Object obj) throws RollbackableException {
        return infoSetDAO.findByExample(obj).get(0);
    }
}
