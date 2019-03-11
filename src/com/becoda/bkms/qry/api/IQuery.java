package com.becoda.bkms.qry.api;

import com.becoda.bkms.common.exception.BkmsException;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: ye
 * Date: 2015-4-3
 * Time: 14:48:07
 * To change this template use File | Settings | File Templates.
 */
public interface IQuery {
    public Hashtable findSQL(String qryId) throws BkmsException;
}
