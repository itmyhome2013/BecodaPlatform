package com.becoda.bkms.run.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-5-9
 * Time: 11:24:05
 * To change this template use File | Settings | File Templates.
 */
public class RemPersonScopeBO {
    private String scopeId;
    private String remId;
    private String toType;
    private String toId;

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

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }


}
