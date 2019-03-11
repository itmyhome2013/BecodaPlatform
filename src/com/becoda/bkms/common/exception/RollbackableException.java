package com.becoda.bkms.common.exception;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-14
 * Time: 16:35:20
 * To change this template use File | Settings | File Templates.
 */
public class RollbackableException extends BkmsException {
    Logger log = Logger.getLogger(RollbackableException.class);

    public RollbackableException(Throwable e, Class clazz) {
        super(e, clazz);
    }

    public RollbackableException(String message, Exception e, Class clazz) {
        super(message, e, clazz);
    }

    public RollbackableException(String message, Class clazz) {
        super(message, clazz);
    }
}

