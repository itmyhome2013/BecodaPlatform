package com.farm.core.sql.query;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;

/**
 * hibernate的帮助类
 */
public class HibernateQueryHandle {
	private static final String SqlSelectSize = " select count(*) num "; // 默认查询数量头

	/**
	 * 执行查询条件
	 * 
	 * @param session
	 *            hibernateSession
	 * @param sql
	 * @param pagesize
	 *            每页多少条
	 * @param currentPage
	 *            当前页
	 * @return
	 * @throws SQLException
	 */
	protected static DataResult runComplexQuery1(Session session, String sql,
			int pagesize, int currentPage) throws SQLException {
		if (sql.indexOf("*") > 0) {
			throw new SQLException("*不能存在于查询语句中，请明确查询字段!");
		}
		sql = sql.toUpperCase();
		DataResult result = null;
		int firstResourt;// 开始条数
		int sizeResourt;// 单页显示
		sizeResourt = pagesize;
		firstResourt = (currentPage - 1) * sizeResourt;
		String titles = sql.toUpperCase().substring(0, sql.indexOf("FROM"));
		String partSql = sql.toUpperCase().substring(sql.indexOf("FROM"));
		List<Map<String, Object>> limap = DataResults.getMaps(titles,
				runLimitQuery(session, sql, firstResourt, sizeResourt));
		result = DataResult.getInstance(limap, runLimitQueryNum(session,
				partSql), currentPage, pagesize);

		return result;
	}

	/**
	 * sql查询
	 * 
	 * @param session
	 *            hibernate会话
	 * @param dataquery
	 * @return
	 * @throws SQLException
	 */
	protected static DataResult runComplexQuery(Session session,
			DataQuery dataquery) throws SQLException {
		DataResult result = null;
		try {
			int firstResourt;// 开始条数
			int sizeResourt;// 单页显示
			String upsql = praseSQL(dataquery);
			String partSql = upsql.substring(upsql.indexOf(" FROM "));
			String headsql = upsql.substring(0, upsql.indexOf(" FROM "));
			if (headsql.indexOf("*") >= 0) {
				throw new SQLException("select can't contain *");
			}
			sizeResourt = dataquery.getPagesize();
			firstResourt = (Integer.valueOf(dataquery.getCurrentPage().trim()) - 1)
					* sizeResourt;
			// 将一个pageDomain中的list<object>解析为map<String,String>
			List<Map<String, Object>> limap = DataResults.getMaps(dataquery
					.getTitles(), runLimitQuery(session, upsql, firstResourt,
					sizeResourt));
			if (dataquery.isDISTINCT()) {
				if (upsql.indexOf("ORDER BY") > 0) {
					upsql = upsql.substring(0, upsql.indexOf("ORDER"));
				}
				partSql = " FROM (" + upsql + ") counum";
			}
			result = DataResult.getInstance(limap, runLimitQueryNum(session,
					partSql), Integer.valueOf(dataquery.getCurrentPage()),
					dataquery.getPagesize());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	/**
	 * 查询条件转换成sql语句
	 * 
	 * @param dataquery
	 *            查询条件封装
	 * @return
	 * @throws SQLException
	 */
	public static String praseSQL(DataQuery dataquery) throws SQLException {
		String distinct = "";
		if (dataquery.isDISTINCT()) {
			distinct = " distinct ";
		}
		StringBuffer SQL_run = new StringBuffer().append("select ").append(
				distinct).append(dataquery.getTitles().toUpperCase()).append(
				getSql_part(dataquery));
		return upCaseSQLKEY(SQL_run.toString());
	}

	/**
	 * 将SQL关键字转换为大写
	 * 
	 * @param SQL
	 * @return
	 */
	private static String upCaseSQLKEY(String SQL) {
		SQL = SQL.replace(" select ", " SELECT ");
		SQL = SQL.replace(" from ", " FROM ");
		SQL = SQL.replace(" as ", " AS ");
		SQL = SQL.replace(" where ", " WHERE ");
		SQL = SQL.replace(" order by ", " ORDER BY ");
		return SQL;
	}

	@SuppressWarnings("unchecked")
	private static List<Object[]> runLimitQuery(Session session_, String Sql,
			int firstResourt, int sizeResourt) {
		List list = null;
		try {
			SQLQuery sqlQuery = session_.createSQLQuery(Sql);
			sqlQuery.setFirstResult(firstResourt);
			sqlQuery.setMaxResults(sizeResourt);
			list = sqlQuery.list();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	private static int runLimitQueryNum(Session session_, String Sqlpart) {
		if (Sqlpart.toUpperCase().indexOf("ORDER BY") > 0) {
			Sqlpart = Sqlpart.substring(0, Sqlpart.toUpperCase().indexOf(
					"ORDER BY"));
		}
		SQLQuery sqlQuery = session_.createSQLQuery(SqlSelectSize + Sqlpart);
		Object num = 0;
		if(sqlQuery.list().size()!=0){
			num = sqlQuery.list().get(0);
		}
		Integer renum = (Integer) Integer.valueOf(num.toString());
		int n = renum.intValue();
		return n;
	}

	private static String getSql_part(DataQuery query) throws SQLException {
		String sql_part = getSql_from(query) + getSql_where(query)
				+ getSortWhere(query);
		return sql_part;
	}

	private static String getSortWhere(DataQuery query) {
		List<DBSort> sortList = query.sort;
		StringBuffer where = new StringBuffer(" order by ");
		boolean isHaveSort = false;
		for (Iterator<DBSort> iterator = sortList.iterator(); iterator
				.hasNext();) {
			DBSort name = (DBSort) iterator.next();
			if (name != null && name.getSortTitleText() != null
					&& name.getSortTypeText() != null
					&& !name.getSortTitleText().equals("")
					&& !name.getSortTypeText().equals("")) {
				where.append(name.getSortTitleText());
				where.append(" ");
				where.append(name.getSortTypeText());
				isHaveSort = true;
				if (iterator.hasNext()) {
					where.append(" , ");
				}
			}
		}
		if (!isHaveSort) {
			return "";
		}
		return where.toString();
	}

	private static String getSql_from(DataQuery query) {
		String sql_from = " from " + query.getTables() + " ";
		return sql_from;
	}

	private static String getSql_where(DataQuery query) throws SQLException {
		if (query.queryRule == null) {
			throw new SQLException("whereList is null!");
		}
		Iterator<DBRule> it_ = query.queryRule.iterator();
		StringBuffer where_ = new StringBuffer("");
		where_.append(" where 1=1 ");
		while (it_.hasNext()) {
			DBRule _queryld = it_.next();
			if (_queryld != null && _queryld.getValue() != null)
				where_.append(_queryld.getThisLimit());
		}
		String sql_where = where_.toString();
		if (query.getUserWhere() != null
				&& query.getUserWhere().trim().length() > 1) {
			sql_where = sql_where + " " + query.getUserWhere() + " ";
		}
		return sql_where;
	}

}
