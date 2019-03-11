package com.becoda.bkms.run.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.run.dao.RemDAO;
import com.becoda.bkms.run.pojo.bo.RemBO;
import com.becoda.bkms.run.pojo.bo.RemOrgScopeBO;
import com.becoda.bkms.run.pojo.bo.RemPersonScopeBO;
import com.becoda.bkms.run.util.RunTools;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-13
 * Time: 10:08:18
 * To change this template use File | Settings | File Templates.
 */
public class RemService {
    private RemDAO remdao;

    public RemDAO getRemdao() {
        return remdao;
    }

    public void setRemdao(RemDAO remdao) {
        this.remdao = remdao;
    }

    public RemBO findRemById(String remId) throws BkmsException {
        try {
            return
                    (RemBO) remdao.findBoById(RemBO.class, remId);
        } catch (Exception e) {
            throw new BkmsException("数据查询失败", e, this.getClass());
        }
    }

    public RemBO[] queryAllRem(String orgId) throws RollbackableException {
        try {
            return remdao.queryAllRem(orgId);
        } catch (Exception e) {
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public RemPersonScopeBO[] queryAllRemPersonScope(String remId, String toType) throws RollbackableException {
        try {
            return remdao.queryAllRemPersonScope(remId, toType);
        } catch (Exception e) {
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public RemOrgScopeBO[] queryAllRemOrgScope(String remId) throws RollbackableException {
        try {
            return remdao.queryAllRemOrgScope(remId);
        } catch (Exception e) {
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    public RemOrgScopeBO[] queryAllRemOrgScope() throws RollbackableException {
        return remdao.queryAllRemOrgScope();
    }
//    public RemOrgScopeBO[] queryAllRemOrgScope(String remId) throws RollbackableException {
//        String hq = " from RemOrgScopeBO rb where rb.scopeId is not null and rb.remId='" + remId + "' " ;
//        hq = hq + " order by rb.scopeId desc  ";
//
//        List list = super.find(hq);
//        try{
//                if(list.isEmpty())
//                    return null;
//                RemOrgScopeBO[]  BOs = new RemOrgScopeBO[list.size()];
//                for(int i=0;i<list.size();i++){
//                     RemOrgScopeBO bo = new RemOrgScopeBO();
//                     Tools.copyProperties(bo,list.get(i));
//                     BOs[i] = bo;
//                }
//
//                return BOs;
//
//           }catch(Exception e){
//                //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给manager
//                throw new RollbackableException("查询数据失败", e, this.getClass());
//           }
//   }

    public int queryRemBrithDayByUserId(User user) throws RollbackableException {
        try {
            ActivePageService activePageService = (ActivePageService) BkmsContext.getBean("sys_activePageService");
            String curDay = (Tools.getSysDate("yyyy-MM-dd")).substring(5);
            String sql = "SELECT A001.ID, A001001,A001007,A001011,A001701,A001705 FROM A001 WHERE ( A001730 = '00900'  and A001755 = '00900' ) "
                    + " AND substr(A001011,6) = '" + curDay + "'"
                    + " ORDER BY A001.A001743, A001.A001745";

            //在登录者的查询范围权限中判断有没有过生日的人
            String scale = RunTools.buildQuerySql(user);
            int index_1 = sql.toUpperCase().lastIndexOf("ORDER BY");
            String newsql = sql.substring(0, index_1) + " AND " + scale + " " + sql.substring(index_1);
            return activePageService.queryForInt(newsql);//结果数量

        } catch (Exception e) {
            throw new RollbackableException("数据查询失败", e, this.getClass());
        }
    }

    public void saveRemScope(String remId, String orgIds, List persons, List roles) throws RollbackableException {
        try {
            //机构范围：先删后保存
            RemOrgScopeBO[] bos_orgScope = remdao.queryAllRemOrgScope(remId);
            if (bos_orgScope != null) {
                for (int i = 0; i < bos_orgScope.length; i++) {
                    RemOrgScopeBO bo = new RemOrgScopeBO();
                    Tools.copyProperties(bo, bos_orgScope[i]);
                    remdao.deleteBo(bo);
                }
            }
            if (!"".equals("orgIds")) {
                String[] arrayOrgIds = Tools.getStringArray(orgIds, ",");
                for (int i = 0; i < arrayOrgIds.length; i++) {
                    RemOrgScopeBO bo = new RemOrgScopeBO();
                    bo.setRemId(remId);
                    bo.setOrgId(arrayOrgIds[i]);
                    Org org = SysCacheTool.findOrgById(arrayOrgIds[i]);
                    bo.setOrgTreeId(org.getTreeId());
                    remdao.createBo(bo);
                }
            }
            //人员范围：先删后保存
            RemPersonScopeBO[] bos_personScope = remdao.queryAllRemPersonScope(remId, "01");
            if (bos_personScope != null) {
                for (int i = 0; i < bos_personScope.length; i++) {
                    RemPersonScopeBO bo = new RemPersonScopeBO();
                    Tools.copyProperties(bo, bos_personScope[i]);
                    remdao.deleteBo(bo);
                }
            }
            if (persons != null && persons.size() > 0) {
                for (int i = 0; i < persons.size(); i++) {
                    RemPersonScopeBO bo = new RemPersonScopeBO();
                    bo.setRemId(remId);
                    bo.setToId(((PersonBO) persons.get(i)).getPersonId());
                    bo.setToType("01");
                    remdao.createBo(bo);
                }
            }
            //角色范围：先删后保存
            RemPersonScopeBO[] bos_personScope_role = remdao.queryAllRemPersonScope(remId, "02");
            if (bos_personScope_role != null) {
                for (int i = 0; i < bos_personScope_role.length; i++) {
                    RemPersonScopeBO bo = new RemPersonScopeBO();
                    Tools.copyProperties(bo, bos_personScope_role[i]);
                    remdao.deleteBo(bo);
                }
            }
            if (roles != null && roles.size() > 0) {
                for (int i = 0; i < roles.size(); i++) {
                    RemPersonScopeBO bo = new RemPersonScopeBO();
                    bo.setRemId(remId);
                    bo.setToId(((RoleInfoBO) roles.get(i)).getRoleId());
                    bo.setToType("02");
                    remdao.createBo(bo);
                }
            }
        } catch (Exception e) {
            throw new RollbackableException("保存数据失败", e, this.getClass());
        }
    }

    public void openOrCloseRem(String[] ids, String flag) throws RollbackableException {
        try {
            for (int i = 0; i < ids.length; i++) {
                RemBO bo = (RemBO) remdao.findBoById(RemBO.class, ids[i]);
                bo.setValidFlag(flag);
                remdao.updateBo(bo.getRemId(), bo);
            }

        } catch (Exception e) {
            throw new RollbackableException("新增数据失败", e, this.getClass());
        }
    }

//    public String addRem(RemBO bo, QueryVO vo) throws RollbackableException {
//        Transaction tx = null;
//        try {
//            tx = super.beginTransaction();//事务开启
//            vo.setName(bo.getRemName());
//            vo.setSysFlag("2");
//            QueryManager qm = new QueryManager(s);
//            qm.createQuery(vo);
//            bo.setRemCond(vo.getQryId());
//            String id = remdao.createBo(bo);
//            super.commitTransaction(tx); //事务提交
//            return id;
//        } catch (Exception e) {
//            this.rollbackTransaction(tx); //发生异常，事务回滚
//            throw new RollbackableException("新增数据失败", e, this.getClass());
//        }
//    }

//    public void updateRem(RemBO bo, QueryVO vo) throws RollbackableException {
//        Transaction tx = null;
//        try {
//            tx = super.beginTransaction();//事务开启
//            remdao.updateBo(bo.getRemId(), bo);
//            QueryManager qm = new QueryManager(s);
//            qm.createQuery(vo);
//            super.commitTransaction(tx); //事务提交
//        } catch (Exception e) {
//            this.rollbackTransaction(tx); //发生异常，事务回滚
//            throw new RollbackableException("更新数据失败", e, this.getClass());
//        }
//    }

}
