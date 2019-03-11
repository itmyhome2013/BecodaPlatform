package com.becoda.bkms.emp.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
//import com.becoda.bkms.ccp.service.CcpService;
//import com.becoda.bkms.ccyl.service.CcylService;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.PageVO;
//import com.becoda.bkms.cont.service.AttachmentService;
import com.becoda.bkms.emp.EmpConstants;
import com.becoda.bkms.emp.pojo.bo.*;
import com.becoda.bkms.emp.pojo.vo.PersonChangeVO;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
import com.becoda.bkms.emp.pojo.vo.RecoverVO;
//import com.becoda.bkms.emp.pojo.vo.ZPResumeInfoVO;
//import com.becoda.bkms.emp.service.BlackListService;
import com.becoda.bkms.emp.service.PersonCodeService;
import com.becoda.bkms.emp.service.PersonService;
//import com.becoda.bkms.emp.ucc.IAdjustUCC;
import com.becoda.bkms.emp.ucc.IPersonUCC;
import com.becoda.bkms.emp.util.EmpUtils;
import com.becoda.bkms.emp.util.IDCard18;
import com.becoda.bkms.emp.util.TransferUtils;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.service.OrgService;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.pms.service.UserManageService;
//import com.becoda.bkms.post.pojo.bo.PostBO;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.api.IQuery;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
//import com.becoda.bkms.union.pojo.bo.UnionBO;
//import com.becoda.bkms.union.service.UnionService;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-1
 * Time: 16:19:32
 */
public class PersonUCCImpl implements IPersonUCC {
    private PersonService personService;
    private PersonCodeService personCodeService;
    private ActivePageService activePageService;
    private OrgService orgService;
//    private CcpService ccpService;
//    private CcylService ccylService;
//    private UnionService unionService;
//    private AttachmentService attachmentService;
    private UserManageService userManageService;
//    private BlackListService blacklistService;
//
//    public BlackListService getBlacklistService() {
//        return blacklistService;
//    }
//
//    public void setBlacklistService(BlackListService blacklistService) {
//        this.blacklistService = blacklistService;
//    }

//    public CcpService getCcpService() {
//        return ccpService;
//    }
//
//    public UnionService getUnionService() {
//        return unionService;
//    }
//
//    public void setUnionService(UnionService unionService) {
//        this.unionService = unionService;
//    }
//
//    public void setCcpService(CcpService ccpService) {
//        this.ccpService = ccpService;
//    }
//
//    public CcylService getCcylService() {
//        return ccylService;
//    }
//
//    public void setCcylService(CcylService ccylService) {
//        this.ccylService = ccylService;
//    }

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }





    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public PersonCodeService getPersonCodeService() {
        return personCodeService;
    }

    public void setPersonCodeService(PersonCodeService personCodeService) {
        this.personCodeService = personCodeService;
    }

    public OrgService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }

    public String checkNewPerson(PersonVO person) throws BkmsException {
        if (!"".equals(person.getPersonCode())) {
            List list2 = personCodeService.checkPerCodeUsed(person.getPersonCode());
            if (list2 != null && list2.size() > 0) {
                return "员工工号已用,请重新输入工号!";
            }
        }
        String idNum = person.getIdNum();
        if (idNum != null) {
//            IDCard18 id = new IDCard18();
//            idNum = id.Get18(idNum);                                           //转化为18位
//            int i = personService.queryPersonCount("  and  p.idCard='" + idNum + "'");
//            if (i > 0) {
//                return "身份证号重复,请重新输入!";
//            }
            PersonBO bo = personService.findPersonByIdCard(idNum);
            if (bo != null) {
                return "身份证号与“" + bo.getName() + "”重复，（“" + bo.getName() + "”在" + SysCacheTool.interpretOrg(bo.getOrgId())
                        + "下，人员状态为“" + SysCacheTool.interpretCode(bo.getState()) + "”）";
            }
        }
//        if (blacklistService.isExitByPersonIdCard(idNum)) {
//            return "系统黑名单中存在该人员信息，请核对后操作!";
//        }
//        List list = personService.queryPersonByNameSpell(person.getNameSpell());
//        if (list.size() > 0) {
//            return "拼音姓名已用,请重新输入!";
//        }
        return null;
    }

    //查询员工是否已经入产品库
    public String checkZPToPerson(String personCode, String IdNum) throws BkmsException {
        String idNum = IdNum;
        if (idNum != null) {
            IDCard18 id = new IDCard18();
            idNum = id.Get18(idNum);                                           //转化为18位
            int i = personService.queryPersonCount("  and  p.idCard='" + idNum + "'");
            if (i > 0) {
                return "此人身份证号已经存在，不能重复录入!";
            }
        }
        if (!"".equals(personCode)) {
            List list2 = personCodeService.checkPerCodeUsed(personCode);
            if (list2 != null && list2.size() > 0) {
                return "员工工号已用,请重新输入工号!";
            }
        }
        return null;
    }

    //抛出回滚异常
    public String createPerson(PersonVO person, User user, byte[] photo) throws RollbackableException {
        String orgTree = "";
        String deptTree = "";
        String deptSort = "";
        String partyTree = "";
        String groupTree = "";
        String labourTree = "";

//        OrgBO org = OrgTool.getOrgByDept(Tools.filterNull(person.getDeptId()));
        OrgBO org = orgService.findOrgByDept(Tools.filterNull(person.getDeptId()));
        if (org != null) {
            person.setOrgId(org.getOrgId());
            orgTree = org.getTreeId();
//            if ("013511".equals(person.getPersonType()) || "013513".equals(person.getPersonType())) { //员工类别为合同工或代理工
//                if (person.getPersonCode() == null || "".equals(person.getPersonCode())) {              //如果员工编号不为空
//                    String percode = personCodeService.generateNewPerCode(org.getOrgId(), person.getPersonType());   //生成员工编号
//                    person.setPersonCode(percode);
//                }
//            }
        }
        OrgBO dept = orgService.findOrgBO(person.getDeptId());
        if (dept != null) {
            deptTree = dept.getTreeId();
            deptSort = dept.getOrgSort();
        }



        //记录插入人员基本信息子集
        String[] infoItems = new String[]{PersonVO.age_Field, PersonVO.name_Field, PersonVO.sex_Field, PersonVO.personCode_Field,
                PersonVO.birth_Field, PersonVO.orgId_Field, PersonVO.deptId_Field,
                PersonVO.idNum_Field, PersonVO.orgTreeId_Field,
                PersonVO.deptTreeId_Field, PersonVO.deptSortl_Field, PersonVO.retireCancel_Field, PersonVO.cancel_Field,
                PersonVO.evernName_Field, PersonVO.status_Field, PersonVO.personType_Field,
                PersonVO.homeTown_Field, PersonVO.homePlace_Field,
                PersonVO.folk_Field, PersonVO.partyFigure_Field, PersonVO.partyTime_Field,
                //PersonVO.workTime_Field, PersonVO.workTime_years, PersonVO.sysTime_Field,
                PersonVO.unitTime_Field, PersonVO.economicYear_Field, PersonVO.financeTime_Field,
                PersonVO.marryStatus_Field, PersonVO.health_Field, PersonVO.homePhone_Field,
                PersonVO.familyAdd_Field, PersonVO.strongPoint_Field, PersonVO.hobby_Field,
                PersonVO.birth_Field, PersonVO.mobilePhone_Field, PersonVO.photo_Field
        };
        String[] ietmValues = new String[]{Tools.filterNull(person.getAge()), Tools.filterNull(person.getName()), Tools.filterNull(person.getSex()), Tools.filterNull(person.getPersonCode()),
                Tools.filterNull(person.getBirth()), Tools.filterNull(person.getOrgId()), Tools.filterNull(person.getDeptId()),
                Tools.filterNull(person.getIdNum()), Tools.filterNull(orgTree),
                Tools.filterNull(deptTree), Tools.filterNull(deptSort), Constants.NO, Constants.NO,
                Tools.filterNull(person.getEvername()), Tools.filterNull(person.getStatus()), Tools.filterNull(person.getPersonType()),
                Tools.filterNull(person.getHomeTown()), Tools.filterNull(person.getHomePlace()),
                Tools.filterNull(person.getFolk()), Tools.filterNull(person.getPartyFigure()), Tools.filterNull(person.getPartyTime()),
                Tools.filterNull(person.getUnitTime()), Tools.filterNull(person.getEconomicYear()), Tools.filterNull(person.getFinanceTime()),
                Tools.filterNull(person.getMarriage()), Tools.filterNull(person.getHealth()), Tools.filterNull(person.getHomePhone()),
                Tools.filterNull(person.getFamilyAdd()), Tools.filterNull(person.getStrongPoint()), Tools.filterNull(person.getHobby()),
                Tools.filterNull(person.getBirth()), Tools.filterNull(person.getMobilePhone()), Tools.filterNull(person.getPhoto())
        };
        String perId = null;
        try {
            perId = activePageService.addRecord(user, "A001", TransferUtils.arrayToMap(infoItems, ietmValues), true, true);
            RecoverVO vo = new RecoverVO();
            //增加人员增减子集记录
            vo.setA016010(person.getA016010());
            vo.setA016040(person.getA016040());
            vo.setA016020(person.getA016020());
            vo.setA016030(person.getA016030());
            vo.setA016200(person.getA016200());
            this.addA016(activePageService, user, vo, perId);
            String[] sqls = new String[2];
            sqls[0] = "update A001 set A001205 = to_char(floor((sysdate - to_date(A001011,'yyyy-MM-dd')) / 365.25))," +
                    "A001710=to_char(floor((sysdate - to_date(A001041,'yyyy-MM-dd')) / 365.25)) where ID='" + perId + "'";
            //增加该人员的一条工作经历子集记录
            sqls[1] = "insert into A019 (ID,SUBID,A019005,A019010,A019015,A019020,A019205,A019201,A019000) " +
                    "values('" + perId + "','" + SequenceGenerator.getUUID() + "','" + (person.getUnitTime() == null ? "" : person.getUnitTime().substring(0, 7)) +
                    "','','" + SysCacheTool.interpretOrg(person.getOrgId()) + "','" + SysCacheTool.interpretOrg(person.getDeptId()) + "','"
                    + "','" + person.getA016200() + "','00901')";


            activePageService.batchExecuteSql(sqls);
            //给考勤模块传perId
//        kqRestInfoService.addForTiaoHoliday(perId);
            person.setPersonId(perId);
            userManageService.addUserInfo(person);
            EmpUtils.log(this, user, "创建人员成功:" + perId);
        } catch (Exception e) {
            throw new RollbackableException("创建人员失败", e, this.getClass());
        }
        return perId;
    }

    //人员增减信息子集 增加一条记录
    private void addA016(ActivePageService activePageService, User user, RecoverVO person, String perId) throws RollbackableException {
        String sql = "update A016 set  A016000 = '" + Constants.NO + "' where ID='" + perId + "'";
        activePageService.executeSql(sql);
        EmpUtils.log(this, user, "新增一条增减信息子集");
        String[] infoItems = new String[]{"ID", "A016010", "A016040", "A016020", "A016030", "A016200", "A016000"};
        String[] ietmValues = new String[]{perId, Tools.filterNull(person.getA016010()), Tools.filterNull(person.getA016040()), Tools.filterNull(person.getA016020()),
                Tools.filterNull(person.getA016030()), Tools.filterNull(person.getA016200()), Constants.YES};
        activePageService.addRecord(user, "A016", TransferUtils.arrayToMap(infoItems, ietmValues), true, true);
    }

    /**
     * 查询人员基本信息
     *
     * @return
     * @throws RollbackableException
     */
//    public ZPResumeInfoVO queryPersonInfo(String personId) throws BkmsException {
//        return personService.queryPersonInfo(personId);
//    }

    public TableVO queryPersonList(User user, String unit, String name, String personType, String superId, PageVO page, String cancelFlag) throws BkmsException {
        return queryPersonList(user, unit, name, personType, superId, page, cancelFlag, EmpConstants.DEFAULT_QUERY_PERSON, true);
    }

   

    /**
     * 查询待入职人员简历
     *
     * @return
     * @throws RollbackableException
     */
//    public ZPResumeInfoVO queryZPResumeInfo(String personId) throws RollbackableException {
//        return personService.queryZPResumeInfo(personId);
//    }

    // 将待入职人员信息导入人员管理系统中
    public void queryZpToPersonAdd(String personId) throws BkmsException {
//        s

    }

    // 查询待入职人员信息
    public TableVO queryZpToPersonList(User user, String name, String cartnum, PageVO page, String qryId) throws BkmsException {
        StringBuffer wheres = new StringBuffer();
//        if (Tools.filterNull(name).equals("") && Tools.filterNull(cartnum).equals("")) {
//            return null;
//        }
        if (!Tools.filterNull(name).equals("")) {
            wheres.append(" and G001.G001200 LIKE '%").append(name).append("%'");
        }
        if (!Tools.filterNull(cartnum).equals("")) {
            wheres.append(" and G001.G001205  LIKE '%").append(cartnum).append("%'");
        }

        String select = "G001.ID, G001200,G001205,G001201,G001221,G001223,G001222,G205204,G205206,G205207";
        String from = "G001 LEFT JOIN g205 ON g001.ID = g205.ID ";
        String where = "(G205222='1' " + wheres + " and G001205  not in(select A001077 from A001,G001  where A001.A001077=g001.g001205))" +
                " or " +
                "(G205223='1' " + wheres + " and G001205  not in(select A001077 from A001,G001  where A001.A001077=g001.g001205))";
        String order = "G205200 ASC";
        InfoItemBO[] header = getHeader(select);
        String sql = "select " + select + " from " + from;
        //转换显示项
        StringBuffer showItem = new StringBuffer();
        for (int i = 0; i < header.length; i++) {
            showItem.append(",").append(header[i].getSetId()).append(".").append(header[i].getItemId());
        }
        //where 子句
        sql += (" where 1=1 " + ("".equals(where) ? "" : " and " + where));
        //拼接自己的条件
//        sql += wheres;
        sql += ("".equals(order) ? "" : " order by " + order);
        page.setQrySql(sql);
        page.setHash(null);
        page.setShowField(showItem.length() > 0 ? showItem.substring(1) : showItem.toString());
        TableVO table = activePageService.queryListBySql(sql, header, null, page, true);
        return table;
    }

    public TableVO queryPersonList(User user, String unit, String name, String personType, String superId, PageVO page, String cancelFlag, String qryId, boolean right) throws BkmsException {
        StringBuffer where = new StringBuffer();
        String czbWhere = "";//一个员工（如果是一人多岗）可在多个部门下查看到 add by huangh in 09-11-18
        if (name != null && !"".equals(name.trim())) {
            StringTokenizer token = new StringTokenizer(name, " ,;:，；：");
            Vector value = new Vector();
            while (token.hasMoreElements()) {
                value.add(token.nextElement());
            }
            int count = value.size();
            if (count == 1) {
                where.append(" and A001.A001001 LIKE '%").append(name).append("%'");
                czbWhere += " and A001.A001001 LIKE '%" + name + "%'";
            } else if (count > 1) {
                for (int i = 0; i < count; i++) {
                    if (i == 0) {
                        where.append(" and (A001.A001001 LIKE '%").append((String) value.elementAt(i)).append("%'");
                        czbWhere += " and A001.A001001 LIKE '%" + value.elementAt(i) + "%'";
                    } else {
                        where.append("  or  A001.A001001 LIKE '%").append((String) value.elementAt(i)).append("%'");
                        czbWhere += " or A001.A001001 LIKE '%" + value.elementAt(i) + "%'";
                    }
                }
                where.append(")");
            }
        }
        if (!Tools.filterNull(unit).equals("")) {
            Org orgbo = SysCacheTool.findOrgById(unit);
            where.append(" and A001.A001738 like'").append(orgbo.getTreeId()).append("%'");
        }
        if (personType != null && !"".equals(personType.trim())) {
            String[] pers = personType.split(",");
            where.append(" and ").append(Tools.splitInSql(pers, "A001.A001054"));
        }
        if (superId != null && !"".equals(superId.trim())) {
            Org bo = SysCacheTool.findOrgById(superId);
            if (bo != null) {
                where.append(" and A001.A001738 LIKE '").append(bo.getTreeId()).append("%' ");
                //一个员工（如果是一人多岗）可在多个部门下查看到 add by huangh in 09-11-18
                where.append("OR (A001.id IN (SELECT id FROM A777 WHERE dept_tree_id LIKE '").append(bo.getTreeId()).append("%')").append(czbWhere).append(")");
            }
        }
        if (Constants.YES.equals(cancelFlag)) {
            where.append(" and (A001730 = '").append(cancelFlag).append("' OR A001755 = '").append(cancelFlag).append("')");
        } else if (Constants.NO.equals(cancelFlag)) {
            where.append(" and A001730 = '").append(cancelFlag).append("'");
        }
        return queryAdvanceByRight(user, qryId, where.toString(), page, right);
    }

    public TableVO queryPersonList(User user, String name, String superId, PageVO page, String cancelFlag, String qryId, String personCode) throws BkmsException {
        StringBuffer where = new StringBuffer();
        String czbWhere = "";//一个员工（如果是一人多岗）可在多个部门下查看到 add by huangh in 09-11-18
        if (name != null && !"".equals(name.trim())) {
            StringTokenizer token = new StringTokenizer(name, " ,;:，；：");
            Vector value = new Vector();
            while (token.hasMoreElements()) {
                value.add(token.nextElement());
            }
            int count = value.size();
            if (count == 1) {
                where.append(" and A001.A001001 LIKE '%").append(name).append("%'");
                czbWhere += " and A001.A001001 LIKE '%" + name + "%'";
            } else if (count > 1) {
                for (int i = 0; i < count; i++) {
                    if (i == 0) {
                        where.append(" and (A001.A001001 LIKE '%").append((String) value.elementAt(i)).append("%'");
                        czbWhere += " and A001.A001001 LIKE '%" + value.elementAt(i) + "%'";
                    } else {
                        where.append("  or  A001.A001001 LIKE '%").append((String) value.elementAt(i)).append("%'");
                        czbWhere += " or A001.A001001 LIKE '%" + value.elementAt(i) + "%'";
                    }
                }
                where.append(")");
            }
        }
        if (personCode != null && !"".equals(personCode.trim())) {
            where.append(" and A001735 LIKE '").append(personCode).append("%'");
            czbWhere += " and A001735 LIKE '" + personCode + "%'";
        }
        if (superId != null && !"".equals(superId.trim())) {
            Org bo = SysCacheTool.findOrgById(superId);
            if (bo != null && !"101".equals(superId)) {
                where.append(" and A001.A001738 LIKE '").append(bo.getTreeId()).append("%'");
                //一个员工（如果是一人多岗）可在多个部门下查看到 add by huangh in 09-11-18
                where.append("OR (A001.id IN (SELECT id FROM A777 WHERE dept_tree_id LIKE '").append(bo.getTreeId()).append("%')").append(czbWhere).append(")");
            }
        }
        if (Constants.YES.equals(cancelFlag)) {
            where.append(" and (A001730 = '").append(cancelFlag).append("' OR A001755 = '").append(cancelFlag).append("')");
        } else if (Constants.NO.equals(cancelFlag)) {
            where.append(" and A001730 = '").append(cancelFlag).append("'");
        }

        return queryAdvance(user, qryId, where.toString(), page);
    }

    public TableVO queryPersonBySuper(User user, String superId, PageVO page, String cancelFlag) throws BkmsException {
        return queryPersonBySuper(user, superId, page, cancelFlag, EmpConstants.DEFAULT_QUERY_PERSON);
    }

    public TableVO queryPersonBySuperRight(User user, String superId, PageVO page, String cancelFlag, boolean right) throws BkmsException {
        return queryPersonList(user, null, null, null, superId, page, cancelFlag, EmpConstants.DEFAULT_QUERY_PERSON, right);
    }

    public TableVO queryPersonBySuper(User user, String superId, PageVO page, String cancelFlag, String qryId) throws BkmsException {
        return queryPersonList(user, null, null, null, superId, page, cancelFlag, qryId, true);
    }


    public TableVO queryPersonList(User user, PageVO page) throws BkmsException {
        String activeQryShowItem = page.getShowField();
        String activeQrySql = page.getQrySql();
        if (activeQryShowItem.startsWith(",")) activeQryShowItem = activeQryShowItem.substring(1);
        String[] items = activeQryShowItem.split(",");
        int len = items.length;
        InfoItemBO[] header = new InfoItemBO[len];
        for (int i = 0; i < len; i++) {
            String[] item = items[i].split("\\.");
            header[i] = SysCacheTool.findInfoItem(item[0], item[1]);
        }
        String righField = Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_PERSON));
        if (righField != null && righField.length() > 0) {
            String[] fields = righField.split(",");
            String requiredFieldString = null;
            int fromIndex = activeQrySql.toLowerCase().indexOf("from");
            String select = activeQrySql.substring(0, fromIndex);
            String upperSelect = select.toUpperCase();
            for (int i = 0; i < fields.length; i++) {
                String field = fields[i];
                if (upperSelect.indexOf(field.toUpperCase()) < 0) {
                    if (requiredFieldString == null) {
                        requiredFieldString = field;
                    } else {
                        requiredFieldString += "," + field;
                    }
                }
            }
            if (requiredFieldString != null) {
                String from = activeQrySql.substring(fromIndex);
                activeQrySql = select + "," + requiredFieldString + " " + from;
            }
        }
        TableVO table = activePageService.queryListBySql(activeQrySql, header, righField, page, true);
        PmsAPI pms = new PmsAPI();
        pms.checkPersonRecord(user, table);
        return table;
    }


    public void updateDismissPerson(User user, PersonChangeVO personchangevo, String[] ids) throws BkmsException {
//        String changeType = Tools.filterNull(personchangevo.getChangeType());
//        if ("".equals(changeType) || ids == null || ids.length == 0) {
//            return;
//        }
//        changeType = changeType.substring(4, changeType.length());
        ArrayList list = new ArrayList();
        //修改人员的减员标志
        list.add("update A001 set A001730 = '" + Constants.YES + "' where  " + Tools.splitInSql(ids, "ID"));
//        list.add("update A019 SET A019010 = '" + Tools.filterNull(personchangevo.getChangeDate()) + "' WHERE A019000='" + Constants.YES + "' AND  " + Tools.splitInSql(ids, "ID"));

        

        //增加增减情况子集
//        list.add("update A016 set A016000 = '" + Constants.NO + "' where A016000 = '" + Constants.YES + "' and  " + Tools.splitInSql(ids, "ID"));
//        for (int i = 0; i < ids.length; i++) {
//            list.add("INSERT INTO A016 (ID,SUBID,A016000,A016010,A016040,A016030,A016020,A016200,A016201) values ('" + ids[i] + "','" + Tools.filterNull(SequenceGenerator.getUUID()) + "','"
//                    + Constants.YES + "','" + Tools.filterNull(personchangevo.getChangeType()) + "'," +
//                    "'" + Tools.filterNull(personchangevo.getChangeDate()) + "','" + Tools.filterNull(personchangevo.getChangeUnit()) + "'," +
//                    "'" + Tools.filterNull(personchangevo.getRelationNum()) + "','" + Tools.filterNull(personchangevo.getTractPerson()) + "'," +
//                    "'"+Tools.filterNull(personchangevo.getLeaveReason())+"')");
//            String stat = "";//人员状态
            //减员类别为辞退，劝退，开除的人员进入黑名单库
//            if (EmpConstants.DISS_TYPE_KC.equals(personchangevo.getChangeType()) ||
//                    EmpConstants.DISS_TYPE_QT.equals(personchangevo.getChangeType()) ||
//                    EmpConstants.DISS_TYPE_CT.equals(personchangevo.getChangeType())) {
//                String type = "";
//                if (EmpConstants.DISS_TYPE_KC.equals(personchangevo.getChangeType())) {
//                    type = EmpConstants.BLACK_TYPE_KC;
//                    stat = EmpConstants.PERSON_STAT_KC;
//                }
//                if (EmpConstants.DISS_TYPE_QT.equals(personchangevo.getChangeType())) {
//                    type = EmpConstants.BLACK_TYPE_QT;
//                    stat = EmpConstants.PERSON_STAT_QT;
//                }
//                if (EmpConstants.DISS_TYPE_CT.equals(personchangevo.getChangeType())) {
//                    type = EmpConstants.BLACK_TYPE_CT;
//                    stat = EmpConstants.PERSON_STAT_CT;
//                }
//                ZpBlacklistBO abo = new ZpBlacklistBO();
//                PersonBO bo = personService.findPerson(ids[i]);
//                abo.setPersonId(ids[i]);
//                abo.setPersonName(bo.getName());
//                abo.setBlackType(type);
//                abo.setCardNumber(bo.getIdCard());
//                abo.setCreateDate(personchangevo.getChangeDate());
//                abo.setListRemark(personchangevo.getRelationNum());
//                blacklistService.addBlackList(abo);
//            } else {
//                if (EmpConstants.DISS_TYPE_DC.equals(personchangevo.getChangeType())) {
//                    stat = EmpConstants.PERSON_STAT_DC;
//                }
//                if (EmpConstants.DISS_TYPE_CZ.equals(personchangevo.getChangeType())) {
//                    stat = EmpConstants.PERSON_STAT_CZ;
//                }
//                if (EmpConstants.DISS_TYPE_TX.equals(personchangevo.getChangeType())) {
//                    stat = EmpConstants.PERSON_STAT_TX;
//                }
//                if (EmpConstants.DISS_TYPE_DEAD.equals(personchangevo.getChangeType())) {
//                    stat = EmpConstants.PERSON_STAT_DEAD;
//                }
//                if (EmpConstants.DISS_TYPE_OTHER.equals(personchangevo.getChangeType())) {
//                    stat = EmpConstants.PERSON_STAT_OTHER;
//                }
////            }
//            //改变人员状态
//            list.add("update A001 set A001725='" + stat + "' where ID='" + ids[i] + "'");
//        }
        activePageService.batchExecuteSql((String[]) list.toArray(new String[list.size()]));
        EmpUtils.log(this, user, "人员减员成功:" + EmpUtils.toString(ids));
    }

    public PersonBO findPerson(String id) throws BkmsException {
        return personService.findPerson(id);
    }

    public String findDissmissType(String personId) throws RollbackableException {
        String sql = "select A016010 from A016 where A016000 ='" + Constants.YES + "' and ID='" + personId + "'";
        PersonBO bo = personService.findPerson(personId);
        String perCode = Tools.filterNull(bo.getPersonCode());
        if (perCode.indexOf("-") > -1) {
            return "编号为" + perCode + "的人员不可恢复";
        }
//        if (blacklistService.isExitByPersonIdCard(bo.getIdCard())) {
//            return "系统黑名单中存在该人员信息，须在黑名单中删除该人员后，方可进行减员撤销操作！";
//        }
//        List list = activePageService.queryForList(sql);
//        if (list != null && !list.isEmpty()) {
//            Map map = (Map) list.get(0);
//            String type = (String) map.get("A016010");
//            if (EmpConstants.DISS_TYPE_CZ.equals(type) || EmpConstants.DISS_TYPE_DC .equals(type)
//                    || EmpConstants.DISS_TYPE_TX .equals(type)) {// 相应减员类别，需要弹出对话框
//                return "true";
//            } else {
//                return "false";
//            }
//        } else {
//            return "false";
//        }
        return "true";
    }

    //减员恢复
    public void updateBackPersonForCzb(User user, RecoverVO vo) throws BkmsException {
        String orgId = vo.getOrgId();
        String deptId = vo.getDeptId();
        String personType = vo.getPersonType();
        String postType = vo.getPostType();
        String perId = vo.getPerId();
        String time = vo.getTime();
        ArrayList list = new ArrayList();
        //修改人员的减员标志并调往相应机构部门
        Org org = SysCacheTool.findOrgById(orgId);
        Org dept = SysCacheTool.findOrgById(deptId);
        String orgTreeId = org.getTreeId();
        String deptTreeId = dept.getTreeId();
        list.add("update A001 set A001728='" + orgTreeId + "',A001738='" + deptTreeId +
                "',A001701='" + orgId + "',A001705='" + deptId + "',A001725='" + EmpConstants.PERSON_STAT_ZG +
                "',A001743='" + dept.getOrgSort() + "',A001745='0999',A001214='" + personType +
                "',A001730 = '" + Constants.NO + "',A001755='" + Constants.NO + "' where ID='" + perId + "'");
        //提交页面人员增加子集的信息全为空，不执行updateA016方法  当是减员人员再入职进行撤销恢复时，系统保留原工作经历子集及增减信息子集中因撤销产生的两条记录，同时在两子集中产生两条新的增员记录
        if (!"".equals(Tools.filterNull(vo.getA016010())) || !"".equals(Tools.filterNull(vo.getA016020())) ||
                !"".equals(Tools.filterNull(vo.getA016030())) || !"".equals(Tools.filterNull(vo.getA016040()))) {
            vo.setA016040(time);
            this.addA016(activePageService, user, vo, perId);
        } else {
            //删除增减情况子集    当是减员误操作进行减员撤销恢复人员时，系统将工作经历子集及增减信息子集中因撤销产生的两条记录自动删除。
            list.add("delete A016  where A016000 = '" + Constants.YES + "' and ID='" + perId + "'");
            //修改上一条记录为当前纪录
            String sql = "select SUBID from A016 where ID = '" + perId + "' and A016000 = '" + Constants.NO + "' order by A016020 desc";
            String subid = personService.getPersonDAO().queryForString(sql);
            if (subid != null && !subid.equals("")) {
                list.add("update A016  set A016000 = '" + Constants.YES + "' where SUBID='" + subid + "'");
            }
        }
        PersonBO bo = personService.findPerson(perId);
//        if (bo != null) {//在原机构备份人员信息
//            if (!bo.getOrgId().equals(orgId)) {
//                IAdjustUCC ucc = (IAdjustUCC) BkmsContext.getBean("emp_adjustUCC");
//                String newId = SequenceGenerator.getKeyId("A001");
//                ucc.abatchCopyInfoSet("A001", newId, perId);
//            }
//        }
        //新增一条简历 ,修改当前记录
        list.add("update A019 set A019010='" + (time == null ? "" : time.substring(0, 7)) + "' where ID='" + perId + "' and A019000='" + Constants.YES + "'");
        list.add("update A019 set A019000='" + Constants.NO + "' where ID='" + perId + "'");
        list.add("insert into A019(A019005,A019015,A019020,ID,SUBID,A019000) values('" + (time == null ? "" : time.substring(0, 7)) + "','" + SysCacheTool.interpretOrg(orgId) +
                "','" + SysCacheTool.interpretOrg(deptId) + "','" + perId + "','" + SequenceGenerator.getUUID() + "','" + Constants.YES + "')");
        if (list.size() > 0) {
            activePageService.batchExecuteSql((String[]) list.toArray(new String[list.size()]));
        }
        EmpUtils.log(this, user, "人员减员撤销成功:" + perId);
    }

    public void updateBackPerson(User user, String[] ids) throws RollbackableException {
        ArrayList list = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            PersonBO bo = personService.findPerson(ids[i]);
//            String perCode = Tools.filterNull(bo.getPersonCode());
//            if (perCode.indexOf("-") > -1) {
//                throw new RollbackableException("编号为" + perCode + "的人员不可恢复", this.getClass());
//            }
            //修改人员的减员标志
            list.add("update A001 set A001730 = '" + Constants.NO + "',A001755='" + Constants.NO + "' where ID='" + ids[i] + "'");
//                String party = Tools.filterNull(bo.getPartyFigure());
            //删除增减情况子集
            list.add("delete A016  where A016000 = '" + Constants.YES + "' and ID='" + ids[i] + "'");
            //修改上一条记录为当前纪录
            String sql = "select SUBID from A016 where ID = '" + ids[i] + "' and A016000 = '" + Constants.NO + "' order by A016020 desc";
            String subid = personService.getPersonDAO().queryForString(sql);
            if (subid != null && !subid.equals("")) {
                list.add("update A016  set A016000 = '" + Constants.YES + "' where SUBID='" + subid + "'");
            }
            //简历最后一条记录补上
            if (!(("013523".equals(bo.getPersonType()) || "013521".equals(bo.getPersonType()) || "013522".equals(bo.getPersonType()) || "013525".equals(bo.getPersonType())) && Constants.YES.equals(bo.getPersonCancel()))) {
                list.add("update A019 SET A019010 = '' WHERE A019000='" + Constants.YES + "' AND ID = '" + ids[i] + "'");
            }
        }
        if (list.size() > 0) {
            activePageService.batchExecuteSql((String[]) list.toArray(new String[list.size()]));
        }
        EmpUtils.log(this, user, "人员减员撤销成功:" + EmpUtils.toString(ids));
    }

    public void deletePerson(String[] ids, User user) throws BkmsException {
        InfoSetBO setBo = SysCacheTool.findInfoSet("A001");
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            activePageService.deleteMainRecord(user, setBo, id, true, true);
        }
        EmpUtils.log(this, user, "删除人员成功:" + EmpUtils.toString(ids));
    }

    public void updateRetireDismissPerson(User user, PersonChangeVO personchangevo, String[] ids) throws BkmsException {

        String changeType = Tools.filterNull(personchangevo.getChangeType());
        if ("".equals(changeType)) {
            return;
        } else {
            changeType = changeType.substring(4, changeType.length());
        }
        ArrayList list = new ArrayList();
        if (ids != null && ids.length > 0) {
            if ("211301".equals(changeType)) {//死亡
                List l = new ArrayList();
                for (int i = 0; i < ids.length; i++) {
                    PersonBO bo = personService.findPerson(ids[i]);
                    String party = Tools.filterNull(bo.getPartyFigure());
                    if (party.equals("012001") || party.equals("012002")) {//党员
                        l.add(ids[i]);
                    }
                }
                if (l.size() > 0) {
                    //修改党员的‘离开中共组织时间’为‘人员减员时间’，‘离开中共组织类型’为‘其他死亡’
                    list.add(new StringBuffer().append("update A818 set A818714 = '").append(personchangevo.getChangeDate()).append("',A818720='029013' where  ").append(Tools.splitInSql((String[]) l.toArray(new String[l.size()]), "ID")).toString());
                }
            }
            //修改人员的减员标志
            list.add("update A001 set A001730 = '" + Constants.YES + "' where  " + Tools.splitInSql(ids, "ID"));
            //增加增减情况子集
            list.add("update A016 set A016000 = '" + Constants.NO + "' where A016000 = '" + Constants.YES + "' and  " + Tools.splitInSql(ids, "ID"));

            for (int i = 0; i < ids.length; i++) {
                list.add("INSERT INTO A016 (ID,SUBID,A016000,A016010,A016020,A016030,A016040,A016045) values ('" + ids[i] + "','" + Tools.filterNull(SequenceGenerator.getKeyId("A016")) + "','" + Constants.YES + "','" + Tools.filterNull(personchangevo.getChangeType()) + "'," +
                        "'" + Tools.filterNull(personchangevo.getChangeDate()) + "','" + Tools.filterNull(personchangevo.getChangeUnit()) + "','" + Tools.filterNull(personchangevo.getTractDate()) + "'," +
                        "'" + Tools.filterNull(personchangevo.getTractPerson()) + "')");
            }
        }
        if (list.size() > 0) {
            activePageService.batchExecuteSql((String[]) list.toArray(new String[list.size()]));
        }
        EmpUtils.log(this, user, "离退休人员减员成功:" + EmpUtils.toString(ids));
    }

    public TableVO queryDismissPer(User user, String name, String dismissType, String disstartDate, String disendDate, String superId, PageVO page) throws BkmsException {
        StringBuffer where = new StringBuffer();
        if (superId != null && !"".equals(superId) && !"null".equals(superId)) {
            where.append(" and A001.A001738 like '").append(SysCacheTool.findOrgById(superId).getTreeId()).append("%'");
        }
        if (name != null && !"".equals(name.trim())) {
            where.append(" and A001.A001001 LIKE '%").append(name).append("%'");
        }
        if (dismissType != null && !"".equals(dismissType.trim())) {
            String[] pers = dismissType.split(",");
            where.append(" and ").append(Tools.splitInSql(pers, "A016.A016010"));
        }
        if (disstartDate != null && !"".equals(disstartDate.trim())) {
            where.append(" and A016.A016020 >= '").append(disstartDate).append("'");
        }
        if (disendDate != null && !"".equals(disendDate.trim())) {
            where.append(" and A016.A016020 <= '").append(disendDate).append("'");
        }
        where.append(" and (A001.A001730 = '" + Constants.YES + "' or A001755 ='" + Constants.YES + "')");
        PmsAPI pmsAPI = new PmsAPI();
        String pmsApiSql = pmsAPI.getPersonScaleCondition(user, true);
        if (pmsApiSql != null && !pmsApiSql.trim().equals("")) {
            if (pmsApiSql.toLowerCase().trim().startsWith("and")) {
                where.append(pmsApiSql);
            } else {
                where.append(" and ").append(pmsApiSql);
            }
        }
        String sql = "select A001.ID,A001.A001001,A001.A001735,A001.A001007,A001.A001011,A001.A001701,A001.A001705,A001054,A001.A001201 from A001 where 1=1 " + where;
        return queryAdvanceBySQL(user, EmpConstants.DEFAULT_QUERY_PERSON, sql, page);
    }


    public TableVO findCurRecord(User user, String setId, String[] fks) throws BkmsException {
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        try {
            TableVO table = new TableVO();
            InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(set.getSetId()).toArray(new InfoItemBO[SysCacheTool.queryInfoItemBySetId(set.getSetId()).size()]);
            String[][] rows = activePageService.queryCurRecord(set, header, fks);
            table.setInfoSet(set);
            table.setHeader(header);
//            id.setItemProperty(SysConstants.INFO_ITEM_PROPERTY_CONTROL_SHOW);
            if (!"A001".equals(set.getSetId())) { //非人员基本信息子集，列表显示人员姓名update by huangh on 2015-7-03
                int num = table.getCol("ID");
                InfoItemBO id = header[num];
                id.setEditProp(SysConstants.INFO_ITEM_EDIT_PROP_READONLY);
                if (rows != null && rows.length > 0) {
                    for (int i = 0; i < rows.length; i++) {
                        rows[i][num] = SysCacheTool.interpretPerson(rows[i][num]);
                    }
                }
            }
            table.setRows(rows);
            PmsAPI pms = new PmsAPI();
            pms.checkPersonRecord(user, table);
            table.setTableRight(pms.checkTable(user, setId));//add by yxm on 2015-3-25
            return table;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public int queryResultCount(String s) throws BkmsException {
        return personService.queryResultCount(s);
    }

    public PersonBO[] queryPerson(String s) throws BkmsException {
        return personService.queryPerson(s);
    }

    public PersonBO[] queryPersonBySuper(String superId, String flag) throws BkmsException {
        return personService.queryPersonBySuper(superId, flag);
    }

    public void updatePersonSort(User user, PersonBO[] persons) throws BkmsException {
        if (persons == null) {
            return;
        }
        String[] sql = new String[persons.length + 1];
        for (int i = 0; i < persons.length; i++) {
            //修改新排序的机构排序号
            sql[i] = "UPDATE A001 SET A001745 = '" + Tools.filterNull(persons[i].getSort()) + "' WHERE ID='" + persons[i].getPersonId() + "'";
        }
        sql[persons.length] = "UPDATE A001 SET A001745 = '0999' WHERE A001730 = '" + Constants.YES + "' and A001705 = (SELECT A001705 FROM A001 WHERE ID = '" + persons[0].getPersonId() + "')";
        activePageService.batchExecuteSql(sql);
//        EmpUtils.log(this, user, "人员排序更新成功:" + EmpUtils.toString(persons));
        EmpUtils.log(this, user, "人员排序更新成功");
    }

//    public UnionBO queryPerLabourByDept(String deptId) throws BkmsException {
//        try {
//            OrgBO bo = orgService.findOrgBO(deptId);
//            String superId = bo.getSuperId();
//            UnionBO unionBo = getUnionBOByDeptId(deptId);
//            if (unionBo != null) {
//                return unionBo;
//            }
//            if (Integer.parseInt(superId) != -1) {
//                return this.queryPerLabourByDept(superId);
//            } else {
//                return unionService.findUnionById("1000000");//找不到所在的党组织，则放在全行党委
//            }
//        } catch (Exception e) {
//            throw new BkmsException("查询失败", e, this.getClass());
//        }
//    }

//    private UnionBO getUnionBOByDeptId(String deptId) {
//        String hql = "from UnionBO p where p.labourMember like '%" + deptId + "%' order by p.labourSort";
//        List list = orgService.getOrgDAO().getHibernateTemplate().find(hql);
//        return (list != null && list.size() > 0) ? (UnionBO) list.get(0) : null;
//    }

    public List queryMultiPerson(String pid) throws RollbackableException {
        return personService.queryMultiPerson(pid);
    }


    public PersonBO[] queryAllPerson(String whereSql) throws RollbackableException {
        return personService.queryAllPerson(whereSql);
    }


    private TableVO queryAdvanceBySQL(User user, String queryId, String sql, PageVO page) throws BkmsException {
        IQuery api = (IQuery) BkmsContext.getBean("qry_queryAPI");
        Hashtable sqlHash = api.findSQL(queryId);
        //调用高级查询的接口，获得拼好的sql语句各块
        String select = (String) sqlHash.get(QryConstants.QRYSQL_showField);
        InfoItemBO[] header = getHeader(select);
        String righField = Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_PERSON));

        page.setQrySql(sql);
        TableVO table = activePageService.queryListBySql(sql, header, righField, page, true);
        table.setRightItem(righField);
        PmsAPI pms = new PmsAPI();
        pms.checkPersonRecord(user, table);
        return table;
    }

    public TableVO queryAdvanceByRight(User user, String queryId, String addWhere, PageVO page, boolean right) throws BkmsException {
        if (queryId == null || queryId.trim().equals("")) {
            queryId = EmpConstants.DEFAULT_QUERY_PERSON;
        }
        IQuery api = (IQuery) BkmsContext.getBean("qry_queryAPI");
        Hashtable sqlHash = api.findSQL(queryId);
        //调用高级查询的接口，获得拼好的sql语句各块
        String select = (String) sqlHash.get(QryConstants.QRYSQL_showField);
        String from = (String) sqlHash.get(QryConstants.QRYSQL_from);
        String where = (String) sqlHash.get(QryConstants.QRYSQL_where);
        String order = (String) sqlHash.get(QryConstants.QRYSQL_order);
//        String scale = (String) sqlHash.get(QryConstants.SQL_SCALE_PART);
        InfoItemBO[] header = getHeader(select);
        String sql = "select " + select + " from " + from;

        //转换显示项
        StringBuffer showItem = new StringBuffer();
        for (int i = 0; i < header.length; i++) {
            showItem.append(",").append(header[i].getSetId()).append(".").append(header[i].getItemId());
        }
        String righField = null;
        if (right) {
            //拼接权限字段
            righField = Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_PERSON));
            sql = addSqlSelect(sql, righField);
        }
        //where 子句
        sql += (" where 1=1 " + ("".equals(where) ? "" : " and " + where));

        if (right) {
            PmsAPI pmsAPI = new PmsAPI();
            String pmsApiSql = pmsAPI.getPersonScaleCondition(user, false);
//        sql += " and " + pmsApiSql;
//        //scale 子句

            if (pmsApiSql != null && !pmsApiSql.trim().equals("")) {
                if (pmsApiSql.toLowerCase().trim().startsWith("and")) {
                    sql += pmsApiSql;
                } else {
                    sql += " and " + pmsApiSql;
                }
            }
        }
        //拼接自己的条件
        sql += addWhere;

        sql += ("".equals(order) ? "" : " order by " + order);
        page.setQrySql(sql);
        page.setShowField(showItem.length() > 0 ? showItem.substring(1) : showItem.toString());
        TableVO table = activePageService.queryListBySql(sql, header, righField, page, true);
        table.setRightItem(righField);
        PmsAPI pms = new PmsAPI();
        pms.checkPersonRecord(user, table);
        return table;
    }

    //默认带权限查询
    private TableVO queryAdvance(User user, String queryId, String addWhere, PageVO page) throws BkmsException {
        return queryAdvanceByRight(user, queryId, addWhere, page, true);
    }

    public static InfoItemBO[] getHeader(String SelectSql) {
        if (SelectSql == null) return null;
        String[] select = StringUtils.split(SelectSql, ",");
        InfoItemBO[] vos = new InfoItemBO[select.length];
        String set[] = StringUtils.split(select[0], ".");    //A001.ID
        InfoItemBO b = SysCacheTool.findInfoItem(set[0], set[1]);
        InfoItemBO v = new InfoItemBO();
        Tools.copyProperties(v, b);
        vos[0] = v;
        int j = 1;
        for (int i = 1; i < select.length; i++) {
            String s1 = select[i];
            String sets[] = StringUtils.split(select[i].trim(), ".");    //A001.ID
            InfoItemBO bo;
            if (sets != null && sets.length == 2)
                bo = SysCacheTool.findInfoItem(sets[0], sets[1]);
            else
                bo = SysCacheTool.findInfoItem(null, s1.trim());
            if (bo != null) {
                v = new InfoItemBO();
                Tools.copyProperties(v, bo);
                v.setShowId(true);
                vos[j] = v;
                j++;
            }
        }
        return (InfoItemBO[]) ArrayUtils.subarray(vos, 0, j);
    }


    /**
     * 拼加权限字段(为了防止sql语句的select项里字段重复)
     *
     * @param sql        拼好的sql语句
     * @param rightField 权限需要的字段
     * @return SQL select 子句
     */
    public static String addSqlSelect(String sql, String rightField) {
        if (sql == null) return "";
        if (rightField == null || rightField.length() == 0) return sql;
        if (sql.toLowerCase().indexOf(rightField.toLowerCase()) < 0) {
            if (rightField.startsWith(",")) rightField = rightField.substring(1);
            if (rightField.endsWith(",")) rightField = rightField.substring(0, rightField.lastIndexOf(","));
            int fromIndex = sql.toLowerCase().indexOf(" from ");
            String select1 = sql.substring(0, fromIndex);
            String from1 = sql.substring(fromIndex);
            String[] fs = rightField.split(",");
            sql = select1;
            for (int i = 0; i < fs.length; i++) {
                if (fs[i].trim().length() < 1) continue;
                if ((select1.toLowerCase().indexOf(fs[i].trim().toLowerCase())) < 0)
                    sql += "," + fs[i];
            }
            sql += from1;
        }
        return sql;
    }

    public List queryPersonCodeByCode(String code) throws BkmsException {
        return getPersonCodeService().queryPersonCodeByCode(code);
    }
}

