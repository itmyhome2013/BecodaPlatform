package com.becoda.bkms.common.dao.impl;

import com.becoda.bkms.common.dao.BkmsJdbcTemplate;
import com.becoda.bkms.common.exception.RollbackableException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-6-09
 * Time: 10:43:06
 */
public class BkmsJdbcTemplateImpl extends JdbcTemplate implements BkmsJdbcTemplate {

    public List namedQuery(String sqlName, Object[] objs) throws RollbackableException {
        return null;
    }

    public List namedExecute(String sqlName, Object[] objs) throws RollbackableException {
        return null;
    }
}
