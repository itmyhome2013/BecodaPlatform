package com.becoda.bkms.pcs.service;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pcs.dao.ActExBussinessDAO;
import com.becoda.bkms.pcs.pojo.bo.ActExBussinessBO;
import com.becoda.bkms.pcs.pojo.bo.ActExProcessBO;
import com.becoda.bkms.pcs.pojo.vo.ActExBussinessForm;
import com.becoda.bkms.run.pojo.bo.BulletinContentBO;
import com.becoda.bkms.run.pojo.bo.BulletinParamBO;
import com.becoda.bkms.run.pojo.bo.BulletinScopeBO;
import com.becoda.bkms.run.pojo.vo.BulletinForm;
import com.becoda.bkms.run.util.RunTools;
import com.becoda.bkms.util.Tools;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 2015-3-12 Time: 15:15:58
 * To change this template use File | Settings | File Templates.
 */
public class ActExBussinessService {

	private ActExBussinessDAO bussinessDAO;

	public List findByBussinessId(String bussinessId) throws RollbackableException {
		return bussinessDAO.findByBussinessId(bussinessId);
	}

	/**
     * 功能：创建bussiness<br>
     *
     */
    public void createBussiness(ActExBussinessForm vo, String userId, String orgId) throws RollbackableException {
        try {
        	ActExBussinessBO parambo = new ActExBussinessBO();
            parambo.setBussiness_name(vo.getBussiness_name());
            parambo.setBussiness_reamrk(vo.getBussiness_reamrk());
            bussinessDAO.createBo(parambo);  //新增
        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("创建Bussiness失败", e, this.getClass());
        }
    }

    public void deleteBussinessByBussinessIdArray(String bussinessIdArray[]) throws RollbackableException {
        try {
            String id = null;
            for (int i = 0; i < bussinessIdArray.length; i++) {
                id = bussinessIdArray[i].trim();
                if(!"".equals(id)&&id!=null){
                	ActExProcessBO bo = (ActExProcessBO) bussinessDAO.findBo(ActExProcessBO.class, id);
                    bussinessDAO.deleteBo(bo);	
                }
                
                
            }

        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("删除事件失败", e, this.getClass());
        }
    }
	public ActExBussinessDAO getBussinessDAO() {
		return bussinessDAO;
	}

	public void setBussinessDAO(ActExBussinessDAO bussinessDAO) {
		this.bussinessDAO = bussinessDAO;
	}
	

}
