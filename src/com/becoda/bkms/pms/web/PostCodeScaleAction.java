package com.becoda.bkms.pms.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.RoleOrgScaleBO;
import com.becoda.bkms.pms.ucc.IRoleOrgScaleUCC;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-18
 * Time: 17:46:12
 * To change this template use File | Settings | File Templates.
 */
public class PostCodeScaleAction extends GenericAction {

    


    /**
     * 存储代码范围权限
     *
     * @param codes     代码字符串   用逗号分割
     * @param scaleFlag "1" 维护  "0" 查询
     */
    private void saveRoleCodeScale(String codes, String scaleFlag, String paramId, String manageFlag, User user) throws BkmsException {
        List list = new ArrayList();
        if (!codes.equals("")) {
            String[] itemIds = codes.split(",");
            for (int i = 0; i < itemIds.length; i++) {
                String itemId = itemIds[i];
                itemId = itemId.substring(1, itemId.length() - 1);
                if (itemId.length() > 4) {
                    RoleOrgScaleBO roleCodeBO = new RoleOrgScaleBO();
                    roleCodeBO.setCodeId(itemId.substring(0, 4));
                    roleCodeBO.setRoleId(paramId);
                    roleCodeBO.setCondId(itemId);
                    roleCodeBO.setScaleFlag(scaleFlag);
                    roleCodeBO.setPmsType(PmsConstants.SCALE_USE);
                    list.add(roleCodeBO);
                }
            }
        }
        List codeSetIdList = new ArrayList();
        IRoleOrgScaleUCC ucc = (IRoleOrgScaleUCC) getBean("pms_roleOrgScaleUCC");
        ucc.updateRoleCodeScale(paramId, scaleFlag, list, manageFlag, codeSetIdList, user);

    }


    
}
