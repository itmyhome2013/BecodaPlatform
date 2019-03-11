package com.becoda.bkms.csu.examinfo.dao;

import java.util.List;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 17:03:11
 * To change this template use File | Settings | File Templates.
 */
public class KpQuestionDAO extends GenericDAO {
    public List queryInfoByType(String type) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from KpQuestion i ");
            if (null == list || list.size() == 0)
                return null;
            else
                return list;
        } catch (Exception e) {
            throw new RollbackableException("根据信息类型查询培训信息失败", e, InformationBO.class);
        }
    }

    
}
