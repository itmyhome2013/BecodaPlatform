package com.becoda.bkms.util;

import org.apache.commons.collections.map.ListOrderedMap;

import java.util.HashMap;

/**
 * User: kangdw
 * Date: 2015-7-19
 * Time: 11:05:05
 */
public class ListMap extends ListOrderedMap {
    public ListMap() {
        super(new HashMap());
    }
}
