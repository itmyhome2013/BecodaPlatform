package com.becoda.bkms.sys.util;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-14
 * Time: 18:12:37
 * To change this template use File | Settings | File Templates.
 */
public class SysUtil {
    public static String getHtmlDataType(String type) {
        int i = Integer.parseInt(type);
        String re = "s";
        switch (i) {
            case 1:
                re = "i";
                break;
            case 2:
                re = "f";
                break;
            case 3:
                re = "s";
                break;
            case 4:
                re = "s";
                break;
            case 5:
                re = "d";
                break;
            case 6:
                re = "code";
                break;
            case 7:
                re = "d6";
                break;
            case 8:
                re = "info";
                break;
            case 9:
                re = "compute";
                break;
            case 10:
                re = "clob";
                break;
            case 11:
                re = "attach";
                break;
            case 12:
                re = "imag";
                break;
        }
        return re;
    }
}
