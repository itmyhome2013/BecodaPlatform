package com.becoda.bkms.run.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.run.pojo.bo.BulletinParamBO;
import com.becoda.bkms.run.pojo.vo.BulletinForm;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-12
 * Time: 16:00:33
 * To change this template use File | Settings | File Templates.
 */
public interface IBulletionUCC {
    public BulletinParamBO[] queryBulletinParam(User user) throws BkmsException;

    public HashMap queryBulletinParamAndContent(String bulletinId) throws BkmsException;

    public void updateBulletin(BulletinForm vo, String userId, String orgId, User user) throws BkmsException;

    public void createBulletin(BulletinForm vo, String userId, String orgId, User user) throws BkmsException;

    public void deleteBulletinByBulletinIdArray(String bulletinIdArray[], User user) throws BkmsException;

    public String findBulletinContent(String bulletinId) throws BkmsException;

    public BulletinParamBO findBulletinParamByBulletinId(String bulletinId) throws BkmsException;

    public String[] findBulletinScopeByBulletinId(String bulletinId) throws BkmsException;

    public List queryBulletinParamAndScopeByCreateOrgId(String topicQry, String dateQry, String orgId, PageVO vo) throws BkmsException;

}
