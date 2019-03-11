package com.becoda.bkms.sys.check;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.BkmsContext;
import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-4-12
 * Time: 10:01:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCheck {
    protected ActivePageService aps;

    public String runCheck(Map dataMap, String setId) throws BkmsException {
        String str = "";
        try {
            aps = (ActivePageService) BkmsContext.getBean("sys_activePageService");
            Map map = new HashMap();
            if (dataMap != null) {
                Iterator it = dataMap.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String v[] = (String[]) dataMap.get(key);
                    if (v != null) {
                        map.put(key, v[0]);
                    }
                }
            }
            str = doCheck(map, setId);
        } catch (HibernateException e) {

        } finally {

        }
        return str;
    }

    protected abstract String doCheck(Map dataMap, String setId);


}
