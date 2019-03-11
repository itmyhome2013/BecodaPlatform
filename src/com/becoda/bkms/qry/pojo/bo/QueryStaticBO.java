package com.becoda.bkms.qry.pojo.bo;


public class QueryStaticBO implements java.io.Serializable {
    private String qryId;
    private String staticId;
    private String name;
    private int seq;
    private String group;
    private String groupShow;

    /**
     * 统计项目:如年龄.统计个数时 为主键,(id,orguid,postid,partyid)
     */
    private String staticItem;

    /**
     * 0-count; 1-max; 2-min; 3-sum
     */
    private String staticType;

    public String getQryId() {
        return qryId;
    }

    public void setQryId(String qryId) {
        this.qryId = qryId;
    }

    public String getStaticId() {
        return staticId;
    }

    public void setStaticId(String staticId) {
        this.staticId = staticId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStaticItem() {
        return staticItem;
    }

    public void setStaticItem(String staticItem) {
        this.staticItem = staticItem;
    }

    public String getStaticType() {
        return staticType;
    }

    public void setStaticType(String staticType) {
        this.staticType = staticType;
    }

    public String getGroupShow() {
        return groupShow;
    }

    public void setGroupShow(String groupShow) {
        this.groupShow = groupShow;
    }
}
