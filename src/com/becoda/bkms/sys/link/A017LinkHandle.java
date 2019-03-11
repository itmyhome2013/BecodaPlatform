package com.becoda.bkms.sys.link;

import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.cache.SysCacheTool;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-18
 * Time: 15:54:14
 * To change this template use File | Settings | File Templates.
 */
public class A017LinkHandle extends AbstractLinkHandle {
    protected String getSetId() {
        return "A017";
    }

    public void add(TableVO table) throws BkmsException {
        String fkValue = getRowItem(table, 0, table.getInfoSet().getSetFk());
        String pkValue = getRowItem(table, 0, table.getInfoSet().getSetPk());
        if ("".equals(fkValue) || "".equals(pkValue)) {
            return;
        }
    }

    public void update(TableVO table, String[] oldValue) throws BkmsException {
        String fkValue = getRowItem(table, 0, table.getInfoSet().getSetFk());
        String pkValue = getRowItem(table, 0, table.getInfoSet().getSetPk());
        if ("".equals(fkValue) || "".equals(pkValue)) {
            return;
        }
    }

    public void whenDel(String setId, String pkValue, String fkValue) throws BkmsException {

    }
}
