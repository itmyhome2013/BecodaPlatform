package com.becoda.bkms.common.charts;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-5-12
 * Time: 11:25:46
 */
public class ChartType {
    //--------------------饼图----饼图-----------------------//
    //--------------------饼图----饼图-----------------------//
    public static final String PIE2D = "Pie2D";    //2D饼图
    public static final String PIE3D = "Pie3D";    //3D饼图

    public static final String DOUGHNUT2D = "Doughnut2D";    //2D空心饼图(炸面圈)
    public static final String DOUGHNUT3D = "Doughnut3D";    //3D空心饼图(炸面圈)


    //--------------------柱图----柱图-----------------------//
    //--------------------柱图----柱图-----------------------//
    public static final String BAR2D = "Bar2D";    //2D横向柱图
    public static final String BAR3D = "MSBar3D";    //3D横向柱图 

    public static final String MSBAR2D = "MSBar2D";    //多列2D横向柱图
    public static final String MSBAR3D = "MSBar3D";    //多列3D横向柱图

    public static final String COLUMN2D = "Column2D";  //2D柱图
    public static final String COLUMN3D = "Column3D";  //3D柱图

    public static final String MSCOLUMN2D = "MSColumn2D";  //多列2D柱图
    public static final String MSCOLUMN3D = "MSColumn3D";  //多列3D柱图

    //--------------------线图----线图-----------------------//
    //--------------------线图----线图-----------------------//
    public static final String LINE = "Line";     //单线图
    public static final String MSLINE = "MSLine"; //多线图

    //--------------------面积图----面积图-----------------------//
    //--------------------面积图----面积图-----------------------//
    public static final String AREA2D = "Area2D";  //2D面积图
    public static final String MSAREA = "MSArea";  //多个2D面积图

    //--------------------组合图----组合图-----------------------//
    //--------------------组合图----组合图-----------------------//
    public static final String MSCOMBI2D = "MSCombi2D";  //2D组合图
    public static final String MSCOMBI3D = "MSCombi3D";  //3D组合图
    public static final String MSCOMBIDY2D = "MSCombiDY2D";  //2D组合图(column+line)

    //表格
    public static final String SSGRID = "SSGrid";  //Grid

    public static final String MSStackedColumn2DLineDY = "MSStackedColumn2DLineDY" ;  //组合图+平均线
    public static final String MSStackedColumn2D = "MSStackedColumn2D" ;  //组合图+平均线
    //单列数据的图类型，与 SingleChart数据对象配合使用
    public static final String[] SINGLE_TYPES = new String[]{SSGRID, LINE, PIE2D, PIE3D, BAR2D, AREA2D, COLUMN2D, COLUMN3D, DOUGHNUT2D, DOUGHNUT3D};
    //多列数据的图类型，与 MultiChart数据对象配合使用
    public static final String[] MULTI_TYPES = new String[]{BAR3D, MSCOMBI2D, MSCOMBI3D, MSCOMBIDY2D, MSBAR3D, MSBAR2D, MSCOLUMN3D, MSAREA, MSLINE, MSCOLUMN2D, MSCOLUMN3D};
    public static final String[] COMBINE_TYPES = new String[]{MSStackedColumn2DLineDY,MSStackedColumn2D} ;
    public static boolean isSingleChart(String type) {
        for (int i = 0; i < SINGLE_TYPES.length; i++) {
            if (SINGLE_TYPES[i].equals(type)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isMultiChart(String type) {
        for (int i = 0; i < MULTI_TYPES.length; i++) {
            if (MULTI_TYPES[i].equals(type)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isCombineChart(String type) {
        for (int i = 0; i < COMBINE_TYPES.length; i++) {
            if (COMBINE_TYPES[i].equals(type)) {
                return true;
            }
        }
        return false;
    }
}
