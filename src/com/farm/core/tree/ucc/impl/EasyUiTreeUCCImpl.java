package com.farm.core.tree.ucc.impl;

import com.farm.core.sql.query.DataQuery;
import com.farm.core.tree.service.EasyUiTreeService;
import com.farm.core.tree.ucc.IEasyUiTreeUCC;

public class EasyUiTreeUCCImpl implements IEasyUiTreeUCC {

	private EasyUiTreeService easyUiTreeService;
	
	@Override
	public boolean isChildrenNode(String parentid) {
		return easyUiTreeService.isChildrenNode(parentid);
	}

	@Override
	public DataQuery treeLoad(DataQuery query, String parentid,String nodeState,String isCompany) {
		return easyUiTreeService.treeLoad(query,parentid,nodeState,isCompany);
	}
	
	@Override
	public DataQuery treeLoadModel(DataQuery query, String id){
		return easyUiTreeService.treeLoadModel(query,id);
	}
	
	@Override
	public DataQuery treeLoad2(DataQuery query, String parentid,String nodeState,String isCompany) {
		return easyUiTreeService.treeLoad2(query,parentid,nodeState,isCompany);
	}
	
	@Override
	public DataQuery treeLoadStation(DataQuery query, String consId){
		return easyUiTreeService.treeLoadStation(query,consId);
	}

	public EasyUiTreeService getEasyUiTreeService() {
		return easyUiTreeService;
	}

	public void setEasyUiTreeService(EasyUiTreeService easyUiTreeService) {
		this.easyUiTreeService = easyUiTreeService;
	}
	

}
