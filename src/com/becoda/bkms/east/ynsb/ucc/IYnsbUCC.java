package com.becoda.bkms.east.ynsb.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 用能设备UCC接口</p>
 * @author liu_hq
 * @date 2017-9-27
 *
 */
public interface IYnsbUCC {
	/**
	 * 查询用能设备
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query);
	/**
	 * 条件查询用能设备
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YnsbBO> queryByBO(YnsbBO bo) throws RollbackableException;
	/**
	 * 编辑用能设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYnsb(YnsbBO bo,User user) throws BkmsException;
	/**
	 * 删除用能设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteYnsb(YnsbBO bo,User user) throws BkmsException;
	/**
	 * 编辑用能设备sis标示
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYnsbSis(YnsbSisBO bo,User user) throws BkmsException;
	/**
	 * 查询用能设备sis标示
	 * @param query
	 * @return
	 */
	public DataQuery queryYnsbSis(DataQuery query);
	/**
	 * 删除用能设备sis标示
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteYnsbSis(YnsbSisBO bo,User user) throws BkmsException;
	/**
	 * 根据用能设备id删除sis标示
	 * @param ynsbid
	 */
	public void deleteYnsbSisBySbid(String ynsbid,User user) throws BkmsException;
	/**
	 * 根据用能设备id修改sis标示能源种类
	 * @param ynsbid
	 * @param nyzl
	 */
	public void editSisBySbid(String ynsbid, String nyzl,User user) throws BkmsException;
	/**
	 * 查询用能设备
	 * @param itemids
	 * @param itemSpell
	 * @return
	 * @throws RollbackableException 
	 */
	public List<CodeItemBO> queryCodeItem(String itemids,String itemSpell) throws RollbackableException;
	/**
	 * 查询园区电表
	 * @param query
	 * @return
	 */
	public DataQuery queryYqdb(DataQuery query);
}
