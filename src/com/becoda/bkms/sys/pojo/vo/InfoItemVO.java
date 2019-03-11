package com.becoda.bkms.sys.pojo.vo;

import com.becoda.bkms.sys.SysConstants;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-3
 * Time: 15:26:17
 * To change this template use File | Settings | File Templates.
 */
public class InfoItemVO {
    //指标编号
    private String itemId;
    //所属的指标集编号
    private String setId;
    //指标名称
    private String itemName;
    // 指标描述
    private String itemDesc;
    // 数据类型
    private String itemDataType;
    //指标长度
    private String itemDataLength;
    //指标精度
    private String itemPrecision;
    //最大值
    private String itemMax;
    // 最小值
    private String itemMin;
    //代码型指标所对应的代码集
    private String itemCodeSet;
    /**
     * 如果某个字段的权限是只读的，页面不校验是否必填
     * 1 必填  0 选填
     */
    private String itemNotNull = SysConstants.INFO_ITEM_IS_NULL;
    //显示顺序
    private String itemSequence;
    /**
     * 状态
     * 1 启用
     * 0 禁用
     */
    private String itemStatus = SysConstants.INFO_STATUS_OPEN;
    /**
     * 系统内部标识
     * 1、系统（隐含控制）指标
     * 2、系统（非隐含控制）指标
     * 3、系统指标
     * 4、用户定义指标
     * 系统指标(<=3)用户不可以删除。指标（>=3）用户可以修改名称
     */
    private String itemProperty = SysConstants.INFO_ITEM_PROPERTY_EXTEND;
    //缺省值
    private String itemDefaultValue;
    //指标对应的函数
    private String itemFormula;
    /*指标的版本
       1:发布
       0:未发布
    */
    private String itemIssue;
    // 指标在页面的显示形式是否是下拉框
    private String itemIsSelect;
    /**
     * 是否在自助模块显示 1 是 0 否
     */
    private String itemSelf;
    /*
      字段所关联的子集，字段的值取自linkset表的主键
    */
    private String itemLinkSet;
    /*
      弹出页面的链接
    */
    private String itemLinkJsp;
    //调用 的js方法
    private String jsScript;
    //调用 翻译方法
    private String interpret;
    //编辑属性
    private String editProp = SysConstants.INFO_ITEM_EDIT_PROP_READONLY;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemDataType() {
        return itemDataType;
    }

    public void setItemDataType(String itemDataType) {
        this.itemDataType = itemDataType;
    }

    public String getItemDataLength() {
        return itemDataLength;
    }

    public void setItemDataLength(String itemDataLength) {
        this.itemDataLength = itemDataLength;
    }

    public String getItemPrecision() {
        return itemPrecision;
    }

    public void setItemPrecision(String itemPrecision) {
        this.itemPrecision = itemPrecision;
    }

    public String getItemMax() {
        return itemMax;
    }

    public void setItemMax(String itemMax) {
        this.itemMax = itemMax;
    }

    public String getItemMin() {
        return itemMin;
    }

    public void setItemMin(String itemMin) {
        this.itemMin = itemMin;
    }

    public String getItemCodeSet() {
        return itemCodeSet;
    }

    public void setItemCodeSet(String itemCodeSet) {
        this.itemCodeSet = itemCodeSet;
    }

    public String getItemNotNull() {
        return itemNotNull;
    }

    public void setItemNotNull(String itemNotNull) {
        this.itemNotNull = itemNotNull;
    }

    public String getItemSequence() {
        return itemSequence;
    }

    public void setItemSequence(String itemSequence) {
        this.itemSequence = itemSequence;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemProperty() {
        return itemProperty;
    }

    public void setItemProperty(String itemProperty) {
        this.itemProperty = itemProperty;
    }

    public String getItemDefaultValue() {
        return itemDefaultValue;
    }

    public void setItemDefaultValue(String itemDefaultValue) {
        this.itemDefaultValue = itemDefaultValue;
    }

    public String getItemFormula() {
        return itemFormula;
    }

    public void setItemFormula(String itemFormula) {
        this.itemFormula = itemFormula;
    }

    public String getItemIssue() {
        return itemIssue;
    }

    public void setItemIssue(String itemIssue) {
        this.itemIssue = itemIssue;
    }

    public String getItemIsSelect() {
        return itemIsSelect;
    }

    public void setItemIsSelect(String itemIsSelect) {
        this.itemIsSelect = itemIsSelect;
    }

    public String getItemSelf() {
        return itemSelf;
    }

    public void setItemSelf(String itemSelf) {
        this.itemSelf = itemSelf;
    }

    public String getItemLinkSet() {
        return itemLinkSet;
    }

    public void setItemLinkSet(String itemLinkSet) {
        this.itemLinkSet = itemLinkSet;
    }

    public String getItemLinkJsp() {
        return itemLinkJsp;
    }

    public void setItemLinkJsp(String itemLinkJsp) {
        this.itemLinkJsp = itemLinkJsp;
    }

    public String getJsScript() {
        return jsScript;
    }

    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public String getEditProp() {
        return editProp;
    }

    public void setEditProp(String editProp) {
        this.editProp = editProp;
    }
}
