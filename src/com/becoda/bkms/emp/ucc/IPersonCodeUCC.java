package com.becoda.bkms.emp.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.emp.pojo.bo.PersonCodeBO;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-2
 * Time: 11:37:28
 */
public interface IPersonCodeUCC {
    PersonCodeBO[] queryPersonCode() throws BkmsException;

    PersonCodeBO[] queryPersonCode(String org) throws BkmsException;

    void deletePersonCode(User user, String[] pk) throws BkmsException;

    void modifyPersonCode(User user, PersonCodeBO po) throws BkmsException;

    void addPersonCode(User user, PersonCodeBO po) throws BkmsException;

    PersonCodeBO findPersonCode(String id) throws BkmsException;
    public List checkPerCodeUsed(String perCode) throws RollbackableException;
}
