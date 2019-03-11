package com.becoda.bkms.sys.pojo.vo;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-9
 * Time: 11:44:27
 * To change this template use File | Settings | File Templates.
 */
public class ParameterVO {
    private String key;
    private String value;

    private String moduleName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
