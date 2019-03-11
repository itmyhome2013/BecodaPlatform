package com.becoda.bkms.qry.pojo.vo;

import com.becoda.bkms.qry.pojo.bo.QueryItemBO;

import java.io.Serializable;
import java.util.ArrayList;

public class QueryVO implements Serializable {
    private String classId;
    private String qryId;
    private String name;
    private String qsType;          //Q|S
    private String setType;         //A|B|C|D
    private String createUser;
    private String createDate;
    private String idFlag;
    private String historyFlag;
    private String orgIds;
    private String orgNames;
    private String sysFlag;
    private String unitType;
    private String addedCondition;

    private String staticItemId;
    private String count;
    private String min;
    private String max;
    private String avg;

    private String operFlag;
    private String historySet;//需要查询历史记录的子集

    private String selectStr;
    private String fromStr;
    private String whereStr;
    private String orderStr;
    private String groupStr;
    private String manualFlag;//MANUAL_FLAG 是否允许手动输入，1，允许，0不允许
    //自定义查询,构造sql时,是否添加默认过滤条件 true不添加 false 添加
    private boolean noAddDefaultCondFlag = false;

    public String getSelectStr() {
        return selectStr;
    }

    public void setSelectStr(String selectStr) {
        this.selectStr = selectStr;
    }

    public String getFromStr() {
        return fromStr;
    }

    public void setFromStr(String fromStr) {
        this.fromStr = fromStr;
    }

    public String getWhereStr() {
        return whereStr;
    }

    public void setWhereStr(String whereStr) {
        this.whereStr = whereStr;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getGroupStr() {
        return groupStr;
    }

    public void setGroupStr(String groupStr) {
        this.groupStr = groupStr;
    }

    public String getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(String manualFlag) {
        this.manualFlag = manualFlag;
    }

    public String getAsyncFlag() {
        return asyncFlag;
    }

    public void setAsyncFlag(String asyncFlag) {
        this.asyncFlag = asyncFlag;
    }

    private String asyncFlag;//ASYNC_FLAG   0,同步，1.异步


    private StaticVO statics[];
    private QueryItemBO item[];

    private ArrayList wageItem;

    public ArrayList getWageItem() {
        return wageItem;
    }

    public void setWageItem(ArrayList wageItem) {
        this.wageItem = wageItem;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getQryId() {
        return qryId;
    }

    public void setQryId(String qryId) {
        this.qryId = qryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQsType() {
        return qsType;
    }

    public void setQsType(String qsType) {
        this.qsType = qsType;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIdFlag() {
        return idFlag;
    }

    public void setIdFlag(String idFlag) {
        this.idFlag = idFlag;
    }

    public String getHistoryFlag() {
        return historyFlag;
    }

    public void setHistoryFlag(String historyFlag) {
        this.historyFlag = historyFlag;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    public String getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag;
    }

    public StaticVO[] getStatics() {
        return statics;
    }

    public void setStatics(StaticVO[] statics) {
        this.statics = statics;
    }

    public QueryItemBO[] getItem() {
        return item;
    }

    public void setItem(QueryItemBO[] item) {
        this.item = item;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getAddedCondition() {
        return addedCondition;
    }

    public void setAddedCondition(String addedCondition) {
        this.addedCondition = addedCondition;
    }

    public String getStaticItemId() {
        return staticItemId;
    }

    public void setStaticItemId(String staticItemId) {
        this.staticItemId = staticItemId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(String operFlag) {
        this.operFlag = operFlag;
    }

    public String getHistorySet() {
        return historySet;
    }

    public void setHistorySet(String historySet) {
        this.historySet = historySet;
    }

    public boolean isNoAddDefaultCondFlag() {
        return noAddDefaultCondFlag;
    }

    public void setNoAddDefaultCondFlag(boolean noAddDefaultCondFlag) {
        this.noAddDefaultCondFlag = noAddDefaultCondFlag;
    }
}
