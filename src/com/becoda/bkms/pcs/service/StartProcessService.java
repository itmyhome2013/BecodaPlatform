package com.becoda.bkms.pcs.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.pcs.dao.ActExProcessDao;
import com.becoda.bkms.pcs.pojo.bo.ActExProcessBO;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：StartProcessService
 * @描述：TODO(启动流程Service层)
 * @创建人： 张晓亮
 * @创建时间：2016年1月18日 下午1:50:47
 * @修改人：张晓亮
 * @修改时间：2016年1月18日 下午1:50:47
 * @修改备注：
 */
public class StartProcessService {
    private ActExProcessDao actExProcessDao;
    
    /**
     * 
     * @方法名称: saveactExProcess
     * @描述：TODO(保存ActExProcessBO对象) 
     * @返回值类型： void  
     * @param actExProcess
     * @throws RollbackableException
     */
    public void saveActExProcess(ActExProcessBO actExProcess) throws RollbackableException{
    	this.actExProcessDao.createBo(actExProcess);
    }

	public ActExProcessDao getActExProcessDao() {
		return actExProcessDao;
	}

	public void setActExProcessDao(ActExProcessDao actExProcessDao) {
		this.actExProcessDao = actExProcessDao;
	}
    
}
