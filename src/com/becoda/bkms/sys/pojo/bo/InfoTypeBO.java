package com.becoda.bkms.sys.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-3
 * Time: 21:46:18
 * To change this template use File | Settings | File Templates.
 */
public class InfoTypeBO {
    /**
     * 指标集类别
     */
    private String infoType;
    /**
     * 指标集类别说明
     */
    private String typeName;
    /**
     * 该分类主表
     */
    private String mainTable;
    /**
     * 主表主键
     */
    private String mainKey;
    /**
     * 子表（多记录表）主键名称
     */
    private String subKey;

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMainTable() {
        return mainTable;
    }

    public void setMainTable(String mainTable) {
        this.mainTable = mainTable;
    }

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

}
