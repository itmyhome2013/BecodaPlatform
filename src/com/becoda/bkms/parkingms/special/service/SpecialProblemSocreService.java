package com.becoda.bkms.parkingms.special.service;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.dao.SpecialProblemSocreDAO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialProblemSocre;
import com.becoda.bkms.util.Tools;

/**
 * 区交委检查问题分值记录service
 * 
 * @author lhq
 * 
 */
public class SpecialProblemSocreService {
	/**
	 * 区交委检查问题分值记录DAO
	 */
	private SpecialProblemSocreDAO specialproblemsocreDAO;
	private GenericDAO genericDAO;

	public SpecialProblemSocreDAO getSpecialproblemsocreDAO() {
		return specialproblemsocreDAO;
	}

	public void setSpecialproblemsocreDAO(
			SpecialProblemSocreDAO specialproblemsocreDAO) {
		this.specialproblemsocreDAO = specialproblemsocreDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	/**
	 * 条件查询(若加入分值时间条件，为按月查询 格式：yyyyMMdd)
	 * @return
	 */
	public List queryList(ParmsSpecialProblemSocre prosoc)throws RollbackableException{
		return specialproblemsocreDAO.queryList(prosoc);
	}
	/**
	 * 城区扣分操作方法
	 * @param prosoc
	 */
	public void editDownSoc(ParmsSpecialProblemSocre prosoc)throws RollbackableException{
		List list= queryList(prosoc);
		if(list.size()!=0){
			ParmsSpecialProblemSocre oldprosoc=(ParmsSpecialProblemSocre)list.get(0);
			if(Tools.parseInt(oldprosoc.getSocre())>0){
				int newsocre =Tools.parseInt(oldprosoc.getSocre())-1; 
				prosoc.setSocre(newsocre+"");
				genericDAO.updateBo(prosoc.getProblemid(), prosoc);
			}
		}
	}
	/**
	 * 分页条件查询
	 * @param vo
	 * @param prosoc
	 * @return
	 * @throws RollbackableException 
	 */
	public List queryListPage(PageVO vo,ParmsSpecialProblemSocre prosoc) throws RollbackableException{
		return specialproblemsocreDAO.queryListPage(vo, prosoc);
	}
}
