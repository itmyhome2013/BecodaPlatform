package com.becoda.bkms.parkingms.special.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialFile;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialInfoVo;
import com.becoda.bkms.parkingms.special.service.SpecialService;
import com.becoda.bkms.parkingms.special.ucc.ISpecialUCC;

public class SpecialUCCImpl implements ISpecialUCC {

	private SpecialService specService;

	public SpecialService getSpecService() {
		return specService;
	}

	public void setSpecService(SpecialService specService) {
		this.specService = specService;
	}

	public void editSpecial(ParmsSpecialInfo vo)throws RollbackableException {
		specService.editSpecial(vo);
	}

	public List querySpecailList(PageVO vo, ParmsSpecialInfoVo sepVo)throws RollbackableException {
		return specService.querySpecailList(vo, sepVo);
	}
	
	public ParmsSpecialInfo getSpecialInfo(String specialId)throws RollbackableException{
		return specService.getSpecialInfo(specialId);
	}

	public Map queryReceiveOrg(String pid) throws RollbackableException {
		return specService.queryReceiveOrg(pid);
	}

	public void saveSpecialFile(ParmsSpecialFile po)
			throws RollbackableException {
		specService.saveSpecialFile(po);
	}

	public List querySpecailFileList(String specialId)
			throws RollbackableException {
		return specService.querySpecailFileList(specialId);
	}

	public ParmsSpecialFile getSpecialFile(String fileId)
			throws RollbackableException {
		return specService.getSpecialFile(fileId);
	}

	public void deleteSpecialFile(ParmsSpecialFile po)
			throws RollbackableException {
		specService.deleteSpecialFile(po);
	}

	public void deleteSpecial(ParmsSpecialInfo po) throws RollbackableException {
		specService.deleteSpecial(po);
	}
	
}
