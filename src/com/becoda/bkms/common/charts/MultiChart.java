package com.becoda.bkms.common.charts;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-30
 * Time: 16:26:35
 */
public class MultiChart extends BaseChart {
    private String[] seriesNames;
    private Data[][] data;

    public Data[][] getData() {
        return data;
    }

    public void setData(Data[][] data) {
        this.data = data;
    }

    public MultiChart() {
    }

    public MultiChart(String caption, String[] categates, Data[][] data) {
        setCaption(caption);
        setCategates(categates);
        setData(data);
    }

    /**
     *
     * @param caption 图说明
     * @param categates 横坐标对象值,
     * @param seriesNames 纵坐标
     * @param data  值
     */
    public MultiChart(String caption, String[] categates, String[] seriesNames, Data[][] data) {
        setCaption(caption);
        setSeriesNames(seriesNames);
        setCategates(categates);
        setData(data);
    }
    public MultiChart(String caption, String[] categates, String[] seriesNames, Data[][] data,String []avgData) {
        setCaption(caption);
        setSeriesNames(seriesNames);
        setCategates(categates);
        setData(data);
        this.setAvgData(avgData);
    }

    public MultiChart(String[] categates, Data[][] data) {
        setCategates(categates);
        setData(data);
    }

    public MultiChart(String[] categates, String[] seriesNames, Data[][] data) {
        setCategates(categates);
        setSeriesNames(seriesNames);
        setData(data);
    }
    public MultiChart(String[] categates, String[] seriesNames, Data[][] data,String[]avgData) {
        setCategates(categates);
        setSeriesNames(seriesNames);
        setData(data);
        this.setAvgData(avgData);
    }

    public String[] getSeriesNames() {
        return seriesNames;
    }

    public void setSeriesNames(String[] seriesNames) {
        this.seriesNames = seriesNames;
    }

}
