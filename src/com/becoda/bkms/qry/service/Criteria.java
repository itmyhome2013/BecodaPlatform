package com.becoda.bkms.qry.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.util.Tools;

import java.util.*;

/**
 * User: kangdw
 * Date: 2015-6-23
 * Time: 15:23:57
 */
public class Criteria {
    protected String pk = "ID";
    protected String historyTable = "ID";
    protected String groupDef;
    protected Map queryTableHash = new TreeMap();
    protected Hashtable groupHash = new Hashtable();
    protected String setType;

    /**
     * 构造函数 传入group的定义
     *
     * @param groupDef
     */
    public void init(String groupDef, String setType, String historyTable) {
        this.setType = setType;
        this.groupDef = groupDef;
        this.historyTable = historyTable;
        if ("A".equals(setType))
            pk = "ID";
        else if ("B".equals(setType))
            pk = "ID";
        else if ("C".equals(setType))
            pk = "ID";
        else if ("D".equals(setType))
            pk = "ID";
        else if ("E".equals(setType))    //招聘类型
            pk = "ID";
    }

    /**
     * 内部方法返回用于拼条件的值
     *
     * @param value
     * @param code
     * @return String
     */
    protected String getValue(String infoItemId, String value, String code) throws BkmsException {
        try {
            InfoItemBO b = SysCacheTool.findInfoItem(null, infoItemId);
            b = (InfoItemBO) Tools.filterNull(InfoItemBO.class, b);
            String codeSetId = null;
            if (QryConstants.DATA_TYPE_CODE.equals(b.getItemDataType()))
                codeSetId = b.getItemCodeSet();

            String ret;
            if (code != null && !"".equals(code))
                ret = Tools.filterNull(code);
            else
                ret = Tools.filterNull(value);
            if (codeSetId != null && ret.indexOf(",") == -1 && ret.indexOf(codeSetId) == -1) //查询条件中不包含“，” 并且需要补codesetid
                ret = codeSetId + ret;
            else if (codeSetId != null && ret.indexOf(",") != -1) { //查询条件中包含“，” 并且需要补codesetid
                String[] rets = ret.split(",");
                ret = "";
                for (int i = 0; i < rets.length; i++) {
                    if (rets[i].indexOf(codeSetId) == -1)
                        ret = ret + codeSetId + rets[i] + ",";
                    else
                        ret = ret + rets[i] + ",";
                }
                if (ret.length() > 0)
                    ret = ret.substring(0, ret.length() - 1);
            }
            return ret;
        } catch (Exception ex) {
            throw new BkmsException("得条件值过程出错", ex, this.getClass());
        }

    }

    /**
     * reset Criteria
     */
    protected void clear() {
        queryTableHash.clear();
        groupHash.clear();
    }

    // ==
    public String equal(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer rt = new StringBuffer().append(field).append(" = '").append(tmp).append("' ");
        groupHash.put(groupId, rt.toString());
        return rt.toString();
    }

    // !=
    public String notequal(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer rt = new StringBuffer().append(field).append(" <> '").append(tmp).append("' ");
        groupHash.put(groupId, rt.toString());
        return rt.toString();
    }

    public String morethan(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        InfoItemBO bo = SysCacheTool.findInfoItem(null, field);
        if (bo == null)
            return null;
        StringBuffer rt = new StringBuffer();
        String operator = "> ";
        if (Tools.filterNull(bo.getItemCodeSet()).length() > 0 && Tools.filterNull(QryConstants.QRY_CODE_REVERSE).indexOf(bo.getItemCodeSet()) != -1)
            operator = "< ";

        if (QryConstants.DATA_TYPE_INT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_FLOAT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_COMPUTE.equals(bo.getItemDataType()))
            rt.append(field).append(" + 0 ").append(operator).append(tmp).append(" ");
        else
            rt.append(field).append(" ").append(operator).append("'").append(tmp).append("' ");
        groupHash.put(groupId, rt.toString());
        return rt.toString();
    }

    public String lessthan(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        InfoItemBO bo = SysCacheTool.findInfoItem(null, field);
        if (bo == null)
            return null;
        String operator = "< ";
        if (Tools.filterNull(bo.getItemCodeSet()).length() > 0 && Tools.filterNull(QryConstants.QRY_CODE_REVERSE).indexOf(bo.getItemCodeSet()) != -1)
            operator = "> ";

        StringBuffer rt = new StringBuffer();
        if (QryConstants.DATA_TYPE_INT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_FLOAT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_COMPUTE.equals(bo.getItemDataType()))
            rt.append(field).append(" + 0 ").append(operator).append(tmp).append(" ");
        else
            rt.append(field).append(" ").append(operator).append("'").append(tmp).append("' ");
        groupHash.put(groupId, rt.toString());
        return rt.toString();
    }

    public String moreequal(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        InfoItemBO bo = SysCacheTool.findInfoItem(null, field);
        if (bo == null)
            return null;
        String operator = ">= ";
        if (Tools.filterNull(bo.getItemCodeSet()).length() > 0 && Tools.filterNull(QryConstants.QRY_CODE_REVERSE).indexOf(bo.getItemCodeSet()) != -1)
            operator = "<= ";

        StringBuffer rt = new StringBuffer();
        if (QryConstants.DATA_TYPE_INT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_FLOAT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_COMPUTE.equals(bo.getItemDataType()))
            rt.append(field).append(" + 0 ").append(operator).append(tmp).append(" ");
        else
            rt.append(field).append(" ").append(operator).append("'").append(tmp).append("' ");
        groupHash.put(groupId, rt.toString());
        return rt.toString();
    }

    public String lessequal(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        InfoItemBO bo = SysCacheTool.findInfoItem(null, field);
        if (bo == null)
            return null;
        String operator = "<= ";
        if (Tools.filterNull(bo.getItemCodeSet()).length() > 0 && Tools.filterNull(QryConstants.QRY_CODE_REVERSE).indexOf(bo.getItemCodeSet()) != -1)
            operator = ">= ";
        StringBuffer rt = new StringBuffer();
        if (QryConstants.DATA_TYPE_INT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_FLOAT.equals(bo.getItemDataType()) || QryConstants.DATA_TYPE_COMPUTE.equals(bo.getItemDataType()))
            rt.append(field).append(" + 0 ").append(operator).append(tmp).append(" ");
        else
            rt.append(field).append(" ").append(operator).append("'").append(tmp).append("' ");
        groupHash.put(groupId, rt.toString());
        return rt.toString();
    }

    public String in(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        tmp = "'" + tmp.replaceAll(",", "','") + "'";
        String rt = new StringBuffer().append(field).append(" in (").append(tmp).append(") ").toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    public String notin(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        tmp = "'" + tmp.replaceAll(",", "','") + "'";
        String rt = new StringBuffer().append(field).append(" not in (").append(tmp).append(") ").toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    public String like(String groupId, String field, String value, String code) throws BkmsException {
        //todo
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        InfoItemBO item = SysCacheTool.findInfoItem(field.substring(0, 4), field);
        String rt = "";
        if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(item.getItemDataType())) {
            CodeItemBO codeitem=SysCacheTool.findCodeItem(code);
            rt = new StringBuffer().append(field)
                    .append(" in (select code_item_id from sys_code_item where code_set_id='")
                    .append(item.getItemCodeSet()).append("' and code_tree_id like '"+codeitem.getTreeId()+"%' )").toString(); 
        } else {
            if (tmp.indexOf("%") == -1)
                tmp = new StringBuffer().append("%").append(tmp).append("%").toString();
            rt = new StringBuffer().append(field).append(" like '").append(tmp).append("' ").toString();
        }
        groupHash.put(groupId, rt);
        return rt;
    }

    public String notlike(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        InfoItemBO item = SysCacheTool.findInfoItem(field.substring(0, 4), field);
        String rt = "";
        if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(item.getItemDataType())) {
            CodeItemBO codeitem=SysCacheTool.findCodeItem(code);
            rt = new StringBuffer().append(field)
                    .append(" not in (select code_item_id from sys_code_item where code_set_id='")
                    .append(item.getItemCodeSet()).append("' and code_tree_id like '"+codeitem.getTreeId()+"%' )").toString();
        } else {
            if (tmp.indexOf("%") == -1)
                tmp = new StringBuffer().append("%").append(tmp).append("%").toString();
            rt = new StringBuffer().append(field).append(" not like '").append(tmp).append("' ").toString();
        }
        groupHash.put(groupId, rt);
        return rt;
    }

    public String exist(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);

        if (field == null || field.length() != 7)
            return null;
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        String rt = new StringBuffer().append(field.substring(0, 4)).append(".").append(pk).append(" in (select distinct ").append(pk).append(" from ").append(field.substring(0, 4)).append(" where ").append(field).append(" = '").append(tmp).append("') ").toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    public String notexist(String groupId, String field, String value, String code) throws BkmsException {
        String tmp = getValue(field, value, code);

        if (field == null || field.length() != 7)
            return null;
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        String rt = new StringBuffer().append(field.substring(0, 4)).append(".").append(pk).append(" not in (select distinct ").append(pk).append(" from ").append(field.substring(0, 4)).append(" where ").append(field).append(" = '").append(tmp).append("') ").toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    public String isnull(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
        sb.append("(").append(field).append(" is null or ");
        sb.append(field).append(" = '' )");
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    public String notisnull(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
        sb.append("(").append(field).append(" is not null or ");
        sb.append(field).append(" <> '' )");
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    //等于系统日期
    public String equalsysdate(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
//        sb.append("(").append(field).append(" = '").append(Tools.getSysDate("yyyy-MM-dd")).append("')");
        sb.append("(").append(field).append(" = ").append("to_char(sysdate,'yyyy-MM-dd')").append(")"); //modify kangdw 2015-7-20
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    //大于系统日期+(天)
    public String morethansysdate(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
//        String sysDate = Tools.getSysDate("yyyy-MM-dd");
//        sb.append("(").append(field).append(" > '").append(Tools.plusDay(sysDate, Integer.parseInt(value))).append("')");
        sb.append("(").append(field).append(" > ").append("to_char(sysdate+(" + value + "),'yyyy-MM-dd')").append(")"); //modify kangdw 2015-7-20
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    //小于系统日期+(天)
    public String lessthansysdate(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
//        String sysDate = Tools.getSysDate("yyyy-MM-dd");
//        sb.append("(").append(field).append(" < '").append(Tools.plusDay(sysDate, Integer.parseInt(value))).append("')");
        sb.append("(").append(field).append(" < ").append("to_char(sysdate+(" + value + "),'yyyy-MM-dd')").append(")"); //modify kangdw 2015-7-20
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    //匹配系统月日+(天)
    public String likesysdateMD(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
//        String sysDate = Tools.getSysDate("yyyy-MM-dd");
//        String newDate = Tools.plusDay(sysDate, Integer.parseInt(value));
//        sb.append("(").append(field).append(" like '%").append(newDate.substring(newDate.indexOf("-"))).append("')");
        sb.append("(substr(" + field + ",6)=to_char(sysdate+(" + value + "),'MM-dd') )"); //modify kangdw 2015-7-20
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    //匹配系统月+(月)
    public String likesysdateM(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
//        String sysDate = Tools.getSysDate("yyyy-MM-dd");
//        String newDate = Tools.plusMonth(sysDate, Integer.parseInt(value));

        InfoItemBO bo = SysCacheTool.findInfoItem(field.substring(0, 4), field);
        if (bo != null) {
            if (SysConstants.INFO_ITEM_DATA_TYPE_DATE8.equals(bo.getItemDataType()) || SysConstants.INFO_ITEM_DATA_TYPE_DATE6.equals(bo.getItemDataType()))
//                sb.append("(").append(field).append(" like '%-").append(newDate.substring(newDate.indexOf("-") + 1)).append("-%')");
                sb.append("(substr(" + field + ",6,2)=to_char(ADD_MONTHS(sysdate,(" + value + ")),'MM'))"); //modify kangdw 2015-7-20
//            else
//                sb.append("(").append(field).append(" like '%-").append(newDate.substring(newDate.indexOf("-") + 1)).append("%')");
        }
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    //匹配系统年
    public String likesysdateY(String groupId, String field, String value, String code) throws BkmsException {
        queryTableHash.put(field.substring(0, 4), field.substring(0, 4));
        StringBuffer sb = new StringBuffer();
//        String sysDate = Tools.getSysDate("yyyy");
//        sb.append("(").append(field).append(" like '").append(sysDate).append("%')");
        sb.append("( substr(" + field + ",0,4)=to_char(sysdate,'YYYY'))");   //modify kangdw 2015-7-20
        String rt = sb.toString();
        groupHash.put(groupId, rt);
        return rt;
    }

    /**
     * 废弃 连接条件改在 JoinTable 中
     *
     * @return
     * @throws BkmsException
     * @deprecated
     */
    public String getJoinCondition() throws BkmsException {
        return null;
//        try {
//            if (queryTableHash.size() == 1)
//                return "";
//            else {
//                Iterator it = queryTableHash.values().iterator();
//                String firstTable = (String) it.next();
//                StringBuffer rt = new StringBuffer();
//                while (it.hasNext()) {
//                    String t = (String) it.next();
//                    rt.append(firstTable.trim()).append(".").append(pk).append(" = ").append(t).append(".").append(pk).append(" and ");
//                    InfoSetBO bo = SysCacheTool.findInfoSet(t);
//                    if (bo != null && InfoSetBO.RS_TYPE_MANY.equals(bo.getSet_rsType()) && !Tools.filterNull(historyTable).equalsIgnoreCase(bo.getSetId()))
//                        rt.append(t).append("000 = '00900' and ");
//                }
//
//                InfoSetBO bo = SysCacheTool.findInfoSet(firstTable);
//                if (bo != null && InfoSetBO.RS_TYPE_MANY.equals(bo.getSet_rsType()) && !Tools.filterNull(historyTable).equalsIgnoreCase(bo.getSetId()))
//                    rt.append(firstTable).append("000 = '00900' and ");
//
//                return rt.substring(0, rt.length() - 4);
//            }
//        } catch (Exception e) {
//            throw new RollbackableException("解析表间关联错误，无法继续");
//        }
    }

    private void parseQueryTable() {
        Iterator it = queryTableHash.values().iterator();
        while (it.hasNext()) {
            String setId = (String) it.next();
            if (setId.startsWith("A"))
                queryTableHash.put("A001", "A001");
            else if (setId.startsWith("B"))
                queryTableHash.put("B001", "B001");
            else if (setId.startsWith("C"))
                queryTableHash.put("C001", "C001");
            else if (setId.startsWith("E"))
                queryTableHash.put("E001", "E001");
        }

    }

    public String getJoinTable(String[] showItemIds, String scaleTable) throws BkmsException {
        try {
            if (showItemIds != null) {
                for (int i = 0; i < showItemIds.length; i++) {
                    if (showItemIds[i] != null && showItemIds[i].length() == 7)
                        queryTableHash.put(showItemIds[i].substring(0, 4), showItemIds[i].substring(0, 4));
                }
            }
            if (scaleTable != null && !"".equals(scaleTable))
                queryTableHash.put(scaleTable, scaleTable);
            Iterator it = queryTableHash.values().iterator();

            String firstTable = (String) it.next();
            //get the path  连接关系的路径。图的算法

            StringBuffer rt = new StringBuffer();
            rt.append(firstTable);
            InfoSetBO firstSet = SysCacheTool.findInfoSet(firstTable.substring(0, 4));
            String pk = firstSet.getSetPk();
            while (it.hasNext()) {
                String joinTable = (String) it.next();
                InfoSetBO joinSet = SysCacheTool.findInfoSet(joinTable.substring(0, 4));
                if (firstTable.substring(0, 1).equals(joinTable.substring(0, 1))) {
                    rt.append(" left join ").append(joinTable).append(" on ").append(firstTable).append(".").append(pk).append(" = ").append(joinTable).append(".").append(pk).append(" ");
                } else {
                    //检查两表关联，如果没有关联则抛出异常。
                    rt.append(" left join ").append(joinTable).append(" on ").append(getPath(firstTable, joinTable)).append(" ");
                }
                if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(joinSet.getSet_rsType())
                        && !Tools.filterNull(historyTable).equalsIgnoreCase(joinTable)) {
                    rt.append("and ").append(joinTable).append("000 = '").append(Constants.YES).append("' ");
                }

            }

            return rt.toString();
        } catch (Exception e) {
            /***************  modify by wanglijun 2015-7-26 ***************/
//            throw new RollbackableException("解析表间关联错误，无法继续");
            throw new RollbackableException("解析表间关联错误，无法继续", e, this.getClass());
            /**************** end modify *****************/
        }
    }

    private String getPath(String firstTable, String joinTable) throws BkmsException {
        String ret = null;
        ret = (String) QryConstants.ht.get(firstTable.substring(0, 1) + joinTable.substring(0, 1));
        ret = ret.replaceAll("@", firstTable);
        ret = ret.replaceAll("#", joinTable);
        if (ret == null || "".equals(ret)) {
            //在数据库中查找
            throw new BkmsException("没有查找" + firstTable + "与" + joinTable + "的关联");
        }
        return ret;
    }

    public String getJoinTable(String[] showItemIds, String scaleTable, String setId) throws BkmsException {
        try {
            if (showItemIds != null) {
                for (int i = 0; i < showItemIds.length; i++) {
                    if (showItemIds[i] != null && showItemIds[i].length() == 7)
                        queryTableHash.put(showItemIds[i].substring(0, 4), showItemIds[i].substring(0, 4));
                }
            }
            if (scaleTable != null && !"".equals(scaleTable))
                queryTableHash.put(scaleTable, scaleTable);
            Iterator it = queryTableHash.values().iterator();

            String firstTable = (String) it.next();

            StringBuffer rt = new StringBuffer();
            rt.append(firstTable);
            InfoSetBO firstSet = SysCacheTool.findInfoSet(firstTable.substring(0, 4));
            String pk = firstSet.getSetPk();
            while (it.hasNext()) {
                String joinTable = (String) it.next();
                InfoSetBO joinSet = SysCacheTool.findInfoSet(joinTable.substring(0, 4));
                if (firstTable.substring(0, 1).equals(joinTable.substring(0, 1))) {
                    rt.append(" left join ").append(joinTable).append(" on ").append(firstTable).append(".").append(pk).append(" = ").append(joinTable).append(".").append(pk).append(" ");
                } else {
                    //检查两表关联，如果没有关联则抛出异常。
                    rt.append(" left join ").append(joinTable).append(" on ").append(getPath(firstTable, joinTable)).append(" ");
                }
                if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(joinSet.getSet_rsType())
                        && !Tools.filterNull(historyTable).equalsIgnoreCase(joinTable)) {
                    if (!joinSet.getSetId().equals(setId)) {  //modify kangdw 2015-7-16
                        rt.append("and ").append(joinTable).append("000 = '").append(Constants.YES).append("' ");
                    }
                }
            }

            return rt.toString();
        } catch (Exception e) {
            /***************  modify by wanglijun 2015-7-26 ***************/
//            throw new RollbackableException("解析表间关联错误，无法继续");
            throw new RollbackableException("解析表间关联错误，无法继续", e, this.getClass());
            /**************** end modify *****************/
        }
    }

    //增加给招聘模块的高级查询使用,
    // 特殊处理在拼写E205表条件时不加 join
    public String getJoinTableForRecr(String[] showItemIds, String scaleTable, String setId) throws BkmsException {
        try {
            if (showItemIds != null) {
                for (int i = 0; i < showItemIds.length; i++) {
                    if (showItemIds[i] != null && showItemIds[i].length() == 7)
                        queryTableHash.put(showItemIds[i].substring(0, 4), showItemIds[i].substring(0, 4));
                }
            }
            if (scaleTable != null && !"".equals(scaleTable))
                queryTableHash.put(scaleTable, scaleTable);
            Iterator it = queryTableHash.values().iterator();

            String firstTable = (String) it.next();

            StringBuffer rt = new StringBuffer();
            rt.append(firstTable);
            InfoSetBO firstSet = SysCacheTool.findInfoSet(firstTable.substring(0, 4));
            String pk = firstSet.getSetPk();
            while (it.hasNext()) {
                String joinTable = (String) it.next();
                if (!"E205".equals(joinTable.substring(0, 4))) {  //招聘志愿表不加
                    InfoSetBO joinSet = SysCacheTool.findInfoSet(joinTable.substring(0, 4));
                    rt.append(" left join ").append(joinTable).append(" on ").append(firstTable).append(".").append(pk).append(" = ").append(joinTable).append(".").append(pk).append(" ");
                    if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(joinSet.getSet_rsType()) && !Tools.filterNull(historyTable).equalsIgnoreCase(joinTable))
                        if (!joinSet.getSetId().equals(setId)) {
//                            rt.append("and ").append(joinTable).append("000 = '").append(Constants.YES).append("' ");
                        }
                }
            }
            return rt.toString();
        } catch (Exception e) {
            /***************  modify by wanglijun 2015-7-26 ***************/
//            throw new RollbackableException("解析表间关联错误，无法继续");
            throw new RollbackableException("解析表间关联错误，无法继续", e, this.getClass());
            /**************** end modify *****************/
        }
    }

    public String getCondition() throws BkmsException {
        try {
            Enumeration en = groupHash.keys();
            if (groupDef == null)
                return null;
            String rt = groupDef;
            rt = "(" + rt + ")";
            rt = rt.replaceAll("\\)", " )");
            if (!en.hasMoreElements())
                return null;

            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                rt = rt.replaceAll(key + " ", groupHash.get(key) + " ");
            }
            rt = rt.replaceAll("与", "and");
            rt = rt.replaceAll("或", "or");
            return rt;
        } catch (Exception e) {
            /***************  modify by wanglijun 2015-7-26 ***************/
//            throw new RollbackableException("拼装条件错误，无法继续");
            throw new RollbackableException("拼装条件错误，无法继续", e, this.getClass());
            /**************** end modify *****************/
        }
    }

    /**
     * *** modify by wanglijun 2015-7-26 为该方法添加错误处理 *********
     */
    public String addDefaultCondition(String condition) throws BkmsException {
        try {
            condition = Tools.filterNull(condition);
            String a = "";
            String b = "";
            String c = "";

            if ("A".equals(setType)) {
                if (condition.indexOf("A001730") == -1) //排出逻辑减员人员
                    c = "A001730 = '" + Constants.NO + "'";
                if (condition.indexOf("A001755") == -1) {  // 排出离退休人员
                    if (c.length() > 0)
                        c += " and A001755 = '" + Constants.NO + "'";
                    else
                        c = "A001755 = '" + Constants.NO + "'";
                }
                if (c.length() > 0)
                    c = " ( " + c + " ) ";

            } else if ("B".equals(setType)) {  //排出逻辑删除行政机构
                if (condition.indexOf("B001730") == -1)
                    c = "B001730= '" + Constants.NO + "'";
            } else if ("C".equals(setType)) {  //排除岗位模板
                if (condition.indexOf("C001730") == -1)
                    c = "C001730= '" + Constants.NO + "'";
            }

            if (condition.length() != 0 && c.length() != 0)
                condition = c + " and " + condition;
            else
                condition = c + condition;

            return condition;
        } catch (Exception e) {
            throw new BkmsException("添加默认查询条件失败", e, this.getClass());
        }
    }
}
