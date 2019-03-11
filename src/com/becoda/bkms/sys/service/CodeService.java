package com.becoda.bkms.sys.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.dao.CodeDAO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-6
 * Time: 14:13:03
 */
public class CodeService {
    private CodeDAO codeDAO;

    public CodeDAO getCodeDAO() {
        return codeDAO;
    }

    public void setCodeDAO(CodeDAO codeDAO) {
        this.codeDAO = codeDAO;
    }

    /**
     * 修改代码集
     * 不允许修改codeSetID
     * codeSetDao.updateCodeSet
     *
     * @param codeSet 代码集BO
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void updateCodeSet(CodeSetBO codeSet) throws RollbackableException {
        codeDAO.updateBo(codeSet, codeSet.getSetId());
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
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void deleteCodeSet(String codeSetID) throws RollbackableException {
        try {
            //删除代码项
            StringBuffer sb = new StringBuffer();
            sb.append("from CodeItemBO cdItem where cdItem.setId ='");
            sb.append(codeSetID).append("'");
            codeDAO.getHibernateTemplate().deleteAll(codeDAO.getHibernateTemplate().find(sb.toString()));
            //删除代码集
            codeDAO.getHibernateTemplate().delete(codeDAO.getHibernateTemplate().get(CodeSetBO.class, codeSetID));
        } catch (Exception e) {
            throw new RollbackableException("在删除信息项权限时出现错误！", e, this.getClass());
        }
    }

    /**
     * 设置代码集状态
     * 启用，禁用
     * codeSetDao.setStatus
     *
     * @param codeSetID 代码集ID
     * @param isOpen    状态
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void makeStatus(boolean isOpen, String codeSetID) throws RollbackableException {
        String status = isOpen ? SysConstants.INFO_STATUS_OPEN : SysConstants.INFO_STATUS_BAN;
        String sName = null;
        try {
            CodeSetBO cSet = (CodeSetBO) codeDAO.getHibernateTemplate().get(CodeSetBO.class, codeSetID);
            sName = cSet.getSetName();
            cSet.setSetStatus(status);
            codeDAO.getHibernateTemplate().update(cSet);
        } catch (Exception e) {
            throw new RollbackableException("修改代码集" + sName + "状态时出现错误！", e, getClass());
        }
    }

    public void makeStatus(boolean isOpen, String[] ids) throws RollbackableException {
        String status = isOpen ? SysConstants.INFO_STATUS_OPEN : SysConstants.INFO_STATUS_BAN;
        String sName = null;
        try {
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                CodeSetBO cSet = (CodeSetBO) codeDAO.getHibernateTemplate().get(CodeSetBO.class, id);
                sName = cSet.getSetName();
                cSet.setSetStatus(status);
                codeDAO.getHibernateTemplate().update(cSet);
            }
        } catch (Exception e) {
            throw new RollbackableException("修改代码集" + sName + "状态时出现错误！", e, getClass());
        }
    }

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
    public void makeItemStatus(boolean isOpen, String codeItemId) throws RollbackableException {
        String status = isOpen ? SysConstants.INFO_STATUS_OPEN : SysConstants.INFO_STATUS_BAN;
        String sName = null;
        try {
            CodeItemBO itemBO = (CodeItemBO) codeDAO.getHibernateTemplate().get(CodeItemBO.class, codeItemId);
            sName = itemBO.getItemName();
            itemBO.setItemStatus(status);
            codeDAO.getHibernateTemplate().update(itemBO);
        } catch (Exception e) {
            throw new RollbackableException("修改代码集" + sName + "状态时出现错误！", e, getClass());
        }
    }

    public void makeItemStatus(boolean isOpen, String[] codeItemIds) throws RollbackableException {
        String status = isOpen ? SysConstants.INFO_STATUS_OPEN : SysConstants.INFO_STATUS_BAN;
        String sName = null;
        try {
            for (int i = 0; i < codeItemIds.length; i++) {
                String id = codeItemIds[i];
                CodeItemBO itemBO = (CodeItemBO) codeDAO.getHibernateTemplate().get(CodeItemBO.class, id);
                sName = itemBO.getItemName();
                itemBO.setItemStatus(status);
                codeDAO.getHibernateTemplate().update(itemBO);
            }
        } catch (Exception e) {
            throw new RollbackableException("修改代码集" + sName + "状态时出现错误！", e, getClass());
        }
    }

    /**
     * 根据SetID查询代码集
     * codeSetDao.queryCodeSet
     *
     * @param codeSetID 代码集ID
     * @return cn.ccb.hrdc.sys.pojo.bo.CodeSetBO
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public CodeSetBO queryCodeSet(String codeSetID) throws RollbackableException {
        return (CodeSetBO) codeDAO.findBo(CodeSetBO.class, codeSetID);
    }

    /**
     * 查询所有代码集
     * codeSetDao.queryCodeSets()
     *
     * @return cn.ccb.hrdc.sys.pojo.bo.CodeSetBO[]
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public List queryCodeSets() throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from CodeSetBO c order by c.setId");
            List list = codeDAO.getHibernateTemplate().find(sb.toString());
            list = (List) Tools.filterNull(ArrayList.class, list);
            int len = list.size();
            List cList = new ArrayList();
            for (int i = 0; i < len; i++) {
                CodeSetBO cSets = new CodeSetBO();
                Object o = list.get(i);
                Tools.copyProperties(cSets, o);
                cList.add(cSets);
            }
            return cList;
        } catch (Exception e) {
            throw new RollbackableException("读取代码集数据错误！", e, getClass());
        }
    }

    public String getNewSetId() throws RollbackableException {
        try {
            //从3000开始计
            List list = codeDAO.getHibernateTemplate().find("select b.setId from CodeSetBO b where b.setId+0>3000");
            if (list != null && list.size() > 0) {
                String newSetId;
                for (int i = 3001; i < 10000; i++) {
                    newSetId = Integer.toString(i);
                    if (list.indexOf(newSetId) == -1) {
                        return newSetId;
                    }
                }
                //达到最大数
                return "";
            } else
                return "3001";
        } catch (Exception e) {
            throw new RollbackableException("读取指标集数据时出错！", e, getClass());
        }
    }

    /**
     * 检测是否正在使用
     *
     * @param setIds setIds
     * @return s
     * @throws RollbackableException e
     */
    public String checkCodeSetUsing(String[] setIds) throws RollbackableException {
        List list;
        String str = "", instr = "";
        if (setIds == null) return "";
        for (int i = 0; i < setIds.length; i++) {
            instr += "'" + setIds[i] + "',";
        }
        //去掉最后一个逗号
        instr = instr.substring(0, instr.length() - 1);

        try {
            String sql = "select c from InfoItemBO i ,CodeSetBO c where i.itemCodeSet=c.setId and c.setId in (" + instr + ")";
            list = codeDAO.getHibernateTemplate().find(sql);
            Hashtable hash = new Hashtable();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    CodeSetBO sbo = (CodeSetBO) list.get(i);
                    if (!hash.containsKey(sbo.getSetId())) {
                        hash.put(sbo.getSetId(), sbo);
                        if ("".equals(str))
                            str += "{" + sbo.getSetName() + "}";
                        else {
                            str += ",{" + sbo.getSetName() + "}";
                        }
                    }
                }
            }
            //如果包含指标类别代码,也不允许删除
            if (instr.indexOf(Constants.INFO_SET_TYPE_CODEID) != -1) {
                if ("".equals(str))
                    str += "{指标集类别代码}";
                else {
                    str += ",{指标集类别代码}";
                }
            }
            return str;
        } catch (Exception e) {
            throw new RollbackableException("检索信息项失败", e, getClass());
        }
    }


    /**
     * 检测编码是否重复
     * 添加时，使用
     * 修改时，不允许修改编码，codeItemID ,所以无需检测
     *
     * @param itemID 代码项ID
     * @return boolean  true 重复 false 不重复
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public boolean isRepeatedCodeItemID(String itemID) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from CodeItemBO cItem where cItem.itemId='");
            sb.append(itemID).append("'");
            List list = codeDAO.getHibernateTemplate().find(sb.toString());
            list = (List) Tools.filterNull(ArrayList.class, list);
            return list.size() > 0;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, this.getClass());
        }
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
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public boolean isRepeatedCodeTreeID(String treeID, String setID, String itemID) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from CodeItemBO cItem where cItem.setId='");
            sb.append(setID);
            sb.append("' and cItem.treeId = '");
            sb.append(treeID).append("'");
            //修改状态
            if ((itemID != null) && (!itemID.equals(""))) {
                sb.append(" and cItem.itemId<>'").append(itemID).append("'");
            }

            List list = codeDAO.getHibernateTemplate().find(sb.toString());
            list = (List) Tools.filterNull(ArrayList.class, list);
            return list.size() > 0;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, this.getClass());
        }
    }


    /**
     * 取得层级TreeId，不包括上级的4位TreeID
     *
     * @param setId   code set id
     * @param superId 上级上ID
     * @return treeid
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public String getNewTreeId(String setId, String superId) throws BkmsException {
        return SequenceGenerator.getTreeId("SYS_CODE_ITEM", "CODE_TREE_ID", superId, 4, 4, "code_set_id='" + setId + "'");
//        try {
//            StringBuffer sb = new StringBuffer();
//            sb.append("select max(cItem.treeId+1) from CodeItemBO cItem where cItem.itemSuper='") //递增5
//                    .append(superId)
//                    .append("'")
//                    .append(" and cItem.setId = '")
//                    .append(setId)
//                    .append("'");
//            List list = codeDAO.getHibernateTemplate().find(sb.toString());
//            if (list == null || list.size() == 0)
//                return "0001";
//            else {
//                String newTreeId = (String) list.get(0);
//                if ("".equals(newTreeId) || newTreeId == null) {
//                    return "0001";
//                }
//                int length = newTreeId.length();
//                //int mod = length % 4;
//                // 如果长度不是4的倍数，则在前边补0
//                if (length < 4) {
//                    for (int i = 0; i < (4 - length); i++) {
//                        newTreeId = "0" + newTreeId;
//                    }
//                } else if (length > 4) {
//                    newTreeId = newTreeId.substring(newTreeId.length() - 4);
//                }
//                return newTreeId;
//            }
//        } catch (Exception e) {
//            //e.printStackTrace();
//            throw new RollbackableException("生成新层次码时，出现读取数据错误！", e, this.getClass());
//        }
    }

    /**
     * 修改代码项状态,将代码项的状态改为启用或者禁用
     *
     * @param itemID 代码项主键
     * @param isOpen 是否启用
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void makeCodeItemStatus(String itemID, boolean isOpen) throws RollbackableException {
        String status = isOpen ? SysConstants.INFO_STATUS_OPEN : SysConstants.INFO_STATUS_BAN;
        try {
            CodeItemBO c = (CodeItemBO) codeDAO.getHibernateTemplate().get(CodeItemBO.class, itemID);
            c.setItemStatus(status);
            codeDAO.getHibernateTemplate().update(c);
        } catch (Exception e) {
            throw new RollbackableException("修改失败", e, this.getClass());
        }
    }

    /**
     * 查询某个代码集的指定父节点的所有代码项
     *
     * @param codeSetID 代码集ID
     * @param superID   父结点主键值
     * @return List
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public List queryCodeItems(String codeSetID, String superID) throws RollbackableException {
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("from CodeItemBO ci ");
            codeSetID = Tools.filterNull(codeSetID);
            superID = Tools.filterNull(superID);
            if (!codeSetID.equals("")) {
                sf.append(" where ci.setId='");
                sf.append(codeSetID).append("'");
                if (!superID.equals("")) {
                    sf.append(" and ci.itemSuper = '");
                    sf.append(superID).append("'");
                }
            }

            sf.append(" order by ci.setId,ci.treeId");
            List list = codeDAO.getHibernateTemplate().find(sf.toString());
            list = (List) Tools.filterNull(ArrayList.class, list);
            int len = list.size();
            List itemList = new ArrayList();//CodeItemBO[] cdItems = new CodeItemBO[len];
            for (int i = 0; i < len; i++) {
                Object o = list.get(i);
                CodeItemBO item = new CodeItemBO();
                Tools.copyProperties(item, o);
                itemList.add(item);
            }
            return itemList;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, this.getClass());
        }
    }

    /**
     * 根据代码集ID删除代码项
     *
     * @param codeSetID 代码集ID
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void deleteCodeItems(String codeSetID) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from CodeItemBO cdItem where cdItem.setId ='");
            sb.append(codeSetID).append("'");
            List list = codeDAO.getHibernateTemplate().find(sb.toString());

            codeDAO.getHibernateTemplate().deleteAll(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("在删除信息项权限时出现错误！", e, this.getClass());
        }
    }


    /**
     * 根据代码集ID删除代码项
     *
     * @param itemIds 代码项ID
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void deleteCodeItems(String[] itemIds) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from CodeItemBO cdItem where cdItem.itemId in (").append(Tools.getInSql(itemIds)).append(")");
            List list = codeDAO.getHibernateTemplate().find(sb.toString());
            codeDAO.getHibernateTemplate().deleteAll(list);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("在删除信息项权限时出现错误！", e, this.getClass());
        }
    }

    public String getSuperTreeId(String superId) throws RollbackableException {
        List list;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select cdItem.treeId from CodeItemBO cdItem where cdItem.itemId ='");
            sb.append(superId).append("'");
            list = codeDAO.getHibernateTemplate().find(sb.toString());
        } catch (Exception e) {
            throw new RollbackableException("查询数据错误！", e, getClass());
        }
        if (list == null || list.size() == 0)
            throw new RollbackableException("没有找到父节点的顺序号！", this.getClass());
        return list.get(0).toString();
    }

    /**
     * 其他限制
     * 检测是否存在其它限制，例如指标集类别代码不允许删除等
     *
     * @param setIds codesetIds
     * @return boolean
     */
    public boolean isAllowDelete(String[] setIds) {
        //国标码和系统码不允许删除
        for (int i = 0; i < setIds.length; i++) {
            int st = Integer.parseInt(setIds[i]);
            if ((st >= 2000 && st < 3000) || st < 1000) {
                return true;
            }
        }
        return false;
    }

    public void deleteCodeSets(String[] ids) throws RollbackableException {
        try {
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                codeDAO.deleteCodeSet(id);
                codeDAO.deleteCodeItems(id);
            }
        } catch (Exception e) {
            throw new RollbackableException("删除代码集错误！", e, getClass());
        }
    }

    public boolean isExistCodeSetName(String name) throws RollbackableException {
        return codeDAO.isExistCodeSetName(name);
    }

    public List queryCodeSetByName(String name) {
        return codeDAO.queryCodeSetByName(name);
    }

    public boolean isExistCodeItemName(String setId, String itemName) throws RollbackableException {
        return codeDAO.isExistCodeItemName(setId, itemName);
    }

    public String getNewItemId() throws BkmsException {
        return SequenceGenerator.getKeyIdByLen("SYS_CODE_ITEM", 6);
    }
    /**
     * 查询所有代码集 根据state=1
     * @return
     * @throws RollbackableException
     */
    public List queryCodeSetsByState() throws RollbackableException{
    	try {
            StringBuffer sb = new StringBuffer();
            sb.append("from CodeSetBO c where c.state=1 order by c.setId");
            List list = codeDAO.getHibernateTemplate().find(sb.toString());
            list = (List) Tools.filterNull(ArrayList.class, list);
            int len = list.size();
            List cList = new ArrayList();
            for (int i = 0; i < len; i++) {
                CodeSetBO cSets = new CodeSetBO();
                Object o = list.get(i);
                Tools.copyProperties(cSets, o);
                cList.add(cSets);
            }
            return cList;
        } catch (Exception e) {
            throw new RollbackableException("读取数据错误！", e, getClass());
        }
    }
    /**
     * 根据条件查询代码集
     * @param params
    		setId		代码集id
    		itemSuper	父id
    		itemAbbr 	缩写-层级
    		itemSpell	拼写-分组
     * @return
     * @throws RollbackableException
     */
    public List<CodeItemBO> queryCodeByParams(Map<String,String> params) throws RollbackableException{
    	return codeDAO.queryCodeByParams(params);
    }
}
