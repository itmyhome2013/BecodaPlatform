package com.becoda.bkms.parkingms.special.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocre;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialSocreVo;
import com.becoda.bkms.parkingms.special.service.SpecialSocreService;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreUCC;

/**
 * 停车单位分值UCC实现类
 * @author lhq
 *
 */
public class SpecialSocreUCCImpl implements ISpecialSocreUCC{
	private SpecialSocreService specialsocreService;


	public SpecialSocreService getSpecialsocreService() {
		return specialsocreService;
	}
	public void setSpecialsocreService(SpecialSocreService specialsocreService) {
		this.specialsocreService = specialsocreService;
	}
	public void editSorce(ParmsSpecialInfo specinfo) throws BkmsException,RollbackableException {
		specialsocreService.editSpecialSorceByspecinfo(specinfo);
		
	}
	public ParmsSpecialSocre getSpecialSocre(String socreid)
			throws RollbackableException {
		return specialsocreService.getSpecialSocre(socreid);
	}
	public List querySocreList(PageVO vo, ParmsSpecialSocreVo specsocre)
			throws RollbackableException {
		return specialsocreService.querySocreList(vo, specsocre);
	}

	public List queryAllList(ParmsSpecialSocreVo socre) throws RollbackableException {
		return specialsocreService.queryAllList(socre);
	}
	public List queryDwListBychengqu(String id)throws RollbackableException{
		return specialsocreService.queryDwListBychengqu(id);
	}
	public int getMonthSocre(ParmsSpecialSocre special, String dates)
			throws RollbackableException {
		return specialsocreService.getMonthSocre(special, dates);
	}
	public int getWekSocre(String recid, String dates)
			throws RollbackableException {
		return specialsocreService.getWekSocre(recid, dates);
	}
	public int getYearSocre(ParmsSpecialSocre special, String dates)
			throws RollbackableException {
		return specialsocreService.getYearSocre(special, dates);
	}

}
