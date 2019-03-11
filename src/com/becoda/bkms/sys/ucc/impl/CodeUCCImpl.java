package com.becoda.bkms.sys.ucc.impl;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
//import com.becoda.bkms.kq.KqConstants;
//import com.becoda.bkms.kq.service.KqInfoMapAPI;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.sys.service.CodeService;
import com.becoda.bkms.sys.ucc.ICodeUCC;

import java.util.List;
import java.util.Map;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-18
 * Time: 11:03:04
 */
public class CodeUCCImpl implements ICodeUCC {
    private CodeService codeService;
//    private KqInfoMapAPI kqInfoMapAPI;
//
//    public KqInfoMapAPI getKqInfoMapAPI() {
//        return kqInfoMapAPI;
//    }
//
//    public void setKqInfoMapAPI(KqInfoMapAPI kqInfoMapAPI) {
//        this.kqInfoMapAPI = kqInfoMapAPI;
//    }

    public CodeService getCodeService() {
        return codeService;
    }

    public void updateBo(Object o, String pk) throws RollbackableException {
        codeService.getCodeDAO().updateBo(o, pk);
    }

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    public void updateCodeSet(CodeSetBO codeSet) throws RollbackableException {
        codeService.updateCodeSet(codeSet);
    }

    /**
     * 删除代码集
     * 先检测代码集是否已经被使用，如果没有被使用，则可以删除
     * if(codeSetDao.checkUsing)
     * {
     * codeSetDao.deleteCodeSet
     * codeItemDao.deleteCodeItems(setID)
     * }
     *
     * @param codeSetID 代码集ID
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void deleteCodeSet(String codeSetID) throws RollbackableException {
        codeService.deleteCodeSet(codeSetID);
    }

    /**
     * 设置代码集状态
     * 启用，禁用
     * codeSetDao.setStatus
     *
     * @param codeSetID 代码集ID
     * @param isOpen    状态
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void makeStatus(boolean isOpen, String codeSetID) throws RollbackableException {
        codeService.makeStatus(isOpen, codeSetID);
    }

    public void makeStatus(boolean isOpen, String[] ids) throws RollbackableException {
        codeService.makeStatus(isOpen, ids);
    }

    /**
     * 根据SetID查询代码集
     * codeSetDao.queryCodeSet
     *
     * @param codeSetID 代码集ID
     * @return cn.ccb.hrdc.sys.pojo.bo.CodeSetBO
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public CodeSetBO queryCodeSet(String codeSetID) throws RollbackableException {
        return codeService.queryCodeSet(codeSetID);
    }

    /**
     * 查询所有代码集
     * codeSetDao.queryCodeSets()
     *
     * @return cn.ccb.hrdc.sys.pojo.bo.CodeSetBO[]
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public List queryCodeSets() throws RollbackableException {
        return codeService.queryCodeSets();
    }

    public String getNewSetId() throws RollbackableException {
        return codeService.getNewSetId();
    }

    /**
     * 检测是否正在使用
     *
     * @param setIds setIds
     * @return s
     * @throws BkmsException e
     */
    public String checkCodeSetUsing(String[] setIds) throws RollbackableException {
        return codeService.checkCodeSetUsing(setIds);
    }


    /**
     * 检测编码是否重复
     * 添加时，使用
     * 修改时，不允许修改编码，codeItemID ,所以无需检测
     *
     * @param itemID 代码项ID
     * @return boolean  true 重复 false 不重复
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public boolean isRepeatedCodeItemID(String itemID) throws RollbackableException {
        return codeService.isRepeatedCodeItemID(itemID);
    }


    /**
     * 检测TreeID是否重复
     * 添加时，只需传入setID,treeID
     * 修改时，需传入setID,treeID,itemID
     *
     * @param treeID 代码项TreeID
     * @param setID  代码集ID
     * @param itemID 主键值 代码集ID+用户代码ID - 代码项唯一ID ,如果修改状态,itemID非空
     * @return boolean
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public boolean isRepeatedCodeTreeID(String treeID, String setID, String itemID) throws RollbackableException {
        return codeService.isRepeatedCodeTreeID(treeID, setID, itemID);
    }


    /**
     * 取得层级TreeId，不包括上级的4位TreeID
     *
     * @param setId   code set id
     * @param superId 上级上ID
     * @return treeid
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public String getNewTreeId(String setId, String superId) throws BkmsException {
        return codeService.getNewTreeId(setId, superId);
    }

    /**
     * 修改代码项状态,将代码项的状态改为启用或者禁用
     *
     * @param itemID 代码项主键
     * @param isOpen 是否启用
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void makeCodeItemStatus(String itemID, boolean isOpen) throws RollbackableException {
        codeService.makeCodeItemStatus(itemID, isOpen);
    }

    /**
     * 查询某个代码集的指定父节点的所有代码项
     *
     * @param codeSetID 代码集ID
     * @param superID   父结点主键值
     * @return List
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public List queryCodeItems(String codeSetID, String superID) throws RollbackableException {
        return codeService.queryCodeItems(codeSetID, superID);
    }

    /**
     * 根据代码集ID删除代码项
     *
     * @param codeSetID 代码集ID
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void deleteCodeItems(String codeSetID) throws RollbackableException {
        codeService.deleteCodeItems(codeSetID);
    }


    /**
     * 根据代码集ID删除代码项
     *
     * @param itemIds 代码项ID
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void deleteCodeItems(String[] itemIds) throws RollbackableException {
        if (itemIds == null || itemIds.length == 0) return;
        codeService.deleteCodeItems(itemIds);
    }

    public String getSuperTreeId(String superId) throws RollbackableException {
        return codeService.getSuperTreeId(superId);
    }

    /**
     * 其他限制
     * 检测是否存在其它限制，例如指标集类别代码不允许删除等
     *
     * @param setIds setIds
     * @return boolean
     */
    public boolean isAllowDelete(String[] setIds) {
        return codeService.isAllowDelete(setIds);
    }

    public void deleteCodeSets(String[] ids) throws RollbackableException {
        codeService.deleteCodeSets(ids);
    }

    public boolean isExistCodeSetName(String name) throws RollbackableException {
        return codeService.isExistCodeSetName(name);
    }

    public List queryCodeSetByName(String name) {
        return codeService.queryCodeSetByName(name);
    }

    public boolean isExistCodeItemName(String setId, String itemName) throws RollbackableException {
        return codeService.isExistCodeItemName(setId, itemName);
    }

    public void createBo(Object codeSet) throws RollbackableException {
        codeService.getCodeDAO().createBo(codeSet);
        //同步考勤影射表
        if (CodeItemBO.class.isInstance(codeSet)) {
            CodeItemBO bo = (CodeItemBO) codeSet;
//            if (KqConstants.KQ_TYPE_CODESET.equals(bo.getSetId())) {
//                kqInfoMapAPI.addKqType(bo);
//            }
        }
    }

    public void makeItemStatus(boolean isOpen, String codeItemId) throws RollbackableException {
        codeService.makeItemStatus(isOpen, codeItemId);
    }

    public void makeItemStatus(boolean isOpen, String[] codeItemIds) throws RollbackableException {
        codeService.makeItemStatus(isOpen, codeItemIds);
    }

    public void updateCodeItem(CodeItemBO ci) throws RollbackableException {
        //同步考勤影射表
//        if (KqConstants.KQ_TYPE_CODESET.equals(ci.getSetId())) {
//            kqInfoMapAPI.updatekqType(ci);
//        }
        //end
        CodeItemBO oldItem = (CodeItemBO) getCodeService().getCodeDAO().findBo(CodeItemBO.class, ci.getItemId());
        //如果修改了TREEID，则更新相关的treeId
        if (!oldItem.getTreeId().equals(ci.getTreeId())) {
            String newTreeId = ci.getTreeId().trim();
            String oldTreeId = oldItem.getTreeId().trim();
            String sql = "update sys_code_item set code_tree_id = '" + newTreeId + "'||substr(code_tree_id," + (newTreeId.length() + 1) + ") where code_tree_id like '" + oldTreeId + "%' and code_set_id = '" + ci.getSetId() + "'";
            codeService.getCodeDAO().getJdbcTemplate().execute(sql);
        }

        updateBo(ci, ci.getItemId());

    }

    public Object findBo(Class classz, String id) throws RollbackableException {
        return codeService.getCodeDAO().findBo(classz, id);
    }

    public String getNewItemId() throws BkmsException {
        return codeService.getNewItemId();
    }
    /**
     * 查询所有代码集 根据state=1
     * @return
     * @throws RollbackableException
     */
    public List queryCodeSetsByState() throws RollbackableException{
    	return codeService.queryCodeSetsByState();
    }
    public List<CodeItemBO> queryCodeByParams(Map<String,String> params) throws RollbackableException{
    	return codeService.queryCodeByParams(params);
    }
}
