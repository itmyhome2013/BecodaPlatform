package com.becoda.bkms.pms.pojo.vo;

/**
 * 信息项Vo 用户权限设置
 * User: lirg
 * Date: 2015-7-21
 * Time: 17:13:48
 * To change this template use File | Settings | File Templates.
 */
public class InfoItemPermissionVO {
    String itemId;
    String itemName;
    String permission;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
