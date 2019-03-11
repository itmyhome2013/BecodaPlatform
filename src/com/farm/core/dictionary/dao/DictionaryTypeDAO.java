package com.farm.core.dictionary.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;
import com.farm.core.dictionary.bo.DictionaryTypeBO;

public class DictionaryTypeDAO extends GenericDAO {

	public List<DictionaryTypeBO> findBoByEntity(String entity){
		List<DictionaryTypeBO> list = new ArrayList<DictionaryTypeBO>();
		try {
			SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
			Session session = sf.getCurrentSession();
			
			list = session.createCriteria(DictionaryTypeBO.class)  
	        .add(Restrictions.like("entity", entity))  
	        .list();
			
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 检查是否已存在默认选项
	 * @param id
	 * @return
	 */
	public boolean checkIsHasDefault(String id){
	
		try {
			SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
			Session session = sf.getCurrentSession();
			
			List list = session.createSQLQuery("select * from dictionary_entity a inner join dictionary_type b on a.id = b.entity where a.id = '"+id+"' and isdefault = '0'")
				.list();
			if(list.size()>0){
				return true;
			}
			
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
