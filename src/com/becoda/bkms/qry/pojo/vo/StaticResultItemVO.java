package com.becoda.bkms.qry.pojo.vo;

import java.io.Serializable;

/**
 * User: kangdw
 * Date: 2015-7-13
 * Time: 20:06:00
 */
public class StaticResultItemVO implements Serializable {
    private int count;
    private String avg;
    private String min;
    private String max;
    private String precent;
    private String allValue;
    private String allValueField;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
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

    public String getPrecent() {
        return precent;
    }

    public void setPrecent(String precent) {
        this.precent = precent;
    }

    public String getAllValue() {
        return allValue;
    }

    public void setAllValue(String allValue) {
        this.allValue = allValue;
    }

    public String getAllValueField() {
        return allValueField;
    }

    public void setAllValueField(String allValueField) {
        this.allValueField = allValueField;
    }
}
