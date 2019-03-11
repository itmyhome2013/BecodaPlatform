package com.becoda.bkms.qry.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.dao.QueryItemDAO;
import com.becoda.bkms.qry.pojo.bo.QueryItemBO;
import com.becoda.bkms.util.SequenceGenerator;

public class QueryItemService {
    private QueryItemDAO itemdao;

    /**
     * 保存一组查询显示项.在本方法里循环调用dao的createBO方法.
     *
     * @param items 待保存itembo的数组
     * @param qryId
     * @return java.lang.String[] 保存后的id数组
     * @roseuid 446EBF9A0182
     */
    public String[] createQueryItem(QueryItemBO[] items, String qryId) throws BkmsException {
        if (qryId == null || items == null || items.length <= 0)
            throw new RollbackableException("无法保存显示项目", null, getClass());
        try {
            this.deleteQueryItem(qryId);
            String[] ids = SequenceGenerator.getKeyId("qry_query_item", items.length);
            for (int i = 0; i < items.length; i++) {
                items[i].setQryItemId(ids[i]);
                items[i].setQryId(qryId);
                itemdao.createBo(items[i]);
            }
            return ids;
        } catch (Exception e) {
            throw new RollbackableException("保存显示项目出错", e, getClass());
        }
    }

    /**
     * 根据qryid一次删除所有显示项定义.
     *
     * @param qryId
     * @roseuid 446EBFB70210
     */
    public void deleteQueryItem(String qryId) throws BkmsException {
        itemdao.deleteQueryItem(qryId);
    }

    /**
     * 根据qryId查询显示项
     *
     * @param qryId
     * @return com.becoda.bkms.qry.pojo.bo.QueryItemBO[]
     * @roseuid 446EBFC202E8
     */
    public QueryItemBO[] queryQueryItem(String qryId) throws BkmsException {
        return itemdao.queryQueryItem(qryId);
    }

    /**
     * 根据setType查询系统默认的显示项
     *
     * @param setType 为大写的A|B|C|D
     * @return com.becoda.bkms.qry.pojo.bo.QueryItemBO[]
     * @roseuid 446EBFD10005
     */
    public QueryItemBO[] queryDefaultItem(String setType) throws BkmsException {
        return itemdao.queryDefaultItem(setType);
    }


    public QueryItemDAO getItemdao() {
        return itemdao;
    }

    public void setItemdao(QueryItemDAO itemdao) {
        this.itemdao = itemdao;
    }
}
