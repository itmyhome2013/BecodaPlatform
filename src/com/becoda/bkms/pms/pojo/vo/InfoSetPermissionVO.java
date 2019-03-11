package com.becoda.bkms.pms.pojo.vo;

/**
 * * 信息集Vo 用户权限设置
 * User: lirg
 * Date: 2015-7-21
 * Time: 17:07:05
 * To change this template use File | Settings | File Templates.
 */
public class InfoSetPermissionVO {
    String setId;
    String setName;
    String permission;

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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
