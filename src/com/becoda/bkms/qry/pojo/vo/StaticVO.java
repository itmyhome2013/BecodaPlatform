package com.becoda.bkms.qry.pojo.vo;

import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.qry.pojo.bo.QueryStaticBO;

public class StaticVO implements java.io.Serializable {
    private QueryStaticBO statics;
    private QueryConditionBO condi[];

    public QueryStaticBO getStatics() {
        return statics;
    }

    public void setStatics(QueryStaticBO statics) {
        this.statics = statics;
    }

    public QueryConditionBO[] getCondi() {
        return condi;
    }

    public void setCondi(QueryConditionBO[] condi) {
        this.condi = condi;
    }
}
