package com.becoda.bkms.parkingms.special.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocreRec;

/**
 * 分值记录UCC接口
 * @author lhq
 *
 */
public interface ISpecialSocreRecUCC {
	/**
	 * 根据检查信息的提交添加分值记录
	 * @throws RollbackableException
	 */
	public void createSorceRecBySpec(ParmsSpecialInfo specinfo,String sorceid)throws RollbackableException;
	/**
	 * 查询年扣分
	 * @param year 格式：yyyyMMdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getYearSocre(ParmsSpecialSocreRec socrerec, String year)throws RollbackableException;
	/**
	 * 查询月扣分
	 * @param yearMonth 格式：yyyyMMdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getMonthSocre(ParmsSpecialSocreRec socrerec, String yearMonth)throws RollbackableException;
	/**
	 * 查询周扣分
	 * @param yearMonth 格式：yyyyMMdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getWekSocre(ParmsSpecialSocreRec socrerec, String yearMonth)throws RollbackableException;
	/**
	 * 条件分页查询扣分记录
	 * @param vo
	 * @param socrerec
	 * @return
	 * @throws RollbackableException
	 */
	public List queryListPage(PageVO vo,ParmsSpecialSocreRec socrerec)throws RollbackableException;

}
