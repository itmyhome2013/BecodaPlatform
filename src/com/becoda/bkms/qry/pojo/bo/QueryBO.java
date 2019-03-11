package com.becoda.bkms.qry.pojo.bo;


public class QueryBO implements java.io.Serializable {
    public static final String QSTYPE_QUERY = "Q";
    public static final String QSTYPE_STATIC = "S";

    public static final String UNIT_TYPE_ORG = "ORG";
    public static final String UNIT_TYPE_PARTY = "PARTY";

    private String classId;
    private String qryId;
    private String name;

    /**
     * Q-查询 ; S-统计
     */
    private String qsType;

    /**
     * A-人员 B-机构 等
     */
    private String setType;

    private String createUser;
    private String createDate;

    /**
     * 是否显示id型数据的Id<br>
     * 0-不显示; 1-显示
     */
    private int idFlag;
    private int historyFlag;
    private String historySet;//需要查询历史记录的子集

    /**
     * 查询的机构范围编号,使用,进行分割.保存orguid<br>
     * 如0001,0002
     */
    private String orgIds;

    /**
     * 机构名称使用,分割.与orgIds相对应
     */
    private String orgNames;

    /**
     * 系统默认查询.系统默认查询不允许修改,只在系统内部使用
     * 0-非系统默认; 1-系统默认查询.
     */
    private int sysFlag;
    /**
     * 附加条件,由调用高级查询的操作传入
     */
    private String addedCondition;

    /**
     * 机构范围类别,UNIT_TYPE_ORG =ORG 机构范围; UNIT_TYPE_PARTY=PARTY 组织关系范围
     */
    private String unitType = "ORG";

    private String staticItemId;
    private String count;
    private String min;
    private String max;
    private String avg;

    private String operFlag;

    private String selectStr;
    private String fromStr;
    private String whereStr;
    private String orderStr;
    private String groupStr;
    private String manualFlag;//MANUAL_FLAG 是否允许手动输入，1，允许，0不允许
    private String asyncFlag;//ASYNC_FLAG   0,同步，1.异步


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

    public int getIdFlag() {
        return idFlag;
    }

    public void setIdFlag(int idFlag) {
        this.idFlag = idFlag;
    }

    public int getHistoryFlag() {
        return historyFlag;
    }

    public void setHistoryFlag(int historyFlag) {
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

    public int getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(int sysFlag) {
        this.sysFlag = sysFlag;
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

    public String getAddedCondition() {
        return addedCondition;
    }

    public void setAddedCondition(String addedCondition) {
        this.addedCondition = addedCondition;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
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
}
