package com.becoda.bkms.sys.service;


import java.util.List;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.XxglConstants;
import com.becoda.bkms.east.jhzb.pojo.InfoTtemQueryVO;
import com.becoda.bkms.east.jhzb.pojo.JhzbIndexStateBO;
import com.becoda.bkms.sys.dao.InfoItemDAO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;

/**
 * Created by IntelliJ IDEA. User: kangdw Date: 2015-3-4 Time: 11:50:07 To
 * change this template use File | Settings | File Templates.
 */
public class InfoItemService {
	private InfoItemDAO infoItemDAO;

	public InfoItemDAO getInfoItemDAO() {
		return infoItemDAO;
	}

	public void setInfoItemDAO(InfoItemDAO infoItemDAO) {
		this.infoItemDAO = infoItemDAO;
	}

	/**
	 * 添加信息项，ID是否存在重复
	 * 
	 * @param infoItem
	 *            信息项BO
	 */
	public void createInfoItem(InfoItemBO infoItem)
			throws RollbackableException {
		infoItemDAO.createInfoItem(infoItem);
	}

	/**
	 * 修改信息项，先检测信息项名称是否存在重复
	 * 
	 * @param infoItem
	 *            信息项BO
	 */
	public void updateInfoItem(InfoItemBO infoItem)
			throws RollbackableException {
		infoItemDAO.updateInfoItem(infoItem);
	}

	/**
	 * 删除指定指标集下的所有指标项，不做删除数据表字段的操作，删除数据表时，自动删除 dao.deleteInfoItems
	 */
	public void deleteInfoItems(String setId) throws RollbackableException {
		infoItemDAO.deleteInfoItems(setId);
	}

	/**
	 * 删除信息项 dao.deleteInfoItems
	 * 
	 * @param infoItemIds
	 *            信息项BO数组
	 */
	public void deleteInfoItems(String setId, String[] infoItemIds)
			throws RollbackableException {
		infoItemDAO.deleteInfoItems(setId, infoItemIds);
	}

	/**
	 * 根据SetID查询信息项
	 * 
	 * @param setId
	 *            信息项BO数组
	 * @return cn.ccb.hrdc.sys.pojo.bo.InfoItemBO[]
	 */
	public InfoItemBO[] queryInfoItems(String setId)
			throws RollbackableException {
		return infoItemDAO.queryInfoItems(setId, "", "", "", false);
	}

	/**
	 * 根据setId查询字段名称
	 * 
	 * @param setId
	 * @param itemId
	 * @return
	 * @throws RollbackableException
	 */
	public InfoItemBO findInfoItem(String setId, String itemId)
			throws RollbackableException {
		return infoItemDAO.findInfoItem(setId, itemId);
	}

	/**
	 * 修改信息项状态,将其改为启用或者禁用
	 * 
	 * @param setId
	 *            信息集ID
	 * @param itemId
	 *            信息项ID
	 * @param status
	 *            状态
	 */
	public void makeStatus(String setId, String itemId, String status)
			throws RollbackableException {
		infoItemDAO.setStatus(setId, itemId, status);
	}

	/**
	 * 得到一个新的指标项名称
	 * 
	 * @param setId
	 * @param property
	 * @return
	 * @throws RollbackableException
	 */
	public String getNewItemId(String setId, String property)
			throws RollbackableException {
		return infoItemDAO.getNewItemId(setId, property);
	}

	/**
	 * 查询表信息
	 * 
	 * @param pageVo
	 * @param name
	 * @return
	 * @throws BkmsException
	 */
	public List findSheetInfoByName(PageVO pageVo, String name)
			throws BkmsException {
		StringBuffer querySQL = new StringBuffer();
		StringBuffer countSQL = new StringBuffer();
		querySQL.append("select a.*,b.JHZB_INDEX_STATE,b.JHZB_ID   from  " + name
				+ " a left join EAST_JHZB_INDEXSTATE b on  a.subid=b.jhzb_index_id  ");
		countSQL.append("select count(*) from  " + name
				+ " a left join EAST_JHZB_INDEXSTATE b on  a.subid=b.jhzb_index_id ");
		return infoItemDAO.queryListBySql(querySQL.toString());
	}

	/**
	 * 查询表信息(根据日期)
	 * 
	 * @param pageVo
	 * @param queryVO
	 * @return
	 * @throws BkmsException
	 */
	public List findSheetInfoByNameAndYear(PageVO pageVo,
			InfoTtemQueryVO queryVO) throws BkmsException {
		StringBuffer querySQL = new StringBuffer();
		StringBuffer countSQL = new StringBuffer();
		StringBuffer whereSQL = new StringBuffer(" where 1=1 ");
		List list = null;
		String dateFieldName = null;
		String projectFieldName = null;
		String unitFieldName = null;
		String xhName= null;
		if (queryVO != null) {
			if (queryVO.getSetId() != null && !"".equals(queryVO.getSetId())) {
				xhName= queryVO.getSetId() + "200";
				dateFieldName = queryVO.getSetId() + "201";
				projectFieldName = queryVO.getSetId() + "202";
				unitFieldName = queryVO.getSetId() + "203";
			}
			if (queryVO.getReportDate() != null
					&& !"".equals(queryVO.getReportDate())) {
				whereSQL.append(" and  ").append(dateFieldName).append(" like '")
						.append(queryVO.getReportDate()).append("%'");
			}
			if (queryVO.getProjectArray() != null
					&& !"".equals(queryVO.getProjectArray())) {
				String[] oids = queryVO.getProjectArray();
				for (int i = 0; i < oids.length; i++){
					if (oids[i] != null && !"".equals(oids[i])){
						if(oids.length==1){
							whereSQL.append(" and  ").append(projectFieldName).append(
									" = '").append(oids[i]).append(
									"'");
						}else{
							if(i==0){
								whereSQL.append(" and  (").append(projectFieldName).append(
										" = '").append(oids[i]).append(
										"'");
							}else if(i==oids.length-1){
								whereSQL.append(" or  ").append(projectFieldName).append(
										" = '").append(oids[i]).append(
										"')");
							}else{
								whereSQL.append(" or  ").append(projectFieldName).append(
										" = '").append(oids[i]).append(
										"'");
							}	
						}
						
					}
				}
				
			}
			if (queryVO.getSearch_unit() != null
					&& !"".equals(queryVO.getSearch_unit())) {
				whereSQL.append(" and  ").append(unitFieldName).append(
						" like '%").append(queryVO.getSearch_unit()).append(
						"%'");
			}
			
			if (queryVO.getSearch_project() != null
					&& !"".equals(queryVO.getSearch_project())) {
				whereSQL.append(" and  ").append(projectFieldName).append(
						" like '%").append(queryVO.getSearch_project()).append(
						"%'");
			}
			
			
			whereSQL.append(" order by to_number(").append(xhName).append(")");
			querySQL
					.append("select a.*,b.JHZB_INDEX_STATE,b.JHZB_ID  from  "
							+ queryVO.getSetId()
							+ " a left join EAST_JHZB_INDEXSTATE b on  a.subid=b.jhzb_index_id ");
			countSQL
					.append("select count(*) from "
							+ queryVO.getSetId()
							+ " a left join EAST_JHZB_INDEXSTATE b on a.subid=b.jhzb_index_id  ");
			querySQL.append(whereSQL.toString());
			countSQL.append(whereSQL.toString());
		}
		return infoItemDAO.queryListBySql(querySQL.toString());
	}

	/**
	 * 新增或修改表信息
	 * 
	 * @param dateValue
	 * @param setId
	 * @param infoId
	 * @param field
	 * @param values
	 * @param updateSql
	 * @throws BkmsException
	 */
	public void addOrUpdateInfo(String dateValue, String setId, String infoId,
			String field, String values, String updateSql,User user) throws BkmsException {
		StringBuffer sql = new StringBuffer();
		if (infoId != null) {
			sql.append(
					"update  " + setId + " set " + updateSql + " "
							+ " where SUBID= ").append("'" + infoId + "'");
		} else {
			sql.append("insert into " + setId + " (" + field + ") " + "values("
					+ values + ")");
		}

		if (dateValue != null && !"".equals(dateValue)) {
			List list = SysCacheTool.queryInfoItemBySuperId(setId);
			String fieldName = null;
			if (list != null && list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					InfoItemBO item = (InfoItemBO) list.get(i);
					String dateName = item.getItemName();
					if ("指标日期".equals(dateName)) {
						fieldName = item.getItemId();
						break;
					}
				}
			}
//			StringBuffer sqlForState = new StringBuffer();
//			sqlForState
//					.append("select * from  EAST_JHZB_INDEXSTATE b where  b.jhzb_sheet_name='"
//							+ setId
//							+ "'  and  b.jhzb_index_date = '"
//							+ dateValue + "' ");
//			List<Map<String, String>> stateList = infoItemDAO
//					.queryListBySql(sqlForState.toString());
//			if (stateList == null) {
//				JhzbIndexStateBO indexState = new JhzbIndexStateBO();
//				indexState.setJhzb_id(SequenceGenerator.getUUID());
//				indexState.setJhzb_index_date(dateValue);// 指标日期
//				indexState.setJhzb_sheet_name(setId);// 表名
//				indexState.setJhzb_index_state("2");// 指标完成状态（1：已完成 2：未完成）
//				infoItemDAO.createBo(indexState);
//			}
		}
		infoItemDAO.getJdbcTemplate().execute(sql.toString());
		if (infoId != null) {
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(setId);
			if("J200".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SCJH_ND);
			}else if("J201".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SCJH_YD);
			}else if("J202".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBJH_ND);
			}else if("J203".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBJH_YD);
			}else if("K200".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_NXZB_YD);
			}else if("K201".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZLZB_YD);
			}else if("K202".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_HBZB_YD);
			}else if("K203".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBZB_YD);
			}		
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperRecordId(infoId);
			opebo.setOperType("modify");
			opebo.setOperDesc(user.getName() + "修改了一条计划指标。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}else{
			//操作日志
			OperLogBO opebo = new OperLogBO();
			opebo.setOperatorId(user.getUserId());
			opebo.setOperatorName(user.getName());
			opebo.setOperatorOrg(user.getDeptId());
			opebo.setOperInfosetId(setId);
			if("J200".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SCJH_ND);
			}else if("J201".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SCJH_YD);
			}else if("J202".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBJH_ND);
			}else if("J203".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBJH_YD);
			}else if("K200".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_NXZB_YD);
			}else if("K201".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZLZB_YD);
			}else if("K202".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_HBZB_YD);
			}else if("K203".equals(setId)){
				opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBZB_YD);
			}		
			opebo.setModuleName(XxglConstants.MODULE_NAME);			
			opebo.setOperType("add");
			opebo.setOperDesc(user.getName() + "新增了一条计划指标。");
            HrmsLog.addOperLog(this.getClass(), opebo);
		}
	}

	/**
	 * 删除表信息
	 * 
	 * @param name
	 * @param ids
	 */
	public void deleteInfoByNameAndId(String name, String ids,User user) {
		String[] oids = ids.split(",");
		for (int i = 0; i < oids.length; i++) {
			try {
				StringBuffer sql = new StringBuffer();
				if (oids[i] != null && !"".equals(oids[i]) && name != null
						&& !"".equals(name)) {
					sql.append("delete from  " + name + " where SUBID= '"
							+ oids[i] + "' ");
					String zbId = oids[i];
					infoItemDAO.getJdbcTemplate().execute(sql.toString());
					//操作日志
					OperLogBO opebo = new OperLogBO();
					opebo.setOperatorId(user.getUserId());
					opebo.setOperatorName(user.getName());
					opebo.setOperatorOrg(user.getDeptId());
					opebo.setOperInfosetId(name);
					if("J200".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SCJH_ND);
					}else if("J201".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SCJH_YD);
					}else if("J202".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBJH_ND);
					}else if("J203".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBJH_YD);
					}else if("K200".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_NXZB_YD);
					}else if("K201".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_ZLZB_YD);
					}else if("K202".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_HBZB_YD);
					}else if("K203".equals(name)){
						opebo.setOperInfosetName(XxglConstants.TABLE_NAME_SBZB_YD);
					}		
					opebo.setModuleName(XxglConstants.MODULE_NAME);	
					opebo.setOperRecordId(zbId);
					opebo.setOperType("delete");
					opebo.setOperDesc(user.getName() + "删除了一条计划指标。");
		            HrmsLog.addOperLog(this.getClass(), opebo);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新指标状态表信息
	 * 
	 * @param setId
	 * @param date
	 * @throws RollbackableException
	 */
	public void updateIndexStatus(String ids,String stateIds,String state,String setId)
			throws RollbackableException {
		String[] oids = ids.split(",");
		String[] stateId=stateIds.split(",");
		for (int i = 0; i < oids.length; i++) {
			try {
				if (oids[i] != null && !"".equals(oids[i]) ) {
					JhzbIndexStateBO bo=new JhzbIndexStateBO();
					//判断状态表id是否存在，若存在执行更新操作，若不存在执行新增操作
					if(!"null".equals(stateId[i]) && stateId[i] != null && !"".equals(stateId[i])){
						bo.setJhzb_id(stateId[i]);
						bo.setJhzb_index_id(oids[i]);
						bo.setJhzb_sheet_name(setId);
						bo.setJhzb_index_state(state);//1:已完成 2:未完成
						infoItemDAO.updateBo(stateId[i], bo);
					}else{
						bo.setJhzb_id(SequenceGenerator.getUUID());
						bo.setJhzb_index_id(oids[i]);
						bo.setJhzb_sheet_name(setId);
						bo.setJhzb_index_state(state);//1:已完成 2:未完成
						infoItemDAO.createBo(bo);
					}
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
