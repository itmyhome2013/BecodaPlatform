package com.becoda.bkms.sys.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: huangh
 * Date: 2015-3-9
 * Time: 11:23:48
 * To change this template use File | Settings | File Templates.
 */
public class ParameterDAO extends GenericDAO {

    public List queryParameter(String key, String value, String moduleName) throws RollbackableException {
        try {
            String strHql = "from ParameterBO o where 1=1 ";
            StringBuffer where = new StringBuffer();
            where.append(key == null || "".equals(key) ? "" :
                    " and o.key like'" + key + "%'");
            where.append(value == null || "".equals(value) ? "" :
                    " and o.value like'" + value + "%'");
            where.append(moduleName == null || "".equals(moduleName) ? "" :
                    " and o.moduleName like'" + moduleName + "%'");
            strHql = strHql + " " + where.toString() + " order by o.moduleName,o.key";
            List list = hibernateTemplate.find(strHql);
            return list;
        } catch (Exception e) {
            throw new RollbackableException("查询参数失败", e, this.getClass());
        }
    }

    public List queryParameter() throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from ParameterBO o order by o.moduleName,o.key");
            return list;
        } catch (Exception e) {
            throw new RollbackableException("查询参数失败", e, this.getClass());
        }

    }
}
