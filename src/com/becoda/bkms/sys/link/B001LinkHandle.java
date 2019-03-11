package com.becoda.bkms.sys.link;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-7
 * Time: 16:43:56
 * To change this template use File | Settings | File Templates.
 */
public class B001LinkHandle extends AbstractLinkHandle {

    public void add(TableVO table) throws BkmsException {
        
    }

    public void update(TableVO table, String[] oldValue) throws BkmsException {
//        if (table == null || !"B001".equals(Tools.filterNull(table.getInfoSet().getSetId()))) {
//            return;
//        }
        String personId = getRowItem(table, 0, "B001255");
        String orgHeaderName = "";
        if (personId != null && personId.trim().length() > 0) {
            orgHeaderName = SysCacheTool.findPersonById(getRowItem(table, 0, "B001255")).getName();
        }
        String pkValue = getRowItem(table, 0, table.getInfoSet().getSetPk());
        String orgName = getRowItem(table, 0, "B001005");
        String orgTree = getRowItem(table, 0, "B001003");
        String orgSort = getRowItem(table, 0, "B001715");
        String orgSuperId = getRowItem(table, 0, "B001002");
        Org org = SysCacheTool.findOrgById(pkValue);
        if (org != null) {
            String oldSuperId = org.getSuperId();
            if (!orgSuperId.equals(oldSuperId)) {

            }
        }


    }

    public void whenDel(TableVO table, Session s) throws BkmsException {

    }

    protected String getSetId() {
        return "B001";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void whenDel(String setId, String pkValue, String fkValue) throws BkmsException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

