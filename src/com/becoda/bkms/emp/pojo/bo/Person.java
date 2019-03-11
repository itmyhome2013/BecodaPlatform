package com.becoda.bkms.emp.pojo.bo;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-18
 * Time: 9:10:50
 * To change this template use File | Settings | File Templates.
 */
public class Person implements Serializable {

    private String personId;
    private String name;
    private String personCode;
    private String orgId; //所属机构   A001701
    private String deptId;//所属部门   A001705
    private String imageId;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
