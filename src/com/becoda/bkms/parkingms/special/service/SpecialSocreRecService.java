package com.becoda.bkms.parkingms.special.service;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.dao.SpecialSorceRecDAO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocreRec;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

/**
 * 分值记录service
 * 
 * @author liuhq
 * 
 */
public class SpecialSocreRecService {
	private SpecialSorceRecDAO specialsocrerecDAO;
	private GenericDAO genericDAO;

	public SpecialSorceRecDAO getSpecialsocrerecDAO() {
		return specialsocrerecDAO;
	}

	public void setSpecialsocrerecDAO(SpecialSorceRecDAO specialsocrerecDAO) {
		this.specialsocrerecDAO = specialsocrerecDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	/**
	 * 根据检查信息的提交添加分值记录
	 * @throws RollbackableException
	 */
	public void createSorceRecBySpec(ParmsSpecialInfo specinfo,String sorceid)throws RollbackableException{
		ParmsSpecialSocreRec socrerec=new ParmsSpecialSocreRec();
		socrerec.setRecSocreId(SequenceGenerator.getUUID());
		socrerec.setSpecialId(specinfo.getSpecialId());
		socrerec.setRecId(specinfo.getRecId());
		socrerec.setRecName(specinfo.getRecName());
		socrerec.setCheckOrgId(specinfo.getCheckOrgId());
		socrerec.setCheckOrgName(specinfo.getCheckOrgName());
		socrerec.setIsProblemId(specinfo.getIsProblemId());
		socrerec.setIsProblem(specinfo.getIsProblem());
		socrerec.setCheckdate(specinfo.getCheckdate());
		socrerec.setCheckUserId(specinfo.getCheckUserId());
		socrerec.setCheckUserName(specinfo.getCheckUserName());
		socrerec.setSocreTime(Tools.getSysDate("yyyyMMdd"));
		socrerec.setSocreId(sorceid);
		socrerec.setCtcid(specinfo.getCheckOrgId());
		genericDAO.createBo(socrerec);
	}
	/**
	 * 查询年扣分
	 * 
	 * @param year
	 * @return
	 */
	public int getYearSocre(ParmsSpecialSocreRec socrerec, String year)throws RollbackableException{
		return specialsocrerecDAO.getYearSocre(socrerec, year);
	}
	/**
	 * 查询月扣分
	 * @param yearMonth
	 * @return
	 */
	public int getMonthSocre(ParmsSpecialSocreRec socrerec, String yearMonth)throws RollbackableException{
		return specialsocrerecDAO.getMonthSocre(socrerec, yearMonth);
	}
	/**
	 * 查询周扣分
	 * @param yearMonth yyyyMMdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getWekSocre(ParmsSpecialSocreRec socrerec, String yearMonth)throws RollbackableException{
		return specialsocrerecDAO.getWekSocre(socrerec, yearMonth);
	}
	/**
	 * 条件分页查询扣分记录
	 * @param vo
	 * @param socrerec
	 * @return
	 * @throws RollbackableException
	 */
	public List queryListPage(PageVO vo,ParmsSpecialSocreRec socrerec)throws RollbackableException{
		return specialsocrerecDAO.queryListPage(vo, socrerec);
	}

}
