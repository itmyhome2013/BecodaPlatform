package com.farm.core.tree.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;
import com.farm.core.sql.query.DataQuery;

public class EasyUiTreeDAO extends GenericDAO {

	public DataQuery treeLoad(DataQuery query, String parentid,String nodeState,String isCompany) {
		String pid = "";
		if (parentid == null) {
			pid = "-1";
		} else {
			pid = parentid;
		}
		/*DataQuery dbQuery = DataQuery.init(query, "bus_b001","id as id,b001255 as name,b001206 as NODESTATE").addUserWhere(
				" and b001002 = '" + pid + "' and b001206 is not null and b001206 = '"+nodeState+"' order by b001207");*/
		DataQuery dbQuery;
		//属分公司首次进入 查询方法
		if("2".equals(nodeState) && "2".equals(isCompany)){
			dbQuery = DataQuery.init(query, "(select ID,  NAME, NODESTATE, SORT,CASE  WHEN ((SELECT COUNT(ID) FROM BUS_B001 WHERE B001002 = B.ID) = 0) THEN 0 ELSE  1  END AS ISCHILDREN FROM (select ID AS ID, b001002, B001255 AS NAME, B001206 AS NODESTATE ,b001715 as SORT  FROM bus_b001  WHERE id = '"+pid+"'  and b001206 is not null and b001206 = '1'  ) b )",
				"ID,NAME,NODESTATE,ISCHILDREN,SORT").addUserWhere(" order by sort");
		}else if("3".equals(isCompany)){
			dbQuery = DataQuery.init(query, "(select ID,  NAME, NODESTATE, SORT,CASE  WHEN ((SELECT COUNT(ID) FROM BUS_B001 WHERE B001002 = B.ID) = 0) THEN 0 ELSE  0  END AS ISCHILDREN FROM (select ID AS ID, b001002, B001255 AS NAME, B001206 AS NODESTATE ,b001715 as SORT  FROM bus_b001  WHERE id = '"+pid+"'  and b001206 is not null ) b )",
			"ID,NAME,NODESTATE,ISCHILDREN,SORT").addUserWhere(" order by sort");
		}
		else{
		    dbQuery = DataQuery.init(query, "(select ID,  NAME, NODESTATE, SORT,CASE  WHEN ((SELECT COUNT(ID) FROM BUS_B001 WHERE B001002 = B.ID) = 0) THEN 0 ELSE  1  END AS ISCHILDREN FROM (select ID AS ID, b001002, B001255 AS NAME, B001206 AS NODESTATE ,b001715 as SORT  FROM bus_b001  WHERE b001002 = '"+pid+"'  ) b )",
				"ID,NAME,NODESTATE,ISCHILDREN,SORT").addUserWhere(" order by sort");
		}
				
				//.addUserWhere(" and b001002 = '" + pid + "' and b001206 is not null and b001206 = '"+nodeState+"' order by b001207");
		return dbQuery;
	}
	
	public DataQuery treeLoadModel(DataQuery query, String id) {
		
		DataQuery dbQuery;
		dbQuery = DataQuery.init(query, "(select distinct cx.CKCXID as ID, cx.CXMC as NAME,'4' as nodestate,0 as ISCHILDREN,1 as sort from B001 xl left join BUS_SOA_JC_XL jcxl  on jcxl.CKJGID = xl.ID  left join BUS_SOA_JC_CL cl  on cl.SSXLSOA = jcxl.CKXLID  left join BUS_SOA_JC_CX cx  on cx.CKCXID = cl.CKCXID  where xl.ID = '"+id+"')",
		"ID,NAME,NODESTATE,ISCHILDREN,SORT");
		return dbQuery;
	}
	
	public DataQuery treeLoad2(DataQuery query, String parentid,String nodeState,String isCompany) {
		String pid = "";
		if (parentid == null) {
			pid = "-1";
		} else {
			pid = parentid;
		}
		/*DataQuery dbQuery = DataQuery.init(query, "bus_b001","id as id,b001255 as name,b001206 as NODESTATE").addUserWhere(
				" and b001002 = '" + pid + "' and b001206 is not null and b001206 = '"+nodeState+"' order by b001207");*/
		DataQuery dbQuery;
		if("2".equals(nodeState) && "2".equals(isCompany)){
			dbQuery = DataQuery.init(query, "(select ID,  NAME, NODESTATE, SORT,CASE  WHEN ((SELECT COUNT(ID) FROM BUS_B001 WHERE B001002 = B.ID) = 0) THEN 0 ELSE  1  END AS ISCHILDREN FROM (select ID AS ID, b001002, B001255 AS NAME, B001206 AS NODESTATE ,b001715 as SORT  FROM bus_b001  WHERE id = '"+pid+"'  and b001206 is not null and b001206 = '1'  ) b )",
				"ID,NAME,NODESTATE,ISCHILDREN,SORT").addUserWhere(" order by sort");
		}else{
			dbQuery = DataQuery.init(query, "(select ID,  NAME, NODESTATE, SORT,CASE  WHEN ((SELECT COUNT(ID) FROM PS_NXES_FIELD WHERE org_no = B.ID) = 0) THEN 0 ELSE  1  END AS ISCHILDREN FROM (select ID AS ID, b001002, B001255 AS NAME, B001206 AS NODESTATE ,b001715 as SORT  FROM bus_b001  WHERE b001002 = '"+pid+"'  ) b )",
			"ID,NAME,NODESTATE,ISCHILDREN,SORT").addUserWhere(" order by sort");
		}
		
				
				//.addUserWhere(" and b001002 = '" + pid + "' and b001206 is not null and b001206 = '"+nodeState+"' order by b001207");
		return dbQuery;
	}

	
	public DataQuery treeLoadStation(DataQuery query, String consId){
		DataQuery dbQuery;
		dbQuery = DataQuery.init(query, "(SELECT a.cons_id as id,a.cons_name as name, '3' as NODESTATE,'0' as ISCHILDREN FROM PS_NXC_CONS a left join PS_NXES_FIELD b on b.cons_id = a.cons_id where b.org_no = '"+consId+"') ",
		"id,name,NODESTATE,ISCHILDREN");
				
		return dbQuery;
	}
	
	public boolean isChildrenNode(String parentid){
		DataQuery dbQuery = DataQuery.init(null,
				"bus_b001",
				"id,b001002 as num")
				.addUserWhere(" and b001002 = '" + parentid + "'");
		List<Map<String, Object>> list;
		try {
			list = dbQuery.search().getResultList();
			if(list.size()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*public boolean isChildrenNode(String parentid) {

		try {
			SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
			Session session = sf.openSession();

			List list = session.createSQLQuery(
					"SELECT * FROM b001 where b001002 = '" + parentid + "'").list();
			if (list.size() > 0) {
				return true;
			}

		} catch (BkmsException e) {
			e.printStackTrace();
		}

		return false;
	}*/
}
