package com.becoda.bkms.emp;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-15
 * Time: 14:45:58
 * To change this template use File | Settings | File Templates.
 */
public class EmpConstants {
    public static String DEFAULT_QUERY_PERSON = "156";

    public static final String MODULE_NAME = "人员管理";

    public static final String ADJUST_unreceived = "1";   //  未接收
    public static final String ADJUST_modfied = "2";     //   已修改
    public static final String ADJUST_received = "3";    //   已接收
    public static final String ADJUST_inorg = "1";       //   机构内调动
    public static final String ADJUST_betweenorg = "2";  //   系统内调动

    public static final String FUNCTION_INFO_MANA = "info_mana";    //信息维护
    public static final String FUNCTION_DISMISS = "dismiss";    //人员减员
    public static final String FUNCTION_BATCH_ADD = "batch_add";    //批量增加
    public static final String FUNCTION_BATCH_UPDATE = "batch_update";    //获量修改
    public static final String FUNCTION_EMP_VIEW = "view";    //查看

    public static final String PERSON_TYPE_CONT = "013511";    //合同工
    public static final String PERSON_TYPE_AGENT = "013513";    //代理工
    public static final String PERSON_TYPE_SHORT = "0135000323";    //临时工
    public static final String PERSON_TYPE_LABEL = "0135000324";    //劳务工

    public static final String ADJUST_diaodong = "020510";    //调动
    public static final String ADJUST_diaohui = "0205400749";    //调回
    public static final String ADJUST_qitai = "020560";    //其他
    //减员类别
    public static final String DISS_TYPE_DC = "703010021007";    //调出
    public static final String DISS_TYPE_CZ = "703010021008";    //辞职
    public static final String DISS_TYPE_TX = "703010021006";    //退休
    public static final String DISS_TYPE_QT = "703010021009";    //劝退
    public static final String DISS_TYPE_KC = "703010021010";    //开除
    public static final String DISS_TYPE_CT = "703010021011";    //辞退
    public static final String DISS_TYPE_DEAD = "703010021012";  //死亡
    public static final String DISS_TYPE_OTHER = "703010021013";  //其他
    //黑名单用
    public static final String BLACK_TYPE_CT = "60060001";    //辞退
    public static final String BLACK_TYPE_KC = "60060002";    //开除
    public static final String BLACK_TYPE_QT = "6006400746";    //劝退
    //人员状态
    public static final String PERSON_STAT_ZG = "70021001";    //在岗
    public static final String PERSON_STAT_DC = "70021006";    //调出
    public static final String PERSON_STAT_CZ = "70021007";    //辞职
    public static final String PERSON_STAT_TX = "70021005";    //退休
    public static final String PERSON_STAT_QT = "70021008";    //劝退
    public static final String PERSON_STAT_KC = "70021009";    //开除
    public static final String PERSON_STAT_CT = "70021010";    //辞退
    public static final String PERSON_STAT_DEAD = "70021011";  //死亡
    public static final String PERSON_STAT_OTHER = "70021012";  //其他

    public static final String POST_NUM_FIRST = "3100400694";    //职务一
    public static final String POST_NUM_SECOND = "3100400695";   //职务二
    public static final String FULL_TIME = "3109400475";   //全日制
}
