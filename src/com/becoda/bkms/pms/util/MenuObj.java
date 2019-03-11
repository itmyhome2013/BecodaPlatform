package com.becoda.bkms.pms.util;

import com.becoda.bkms.pms.pojo.bo.OperateBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-6-23
 * Time: 16:02:41
 * To change this template use File | Settings | File Templates.
 */
public class MenuObj {


    /**
     * 所有的子菜单,按TreeID排序， object = MenuObj
     */
    private List menus;
    private String operateId;
    private String name;
    private String treeId;
    private String superId;
    private String url;
    private String moduleId;
    private String abbreviation;//add by hh
    private String moduleIntro;//add by hh
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

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void Clone(OperateBO operate) {
        this.setOperateId(operate.getOperateId());
        this.setName(operate.getOperateName());
        this.setSuperId(operate.getSuperId());
        this.setTreeId(operate.getTreeId());
        this.setUrl(operate.getUrl());
        this.setModuleId(operate.getModuleID());
        this.setAbbreviation(operate.getAbbreviation());
        this.setModuleIntro(operate.getModuleIntro());
        this.setModuleNum(operate.getModuleNum());
        this.setModuleStatus(operate.getModuleStatus());
    }

    public List getMenus() {
        return menus;
    }

    public void setMenus(List menus) {
        this.menus = menus;
    }

}
