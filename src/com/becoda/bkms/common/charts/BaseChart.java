package com.becoda.bkms.common.charts;

import java.util.HashMap;
import java.util.Map;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-5-15
 * Time: 10:20:05
 */
public abstract class BaseChart {
    protected String caption;     //(可为空) 标题
    protected String chartLink;     //整个图形的链接
    /**
     * 其它属性
     * 字符串格式以 逗号分隔，如 xAxisName=X,yAxisName=Y
     */
    protected Map properties;
    protected String[] categates; //数据分类
    protected String[] avgData; //数据分类

    //基线，对于饼图与面积图无效
    protected TrendLine[] trendLines;   //基线;
    //垂直分割线
    protected VerticalLine[] verticalLines; //垂直分割线

    public String[] getAvgData() {
        return avgData;
    }

    public void setAvgData(String[] avgData) {
        this.avgData = avgData;
    }

    /**
     * 链接的目标框架 或者是 弹出窗口，
     * 如果设置此属性，那么链接将链接到 linkTarget 的 frame
     * 弹出窗定义语法为：
     * 将弹窗的属性定义在 frame 名称的后面，窗口名与与各属性之间均用逗号分隔，例如：detailsWin,width=400,height=300,toolbar=no,scrollbars=no,resizable=no
     * 当 frame 窗口已经存在时(窗口名为_self,_parent 或已经定义在页面中)，其后的属性将不会生效
     * 弹出窗的所有属性有：
     * resizable,scrollbars,menubar,toolbar ,location ,directories ,status ,fullscreen (值为 1,0 或 yes,no)
     * top ,left ,height,width (数值)
     * 若弹出新窗口，
     */
    protected String linkTarget;

    public String getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;

    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String[] getCategates() {
        return categates;
    }

    public void setCategates(String[] categates) {
        this.categates = categates;
    }

    public Map getProperties() {
        return properties;
    }

    public void setPropertiesString(String propertiesString) {
        if (propertiesString != null && propertiesString.trim().length() > 2 && propertiesString.indexOf("=") > 0) {
            if (properties == null) {
                properties = new HashMap();
            }
            String[] ps = propertiesString.split(",");
            for (int i = 0; i < ps.length; i++) {
                String p = ps[i];
                String[] nv = p.split("=");
                properties.put(nv[0], nv[1]);
            }
        }
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    public String getChartLink() {
        return chartLink;
    }

    public void setChartLink(String chartLink) {
        this.chartLink = chartLink;
    }

    public boolean hasProperties() {
        return properties != null && properties.size() > 0;
    }

    public TrendLine[] getTrendLines() {
        return trendLines;
    }

    public void setTrendLines(TrendLine[] trendLines) {
        this.trendLines = trendLines;
    }

    public VerticalLine[] getVerticalLines() {
        return verticalLines;
    }

    public boolean hasVerticalLine(int index) {
        return getVerticalLine(index) != null;
    }

    public void setVerticalLines(VerticalLine[] verticalLines) {
        this.verticalLines = verticalLines;
    }

    public VerticalLine getVerticalLine(int index) {
        if (verticalLines == null || verticalLines.length == 0) {
            return null;
        }
        for (int i = 0; i < verticalLines.length; i++) {
            VerticalLine verticalLine = verticalLines[i];
            if (verticalLine.getIndex() == index) {
                return verticalLine;
            }
        }
        return null;
    }
}
