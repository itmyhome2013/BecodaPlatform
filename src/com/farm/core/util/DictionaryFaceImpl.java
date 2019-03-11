package com.farm.core.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.platform.dao.PlatFormDAO;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;

class DictionaryFaceImpl implements DictionaryFaceInter {
	
	private PlatFormDAO platFormDAO;
	
	public Map<String, String> findTitleForIndex(String index) throws BkmsException {
		return getMapKeyValueByListEntity(findTitleForIndeHasSort(index));
	}
	
	public Map<String, String> getMapKey(List<Map<String, String>> list){
		return getMapKeyValueByListEntity(list);
	}
	
	@Override
	public List<Map<String, Object>> findRuleItemOption(String parentid,String fieldName) throws BkmsException{
		
		DataQuery query = DataQuery
				.getInstance(
						"1",
						"entitytype as mark,name as name",
						"DICTIONARY_TYPE");
		if(fieldName.equals("xm")){	
			query.addRule(new DBRule("entity", "079578", "="));
		}else if(fieldName.equals("jgmc")){
			query.addRule(new DBRule("entity", "189059", "="));
		}else if(fieldName.equals("jldw")){
			query.addRule(new DBRule("entity", "388157", "="));
		}
		query.setPagesize(100);
		query.addDefaultSort(new DBSort("entitytype+0", "asc"));
		if(!Tools.stringIsNull(parentid)){
			if(fieldName.equals("xm")){
				query.addUserWhere(" and (id='"+parentid+"' or parentid='"+parentid+"') ");			
			}else{
				query.addUserWhere(" and entitytype="+parentid+" ");
			}
		}
		try {
			return query.search().getResultList();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将一个pageDomain的List集合转换为map
	 * 
	 * @param list
	 * @return
	 */
	private  Map<String, String> getMapKeyValueByListEntity(
			List<Map<String, String>> list) {
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator<Map<String, String>> iterator = list.iterator(); iterator
				.hasNext();) {
			Map<String, String> entityMap = iterator.next();
			map.put(entityMap.get("entitytype".toUpperCase()).toString(),
					entityMap.get("name".toUpperCase()).toString());
		}
		return map;
	}

	public List<Map<String, String>> findTitleForIndeHasSort(String index) throws BkmsException {
		StringBuffer querySQL= new StringBuffer(" select b.entitytype as entitytype,b.name as name" +
		" from dictionary_entity a inner join dictionary_type b on a.id=b.entity ");
		querySQL.append(" where a.entityindex = '"+index+"' ");
		
		return platFormDAO.queryListBySql(querySQL.toString());
	}

	public PlatFormDAO getPlatFormDAO() {
		return platFormDAO;
	}

	public void setPlatFormDAO(PlatFormDAO platFormDAO) {
		this.platFormDAO = platFormDAO;
	}
	
}
