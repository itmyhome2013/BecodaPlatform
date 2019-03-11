package com.becoda.bkms.common.charts;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-30
 * Time: 16:26:35
 */
public class CombineChart extends BaseChart {
    private String[] seriesNames;
    private Data[][] data;       //     只做竖行的组合, 两个横排组合暂时不支持.
//    private String[] lineSet ;     ?
    private String targetValue ;  //  目标值 , 如平均值

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public Data[][] getData() {
        return data;
    }

    public void setData(Data[][] data) {
        this.data = data;
    }

    public CombineChart() {
    }

    public CombineChart(String caption, String[] categates, Data[][] data) {
        setCaption(caption);
        setCategates(categates);
        setData(data);
    }

    public CombineChart(String caption, String[] categates, String[] seriesNames, Data[][] data) {
        setCaption(caption);
        setSeriesNames(seriesNames);
        setCategates(categates);
        setData(data);
    }
    public CombineChart(String caption, String[] categates, String[] seriesNames, Data[][] data,String targetValue) {
        setCaption(caption);
        setSeriesNames(seriesNames);
        setCategates(categates);
        setData(data);
        setTargetValue(targetValue);
    }

    public CombineChart(String[] categates, Data[][] data) {
        setCategates(categates);
        setData(data);
    }

    public CombineChart(String[] categates, String[] seriesNames, Data[][] data) {
        setCategates(categates);
        setSeriesNames(seriesNames);
        setData(data);
    }
    public CombineChart(String[] categates, String[] seriesNames, Data[][] data,String[]avgData) {
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