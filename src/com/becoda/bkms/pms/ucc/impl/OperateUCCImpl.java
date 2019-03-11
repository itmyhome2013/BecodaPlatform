package com.becoda.bkms.pms.ucc.impl;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.OperateBO;
import com.becoda.bkms.pms.service.OperateService;
import com.becoda.bkms.pms.ucc.IOperateUCC;
import com.becoda.bkms.util.HrmsLog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-5
 * Time: 14:04:44
 * To change this template use File | Settings | File Templates.
 */
public class OperateUCCImpl implements IOperateUCC, Serializable {
    private OperateService operateService;

    public OperateService getOperateService() {
        return operateService;
    }

    public void setOperateService(OperateService operateService) {
        this.operateService = operateService;
    }

    public List queryAllOperate() throws RollbackableException {
        return operateService.queryAllOperates();
    }

    /**
     * 得到系统所有模块
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryAllModule() throws RollbackableException {
        return operateService.queryAllModule();
    }

    public void deleteOperateById(String operateId, User user) throws RollbackableException {
        operateService.deleteOpeById(operateId);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "删除了菜单编号为（" + operateId + "）的菜单。");
    }
    
    public void moduleOpen(String operateId, User user) throws RollbackableException{
    	
    	operateService.moduleOpen(operateId);
       // HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "启用了菜单编号为（" + operateId + "）的菜单。");
    }
    
    public void moduleClose(String operateId, User user) throws RollbackableException{
    	
    	operateService.moduleClose(operateId);
       // HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "禁用了菜单编号为（" + operateId + "）的菜单。");
    }

    public void saveOperate(OperateBO operateBo) throws RollbackableException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void saveOperate(OperateBO operateBo, boolean flag, User user) throws RollbackableException {
        operateService.saveOperate(operateBo, flag);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "新增了菜单编号为（" + operateBo.getOperateId() + "）的菜单。");
    }

    public void updateOperate(OperateBO operateBo, User user) throws RollbackableException {
        operateService.updateOperate(operateBo);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改了菜单编号为（" + operateBo.getOperateId() + "）的菜单。");
    }

    public OperateBO updateOperate(String operateId, String opeName, String opeModule, String opeUrl, String opeSuperId, String sysflag, String treeId, String type, User user) throws RollbackableException {
        OperateBO bo = operateService.updateOperate(operateId, opeName, opeModule, opeUrl, opeSuperId, sysflag, treeId, type);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), PmsConstants.MODULE_NAME, user.getName() + "修改了菜单编号为（" + operateId + "）的菜单。");
        return bo;
    }

    /**
     * 更新一个无状态的POJO
     *
     * @param bo bo
     * @param pk pk
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public void updateBo(Object bo, String pk) throws RollbackableException {
        operateService.updateBo(bo, pk);
    }

    public OperateBO getOperateById(String operateId) throws RollbackableException {
        return operateService.getOperateById(operateId);
    }

    public OperateBO getOperateByTreeId(String treeId) throws RollbackableException {
        return operateService.getOperateByTreeId(treeId);
    }

    /**
     * 通过treeId获得模块下的所有菜单
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryMenusByTreeId(String treeId) throws RollbackableException {
        return operateService.queryMenusByTreeId(treeId);
    }

    /**
     * 通过treeId获得模块下的所有菜单
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryMenusByOpeId(String operateId) throws RollbackableException {
        try {
            OperateBO operateBo = operateService.getOperateById(operateId);
            return operateService.queryMenusByTreeId(operateBo.getTreeId());
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public String isOperateExit(String name) throws RollbackableException {
        String msg = "";
        OperateBO bo = operateService.getOperateByName(name);
        if (bo != null) {
            msg = "该名称已被使用";
        } else {
            msg = "该名称可以使用";
        }
        return msg;
    }
}
