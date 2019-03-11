package com.becoda.bkms.parkingms.special.dao;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialProblemSocre;
import com.becoda.bkms.util.Tools;

/**
 * 区交委检查问题分值记录DAO
 * 
 * @author lhq
 * 
 */
public class SpecialProblemSocreDAO extends GenericDAO {
	/**
	 * 条件查询(若加入分值时间条件，为按月查询 格式：yyyyMMdd)
	 * @return
	 */
	public List queryList(ParmsSpecialProblemSocre prosoc)throws RollbackableException{
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("from ParmsSpecialProblemSocre where 1=1");
			if(prosoc.getCtcid()!=null&&!"".equals("")){
				sb.append(" and ctcid='").append(prosoc.getCtcid()).append("'");
			}
			if(prosoc.getProblemid()!=null&&!"".equals(prosoc.getProblemid())){
				sb.append(" and problemid='").append(prosoc.getProblemid()).append("'");
			}
			if(prosoc.getSocretime()!=null&&!"".equals(prosoc.getSocretime())){
				sb.append(" and substr(socretime,0,6)='").append(prosoc.getSocretime().substring(0, 6)).append("'");
			}
			return queryHqlList(sb.toString());
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialProblemSocreDAO.class);
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
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer countsb = new StringBuffer();
			String socretime = Tools.getSysDate("yyyyMM");
			sb.append("from ParmsSpecialProblemSocre t where 1=1 and substr(t.socretime,0,6)='").append(socretime).append("'");
			countsb.append("select count(t) from ParmsSpecialProblemSocre t where 1=1 and substr(t.socretime,0,6)='").append(socretime).append("'");
			return pageQuery(vo, countsb.toString(), sb.toString());
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialProblemSocreDAO.class);
		}
	}

}
