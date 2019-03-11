package com.becoda.bkms.parkingms.special.service;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.dao.SpecialSorceDAO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialProblemSocre;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocre;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialSocreVo;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreRecUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;


/**
 * 停车单位分值service
 * 
 * @author liuhq
 * 
 */
public class SpecialSocreService {
	/**
	 * 停车单位分值
	 */
	private SpecialSorceDAO specialsocreDAO;
	private GenericDAO genericDAO;
	public SpecialSorceDAO getSpecialsocreDAO() {
		return specialsocreDAO;
	}

	public void setSpecialsocreDAO(SpecialSorceDAO specialsocreDAO) {
		this.specialsocreDAO = specialsocreDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	/**
	 *分值处理方法
	 * 
	 * @param specinfo
	 * @throws BkmsException
	 */
	public void editSpecialSorceByspecinfo(ParmsSpecialInfo specinfo)
			throws BkmsException ,RollbackableException{
		//区交委检查问题分值service
		SpecialProblemSocreService specialproblemsocreService=(SpecialProblemSocreService)BkmsContext.getBean("specialproblemsocreService");
		ISpecialSocreRecUCC sorcerecucc =(ISpecialSocreRecUCC)BkmsContext.getBean("specialsocrerecUCC");
		String recid = specinfo.getRecId();
		String socreTime = Tools.getSysDate("yyyyMM");
		ParmsSpecialSocre socre = specialsocreDAO.getSorceBy(recid, socreTime);// 根据单位id和当前日期查找分值
		//没有当前月份的记录，创建一条新的
		if (socre.getRecId() == null || "".equals(socre.getRecId())) {
//			socre.setSocreId(SequenceGenerator.getUUID());
//			socre.setRecId(recid);
//			socre.setRecName(specinfo.getRecName());
//			socre.setSocre("1");
//			socre.setSocreTime(Tools.getSysDate("yyyyMMdd"));
//			genericDAO.createBo(socre);
//			sorcerecucc.createSorceRecBySpec(specinfo, socre.getSocreId());//添加扣分记录
		} else {
			ParmsSpecialProblemSocre probsoc=new ParmsSpecialProblemSocre();
			probsoc.setProblemid(specinfo.getIsProblemId());
			probsoc.setSocretime(Tools.getSysDate("yyyyMMdd"));
			probsoc.setCtcid(specinfo.getReceiveOrgId());//区交委id
			ParmsSpecialProblemSocre oldprobsoc = (ParmsSpecialProblemSocre)specialproblemsocreService.queryList(probsoc).get(0);
			if(Tools.parseInt(oldprobsoc.getSocre())>0){
				//街道小区
				int oldsorce = Integer.parseInt(socre.getSocre());
				int newsorce = oldsorce + 1;
				socre.setSocre(newsorce + "");
				genericDAO.updateBo(socre.getRecId(), socre);
				//区交委问题扣分
				oldprobsoc.setSocre((Tools.parseInt(oldprobsoc.getSocre())-1)+"");
				specialproblemsocreService.editDownSoc(oldprobsoc);
				sorcerecucc.createSorceRecBySpec(specinfo, socre.getSocreId());//添加扣分记录（有扣分有记录）
			}
			sorcerecucc.createSorceRecBySpec(specinfo, null);//添加扣分记录(有记录但未扣分)
		}
	}
	//根据id查询
	public ParmsSpecialSocre getSpecialSocre(String socreid)
			throws RollbackableException {
		return (ParmsSpecialSocre) genericDAO.findBo(ParmsSpecialSocre.class,
				socreid);
	}
	/**
	 * 查询单位分值表（分页条件查询）
	 * @param vo
	 * @param specsocre
	 * @return
	 * @throws RollbackableException
	 */
	public List querySocreList(PageVO vo, ParmsSpecialSocreVo specsocre)throws RollbackableException{
		return specialsocreDAO.querySocreList(vo, specsocre);
	}
	/**
	 * 按条件查询数据
	 * @return
	 * @throws RollbackableException
	 */
	public List queryAllList(ParmsSpecialSocreVo socre)throws RollbackableException{
		return specialsocreDAO.queryAllList(socre);
	}
	/**
	 * 根据城区（指派单位id）查询ParmsSpecialInfo（专项信息表）
	 */
	public List queryDwListBychengqu(String id)throws RollbackableException{
		return specialsocreDAO.queryDwListBychengqu(id);
	}
	
	/**
	 * 查询年扣分
	 * 
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getYearSocre(ParmsSpecialSocre special, String dates)throws RollbackableException{
		return specialsocreDAO.getYearSocre(special, dates);
	}
	
	/**
	 * 查询月扣分
	 * 
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getMonthSocre(ParmsSpecialSocre special, String dates)throws RollbackableException{
		return specialsocreDAO.getMonthSocre(special, dates);
	}
	
	/**
	 * 查询周扣分
	 * 
	 * @param recid
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getWekSocre(String recid, String dates)throws RollbackableException{
		return specialsocreDAO.getWekSocre(recid, dates);
	}
}
