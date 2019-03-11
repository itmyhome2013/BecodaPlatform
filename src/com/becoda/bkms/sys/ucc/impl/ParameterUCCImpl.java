package com.becoda.bkms.sys.ucc.impl;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.pojo.bo.ParameterBO;
import com.becoda.bkms.sys.service.ParameterService;
import com.becoda.bkms.sys.ucc.IParameterUCC;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: huangh
 * Date: 2015-3-9
 * Time: 11:39:06
 * To change this template use File | Settings | File Templates.
 */
public class ParameterUCCImpl implements IParameterUCC {
    ParameterService parameterService;


    public void updateParameter(Map paraMap) throws BkmsException {
        parameterService.updateParameter(paraMap);
    }

    public void updateParameter(ParameterBO bo) throws BkmsException {
        parameterService.updateParameter(bo);
    }

    public Map syncParameterFromDB() throws BkmsException {
        return parameterService.syncParameterFromDB();

    }

    public ParameterService getParameterService() {
        return parameterService;
    }

    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    //新增参数
    public void createParameter(ParameterBO bo) throws BkmsException {
        try {
            ParameterBO pbo = (ParameterBO) parameterService.getParameterDAO().findBo(ParameterBO.class, bo.getKey());
            if (pbo != null) {
                if (bo.getKey().equals(pbo.getKey())) {
                    throw new BkmsException("参数名称不能相同", this.getClass());
                }
            }
            this.parameterService.createParameter(bo);
        } catch (RollbackableException re) {
            throw re;
        } catch (BkmsException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    //查询参数
    public List queryParameter() throws BkmsException {
        return this.parameterService.queryParameter();
    }

    //删除参数
    public void deleteParameter(String key) throws BkmsException {
        this.parameterService.deleteParameter(key);
    }

    //按条件查询参数
    public List queryParameter(String key, String value, String moduleName) throws BkmsException {
        return this.parameterService.queryParameter(key, value, moduleName);
    }
}
