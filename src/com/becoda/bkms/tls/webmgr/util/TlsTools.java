package com.becoda.bkms.tls.webmgr.util;

import com.becoda.bkms.util.Tools;

public class TlsTools {

    public static String trSur_type(String code) {//调查问卷类型 单选：1 多选：2

        switch (Integer.parseInt(code)) {
            case 1:
                return "多选";
            case 2:
                return "单选";

        }
        return null;
    }

    public static String trSur_jointype(String code) {//问卷调查人员 所有人员：1 指定人员：2

        switch (Integer.parseInt(code)) {
            case 1:
                return "所有人员";
            case 2:
                return "指定人员";

        }
        return null;
    }

    public static String trSur_stats(String code) {//调查问卷状态 未发布：0，已发布：1，取消发布：2，调查完毕：3

        switch (Integer.parseInt(code)) {
            case 0:
                return "未发布";
            case 1:
                return "已发布";
            case 2:
                return "取消发布";
            case 3:
                return "调查完毕";


        }
        return null;
    }

    //课程状态 未发布：0，已发布：1，取消发布：2
    public static String getCourseStatus(String status) {
        if (Tools.filterNull(status).equals("")) return "";
        switch (Integer.parseInt(status)) {
            case 0:
                return "未发布";
            case 1:
                return "已发布";
            case 2:
                return "已禁用";
        }
        return null;
    }

    //课程类别 AICC：1，SCORM：2，DOC：普通文件
    public static String getCourseStudyType(String type) {
        if (Tools.filterNull(type).equals("")) return "";
        switch (Integer.parseInt(type)) {
            case 1:
                return "AICC";
            case 2:
                return "SCORM";
            case 3:
                return "普通文件";
        }
        return null;
    }

    //题型 单选题：1，多选题：2，判断题：3；简单题：4
    public static String getQuestionModel(String model) {
        if (Tools.filterNull(model).equals("")) return "";
        switch (Integer.parseInt(model)) {
            case 1:
                return "单选题";
            case 2:
                return "多选题";
            case 3:
                return "判断题";
            case 4:
                return "简答题";
        }
        return null;
    }


    //考试类型 课程考试：1，统一考试：
    public static String getExamType(String type) {
        if (Tools.filterNull(type).equals("")) return "";
        switch (Integer.parseInt(type)) {
            case 1:
                return "课程考试";
            case 2:
                return "统一考试";
        }
        return null;
    }

    //试题所属 课程类：1，统一考试类：2
    public static String getQuestionBelongTo(String bt) {
        if (Tools.filterNull(bt).equals("")) return "";
        switch (Integer.parseInt(bt)) {
            case 1:
                return "课件类";
            case 2:
                return "统一考试类";
        }
        return null;
    }


    //试题、试卷状态 启用：1，作废：2
    public static String getStatus(String status) {
        if (Tools.filterNull(status).equals("")) return "";
        switch (Integer.parseInt(status)) {
            case 1:
                return "启用";
            case 2:
                return "作废";
        }
        return null;
    }


    public static String trEva_stats(String code) {//评估表状态 启用：0禁用:1

        switch (Integer.parseInt(code)) {
            case 0:
                return "启用";
            case 1:
                return "禁用";
        }
        return null;
    }

    public static String trEva_type(String code) {//评估类别 评估班：1；内训师:2

        switch (Integer.parseInt(code)) {
            case 1:
                return "培训班";
            case 2:
                return "内训师";
        }
        return null;
    }

    public static String trEva_level(String code) {//评估层次 1;2

        switch (Integer.parseInt(code)) {
            case 1:
                return "一级";
            case 2:
                return "二级";
        }
        return null;
    }

    public static String trEvaItemType(String code) {//评估项类型 填空题1;简单题2

        switch (Integer.parseInt(code)) {
            case 1:
                return "填空题";
            case 2:
                return "简单题";
        }
        return null;
    }


    //union exam
    public static String getUnionExamStatus(String status){
        status=Tools.filterNullToZero(status);
        switch (Integer.parseInt(status)) {
            case 0:
                return "未发布";
            case 1:
                return "已发布";
            case 2:
                return "取消发布";
            case 3:
                return "考试中";
            case 4:
                return "考试完成";
            case 5:
                return "阅卷中";
            case 6:
                return "阅卷完毕";
        }
        return "";
    }
    //评估项SEQ转换,JSP显示
    public static String tranSEQ(String seq){
    	String s2="";
    	if(seq.length()==6){
    	 s2 = seq.substring(3);
    	}
    	else
    		s2=seq;
    	
    	int f2 = 0;
    	for(int i = 0;i<s2.length();i++){
    		if(s2.charAt(i)!='0'){
    			break;
    		}else
    			f2++;
    		
    			
    	}
    	s2=s2.substring(f2,3);
    	if(s2.equals(""))
    		s2="0";
    	return s2;
    	
    	
    	
    	
    }
    //一级指标SEQ显示
    public static String tranSEQ2(String seq){
    	String s2="";
    	if(seq.length()==6){
    	 s2 = seq.substring(0,3);
    	}
    	else
    		return "none";
    	
    	int f2 = 0;
    	for(int i = 0;i<s2.length();i++){
    		if(s2.charAt(i)!='0'){
    			break;
    		}else
    			f2++;
    		
    			
    	}
    	s2=s2.substring(f2,3);
    	return s2;
    	
    	
    	
    	
    }
}
