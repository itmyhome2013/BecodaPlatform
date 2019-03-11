package com.becoda.bkms.util;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.pojo.vo.User;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Iterator;
import java.util.Date;

/**
 * User: Jair.Shaw
 * Date: 2015-3-11
 * Time: 10:38:04
 */
public class Print {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    public static final NumberFormat NURMAL_NUMBER_FORMAT = NumberFormat.getNumberInstance();
    static {
        NUMBER_FORMAT.setGroupingUsed(false);
        NUMBER_FORMAT.setMaximumFractionDigits(10);
        NUMBER_FORMAT.setMaximumIntegerDigits(17);
        NURMAL_NUMBER_FORMAT.setGroupingUsed(false);
        NURMAL_NUMBER_FORMAT.setMaximumFractionDigits(2);
        NURMAL_NUMBER_FORMAT.setMaximumIntegerDigits(10);
    }

    public static String options(int start,int end) {
        StringBuffer sb = new StringBuffer();
        for (int i = start; i <= end; i++) {
            sb.append("<option value=\"").append(i).append("\">").append(i).append("</option>");
        }
        return sb.toString();
    }


    
    public static String options(int start,int end,int selectedIndex) {
        StringBuffer sb = new StringBuffer();
        for (int i = start; i <= end; i++) {
            boolean selected = i == selectedIndex;
            String selectedStr = selected ? "selected" : "";
            sb.append("<option value=\"").append(i).append("\" ").append(selectedStr).append(">").append(i).append("</option>");
        }
        return sb.toString();
    }

    public static String options(int start,int end,String selectedIndex) {
        int si = selectedIndex == null || !Tools.isNum(selectedIndex) ? -1 : Integer.parseInt(selectedIndex);
        return options(start, end, si);
    }
    
    public static String options(Map m) {
        if (m == null || m.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            Object v = m.get(o);
            v = v == null ? "" : v.toString();
            sb.append("<option value=\"").append(o.toString()).append("\">").append(v.toString()).append("</option>");
        }
        return sb.toString();
    }
    public static String options(Map m,String value) {
        if (m == null || m.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            Object v = m.get(o);
            v = v == null ? "" : v.toString();
            boolean selected = value.equals(o);
            String selectedStr = selected ? "selected" : "";
            sb.append("<option value=\"").append(o).append("\" ").append(selectedStr).append(">").append(v.toString()).append("</option>");
        }
        return sb.toString();
    }

    public static String percent(double d) {
        d *= 100;
        d = Arith.round(d, 2);
        return new StringBuffer(str(d)).append("%").toString();
    }

    public static String str(double d) {
        return NUMBER_FORMAT.format(d);
    }
    public static String strn(double d) {
        return NURMAL_NUMBER_FORMAT.format(d);
    }
//    public static String str(float d) {
//        return NUMBER_FORMAT.format(d);
//    }

    public static String strD10000(double d) {
        d = Arith.round(d / 10000,4);
        return NUMBER_FORMAT.format(d);
    }

    public static String str(Date d) {
        return DATE_FORMAT.format(d);
    }

    public static String strM100(double loanInsuranceRate) {
        return str(Arith.round(loanInsuranceRate * 100, 2));
    }
    
    public static String orgId(String paraName, HttpServletRequest request) {
        String orgId = Tools.filterNull(request.getParameter(paraName));
        if (orgId == null ||orgId .trim().length() == 0) {
            User user = (User) request.getSession().getAttribute(Constants.USER_INFO);
            if (user != null) {
                orgId = user.getOrgId();
            }
        }
        return orgId;
    }
}