package com.becoda.bkms.parkingms.special.ucc;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialFile;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialInfoVo;

public interface ISpecialUCC {

	public void editSpecial(ParmsSpecialInfo vo)throws RollbackableException;
	
	public List querySpecailList(PageVO vo,ParmsSpecialInfoVo sepVo)throws RollbackableException;
	
	public ParmsSpecialInfo getSpecialInfo(String specialId)throws RollbackableException;
	
	public Map queryReceiveOrg(String pid)throws RollbackableException;
	
	public void saveSpecialFile(ParmsSpecialFile po)throws RollbackableException;
	
	public List querySpecailFileList(String specialId)throws RollbackableException;
	
	public ParmsSpecialFile getSpecialFile(String fileId)throws RollbackableException;
	
	public void deleteSpecialFile(ParmsSpecialFile po)throws RollbackableException;
	
	public void deleteSpecial(ParmsSpecialInfo po)throws RollbackableException;
}
