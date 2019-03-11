package com.becoda.bkms.common.charts;


import java.util.Random;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-5-12
 * Time: 14:34:33
 */
public class ChartDemo {
    public static MultiChart getMultiChartDemoBar3D(boolean hasLink) {
        MultiChart multiChart;
        String[] multiCates = new String[]{"Mon", "Tue", "Wed", "Tue", "Fri", "Sat", "Sun"};
        Data[][] datas = new Data[1][7];
        for (int i = 0; i < 7; i++) {
            if (hasLink) {
                datas[0][i] = new Data(String.valueOf(getInt()), "http://www.g.cn?act=act&index=" + 1);
            } else {
                datas[0][i] = new Data(String.valueOf(getInt()));
            }
        }
        multiChart = new MultiChart("示例3DBar图", multiCates, datas);
        return multiChart;
    }

    public static MultiChart getMultiChartDemo(boolean hasLink) {
        MultiChart multiChart;
        String[] multiCates = new String[]{"Mon", "Tue", "Wed", "Tue", "Fri", "Sat", "Sun"};
        Data[][] datas = new Data[4][7];
        for (int i = 0; i < 4; i++) {
            Data[] data = new Data[7];
            for (int j = 0; j < 7; j++) {
                if (hasLink) {
                    data[j] = new Data(String.valueOf(getInt()), "http://www.g.cn?act=act&i=" + i);
                } else {
                    data[j] = new Data(String.valueOf(getInt()));
                }
            }
            datas[i] = data;
        }
        multiChart = new MultiChart("ChartDemo multi chart", multiCates, datas);
        return multiChart;
    }

    public static SingleChart getSingleChartDemo(boolean hasLink) {
        SingleChart singleChart;
        String[] singleCates = new String[]{"Mon", "Tue", "Wed", "Tue", "Fri", "Sat", "Sun"};
        Data[] data = new Data[7];
        for (int i = 0; i < 7; i++) {
            if (hasLink) {
                data[i] = new Data(String.valueOf(getInt()), "http://www.g.cn?act=act&as=" + i, "dispValue", "tooltip");
            } else {
                data[i] = new Data(String.valueOf(getInt()));
            }
        }
        singleChart = new SingleChart(singleCates, data);
        return singleChart;
    }

    public static int getInt() {
        return Math.abs(getInt1(10000));
    }

    public static int getInt1(int max) {
        int i = new Random().nextInt();
        while (i < 0) {
            i = new Random().nextInt();
        }
        return i % max;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(ChartDemo.getInt());
//        }
//        for (int i = 0; i < 10; i++) {
//            System.out.println(ChartDemo.getInt1(1000));
//        }
        String[] cs = new String[]{"1990", "1999", "2008"};
        Data[] ds = new Data[]{new Data("122"), new Data("134"), new Data("135")};
        SingleChart singleChart = new SingleChart(cs, ds);
        System.out.println("aaaaaaaaaaaa : " + singleChart.getCategates());
//
//        singleChart.setValue("1", new Data("1", "1.2"));
//        singleChart.setValue("2", new Data("2", "2.2"));
//        singleChart.setValue("3", new Data("3", "3.2"));
//        singleChart.setValue("4", new Data("4", "4.2"));
//        singleChart.setValue("2", new Data("22", "22.2"));
//        singleChart.setValue("1", new Data("11", "11.2"));
//        System.out.println("ok?" + singleChart.getLink().length);
    }
}
