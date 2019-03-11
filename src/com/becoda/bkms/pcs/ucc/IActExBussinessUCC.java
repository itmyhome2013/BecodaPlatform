package com.becoda.bkms.pcs.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pcs.pojo.vo.ActExBussinessForm;
import com.becoda.bkms.run.pojo.vo.BulletinForm;

public interface IActExBussinessUCC {

	public List findByBussinessId(String bussinessId) throws RollbackableException;

	public void createBussiness(ActExBussinessForm vo, String userId, String orgId, User user) throws BkmsException;
	
	/**
	 * 删除
	 * @param bussinessIdArray
	 * @param user
	 * @throws BkmsException
	 */
	public void deleteBussinessByBussinessIdArray(String bussinessIdArray[], User user) throws BkmsException;

}
