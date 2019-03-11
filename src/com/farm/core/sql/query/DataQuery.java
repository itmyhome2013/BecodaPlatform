package com.farm.core.sql.query;

import java.sql.SQLException;
import java.util.ArrayList;   
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;

/**
 * 数据库查询封装类
 * 
 * @author huxuxu
 * 
 */
public class DataQuery {
	/**
	 * 缓存器单位枚举值
	 * 
	 * @author Administrator
	 * 
	 */
	public enum CACHE_UNIT {
		/**
		 * 毫秒
		 */
		Millisecond(1), /**
		 * 秒
		 */
		second(1000), /**
		 * 分
		 */
		minute(1000 * 60);
		int num;

		CACHE_UNIT(int val) {
			num = val;
		}
	}

	private int pagesize = 10;
	private String currentPage;// 当前页
	private String sortTitleText;// 排序字段ok
	private String sortTypeText;// 排序类型ok
	private String ruleText;// 查询条件
	private String titles;
	private String tables;
	private String userWhere;
	private DBSort defaultSort;
	// 结果集合的缓存
	private static final Map<String, DataResult> resultCache = new HashMap<String, DataResult>();
	private boolean DISTINCT = false;
	// 启用缓存(毫秒数)：只要该数字大于0则表示启用，从缓存区读取数据如果缓存区没有数据则数据库查询并更新到缓冲区
	private long cacheMillisecond;

	// ----------------私有域-------------
	protected List<DBSort> sort = new ArrayList<DBSort>();
	protected List<DBRule> queryRule = new ArrayList<DBRule>();

	/**
	 * 获得查询条件格式序列
	 * 
	 * @return
	 */
	public List<DBRule> getQueryRule() {
		return queryRule;
	}

	/**
	 * 获得查询对象实例
	 * 
	 * @param currentPage
	 *            当前页
	 * @param titles
	 *            展现字段
	 * @param tables
	 *            表描述
	 * @return
	 */
	public static DataQuery getInstance(String currentPage, String titles,
			String tables) {
		DataQuery query = new DataQuery();
		query.setCurrentPage(currentPage);
		query.setTitles(titles);
		query.setTables(tables);
		return query;
	}

	/**
	 * 执行查询,该方法适用于sever中，没有对用于页面状态的序列化，没有缓存功能
	 * 
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	public DataResult search(Session session) throws SQLException {
		if (sort.size() <= 0) {
			sort.add(defaultSort);
		}
		return HibernateQueryHandle.runComplexQuery(session, this);
	}

	// cacheMillisecond

	/**
	 * 执行查询,序列化页面状态,可配置为缓存
	 * 
	 * @return
	 * @throws SQLException
	 * @throws BkmsException 
	 */
	public DataResult search() throws SQLException, BkmsException {
		String key = null;
		if (cacheMillisecond > 0) {
			
			// 缓存中超出1000个内容的时候就将缓存清空
			if (resultCache.size() > 1000) {
				resultCache.clear();
			}
		}
		if (sort.size() <= 0) {
			sort.add(defaultSort);
		}
		
		SessionFactory sessionFactory = (SessionFactory) BkmsContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		DataResult result = null;
		try {
			result = HibernateQueryHandle.runComplexQuery(session, this);
			result.setTitles(DataResults.getTitles(titles));
			result.setSortTitleText(sortTitleText);
			result.setSortTypeText(sortTypeText);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new SQLException(e);
		} finally {
			session.close();
		}
		if (cacheMillisecond > 0) {
			// 启用缓存功能,将当前结果存入缓存
			if (key != null) {
				result.setCtime(new Date());
				resultCache.put(key, result);
			}
		}
		return result;
	}



	/**
	 * 添加一个排序
	 * 
	 * @param dbsort
	 * @return
	 */
	public DataQuery addSort(DBSort dbsort) {
		sort.add(dbsort);
		return this;
	}

	/**
	 * 添加一个默认排序
	 * 
	 * @param dbsort
	 * @return
	 */
	public DataQuery addDefaultSort(DBSort dbsort) {
		defaultSort = dbsort;
		return this;
	}

	/**
	 * 清除排序
	 * 
	 * @param sort
	 */
	public DataQuery clearSort() {
		this.sort.clear();
		return this;
	}

	/**
	 * 添加一个过滤条件
	 * 
	 * @param sort
	 */
	public DataQuery addRule(DBRule rule) {
		DataQuerys.wipeVirus(rule.getValue());
		this.queryRule.add(rule);
		return this;
	}

	/**
	 * 获得一个查询条件并从query中移除该条件
	 * 
	 * @param index
	 *            查询条件索引号
	 * @return
	 */
	public DBRule getAndRemoveRule(int index) {
		DBRule dbrule = this.queryRule.get(index);
		queryRule.remove(index);
		ruleText = parseRules();
		return dbrule;
	}

	/**
	 * 获得一个查询条件并从query中移除该条件,每次获得匹配到得第一个titleName 查询条件字段名
	 * 
	 * @param titleName
	 *            查询条件字段名
	 * @return
	 */
	public DBRule getAndRemoveRule(String titleName) {
		int n = -1;
		for (int i = 0; i < queryRule.size(); i++) {
			if (queryRule.get(i).getKey().equals(titleName.toUpperCase())) {
				n = i;
			}
		}
		DBRule dbrule = null;
		if (n >= 0) {
			dbrule = getAndRemoveRule(n);
		}
		return dbrule;
	}

	/**
	 * 将条件对象集合转换为条件字符串
	 * 
	 * @return
	 */
	private String parseRules() {
		StringBuffer sb = null;
		for (DBRule node : queryRule) {
			// parentid:=:NONE_,_
			if (sb == null) {
				sb = new StringBuffer();
			} else {
				sb.append("_,_");
			}
			sb.append(node.getKey());
			sb.append(":");
			sb.append(node.getComparaSign());
			sb.append(":");
			sb.append(node.getValue());
		}
		if (sb == null) {
			return "";
		} else {
			return sb.toString();
		}
	}

	/**
	 * 清除排序
	 * 
	 * @param sort
	 */
	public DataQuery clearRule() {
		this.queryRule.clear();
		return this;
	}

	/**
	 * 是否在SQL中加入distinct
	 * 
	 * @param var
	 *            true||false
	 */
	public void SetDISTINCT(boolean var) {
		DISTINCT = var;
	}

	/**
	 * 是否加入了distinct关键字
	 * 
	 * @return
	 */
	public boolean isDISTINCT() {
		return DISTINCT;
	}

	// pojo-------------------------------
	/**
	 * 获得每页记录条数
	 * 
	 * @return
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * 设置每页记录条数
	 * 
	 * @param pagesize
	 */
	public DataQuery setPagesize(int pagesize) {
		this.pagesize = pagesize;
		return this;
	}

	/**
	 * 获得当前页
	 * 
	 * @return
	 */
	public String getCurrentPage() {
		if (currentPage == null || currentPage.trim().length() <= 0) {
			return "1";
		}
		return currentPage;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获得排序字段
	 * 
	 * @return
	 */
	public String getSortTitleText() {
		return sortTitleText;
	}

	/**
	 * 获得排序类型
	 * 
	 * @return
	 */
	public String getSortTypeText() {
		return sortTypeText;
	}

	/**
	 * 设置排序字段,但是会清理掉已有排序
	 * 
	 * @param sortTitleText
	 */
	public void setSortTitleText(String sortTitleText) {
		this.sortTitleText = sortTitleText;
		if (this.sortTitleText != null && this.sortTypeText != null) {
			sort.clear();
			sort.add(new DBSort(this.sortTitleText, this.sortTypeText));
		}
	}

	/**
	 * 设置排序类型
	 * 
	 * @param sortTypeText
	 */
	public void setSortTypeText(String sortTypeText) {
		if (sortTypeText != null
				&& sortTypeText.toUpperCase().trim().equals("NULL")) {
			sortTypeText = null;
		}
		this.sortTypeText = sortTypeText;
		if (this.sortTitleText != null && this.sortTypeText != null) {
			sort.clear();
			sort.add(new DBSort(this.sortTitleText, this.sortTypeText));
		}
		// if (this.sortTitleText != null && this.sortTypeText == null) {
		// sort.clear();
		// sort.add(new DBSort(this.sortTitleText, "asc"));
		// }
	}

	/**
	 * 获得条件描述字符串
	 * 
	 * @return
	 */
	public String getRuleText() {
		return ruleText;
	}

	/**
	 * 设置查询条件，但是会清理掉已有条件
	 * 
	 * @param ruleText
	 *            查询条件
	 */
	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
		List<DBRule> list = null;
		if (!checkStringForLimitDomain(ruleText)) {
			list = new ArrayList<DBRule>();
		} else {
			ruleText = ruleText.replace("_D_", ":");
			String[] strarray = ruleText.split("_,_");
			list = new ArrayList<DBRule>();
			for (String onestr : strarray) {
				if (onestr != null && !onestr.trim().equals("")) {
					String[] valueT = onestr.split(":",3); //最多分隔3部分
					if (valueT.length >= 3 && valueT[0] != null
							&& valueT[1] != null && valueT[2] != null) {
						if (!valueT[0].equals("") && !valueT[0].equals("")
								&& !valueT[0].equals("")) {
							DBRule dbrule = new DBRule(valueT[0], valueT[2],
									valueT[1]);
							list.add(dbrule);
						}
					}
				}
			}
		}
		queryRule.clear();
		queryRule.addAll(list);
	}

	/**
	 * 获得结果集字段
	 * 
	 * @return
	 */
	public String getTitles() {
		return titles;
	}

	/**
	 * 设置结果集字段
	 * 
	 * @return
	 */
	public void setTitles(String titles) {
		this.titles = titles;
	}

	/**
	 * 获得表描述
	 * 
	 * @return
	 */
	public String getTables() {
		return tables;
	}

	/**
	 * 设置表描述
	 * 
	 * @param tables
	 */
	public void setTables(String tables) {
		this.tables = tables;
	}

	/**
	 * 对一个查询条件类型的数据进行验证
	 * 
	 * @param str
	 * @return
	 */
	private boolean checkStringForLimitDomain(String str) {
		if (str == null)
			return false;
		else
			return true;
	}

	/**
	 * 获得用户自定义查询条件
	 * 
	 * @return
	 */
	public String getUserWhere() {
		return userWhere;
	}

	/**
	 * 设置自定义条件
	 * 
	 * @param userWhere
	 *            需要添加 AND关键字
	 */
	public void setUserWhere(String userWhere) {
		this.userWhere = userWhere;
	}

	/**
	 * 增加自定义条件
	 * 
	 * @param userWhere
	 *            需要添加 AND关键字
	 */
	public DataQuery addUserWhere(String SQLString) {
		if (this.userWhere == null) {
			this.userWhere = "";
		}
		this.userWhere = this.userWhere + SQLString;
		return this;
	}

	/**
	 * 初始化查询类
	 * 
	 * @param query
	 *            对象引用
	 * @param tables
	 *            表
	 * @param titles
	 *            字段
	 */
	public static DataQuery init(DataQuery query, String tables, String titles) {
		if (query == null) {
			query = new DataQuery();
		}
		query.setTables(tables);
		query.setTitles(titles);
		if (query.sortTypeText == null) {
			query.sortTypeText = "asc";
		}
		if (query.getCurrentPage() == null) {
			query.setCurrentPage("1");
		}
		// 删除所有排序字段不合乎要求的
		{
			int n = 0;
			List<Integer> indexArray = new ArrayList<Integer>();
			for (; n < query.sort.size(); n++) {
				if (query.sort.get(n).getSortTitleText() == null
						|| query.sort.get(n).getSortTitleText().trim()
								.toUpperCase().equals("NULL")) {
					indexArray.add(n);
				}
			}
			for (Integer index : indexArray) {
				query.sort.remove(index.intValue());
			}
		}

		return query;
	}

	/**
	 * 获得默认排序
	 * 
	 * @return
	 */
	public DBSort getDefaultSort() {
		return defaultSort;
	}

	/**
	 * 设置数据缓存
	 * 
	 * @param cachetime
	 *            缓存时间长
	 * @param cache_unit
	 *            缓存时间单位
	 */
	public void setCache(int cachetime, CACHE_UNIT cache_unit) {
		this.cacheMillisecond = cachetime * cache_unit.num;
	}

	/**
	 * 设置默认排序
	 * 
	 * @param defaultSort
	 */
	public void setDefaultSort(DBSort defaultSort) {
		this.defaultSort = defaultSort;
	}
}
