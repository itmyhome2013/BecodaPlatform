package com.farm.core.tree.ucc;

import com.farm.core.sql.query.DataQuery;

public interface IEasyUiTreeUCC {
	/**
	 * 查询树
	 * 
	 * @param query
	 * @param parentid
	 *            父ID
	 * @return
	 */
	public DataQuery treeLoad(DataQuery query, String parentid,String nodeState,String isCompany);
	
	/**
	 * 根据线路ID查询车型
	 * @param query
	 * @param id 线路ID
	 * @return
	 */
	public DataQuery treeLoadModel(DataQuery query, String id);
	
	public DataQuery treeLoad2(DataQuery query, String parentid,String nodeState,String isCompany);
	
	
	/**
	 * 根据车队ID查询场站
	 * @param query
	 * @param consId
	 * @return
	 */
	public DataQuery treeLoadStation(DataQuery query, String consId);

	/**
	 * 查询是否有子节点
	 * 
	 * @param parentid
	 * @return
	 */
	public boolean isChildrenNode(String parentid);
}
