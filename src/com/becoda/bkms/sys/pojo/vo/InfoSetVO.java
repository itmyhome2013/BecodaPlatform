package com.becoda.bkms.sys.pojo.vo;

import com.becoda.bkms.sys.SysConstants;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-3
 * Time: 15:26:29
 * To change this template use File | Settings | File Templates.
 */
public class InfoSetVO {
    //指标集id
    private String setId;
    //指标集的名称
    private String setName;
    //指标集的描述
    private String setDesc;
    //指标集的物理分类
    private String set_bType;
    //指标集的类别
    private String set_sType;
    //指标集的分类
    private String set_rsType = SysConstants.INFO_SET_RS_TYPE_MANY;
    //指标集的状态
    private String setStatus = SysConstants.INFO_STATUS_OPEN;
    //指标集的属性
    private String setProperty = SysConstants.INFO_SET_PROPERTY_USER;
    //指标集的显示顺序
    private String setSequence;
    //指标集的创建者
    private String setCreator;
    //指标集的主键
    private String setPk;
    //指标集的外键
    private String setFk;
    //指标集的版本
    private String set_Issue;
    //指标集的排序字段
    private String setOrder;
    //指标集增加记录时要执行的方法
    private String addFunction;
    //指标集修改记录时要执行的方法
    private String updateFunction;
    //指标集删除记录时要执行的方法；
    private String delFunction;

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetDesc() {
        return setDesc;
    }

    public void setSetDesc(String setDesc) {
        this.setDesc = setDesc;
    }

    public String getSet_bType() {
        return set_bType;
    }

    public void setSet_bType(String set_bType) {
        this.set_bType = set_bType;
    }

    public String getSet_sType() {
        return set_sType;
    }

    public void setSet_sType(String set_sType) {
        this.set_sType = set_sType;
    }

    public String getSet_rsType() {
        return set_rsType;
    }

    public void setSet_rsType(String set_rsType) {
        this.set_rsType = set_rsType;
    }

    public String getSetStatus() {
        return setStatus;
    }

    public void setSetStatus(String setStatus) {
        this.setStatus = setStatus;
    }

    public String getSetProperty() {
        return setProperty;
    }

    public void setSetProperty(String setProperty) {
        this.setProperty = setProperty;
    }

    public String getSetSequence() {
        return setSequence;
    }

    public void setSetSequence(String setSequence) {
        this.setSequence = setSequence;
    }

    public String getSetCreator() {
        return setCreator;
    }

    public void setSetCreator(String setCreator) {
        this.setCreator = setCreator;
    }

    public String getSetPk() {
        return setPk;
    }

    public void setSetPk(String setPk) {
        this.setPk = setPk;
    }

    public String getSetFk() {
        return setFk;
    }

    public void setSetFk(String setFk) {
        this.setFk = setFk;
    }

    public String getSet_Issue() {
        return set_Issue;
    }

    public void setSet_Issue(String set_Issue) {
        this.set_Issue = set_Issue;
    }

    public String getSetOrder() {
        return setOrder;
    }

    public void setSetOrder(String setOrder) {
        this.setOrder = setOrder;
    }

    public String getAddFunction() {
        return addFunction;
    }

    public void setAddFunction(String addFunction) {
        this.addFunction = addFunction;
    }

    public String getUpdateFunction() {
        return updateFunction;
    }

    public void setUpdateFunction(String updateFunction) {
        this.updateFunction = updateFunction;
    }

    public String getDelFunction() {
        return delFunction;
    }

    public void setDelFunction(String delFunction) {
        this.delFunction = delFunction;
    }
}
