package com.farm.core.dictionary.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.farm.core.sql.query.DataQuery;

public class DictionaryDAO extends GenericDAO {

	public DataQuery queryAll(DataQuery query){
		DataQuery dbQuery = DataQuery
		.init(query,
				"dictionary_entity",
				"id,name,entityindex,type,type as types,comments");
		return dbQuery;
	}
}
