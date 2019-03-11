package com.becoda.bkms.sys.platform.ucc.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.platform.service.PlatFormService;
import com.becoda.bkms.sys.platform.ucc.IPlatFormUCC;

public class PlatFormUCCImpl implements IPlatFormUCC, Serializable {

	private PlatFormService platFormService;
	
	public List<Map<String,String>> findTitleForIndeHasSort(String index) throws BkmsException {
		return platFormService.findTitleForIndeHasSort(index);
	}
	
	@Override
	public List<Map<String, String>> findBaseLibrary() throws BkmsException {
		return platFormService.findBaseLibrary();
	}
	
	public List<Map<String, String>> findCodeItemBySetId(String gbcode) throws BkmsException {
		return platFormService.findCodeItemBySetId(gbcode);
	}

	public Map<String, String> findCodeItemForIndex(String index) throws BkmsException {
		return platFormService.findCodeItemForIndex(index);
	}
	
	public PlatFormService getPlatFormService() {
		return platFormService;
	}

	public void setPlatFormService(PlatFormService platFormService) {
		this.platFormService = platFormService;
	}


}
