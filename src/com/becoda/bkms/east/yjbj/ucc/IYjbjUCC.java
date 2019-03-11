package com.becoda.bkms.east.yjbj.ucc;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.yjbj.pojo.NxjcYjbjSbBO;
import com.becoda.bkms.east.yjbj.pojo.YcbjBO;
import com.becoda.bkms.east.yjbj.pojo.YjbjBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbBO;
import com.becoda.bkms.east.ynsb.pojo.YnsbSisBO;
import com.becoda.bkms.east.zdsb.pojo.ZdsbSisBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 预警报警UCC接口</p>
 * @author zhu_lw
 * @date 2017-11-24
 *
 */
public interface IYjbjUCC {
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
	 * 条件查询预警报警
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YjbjBO> queryByYjbjBO(YjbjBO bo) throws RollbackableException;
	/**
	 * 条件查询sisbo
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<ZdsbSisBO> queryBySisBO(ZdsbSisBO bo) throws RollbackableException;
	/**
	 * 编辑用能设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYnsb(YnsbBO bo) throws RollbackableException;
	/**
	 * 删除用能设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void deleteYnsb(YnsbBO bo) throws RollbackableException;
	/**
	 * 编辑用能设备sis标示
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYjbj(YjbjBO yjbjBO,User user) throws BkmsException;
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
	public void deleteYnsbSis(YnsbSisBO bo) throws RollbackableException;
	/**
	 * 根据用能设备id删除sis标示
	 * @param ynsbid
	 */
	public void deleteYnsbSisBySbid(String ynsbid);
	/**
	 * 新增异常报警
	 * @param bo
	 * @throws RollbackableException
	 */
	public void addYcbj(YcbjBO bo) throws RollbackableException;
	/**
	 * 定时处理异常报警
	 * @throws ParseException
	 * @throws RollbackableException
	 */
	public void saveTimingYcbj() throws ParseException, RollbackableException;
	/**
	 * 查询异常报警
	 * @param query
	 * @param sj
	 * @return
	 */
	public DataQuery queryYcbj(DataQuery query,String sj,String lx);
	/**
	 * 查询历史异常报警
	 * @param query
	 * @return
	 */
	public DataQuery queryHistoryYcbj(DataQuery query,String lx);
	/**
	 * 报警提醒查询
	 * @return
	 */
	public List<Map<String, Object>> queryAlarmAlert();
	/**
	 * 条件查询异常报警
	 * @param starttime
	 * @param endtime
	 * @param sbbh
	 * @return
	 */
	public List<Map<String, Object>> queryYcbj(String starttime,String endtime, String sbbh,String lx);
	/**
	 * 根据时间查询异常报警
	 * @param sj
	 * @return
	 * @throws BkmsException 
	 */
	public List<Map<String, String>> queryBySj(String sj,String fid) throws BkmsException;
	/**
	 * 查询能效检测预警报警设备
	 * @param query
	 * @return
	 */
	public DataQuery queryNxjcYcbjSb(DataQuery query);
	/**
	 * 修改能效检测预警报警设备
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editNxjcYjbj(NxjcYjbjSbBO bo,User user) throws BkmsException;
	/**
	 * 查询异常报警
	 * @param bo
	 * @return
	 * @throws RollbackableException
	 */
	public List<YcbjBO> queryYcbjByBO(YcbjBO bo) throws RollbackableException;
	/**
	 * 修改异常报警
	 * @param bo
	 * @throws RollbackableException
	 */
	public void editYcbj(YcbjBO bo) throws RollbackableException;
}
