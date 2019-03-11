package com.becoda.bkms.sys.link;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.ucc.IPersonUCC;
import com.becoda.bkms.emp.util.PersonTool;
import com.becoda.bkms.sys.pojo.vo.TableVO;


public class A701LinkHandle extends AbstractLinkHandle {
    private IPersonUCC personUCC;

    public IPersonUCC getPersonUCC() {
        return personUCC;
    }

    public void setPersonUCC(IPersonUCC personUCC) {
        this.personUCC = personUCC;
    }

    protected String getSetId() {
        return "A701";  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void add(TableVO table) throws BkmsException {

    }

    protected void update(TableVO table, String[] oldValue) throws BkmsException {
        String pkValue = getRowItem(table, 0, table.getInfoSet().getSetPk());
        String workYearsReduce = getRowItem(table, 0, "A701710");
        String finYearsReduce = getRowItem(table, 0, "A701201");
        String workYears2 = getRowItem(table, 0, "A701705");
        String finYears2 = getRowItem(table, 0, "A701200");
        String workYears;
        String finYears;
        PersonBO per = personUCC.findPerson(pkValue);
        if (workYearsReduce != null && !"".equals(workYearsReduce) && workYears2 != null && !"".equals(workYears2)) {
            if (per != null && per.getWorkTime() != null) {
                workYears = PersonTool.workYears(per.getWorkTime(), null, "1");

                if (Integer.parseInt(workYears) - Integer.parseInt(workYearsReduce) <= 0) {
                    workYears = "0";
                } else {
                    workYears = Integer.parseInt(workYears) - Integer.parseInt(workYearsReduce) + "";
                }
                String sql = "update A701 set A701705 = '" + workYears + "' where ID = '" + pkValue + "'";
                activePageService.executeSql(sql);
//                String[] infoItems = new String[]{"ID","A701705"};
//                String[] itemValues = new String[]{pkValue,workYears};
//                activePageService.up("A701", null, pkValue, null, false, null, null, infoItems, itemValues);
            }
        }
        if (finYearsReduce != null && !"".equals(finYearsReduce) && finYears2 != null && !"".equals(finYears2)) {
            if (per != null && per.getFinanceTime() != null) {
                finYears = PersonTool.financeYears(per.getFinanceTime(), Integer.parseInt(finYearsReduce));
                String sql = "update A701 set A701200 = '" + finYears + "' where ID = '" + pkValue + "'";
                activePageService.executeSql(sql);
//                String[] infoItems2 = new String[]{"A701200"};
//                String[] itemValues2 = new String[]{finYears};
//                activePageService.updatePageInfo("A701", null, pkValue, null, false, null, null, infoItems2, itemValues2);
            }
        }
    }

    public void whenDel(String setId, String pkValue, String fkValue) throws BkmsException {

    }

//    public void whenUpdate(TableVO oldTable, TableVO newTable, Session s) throws BkmsException {
//
//        if (newTable == null) {
//            return;
//        }
//        if (newTable.getSetId() == null || !"A701".equals(newTable.getSetId())) {
//            return;
//        }
//        ActivePageManager apm = new ActivePageManager(s);
//        RecordVO[] r = newTable.getRowData();
//        RecordVO[] r2 = oldTable.getRowData();
//        if (r != null && r.length > 0) {
//            String pkValue = ((CellVO) r[0].cellArray2Hash().get(newTable.getSetPk())).getValue();
//            String workYearsReduce = ((CellVO) r[0].cellArray2Hash().get("A701710")).getValue();
//            String finYearsReduce = ((CellVO) r[0].cellArray2Hash().get("A701201")).getValue();
//            String workYears2 = ((CellVO) r[0].cellArray2Hash().get("A701705")).getValue();
//            String finYears2 = ((CellVO) r[0].cellArray2Hash().get("A701200")).getValue();
//            String workYears = "";
//            String finYears = "";
//            PersonBO per = SysCacheTool.findPersonById(pkValue);
//            if (workYearsReduce != null && !"".equals(workYearsReduce) && workYears2 != null && !"".equals(workYears2))
//            {
//                if (per != null && per.getWorkTime() != null) {
//                    workYears = PersonTool.workYears(per.getWorkTime(), null, "1");
//
//                    if (Integer.parseInt(workYears) - Integer.parseInt(workYearsReduce) <= 0) {
//                        workYears = "0";
//                    } else {
//                        workYears = Integer.parseInt(workYears) - Integer.parseInt(workYearsReduce) + "";
//                    }
//
//                    String[] infoItems = new String []{"A701705"};
//                    String[]  itemValues = new String[]{workYears};
//                    apm.updatePageInfo("A701", null, pkValue, null, false, null, null, infoItems, itemValues);
//                }
//            }
//            if (finYearsReduce != null && !"".equals(finYearsReduce) && finYears2 != null && !"".equals(finYears2)) {
//                if (per != null && per.getFinanceTime() != null) {
//                    finYears = PersonTool.financeYears(per.getFinanceTime(),Integer.parseInt(finYearsReduce));
//
//                    String[] infoItems2 = new String []{"A701200"};
//                    String[]  itemValues2 = new String[]{finYears};
//                    apm.updatePageInfo("A701", null, pkValue, null, false, null, null, infoItems2, itemValues2);
//                }
//            }
//        }
//    }

//    public void whenDel(TableVO table, Session s) throws BkmsException {
//        //
//    }
}