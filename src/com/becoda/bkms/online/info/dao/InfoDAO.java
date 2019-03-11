package com.becoda.bkms.online.info.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.online.info.pojo.bo.InfoBo;

public class InfoDAO extends GenericDAO {

	private GenericDAO genericDAO;

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	//批量更新信息状态
    public void updateAllStatus(String[] ids, String col, String value) throws RollbackableException {
        try {
            String idstr = "";
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    if (i == 0) {
                        idstr = "'" + ids[0] + "'";
                    } else {
                        idstr = idstr + ",'" + ids[i] + "'";
                    }
                }
            }
            String sql = "UPDATE online_info  SET " + col + "='" + value + "' where SUBID in(" + idstr + ")";
            getJdbcTemplate().execute(sql);
        } catch (Exception e) {
            throw new RollbackableException("批量更新信息状态", e, InfoBo.class);
        }
    }
}
