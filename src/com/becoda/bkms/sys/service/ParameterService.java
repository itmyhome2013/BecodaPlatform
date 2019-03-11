package com.becoda.bkms.sys.service;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.dao.ParameterDAO;
import com.becoda.bkms.sys.pojo.bo.ParameterBO;
import com.becoda.bkms.util.Tools;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: huangh
 * Date: 2015-3-9
 * Time: 11:21:14
 * To change this template use File | Settings | File Templates.
 */
public class ParameterService {
    private ParameterDAO parameterDAO;

    //查询参数
    public List queryParameter() throws RollbackableException {
        return parameterDAO.queryParameter();
    }

    //条件查询参数
    public List queryParameter(String key, String value, String moduleName) throws RollbackableException {
        return parameterDAO.queryParameter(key, value, moduleName);
    }

    public void deleteParameter(String key) throws RollbackableException {
        if (key == null || "".equals(key))
            return;
        try {
            Object bo = parameterDAO.findBo(ParameterBO.class, key);
            if (bo != null)
                parameterDAO.deleteBo(bo);
        } catch (Exception e) {
            throw new RollbackableException("无法删除参数！", e, getClass());
        }

    }

    public void updateParameter(Map paraMap) throws RollbackableException {
        if (paraMap == null || paraMap.size() <= 0)
            return;
        try {
            Iterator it = paraMap.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = (String) paraMap.get(key);
                ParameterBO bo = (ParameterBO) parameterDAO.findBoById(ParameterBO.class, key);
                if (bo == null) {
                    bo = new ParameterBO();
                    bo.setKey(key);
                    bo.setValue(value);
                    parameterDAO.createBo(bo);
                } else {
                    bo.setValue(value);
                    parameterDAO.updateBo(bo.getKey(), bo);
                }
            }
        } catch (Exception e) {
            throw new RollbackableException("无法保存参数", e, getClass());
        }
    }

    public void updateParameter(ParameterBO bo) throws RollbackableException {
        if (bo == null)
            return;
        try {
            parameterDAO.updateBo(bo.getKey(), bo);
        } catch (Exception e) {
            throw new RollbackableException("无法保存参数！", e, getClass());
        }
    }

    //新增参数
    public void createParameter(ParameterBO bo) throws RollbackableException {
        parameterDAO.createBo(bo);
    }

    public Map syncParameterFromDB() throws RollbackableException {
        Map hash = new TreeMap();
        try {
            Object o = Constants.class.newInstance();
            Field[] fields = Constants.class.getFields();

            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if (9 == f.getModifiers()) {
                    String key = f.getName();
                    ParameterBO bo = (ParameterBO) parameterDAO.findBoById(ParameterBO.class, key);
                    if (bo == null) {
                        hash.put(key, String.valueOf(f.get(o)));
                    } else {
                        hash.put(key, bo.getValue());
                        if (f.getType().getName().indexOf("int") != -1) {
                            f.setInt(o, Integer.parseInt(Tools.filterNullToZero(bo.getValue())));
                        } else if (f.getType().getName().indexOf("boolean") != -1) {
                            if (bo.getValue() == null)
                                bo.setValue("true");
                            f.setBoolean(o, Boolean.getBoolean(bo.getValue()));
                        } else
                            f.set(Constants.class.newInstance(), bo.getValue());
                    }
                }
            }
            return hash;
        } catch (Exception e) {
            throw new RollbackableException("同步系统参数失败", e, getClass());
        }
    }

    public ParameterDAO getParameterDAO() {
        return parameterDAO;
    }

    public void setParameterDAO(ParameterDAO parameterDAO) {
        this.parameterDAO = parameterDAO;
    }
}
