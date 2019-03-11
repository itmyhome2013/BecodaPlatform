package com.becoda.bkms.parkingms.special.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocre;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialSocreVo;

/**
 * 停车单位分值UCC接口
 * 
 * @author lhq
 * 
 */
public interface ISpecialSocreUCC {
	/**
	 * 分值处理方法
	 * 
	 * @param specinfo
	 * @throws RollbackableException
	 */
	public void editSorce(ParmsSpecialInfo specinfo) throws BkmsException,RollbackableException;

	/**
	 * 根据id得到分值记录
	 * 
	 * @param socreid
	 * @return
	 * @throws RollbackableException
	 */
	public ParmsSpecialSocre getSpecialSocre(String socreid) throws RollbackableException;

	/**
	 * 查询单位分值表(分页条件查询)
	 * 
	 * @param vo
	 * @param specsocre
	 * @return
	 * @throws RollbackableException
	 */
	public List querySocreList(PageVO vo, ParmsSpecialSocreVo specsocre)throws RollbackableException;
	/**
	 * 按条件查询数据
	 * @return
	 * @throws RollbackableException
	 */
	public List queryAllList(ParmsSpecialSocreVo socre)throws RollbackableException;
	/**
	 * 根据城区查询ParmsSpecialInfo（专项信息表）
	 */
	public List queryDwListBychengqu(String id)throws RollbackableException;
	/**
	 * 查询年扣分
	 * 
	 * @param recid
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getYearSocre(ParmsSpecialSocre special, String dates)throws RollbackableException;
	/**
	 * 查询月扣分
	 * 
	 * @param recid
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getMonthSocre(ParmsSpecialSocre special, String dates)throws RollbackableException;
	/**
	 * 查询周扣分
	 * 
	 * @param recid
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getWekSocre(String recid, String dates)throws RollbackableException;

}
