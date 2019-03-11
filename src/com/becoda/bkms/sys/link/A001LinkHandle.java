package com.becoda.bkms.sys.link;

import com.becoda.bkms.cache.SysCacheTool;
//import com.becoda.bkms.ccp.ucc.ICcpUCC;
//import com.becoda.bkms.ccyl.ucc.ICcylUCC;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.util.PersonTool;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.sys.pojo.vo.TableVO;
//import com.becoda.bkms.union.ucc.IUnionUCC;


public class A001LinkHandle extends AbstractLinkHandle {


    protected String getSetId() {
        return "A001";
    }


    public void add(TableVO table) throws BkmsException {
//
    }

    public void update(TableVO table, String[] oldValue) throws BkmsException {
        String pkValue = getRowItem(table, 0, table.getInfoSet().getSetPk());
        String orgId = getRowItem(table, 0, "A001701");     //机构
        String deptId = getRowItem(table, 0, "A001705");     //部门
        String id = getRowItem(table, 0, "ID");     //部门
        if (orgId != null && deptId != null) {
            String[] sqls=new String[3];
            Org org = SysCacheTool.findOrgById(orgId);
            Org dept = SysCacheTool.findOrgById(deptId);
            String orgTreeId = org.getTreeId();
            String deptTreeId = dept.getTreeId();
            String workTime = getRowItem(table, 0, "A001041");
            String workYears;
            workYears = PersonTool.workYears(workTime, null, "1");
            sqls[0] = "update A001 set A001728='" + orgTreeId + "',A001738='" + deptTreeId +
                    "',A001710='" + workYears + "',A001743='" + dept.getOrgSort() + "',A001745='999' where ID='" + pkValue + "'";
            sqls[1] ="update A001 set A001205 = to_char(floor((sysdate - to_date(A001011,'yyyy-MM-dd')) / 365.25))," +
                "A001710=to_char(floor((sysdate - to_date(A001041,'yyyy-MM-dd')) / 365.25)) where ID='"+id+"'";
            activePageService.batchExecuteSql(sqls);
        }
    }

    public void whenDel(String setId, String pkValue, String fkValue) throws BkmsException {
        if ("A001".equals(setId)) {
            //清除报表目录记录
            activePageService.executeSql("delete from rpt_class_set where user_id='" + pkValue + "'");
        }
    }
}
