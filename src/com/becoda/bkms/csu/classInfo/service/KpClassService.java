package com.becoda.bkms.csu.classInfo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.classInfo.pojo.KpClass;
import com.becoda.bkms.util.Tools;

/**
 * <br>
 */
public class KpClassService {
	private GenericDAO genericDAO;

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public List<KpClass> queryKpClass(String providerId,String projectId,String packageId)
			throws RollbackableException {
		KpClass eBidProductBO = new KpClass();
		List<KpClass> list = genericDAO.getHibernateTemplate().find(
				"from EBidProductBO e where e.projectProviderId='"+ providerId + "' and e.projectId='"+ projectId + "' and e.packageId='"+ packageId + "'");
		return list;
	}

	// 添加
	public void saveKpClass(KpClass kpClass) throws RollbackableException {
		genericDAO.createBo(kpClass);
	}

	// 修改
	public void updateKpClass(KpClass kpClass) throws RollbackableException {
		genericDAO.updateBo(kpClass.getClassId(), kpClass);
		
	}

	// 删除
	public String deletekpClass(KpClass kpClass) throws RollbackableException{
		genericDAO.deleteBo(kpClass);
		return "删除成功！";
    }
	
	public int deletKpClassByIds(String hql,String columnName,String[] ids){
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
	public KpClass getKpClass(String id){
		KpClass kpClass = null;
		try {
			kpClass = (KpClass) genericDAO.findBo(KpClass.class, id);
		} catch (RollbackableException e) {
			e.printStackTrace();
		}
		return kpClass;
	}
	
	 public Map queryKpClassInfoByType(Integer page, Integer rows,KpClass kpClass) throws RollbackableException{
		 StringBuffer hql = new StringBuffer();
		 hql.append(" from KpClass t where 1=1 ");
		 if(kpClass != null ){
			 
		 }
		 String countHql = "select count(t.classId) " + hql.toString();
		 return genericDAO.pageQueryByEsayUI(page,rows, countHql, hql.toString()); 
	 }
	public int disbandKpClassByIds(String[] ids){
		try {
			KpClass kpClass = null;
			for (String id : ids) {
				kpClass = (KpClass) genericDAO.findBo(KpClass.class, id);
				if(kpClass != null){
					kpClass.setAttr1("1"); //班级解散 1 , 0 默认
					genericDAO.updateBo(id, kpClass);
				}else{
					
				}
			}
		} catch (RollbackableException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	 
}
