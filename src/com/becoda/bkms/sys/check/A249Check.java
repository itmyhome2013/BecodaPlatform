package com.becoda.bkms.sys.check;

import com.becoda.bkms.util.Tools;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-5
 * Time: 14:22:38
 * To change this template use File | Settings | File Templates.
 */
public class A249Check extends AbstractCheck {
    public String doCheck(Map dataMap, String setId) {
        String info = "";
        String A249201 = Tools.filterNull((String) dataMap.get("A249201"));//应休
        String A249202 = Tools.filterNull((String) dataMap.get("A249202"));//剩余
        String A249207 = Tools.filterNull((String) dataMap.get("A249207"));//已休


        double d1 = Double.parseDouble(A249201);
        double d2 = Double.parseDouble(A249202);
        double d3 = Double.parseDouble(A249207);

        if ((d2 + d3) != d1) {
            info += "请保证：剩余+已休=应休!\n";
        }
        return info;
    }
}
