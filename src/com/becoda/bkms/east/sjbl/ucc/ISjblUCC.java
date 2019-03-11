package com.becoda.bkms.east.sjbl.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.east.sjbl.pojo.SjblBO;
import com.farm.core.sql.query.DataQuery;

/**
 * 
 * <p>Description: 手工补录UCC接口</p>
 * @author zhu_lw
 * @date 2017-11-27
 *
 */
public interface ISjblUCC {
	/**
	 * 查询手工补录信息
	 * @param query
	 * @return
	 */
	public DataQuery queryList(DataQuery query);
	/**
	 * 查询手工补录用于编辑
	 * @param zdjz
	 * @param nyzl
	 * @param tbrq
	 * @return
	 * @throws BkmsException
	 */
	public List querySjblMsgForEdit(String zdjz,String nyzl,String tbrq,String sjbl_id) throws BkmsException;
	/**
	 * 新增或修改手工补录信息
	 * @param bo
	 * @throws RollbackableException
	 */
	public void addOrUpdate(SjblBO bo,User user) throws BkmsException;
	
}
