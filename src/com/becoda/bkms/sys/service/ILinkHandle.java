package com.becoda.bkms.sys.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.pojo.vo.TableVO;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-12
 * Time: 13:51:22
 * To change this template use File | Settings | File Templates.
 */

public interface ILinkHandle {
    public void whenAdd(TableVO table) throws BkmsException;

    public void whenUpdate(TableVO table, String[] oldValue) throws BkmsException;

    public void whenDel(String setId, String pkValue, String fkValue) throws BkmsException;
}

