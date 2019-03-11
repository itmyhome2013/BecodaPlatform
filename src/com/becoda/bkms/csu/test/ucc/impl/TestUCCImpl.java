package com.becoda.bkms.csu.test.ucc.impl;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.test.service.TestService;
import com.becoda.bkms.csu.test.ucc.ITestUCC;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;

public class TestUCCImpl implements ITestUCC {

	private TestService domeSer;

	public TestService getDomeSer() {
		return domeSer;
	}

	public void setDomeSer(TestService domeSer) {
		this.domeSer = domeSer;
	}

	public Map queryList(int page , int rows ,ParmsSpecialInfo po) throws RollbackableException {
		return domeSer.queryDemo(page,rows,po);
	}

	public void delete(String[] ids) {
		domeSer.deleteinfo(ids);
	}

	
	
}
