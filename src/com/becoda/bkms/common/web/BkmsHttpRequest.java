package com.becoda.bkms.common.web;


import com.becoda.bkms.util.Endecode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-2-20
 * Time: 10:38:36
 * To change this template use File | Settings | File Templates.
 */
public class BkmsHttpRequest extends HttpServletRequestWrapper {
    public BkmsHttpRequest(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);

    }
    /**
     * 取不经过转义的参数值
     * @param paraName pN
     * @return value
     */
    public String getOriginalParameter(String paraName) {
        return super.getParameter(paraName);
    }
    public String getParameter(String paraName) {
        String para = super.getParameter(paraName);
        String method = super.getMethod();

        if (para != null) {
            if ("GET".equalsIgnoreCase(method) && !"actPara".equals(paraName)) {
                para = Endecode.base64Decode(para);
            }
            if (!"QRYSQL_qrySql".equals(paraName) && !"QRYSQL_showField".equals(paraName) && !"QRYSQL_hash".equals(paraName) && !"groupShow".equals(paraName) && !"addedCondition".equals(paraName) && !"filterSQL".equals(paraName))
                para = para.replaceAll("<", "＜").replaceAll(">", "＞").replaceAll("'", "＇").replaceAll("\"", "＂");
        }
        return para;
    }

    public Map getParameterMap() {
        Enumeration key = super.getParameterNames();
        Map newMap = new HashMap();
        while (key.hasMoreElements()) {
            String name = (String) key.nextElement();
            String[] rs = super.getParameterValues(name);
            if (rs != null && rs.length == 1) {
                rs[0] = rs[0].replaceAll("<", "＜");
                rs[0] = rs[0].replaceAll(">", "＞");
                rs[0] = rs[0].replaceAll("'", "＇");
                rs[0] = rs[0].replaceAll("\"", "＂");
                newMap.put(name, rs[0]);
            } else {
                newMap.put(name, rs);
            }
        }
        return newMap;
    }

    public String[] getParameterValues(String name) {
        String[] rs = super.getParameterValues(name);
        if (rs != null && rs.length >= 0) {
            for (int i = 0; i < rs.length; i++) {
                rs[i] = rs[i].replaceAll("<", "＜").replaceAll(">", "＞").replaceAll("'", "＇").replaceAll("\"", "＂");
            }
        }
        return rs;
    }
}
