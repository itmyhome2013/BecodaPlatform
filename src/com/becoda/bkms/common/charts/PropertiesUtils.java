package com.becoda.bkms.common.charts;

import java.util.Map;
import java.util.HashMap;

/**
 * User: Jair.Shaw
 * Date: 2015-7-4
 * Time: 22:36:24
 */
public class PropertiesUtils {

    public static Map parsePropertiesString(String propertiesString) {
        Map m = new HashMap();
        if (propertiesString != null && propertiesString.trim().length() > 0) {
            propertiesString = propertiesString.trim();
            String[] ps = propertiesString.split(",");
            for (int i = 0; i < ps.length; i++) {
                String p = ps[i];
                String[] kv = p.split("=");
                if (kv.length == 2) {
                    m.put(kv[0], kv[1]);
                }
            }
        }
        return m;
    }
}
