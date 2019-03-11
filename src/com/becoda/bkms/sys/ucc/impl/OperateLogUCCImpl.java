package com.becoda.bkms.sys.ucc.impl;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.sys.service.OperateLogService;
import com.becoda.bkms.sys.ucc.IOperateLogUCC;

import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-19
 * Time: 15:14:29
 */
public class OperateLogUCCImpl implements IOperateLogUCC {
    private OperateLogService operateLogService;

    public OperateLogService getOperateLogService() {
        return operateLogService;
    }

    public void setOperateLogService(OperateLogService operateLogService) {
        this.operateLogService = operateLogService;
    }

    public List queryOperLog(String start, String end, String operName, String userName, String operType, String orgId, PageVO vo) throws BkmsException {
        return operateLogService.queryOperLog(start, end, operName, userName, operType, orgId, vo);
    }

    public void deleteAndExpOperLog(String[] operId, int fileType, String rootPath) throws BkmsException {
        operateLogService.expOperLog(operId, fileType, rootPath);
    }

    public String[] getDiskLogFile(String rootPath) {
        return operateLogService.getDiskLogFile(rootPath);
    }


    public void delDiskLogFile(String[] fileName, String rootPath) {
        operateLogService.delDiskLogFile(fileName, rootPath);
    }

    public OperLogBO[] query(OperLogBO logLog) throws BkmsException {
        return operateLogService.query(logLog);
    }

}
