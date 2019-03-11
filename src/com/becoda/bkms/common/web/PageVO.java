package com.becoda.bkms.common.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.qry.util.QryTools;

import java.util.Hashtable;

/**
 * User: kangdw
 * Date: 2015-06-09
 * Time: 17:46:07
 */
public class PageVO {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    private int totalPage;
    private int totalRecord;
    private int currentPage;
    private int pageSize;
    private String orderPart;
    private String orderField;

    String getOrderField() {
        return orderField;
    }

    void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    private String isASC;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        if (pageSize == 0)
            pageSize = 50;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderPart() {
        return orderPart;
    }

    public void setOrderPart(String orderPart) {
        this.orderPart = orderPart;
    }

    String getASC() {
        return isASC;
    }

    void setASC(String ASC) {
        isASC = ASC;
    }

    //end add by sunmh 2015-01-10

    //add by kangdw
    private String qrySql;
    private String showField;
    private String hash; //高级查询的序列化对象

    public String getQrySql() {
        return qrySql;
    }

    public void setQrySql(String qrySql) {
        this.qrySql = qrySql;
    }

    public String getShowField() {
        return showField;
    }

    public void setShowField(String showField) {
        this.showField = showField;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Hashtable getHashSQL() throws BkmsException {
        if (hash == null || hash.trim().equals("")) {
            return null;
        }
        return QryTools.deSer(hash);
    }
    //end
}
