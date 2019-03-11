package com.becoda.bkms.sys.pojo.bo;

import java.io.Serializable;

public class CodeItemBO implements Serializable {
    private String itemId;// 代码的ID     唯一编码   代码集编码+用户编码
    private String setId;//代码集的ID
    private String itemSuper;//父节点的codeSetItemID
    private String treeId;// 存储层次关系，此编码自动生成，当用户选择了上级节点后，本级TreeID=上级treeID+本级四位编码
    private String itemName;//代码的名称
    private String itemAbbr;//代码的简称
    private String itemSpell;//代码的简拼
    private String itemStatus;//代码的状态   1：启用  0：禁用
    private String itemCompareValue;//代码的大小比较
    private String userItemId;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }


    public String getItemSuper() {
        return itemSuper;
    }

    public void setItemSuper(String itemSuper) {
        this.itemSuper = itemSuper;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAbbr() {
        return itemAbbr;
    }

    public void setItemAbbr(String itemAbbr) {
        this.itemAbbr = itemAbbr;
    }

    public String getItemSpell() {
        return itemSpell;
    }

    public void setItemSpell(String itemSpell) {
        this.itemSpell = itemSpell;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemCompareValue() {
        return itemCompareValue;
    }

    public void setItemCompareValue(String itemCompareValue) {
        this.itemCompareValue = itemCompareValue;
    }

    public String getUserItemId() {
        return userItemId;
    }

    public void setUserItemId(String userItemId) {
        this.userItemId = userItemId;
    }
}
