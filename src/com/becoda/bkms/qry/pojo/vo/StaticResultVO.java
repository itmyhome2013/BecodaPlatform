package com.becoda.bkms.qry.pojo.vo;

import java.io.Serializable;

/**
 * User: kang
 * Date: 2015-5-31
 * Time: 9:53:40
 */
public class StaticResultVO implements Serializable {
    String itemId;
    String count;
    String min;
    String max;
    String avg;
    String name;//staticName;

    StaticResultItemVO[] items;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StaticResultItemVO[] getItems() {
        return items;
    }

    public void setItems(StaticResultItemVO[] items) {
        this.items = items;
    }
}
