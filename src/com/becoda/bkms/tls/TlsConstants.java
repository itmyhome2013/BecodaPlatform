package com.becoda.bkms.tls;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 培训相关的静态变量均在此类维护.
 * User: kangdw
 * Date: 2015-7-28
 * Time: 14:55:03
 * To change this template use File | Settings | File Templates.
 */
public class TlsConstants {
    public final static String MODULE_NAME = "PXGL";
    public final static String UPLOAD_PATH = "/file/tls/upload/";
    public final static String DOWNLOAD_PATH = "/file/tls/download/";
    public static final String TEMPLATE_PATH = "/file/tls/template/";

    /**start 培训计划相关**/

    /**end 培训计划相关**/

    /**
     * start 在线管理相关
     */
    //教师类型
    public final static String INNER_TEACHER_TYPE = "22160";  //内部老师
    public final static String OUT_TEACHER_TYPE = "22161";  //外部老师
    //培训结业状态
    public static final String END_STATUS_NO= "11221";       //未结业
    public static final String END_STATUS_YES = "11220";    //结业
    //指标常量
    public static final String SETID_COURSE = "3121";       //课程
    public static final String SETID_EXAMINEE = "3127";     //考试
    public static final String SETID_EXAMINEE_PROP = "3128";

    //执行状态未执行、执行中、结束
    public final static String EXEC_STATUS_NO = "3124400805";  //未执行
    public final static String EXEC_STATUS_YES = "3124400806"; //执行中
    public final static String EXEC_STATUS_END = "3124400807"; //结束
    //培训人员审核状态
    public final static String CR_SIGNUPTYPE_HAND = "1";  //手工增加
    public final static String CR_SIGNUPTYPE_NET = "2"; //网上报名
    //培训人员归结状态
    public final static String CR_SUMUPSTATUS_YES = "3125400809";  //已归结
    public final static String CR_SUMUPSTATUS_NO = "3125400808"; //未归结
    //培训人员审核状态
    public final static String CR_PERSONSTATUS_NO = "0";  //审批中
    public final static String CR_PERSONSTATUS_YES = "1"; //审批通过
    public final static String CR_PERSONSTATUS_DIS = "2"; //审批未通过
    //调整状态
    public final static String CR_ADJUSTSTATUS_NO = "11210";  //常规
    public final static String CR_ADJUSTSTATUS_YES = "11211"; //追加
    public final static String CR_ADJUSTSTATUS_DIS = "11212"; //废弃

//培训审批状态
    public final static String CR_STATUS_NO = "3013000142";  //审批中
    public final static String CR_STATUS_YES = "3013000143"; //审批通过
    public final static String CR_STATUS_DIS = "3013000144"; //审批未通过
    //信息管理
    public final static String INFO_STATUS_NO = "0";  //未发布
    public final static String INFO_STATUS_YES = "1"; //已发布
    public final static String INFO_STATUS_DIS = "2"; //取消发布

    public final static String IS_TOP = "1"; //置顶：1
    public final static String IS_NOT_TOP = "0"; //未置顶：0

    public final static String IS_BANNER = "1"; //置顶：1
    public final static String IS_NOT_BANNER = "0"; //未置顶：0
    //问卷调查
    public final static String QU_STATUS_NO = "0";    //未发布
    public final static String QU_STATUS_YES = "1";   //已发布
    public final static String QU_STATUS_DIS = "2";   //取消发布
    public final static String QU_STATUS_OVER = "3";  //调查完毕

    //课件状态
    public final static String COURSE_STATUS_PUBLISH_NO = "0";    //未发布
    public final static String COURSE_STATUS_PUBLISH_YES = "1";   //已发布
    public final static String COURSE_STATUS_PUBLISH_CANCEL = "2";//取消发布

    //课件类别
    public final static String COURSE_TYPE_AICC = "1";    //aicc课件
    public final static String COURSE_TYPE_SCORM = "2";   //scorm课件
    public final static String COURSE_TYPE_DOC = "3";     //文件类型
    
    //课件考试方式
    public final static String COURSE_EXAM_WAY_1 = "1";    //学习完成后考试
    public final static String COURSE_EXAM_WAY_2 = "2";    //直接考试
    
    //课件选修方式
//    public final static String COURSE_SEL_TYPE_1 = "1";    //直接选修
//    public final static String COURSE_SEL_TYPE_2 = "2";    //需要授权
    //课件权限方式
    public final static String COURSE_RIGHT_TYPE_DEPT = "1";    //部门
    public final static String COURSE_RIGHT_TYPE_PERSON = "2";    //人员
    //课件类型
    public static final String COURSE_SEL_TYPE_COMPULSORY = "1";    //必修课
    public static final String COURSE_SEL_TYPE_ELECTIVE = "2";    //选修课

    //题型
    public final static String QUESTION_MODEL_SINGLE = "1";    //单选
    public final static String QUESTION_MODEL_MULTI = "2";    //多选
    public final static String QUESTION_MODEL_CHECK = "3";    //判断
    public final static String QUESTION_MODEL_ANSWER = "4";    //简答题
    public final static String QUESTION_MODEL_SINGLE_DESC = "单选";    //单选
    public final static String QUESTION_MODEL_MULTI_DESC = "多选";    //多选
    public final static String QUESTION_MODEL_CHECK_DESC = "判断";    //判断
    public final static String QUESTION_MODEL_ANSWER_DESC = "简答";    //简答题
    public static final String QUESTION_MODEL_DESC_STR = QUESTION_MODEL_SINGLE_DESC + "," + QUESTION_MODEL_MULTI_DESC + "," + QUESTION_MODEL_CHECK_DESC + "," + QUESTION_MODEL_ANSWER_DESC;

    public static String getQuestionModelDesc(String qmd) {
        if (QUESTION_MODEL_SINGLE.equals(qmd)) {
            return QUESTION_MODEL_SINGLE_DESC;
        }else if (QUESTION_MODEL_MULTI.equals(qmd)) {
            return QUESTION_MODEL_MULTI_DESC;
        }else if (QUESTION_MODEL_CHECK.equals(qmd)) {
            return QUESTION_MODEL_CHECK_DESC;
        }else if (QUESTION_MODEL_ANSWER.equals(qmd)) {
            return QUESTION_MODEL_ANSWER_DESC;
        }
        return "";
    }

    public static String getQuestionModelCode(String qmd) {
        if (QUESTION_MODEL_SINGLE_DESC.equals(qmd)) {
            return QUESTION_MODEL_SINGLE;
        }else if (QUESTION_MODEL_MULTI_DESC.equals(qmd)) {
            return QUESTION_MODEL_MULTI;
        }else if (QUESTION_MODEL_CHECK_DESC.equals(qmd)) {
            return QUESTION_MODEL_CHECK;
        }else if (QUESTION_MODEL_ANSWER_DESC.equals(qmd)) {
            return QUESTION_MODEL_ANSWER;
        }
        return "";
    }

    public static boolean isValidQuestionModel(String qmd) {
        return !getQuestionModelCode(qmd).equals("");
    }

    //考试类别
    public final static String EXAM_TYPE_KCKS = "1";    //课程考试
    public final static String EXAM_TYPE_TYKS = "2";    //统一考试

    //试题,试卷状态
    public final static String STATUS_VALID = "1";      //启用
    public final static String STATUS_INVALID = "2";    //作废

    //union exam
    //未发布：0，已发布：1，取消发布：2，考试中：3 ，考试完成：4，阅卷中：5，阅卷完毕：6
    public final static String EXAM_STATUS_NO="0";
    public final static String EXAM_STATUS_ISSUE="1";
    public final static String EXAM_STATUS_CANCLE="2";
    public final static String EXAM_STATUS_EXAMING="3";
    public final static String EXAM_STATUS_FINISH_EXAM="4";
    public final static String EXAM_STATUS_CHECKING="5";
    public final static String EXAM_STATUS_FINISH_CHECK="6";

    public final static Map EXAM_STATUS_DESC_MAP = new LinkedHashMap();
    static {
        EXAM_STATUS_DESC_MAP.put(EXAM_STATUS_NO,"未发布");
        EXAM_STATUS_DESC_MAP.put(EXAM_STATUS_ISSUE,"已发布");
        EXAM_STATUS_DESC_MAP.put(EXAM_STATUS_CANCLE,"取消发布");
        EXAM_STATUS_DESC_MAP.put(EXAM_STATUS_EXAMING,"考试中");
        EXAM_STATUS_DESC_MAP.put(EXAM_STATUS_FINISH_EXAM,"考试完成");
        EXAM_STATUS_DESC_MAP.put(EXAM_STATUS_CHECKING,"阅卷中");
        EXAM_STATUS_DESC_MAP.put(EXAM_STATUS_FINISH_CHECK,"阅卷完毕");
    }
    
    public static String getExamStatusDesc(String status) {
        String desc = (String) EXAM_STATUS_DESC_MAP.get(status);
        return desc == null ? "" : desc;
    }
    

    /**start 信息板块名称**/
    public final static String PX_TZ = "3118400765";//培训通知
    public final static String PX_DT = "3118400764";//培训动态
    public final static String PX_JSFC = "3118400766";//讲师风采
    public final static String PX_TSTJ = "3118400767";//图书推荐
    public final static String PX_XYTD = "3118400768";//学员天地
    public final static String PX_XYTD2 = "3118400911";//学员天地2
    public final static String PX_CJWT = "3118400769";//常见问题
    public final static String PX_XZQ = "3118400770";//下载区
    /**end 信息板块名称**/
    
    /**start 积分类型**/
    public final static String JF_DL = "3135400940";//登陆积分
    public final static String JF_ZXTW = "3135400941";//答疑区在线提问
    public final static String JF_ZXTY = "3135400942";//答疑区在线回答
    public final static String JF_PXDC = "3135400944";//参与培训调查
    public final static String JF_BX = "3135400945";//在线课程学习（必修）
    public final static String JF_XX = "3135400946";//在线课程学习（选修）
    public final static String JF_KSCJ = "3135400947";//在线考试成绩



    
    public static String getSelTypeDesc(String type) {
        if (COURSE_SEL_TYPE_COMPULSORY.equals(type)) {
            return "必修";
        }else if (COURSE_SEL_TYPE_ELECTIVE.equals(type)) {
            return "选修";
        }
        return "";
    }
}
