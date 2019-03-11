package com.becoda.bkms.parkingms.special.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocreRec;
import com.becoda.bkms.parkingms.special.service.SpecialSocreRecService;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreRecUCC;
/**
 * 分值记录UCC实现类
 * @author lhq
 *
 */
public class SpecialSocreRecUCCImpl implements ISpecialSocreRecUCC{
	private SpecialSocreRecService specialsocrerecService;


	public SpecialSocreRecService getSpecialsocrerecService() {
		return specialsocrerecService;
	}

	public void setSpecialsocrerecService(
			SpecialSocreRecService specialsocrerecService) {
		this.specialsocrerecService = specialsocrerecService;
	}

	public void createSorceRecBySpec(ParmsSpecialInfo specinfo, String sorceid)
			throws RollbackableException {
		specialsocrerecService.createSorceRecBySpec(specinfo, sorceid);
		
	}

	public int getYearSocre(ParmsSpecialSocreRec socrerec, String year)
			throws RollbackableException {
		return specialsocrerecService.getYearSocre(socrerec, year);
	}

	public int getMonthSocre(ParmsSpecialSocreRec socrerec, String yearMonth)
			throws RollbackableException {
		return specialsocrerecService.getMonthSocre(socrerec, yearMonth);
	}

	public int getWekSocre(ParmsSpecialSocreRec socrerec, String yearMonth)
			throws RollbackableException {
		return specialsocrerecService.getWekSocre(socrerec, yearMonth);
	}

	public List queryListPage(PageVO vo, ParmsSpecialSocreRec socrerec)
			throws RollbackableException {
		return specialsocrerecService.queryListPage(vo, socrerec);
	}
	

}
