package com.becoda.bkms.csu.test.dao;

import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;

public class TestDAO extends GenericDAO {

	public Map querySpecailList(int page , int rows ,ParmsSpecialInfo po )throws RollbackableException {
		try {
            StringBuffer sb = new StringBuffer();
            StringBuffer countsb = new StringBuffer();
            sb.append("from ParmsSpecialInfo t where 1=1 ");
            countsb.append("select count(t) from ParmsSpecialInfo t where 1=1 ");
            if(po.getCode()!=null&&!"".equals(po.getCode())){
            	sb.append(" and code = '").append(po.getCode()).append("'");
            	countsb.append(" and code = '").append(po.getCode()).append("'");
            }
            String order = " order by t.createTime  ";
            return pageQueryByEsayUI(page,rows, countsb.toString(), sb.append(order).toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, TestDAO.class);
        }
	}
}
