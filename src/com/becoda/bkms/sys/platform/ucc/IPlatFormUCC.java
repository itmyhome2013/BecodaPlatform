package com.becoda.bkms.sys.platform.ucc;

import java.util.List;
import java.util.Map;
import com.becoda.bkms.common.exception.BkmsException;

public interface IPlatFormUCC {
	
	public List<Map<String, String>> findTitleForIndeHasSort(String index) throws BkmsException;
	
	
	/**
	 * 查询基数库
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> findBaseLibrary() throws BkmsException;

	/**
	 * 从代码集中检出一个option列表
	 * @param gbcode 国际代码
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> findCodeItemBySetId(String gbcode) throws BkmsException;
	
	/**
	 * 根据国际代码 查询其代码项
	 * @param index
	 * @return
	 * @throws BkmsException
	 */
	public Map<String, String> findCodeItemForIndex(String index) throws BkmsException;
}
