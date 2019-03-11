package com.becoda.bkms.sys.link;

import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.common.exception.BkmsException;

import java.util.Map;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-23
 * Time: 11:05:14
 * To change this template use File | Settings | File Templates.
 */
public class A019LinkHandle extends AbstractLinkHandle {
    protected String getSetId() {
        return "A019";
    }

    protected void add(TableVO table) throws BkmsException {
        String fkValue = getRowItem(table, 0, table.getInfoSet().getSetFk());
        String pkValue = getRowItem(table, 0, table.getInfoSet().getSetPk());
        if ("".equals(fkValue) || "".equals(pkValue)) {
            return;
        }
        String time = getRowItem(table, 0, "A019005"); //开始时间
        //新增，补上一工作经历的结束时间，改变当前字段的值
        String sql = "select * from A019 where ID='" + fkValue + "' order by A019005 desc";
        List list = activePageService.queryForList(sql);
        if (list != null && !list.isEmpty() && list.size() > 1) {
            Map map = (Map) list.get(1);
//            String overTime = (String) map.get("A019010");
            String subId = (String) map.get("SUBID");
            sql = "update A019 set A019010='" + time + "' where SUBID='" + subId + "' ";
            activePageService.executeSql(sql);
        }
    }

    protected void update(TableVO table, String[] oldValue) throws BkmsException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void whenDel(String setId, String pkValue, String fkValue) throws BkmsException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
