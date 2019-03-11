package com.becoda.bkms.pms.api;

//import com.becoda.bkms.ccp.pojo.bo.PartyBO;
//import com.becoda.bkms.ccyl.pojo.bo.CcylBO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.NodeVO;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.service.PersonService;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.OperateBO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
//import com.becoda.bkms.union.pojo.bo.UnionBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PmsAPI {

    /**
     * 返回与人员\机构\岗位\党务 相关的范围条件
     * 主要是基础模块使用 包括人员 机构 党务管理和高级查询
     * 如果infoType="A" sysCadreField不允许为空
     *
     * @param treeFieldName tree字段
     * @param sysCadreField 系统管理干部字段
     * @param infoType      A 人员 B 机构 C岗位 D党务
     * @param isOper        true 操作权限 false 查询权限
     * @param isPartyMember 查询的是否是党员，此参数只有在infoType="A"时有效，即查询的是行政机构人员还是党员
     * @return 带括号的条件语句
     */

    private String getScaleConditionByType(User user, String treeFieldName, String sysCadreField, String perTypeField, String infoType, boolean isOper, boolean isPartyMember) {
        List pmsScale;
        List noPmsScale;
        Hashtable sysCadreCode = null;
        if (("A".equals(infoType) && !isPartyMember) || "B".equals(infoType) || "C".equals(infoType)) {
            if (isOper) {
                pmsScale = user.getHaveOperateOrgScale();
                noPmsScale = user.getHaveNoOperateOrgScale();
                sysCadreCode = user.getPmsOperateCode();
            } else {
                pmsScale = user.getHaveQueryOrgScale();
                noPmsScale = user.getHaveNoQueryOrgScale();
                sysCadreCode = user.getPmsQueryCode();
            }
        } else {
            if (isOper) {
                pmsScale = user.getHaveOperatePartyScale();
                noPmsScale = user.getHaveNoOperatePartyScale();
            } else {
                pmsScale = user.getHaveQueryPartyScale();
                noPmsScale = user.getHaveNoQueryPartyScale();
            }
        }
        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);
        sysCadreCode = (Hashtable) Tools.filterNull(Hashtable.class, sysCadreCode);

        StringBuffer Cond = new StringBuffer();
        //如果没有机构权限
        if (pmsScale.size() == 0) {
            return "(1=2)";
        }

        //有权限的条件组合
        for (int i = 0; i < pmsScale.size(); i++) {
            if (i != 0)
                Cond.append(" or ");
            else
                Cond.append("(");
            String treeId = ((NodeVO) pmsScale.get(i)).getTreeId();
            Cond.append(treeFieldName)
                    .append(" like '")
                    .append(treeId)
                    .append("%' ");
        }
        if (pmsScale.size() > 0)
            Cond.append(")");
//      排除权限条件组合
        for (int i = 0; i < noPmsScale.size(); i++) {
            String treeId = ((NodeVO) noPmsScale.get(i)).getTreeId();
            Cond.append(" and substr(")
                    .append(treeFieldName)
                    .append(",0,").append(treeId.length())
                    .append(") <> '")
                    .append(treeId)
                    .append("' ");
        }

        if ("A".equals(infoType) && !isPartyMember) {
            //得到系统管理干部代码列表
            List pmsPerTypeCode = (List) sysCadreCode.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID);//员工类别代码
            //监测系统管理干部权限
            sysCadreField = Tools.filterNull(sysCadreField);

            

            //检测员工类别代码
            perTypeField = Tools.filterNull(perTypeField);
            pmsPerTypeCode = (List) Tools.filterNull(ArrayList.class, pmsPerTypeCode);

            if (!perTypeField.equals("")) {   //如果不为空,查询则与人员相关,此时需检测员工类别代码条件限制
                if (pmsPerTypeCode.size() > 0) {
                    StringBuffer codeCond = new StringBuffer();
                    for (int i = 0; i < pmsPerTypeCode.size(); i++) {
                        if (i != 0) {
                            codeCond.append(" or ");
                        }
                        CodeItemBO item = (CodeItemBO) pmsPerTypeCode.get(i);
                        codeCond.append(perTypeField)
                                .append(" = '")
                                .append(item.getItemId())
                                .append("' ");

                    }
                    Cond.append(" and (").append(codeCond).append(") ");
                } else {
                    Cond.append(" and (1=2) ");
                }
            }
        }
        return "(" + Cond.toString() + ")";
    }

    /**
     * 构造机构范围权限条件语句
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName tree字段
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     */
    public String getOrgScaleCondition(User user, String treeFieldName, boolean isOper) {
        if (user == null || treeFieldName == null || "".equals(treeFieldName))
            return null;

        List pmsScale;
        List noPmsScale;

        if (isOper) {
            pmsScale = user.getHaveOperateOrgScale();
            noPmsScale = user.getHaveNoOperateOrgScale();
        } else {
            pmsScale = user.getHaveQueryOrgScale();
            noPmsScale = user.getHaveNoQueryOrgScale();
        }

        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);

        StringBuffer Cond = new StringBuffer();
        //如果没有机构权限
        if (pmsScale.size() == 0) {
            return "(1=2)";
        }

        //有权限的条件组合
        for (int i = 0; i < pmsScale.size(); i++) {
            if (i != 0)
                Cond.append(" or ");
            else
                Cond.append("(");
            String treeId = ((Org) pmsScale.get(i)).getTreeId();
            Cond.append(treeFieldName)
                    .append(" like '")
                    .append(treeId)
                    .append("%' ");
        }
        if (pmsScale.size() > 0)
            Cond.append(")");
        //排除权限条件组合
        for (int i = 0; i < noPmsScale.size(); i++) {
            String treeId = ((Org) noPmsScale.get(i)).getTreeId();
            Cond.append(" and substr(")
                    .append(treeFieldName)
                    .append(",0,").append(treeId.length())
                    .append(") <> '")
                    .append(treeId)
                    .append("' ");
        }
        return "(" + Cond.toString() + ")";
    }

    /**
     * 构造薪酬机构范围权限条件语句
     *
     * @param user          当前用户
     * @param treeFieldName tree字段
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     */
    public String getWageUnitScaleCondition(User user, String treeFieldName, boolean isOper) {
//        if (user == null || treeFieldName == null || "".equals(treeFieldName))
//            return null;
//
//        List pmsScale;
//        List noPmsScale;
//
//        if (isOper) {
//            pmsScale = user.getHaveOperateWageUnitScale();
//            noPmsScale = user.getHaveNoOperateWageUnitScale();
//        } else {
//            pmsScale = user.getHaveQueryWageUnitScale();
//            noPmsScale = user.getHaveNoQueryWageUnitScale();
//        }
//
//        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
//        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);
//
//        StringBuffer Cond = new StringBuffer();
//        //如果没有机构权限
//        if (pmsScale.size() == 0) {
//            return "(1=2)";
//        }
//
//        //有权限的条件组合
//        for (int i = 0; i < pmsScale.size(); i++) {
//            if (i != 0)
//                Cond.append(" or ");
//            else
//                Cond.append("(");
//            String treeId = ((WageUnitBO) pmsScale.get(i)).getTreeId();
//            Cond.append(treeFieldName)
//                    .append(" like '")
//                    .append(treeId)
//                    .append("%' ");
//        }
//        if (pmsScale.size() > 0)
//            Cond.append(")");
//        //排除权限条件组合(暂不支持)
//        for (int i = 0; i < noPmsScale.size(); i++) {
//            String treeId = ((WageUnitBO) noPmsScale.get(i)).getTreeId();
//            Cond.append(" and substr(")
//                    .append(treeFieldName)
//                    .append(",0,").append(treeId.length())
//                    .append(") <> '")
//                    .append(treeId)
//                    .append("' ");
//        }
//        return "(" + Cond.toString() + ")";
        return "";
    }

    /**
     * 构造人员范围权限条件语句,检测全部权限控制指标
     * 主要是基础模块使用
     *
     * @param user            当前用户
     * @param fieldNamePrefix 查询对象的别名
     * @param isOper          true 操作权限 false 查询权限
     * @return 带括号的条件语句（hql语句）
     * @throws BkmsException
     */
    public String getPersonScaleCondition(User user, String fieldNamePrefix, boolean isOper) throws BkmsException {
        fieldNamePrefix = Tools.filterNull(fieldNamePrefix);
        if ("".equals(fieldNamePrefix)) return "(1=2)";
        String treeFieldName = fieldNamePrefix + "." + PmsConstants.HQ_PERSON_DEPT_TREEID_ITEM;
        String perTypeField = fieldNamePrefix + "." + PmsConstants.HQ_PERSON_TYPE_ITEM;
        return getPersonScaleCondition(user, treeFieldName, perTypeField, isOper);
    }

    /**
     * 构造人员范围权限条件语句,检测全部权限控制指标
     * 主要是基础模块使用
     *
     * @param user   当前用户
     * @param isOper true 操作权限 false 查询权限
     * @return 带括号的条件语句（sql语句）
     * @throws BkmsException
     */
    public String getPersonScaleCondition(User user, boolean isOper) throws BkmsException {
        if (user == null) return "(1=2)";
        String treeFieldName = StaticVariable.get(PmsConstants.PERSON_DEPT_TREEID_ITEM);
        String perTypeField = StaticVariable.get(PmsConstants.PERSON_TYPE_ITEM);
        return getPersonScaleCondition(user, treeFieldName, perTypeField, isOper);
    }

    /**
     * 构造人员范围权限条件语句,只根据treeId检测权限
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName 人员所在部门treeId
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     * @throws BkmsException
     */
    public String getPersonSimpleScaleCondition(User user, String treeFieldName, boolean isOper) throws BkmsException {
        return getPersonScaleCondition(user, treeFieldName, "", isOper);
    }

    /**
     * 构造人员范围权限条件语句,检测treeId、系统管理干部指标权限
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName 人员所在部门treeId
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     * @throws BkmsException
     */
//    public String getPersonSimpleScaleCondition(User user, String treeFieldName,boolean isOper) throws BkmsException {
//        return getPersonScaleCondition(user, treeFieldName,"", "", "", isOper);
//    }

    /**
     * 构造人员范围权限条件语句,检测treeId、系统管理干部指标、员工类别权限
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName 人员所在部门treeId
     * @param perTypeField  员工类别指标
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     * @throws BkmsException
     */
    public String getPersonSimpleScaleCondition(User user, String treeFieldName,String perTypeField, boolean isOper) throws BkmsException {
        return getPersonScaleCondition(user, treeFieldName,perTypeField, isOper);
    }

    /**
     * 构造人员范围权限条件语句 （最底层方法）
     * 主要是基础模块使用
     *
     * @param user             当前用户
     * @param treeFieldName    机构tree字段
     * @param perTypeField     人员类别字段
     * @param isOper           true 操作权限 false 查询权限
     * @return 带括号的条件语句
     * @throws BkmsException
     */
    public String getPersonScaleCondition(User user, String treeFieldName, String perTypeField, boolean isOper) throws BkmsException {
        if (user == null)
            return null;

        List pmsScale = null;
        List noPmsScale = null;
        Hashtable sysCadreCode = null;
        if (isOper) {
            pmsScale = user.getHaveOperateOrgScale();
            noPmsScale = user.getHaveNoOperateOrgScale();
            sysCadreCode = user.getPmsOperateCode();
        } else {
            pmsScale = user.getHaveQueryOrgScale();
            noPmsScale = user.getHaveNoQueryOrgScale();
            sysCadreCode = user.getPmsQueryCode();
        }

        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);
        sysCadreCode = (Hashtable) Tools.filterNull(Hashtable.class, sysCadreCode);

        StringBuffer Cond = new StringBuffer();
        //如果没有机构权限
        if (pmsScale.size() == 0) {
            return "(1=2)";
        }

        //有权限的条件组合
        for (int i = 0; i < pmsScale.size(); i++) {
            if (i != 0)
                Cond.append(" or ");
            else
                Cond.append("(");
            String treeId = ((Org) pmsScale.get(i)).getTreeId();
            Cond.append(treeFieldName)
                    .append(" like '")
                    .append(treeId)
                    .append("%' ");
        }
        if (pmsScale.size() > 0)
            Cond.append(")");
        //排除权限条件组合
        for (int i = 0; i < noPmsScale.size(); i++) {
            String treeId = ((Org) noPmsScale.get(i)).getTreeId();
            Cond.append(" and substr(")
                    .append(treeFieldName)
                    .append(",0,").append(treeId.length())
                    .append(") <> '")
                    .append(treeId)
                    .append("' ");
        }

        //得到系统管理干部代码列表
        List pmsPerTypeCode = (List) sysCadreCode.get(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID));//员工类别代码
        String checkCodeArea = Tools.filterNull(StaticVariable.get(PmsConstants.PERSON_CHECK_CODESETID_AREA));
        /************* start  检测系统管理干部权限    *****************/

        /****************** end  检测系统管理干部权限 **************************/

        //检测员工类别代码
        if (checkCodeArea.indexOf(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID)) >= 0) {
            perTypeField = Tools.filterNull(perTypeField);
            pmsPerTypeCode = (List) Tools.filterNull(ArrayList.class, pmsPerTypeCode);
            if (!perTypeField.equals("")) {   //如果不为空,查询则与人员相关,此时需检测员工类别代码条件限制
                if (pmsPerTypeCode.size() > 0) {
                    StringBuffer codeCond = new StringBuffer();
                    for (int i = 0; i < pmsPerTypeCode.size(); i++) {
                        if (i != 0) {
                            codeCond.append(" or ");
                        }
                        CodeItemBO item = (CodeItemBO) pmsPerTypeCode.get(i);
                        codeCond.append(perTypeField)
                                .append(" = '")
                                .append(item.getItemId())
                                .append("' ");
                    }
                    Cond.append(" and (").append(codeCond).append(") ");
                } else {
                    Cond.append(" and (1=2) ");
                }
            }
        }
        
        return "(" + Cond.toString() + ")";
    }

    

    /**
     * 构造岗位范围权限条件语句
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName tree字段
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     */
    public String getPostScaleCondition(User user, String treeFieldName, boolean isOper) {
        if (user == null || treeFieldName == null || "".equals(treeFieldName))
            return null;

        List pmsScale;
        List noPmsScale;

        if (isOper) {
            pmsScale = user.getHaveOperateOrgScale();
            noPmsScale = user.getHaveNoOperateOrgScale();
        } else {
            pmsScale = user.getHaveQueryOrgScale();
            noPmsScale = user.getHaveNoQueryOrgScale();
        }

        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);

        StringBuffer Cond = new StringBuffer();
        //如果没有机构权限
        if (pmsScale.size() == 0) {
            return "(1=2)";
        }

        //有权限的条件组合
        for (int i = 0; i < pmsScale.size(); i++) {
            if (i != 0)
                Cond.append(" or ");
            else
                Cond.append("(");
            String treeId = ((Org) pmsScale.get(i)).getTreeId();
            Cond.append(treeFieldName)
                    .append(" like '")
                    .append(treeId)
                    .append("%' ");
        }
        if (pmsScale.size() > 0)
            Cond.append(")");
        //排除权限条件组合（暂不支持）
        for (int i = 0; i < noPmsScale.size(); i++) {
            String treeId = ((Org) noPmsScale.get(i)).getTreeId();
            Cond.append(" and substr(")
                    .append(treeFieldName)
                    .append(",0,").append(treeId.length())
                    .append(") <> '")
                    .append(treeId)
                    .append("' ");
        }
        return "(" + Cond.toString() + ")";
    }

    /**
     * 构造党组织范围权限条件语句
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName tree字段
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     */
//    public String getPartyScaleCondition(User user, String treeFieldName, boolean isOper) {
//        if (user == null || treeFieldName == null || "".equals(treeFieldName))
//            return null;
//
//        List pmsScale;
//        List noPmsScale;
//        if (isOper) {
//            pmsScale = user.getHaveOperatePartyScale();
//            noPmsScale = user.getHaveNoOperatePartyScale();
//        } else {
//            pmsScale = user.getHaveQueryPartyScale();
//            noPmsScale = user.getHaveNoQueryPartyScale();
//        }
//
//        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
//        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);
//
//        StringBuffer Cond = new StringBuffer();
//        //如果没有党组织权限
//        if (pmsScale.size() == 0) {
//            return "(1=2)";
//        }
//
//        //有权限的条件组合
//        for (int i = 0; i < pmsScale.size(); i++) {
//            if (i != 0)
//                Cond.append(" or ");
//            else
//                Cond.append("(");
//            String treeId = ((PartyBO) pmsScale.get(i)).getTreeId();
//            Cond.append(treeFieldName)
//                    .append(" like '")
//                    .append(treeId)
//                    .append("%' ");
//        }
//        if (pmsScale.size() > 0)
//            Cond.append(")");
//        //排除权限条件组合（暂不支持）
//        for (int i = 0; i < noPmsScale.size(); i++) {
//            String treeId = ((PartyBO) noPmsScale.get(i)).getTreeId();
//            Cond.append(" and substr(")
//                    .append(treeFieldName)
//                    .append(",0,").append(treeId.length())
//                    .append(") <> '")
//                    .append(treeId)
//                    .append("' ");
//        }
//        return "(" + Cond.toString() + ")";
//    }


    /**
     * 构造团组织范围权限条件语句
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName tree字段
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     */
//    public String getCcylScaleCondition(User user, String treeFieldName, boolean isOper) {
//        if (user == null || treeFieldName == null || "".equals(treeFieldName))
//            return null;
//
//        List pmsScale;
//        List noPmsScale;
//
//        if (isOper) {
//            pmsScale = user.getHaveOperateCcylScale();
//            noPmsScale = user.getHaveNoOperateCcylScale();
//        } else {
//            pmsScale = user.getHaveQueryCcylScale();
//            noPmsScale = user.getHaveNoQueryCcylScale();
//        }
//
//        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
//        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);
//
//        StringBuffer Cond = new StringBuffer();
//        //如果没有团组织权限
//        if (pmsScale.size() == 0) {
//            return "(1=2)";
//        }
//
//        //有权限的条件组合
//        for (int i = 0; i < pmsScale.size(); i++) {
//            if (i != 0)
//                Cond.append(" or ");
//            else
//                Cond.append("(");
//            String treeId = ((CcylBO) pmsScale.get(i)).getTreeId();
//            Cond.append(treeFieldName)
//                    .append(" like '")
//                    .append(treeId)
//                    .append("%' ");
//        }
//        if (pmsScale.size() > 0)
//            Cond.append(")");
//        //排除权限条件组合（暂不支持）
//        for (int i = 0; i < noPmsScale.size(); i++) {
//            String treeId = ((CcylBO) noPmsScale.get(i)).getTreeId();
//            Cond.append(" and substr(")
//                    .append(treeFieldName)
//                    .append(",0,").append(treeId.length())
//                    .append(") <> '")
//                    .append(treeId)
//                    .append("' ");
//        }
//
//        return "(" + Cond.toString() + ")";
//    }

    /**
     * 构造工会组织范围权限条件语句
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName tree字段
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     */
//    public String getUnionScaleCondition(User user, String treeFieldName, boolean isOper) {
//        if (user == null || treeFieldName == null || "".equals(treeFieldName))
//            return null;
//
//        List pmsScale;
//        List noPmsScale;
//
//        if (isOper) {
//            pmsScale = user.getHaveOperateLabourScale();
//            noPmsScale = user.getHaveNoOperateLabourScale();
//        } else {
//            pmsScale = user.getHaveQueryLabourScale();
//            noPmsScale = user.getHaveNoQueryLabourScale();
//        }
//
//        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
//        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);
//
//        StringBuffer Cond = new StringBuffer();
//        //如果没有工会组织权限
//        if (pmsScale.size() == 0) {
//            return "(1=2)";
//        }
//
//        //有权限的条件组合
//        for (int i = 0; i < pmsScale.size(); i++) {
//            if (i != 0)
//                Cond.append(" or ");
//            else
//                Cond.append("(");
//            String treeId = ((UnionBO) pmsScale.get(i)).getTreeId();
//            Cond.append(treeFieldName)
//                    .append(" like '")
//                    .append(treeId)
//                    .append("%' ");
//        }
//        if (pmsScale.size() > 0)
//            Cond.append(")");
//        //排除权限条件组合（暂不支持）
//        for (int i = 0; i < noPmsScale.size(); i++) {
//            String treeId = ((UnionBO) noPmsScale.get(i)).getTreeId();
//            Cond.append(" and substr(")
//                    .append(treeFieldName)
//                    .append(",0,").append(treeId.length())
//                    .append(") <> '")
//                    .append(treeId)
//                    .append("' ");
//        }
//
//        return "(" + Cond.toString() + ")";
//    }

    /**
     * 根据岗位分类判断对某类岗位是否有权限
     *
     * @param user
     * @param postId
     * @return
     */

//    public boolean checkPmsPost(User user, String postId) {
//        PostDAO dao = (PostDAO) getBean("post_postDAO");
//        PostBO post = dao.findPost(postId);
//        boolean pmsPostType = false;
//        Hashtable pmsCodes = user.getPmsOperateCode();
//        List postTypes = (List) pmsCodes.get(PmsConstants.SYS_POSTTYPE_CODEID);
//        if (postTypes != null) {
//            //得到所有的岗位分类权限代码
//            String strPostType = "";
//            for (int i = 0; i < postTypes.size(); i++) {
//                CodeItemBO item = (CodeItemBO) postTypes.get(i);
//                if (item == null) continue;
//                strPostType += item.getItemId() + ",";
//            }
//            //如果非空才控制权限
//            if (post.getPostClass() != null && !"".equals(post.getPostClass())) {
//                if (strPostType.indexOf(post.getPostClass()) != -1) {
//                    pmsPostType = true;
//                }
//            } else {
//                pmsPostType = true;
//            }
//        }
//        return pmsPostType;
//    }

    /**
     * 构造后备人才代码范围权限条件语句
     * 主要是基础模块使用
     *
     * @param user          当前用户
     * @param treeFieldName 部门TreeId
     * @param houbeiField   后备人才字段
     * @param isOper        true 操作权限 false 查询权限
     * @return 带括号的条件语句
     * @throws BkmsException
     */
    public String getReserveScaleCondition(User user, String treeFieldName, String houbeiField, boolean isOper) throws BkmsException {
        if (user == null || treeFieldName == null || "".equals(treeFieldName) || houbeiField == null || "".equals(houbeiField))
            return null;

        List pmsHBCode = null;
        List pmsScale = null;
        List noPmsScale = null;
        Hashtable hash = null;

        if (isOper) {
            pmsScale = user.getHaveOperateOrgScale();
            noPmsScale = user.getHaveNoOperateOrgScale();
            hash = user.getPmsOperateCode();
        } else {
            pmsScale = user.getHaveQueryOrgScale();
            noPmsScale = user.getHaveNoQueryOrgScale();
            hash = user.getPmsQueryCode();
        }

        pmsScale = (List) Tools.filterNull(ArrayList.class, pmsScale);
        noPmsScale = (List) Tools.filterNull(ArrayList.class, noPmsScale);
        hash = (Hashtable) Tools.filterNull(Hashtable.class, hash);

        StringBuffer Cond = new StringBuffer();
        //如果没有机构权限
        if (pmsScale == null || pmsScale.size() == 0) {
            return "(1=2)";
        }

        if (pmsHBCode == null || pmsHBCode.size() == 0) {
            return "1=2";
        }

        //有权限的条件组合
        for (int i = 0; i < pmsScale.size(); i++) {
            if (i != 0)
                Cond.append(" or ");
            else
                Cond.append("(");
            String treeId = ((Org) pmsScale.get(i)).getTreeId();
            Cond.append(treeFieldName)
                    .append(" like '")
                    .append(treeId)
                    .append("%' ");
        }
        if (pmsScale.size() > 0)
            Cond.append(")");

        //排除权限条件组合
        if (noPmsScale != null && noPmsScale.size() > 0) {
            for (int i = 0; i < noPmsScale.size(); i++) {
                String treeId = ((Org) noPmsScale.get(i)).getTreeId();
                Cond.append(" and ")
                        .append(treeFieldName)
                        .append(" not like '")
                        .append(treeId)
                        .append("%' ");
            }
        }

        StringBuffer codeCond = new StringBuffer();
        codeCond.append(houbeiField).append(" is not null");
        for (int i = 0; i < pmsHBCode.size(); i++) {
            if (i != 0) {
                codeCond.append(" or ");
            }

            CodeItemBO item = (CodeItemBO) pmsHBCode.get(i);
            codeCond.append(houbeiField)
                    .append(" = '")
                    .append(item.getItemId())
                    .append("' ");
        }
        Cond.append(" and (").append(codeCond).append(") ");
        return "(" + Cond.toString() + ")";
    }

    /**
     * 得到有权限的查询机构的根节点
     *
     * @param user 当前用户
     * @return List Org对象
     */
    public List getOrgQryTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveQueryOrgScale();
    }

    /**
     * 得到有权限的党组织查询机构的根节点
     *
     * @param user 当前用户
     * @return List PartyBO对象
     */
    public List getPartyTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveQueryPartyScale();
    }

    /**
     * 得到有权限的团组织查询机构的根节点
     *
     * @param user 当前用户
     * @return List CcylBO对象
     */
    public List getCcylTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveQueryCcylScale();
    }

    /**
     * 得到有权限的工会组织查询机构的根节点
     *
     * @param user 当前用户
     * @return List  UnionBO对象
     */
    public List getUnionTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveQueryLabourScale();
    }

    /**
     * 得到有权限的薪酬查询机构的根节点
     *
     * @param user 当前用户
     * @return List WageUnitBO对象
     */
    public List getWageUnitQryTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveQueryWageUnitScale();
    }

    /**
     * 得到有权限的维护机构的根节点
     *
     * @param user 当前用户
     * @return List Org对象
     */
    public List getOrgOptTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveOperateOrgScale();
    }

    /**
     * 得到有权限的党组织维护机构的根节点
     *
     * @param user 当前用户
     * @return List PartyBO对象
     */
    public List getPartyOptTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveOperatePartyScale();
    }

    /**
     * 得到有权限的团组织维护机构的根节点
     *
     * @param user 当前用户
     * @return List CcylBO对象
     */
    public List getCcylOptTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveOperateCcylScale();
    }

    /**
     * 得到有权限的工会组织维护机构的根节点
     *
     * @param user 当前用户
     * @return List  UnionBO对象
     */
    public List getUnionOptTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveOperateLabourScale();
    }

    /**
     * 得到有权限的薪酬维护机构的根节点
     *
     * @param user 当前用户
     * @return List WageUnitBO对象
     */
    public List getWageUnitOptTreeRoot(User user) {
        if (user == null)
            return null;

        return user.getHaveOperateWageUnitScale();
    }

    /**
     * 得到有权机构
     *
     * @param type   - type 0行政机构   1党务机构  2 薪资
     * @param nodevo
     * @return OrgBO[]
     * @roseuid 4476A1B60109
     */
//    public NodeVO[] checkTreeNodes(String type, NodeVO[] nodevo, User user) {
//
//        List listNode = new ArrayList();
//        if ("0".equals(type)) {        //行政机构
//            List pmsOrgIds = user.getHaveQueryOrgScale();
//            List noPmsOrgIds = user.getHaveNoQueryOrgScale();
//
//            if (pmsOrgIds == null) return null;  //如果权限列表为空 ，返回null;
//
//            for (int i = 0; i < nodevo.length; i++) {
//                boolean orgFlag = false;
//                String orgTreeId = nodevo[i].getTreeId();
////有权限列表
//                for (int j = 0; j < pmsOrgIds.size(); j++) {
//                    OrgBO org = (OrgBO) pmsOrgIds.get(j);
//                    if (orgTreeId.startsWith(org.getTreeId())) {
//                        orgFlag = true;
//                        break;
//                    }
//                }
//                //如果有权限，则检测无权机构列表
//                if (orgFlag && noPmsOrgIds != null) {
//                    for (int j = 0; j < noPmsOrgIds.size(); j++) {
//                        OrgBO org = (OrgBO) noPmsOrgIds.get(j);
//                        if (orgTreeId.startsWith(org.getTreeId())) {
//                            orgFlag = false;
//                            break;
//                        }
//                    }
//                }
//                if (orgFlag)
//                    listNode.add(nodevo[i]);
//            }
//            return (NodeVO[]) listNode.toArray(new OrgBO[0]);
//        } else if ("1".equals(type)) {   //  党务机构
//            List pmsPartyIds = user.getHaveQueryPartyScale();
//            List noPmsPartyIds = user.getHaveNoQueryPartyScale();
//
//            if (pmsPartyIds == null) return null;  //如果权限列表为空 ，返回null;
//
//            for (int i = 0; i < nodevo.length; i++) {
//                boolean partyFlag = false;
//                String partyTreeId = nodevo[i].getTreeId();
////有权限列表
//                for (int j = 0; j < pmsPartyIds.size(); j++) {
//                    PartyBO party = (PartyBO) pmsPartyIds.get(j);
//                    if (partyTreeId.startsWith(party.getTreeId())) {
//                        partyFlag = true;
//                        break;
//                    }
//                }
//                //如果有权限，则检测无权机构列表
//                if (partyFlag && noPmsPartyIds != null) {
//                    for (int j = 0; j < noPmsPartyIds.size(); j++) {
//                        PartyBO party = (PartyBO) noPmsPartyIds.get(j);
//                        if (partyTreeId.startsWith(party.getTreeId())) {
//                            partyFlag = false;
//                            break;
//                        }
//                    }
//                }
//                if (partyFlag)
//                    listNode.add(nodevo[i]);
//            }
//            return (NodeVO[]) listNode.toArray(new PartyBO[0]);
//        } else {  //新姿机构
//            return null;
//        }
//    }


    /**
     * 检测查询人员数据的行、列、单元格权限
     * 根据传入的控制指标，返回与指标顺序对应的权限数组
     * 其中行权限数组内容为（0 有权限，1 无权限）
     * 列权限数组内容为（1 拒绝、2 读、3 写）
     * 单元格权限数组内容为（1 拒绝、2 读、3 写）
     *
     * @param user  当前用户
     * @param table 数据
     * @throws BkmsException
     */
    public void checkPersonRecord(User user, TableVO table) throws BkmsException {
        if (user == null || table == null || table.getHeader() == null
                || table.getRightData() == null || table.getRightData().length == 0)
            return;

        //权限控制指标 A001054,A001738,A001765
        String[] rightItem = table.getRightItem();
        //查询结果数据
        String[][] rightData = table.getRightData();
        //列头
        InfoItemBO[] header = table.getHeader();
        //列权限
        int[] colRight = this.checkHeader(user, header);
        //单元格权限
        int cellRight[][] = new int[rightData.length][header.length];
        for (int i = 0; i < rightData.length; i++) {
            int tmp[] = new int[header.length];
            for (int j = 0; j < header.length; j++) {
                tmp[j] = colRight[j];
            }
            cellRight[i] = tmp;
        }

        Hashtable hash = new Hashtable();
        for (int i = 0; i < rightItem.length; i++) {
            hash.put(rightItem[i], "" + i);
        }
        int personTypeIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.PERSON_TYPE_ITEM)));
        int deptTreeIdIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.PERSON_DEPT_TREEID_ITEM)));

        int len = rightData.length;

        List pmsOrgIds = user.getHaveOperateOrgScale();
        List noPmsOrgIds = user.getHaveNoOperateOrgScale();

        pmsOrgIds = (List) Tools.filterNull(ArrayList.class, pmsOrgIds);
        noPmsOrgIds = (List) Tools.filterNull(ArrayList.class, noPmsOrgIds);

        Hashtable pmsCodes = user.getPmsOperateCode();
        pmsCodes = (Hashtable) Tools.filterNull(Hashtable.class, pmsCodes);

        int[] recordlist = new int[len];
        for (int i = 0; i < len; i++) {
            String[] data = rightData[i];
            boolean sysCadreflag = false;//系统管理干部
            boolean sysPersonTypeflag = false; //人员类别
            boolean orgflag = false;
            boolean postTypeflag = false;//岗位分类
            String orgTree = Tools.filterNull(data[deptTreeIdIndex]);
            String sysPerTypeCode = Tools.filterNull(data[personTypeIndex]);

            if (orgTree == null || "".equals(orgTree))
                orgflag = true;      //如果该人不属于任何机构，则不控制其范围权限
            else {
                //根据有权机构判断
                for (int j = 0; j < pmsOrgIds.size(); j++) {
                    String tId = ((Org) pmsOrgIds.get(j)).getTreeId();
                    if (orgTree.startsWith(tId)) {
                        orgflag = true;
                        break;
                    }
                }
                if (orgflag) {
                    //根据排除机构判断
                    for (int j = 0; j < noPmsOrgIds.size(); j++) {
                        String tId = ((Org) noPmsOrgIds.get(j)).getTreeId();
                        if (orgTree.startsWith(tId)) {
                            orgflag = false;
                            break;
                        }
                    }

                }
            }
            String checkCodeSetArea = Tools.filterNull(StaticVariable.get(PmsConstants.PERSON_CHECK_CODESETID_AREA));
//            if (orgflag) {
            if (orgflag && checkCodeSetArea.length() > 0) {
                //如果机构范围有权限,则判断系统管理干部代码权限

                //如果机构范围有权限,则判断员工类别代码权限
                String code2 = StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID);
                if (checkCodeSetArea.indexOf(code2) >= 0) {
                    List pmsPerTypeCode = (List) pmsCodes.get(code2);
                    if (pmsPerTypeCode != null) {
                        for (int j = 0; j < pmsPerTypeCode.size(); j++) {
                            CodeItemBO item = (CodeItemBO) pmsPerTypeCode.get(j);
                            if (sysPerTypeCode.equals(item.getItemId())) {
                                sysPersonTypeflag = true;
                                break;
                            }
                        }
                    }
                }
              
            }

            
            
            if (orgflag && sysCadreflag && sysPersonTypeflag && postTypeflag)
                if (orgflag)
                    recordlist[i] = PmsConstants.PERMISSION_HAVE;
                else
                    recordlist[i] = PmsConstants.PERMISSION_NOT_HAVE;

        }
        table.setColRight(colRight);
        table.setRowRight(recordlist);
        table.setCellRight(cellRight);
    }

    /**
     * 检测查询机构数据的行、列、单元格权限
     * 根据传入的控制指标，返回与指标顺序对应的权限数组
     * 其中行权限数组内容为（0 有权限，1 无权限）
     * 列权限数组内容为（1 拒绝、2 读、3 写）
     * 单元格权限数组内容为（1 拒绝、2 读、3 写）
     *
     * @param user  当前用户
     * @param table 数据
     * @throws BkmsException
     */
    public void checkOrgRecord(User user, TableVO table) throws BkmsException {
        if (user == null || table == null || table.getHeader() == null
                || table.getRightData() == null || table.getRightData().length == 0)
            return;

        //权限控制指标 B001003
        String[] rightItem = table.getRightItem();
        //查询结果数据
        String[][] rightData = table.getRightData();
        //列头
        InfoItemBO[] header = table.getHeader();
        //列权限
        int[] colRight = this.checkHeader(user, header);
        //单元格权限
        int cellRight[][] = new int[rightData.length][header.length];
        for (int i = 0; i < rightData.length; i++) {
            int tmp[] = new int[header.length];
            for (int j = 0; j < header.length; j++) {
                tmp[j] = colRight[j];
            }
            cellRight[i] = tmp;
        }

        //取得权限指标的位置
        Hashtable hash = new Hashtable();
        for (int i = 0; i < rightItem.length; i++) {
            hash.put(rightItem[i], "" + i);
        }
        int orgTreeIdIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.ORG_TREEID_ITEM)));

        int len = rightData.length;

        List pmsOrgIds = user.getHaveOperateOrgScale();
        List noPmsOrgIds = user.getHaveNoOperateOrgScale();

        pmsOrgIds = (List) Tools.filterNull(ArrayList.class, pmsOrgIds);
        noPmsOrgIds = (List) Tools.filterNull(ArrayList.class, noPmsOrgIds);

        int[] recordlist = new int[len];
        for (int i = 0; i < len; i++) {
            boolean orgflag = false;
            String[] data = rightData[i];
            String orgTree = Tools.filterNull(data[orgTreeIdIndex]);
            if (orgTree == null || "".equals(orgTree))
                orgflag = true;      //如果orgTreewei为空，则不控制其范围权限
            else {
                //根据有权机构判断
                for (int j = 0; j < pmsOrgIds.size(); j++) {
                    String tId = ((Org) pmsOrgIds.get(j)).getTreeId();
                    if (orgTree.startsWith(tId)) {
                        orgflag = true;
                        break;
                    }
                }
                if (orgflag) {
                    //根据无权机构判断
                    for (int j = 0; j < noPmsOrgIds.size(); j++) {
                        String tId = ((Org) noPmsOrgIds.get(j)).getTreeId();
                        if (orgTree.startsWith(tId)) {
                            orgflag = false;
                            break;
                        }
                    }
                }
            }

            if (orgflag)
                recordlist[i] = PmsConstants.PERMISSION_HAVE; //有权限
            else
                recordlist[i] = PmsConstants.PERMISSION_NOT_HAVE; //无权限
        }
        table.setColRight(colRight);
        table.setRowRight(recordlist);
        table.setCellRight(cellRight);
    }

    /**
     * 检测查询岗位数据的行、列、单元格权限
     * 根据传入的控制指标，返回与指标顺序对应的权限数组
     * 其中行权限数组内容为（0 有权限，1 无权限）
     * 列权限数组内容为（1 拒绝、2 读、3 写）
     * 单元格权限数组内容为（1 拒绝、2 读、3 写）
     *
     * @param user  当前用户
     * @param table 数据
     * @throws BkmsException
     */
    public void checkPostRecord(User user, TableVO table) throws BkmsException {
        if (user == null || table == null || table.getHeader() == null
                || table.getRightData() == null || table.getRightData().length == 0)
            return;

        //权限控制指标C001701,C001001
        String[] rightItem = table.getRightItem();
        //查询结果数据
        String[][] rightData = table.getRightData();
        //列头
        InfoItemBO[] header = table.getHeader();
        //列权限
        int[] colRight = this.checkHeader(user, header);
        //单元格权限
        int cellRight[][] = new int[rightData.length][header.length];
        for (int i = 0; i < rightData.length; i++) {
            int tmp[] = new int[header.length];
            for (int j = 0; j < header.length; j++) {
                tmp[j] = colRight[j];
            }
            cellRight[i] = tmp;
        }

        //取得权限指标的位置
        Hashtable hash = new Hashtable();
        for (int i = 0; i < rightItem.length; i++) {
            hash.put(rightItem[i], "" + i);
        }
        int orgTreeIdIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.POST_TREEID_ITEM)));
        int postTypeIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.POST_TYPE_ITEM)));

        int len = rightData.length;

        List pmsOrgIds = user.getHaveOperateOrgScale();
        List noPmsOrgIds = user.getHaveNoOperateOrgScale();

        pmsOrgIds = (List) Tools.filterNull(ArrayList.class, pmsOrgIds);
        noPmsOrgIds = (List) Tools.filterNull(ArrayList.class, noPmsOrgIds);
        Hashtable pmsCodes = user.getPmsOperateCode();
        pmsCodes = (Hashtable) Tools.filterNull(Hashtable.class, pmsCodes);

        int[] recordlist = new int[len];
        for (int i = 0; i < len; i++) {
            boolean postflag = false;
            boolean postTypeFlag = false;
            String[] data = rightData[i];
            String orgTree = Tools.filterNull(data[orgTreeIdIndex]);
            String postTypeCode = Tools.filterNull(data[postTypeIndex]);
            if (orgTree == null || "".equals(orgTree))
                postflag = true;      //如果该人不属于任何机构，则不控制其范围权限
            else {
                //根据有权机构判断
                for (int j = 0; j < pmsOrgIds.size(); j++) {
                    String tId = ((Org) pmsOrgIds.get(j)).getTreeId();
                    if (orgTree.startsWith(tId)) {
                        postflag = true;
                        break;
                    }
                }
                if (postflag) {
                    //根据无权机构判断
                    for (int j = 0; j < noPmsOrgIds.size(); j++) {
                        String tId = ((Org) noPmsOrgIds.get(j)).getTreeId();
                        if (orgTree.startsWith(tId)) {
                            postflag = false;
                            break;
                        }
                    }
                }
            }
            if (postflag && postTypeFlag)
                recordlist[i] = PmsConstants.PERMISSION_HAVE; //有权限
            else
                recordlist[i] = PmsConstants.PERMISSION_NOT_HAVE; //无权限
        }
        table.setColRight(colRight);
        table.setRowRight(recordlist);
        table.setCellRight(cellRight);
    }

    /**
     * 检测通过党组织组织查询人员数据的行、列、单元格权限
     * 根据传入的控制指标，返回与指标顺序对应的权限数组
     * 其中行权限数组内容为（0 有权限，1 无权限）
     * 列权限数组内容为（1 拒绝、2 读、3 写）
     * 单元格权限数组内容为（1 拒绝、2 读、3 写）
     *
     * @param user  当前用户
     * @param table 数据
     * @throws BkmsException
     */
//    public void checkPartyRecord(User user, TableVO table) throws BkmsException {
//        if (user == null || table == null || table.getHeader() == null
//                || table.getRightData() == null || table.getRightData().length == 0)
//            return;
//
//        //权限控制指标
//        String[] rightItem = table.getRightItem();
//        //查询结果数据
//        String[][] rightData = table.getRightData();
//        //列头
//        InfoItemBO[] header = table.getHeader();
//        //列权限
//        int[] colRight = this.checkHeader(user, header);
//        //单元格权限
//        int cellRight[][] = new int[rightData.length][header.length];
//        for (int i = 0; i < rightData.length; i++) {
//            int tmp[] = new int[header.length];
//            for (int j = 0; j < header.length; j++) {
//                tmp[j] = colRight[j];
//            }
//            cellRight[i] = tmp;
//        }
//
//        //取得权限指标的位置
//        Hashtable hash = new Hashtable();
//        for (int i = 0; i < rightItem.length; i++) {
//            hash.put(rightItem[i], "" + i);
//        }
//        int partyTreeIdIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.PARTY_TREEID_ITEM)));
//
//        int len = rightData.length;
//
//        List pmsPartyIds = user.getHaveOperatePartyScale();
//        List noPmsPartyIds = user.getHaveNoOperatePartyScale();
//
//        pmsPartyIds = (List) Tools.filterNull(ArrayList.class, pmsPartyIds);
//        noPmsPartyIds = (List) Tools.filterNull(ArrayList.class, noPmsPartyIds);
//
//        int[] recordlist = new int[len];
//        for (int i = 0; i < len; i++) {
//            boolean partyflag = false;
//            String[] data = rightData[i];
//            String partyTree = Tools.filterNull(data[partyTreeIdIndex]);
//
//            if (partyTree == null || "".equals(partyTree))
//                partyflag = true;      //如果orgTreewei为空，则不控制其范围权限
//            else {
//                //根据有权机构判断
//                for (int j = 0; j < pmsPartyIds.size(); j++) {
//                    String tId = ((PartyBO) pmsPartyIds.get(j)).getTreeId();
//                    if (partyTree.startsWith(tId)) {
//                        partyflag = true;
//                        break;
//                    }
//                }
//                if (partyflag) {
//                    //根据无权机构判断（暂不支持）
//                    for (int j = 0; j < noPmsPartyIds.size(); j++) {
//                        String tId = ((PartyBO) noPmsPartyIds.get(j)).getTreeId();
//                        if (partyTree.startsWith(tId)) {
//                            partyflag = false;
//                            break;
//                        }
//                    }
//
//                }
//            }
//
//            if (partyflag)
//                recordlist[i] = PmsConstants.PERMISSION_HAVE; //有权限
//            else
//                recordlist[i] = PmsConstants.PERMISSION_NOT_HAVE; //无权限
//        }
//        table.setColRight(colRight);
//        table.setRowRight(recordlist);
//        table.setCellRight(cellRight);
//    }

    /**
     * 检测查询团组织数据的行、列、单元格权限
     * 根据传入的控制指标，返回与指标顺序对应的权限数组
     * 其中行权限数组内容为（0 有权限，1 无权限）
     * 列权限数组内容为（1 拒绝、2 读、3 写）
     * 单元格权限数组内容为（1 拒绝、2 读、3 写）
     *
     * @param user  当前用户
     * @param table 数据
     * @throws BkmsException
     */
//    public void checkCcylRecord(User user, TableVO table) throws BkmsException {
//        if (user == null || table == null || table.getHeader() == null
//                || table.getRightData() == null || table.getRightData().length == 0)
//            return;
//
//        //权限控制指标
//        String[] rightItem = table.getRightItem();
//        //查询结果数据
//        String[][] rightData = table.getRightData();
//        //列头
//        InfoItemBO[] header = table.getHeader();
//        //列权限
//        int[] colRight = this.checkHeader(user, header);
//        //单元格权限
//        int cellRight[][] = new int[rightData.length][header.length];
//        for (int i = 0; i < rightData.length; i++) {
//            int tmp[] = new int[header.length];
//            for (int j = 0; j < header.length; j++) {
//                tmp[j] = colRight[j];
//            }
//            cellRight[i] = tmp;
//        }
//
//        //取得权限指标的位置
//        Hashtable hash = new Hashtable();
//        for (int i = 0; i < rightItem.length; i++) {
//            hash.put(rightItem[i], "" + i);
//        }
//        int ccylTreeIdIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.CCYL_TREEID_ITEM)));
//
//        int len = rightData.length;
//
//        List pmsCcylIds = user.getHaveOperateCcylScale();
//        List noPmsCcylIds = user.getHaveNoOperateCcylScale();
//
//        pmsCcylIds = (List) Tools.filterNull(ArrayList.class, pmsCcylIds);
//        noPmsCcylIds = (List) Tools.filterNull(ArrayList.class, noPmsCcylIds);
//
//        int[] recordlist = new int[len];
//        for (int i = 0; i < len; i++) {
//            boolean ccylflag = false;
//            String[] data = rightData[i];
//            String ccylTree = Tools.filterNull(data[ccylTreeIdIndex]);
//
//            if (ccylTree == null || "".equals(ccylTree))
//                ccylflag = true;      //如果ccylTree为空，则不控制其范围权限
//            else {
//                //根据有权机构判断
//                for (int j = 0; j < pmsCcylIds.size(); j++) {
//                    String tId = ((CcylBO) pmsCcylIds.get(j)).getTreeId();
//                    if (ccylTree.startsWith(tId)) {
//                        ccylflag = true;
//                        break;
//                    }
//                }
//                if (ccylflag) {
//                    //根据无权机构判断（暂不支持）
//                    for (int j = 0; j < noPmsCcylIds.size(); j++) {
//                        String tId = ((CcylBO) noPmsCcylIds.get(j)).getTreeId();
//                        if (ccylTree.startsWith(tId)) {
//                            ccylflag = false;
//                            break;
//                        }
//                    }
//                }
//            }
//
//            if (ccylflag)
//                recordlist[i] = PmsConstants.PERMISSION_HAVE; //有权限
//            else
//                recordlist[i] = PmsConstants.PERMISSION_NOT_HAVE; //无权限
//        }
//        table.setColRight(colRight);
//        table.setRowRight(recordlist);
//        table.setCellRight(cellRight);
//    }

    /**
     * 检测查询工会组织数据的行、列、单元格权限
     * 根据传入的控制指标，返回与指标顺序对应的权限数组
     * 其中行权限数组内容为（0 有权限，1 无权限）
     * 列权限数组内容为（1 拒绝、2 读、3 写）
     * 单元格权限数组内容为（1 拒绝、2 读、3 写）
     *
     * @param user  当前用户
     * @param table 数据
     * @throws BkmsException
     */
//    public void checkUnionRecord(User user, TableVO table) throws BkmsException {
//        if (user == null || table == null || table.getHeader() == null
//                || table.getRightData() == null || table.getRightData().length == 0)
//            return;
//
//        //权限控制指标
//        String[] rightItem = table.getRightItem();
//        //查询结果数据
//        String[][] rightData = table.getRightData();
//        //列头
//        InfoItemBO[] header = table.getHeader();
//        //列权限
//        int[] colRight = this.checkHeader(user, header);
//        //单元格权限
//        int cellRight[][] = new int[rightData.length][header.length];
//        for (int i = 0; i < rightData.length; i++) {
//            int tmp[] = new int[header.length];
//            for (int j = 0; j < header.length; j++) {
//                tmp[j] = colRight[j];
//            }
//            cellRight[i] = tmp;
//        }
//
//        //取得权限指标的位置
//        Hashtable hash = new Hashtable();
//        for (int i = 0; i < rightItem.length; i++) {
//            hash.put(rightItem[i], "" + i);
//        }
//        int unionTreeIdIndex = Integer.parseInt((String) hash.get(StaticVariable.get(PmsConstants.UNION_TREEID_ITEM)));
//
//        int len = rightData.length;
//
//        List pmsUnionIds = user.getHaveOperateLabourScale();
//        List noPmsUnionIds = user.getHaveNoOperateLabourScale();
//
//        pmsUnionIds = (List) Tools.filterNull(ArrayList.class, pmsUnionIds);
//        noPmsUnionIds = (List) Tools.filterNull(ArrayList.class, noPmsUnionIds);
//
//        int[] recordlist = new int[len];
//        for (int i = 0; i < len; i++) {
//            boolean unionflag = false;
//            String[] data = rightData[i];
//            String unionTree = Tools.filterNull(data[unionTreeIdIndex]);
//
//            if (unionTree == null || "".equals(unionTree))
//                unionflag = true;      //如果unionTree为空，则不控制其范围权限
//            else {
//                //根据有权机构判断
//                for (int j = 0; j < pmsUnionIds.size(); j++) {
//                    String tId = ((UnionBO) pmsUnionIds.get(j)).getTreeId();
//                    if (unionTree.startsWith(tId)) {
//                        unionflag = true;
//                        break;
//                    }
//                }
//                if (unionflag) {
//                    //根据无权机构判断（暂不支持）
//                    for (int j = 0; j < noPmsUnionIds.size(); j++) {
//                        String tId = ((UnionBO) noPmsUnionIds.get(j)).getTreeId();
//                        if (unionTree.startsWith(tId)) {
//                            unionflag = false;
//                            break;
//                        }
//                    }
//
//                }
//            }
//
//            if (unionflag)
//                recordlist[i] = PmsConstants.PERMISSION_HAVE; //有权限
//            else
//                recordlist[i] = PmsConstants.PERMISSION_NOT_HAVE; //无权限
//        }
//        table.setColRight(colRight);
//        table.setRowRight(recordlist);
//        table.setCellRight(cellRight);
//    }

    /**
     * 检测用户指标权限
     * 根据传入的指标，返回与指标顺序对应的权限数组
     *
     * @param user   当前用户
     * @param header 查询结果指标数组
     * @return int[] 数组内容为（1 拒绝、2 读、3 写）
     * @throws BkmsException
     */
    public int[] checkHeader(User user, InfoItemBO[] header) throws BkmsException {
        if (user == null || header == null || header.length == 0)
            return null;

        Hashtable hash = user.getPmsInfoItem();
        //modify by wanglijuan 2015-7-20
        if (hash == null) {
//            return null;
            hash = new Hashtable();
        }
        //end 2015-7-20
        int[] ret = new int[header.length];
        String rule = StaticVariable.get(PmsConstants.FIELDDEFAULT_RULE); //得到当字段没有分配时取默认权限的规则
        for (int i = 0; i < header.length; i++) {
            String field = Tools.filterNull(header[i].getItemId());
            if (hash.containsKey(field)) {
                ret[i] = Integer.parseInt((String) hash.get(field));
            } else {
                ret[i] = Integer.parseInt(rule);
            }
        }
        return ret;
    }

    /**
     * 检测表的读写权限
     * 1.调用queryUserDatas得到用户对所有字段和表的权限。
     * 2。从hashtable取出表权限填充tableVO。
     * 3.如果表未进行分配，则根据设置的默认规则对表授权限
     *
     * @param user  当前用户
     * @param setId 指标集
     * @return int 内容为（1 拒绝、2 读、3 写）
     * @throws BkmsException
     */
    public int checkTable(User user, String setId) throws BkmsException {
        String rule = StaticVariable.get(PmsConstants.TABLEDEFAULT_RULE);  //得到当表没有分配时取默认权限的规则
        if (setId == null)
            return Integer.parseInt(rule); //当表没有设置权限时取系统默认的规则

        Hashtable hash = user.getPmsInfoSet();
        if (hash == null) {
            hash = new Hashtable();
        }
        String key = Tools.filterNull(setId);
        if (hash.containsKey(key))
            return Integer.parseInt((String) hash.get(key));
        else {
            return Integer.parseInt(rule); //当表没有设置权限时取系统默认的规则
        }
    }

    /**
     * 检测表的有无权限
     *
     * @param user  当前用户
     * @param setID 指标集
     * @return int （0 有权，1 无权）
     * @throws BkmsException
     */
    public int checkInfoSet(User user, String setID) throws BkmsException {
        if (user == null || setID == null || "".equals(setID))
            return PmsConstants.PERMISSION_NOT_HAVE;

        setID = Tools.filterNull(setID);

        Hashtable hash = user.getPmsInfoSet();
        if (hash == null) {
            hash = new Hashtable();
        }
        if (hash.containsKey(setID)) {
            if (String.valueOf(PmsConstants.PERMISSION_REFUSE).equals(hash.get(setID))) //拒绝
                return PmsConstants.PERMISSION_NOT_HAVE;
            else
                return PmsConstants.PERMISSION_HAVE;
        } else {
            String rule = StaticVariable.get(PmsConstants.TABLEDEFAULT_RULE);
            if (String.valueOf(PmsConstants.PERMISSION_REFUSE).equals(rule))          //如果默认规则为“拒绝”权限，则返回“1”
                return PmsConstants.PERMISSION_NOT_HAVE;
            else
                return PmsConstants.PERMISSION_HAVE;
        }
    }

    /**
     * 检测对菜单是否有权限
     *
     * @param user      当前用户
     * @param OperateID 菜单编码
     * @return int 0 有权限 1 无权限
     */
    public int checkOperate(User user, String OperateID) {
        if (user == null || OperateID == null || "".equals(OperateID))
            return PmsConstants.PERMISSION_NOT_HAVE;

        List list;
        list = user.getPmsOperateList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                OperateBO oper = (OperateBO) list.get(i);
                {
                    if (oper.getOperateId().equals(OperateID))
                        return PmsConstants.PERMISSION_HAVE;
                }
            }
        }
        return PmsConstants.PERMISSION_NOT_HAVE;
    }

    /**
     * 判断对某个机构treeId是否有权限
     *
     * @param user      当前用户
     * @param treeId    机构TreeId
     * @param scaleFlag 1 维护 0 查询
     * @return int 0 有权限 1 无权限
     */
    public int checkOrgTreeId(User user, String treeId, String scaleFlag) {
        if (user == null || treeId == null || "".equals(treeId) || scaleFlag == null || "".equals(scaleFlag))
            return PmsConstants.PERMISSION_NOT_HAVE;

        List haveOrgScale;
        List haveNoOrgScale;
        if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
            haveOrgScale = user.getHaveOperateOrgScale();
            haveNoOrgScale = user.getHaveNoOperateOrgScale();
        } else {
            haveOrgScale = user.getHaveQueryOrgScale();
            haveNoOrgScale = user.getHaveNoQueryOrgScale();
        }
        int pms = PmsConstants.PERMISSION_NOT_HAVE;
        //先在有权限范围内判断，如果有权限，则在无权限范围内判断
        if (haveOrgScale != null && haveOrgScale.size() > 0) {
            for (int i = 0; i < haveOrgScale.size(); i++) {
                Org org = (Org) haveOrgScale.get(i);
                if (treeId.startsWith(org.getTreeId())) {
                    pms = PmsConstants.PERMISSION_HAVE;
                    break;
                }
            }
            if (pms == PmsConstants.PERMISSION_HAVE) {
                if (haveNoOrgScale != null && haveNoOrgScale.size() > 0) {
                    for (int i = 0; i < haveNoOrgScale.size(); i++) {
                        Org org = (Org) haveNoOrgScale.get(i);
                        if (treeId.startsWith(org.getTreeId())) {
                            pms = PmsConstants.PERMISSION_NOT_HAVE;
                            break;
                        }
                    }
                }
            }
        }
        return pms;
    }

    /**
     * 判断对某个党组织treeId是否有权限
     *
     * @param user      当前用户
     * @param treeId    党组织TreeId
     * @param scaleFlag 1 维护 0 查询
     * @return int 0 有权限 1 无权限
     */
//    public int checkPartyTreeId(User user, String treeId, String scaleFlag) {
//        if (user == null || treeId == null || "".equals(treeId) || scaleFlag == null || "".equals(scaleFlag))
//            return PmsConstants.PERMISSION_NOT_HAVE;
//
//        List havePartyScale;
//        List haveNoPartyScale;
//        if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
//            havePartyScale = user.getHaveOperatePartyScale();
//            haveNoPartyScale = user.getHaveNoOperatePartyScale();
//        } else {
//            havePartyScale = user.getHaveQueryPartyScale();
//            haveNoPartyScale = user.getHaveNoQueryPartyScale();
//        }
//        int pms = PmsConstants.PERMISSION_NOT_HAVE;
//        //先在有权限范围内判断，如果有权限，则在无权限范围内判断
//        if (havePartyScale != null && havePartyScale.size() > 0) {
//            for (int i = 0; i < havePartyScale.size(); i++) {
//                PartyBO party = (PartyBO) havePartyScale.get(i);
//                if (treeId.startsWith(party.getTreeId())) {
//                    pms = PmsConstants.PERMISSION_HAVE;
//                    break;
//                }
//            }
//            if (pms == PmsConstants.PERMISSION_HAVE) {
//                //注:党组织目前不支持排除权限
//                if (haveNoPartyScale != null && haveNoPartyScale.size() > 0) {
//                    for (int i = 0; i < haveNoPartyScale.size(); i++) {
//                        PartyBO party = (PartyBO) haveNoPartyScale.get(i);
//                        if (treeId.startsWith(party.getTreeId())) {
//                            pms = PmsConstants.PERMISSION_NOT_HAVE;
//                            break;
//                        }
//                    }
//                }
//
//            }
//        }
//        return pms;
//    }

    /**
     * 判断对某个团组织treeId是否有权限
     *
     * @param user      当前用户
     * @param treeId    团组织TreeId
     * @param scaleFlag 1 维护 0 查询
     * @return int 0 有权限 1 无权限
     */
//    public int checkCcylTreeId(User user, String treeId, String scaleFlag) {
//        if (user == null || treeId == null || "".equals(treeId) || scaleFlag == null || "".equals(scaleFlag))
//            return PmsConstants.PERMISSION_NOT_HAVE;
//        List haveCcylScale;
//        List haveNoCcylScale;
//        if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
//            haveCcylScale = user.getHaveOperateCcylScale();
//            haveNoCcylScale = user.getHaveNoOperateCcylScale();
//        } else {
//            haveCcylScale = user.getHaveQueryCcylScale();
//            haveNoCcylScale = user.getHaveNoQueryCcylScale();
//        }
//        int pms = PmsConstants.PERMISSION_NOT_HAVE;
//        //先在有权限范围内判断，如果有权限，则在无权限范围内判断
//        if (haveCcylScale != null && haveCcylScale.size() > 0) {
//            for (int i = 0; i < haveCcylScale.size(); i++) {
//                CcylBO ccyl = (CcylBO) haveCcylScale.get(i);
//                if (treeId.startsWith(ccyl.getTreeId())) {
//                    pms = PmsConstants.PERMISSION_HAVE;
//                    break;
//                }
//            }
//            if (pms == PmsConstants.PERMISSION_HAVE) {
//                //注:团组织目前不支持排除权限
//                if (haveNoCcylScale != null && haveNoCcylScale.size() > 0) {
//                    for (int i = 0; i < haveNoCcylScale.size(); i++) {
//                        CcylBO ccyl = (CcylBO) haveNoCcylScale.get(i);
//                        if (treeId.startsWith(ccyl.getTreeId())) {
//                            pms = PmsConstants.PERMISSION_NOT_HAVE;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        return pms;
//    }

    /**
     * 判断对某个工会组织treeId是否有权限
     *
     * @param user      当前用户
     * @param treeId    工会组织TreeId
     * @param scaleFlag 1 维护 0 查询
     * @return int 0 有权限 1 无权限
     */
//    public int checkUnionTreeId(User user, String treeId, String scaleFlag) {
//        if (user == null || treeId == null || "".equals(treeId) || scaleFlag == null || "".equals(scaleFlag))
//            return PmsConstants.PERMISSION_NOT_HAVE;
//
//        List haveUnionScale;
//        List haveNoUnionScale;
//        if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
//            haveUnionScale = user.getHaveOperateLabourScale();
//            haveNoUnionScale = user.getHaveNoOperateLabourScale();
//        } else {
//            haveUnionScale = user.getHaveQueryLabourScale();
//            haveNoUnionScale = user.getHaveNoQueryLabourScale();
//        }
//        int pms = PmsConstants.PERMISSION_NOT_HAVE;
//        //先在有权限范围内判断，如果有权限，则在无权限范围内判断
//        if (haveUnionScale != null && haveUnionScale.size() > 0) {
//            for (int i = 0; i < haveUnionScale.size(); i++) {
//                UnionBO union = (UnionBO) haveUnionScale.get(i);
//                if (treeId.startsWith(union.getTreeId())) {
//                    pms = PmsConstants.PERMISSION_HAVE;
//                    break;
//                }
//            }
//            if (pms == PmsConstants.PERMISSION_HAVE) {
//                //注:工会组织目前不支持排除权限
//                if (haveNoUnionScale != null && haveNoUnionScale.size() > 0) {
//                    for (int i = 0; i < haveNoUnionScale.size(); i++) {
//                        UnionBO union = (UnionBO) haveNoUnionScale.get(i);
//                        if (treeId.startsWith(union.getTreeId())) {
//                            pms = PmsConstants.PERMISSION_NOT_HAVE;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        return pms;
//    }

    /**
     * 判断对某个薪酬组织treeId是否有权限
     *
     * @param user      当前用户
     * @param treeId    薪酬组织TreeId
     * @param scaleFlag 1 维护 0 查询
     * @return int 0 有权限 1 无权限
     */
    public int checkWageUnitTreeId(User user, String treeId, String scaleFlag) {
//        if (user == null || treeId == null || "".equals(treeId) || scaleFlag == null || "".equals(scaleFlag))
//            return PmsConstants.PERMISSION_NOT_HAVE;
//
//        List haveWageUnitScale;
//        List haveNoWageUnitScale;
//        if (PmsConstants.OPERATE_SCALE.equals(scaleFlag)) {
//            haveWageUnitScale = user.getHaveOperateWageUnitScale();
//            haveNoWageUnitScale = user.getHaveNoOperateWageUnitScale();
//        } else {
//            haveWageUnitScale = user.getHaveQueryWageUnitScale();
//            haveNoWageUnitScale = user.getHaveNoQueryWageUnitScale();
//        }
//        int pms = PmsConstants.PERMISSION_NOT_HAVE;
//        //先在有权限范围内判断，如果有权限，则在无权限范围内判断
//        if (haveWageUnitScale != null && haveWageUnitScale.size() > 0) {
//            for (int i = 0; i < haveWageUnitScale.size(); i++) {
//                WageUnitBO wage = (WageUnitBO) haveWageUnitScale.get(i);
//                if (treeId.startsWith(wage.getTreeId())) {
//                    pms = PmsConstants.PERMISSION_HAVE;
//                    break;
//                }
//            }
//            if (pms == PmsConstants.PERMISSION_HAVE) {
//                //注:目前不支持排除权限
//                if (haveNoWageUnitScale != null && haveNoWageUnitScale.size() > 0) {
//                    for (int i = 0; i < haveNoWageUnitScale.size(); i++) {
//                        WageUnitBO wage = (WageUnitBO) haveNoWageUnitScale.get(i);
//                        if (treeId.startsWith(wage.getTreeId())) {
//                            pms = PmsConstants.PERMISSION_NOT_HAVE;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        return pms;
        return 0;
    }

    /**
     * 查询有权限指标项
     *
     * @param user      当前用户
     * @param InfoItems InfoItemBO对象
     * @param pmsType   CellVO.PERMISSION_READ  ,CellVO.PERMISSION_WRITE
     * @return List 有权限指标项
     */
    public List queryUserInfoItems(User user, List InfoItems, int pmsType) throws BkmsException {
        if (user == null || InfoItems == null || InfoItems.isEmpty())
            return null;

        int len = InfoItems.size();
        List list = new ArrayList();
        Hashtable hash = user.getPmsInfoItem();
        if (hash == null) {
            hash = new Hashtable();
        }
        String rule = StaticVariable.get(PmsConstants.FIELDDEFAULT_RULE); //得到当字段没有分配时取默认权限的规则
        for (int i = 0; i < len; i++) {
            String field = Tools.filterNull(((InfoItemBO) InfoItems.get(i)).getItemId());
            int type;
            //得到内存中的权限
            if (hash.containsKey(field)) {
                type = Integer.parseInt((String) hash.get(field));
            } else {
                type = Integer.parseInt(rule);
            }
            //写权限
            if (PmsConstants.PERMISSION_WRITE == pmsType && pmsType == type) {
                list.add(InfoItems.get(i));
            }
            //读权限   所有大于写权限的权限
            if (PmsConstants.PERMISSION_READ == pmsType && type >= pmsType) {
                list.add(InfoItems.get(i));
            }
        }
        return list;
    }

    public int checkSpecialTable(User user, String setId, String checkedPersonId) throws BkmsException {
        int setRight = checkTable(user, setId);
        return setRight;
    }
}
