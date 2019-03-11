package com.becoda.bkms.parkingms.special.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialProblemSocre;
import com.becoda.bkms.parkingms.special.service.SpecialProblemSocreService;
import com.becoda.bkms.parkingms.special.ucc.ISpecialProblemSocreUCC;
/**
 * 区交委检查问题分值记录UCC实现类
 * @author lhq
 *
 */
public class SpecialProblemSocreUCCImpl implements ISpecialProblemSocreUCC{
	private SpecialProblemSocreService specialproblemsocreService;
	

	public SpecialProblemSocreService getSpecialproblemsocreService() {
		return specialproblemsocreService;
	}


	public void setSpecialproblemsocreService(
			SpecialProblemSocreService specialproblemsocreService) {
		this.specialproblemsocreService = specialproblemsocreService;
	}


	public List queryList(ParmsSpecialProblemSocre prosoc)
			throws RollbackableException {
		return specialproblemsocreService.queryList(prosoc);
	}


	public List queryListPage(PageVO vo, ParmsSpecialProblemSocre prosoc)
			throws RollbackableException {
		return specialproblemsocreService.queryListPage(vo, prosoc);
	}

}
