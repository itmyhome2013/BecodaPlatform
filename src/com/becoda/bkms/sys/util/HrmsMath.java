package com.becoda.bkms.sys.util;

import bsh.EvalError;
import bsh.Interpreter;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.Tools;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-12
 * Time: 13:16:06
 * To change this template use File | Settings | File Templates.
 */
public class HrmsMath {
    public static String sumForStr(String s1, String s2) {
        s1=Tools.filterNullToZero(s1);
        s2=Tools.filterNullToZero(s2);
        return new Integer(Integer.parseInt(s1) + Integer.parseInt(s2)).toString();
    }

    public static String minusForStr(String s1, String s2) {
        s1=Tools.filterNullToZero(s1);
        s2=Tools.filterNullToZero(s2);
        return new Integer(Integer.parseInt(s1) - Integer.parseInt(s2)).toString();
    }

    public static String plusStr(String s1, String s2) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";
        return s1 + s2;
    }

    public static String genYearMonth(String year, String month) {
        if (year == null) year = Tools.getSysDate("yyyy");
        if (month == null) month = Tools.getSysDate("MM");
        if (month.length() == 1) {
            month = "0" + month;
        }
        return year + "-" + month;
    }

    /**
     * 年龄计算公式：年龄＝IF MONTH(~人员基本情况.出生日期~) < MONTH(TODAY())   ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~)ELSE  IF MONTH(~人员基本情况.出生日期~) > MONTH(TODAY())     ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~) - 1  ELSE     IF DAY(~人员基本情况.出生日期~) <= DAY(TODAY())        ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~)     ELSE        ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~) - 1     ENDIF   ENDIFENDIF
     *
     * @param birth
     * @return
     */
    public static String age(String birth) {
        String age = "";
        if (birth == null || "".equals(birth.trim())) {
            return age;
        }
        String dt = Tools.getSysDate("yyyy-MM-dd");
        String[] curday = dt.split("-");
        String[] birthday = birth.split("-");

        if (Integer.parseInt(birthday[1]) < Integer.parseInt(curday[1])) {//MM<
            age = String.valueOf(Integer.parseInt(curday[0]) - Integer.parseInt(birthday[0]));
        } else {
            if (Integer.parseInt(birthday[1]) > Integer.parseInt(curday[1])) {//MM>
                age = String.valueOf(Integer.parseInt(curday[0]) - Integer.parseInt(birthday[0]) - 1);
            } else {//MM==
                if (Integer.parseInt(birthday[2]) <= Integer.parseInt(curday[2])) {//dd<
                    age = String.valueOf(Integer.parseInt(curday[0]) - Integer.parseInt(birthday[0]));
                } else {//dd>=
                    age = String.valueOf(Integer.parseInt(curday[0]) - Integer.parseInt(birthday[0]) - 1);
                }
            }
        }
        return age;
    }

    /**
     * 年龄计算公式：年龄＝IF MONTH(~人员基本情况.出生日期~) < MONTH(TODAY())   ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~)ELSE  IF MONTH(~人员基本情况.出生日期~) > MONTH(TODAY())     ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~) - 1  ELSE     IF DAY(~人员基本情况.出生日期~) <= DAY(TODAY())        ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~)     ELSE        ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~) - 1     ENDIF   ENDIFENDIF
     *
     * @param birth
     * @return
     */
    public static String ageByMonth(String birth) {
        String age = "";
        if (birth == null || "".equals(birth.trim())) {
            return age;
        }
        String dt = Tools.getSysDate("yyyy-MM-dd");
        String[] curday = dt.split("-");
        String[] birthday = birth.split("-");

        if (Integer.parseInt(birthday[1]) <= Integer.parseInt(curday[1])) {//MM<
            age = String.valueOf(Integer.parseInt(curday[0]) - Integer.parseInt(birthday[0]));
        } else {
            if (Integer.parseInt(birthday[1]) > Integer.parseInt(curday[1])) {//MM>
                age = String.valueOf(Integer.parseInt(curday[0]) - Integer.parseInt(birthday[0]) - 1);
            } else {//MM==

            }
        }
        return age;
    }

    /**
     * 系统自动计算（时间计划）计算公式：年龄＝（当前日期－出生日期）天数/365   （精确计算到小数位1位）
     */
    public static String ageExact(String birth) {
        String age = "";
        if (birth == null || "".equals(birth.trim())) {
            return age;
        }
        double val = 0;
        try {
            String curdate = Tools.getSysDate("yyyy-MM-dd");
            String curYear = Tools.getSysDate("yyyy");
            String birthYear = Tools.getDateByFormat(birth, "yyyy");
            val = Integer.parseInt(curYear) - Integer.parseInt(birthYear);
            double val2 = Tools.betweenDays(curdate, curYear + birth.substring(4)) / 365.0;
            val = val + val2;
        } catch (Exception e) {
            new BkmsException("计算年龄错误", e, HrmsMath.class);
        }
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        age = format.format(val);
        return age;
    }


    /**
     * 计算公式 (四则运算)
     */
    public static String ruleCompute(String formula) throws BkmsException {
        //四则运算
        Interpreter exp = new Interpreter();
        try {
            double rs = Double.parseDouble(exp.eval(formula).toString());
            rs = Math.round(rs * 1000.0) / 1000.0;
            return rs + "";
        } catch (EvalError evalError) {
            throw new BkmsException("表达式或者数据错误，请检查", evalError, HrmsMath.class);
        }
    }

    /**
     * 计算工龄
     *
     * @param workdate
     * @return
     */
    public static String workYears(String workdate, int base) {
        try {
            if (workdate == null || "".equals(workdate)) {
                workdate = Tools.getSysDate("yyyy-MM-dd");
            }

            String curYear = Tools.getSysDate("yyyy");
            String workYear = workdate.substring(0, 4);
            int year = Integer.parseInt(curYear) - Integer.parseInt(workYear) + 1 + base;
            return "" + year;
        } catch (Exception e) {
            new BkmsException("计算工龄", e, HrmsMath.class);
        }
        return "0";
    }

    /**
     * 计算行龄
     *
     * @param date
     * @return
     */
    public static String bankWorkYears(String date) {

        try {
            if (date == null || "".equals(date)) {
                date = Tools.getSysDate("yyyy-MM-dd");
            }
            String curYear = Tools.getSysDate("yyyy");
            String workYear = date.substring(0, 4);
            int year = Integer.parseInt(curYear) - Integer.parseInt(workYear);
            return "" + year;

        } catch (Exception e) {
            new BkmsException("计算单位工龄失败", e, HrmsMath.class);
        }
        return "0";
    }


    /**
     * 日期加上月份
     *
     * @param date   8位日期
     * @param months
     * @return result
     */
    public static String plusMonth(String date, String months) {
        String result = "";
        int monthss = Integer.parseInt(months);
        if (Tools.filterNull(date).equals(""))
            return "";
        int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
        //int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.length()));
        int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3));
        int day = Integer.parseInt(date.substring(date.indexOf("-", 7) + 1, date.indexOf("-", 7) + 3));
        GregorianCalendar firstFlight = new GregorianCalendar(year, month, day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        firstFlight.add(GregorianCalendar.MONTH, monthss - 1);
        Date d = firstFlight.getTime();
        result = formatter.format(d);
        return result;
    }
}
