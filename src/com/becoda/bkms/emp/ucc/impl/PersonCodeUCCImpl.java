package com.becoda.bkms.emp.ucc.impl;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.emp.pojo.bo.PersonCodeBO;
import com.becoda.bkms.emp.service.PersonCodeService;
import com.becoda.bkms.emp.ucc.IPersonCodeUCC;
import com.becoda.bkms.emp.util.EmpUtils;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-2
 * Time: 11:37:42
 */
public class PersonCodeUCCImpl implements IPersonCodeUCC {
    private PersonCodeService personCodeService;

    public PersonCodeService getPersonCodeService() {
        return personCodeService;
    }

    public void setPersonCodeService(PersonCodeService personCodeService) {
        this.personCodeService = personCodeService;
    }

    public PersonCodeBO[] queryPersonCode() throws BkmsException {
        return personCodeService.queryPersonCode();
    }

    public void deletePersonCode(User user, String[] pk) throws BkmsException {
        for (int i = 0; i < pk.length; i++) {
            personCodeService.delPersonCode(pk[i]);
        }
        EmpUtils.log(this, user, "工号删除成功:" + EmpUtils.toString(pk));
    }

    public PersonCodeBO[] queryPersonCode(String org) throws BkmsException {
        return personCodeService.queryPersonCode(org);
    }

    public void modifyPersonCode(User user, PersonCodeBO po) throws BkmsException {
        personCodeService.modifyPersonCode(po);
        EmpUtils.log(this, user, "工号修改成功:" + po.getId());
    }

    public void addPersonCode(User user, PersonCodeBO po) throws BkmsException {
        personCodeService.addPersonCode(po);
        EmpUtils.log(this, user, "工号添加成功:" + po.getId());
    }

    public PersonCodeBO findPersonCode(String id) throws BkmsException {
        return personCodeService.findPersonCode(id);
    }

	public List checkPerCodeUsed(String perCode) throws RollbackableException {
		return personCodeService.checkPerCodeUsed(perCode);
	}

}
