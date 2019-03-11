package com.becoda.bkms.org.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-18
 * Time: 9:12:25
 * To change this template use File | Settings | File Templates.
 */
public class Org {
    private String orgId;
    private String name;
    private String superId;
    private String treeId;
    private String orgCode;
    private String nature;
    private String orgLevel;//机构层级
    private String orgHeader;//机构全称
    private String orgCancel;//是否撤销 B001730 added by yxm on 2015-3-19
    private String orgSort;//机构排序  B001715 added by yxm on 2015-4-9

    public String getOrgHeader() {
        return orgHeader;
    }

    public void setOrgHeader(String orgHeader) {
        this.orgHeader = orgHeader;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCancel() {
        return orgCancel;
    }

    public void setOrgCancel(String orgCancel) {
        this.orgCancel = orgCancel;
    }

    public String getOrgSort() {
        return orgSort;
    }

    public void setOrgSort(String orgSort) {
        this.orgSort = orgSort;
    }
}
