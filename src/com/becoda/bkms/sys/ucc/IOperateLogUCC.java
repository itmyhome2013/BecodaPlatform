package com.becoda.bkms.sys.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;

import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-19
 * Time: 15:13:38
 */
public interface IOperateLogUCC {
    public List queryOperLog(String start, String end, String operName, String userName, String operType, String orgId, PageVO vo) throws BkmsException;

    public void deleteAndExpOperLog(String[] operId, int fileType, String rootPath) throws BkmsException;


    public String[] getDiskLogFile(String rootPath);

    public void delDiskLogFile(String[] fileName, String rootPath);

    public OperLogBO[] query(OperLogBO logLog) throws BkmsException;
}

