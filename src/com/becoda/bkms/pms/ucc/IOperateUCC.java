package com.becoda.bkms.pms.ucc;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.pojo.bo.OperateBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-5
 * Time: 13:49:23
 * To change this template use File | Settings | File Templates.
 */
public interface IOperateUCC {
    public List queryAllOperate() throws RollbackableException;

    public void deleteOperateById(String operateId, User user) throws RollbackableException;
    
    public void moduleOpen(String operateId, User user) throws RollbackableException;
    
    public void moduleClose(String operateId, User user) throws RollbackableException;

    public void saveOperate(OperateBO operateBo) throws RollbackableException;

    public void updateOperate(OperateBO operateBo, User user) throws RollbackableException;

    public void updateBo(Object bo, String pk) throws RollbackableException;

    public List queryAllModule() throws RollbackableException;

    public OperateBO getOperateById(String operateId) throws RollbackableException;

    public List queryMenusByTreeId(String treeId) throws RollbackableException;

    public List queryMenusByOpeId(String operateId) throws RollbackableException;

    public void saveOperate(OperateBO operateBo, boolean flag, User user) throws RollbackableException;

    public OperateBO getOperateByTreeId(String treeId) throws RollbackableException;

    public String isOperateExit(String name) throws RollbackableException;

    public OperateBO updateOperate(String operateId, String opeName, String opeModule, String opeUrl, String opeSuperId, String sysflag, String treeId, String type, User user) throws RollbackableException;
}
