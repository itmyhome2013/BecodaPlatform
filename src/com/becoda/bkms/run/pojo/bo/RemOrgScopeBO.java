package com.becoda.bkms.run.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-5-9
 * Time: 11:20:14
 * To change this template use File | Settings | File Templates.
 */
public class RemOrgScopeBO {
    private String scopeId;
    private String remId;
    private String orgTreeId;
    private String orgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getRemId() {
        return remId;
    }

    public void setRemId(String remId) {
        this.remId = remId;
    }

    public String getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(String orgTreeId) {
        this.orgTreeId = orgTreeId;
    }
}
