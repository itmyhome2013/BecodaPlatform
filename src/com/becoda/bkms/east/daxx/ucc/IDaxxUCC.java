package com.becoda.bkms.east.daxx.ucc;

import java.io.IOException;
import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.daxx.pojo.DaxxBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 档案信息UCC接口</p>
 * @author zhu_lw
 * @date 2018-04-10
 *
 */
public interface IDaxxUCC {
	/**
	 * 查询档案信息
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query);
	/**
	 * 获取部门
	 * @return
	 */
	public List findSsbm();
	/**
	 * 获取所有机组
	 * @return
	 */
	public List findTotalJz();
	/**
	 * 获取部门下的机组
	 * @return
	 */
	public List findSsjz(String bmid);
	/**
	 * 条件查询档案信息
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<DaxxBO> queryByBO(DaxxBO bo) throws RollbackableException;
	/**
	 * 编辑档案信息
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editDaxx(DaxxBO bo,User user) throws BkmsException;
	/**
	 * 删除档案信息
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteDaxx(DaxxBO bo,User user) throws BkmsException;
}
