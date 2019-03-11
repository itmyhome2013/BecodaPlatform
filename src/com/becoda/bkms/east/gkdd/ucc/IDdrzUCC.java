package com.becoda.bkms.east.gkdd.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.gkdd.pojo.DdrzBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description:调度日志UCC接口 </p>
 * @author liu_hq
 * @date 2017-9-14
 *
 */
public interface IDdrzUCC {
	/**
	 * 编辑调度日志
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editDdrz(DdrzBO bo,User user) throws BkmsException;
	/**
	 * 查询调度日志
	 * @param query
	 * @return DataQuery
	 */
	public DataQuery queryList(DataQuery query);
	/**
	 * 条件查询调度日志
	 * @param bo
	 * @return List<DdrzBO>
	 * @throws RollbackableException
	 */
	public List<DdrzBO> queryDdrzList(DdrzBO bo) throws RollbackableException;
	/**
	 * 删除调度日志
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteDdrz(DdrzBO bo,User user) throws BkmsException;
}
