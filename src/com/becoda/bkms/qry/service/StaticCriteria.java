package com.becoda.bkms.qry.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.Tools;

import java.util.Map;
import java.util.TreeMap;

/**
 * User: kangdw
 * Date: 2015-7-11
 * Time: 15:34:23
 */
public class StaticCriteria extends Criteria {

    protected Map baseStaticTableHash = new TreeMap();
    protected String baseStaticSql;
    protected String allValue = null;

    public String allvalue(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        allValue = new StringBuffer().append(" group by ").append(field).toString();
        groupHash.put(groupId, allValue);
        return allValue;
    }

    /**
     * 本方法将如果得到的super.getCondition没有包含group by,表示是个普通条件.用basesql 与getCondition的值拼
     * 如果包含groupby 则basesql 直接加 condition
     *
     * @return
     * @throws BkmsException
     */
    public String getCondition() throws BkmsException {
        String tmp = Tools.filterNull(super.getCondition());
        StringBuffer rt = new StringBuffer();
        if (tmp.indexOf("group by") != -1) { //如果包含group by
            rt.append(Tools.filterNull(this.baseStaticSql)).append(" ").append(allValue);
        } else {
            if (Tools.filterNull(baseStaticSql).trim().equals("") || Tools.filterNull(tmp).trim().equals(""))
                rt.append(Tools.filterNull(baseStaticSql)).append(Tools.filterNull(tmp));
            else
                rt.append("(").append(Tools.filterNull(baseStaticSql)).append(") and (").append(tmp).append(") ");
        }

        return rt.toString();
    }

    public void newSubStatic() throws BkmsException {
        super.clear();
        allValue = "";
        super.queryTableHash.putAll(this.baseStaticTableHash);//将基本条件涉及到的表放在groupHash里.供getJoinTable和JoinCondition用
    }

    public void newBaseStatic() throws BkmsException {
        baseStaticSql = "";
        baseStaticTableHash.clear();
    }

    public void endBaseStatic() throws BkmsException {
        this.baseStaticTableHash.putAll(super.queryTableHash);
        this.baseStaticSql = super.addDefaultCondition(super.getCondition());
    }
}
