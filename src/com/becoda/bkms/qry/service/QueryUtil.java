package com.becoda.bkms.qry.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.qry.pojo.bo.QueryItemBO;
import com.becoda.bkms.qry.pojo.vo.StaticVO;
import com.becoda.bkms.util.Tools;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * User: kangdw
 * Date: 2015-6-27
 * Time: 15:11:17
 */
public class QueryUtil {
    Logger log = Logger.getLogger(QueryUtil.class);

    /**
     * 根据指标集id拼select语句
     *
     * @param ids
     * @return StringBuffer
     */
    private StringBuffer parseSelect(String[] ids) throws BkmsException {
        /****** modify by wanglijun 2015-7-26 为该方法添加错误处理 **********/
        try {
            StringBuffer sb = new StringBuffer();
            if (ids == null || ids.length <= 0)
                return sb;
            for (int i = 0; i < ids.length; i++) {
                sb.append(ids[i]).append(",");
            }
            return sb.deleteCharAt(sb.length() - 1);//删除最后位的,
        } catch (Exception e) {
            throw new BkmsException("根据指标集id拼select语句", e, this.getClass());
        }
    }

    public StringBuffer parseOrder(QueryItemBO[] bos) throws BkmsException {
        /****** modify by wanglijun 2015-7-26 为该方法添加错误处理 **********/
        try {
            StringBuffer sb = new StringBuffer();
            if (bos == null)
                return sb;
            ArrayList list = new ArrayList();
            for (int i = 0; i < bos.length; i++)
                list.add(null);
            for (int i = 0; i < bos.length; i++) {
                if (bos[i].getOrderFlag() != null)
                    list.add(bos[i].getOrderSeq(), bos[i]);
            }
            for (int i = 0; i < list.size(); i++) {
                QueryItemBO b = (QueryItemBO) list.get(i);
                if (b != null && "0".equals(b.getOrderFlag()))
                    sb.append(b.getItemId()).append(" ASC, ");
                else if (b != null && "1".equals(b.getOrderFlag()))
                    sb.append(b.getItemId()).append(" DESC, ");
            }
            for (int i = 0; i < list.size(); i++) {
                QueryItemBO b = (QueryItemBO) list.get(i);
                if (b != null) {
                    sb.append(" " + b.getItemId().substring(0, 1) + "001.ID, ");
                }
            }
            if (sb.length() > 0)
                sb.delete(sb.length() - 2, sb.length());
            return sb;
        } catch (Exception e) {
            throw new BkmsException("拼装排序字段错误", e, this.getClass());
        }
    }

    public StringBuffer parseStaticSelect(QueryBO bo) throws BkmsException {
        /****** modify by wanglijun 2015-7-26 为该方法添加错误处理 **********/
        try {
            StringBuffer sb = new StringBuffer();
            if (bo == null)
                return null;

            String scaleTable = getScaleTable(bo.getSetType(), bo.getUnitType());
            String pk = getSetPk(bo.getSetType());

            sb.append("select ");
            if ("checked".equals(bo.getCount()))
                sb.append("count(").append(scaleTable).append(".").append(pk).append(") as count ,");
            if ("checked".equals(bo.getMax()))
                sb.append("max(").append(bo.getStaticItemId()).append("+0) as max,");
            if ("checked".equals(bo.getMin()))
                sb.append("min(").append(bo.getStaticItemId()).append("+0) as min,");
            if ("checked".equals(bo.getAvg()))
                sb.append("round(avg(").append(bo.getStaticItemId()).append("+0),2) as avg,");
            sb.delete(sb.length() - 1, sb.length());//删除最后的","

            return sb;
        } catch (Exception e) {
            throw new BkmsException("拼装查询部分错误", e, this.getClass());
        }
    }

    /**
     * @param user
     * @param bos
     * @param staticvo
     * @param bo
     * @param noAddDefaultCondFlag 是否添加默认过滤条件 true不添加 false 添加
     * @return
     * @throws BkmsException
     */
    public Hashtable buildQuerySql(User user, QueryItemBO[] bos, StaticVO staticvo, QueryBO bo, boolean noAddDefaultCondFlag) throws BkmsException {
        try {
            //处理机构范围条件
            Hashtable ht = new Hashtable();
            String scaleField = this.getScaleField(bo.getSetType(), bo.getUnitType());
            String scaleTable = this.getScaleTable(bo.getSetType(), bo.getUnitType());
            StringBuffer scaleCondition = new StringBuffer();

            //处理用户选择的查询范围
            if (!"".equals(Tools.filterNull(bo.getOrgIds()))) {
                String[] oIds = bo.getOrgIds().split(",");
                scaleCondition.append(" (");
                for (int i = 0; i < oIds.length; i++) {
                    String treeId = "";
                    if (QueryBO.UNIT_TYPE_ORG.equals(bo.getUnitType())) {
                        Org o = SysCacheTool.findOrgById(oIds[i]);
                        treeId = ((Org) Tools.filterNull(Org.class, o)).getTreeId();
                    } else {    //todo  有bug  范围
//                        PartyBO p = SysCacheTool.findParty(oIds[i]);
//                        treeId = ((PartyBO) Tools.filterNull(PartyBO.class, p)).getTreeId();
                    }
                    scaleCondition.append(scaleField).append(" like '").append(treeId).append("%' or ");
                }
                if (oIds.length > 0) {
                    scaleCondition.delete(scaleCondition.length() - 3, scaleCondition.length());
                    scaleCondition.append(") ");
                } else {
                    scaleCondition.delete(0, scaleCondition.length());
                }
            }
            ht.put(QryConstants.SQL_SCALE_PART, scaleCondition.toString());

            //处理用户设置的显示项目
            String[] ids = new String[bos.length];
            for (int i = 0; i < bos.length; i++) {
                ids[i] = bos[i].getItemId();
            }
            StringBuffer select = parseSelect(ids);
            if (!"".equals(scaleTable))
                select.insert(0, scaleTable + ".ID, ");
            if (ht != null)
                ht.put(QryConstants.SQL_SELECT_PART, select.toString());

            select.insert(0, "select ");

            //处理用户设置的查询条件
            String condition = null;
            String from = null;
            StringBuffer conditionPart = new StringBuffer();
            Criteria c = new Criteria();
            if (staticvo.getStatics() != null) {
//                c.init(staticvo.getStatics().getGroup(), bo.getSetType(), ""); //可以改成接口
                c.init(staticvo.getStatics().getGroup(), bo.getSetType(), bo.getHistorySet()); //modify kangdw 2015-7-16
                if (staticvo.getCondi() != null) {
                    for (int i = 0; i < staticvo.getCondi().length; i++) {
                        QueryConditionBO b = staticvo.getCondi()[i];
                        Method m = Criteria.class.getMethod(b.getOperator(), new Class[]{String.class, String.class, String.class, String.class});
                        m.invoke(c, new Object[]{b.getGroupId(), b.getItemId(), b.getText(), b.getValue()});
                    }
                }
                String condi = Tools.filterNull(c.getCondition()).trim();
                conditionPart.append(condi);
                if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
                    if (conditionPart.length() > 0)
                        conditionPart.append(" and (").append(bo.getAddedCondition()).append(") ");
                    else
                        conditionPart.append(" (").append(bo.getAddedCondition()).append(") ");
                }
                if (!noAddDefaultCondFlag) {
                    condition = c.addDefaultCondition(conditionPart.toString());
                    conditionPart = new StringBuffer(condition);
                }
                from = c.getJoinTable(ids, scaleTable, bo.getHistorySet());
            } else if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
                conditionPart = new StringBuffer(bo.getAddedCondition());
            }

            if (ht != null) {
                //ht.put(QryConstants.SQL_WHERE_PART, Tools.filterNull(condition));
                ht.put(QryConstants.SQL_FROM_PART, Tools.filterNull(from));
            }
            //组装sql
            select.append(" from ").append(from);

            //范围条件
            if (scaleCondition.length() > 0 && conditionPart.toString().trim().length() > 0)
                conditionPart.append(" and (").append(scaleCondition).append(") ");
            else
                conditionPart.append(scaleCondition);

            //合并成where
            if (ht != null)
                ht.put(QryConstants.SQL_WHERE_PART, Tools.filterNull(conditionPart.toString()));
            if (conditionPart.length() > 0)
                select.append(" where ").append(conditionPart);
            //合并order
            StringBuffer order = parseOrder(bos);
            if (order == null || order.length() == 0)
                order.append(getDefaultOrder(bo.getSetType()));

            select.append(" order by ").append(order);
            if (ht != null)
                ht.put(QryConstants.SQL_ORDER_PART, order.toString());
            ht.put(QryConstants.SQL_FULL, select.toString());
            log.info(select.toString());
            return ht;
        } catch (BkmsException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RollbackableException("无法生成sql", e, getClass());
        }
    }

//    private String trans (String inStr){
//        String ret = " to_per(A001.ID) A001.ID";
//        if ("B001".equals(inStr))
//            ret = " to_org(B001.ID) B001.ID" ;
//        else if ("C001".equals(inStr))
//            ret = " to_post(C001.ID) C001.ID" ;
//        else if ("D001".equals(inStr))
//            ret = " to_party(E001.ID) C001.ID" ;
//        else if ("E001".equals(inStr))
//            ret = " to_recr(E001.ID) E001.ID" ;
//        return ret;
//    }

    /**
     * 创建排除A028的条件
     *
     * @param user
     * @param bos
     * @param staticvo
     * @param bo
     * @return
     * @throws BkmsException
     */
    public Hashtable buildQryWageSql(User user, QueryItemBO[] bos, StaticVO staticvo, QueryBO bo) throws BkmsException {
        try {
            //处理机构范围条件
            Hashtable ht = new Hashtable();
            String scaleField = this.getScaleField(bo.getSetType(), bo.getUnitType());
            String scaleTable = this.getScaleTable(bo.getSetType(), bo.getUnitType());
            String pk = this.getSetPk(bo.getSetType());
            StringBuffer scaleCondition = new StringBuffer();

            String scale = "";
            //得到权限的查询范围
//            if (user != null) {
//                PmsAPI api = (PmsAPI) BkmsContext.getBean("pms_pmsAPI");
//                boolean flag = false;
//                if ("PARTY".equals(bo.getUnitType()))
//                    flag = true;
//                boolean isOper = false;
//                if ("2".equals(bo.getOperFlag()))
//                    isOper = true;
//                scale = api.getScaleConditionByType(user, scaleField, getCardeField(bo.getSetType(), bo.getUnitType()), getPerTypeField(bo.getSetType(), bo.getUnitType()), bo.getSetType(), isOper, flag);//todo 需要把操作范围和查询范围参数化
//            }
            //处理选择的查询范围
            if (!"".equals(Tools.filterNull(bo.getOrgIds()))) {
                String[] oIds = bo.getOrgIds().split(",");
                scaleCondition.append(" (");
                for (int i = 0; i < oIds.length; i++) {
                    String treeId = "";
                    if (QueryBO.UNIT_TYPE_ORG.equals(bo.getUnitType())) {
                        Org o = SysCacheTool.findOrgById(oIds[i]);
                        treeId = ((Org) Tools.filterNull(Org.class, o)).getTreeId();
                    } else {    //todo  有bug  范围
//                        PartyBO p = SysCacheTool.findParty(oIds[i]);
//                        treeId = ((PartyBO) Tools.filterNull(PartyBO.class, p)).getTreeId();
                    }
                    scaleCondition.append(scaleField).append(" like '").append(treeId).append("%' or ");
                }
                if (oIds.length > 0) {
                    scaleCondition.delete(scaleCondition.length() - 3, scaleCondition.length());
                    scaleCondition.append(") ");
                } else {
                    scaleCondition.delete(0, scaleCondition.length());
                }
            }
            //将用户权限并入查询的范围权限中
            if (Tools.filterNull(scale).length() > 0 && scaleCondition.length() > 0)
                scaleCondition.insert(0, scale + " and ");
            else
                scaleCondition.insert(0, scale);
            ht.put(QryConstants.SQL_SCALE_PART, scaleCondition.toString());
//            //处理显示项目
//            String[] ids = new String[bos.length];
//            for (int i = 0; i < bos.length; i++)
//                ids[i] = bos[i].getItemId();
//            StringBuffer select = parseSelect(ids);
//            select.insert(0, scaleTable + "." + pk + ", ");
//
//            //将select部分存入session
//            if (ht != null)
//                ht.put(QryConstants.SQL_SELECT_PART, select.toString());
//
//            select.insert(0, "select ");

            //处理查询条件
            String condition = null;
            String from = null;
            StringBuffer conditionPart = new StringBuffer();

            Criteria c = new Criteria();
            //Criteria c1 = new Criteria();

            if (staticvo.getStatics() != null) {
                c.init(staticvo.getStatics().getGroup(), bo.getSetType(), ""); //可以改成接口
                //c1.init(staticvo.getStatics().getGroup(), bo.getSetType(), ""); //可以改成接口
                if (staticvo.getCondi() != null) {
                    for (int i = 0; i < staticvo.getCondi().length; i++) {
                        QueryConditionBO b = staticvo.getCondi()[i];
                        Method m = Criteria.class.getMethod(b.getOperator(), new Class[]{String.class, String.class, String.class, String.class});
                        m.invoke(c, new Object[]{b.getGroupId(), b.getItemId(), b.getText(), b.getValue()});
                    }
                }
                String condi = Tools.filterNull(c.getCondition()).trim();
                ht.put("A028CONDI", condi);
                //附加条件  0809 修改 先拼addcondition 在拼defaultcondition
                conditionPart.append(condi);
                if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
                    if (conditionPart.length() > 0)
                        conditionPart.append(" and (").append(bo.getAddedCondition()).append(") ");
                    else
                        conditionPart.append(" (").append(bo.getAddedCondition()).append(") ");
                }
                condition = c.addDefaultCondition(conditionPart.toString());
                conditionPart = new StringBuffer(condition);

                //from = c.getJoinTable(ids, scaleTable, bo.getHistorySet());
            } else if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
                conditionPart = new StringBuffer(bo.getAddedCondition());
            }

            if (ht != null) {
                ht.put(QryConstants.SQL_WHERE_PART, Tools.filterNull(condition));
                ht.put(QryConstants.SQL_FROM_PART, Tools.filterNull(from));
            }
            //组装sql
            //select.append(" from ").append(from);

            //范围条件
            if (scaleCondition.length() > 0 && conditionPart.toString().trim().length() > 0)
                conditionPart.append(" and (").append(scaleCondition).append(") ");
            else
                conditionPart.append(scaleCondition);

            //查询条件
//            if (condition != null && !"".equals(condition)) {
//                if (conditionPart.length() > 0)
//                    conditionPart.append(" and (").append(condition).append(") ");
//                else
//                    conditionPart.append(" (").append(condition).append(") ");
//            }
//            //附加条件   0809-为yxm修改 拼addcondition后在处理 defaultcondition
//            if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
//                if (conditionPart.length() > 0)
//                    conditionPart.append(" and (").append(bo.getAddedCondition()).append(") ");
//                else
//                    conditionPart.append(" (").append(bo.getAddedCondition()).append(") ");
//            }
            //合并order
            StringBuffer order = parseOrder(bos);
            if (order == null || order.length() == 0)
                order.append(getDefaultOrder(bo.getSetType()));

            if (ht != null)
                ht.put(QryConstants.SQL_ORDER_PART, order.toString());
            return ht;
            /******** add by wanglijun 2015-7-26 ***********/
        } catch (BkmsException ex) {
            throw ex;
            /************ end add ******************/
        } catch (Exception e) {
            throw new RollbackableException("无法生成sql", e, getClass());
        }
    }

    //招聘模块使用的高级查询
    //去掉排序,显示项,查询权限,附加条件的处理
    public Hashtable buildQuerySqlForRecr(User user, QueryItemBO[] bos, StaticVO staticvo, QueryBO bo) throws BkmsException {
        try {
            //处理机构范围条件
            Hashtable ht = new Hashtable();
            String scaleField = this.getScaleField(bo.getSetType(), bo.getUnitType());
            String scaleTable = this.getScaleTable(bo.getSetType(), bo.getUnitType());
            String pk = this.getSetPk(bo.getSetType());
            StringBuffer scaleCondition = new StringBuffer();

//            String scale = "";
            //得到权限的查询范围
//            if (user != null) {
//                PmsAPI api = (PmsAPI) BkmsContext.getBean("pms_pmsAPI");
//                boolean flag = false;
//                if ("PARTY".equals(bo.getUnitType()))
//                    flag = true;
//                boolean isOper = false;
//                if ("2".equals(bo.getOperFlag()))
//                    isOper = true;
//                scale = api.getScaleConditionByType(user, scaleField, getCardeField(bo.getSetType(), bo.getUnitType()), getPerTypeField(bo.getSetType(), bo.getUnitType()), bo.getSetType(), isOper, flag);//todo 需要把操作范围和查询范围参数化
//            }
            //处理选择的查询范围
            if (!"".equals(Tools.filterNull(bo.getOrgIds()))) {
                String[] oIds = bo.getOrgIds().split(",");
                scaleCondition.append(" (");
                for (int i = 0; i < oIds.length; i++) {
                    String treeId;
//                    if (QueryBO.UNIT_TYPE_ORG.equals(bo.getUnitType())) {
                    Org o = SysCacheTool.findOrgById(oIds[i]);
                    treeId = ((Org) Tools.filterNull(Org.class, o)).getTreeId();
//                    } else {    //todo  有bug  范围
//                        PartyBO p = SysCacheTool.findParty(oIds[i]);
//                        treeId = ((PartyBO) Tools.filterNull(PartyBO.class, p)).getTreeId();
//                    }
                    scaleCondition.append(scaleField).append(" like '").append(treeId).append("%' or ");
                }
                if (oIds.length > 0) {
                    scaleCondition.delete(scaleCondition.length() - 3, scaleCondition.length());
                    scaleCondition.append(") ");
                } else {
                    scaleCondition.delete(0, scaleCondition.length());
                }
            }
            //将用户权限并入查询的范围权限中
//            if (Tools.filterNull(scale).length() > 0 && scaleCondition.length() > 0)
//                scaleCondition.insert(0, scale + " and ");
//            else
//                scaleCondition.insert(0, scale);
            ht.put(QryConstants.SQL_SCALE_PART, scaleCondition.toString());
            //处理显示项目
            String[] ids = new String[bos.length];
            for (int i = 0; i < bos.length; i++)
                ids[i] = bos[i].getItemId();
            StringBuffer select = parseSelect(ids);
            select.insert(0, scaleTable + "." + pk + ", ");

            //将select部分存入session
            if (ht != null)
                ht.put(QryConstants.SQL_SELECT_PART, select.toString());
            select.insert(0, "select ");

            //处理查询条件
            String condition = null;
            String from = null;
            StringBuffer conditionPart = new StringBuffer();

            Criteria c = new Criteria();
            if (staticvo.getStatics() != null) {
                c.init(staticvo.getStatics().getGroup(), bo.getSetType(), ""); //可以改成接口
                if (staticvo.getCondi() != null) {
                    for (int i = 0; i < staticvo.getCondi().length; i++) {
                        QueryConditionBO b = staticvo.getCondi()[i];
                        Method m = Criteria.class.getMethod(b.getOperator(), new Class[]{String.class, String.class, String.class, String.class});
                        m.invoke(c, new Object[]{b.getGroupId(), b.getItemId(), b.getText(), b.getValue()});
                    }
                }
                String condi = Tools.filterNull(c.getCondition()).trim();

                //附加条件  0809 修改 先拼addcondition 在拼defaultcondition
                conditionPart.append(condi);
//                if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
//                    if (conditionPart.length() > 0)
//                        conditionPart.append(" and (").append(bo.getAddedCondition()).append(") ");
//                    else
//                        conditionPart.append(" (").append(bo.getAddedCondition()).append(") ");
//                }
                condition = c.addDefaultCondition(conditionPart.toString());
                if (!"".equals(condition) && condition != null)
                    conditionPart = new StringBuffer(condition);

                from = c.getJoinTableForRecr(ids, scaleTable, bo.getHistorySet());  //处理E205时不加join
            } else if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
                conditionPart = new StringBuffer(bo.getAddedCondition());
            }

            if (ht != null) {
                ht.put(QryConstants.SQL_WHERE_PART, Tools.filterNull(condition));
                ht.put(QryConstants.SQL_FROM_PART, Tools.filterNull(from));
            }
            //组装sql
            select.append(" from ").append(from);

            //范围条件
//            if (scaleCondition.length() > 0 && conditionPart.toString().trim().length() > 0)
//                conditionPart.append(" and (").append(scaleCondition).append(") ");
//            else
//                conditionPart.append(scaleCondition);

            //查询条件
            if (condition != null && !"".equals(condition)) {
                if (conditionPart.length() > 0)
                    conditionPart.append(" and (").append(condition).append(") ");
                else
                    conditionPart.append(" (").append(condition).append(") ");
            }
//            //附加条件   0809-为yxm修改 拼addcondition后在处理 defaultcondition
//            if (bo.getAddedCondition() != null && !"".equals(bo.getAddedCondition())) {
//                if (conditionPart.length() > 0)
//                    conditionPart.append(" and (").append(bo.getAddedCondition()).append(") ");
//                else
//                    conditionPart.append(" (").append(bo.getAddedCondition()).append(") ");
//            }
            //合并成where
            if (conditionPart.length() > 0)
                select.append(" where ").append(conditionPart);
            //合并order
//            StringBuffer order = parseOrder(bos);
//            if (order == null || order.length() == 0)
//                order.append(getDefaultOrder(bo.getSetType()));
//            select.append(" order by ").append(order);
//            if (ht != null)
//                ht.put(QryConstants.SQL_ORDER_PART, order.toString());
            ht.put(QryConstants.SQL_FULL, select.toString());
            log.debug(select.toString());
            return ht;
            /******** add by wanglijun 2015-7-26 ***********/
        } catch (BkmsException ex) {
            throw ex;
            /************ end add ******************/
        } catch (Exception e) {
            throw new RollbackableException("无法生成sql", e, getClass());
        }
    }


    private String getDefaultOrder(String setType) {
        if ("A".equals(setType))
            return "A001.A001743, A001.A001745, A001.ID";
        else if ("B".equals(setType))
            return "B001.B001715, B001.ID";
        else if ("C".equals(setType))
            return "C001.ID";
        else if ("D".equals(setType))
            return "D001.D001715, D001.ID";
        return "";
    }


    public String[] buildStaticSql(User user, StaticVO[] staticvos, QueryBO bo) throws BkmsException {
        try {
            String scaleField = this.getScaleField(bo.getSetType(), bo.getUnitType());
            String scaleTable = this.getScaleTable(bo.getSetType(), bo.getUnitType());

            StringBuffer scaleCondition = new StringBuffer();
            String[] staticSql = new String[staticvos.length];

            String scale = "";
//            if (user != null) {
//                PmsAPI api = (PmsAPI) BkmsContext.getBean("pms_pmsAPI");
//                boolean flag = false;
//                if ("PARTY".equals(bo.getUnitType()))
//                    flag = true;
//                scale = api.getScaleConditionByType(user, scaleField, getCardeField(bo.getSetType(), bo.getUnitType()), getPerTypeField(bo.getSetType(), bo.getUnitType()), bo.getSetType(), false, flag);//todo 需要把操作范围和查询范围参数化
//            }

            if (!"".equals(Tools.filterNull(bo.getOrgIds()))) {
                scaleCondition.append(" (");
                String[] oIds = bo.getOrgIds().split(",");
                for (int i = 0; i < oIds.length; i++) {
                    String treeId = "";
                    if (QueryBO.UNIT_TYPE_ORG.equals(bo.getUnitType())) {
                        Org o = SysCacheTool.findOrgById(oIds[i]);
                        treeId = ((Org) Tools.filterNull(Org.class, o)).getTreeId();
                    } else {
//                        PartyBO p = SysCacheTool.findParty(oIds[i]);
//                        treeId = ((PartyBO) Tools.filterNull(PartyBO.class, p)).getTreeId();
                    }
                    scaleCondition.append(scaleField).append(" like '").append(treeId).append("%' or ");
                }
                if (oIds.length > 0) {
                    scaleCondition.delete(scaleCondition.length() - 3, scaleCondition.length());
                    scaleCondition.append(") ");
                } else {
                    scaleCondition.delete(0, scaleCondition.length());
                }
            }
            //将权限合并
            if (Tools.filterNull(scale).length() > 0 && scaleCondition.length() > 0)
                scaleCondition.insert(0, scale + " and ");
            else
                scaleCondition.insert(0, scale);

            //处理显示项目
            StringBuffer select = parseStaticSelect(bo);

            //处理查询条件
            String condition = null;
            String from = null;

            StaticCriteria criteria = new StaticCriteria();
            for (int pos = 0; pos < staticvos.length; pos++) {
                StaticVO staticvo = staticvos[pos];
                if (staticvo.getStatics() != null) {

                    if (pos == 0)
                        criteria.newBaseStatic();
                    if (pos == 1)
                        criteria.endBaseStatic();
                    if (pos > 0)
                        criteria.newSubStatic();

                    criteria.init(staticvo.getStatics().getGroup(), bo.getSetType(), ""); //可以改成接口

                    if (staticvo.getCondi() != null) {
                        for (int i = 0; i < staticvo.getCondi().length; i++) {
                            QueryConditionBO b = staticvo.getCondi()[i];
                            Method m = StaticCriteria.class.getMethod(b.getOperator(), new Class[]{String.class, String.class, String.class, String.class});
                            m.invoke(criteria, new Object[]{b.getGroupId(), b.getItemId(), b.getText(), b.getValue()});
                        }
                    }
                    condition = criteria.addDefaultCondition(criteria.getCondition());
                    from = criteria.getJoinTable(new String[]{bo.getStaticItemId()}, scaleTable);//把统计字段坐在的表和count用的表传进去
                }

                //组装sql
                StringBuffer bufferedStaticSql = new StringBuffer();
                bufferedStaticSql.append(select).append(" from ").append(from);

                StringBuffer conditionPart = new StringBuffer();
                //合并where条件 连接条件
                //范围条件
                if (scaleCondition.length() > 0)
                    conditionPart.append(" (").append(scaleCondition).append(") ");

                //查询条件
                if (condition != null && !"".equals(condition)) {
                    if (conditionPart.length() > 0 && !condition.trim().startsWith("group by"))
                        conditionPart.append(" and ").append(condition);
                    else
                        conditionPart.append(" ").append(condition);
                }

                //合并成where
                if (conditionPart.length() > 0 && !conditionPart.toString().trim().startsWith("group by"))
                    bufferedStaticSql.append(" where ").append(conditionPart);
                else
                    bufferedStaticSql.append(" ").append(conditionPart);
                //group by 字段添加到select前
                if (bufferedStaticSql.indexOf("group by") != -1) {
                    String sql = bufferedStaticSql.toString().trim();
                    String groupField = sql.substring(sql.length() - 7, sql.length());
                    bufferedStaticSql.insert(6, " " + groupField + " as allvalue,");
                }

                log.debug(bufferedStaticSql);
                staticSql[pos] = bufferedStaticSql.toString();
            }
            return staticSql;
        } catch (Exception e) {
            throw new RollbackableException("无法执行统计", e, getClass());
        }
    }

    public String getSetPk(String setType) {
        if ("A".equals(setType)) {
            return "ID";
        } else if ("B".equals(setType)) {
            return "ID";
        } else if ("C".equals(setType)) {
            return "ID";
        } else if ("D".equals(setType)) {
            return "ID";
        } else if ("E".equals(setType)) {  //添加E类 招聘用
            return "ID";      //应聘人员id
        }
        return "ID";
    }

    public String getScaleTable(String setType, String unitType) {
        //其实应该根据
        if ("A".equals(setType) && "ORG".equals(unitType)) {
            return "A001";
        } else if ("A".equals(setType) && "PARTY".equals(unitType)) {
            return "A001";
        } else if ("A".equals(setType) && "WAGE".equals(unitType)) {
            return "A815";
        } else if ("B".equals(setType)) {
            return "B001";
        } else if ("C".equals(setType)) {
            return "C001";
        } else if ("D".equals(setType)) {
            return "D001";
        } else if ("E".equals(setType)) {  //添加E类 招聘用
            return "E001";       //应聘人员基本信息表
        } else if ("F".equals(setType)) {  //添加E类 招聘用
            return "F001";       //应聘人员基本信息表
        } else if ("G".equals(setType)) {  //添加E类 招聘用
            return "G001";       //应聘人员基本信息表
        } else if ("X".equals(setType)) {  //添加E类 招聘用
            return "A001";       //应聘人员基本信息表
        } else {
            return setType + "001";     // added by kangdw
        }
    }

    public String getScaleField(String setType, String unitType) {
        if ("A".equals(setType) && "ORG".equals(unitType)) {
            return "A001738";
        } else if ("A".equals(setType) && "PARTY".equals(unitType)) {
            return "A001740";
        } else if ("A".equals(setType) && "WAGE".equals(unitType)) {
            return "A815700";
        } else if ("B".equals(setType)) {
            return "B001003";
        } else if ("C".equals(setType)) {
            return "C001701";
        } else if ("D".equals(setType)) {//party
            return "D001003";
        } else if ("E".equals(setType)) { //ccyl
            return "E001003";
        } else if ("F".equals(setType)) {//union
            return "F001003";
        }
        return "A001738";
    }


    public String getPerTypeField(String setType, String unitType) {
        if ("A".equals(setType) && "ORG".equals(unitType)) {
            return "A001054";
        }
        return null;
    }

}
