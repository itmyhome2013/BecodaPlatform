package com.becoda.bkms.run.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.run.pojo.bo.RemBO;
import com.becoda.bkms.run.pojo.bo.RemOrgScopeBO;
import com.becoda.bkms.run.pojo.bo.RemPersonScopeBO;
import com.becoda.bkms.util.Tools;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-13
 * Time: 9:57:55
 * To change this template use File | Settings | File Templates.
 */
public class RemDAO extends GenericDAO {

    public RemBO[] queryAllRem(String orgId) throws RollbackableException {
        String hq = " from RemBO rb where rb.remId is not null and rb.createOrg='" + orgId + "' ";
        hq = hq + " order by rb.createDate desc  ";

        List list = hibernateTemplate.find(hq);
        try {
            if (list.isEmpty())
                return null;
            RemBO[] BOs = new RemBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                RemBO bo = new RemBO();
                Tools.copyProperties(bo, list.get(i));
                BOs[i] = bo;
            }

            return BOs;

        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给service
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public RemPersonScopeBO[] queryAllRemPersonScope(String remId, String toType) throws RollbackableException {
        String hq = " from RemPersonScopeBO rb where rb.scopeId is not null and rb.remId='" + remId + "' ";
        if (toType != null && !toType.equals("")) {
            hq = hq + " and rb.toType = '" + toType + "'";
        }
        hq = hq + " order by rb.scopeId desc  ";

        List list = hibernateTemplate.find(hq);
        try {
            if (list.isEmpty())
                return null;
            RemPersonScopeBO[] BOs = new RemPersonScopeBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                BOs[i] = (RemPersonScopeBO) list.get(i);
            }

            return BOs;

        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给service
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public RemOrgScopeBO[] queryAllRemOrgScope(String remId) throws RollbackableException {
        String hq = " from RemOrgScopeBO rb where rb.scopeId is not null and rb.remId='" + remId + "' ";
        hq = hq + " order by rb.scopeId desc  ";

        List list = hibernateTemplate.find(hq);
        try {
            if (list.isEmpty())
                return null;
            RemOrgScopeBO[] BOs = new RemOrgScopeBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                BOs[i] = (RemOrgScopeBO) list.get(i);
            }

            return BOs;

        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给service
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public RemPersonScopeBO[] queryAllRemPersonScope(String id) throws RollbackableException {
        String hq = " from RemPersonScopeBO  rps, RemBO rb where rps.remId = rb.remId and rb.validFlag='00901' and rps.toId='" + id + "' ";

        hq = hq + " order by rps.scopeId desc  ";

        List list = hibernateTemplate.find(hq);
        try {
            if (list.isEmpty())
                return null;
            RemPersonScopeBO[] BOs = new RemPersonScopeBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                RemPersonScopeBO bo = new RemPersonScopeBO();
                Tools.copyProperties(bo, obj[0]);
                BOs[i] = bo;
            }

            return BOs;

        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给service
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public RemOrgScopeBO[] queryAllRemOrgScope() throws RollbackableException {
        String hq = " from RemOrgScopeBO ros, RemBO rb where ros.remId = rb.remId and rb.validFlag='00901' ";
        hq = hq + " order by ros.scopeId desc  ";

        List list = hibernateTemplate.find(hq);
        try {
            if (list.isEmpty())
                return null;
            RemOrgScopeBO[] BOs = new RemOrgScopeBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                RemOrgScopeBO bo = new RemOrgScopeBO();
                Tools.copyProperties(bo, obj[0]);
                BOs[i] = bo;
            }

            return BOs;

        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给service
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public void deleteBo(Class clazz, String pk) throws RollbackableException {
        try {
            Object ob = super.findBo(clazz, pk);
            if (ob != null)
                super.deleteBo(ob);
        } catch (Exception e) {
            throw new RollbackableException("删除失败", e, this.getClass());
        }
    }
}
