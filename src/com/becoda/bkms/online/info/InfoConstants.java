package com.becoda.bkms.online.info;


public class InfoConstants {

    //信息管理
    public final static String INFO_STATUS_NO = "0";  //未发布
    public final static String INFO_STATUS_YES = "1"; //已发布
    public final static String INFO_STATUS_DIS = "2"; //取消发布

    public final static String IS_TOP = "1"; //置顶：1
    public final static String IS_NOT_TOP = "0"; //未置顶：0

    /*
     * category   WSTS:0, WSZX:1, JYZJ:2
     */
    public final static String WSTS = "0";
    public final static String WSZX = "1";
    public final static String JYZJ = "2";
    
    public final static String WSTS_CODE = "3004";
    public final static String WSZX_CODE = "3005";
    public final static String JYZJ_CODE = "3006";
    
    public final static String IS_PUBLIC = "00901";//公开
    public final static String IS_NOT_PUBLIC = "00900";//不公开
    
    public static String getInfoCode(String category){
    	String st = "";
    	if(category.equals(WSTS)){
    		st = WSTS_CODE;
    	}
    	if(category.equals(WSZX)){
    		st = WSZX_CODE;
    	}
    	if(category.equals(JYZJ)){
    		st = JYZJ_CODE;
    	}
    	return st;
    }
    
    public static String getMenuName(String category){
    	String st = "";
    	if(category.equals(WSTS_CODE)){
    		st = "网上投诉";
    	}
    	if(category.equals(WSZX_CODE)){
    		st = "网上咨询";
    	}
    	if(category.equals(JYZJ_CODE)){
    		st = "建议征集";
    	}
    	return st;
    }
}
