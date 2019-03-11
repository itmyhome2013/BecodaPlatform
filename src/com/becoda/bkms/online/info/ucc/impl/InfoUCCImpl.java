package com.becoda.bkms.online.info.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.online.info.pojo.bo.InfoBo;
import com.becoda.bkms.online.info.pojo.vo.InfoVo;
import com.becoda.bkms.online.info.service.InfoService;
import com.becoda.bkms.online.info.ucc.IInfoUCC;
import com.becoda.bkms.util.Tools;

public class InfoUCCImpl implements IInfoUCC {
	
	private InfoService infoService;
	
	public InfoService getInfoService() {
		return infoService;
	}

	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}

	public InfoBo findInfoById(String id) throws RollbackableException {
		return infoService.getInfoById(id);
	}

	public boolean checkSameName(String name, String id)
			throws RollbackableException {
		return infoService.checkSameName(name, id);
	}

	public void deleteInfomation(String id) throws RollbackableException {
		infoService.deleteInformation(id);
	}

	public void deleteInfomation(String[] id) throws RollbackableException {
		infoService.deleteInformation(id);
	}

	public String saveInfomation(InfoBo bo) throws RollbackableException {
		return infoService.saveInfomation(bo);
	}

	public void updateInfomation(InfoBo bo) throws RollbackableException {
		infoService.updateInfomation(bo);
	}

	public List queryInfoByType(PageVO page,InfoVo info) throws RollbackableException {
		StringBuffer hql = new StringBuffer("from InfoBo i where 1=1 ");
		if (!Tools.stringIsNull(info.getCategory())) {
            hql.append(" and i.category='").append(info.getCategory()).append("'");
        }
		if (!Tools.stringIsNull(info.getType())) {
            hql.append(" and i.type='").append(info.getType()).append("'");
        }
		if (!Tools.stringIsNull(info.getIsPublic())) {
            hql.append(" and i.isPublic='").append(info.getIsPublic()).append("'");
        }
        String countHql = "select count(i.id) " + hql.toString();
        hql.append(" order by i.top desc,i.createTime desc");
        return infoService.getInfoDAO().pageQuery(page, countHql, hql.toString());
	}

	
	
	public void updateAllStatus(String[] ids, String col, String value)
			throws RollbackableException {
		infoService.updateAllStatus(ids, col, value);
	}

	public List queryInfo(String[] ids) throws RollbackableException {
		return infoService.queryInfo(ids);
	}
	

}
