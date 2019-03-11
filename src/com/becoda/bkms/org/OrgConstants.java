package com.becoda.bkms.org;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-17
 * Time: 16:37:42
 * To change this template use File | Settings | File Templates.
 */
public class OrgConstants {
    public static final String ROOT_ORG_ID = "-1";

    //    public static final String ORG_TYPE_INSIDE = "0891300";//内设机构

    public static final String OBJECT = "ORG_SESSION_OBJECT";

    public static String DEFAULT_QUERY_PARTYMEMBER = "153";
    public static String DEFAULT_QUERY_GROUPMEMBER = "1000117";
    public static String DEFAULT_QUERY_LABOURMEMBER = "1000118";
    public static String DEFAULT_QUERY_POST = "139";
    public static String DEFAULT_QUERY_ORG = "135";
    public static String DEFAULT_QUERY_PERSON = "156";
    public static String DEFAULT_QUERY_BACKPERSON = "155";
    public static String DEFAULT_QUERY_PARTY = "158";
    public static String DEFAULT_QUERY_DISSPARTY = "159";
    public static String DEFAULT_QUERY_GROUP = "160";
    public static String DEFAULT_QUERY_DISSGROUP = "161";
    public static String DEFAULT_QUERY_LABOUR = "166";
    public static String DEFAULT_QUERY_DISSLABOUR = "167";
    public static String DEFAULT_QUERY_PARTYFEE = "163";
    public static String DEFAULT_QUERY_ORGAREA = "165";

    //机构性质
    public static final String NATURE_ZH = "3092400315";//总行
    public static final String NATURE_FH = "3092400316";//分支机构
    public static final String NATURE_NS = "3092400317";//内设部门
    public static final String NATURE_XN = "3092400318";//虚拟机构
    public static final String NATURE_FH_1 = "3092400715";//一级分行
    public static final String NATURE_FH_2 = "3092400716";//次一级分行
    public static final String NATURE_FH_3 = "3092400717";//总行直属支行
    public static final String NATURE_FH_4 = "3092400718";//二级分行
    public static final String NATURE_FH_5 = "3092400719";//一级支行
    public static final String NATURE_FH_6 = "3092400720";//二级分行管辖支行
    public static final String NATURE_FH_7 = "3092400721";//二级支行


    //机构层级
    public static String LEVEL_ZX = "0895400307";//中心

    public final static String ORG_STOCK_FULL = "08871"; // 全资
    public final static String ORG_STOCK_CONTROL = "08872"; // 控股
    public final static String ORG_STOCK_SHARE = "08873"; // 控股
    //日志
    public static String MODULE_NAME = "机构管理";
    //机构变动类型
    public static final String CHANGENAME = "08901";//更名
    public static final String CHANGEADDRESS = "08906";//变址
    public static final String UPLEVEL = "08902";//升格
    public static final String DOWNLEVEL = "08903";//降格
    public static final String SUPERORGCHANGE = "08904";//主管机构变动
    public static final String HEADERCHANGE = "08907";//主要负责人变动
    public static final String UNIONORG = "08900";//合并
    public static final String RANGECHANGE = "08908";//经营范围变动

}
