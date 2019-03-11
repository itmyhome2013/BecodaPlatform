package com.becoda.bkms.common.charts;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.*;
import java.math.BigDecimal;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-30
 * Time: 16:26:11
 */
public class ChartUtils {
    public static String BATH_PATH;
    public static final String DEFAULT_SINGLE_XML_TEMPLATE = "/jsp/common/charts/xmlTemplate/common/single.xml";
    public static final String DEFAULT_MULTI_XML_TEMPLATE = "/jsp/common/charts/xmlTemplate/common/multi.xml";
    public static Map mapColor = new HashMap();

    public static String getXML(SingleChart chart) {
        return getXML(chart, DEFAULT_SINGLE_XML_TEMPLATE);
    }

    /**
     * 取得装好数据的XML字符串
     *
     * @param chart           数据对象
     * @param xmlTemplateFile xml 模板文件的地址
     * @return xml string
     */
    public static String getXML(SingleChart chart, String xmlTemplateFile) {
        if (xmlTemplateFile == null || xmlTemplateFile.trim().equals("")) {
            xmlTemplateFile = DEFAULT_SINGLE_XML_TEMPLATE;
        }

        if (chart == null) return "";
        SAXReader reader = new SAXReader();
        Document doc;
        try {
            doc = reader.read(ChartUtils.BATH_PATH + xmlTemplateFile);
            Element root = doc.getRootElement();
            setPropertiesToElement(root, chart);
            //分类
            String[] categates = chart.getCategates();
            //数据
            Data[] datas = chart.getData();
            //数据列等于分类数量
            int colSize = categates.length;
            List sets = root.elements("set");
            for (int i = 0; i < colSize; i++) {
                String c = categates[i];
                Data data = datas[i];
                //如果模板中存在够用的分类，则取模板中的分类，否则，新建分类
                Element e = (i < sets.size()) ? (Element) sets.get(i) : root.addElement("set");
                e.addAttribute("label", c);
                setDataToElement(e, data, chart.getLinkTarget());
                //查看是否定义了垂直线，如定义了，则加入垂直线
                if (chart.hasVerticalLine(i)) {
                    addVerticalLineToElement(chart.getVerticalLine(i), root);
                }
            }
            //删除模板中多余的分类
            if (sets.size() > colSize) {
                for (int i = colSize; i < sets.size(); i++) {
                    root.remove((Element) sets.get(i));
                }
            }

            //添加基准线
            addTrendLinesToElement(chart.getTrendLines(), root);
            return convertXmlToChart(doc.asXML());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static void addVerticalLineToElement(VerticalLine line, Element root) {
        if (line == null) return;
        Element vLine = root.addElement("vLine");
        if (line.getLabel() != null) {
            vLine.addAttribute("label", line.getLabel());
        }
        if (line.getColor() != null) {
            vLine.addAttribute("color", line.getColor());
        }
        Map m = line.getProperties();
        if (m != null && m.size() > 0) {
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String k = (String) it.next();
                String v = (String) m.get(k);
                vLine.addAttribute(k, v);
            }
        }
    }

    private static void addTrendLinesToElement(TrendLine[] lines, Element root) {
        if (lines == null || lines.length == 0) {
            return;
        }
        Element trendLines = root.addElement("trendLines");
        for (int i = 0; i < lines.length; i++) {
            TrendLine line = lines[i];
            Element lineEle = trendLines.addElement("line");
            lineEle.addAttribute("startValue", line.getStartValue());
            lineEle.addAttribute("displayValue", line.getDiaplayValue());
            if (line.getColor() != null && line.getColor().trim().length() > 0) {
                lineEle.addAttribute("color", line.getColor());
            }
            if (line.getEndValue() != null && line.getEndValue().trim().length() > 0) {
                lineEle.addAttribute("endValue", line.getEndValue());
            }
            if (line.getToolTip() != null && line.getToolTip().trim().length() > 0) {
                lineEle.addAttribute("toolText", line.getToolTip());
            }
            Map m = line.getProperties();
            if (m != null && m.size() > 0) {
                Iterator it = m.keySet().iterator();
                while (it.hasNext()) {
                    String k = (String) it.next();
                    String v = (String) m.get(k);
                    lineEle.addAttribute(k, v);
                }
            }
        }
    }


    public static String getXML(MultiChart chart) {
        return getXML(chart, DEFAULT_MULTI_XML_TEMPLATE);
    }

    /**
     * 取得装好数据的XML字符串
     *
     * @param chart           数据对象
     * @param xmlTemplateFile xml 模板文件的地址
     * @return xml string
     */
    public static String getXML(MultiChart chart, String xmlTemplateFile) {
        if (xmlTemplateFile == null || xmlTemplateFile.trim().equals("")) {
            xmlTemplateFile = DEFAULT_MULTI_XML_TEMPLATE;
        }
        SAXReader reader = new SAXReader();
        Document doc;
        try {
            doc = reader.read(ChartUtils.BATH_PATH + xmlTemplateFile);
            Element root = doc.getRootElement();
            setPropertiesToElement(root, chart);
            //分类
            String[] categates = chart.getCategates();
            //数据列
            String[] seriesNames = chart.getSeriesNames();

            //数据列等于分类数量
            int colSize = categates.length;
            Element cates = root.element("categories");
            List categateElements = cates.elements("category");
            for (int i = 0; i < colSize; i++) {
                String c = categates[i];
                if (i < categateElements.size()) {
                    Element e = (Element) categateElements.get(i);
                    e.addAttribute("label", c);
                } else {
                    Element e = cates.addElement("category");
                    e.addAttribute("label", c);
                }
                //查看是否定义了垂直线，如定义了，则加入垂直线
                if (chart.hasVerticalLine(i)) {
                    addVerticalLineToElement(chart.getVerticalLine(i), cates);
                }
            }

            //删除模板中多余的分类
            if (categateElements.size() > colSize) {
                for (int i = colSize; i < categateElements.size(); i++) {
                    cates.remove((Element) categateElements.get(i));
                }
            }
            //数据
            Data[][] data = chart.getData();

            List dataSets = root.elements("dataset");
            for (int i = 0; i < data.length; i++) {
                Data[] ds = data[i];
                if (i < dataSets.size()) {
                    Element dataSet = (Element) dataSets.get(i);
                    //设置数据
                    List sets = dataSet.elements("set");
                    for (int j = 0; j < colSize; j++) {
                        Data d = ds[j];
                        Element set = j < sets.size() ? (Element) sets.get(j) : dataSet.addElement("set");
                        setDataToElement(set, d, chart.getLinkTarget());
                    }
                    //设置属性
                    if (seriesNames != null && seriesNames.length == data.length) {
                        dataSet.addAttribute("seriesName", seriesNames[i]);
                        if (ds[0].containsColor()) {  //设置颜色
                        dataSet.addAttribute("color", ds[0].getColor());
                        dataSet.addAttribute("anchorBorderColor", ds[0].getColor());
                        dataSet.addAttribute("anchorBgColor", ds[0].getColor());
                    }
                    }

                    //删除模板中多余的数据
                    if (sets.size() > colSize) {
                        for (int j = colSize; j < categateElements.size(); j++) {
                            dataSet.remove((Element) sets.get(j));
                        }
                    }
                } else {
                    //创建dataset
                    Element dataSet = root.addElement("dataset");
                    for (int j = 0; j < colSize; j++) {
                        Data d = ds[j];
                        //创建set
                        Element set = dataSet.addElement("set");
                        setDataToElement(set, d, chart.getLinkTarget());
                    }
                    //设置属性
                    if (seriesNames != null && seriesNames.length == data.length) {
                        dataSet.addAttribute("seriesName", seriesNames[i]);
                        if (ds[0].containsColor()) {  //设置颜色
                        dataSet.addAttribute("color", ds[0].getColor());
                        dataSet.addAttribute("anchorBorderColor", ds[0].getColor());
                        dataSet.addAttribute("anchorBgColor", ds[0].getColor());
                    }
                    }
                }
            }
            //删除掉多余的Set
            if (dataSets.size() > data.length) {
                for (int i = data.length; i < dataSets.size(); i++) {
                    root.remove((Element) dataSets.get(i));
                }
            }

            //平均线
            String[] avgLine = chart.getAvgData();
            if (avgLine != null) {
                int avgSize = avgLine.length;
                Element avg = root.addElement("dataset");
                avg.addAttribute("seriesName", "平均值");
                avg.addAttribute("lineThickness", "3");
                avg.addAttribute("parentYAxis", "P");
                avg.addAttribute("renderAs", "LINE");
                avg.addAttribute("showValues", "1");
                List avgElements = avg.elements("set");
                for (int i = 0; i < avgSize; i++) {
                    String c = avgLine[i];
                    if (i < avgElements.size()) {
                        Element e = (Element) avgElements.get(i);
                        e.addAttribute("value", c);
                    } else {
                        Element e = avg.addElement("set");
                        e.addAttribute("value", c);
                    }
                }
            }
            //end
            //添加基准线
            addTrendLinesToElement(chart.getTrendLines(), root);
            return convertXmlToChart(doc.asXML());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得装好数据的XML字符串
     *
     * @param chart           数据对象
     * @param xmlTemplateFile xml 模板文件的地址
     * @return xml string
     */
    public static String getXML(CombineChart chart, String xmlTemplateFile) {
        if (xmlTemplateFile == null || xmlTemplateFile.trim().equals("")) {
            xmlTemplateFile = DEFAULT_MULTI_XML_TEMPLATE;
        }
        SAXReader reader = new SAXReader();
        Document doc;
        try {
            doc = reader.read(ChartUtils.BATH_PATH + xmlTemplateFile);
            Element root = doc.getRootElement();
            setPropertiesToElement(root, chart);
            //分类
            String[] categates = chart.getCategates();
            //数据列
            String[] seriesNames = chart.getSeriesNames();

            //数据列等于分类数量
            int colSize = categates.length;
            Element cates = root.element("categories");
            List categateElements = cates.elements("category");
            for (int i = 0; i < colSize; i++) {
                String c = categates[i];
                if (i < categateElements.size()) {
                    Element e = (Element) categateElements.get(i);
                    e.addAttribute("label", c);
                } else {
                    Element e = cates.addElement("category");
                    e.addAttribute("label", c);
                }
            }

            //删除模板中多余的分类
            if (categateElements.size() > colSize) {
                for (int i = colSize; i < categateElements.size(); i++) {
                    cates.remove((Element) categateElements.get(i));
                }
            }
            //数据
            Data[][] data = chart.getData();

            List dataSets = root.elements("dataset");
            Element dataSetRoot = root.addElement("dataset");
            for (int i = 0; i < data.length; i++) {
                Data[] ds = data[i];
                if (i < dataSets.size()) {
                    Element dataSet = (Element) dataSets.get(i);
                    //设置数据
                    List sets = dataSet.elements("set");
                    for (int j = 0; j < colSize; j++) {
                        Data d = ds[j];
                        Element set = j < sets.size() ? (Element) sets.get(j) : dataSet.addElement("set");
                        setDataToElement(set, d, chart.getLinkTarget());
                    }
                    //设置属性
                    if (seriesNames != null && seriesNames.length == data.length) {
                        dataSet.addAttribute("seriesName", seriesNames[i]);
                        dataSet.addAttribute("color", ds[0].getColor());

                    }
                    //删除模板中多余的数据
                    if (sets.size() > colSize) {
                        for (int j = colSize; j < categateElements.size(); j++) {
                            dataSet.remove((Element) sets.get(j));
                        }
                    }
                } else {
                    //创建dataset
                    Element dataSet = dataSetRoot.addElement("dataset");
                    for (int j = 0; j < colSize; j++) {
                        Data d = ds[j];
                        //创建set
                        Element set = dataSet.addElement("set");
                        setDataToElement(set, d, chart.getLinkTarget());
                    }
                    //设置属性
                    if (seriesNames != null && seriesNames.length == data.length) {
                        dataSet.addAttribute("seriesName", seriesNames[i]);
                    }
                }
            }
            //删除掉多余的Set
            if (dataSets.size() > data.length) {
                for (int i = data.length; i < dataSets.size(); i++) {
                    root.remove((Element) dataSets.get(i));
                }
            }

            //划线
            Element trendlines = root.element("trendlines");
            String targetValue = chart.getTargetValue();
            if (trendlines != null && targetValue != null) {
                Element line = trendlines.element("line");
                line.addAttribute("startValue", targetValue);
            } else {
                Element trendlinesE = root.addElement("trendlines");
                Element line = trendlinesE.addElement("line");
                line.addAttribute("startValue", targetValue);
                line.addAttribute("color", "91C728");
                line.addAttribute("displayValue", "平均值" + targetValue);
                line.addAttribute("showOnTop", "1");
            }

            //end
            return convertXmlToChart(doc.asXML());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertXmlToChart(String xml) {
        if (xml.indexOf("?>") > 0) {
            xml = xml.substring(xml.indexOf("?>") + 2);
        }
        xml = xml.replaceAll("\"", "'");
        xml = xml.replaceAll("\n", "");
        return xml;
    }

    private static void setDataToElement(Element element, Data data, String linkTarget) {
        element.addAttribute("value", data.getValue());
        if (data.containsLink()) {
            element.addAttribute("link", parseLink(data.getLink(), linkTarget));
        }
        if (data.containsDispayValue()) {
            element.addAttribute("displayValue", data.getDisplayValue());
        }
        if (data.containsToolTip()) {
            element.addAttribute("toolText", data.getToolTip());
        }
        if (data.containsColor()) {
            element.addAttribute("color", data.getColor());
            element.addAttribute("anchorBorderColor", data.getColor());
            element.addAttribute("anchorBgColor", data.getColor());
        }
        if (data.hasProperties()) {
            setOtherPropertiesToElement(element, data.getProperties());
        }
    }

    private static void setOtherPropertiesToElement(Element element, Map properties) {
        if (properties == null || properties.size() == 0) {
            return;
        }
        Iterator it = properties.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = (String) properties.get(key);
            if (key != null && value != null && key.trim().length() > 0 && value.trim().length() > 0) {
                element.addAttribute(key, (String) properties.get(key));
            }
        }
    }

    private static void setPropertiesToElement(Element element, BaseChart chart) {
        String caption = chart.getCaption();
        //覆盖其标题
        if (caption != null && caption.trim().length() > 0) {
            element.addAttribute("caption", caption);
        }
        //其它属性
        if (chart.hasProperties()) {
            setOtherPropertiesToElement(element, chart.getProperties());
        }
    }


    /**
     * 拼凑 URL 为 FusionCharts 可识别的URL串
     * 1.URLEncode
     * 2.add frame
     *
     * @param linkUrl    原来的 linkUrl
     * @param linkTarget 链接目标窗口
     * @return url
     */
    private static String parseLink(String linkUrl, String linkTarget) {
        try {
            String url = URLEncoder.encode(linkUrl, "UTF-8");
            if (linkTarget != null && !linkTarget.trim().equals("")) {
                if (linkTarget.startsWith("P-")) {
                    if (linkTarget.endsWith("-")) {
                        url = new StringBuffer(linkTarget).append(url).toString();
                    } else {
                        url = new StringBuffer(linkTarget).append("-").append(url).toString();
                    }
                } else if (linkTarget.indexOf(",") > 0) {
                    url = new StringBuffer("P-").append(linkTarget).append("-").append(url).toString();
                } else {
                    url = new StringBuffer("F-").append(linkTarget).append("-").append(url).toString();
                }
            }
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *取颜色值
     * @param key 为序号
     * @return
     */
    public static String getColor(String key) {
        if (mapColor.containsKey(key)){
            return (String)mapColor.get(key) ;
        }else{
            String r1 = Integer.toString(new BigDecimal(Math.random() * 15).intValue(), 16);
            String r2 = Integer.toString(new BigDecimal(Math.random() * 15).intValue(), 16);
            String r3 = Integer.toString(new BigDecimal(Math.random() * 15).intValue(), 16);
            String r4 = Integer.toString(new BigDecimal(Math.random() * 15).intValue(), 16);
            String r5 = Integer.toString(new BigDecimal(Math.random() * 15).intValue(), 16);
            String r6 = Integer.toString(new BigDecimal(Math.random() * 15).intValue(), 16);
            String rgb =(r1 + r2 + r3 + r4 + r5 + r6).toUpperCase() ;
            mapColor.put(key,rgb);
            return rgb ;
        }
    }

}
