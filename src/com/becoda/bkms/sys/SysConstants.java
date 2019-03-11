package com.becoda.bkms.sys;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-2-23
 * Time: 16:00:57
 * To change this template use File | Settings | File Templates.
 */
public class SysConstants {
    /**
     * 指标项（集）状态*
     */
    public final static String INFO_STATUS_BAN = "0";//指标项（集）禁用
    public final static String INFO_STATUS_OPEN = "1";//指标项（集）启用

    public final static String INFO_SET_PROPERTY_HIDE = "0";//系统隐藏指标集
    public final static String INFO_SET_PROPERTY_SYS = "1";//系统指标集
    public final static String INFO_SET_PROPERTY_USER = "2";//用户指标集
    public final static String INFO_SET_PROPERTY_GB = "3";//国标指标集

    public final static String INFO_SET_RS_TYPE_SINGLE = "1";
    public final static String INFO_SET_RS_TYPE_MANY = "2";
    /**
     * 指标项数据类型*
     */
    public final static String INFO_ITEM_IS_NULL = "0";// 可为空
    public final static String INFO_ITEM_IS_NOTNULL = "1";//不可为空

    public final static String INFO_ITEM_DATA_TYPE_INT = "1";   //整数
    public final static String INFO_ITEM_DATA_TYPE_FLOAT = "2"; //小数
    public final static String INFO_ITEM_DATA_TYPE_STRING = "3";//文本
    public final static String INFO_ITEM_DATA_TYPE_REMARK = "4";  //文本域
    public final static String INFO_ITEM_DATA_TYPE_DATE8 = "5";   //yyyy-MM-dd 8位日期
    public final static String INFO_ITEM_DATA_TYPE_CODE = "6";  //代码
    public final static String INFO_ITEM_DATA_TYPE_DATE6 = "7";   //yyyy-MM6位日期
    public final static String INFO_ITEM_DATA_TYPE_INFO = "8";      //  指标类型
    public final static String INFO_ITEM_DATA_TYPE_COMPUTE = "9"; //计算
    public final static String INFO_ITEM_DATA_TYPE_CLOB = "10";      //  大文本
    public final static String INFO_ITEM_DATA_TYPE_ATTACH = "11";   //附件类型
    public final static String INFO_ITEM_DATA_TYPE_IMAG = "14";   //照片类型


    public final static String INFO_ITEM_ISSUE_NO = "0"; //未发布
    public final static String INFO_ITEM_ISSUE_YES = "1"; //发布

    public final static String INFO_ITEM_EDIT_PROP_HIDE = "1";
    public final static String INFO_ITEM_EDIT_PROP_READONLY = "2";
    public final static String INFO_ITEM_EDIT_PROP_WRITABLE = "3";

//    public final static String INFO_ITEM_PROPERTY_CONTROL_HIDE = "1";
//    public final static String INFO_ITEM_PROPERTY_CONTROL_SHOW = "2";

    public final static String INFO_ITEM_PROPERTY_GB = "2";
    public final static String INFO_ITEM_PROPERTY_SYSTEM = "3";
    public final static String INFO_ITEM_PROPERTY_EXTEND = "4";

    public final static String INFO_ITEM_SELF = "1";
    public final static String INFO_ITEM_SYSTEM = "0";

    public final static String YESORNO_NAME = "yesorno_code";

    public static final String OPER_ADD = "add";
    public static final String OPER_MODIFY = "modify";
    public static final String OPER_DELETE = "delete";

//    public final static String SYS_SAVE_ABILITY_SET = "A051,A850,A220,A225,A230";

    /**
     * 最底层
     */
    public static String CODE_LAYER_LEAF = "1";
    /**
     * 任意层
     */
    public static String CODE_LAYER_EVERY = "0";
}
