package com.becoda.bkms.sys.pojo.vo;

import com.becoda.bkms.sys.SysConstants;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-28
 * Time: 13:21:39
 * To change this template use File | Settings | File Templates.
 */
public class CodeItemVO {
    private String itemId;// 代码的ID     唯一编码   代码集编码+用户编码
    private String setId;//代码集的ID
    private String userItemId;//用户编码
    private String itemSuper;//父节点的codeSetItemID
    private String treeId;// 存储层次关系，此编码自动生成，当用户选择了上级节点后，本级TreeID=上级treeID+本级四位编码
    private String itemName;//代码的名称
    private String itemAbbr;//代码的简称
    private String itemSpell;//代码的简拼
    private String itemStatus = SysConstants.INFO_STATUS_OPEN;//代码的状态   1：启用  0：禁用
    private String seqId; //层次码
    private String itemCompareValue;//代码的大小比较
    private String setName;


    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

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

    public String getUserItemId() {
        return userItemId;
    }

    public void setUserItemId(String userItemId) {
        this.userItemId = userItemId;
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
}
