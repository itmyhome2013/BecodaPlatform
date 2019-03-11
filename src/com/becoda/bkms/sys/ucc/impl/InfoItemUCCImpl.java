package com.becoda.bkms.sys.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.east.jhzb.pojo.InfoTtemQueryVO;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.pms.pojo.bo.RoleDataBO;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.service.RoleDataService;
import com.becoda.bkms.pms.service.UserManageService;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.service.InfoItemService;
import com.becoda.bkms.sys.ucc.IInfoItemUCC;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: kangdw Date: 2015-3-4 Time: 11:52:33 To
 * change this template use File | Settings | File Templates.
 */
public class InfoItemUCCImpl implements IInfoItemUCC {
	private InfoItemService infoItemService;
	// private WageAPI wageAPI;
	private UserManageService userManageService;
	private RoleDataService roleDataService;

	public InfoItemService getInfoItemService() {
		return infoItemService;
	}

	public void setInfoItemService(InfoItemService infoItemService) {
		this.infoItemService = infoItemService;
	}

	// public WageAPI getWageAPI() {
	// return wageAPI;
	// }
	//
	// public void setWageAPI(WageAPI wageAPI) {
	// this.wageAPI = wageAPI;
	// }

	public UserManageService getUserManageService() {
		return userManageService;
	}

	public void setUserManageService(UserManageService userManageService) {
		this.userManageService = userManageService;
	}

	public RoleDataService getRoleDataService() {
		return roleDataService;
	}

	public void setRoleDataService(RoleDataService roleDataService) {
		this.roleDataService = roleDataService;
	}

	/**
	 * @param infoItem
	 */
	public void createInfoItem(InfoItemBO infoItem, String userId)
			throws BkmsException {
		try {
			infoItemService.createInfoItem(infoItem);
			// 与新姿同步
			String len = infoItem.getItemDataLength();
			if (len == null || len.equals(""))
				len = "100";
			// wageAPI.createOrDelWageInfoItem(infoItem.getSetId(),
			// infoItem.getItemId(), len, "add");

			// 同步权限
			String isManage = userManageService.isSysManager(userId);
			if ("1".equals(isManage)) // 如果是系统管理员,则返回
				return;
			String roleId;
			List lst = userManageService.queryUserRole(userId);
			if (lst != null && lst.size() > 0) {
				RoleInfoBO rbo = (RoleInfoBO) lst.get(0);
				roleId = rbo.getRoleId(); // 管理员,都是一个角色
				RoleDataBO rdbo = new RoleDataBO();
				rdbo.setDataId(infoItem.getItemId());

				rdbo.setDataType(PmsConstants.INFO_ITEM_TYPE);
				rdbo.setPmsType(String.valueOf(PmsConstants.PERMISSION_WRITE));
				rdbo.setRoleId(roleId);
				roleDataService.createRoleData(rdbo);
			}

		} catch (Exception e) {
			throw new RollbackableException(e, getClass());
		}
	}

	/**
	 * @param infoItem
	 */
	public void updateInfoItem(InfoItemBO infoItem) throws BkmsException {
		try {
			infoItemService.updateInfoItem(infoItem);
		} catch (RollbackableException e) {
			throw e;
		} catch (Exception e) {
			throw new RollbackableException(e, getClass());
		}
	}

	/**
	 * @param itemIds
	 */
	public void deleteInfoItems(String setId, String[] itemIds, String userId)
			throws BkmsException {
		try {
			if (!"dev".equals(StaticVariable.get(Constants.RUN_MODE))) {
				String str = this.checkAllowDelete(itemIds);
				if (str != null && !"".equals(str)) {
					throw new RollbackableException(str, this.getClass());
				}
			}
			infoItemService.deleteInfoItems(setId, itemIds);

			// 与新姿同步
			// for (int i = 0; i < itemIds.length; i++) {
			// wageAPI.createOrDelWageInfoItem(setId, itemIds[i], "0", "del");
			// }
			// 同步权限
			String isManage = userManageService.isSysManager(userId);
			if ("1".equals(isManage)) // 如果是系统管理员,则返回
				return;
			String roleId;
			List lst = userManageService.queryUserRole(userId);
			if (lst != null && lst.size() > 0) {
				RoleInfoBO rbo = (RoleInfoBO) lst.get(0);
				roleId = rbo.getRoleId(); // 管理员,都是一个角色
				roleDataService.deleteRoleDataByDataId(roleId, itemIds);
			}
		} catch (RollbackableException e) {
			throw e;
		} catch (Exception e) {
			throw new RollbackableException(e, getClass());
		}
	}

	/**
	 * @param infoItemId
	 * @return cn.ccb.hrdc.sys.pojo.bo.InfoItemBO
	 */
	public InfoItemBO findInfoItem(String setId, String infoItemId)
			throws BkmsException {
		return infoItemService.findInfoItem(setId, infoItemId);
	}

	public List queryRightItemlist(User user, String setId)
			throws BkmsException {
		try {
			InfoItemBO[] items = infoItemService.queryInfoItems(setId);

			if (items == null)
				return null;
			ArrayList list = new ArrayList();
			ArrayList right = new ArrayList();
			int len = items.length;
			PmsAPI api = new PmsAPI();
			int rg = api.checkInfoSet(user, setId);
			for (int i = 0; i < len; i++) {
				String checkReadOnly = String.valueOf(rg);
				list.add(items[i]);
				right.add(checkReadOnly);
			}
			list.add(0, right);
			return list;
		} catch (RollbackableException e) {
			throw e;
		} catch (Exception e) {
			throw new RollbackableException(e, this.getClass());
		}

	}

	/**
	 * @param itemIds
	 * @param status
	 */
	public void makeStatus(String setId, String[] itemIds, String status)
			throws BkmsException {
		try {
			for (int i = 0; i < itemIds.length; i++) {
				infoItemService.makeStatus(setId, itemIds[i], status);
			}
		} catch (RollbackableException e) {
			throw e;
		} catch (Exception e) {
			throw new RollbackableException(e, getClass());
		}
	}

	/**
	 * @param infoItemID
	 */
	public void issueInfoItem(String infoItemID) {

	}

	/**
	 * 得到一个新的指标项名称
	 * 
	 * @param setId
	 *            大类
	 * @return 指标集名称
	 */
	public String getNewItemId(String setId, String property)
			throws BkmsException {
		return infoItemService.getNewItemId(setId, property);
	}

	/**
	 * 检测指标集是否允许被删除
	 * 
	 * @param itemIds
	 *            指标项数组
	 * @return "" 表示没有,!="" 表示被占用,返回值返回被占用的模块
	 */
	public String checkAllowDelete(String[] itemIds) throws BkmsException {
		String rtnValue = "";
		// 检测新姿模块
		// for (int i = 0; i < itemIds.length; i++) {
		// if (wageAPI.checkUsingWageInfoSet(itemIds[i])) {
		// InfoItemBO item = SysCacheTool.findInfoItem(itemIds[i].substring(0,
		// 4), itemIds[i]);
		// if (item != null) {
		// if ("".equals(rtnValue))
		// rtnValue = "{" + item.getItemName() + "}";
		// else
		// rtnValue += ",{" + item.getItemName() + "}";
		// }
		// }
		// }
		// if (!"".equals(rtnValue)) {
		// rtnValue = "指标项" + rtnValue + "已经被薪资模块使用.";
		// }

		// 检测国标范围
		for (int i = 0; i < itemIds.length; i++) {
			String id = itemIds[i].substring(4, 7);
			if (Tools.checkInGBScale(Integer.parseInt(id))) {
				InfoItemBO item = SysCacheTool.findInfoItem(itemIds[i]
						.substring(0, 4), itemIds[i]);
				if ("".equals(rtnValue))
					rtnValue = "{" + item.getItemName() + "}";
				else
					rtnValue += ",{" + item.getItemName() + "}";
			}
		}
		if (!"".equals(rtnValue)) {
			rtnValue = "指标项" + rtnValue + "属于国标范围.";
		}

		// 检测国标范围
		for (int i = 0; i < itemIds.length; i++) {
			String id = itemIds[i].substring(4, 7);
			if (Tools.checkInProgramScale(Integer.parseInt(id))) {
				InfoItemBO item = SysCacheTool.findInfoItem(itemIds[i]
						.substring(0, 4), itemIds[i]);
				if ("".equals(rtnValue))
					rtnValue = "{" + item.getItemName() + "}";
				else
					rtnValue += ",{" + item.getItemName() + "}";
			}
		}
		if (!"".equals(rtnValue)) {
			rtnValue = "指标项" + rtnValue + "属于系统使用指标集.";
		}
		return rtnValue;
	}

	@Override
	public List findSheetInfoByName(PageVO pageVo, String name)
			throws BkmsException {
		// TODO Auto-generated method stub
		return infoItemService.findSheetInfoByName(pageVo, name);
	}

	@Override
	public void addOrUpdateInfo(String dateValue, String setId, String infoId,
			String field, String values, String updateSql,User user) throws BkmsException {
		// TODO Auto-generated method stub
		infoItemService.addOrUpdateInfo(dateValue, setId, infoId, field,
				values, updateSql,user);
	}

	@Override
	public void deleteInfoByNameAndId(String name, String ids,User user)
			throws BkmsException {
		// TODO Auto-generated method stub
		infoItemService.deleteInfoByNameAndId(name, ids,user);
	}

	@Override
	public List findSheetInfoByNameAndYear(PageVO pageVo,
			InfoTtemQueryVO queryVO) throws BkmsException {
		// TODO Auto-generated method stub
		return infoItemService.findSheetInfoByNameAndYear(pageVo, queryVO);
	}

	@Override
	public void updateIndexStatus(String ids,String stateIds,String state,String setId)
			throws BkmsException {
		// TODO Auto-generated method stub
		infoItemService.updateIndexStatus(ids,stateIds,state,setId);
	}

}
