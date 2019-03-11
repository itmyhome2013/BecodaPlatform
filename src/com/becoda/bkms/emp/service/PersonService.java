package com.becoda.bkms.emp.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.emp.dao.PersonDAO;
import com.becoda.bkms.emp.pojo.bo.*;
//import com.becoda.bkms.org.dao.OrgDAO;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.List;


public class PersonService {
    private PersonDAO personDAO;
//    private OrgDAO orgDAO;

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

//    public OrgDAO getOrgDAO() {
//        return orgDAO;
//    }
//
//    public void setOrgDAO(OrgDAO orgDAO) {
//        this.orgDAO = orgDAO;
//    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    private GenericDAO genericDAO;

    public GenericDAO getGenericDAO() {
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public PersonBO findPerson(String perId) throws RollbackableException {
        return personDAO.findPerson(perId);
    }
    //根据身份证查询并返回人员
    public PersonBO findPersonByIdCard(String idCard) throws RollbackableException {
        try {
            List list;
            if (idCard.length() == 18) {
                String idCard_15 = idCard.substring(0, 6) + idCard.substring(8, 17);//转化为15位
                list = genericDAO.getHibernateTemplate().find(" from PersonBO p  where " +
                        " p.idCard like '" + idCard + "' or p.idCard like '" + idCard_15 + "'");
            } else {
                list = genericDAO.getHibernateTemplate().find(" from PersonBO p  where " +
                        " p.idCard like '" + idCard + "'");
            }
            if (null == list || list.size() == 0)
                return null;
            else{
                return (PersonBO)list.get(0);
            }
        } catch (Exception e) {
            throw new RollbackableException("检索失败错误", e, this.getClass());
        }
    }

    

    /**
     * 根据查询条件查询人员
     *
     * @param sql
     * @return 人员对象数组
     * @throws RollbackableException
     */
    public PersonBO[] queryPerson(String sql) throws RollbackableException {
        return personDAO.queryPerson(sql);
    }

    /**
     * 根据条件查询记录条数判断返回记录数是否大于500条
     * zhouchengjuan添加的
     *
     * @param sql
     * @return
     * @throws RollbackableException
     */

    public int queryResultCount(String sql) throws RollbackableException {
        try {
            return personDAO.queryResultCount(sql);
        } catch (Exception e) {
            throw new RollbackableException(e.getMessage(), e, PersonDAO.class);
        }
    }

    /**
     * 查询某个机构下的人员
     *
     * @param superId
     * @param cancel
     * @return
     * @throws RollbackableException
     */
    public PersonBO[] queryPersonBySuper(String superId, String cancel) throws RollbackableException {
        try {
            return personDAO.queryPersonBySuper(superId, cancel);
        } catch (Exception e) {
            throw new RollbackableException("查询人员失败", e, this.getClass());
        }
    }

    //根据拼音姓名查询
    public List queryPersonByNameSpell(String spell) throws RollbackableException {
        try {
            return personDAO.queryPersonByNameSpell(spell);
        } catch (Exception e) {
            throw new RollbackableException("查询人员失败", e, this.getClass());
        }
    }

    /**
     * 查询岗位下的人员
     *
     * @param postId
     * @param cancel
     * @return
     * @throws RollbackableException
     */
    public PersonBO[] queryPersonByPost(String postId, String cancel) throws RollbackableException {
        try {
            return personDAO.queryPersonByPost(postId, cancel);
        } catch (Exception e) {
            throw new RollbackableException("查询人员失败", e, this.getClass());
        }
    }

    /**
     * 查询合同工数量
     *
     * @param orgId
     * @return
     * @throws RollbackableException
     */
    public int queryContractPerson(String orgId) throws RollbackableException {
        try {
            return personDAO.queryContractPerson(orgId);
        } catch (Exception e) {
            throw new RollbackableException("查询失败！", this.getClass());
        }
    }

    //每天定时计算年龄
    public void computeAge() throws RollbackableException {
        personDAO.computeAge();
    }

    //每天定时计算工龄
    public void computeWorkYears() throws RollbackableException {
        personDAO.computeWorkYears();
    }

    //通过工龄计算工龄区间段
    public void computeWorkYearsSect() throws RollbackableException {
        personDAO.computeWorkYearsSect();
    }


    //每天定时计算岗位的实有人数,超编,缺编等
    public void computerPerInPost() throws RollbackableException {
        personDAO.computerPerInPost();
    }

    /**
     * 根据条件查询人数
     *
     * @param sql
     * @return
     * @throws RollbackableException
     */
    public int queryPersonCount(String sql) throws RollbackableException {
        try {
            return personDAO.queryPersonCount(sql);
        } catch (Exception e) {
            throw new RollbackableException("查询人员失败", e, this.getClass());
        }
    }

    /**
     * @param sql
     * @return
     * @throws RollbackableException
     */
    public PersonBO[] queryAllPerson(String sql) throws RollbackableException {
        try {
            return personDAO.queryAllPerson(sql);
        } catch (Exception e) {
            throw new RollbackableException("查询人员失败", e, this.getClass());
        }
    }

    public List queryMultiPerson(String pid) throws RollbackableException {
        if (pid == null || pid.equals("")) {
            return null;
        }
        String[] tmp = pid.split(",");
        List list = new ArrayList();
        for (int i = 0; i < tmp.length; i++) {
            Person bo = SysCacheTool.findPersonById(tmp[i]);
            if (bo != null) {
                list.add(bo.getPersonId() + "|" + bo.getName());
            }
        }
        return list;
    }

}