package com.becoda.bkms.run.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;

import java.util.List;


public class BulletinContentDAO extends GenericDAO {
    /**
     * @param BlltnId
     * @return List
     */

    public List findByBlltnId(String BlltnId) {
        String strHQL = "from BulletinContentBO bc where bc.blltnId = ? ";
        String[] obj = new String[1];
        obj[0] = BlltnId;
        List list = hibernateTemplate.find(strHQL, obj);
        return list;
    }

    public void deleteBo(Class clazz, String pk) throws RollbackableException {
        try {
            Object bo = hibernateTemplate.get(clazz, pk);
            if (bo != null) {
                hibernateTemplate.delete(bo);
            }
        } catch (Exception e) {
            throw new RollbackableException("删除持久化对象错误", e, this.getClass());
        }
    }
}
