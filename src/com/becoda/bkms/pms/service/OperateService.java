package com.becoda.bkms.pms.service;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.dao.OperateDAO;
import com.becoda.bkms.pms.pojo.bo.OperateBO;
import com.becoda.bkms.util.SequenceGenerator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-5
 * Time: 13:51:39
 * To change this template use File | Settings | File Templates.
 */
public class OperateService {
    private OperateDAO operateDao;

    public OperateDAO getOperateDao() {
        return operateDao;
    }

    public void setOperateDao(OperateDAO operateDao) {
        this.operateDao = operateDao;
    }

    /**
     * 得到系统所有菜单
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryAllOperates() throws RollbackableException {
        try {
            return operateDao.queryAllOperates();
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 得到系统所有模块
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryAllModule() throws RollbackableException {
        try {
            return operateDao.queryAllModule();
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 删除菜单
     *
     * @param operateId
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public void deleteOpeById(String operateId) throws RollbackableException {
        try {
            OperateBO bo = (OperateBO) operateDao.findBo(OperateBO.class, operateId);
            operateDao.deleteBo(bo);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }
    
    public void moduleOpen(String operateId) throws RollbackableException{
    	try {
            OperateBO bo = (OperateBO) operateDao.findBo(OperateBO.class, operateId);
            bo.setModuleStatus("0");
            String sql="update PMS_OPERATE set MODULE_STATUS='0' where OPERATE_ID='"+operateId+"'";
            operateDao.getJdbcTemplate().update(sql);
            //operateDao.getHibernateTemplate().update(bo);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }
    
    public void moduleClose(String operateId) throws RollbackableException{
    	try {
            OperateBO bo = (OperateBO) operateDao.findBo(OperateBO.class, operateId);
            bo.setModuleStatus("1");
            String sql="update PMS_OPERATE set MODULE_STATUS=1 where OPERATE_ID='"+operateId+"'";
            operateDao.getJdbcTemplate().update(sql);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 新建菜单
     *
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public void saveOperate(OperateBO operate) throws RollbackableException {
        try {
            String treeId = SequenceGenerator.getTreeId("PMS_OPERATE", "TREE_ID", null, 4, 1);
            operate.setTreeId(treeId);
            operateDao.getHibernateTemplate().save(operate);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 新建菜单
     *
     * @param flag :是否是根目录，true为根目录
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public void saveOperate(OperateBO operate, boolean flag) throws RollbackableException {
        try {
            if (flag) {
                String treeId = SequenceGenerator.getKeyId("PMS_TREEID");
                operate.setTreeId(treeId);
                String operateId = SequenceGenerator.getKeyId("PMS_OPERATE");
                operate.setSuperId("-1");
                operate.setOperateId(operateId);
            } else {
                OperateBO superBO = this.getOperateById(operate.getSuperId());
                String treeId = SequenceGenerator.getTreeId("PMS_OPERATE", "TREE_ID", superBO.getTreeId(), 4, 1);
                operate.setTreeId(treeId);
                String operateId = SequenceGenerator.getKeyId("PMS_OPERATE");
                operate.setOperateId(operateId);
            }
            //operateDao.getHibernateTemplate().saveOrUpdate(operate);
            operateDao.getHibernateTemplate().save(operate);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 修改菜单
     *
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public void updateOperate(OperateBO operate) throws RollbackableException {
        try {
            String superId = operate.getSuperId();
            String operateId = operate.getOperateId();
            OperateBO bo = (OperateBO) operateDao.findBo(OperateBO.class, operateId);
            if (!superId.equals(bo.getSuperId())) {
                operate.setTreeId(SequenceGenerator.getTreeId("PMS_OPERATE", "TREE_ID", superId, 4, 1));
            }
            operateDao.getHibernateTemplate().update(operate);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    public OperateBO updateOperate(String operateId, String opeName, String opeModule, String opeUrl, String opeSuperId, String sysflag, String treeId, String type) throws RollbackableException {
        try {
            OperateBO bo = (OperateBO) operateDao.findBo(OperateBO.class, operateId);
            if (!opeSuperId.equals(bo.getSuperId())) {
                OperateBO superBO = (OperateBO) operateDao.findBoById(OperateBO.class, opeSuperId);
                if (superBO == null) {
                    throw new Exception("superId不存在");
                }
                String superTreeId = superBO.getTreeId();
                bo.setTreeId(SequenceGenerator.getTreeId("PMS_OPERATE", "TREE_ID", superTreeId, 4, 1));
            }
            bo.setOperateName(opeName);
            bo.setModuleID(opeModule);
            bo.setUrl(opeUrl);
            bo.setSuperId(opeSuperId);
            bo.setSysFlag(sysflag);
            bo.setOperateType(type);
            this.updateOperate(bo);
            return bo;
        } catch (Exception e) {
            throw new RollbackableException("更新单个记录失败", e, this.getClass());
        }
    }
//    public static void main(String args[]){
//        String s1="12340001";
//        String s2="1234";
//        String s3="1414";
//        System.out.println(judgement(s1,s2));
//        judgement(s1,s3);
//    }

    public boolean judgement(String treeId, String superId) {
        String tree = treeId.substring(0, 4);
        if (superId.equals(tree)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新一个无状态的POJO
     *
     * @param bo bo
     * @param pk pk
     * @throws RollbackableException e
     */
    public void updateBo(Object bo, String pk) throws RollbackableException {
        try {
            operateDao.updateBo(pk, bo);
        } catch (Exception e) {
            throw new RollbackableException("更新单个记录失败", e, this.getClass());
        }
    }

    /**
     * 获得菜单
     *
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public void queryOperate(String str) throws RollbackableException {
        try {
            //operateDao.getHibernateTemplate().findByNamedParam("from OperateBO.o where u.")
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 取得某一个模块
     *
     * @param operateId
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public OperateBO getOperateById(String operateId) throws RollbackableException {
        try {
            //operateDao.getHibernateTemplate().findByNamedParam("from OperateBO.o where u.")
            return (OperateBO) operateDao.findBo(OperateBO.class, operateId);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 取得某一个模块
     *
     * @param name
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public OperateBO getOperateByName(String name) throws RollbackableException {
        try {
            return operateDao.getOperateByName(name);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 取得某一个模块
     *
     * @param treeId
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public OperateBO getOperateByTreeId(String treeId) throws RollbackableException {
        try {
            OperateBO bo = null;
            List list = operateDao.getHibernateTemplate().find("from OperateBO as o where o.treeId='" + treeId + "'");
            if (list.size() > 0) {
                bo = (OperateBO) list.get(0);
            }
            return bo;
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }

    /**
     * 通过treeId获得模块下的所有菜单
     *
     * @return List<OperatBO>
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryMenusByTreeId(String treeId) throws RollbackableException {
        try {
            //operateDao.getHibernateTemplate().findByNamedParam("from OperateBO.o where u.")
            return operateDao.queryMenusByTreeId(treeId);
        } catch (Exception e) {
            throw new RollbackableException(e, OperateBO.class);
        }
    }
}
