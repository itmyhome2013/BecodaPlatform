package com.becoda.bkms.sys.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.pojo.bo.ParameterBO;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: huangh
 * Date: 2015-3-9
 * Time: 11:37:27
 * To change this template use File | Settings | File Templates.
 */
public interface IParameterUCC {
    public void updateParameter(Map paraMap) throws BkmsException;

    public void updateParameter(ParameterBO bo) throws BkmsException;

    public Map syncParameterFromDB() throws BkmsException;

    public void createParameter(ParameterBO bo) throws BkmsException;

    public List queryParameter() throws BkmsException;

    //删除参数
    public void deleteParameter(String key) throws BkmsException;

    public List queryParameter(String key, String value, String moduleName) throws BkmsException;
}
