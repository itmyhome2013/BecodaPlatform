package com.becoda.bkms.common.charts;

import java.util.Map;

/**
 * User: Jair.Shaw
 * Date: 2015-7-4
 * Time: 22:28:04
 */
public class VerticalLine {
    private int index;  //必选属性//垂直线的位置，在第几组分类之后
    private String label;   //垂直线的名字
    private String color;   //颜色
    private Map properties;

    public VerticalLine(int index) {
        this.index = index;
    }

    public VerticalLine(int index, String label) {
        this.index = index;
        this.label = label;
    }

    public VerticalLine(int index, String label, String color) {
        this.index = index;
        this.label = label;
        this.color = color;
    }
    public VerticalLine(int index, String label, String color,String propertiesString) {
        this.index = index;
        this.label = label;
        this.color = color;
        setPropertiesString(propertiesString);
    }

    public VerticalLine(int index, String label, String color, Map properties) {
        this.index = index;
        this.label = label;
        this.color = color;
        this.properties = properties;
    }

     public void setPropertiesString(String propertiesString) {
        setProperties(PropertiesUtils.parsePropertiesString(propertiesString));
    }
    public void addPropertiesString(String propertiesString) {
        Map m = PropertiesUtils.parsePropertiesString(propertiesString);
        if (properties == null) {
            properties = m;
        }else{
            properties.putAll(m);
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }
}
