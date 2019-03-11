package com.becoda.bkms.east.zdsb.ucc;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.zdsb.pojo.BjqdBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSbscBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbStateBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 重点设备UCC接口</p>
 * @author zhu_lw
 * @date 2017-12-22
 *
 */
public interface IZdsbUCC {
	/**
	 * 查询重点设备
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query);
	/**
	 * 查询重点设备状态
	 * @param query
	 * @return
	 */
	public DataQuery queryStateList(DataQuery query);
	
	/**
	 * 条件查询重点设备
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZdsbBO> queryByBO(ZdsbBO bo) throws RollbackableException;
	/**
	 * 编辑重点设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editZdsb(ZdsbBO bo,User user) throws BkmsException;
	
	/**
	 * 编辑重点设备状态
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editZdsbState(ZdsbStateBO bo,User user) throws BkmsException;
	/**
	 * 编辑重点设备设备时长
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editZdsbSbsc(ZdsbSbscBO bo,User user) throws BkmsException;
	/**
	 * 删除重点设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteZdsb(ZdsbBO bo,User user) throws BkmsException;
	
	/**
	 * 根据重点设备id删除sis标示
	 * @param zdsbid
	 */
	public void deleteZdsbSisBySbid(String zdsbid,User user) throws BkmsException;
	/**
	 * 根据重点设备id删除设备时长
	 * @param zdsbid
	 */
	public void deleteZdsbSbscBySbid(String zdsbid,User user) throws BkmsException;
	/**
	 * 查询重点设备sis标示
	 * @param query
	 * @return
	 */
	public DataQuery queryZdsbSis(DataQuery query);
	/**
	 * 编辑重点设备sis标示
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editZdsbSis(ZdsbSisBO bo,User user) throws BkmsException;
	/**
	 * 删除重点设备sis标示
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteZdsbSis(ZdsbSisBO bo,User user) throws BkmsException;
	/**
	 * 通过条件查询重点设备
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbByParams(Map<String,String> params);
	/**
	 * 查询重点设备设备时长
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbSbsc();
	/**
	 * 通过条件查询设备检测信息
	 * @param sj
	 * @return
	 */
	public List<Map<String, Object>> queryZdsbSbjcByParams(String sj);
	/**
	 * 查询备件清单
	 * @param query
	 * @return
	 */
	public DataQuery queryBjqdList(DataQuery query);
	/**
	 * 条件查询备件清单
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<BjqdBO> queryBjqdByBO(BjqdBO bo) throws RollbackableException;
	/**
	 * 编辑备件清单
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editBjqd(BjqdBO bo,User user) throws BkmsException;
	/**
	 * 删除备件清单
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteBjqd(BjqdBO bo,User user) throws BkmsException;
}
