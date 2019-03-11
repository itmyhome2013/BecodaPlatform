package com.becoda.bkms.emp.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.emp.dao.PersonCodeDAO;
import com.becoda.bkms.emp.pojo.bo.PersonCodeBO;
import com.becoda.bkms.org.dao.OrgDAO;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.util.SequenceGenerator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-16
 * Time: 16:17:47
 * To change this template use File | Settings | File Templates.
 */

public class PersonCodeService {
    private PersonCodeDAO personCodeDAO;
    private OrgDAO orgDAO;

    public PersonCodeDAO getPersonCodeDAO() {
        return personCodeDAO;
    }

    public void setPersonCodeDAO(PersonCodeDAO personCodeDAO) {
        this.personCodeDAO = personCodeDAO;
    }

    public OrgDAO getOrgDAO() {
        return orgDAO;
    }

    public void setOrgDAO(OrgDAO orgDAO) {
        this.orgDAO = orgDAO;
    }

    public void addPersonCode(PersonCodeBO m) throws RollbackableException {
        try {
            m.setId(SequenceGenerator.getKeyId("person_code"));
        } catch (BkmsException e) {
            throw new RollbackableException("添加用户代码失败", e, this.getClass());
        }
        personCodeDAO.createBo(m);
    }

    public void modifyPersonCode(PersonCodeBO m) throws RollbackableException {
        personCodeDAO.modifyPersonCode(m);
    }

    public void delPersonCode(String id) throws RollbackableException {
        personCodeDAO.delPersonCode(id);
    }

    public PersonCodeBO findPersonCode(String id) throws RollbackableException {
        return personCodeDAO.findPersonCode(id);
    }

    public PersonCodeBO[] queryPersonCode() throws RollbackableException {
        return personCodeDAO.queryPersonCode();
    }

    public PersonCodeBO[] queryPersonCode(String org) throws RollbackableException {
        return personCodeDAO.queryPersonCode(org);
    }

//    public PersonCodeBO[] queryPersonCode(String org, String leader) throws RollbackableException {
//        try {
//            return personCodeDAO.queryPersonCode(org, leader);
//        } catch (DAOException e) {
//            throw new RollbackableException("查询失败", e, this.getClass());
//        }
//    }

    public String queryMaxEndCode(String org, String leader) throws RollbackableException {
        return personCodeDAO.queryMaxEndCode(org, leader);
    }


    /**
     * @param orgId
     * @param orgId kind
     * @return 如果员工编号区段已经用完，没有员工编号区段 抛出RollbackableException
     * @throws RollbackableException
     */
    public String generateNewPerCode(String orgId, String kind) throws RollbackableException {
        String newPerCode = null;
        try {
            OrgBO unit = orgDAO.findOrg(orgId); //获得单位
            String treeid = unit.getTreeId();
            if (treeid.length() >= 6) {
                treeid = treeid.substring(0, 6);
            }
            OrgBO obo = orgDAO.findOrgByTreeId(treeid);
            String oboOrgId = obo.getOrgId();
            newPerCode = personCodeDAO.queryMinEndCode(oboOrgId, kind);
            if (newPerCode == null || "".equals(newPerCode)) {
                throw new RollbackableException("单位" + unit.getName() + "员工编号区段已经用完", this.getClass());
            }
        } catch (RollbackableException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RollbackableException("生成编号失败", e, this.getClass());
        }
        return newPerCode;
    }

    //检测员工编号是否已用
    public List checkPerCodeUsed(String perCode) throws RollbackableException {
        return personCodeDAO.checkPerCodeUsed(perCode);
    }
    //根据员工号 , 找出所有匹配的员工号
    public List queryPersonCodeByCode(String code)throws BkmsException{

        return personCodeDAO.queryPersonCodeByCode(code) ;
    }
}