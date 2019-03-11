package com.becoda.bkms.emp.pojo.bo;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-27
 * Time: 8:54:49
 * To change this template use File | Settings | File Templates.
 */
public class PersonCode implements Serializable {
    private String personCode;
    private String personId;

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
