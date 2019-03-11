package com.becoda.bkms.sys.platform.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.platform.dao.PlatFormDAO;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;

public class PlatFormService {

	private PlatFormDAO platFormDAO;

	public List<Map<String, String>> findCodeItemBySetId(String gbcode) throws BkmsException {
		StringBuffer querySQL= new StringBuffer(" SELECT b.code_item_name as name , b.code_item_abbr as abbr  ");
		querySQL.append(" FROM sys_code_set a inner join sys_code_item b on a.set_id = b.code_set_id ");
		querySQL.append(" where a.set_gbcode = '"+gbcode+"' ");
		
		return platFormDAO.queryListBySql(querySQL.toString());
		
	}
	
	public List<Map<String, String>> findTitleForIndeHasSort(String index) throws BkmsException {
		
		StringBuffer querySQL= new StringBuffer(" select b.entitytype as entitytype,b.name as name,b.isdefault as ISDEFAULT" +
				" from dictionary_entity a inner join dictionary_type b on a.id=b.entity ");
		querySQL.append(" where a.entityindex = '"+index+"' order by cast(sort as int) asc");
		
		return platFormDAO.queryListBySql(querySQL.toString());
		
	}
	
	public List<Map<String, String>> findBaseLibrary() throws BkmsException {
		
		StringBuffer querySQL= new StringBuffer(" select TITLE,  SUM(TPQD) AS TPQD  FROM bus_energy_base WHERE 1 = 1 group by title ");
		
		return platFormDAO.queryListBySql(querySQL.toString());
		
	}
	
	public Map<String, String> findCodeItemForIndex(String index) throws BkmsException {
		
		StringBuffer querySQL= new StringBuffer(" SELECT b.code_item_name as name , b.code_item_abbr as abbr  ");
		querySQL.append(" FROM sys_code_set a inner join sys_code_item b on a.set_id = b.code_set_id ");
		querySQL.append(" where a.set_gbcode = '"+index+"' ");
		
		List<Map<String, String>> list = platFormDAO.queryListBySql(querySQL.toString());
		
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator<Map<String, String>> iterator = list.iterator(); iterator
				.hasNext();) {
			Map<String, String> entityMap = iterator.next();
			map.put(entityMap.get("abbr".toUpperCase()).toString(),
					entityMap.get("name".toUpperCase()).toString());
		}
		
		return  map;
	}

	public PlatFormDAO getPlatFormDAO() {
		return platFormDAO;
	}

	public void setPlatFormDAO(PlatFormDAO platFormDAO) {
		this.platFormDAO = platFormDAO;
	}
	

}
