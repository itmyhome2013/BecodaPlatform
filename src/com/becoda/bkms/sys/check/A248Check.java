package com.becoda.bkms.sys.check;

import com.becoda.bkms.util.Tools;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-5
 * Time: 14:18:22
 * To change this template use File | Settings | File Templates.
 */
public class A248Check extends AbstractCheck {
    public String doCheck(Map dataMap, String setId) {
        String info = "";
        String A248201 = Tools.filterNull((String) dataMap.get("A248201"));//应休
        String A248202 = Tools.filterNull((String) dataMap.get("A248202"));//剩余
        String A248203 = Tools.filterNull((String) dataMap.get("A248203"));//已休

        double d1 = Double.parseDouble(A248201);
        double d2 = Double.parseDouble(A248202);
        double d3 = Double.parseDouble(A248203);

        if ((d2 + d3) != d1) {
            info += "请保证：剩余+已休=应休!\n";
        }
        return info;
    }
}

