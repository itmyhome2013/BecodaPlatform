package com.becoda.bkms.csu.test.ucc;

import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;


public interface ITestUCC {
	
	public Map queryList(int page , int rows ,ParmsSpecialInfo po)throws RollbackableException; 
	
	public void delete(String ids[]);
}
