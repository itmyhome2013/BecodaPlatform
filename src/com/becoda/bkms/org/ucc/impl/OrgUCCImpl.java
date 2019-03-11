package com.becoda.bkms.org.ucc.impl;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.ucc.impl.PersonUCCImpl;
import com.becoda.bkms.org.OrgConstants;
//import com.becoda.bkms.org.pojo.bo.BakTimeBO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.pojo.bo.OrgBO;
//import com.becoda.bkms.org.pojo.vo.BakTimeVO;
//import com.becoda.bkms.org.pojo.vo.OrgChangeVO;
import com.becoda.bkms.org.pojo.vo.OrgSetVO;
import com.becoda.bkms.org.pojo.vo.OrgVO;
//import com.becoda.bkms.org.service.OrgHisService;
import com.becoda.bkms.org.service.OrgService;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.api.PmsAPI;
//import com.becoda.bkms.post.pojo.bo.PostBO;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.api.IQuery;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.*;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-21
 * Time: 13:48:14
 */
public class OrgUCCImpl implements IOrgUCC {
    private OrgService orgService;
    private ActivePageService activePageService;


    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

    public OrgService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }

    public OrgBO findOrgBO(String orgId) throws BkmsException {
        return orgService.findOrgBO(orgId);
    }


    public void checkOrgSubValidOrg(String[] ids) throws BkmsException {
        orgService.checkOrgSubValidOrg(ids);
    }

    public void checkOrgSubOrg(String[] ids) throws BkmsException {
        orgService.checkOrgSubOrg(ids);
    }

    public void checkPerByOrg(String[] ids) throws BkmsException {
        orgService.checkPerByOrg(ids);
    }

    public void checkPostByOrg(String[] ids) throws BkmsException {
        orgService.checkPostByOrg(ids);
    }

    public OrgBO[] queryAllOrgBySuper(String superTreeId) throws BkmsException {
        return orgService.queryAllOrgBySuper(superTreeId);
    }

    public OrgBO[] queryAllOrgBySuperSelf(String superTreeId) throws BkmsException {
        return orgService.queryAllOrgBySuperSelf(superTreeId);
    }

    public OrgBO[] queryAllOrgBySuper(String superTreeId, String cancel) throws BkmsException {
        return orgService.queryAllOrgBySuper(superTreeId, cancel);
    }

    public OrgBO[] queryAllOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException {
        return orgService.queryAllOrgBySuperSelf(superTreeId, cancel);
    }

    public int queryOrgCodeCount(String orgCode, String orgId) throws BkmsException {
        return orgService.queryOrgCodeCount(orgCode, orgId);
    }

    public List queryOrgName(String superId, String newOrgName) throws BkmsException {
        return orgService.queryOrgName(superId, newOrgName);
    }

//    public PostBO[] queryPostByOrg(String orgId) throws BkmsException {
//        return orgService.queryPostByOrg(orgId);
//    }

    public OrgBO findOrgByTreeId(String treeId) throws BkmsException {
        return orgService.findOrgByTreeId(treeId);
    }

    public String createOrg(User user, String setId, OrgVO orgVO) throws RollbackableException {
        try {
            //得到新增机构的treeId,机构隶属码和机构排序
            String treeId = "";
            String sort = "";
            OrgBO superOrg = orgService.findOrgBO(orgVO.getSuperId());
            if (superOrg != null) {
                treeId = SequenceGenerator.getTreeId("B001", "B001003", superOrg.getTreeId(), 3, 1);
                sort = SequenceGenerator.getTreeId("B001", "B001715", superOrg.getOrgSort(), 3, 1);
            }
            //记录插入机构基本信息子集
            Map data = new HashMap();
            String[] infoItems = new String[]{"B001005", "B001002", "B001050", "B001010", "B001225", "B001208", "B001255",
                    "B001003", "B001715", "B001730", "B001203",
                    "B001240", "B001200", "B001214",
                    "B001050", "B001203",
                    "B001212"};
            String[] ietmValues = new String[]{Tools.filterNull(orgVO.getOrgName()), Tools.filterNull(orgVO.getSuperId()),
                    Tools.filterNull(orgVO.getOrgClass()),
                    Tools.filterNull(orgVO.getOrgCode()),
                    Tools.filterNull(orgVO.getOrgAddress()),
                    Tools.filterNull(orgVO.getPhone()), Tools.filterNull(orgVO.getOrgAllName()),
                    Tools.filterNull(treeId), Tools.filterNull(sort), Constants.NO, Tools.filterNull(orgVO.getNature()),
                    Tools.filterNull(orgVO.getPostCode()),
                    Tools.filterNull(orgVO.getOffice()),
                    Tools.filterNull(orgVO.getOrgArea()),
                    Tools.filterNull(orgVO.getOrgLevel()), Tools.filterNull(orgVO.getFactPer()),
                    Tools.filterNull(orgVO.getFax())};
            //将数据装入到MAP
            for (int i = 0; i < ietmValues.length; i++) {
                data.put(infoItems[i], ietmValues[i]);
            }
            String orgId = activePageService.addRecord(user, "B001", data, false, false);
            //更新机构设立信息
            String[] infoItems1 = new String[]{"ID", "B004005", "B004010", "B004208", "B004202",
                    "B004209", "B004211", "B004212", "B004203", "B004204", "B004210"};
            String[] ietmValues1 = new String[]{orgId, Tools.filterNull(orgVO.getDeptSetupTime()),
                    Tools.filterNull(orgVO.getDeptSetupNo()),
                    Tools.filterNull(orgVO.getOrgTempNo()),
                    Tools.filterNull(orgVO.getOrgTempTime()),

                    Tools.filterNull(orgVO.getAgreeNo()),
                    Tools.filterNull(orgVO.getAgreeDate()),
                    Tools.filterNull(orgVO.getWorkingTime()),
                    Tools.filterNull(orgVO.getLicenceTime()),
                    Tools.filterNull(orgVO.getStartTime()),
                    Tools.filterNull(orgVO.getScmemo()),
            };
            Map orgSetupData = new HashMap();
            for (int i = 0; i < ietmValues1.length; i++) {
                orgSetupData.put(infoItems1[i], ietmValues1[i]);
            }
            activePageService.updateMapRecord(user, "B004", orgSetupData, true, true);
//            SysCache.setMap(new String[]{orgId}, CacheConstants.OPER_ADD, CacheConstants.OBJ_ORG);
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "增加机构");
            return orgId;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new RollbackableException("", e, this.getClass());
        }
    }

    public TableVO queryOrgList(User user, PageVO page, String orgName, String superId, String orgLevel, String cancel, String forom) throws BkmsException {
        IQuery queryApi = (IQuery) BkmsContext.getBean("qry_queryAPI");
        Hashtable sqlHash = queryApi.findSQL(OrgConstants.DEFAULT_QUERY_ORG);
        //调用高级查询的接口，获得拼好的sql语句各块
        String select = (String) sqlHash.get(QryConstants.QRYSQL_showField);
        String from = (String) sqlHash.get(QryConstants.QRYSQL_from);
//        String where1 = (String) sqlHash.get(QryConstants.QRYSQL_where);
        String where1 = "";
        String order = (String) sqlHash.get(QryConstants.QRYSQL_order);
//        String scale = (String) sqlHash.get(QryConstants.SQL_SCALE_PART);
        InfoItemBO[] header = PersonUCCImpl.getHeader(select);
        String sql = "select " + select + " from " + from;

        StringBuffer where = new StringBuffer(where1);
        if (orgName != null && !"".equals(orgName)) {
            where.append(" and B001.B001005 LIKE '%").append(orgName).append("%'");
        }

        if (superId != null && !"".equals(superId)) {
            Org bo = SysCacheTool.findOrgById(superId);
            if (bo != null) {
                where.append(" and B001.B001003 LIKE '").append(bo.getTreeId()).append("%'");
//                if (orgName == null && orgLevel == null && orgType == null) {
//                    where.append(" and length(B001.B001003)<=(length('").append(bo.getTreeId()).append("')+3)");
//                }
            }
        } else {
            where.append(" and B001.B001002 = '101'");
        }
        if (orgLevel != null && !"".equals(orgLevel)) {
            where.append(" and B001.B001050 ='").append(orgLevel).append("'");
        }

        if (Constants.YES.equals(cancel)) {
            where.append("and B001730 = '").append(Constants.YES).append("'");
        } else {
            where.append("and B001730 = '").append(Constants.NO).append("'");
        }
        //增加查询范围权限
        PmsAPI api = new PmsAPI();
        String scaleSql = api.getOrgScaleCondition(user, "B001.B001003", false);
        if (scaleSql != null && !scaleSql.trim().equals("")) {
            if (scaleSql.toLowerCase().trim().startsWith("and")) {
                where.append(scaleSql);
            } else {
                where.append(" and ").append(scaleSql);
            }
        }
//        where.append(" and ").append(scaleSql);
        String showItem = "";
        for (int i = 0; i < header.length; i++) {
            showItem += "," + header[i].getSetId() + "." + header[i].getItemId();
        }
        if (!showItem.equals("")) {
            showItem = showItem.substring(1);
        }
        String righField = Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_ORG));
        sql = PersonUCCImpl.addSqlSelect(sql, righField);
        //where 子句
        sql += (" where 1=1 " + where);
//        sql += (" where 1=1 " + ("".equals(where) ? "" : " and " + where));

        sql += ("".equals(order) ? "" : " order by " + order);
        page.setQrySql(sql);
        page.setShowField(showItem);

        TableVO table = activePageService.queryListBySql(sql, header, righField, page, true);
        table.setRightItem(righField);
        PmsAPI pms = new PmsAPI();
        pms.checkOrgRecord(user, table);
        table.setTableRight(pms.checkTable(user, "B001"));
        return table;
    }

    public TableVO queryOrgList(User user, PageVO page, String orgName, String superId, String orgType, String orgLevel, String cancel, String forom) throws BkmsException {
        //wanlj
        IQuery queryApi = (IQuery) BkmsContext.getBean("qry_queryAPI");
        Hashtable sqlHash = queryApi.findSQL(OrgConstants.DEFAULT_QUERY_ORG);
        //调用高级查询的接口，获得拼好的sql语句各块
        String select = (String) sqlHash.get(QryConstants.QRYSQL_showField);
        String from = (String) sqlHash.get(QryConstants.QRYSQL_from);
//        String where1 = (String) sqlHash.get(QryConstants.QRYSQL_where);
        String where1 = "";
        String order = (String) sqlHash.get(QryConstants.QRYSQL_order);
//        String scale = (String) sqlHash.get(QryConstants.SQL_SCALE_PART);
        InfoItemBO[] header = PersonUCCImpl.getHeader(select);
        String sql = "select " + select + " from " + from;

        StringBuffer where = new StringBuffer(where1);
        if (orgName != null && !"".equals(orgName)) {
            where.append(" and B001.B001005 LIKE '%").append(orgName).append("%'");
        }

        if (superId != null && !"".equals(superId)) {
            Org bo = SysCacheTool.findOrgById(superId);
            if (bo != null) {
                where.append(" and B001.B001003 LIKE '").append(bo.getTreeId()).append("%'");
                if ((orgLevel == null || "".equals(orgLevel)) &&
                        (orgName == null || "".equals(orgName)) &&
                        (orgType == null || "".equals(orgType)) && (Constants.NO.equals(cancel))) {//查询下一级机构,撤销机构查询全部
                    where.append(" and length(B001.B001003)<=(length('").append(bo.getTreeId()).append("')+3)");
                }
            }
        } else {
            where.append(" and B001.B001002 = '101'");
        }
        if (orgLevel != null && !"".equals(orgLevel)) {
            where.append(" and B001.B001050 ='").append(orgLevel).append("'");
        }
        if (Constants.YES.equals(cancel)) {
            where.append("and B001730 = '").append(Constants.YES).append("'");
        } else {
            where.append("and B001730 = '").append(Constants.NO).append("'");
        }
        //增加查询范围权限
        PmsAPI api = new PmsAPI();
        String scaleSql = api.getOrgScaleCondition(user, "B001.B001003", false);
        if (scaleSql != null && !scaleSql.trim().equals("")) {
            if (scaleSql.toLowerCase().trim().startsWith("and")) {
                where.append(scaleSql);
            } else {
                where.append(" and ").append(scaleSql);
            }

        }
//        where.append(" and ").append(scaleSql);
        String showItem = "";
        for (int i = 0; i < header.length; i++) {
            showItem += "," + header[i].getSetId() + "." + header[i].getItemId();
        }
        if (!showItem.equals("")) {
            showItem = showItem.substring(1);
        }
        String righField = Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_ORG));
        sql = PersonUCCImpl.addSqlSelect(sql, righField);
        //where 子句
        sql += (" where 1=1 " + where);
//        sql += (" where 1=1 " + ("".equals(where) ? "" : " and " + where));

        sql += ("".equals(order) ? "" : " order by " + order);
        page.setQrySql(sql);
        page.setShowField(showItem);

        TableVO table = activePageService.queryListBySql(sql, header, righField, page, true);
        table.setRightItem(righField);
        PmsAPI pms = new PmsAPI();
        pms.checkOrgRecord(user, table);
        table.setTableRight(pms.checkTable(user, "B001"));
        return table;
    }

    public String queryOrgList(TableVO table, String orgName, String superId, String orgType, int pageNum, int rowNum, String cancel, User user, String flag, String queryId) throws BkmsException {
        return orgService.queryOrgList(table, orgName, superId, orgType, pageNum, rowNum, cancel, user, flag, queryId);
    }

    public void updateDismissOrg(OrgSetVO orgsetvo, String[] ids, User user) throws BkmsException {
        try {
            //检查是否可以撤销--如果不可以，由抛出异常
//            checkOrgSubValidOrg(ids);
            checkPerByOrg(ids);
//            checkPostByOrg(ids);
//            checkWageUnitByOrg(ids);
            String[] idsNew = orgService.querySubOrg(ids);
            orgService.updateDismissOrg(orgsetvo, idsNew);
//            orgService.updateDismissOrgByTree(orgsetvo, ids);
            if (idsNew != null && idsNew.length > 0) {
                for (int i = 0; i < idsNew.length; i++) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorName(user.getName());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperInfosetId("B004");
                    bo.setOperType("modify");
                    bo.setOperDesc("机构撤销");
                    bo.setOperPersonId(idsNew[i]);
                    bo.setOperInfosetName("机构设立撤销信息集");
                    bo.setOperInfoitemName("撤销类别,撤销时间,机构撤销监管部门批准文号,机构撤销行内批准文号");
                    String desc =SysCacheTool.interpretCode(orgsetvo.getCancelType())+"，"+orgsetvo.getCancelTime()+"，"+orgsetvo.getCancelUnit()+"，"+orgsetvo.getCancelNo();
                    bo.setOperDescSuf(desc);
                    bo.setOperValueSuf(desc);
                    HrmsLog.addOperLog(this.getClass(), bo);
                }
            }
//            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "机构撤销");
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException(e.getMessage(), e, this.getClass());
        }
    }

    private void checkWageUnitByOrg(String[] ids) throws BkmsException {
        orgService.checkWageUnitByOrg(ids);
    }

    public void updateBackOrg(String[] ids, User user) throws BkmsException {
        try {
            orgService.updateBackOrg(ids);
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorName(user.getName());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperInfosetId("B004");
                    bo.setOperType("modify");
                    bo.setOperDesc("机构恢复");
                    bo.setOperPersonId(ids[i]);
                    bo.setOperInfosetName("机构设立撤销信息集");
                    HrmsLog.addOperLog(this.getClass(), bo);
                }
            }
//            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "机构恢复");
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException(e.getMessage(), e, this.getClass());
        }
    }

    public void deleteOrg(String[] ids, User user) throws BkmsException {
        try {
            orgService.deleteOrg(ids, user);
            this.evictQuary();
            SysCache.setMap(ids, CacheConstants.OPER_DELETE, CacheConstants.OBJ_ORG);
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorName(user.getName());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperInfosetId("B004");
                    bo.setOperType("delete");
                    bo.setOperDesc("机构删除");
                    bo.setOperPersonId(ids[i]);
                    HrmsLog.addOperLog(this.getClass(), bo);
                }
            }
//            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "机构删除");
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException(e.getMessage(), e, this.getClass());
        }
    }



    public void updateOrgSort(OrgBO[] orgs, String superId, User user) throws BkmsException {
        try {
            orgService.updateOrgSort(orgs, superId);
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "机构排序");
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException(e.getMessage(), e, this.getClass());
        }
    }

//    public OrgBO findUnitOrgIncludeFunDep(String orgId) throws BkmsException {
//        return orgService.findUnitOrgIncludeFunDep(orgId);
//    }

    public List findOrgByType() throws BkmsException {
        return orgService.findOrgByType();
    }

    public OrgBO[] queryAllOutsideOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException {
        return orgService.queryAllOutsideOrgBySuperSelf(superTreeId, cancel);
    }

    public OrgBO[] queryNextLeveOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException {
        return orgService.queryNextLeveOrgBySuperSelf(superTreeId, cancel);
    }

    public Hashtable queryAllInsideOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException {
        return orgService.queryAllInsideOrgBySuperSelf(superTreeId, cancel);
    }

    public List queryInsideOrgBySuperId(String superId) throws BkmsException {
        return orgService.queryInsideOrgBySuperId(superId);
    }

    public OrgBO[] queryAllOutsideOrgBySuperSelfOrderByLayer(String superTreeId, int layer) throws BkmsException {
        return orgService.queryAllOutsideOrgBySuperSelfOrderByLayer(superTreeId, layer);
    }

    public List queryAllStockOrgBySuperAndLayer(String stockTreeId, int layer) throws BkmsException {
        return orgService.queryAllStockOrgBySuperAndLayer(stockTreeId, layer);
    }

    public List queryAllStockOrgBySuper(String stockTreeId, String cancel) throws BkmsException {
        return orgService.queryAllStockOrgBySuper(stockTreeId, cancel);
    }

//    public Hashtable queryAllInsideOrgBySuperStockSelf(String superStockTreeId, String cancel) throws BkmsException {
//        return orgService.queryAllInsideOrgBySuperStockSelf(superStockTreeId, cancel);
//    }
//
//    public List queryOrgPostNum(String orgTreeId, String cancel) throws BkmsException {
//        return orgService.queryOrgPostNum(orgTreeId, cancel);
//    }

    public TableVO findOrgInfoSetRecord(User user, String setId, String pk) throws BkmsException {
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        try {
            TableVO table = new TableVO();
            List list = SysCacheTool.queryInfoItemBySetId(set.getSetId());
            InfoItemBO[] header = (InfoItemBO[]) list.toArray(new InfoItemBO[list.size()]);
            String[][] rows = null;
            if (pk == null) {
                rows = new String[1][header.length];
                String[] blank = new String[header.length];
                for (int i = 0; i < header.length; i++) {
                    blank[i] = header[i].getItemDefaultValue();
                }
                rows[0] = blank;
            } else {
                String row[] = activePageService.findRecord(set, header, pk);
                rows = new String[1][header.length];
                rows[0] = row;
            }
            table.setInfoSet(set);
            table.setHeader(header);
            table.setRows(rows);

            PmsAPI pms = new PmsAPI();
            pms.checkOrgRecord(user, table);
            table.setTableRight(pms.checkTable(user, setId));//add by yxm on 2015-3-25
            return table;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public TableVO queryOrgInfoSetRecordList(User user, String setId, String fk) throws BkmsException {
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        try {
            TableVO table = new TableVO();
            List l = SysCacheTool.queryInfoItemBySetId(set.getSetId());
            InfoItemBO[] header = (InfoItemBO[]) l.toArray(new InfoItemBO[l.size()]);
            String rows[][] = activePageService.queryRecord(set, header, fk);
            table.setInfoSet(set);
            table.setHeader(header);
            table.setRows(rows);
            PmsAPI pms = new PmsAPI();
            pms.checkOrgRecord(user, table);
            table.setTableRight(pms.checkTable(user, setId));
            return table;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public TableVO getBlankOrgInfoSetRecord(User user, String setId, String fk) throws BkmsException {
        try {
            TableVO table = activePageService.getBlankInfoSetRecord(setId, null, fk);
            PmsAPI pms = new PmsAPI();
            pms.checkOrgRecord(user, table);
            table.setColRight(pms.checkHeader(user, table.getHeader()));
            table.setTableRight(pms.checkTable(user, setId));
            return table;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public void deleteOrgInfoSetRecord(User user, String setId, String[] pk, String fk) throws BkmsException {
        try {
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            activePageService.deleteSubRecord(user, set, pk, fk, true, true);
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "删除机构其它子集记录");
            if (setId.equalsIgnoreCase("B001")) {
                SysCache.setMap(pk, CacheConstants.OPER_DELETE, CacheConstants.OBJ_ORG);
            }
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public void updateOrgInfoSetRecord(User user, String setId, Map map, String pk) throws BkmsException {
        try {
            activePageService.updateMapRecord(user, setId, map, true, true);
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "修改机构信息");
            if (setId.equalsIgnoreCase("B001")) {
                SysCache.setMap(new String[]{pk}, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_ORG);
            }
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public void addOrgInfoSetRecord(User user, String setId, Map parameterMap) throws BkmsException {
        try {
            String pk = activePageService.addRecord(user, setId, parameterMap, true, true);
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), OrgConstants.MODULE_NAME, "增加机构信息");
            if (setId.equalsIgnoreCase("B001")) {
                SysCache.setMap(new String[]{pk}, CacheConstants.OPER_ADD, CacheConstants.OBJ_ORG);
            }
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public String findOrgByDeptName(String deptId) throws BkmsException {
        return orgService.findOrgByDeptName(deptId);
    }

    public OrgBO findOrgByDept(String deptId) throws BkmsException {
        return orgService.findOrgByDept(deptId);
    }

    public void evictQuary() throws BkmsException {
        HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
        ht.getSessionFactory().evictQueries();
    }

    public void evictEntity(String clazz) throws BkmsException {
        HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
        ht.getSessionFactory().evictEntity(clazz);
    }

    //根据父id分页查询
	public List queryOrgBysuperId(PageVO vo, String superid)
			throws BkmsException {
		return orgService.queryOrgBysuperId(vo, superid);
	}
}
