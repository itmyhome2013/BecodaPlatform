package com.becoda.bkms.qry.api;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.qry.pojo.bo.QueryItemBO;
import com.becoda.bkms.qry.service.QueryItemService;
import com.becoda.bkms.qry.service.QueryService;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: ye
 * Date: 2015-4-3
 * Time: 14:48:44
 * To change this template use File | Settings | File Templates.
 */
public class QueryAPI implements IQuery {
    private QueryService queryservice;
    private QueryItemService queryItemService;

    public QueryService getQueryservice() {
        return queryservice;
    }

    public void setQueryservice(QueryService queryservice) {
        this.queryservice = queryservice;
    }

    public QueryItemService getQueryItemService() {
        return queryItemService;
    }

    public void setQueryItemService(QueryItemService queryItemService) {
        this.queryItemService = queryItemService;
    }

    public Hashtable findSQL(String qryId) throws BkmsException {
        return queryservice.findSQL(qryId);
    }

    //根据查询ID查询显示项
    public InfoItemBO[] queryHeader(String qryId, String type) throws BkmsException {
        InfoItemBO[] iibos = null;
        try {
            QueryItemBO[] bos = queryItemService.queryQueryItem(qryId);
            if (bos != null) {
                iibos = new InfoItemBO[bos.length + 1];
                for (int i = 0; i < bos.length; i++) {
                    InfoItemBO ibo = SysCacheTool.findInfoItem(bos[i].getSetId(), bos[i].getItemId());
                    iibos[i] = ibo;
                }
                InfoItemBO ibo = SysCacheTool.findInfoItem(type + "001", "ID");
                iibos[bos.length] = ibo;
            }
        } catch (BkmsException e) {
            e.printStackTrace();
        }
        return iibos;
    }

}
