package com.becoda.bkms.emp.util;

import java.util.HashMap;
import java.util.Map;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-2
 * Time: 13:20:41
 */
public class TransferUtils {
    public static Map arrayToMap(String[] items, String[] values) {
        Map map = new HashMap();
        if (items != null && values != null && items.length == values.length) {
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                map.put(item, values[i]);
            }
        }
        return map;
    }
}
