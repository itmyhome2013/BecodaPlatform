package com.becoda.bkms.common.charts;

import org.apache.commons.lang.ArrayUtils;


/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-5-4
 * Time: 9:52:20
 */
public class SingleChart extends BaseChart {
    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public SingleChart() {
    }

    public SingleChart(String[] categates, Data[] data) {
        setCategates(categates);
        setData(data);
    }

    public SingleChart(String caption, String[] categates, Data[] data) {
        setCaption(caption);
        setCategates(categates);
        setData(data);
    }
    public SingleChart(String caption, String[] categates, Data[] data,String[] avgData) {
        setCaption(caption);
        setCategates(categates);
        setData(data);
        setAvgData(avgData);
    }
    public void addValue(String categate, Data d) {
        throw new UnsupportedOperationException("方法未实现");
    }

    public void setValue(String categate, String value) {
        setValue(categate, new Data(value));
    }

    public void setValue(String categate, Data d) {
        if (categate == null || categate.trim().equals("")) {
            return;
        }
        //如果所有数据还是空
        if (categates == null || categates.length == 0) {
            categates = new String[]{categate};
            setData(new Data[]{d});
            return;
        }
        //检查是否在已有分类中已经存在，若存在，则设置值返回
        int posi = ArrayUtils.indexOf(categates, categate);
        if (posi > -1) {
            this.data[posi] = d;
            return;
        }
        //如果是新加入的分类
        categates = (String[]) ArrayUtils.add(categates, categate);
        setData((Data[]) ArrayUtils.add(this.data, d));
    }
}
