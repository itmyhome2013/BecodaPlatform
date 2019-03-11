package com.becoda.bkms.common.dao.impl;

import com.becoda.bkms.common.dao.HrmsJdbcTemplate;
import com.becoda.bkms.common.exception.RollbackableException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-24
 * Time: 10:43:06
 */
public class HrmsJdbcTemplateImpl extends JdbcTemplate implements HrmsJdbcTemplate {

    public List namedQuery(String sqlName, Object[] objs) throws RollbackableException {
        return null;
    }

    public List namedExecute(String sqlName, Object[] objs) throws RollbackableException {
        return null;
    }
}
