package com.becoda.bkms.qry.pojo.bo;


public class QueryItemBO implements java.io.Serializable {
    private String qryId;
    private String qryItemId;
    private String setId;
    private String itemId;

    /**
     * 0-asc 1-desc
     */
    private String orderFlag = null;
    private int orderSeq = 0;
    private int showId = 0;
    private int showHistory = 0;
    /**
     * 1-默认查询显示项目; 0-普通查询显示项目
     */
    private int defaultFlag;

    /**
     * A-人员 B-机构  与defaultFlag=1时一起用.用来标记人员或机构默认查询显示项目.
     */
    private String defaultType;

    public String getQryId() {
        return qryId;
    }

    public void setQryId(String qryId) {
        this.qryId = qryId;
    }

    public String getQryItemId() {
        return qryItemId;
    }

    public void setQryItemId(String qryItemId) {
        this.qryItemId = qryItemId;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public int getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        this.orderSeq = orderSeq;
    }

    public int getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(int defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getShowHistory() {
        return showHistory;
    }

    public void setShowHistory(int showHistory) {
        this.showHistory = showHistory;
    }
}
