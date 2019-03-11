package com.becoda.bkms.run.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.run.pojo.bo.RemBO;
import com.becoda.bkms.run.pojo.bo.RemOrgScopeBO;
import com.becoda.bkms.run.pojo.bo.RemPersonScopeBO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-23
 * Time: 15:11:50
 * To change this template use File | Settings | File Templates.
 */
public interface IRemUCC {
    public TableVO queryBirthList(User user, String sql, InfoItemBO[] showHeader, PageVO page, boolean isPage) throws BkmsException;

    public RemBO[] queryAllRem(String orgId) throws BkmsException;

    public RemBO findRemById(User user, String remId) throws BkmsException;

//    public RemBO queryAllRemOrgScope(String remId) throws BkmsException ;

    public RemOrgScopeBO[] queryAllRemOrgScope(String remId) throws BkmsException;

    public RemPersonScopeBO[] queryAllRemPersonScope(String remId, String toType) throws BkmsException;

    public ArrayList queryRemByUserId(String userId, User user) throws BkmsException;

    public void startOrStopRem(String[] ids, String flag, User user) throws BkmsException;

    public void saveRemScope(String remId, String orgIds, List persons, List roles, User user) throws BkmsException;

    public String addRem(RemBO bo,QueryVO vo, User user) throws BkmsException;

    public void updateRem(RemBO bo,QueryVO vo, User user) throws BkmsException;

    public void deleteRem(String[] ids, User user) throws BkmsException;

    public int queryRemBrithDayByUserId(User user) throws BkmsException;
}
