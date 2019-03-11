package com.becoda.bkms.common.charts;

import java.util.HashMap;
import java.util.Map;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-5-20
 * Time: 16:42:42
 */
public class Data {
    private String value;   //值
    private String link;    //链接
    private String displayValue;    //显示值
    private String toolTip; //提示
    private Map properties; //属性
    private String color ; // 颜色
    public Data(String value) {
        setValue(value);
    }

    public Data(String value, String link) {
        setValue(value);
        setLink(link);
    }

    public Data(String value, String link, String displayValue) {
        setValue(value);
        setLink(link);
        setDisplayValue(displayValue);
    }

    public Data(String value, String link, String displayValue, String toolTip) {
        setValue(value);
        setLink(link);
        setDisplayValue(displayValue);
        setToolTip(toolTip);
    }

    public Data(String value, String link, String displayValue, String toolTip, Map properties) {
        setValue(value);
        setLink(link);
        setDisplayValue(displayValue);
        setToolTip(toolTip);
        setProperties(properties);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null || value.trim().length() == 0) {
            this.value = "0";
        } else {
            this.value = value;
        }
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean containsLink() {
        return link != null && link.trim().length() > 0;
    }

    public boolean containsDispayValue() {
        return displayValue != null && displayValue.trim().length() > 0;
    }

    public boolean containsToolTip() {
        return toolTip != null && toolTip.trim().length() > 0;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    public void setProperties(String attributeName, String attributeValue) {
        if (properties == null) {
            properties = new HashMap();
        }
        if (attributeName != null && attributeName.trim().length() > 0 && attributeValue != null && attributeValue.trim().length() > 0) {
            properties.put(attributeName, attributeValue);
        }
    }

    public void removeProperties(String attributeName) {
        if (properties == null) {
            return;
        }
        properties.remove(attributeName);
    }

    public boolean hasProperties() {
        return properties != null && properties.size() > 0;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
     public boolean containsColor() {
        return color != null && color.trim().length()>0;
    }
}
