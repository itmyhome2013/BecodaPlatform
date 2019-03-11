package com.becoda.bkms.csu.classInfo.service;

import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.classInfo.pojo.KpClassSpace;

/**
 * <br>
 */
public class KpClassSpaceService {
	private GenericDAO genericDAO;

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}


	// 添加
	public void saveKpClassSpace(KpClassSpace kpClassSpace) throws RollbackableException {
		genericDAO.createBo(kpClassSpace);
	}

	// 修改
	public void updateKpClassSpace(KpClassSpace kpClassSpace) throws RollbackableException {
		genericDAO.updateBo(kpClassSpace.getSpaceId(), kpClassSpace);
		
	}

	// 删除
	public String deleteKpClassSpace(KpClassSpace kpClassSpace) throws RollbackableException{
		genericDAO.deleteBo(kpClassSpace);
		return "删除成功！";
    }
	
	public int deletKpClassSpaceByIds(String hql,String columnName,String[] ids){
		int flag = 0;
		try {
			flag = genericDAO.deleteByIds(hql, columnName, ids);
		} catch (BkmsException e) {
			e.printStackTrace();
			return 0;
		}
		return flag;
	}
	
	//查询班级实体
	public KpClassSpace getKpClassSpace(String id){
		KpClassSpace kpClassSpace = null;
		try {
			kpClassSpace = (KpClassSpace) genericDAO.findBo(KpClassSpace.class, id);
		} catch (RollbackableException e) {
			e.printStackTrace();
		}
		return kpClassSpace;
	}
	
	 public Map queryKpClassSpaceByType(Integer page, Integer rows,KpClassSpace kpClassSpace) throws RollbackableException{
		 StringBuffer hql = new StringBuffer();
		 hql.append(" from KpClassSpace t where 1=1 ");
		 if(kpClassSpace != null ){
			 
		 }
		 String countHql = "select count(t.classId) " + hql.toString();
		 return genericDAO.pageQueryByEsayUI(page,rows, countHql, hql.toString()); 
	 }
	 
}
