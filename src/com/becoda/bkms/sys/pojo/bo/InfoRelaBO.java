package com.becoda.bkms.sys.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-3
 * Time: 21:51:30
 * To change this template use File | Settings | File Templates.
 */
public class InfoRelaBO {
    private String relaId;
    private String mainInfoType;
    private String relaInfoType;
    private String relaTable;
    private String relaField;

    public String getRelaId() {
        return relaId;
    }

    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }

    public String getMainInfoType() {
        return mainInfoType;
    }

    public void setMainInfoType(String mainInfoType) {
        this.mainInfoType = mainInfoType;
    }

    public String getRelaInfoType() {
        return relaInfoType;
    }

    public void setRelaInfoType(String relaInfoType) {
        this.relaInfoType = relaInfoType;
    }

    public String getRelaTable() {
        return relaTable;
    }

    public void setRelaTable(String relaTable) {
        this.relaTable = relaTable;
    }

    public String getRelaField() {
        return relaField;
    }

    public void setRelaField(String relaField) {
        this.relaField = relaField;
    }
}
