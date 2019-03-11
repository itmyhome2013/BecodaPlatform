package com.becoda.bkms.common.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
//import com.becoda.bkms.emp.pojo.bo.ZpBlacklistBO;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-9
 * Time: 9:37:41
 */
public class GenericDAO {

    protected BkmsHibernateTemplate hibernateTemplate;
    protected BkmsJdbcTemplate jdbcTemplate;

    public BkmsHibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(BkmsHibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public BkmsJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(BkmsJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List pageQuery(PageVO vo, String countHql, String queryHql) throws RollbackableException {
        Session s;
        try {
            SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
            s = sf.getCurrentSession();// SessionFactoryUtils.getSession(hibernatetemplate.getSessionFactory(), true);

//            s = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            List list;
            list = s.createQuery(countHql).list();
            int runtimePageSize = vo.getPageSize();//add by sunmh 2015-01-10
            int rSize = Tools.parseInt(list.get(0));
            //int totalPage = rSize / vo.getPageSize(); //delete by sunmh 2015-01-10
            int totalPage = rSize / runtimePageSize;     //add by sunmh 2015-01-10
            //if (rSize %vo.getPageSize() > 0)          //delete by sunmh 2015-01-10
            if (rSize % runtimePageSize > 0)             //add by sunmh 2015-01-10
                totalPage++;

            if (vo.getCurrentPage() > totalPage && totalPage != 0)
                vo.setCurrentPage(totalPage);

            vo.setTotalPage(totalPage);
            vo.setTotalRecord(rSize);

            Query q = s.createQuery(queryHql);
            //q.setFirstResult((vo.getCurrentPage() - 1) * vo.getPageSize());   //delete by sunmh 2015-01-10
            q.setFirstResult((vo.getCurrentPage() - 1) * runtimePageSize);      //add by sunmh 2015-01-10
            q.setMaxResults(runtimePageSize);

            return q.list();
        } catch (Exception e) {
            throw new RollbackableException("分页查询错误", e, this.getClass());
        }
    }

    public List pageQuery(PageVO vo, String countHql, String queryHql, Object[] obj, Type[] types) throws RollbackableException {
        Session s;
        try {
            SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");

            s = sf.getCurrentSession();// SessionFactoryUtils.getSession(hibernatetemplate.getSessionFactory(), true);
            List list;
            Query q = s.createQuery(countHql);
            for (int i = 0; i < obj.length; i++)
                q.setParameter(i, obj[i], types[i]);
            list = q.list();
            int rSize = Tools.parseInt(list.get(0));
            int runtimePageSize = vo.getPageSize();//add by sunmh 2015-01-10
            
            //int totalPage = rSize / vo.getPageSize();  //delete by sunmh 2015-01-10
            int totalPage = rSize / runtimePageSize;      //add by sunmh 2015-01-10
            //if (rSize % vo.getPageSize() > 0)          //delete by sunmh 2015-01-10
            if (rSize % runtimePageSize > 0)             //add by sunmh 2015-01-10
                totalPage++;

            if (vo.getCurrentPage() > totalPage && totalPage != 0)
                vo.setCurrentPage(totalPage);

            vo.setTotalPage(totalPage);
            vo.setTotalRecord(rSize);

            q = s.createQuery(queryHql);
            for (int i = 0; i < obj.length; i++)
                q.setParameter(i, obj[i], types[i]);

            // return q.setFirstResult((vo.getCurrentPage() - 1) * vo.getPageSize()).setMaxResults(vo.getPageSize()).list();//delere by sunmh 2015-01-10
            return q.setFirstResult((vo.getCurrentPage() - 1) * runtimePageSize).setMaxResults(runtimePageSize).list();  //add by sunmh 2015-01-10

        } catch (Exception e) {
            throw new RollbackableException("参数分页查询错误", e, getClass());
        }
    }


    public String createBo(Object obj) throws RollbackableException {
        return (String) hibernateTemplate.save(obj);
    }
//    public void deleteBBO(String id) throws RollbackableException {
//				try {
//						Object o = hibernateTemplate.get(ZpBlacklistBO.class, id);
//						if (o != null) {
//								this.deleteBo(o);
//						}
//				} catch (Exception e) {
//						throw new RollbackableException("删除失败！", e, this.getClass());
//				}
//		}

    
    public static String date2Str(Date date, String fomar) {
    	SimpleDateFormat fomart = new SimpleDateFormat(fomar);
		if (null == date) {
			return null;
		}
		return fomart.format(date);
	}
    
    public Object findBo(Class clazz, String id) throws RollbackableException {
    	Object obj = hibernateTemplate.get(clazz, id);
        return obj;
    }

    public Map findFirstRecord(String sql) throws RollbackableException {
        List list = jdbcTemplate.queryForList(sql);
        return list == null || list.size() < 1 ? null : (Map) list.get(0);
    }

    public String queryForString(String sql) throws RollbackableException {
        try {
            Map map = findFirstRecord(sql);
            if (map != null && map.size() > 0) {
                Object o = map.get(map.keySet().iterator().next());
                return o == null ? "" : o.toString();
            }
            return "";
//            return (String) jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            throw new RollbackableException("查询sql语句错误:" + sql, e, getClass());
        }
    }

    /**
     * 将传入的bo数据更新到数据库中 ,需要传入主键值,bo数据
     *
     * @param pk 待更新bo的主键
     * @param bo 待更新bo
     */
    public void updateBo(String pk, Object bo) throws RollbackableException {
        hibernateTemplate.update(bo);
    }

    public void deleteBo(Object po) throws RollbackableException {
        hibernateTemplate.delete(po);
    }

    public void deleteBo(Collection polist) throws RollbackableException {
        hibernateTemplate.deleteAll(polist);
    }

    /**
     * 根据id查询一个对象,返回的对象脱离hibernate实体管理.
     *
     * @param clazz
     * @param pk
     * @return
     * @throws RollbackableException
     * @deprecated
     */
    public Object findBoById(Class clazz, String pk) throws RollbackableException {
        try {
            return hibernateTemplate.get(clazz, pk);
        } catch (Exception e) {
            throw new RollbackableException("查询单个记录失败", e, this.getClass());
        }
    }

    /**
     * 使用DetachedCriteria进行查询，各模块可以在service里组装DC，实现 > < <> like not  in  isnull 等查询条件
     *
     * @param vo pagevo 如果为空则查询全部记录，
     * @return
     */
    public List findByCriteria(DetachedCriteria dc, PageVO vo) {
        if (vo == null)
            return hibernateTemplate.findByCriteria(dc);

        List list;
        dc.setProjection(Projections.rowCount());
        list = hibernateTemplate.findByCriteria(dc);
        //计算总记录数
        int rSize = Tools.parseInt(list.get(0));
        int runtimePageSize = vo.getPageSize();
        
        int totalPage = rSize / runtimePageSize;
        
        if (rSize % runtimePageSize > 0)
            totalPage++;
        
        if (vo.getCurrentPage() > totalPage && totalPage != 0)
            vo.setCurrentPage(totalPage);

        vo.setTotalPage(totalPage);
        vo.setTotalRecord(rSize);

        int firstResult = (vo.getCurrentPage() - 1) * runtimePageSize;
        return hibernateTemplate.findByCriteria(dc, firstResult, runtimePageSize);
    }


    /**
     * 根据持久化类和map构成的查询条件进行查询
     *
     * @param clazz     持久化类
     * @param parameter 持久化类属性为key，查询参数为value
     * @param order     格式为：[排序属性名1] [asc/desc][逗号分割] 下一排序属性,和sql orderby 后面的内容相似
     * @return
     */
    public List findByMap(Class clazz, Map parameter, String order) {
        return this.findByMap(clazz, parameter, order, null);
    }

    /**
     * 根据持久化类和map构成的查询条件进行查询
     * 如果parameter传入为null 或者size=0 则查询全表数据 ，慎重.
     *
     * @param clazz     持久化类
     * @param parameter 持久化类属性为key，查询参数为value
     * @param order     格式为：[排序属性名1] [asc/desc][逗号分割] 下一排序属性,和sql orderby 后面的内容相似
     * @param vo        分页信息
     * @return
     */
    public List findByMap(Class clazz, Map parameter, String order, PageVO vo) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        if (parameter != null) {
            dc.add(Restrictions.allEq(parameter));
        }
        return findByDetachedCriteria(order, vo, dc);


    }

    private List findByDetachedCriteria(String order, PageVO vo, DetachedCriteria dc) {
        //分解排序条件
        if (order != null && order.length() > 0) {
            String[] orderpart = order.split(",");
            for (int i = 0; i < orderpart.length; i++) {
                String[] part = orderpart[i].split(" ");
                if (part.length == 2 && "DESC".equalsIgnoreCase(part[1]))
                    dc.addOrder(Order.desc(part[0]));
                else
                    dc.addOrder(Order.asc(part[0]));
            }
        }

        return this.findByCriteria(dc, vo);
    }

    public List findByExample(Object exampleEntity) throws RollbackableException {
        return hibernateTemplate.findByExample(exampleEntity);
    }

    /**
     * @param exampleEntity
     * @param pagevo        如果传入null 则不分页
     * @param orders        排序属性名和升序降序说明（asc|desc），多个字段逗号分隔。类似 field1 desc, field2 asc
     * @return
     * @throws RollbackableException
     */
    public List findByExample(Object exampleEntity, PageVO pagevo, String orders) throws RollbackableException {
        if (exampleEntity == null) return null;
        DetachedCriteria dc = DetachedCriteria.forClass(exampleEntity.getClass());
        dc.add(Example.create(exampleEntity));
        return this.findByDetachedCriteria(orders, pagevo, dc);
    }

    public List findByExample(Object exampleEntity, String orders) throws RollbackableException {
        if (exampleEntity == null) return null;
        DetachedCriteria dc = DetachedCriteria.forClass(exampleEntity.getClass());
        dc.add(Example.create(exampleEntity));
        return this.findByDetachedCriteria(orders, null, dc);
    }

    public Map queryMap(String sql) {
        Map map = new HashMap();
        List list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map o = (Map) list.get(i);
                Iterator it = o.keySet().iterator();
                if (it.hasNext()) {
                    Object o1 = it.next();
                    map.put(o.get(o1), o.get(it.next()));
                }
            }
        }
        return map;
    }
    public List queryHqlList(String hql) throws RollbackableException {
        try {
            return this.hibernateTemplate.find(hql);
        } catch (Exception e) {
            throw new RollbackableException(e, getClass());
        }
    }
    public List queryFirstField(String sql) {
        List relist = new ArrayList();
        List list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map o = (Map) list.get(i);
                relist.add(o.get(o.keySet().iterator().next()));
            }
        }
        return relist;
    }

    class QueryResult {
        public List result;
        public PageVO page;
    }
    
    
    /**
	 * 张晓亮 新增
	 * @方法名称: queryPageListBySql
	 * @描述：TODO(sql分页查询)
	 * @返回值类型： List<Map<String,String>>
	 * @param vo
	 *            分页对象
	 * @param countSQL
	 *            记录数据SQL
	 * @param querySQL
	 *            查询SQL
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String,String>> queryPageListBySql(PageVO vo, String countSQL, String querySQL) throws BkmsException{
		 SessionFactory sessionFactory = (SessionFactory) BkmsContext.getBean("sessionFactory");
		 Session session = sessionFactory.getCurrentSession();
		 List countList= session.createSQLQuery(countSQL).list();
		 int runtimePageSize = vo.getPageSize();
		 int rSize = Tools.parseInt(countList.get(0));
		 int totalPage = rSize / runtimePageSize; 
		 if (rSize % runtimePageSize > 0)             
             totalPage++;
         if (vo.getCurrentPage() > totalPage && totalPage != 0)
             vo.setCurrentPage(totalPage);
         vo.setTotalPage(totalPage);
         vo.setTotalRecord(rSize);

         Query query = session.createSQLQuery(querySQL.toString());
         query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
         query.setFirstResult((vo.getCurrentPage() - 1) * runtimePageSize);      
         query.setMaxResults(runtimePageSize);
         List resultlist = query.list();
         if(resultlist.isEmpty()){
        	 return null;
         }else{
        	 return (List<Map<String,String>>)resultlist;
         }
	}

	/**
	 * 张晓亮 新增
	 * @方法名称: queryListBySql
	 * @描述：TODO(sql查询)
	 * @返回值类型： List<Map<String,String>>
	 * @param querySQL
	 *            查询SQL
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String,String>> queryListBySql(String querySQL) throws BkmsException{
		SessionFactory sessionFactory = (SessionFactory) BkmsContext.getBean("sessionFactory");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(querySQL.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
        List resultlist = query.list();
        if(resultlist.isEmpty()){
       	 return null;
        }else{
       	 return (List<Map<String,String>>)resultlist;
        }
	}
	
	/**
	 * add by hudl esayUI pageQuery
	 * @param vo
	 * @param countHql
	 * @param queryHql
	 * @return
	 * @throws RollbackableException
	 */
	public Map pageQueryByEsayUI(int page , int rows, String countHql, String queryHql) throws RollbackableException {
        Session s;
        try {
            SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
            s = sf.getCurrentSession();
            List list;
            list = s.createQuery(countHql).list();
            int rSize = Tools.parseInt(list.get(0));
            int totalPage = rSize / rows;    
            if (rSize % rows > 0){          
                totalPage++;
            }
            Query q = s.createQuery(queryHql);
            q.setFirstResult((page - 1) * rows);      
            q.setMaxResults(rows);

            List relist = q.list();
            Map map = new HashMap();
            map.put("total", rSize);
            map.put("rows", relist);
            return map;
        } catch (Exception e) {
            throw new RollbackableException("分页查询错误", e, this.getClass());
        }
    }
	
	/**
	 * add by hudl esayUI pageQuery  sql
	 * @param vo
	 * @param countSQL
	 * @param querySQL
	 * @return
	 * @throws BkmsException
	 */
	public Map pageQueryByEsayUISql(int page , int rows, String countSQL, String querySQL) throws BkmsException{
		try {
			SessionFactory sessionFactory = (SessionFactory) BkmsContext.getBean("sessionFactory");
			Session session = sessionFactory.getCurrentSession();
			List countList= session.createSQLQuery(countSQL).list();
			int rSize = Tools.parseInt(countList.get(0));
			int totalPage = rSize / rows; 
			if (rSize % rows > 0){
			   totalPage++;
			}
	        Query query = session.createSQLQuery(querySQL.toString());
	        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
	        query.setFirstResult((page - 1) * rows);      
	        query.setMaxResults(rows);
	        List resultlist = query.list();
	        if(resultlist.isEmpty()){
	        	return null;
	        }else{
	        	Map map = new HashMap();
	            map.put("total", rSize);
	            map.put("rows", resultlist);
	        	return map;
	        }
        } catch (Exception e) {
            throw new RollbackableException("分页查询错误", e, this.getClass());
        }
	}
	
	/**
	 * add by hudl 批量删除 hql
	 * @param hql
	 * @param ids
	 * @throws BkmsException
	 */
	public int deleteByIds(String hql,String columnName,String ids[])throws BkmsException{
		for (int i = 0; i < ids.length; i++) {
			if(i==0) {
                hql += " where "+columnName+" = '"+ids[i]+"'";
            } else {
                hql += " or " + columnName+" = '"+ids[i]+"'";
            }
		}
		SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
		Session session = sf.getCurrentSession();
		Query q= session.createQuery(hql);
		int count = q.executeUpdate();
		session.flush();
		session.clear();
        return count;
	}
	
	/**
	 * add by hudl 批量删除 sql
	 * @param hql
	 * @param ids
	 * @throws BkmsException
	 */
	public int deleteByIdsSql(String sql,String columnName,String ids[])throws BkmsException{
		for (int i = 0; i < ids.length; i++) {
			if(i==0) {
				sql += " where "+columnName+" = '"+ids[i]+"'";
            } else {
            	sql += " or " + columnName+" = '"+ids[i]+"'";
            }
		}
		SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
		Session session = sf.getCurrentSession();
		Query q= session.createSQLQuery(sql);
		int count = q.executeUpdate();
		session.flush();
		session.clear();
        return count;
	}
}
