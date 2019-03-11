package com.becoda.bkms.emp.util;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-16
 * Time: 15:21:47
 * To change this template use File | Settings | File Templates.
 */
public class PersonTool {

//    public static String ORG_EDIT_PRIVILEGE = "0320";
//    public static String PARTY_EDIT_PRIVILEGE = "0321";
    //    public static String GROUP_EDIT_PRIVILEGE = "0305";
    //    public static String LABOUR_EDIT_PRIVILEGE = "0309";
    public static String ORG_EDIT_PRIVILEGE = "0318";
    public static String PARTY_EDIT_PRIVILEGE = "0319";
    public static String GROUP_EDIT_PRIVILEGE = "0320";
    public static String LABOUR_EDIT_PRIVILEGE = "0321";


    public static String personType_Contract = "013511";
    public static String personType_Surrogate = "013513";

    //调配原因
    public static String adjReason_Gjgwlg = "0210000269";
    public static String adjReason_Gbjl = "02102";


    public static String getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    public static String getPerName(String perId) {
        Person bo = SysCacheTool.findPersonById(perId);
        if (bo != null) {
            return bo.getName();
        }
        return "";
    }

    public static HashMap setTable(TableVO table, String[] userId) {
        throw new RuntimeException("方法未实现");
//        HashMap mymap = new HashMap();
//        int k = userId.length;
//        int m = table.getRowData().length;
//        RecordVO re;
//        String fk = "";
//        if (table.getSetFk() == null) table.setSetFk(table.getSetPk());
//        for (int j = 0; j < k; j++) {
//            mymap.put(userId[j], null);
//        }
//        for (int i = 0; i < m; i++) {
//            re = table.getRowData()[i];
//            fk = ((CellVO) re.cellArray2Hash().get(table.getSetFk())).getValue();
//            mymap.put(fk, re);
//        }
//        return mymap;
    }

    /**
     * 计算（时间计划）计算公式：两段时间的间隔年数
     */

    public static String year(String date1, String date2) {
        String age = "0";
        if (date1 == null || "".equals(date1.trim()) || date2 == null || "".equals(date2.trim())) {
            return age;
        }
//        String dt1 = Tools.getSysDate("yyyy-MM-dd");
        String[] dt1 = date1.split("-");
        String[] dt2 = date2.split("-");

        if (Integer.parseInt(dt1[1]) < Integer.parseInt(dt2[1])) {//MM<
            age = String.valueOf(Integer.parseInt(dt2[0]) - Integer.parseInt(dt1[0]));
        } else {
            if (Integer.parseInt(dt1[1]) > Integer.parseInt(dt2[1])) {//MM>
                age = String.valueOf(Integer.parseInt(dt2[0]) - Integer.parseInt(dt1[0]) - 1);
            } else {//MM==
                if (Integer.parseInt(dt1[2]) <= Integer.parseInt(dt2[2])) {//dd<
                    age = String.valueOf(Integer.parseInt(dt2[0]) - Integer.parseInt(dt1[0]));
                } else {//dd>=
                    age = String.valueOf(Integer.parseInt(dt2[0]) - Integer.parseInt(dt1[0]) - 1);
                }
            }
        }
        return age;
    }

    /**
     * 计算工龄        在职人员 type=1;非1月份离退人员 2；1月份离退人员3；
     *
     * @param workdate
     * @return
     */
    public static String workYears(String workdate, String retier, String type) {
        try {
            if (workdate == null || "".equals(workdate)) {
                workdate = Tools.getSysDate("yyyy-MM-dd");
            }
            if ("2".equals(type) || "3".equals(type)) {
                if (retier == null || "".equals(retier))
                    retier = Tools.getSysDate("yyyy-MM-dd");
            }

            String curYear = Tools.getSysDate("yyyy");
            String workYear = workdate.substring(0, 4);
            String retireYear = "";
            if (retier != null && !"".equals(retier))
                retireYear = retier.substring(0, 4);
            if ("1".equals(type)) {
                int year = Integer.parseInt(curYear) - Integer.parseInt(workYear) + 1;
                return "" + year;
            } else if ("2".equals(type)) {
                int year = Integer.parseInt(retireYear) - Integer.parseInt(workYear) + 1;
                return "" + year;
            } else if ("3".equals(type)) {
                int year = Integer.parseInt(retireYear) - Integer.parseInt(workYear);
                return "" + year;
            }


        } catch (Exception e) {
            new BkmsException("计算工龄", e, PersonTool.class);
        }
        return "0";
    }

    /**
     * 计算金融从业年限
     *
     * @param financeDate    金融从业日期 ,yyyy-MM 格式
     * @param finYearsReduce 金融工作年限扣除值(年)
     * @return **年**月     (年份相减，月份相减)
     */
    public static String financeYears(String financeDate, int finYearsReduce) {
        String financeYears = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date financeD = sdf.parse(financeDate);
            Calendar nowC = Calendar.getInstance();
            Calendar financeC = Calendar.getInstance();
            financeC.setTime(financeD);

            int diffY = nowC.get(Calendar.YEAR) - financeC.get(Calendar.YEAR);
            int diffM = nowC.get(Calendar.MONTH) - financeC.get(Calendar.MONTH);
            int months = diffY * 12 + diffM - finYearsReduce * 12;
            int year = months / 12;
            int month = months % 12;
            financeYears += year + "年";
            if (month != 0) {
                financeYears += month + "月";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return financeYears;
    }

    /**
     * 计算行龄
     *
     * @param date
     * @return
     */
    public static String bankWorkYears(String date, String type) {

        try {
            if (date == null || "".equals(date)) {
                date = Tools.getSysDate("yyyy-MM-dd");
            }
            String curYear = Tools.getSysDate("yyyy");
            String workYear = date.substring(0, 4);
            int year = Integer.parseInt(curYear) - Integer.parseInt(workYear);
            return "" + year;

        } catch (Exception e) {
            new BkmsException("计算单位工龄失败", e, PersonTool.class);
        }
        return "0";
    }

    /**
     * 通过职称得到职称级别
     *
     * @param zhc
     * @return
     */

    public static String getZhcJb(String zhc) {
        String zhcJb = "";
        try {
            if (zhc == null || "".equals(zhc)) {
                return "";
            }
            if ("3028000398".equals(zhc) || "3028000403".equals(zhc) || "3028000408".equals(zhc) || "3028000413".equals(zhc)
                    || "3028000418".equals(zhc) || "3028000423".equals(zhc) || "3028000428".equals(zhc) ||
                    "3028000433".equals(zhc) || "3028000434".equals(zhc))
                zhcJb = "3029000242";
            else
            if ("3028000399".equals(zhc) || "3028000404".equals(zhc) || "3028000409".equals(zhc) || "3028000414".equals(zhc)
                    || "3028000419".equals(zhc) || "3028000424".equals(zhc) || "3028000429".equals(zhc) ||
                    "3028000435".equals(zhc))
                zhcJb = "3029000243";
            else
            if ("3028000400".equals(zhc) || "3028000405".equals(zhc) || "3028000410".equals(zhc) || "3028000415".equals(zhc)
                    || "3028000420".equals(zhc) || "3028000425".equals(zhc) || "3028000430".equals(zhc) ||
                    "3028000436".equals(zhc))
                zhcJb = "3029000244";
            else
            if ("3028000401".equals(zhc) || "3028000406".equals(zhc) || "3028000411".equals(zhc) || "3028000416".equals(zhc)
                    || "3028000421".equals(zhc) || "3028000426".equals(zhc) || "3028000431".equals(zhc) ||
                    "3028000437".equals(zhc))
                zhcJb = "3029000245";
        } catch (Exception e) {
            new BkmsException("职称得到职称级别失败", e, PersonTool.class);
        }
        return zhcJb;
    }

    /**
     * 通过职称得到职称专业
     *
     * @param zhc
     * @return
     */
    public static String getZhcZhy(String zhc) {
        String zhcZhy = "";
        try {
            if (zhc == null || "".equals(zhc)) {
                return "";
            }
            if ("3028000398".equals(zhc) || "3028000399".equals(zhc) || "3028000400".equals(zhc) || "3028000401".equals(zhc))
                zhcZhy = "3059000438";
            else
            if ("3028000403".equals(zhc) || "3028000404".equals(zhc) || "3028000405".equals(zhc) || "3028000406".equals(zhc)
                    )
                zhcZhy = "3059000439";
            else
            if ("3028000408".equals(zhc) || "3028000409".equals(zhc) || "3028000410".equals(zhc) || "3028000411".equals(zhc)
                    )
                zhcZhy = "3059000440";

            else
            if ("3028000413".equals(zhc) || "3028000414".equals(zhc) || "3028000415".equals(zhc) || "3028000416".equals(zhc)
                    )
                zhcZhy = "3059000441";

            else
            if ("3028000418".equals(zhc) || "3028000419".equals(zhc) || "3028000420".equals(zhc) || "3028000421".equals(zhc)
                    )
                zhcZhy = "3059000442";
            else
            if ("3028000423".equals(zhc) || "3028000424".equals(zhc) || "3028000425".equals(zhc) || "3028000426".equals(zhc)
                    )
                zhcZhy = "3059000443";
            else
            if ("3028000428".equals(zhc) || "3028000429".equals(zhc) || "3028000430".equals(zhc) || "3028000431".equals(zhc)
                    )
                zhcZhy = "3059000444";
            else
            if ("3028000433".equals(zhc) || "3028000434".equals(zhc) || "3028000435".equals(zhc) || "3028000436".equals(zhc)
                    )
                zhcZhy = "3059000445";
        } catch (Exception e) {
            new BkmsException("通过职称得到职称专业失败", e, PersonTool.class);
        }
        return zhcZhy;
    }


}
