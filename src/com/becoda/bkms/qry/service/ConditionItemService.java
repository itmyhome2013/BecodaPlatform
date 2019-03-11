package com.becoda.bkms.qry.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.dao.QueryConditionDAO;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.util.SequenceGenerator;


public class ConditionItemService {

    private QueryConditionDAO conditiondao;

    public QueryConditionBO[] queryCondition(String staticId) throws BkmsException {
        return conditiondao.queryCondition(staticId);
    }

    public void deleteCondition(String staticId) throws BkmsException {
        conditiondao.deleteCondition(staticId);
    }

    public String[] createConditions(QueryConditionBO[] cbos, String staticId) throws BkmsException {
        if (cbos == null || cbos.length <= 0)
            return null;
        try {
            this.deleteCondition(staticId);
            String[] ids = SequenceGenerator.getKeyId("qry_condition", cbos.length);
            for (int i = 0; i < cbos.length; i++) {
                cbos[i].setCondiId(ids[i]);
                cbos[i].setStaticId(staticId);
                conditiondao.createBo(cbos[i]);
            }
            return ids;
        } catch (Exception e) {
            /*********** modify by wanglijun 2015-7-26 *************/
//            throw new RollbackableException("", "", e, getClass());
            throw new RollbackableException("保存查询条件失败", e, getClass());
            /****************** end modify *************************/
        }
    }


    public QueryConditionDAO getConditiondao() {
        return conditiondao;
    }

    public void setConditiondao(QueryConditionDAO conditiondao) {
        this.conditiondao = conditiondao;
    }
}
