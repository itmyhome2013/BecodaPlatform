package com.becoda.bkms.tls.webmgr.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.tls.webmgr.pojo.bo.QuestionnaireBO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionnaireDAO extends GenericDAO {
	//根据年度查询调查问卷信息
	public List queryQuesByTime(String sur_start_date)
			throws RollbackableException {
		try {
			List list = hibernateTemplate
					.find("from QuestionnaireBO i where substr(i.sur_start_date,1,4) = '"
							+ sur_start_date + "'");
			if (null == list || list.size() == 0)
				return null;
			else
				return list;
		} catch (Exception e) {
			throw new RollbackableException("根据年度查询调查问卷信息失败", e,
					this.getClass());
		}
	}

	public List queryQues() throws RollbackableException {
		try {
			List list = hibernateTemplate.find("from QuestionnaireBO");
			if (null == list || list.size() == 0)
				return null;
			else
				return list;
		} catch (Exception e) {
			throw new RollbackableException("查询问卷信息失败", e,
					this.getClass());
		}
	}

	// 查询问卷表
	public List queryQues(PageVO vo, String sur_start_date)
			throws RollbackableException {
		try{
		String countHql = "Select count(b) from QuestionnaireBO b  ";
		String queryHql = "from QuestionnaireBO b ";
		StringBuffer sql = new StringBuffer();
		if (sur_start_date != null && !"".equals(sur_start_date)) {
			sql.append("where substr(b.sur_start_date,1,4) = '"
					+ sur_start_date + "'");
			countHql = countHql.concat(sql.toString());
			queryHql = queryHql.concat(sql.toString());
		}

		return this.pageQuery(vo, countHql, queryHql);
		} catch (Exception e) {
			throw new RollbackableException("查询问卷信息失败", e,
					this.getClass());
		}

	}
	
	// 查询问卷表的选项
	public Map queryOption(String sur_start_date) throws RollbackableException {
		try {
			String sql;
			if (sur_start_date != null && !"".equals(sur_start_date)) {
			 sql = "select sur_id from tls_survey where substr(sur_start_date,1,4) ='"
					+ sur_start_date + "'";
			}else
				 sql = "select sur_id from tls_survey";
					
			List list = getJdbcTemplate().queryForList(sql);//问卷数
			String sur_id;
			List listOption;
			//Map optionMap;
			Map map = new HashMap();
			for(int i = 0; i<list.size();i++){
				 sur_id =(String)((Map)list.get(i)).get("sur_id");
				 sql="select OPT_NAME from TLS_SURVEY_OPTION where SUR_ID ='"
						+ sur_id + "' order by OPT_NO";
				 listOption = getJdbcTemplate().queryForList(sql);//特定的选项数
				 String[] options=new String[listOption.size()];
				 for(int j = 0; j<listOption.size();j++){
					 options[j] =(String)((Map)listOption.get(j)).get("OPT_NAME");
				 }
				 map.put(sur_id, options);
				 
				 
			}

			return map;
		} catch (Exception e) {
			throw new RollbackableException("查找调查问卷选项失败", e,
					this.getClass());
		}
	}

	// 根据surid查询
	public QuestionnaireBO queryQuesBySurID(String surid)
			throws RollbackableException {
		try {
			Object o = (QuestionnaireBO) hibernateTemplate.get(
					QuestionnaireBO.class, surid);
			if (o != null)
				return (QuestionnaireBO) o;
			else
				return null;
		} catch (Exception e) {
			throw new RollbackableException("查找调查问卷失败", e,
					this.getClass());
		}
	}

	// 发布前查询问卷选项是否为空
	public boolean checkIsNullOption(String[] ids)
			throws RollbackableException {
		try {
			String idstr = "";
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					if (i == 0) {
						idstr = "'" + ids[0] + "'";
					} else {
						idstr = idstr + ",'" + ids[i] + "'";
					}
				}
			}
			
			String sql = "select count(*) from TLS_SURVEY_OPTION o LEFT JOIN TLS_SURVEY s on o.SUR_ID =s.SUR_ID where o.SUR_ID in (" + idstr+")";
			int i =getJdbcTemplate().queryForInt(sql);
			if(i!=0){
				return false;
			}
			else return true;
		} catch (Exception e) {
			throw new RollbackableException("查询问卷调查选项信息失败", e,
					this.getClass());
		}
	}
	
	// 批量更新问卷调查状态
	public void updateAllStatus(String[] ids, String status)
			throws RollbackableException {
		try {
			String idstr = "";
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					if (i == 0) {
						idstr = "'" + ids[0] + "'";
					} else {
						idstr = idstr + ",'" + ids[i] + "'";
					}
				}
			}
			String sql = "UPDATE tls_survey  SET sur_status='" + status
					+ "' where sur_ID in(" + idstr + ")";
			getJdbcTemplate().execute(sql);
		} catch (Exception e) {
			throw new RollbackableException("批量更新问卷状态", e,
					this.getClass());
		}
	}

	// 删除已有问卷的选项内容
	public void deleteOptions(String[] surid) throws RollbackableException {
		String idstr = "";
		if (surid != null) {
			for (int i = 0; i < surid.length; i++) {
				if (i == 0) {
					idstr = "'" + surid[0] + "'";
				} else {
					idstr = surid + ",'" + surid[i] + "'";
				}
			}
		}

		String sql = "delete from TLS_SURVEY_OPTION where sur_id in(" + idstr
				+ ")";
		getJdbcTemplate().execute(sql);
	}
	
	// 删除已有问卷的调查结果
	public void deleteResult(String[] surid) throws RollbackableException {
		String idstr = "";
		if (surid != null) {
			for (int i = 0; i < surid.length; i++) {
				if (i == 0) {
					idstr = "'" + surid[0] + "'";
				} else {
					idstr = surid + ",'" + surid[i] + "'";
				}
			}
		}

		String sql = "delete from TLS_SURVEY_RESULT where sur_id in(" + idstr
				+ ")";
		getJdbcTemplate().execute(sql);
	}

	// 根据surid查询是否已经存在选项内容
	public int queryOptionsBySurID(String surid) throws RollbackableException {
		try {
			String sql = "select count(*) from TLS_SURVEY_OPTION where sur_id ='"
					+ surid + "'";
			int i = getJdbcTemplate().queryForInt(sql);

			return i;
		} catch (Exception e) {
			throw new RollbackableException("查找调查问卷的对应失败", e,
					this.getClass());
		}
	}

	// 根据surid查询是否已经存在选项内容
	public List queryOptionsBySur(String surid) throws RollbackableException {
		try {
			String sql = "select * from TLS_SURVEY_OPTION where sur_id ='"
					+ surid + "' order by opt_no";
			List i = getJdbcTemplate().queryForList(sql);

			return i;
		} catch (Exception e) {
			throw new RollbackableException("查找调查问卷的对应失败", e,
					this.getClass());
		}
	}
	
	// 根据surid查询调查问卷结果表结果
	public List queryResult(String surid) throws RollbackableException {
		try {
			String sql = "select count(*) as num, avg(a.opt_no) as no from TLS_SURVEY_RESULT b  left join TLS_SURVEY_OPTION a  on a.opt_id =b.opt_id where a.sur_id ='"
					+ surid + "'group by a.opt_no order by no";
			List i = getJdbcTemplate().queryForList(sql);

			return i;
		} catch (Exception e) {
			throw new RollbackableException("查找调查问卷结果失败", e,
					this.getClass());
		}
	}

	// 查询调查人员
	public List queryQuesRightByName(PageVO vo, String surid, String name,String type)
			throws RollbackableException {
		try{
		String countHql = null;
		String queryHql = null;
		StringBuffer sql = new StringBuffer();
		if (name != null && !"".equals(name)) {
			countHql = "select count(quesbo) from QuesRightBO quesbo,PersonBO perbo ";
			queryHql = "select quesbo from QuesRightBO quesbo,PersonBO perbo ";
			sql
					.append("where quesbo.sur_id='"
							+ surid
							+ "' and quesbo.per_org_id=perbo.personId and perbo.name like '%"+name+"%'");
		} else {
			countHql = "select count(quesbo) from QuesRightBO quesbo ";
			queryHql = "from QuesRightBO quesbo ";
			sql.append("where quesbo.sur_id='" + surid + "' and quesbo.r_type ='"+type+"'");
		}
		countHql = countHql.concat(sql.toString());
		queryHql = queryHql.concat(sql.toString());

		return this.pageQuery(vo, countHql, queryHql);
		} catch (Exception e) {
			throw new RollbackableException("查询调查人员失败", e, this.getClass());
		}
	}

	// 根据surid和人员ID查询是否已经存在相应的调查人员
	public int queryQuesRightBySurID_PerID(String surid, String perid)
			throws RollbackableException {
		try {
			String sql = "select count(*) from TLS_VOTE_RIGHT where sur_id ='"
					+ surid + "' and per_org_id ='" + perid + "'";
			int i = getJdbcTemplate().queryForInt(sql);

			return i;
		} catch (Exception e) {
			throw new RollbackableException("查找对应失败", e, this.getClass());
		}
	}
	//删除人员权限
	public void deleteRight(String[] surid, String[] perid)
			throws RollbackableException {
		
		String sql=null;
		String idstr = "";
		if (surid != null) {
			for (int i = 0; i < surid.length; i++) {
				if (i == 0) {
					idstr = "'" + surid[0] + "'";
				} else {
					idstr = idstr + ",'" + surid[i] + "'";
				}
			}
		}
		
		String peridstr = "";
		if (perid != null) {
			for (int i = 0; i < perid.length; i++) {
				if (i == 0) {
					peridstr = "'" + perid[0] + "'";
				} else {
					peridstr = peridstr + ",'" + perid[i] + "'";
				}
			}
		}
		
		if (perid == null||perid.equals("")) {
			 sql = "delete from TLS_VOTE_RIGHT where sur_id in(" + idstr + ")";
					
		} else {
			 sql = "delete from TLS_VOTE_RIGHT where sur_id in(" + idstr + ")"
					+ "and per_org_id in (" + peridstr + ")";
		}
		getJdbcTemplate().execute(sql);
		

	}
	
	// 根据surid查询调查问卷结果表其他选项结果
	public List queryResultOther(String surid) throws RollbackableException {
		try {
			String sql = "select  b.OTHER_INFO from TLS_SURVEY_RESULT b where b.OPT_TYPE='2' AND b.sur_id ='"
					+ surid + "'";
			List i = getJdbcTemplate().queryForList(sql);

			return i;
		} catch (Exception e) {
			throw new RollbackableException("查找调查问卷结果其他项", e,
					this.getClass());
		}
	}
}
