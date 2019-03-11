package com.becoda.bkms.emp.dao;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.emp.pojo.bo.PersonCodeBO;
import com.becoda.bkms.util.Arith;
import com.becoda.bkms.util.Tools;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-16
 * Time: 16:11:53
 * To change this template use File | Settings | File Templates.
 */
public class PersonCodeDAO extends GenericDAO {

    public PersonCodeBO findPersonCode(String id) throws RollbackableException {
        try {
            List list;
            list = hibernateTemplate.find("from PersonCodeBO p where p.id = '" + id + "'");
            if (null == list || list.size() == 0)
                return null;
            else
                return (PersonCodeBO) list.get(0);

        } catch (HibernateException e) {
            throw new RollbackableException("检索失败" + id + "错误", e, this.getClass());
        }

    }

    public void addPersonCode(PersonCodeBO po) throws RollbackableException {
        this.createBo(po);
    }

    public void modifyPersonCode(PersonCodeBO po) throws RollbackableException {
        try {
            if (null != po.getId()) {
                PersonCodeBO p = (PersonCodeBO) hibernateTemplate.load(PersonCodeBO.class, po.getId());
                Tools.copyProperties(p, po);
            }
        } catch (Exception e) {
            throw new RollbackableException("修改失败.", e, this.getClass());
        }
    }

    public void delPersonCode(String id) throws RollbackableException {
        try {
            String sql = "delete from person_code where id='" + id + "'";
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new RollbackableException("删除失败.", e, this.getClass());
        }
    }

    public PersonCodeBO[] queryPersonCode() throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from PersonCodeBO m order by m.startCode asc");
            if (null == list || list.size() == 0)
                return null;
            else {
                PersonCodeBO[] m = new PersonCodeBO[list.size()];
                for (int i = 0; i < m.length; i++) {
                    m[i] = (PersonCodeBO) list.get(i);
                }
                return m;
            }

        } catch (HibernateException e) {
            throw new RollbackableException("检索失败", e, this.getClass());
        }
    }

    public PersonCodeBO[] queryPersonCode(String org) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from PersonCodeBO m where m.orgId = '" + org + "'");
            if (null == list || list.size() == 0)
                return null;
            else {
                PersonCodeBO[] m = new PersonCodeBO[list.size()];
                for (int i = 0; i < m.length; i++) {
                    m[i] = (PersonCodeBO) list.get(i);
                }
                return m;
            }

        } catch (HibernateException e) {
            throw new RollbackableException("检索失败", e, this.getClass());
        }
    }

    public String queryMaxEndCode(String org, String leader) throws RollbackableException {

        String maxCode = "";
        try {
            List list = hibernateTemplate.find("select max(m.endCode+0) from PersonCodeBO m where m.orgId = '" + org + "' and leader = '" + leader + "'");
            if (null == list || list.size() == 0)
                return null;
            else {
                maxCode = (String) list.get(0);
            }


        } catch (HibernateException e) {
            throw new RollbackableException("检索失败", e, this.getClass());
        }

        return maxCode;
    }

    /*
    选择最小的能用的
    */
    public String queryMinEndCode(String orgid, String kind) throws RollbackableException {

        String maxCode = "";
        String maxCode2 = "";
        try {
            List list = hibernateTemplate.find("from PersonCodeBO m where m.orgId = '" + orgid + "' and (m.max+0)<(m.endCode+0) and  m.type='" + kind + "' order by (m.endCode+0) asc");
            if (null == list || list.size() == 0)
                return null;
            else {

                PersonCodeBO code = (PersonCodeBO) list.get(0);
                if ("0".equals(code.getMax())) {
                    maxCode2 = code.getStartCode();
                } else {
                    // maxCode2 = String.valueOf(Integer.parseInt(code.getMax()) + 1);
                    maxCode2 = String.valueOf(Integer.parseInt(code.getMax()));
                }
                maxCode = maxCode2;
                boolean used = true;  //已经被占用
                while (used) {
                    maxCode = Arith.add(maxCode, "1");
                    if (Arith.compare(maxCode, code.getEndCode()) > 0) {
                        throw new RollbackableException("工号被用完", this.getClass());
                    }
                    while (maxCode.length() < 5) {
                        maxCode = "0" + maxCode;
                    }
                    Person per = SysCacheTool.findPersonByCode(maxCode);
                    if (per == null) {
                        used = false;
                    }
                }
                code.setMax(maxCode);
                hibernateTemplate.update(code);
            }
        } catch (Exception e) {
            throw new RollbackableException("检索失败", e, this.getClass());
        }
        return maxCode;
    }

    public PersonCodeBO querybyMax(String max) throws RollbackableException {

        PersonCodeBO pbo = null;
        try {
            List list = hibernateTemplate.find("from PersonCodeBO p where p.max='" + max + "'");
            if (null == list || list.size() == 0)
                return null;
            else {
                pbo = (PersonCodeBO) list.get(0);
            }


        } catch (HibernateException e) {
            throw new RollbackableException("检索失败", e, this.getClass());
        }

        return pbo;
    }

    public String queryMaxEndCodePerson(String org, String leader) throws RollbackableException {
        try {
            String sql = "select max(end_code+0) from person_code where org='" + org + "' and leader = '" + leader + "'";
            List l = jdbcTemplate.queryForList(sql);
            return l != null && l.size() > 0 ? l.get(0).toString() : "";
        } catch (Exception e) {
            throw new RollbackableException("检索失败", e, this.getClass());
        }
    }

    public List checkPerCodeUsed(String perCode) throws RollbackableException {
        try {
            return hibernateTemplate.find("from  PersonBO  p  where  p.personCode='" + perCode + "'");
        } catch (Exception e) {
            throw new RollbackableException("检索失败", e, this.getClass());
        }
    }

    public List queryPersonCodeByCode(String code)throws BkmsException {
        String sql = "select A001735 from A001 a where A001735 like '"+code+"%' order by A001735 desc " ;
        return jdbcTemplate.queryForList(sql);
    }
}



