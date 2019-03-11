package com.becoda.bkms.parkingms.special.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bsh.ParseException;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocreRec;
import com.becoda.bkms.util.Tools;

/**
 * 分值记录dao
 * 
 * @author lhq
 * 
 */
public class SpecialSorceRecDAO extends GenericDAO {
	/**
	 * 查询年扣分
	 * 
	 * @param year
	 * @return
	 */
	public int getYearSocre(ParmsSpecialSocreRec socrerec, String year)
			throws RollbackableException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("select count(*) from ParmsSpecialSocreRec where SUBSTR(SOCRETIME,0,4)='").append(year).append("'");
			if (socrerec.getRecId() != null && "" != socrerec.getRecId()) {
				hql.append(" and recId='").append(socrerec.getRecId()).append("'");
			}
			if (socrerec.getSpecialId() != null&& "" != socrerec.getSpecialId()) {
				hql.append(" and specialId='").append(socrerec.getSpecialId()).append("'");
			}if(socrerec.getIsProblemId()!=null&&!"".equals(socrerec.getIsProblemId())){
				hql.append(" and isProblemId='").append(socrerec.getIsProblemId()).append("'");
			}
			hql.append(" and socreId is not null  ");// 只统计有扣分的，不统计有记录但未扣分的
			List list = hibernateTemplate.find(hql.toString());
			int YearSocre = Tools.parseInt(list.get(0));
			return YearSocre;
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e,
					SpecialSorceRecDAO.class);
		}
	}
	/**
	 * 查询月扣分
	 * @param yearMonth
	 * @return
	 */
	public int getMonthSocre(ParmsSpecialSocreRec socrerec, String yearMonth)throws RollbackableException{
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("select count(*) from ParmsSpecialSocreRec where SUBSTR(SOCRETIME,0,6)='").append(yearMonth).append("'");
			if(socrerec.getRecId()!=null&&""!=socrerec.getRecId()){
				hql.append(" and recId='").append(socrerec.getRecId()).append("'");
			}
			if(socrerec.getSpecialId()!=null&&""!=socrerec.getSpecialId()){
				hql.append(" and specialId='").append(socrerec.getSpecialId()).append("'");
			}if(socrerec.getIsProblemId()!=null&&!"".equals(socrerec.getIsProblemId())){
				hql.append(" and isProblemId='").append(socrerec.getIsProblemId()).append("'");
			}
			hql.append(" and socreId is not null  ");// 只统计有扣分的，不统计有记录但未扣分的
				List list = hibernateTemplate.find(hql.toString());
				int MonthSocre = Tools.parseInt(list.get(0));
			return MonthSocre;
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceRecDAO.class);
		}
		
	}
	/**
	 * 查询周扣分
	 * @param yearMonth yyyyMMdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getWekSocre(ParmsSpecialSocreRec socrerec, String yearMonth)throws RollbackableException{
		StringBuffer hql = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int WekSocre=0;
	    try {
			Date date = sdf.parse(yearMonth);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			hql.append("select count(*) from ParmsSpecialSocreRec where ").append("  to_char( to_date(socretime,'yyyy-MM-dd'),'iw')=").append(c.get(Calendar.WEEK_OF_YEAR));
			if(socrerec.getRecId()!=null&&""!=socrerec.getRecId()){
				hql.append(" and recId='").append(socrerec.getRecId()).append("'");
			}
			if(socrerec.getSpecialId()!=null&&""!=socrerec.getSpecialId()){
				hql.append("and specialId='").append(socrerec.getSpecialId()).append("'");
			}if(socrerec.getSocreId()!=null&&!"".equals("socrerec.getSocreId()")){
				hql.append(" and socreId='").append(socrerec.getSocreId()).append("'");
			}if(socrerec.getCtcid()!=null&&!"".equals(socrerec.getCtcid())){
				hql.append(" and ctcid='").append(socrerec.getCtcid()).append("'");
			}if(socrerec.getIsProblemId()!=null&&!"".equals(socrerec.getIsProblemId())){
				hql.append(" and isProblemId='").append(socrerec.getIsProblemId()).append("'");
			}
			hql.append(" and socreId is not null  ");//只统计有扣分的，不统计有记录但未扣分的
				List list = hibernateTemplate.find(hql.toString());
				WekSocre=Tools.parseInt(list.get(0));
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceRecDAO.class);
		}  
		return WekSocre;
	}
	/**
	 * 条件分页查询扣分记录
	 * @param vo
	 * @param socrerec
	 * @return
	 * @throws RollbackableException
	 */
	public List queryListPage(PageVO vo,ParmsSpecialSocreRec socrerec)throws RollbackableException{
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer countsb = new StringBuffer();
			sb.append("from ParmsSpecialSocreRec t where 1=1");
			countsb.append("select count(t) from ParmsSpecialSocreRec t where 1=1");
			if (socrerec.getRecId() != null && !"".equals(socrerec.getRecId())) {
				sb.append(" and recId='").append(socrerec.getRecId()).append("'");
			}
			if (socrerec.getSocreTime() != null&& !"".equals(socrerec.getSocreTime())) {
				String socretime = socrerec.getSocreTime();
				//按年查询
				if (socretime.length() == 4) {
					sb.append(" and substr(socreTime,0,4)='").append(socretime).append("'");
				}
				//按月查询
				if (socretime.length() == 6) {
					sb.append(" and substr(socreTime,0,6)='").append(socretime).append("'");
				}
				//按周查询
				if (socretime.length() == 8) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = sdf.parse(socretime);
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					sb.append(" and to_char( to_date(socretime,'yyyyMMdd'),'iw')=").append(c.get(Calendar.WEEK_OF_YEAR));
				}
			}
			return pageQuery(vo, countsb.toString(), sb.toString());
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceRecDAO.class);
		}
	}
	
}
