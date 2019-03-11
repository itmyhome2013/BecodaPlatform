package com.farm.core.tree.service;

import com.farm.core.sql.query.DataQuery;
import com.farm.core.tree.dao.EasyUiTreeDAO;

public class EasyUiTreeService {
	
	private EasyUiTreeDAO easyUiTreeDao;
	
	public DataQuery treeLoad(DataQuery query,String parentid,String nodeState,String isCompany){
		return easyUiTreeDao.treeLoad(query,parentid,nodeState,isCompany);
	}
	
	public DataQuery treeLoadModel(DataQuery query,String id){
		return easyUiTreeDao.treeLoadModel(query,id);
	}
	
	public DataQuery treeLoad2(DataQuery query,String parentid,String nodeState,String isCompany){
		return easyUiTreeDao.treeLoad2(query,parentid,nodeState,isCompany);
	}
	
	public DataQuery treeLoadStation(DataQuery query, String consId){
		return easyUiTreeDao.treeLoadStation(query,consId);
	}
	
	public boolean isChildrenNode(String parentid){
		return easyUiTreeDao.isChildrenNode(parentid);
	}
	

	public EasyUiTreeDAO getEasyUiTreeDao() {
		return easyUiTreeDao;
	}

	public void setEasyUiTreeDao(EasyUiTreeDAO easyUiTreeDao) {
		this.easyUiTreeDao = easyUiTreeDao;
	}
	
}
