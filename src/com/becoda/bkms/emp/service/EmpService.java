package com.becoda.bkms.emp.service;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.emp.pojo.bo.PersonBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-18
 * Time: 9:15:16
 * To change this template use File | Settings | File Templates.
 */
public class EmpService {
    private GenericDAO genericDAO;

    public GenericDAO getGenericDAO() {
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public List queryAllPerson() throws RollbackableException {
        return genericDAO.findByExample(new Person());
    }

    public PersonBO[] queryAllPerson(String where) throws RollbackableException {
        try {
            List list = genericDAO.getHibernateTemplate().find("from PersonBO p where 1 = 1 " + where);
            return list == null || list.size() < 1 ? null : (PersonBO[]) list.toArray(new PersonBO[list.size()]);
        } catch (Exception e) {
            throw new RollbackableException("", e, this.getClass());
        }
    }

    public Person findPerson(String personId) throws RollbackableException {
        return (Person) genericDAO.getHibernateTemplate().get(Person.class, personId);
    }

    public Person findPersonByCode(String personCode) throws RollbackableException {
        Person person = new Person();
        person.setPersonCode(personCode);
        List list = genericDAO.findByExample(person);
        return list == null || list.size() < 1 ? null : (Person) list.get(0);
    }

    public PersonBO findPersonBO(String personId) throws RollbackableException {
        return (PersonBO) genericDAO.findBo(PersonBO.class, personId);
    }

    public PersonBO findPersonBOByCode(String personCode) throws RollbackableException {
        PersonBO person = new PersonBO();
        person.setPersonCode(personCode);
        return (PersonBO) genericDAO.findByExample(person).get(0);
    }
}
