package com.becoda.bkms.qry;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-19
 * Time: 10:42:40
 * 查询管理中用到的静态常量
 */
public class QryConstants {
    //类别关联表
    public static final Hashtable ht = new Hashtable();

    static {
        ht.put("AB", "@.A001705=#.ID");
        ht.put("AC", "@.A001715=#.ID");
        ht.put("AF", "@.A001710=#.ID");
    }

    public static final String SQL_SELECT_PART = "SQL_SELECT_PART";
    public static final String SQL_FROM_PART = "SQL_FROM_PART";
    public static final String SQL_WHERE_PART = "SQL_WHERE_PART";
    public static final String SQL_ORDER_PART = "SQL_ORDER_PART";
    public static final String SQL_SCALE_PART = "SQL_SCALE_PART";
    public static final String SQL_FULL = "SQL_FULL";

    /**
     * 放到Session里的对象,列表页面使用
     */
    public static final String OBJECT = "OBJECT";


    public static final String QRY_CODE_REVERSE = "";

    public static final String DATA_TYPE_INT = "1";
    public static final String DATA_TYPE_FLOAT = "2";
    public static final String DATA_TYPE_STRING = "3";
    public static final String DATA_TYPE_REMARK = "4";
    public static final String DATA_TYPE_DATE = "5";
    public static final String DATA_TYPE_CODE = "6";
    public static final String DATA_TYPE_ORG = "7";
    public static final String DATA_TYPE_PERSON = "8";
    public static final String DATA_TYPE_COMPUTE = "9";
    public static final String DATA_TYPE_CLOB = "10";
    public static final String DATA_TYPE_POST = "11";
    public static final String DATA_TYPE_PARTY = "12";
    public static final String DATA_TYPE_WAGE = "13";
    public static final String DATA_TYPE_IMAG = "14";
    public static final String DATA_TYPE_DATE6 = "15";

    /**
     * 禁用
     */
//    public static String STATUS_BAN = "0";
    /**
     * 启用
     */
//    public static String STATUS_OPEN = "1";

    /**
     * 发布
     */
    //    public static String ISSUE_YES = "1";
    /**
     * 没发布
     */
//    public static String ISSUE_NO = "0";

//    public static String RS_TYPE_SINGLE = "1";
//    public static String RS_TYPE_MANY = "2";

    //高级查询权限过滤类型常量
    //高级查询权限过滤类型常量
    public static final String PMS_TYPE_PERSON = "A";   //人员
    public static final String PMS_TYPE_ORG = "B";      //机构
    public static final String PMS_TYPE_POST = "C";     //岗位
    public static final String PMS_TYPE_PARTY = "D";    //党
    public static final String PMS_TYPE_CCYL = "E";     //团
    public static final String PMS_TYPE_UNION = "F";    //工会
    //高级查询权限过滤类型常量
    public static final String PMS_TYPE_PERSON_PARTY = "A_P";  //从党组织查人的类型
    public static final String PMS_TYPE_PERSON_CCYL = "A_C";   //从团组织查询人的类型
    public static final String PMS_TYPE_PERSON_UNION = "A_U";  //从工会查询人的类型
    //高级查询权限过滤类型常量----------结束

//    public static String SET_PROPERTY_SYS = "1";
//    public static String SET_PROPERTY_USER = "2";
//    public static String SET_PROPERTY_VIEW = "3";

    public static String QRYSQL_qrySql = "QRYSQL_qrySql";//：     完整的sql
    public static String QRYSQL_showField = "QRYSQL_showField";//:   select 与 from 之间的内容 A001.id,A001.A001715,A001.A001511
    public static String QRYSQL_from = "QRYSQL_from";//:   	    from 与where 之间 不包含 from与where
    public static String QRYSQL_where = "QRYSQL_where";//:       不包含where
    public static String QRYSQL_order = "QRYSQL_order";//:       order后的
    public static String QRYSQL_hash = "QRYSQL_hash";//:       order后的
    public static String QRYSQL_staicVO = "QRYSQL_staicVO";//:       order后的
    public static String QRYSQL_qryVO = "QRYSQL_qryVO";//:       order后的


}
