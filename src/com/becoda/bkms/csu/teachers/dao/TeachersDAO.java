package com.becoda.bkms.csu.teachers.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.teachers.pojo.KpPersonLibrary;
import com.becoda.bkms.csu.test.dao.TestDAO;

public class TeachersDAO extends GenericDAO {

	public Map queryList(int page, int rows, Map parameter) throws RollbackableException{
		// TODO Auto-generated method stub
		try {
            String sql ="  from KpPersonLibrary t where 1=1";
            String countsb="select count(t) from KpPersonLibrary t where 1=1 ";
            if(parameter.get("name")!=null &&!parameter.get("name").toString().equals("")){
            	sql+=" and t.name like '%"+parameter.get("name").toString()+"%'";
            	countsb+=" and t.name like '%"+parameter.get("name").toString()+"%'";
            }
            if(parameter.get("phone")!=null &&!parameter.get("phone").toString().equals("")){
            	sql+="and t.phone like '%"+parameter.get("phone").toString()+"%'";
            	countsb+="and t.phone like '%"+parameter.get("phone").toString()+"%'";
            }
            if(parameter.get("idcard")!=null &&!parameter.get("idcard").toString().equals("")){
            	sql+="and t.idcard like '%"+parameter.get("idcard").toString()+"%'";
            	countsb+=" and t.idcard like '%"+parameter.get("idcard").toString()+"%'";
            }
            String order = " order by t.createTime  ";
            return pageQueryByEsayUI(page,rows, countsb.toString(), sql+=order);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, TeachersDAO.class);
        }
	}

	public KpPersonLibrary getTeachersId(String id) throws BkmsException {
		// TODO Auto-generated method stub
		 String sql ="from KpPersonLibrary t where 1=1 " +
					"  and t.memId='"+id+"'";
		 List list = new ArrayList();
		 list=queryHqlList(sql);
		 if(list.size()>0){
			 return (KpPersonLibrary) list.get(0); 
		 }
		return null;
	}

	public boolean saveTeachers(KpPersonLibrary kpPersonLibrary) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(kpPersonLibrary);
		return true;
	}

	public void updateTeachers(KpPersonLibrary kpPersonLibrary) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(kpPersonLibrary);
	}

	public void deleteTeachersIds(String ids) {
		// TODO Auto-generated method stub
		String hql = "delete from KpPersonLibrary ";
		String columnName = "memId";
		String strArray[] = ids.split(","); 
		try {
			deleteByIds(hql, columnName, strArray);
		} catch (BkmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
