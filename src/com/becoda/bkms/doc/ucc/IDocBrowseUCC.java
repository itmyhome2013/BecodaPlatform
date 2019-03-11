package com.becoda.bkms.doc.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.doc.pojo.vo.OrgBasicInfoVO;
import com.becoda.bkms.doc.pojo.vo.PersonBasicInfoVO;
import com.becoda.bkms.sys.pojo.vo.TableVO;

import java.util.Hashtable;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-21
 * Time: 14:25:45
 */
public interface IDocBrowseUCC {
    public OrgBasicInfoVO queryOrgInfo(String orgID) throws BkmsException;

    PersonBasicInfoVO queryPersonBasicInfoSet(String personId) throws BkmsException;

//    byte[] findPhoto(String s) throws BkmsException;

    TableVO queryPageInfo(String setId, String pk, String personId, User user, String where, String orderby) throws BkmsException;

    public Hashtable getAreaMap();

    public Hashtable queryPersonDataSet(String personID) throws BkmsException;

}
