package com.becoda.bkms.run.util;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.util.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-4-27
 * Time: 10:23:57
 * To change this template use File | Settings | File Templates.
 */
public class RunTools {
    public static String buildQuerySql(User user) throws BkmsException {
        try {
            //处理机构范围条件
            String scaleField = getScaleField("A", "ORG");
            String scale = "";
            //得到权限的查询范围
            if (user != null) {
                PmsAPI api = new PmsAPI();
                boolean flag = false;
                boolean isOper = false;// 查询权限
                scale = api.getPersonSimpleScaleCondition(user, "A001738", "A001054", isOper);
//                scale = api.getScaleConditionByType(user, scaleField, getCardeField("A", "ORG"), getPerTypeField("A", "ORG"), "A", isOper, flag, false, false);
            }
            return scale;
        } catch (Exception e) {
            throw new BkmsException("获得登录用户权限的查询范围失败", e, RunTools.class);
        }
    }

    public static String getScaleField(String setType, String unitType) {
        if ("A".equals(setType) && "ORG".equals(unitType)) {
            return "A001738";
        } else if ("A".equals(setType) && "PARTY".equals(unitType)) {
            return "A001740";
        } else if ("A".equals(setType) && "WAGE".equals(unitType)) {
            return "A815700";
        } else if ("B".equals(setType)) {
            return "B001003";
        } else if ("C".equals(setType)) {
            return "C001701";
        } else if ("D".equals(setType)) {
            return "D001003";
        }
        return "A001738";
    }


    public static String getPerTypeField(String setType, String unitType) {
        if ("A".equals(setType) && "ORG".equals(unitType)) {
            return "A001054";
        }
        return null;
    }

    /**
     * 解析where条件中的日期串 格式：[当前日期-20],[当前年份-20]
     *
     * @return 返回运算后的日期字符串
     */
    public static String parseDate(String currentDate, String strSql) {

        String resultDate = "";
        String strVar = strSql.substring(0, 4);
        if (strSql.length() == 4) {//即 strSql="当前日期”的时候 ;
            return currentDate;
        }
        String strOperator = strSql.substring(4, 5);
        String strValue = strSql.substring(5, strSql.length());

        switch (strOperator.charAt(0)) {
            case '+':
                if ("当前日期".equalsIgnoreCase(strVar)) {
                    resultDate = Tools.plusMinusDay(currentDate, Integer.parseInt(strValue));
                } else if ("当前年份".equalsIgnoreCase(strVar))
                    resultDate = Tools.plusMinusDay(currentDate, Integer.parseInt(strValue) * 365);
                else if ("当前月份".equalsIgnoreCase(strVar)) {
                    int month = Integer.parseInt(strValue) % 12;
                    int year = Integer.parseInt(strValue) / 12;
                    resultDate = Tools.plusMinusDay(currentDate, year * 365 + month * 30);
                } else
                    return "";
                break;
            case '-':
                if ("当前日期".equalsIgnoreCase(strVar)) {
                    resultDate = Tools.plusMinusDay(currentDate, -Integer.parseInt(strValue));
                } else if ("当前年份".equalsIgnoreCase(strVar))
                    resultDate = Tools.plusMinusDay(currentDate, -Integer.parseInt(strValue) * 365);
                else if ("当前月份".equalsIgnoreCase(strVar)) {
                    int month = Integer.parseInt(strValue) % 12;
                    int year = Integer.parseInt(strValue) / 12;
                    resultDate = Tools.plusMinusDay(currentDate, (year * 365 + month * 30) * (-1));
                } else
                    return "";
                break;
            default:
                return "";
        }
        return resultDate;
    }

    public static String chSql(String strSql) {
        try {
            String currentDate = Tools.getSysDate("yyyy-MM-dd");
            String strResult = strSql, strDate = "", strT = "", strTemp = "";
            int start, end;
            while (strResult.indexOf("[") != -1) {
                start = strResult.indexOf("[");
                end = strResult.indexOf("]");
                strTemp = strTemp + strResult.substring(0, start - 1);
                strDate = strResult.substring(start + 1, end);
                strT = RunTools.parseDate(currentDate, strDate);
                if ("".equalsIgnoreCase(strT)) {//解析出错
                    strSql = "";
                    break;
                }
                strTemp = strTemp + "'" + strT + "'";
                strResult = strResult.substring(end + 2, strResult.length());
            }
            if ("".equalsIgnoreCase(strSql))
                return "";    //解析日期出错
            else {
                strSql = strTemp + strResult;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strSql;  //完整sql
    }

    public static String ISO2GBK(String strvalue) {
        try {
            if (strvalue == null) {
                return null;
            } else {
                strvalue = new String(strvalue.getBytes("ISO-8859-1"), "GBK");
                return strvalue;
            }
        } catch (Exception e) {
            System.out.println("RunTools.toChinese: " + e.getMessage());
            return null;
        }
    }

    public static String GBK2ISO(String strvalue) {
        try {
            if (strvalue == null) {
                return null;
            } else {
                strvalue = new String(strvalue.getBytes("GBK"), "ISO-8859-1");
                return strvalue;
            }
        } catch (Exception e) {
            System.out.println("RunTools.toISO8859: " + e.getMessage());
            return null;
        }
    }

    public static java.sql.Date getDate2() {
        java.sql.Date sd;
        java.util.Date ud = new Date();
        sd = new java.sql.Date(ud.getTime());
        return sd;
    }

    public static String getDate10() {
        //得到当前日期字符串yyyy-MM-dd格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getDate8() {
        //得到当前日期字符串 yyyyMMdd格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String getDateLong() {
        //得到当前日期字符串 yyyy年MM月dd日格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
    }

    public static String getTime() {
        //得到当前时间字符串 HH:mm:ss格式
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String DatetoString10(Date date) {
        //得到日期字符串 yyyy-MM-dd格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);

    }

    /**
     * 将字符串转换成一个日期型
     *
     * @param startDate
     */
    public static Date String10toDate(String startDate) {
        Date dt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dt = sdf.parse(startDate);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String toDate8(String kdate) {
        //得到当前日期字符串 yyyyMMdd格式
        //Date dt=new Date(kdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
        //myDate = df.parse(myString);
        try {
            java.util.Date dt = sdf.parse(kdate);
            return sdf.format(dt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String getConfirmStartDay() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月25日");
        String s = sdf.format(date);
        return s;
    }

    public static String getConfirmEndDay() {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(GregorianCalendar.MONTH, 1);
        gc.add(GregorianCalendar.DATE, -date.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date dateTemp = gc.getTime();
        String s = sdf.format(dateTemp);
        return s;
    }
}
