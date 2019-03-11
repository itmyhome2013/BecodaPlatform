package com.becoda.bkms.run.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.run.pojo.bo.BulletinScopeBO;
import com.becoda.bkms.util.Tools;

import java.util.List;


public class BulletinScopeDAO extends GenericDAO {
    public BulletinScopeBO[] findById(String id) throws RollbackableException {
        BulletinScopeBO[] BOs = null;
        try {
            String strHQL = " from BulletinScopeBO bo where bo.blltnId='" + id + "'";
            List list = hibernateTemplate.find(strHQL);
            if (list.isEmpty())
                return null;
            BOs = new BulletinScopeBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                BulletinScopeBO bo = new BulletinScopeBO();
                Tools.copyProperties(bo, list.get(i));
                BOs[i] = bo;
            }
        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给manager
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
        return BOs;
    }

    /**
     * 功能：根据OrgId列表查询公告Id列表<br>
     *
     * @param OrgIds 机构树OrgId串，用逗号分割，如'0001','0002','0003'，用于拼sql语句<br>
     * @return 公告Id列表<br>
     */
    public BulletinScopeBO[] findByOrgIds(String OrgIds) throws RollbackableException {
        BulletinScopeBO[] BOs = null;
        try {
            if (OrgIds != null) {
                String strHQL = " from BulletinScopeBO bo where bo.orgId in (" + OrgIds + ")";
                List list = hibernateTemplate.find(strHQL);
                if (list.isEmpty())
                    return null;
                BOs = new BulletinScopeBO[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    BulletinScopeBO bo = new BulletinScopeBO();
                    Tools.copyProperties(bo, list.get(i));
                    BOs[i] = bo;
                }
            }
        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给manager
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
        return BOs;
    }
}
