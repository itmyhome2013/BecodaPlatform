package com.becoda.bkms.sys.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;

import java.util.List;
import java.util.Map;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-18
 * Time: 10:55:56
 */
public interface ICodeUCC {
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
    public void deleteCodeSet(String codeSetID) throws RollbackableException;

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
    public void makeStatus(boolean isOpen, String codeSetID) throws RollbackableException;

    public void makeStatus(boolean isOpen, String[] ids) throws RollbackableException;

    /**
     * 根据SetID查询代码集
     * codeSetDao.queryCodeSet
     *
     * @param codeSetID 代码集ID
     * @return cn.ccb.hrdc.sys.pojo.bo.CodeSetBO
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public CodeSetBO queryCodeSet(String codeSetID) throws RollbackableException;

    /**
     * 查询所有代码集
     * codeSetDao.queryCodeSets()
     *
     * @return cn.ccb.hrdc.sys.pojo.bo.CodeSetBO[]
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public List queryCodeSets() throws RollbackableException;

    public String getNewSetId() throws RollbackableException;

    /**
     * 检测是否正在使用
     *
     * @param setIds setIds
     * @return s
     * @throws BkmsException e
     */
    public String checkCodeSetUsing(String[] setIds) throws RollbackableException;


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
    public boolean isRepeatedCodeItemID(String itemID) throws RollbackableException;


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
    public boolean isRepeatedCodeTreeID(String treeID, String setID, String itemID) throws RollbackableException;


    /**
     * 取得层级TreeId，不包括上级的4位TreeID
     *
     * @param setId   code set id
     * @param superId 上级上ID
     * @return treeid
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public String getNewTreeId(String setId, String superId) throws BkmsException;

    /**
     * 修改代码项状态,将代码项的状态改为启用或者禁用
     *
     * @param itemID 代码项主键
     * @param isOpen 是否启用
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void makeCodeItemStatus(String itemID, boolean isOpen) throws RollbackableException;

    /**
     * 查询某个代码集的指定父节点的所有代码项
     *
     * @param codeSetID 代码集ID
     * @param superID   父结点主键值
     * @return List
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public List queryCodeItems(String codeSetID, String superID) throws RollbackableException;

    /**
     * 根据代码集ID删除代码项
     *
     * @param codeSetID 代码集ID
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void deleteCodeItems(String codeSetID) throws RollbackableException;


    /**
     * 根据代码集ID删除代码项
     *
     * @param itemIds 代码项ID
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public void deleteCodeItems(String[] itemIds) throws RollbackableException;

    public String getSuperTreeId(String superId) throws RollbackableException;

    /**
     * 其他限制
     * 检测是否存在其它限制，例如指标集类别代码不允许删除等
     *
     * @param setIds setIds
     * @return boolean
     */
    public boolean isAllowDelete(String[] setIds);

    public void deleteCodeSets(String[] ids) throws RollbackableException;

    public boolean isExistCodeSetName(String name) throws RollbackableException;

    public List queryCodeSetByName(String name);

    public boolean isExistCodeItemName(String setId, String itemName) throws RollbackableException;

    void updateCodeSet(CodeSetBO codeSet) throws RollbackableException;

    public void createBo(Object o) throws RollbackableException;


    public void updateBo(Object o, String pk) throws RollbackableException;

    /**
     * 设置代码项状态
     * 启用，禁用
     * codeSetDao.setStatus
     *
     * @param codeItemId 代码集ID
     * @param isOpen     状态
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void makeItemStatus(boolean isOpen, String codeItemId) throws RollbackableException;

    public void makeItemStatus(boolean isOpen, String[] codeItemIds) throws RollbackableException;

    void updateCodeItem(CodeItemBO ci) throws RollbackableException;

    public Object findBo(Class classN,String id) throws RollbackableException;
    public String  getNewItemId() throws BkmsException;
    
    /**
     * 查询所有代码集 根据state=1
     * @return
     * @throws RollbackableException
     */
    public List queryCodeSetsByState() throws RollbackableException;
    /**
     * 根据条件查询代码集
     * @param params
     * @return
     * @throws RollbackableException
     */
    public List<CodeItemBO> queryCodeByParams(Map<String,String> params) throws RollbackableException;
}
