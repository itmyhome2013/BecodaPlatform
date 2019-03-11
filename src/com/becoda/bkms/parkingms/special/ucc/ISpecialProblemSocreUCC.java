package com.becoda.bkms.parkingms.special.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialProblemSocre;

/**
 * 区交委检查问题分值记录UCC接口
 * @author lhq
 *
 */
public interface ISpecialProblemSocreUCC {
	/**
	 * 条件查询(若加入分值时间条件，为按月查询 格式：yyyyMMdd)
	 * @param ctcid
	 * @param problemid
	 * @return
	 */
	public List queryList(ParmsSpecialProblemSocre prosoc)throws RollbackableException;
	/**
	 * 分页条件查询
	 * @param vo
	 * @param prosoc
	 * @return
	 * @throws RollbackableException 
	 */
	public List queryListPage(PageVO vo,ParmsSpecialProblemSocre prosoc) throws RollbackableException;
}
