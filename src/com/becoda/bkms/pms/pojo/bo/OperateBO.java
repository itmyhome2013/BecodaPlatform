package com.becoda.bkms.pms.pojo.bo;


/**
 * 功能BO<br>
 * author:lirg<br>
 * 2015-5-24
 */
public class OperateBO {
    /**
     * 机构管理模块ID
     */
    public static String ORGMODULEID = "JGGL";
    /**
     * 人员管理模块    ID
     */
    public static String PERMODULEID = "RYGL";
    /**
     * 岗位管理模块  ID
     */
    public static String POSTMODULEID = "GWGL";
    /**
     * 合同管理模块ID
     */
    public static String CONTMODULEID = "HTGL";
    /**
     * 绩效考核模块ID
     */
    public static String EVAMODULEID = "JXGL";
    /**
     * 薪酬管理模块    ID
     */
    public static String WAGEMODULEID = "XCGL";
    /**
     * 查询管理模块  ID
     */
    public static String QUERYMODULEID = "CXGL";
    /**
     * 运行管理模块ID
     */
    public static String RUNMODULEID = "YXGL";
    /**
     * 系统管理模块ID
     */
    public static String SYSMODULEID = "XTGL";
    /**
     * 报表管理模块ID
     */
    public static String RPTMODULEID = "BBGL";

    /**
     * 年薪管理模块ID
     */
    public static String NSlMODULEID = "NXGL";

    /**
     * 流程管理模块ID
     */
    public static String WFSMODULEID = "LCGL";
    /**
     * 调查管理模块ID
     */
    public static String MYDMODULEID = "DCGL";
    /**
     * 培训管理模块ID
     */
    public static String PXMODULEID = "PXGL";
    /**
     * 出国管理模块ID
     */
    public static String CGMODULEID = "CGGL";
    /**
     * 考勤管理模块ID
     */
    public static String KQMODULEID = "KQGL";
    /**
     * 招聘管理模块ID
     */
    public static String ZPMODULEID = "ZPGL";
    /**
     * 工作流程模块
     */
    public static String GZLCMODULEID = "GZLC";
    /**
     * 功能ID
     */
    private String operateId;

    /**
     * 功能名称
     */
    private String operateName;

    /**
     * url路径
     */
    private String url;

    /**
     * 节点
     */
    private String treeId;

    /**
     * 功能类别
     * 1 业务用户菜单功能
     * 2 界面按钮权限
     */
    private String operateType;
    /**
     * 父节点
     */
    private String superId;
    /**
     * 模块编号
     */
    private String moduleID;

    private String sysFlag;
    /**
     * 简写
     */
    private String abbreviation;//add by hh
    /**
     * 模块信息
     */
    private String moduleIntro;//add by hh
    /**
     * 模块排序
     */
    private String moduleNum;//add by hh

    /**
     * fxj添加
     * 菜单模块：启用/禁用
     */
    private String moduleStatus;
    
    
    public String getModuleStatus() {
		return moduleStatus;
	}

	public void setModuleStatus(String moduleStatus) {
		this.moduleStatus = moduleStatus;
	}

	public String getModuleIntro() {
        return moduleIntro;
    }

    public void setModuleIntro(String moduleIntro) {
        this.moduleIntro = moduleIntro;
    }

    public String getModuleNum() {
        return moduleNum;
    }

    public void setModuleNum(String moduleNum) {
        this.moduleNum = moduleNum;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }
}
