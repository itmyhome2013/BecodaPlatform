package com.becoda.bkms.common.charts;

import java.util.Map;
import java.util.HashMap;

/**
 * User: Jair.Shaw
 * Date: 2015-7-4
 * Time: 14:51:35
 */
public class TrendLine {
    //趋势线的开始值,只设置此值将会是一条水平线
    private String startValue;
    //趋势线的结束值，同时设置结束值，将画出一条斜线,X轴的起点为 startValue,X轴的终点为 endValue
    private String endValue;
    //线条与显示值的颜色
    private String color = "009933";    //由于FC会默认为黑色，所以设置一个默认值
    //显示值
    private String diaplayValue;
    private String toolTip;
    //其它属性，可通过字符串方式(setPropertiesString("key=value,key2=value2,..."))初始化
    private Map properties;

    public TrendLine() {
    }

    public TrendLine(String startValue, String diaplayValue) {
        this.startValue = startValue;
        this.diaplayValue = diaplayValue;
    }

    public TrendLine(String startValue, String diaplayValue, String color) {
        this.color = color;
        this.startValue = startValue;
        this.diaplayValue = diaplayValue;
    }

    public TrendLine(String startValue, String color, String diaplayValue, String endValue, String toolTip, Map properties) {
        this.startValue = startValue;
        this.color = color;
        this.diaplayValue = diaplayValue;
        this.endValue = endValue;
        this.toolTip = toolTip;
        this.properties = properties;
    }

    public TrendLine(String startValue, String color, String diaplayValue, String endValue, String toolTip, String propertiesString) {
        new TrendLine(startValue, color, diaplayValue, endValue, toolTip, PropertiesUtils.parsePropertiesString(propertiesString));
    }

    public TrendLine(String startValue, String color, String diaplayValue, String endValue, String toolTip) {
        this.startValue = startValue;
        this.color = color;
        this.diaplayValue = diaplayValue;
        this.endValue = endValue;
        this.toolTip = toolTip;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDiaplayValue() {
        return diaplayValue;
    }

    public void setDiaplayValue(String diaplayValue) {
        this.diaplayValue = diaplayValue;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }
}
