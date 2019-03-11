package com.becoda.bkms.parkingms.special.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocre;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialSocreVo;
import com.becoda.bkms.util.Tools;

/**
 * 停车单位分值dao
 * 
 * @author liuhq
 * 
 */
public class SpecialSorceDAO extends GenericDAO {
	/**
	 * 根据单位id和日期查询
	 * 
	 * @return
	 */
	public ParmsSpecialSocre getSorceBy(String recId, String socreTime)
			throws RollbackableException {
		try {
			String hql = "from ParmsSpecialSocre where recId='" + recId
					+ "' and substr(socreTime,0,6)='" + socreTime + "'";
			ParmsSpecialSocre specsocre = new ParmsSpecialSocre();
			if (hibernateTemplate.find(hql).size() != 0) {
				specsocre = (ParmsSpecialSocre) hibernateTemplate.find(hql)
						.get(0);
			}
			return specsocre;
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceDAO.class);
		}
	}

	/**
	 * 查询单位分值表（分页条件查询）
	 * 
	 * @param vo
	 * @param specsocre
	 * @return
	 */
	public List querySocreList(PageVO vo, ParmsSpecialSocreVo specsocre)
			throws RollbackableException {
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer countsb = new StringBuffer();
			sb.append("from ParmsSpecialSocre t where 1=1");
			countsb.append("select count(t) from ParmsSpecialSocre t where 1=1");
			if (specsocre.getSocreTime() != null
					&& !"".equals(specsocre.getSocreTime())) {
				sb.append(" and substr(t.socreTime,0,6)='").append(
						specsocre.getSocreTime().substring(0, 6)).append("'");
				countsb.append(" and substr(t.socreTime,0,6)='").append(
						specsocre.getSocreTime().substring(0, 6)).append("'");
			}
			String order = " order by t.socreTime";
			return pageQuery(vo, countsb.toString(), sb.append(order)
					.toString());
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceDAO.class);
		}
	}

	/**
	 * 按条件查询数据
	 * 
	 * @return
	 * @throws RollbackableException
	 */
	public List queryAllList(ParmsSpecialSocreVo socre)
			throws RollbackableException {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from ParmsSpecialSocre where 1=1");
			if (socre.getSocreTime() != null
					&& !"".equals(socre.getSocreTime())) {
				sb.append(" and socreTime='").append(
						socre.getSocreTime().substring(0, 6)).append("'");
			}if(socre.getCtcid()!=null&&!"".equals(socre.getCtcid())){
				sb.append(" and ctcid='").append(socre.getCtcid()).append("'");
			}
			return queryHqlList(sb.toString());
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceDAO.class);
		}
	}

	/**
	 * 根据城区查询ParmsSpecialInfo（专项信息表）
	 */
	public List queryDwListBychengqu(String id) throws RollbackableException {
		try {
			return queryHqlList("from ParmsSpecialInfo where receiveOrgId='"
					+ id + "'");
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceDAO.class);
		}
	}

	/**
	 * 查询年扣分
	 * 
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getYearSocre(ParmsSpecialSocre special, String dates)throws RollbackableException {
		try {
			StringBuffer sb = new StringBuffer();
			sb
					.append(
							"select sum(t.socre) from ParmsSpecialSocre t where  substr(t.socreTime,0,4)='")
					.append(dates.substring(0, 4)).append("'");
			if (special.getRecId() != null && !"".equals(special.getRecId())) {
				sb.append(" and t.recId='").append(special.getRecId()).append(
						"'");
			}
			if (special.getCtcid() != null && !"".equals(special.getCtcid())) {
				sb.append(" and t.ctcid='").append(special.getCtcid()).append(
						"'");
			}
			List list = queryHqlList(sb.toString());
			return Tools.parseInt(list.get(0));
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceDAO.class);
		}
	}
	
	/**
	 * 查询月扣分
	 * 
	 * @param dates
	 *            日期格式：yyyymmdd
	 * @return
	 * @throws RollbackableException
	 */
	public int getMonthSocre(ParmsSpecialSocre special, String dates)throws RollbackableException {
		try {
			StringBuffer sb = new StringBuffer();
			sb
					.append(
							"select sum(t.socre) from ParmsSpecialSocre t where  substr(t.socreTime,0,6)='")
					.append(dates.substring(0, 6)).append("'");
			if (special.getRecId() != null && !"".equals(special.getRecId())) {
				sb.append(" and t.recId='").append(special.getRecId()).append(
						"'");
			}
			if (special.getCtcid() != null && !"".equals(special.getCtcid())) {
				sb.append(" and t.ctcid='").append(special.getCtcid()).append(
						"'");
			}
			List list = queryHqlList(sb.toString());
			return Tools.parseInt(list.get(0));
		} catch (Exception e) {
			throw new RollbackableException("查询失败！", e, SpecialSorceDAO.class);
		}
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
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(dates);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			StringBuffer sb = new StringBuffer();
			sb
					.append(
							"select sum(t.socre) from ParmsSpecialSocre t where  to_char( to_date(t.socreTime,'yyyyMMdd'),'iw')=")
					.append(c.get(Calendar.WEEK_OF_YEAR)).append(
							" and t.recId='").append(recid).append("'");
			List list = queryHqlList(sb.toString());
			return Tools.parseInt(list.get(0));
		} catch (Exception e) {
			System.out.println(e);
			throw new RollbackableException("查询失败！", e, SpecialSorceDAO.class);
		}
	}

}
