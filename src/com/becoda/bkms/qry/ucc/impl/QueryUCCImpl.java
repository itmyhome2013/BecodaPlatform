package com.becoda.bkms.qry.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.ucc.impl.EmpInfoManageUCCImpl;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticResultVO;
import com.becoda.bkms.qry.service.QueryService;
import com.becoda.bkms.qry.service.StaticService;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.qry.util.QryTools;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Hashtable;
import java.util.List;


public class QueryUCCImpl implements IQueryUCC {
    private ActivePageService activePageService;
    private StaticService staticService;


    public StaticService getStaticService() {
        return staticService;
    }

    public void setStaticService(StaticService staticService) {
        this.staticService = staticService;
    }

    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

    /**
     * 注入QueryService
     */
    private QueryService queryservice;

    public void createTmpId(String[] ids) throws BkmsException {

    }

    /**
     * 根据传入的qryId数组删除多个查询 <BR>
     * 需要删除staticbo querybo queryitembo conditionbo
     *
     * @param qryIds
     */
    public void deleteQuery(String[] qryIds) throws BkmsException {
        if (qryIds == null || qryIds.length <= 0)
            return;
        for (int i = 0; i < qryIds.length; i++)
            queryservice.deleteQuery(qryIds[i]);
    }

    /**
     * 根据vo创建一个查询 vo中包含querybo staticbo queryitembo,conditionbo,<BR>
     * 创建前需要判断是否有qryid,若有调用deleteQuery方法.
     *
     * @param vo
     * @return qryid
     */
    public QueryVO createQuery(QueryVO vo) throws BkmsException {
        return queryservice.createQuery(vo);
    }

    public QueryVO createStatic(QueryVO vo) throws BkmsException {
        return queryservice.createStatic(vo);
    }

    /**
     * 根据classId查询下面挂的查询定义.
     *
     * @param classId
     * @return QueryBO[]
     */
    public QueryBO[] queryQuery(String classId) throws BkmsException {
        return queryservice.queryQuery(classId);
    }

    /**
     * 根据qryId从数据库中查询各个bo 组装成queryvo
     *
     * @param qryId 查询定义id
     * @return QueryVO 返回组装了各个bo的vo对象
     */
    public QueryVO findQueryVO(String qryId) throws BkmsException {
        return queryservice.findQueryVO(qryId);
    }

    public QueryVO findQryWageVO(String qryId) throws BkmsException {
        return queryservice.findQryWageVO(qryId);
    }

    /**
     * 根据vo拼出查询sql.本方法内部需要根据类别得到不同(ABCD)类的拼sql的实现类.
     *
     * @param vo 包含各个bo的vo对象.未保存到数据库中的vo也可以进行查询
     * @return String
     */
    public Hashtable buildQuerySqlHash(User user, QueryVO vo) throws BkmsException {
        return queryservice.getQuerySqlHash(user, vo);
    }

    /**
     * 据vo拼出整个sql 包括select部分 from部分 where部分 orderby部分
     * 返回hash 包含 各部分sql和全部的sql
     * 招聘模块使用,仅拼写E类的指标,并不使用通用的权限接口
     */
    public Hashtable buildQuerySqlForRecr(User user, QueryVO vo) throws BkmsException {
        return queryservice.buildQuerySqlForRecr(user, vo);
    }

//    /**
//      * 根据vo执行查询,查询结果封装到tablevo中.该tablevo应该放到session中.
//      *
//      * @param vo
//      * @roseuid 446D71CD0133
//      */
//     public void putData2Session(QueryVO vo, HttpSession session,String sessionKey) throws BkmsException {
//         queryservice.putData2Session(vo,session,sessionKey);
//     }


    public QueryService getQueryservice() {
        return queryservice;
    }

    public void setQueryservice(QueryService queryservice) {
        this.queryservice = queryservice;
    }

    public QueryBO[] queryQuery(String classId, PageVO vo) throws BkmsException {
        return queryservice.queryQuery(classId, vo);
    }

    public QueryVO findDefaultQueryVO(String setType, String qsType, String classId) throws BkmsException {
        return queryservice.findDefaultQueryVO(setType, qsType, classId);
    }

    public QueryVO findDefaultQryWageVO(String setType, String qsType, String classId) throws BkmsException {
        return queryservice.findDefaultQryWageVO(setType, qsType, classId);
    }

    public Hashtable findSQL(String qryId) throws BkmsException {
        return queryservice.findSQL(qryId);
    }

    public InfoItemBO[] getCellVO(String SelectSql) {
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
            InfoItemBO bo = SysCacheTool.findInfoItem(null, s1);
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

    public TableVO executeQuery(Hashtable sqlHash, PageVO pvo) throws BkmsException {
        String select = (String) sqlHash.get(QryConstants.QRYSQL_showField);
        String from = (String) sqlHash.get(QryConstants.QRYSQL_from);

        String where = (String) sqlHash.get(QryConstants.QRYSQL_where);
        String order = (String) sqlHash.get(QryConstants.QRYSQL_order);
        String sql = "select " + select + " from " + from
                + ("".equals(where) ? "" : " where " + where)
                + ("".equals(order) ? "" : " order by " + order);
        sql = sql.replaceAll("‘", "'");
        sql = sql.replaceAll("’", "'");
        sql = sql.replaceAll("＇", "'");
//        InfoItemBO[] header = this.getCellVO(select);
        InfoItemBO[] header = QryTools.getHeader(select);
        return activePageService.queryListBySql(sql, header, null, pvo, true);
    }

    public TableVO executeQuery(User user, Hashtable sqlHash, PageVO pvo, String pmsType) throws BkmsException {
        String select = (String) sqlHash.get(QryConstants.QRYSQL_showField);
        String from = (String) sqlHash.get(QryConstants.QRYSQL_from);

        String where = (String) sqlHash.get(QryConstants.QRYSQL_where);
        //加入权限记录
        String pmsWhere = getPmsWhereSql(user, pmsType);
        if (where == null || where.trim().equals("")) {
            where = pmsWhere;
        } else if (pmsWhere != null && !pmsWhere.trim().equals("")) {
            where += " and " + pmsWhere;
        }

        String order = (String) sqlHash.get(QryConstants.QRYSQL_order);
        String sql = "select " + select + " from " + from
                + ("".equals(where) ? "" : " where " + where)
                + ("".equals(order) ? "" : " order by " + order);
        //拼接权限字段
        String righField = getRightField(pmsType);
        sql = EmpInfoManageUCCImpl.addSqlSelect(sql, righField);
        InfoItemBO[] header = QryTools.getHeader(select);
        TableVO table = null;
        if (pvo != null)
            table = activePageService.queryListBySql(sql, header, righField, pvo, true);
        else
            table = activePageService.queryListBySql(sql, header, righField, pvo, false);
        //检查记录权限
        checckRecordPms(user, table, pmsType);
        return table;
    }

    private String getRightField(String type) throws BkmsException {
        if (QryConstants.PMS_TYPE_PERSON.equals(type) || QryConstants.PMS_TYPE_PERSON_CCYL.equals(type) || QryConstants.PMS_TYPE_PERSON_PARTY.equals(type) || QryConstants.PMS_TYPE_PERSON_UNION.equals(type)) {
            return Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_PERSON));
        } else if (QryConstants.PMS_TYPE_ORG.equals(type)) {
            return Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_ORG));
        }else {
            throw new BkmsException("不支持的高级查询权限类型:" + type, this.getClass());
        }
    }

    private void checckRecordPms(User user, TableVO table, String type) throws BkmsException {
        PmsAPI pms = new PmsAPI();
        if (QryConstants.PMS_TYPE_PERSON.equals(type) || QryConstants.PMS_TYPE_PERSON_CCYL.equals(type) || QryConstants.PMS_TYPE_PERSON_PARTY.equals(type) || QryConstants.PMS_TYPE_PERSON_UNION.equals(type)) {
            pms.checkPersonRecord(user, table);
        } else if (QryConstants.PMS_TYPE_ORG.equals(type)) {
            pms.checkOrgRecord(user, table);
        }
//        else if (QryConstants.PMS_TYPE_PARTY.equals(type)) {
//            pms.checkPartyRecord(user, table);
//        } else if (QryConstants.PMS_TYPE_POST.equals(type)) {
//            pms.checkPostRecord(user, table);
//        } else if (QryConstants.PMS_TYPE_CCYL.equals(type)) {
//            pms.checkCcylRecord(user, table);
//        } else if (QryConstants.PMS_TYPE_UNION.equals(type)) {
//            pms.checkUnionRecord(user, table);
//        }
        else if (type != null && type.trim().length() > 0) {
            throw new BkmsException("不支持的高级查询权限类型:" + type, this.getClass());
        }
    }

    private String getPmsWhereSql(User user, String type) throws BkmsException {
        PmsAPI pmsAPI = new PmsAPI();
        if (QryConstants.PMS_TYPE_PERSON.equals(type)) {
            return pmsAPI.getPersonScaleCondition(user, false);
        }
//        else if (QryConstants.PMS_TYPE_PERSON_CCYL.equals(type)) {
//            return pmsAPI.getCcylMemberScaleCondition(user, StaticVariable.get(PmsConstants.CCYL_TREEID_ITEM), false);
//        } else if (QryConstants.PMS_TYPE_PERSON_PARTY.equals(type)) {
//            return pmsAPI.getPartyMemberScaleCondition(user, StaticVariable.get(PmsConstants.PARTY_TREEID_ITEM), false);
//        } else if (QryConstants.PMS_TYPE_PERSON_UNION.equals(type)) {
//            return pmsAPI.getUnionMemberScaleCondition(user, StaticVariable.get(PmsConstants.UNION_TREEID_ITEM), false);
//        } else if (QryConstants.PMS_TYPE_ORG.equals(type)) {
//            return pmsAPI.getOrgScaleCondition(user, StaticVariable.get(PmsConstants.ORG_TREEID_ITEM), false);
//        } else if (QryConstants.PMS_TYPE_PARTY.equals(type)) {
//            return pmsAPI.getOrgScaleCondition(user, StaticVariable.get(PmsConstants.PARTY_TREEID_ITEM), false);
//        } else if (QryConstants.PMS_TYPE_POST.equals(type)) {
//            return pmsAPI.getPostScaleCondition(user, StaticVariable.get(PmsConstants.POST_TREEID_ITEM), false);
//        } else if (QryConstants.PMS_TYPE_CCYL.equals(type)) {
//            return pmsAPI.getCcylScaleCondition(user, StaticVariable.get(PmsConstants.CCYL_TREEID_ITEM), false);
//        } else if (QryConstants.PMS_TYPE_UNION.equals(type)) {
//            return pmsAPI.getUnionScaleCondition(user, StaticVariable.get(PmsConstants.UNION_TREEID_ITEM), false);
//        }
        else if ("".equals(type)) {
            return "";
        }
        throw new BkmsException("不支持的高级查询权限类型:" + type, this.getClass());
    }

    /**
     * 根据系统标志查询
     *
     * @param sysFlag
     * @return
     * @throws BkmsException
     */
    public List queryQueryBySysFlag(String sysFlag) throws BkmsException {
        return queryservice.queryQueryBySysFlag(sysFlag);
    }

    public StaticResultVO[] executeStatic(User user, QueryVO vo) throws BkmsException {
        return staticService.executeStatic(user, vo);
    }

}
