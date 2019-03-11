package com.becoda.bkms.run.ucc.impl;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.run.RunConstants;
import com.becoda.bkms.run.pojo.bo.BulletinParamBO;
import com.becoda.bkms.run.pojo.vo.BulletinForm;
import com.becoda.bkms.run.service.BulletinService;
import com.becoda.bkms.run.ucc.IBulletionUCC;
import com.becoda.bkms.util.HrmsLog;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-12
 * Time: 15:59:58
 * To change this template use File | Settings | File Templates.
 */
public class BulletinUCCImpl implements IBulletionUCC {
    private BulletinService bulletinService;

    public BulletinService getBulletinService() {
        return bulletinService;
    }

    public void setBulletinService(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    public BulletinParamBO[] queryBulletinParam(User user) throws RollbackableException {
        return bulletinService.queryBulletinParam(user);
    }

    public HashMap queryBulletinParamAndContent(String bulletinId) throws RollbackableException {
        return bulletinService.queryBulletinParamAndContent(bulletinId);
    }

    public void updateBulletin(BulletinForm vo, String userId, String orgId, User user) throws RollbackableException {
        bulletinService.updateBulletin(vo, userId, orgId);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "更新公告");
    }

    public void createBulletin(BulletinForm vo, String userId, String orgId, User user) throws RollbackableException {
        bulletinService.createBulletin(vo, userId, orgId);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "新建公告");
    }

    public void deleteBulletinByBulletinIdArray(String bulletinIdArray[], User user) throws RollbackableException {
        bulletinService.deleteBulletinByBulletinIdArray(bulletinIdArray);
        HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "删除公告");
    }

    public String findBulletinContent(String bulletinId) throws RollbackableException {
        return bulletinService.findBulletinContent(bulletinId);
    }

    public BulletinParamBO findBulletinParamByBulletinId(String bulletinId) throws RollbackableException {
        return bulletinService.findBulletinParamByBulletinId(bulletinId);
    }

    public String[] findBulletinScopeByBulletinId(String bulletinId) throws RollbackableException {
        return bulletinService.findBulletinScopeByBulletinId(bulletinId);
    }

    public List queryBulletinParamAndScopeByCreateOrgId(String topicQry, String dateQry, String orgId, PageVO vo) throws RollbackableException {
        return bulletinService.queryBulletinParamAndScopeByCreateOrgId(topicQry, dateQry, orgId, vo);
    }
}
