package com.becoda.bkms.common.dao;

import com.becoda.bkms.common.exception.RollbackableException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-06-09
 * Time: 17:51:50
 * To change this template use File | Settings | File Templates.
 */
public interface BkmsJdbcOperations {
    public List namedQuery(String sqlName, Object[] objs) throws RollbackableException;

    public List namedExecute(String sqlName, Object[] objs) throws RollbackableException;
}
