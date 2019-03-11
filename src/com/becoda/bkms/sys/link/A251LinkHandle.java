package com.becoda.bkms.sys.link;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.util.PersonTool;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.Tools;

public class A251LinkHandle extends AbstractLinkHandle {
    protected String getSetId() {
        return "A251";
    }

    protected void add(TableVO table) throws BkmsException {
        String pkValue = getRowItem(table, 0, table.getInfoSet().getSetPk());
        String fkValue = getRowItem(table, 0, table.getInfoSet().getSetFk());
        String isMost = getRowItem(table, 0, "A251206");
        String postCall = getRowItem(table, 0, "A251200");
        String zhJbie = PersonTool.getZhcJb(postCall);
        String zhSpacil = PersonTool.getZhcZhy(postCall);
        String sql = "update A251 set A251207='" + Tools.filterNull(zhJbie) + "',A251201='" + Tools.filterNull(zhSpacil) + "' where SUBID = '" + pkValue + "'";
        activePageService.executeSql(sql);
        if ("3031000255".equals(isMost)) {
            sql = "update A001 set A001240 = '" + Tools.filterNull(postCall) + "' where ID = '" + fkValue + "'";
            activePageService.executeSql(sql);
        } else if ("3031000256".equals(isMost)) {
            sql = "update A001 set A001241 = '" + Tools.filterNull(postCall) + "' where ID = '" + pkValue + "'";
            activePageService.executeSql(sql);
        }
    }

    protected void update(TableVO table, String[] oldValue) throws BkmsException {
        add(table);
    }

    public void whenDel(String setId, String pkValue, String fkValue) throws BkmsException {
        String sql = "select A251206 from A251 where SUBID = '" + pkValue + "'";
        String isMost = activePageService.getActivePageDAO().queryForString(sql);
        if ("3031000255".equals(isMost)) {
            sql = "update A001 set A001240 = '' where ID = '" + fkValue + "'";
            activePageService.executeSql(sql);
        } else if ("3031000256".equals(isMost)) {
            sql = "update A001 set A001241 = '' where ID = '" + fkValue + "'";
            activePageService.executeSql(sql);
        }
    }
}
