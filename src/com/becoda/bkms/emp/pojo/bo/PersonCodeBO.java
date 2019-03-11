package com.becoda.bkms.emp.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-7-14
 * Time: 17:45:57
 * To change this template use File | Settings | File Templates.
 */
public class PersonCodeBO {

    private String id;
    private String orgId;
    private String startCode;
    private String endCode;
    private String leader;
    private String max;
    private String type;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public String getEndCode() {
        return endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
