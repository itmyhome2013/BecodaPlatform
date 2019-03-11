package com.becoda.bkms.util;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-6-5
 * Time: 15:16:44
 * To change this template use File | Settings | File Templates.
 */
public class BkmsContext implements ApplicationContextAware {
    private static ApplicationContext appContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public static Object getBean(String beanId) throws BkmsException {
        try {
            return appContext.getBean(beanId);
        } catch (Exception e) {
            throw new RollbackableException("创建bean实例错误", e, BkmsContext.class);
        }
    }
}
