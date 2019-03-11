package com.becoda.bkms.pms;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-28
 * Time: 16:25:51
 * To change this template use File | Settings | File Templates.
 */
public class PmsConstants {
    /**
     * 用户状态
     */
    public final static String STATUS_BAN = "0";  //禁用
    public final static String STATUS_OPEN = "1";//启用

    /**
     * 人员类别
     */
    public final static String PERSON_TYPE_ITEM = "PERSON_TYPE_ITEM";//A001054

    /**
     * 部门TreeId
     */
    public final static String PERSON_DEPT_TREEID_ITEM = "PERSON_DEPT_TREEID_ITEM";//A001738

    //权限检测人员类别包括的所有项目
    public final static String PERSON_CHECK_CODESETID_AREA= "PERSON_CHECK_CODESETID_AREA" ;

    /**
     * 机构TreeId
     */
    public final static String ORG_TREEID_ITEM = "ORG_TREEID_ITEM";//B001003
    /**
     * 机构TreeId
     */
    public final static String POST_TREEID_ITEM = "POST_TREEID_ITEM";//C001701
    /**
     * 岗位
     */
    public final static String POST_TYPE_ITEM = "POST_TYPE_ITEM";//C001001
   
    /**
     * 人员权限控制指标
     */
    public final static String COTROL_ITEMS_PERSON = "COTROL_ITEMS_PERSON";//A001054,A001738,A001765
    /**
     * 机构权限控制指标
     */
    public final static String COTROL_ITEMS_ORG = "COTROL_ITEMS_ORG";//B001003

    /**
     * 员工类别
     */
    public final static String SYS_PERSONTYPE_CODE_SETID = "SYS_PERSONTYPE_CODE_SETID";//0135

    /**
     * 设备监测
     */
    public final static String SBJC_VALUE = "SBJC_VALUE";
    
    /**
     * 能效监测开关
     */
    public final static String NXJC_SWITCH = "NXJC_SWITCH";





    /**
     * 权限管理菜单
     */
    public final static String MENU_PMS = "MENU_PMS";//0810

    public final static String TABLEDEFAULT_RULE = "TABLEDEFAULT_RULE";//指标集默认为只读权限  2
    public final static String FIELDDEFAULT_RULE = "FIELDDEFAULT_RULE";//指标项默认为只读权限  2

    /**
     * 权限类型
     * 1：拒绝
     * 2：可读
     * 3：可写
     */
    public final static int PERMISSION_REFUSE = 1;
    public final static int PERMISSION_READ = 2;
    public final static int PERMISSION_WRITE = 3;
    /**
     * 信息项类型
     * 0：指标集
     * 1：指标项
     */
    public final static String INFO_SET_TYPE = "0";
    public final static String INFO_ITEM_TYPE = "1";

    public final static String SUPER_MANAGER_ROLE_ID = "1";//超级管理员
    public final static String ROLE_MANAGER = "2";//系统管理员
    public final static String ROLE_NORMAL = "3";//普通用户
    /**
     * 系统管理员
     */
    public final static String IS_SYS_MANAGER = "1";
    /**
     * 非系统管理员
     */
    public final static String IS_NOT_SYS_MANAGER = "0";

    /**
     * 领导人员
     */
    public final static String IS_HR_LEADER = "1";
    /**
     * 非领导人员
     */
    public final static String IS_NOT_HR_LEADER = "0";

    /**
     * 经理人
     */
    public final static String IS_DEPT_LEADER = "1";
    /**
     * 非经理人
     */
    public final static String IS_NOT_DEPT_LEADER = "0";
    /**
     * 业务用户
     */
    public final static String IS_BUSINESS_USER = "1";

    /**
     * 非业务用户
     */
    public final static String IS_NOT_BUSINESS_USER = "0";

    public final static String SYSLOGIN_AUTO = "0";  //自动登录系统
    public final static String SYSLOGIN_BUSINESS = "1";   //业务系统
//    public final static String SYSLOGIN_ORG = "2";   //机构领导系统
    //    public final static String SYSLOGIN_DEPT = "3";   //部门领导系统
    public final static String SYSLOGIN_SELF = "4";   //自助领导系统
    public final static String SYSLOGIN_HR = "5";   //HR领导系统

    public final static String CODE_TYPE_ORG = "DEPT";   //机构类型
    public final static String CODE_TYPE_WAGE = "WAGE";   //薪酬机构

    /**
     * 部门TreeId
     */
    public final static String HQ_PERSON_DEPT_TREEID_ITEM = "deptTreeId";

    /**
     * 人员类别
     */
    public final static String HQ_PERSON_TYPE_ITEM = "personType";
    

    public final static String MODULE_NAME = "权限管理";

    public final static String QUERY_SCALE = "0";//查询权限
    public final static String OPERATE_SCALE = "1";//维护权限

    public final static String SCALE_USE = "1";//有权范围
    public final static String SCALE_REFUSE = "0";//排除范围

    public final static String MENU_TYPE_MENU = "1";//菜单
    public final static String MENU_TYPE_BUTTON = "2";//按钮

    public final static String MENU_NORMAL = "0";//普通菜单
    public final static String MENU_ABNORMAL = "1";//管理员菜单

    public final static int PERMISSION_HAVE = 0;//有权限
    public final static int PERMISSION_NOT_HAVE = 1;//无权限

    public final static int TREE_LENGTH = 4;//treeId层级长度
    public final static int TREE_STEP_LENGTH = 1;//treeId步长

    public final static int INFOSET_LENGTH = 4;//treeId步长

}
