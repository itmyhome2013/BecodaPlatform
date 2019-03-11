package com.becoda.bkms.run.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.service.UserManageService;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.service.QueryService;
import com.becoda.bkms.run.RunConstants;
import com.becoda.bkms.run.pojo.bo.RemBO;
import com.becoda.bkms.run.pojo.bo.RemOrgScopeBO;
import com.becoda.bkms.run.pojo.bo.RemPersonScopeBO;
import com.becoda.bkms.run.service.RemService;
import com.becoda.bkms.run.ucc.IRemUCC;
import com.becoda.bkms.run.util.RunTools;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-23
 * Time: 15:12:09
 * To change this template use File | Settings | File Templates.
 */
public class RemUCCImpl implements IRemUCC {
    private ActivePageService activePageService;
    private QueryService queryService;
    private RemService remService;
    private UserManageService userManageService;

    public QueryService getQueryService() {
        return queryService;
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    public RemService getRemService() {
        return remService;
    }

    public void setRemService(RemService remService) {
        this.remService = remService;
    }


    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

    public TableVO queryBirthList(User user, String sql, InfoItemBO[] header, PageVO page, boolean isPage) throws BkmsException {
        //String righField = Tools.filterNull(StaticVariable.get("A")); 人员管理所能查看的列权限。
        TableVO table = activePageService.queryListBySql(sql, header, null, page, true);
        PmsAPI pms = new PmsAPI();
        pms.checkPersonRecord(user, table);
        return table;
    }

    public RemBO[] queryAllRem(String orgId) throws BkmsException {
        try {
            return remService.queryAllRem(orgId);
        } catch (Exception e) {
            throw new BkmsException("查询数据失败", e, this.getClass());
        }
    }

    public RemBO findRemById(User user, String remId) throws BkmsException {
        RemBO bo = remService.findRemById(remId);
        bo.setSql(this.getSqlByQryId(user, bo.getRemCond()));
        return bo;
    }

//    public RemBO queryAllRemOrgScope(String remId) throws BkmsException {
//        return remService.queryAllRemOrgScope(remId) ;
//    }

    public RemOrgScopeBO[] queryAllRemOrgScope(String remId) throws BkmsException {
        return remService.queryAllRemOrgScope(remId);
    }

    public RemPersonScopeBO[] queryAllRemPersonScope(String remId, String toType) throws BkmsException {
        return remService.queryAllRemPersonScope(remId, toType);
    }

    public ArrayList queryRemByUserId(String userId, User user) throws BkmsException {
        try {
            Person person = SysCacheTool.findPersonById(userId);
            String orgId = person.getOrgId();
            Org org = SysCacheTool.findOrgById(orgId);

            Hashtable ht = new Hashtable();
            //判断在不在机构范围里
            RemOrgScopeBO[] bos_OrgScope = remService.queryAllRemOrgScope();
            if (bos_OrgScope != null) {
                for (int i = 0; i < bos_OrgScope.length; i++) {
                    if (org.getTreeId().indexOf(bos_OrgScope[i].getOrgTreeId()) != -1) {
                        if (!ht.containsKey(bos_OrgScope[i].getRemId())) {
                            RemBO bo = (RemBO) remService.getRemdao().findBoById(RemBO.class, bos_OrgScope[i].getRemId());
                            ht.put(bo.getRemId(), bo);
                        }
                    }
                }
            }
            //判断在不在人员范围里
            RemPersonScopeBO[] bos_personScope = remService.getRemdao().queryAllRemPersonScope(userId);
            if (bos_personScope != null) {
                for (int i = 0; i < bos_personScope.length; i++) {
                    if (!ht.containsKey(bos_personScope[i].getRemId())) {
                        RemBO bo = (RemBO) remService.getRemdao().findBoById(RemBO.class, bos_personScope[i].getRemId());
                        ht.put(bo.getRemId(), bo);
                    }
                }
            }
            //判断在不在角色范围里
            List rolelist = userManageService.queryUserRole(userId);
            if (rolelist != null || rolelist.size() > 0) {
                for (int a = 0; a < rolelist.size(); a++) {
                    RoleInfoBO rbo = (RoleInfoBO) rolelist.get(a);
                    RemPersonScopeBO[] bos_personScope_role = remService.getRemdao().queryAllRemPersonScope(rbo.getRoleId());
                    if (bos_personScope_role != null) {
                        for (int i = 0; i < bos_personScope_role.length; i++) {
                            if (!ht.containsKey(bos_personScope_role[i].getRemId())) {
                                RemBO bo = (RemBO) remService.getRemdao().findBoById(RemBO.class, bos_personScope_role[i].getRemId());
                                ht.put(bo.getRemId(), bo);
                            }
                        }
                    }
                }

            }
            ArrayList list = new ArrayList();
            Iterator it = ht.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                RemBO rembo = (RemBO) ht.get(key);
                //判断有没有提醒结果
                String scale = RunTools.buildQuerySql(user);
                String qryId = rembo.getRemCond();
//                QueryManager qm = new QueryManager();
//                QueryVO qryVo = qm.findQueryVO(qryId);
//                String sql = qm.getQuerySql1(user, qryVo);
////                String sql = rembo.getRemCond();
////                sql = this.chSql(sql);  //转换sql中的日期 add
//                int index_1 = sql.toUpperCase().lastIndexOf("ORDER BY");
//                String newsql = sql.substring(0, index_1) + " AND " + scale + " " + sql.substring(index_1);
//                ActivePageService activePageService = (ActivePageService) BkmsContext.getBean("sys_activePageService");
//                int size = activePageService.queryForInt(newsql);//提醒结果的数量
//                if (size > 0) {
//                    list.add(rembo);
//                }
                String sql = "";
                if (qryId != null) {
                    sql = this.getSqlByQryId(user, qryId);
//                String sql = rembo.getRemCond();
                    sql = this.chSql(sql);  //转换sql中的日期 add
                    int index_1 = sql.toUpperCase().lastIndexOf("ORDER BY");
                    String newsql = "";
                    if (scale != null && !"".equals(scale))
                        newsql = sql.substring(0, index_1) + " AND " + scale + " " + sql.substring(index_1);
                    ActivePageService activePageService = (ActivePageService) BkmsContext.getBean("sys_activePageService");
                    int size = activePageService.queryForInt(newsql);//提醒结果的数量
                    if (size > 0) {
                        rembo.setSql(sql);
                        list.add(rembo);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            throw new RollbackableException("数据查询失败", e, this.getClass());
        }
    }

    public String getSqlByQryId(User user, String qryId) throws BkmsException {
        String sql = "";
        if (qryId != null) {
            Hashtable hs = queryService.findSQL(qryId);
            StringBuffer select1 = new StringBuffer();
//            String adselect = (String) hs.get(QryConstants.QRYSQL_showField);
//            if (adselect == null || adselect.trim().equals("")) {
//                select1.append("*");
//            } else {
//                select1.append(adselect.trim());
//            }
            select1.append("COUNT(*)");
            StringBuffer from1 = new StringBuffer();
            String adfrom = (String) hs.get(QryConstants.QRYSQL_from);
            if (adfrom == null || adfrom.trim().equals("")) {
                from1.append("A001");
            } else {
                from1.append(adfrom.trim());
            }
            StringBuffer where1 = new StringBuffer();
            String adwhere = (String) hs.get(QryConstants.QRYSQL_where);
            if (adwhere == null || adwhere.trim().equals("")) {
                where1.append("1=1");
            } else {
                where1.append(adwhere.trim());
            }
            String adorder = (String) hs.get(QryConstants.QRYSQL_order);
            return sql = "select " + select1 + " from " + from1 + " where " + where1 + " ORDER BY " + adorder;
        } else {
            return sql;
        }
    }

    public void startOrStopRem(String[] ids, String flag, User user) throws RollbackableException {
        try {

            for (int i = 0; i < ids.length; i++) {
                RemBO bo = (RemBO) remService.getRemdao().findBoById(RemBO.class, ids[i]);
                bo.setValidFlag(flag);
                remService.getRemdao().updateBo(bo.getRemId(), bo);
            }
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "启用或禁用提醒");
        } catch (Exception e) {
            throw new RollbackableException("启用或禁用提醒失败", e, this.getClass());
        }
    }

    public void saveRemScope(String remId, String orgIds, List persons, List roles, User user) throws BkmsException {
        try {
            //机构范围：先删后保存
            RemOrgScopeBO[] bos_orgScope = remService.getRemdao().queryAllRemOrgScope(remId);
            if (bos_orgScope != null) {
                for (int i = 0; i < bos_orgScope.length; i++) {
                    remService.getRemdao().deleteBo(bos_orgScope[i]);
                }
            }
            if (!"".equals("orgIds")) {
                String[] arrayOrgIds = Tools.getStringArray(orgIds, ",");
                for (int i = 0; i < arrayOrgIds.length; i++) {
                    RemOrgScopeBO bo = new RemOrgScopeBO();
                    bo.setRemId(remId);
                    bo.setOrgId(arrayOrgIds[i]);
                    Org org = SysCacheTool.findOrgById(arrayOrgIds[i]);
                    bo.setOrgTreeId(org.getTreeId());
                    remService.getRemdao().createBo(bo);
                }
            }
            //人员范围：先删后保存
            RemPersonScopeBO[] bos_personScope = remService.getRemdao().queryAllRemPersonScope(remId, "01");
            if (bos_personScope != null) {
                for (int i = 0; i < bos_personScope.length; i++) {
                    remService.getRemdao().deleteBo(bos_personScope[i]);
                }
            }
            if (persons != null && persons.size() > 0) {
                for (int i = 0; i < persons.size(); i++) {
                    RemPersonScopeBO bo = new RemPersonScopeBO();
                    bo.setRemId(remId);
                    bo.setToId(((Person) persons.get(i)).getPersonId());
                    bo.setToType("01");
                    remService.getRemdao().createBo(bo);
                }
            }
            //角色范围：先删后保存
            RemPersonScopeBO[] bos_personScope_role = remService.getRemdao().queryAllRemPersonScope(remId, "02");
            if (bos_personScope_role != null) {
                for (int i = 0; i < bos_personScope_role.length; i++) {
                    remService.getRemdao().deleteBo(bos_personScope_role[i]);
                }
            }
            if (roles != null && roles.size() > 0) {
                for (int i = 0; i < roles.size(); i++) {
                    RemPersonScopeBO bo = new RemPersonScopeBO();
                    bo.setRemId(remId);
                    bo.setToId(((RoleInfoBO) roles.get(i)).getRoleId());
                    bo.setToType("02");
                    remService.getRemdao().createBo(bo);
                }
            }
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "保存提醒数据");
        } catch (Exception e) {
            throw new BkmsException("保存提醒数据失败", e, this.getClass());
        }
    }

    public String addRem(RemBO bo, QueryVO vo, User user) throws BkmsException {
        try {
            vo.setName(bo.getRemName());
            vo.setSysFlag("2");
            queryService.createQuery(vo);
            bo.setRemCond(vo.getQryId());
            String id = remService.getRemdao().createBo(bo);
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "新增数据");
            return id;
        } catch (Exception e) {
            throw new BkmsException("新增数据失败", e, this.getClass());
        }
    }


    public void updateRem(RemBO bo, QueryVO vo, User user) throws BkmsException {
        try {
            remService.getRemdao().updateBo(bo.getRemId(), bo);
            queryService.createQuery(vo);
            HrmsLog.addOperLog(this.getClass(), user.getUserId(), RunConstants.MODULE_NAME, "更新数据");
        } catch (Exception e) {
            throw new BkmsException("更新数据失败", e, this.getClass());
        }
    }

    //检查员sql语句的正确性
    public String isSQLValid(String strSql) throws RollbackableException {
        try {
//            ActivePageManager apm = new ActivePageManager(s);
            String currentDate = Tools.getSysDate("yyyy-MM-dd");
            String strResult = strSql, strDate = "", strT = "", strTemp = "";
            int start, end;
            while (strResult.indexOf("[") != -1) {
                start = strResult.indexOf("[");
                end = strResult.indexOf("]");

                strTemp = strTemp + strResult.substring(0, start - 1);
                strDate = strResult.substring(start + 1, end);

                strT = RunTools.parseDate(currentDate, strDate);
                if ("".equalsIgnoreCase(strT)) {//解析出错
                    strSql = "";
                    break;
                }
                strTemp = strTemp + "'" + strT + "'";
                strResult = strResult.substring(end + 2, strResult.length());
            }
            if ("".equalsIgnoreCase(strSql))
                return "3";//解析日期出错
            else {
                strSql = strTemp + strResult;
            }

            int size = activePageService.queryForInt(strSql);
            if (size == 0) {
                return "1";//找不到结果
            }

            return "2";//找到相应的记录
        } catch (Exception e) {
            throw new RollbackableException("检查员sql语句的正确性失败", e, this.getClass());
        }
    }

    //转换sql中的 当前时间或年份等字段  返回可使用的sql
    public String chSql(String strSql) throws RollbackableException {
        try {
            String currentDate = Tools.getSysDate("yyyy-MM-dd");
            String strResult = strSql, strDate = "", strT = "", strTemp = "";
            int start, end;
            while (strResult.indexOf("[") != -1) {
                start = strResult.indexOf("[");
                end = strResult.indexOf("]");
                strTemp = strTemp + strResult.substring(0, start - 1);
                strDate = strResult.substring(start + 1, end);
                strT = RunTools.parseDate(currentDate, strDate);
                if ("".equalsIgnoreCase(strT)) {//解析出错
                    strSql = "";
                    break;
                }
                strTemp = strTemp + "'" + strT + "'";
                strResult = strResult.substring(end + 2, strResult.length());
            }
            if ("".equalsIgnoreCase(strSql))
                return "";    //解析日期出错
            else {
                strSql = strTemp + strResult;
                return strSql;  //完整sql
            }
        } catch (Exception e) {
            throw new RollbackableException("检查员sql语句的正确性失败", e, this.getClass());
        }
    }

    public void deleteRem(String[] ids, User user) throws RollbackableException {
        try {
            for (int a = 0; a < ids.length; a++) {
                RemBO rembo = (RemBO) remService.getRemdao().findBoById(RemBO.class, ids[a]);
                queryService.deleteQuery(rembo.getRemCond());
                //机构范围
                RemOrgScopeBO[] bos_OrgScope = remService.getRemdao().queryAllRemOrgScope(rembo.getRemId());
                if (bos_OrgScope != null) {
                    for (int i = 0; i < bos_OrgScope.length; i++) {
                        remService.getRemdao().deleteBo(RemOrgScopeBO.class, bos_OrgScope[i].getScopeId());
                    }
                }
                //人员和角色范围：
                RemPersonScopeBO[] bos_personScope = remService.getRemdao().queryAllRemPersonScope(rembo.getRemId(), "");
                if (bos_personScope != null) {
                    for (int i = 0; i < bos_personScope.length; i++) {
                        remService.getRemdao().deleteBo(RemPersonScopeBO.class, bos_personScope[i].getScopeId());
                    }
                }
                remService.getRemdao().deleteBo(RemBO.class, rembo.getRemId());
            }

        } catch (Exception e) {
            throw new RollbackableException("新增数据失败", e, this.getClass());
        }
    }

    public int queryRemBrithDayByUserId(User user) throws BkmsException {
        try {

            String curDay = (Tools.getSysDate("yyyy-MM-dd")).substring(5);
            String sql = "SELECT COUNT(ID) FROM A001 WHERE ( A001730 = '00900'  and A001755 = '00900' ) "
                    + " AND substr(A001011,6) = '" + curDay + "'"
                    + " ORDER BY A001.A001743, A001.A001745";

            //在登录者的查询范围权限中判断有没有过生日的人
            String scale = RunTools.buildQuerySql(user);
            int index_1 = sql.toUpperCase().lastIndexOf("ORDER BY");
            String newsql = sql.substring(0, index_1) + " AND " + scale + " " + sql.substring(index_1);
            return activePageService.queryForInt(newsql);//结果数量

        } catch (Exception e) {
            throw new BkmsException("数据查询失败", e, this.getClass());
        }
    }
}
